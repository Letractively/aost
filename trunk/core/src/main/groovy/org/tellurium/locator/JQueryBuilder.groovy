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
  protected static final String SPACE = " "
  
  //represent it is a partial match i.e., contains
  private static final String CONTAIN_PREFIX = "%%"

  protected static final String ID = "id"
  protected static final String CLASS = "class"

  protected static final int LENGTH = 64

  protected static def ATTR_BLACK_LIST = ['action']

  protected static boolean inBlackList(String attr){
    return ATTR_BLACK_LIST.contains(attr)
  }

  protected static String checkTag(String tag){
    if(tag != null && tag.trim().length() > 0){
      return tag
    }

    return MATCH_ALL
  }

  protected static String containText(String text){
    String val = text
    if(includeSingleQuote(text)){
      SplitInfo info = getLargestPortion(text)
      val = info.val
    }
    return "${CONTAINS_FILTER}(${val})"
  }

  protected static String attrText(String text){
    String val = text
    if(includeSingleQuote(text)){
      SplitInfo info = getLargestPortion(text)
      val = info.val
      return "${CONTAINS_FILTER}(${val})"
    }

    //need the following custom selector ":text()" support
    /*
      $.extend($.expr[':'],{
        text: function(a,i,m) {
           return $(a).text() === m[3];
        }
      });
     */
    return ":text(${val})"
  }

  //starts from zero
  protected static String attrPosition(int index){
    return ":eq(${index})"
  }

  protected static String attrId(String id){
    if(id != null && id.trim().length() > 0){
      return "${ID_SELECTOR_PREFIX}${id}"
    }

    return "[id]"
  }

  protected static String attrClass(String clazz){
    //need to consider the multiple-class syntax, for example, $('.myclass.otherclass')
    //As a CSS selector, the multiple-class syntax of example 3 is supported by all modern
    //web browsers, but not by Internet Explorer versions 6 and below
    if(clazz != null && clazz.trim().length() > 0){
      String[] parts = clazz.split(SPACE)
      if(parts.length == 1){
        //only only 1 class
         return "${CLASS_SELECTOR_PREFIX}${clazz}"
      }else{
        StringBuffer sb = new StringBuffer()
        for(String part: parts){
          sb.append("${CLASS_SELECTOR_PREFIX}${part}")
        }
        return sb.toString()
      }
    }

//    return "${CLASS_SELECTOR_PREFIX}${clazz}"
    return "[class]"
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
    sb.append(checkTag(tag))
    if(attributes != null && attributes.size() > 0){
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        //should not add other attributes if the ID is presented since jQuery will only select the first element for
        // the ID and additional attributes will not help at all
        sb.append(attrId(id))

        return sb.toString()
      }

      String clazz = attributes.get(CLASS)
      if(clazz != null)
        sb.append(attrClass(clazz))

      Set keys = attributes.keySet()
      for (String key: keys) {
        String val = attributes.get(key)
        if ((!key.equals(ID)) && (!key.equals(CLASS)) && (!inBlackList(key))) {
          sb.append(attrPairs(key, val))
        }
      }
    }

    if(text != null && text.trim().length() > 0){
      if(text.startsWith(CONTAIN_PREFIX))
        sb.append(containText(text.substring(2)))
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