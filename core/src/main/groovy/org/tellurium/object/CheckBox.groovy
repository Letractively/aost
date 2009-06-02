package org.tellurium.object

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
    
    def check(Closure c){

        c(locator, respondToEvents)
    }

    def boolean isChecked(Closure c){

          c(locator)
    }

    def uncheck(Closure c){

        c(locator, respondToEvents)
    }

    public JSONObject toJSON() {

      return buildJSON(){jso ->
        jso.put(UI_TYPE, "CheckBox")
      }
    }
}