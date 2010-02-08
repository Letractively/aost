package org.telluriumsource.ui.object

import org.json.simple.JSONObject

 /**
 *  Check Box
 *
 * you can select one or more options from a set of alternatives.
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class CheckBox extends UiObject{

    public static final String TAG = "input"
    public static final String TYPE = "checkbox"
    
  def click(Closure c) {

      c(locator, respondToEvents)
  }

  def doubleClick(Closure c) {

      c(locator, respondToEvents)
  }

  def clickAt(String coordination, Closure c) {

      c(locator, respondToEvents)
  }


    public JSONObject toJSON() {

      return buildJSON(){jso ->
        jso.put(UI_TYPE, "CheckBox")
      }
    }
}