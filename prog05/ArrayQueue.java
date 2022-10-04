package prog05;

import java.util.*;

/**
 * Implements the Queue interface using a circular array (ring buffer).
 **/
public class ArrayQueue<E> extends AbstractQueue<E>
  implements Queue<E> {

  // Data Fields
  /** Index of the first element of the queue. */
  protected int first;
  /** Current size of the queue. */
  protected int size;
  /** Default capacity of the queue. */
  protected static final int DEFAULT_CAPACITY = 5;
  /** Array to hold the elements. */
  protected E[] theElements;

  // Constructors
  /**
   * Construct a queue with the default initial capacity.
   */
  public ArrayQueue () {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Construct a queue with the specified initial capacity.
   * @param initCapacity The initial capacity
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue (int initCapacity) {
    theElements = (E[]) new Object[initCapacity];
    first = 0;
    size = 0;
  }

  // Public Methods
  /**
   * Inserts a new element as last.
   * @post element is added as last.
   * @param element The element to add.
   * @return true (always successful)
   */
  @Override
  public boolean offer (E element) {
  if (theElements.length == size) {
    reallocate();
  }

    // Store the new element at the next available index.
    theElements[(first + size) % theElements.length] = element;
    // Increment size.
    size++;
    return true;
  }

  /**
   * Returns the first element queue without removing it.
   * @return The first element of the queue (if successful).
   * return null if the queue is empty
   */
  @Override
  public E peek () {
    if (isEmpty())
      return null;
    return theElements[first];
  }

  /**
   * Removes the entry at the first of the queue and returns it
   * if the queue is not empty.
   * @post first references element that was second in the queue.
   * @return The element removed if successful or null if not
   */
  @Override
  public E poll () {
    // EXERCISE 2
    if (isEmpty()) {
      return null;
    }
    E result = theElements[first];// not right
    theElements[first] = null;
    ++first;
    --size;
    if(first == theElements.length) {
      first = 0;
    }

    return result;
  }

  /**
   * Return the size of the queue
   * @return The number of elements in the queue
   */
  @Override
  public int size () {
    return size;
  }

  /**
   * Converts the queue into a String.
   * @return A String representation of the queue.
   */
  public String toStringX () {
    String s = "";
    // EXERCISE 3
    for (int offset = 0; offset < size; ++offset) {
      s += theElements[(first + offset) % theElements.length];
      s += " ";
    }

    return s;
  }

  /**
   * Returns an iterator to the elements in the queue
   * @return an iterator to the elements in the queue
   */
  @Override
  public Iterator<E> iterator () {
    return new Iter();
  }
    
  // Protected Methods
  /** Inner class to implement the Iterator<E> interface. */
  protected class Iter implements Iterator<E> {
    // EXERCISE 4a
    protected int offset = 0;

    /**
     * Returns true if there are more elements in the queue to access.
     * @return true if there are more elements in the queue to access.
     */
    @Override
    public boolean hasNext () {
       //EXERCISE 4b
      if ((first + offset) == theElements.length) {
        return false;
      }
      if (theElements[(first + offset) % theElements.length] != null) {
        return true; // fix
      }
      return false;
    }

    /**
     * Returns the next element in the queue.
     * @pre index references the next element to access.
     * @post index and count are incremented.
     * @return The element with subscript index
     */
    @Override
    public E next () {
      // EXERCISE 4c
      if (!hasNext()) {
        throw new NoSuchElementException();

      } else {
        E returnValue = theElements[(first + offset) % theElements.length];
        ++offset;
        return returnValue;
      }

    }

    /**
     * Remove the element accessed by the Iter object -- not implemented.
     * @throws UnsupportedOperationException when called
     */
    @Override
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Double the capacity and reallocate the elements.
   * @pre The array is filled to capacity.
   * @post The capacity is doubled and the first half of the
   *       expanded array is filled with elements.
   */
  @SuppressWarnings("unchecked")
  protected void reallocate () {
    int newCapacity = 2 * theElements.length;
    E[] newElements = (E[]) new Object[newCapacity];
    for (int offset = 0; offset < size; offset++)
      newElements[offset] = theElements[(first + offset) % theElements.length];
    theElements = newElements;
    first = 0;
  }

  public static void main (String[] args) {
    ArrayQueue<String> q = new ArrayQueue<String>();

    q.offer("Victor");
    q.offer("Lisa");
    q.offer("Hal");
    q.offer("Zoe");
    System.out.println(q);
  }

}
