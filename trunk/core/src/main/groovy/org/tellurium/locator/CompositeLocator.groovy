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
  //TODO: need to move the constants to a central place 
    public static final String ID = "id"
    private static final String NOT_PREFIX = "!"
    private static final String START_PREFIX = "^"
    private static final String END_PREFIX = "\$"
    private static final String ANY_PREFIX = "*"

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