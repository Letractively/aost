package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Accessor {
  //whether we should check if the UI element is presented
  public static String CHECK_ELEMENT = "checkElement";
  boolean checkElement = false;

  def Accessor() {
  }

  def Accessor(Map map) {
    this.checkElement = map.get(CHECK_ELEMENT);
  }
  
  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(CHECK_ELEMENT, this.checkElement);

    return obj;
  }
}
