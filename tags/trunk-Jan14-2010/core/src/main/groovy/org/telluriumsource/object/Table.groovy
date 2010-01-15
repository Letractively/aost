package org.telluriumsource.object

import org.telluriumsource.access.Accessor
import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.exception.InvalidUidException
import org.telluriumsource.locator.CompositeLocator
import org.telluriumsource.locator.XPathBuilder
import org.telluriumsource.object.Container
import org.telluriumsource.object.TextBox
import org.telluriumsource.object.UiObject
import org.json.simple.JSONObject


/**
 *   This is a table without header tag "thead' and foot "tfoot", but in the format of
 *
 *   table
 *       tbody
 *          tr
 *             th
 *             ...
 *             th
 *          tr
 *             td
 *             ...
 *             td
 *
 *   Table should be very generic since each column and row could hold different
 *   UI objects. In that sense, table is also a container
 *
 *   However, all the UI objects inside the table should have related xpath. Also,
 *   the UI object uid should carry informatio about table row, table column.
 *   It also has wild case to match all rows, all columns, all both.
 *
 *   That is to say, for the i-th row, j-th column, the uid should use the following
 *   name conversion:
 *
 *    "row: i, column: j"
 *
 *   note that row and column starts from 1. Uppercase or lowercase would both be fine.

 *   The wild case for row or column is "*", or you do not specify the row, or column
 *   and for all, you can useString "all" or "ALL".
 *
 *   As a result, the following names are valid (case insensitive):
 *
 *   "row : 1, column : 3"         //first row, 3rd column
 *   "row : 5"                    //5th row, all columns
 *   "column : 6"                 //6th column, all rows
 *
 *   "row : *, column : 3"         //3rd column, all rows
 *   "row : 5, column : *"         //5th row, all columns
 *
 *   "all"                       //all rows, all columns
 *
 *   The internal representation is
 *
 *   "_i_j"
 *
 *   and wild case will be replace by ALL. Thus, for a given i-th row, j-th-column,
 *   the search chain would look as follows,
 *
 *   _i_j
 *
 *   _ALL_j
 *
 *   _i_ALL
 *
 *   _ALL_ALL
 *
 *   By default, the UI Object is a text box and there is no need to specify it.
 *   we can always get back the text by specifying the row number and column number.
 *
 *   If not one could be found by the above search chain, it will assume that you
 *   are using the text box.
 *
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */


class Table extends Container {

  public static final String TAG = "table"

  public static final String ID_SEPARATOR = ",";
  public static final String ID_WILD_CASE = "*";
  public static final String ID_FIELD_SEPARATOR = ":";
  public static final String ALL_MATCH = "ALL";
  public static final String ROW = "ROW";
  public static final String COLUMN = "COLUMN";
  public static final String HEADER = "HEADER";
  public static final String TBODY = "/tbody"
  protected TextBox defaultUi = new TextBox()
  //add a map to hold all the header elements

  protected String tbody = TBODY

  def headers = [:]
  def bodyAttributes = [:]

  @Override
  public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Table")
      }
  }

  @Override
  def add(UiObject component) {
    if (validId(component.uid)) {
      if (component.uid.toUpperCase().trim().startsWith(HEADER)) {
        //this is a header
        String internHeaderId = internalHeaderId(component.uid)
        component.tid = internHeaderId
        headers.put(internHeaderId, component)
      } else {
        //this is a regular element
        String internId = internalId(component.uid)
        //force to not use cache for table cell elements
//        component.cacheable = false
        component.tid = internId
        components.put(internId, component)
      }
    } else {
        throw new InvalidUidException(i18nBundle.getMessage("Container.InvalidUID" , {component.uid}))
    }
  }

  public setBodyAttributes(Map attributes) {
    if (attributes != null && attributes.size() > 0) {
      this.bodyAttributes.putAll(attributes);
    }
    this.bodyAttributes.put("tag", "tbody")
    this.tbody = this.getTBodyXPath()
  }

  public String getTBodyXPath() {

    return XPathBuilder.buidXPathFromAttributes(this.bodyAttributes)
  }

  public boolean hasHeader() {
    return (headers.size() > 0)
  }

  public static String internalHeaderId(String id) {
    String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
    parts[0] = parts[0].trim()
    parts[1] = parts[1].trim()
    if (ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
      return "_ALL"
    else
      return "_${parts[1].toUpperCase()}"
  }

  //should validate the uid before call this to convert it to internal representation
  public static String internalId(String id) {
    String row
    String column

    //convert to upper case so that it is case insensitive
    String upperId = id.trim().toUpperCase()

    //check match all case
    if (ALL_MATCH.equals(upperId)) {
      row = "ALL"
      column = "ALL"

      return "_${row}_${column}"
    }

    String[] parts = upperId.split(ID_SEPARATOR);

    if (parts.length == 1) {
      String[] fields = parts[0].trim().split(ID_FIELD_SEPARATOR)
      fields[0] = fields[0].trim()
      fields[1] = fields[1].trim()
      if (ROW.equals(fields[0])) {
        row = fields[1]
        if (ID_WILD_CASE.equalsIgnoreCase(row))
          row = "ALL"
        column = "ALL"
      }

      if (COLUMN.equals(fields[0])) {
        column = fields[1]
        if (ID_WILD_CASE.equalsIgnoreCase(column))
          column = "ALL"
        row = "ALL"
      }
    } else {
      for (int i = 0; i < 2; i++) {
        String[] fields = parts[i].trim().split(ID_FIELD_SEPARATOR)
        fields[0] = fields[0].trim()
        fields[1] = fields[1].trim()
        if (ROW.equals(fields[0])) {
          row = fields[1]
          if (ID_WILD_CASE.equals(row))
            row = "ALL"
        }

        if (COLUMN.equals(fields[0])) {
          column = fields[1]
          if (ID_WILD_CASE.equals(column))
            column = "ALL"
        }
      }
    }

    return "_${row}_${column}"
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

  public UiObject findUiObject(int row, int column) {

    //first check _i_j format
    String key = "_${row}_${column}"
    UiObject obj = components.get(key)

    //then, check _ALL_j format
    if (obj == null) {
      key = "_ALL_${column}"
      obj = components.get(key)
    }

    //thirdly, check _i_ALL format
    if (obj == null) {
      key = "_${row}_ALL"
      obj = components.get(key)
    }

    //last, check ALL format
    if (obj == null) {
      key = "_ALL_ALL"
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

    //check match all case
    if (ALL_MATCH.equals(upperId))
      return true

    String[] parts = upperId.split(ID_SEPARATOR)
    //should not be more than two parts, i.e., column part and row part
    if (parts.length > 2)
      return false

    if (parts.length <= 1) {
      //If only one part is specified
      return validateField(parts[0])
    } else {
      return validateField(parts[0]) && validateField(parts[1])
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

    //must start with "ROW" or "COLUMN"
    if (!ROW.equals(parts[0]) && !COLUMN.equals(parts[0]))
      return false

    if (ID_WILD_CASE.equals(parts[1]))
      return true
    else {
      return (parts[1] ==~ /[0-9]+/)
    }
  }

  protected boolean hasNamespace(){
    return this.namespace != null && this.namespace.trim().length() > 0
  }
  protected String getCellLocator(int row, int column) {
    if(hasNamespace()){
      return  "/${this.namespace}:tbody/${this.namespace}:tr[child::${this.namespace}:td][${row}]/${this.namespace}:td[${column}]"
    }
    return tbody + "/tr[child::td][${row}]/td[${column}]"
  }

  protected String getCellSelector(int row, int column) {
    //TODO: :has(td) is not the same as child::td, for example, if we have another embedded table. Need to address this case
    return " > tbody > tr:has(td):eq(${row-1}) > td:eq(${column-1})"
  }

  protected String getHeaderLocator(int column) {

//        return "/tbody/tr[1]/th[${column}]"
    if(this.hasNamespace()){
      return "/${this.namespace}:tbody/${this.namespace}:tr[child::${this.namespace}:th]/${this.namespace}:th[${column}]"
    }
    return tbody + "/tr[child::th]/th[${column}]"
  }

  protected String getHeaderSelector(int column) {

    return " > tbody > tr:has(th) > th:eq(${column-1})"
  }

  String[] getAllTableCellText(Closure c){
    return c(this.locator, " > tbody > tr > td")
  }

  int getTableHeaderColumnNumByXPath(Closure c) {

    String rl = c(this.locator)
    Accessor accessor = new Accessor()
//        String xpath = rl + "/tbody/tr[1]/th"
    String xpath;
    if(this.hasNamespace()){
       xpath = rl + "/${this.namespace}:tbody/${this.namespace}:tr[child::${this.namespace}:th]/${this.namespace}:th"
    }else{
       xpath = rl + tbody + "/tr[child::th]/th"
    }
    int columnum = accessor.getXpathCount(WorkflowContext.getDefaultContext(), xpath)

    return columnum
  }

  int getTableMaxRowNumByXPath(Closure c) {

    String rl = c(this.locator)
    Accessor accessor = new Accessor()
    String xpath;
    if(this.hasNamespace()){
       xpath = rl + "/${this.namespace}:tbody/${this.namespace}:tr[child::${this.namespace}:td]/${this.namespace}:td[1]"
    }else{
       xpath = rl + tbody + "/tr[child::td]/td[1]"
    }

    int rownum = accessor.getXpathCount(WorkflowContext.getDefaultContext(), xpath)

    return rownum
  }

  int getTableMaxColumnNumByXPath(Closure c) {

    String rl = c(this.locator)
    Accessor accessor = new Accessor()
    String xpath = rl
    if(this.hasNamespace()){
      xpath = xpath + "/${this.namespace}:tbody/${this.namespace}:tr[child::${this.namespace}:td][1]/${this.namespace}:td"
    }else{
      xpath = xpath + tbody + "/tr[child::td][1]/td"
    }

    int columnum = accessor.getXpathCount(WorkflowContext.getDefaultContext(), xpath)

    return columnum
  }

  int getTableHeaderColumnNumBySelector(Closure c) {
    return c(this.locator, " > tbody > tr:has(th):eq(0) > th")
  }

  int getTableMaxRowNumBySelector(Closure c) {

    return c(this.locator, " > tbody > tr:has(td)")
  }

  int getTableMaxColumnNumBySelector(Closure c) {
     return c(this.locator, " > tbody > tr:has(td):eq(0) > td")
  }

  int getMaxHeaderIndex() {
    int max = 0;
    boolean hasAll = false;
    if (this.headers.size() > 0) {
      this.headers.each {String uid, UiObject obj ->
        String auid = uid.replaceFirst('_', '')
        if ("ALL".equalsIgnoreCase(auid.trim())) {
          hasAll = true;
        }else{
          int indx = Integer.parseInt(uid.replaceFirst('_', ''));
          if (indx > max) {
            max = indx;
          }
        }
      }
    }

    if(hasAll)
      max++;

    return max;
  }

  def getMaxRowColumnIndices(){
    int maxrow = 0;
    int maxcol = 0;
    boolean rowHasAll = false;
    boolean colHasAll = false;
    components.each {String uid, UiObject obj ->
      String[] splitted = uid.replaceFirst('_', '').split("_");
      if("ALL".equalsIgnoreCase(splitted[0])){
        rowHasAll = true;
      }else{
        int rowindx = Integer.parseInt(splitted[0]);
        if(rowindx > maxrow)
          maxrow = rowindx;
      }

      if("ALL".equalsIgnoreCase(splitted[1])){
        colHasAll = true;
      }else{
        int colindx = Integer.parseInt(splitted[1]);
        if(colindx > maxcol)
          maxcol = colindx;
      }
    }

    if (rowHasAll)
      maxrow++;

    if (colHasAll)
      maxcol++;

    return [maxrow, maxcol];
  }

  @Override
  public String generateHtml() {
    StringBuffer sb = new StringBuffer(128);
    String indent = getIndent();

    if (this.locator != null)
        sb.append(indent + this.locator.generateHtml(false)).append("\n").append(indent + " <tbody>\n");
    else
        sb.append(indent + "<table>\n").append(indent + " <tbody>\n");

    if(this.hasHeader()){
      int maxheader = this.getMaxHeaderIndex();
      sb.append(indent + "  <tr>\n");
      for(int i=1; i<=maxheader; i++){
          sb.append(indent + "   <th>\n")
          UiObject obj = this.findHeaderUiObject(i);
          if (obj == null) {
            obj = this.defaultUi
          }
          sb.append(obj.generateHtml()).append("\n");
          sb.append(indent + "   </th>\n")
      }
      sb.append(indent + "  </tr>\n");
    }

    if (this.components.size() > 0) {
      Integer[] val = this.getMaxRowColumnIndices();
      int maxrow = val[0];
      int maxcol = val[1];
      for(int j=1; j<=maxrow; j++){
        sb.append(indent + "  <tr>\n");
        for(int k=1; k<=maxcol; k++){
          sb.append(indent + "   <td>\n");
          UiObject elem = this.findUiObject(j, k);
          if (elem == null) {
            elem = this.defaultUi
          }
          sb.append(elem.generateHtml()).append("\n");
          sb.append(indent + "   </td>\n");
        }
        sb.append(indent + "  </tr>\n");
      }
    }
    sb.append(indent + " </tbody>\n");
    sb.append(indent + "</table>\n");

    return sb.toString();
  }

  //walk to a regular UI element in the table
  protected walkToElement(WorkflowContext context, UiID uiid) {
    String child = uiid.pop()
    String[] parts = child.replaceFirst('_', '').split("_")

    int nrow = Integer.parseInt(parts[0])
    int ncolumn = Integer.parseInt(parts[1])
    //otherwise, try to find its child
    UiObject cobj = this.findUiObject(nrow, ncolumn)

    //If cannot find the object as the object template, return the TextBox as the default object
    if (cobj == null) {
      cobj = this.defaultUi
    }

    //update reference locator by append the relative locator for this container
    if (this.locator != null) {
      groupLocating(context)
    }

    //append relative location, i.e., row, column to the locator
    String loc = null
    if(context.isUseCssSelector()){
      //jquery eq() starts from zero, while xpath starts from one
      loc = getCellSelector(nrow, ncolumn)
    }else{
      loc = getCellLocator(nrow, ncolumn)
    }

    context.appendReferenceLocator(loc)

    if(cobj.locator != null){
      if(cobj.locator instanceof CompositeLocator){
        CompositeLocator cl = (CompositeLocator)cobj.locator
        if("td".equals(cl.tag) && cl.header == null){
          //context.setTableDuplicateTag()
          context.skipNext()
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
    String loc = null
    if(context.isUseCssSelector()){
      loc = getHeaderSelector(index)
    }else{
      loc = getHeaderLocator(index)
    }

    context.appendReferenceLocator(loc)

    if(cobj.locator != null){
      if(cobj.locator instanceof CompositeLocator){
        CompositeLocator cl = (CompositeLocator)cobj.locator
        if("th".equals(cl.tag) && cl.header == null){
//          context.setTableDuplicateTag()
          context.skipNext()
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
//    if (uiid.size() < 1)
//      return this
    if (uiid.size() < 1) {
      if (this.locator != null && this.useGroupInfo) {
        groupLocating(context)
      }

      return this
    }

    String child = uiid.peek()

    if (child.trim().equalsIgnoreCase(HEADER)) {
      return walkToHeader(context, uiid)
    } else {
      return walkToElement(context, uiid)
    }
  }

  @Override
  public void traverse(WorkflowContext context) {
    context.appendToUidList(context.getUid())

    traverseHeader(context)
    traverseElement(context)
    context.popUid()
  }

  @Override
  public void treeWalk(WorkflowContext context){
    this.jsonifyObject(context)
    if(this.hasHeader()){
      this.headers.each {key, component ->
        if(component.cacheable){
          component.treeWalk(context)
        }
      }
    }

    if (!this.noCacheForChildren) {
      this.components.each {key, component ->
        if (component.cacheable) {
          component.treeWalk(context)
        }
      }
    }
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

  protected void traverseElement(WorkflowContext context){

    int rmax = 0
    int cmax = 0
    this.components.each {key, component->
      String[] parts = key.replaceFirst('_', '').split("_")
      if(parts[0] ==~ /[0-9]+/ && rmax < Integer.parseInt(parts[0])){
        rmax = Integer.parseInt(parts[0])
      }
      if(parts[1] ==~ /[0-9]+/ && cmax < Integer.parseInt(parts[1])){
        cmax = Integer.parseInt(parts[1])
      }
    }

    rmax++
    cmax++
    boolean includeMatchAll = false

    this.components.each {key, component->
      String[] parts = key.replaceFirst('_', '').split("_")
      if(parts[0] ==~ /[0-9]+/ && parts[1] ==~ /[0-9]+/){
        context.directPushUid("[${parts[0]}][${parts[1]}]")
      }else if(parts[0] ==~ /[0-9]+/ && ALL_MATCH.equalsIgnoreCase(parts[1])){
        context.directPushUid("[${parts[0]}][${cmax}]")
      }else if(ALL_MATCH.equalsIgnoreCase(parts[0]) && parts[1]==~ /[0-9]+/){
        context.directPushUid("[${rmax}][${parts[1]}]")
      }else{
        includeMatchAll = true
        context.directPushUid("[${rmax}][${cmax}]")
      }

      component.traverse(context)
    }

    if(!includeMatchAll){
      context.directPushUid("[${rmax}][${cmax}]")
      defaultUi.traverse(context)
    }
  }
}
