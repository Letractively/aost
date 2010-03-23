package org.tellurium.object

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.json.simple.JSONObject

/**
 * Abstracted class for a list, which holds one dimension array of Ui objects
 * similar to Table, but table is two dimensions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class List extends Container {

    public static final String ALL_MATCH = "ALL";

    public static final String SEPARATOR = "separator"
    //the separator for the list, it is empty by default
    protected String separator = ""

    @Override
    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "List")
        if(separator != null && separator.trim().length() > 0)
          jso.put(SEPARATOR, this.separator)
      }
    }

    protected TextBox defaultUi = new TextBox()

    @Override
    def add(UiObject component) {
        if (validId(component.uid)) {
            String internId = internalId(component.uid)
            //force to not use cache for List elements
//            component.cacheable = false
            components.put(internId, component)
        } else {
            System.out.println("Warning: Invalid id: ${component.uid}")
        }
    }

    //should validate the uid before call this to convert it to internal representation
    public static String internalId(String id) {

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        return "_${upperId}"
    }

    public UiObject findUiObject(int index) {

        //first check _index format
        String key = "_${index}"
        UiObject obj = components.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
            obj = components.get(key)
        }

        return obj
    }

    public boolean validId(String id) {
        //UID cannot be empty
        if (id == null || (id.trim().length() <= 0))
            return false

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        //check match all case
        if (ALL_MATCH.equals(upperId))
            return true
        return (upperId ==~ /[0-9]+/)
    }

    // example:
    // //div/descendant-or-self::table[2]/descendant-or-self::table
    protected String deriveListLocator(int index) {
        Map<String, Integer> tags = new HashMap<String, Integer>()
        UiObject last = null
        for (int i = 1; i <= index; i++) {
            UiObject obj = findUiObject(i)
            String tag = obj.locator.getTag()
            Integer occur = tags.get(tag)
            if (occur == null) {
                tags.put(tag, 1)
            } else {
                tags.put(tag, occur + 1)
            }
            if (i == index) {
                last = obj
            }
        }

        String lastTag = last.locator.getTag()
        Integer lastOccur = tags.get(lastTag)
      
/*        if(last.locator.direct){
          return "/${lastTag}[${lastOccur}]"
        }else{
          return "/descendant::${lastTag}[${lastOccur}]"
        }
*/

        //force to be direct child (if consider List trailer)
        return "/${lastTag}[${lastOccur}]"
    }

    protected String deriveListSelector(int index) {
        Map<String, Integer> tags = new HashMap<String, Integer>()
        UiObject last = null
        for (int i = 1; i <= index; i++) {
            UiObject obj = findUiObject(i)
            String tag = obj.locator.getTag()
            Integer occur = tags.get(tag)
            if (occur == null) {
                tags.put(tag, 1)
            } else {
                tags.put(tag, occur + 1)
            }
            if (i == index) {
                last = obj
            }
        }

        String lastTag = last.locator.getTag()
        Integer lastOccur = tags.get(lastTag)

/*        if(last.locator.direct){
          return " > ${lastTag}:eq(${lastOccur-1})"
        }else{
          return " ${lastTag}:eq(${lastOccur-1})"
        }
*/

        //force to be direct child (if consider List trailer)
        return " > ${lastTag}:eq(${lastOccur-1})"
    }

    String getListLocator(int index) {
        if (separator == null || separator.trim().size() == 0)
            return deriveListLocator(index)

//        return "/descendant::" + separator + "[${index}]"
        return "/" + separator + "[${index}]"
    }

    String getListSelector(int index) {
        if (separator == null || separator.trim().size() == 0)
            return deriveListSelector(index)

//        return " " + separator + ":eq(${index-1})"
        return " > " + separator + ":eq(${index-1})"
    }

    int getListSizeByXPath(Closure c) {

      String rl = c(this.locator)

      Accessor accessor = new Accessor()
      WorkflowContext context = WorkflowContext.getDefaultContext()
      if (this.separator != null && this.separator.trim().length() > 0) {
        return accessor.getXpathCount(context, rl + "/${this.separator}")
      } else {
        int index = 1
        while (accessor.isElementPresent(context, rl + getListLocator(index))) {
          index++
        }

        index--

        return index
      }
    }

    int getListSizeByJQuerySelector(Closure c){
      java.util.List separators = new ArrayList()
      if(separator != null  && this.separator.trim().size() > 0){
        separators.add(this.separator)
      }else{
        this.components.each {key, component->
          separators.add(component.locator.tag)
        }
      }

      return c(this.locator, separators.join(","))
    }

    @Override
    public void traverse(WorkflowContext context){
      context.appendToUidList(context.getUid())

      int max = 0
      components.each {key, component->
        String aid = key.replaceFirst('_', '')
        if(aid ==~ /[0-9]+/){
          context.directPushUid("[${aid}]")
          component.traverse(context)
          if(max < Integer.parseInt(aid))
            max = Integer.parseInt(aid)
        }
      }

      UiObject obj = components.get("_ALL")
      max++;
      if(obj == null)
        obj = defaultUi
      context.directPushUid("[${max}]")
      obj.traverse(context)
      context.popUid()
    }
  
    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        String child = uiid.pop()

        String part = child.replaceFirst('_', '')

        int nindex = Integer.parseInt(part)

        //otherwise, try to find its child
        UiObject cobj = this.findUiObject(nindex)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
          groupLocating(context)
        }

        //append relative location, i.e., index to the locator
        String loc = null
        if(context.isUseJQuerySelector()){
          loc = getListSelector(nindex)
        }else{
          loc = getListLocator(nindex)
        }
        context.appendReferenceLocator(loc)
        //If the List does not have a separator
        //tell WorkflowContext not to process the next object's locator because List has already added that
        if(this.separator == null || this.separator.trim().length() == 0){
          context.skipNext()
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }
    }

}