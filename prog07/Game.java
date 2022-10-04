package prog07;

import prog02.GUI;
import prog02.UserInterface;
import prog05.ArrayQueue;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Game {
    static UserInterface ui;
    private static List<Node> wordEntries = new ArrayList<Node>();

    public Game(UserInterface ui) {
        this.ui = ui;
    }
    public void play(String sWord, String fWord) {
        String newWord = "";
        while (true) {
            ui.sendMessage("Current word: "+ sWord + "\nTarget word: " + fWord);
            newWord = ui.getInfo("Enter next word");
            if (newWord == (null) || newWord.equals("")) {
                return;
            }
            while (find(newWord) == null) {
                ui.sendMessage(newWord + " is not a word.");
                ui.sendMessage("Current word: "+ sWord + "\nTarget word: " + fWord);
                newWord = ui.getInfo("Try again:");
                if (newWord == (null) || newWord.equals("")) {
                    return;
                }
            }
            if (!oneLetterDifferent(sWord,newWord)) {
                ui.sendMessage("Words do not differ by one letter.");
                continue;
            }
            sWord = newWord;
            if (sWord.equals(fWord)) {
                ui.sendMessage("You win!");
                return;
            }

        }
    }
    public boolean oneLetterDifferent(String string1, String string2) {
        int different = 0;
        char [] word1 = string1.toCharArray();
        char [] word2 = string2.toCharArray();
        int minLength = Math.min(word1.length,word2.length);
        for (int i = 0; i < minLength; ++i ) {
            if (word1[i] != word2[i]) {
                ++different;
            }
        }
        if (different == 1 && word1.length == word2.length) {
            return true;
        }
        return false;
    }
    public static int lettersDifferent(String word1, String word2) {
        int different = 0;
        char [] string1 = word1.toCharArray();
        char [] string2 = word2.toCharArray();
        int minLength = Math.min(string1.length, string2.length);
        for (int i = 0; i < minLength; ++i) {
            if (string1[i] != string2[i]) {
                ++different;
            }
        }
        different += Math.abs(string1.length - string2.length);
        return different;
    }
    public int distanceToStart(Node node) {
        int distance = 0;
        while (node != null) {
            ++distance;
            node = node.next;
        }
        return distance;
    }
    protected class NodeComparator implements Comparator<Node> {
        private String targetWord;

        NodeComparator(String word) {
            targetWord = word;
        }

        private int priority(Node node) {
            int priority = distanceToStart(node) + lettersDifferent(node.word, targetWord);
            return priority;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return priority(o1) - priority(o2);
        }
    }
    public static void loadWords(String file) {
        BufferedReader br = null;
        String word;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((word = br.readLine()) != null) {
                wordEntries.add(new Node(word));
            }
        } catch (FileNotFoundException e) {
            ui.sendMessage("File does not exist");
        } catch (IOException e) {
            ui.sendMessage("Error reading file");
        }
    }
    public void solve(String sWord, String fWord) {
        //Queue<Node> solveQueue = new LinkedQueue<>();
         Queue <Node> solveQueue = new ArrayQueue<>();
         solveQueue.offer(find(sWord));
         Node startNode = find(sWord);
         Node endNode = null;
        ArrayList<String> solution = new ArrayList<>();
        String s = "";
        int polls = 0;

         while (!solveQueue.isEmpty()) {
             Node theNode = solveQueue.poll();
             ++polls;
             for (int i = 0; i < wordEntries.size(); i++) {

                 if ((oneLetterDifferent(theNode.word, wordEntries.get(i).word)) && (wordEntries.get(i).next == null)
                         && (!wordEntries.get(i).word.equals(startNode.word))) {
                     Node nextNode = wordEntries.get(i);
                     nextNode.next = theNode;
                     solveQueue.offer(nextNode);

                     if (nextNode.word.equals(fWord)) {
                         endNode = nextNode;
                         ui.sendMessage("Got to " + fWord + " from " + sWord);
                         while (!solveQueue.isEmpty()) {
                             solveQueue.poll();
                         }
                         s = startNode.word + "\n";
                         for (Node n = endNode; n.next != null; n = n.next) {
                             solution.add(n.word);
                         }
                         for (int k = solution.size()-1; k > 0; --k) {
                             s += solution.get(k) + "\n";
                         }
                         s += endNode.word;
                         ui.sendMessage(s);
                         break;
                     }

                 }
             }

         }
         ui.sendMessage("Polled queue " + polls + " times.");
         if (solution.size() == 0) {
             ui.sendMessage("Could not find path to " + fWord + " from "+ sWord);
         }
         }

    public void solve2(String sWord, String fWord) {
        //Queue<Node> solveQueue = new LinkedQueue<>();
//        Queue <Node> solveQueue = new ArrayQueue<>();
        Queue <Node> solveQueue = new PriorityQueue<>(new NodeComparator(fWord));
        solveQueue.offer(find(sWord));
        Node startNode = find(sWord);
        Node endNode = null;
        ArrayList<String> solution = new ArrayList<>();
        String s = "";
        int polls = 0;

        while (!solveQueue.isEmpty()) {
            Node theNode = solveQueue.poll();
            ++polls;
            for (int i = 0; i < wordEntries.size(); i++) {

                if ((oneLetterDifferent(theNode.word, wordEntries.get(i).word)) && (wordEntries.get(i).next == null)
                        && (!wordEntries.get(i).word.equals(startNode.word))) {
                    Node nextNode = wordEntries.get(i);
                    nextNode.next = theNode;
                    solveQueue.offer(nextNode);

                    if (nextNode.word.equals(fWord)) {
                        endNode = nextNode;
                        ui.sendMessage("Got to " + fWord + " from " + sWord);
                        while (!solveQueue.isEmpty()) {
                            solveQueue.poll();
                        }
                        s = startNode.word + "\n";
                        for (Node n = endNode; n.next != null; n = n.next) {
                            solution.add(n.word);
                        }
                        for (int k = solution.size()-1; k > 0; --k) {
                            s += solution.get(k) + "\n";
                        }
                        s += endNode.word;
                        ui.sendMessage(s);
                        break;
                    }

                }
            }

        }
        ui.sendMessage("Polled queue " + polls + " times.");
        if (solution.size() == 0) {
            ui.sendMessage("Could not find path to " + fWord + " from "+ sWord);
        }
    }

    public void solve3(String sWord, String fWord) {
        //Queue<Node> solveQueue = new LinkedQueue<>();
        //Queue <Node> solveQueue = new ArrayQueue<>();
        Queue <Node> solveQueue = new PriorityQueue<>(new NodeComparator(fWord));
        solveQueue.offer(find(sWord));
        Node startNode = find(sWord);
        Node endNode = null;
        ArrayList<String> solution = new ArrayList<>();
        String s = "";
        int polls = 0;

        while (!solveQueue.isEmpty()) {
            Node theNode = solveQueue.poll();
            ++polls;
            for (int i = 0; i < wordEntries.size(); i++) {
                Node nextNode = wordEntries.get(i);
//                nextNode.next = theNode;
//                solveQueue.offer(nextNode);

                if ((oneLetterDifferent(theNode.word, nextNode.word)) && (nextNode.next == null)
                        && (!nextNode.word.equals(startNode.word))) {
                    nextNode.next = theNode;
                    solveQueue.offer(nextNode);

                    if (nextNode.word.equals(fWord)) {
                        endNode = nextNode;
                        ui.sendMessage("Got to " + fWord + " from " + sWord);
                        while (!solveQueue.isEmpty()) {
                            solveQueue.poll();
                        }
                        s = startNode.word + "\n";
                        for (Node n = endNode; n.next != null; n = n.next) {
                            solution.add(n.word);
                        }
                        for (int k = solution.size()-1; k > 0; --k) {
                            s += solution.get(k) + "\n";
                        }
                        s += endNode.word;
                        ui.sendMessage(s);
                        break;
                    }

                } else if((nextNode != startNode) && (oneLetterDifferent(theNode.word, nextNode.word)) && (distanceToStart(nextNode) > distanceToStart(theNode)+1)) {

                    nextNode.next = theNode;
                    solveQueue.remove(nextNode);
                    solveQueue.offer(nextNode);


                }
            }

        }
        ui.sendMessage("Polled queue " + polls + " times.");
        if (solution.size() == 0) {
            ui.sendMessage("Could not find path to " + fWord + " from "+ sWord);
        }
    }


    protected static Node find(String find) {
        for (int i = 0; i < wordEntries.size(); i++) {
            if (wordEntries.get(i).word.equals(find)) {
                return wordEntries.get(i);
            }
        }
        return null;
    }

    protected static class Node<E> {
        // Data Fields

        /** The reference to the element. */
        protected String word;
        /** The reference to the next node. */
        protected Node next;


        // Constructors
        /**
         * Creates a new node with a null next field.
         * @param word The word stored
         */
        protected Node (String word) {
            this.word = word;
            next = null;
        }
    }


    public static void main(String[] args) {
        String [] commands = {"Human plays","Computer plays", "Computer solve2", "Computer solve3"};
        Game game = new Game(new GUI("Word Game"));
        String file = ui.getInfo("Enter word file: ");
        if (file == null || file.equals("")) {
            return;
        }
        loadWords(file);
        String sWord = ui.getInfo("Enter starting word");
        if (sWord == (null) || sWord.equals("")) {
            return;
        }
        while (find(sWord) == null) {
            ui.sendMessage(sWord + " is not a word");
            sWord = ui.getInfo("Try again:");
            if (sWord == (null) || sWord.equals("")) {
                return;
            }
        }
        String fWord = ui.getInfo("Enter target word");
        if (fWord == (null) || fWord.equals("")) {
            return;
        }
        while (find(fWord) == null) {
            ui.sendMessage(sWord + " is not a word");
            fWord = ui.getInfo("Try again:");
            if (fWord == (null) || fWord.equals("")) {
                return;
            }
        }
        switch (ui.getCommand(commands)) {
            case 0:
                game.play(sWord, fWord);
                break;
            case 1:
                game.solve(sWord, fWord);
                break;
            case 2:
                game.solve2(sWord, fWord);
                break;
            case 3:
                game.solve3(sWord,fWord);
                break;
            case -1:
                return;
        }

    }
}
