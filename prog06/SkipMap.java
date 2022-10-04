package prog06;
import java.util.Random;

public class SkipMap<K extends Comparable<K>, V> extends LinkedMap<K, V> {
  // SkipMap containing half the elements chosen at random.
  SkipMap<K, Entry> skip;

  // Coin flipping code.
  Random random = new Random(1);
  /** Flip a coin.
   * @return true if you flip heads.
   */
  boolean heads () {
    return random.nextInt() % 2 == 0;
  }

  public V put (K key, V value) {
    Entry entry = find(key);
    // EXERCISE
    // Put the code you write for LinkedMap.put here.
    ///
    if (containsKey(key)) {
      entry.setValue(value);
      return value;
    }

    ///

    Entry newEntry = new Entry(key, value);
    add(entry, newEntry);

    // EXERCISE (SkipMap)
    // If you flip heads, store a pointer to the new entry in skip.
    // You will have to initialize skip if it is still null.
    ///
    if (heads()) {
      if (skip == null) {
        skip = new SkipMap<>();
        skip.put(key, newEntry);
        return value;
      }
      skip.put(key,newEntry);
      return value;
    }




    ///

    return null;
  }      

  protected Entry find (K key) {
    if (first == null || key.compareTo(first.key) < 0)
      return null;

    // Starting entry for the search.
    Entry e = first;

    // EXERCISE (SkipMap)
    // If skip is not null, ask skip.find for where to start the search.
    // The return type is SkipMap<K, Entry>.Entry
    // If it isn't null, use its value as the starting entry instead of first.
    ///
      if (skip != null) {
        LinkedMap<K, LinkedMap<K, V>.Entry>.Entry f = skip.find(key);
        if (f != null) {
          e = f.getValue();
        }
      }




    ///

    // EXERCISE
    // Put the code you wrote for LinkedMap.find here.
    ///

    while (e.next != null && key.compareTo(e.next.key) >= 0) {
      e = e.next;
    }

    ///

    return e;
  }

  public V remove (Object keyAsObject) {
    K key = (K) keyAsObject;
    Entry entry = find(key);
    if (entry == null || !entry.key.equals(key))
      return null;
    remove(entry);
    
    // EXERCISE (SkipMap)
    // Remove from skip (if skip is not null).
    // Set skip to null if it is empty.
    ///
    if (skip != null) {
      skip.remove(key);
      if (skip.first == null) {
        skip = null;
      }
    }




    ///
    
    return entry.getValue();
  }      
}
