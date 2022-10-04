package prog11;

import prog02.GUI;
import java.util.*;

import javax.xml.stream.events.StartDocument;

public class Main {
  public static void main(String[] args) {
    Browser browser = new BetterBrowser();
    SearchEngine goggle = new Goggle();

    List<String> startingURLs = new ArrayList<String>();
    //startingURLs.add("http://www.cs.miami.edu");
    //startingURLs.add("http://www.cs.miami.edu/home/vjm/csc220/google/mary.html");
     startingURLs.add("http://www.cs.miami.edu/home/vjm/csc220/google2/1.html");

    List<String> temp = new ArrayList<String>();

    for (int i = 0; i < startingURLs.size(); i++) {
      temp.add(BetterBrowser.reversePathURL(startingURLs.get(i)));
    }

    startingURLs = temp;

    goggle.collect(browser, startingURLs);

    Goggle g = (Goggle) goggle;
    System.out.println("map from URL to page index");
    System.out.println(g.urlToIndex);
    System.out.println("map from page index to page disk");
    System.out.println(g.pageDisk);
    System.out.println("map from word to word index");
    System.out.println(g.wordToIndex);
    System.out.println("map from word index to word file");
    System.out.println(g.wordDisk);

    g.rankSlow();
    System.out.println("page disk");
    for (PageFile file : g.pageDisk.values()) {
      System.out.println(file);
      file.priority.clear();
    }

    g.rankFast();
    System.out.println("page disk");
    for (PageFile file : g.pageDisk.values())
      System.out.println(file);

    List<String> keyWords = new ArrayList<String>();
    if (false) {
      keyWords.add("mary");
      keyWords.add("jack");
      keyWords.add("jill");
    } else {
      GUI gui = new GUI("Goggle");
      while (true) {
        String input = gui.getInfo("Enter search words.");
        if (input == null)
          return;
        String[] words = input.split("\\s", 0);
        keyWords.clear();
        for (String word : words)
          keyWords.add(word);
        String[] urls = goggle.search(keyWords, 5);
        String res = "Found " + keyWords + " on";
        for (int i = 0; i < urls.length; i++)
          res = res + "\n" + BetterBrowser.inverseReversePathURL(urls[i]);
        gui.sendMessage(res);
      }
    }

    String[] urls = goggle.search(keyWords, 5);

    System.out.println("Found " + keyWords + " on");
    for (int i = 0; i < urls.length; i++)
      System.out.println(BetterBrowser.inverseReversePathURL(urls[i]));
  }
}
