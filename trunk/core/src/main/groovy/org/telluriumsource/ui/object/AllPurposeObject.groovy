package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class AllPurposeObject extends UiObject{

  JSONObject toJSON() {

  return buildJSON() {jso ->
      jso.put(UI_TYPE, "AllPurposeObject");
    }
  }

  def methodMissing(String name, args) {
    return this.invokeMethod("innerCall", args);
  }
}
