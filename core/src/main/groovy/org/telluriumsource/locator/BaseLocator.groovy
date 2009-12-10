package org.telluriumsource.locator

import org.json.simple.JSONObject

class BaseLocator{
    public static final String LOC = "loc"
    String loc

    public String getTag(){
        //TODO: parse the tag from the base locator if it is XPATH, how about Selector?
        return ""
    }

    public generateHtml(boolean closeTag){
      //Do not support generate Html for base locator
      return ""
    }

    JSONObject toJSON(){
      JSONObject jso = new JSONObject()
      jso.put(LOC, loc)
      
      return jso
    }

    public String generateCloseTag(){
      return ""
    }
}