package prog03;

public class LinearFib extends Fib{

    public double fib(int n) {
        double a = 0;
        double b = 1;
        double c = 0;

        for (int i = 0; i < n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
    public double O(int n) {
        return n;
    }

}
