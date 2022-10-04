package prog04;

import java.util.Stack;
import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;

public class Game {
  //static UserInterface ui = new ConsoleUI();
  static UserInterface ui = new GUI("Tower of Hanoi");

  private class Move {

    protected int toPeg;
    protected int fromPeg;

    private Move() {
      toPeg = -1;
      fromPeg = -1;
    }


    private Move (int fromPeg, int toPeg) {
      this.toPeg = toPeg;
      this.fromPeg = fromPeg;
    }


    public String toString() {
//      System.out.print("Move one disk from peg " + (char)fromPeg+97 + " to peg " +
//              (char)toPeg+97);
      return ("Move one disk from peg " + (char)fromPeg+97 + " to peg " +
                (char)toPeg+97);
    }
  }
  private class MoveN extends Move {
    protected int numberToMove;

    private MoveN() {
      this.numberToMove = 1;
    }

    private MoveN(int fromPeg, int toPeg, int numberToMove) {
      this.fromPeg = fromPeg;
      this.toPeg = toPeg;
      this.numberToMove = numberToMove;
    }

    public String toString() {
      String s = "";
      s += "Move " + numberToMove + " disks from peg " + (char)('a' + fromPeg) + " to peg " +
              (char)('a' + toPeg) + "\n";
      return s;
    }
  }
  StackInterface<MoveN> theMoves = new ArrayStack<MoveN>();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Game tower = new Game(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInterface<Integer>[] pegs = (StackInterface<Integer>[]) new ArrayStack[3];

  Game (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++) {
      pegs[i] = new ArrayStack<Integer>();
    }
    for (int i = nDisks; i > 0; i--) {
      pegs[0].push(i);
    }

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).


  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
    int[] froms = { 0, 0, 1, 1, 2, 2 };
    int[] tos = { 1, 2, 0, 2, 0, 1 };

    boolean fixthis = true;

    while ((!pegs[0].empty() || !pegs[1].empty())) {
      displayPegs();
      int imove = ui.getCommand(moves);
      if (imove == -1)
        return;
      String move = moves[imove];
      int from = froms[imove];
      int to = tos[imove];
      move(from, to);
    }

    displayPegs();
    ui.sendMessage("You win!");
  }

  String stackToString (StackInterface<Integer> peg) {
    StackInterface<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
    while (!peg.empty()) {
      helper.push(peg.pop());
    }
    while (!helper.empty()) {
      s += peg.push(helper.pop());
    }

    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    if (pegs[from].empty()) {
      ui.sendMessage("No disk to move in stack.");
      return;
    }
    if (!pegs[to].empty() && (pegs[to].peek() < pegs[from].peek())) {
      ui.sendMessage("Cannot place larger disk on smaller disk.");
      return;
    }
    pegs[to].push(pegs[from].pop());
    // EXERCISE:  move one disk form pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages.

  }

  void displayMoves () {
    StackInterface<MoveN> helper = new ArrayStack<MoveN>();
    String s = "";
    while (!theMoves.empty()) {
      helper.push(theMoves.pop());
    }
    while (!helper.empty()) {
      MoveN middleMan = new MoveN();
      s += (theMoves.push(helper.pop())).toString();



    }
    if (!s.equals("")) {
      ui.sendMessage(s);
    }
  }

  // EXERCISE

  // EXERCISE

  void solve () {
    theMoves.push(new MoveN (0,2,nDisks));
    int emptyPeg,from,to,numMoves;
    displayPegs();

    while (!theMoves.empty()) {
      displayMoves();
      MoveN move1 = theMoves.pop();
      if (move1.numberToMove == 1) {
        move(move1.fromPeg, move1.toPeg);
        displayPegs();
      } else {
        switch (move1.fromPeg + move1.toPeg) {
          case 2:
            emptyPeg = 1;
            from = move1.fromPeg;
            to = move1.toPeg;
            numMoves = move1.numberToMove;
            theMoves.push(new MoveN(emptyPeg, to, numMoves - 1));
            theMoves.push(new MoveN(from, to, 1));
            theMoves.push(new MoveN(from, emptyPeg, numMoves - 1));
            break;
          case 1:
            emptyPeg = 2;
            from = move1.fromPeg;
            to = move1.toPeg;
            numMoves = move1.numberToMove;
            theMoves.push(new MoveN(emptyPeg, to, numMoves - 1));
            theMoves.push(new MoveN(from, to, 1));
            theMoves.push(new MoveN(from, emptyPeg, numMoves - 1));
            break;
          case 3:
            emptyPeg = 0;
            from = move1.fromPeg;
            to = move1.toPeg;
            numMoves = move1.numberToMove;
            theMoves.push(new MoveN(emptyPeg, to, numMoves - 1));
            theMoves.push(new MoveN(from, to, 1));
            theMoves.push(new MoveN(from, emptyPeg, numMoves - 1));
            break;
        }
      }
    }

    // EXERCISE 13
  }        
}
