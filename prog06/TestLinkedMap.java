package prog06;

public class TestLinkedMap extends LinkedMap {
  Entry a1 = new Entry("a", "1");
  Entry b2 = new Entry("b", "2");
  Entry c3 = new Entry("c", "3");
  Entry d4 = new Entry("d", "4");
  Entry e5 = new Entry("e", "5");
  Entry f6 = new Entry("f", "6");
  Entry x0 = new Entry("x", "0");

  void set (Entry[] entries) {
    if (entries.length == 0) {
      first = last = null;
      return;
    }
    first = entries[0];
    entries[0].previous = null;
    for (int i = 1; i < entries.length; i++) {
      entries[i-1].next = entries[i];
      entries[i].previous = entries[i-1];
    }
    last = entries[entries.length-1];
    entries[entries.length-1].next = null;
  }

  boolean equals (Entry[] entries) {
    if (first != entries[0])
      return false;
    if (entries[0].previous != null)
      return false;
    for (int i = 1; i < entries.length; i++) {
      if (entries[i-1].next != entries[i])
        return false;
      if (entries[i].previous != entries[i-1])
        return false;
    }
    if (last != entries[entries.length-1])
      return false;
    if (entries[entries.length-1].next != null)
      return false;
    return true;
  }

  boolean testFind (Entry[] in, String s, Entry out) {
    set(in);
    try {
      Entry ret = find(s);
      if (ret != out)
        return false;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }

  boolean testAdd (Entry[] in, Entry previous, Entry newEntry, Entry[] out) {
    set(in);
    try {
      add(previous, newEntry);
      if (!equals(out)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
    return true;
  }
  
  void check (boolean b) {
    if (!b) {
      System.out.println("FAIL");
    } else {
      System.out.println("PASS");
    }
  }

  void testFind () {
    {
      Entry[] in = {};
      check(testFind(in, "a", null));
    }
    {
      Entry[] in = {b2};
      check(testFind(in, "a", null));
      check(testFind(in, "b", b2));
      check(testFind(in, "c", b2));
    }
    {
      Entry[] in = {b2, d4};
      check(testFind(in, "a", null));
      check(testFind(in, "b", b2));
      check(testFind(in, "c", b2));
      check(testFind(in, "d", d4));
      check(testFind(in, "e", d4));
    }
    {
      Entry[] in = {b2, d4, f6};
      check(testFind(in, "a", null));
      check(testFind(in, "b", b2));
      check(testFind(in, "c", b2));
      check(testFind(in, "d", d4));
      check(testFind(in, "e", d4));
      check(testFind(in, "f", f6));
      check(testFind(in, "g", f6));
    }
  }

  void testAdd () {
    {
      Entry[] in = {};
      Entry[] out = {x0};
      check(testAdd(in, null, x0, out));
    }
    {
      Entry[] in = {a1};
      Entry[] out = {x0, a1};
      check(testAdd(in, null, x0, out));
    }
    {
      Entry[] in = {a1};
      Entry[] out = {a1, x0};
      check(testAdd(in, a1, x0, out));
    }
    {
      Entry[] in = {a1, b2};
      Entry[] out = {x0, a1, b2};
      check(testAdd(in, null, x0, out));
    }
    {
      Entry[] in = {a1, b2};
      Entry[] out = {a1, x0, b2};
      check(testAdd(in, a1, x0, out));

    }
    {
      Entry[] in = {a1, b2};
      Entry[] out = {a1, b2, x0};
      check(testAdd(in, b2, x0, out));
    }
    {
      Entry[] in = {a1, b2, c3};
      Entry[] out = {x0, a1, b2, c3};
      check(testAdd(in, null, x0, out));
    }
    {
      Entry[] in = {a1, b2, c3};
      Entry[] out = {a1, x0, b2, c3};
      check(testAdd(in, a1, x0, out));
    }
    {
      Entry[] in = {a1, b2, c3};
      Entry[] out = {a1, b2, x0, c3};
      check(testAdd(in, b2, x0, out));
    }
    {
      Entry[] in = {a1, b2, c3};
      Entry[] out = {a1, b2, c3, x0};
      check(testAdd(in, c3, x0, out));
    }
  }

  public static void main (String[] args) {
    TestLinkedMap tlm = new TestLinkedMap();
    System.out.println("testing find");
    tlm.testFind();
    System.out.println("testing add");
    tlm.testAdd();
  }
}

    
