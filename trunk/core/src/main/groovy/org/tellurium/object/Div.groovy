package org.tellurium.object

import org.json.simple.JSONObject

/**
 * Created by IntelliJ IDEA.
 * User: vivmon1
 * Date: Jul 25, 2008
 * Time: 5:16:01 PM
 * To change this template use File | Settings | File Templates.
 */
class Div extends UiObject{
    public static final String TAG = "div"

    def click(Closure c){
        c(locator, respondToEvents)
    }

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Div")
      }
    }
}