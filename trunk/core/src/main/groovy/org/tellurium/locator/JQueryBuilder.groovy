package org.tellurium.locator
/**
 *
 *
 *  A utility class to build jQuery from a given parameter set
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *  Date: Apr 9, 2009
 *
 */

public class JQueryBuilder {

  protected static final String CHILD_SEPARATOR = " > "
  protected static final String DESCENDANT_SEPARATOR = " "
  protected static final String NEXT_SEPARATOR = " + "
  protected static final String SIBLING_SEPARATOR = " ~ "
  protected static final String ID_SELECTOR_PREFIX = "#"
  protected static final String CLASS_SELECTOR_PREFIX = "."
  protected static final String MATCH_ALL = "*"
  protected static final String CONTAINS_FILTER = ":contains"
  protected static final String SINGLE_QUOTE = "'"

  //represent it is a partial match i.e., contains
  private static final String CONTAIN_PREFIX = "%%"

  protected static final String ID = "id"
  protected static final String CLASS = "class"

  protected static final int LENGTH = 64

  protected static String containText(String text){
    String val = text
    if(includeSingleQuote(text)){
      SplitInfo info = getLargestPortion(text)
      val = info.val
    }
    return "${CONTAINS_FILTER}(${val})"
  }

  protected static String attrText(String text){
    return "[text()=${guardQuote(text)}]"
  }

  //starts from zero
  protected static String attrPosition(int index){
    return ":eq(${index})"
  }

  protected static String attrId(String id){
    return "${ID_SELECTOR_PREFIX}${id}"
  }

  protected static String attrClass(String clazz){
    return "${CLASS_SELECTOR_PREFIX}${clazz}"
  }

  protected static String attrPairs(String attr, String val){
    if(includeSingleQuote(val)){
      SplitInfo info = getLargestPortion(val)
      String result = null
      switch(info.pos){
        case Pos.FIRST:
          result = "[${attr}^=${info.val}]"
          break
        case Pos.MIDDLE:
          result = "[${attr}*=${info.val}]"
          break
        case Pos.LAST:
          result = "[${attr}\$=${info.val}]"
      }

      return result
    }

    return "[${attr}=${val}]"
  }

  //not really working if we convert String multiple times
  protected static String guardQuote(String val){
    if(val!= null && val.indexOf(SINGLE_QUOTE) > 0){
      return val.replaceAll("'", "\\\'")
//      return "\"${val}\""
    }

    return val
  }

  protected static SplitInfo getLargestPortion(String val){
    String[] parts = val.split(SINGLE_QUOTE)
    int max = 0
    String mval = parts[0].trim()
    for(int i=1; i<parts.length; i++){
      String current = parts[i].trim()
      if(current.length() > mval.length()){
        max = i
        mval = current
      }
    }
    SplitInfo result = new SplitInfo()
    if(max==0){
      result.pos = Pos.FIRST
    }else if(max == parts.length-1){
      result.pos = Pos.LAST
    }else{
      result.pos = Pos.MIDDLE
    }
    result.val = mval

    return result
  }

  protected static boolean includeSingleQuote(String val){
    if(val != null && val.indexOf(SINGLE_QUOTE) > 0)
      return true

    return false
  }

  
  public static String buildJQuerySelector(String tag, String text, String position, boolean direct, Map<String, String> attributes) {
    StringBuffer sb = new StringBuffer(LENGTH)
    if(direct){
      sb.append(CHILD_SEPARATOR)
    }else{
      sb.append(DESCENDANT_SEPARATOR)
    }

    //put the tag name first
    sb.append(tag)
    if(attributes != null && attributes.size() > 0){
      String id = attributes.get(ID)
      if(id != null)
        sb.append(attrId(id))
      String clazz = attributes.get(CLASS)
      if(clazz != null)
        sb.append(attrClass(clazz))

      Set keys = attributes.keySet()
      for (String key: keys) {
        String val = attributes.get(key)
        if (!(key.equals(ID) || key.equals(CLASS))) {
          sb.append(attrPairs(key, val))
        }
      }
    }

    if(text != null && text.trim().length() > 0){
      if(text.startsWith(CONTAIN_PREFIX))
        sb.append(containText(text))
      else
        sb.append(attrText(text))
    }

    if(position != null && position.trim().length() > 0){
      int index = Integer.parseInt(position)
      if(index >= 0){
        sb.append(attrPosition(index))
      }
    }

    return sb.toString()
  }
}