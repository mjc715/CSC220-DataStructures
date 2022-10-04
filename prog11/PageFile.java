package prog11;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

/** This class represents the information stored in a file to record a
  * web page. */
public class PageFile {
  public final Long index;
  public final String url;
  Set<Long> indices = new TreeSet<Long>();
  List<Double> priority = new ArrayList<Double>();

  public PageFile (Long index, String url) {
    this.index = index;
    this.url = url;
  }

  public String toString () {
    return index + "(" + url + ")" + indices + priority;
  }
}


    
