package prog08;

public interface Sorter<E extends Comparable<E>> {
  double O (int n);

  void sort (E[] array);
}
