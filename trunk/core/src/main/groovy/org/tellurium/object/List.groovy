package org.tellurium.object

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.locator.CompositeLocator
import org.tellurium.locator.XPathBuilder
import org.tellurium.locator.JQueryBuilder

/**
 * Abstracted class for a list, which holds one dimension array of Ui objects
 * similar to Table, but table is two dimensions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class List extends Container {

    public static final String ALL_MATCH = "ALL";

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

    //the separator for the list, it is empty by default
    String separator = ""

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

    protected String buildLocatorWithoutPosition(CompositeLocator locator){
       return XPathBuilder.buildXPathWithoutPosition(locator.getTag(), locator.getText(), locator.getAttributes())
    }

    protected String buildJQuerySelectorWithoutPosition(CompositeLocator locator){
       return JQueryBuilder.buildJQuerySelectorWithoutPosition(locator.getTag(), locator.getText(), locator.getAttributes())
    }
  
    // example:
    // //div/descendant-or-self::table[2]/descendant-or-self::table
    protected String deriveListLocator(int index) {
        Map<String, Integer> locs = new HashMap<String, Integer>()
        String last = null
        for (int i = 1; i <= index; i++) {
            UiObject obj = findUiObject(i)
//            String tag = obj.locator.getTag()
            String pl = this.buildLocatorWithoutPosition(obj.locator)
            Integer occur = locs.get(pl)
            if (occur == null) {
                locs.put(pl, 1)
            } else {
                locs.put(pl, occur + 1)
            }
            if (i == index) {
                last = pl
            }
        }

//        String lastTag = last.locator.getTag()
//        Integer lastOccur = loc.get(lastTag)
          Integer lastOccur = locs.get(last)
      
/*        if(last.locator.direct){
          return "/${lastTag}[${lastOccur}]"
        }else{
          return "/descendant::${lastTag}[${lastOccur}]"
        }
*/

        //force to be direct child (if consider List trailer)
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return "/${this.namespace}:${last}[${lastOccur}]"
        }
        return "/${last}[${lastOccur}]"
    }

    protected String deriveListSelector(int index) {
        Map<String, Integer> locs = new HashMap<String, Integer>()
        String last = null
        for (int i = 1; i <= index; i++) {
            UiObject obj = findUiObject(i)
//            String pl = obj.locator.getTag()
            String pl = this.buildJQuerySelectorWithoutPosition(obj.locator)
            Integer occur = locs.get(pl)
            if (occur == null) {
                locs.put(pl, 1)
            } else {
                locs.put(pl, occur + 1)
            }
            if (i == index) {
                last = pl
            }
        }

        Integer lastOccur = locs.get(last)

/*        if(last.locator.direct){
          return " > ${lastTag}:eq(${lastOccur-1})"
        }else{
          return " ${lastTag}:eq(${lastOccur-1})"
        }
*/

        //force to be direct child (if consider List trailer)
        return " > ${last}:eq(${lastOccur-1})"
    }

    String getListLocator(int index) {
        if (separator == null || separator.trim().size() == 0)
            return deriveListLocator(index)

//        return "/descendant::" + separator + "[${index}]"
                     //force to be direct child (if consider List trailer)
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return "/${this.namespace}:" + separator + "[${index}]"
        }

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
      if (this.separator != null && this.separator.trim().length() > 0) {
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return accessor.getXpathCount(rl + "/${this.namespace}:${this.separator}")
        }
        return accessor.getXpathCount(rl + "/${this.separator}")
      } else {
        int index = 1
        while (accessor.isElementPresent(rl + getListLocator(index))) {
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
//          separators.add(component.locator.tag)
          separators.add(this.buildJQuerySelectorWithoutPosition(component.locator))
        }
      }

      return c(this.locator, separators.join(","))
    }


  @Override
  public String generateHtml() {
    StringBuffer sb = new StringBuffer(64);
    String ident = getIndent();
    if (this.components.size() > 0) {
      if (this.locator != null)
        sb.append(ident + this.locator.generateHtml(false)).append("\n");

      int max = 0;
      boolean hasAll = false;
      this.components.each {String uid, UiObject obj ->
        String auid = uid.replaceFirst('_', '')
        if ("ALL".equalsIgnoreCase(auid.trim())) {
          hasAll = true;
        }else{
          int indx = Integer.parseInt()
          if (indx > max) {
            max = indx
          }
        }
      }

      if(hasAll)
        max++;
      
      for (int i = 1; i <= max; i++) {
        if(this.separator != null && this.separator.trim().length() > 0){
          sb.append(ident + "  <${separator}>\n")
        }
        UiObject obj = findUiObject(i)
        if(obj == null)
          obj = defaultUi        
        sb.append(obj.generateHtml());
        if(this.separator != null && this.separator.trim().length() > 0){
          sb.append(ident + "  </${separator}>\n")
        }
      }

      if (this.locator != null){
        sb.append(getIndent() + this.locator.generateCloseTag() + "\n");
      }
    }

    return sb.toString();
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