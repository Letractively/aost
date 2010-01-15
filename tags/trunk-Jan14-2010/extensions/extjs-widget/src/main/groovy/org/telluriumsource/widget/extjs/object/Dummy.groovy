package org.telluriumsource.widget.extjs.object

import org.telluriumsource.widget.extjs.ExtJSWidget
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * A dummy widget
 *
 * Date: Jan 21, 2009
 * 
 */
class Dummy extends ExtJSWidget {

    public void defineWidget() {

    }

    public String helloExtJSWidget(){
        return "Hello extJS Widget!"
    }

  public JSONObject toJSON() {
    return buildJSON() {jso ->
      jso.put(UI_TYPE, NAMESPACE + "_" + "Dummy")
    }
  }

}