package prog07;
import java.util.*;

public class BST <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Entry implements Map.Entry<K, V> {
    K key;
    V value;
    Entry left, right;
    
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
  
  private Entry root;
  private int size;

  public int size () { return size; }

  /**
   * Find the entry with the given key.
   * @param key The key to be found.
   * @return The entry with that key.
   */
  private Entry find (K key, Entry root) {
    // EXERCISE:
    ///
    if (root == null) {
      return null;
    }
    if (key.compareTo(root.key) == 0) {
      return root;
    }
    if (key.compareTo(root.key) < 0) {
      return find(key, root.left);
    } else {
      return find(key, root.right);
    }
  }    

  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    // EXERCISE:
    ///
  Entry output = find((K) key, root);
  if (output == null)
    return null;
  return output.value;


    ///
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Entry add (K key, V value, Entry root) {
    // EXERCISE:
    ///
    if (root == null) {

      return new Entry(key,value);
    }
    if (key.compareTo(root.key) < 0) {
      root.left = add(key, value, root.left);
      return root;
    }else {

      root.right = add(key, value, root.right);
      return root;
    }






    ///

  }
  
  int depth (Entry root) {
    if (root == null)
      return -1;
    return 1 + Math.max(depth(root.left), depth(root.right));
  }

  public V put (K key, V value) {
    // EXERCISE:
    ///
    Entry entry = find(key, root);
    if (entry == null) {
      root = add(key, value, root);
      return null;
    } else {
      V value2 = entry.getValue();
      entry.setValue(value);
      return value2;
    }
    ///
  }      
  
  public V remove (Object keyAsObject) {
    K key = (K) keyAsObject;
    Entry removed = find(key, root);
    // EXERCISE:  Delete these lines and implement remove.
    ///
    if (removed != null) {
      V value = removed.value;
      root = remove(key, root);
      return value;
    }

    ///
    return null;
  }

  private Entry remove (K key, Entry root) {
    // EXERCISE:
    ///
    if (key.compareTo(root.key) == 0) {
      return removeRoot(root);
    }
    if (key.compareTo(root.key) < 0) {
      root.left = remove(key, root.left);
    }
    if (key.compareTo(root.key) > 0) {
      root.right = remove(key, root.right);
    }
    ///

    return root;
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining entrys.
   */
  private Entry removeRoot (Entry root) {
    // IMPLEMENT using getMinimum and removeMinimum
    // Entry returned by getMinimum becomes the new root.
    ///
    if (root.left == null) {
      return root.right;
    }
    if (root.right == null) {
      return root.left;
    }
    Entry root2 = getMinimum(root.right);
    root.right = removeMinimum(root.right);
    root2.left = root.left;
    root2.right = root.right;
    root = root2;


    ///
    return root;
  }

  // EXERCISE: implement getMinimum and removeMinimum
  ///
  private Entry getMinimum(Entry root) {
    if (root.left == null) {
      return root;
    } else {
      return getMinimum(root.left);
    }
  }

  private Entry removeMinimum(Entry root) {
    if (root.left == null) {
      return root.right;
    }
    root.left = removeMinimum(root.left);
    return root;
  }

  ///

  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString1 () {
    return toString(root, 0);
  }
  
  private String toString (Entry root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public String toString () {
    if (root == null)
      return "\n";
    int w = width(root);
    List<String> list = toList(root, w);
    String s = "";
    for (String line : list)
      s += line + "\n";
    return s;
  }

  private int width (Entry root) {
    if (root == null)
      return 0;
    String kv = "" + root.key /*+ root.value*/;
    String s = spaces(kv.length());
    int wl = width(root.left);
    int wr = width(root.right);
    int wmax = wl > wr ? wl : wr;
    return kv.length() + 2 * wmax;
  }

  private List<String> toList (Entry root, int width) {
    String kv = "" + root.key /*+ root.value*/;
    String skv = spaces(kv.length());
    int width2 = (width - kv.length()) / 2;
    String sw = spaces(width2);
    List<String> out = new ArrayList<String>();
    out.add(sw + kv + sw);
    out.add(sw + root.value + sw);
    if (root.left == null && root.right == null) {
      return out;
    }
    if (root.left == null) {
      List<String> right = toList(root.right, width2);
      for (String r : right)
        out.add(sw + skv + r);
      return out;
    }
    if (root.right == null) {
      List<String> left = toList(root.left, width2);
      for (String l : left)
        out.add(l + skv + sw);
      return out;
    }
    List<String> left = toList(root.left, width2);
    List<String> right = toList(root.right, width2);
    for (int i = 0; i < left.size() && i < right.size(); i++)
      out.add(left.get(i) + skv + right.get(i));
    if (left.size() > right.size()) {
      for (int i = right.size(); i < left.size(); i++)
        out.add(left.get(i) + skv + sw);
    }
    if (left.size() < right.size()) {
      for (int i = left.size(); i < right.size(); i++)
        out.add(sw + skv + right.get(i));
    }
    return out;
  }    

  String spaces (int n) {
    String s = "";
    for (int i = 0; i < n; i++)
      s += " ";
    return s;
  }

  public static void main (String[] args) {
    BST<Character, Integer> tree = new BST<Character, Integer>();
    String s = "notbalanced";
    
    for (int i = 0; i < s.length(); i++) {
      System.out.println("put(" + s.charAt(i) + ", " + i + ")");
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println();
      System.out.println("get(" + s.charAt(i) + ") = " + tree.get(s.charAt(i)));
    }

    for (int i = 0; i < s.length(); i++) {
      System.out.print("remove(" + s.charAt(i) + ") returns ");
      System.out.println(tree.remove(s.charAt(i)));
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println();
      System.out.println("get(" + s.charAt(i) + ") = " + tree.get(s.charAt(i)));
    }
  }
}
