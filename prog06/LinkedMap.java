package prog06;
import java.util.*;
import java.util.Map.Entry;

public class LinkedMap <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  protected class Entry
    implements Map.Entry<K, V> {

    K key;
    V value;
    Entry previous, next;
    
    Entry (K key, V value) {
      this.key = key;
      this.value = value;
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  protected Entry first, last;
  
  /**
   * Find the last Entry e with e.key "<=" key
   * or null if there isn't one.
   * @param key The Key to be found.
   * @return Last Entry e with e.key.compareTo(key) <= 0
   * or null if there isn't one.
   */
  protected Entry find (K key) {
    if (first == null || key.compareTo(first.key) < 0) {
      return null;
    }
    Entry e = first;

    while (e.next != null && key.compareTo(e.next.key) >= 0) {
      e = e.next;
    }
    // EXERCISE
    // Move e forward if that doesn't go past key.
    ///


    ///

    return e;
  }    
  
  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    Entry entry = find(key);
    return (entry != null && entry.key.equals(key));
  }
  
  public V get (Object keyAsObject) {
    // EXERCISE
    // Look at containsKey and fix this.
    ///
    if (containsKey(keyAsObject)) {
      K key = (K) keyAsObject;
      return find(key).getValue();
    }

    ///
    return null;
  }
  
  /**
   * Add newEntry after previous in list or as first entry if previous==null.
   * @param previous The Entry to insert after at the beginning if null.
   * @param newEntry The new Entry to be inserted.
   * @return newEntry
   */
  protected Entry add (Entry previous, Entry newEntry) {
    if (previous == null) {
      if (first == null) {
        first = newEntry;
        last = newEntry;
        last.next = null;
        first.previous = null;
        return newEntry;

      }
      newEntry.next = first;
      first.previous = newEntry;
      first = newEntry;
      first.previous = null;

    } else if (previous == last) {
      previous.next = newEntry;
      newEntry.previous = previous;
      last = newEntry;
      last.next = null;

    } else {
      Entry nextEntry = previous.next;
      newEntry.next = nextEntry;
      newEntry.previous = previous;
      nextEntry.previous = newEntry;
      previous.next = newEntry;

    }
    return newEntry;
  }

  public V put (K key, V value) {
    Entry entry = find(key);
    if (containsKey((Object) key)) {
      entry.setValue(value);
      return value;
    }
    // EXERCISE
    // Look at containsKey and then fix this.
    ///


    ///
    Entry newEntry = new Entry(key, value);
    add(entry, newEntry);
    return value;
  }      
  
  /**
   * Remove Entry entry from list.
   * @param entry The entry to remove.
   */
  protected void remove (Entry entry) {
    // EXERCISE
    ///
    if (first == entry && last == entry) {
      first = null;
      last = null;
      return;
    }
    if (entry == last) {
      Entry prevEntry = entry.previous;
      prevEntry.next = null;
      last = prevEntry;
      return;
    }
    if (entry == first) {
      Entry nextEntry = first.next;
      first = nextEntry;
      nextEntry.previous = null;
      return;
    }
      Entry nextEntry = entry.next;
      Entry prevEntry = entry.previous;
      prevEntry.next = nextEntry;
      nextEntry.previous = prevEntry;

    ///
  }

  public V remove (Object keyAsObject) {
    // EXERCISE
    // Use find, but make sure you got the right one!
    // If you do, then remove it.
    ///
   Entry entry = find((K) keyAsObject);
 if (entry != null || entry.getKey() != (K)keyAsObject) {
     return null;
   } else {
     V value = entry.getValue();
     remove(entry);
     return value;
   }
      ///

  }      

  protected class Iter implements Iterator<Map.Entry<K, V>> {
    // EXERCISE
    ///
    Entry newFirst = first;
    ///
    
    public boolean hasNext () { 
      // EXERCISE
      ///
      if (newFirst != null) {
        return true;
      }
      return false; // wrong
      ///
    }
    
    public Map.Entry<K, V> next () {
      // EXERCISE
      // Entry implements Map.Entry<K, V> so you return the next
      // Entry, not its data.
      ///
      // Map.Entry<K, V> ret = next;
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Entry returnEntry = newFirst;
      newFirst = newFirst.next;

      return returnEntry; // wrong
      ///
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return LinkedMap.this.size(); }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public static void main (String[] args) {
    Map<String, Integer> map = new LinkedMap<String, Integer>();
    
    if (false) {
      map.put("Victor", 50);
      map.put("Irina", 45);
      map.put("Lisa", 47);
    
      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());
    
      System.out.println(map.put("Irina", 55));

      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());

      System.out.println(map.remove("Irina"));
      System.out.println(map.remove("Irina"));
      System.out.println(map.get("Irina"));
    
      for (Map.Entry<String, Integer> pair : map.entrySet())
        System.out.println(pair.getKey() + " " + pair.getValue());
    }
    else {
      String[] keys = { "Vic", "Ira", "Sue", "Zoe", "Bob", "Ann", "Moe" };
      for (int i = 0; i < keys.length; i++) {
        System.out.print("put(" + keys[i] + ", " + i + ") = ");
        System.out.println(map.put(keys[i], i));
        System.out.println("remove " + keys[i]);
        map.remove(keys[i]);
        System.out.println(map);
        System.out.print("put(" + keys[i] + ", " + -i + ") = ");
        System.out.println(map.put(keys[i], -i));
        System.out.println(map);
        System.out.print("get(" + keys[i] + ") = ");
        System.out.println(map.get(keys[i]));
        System.out.print("remove(" + keys[i] + ") = ");
        System.out.println(map.remove(keys[i]));
        System.out.println(map);
        System.out.print("get(" + keys[i] + ") = ");
        System.out.println(map.get(keys[i]));
        System.out.print("remove(" + keys[i] + ") = ");
        System.out.println(map.remove(keys[i]));
        System.out.println(map);
        System.out.print("put(" + keys[i] + ", " + i + ") = ");
        System.out.println(map.put(keys[i], i));
        System.out.println(map);
      }
    }
  }
}
