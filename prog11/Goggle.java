package prog11;

import prog05.ArrayQueue;

import java.util.*;

public class Goggle implements SearchEngine {

    Map<String,Long> urlToIndex = new TreeMap<>();
    Disk<PageFile> pageDisk = new Disk<>();
    Disk<List<Long>> wordDisk = new Disk<>();
    Map<String,Long> wordToIndex = new HashMap<>();

    public void collect(Browser browser, List<String> startingURLs) {
        Queue<Long> indexQueue = new ArrayDeque<Long>();
        for (String s: startingURLs) {
             if (!urlToIndex.containsKey(s)) {
                 Long newIndex = indexPage(s);
                 indexQueue.offer(newIndex);
             }
        }
        while (!indexQueue.isEmpty()) {
            Long index = indexQueue.poll();
            Long newPageIndex;
            if (browser.loadPage(pageDisk.get(index).url)) {

                List<String> newURLs = browser.getURLs();
                List<String> newWords = browser.getWords();

                for (String s: newURLs) {
                    if (!urlToIndex.containsKey(s)) {
                        newPageIndex = indexPage(s);
                        indexQueue.offer(newPageIndex);
                        pageDisk.get(index).indices.add(newPageIndex);
                    } else {
                        pageDisk.get(index).indices.add(urlToIndex.get(s));
                    }

                }
                Long wordIndex;
                for (String w : newWords) {
                    if (!wordToIndex.containsKey(w)) {
                        wordIndex = indexWord(w);
                    } else {
                        wordIndex = wordToIndex.get(w);
                    }
                    if(wordDisk.get(wordIndex).isEmpty() || !Objects.equals
                            (wordDisk.get(wordIndex).get(wordDisk.get(wordIndex).size() - 1), index))
                    wordDisk.get(wordIndex).add(index);
                }

            }
        }
    }

    public String[] search(List<String> searchWords, int numResults) {
        long [] currentPageIndexes = new long[searchWords.size()];
        PageComparator comparePages = new PageComparator();
        PriorityQueue<Long> bestPageIndexes = new PriorityQueue<Long>(new PageComparator());

        Iterator<Long> [] wordFileIterators = (Iterator<Long>[]) new Iterator[searchWords.size()];
        for (int i = 0; i < searchWords.size(); ++i) {
            Long index = wordToIndex.get(searchWords.get(i));
            wordFileIterators[i] = wordDisk.get(index).iterator();

        }
        while (getNextPageIndexes(currentPageIndexes, wordFileIterators)) {
            if (allEqual(currentPageIndexes)) {
                System.out.println(pageDisk.get(currentPageIndexes[0]).url);
                if (bestPageIndexes.size() < numResults) {
                    bestPageIndexes.offer(currentPageIndexes[0]);
                } else if (comparePages.compare(currentPageIndexes[0], bestPageIndexes.peek()) > 0){
                    bestPageIndexes.poll();
                    bestPageIndexes.offer(currentPageIndexes[0]);
                }
            }
        }
        Queue <String> results1 = new ArrayQueue<>();
        Stack<String> helper = new Stack<>();
        String [] results2 = new String [bestPageIndexes.size()];

        int i = 0;
        while (!bestPageIndexes.isEmpty()) {
            helper.push(pageDisk.get(bestPageIndexes.poll()).url);
        }
        while (!helper.isEmpty()) {
            results2[i] = helper.pop();
            ++i;
        }
//        while (!results1.isEmpty()) {
//            results2[i] = results1.poll();
//            i++;
//        }

        return results2;
    }
    private boolean allEqual(long[] array) {
        long firstElement = array[0];

        for (int i = 0; i < array.length; ++i) {
            if (!(firstElement == array[i])) {
                return false;
            }
        }
        return true;
    }

    private long getLargest(long[] array) {
        long largest = array[0];
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > largest) {
                largest = array[i];
            }
        }
        return largest;
    }

    private boolean getNextPageIndexes(long [] currentPageIndexes, Iterator<Long> [] wordFileIterators) {
        long largest = getLargest(currentPageIndexes);
        if (allEqual(currentPageIndexes)) {
            ++largest;
        }
        for (int i = 0; i < currentPageIndexes.length; ++i) {
            if (currentPageIndexes[i] != largest) {
                if (wordFileIterators[i].hasNext()) {
                    currentPageIndexes[i] = wordFileIterators[i].next();
                } else {
                    return false;
                }
            }

        }
        return true;
    }

    public long indexPage (String url) {
        long index = pageDisk.newFile();
        PageFile newPage = new PageFile(index, url);
        pageDisk.put(index, newPage);
        urlToIndex.put(url, index);
        System.out.println("indexing page " + index + "(" + url + ")");
        return index;
    }
    public long indexWord (String word) {
        long index = wordDisk.newFile();
        wordToIndex.put(word, index);
        List <Long> indicies = new ArrayList<>();
        wordDisk.put(index, indicies);
        System.out.println("indexing word " + index + "(" + word + ")");
        return index;

    }
    public void rankSlow() {
        for (PageFile file : pageDisk.values()) {
            file.priority.add(1.0);
        }

            for (int i = 1; i < 20; ++i) {
                for (PageFile file : pageDisk.values()) {
                    System.out.println(file);
                    file.priority.add(0.0);
                }

                for (PageFile file : pageDisk.values()) {
                    double vote = file.priority.get(i - 1) / (file.indices.size());

                    for (Long l : file.indices) {
                        PageFile file2 = pageDisk.get(l);
                        double newPrio = file2.priority.get(i) + vote;
                        file2.priority.set(i, newPrio);
                    }
                }
            }
        }


    public void rankFast() {
        for (PageFile file : pageDisk.values()) {
            file.priority.add(1.0);
        }

        for (int i = 1; i < 20; ++i) {
            for (PageFile file : pageDisk.values()) {
                System.out.println(file);
            }
            List<Vote> votes = new ArrayList<>();

            for (PageFile file : pageDisk.values()) {
                double fracVote = file.priority.get(i - 1) / (file.indices.size());

                for (Long l : file.indices) {
                    Vote v = new Vote(l, fracVote);
                    votes.add(v);
                }
            }

                    Collections.sort(votes);
                    Iterator<Vote> voteIterator = votes.iterator();
                    Vote currentVote = voteIterator.next();
                    int j = 0;
                    for (PageFile file2 : pageDisk.values()) {
                        Double totalVote = 0.0;

                        while (votes.size() - 1 > j && Objects.equals(currentVote.index, file2.index)) {
                            totalVote += currentVote.vote;
                            ++j;
                            currentVote = voteIterator.next();
                        }
                        file2.priority.add(totalVote);
                    }


        }
    }
    class PageComparator implements Comparator<Long> {
        public int compare(Long i1, Long i2) {
            double p1 = pageDisk.get(i1).priority.get(19);
            double p2 = pageDisk.get(i2).priority.get(19);
            if (p1 < p2)
                return -1;
            else if (p1 > p2)
                return 1;
            else
                return 0;
        }
    }


    public class Vote implements Comparable<Vote> {
        private Long index;
        private double vote;

        public Vote(Long index, double vote) {
            this.index = index;
            this.vote = vote;
        }

        public int compareTo(Vote o) {
            return index.compareTo(o.index);
        }
    }
}
