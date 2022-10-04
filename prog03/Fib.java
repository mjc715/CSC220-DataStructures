package prog03;

/**
 *
 * @author vjm
 */
public abstract class Fib {
  /** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
      @param n index
      @return nth Fibonacci number
  */
  public abstract double fib (int n);

  /** The order O() of the implementation.
      @param n index
      @return the function of n inside the O()
  */
  public abstract double O (int n);

  private double c;

  public void recordConstant (int n, double t) {
      this.c = t / O(n);
  }

  public double estimateTime (int n) {
    return (c * O(n));

  }
}
