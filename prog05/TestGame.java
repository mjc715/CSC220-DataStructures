package prog05;

import prog02.UserInterface;
import java.util.List;
import java.util.ArrayList;

public class TestGame implements UserInterface {
  List<String> messages = new ArrayList<String>();
  public void sendMessage (String message) {
    System.out.println("sendMessage(\"" + message + "\")");
  }

  int iTest = 0;
  int iInfo = 0;
  String[][] infos = { { "pass" },
                       { "fasl" },
                       { "fall", "pall", "pals", "pass" } };
  
  public String getInfo (String prompt) {
    if (iInfo < infos[iTest].length) {
      System.out.println("getInfo(\"" + prompt + "\" returns \"" + infos[iTest][iInfo] + "\".");
      return infos[iTest][iInfo++];
    }
    else {
      System.out.println("getInfo(\"" + prompt + "\" returns null.");
      return null;
    }
  }

  public int getCommand (String[] commands) {
    return 0;
  }

  public static void main (String[] args) {
    TestGame ui = new TestGame();
    Game game = new Game(ui);
    game.loadWords("words");

    ui.iTest = 0;
    ui.iInfo = 0;
    game.play("fail", "pass");
    System.out.println("Should have rejected pass because more than one letter changed.");
    System.out.println();

    ui.iTest = 1;
    ui.iInfo = 0;
    game.play("fail", "pass");
    System.out.println("Should have rejected fasl as not a real word.");
    System.out.println();

    ui.iTest = 2;
    ui.iInfo = 0;
    game.play("fail", "pass");
    System.out.println("Should have let me win.");
    System.out.println();

    //game.solve("fail", "pass");
    System.out.println("Should have gotten from fail to pass in 4 steps.");
  }
}

