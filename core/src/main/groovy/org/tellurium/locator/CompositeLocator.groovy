package org.tellurium.locator

import static org.tellurium.Const.*
import org.json.simple.JSONObject

/**
 *  The composite locator which is used to automatically generate the relative
 *  xpath by given attributes and parameters.
 *
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class CompositeLocator {
    //obtained from UI object and should not be specified in clocator
    String namespace

    public static final String HEADER = "header"
    String header

    public static final String TAG = "tag"
    String tag

    public static final String TEXT = "text"
    String text

    public static final String TRAILER = "trailer"
    String trailer

    public static final String POSITION = "position"
    def position

    public static final String DIRECT = "direct"
    boolean direct = false

    Map<String, String> attributes = [:]

    JSONObject toJSON(){
      JSONObject jso = new JSONObject()
      if(header != null && header.trim().length() > 0)
        jso.put(HEADER, header)
      if(tag != null && tag.trim().length() > 0)
        jso.put(TAG, tag)
      if(trailer != null && trailer.trim().length() > 0)
        jso.put(TRAILER, trailer)
      if(position != null)
        jso.put(POSITION, position)
      if(direct)
        jso.put(DIRECT, direct)

      attributes.each {key, val->
        jso.put(key, val)
      }
      
      return jso
    }

    public String getTag(){
        return tag
    }

    public boolean isIdIncluded(){
      boolean result = false
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        //only consider ID included if there are really id provided
        if(!(id.startsWith(NOT_PREFIX) || id.startsWith(START_PREFIX) || id.startsWith(END_PREFIX) ||  id.startsWith(ANY_PREFIX))){
          result = true
        }
      }

      return result
    }

   //handy method
    public boolean noIdIncluded(){
      return !isIdIncluded()
    }
}