package prog04;

import java.util.EmptyStackException;

/** Implementation of the interface StackInterface<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInterface<E> {
  // Data Fields
  /** Storage for stack. */
  E[] stackArray;

  /** Index to top of stack. */
  int topIndex = -1; // initially -1 because there is no top

  private static final int INITIAL_CAPACITY = 4;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    stackArray = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {

    if (topIndex == stackArray.length-1) {
        E[] newArray = (E[])new Object[topIndex*2];
        System.arraycopy(stackArray, 0, newArray,0,stackArray.length);
        stackArray = newArray;
    }

    topIndex++;
    stackArray[topIndex] = obj;
    return obj;
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();
    E entry = stackArray[topIndex];
    stackArray[topIndex] = null;
    --topIndex;
    /**** EXERCISE ****/
    return entry; // fix this
  }

  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek () {
      if (empty()) {
          throw new EmptyStackException();
      }
    /**** EXERCISE ****/


    return stackArray[topIndex]; // fix this
  }

  /**** EXERCISE ****/
  /** Returns true if the stack is empty; otherwise it returns false.
      @return true if the stack is empty; false if not
   */
  public boolean empty() {

      if (topIndex == -1) {
          return true;
      }
      return false;
  }
}
