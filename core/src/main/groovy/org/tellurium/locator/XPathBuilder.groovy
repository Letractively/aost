package org.tellurium.locator
/**
 *   A utility class to build xpath from a given parameter set
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class XPathBuilder {
    // Selects all children of the current node
    private static final String CHILD = "child"

    // Selects all descendants (children, grandchildren, etc.) of the current node
    private static final String DESCENDANT = "descendant"

    //  Selects all descendants (children, grandchildren, etc.) of the current node
    // and the current node itself
    private static final String DESCENDANT_OR_SELF = "descendant-or-self"

    //  Selects the current node
    private static final String SELF = "self"

    //  Selects everything in the document after the closing tag of the current node
    private static final String FOLLOWING = "following"

    //  Selects all siblings after the current node
    private static final String FOLLOWING_SIBLING = "following-sibling"

    //  Selects the parent of the current node
    private static final String PARENT ="parent"

    //the prefix to start get a relative xpath, it is save to start with the following prefix
    private static final String DESCENDANT_OR_SELF_PATH ="/descendant-or-self::"

    private static final String CHILD_PATH ="/child::"

    private static final String DESCENDANT_PREFIX = "descendant::"

    private static final String CHILD_PREFIX = "child::"

    private static final String MATCH_ALL ="*"

    //represent it is a partial match i.e., contains
    private static final String CONTAIN_PREFIX = "%%"

    private static final int TYPICAL_LENGTH = 64

    // example xpaths
    //  //descendant-or-self::*[@uid='hp_table']/tbody/tr/td[1]/descendant-or-self::div/div[2]/p[1]/a
    //  /html/body/div[5]/center/table[@uid='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::table[@uid='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::*[@uid='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::*[@uid='hp_table']/tbody/tr/td[1]/descendant-or-self::div/div[2]/p[1]/a
    
    //  /thead/tr/th[@class]/descendant-or-self::*[normalize-space(text())='?']
    // /parent::th/following-sibling::th[@group=normalize-space(preceding-sibling::th[@class]
    // /descendant-or-self::*[normalize-space(text())='?']
    // /parent::th/following-sibling::th[@group][1]/attribute::group)][?]

    public static String buildDescendantXPath(String tag, String text, String position, Map<String, String> attributes){
        if(position != null && position.isInteger())
            return buildXPathWithPrefix(DESCENDANT_PREFIX, tag, text, Integer.parseInt(position), attributes, null)

        return  buildXPathWithPrefix(DESCENDANT_PREFIX, tag, text, -1 , attributes, null)
    }

    public static String buildChildXPath(String tag, String text, String position, Map<String, String> attributes){
        if(position != null && position.isInteger())
            return buildXPathWithPrefix(CHILD_PREFIX, tag, text, Integer.parseInt(position), attributes, null)

        return  buildXPathWithPrefix(CHILD_PREFIX, tag, text, -1 , attributes, null)
    }

    private static String buildXPathWithPrefix(String prefix, String tag, String text, int position, Map<String, String> attributes, Closure c)
    {
        StringBuffer sb = new StringBuffer(TYPICAL_LENGTH)
        sb.append(prefix)
        if( tag != null && (tag.length() > 0)){
          //if the tag is available, useString it
          sb.append(tag)
        }else{
          //otherwise, useString match all
          sb.append(MATCH_ALL)
        }
        
        List<String> list = new ArrayList<String>()

        if(c != null){
            c(list)
        }

        String vText = buildText(text)
        if(vText.length() > 0)
            list.add(vText)
        String vPosition = buildPosition(position)
        if(vPosition.length() > 0)
            list.add(vPosition)

        if(attributes != null && (!attributes.isEmpty())){
            attributes.each { String key, String value ->
                String vAttr = buildAttribute(key, value)
                if(vAttr.length() > 0)
                    list.add(vAttr)
            }
        }

        if(!list.isEmpty()){
            String attr = list.join(" and ")
            sb.append("[").append(attr).append("]")
        }

        return sb.toString()
    }

    protected static String internBuildXPath(String tag, String text, int position, boolean direct, Map<String, String> attributes, Closure c)
    {
        if(direct)
            return buildXPathWithPrefix(CHILD_PATH, tag, text, position, attributes, c)
        else
            return buildXPathWithPrefix(DESCENDANT_OR_SELF_PATH, tag, text, position, attributes, c)
    }

    public static String buildGroupXPath(String tag, String text, String position, boolean direct, Map<String, String> attributes, Closure c){
        if(position != null && position.isInteger())
            return internBuildXPath(tag, text, Integer.parseInt(position), direct, attributes, c)

        return  internBuildXPath(tag, text, -1 , direct, attributes, c)
    }

    public static String buildXPath(String tag, String text, String position, Map<String, String> attributes){
        if(position != null && position.isInteger())
            return internBuildXPath(tag, text, Integer.parseInt(position), false, attributes, null)

        return  internBuildXPath(tag, text, -1 , false, attributes, null)
    }

    public static String buildXPath(String tag, int position, Map<String, String> attributes){
         return internBuildXPath(tag, null, position, false, attributes, null)
    }

    public static String buildXPath(String tag, String text, Map<String, String> attributes){
         return internBuildXPath(tag, text, -1, false, attributes, null)
    }

    public static String buildXPath(String tag, Map<String, String> attributes){
         return internBuildXPath(tag, null, -1, false, attributes, null)
    }
  
    protected static String buildPosition(int position){
        if(position < 1)
            return ""
        
        return "position()=${position}"
    }

    //For string value, need to useString double quota "", otherwise, the value will become
    //invalid for single quota '' once we have the value as "I'm feeling lucky"
    protected static String buildText(String value){
        if(value == null || (value.trim().length() <= 0))
            return ""

        String trimed = value.trim()
        if(trimed.startsWith(CONTAIN_PREFIX)){
            String actual = value.substring(2)
            return "contains(text(),\"${actual}\")"
        }else{
            return "normalize-space(text())=normalize-space(\"${trimed}\")"
        }

    }

    protected static String buildAttribute(String name, String value){
        //must have an attribute name
        if(name == null || (name.trim().length() <= 0))
           return ""

        //indicate has the attribute
        if(value == null || (value.trim().length() <= 0)){
           return "@${name}"
        }

        String trimed = value.trim()
        //if it is a partial match
        if(trimed.startsWith(CONTAIN_PREFIX)){
            String actual = value.substring(2)
            return "contains(@${name},\"${actual}\")"
        }else{
            return "@${name}=\"${trimed}\""
        }
    }
}