package prog11;

import java.util.*;

/** This class simulates a hard disk.  Requesting a new file returns
 * its location on the disk, which is the (long integer) block number
 * of its first block.  Since files can be different sizes, each file
 * starts at a block number between 1 and 4 blocks later than the
 * previous file. T is the type of information stored in the file.
 * Disk maps a block number to the information by extending
 * TreeMap, which implements the Map interface. */
public class Disk<T> extends TreeMap<Long, T> {
  public Long newFile () {
    nextIndex += 1 + random.nextInt(4);
    return nextIndex;
  }

  private Long nextIndex = 0L;
  private Random random = new Random(0);
}
