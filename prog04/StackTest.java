package prog04;
import java.util.EmptyStackException;

public class StackTest {
  public static boolean testEmpty (StackInterface<Integer> s, boolean value) {
    boolean ret = false;
    try {
      ret = s.empty();
    } catch (Exception e) {
      System.out.println("empty " + e);
      return false;
    }
    if (ret != value) {
      System.out.println("empty returned " + ret + " instead of " + value);
      return false;
    }
    return true;
  }

  public static boolean testPeek (StackInterface<Integer> s, boolean empty, Integer value) {
    if (empty) {
      try {
        s.peek();
        System.out.println("peek on empty stack did not throw EmptyStackException.");
      } catch (EmptyStackException e) {
        return true;
      } catch (Exception e) {
        System.out.println("peek on empty stack threw " + e);
      }
    }
    else {
      Integer ret = -1;
      try {
        ret = s.peek();
      } catch (Exception e) {
        System.out.println("peek " + e);
        return false;
      }
      if (ret != value) {
        System.out.println("peek returned " + ret + " instead of " + value);
        return false;
      }
    }
    return true;
  }

  public static boolean testPop (StackInterface<Integer> s, boolean empty, Integer value) {
    if (empty) {
      try {
        s.pop();
        System.out.println("pop on empty stack did not throw EmptyStackException.");
      } catch (EmptyStackException e) {
        return true;
      } catch (Exception e) {
        System.out.println("pop on empty stack threw " + e);
      }
    }
    else {
      Integer ret = -1;
      try {
        ret = s.pop();
      } catch (Exception e) {
        System.out.println("pop on non-empty stack threw " + e);
        return false;
      }
      if (ret != value) {
        System.out.println("pop returned " + ret + " instead of " + value);
        return false;
      }
    }
    return true;
  }

  public static boolean testPush (StackInterface<Integer> s, Integer value) {
    Integer ret;
    try {
      ret = s.push(value);
    } catch (Exception e) {
      System.out.println("push " + e);
      return false;
    }
    if (ret != value) {
      System.out.println("push of " + value + " returned " + value);
      return false;
    }
    return true;
  }

  public static boolean test (StackInterface<Integer> stack) {
    return (testEmpty(stack, true) &&
            testPeek(stack, true, 0) &&
            testPop(stack, true, 0) &&
            testPush(stack, 17) &&
            testEmpty(stack, false) &&
            testPeek(stack, false, 17) &&
            testPop(stack, false, 17) &&
            testEmpty(stack, true) &&
            testPeek(stack, true, 17) &&
            testPush(stack, 17) &&
            testPush(stack, 23) &&
            testPush(stack, 31) &&
            testPush(stack, 47) &&
            testPush(stack, 56) &&
            testEmpty(stack, false) &&
            testPeek(stack, false, 56) &&
            testPop(stack, false, 56));
  }

  public static void main (String[] args) {
    try {
      test(new ArrayStack());
      test(new LinkedStack());
      test(new ListStack());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
  
    
