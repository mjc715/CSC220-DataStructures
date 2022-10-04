package prog07;

import prog02.ConsoleUI;

public class TestNodeComparator extends Game {
  TestNodeComparator () {
    super(new ConsoleUI());
  }

  void test () {
    NodeComparator comp = new NodeComparator("rain");
    Node said = new Node("said");
    Node rain = new Node("rain");
    int c = comp.compare(said, rain);
    System.out.println("compare(said, rain) = " + c);
    if (c != 2) {
      System.out.println("failed");
      return;
    }

    c = comp.compare(rain, said);
    System.out.println("compare(rain, said) = " + c);
    if (c != -2) {
      System.out.println("failed");
      return;
    }
    
    System.out.println("Setting distance from rain to start to 3.");
    rain.next = new Node("raid");
    rain.next.next= new Node("said");
    rain.next.next.next = new Node("slid");

    c = comp.compare(said, rain);
    System.out.println("compare(said, rain) = " + c);
    if (c != -1) {
      System.out.println("failed");
      return;
    }

    c = comp.compare(rain, said);
    System.out.println("compare(rain, said) = " + c);
    if (c != 1) {
      System.out.println("failed");
      return;
    }
    
    System.out.println("success");
  }

  public static void main (String[] args) {
    TestNodeComparator test = new TestNodeComparator();
    test.test();
  }
}
