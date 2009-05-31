package org.tellurium.locator

import org.json.simple.JSONObject

class BaseLocator{
    public static final String LOC = "loc"
    String loc

    public String getTag(){
        //TODO: parse the tag from the base locator if it is XPATH, how about Selector?
        return ""
    }

  JSONObject toJSON(){
    JSONObject jso = new JSONObject()
    jso.put(LOC, loc)
    
    return jso
  }
}