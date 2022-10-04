package prog02;

import java.io.*;

/**
 * This is an implementation of PhoneDirectory that uses a sorted
 * array to store the entries.
 *
 * @author Michael Castellucci
 */
public class SortedPD extends ArrayBasedPD {

    /**
     * Add an entry or change an existing entry.
     *
     * @param name   The name of the person being added or changed
     * @param number The new number to be assigned
     * @return The old number or, if a new entry, null
     */
    public String addOrChangeEntry(String name, String number) {
        String returnString = null;
        int index = find(name);
        if (index >= 0 && theDirectory[index]!=null) {
            String oldNumber = theDirectory[index].getNumber();
            theDirectory[index].setNumber(number);
            return oldNumber;
        } else {
            add(-index - 1, new DirectoryEntry(name, number));
            return null;
        }

    }

    /**
     * Find an entry in the directory.
     *
     * @param name The name to be found
     * @return The index of the entry with that name or, if it is not
     * there, (-insert_index - 1), where insert_index is the index
     * where should be added.
     */
      protected int find(String name) {

          int maybe = 0;
          int not = size;
          int middle = 0;
          if (name == null) {
              return -1;
          }
          while (maybe < not) {
              middle = (maybe + not) / 2;
              if (theDirectory[size - 1] != null) {
                  if (theDirectory[middle].getName().compareTo(name) > 0) {
                      not = middle;
                  } else if (theDirectory[middle].getName().compareTo(name) < 0) {
                      maybe = middle + 1;
                  } else if (theDirectory[middle].getName().compareTo(name) == 0) {
                      return middle;
                  }
              }
          }
//          return(maybe);
          return(-maybe - 1);
      }



    /**
     * Add an entry to the directory.
     *
     * @param index    The index at which to add the entry to theDirectory.
     * @param newEntry The new entry to add.
     * @return The DirectoryEntry that was just added.
     */
    protected DirectoryEntry add(int index, DirectoryEntry newEntry) {
        if (size == theDirectory.length) {
            reallocate();
        }
        theDirectory[size] = null;
        for (int i = 0; i <= size - 1 - index; i++) {
            theDirectory[size-i] = theDirectory[size-1-i];
        }
        ++size;
        theDirectory[index] = newEntry;

        return newEntry;
    }

    /**
     * Remove an entry from the directory.
     *
     * @param index The index in theDirectory of the entry to remove.
     * @return The DirectoryEntry that was just removed.
     */
    protected DirectoryEntry remove(int index) {
        DirectoryEntry entry = theDirectory[index];
        if (theDirectory[index] == null) {
            return null;
        }

        for (int i = index; i < size - 1; i++) {
            theDirectory[i] = theDirectory[i+1];
        }

        size--;
        return entry;

    }

}