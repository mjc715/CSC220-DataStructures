package prog09;
import java.util.Map;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Array2Map <K extends Comparable<K>> extends ArrayMap<K> {
  ArrayMap<K> getMap (int i) {
    return (ArrayMap<K>) entries[i].value;
  }

  Array2Map<K> split () {
    if (size != 4)
      System.err.println("splitting with size < 4: " + size);
    Array2Map<K> right = new Array2Map<K>();
    right.entries[0] = entries[2];
    right.entries[1] = entries[3];
    size = 2;
    right.size = 2;
    return right;
  }

  public int size () {
    int n = 0;
    for (int i = 0; i < size; ++i)
      n += getMap(i).size();
    return n;
  }

  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    int index = find(key);
    if (index >= 0)
      return true;
    index = -index - 2;
    if (index < 0)
      return false;
    return getMap(index).containsKey(key);
  }
  
  public Object get (Object keyAsObject) {
    K key = (K) keyAsObject;
    int index = find(key);
    if (index < 0) {
      index = -index - 2;
      if (index < 0)
        return null;
    }
      return getMap(index).get(keyAsObject);


    // EXERCISE 4:
    // Look at containsKey.
    // Implement get.
  }

  public Object put (K key, Object value) {
    int index = find(key);

    // EXERCISE 5:
    if (index < 0) {
      index = -index - 2; // wrong
      if (index < 0)
        index = 0;
      // Adjust index to where key should go.
    }

    ArrayMap<K> map = getMap(index);
    Object oldValue = map.put(key, value);

    // Update the key of entries[index];
    entries[index].key = map.entries[0].key;
    if (entries[index].key == null || entries[index].key.compareTo(key) > 0)
      entries[index].key = key;


    if (map.size == 4) {
      ArrayMap<K> right = map.split();
      add(index + 1, new Entry(right.entries[0].key, right));


      // add new entry for right.  What index?  What key?
    }
    return oldValue;
  }

  public Object remove (Object keyAsObject) {
    K key = (K) keyAsObject;
    int index = find(key);
    if (index < 0) {
      index = -index-2;

      if (index < 0)
        return null;
      // Adjust value of index to where key might be found.


        //return null; // sometimes, not always
    }
    ArrayMap<K> map = getMap(index);
    Object oldValue = map.remove(key);

    // Update value of entries[index].key
    //entries[index].key = entries[index+1].key;

    if (map.size == 1) {
      if (index - 1 < 0 && getMap(index+1).size == 3) {
        ArrayMap<K> neighbor = getMap(index+1);
        map.add(map.size, neighbor.entries[0]);
        neighbor.remove(0);
        entries[0].key = map.entries[0].key;
        entries[1].key = neighbor.entries[0].key;

      } else if (index - 1 < 0 && getMap(index+1).size == 2) {

        ArrayMap<K> neighbor = getMap(index+1);
        neighbor.add(0, map.entries[0]);
        entries[1].key = map.entries[0].key;
        remove(0);

      } else if (index + 1 >= size && getMap(index-1).size == 2) {

        ArrayMap<K> neighbor = getMap(index-1);
        neighbor.add(neighbor.size, map.remove(0));
        remove(index);

      } else if (index + 1 >= size && getMap(index-1).size == 3) {

        ArrayMap<K> neighbor = getMap(index-1);
        map.add(0, neighbor.remove(neighbor.size-1));
        entries[index].key = map.entries[0].key;
      } else if (getMap(index+1).size == 2 && getMap(index-1).size == 2){

        ArrayMap<K> neighbor = getMap(index-1);
        neighbor.add(neighbor.size, map.entries[0]);
        remove(index);

      } else if (getMap(index+1).size == 3) {

        ArrayMap<K> neighbor = getMap(index+1);
        map.add(map.size, neighbor.remove(0));
        entries[index+1].key = neighbor.entries[0].key;
      }

      // FOUR cases

    }
    return oldValue;
  }

  protected class Iter implements Iterator<Map.Entry<K, Object>> {
    int index = 0;
    Iterator<Map.Entry<K, Object>> iter;
    
    Iter () {
      if (index < size)
        iter = getMap(index).entrySet().iterator();
    }

    public boolean hasNext () { 
      return index < size && iter.hasNext();
    }
    
    public Map.Entry<K, Object> next () {
      if (!hasNext())
        throw new NoSuchElementException();
      Map.Entry<K, Object> entry = iter.next();
      if (!iter.hasNext()) {
        index++;
        if (index < size)
          iter = getMap(index).entrySet().iterator();
      }
      return entry;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, Object>> {
    public Iterator<Map.Entry<K, Object>> iterator () {
      return new Iter();
    }
    
    public int size () { return Array2Map.this.size(); }
  }
  
  // public Set<Map.Entry<K, Object>> entrySet () { return new Setter(); }

  void putTest (K key, int value) {
    System.out.println("put(" + key + ", " + value + ") = " + put(key, value));
    System.out.println(this);
    if (!get(key).equals(value))
      System.out.println("ERROR: get(" + key + ") = " + get(key));
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
  }

  public static void main (String[] args) {
    ArrayMap<String> map = new ArrayMap<String>();
    map.put("b", 1);
    Array2Map<String> map2 = new Array2Map<String>();
    map2.add(0, map2.new Entry("b", map));
    map2.size = 1;
    System.out.println(map2);

    map2.putTest("c", 2);
    map2.putTest("c", 7);
    map2.putTest("a", 0);
    map2.putTest("d", 3);
    map2.putTest("d", 7);
    map2.removeTest("a");
    map2.putTest("e", 9);
    map2.putTest("f", 8);
    map2.removeTest("b");
    map2.removeTest("e");
    map2.putTest("e", 9);
    map2.putTest("b", 1);
    map2.removeTest("f");
    map2.putTest("f", 3);
    map2.putTest("g", 4);
    map2.removeTest("d");
    map2.putTest("d", 6);
    map2.putTest("h", 8);
    map2.removeTest("e");
  }
}
