package org.telluriumsource.entity

import org.telluriumsource.ui.locator.CompositeLocator

/**
 * Relax detail for closest matching in Engine when do UI module locating and caching
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 31, 2009
 * 
 */

public class RelaxDetail {
    //which UID got relaxed, i.e., closest Match
    public static String UID = "uid";
    private String uid = null;

    //the clocator defintion for the UI object corresponding to the UID
    public static String LOCATOR = "locator";
    private CompositeLocator locator = null;

    //The actual html source of the closest match element
    public static String HTML = "html";
    private String html = null;

    def RelaxDetail(){

    }

    def RelaxDetail(Map map){
      this.uid = map.get(UID);
      this.html = map.get(HTML);
      this.locator = new CompositeLocator();
      this.locator.build(map.get(LOCATOR));
    }
}