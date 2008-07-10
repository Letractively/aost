package aost.locator
/**
 *   A utility class to build xpath from a given parameter set
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class XPathBuilder {
    // Selects all children of the current node
    private static final String CHILD = "child"

    // Selects all descendants (children, grandchildren, etc.) of the current node
    private static final String DESCENDANT = "descendan"

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
    private static final String PREFIX ="/descendant-or-self::"

    private static final String MATCH_ALL ="*"

    //represent it is a partial match i.e., contains
    private static final String CONTAIN_PREFIX = "%%"

    private static final int TYPICAL_LENGTH = 64

    // example xpaths
    //  //descendant-or-self::*[@id='hp_table']/tbody/tr/td[1]/descendant-or-self::div/div[2]/p[1]/a
    //  /html/body/div[5]/center/table[@id='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::table[@id='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::*[@id='hp_table']/tbody/tr/td[1]/div/div[2]/p[1]/a
    //  /html/body/div[5]/center/descendant-or-self::*[@id='hp_table']/tbody/tr/td[1]/descendant-or-self::div/div[2]/p[1]/a

    public static String buildXPath(String tag, String text, String position, Map<String, String> attributes){
        if(position != null && position.isInteger())
            return internBuildXPath(tag, text, Integer.parseInt(position), attributes)

        return  internBuildXPath(tag, text, -1 , attributes)
    }

    protected static String internBuildXPath(String tag, String text, int position, Map<String, String> attributes)
    {

        StringBuffer sb = new StringBuffer(TYPICAL_LENGTH)
        sb.append(PREFIX)
        if( tag != null && (!tag.isEmpty())){
          //if the tag is available, use it
          sb.append(tag)
        }else{
          //otherwise, use match all
          sb.append(MATCH_ALL)
        }
        List<String> list = new ArrayList<String>()

        String vText = buildText(text)
        if(!vText.isEmpty())
            list.add(vText)
        String vPosition = buildPosition(position)
        if(!vPosition.isEmpty())
            list.add(vPosition)

        if(attributes != null && (!attributes.isEmpty())){
            attributes.each { String key, String value ->
                String vAttr = buildAttribute(key, value)
                if(!vAttr.isEmpty())
                    list.add(vAttr)
            }
        }

        if(!list.isEmpty()){
            String attr = list.join(" and ")
            sb.append("[").append(attr).append("]")
        }

        return sb.toString()
    }

    public static String buildXPath(String tag, int position, Map<String, String> attributes){
         return internBuildXPath(tag, null, position, attributes)
    }

    public static String buildXPath(String tag, String text, Map<String, String> attributes){
         return internBuildXPath(tag, text, -1, attributes)
    }

    public static String buildXPath(String tag, Map<String, String> attributes){
         return internBuildXPath(tag, null, -1, attributes)
    }
  
    protected static String buildPosition(int position){
        if(position < 1)
            return ""
        
        return "position()=${position}"
    }

    protected static String buildText(String value){
        if(value == null || value.trim().isEmpty())
            return ""

        String trimed = value.trim()
        if(trimed.startsWith(CONTAIN_PREFIX)){
            String actual = value.substring(2)
            return "contains(text(),'${actual}')"
        }else{
            return "text()='${trimed}'"
        }

    }

    protected static String buildAttribute(String name, String value){
        //must have an attribute name
        if(name == null || name.trim().isEmpty())
           return ""

        //indicate has the attribute
        if(value == null || value.trim().isEmpty()){
           return "@${name}"
        }

        String trimed = value.trim()
        //if it is a partial match
        if(trimed.startsWith(CONTAIN_PREFIX)){
            String actual = value.substring(2)
            return "contains(@${name},'${actual}')"
        }else{
            return "@${name}='${trimed}'"
        }
    }
}