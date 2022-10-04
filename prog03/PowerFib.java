package prog03;

/**
 *
 * @author vjm
 */
public class PowerFib extends ExponentialFib {
  public final double sqrt5 = Math.sqrt(5);

    /** The Fibonacci number generator 0, 1, 1, 2, 3, 5, ...
	@param n index
	@return nth Fibonacci number
    */
    public double fib (int n) {
      return (pow(phi, n) - pow((1 - phi), n)) / sqrt5;
    }

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double O (int n) {
	return n;
    }

    /** Raise x to the n'th power
	@param x x
	@param n n
	@return x to the n'th power
    */
    protected double pow (double x, int n) {
	double y = 1;
	for (int i = 0; i < n; i++)
	    y *= x;
	return y;
    }
}
