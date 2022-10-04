package prog09;

import prog02.GUI;
import prog02.UserInterface;
import prog06.SkipMap;
import java.lang.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];
    for (int n = 0; n < word.length(); n++) {
      char c = word.charAt(n);
      int i = n;

      while (i > 0 && c < sorted[i-1]) {
        sorted[i] = sorted[i-1];
        i--;
      }

      sorted[i] = c;
    }

    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI("Jumble");
    // UserInterface ui = new ConsoleUI();

    //Map<String,String> map = new TreeMap<String,String>();
     //Map<String,String> map = new LinkedMap<String,String>();
     Map<String, List <String>> map = new BTree<>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));

      if (!map.containsKey(sort(word))) {
        List <String> wordList = new ArrayList<String>();
        wordList.add(word);
        map.put(sort(word), wordList);

      } else {
        List<String> wordList = map.get(sort(word));
        wordList.add(word);
        map.put(sort(word), wordList);

      }
      // EXERCISE: Insert an entry for word into map.
      // What is the key?  What is the value?


    }

    while (true) {
      String jumble = ui.getInfo("Enter jumble.");
      if (jumble == null) {
        String sortedLetters = (ui.getInfo("Enter letters from clues."));
        if (sortedLetters == null)
          return;

        sortedLetters = sort(sortedLetters);
        String asd = ui.getInfo("How many letters in the first word?");
        int wordLength = Integer.parseInt(asd);
        int key1index = 0;

        for (String key1 : map.keySet()) {
          String key2 = "";
          if (key1.length() == wordLength) {

            for (int i = 0; i < sortedLetters.length(); ++i) {

              if (key1index == key1.length()-1)
                key2 += sortedLetters.charAt(i);
              else if (sortedLetters.charAt(i) == key1.charAt(key1index))
                ++key1index;
              else if (sortedLetters.charAt(i) > key1.charAt(key1index))
                continue;
              else
                key2 = key2 + sortedLetters.charAt(i);
            }

//            if (key1index == key1.length()-1) {
//              if (map.containsKey(sort(key2)))
//                ui.sendMessage(map.get(sort(key1)) + " " + map.get(sort(key2)));

            }
          if (key1index == key1.length()-1) {
            if (map.containsKey(sort(key2)))
              ui.sendMessage(map.get(key1) + " " + map.get(key2));
          }
        }
        return;
      }

      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      List<String> words = map.get(sort(jumble)); // wrong
      String msg = jumble + " unjumbled is: \n";

      if (words == null)
        ui.sendMessage("No match for " + jumble);
      else
        for(String s: words)
          msg += s + "\n";
        ui.sendMessage(msg);
    }
  }
}

        
    

