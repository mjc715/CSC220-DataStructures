package prog09;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractSet;
import java.util.Set;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BTree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {
  
  ArrayMap<K> map = new ArrayMap<K>();

  public int size () { return map.size(); }

  public boolean containsKey (Object keyAsObject) {
    return map.containsKey(keyAsObject);
  }
  
  public V get (Object keyAsObject) {
    return (V) map.get(keyAsObject);
  }

  public V put (K key, V value) {
    V oldValue = (V) map.put(key, value);
    if (map.size == 4) {
      // EXERCISE 6:
      // split map into two maps
      // Create new Array2Map with these maps as values.
      // Set map to the Array2Map.
      Array2Map <K> right = new Array2Map<>();
      ArrayMap splitted = map.split();
      right.add(0, right.new Entry(map.entries[0].key, map));
      right.add(1, right.new Entry(right.entries[0].key, splitted));
      map = right;

    }
    return oldValue;
  }

  public V remove (Object keyAsObject) {
    V value = (V) map.remove(keyAsObject);
    if (map.size == 1 && map instanceof Array2Map) {
      // EXERCISE 8
      // Replace map with value of the only entry.
      map = (ArrayMap<K>) map.entries[0].value;
    }
    return value;
  }

  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Iterator<Map.Entry<K, Object>> iter = map.entrySet().iterator();
    
    public boolean hasNext () { 
      return iter.hasNext();
    }
    
    public Map.Entry<K, V> next () {
      if (!hasNext())
        throw new NoSuchElementException();
      return (Map.Entry<K, V>) iter.next();
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return map.size(); }
  }

  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }

  void print (ArrayMap map, String indent) {
    Set<Map.Entry<K, Object>> set = map.entrySet();
    for (Map.Entry<K, Object> entry : set) {
      System.out.print(indent + entry.getKey());
      if (entry.getValue() instanceof ArrayMap) {
        System.out.println();
        print((ArrayMap) entry.getValue(), indent + "  ");
      }
      else
        System.out.println(" " + entry.getValue());
    }
  }

  void putTest (K key, V value) {
    System.out.println("put(" + key + ", " + value + ") = " + put(key, value));
    if (!get(key).equals(value))
      System.out.println("ERROR: get(" + key + ") = " + get(key));
    System.out.println(this);
    print(map, "");
  }

  void removeTest (K key) {
    Object v = get(key);
    Object value = remove(key);
    if (!v.equals(value))
      System.out.print("ERROR: ");
    System.out.println("remove(" + key + ") = " + value);
    value = remove(key);
    if (value != null)
      System.out.println("ERROR: remove(" + key + ") = " + value);
    System.out.println(this);
    print(map, "");
  }

  public static void main (String[] args) {
    BTree<String, Integer> tree = new BTree<String, Integer>();
    System.out.println("tree = " + tree);

    tree.putTest("a", 0);
    tree.putTest("b", 1);
    tree.putTest("c", 2);
    tree.putTest("d", 3);
    tree.putTest("e", 4);
    tree.putTest("f", 5);
    tree.putTest("g", 6);
    tree.putTest("h", 7);
    tree.putTest("i", 8);
    tree.removeTest("a");
  }
}

