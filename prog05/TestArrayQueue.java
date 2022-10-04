package prog05;

import java.util.Iterator;

public class TestArrayQueue extends ArrayQueue {
  class State {
    String[] data;
    int f, l, s;
    State (String[] data, int f, int l, int s) {
      this.data = data;
      this.f = f;
      this.l = l;
      this.s = s;
    }

    void set () {
      theElements = new Object[data.length];
      for (int i = 0; i < data.length; i++)
        theElements[i] = data[i];
      first = f;
      size = s;
    }

    boolean equals (Object a, Object b) {
      if ((a == null) != (b == null))
        return false;
      if (a == null)
        return true;
      return a.equals(b);
    }

    boolean same () {
      if (theElements.length != data.length)
        return false;
      for (int i = 0; i < data.length; i++)
        if (!equals(theElements[i], data[i]))
          return false;
      return first == f && size == s;
    }
  }

  class Test {
    State before;
    String call;
    String arg;
    Object ret;
    State after;
    State after2;
    Test (State before, String call, String arg, Object ret, State after) {
      this.before = before;
      this.call = call;
      this.arg = arg;
      this.ret = ret;
      this.after = after;
    }
    Test (State before, String call, String arg, Object ret, State after, State after2) {
      this.before = before;
      this.call = call;
      this.arg = arg;
      this.ret = ret;
      this.after = after;
      this.after2 = after2;
    }

    void printIterator () {
      System.out.print("Iterator:");
      int count = 0;
      for (Iterator iter = iterator(); iter.hasNext(); count++) {
        System.out.print(" " + iter.next());
        if (count > 20)
          break;
      }
      System.out.println();
    }
        
    boolean checkIterator () {
      int count = 0;
      for (Iterator iter = iterator(); iter.hasNext(); iter.next()) {
        count++;
        if (count > 20)
          break;
      }
      if (count != size)
        return false;

      int j = first;
      Iterator it = iterator();
      for (int i = 0; i < size; i++) {
        if (!it.next().equals(theElements[j]))
          return false;
        j = (j + 1) % theElements.length;
      }

      return true;
    }

    void print () {
      System.out.print("theElements:");
      int n = theElements.length;
      for (int i = 0; i < theElements.length; i++)
        System.out.print(" " + theElements[i]);
      System.out.println();
      System.out.println("first=" + first + " size=" + size);
      printIterator();
    }

    boolean test () {
      boolean ok = true;
      before.set();
      print();
      boolean iteratorWorks = checkIterator();
      // if (!checkIterator())
      // return false;
      System.out.println(call + "(" + arg + ")");
      Object r = null;
      try {
        if (call.equals("offer"))
          r = offer(arg);
        else if (call.equals("peek"))
          r = peek();
        else
          r = poll();
      } catch (Exception e) {
        System.out.println("ERROR: " + e);
        return false;
      }
      System.out.println("returns " + r);
      if (ret == null && r != null || ret != null && !ret.equals(r))
        ok = false;
      print();
      iteratorWorks = iteratorWorks && checkIterator();
      // if (!checkIterator())
      // return false;
      if (!(after.same() || (after2 != null && after2.same())))
        ok = false;

      if (ok && iteratorWorks)
        System.out.println("CORRECT");
      else
        System.out.println("INCORRECT");

      System.out.println();
      return ok && iteratorWorks;
    }
  }

  boolean comment (boolean a, boolean b) {
    return a && b;
  }

  boolean test () {
    Test test;
    boolean ok = true;

    test = new Test(new State(new String[]{null, null, null}, 0, 2, 0),
                    "poll", "", null,
                    new State(new String[]{null, null, null}, 0, 2, 0));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", null, null}, 0, 2, 0),
                    "poll", "", null,
                    new State(new String[]{"bob", null, null}, 0, 2, 0));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", null, null}, 0, 0, 1),
                    "poll", "", "bob",
                    new State(new String[]{"bob", null, null}, 1, 0, 0),
                    new State(new String[]{null, null, null}, 1, 0, 0));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 1, 3),
                    "poll", "", "eve",
                    new State(new String[]{"bob", "ann", "eve"}, 0, 1, 2),
                    new State(new String[]{"bob", "ann", null}, 0, 1, 2));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 0, 1, 2),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "ann", "vic"}, 0, 2, 3));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 0, 2),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "vic", "eve"}, 2, 1, 3));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 0, 2, 3),
                    "offer", "vic", true,
                    new State(new String[]{"bob", "ann", "eve", "vic", null, null}, 0, 3, 4));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 1, 0, 3),
                    "offer", "vic", true,
                    new State(new String[]{"ann", "eve", "bob", "vic", null, null}, 0, 3, 4));
    ok = comment(test.test(), ok);

    test = new Test(new State(new String[]{"bob", "ann", "eve"}, 2, 1, 3),
                    "offer", "vic", true,
                    new State(new String[]{"eve", "bob", "ann", "vic", null, null}, 0, 3, 4));
    ok = comment(test.test(), ok);

    return ok;
  }

  public static void main (String[] args) {
    TestArrayQueue test = new TestArrayQueue();

    if (test.test())
      System.out.println("PASS");
    else
      System.out.println("FAIL");
  }
}
    
