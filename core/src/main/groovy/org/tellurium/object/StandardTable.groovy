package org.tellurium.object

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.exception.InvalidUidException
import org.tellurium.locator.CompositeLocator
import org.tellurium.locator.GroupLocateStrategy
import org.tellurium.locator.LocatorProcessor
import org.tellurium.object.Container
import org.tellurium.object.TextBox
import org.tellurium.object.UiObject
import org.tellurium.extend.Extension

/**
 * Standard table is in the format of
 *
 *  table
 *     thead
 *        tr
 *          td
 *          ...
 *          td
 *     tbody
 *        tr
 *          td
 *          ...
 *          td
 *        ...
 *     tbody (multiple tbodies)
 *        tr
 *          td
 *          ...
 *          td
 *        ...
 *     tfoot
 *        tr
 *          td
 *          ...
 *          td
 *
 * The above table format is used a lot in Java Script framework such as Dojo
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class StandardTable extends Container{

  public static final String TAG = "table"

     public static final String ID_SEPARATOR = ","
     public static final String ID_WILD_CASE = "*"
     public static final String ID_FIELD_SEPARATOR = ":"
     public static final String ALL_MATCH = "ALL"
     public static final String ROW = "ROW"
     public static final String COLUMN = "COLUMN"
     public static final String HEADER = "HEADER"
     public static final String FOOT = "FOOT"
     public static final String TBODY = "TBODY"

     protected static final String INVALID_UID_ERROR_MESSAGE = "Invalid UID "

     protected TextBox defaultUi = new TextBox()
     //add a map to hold all the header elements
     def headers = [:]
     //add a map to hold all the tfoot elements
     def foots = [:]

     @Override
     def add(UiObject component){
        if(validId(component.uid)){
            if(component.uid.toUpperCase().trim().startsWith(HEADER)){
                //this is a header
                String internHeaderId = internalHeaderId(component.uid)
                headers.put(internHeaderId, component)
            }else if(component.uid.toUpperCase().trim().startsWith(FOOT)){
                //this is a foot
                String internFootId = internalFootId(component.uid)
                foots.put(internFootId, component)
            }else{
                //this is a regular element
                String internId = internalId(component.uid)
                //force to not use cache for table cell elements
//                component.cacheable = false
                components.put(internId, component)
            }
        }else{
            throw new InvalidUidException("${INVALID_UID_ERROR_MESSAGE} ${component.uid}")
        }
     }

     public boolean hasHeader(){
         return (headers.size() > 0)
     }

     public static String internalHeaderId(String id){
         String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
         parts[0] = parts[0].trim()
         parts[1] = parts[1].trim()
         if(ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return "_ALL"
         else
            return "_${parts[1].toUpperCase()}"
     }

     public static String internalFootId(String id){
         String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
         parts[0] = parts[0].trim()
         parts[1] = parts[1].trim()
         if(ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return "_ALL"
         else
            return "_${parts[1].toUpperCase()}"
     }

     //should validate the uid before call this to convert it to internal representation
     public static String internalId(String id){
        String row
        String column
        String tbody

         //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

         //check match all case
        if(ALL_MATCH.equals(upperId)){
            row = "ALL"
            column = "ALL"
            tbody = "ALL"

            return "_${tbody}_${row}_${column}"
        }

        String[] parts = upperId.split(ID_SEPARATOR);
        def ids = [:]
        parts.each { String part ->
           String[] fields = part.trim().split(ID_FIELD_SEPARATOR)
           fields[0] = fields[0].trim()
           fields[1] = fields[1].trim()
           if(ID_WILD_CASE.equalsIgnoreCase(fields[1])){
             fields[1] = "ALL"
           }
           ids.put(fields[0], fields[1])
        }
        row = ids.get(ROW)
        column = ids.get(COLUMN)
        tbody = ids.get(TBODY)
        if(tbody == null){
          //if tbody is not defined, assume it is the first one
          tbody = "1"
        }

        return "_${tbody}_${row}_${column}"
     }

    public UiObject findHeaderUiObject(int index) {
        //first check _i format
        String key = "_${index}"
        UiObject obj = headers.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
            obj = headers.get(key)
        }

        return obj
    }

    public UiObject findFootUiObject(int index) {
        //first check _i format
        String key = "_${index}"
        UiObject obj = foots.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
            obj = foots.get(key)
        }

        return obj
    }

  public UiObject findUiObject(int tbody, int row, int column) {

    //first check _i_j_k format
    String key = "_${tbody}_${row}_${column}"
    UiObject obj = components.get(key)

    //thirdly, check _i_j_ALL format
    if (obj == null) {
      key = "_${tbody}_${row}_ALL"
      obj = components.get(key)
    }

    //then, check _i_ALL_K format
    if (obj == null) {
      key = "_${tbody}_ALL_${column}"
      obj = components.get(key)
    }

    //check _ALL_j_k format
    if (obj == null) {
      key = "_ALL_${row}_${column}"
      obj = components.get(key)
    }

    //check _i_ALL_ALL
    if(obj == null){
      key = "_${tbody}_ALL_ALL"
      obj = components.get(key)
    }

    //check _ALL_j_ALL
    if(obj == null){
      key = "_ALL_${row}_ALL"
      obj = components.get(key)
    }

    //check _ALL_ALL_k
    if(obj == null){
      key = "_ALL_ALL_${column}"
      obj = components.get(key)
    }

    //last, check ALL format
    if (obj == null) {
      key = "_ALL_ALL_ALL"
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
        //check if this object is for the header in the format of
        // "header: 2", "header: all"
        if (upperId.startsWith(HEADER)) {
            return validateHeader(id)
        }

        //check if this object is for the foot in the format of
        // "foot: 2", "foot: all"
        if (upperId.startsWith(FOOT)) {
            return validateFoot(id)
        }

        //check match all case
        if (ALL_MATCH.equals(upperId))
            return true

        String[] parts = upperId.split(ID_SEPARATOR)
        //should not be more than three parts, i.e., column, row, and tbody
        if (parts.length > 3)
            return false

        parts.each { String part ->
          if(!validateField(part)){
            return false
          }
        }

        return true
    }

    protected boolean validateFoot(String id) {
        if (id == null || (id.trim().length() <= 0))
            return false

        String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        if (!FOOT.equalsIgnoreCase(parts[0]))
            return false

        //check the template, which could either be "*", "all", or numbers
        if (ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected boolean validateHeader(String id) {
        if (id == null || (id.trim().length() <= 0))
            return false

        String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        if (!HEADER.equalsIgnoreCase(parts[0]))
            return false

        //check the template, which could either be "*", "all", or numbers
        if (ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected boolean validateField(String field) {
        if (field == null || (field.trim().length() <= 0))
            return false

        String[] parts = field.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        //must start with "ROW", "COLUMN", or "TBODY"
        if (!ROW.equals(parts[0]) && !COLUMN.equals(parts[0]) && !TBODY.equals(parts[0]))
            return false

        if (ID_WILD_CASE.equals(parts[1]) )
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected String getCellLocator(int tbody, int row, int column) {

        return "/tbody[${tbody}]/tr[${row}]/td[${column}]"
    }

    protected String getCellSelector(int tbody, int row, int column) {

        return " > tbody:eq(${tbody}) > tr:eq(${row-1}) > td:eq(${column-1})"
    }
  
    protected String getHeaderLocator(int column) {

        return "/thead/tr/td[${column}]"
    }

    protected String getHeaderSelector(int column) {

        return " > thread > tr > td:eq(${column-1})"
    }

    protected String getFootLocator(int column) {

        return "/tfoot/tr/td[${column}]"
    }

    protected String getFootSelector(int column) {

        return " > tfoot > tr > td:eq(${column-1})"
    }

    String[] getAllTableCellText(Closure c) {
        return c(this.locator, " > tbody > tr > td")
    }

    String[] getAllTableCellTextForTbody(int index, Closure c) {
        return c(this.locator, " > tbody:eq(${index-1}) > tr > td")
    }

    int getTableHeaderColumnNumByXPath(Closure c) {
        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/thead/tr/td"
        int columnum = accessor.getXpathCount(xpath)

        return columnum

    }

    int getTableFootColumnNumByXPath(Closure c) {
        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tfoot/tr/td"
        int columnum = accessor.getXpathCount(xpath)

        return columnum

    }

    int getTableMaxRowNumByXPath(Closure c) {

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody[1]/tr/td[1]"
        int rownum = accessor.getXpathCount(xpath)

        return rownum
    }

    int getTableMaxRowNumForTbodyByXPath(int ntbody, Closure c) {

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody[${ntbody}]/tr/td[1]"
        int rownum = accessor.getXpathCount(xpath)

        return rownum
    }

    int getTableMaxColumnNumByXPath(Closure c) {

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody[1]/tr[1]/td"

        int columnum = accessor.getXpathCount(xpath)

        return columnum
    }

    int getTableMaxColumnNumForTbodyByXPath(int ntbody, Closure c) {

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody[${ntbody}]/tr[1]/td"

        int columnum = accessor.getXpathCount(xpath)

        return columnum
    }

    int getTableMaxTbodyNumByXPath(Closure c){
        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody"

        int tbodynum = accessor.getXpathCount(xpath)

        return tbodynum
    }

    int getTableHeaderColumnNumBySelector(Closure c) {
        return c(this.locator, " > thead > tr:eq(0) > td")
    }

    int getTableFootColumnNumBySelector(Closure c) {
        return c(this.locator, " > tfoot > tr:eq(0) > td")
    }

    int getTableMaxRowNumBySelector(Closure c) {
        return c(this.locator, " > tbody:eq(0) > tr:has(td)")
    }

    int getTableMaxRowNumForTbodyBySelector(int ntbody, Closure c) {
        return c(this.locator, " > tbody:eq(${ntbody-1}) > tr:has(td)")
    }

    int getTableMaxColumnNumBySelector(Closure c) {
        return c(this.locator, " > tbody:eq(0) > tr:eq(0) > td")
    }

    int getTableMaxColumnNumForTbodyBySelector(int ntbody, Closure c) {
         return c(this.locator, " > tbody:eq(${ntbody-1}) > tr:eq(0) > td")
    }

    int getTableMaxTbodyNumBySelector(Closure c){
         return c(this.locator, " > tbody")
    }

    //walk to a regular UI element in the table
    protected walkToElement(WorkflowContext context, UiID uiid) {
        String child = uiid.pop()
        String[] parts = child.replaceFirst('_', '').split("_")
        int nrow
        int ncolumn
        int ntbody
        if(parts.length == 3){
          ntbody = Integer.parseInt(parts[0])
          nrow = Integer.parseInt(parts[1])
          ncolumn = Integer.parseInt(parts[2])
        }else{
          ntbody = 1
          nrow = Integer.parseInt(parts[0])
          ncolumn = Integer.parseInt(parts[1])
        }
      
        //otherwise, try to find its child
        UiObject cobj = this.findUiObject(ntbody, nrow, ncolumn)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
          groupLocating(context)
        }

        //append relative location, i.e., tbody, row, column to the locator
        String loc = null
        if(context.isUseJQuerySelector()){
          //jquery eq() starts from zero, while xpath starts from one
//          loc = getCellSelector(ntbody-1, nrow-1, ncolumn-1)
          loc = getCellSelector(ntbody, nrow, ncolumn)
        }else{
          loc = getCellLocator(ntbody, nrow, ncolumn)
        }

        context.appendReferenceLocator(loc)
      
        if(cobj.locator != null){
           if(cobj.locator instanceof CompositeLocator){
              CompositeLocator cl = (CompositeLocator)cobj.locator
              if("td".equals(cl.tag) && cl.header == null){
                context.setTableDuplicateTag()
              }
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walk to a header UI element in the table
    protected walkToHeader(WorkflowContext context, UiID uiid) {
        //pop up the "header" indicator
        uiid.pop()
        //reach the actual uiid for the header element
        String child = uiid.pop()

        child = child.replaceFirst('_', '')
        int index = Integer.parseInt(child.trim())

        //try to find its child
        UiObject cobj = this.findHeaderUiObject(index)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
          groupLocating(context)
        }

        //append relative location, i.e., row, column to the locator
        String loc =  null
        if(context.isUseJQuerySelector()){
          loc = getHeaderSelector(index)
        }else{
          loc = getHeaderLocator(index)
        }
      
        context.appendReferenceLocator(loc)

        if(cobj.locator != null){
          if(cobj.locator instanceof CompositeLocator){
            CompositeLocator cl = (CompositeLocator)cobj.locator
            if("td".equals(cl.tag) && cl.header == null){
              context.setTableDuplicateTag()
            }
          }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walk to a foot UI element in the table
    protected walkToFoot(WorkflowContext context, UiID uiid) {
        //pop up the "foot" indicator
        uiid.pop()
        //reach the actual uiid for the foot element
        String child = uiid.pop()

        child = child.replaceFirst('_', '')
        int index = Integer.parseInt(child.trim())

        //try to find its child
        UiObject cobj = this.findFootUiObject(index)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
          groupLocating(context)
        }

        //append relative location, i.e., row, column to the locator
        String loc =  null
        if(context.isUseJQuerySelector()){
          loc = getFootSelector(index)
        }else{
          loc = getFootLocator(index)
        }
      
        context.appendReferenceLocator(loc)

        if(cobj.locator != null){
          if(cobj.locator instanceof CompositeLocator){
            CompositeLocator cl = (CompositeLocator)cobj.locator
            if("td".equals(cl.tag) && cl.header == null){
              context.setTableDuplicateTag()
            }
          }
        }
 
        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        String child = uiid.peek()

        if (child.trim().equalsIgnoreCase(HEADER)) {
            return walkToHeader(context, uiid)
        }else if(child.trim().equalsIgnoreCase(FOOT)){
            return walkToFoot(context, uiid)
        }else {
            return walkToElement(context, uiid)
        }
    }

  @Override
  public void traverse(WorkflowContext context) {
    context.appendToUidList(context.getUid())

    traverseHeader(context)
    traverseElement(context)
    traverseFoot(context)
  }

  protected void traverseHeader(WorkflowContext context){
    if(this.hasHeader()){
      int max = 0
      this.headers.each {key, component ->
        String aid = key.replaceFirst('_', '')
        if (aid ==~ /[0-9]+/) {
          context.pushUid("header[${aid}]")
          component.traverse(context)
          if (max < Integer.parseInt(aid))
            max = Integer.parseInt(aid)
        }
      }

      UiObject obj = this.headers.get("_ALL")
      if(obj != null){
        max++
        context.pushUid("header[${max}]")
        obj.traverse(context)
      }
    }
  }

  protected void traverseFoot(WorkflowContext context){
    if(this.foots.size() > 0){
      int max = 0
      this.foots.each {key, component ->
        String aid = key.replaceFirst('_', '')
        if (aid ==~ /[0-9]+/) {
          context.pushUid("foot[${aid}]")
          component.traverse(context)
          if (max < Integer.parseInt(aid))
            max = Integer.parseInt(aid)
        }
      }

      UiObject obj = this.foots.get("_ALL")
      if(obj != null){
        max++
        context.pushUid("foot[${max}]")
        obj.traverse(context)
      }
    }
  }

  protected void traverseElement(WorkflowContext context) {
    int tmax = 0
    int rmax = 0
    int cmax = 0
    this.components.each {key, component ->
//      String[] parts = key.replaceFirst('_', '').split("_")
      String[] parts = key.replaceFirst('_', '').split("_")
      if (parts.length == 2) {
        if (parts[0] ==~ /[0-9]+/ && rmax < Integer.parseInt(parts[0])) {
          rmax = Integer.parseInt(parts[0])
        }
        if (parts[1] ==~ /[0-9]+/ && cmax < Integer.parseInt(parts[1])) {
          cmax = Integer.parseInt(parts[1])
        }
      } else {
        if (parts[0] ==~ /[0-9]+/ && tmax < Integer.parseInt(parts[0])) {
          tmax = Integer.parseInt(parts[0])
        }
        if (parts[1] ==~ /[0-9]+/ && rmax < Integer.parseInt(parts[1])) {
          rmax = Integer.parseInt(parts[1])
        }
        if (parts[2] ==~ /[0-9]+/ && cmax < Integer.parseInt(parts[2])) {
          cmax = Integer.parseInt(parts[2])
        }
      }
    }

    tmax++
    rmax++
    cmax++
    boolean includeMatchAll = false

    this.components.each {key, component ->
      String[] parts = key.replaceFirst('_', '').split("_")
      
      if (parts.length == 2) {
        String part0 = processField(parts[0], rmax)
        String part1 = processField(parts[1], cmax)
        context.directPushUid("[${tmax}][${part0}][${part1}]")
        if (ALL_MATCH.equalsIgnoreCase(parts[0]) && ALL_MATCH.equalsIgnoreCase(parts[1])) {
          includeMatchAll = true
        }
      } else {
        String part0 = processField(parts[0], tmax)
        String part1 = processField(parts[1], rmax)
        String part2 = processField(parts[2], cmax)
        context.directPushUid("[${part0}][${part1}][${part2}]")
        if (ALL_MATCH.equalsIgnoreCase(parts[0]) && ALL_MATCH.equalsIgnoreCase(parts[1]) && ALL_MATCH.equalsIgnoreCase(parts[2])) {
          includeMatchAll = true
        }
      }

      component.traverse(context)
    }

    if (!includeMatchAll) {
      context.directPushUid("[${tmax}][${rmax}][${cmax}]")
      defaultUi.traverse(context)
    }
  }

  protected String processField(String field, int max){
    if(field ==~ /[0-9]+/){
      return field
    }else{
      return "${max}"
    }
  }
}