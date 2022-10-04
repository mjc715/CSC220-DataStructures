package prog05;

import java.util.Iterator;

public class TestLinkedQueue extends LinkedQueue {
  String[] data = { "bob", "ann", "eve" };

  void init (int n) {
    size = n;
    if (n == 0) {
      top = bottom = null;
      return;
    }
    top = bottom = new Node(data[n-1]);
    for (int i = n-2; i >= 0; i--) {
      Node node = new Node(data[i]);
      node.next = top;
      top = node;
    }
  }

  void printIterator () {
    System.out.print("Iterator:");
    for (Iterator iter = iterator(); iter.hasNext();)
      System.out.print(" " + iter.next());
    System.out.println();
  }

  void print () {
    for (Node entry = top; entry != null; entry = entry.next)
      System.out.print(entry.element + " ");
    System.out.println(" bottom = " + (bottom == null ? bottom : bottom.element) + ", size = " + size);
    printIterator();
  }
  
  boolean equals (int a, int b) {
    if (size != b - a)
      return false;
    if (size == 0)
      return top == null && bottom == null;
    int i = a;
    for (Node entry = top; entry != null; entry = entry.next) {
      if (i == b || !data[i].equals(entry.element))
        return false;
      i++;
    }
    if (i != b)
      return false;
    if (!data[b-1].equals(bottom.element))
      return false;
    return true;
  }

  boolean testIterator (int n) {
    int count = 0;
    for (Iterator iter = iterator(); iter.hasNext(); iter.next())
      count++;
    if (count != size)
      return false;
    Iterator it = iterator();
    for (Node entry = top; entry != null; entry = entry.next)
      if (!entry.element.equals(it.next()))
        return false;
    return true;
  }

  boolean testPeek (int n) {
    init(n);
    print();
    System.out.println("peek()");
    Object r;
    try {
      r = peek();
      System.out.println("returns " + r);
      if (r == null || !r.equals(data[0]))
        return false;
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      return false;
    }      
    print();
    if (!equals(0, n))
      return false;
    System.out.println();
    return true;
  }
    
  boolean testPoll (int n) {
    init(n);
    print();
    System.out.println("poll()");
    Object r;
    try {
      r = poll();
      System.out.println("returns " + r);
      if (r == null || !r.equals(data[0]))
        return false;
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      return false;
    }      
    print();
    if (!equals(1, n))
      return false;
    System.out.println("end poll");
    return true;
  }
    
  boolean testOffer (int n) {
    init(n);
    print();
    System.out.println("offer(" + data[n] + ")");
    Object r;
    try {
      r = offer(data[n]);
      System.out.println("returns " + r);
      if (r == null || !r.equals(true))
        return false;
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      return false;
    }      
    print();
    if (!equals(0, n+1))
      return false;
    System.out.println();
    return true;
  }

  boolean test () {
    init(0);
    System.out.println("testing peek() on empty queue");
    try {
      if (peek() != null) {
        System.out.println("peek on empty is not null");
        return false;
      }
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      return false;
    }
    System.out.println();

    System.out.println("testing poll() on empty queue");
    try {
      if (poll() != null) {
        System.out.println("poll on empty is not null");
        return false;
      }
    } catch (Exception e) {
      System.out.println("ERROR: " + e);
      return false;
    }
    System.out.println();

    if (!testOffer(0)) return false;
    if (!testIterator(0)) return false;

    for (int n = 1; n <= 3; n++) {
      if (!testIterator(n)) return false;
      if (!testPeek(n)) return false;
      if (!testPoll(n)) return false;
      if (n < 3)
        if (!testOffer(n)) return false;
    }

    return true;
  }
    
  public static void main (String[] args) {
    TestLinkedQueue test = new TestLinkedQueue();

    if (test.test())
      System.out.println("PASS");
    else
      System.out.println("FAIL");
  }
}
