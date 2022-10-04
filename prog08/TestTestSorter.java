package prog08;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class TestTestSorter<E extends Comparable<E>> {
  public static void main (String[] args) {
    TestSorter<Integer> tester = new TestSorter<Integer>();
    Integer[] array1 = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Integer[] array2 = { 3, 1, 4, 2, 5, 9, 3, 6 };
    Integer[] array3 = { 1, 1, 2, 3, 4, 5, 6, 9 };

    int score = 30;
    boolean nstest = true;
    if (tester.inOrder(array1)) {
      System.out.println("inOrder({ 3, 1, 4, 1, 5, 9, 3, 6 }) = true");
      nstest = false;
    }
    if (!tester.inOrder(array3)) {
      System.out.println("inOrder({ 1, 1, 2, 3, 4, 5, 6, 9 }) = false");
      nstest = false;
    }
    if (!nstest) {
      System.out.println(-10);
      score -= 10;
    }

    boolean detest = true;
    if (!tester.sameElements(array1, array3)) {
      System.out.println("sameElements({ 3, 1, 4, 1, 5, 9, 2, 6 }, { 1, 1, 2, 3, 4, 5, 6, 9 }) = false");
      detest = false;
    }
    if (tester.sameElements(array1, array2)) {
      System.out.println("sameElements({ 3, 1, 4, 1, 5, 9, 2, 6 }, { 3, 1, 4, 2, 5, 9, 3, 6 }) = true");
      detest = false;
    }
    if (!detest) {
      System.out.println(-15);
      score -= 15;
    }

    Integer[] input = randomArray(1000);
    Integer[] output = input.clone();
    Sorter<Integer> insertionSort = new InsertionSort<Integer>();

    insertionSort.sort(output);
    
    if (!tester.inOrder(output) || !tester.sameElements(input, output) || output[0] > output[1] || output[1] > output[2]) {
      System.out.println("insertionSort failed \n-5");
      score -= 5;
    }
    
    System.out.println("SCORE: " + score + "/30");
  }

  public static Integer[] randomArray (int n) {
    Integer[] array = new Integer[n];
    Random random = new Random(0);
    for (int i = 0; i < n; i++)
      array[i] = random.nextInt(n);
    return array;
  }
}
