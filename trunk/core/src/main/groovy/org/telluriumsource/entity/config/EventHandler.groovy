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
class EventHandler {
  //whether we should check if the UI element is presented
  public static String CHECK_ELEMENT = "checkElement";
  boolean checkElement = false
  
  //wether we add additional events like "mouse over"
  public static String EXTRA_EVENT = "xtraEvent";
  boolean extraEvent = false

  def EventHandler() {
  }

  def EventHandler(Map map) {
    this.checkElement = map.get(CHECK_ELEMENT);
    this.extraEvent = map.get(EXTRA_EVENT);
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(CHECK_ELEMENT, this.checkElement);
    obj.put(EXTRA_EVENT, this.extraEvent);

    return obj;
  }
}
