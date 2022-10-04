package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /**
   * Use this variable to store the result of each call to fib.
   */
  public static double fibn;

  /**
   * Determine the time in microseconds it takes to calculate the
   * n'th Fibonacci number.
   *
   * @param fib an object that implements the Fib interface
   * @param n   the index of the Fibonacci number to calculate
   * @return the time for the call to fib(n)
   */
  public static double time(Fib fib, int n) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Calculate the n'th Fibonacci number.  Store the
    // result in fibn.
    fibn = fib.fib(n);

    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return (end - start) / 1000.0;
  }

  /**
   * Determine the average time in microseconds it takes to calculate
   * the n'th Fibonacci number.
   *
   * @param fib    an object that implements the Fib interface
   * @param n      the index of the Fibonacci number to calculate
   * @param ncalls the number of calls to average over
   * @return the average time per call
   */
  public static double averageTime(Fib fib, int n, int ncalls) {
    double totalTime = 0;

    for (int i = 0; i < ncalls; i++) {
      totalTime += time(fib, n);
    }

    // Add up the total call time for ncalls calls to time (above).


    // Return the average time.
    return (totalTime / ncalls);
  }

  /**
   * Determine the time in microseconds it takes to to calculate the
   * n'th Fibonacci number.  Average over enough calls for a total
   * time of at least one second.
   *
   * @param fib an object that implements the Fib interface
   * @param n   the index of the Fibonacci number to calculate
   * @return the time it it takes to compute the n'th Fibonacci number
   */
  public static double accurateTime(Fib fib, int n) {
    // Get the time in microseconds for one call.
    double t = averageTime(fib, n, 1);

    if (t >= 1000000) {
      return t;
    }


    // If the time is (equivalent to) more than a second, return it.


    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int) (1000000 / t); // fix this


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  private static UserInterface ui = new TestUI("Fibonacci experiments");
  //private static UserInterface ui = new GUI("Fibonacci experiments");

  /**
   * Get a non-negative integer from the using using ui.
   * If the user enters a negative integer, like -2, say
   * "-2 is negative...invalid"
   * If the user enters a non-integer, like abc, say
   * "abc is not an integer"
   * If the user clicks cancel, return -1.
   *
   * @return the non-negative integer entered by the user or -1 for cancel.
   */
  static int getInteger() {
    try {

      String s = ui.getInfo("Enter n");

      if (s == null) {

        return -1;
      }
      if (Integer.parseInt(s) < 0) {
        ui.sendMessage("Number must be positive. Try again.");
        return -2;
      }
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      ui.sendMessage("Must be a number. Try again.");
      return -2;
    }
  }

  public static void doExperiments (Fib fib) {

    String[] commands = {"Yes","No"};

    System.out.println("doExperiments " + fib);
    int i = 0;
    while (true) {
      int integer = getInteger();
      if (integer == -1) {
        return;
      } else if (integer == -2) {
        continue;
      } else {
        if (i != 0) {
          double estTime = fib.estimateTime(integer);
          if ((estTime / 1000000 / 60 / 60) >= 1.0) {
            ui.sendMessage("Estimated time is over an hour, you will be asked if you are sure you want to run");
            if (ui.getCommand(commands) == 1) {
              continue;
            }
          }
          ui.sendMessage("Estimated time is " + estTime + " microseconds");
          double time = accurateTime(fib, integer);
          fib.recordConstant(integer,time);
          double error = ((time - estTime) / estTime) * 100.0;
          ui.sendMessage("Fibn for n = " + integer + " is " + fibn + ", done in " + time +
                  " microseconds. Percent error is " + error + "%");
        } else {
          double time = accurateTime(fib, integer);
          fib.recordConstant(integer,time);
          ui.sendMessage("Fibn for n = " + integer + " is " + fibn + ", done in " + time + "microseconds");
        }
        }
      i = 1;
      }
    }

  public static void doExperiments () {
    // Give the user a choice instead, in a loop, with the option to exit.
    String[] fibs = {"ExponentialFib","LinearFib","LogFib","ConstantFib","MysteryFib","EXIT"};
    boolean quit = false;
    while (quit != true) {
      switch(ui.getCommand(fibs)) {
        case 0:
          doExperiments(new ExponentialFib());
          break;
        case 1:
          doExperiments(new LinearFib());
          break;
        case 2:
          doExperiments(new LogFib());
          break;
        case 3:
          doExperiments(new ConstantFib());
          break;
        case 4:
          doExperiments(new MysteryFib());
          break;
        case 5:
          quit = true;
          break;
      }
    }
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
//    double time1 = averageTime(efib, n1, 1000);
    double time1 = accurateTime(efib,n1);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    // Calculate actual running time for n2=30.
//    double time2 = averageTime(efib, n2, 100);
    double time2 = accurateTime(efib,n2);
    System.out.println("n2 " + n2 + " actual time " + time2);

    // Estimate how long ExponentialFib.fib(100) would take.
    double time2est2 = c * efib.O(100) / 1000000 / 60/ 60/ 24/ 365;
    System.out.println("n3 100 estimated time " + time2est2 + " years");

  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    //labExperiments();
    doExperiments();
  }
}
