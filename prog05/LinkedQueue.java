package prog05;

import java.util.Queue;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements the Queue interface using a singly-linked list.
 **/
public class LinkedQueue<E> extends AbstractQueue<E>
  implements Queue<E> {

  // Data Fields
  /** Reference to the Node with the first element in the queue. */
  protected Node<E> top;
  /** Reference to the Node with the last element in the queue. */
  protected Node<E> bottom;
  /** Size of queue. */
  protected int size;

  /** A Node is the building block for a single-linked list. */
  protected static class Node<E> {
    // Data Fields

    /** The reference to the element. */
    protected E element;
    /** The reference to the next node. */
    protected Node<E> next;

    // Constructors
    /**
     * Creates a new node with a null next field.
     * @param element The element stored
     */
    protected Node (E element) {
      this.element = element;
      next = null;
    }
  } //end class Node

  // Methods
  /**
   * Insert an element at the bottom of the queue.
   * @post element is added to the bottom of the queue.
   * @param element The element to add
   * @return true (always successful)
   */
  @Override
  public boolean offer (E element) {

      Node newNode = new Node<E>(element);
      if (size == 0) {
        top = newNode;
        bottom = top;
        ++size;
        return true;
      }
      bottom.next = newNode;
      bottom = newNode;
      ++size;

    return true;
  }

  /**
   * Remove the top element of the queue and return it
   * if the queue is not empty.
   * @post top references element that was second in the queue.
   * @return The element removed if successful, or null if not
   */
  @Override
  public E poll () {
    if (isEmpty()) {
      return null;
    }
    E element = top.element;// fix
    top = top.next;
    --size;
    if (size == 0) {
      bottom = null;
    }

    // EXERCISE 5

    return element; // Return data at top of queue.
  }

  /**
   * Return the element at the top of the queue without removing it.
   * @return The element at the top of the queue if successful;
   * return null if the queue is empty
   */
  @Override
  public E peek () {
    if (isEmpty())
      return null;

    // EXERCISE 5
    return top.element;
  }

  /**
   * Returns the size of the queue
   * @return the size of the queue
   */
  @Override
  public int size () {
    return size;
  }

  /**
   * Converts the queue into a String.
   * @return A String representation of the queue.
   */
  @Override
  public String toString () {
    String s = "";
    // EXERCISE 6
    for (Node n = top; n != null; n = n.next ) {
      s += n.element;
      s += " ";
    }
    return s;
  }

  /**
   * Returns an iterator to the contents of this queue
   * @return an iterator to the contents of this queue
   */
  public Iterator<E> iterator () {
    return new Iter();
  }

  /**
   * Inner class to provide an iterator to the contents of
   * the queue.
   */
  protected class Iter implements Iterator<E> {
    // EXERCISE 7
    protected int offset;
    Node <E> newTop = top;
    /**
     * Returns true if there are more elements in the iteration
     * @return true if there are more elements in the iteration
     */
    @Override
    public boolean hasNext () {
      // EXERCISE 7
      if (newTop != null) {
        return true;
      }
      return false; // fix
    }
    /**
     * Return the next element in the iteration and advance the iterator
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next () {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
        // fix
      E element = newTop.element;
      newTop = newTop.next;
        return element;
      // EXERCISE  7
    }
    /**
     * Removes the last element returned by this iteration
     * @throws UnsupportedOperationException this operation is not
     * supported
     */
    @Override
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
}
/*</listing>*/
