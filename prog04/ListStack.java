package prog04;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInterface<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInterface<E> {
  // Data Fields
  /** Storage for stack. */
  List<E> stackList;

  /** Initialize stackList to an empty List. */
  public ListStack() {
    stackList = new ArrayList<E>();
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    stackList.add(obj);
    return obj;
  }

  /**** EXERCISE ****/
  /** Returns the object at the top of the stack without removing it
      or changing the stack.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return stackList.get(stackList.size() - 1); // fix this
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack has one less item.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop() {
    if (empty()) {
      throw new EmptyStackException();
    }
    return stackList.get(stackList.size() - 1); // fix this
  }

  /** Returns true if the stack is empty; otherwise it returns false.
      @return true if the stack is empty; false if not
   */
  public boolean empty() {
    return stackList.isEmpty();
    }
  }

