package org.tellurium.locator

/**
 *  The composite locator which is used to automatically generate the relative
 *  xpath by given attributes and parameters.
 *
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class CompositeLocator {
    public static final String ID = "id"

    String header
    String tag
    String text
    String trailer
    def position
    boolean direct = false
    Map<String, String> attributes = [:]

    public String getTag(){
        return tag
    }

    public boolean isIdIncluded(){
      boolean result = false
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        //only consider ID included if there are really id provided
        result = true
      }

      return result
    }

   //handy method
    public boolean noIdIncluded(){
      return !isIdIncluded()
    }
}