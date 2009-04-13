package org.tellurium.widget

import java.util.List
import org.tellurium.access.Accessor
import org.tellurium.dsl.UiDslParser
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.event.EventHandler
import org.tellurium.exception.UiObjectNotFoundException
import org.tellurium.locator.LocatorProcessor
import org.tellurium.object.StandardTable
import org.tellurium.object.UiObject
import org.tellurium.util.Helper
import org.tellurium.extend.Extension
import org.stringtree.json.JSONReader
import org.json.simple.JSONArray

/**
 * The base class for Widget objects.
 *
 * Could be implemented using method delegation, but really ugly in that way.
 *
 * So we duplicate the code from BaseDslContext here until Groovy starts to support Mixin.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
//@Mixin(BaseDslContext)
abstract class Widget extends UiObject {
 //later on, may need to refactor it to use resource file so that we can show message for different localities
  protected static final String ERROR_MESSAGE = "Cannot find UI Object"
  protected static final String JQUERY_SELECTOR = "jquery="
  protected static final String DEFAULT_XPATH = "default"
  protected static final String JAVASCRIPT_XPATH = "javascript"
  protected static final String AJAXSLT_XPATH = "ajaxslt"
  public final static String NAMESPACE_SUFFIX = "_"

  //flag to decide whether we should use jQuery Selector
  protected boolean exploreJQuerySelector = false

  //the reference xpath for widget's parent
  private String pRef;

  //Note:
  //we need namespace to differentiate the same widget name from different widget modules
  //for example, if Dojo and ExtJS both has the widget called Accordion, we have to differentiate
  //them using name space, i.e., DOJO::Accordion and ExtJS::Accordion

  public void updateParentRef(String ref) {
    this.pRef = ref
  }

  abstract public void defineWidget()

  UiDslParser ui = new UiDslParser()
  //decoupling eventhandler, locateProcessor, and accessor from UI objects
  //and let DslContext to handle all of them directly. This will also make
  //the framework reconfiguration much easier.
  EventHandler eventHandler = new EventHandler()
  Accessor accessor = new Accessor()
  LocatorProcessor locatorProcessor = new LocatorProcessor()
  Extension extension = new Extension()

  private JSONReader reader = new JSONReader()

  protected String locatorMapping(WorkflowContext context, loc) {
    //get ui object's locator
    String lcr = locatorProcessor.locate(loc)
    //widget's locator
    String wlc = locatorProcessor.locate(this.locator)

    //get the reference locator all the way to the ui object
    if (context.getReferenceLocator() != null) {
//            lcr = context.getReferenceLocator() + lcr
      context.appendReferenceLocator(lcr)
      lcr = context.getReferenceLocator()
    }

    //append the object's xpath to widget's xpath
    lcr = wlc + lcr

    //add parent reference xpath
    if (pRef != null)
      lcr = pRef + lcr

    if (this.exploreJQuerySelector) {
      lcr = JQUERY_SELECTOR + lcr.trim()
    } else {
      //make sure the xpath starts with "//"
      if (lcr != null && (!lcr.startsWith("//")) && (!lcr.startsWith(JQUERY_SELECTOR))) {
        lcr = "/" + lcr
      }
    }
    
    return lcr
  }

  public Object parseSeleniumJSONReturnValue(String out){
    if(out.startsWith("OK,")){
      out = out.substring(3);
    } else {
      return null;
    }

    return reader.read(out);
  }

  /**
   * Pass in a jquery selector, and a list of DOM properties to gather from each selected element.
   * returns an arraylist of hashmaps with the requested properties as 'key->value'
   */

  def ArrayList getSelectorProperties(String jqSelector, List<String> props) {
    JSONArray arr = new JSONArray();
    arr.addAll(props);
    String json = arr.toString();
    String out = extension.getSelectorProperties(jqSelector, json);
    return (ArrayList) parseSeleniumJSONReturnValue(out);
  }

  /**
   * pass in a jquery selector, and get back an arraylist of inner text of all elements selected,
   * one string per element
   */

  def ArrayList getSelectorText(String jqSelector) {
    String out = extension.getSelectorText(jqSelector);
    return (ArrayList) parseSeleniumJSONReturnValue(out);
  }

  /**
   * pass in a jquery selector, and a javascript function as a string. the function will be called within
   * the context of the wrapped set, ie, the wrapped set will be 'this' in the function.
   * the function must return JSON
   *
   * NOTE: the function CAN NOT have any comments or you will get a syntax error inside of selenium core.
   * NOTE: each line of the function must be terminated with a semicolin ';'
   */

  def Object getSelectorFunctionCall(String jqSelector, String fn) {
      JSONArray arr = new JSONArray();
      fn = "function(){" + fn + "}";
      arr.add(fn.replaceAll("[\n\r]", ""));
      String json = arr.toString();
      String out = extension.getSelectorFunctionCall(jqSelector, json);

      return parseSeleniumJSONReturnValue(out);
  }

  public void useJQuerySelector(){
    this.exploreJQuerySelector = true
    locatorProcessor.useJQuerySelector()
  }

  public void disableJQuerySelector(){
    this.exploreJQuerySelector = false
    locatorProcessor.disableJQuerySelector()
  }

  public void useDefaultXPathLibrary() {
    accessor.useXpathLibrary(DEFAULT_XPATH)
  }

  public void useJavascriptXPathLibrary() {
    accessor.useXpathLibrary(JAVASCRIPT_XPATH)
  }

  public void useAjaxsltXPathLibrary() {
    accessor.useXpathLibrary(AJAXSLT_XPATH)
  }

  //uid should use the format table2[2][3] for Table or list[2] for List
  def getUiElement(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = ui.walkTo(context, uid)

    return obj
  }

  def UiObject walkToWithException(WorkflowContext context, String uid) {
    UiObject obj = ui.walkTo(context, uid)
    if (obj != null)
      return obj

    throw new UiObjectNotFoundException("${ERROR_MESSAGE} ${uid}")
  }

  def click(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.click() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.click(locator, events)
    }
  }

  def doubleClick(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.doubleClick(locator, events)
    }
  }

  def clickAt(String uid, String coordination) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clickAt(locator, coordination, events)
    }
  }

  def check(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.check() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.check(locator, events)
    }
  }

  def uncheck(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.uncheck(locator, events)
    }
  }

  def type(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.type(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.type(locator, input, events)
    }
  }

  def keyType(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.keyType(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.keyType(locator, input, events)
    }
  }

  def typeAndReturn(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.typeAndReturn(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.typeAndReturn(locator, input, events)
    }
  }

  def clearText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.clearText() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clearText(locator, events)
    }
  }

  def select(String uid, String target) {
    selectByLabel(uid, target)
  }

  def selectByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def selectByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def addSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def addSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def removeSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeAllSelections(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.removeAllSelections() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeAllSelections(locator)
    }
  }

  String[] getSelectOptions(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectOptions() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectOptions(locator)
    }
  }

  String[] getSelectedLabels(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabels() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabels(locator)
    }
  }

  String getSelectedLabel(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabel() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabel(locator)
    }
  }

  String[] getSelectedValues(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValues() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValues(locator)
    }
  }

  String getSelectedValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValue(locator)
    }
  }

  String[] getSelectedIndexes(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndexes() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndexes(locator)
    }
  }

  String getSelectedIndex(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndex() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndex(locator)
    }
  }

  String[] getSelectedIds(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIds() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIds(locator)
    }
  }

  String getSelectedId(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedId() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedId(locator)
    }
  }

  boolean isSomethingSelected(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)

    return obj.isSomethingSelected() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isSomethingSelected(locator)
    }
  }

  String waitForText(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
      String locator = locatorMapping(context, loc)
      accessor.waitForText(locator, tmo)
    }
  }

  int getListSize(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    org.tellurium.object.List obj = (org.tellurium.object.List) walkToWithException(context, uid)
    return obj.getListSize() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  boolean isElementPresent(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)
    return obj.isElementPresent() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isElementPresent(locator)
    }
  }

  boolean isVisible(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)
    return obj.isVisible() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isVisible(locator)
    }
  }

  boolean isChecked(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)
    return obj.isChecked() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isChecked(locator)
    }
  }

  def boolean isEnabled(String uid) {
    return !isDisabled(uid);
  }

  boolean waitForElementPresent(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(locator, timeout)
    }
  }

  boolean waitForElementPresent(String uid, int timeout, int step) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout, step) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(locator, timeout, step)
    }
  }

  boolean waitForCondition(String script, int timeoutInMilliSecond) {
    accessor.waitForCondition(script, Integer.toString(timeoutInMilliSecond))
  }

  String getText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getText() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getText(locator)
    }
  }

  String getValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getValue(locator)
    }
  }

  def pause(int milliseconds) {
    Helper.pause(milliseconds)
  }

  String getLink(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getLink() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageSource(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getImageSource() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageAlt(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageTitle(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  def submit(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.submit() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.submit(locator)
    }
  }

  boolean isEditable(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    return walkToWithException(context, uid)?.isEditable() {loc ->
      String locator = locatorMapping(context, loc)
      return accessor.isEditable(locator)
    }
  }

  void waitForPageToLoad(int timeout) {
    accessor.waitForPageToLoad(Integer.toString(timeout))
  }

  String getEval(String script) {
    return accessor.getEval(script)
  }

  def mouseOver(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseOver() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOver(locator)
    }
  }

  def mouseOut(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseOut() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOut(locator)
    }
  }

  def dragAndDrop(String uid, String movementsString) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.dragAndDrop(locator, movementsString)
    }
  }

  def dragAndDropTo(String sourceUid, String targetUid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def src = walkToWithException(context, sourceUid)

    WorkflowContext ncontext = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    def target = walkToWithException(ncontext, targetUid)

    if (src != null && target != null) {
      String srcLocator = locatorMapping(context, src.locator)
      String targetLocator = locatorMapping(ncontext, target.locator)
      eventHandler.dragAndDropToObject(srcLocator, targetLocator)
    }
  }

  def mouseDown(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseDown() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDown(locator)
    }
  }

  def mouseDownRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseDownRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRight(locator)
    }
  }

  def mouseDownAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseDownAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownAt(locator, coordinate)
    }
  }

  def mouseDownRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRightAt(locator, coordinate)
    }
  }

  def mouseUp(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseUp() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUp(locator)
    }
  }

  def mouseUpRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseUpRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRight(locator)
    }
  }

  def mouseUpRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRightAt(locator, coordinate)
    }
  }

  def mouseMove(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseMove() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMove(locator)
    }
  }

  def mouseMoveAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.mouseMoveAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMoveAt(locator, coordinate)
    }
  }

  Number getXpathCount(String xpath) {
    return accessor.getXpathCount(xpath)
  }

  Number getJQuerySelectorCount(String jQuerySelector){
    String jq = jQuerySelector
    if(!jq.startsWith(JQUERY_SELECTOR)){
      jq=JQUERY_SELECTOR + jQuerySelector
    }

    return extension.getJQuerySelectorCount(jq)
  }

  Number getLocatorCount(String locator){
    if(this.exploreJQuerySelector)
      return getJQuerySelectorCount(locator)

    return getXpathCount(locator)
  }

  String getXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getXPath() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
    if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
      locator = "/" + locator
    }

    return locator
  }

  String getSelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    walkToWithException(context, uid)?.getSelector() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
    locator = JQUERY_SELECTOR + locator.trim()

    return locator
  }

  String getLocator(String uid){
    if(this.exploreJQuerySelector){
      return getSelector(uid)
    }

    return getXPath(uid)
  }

  String[] getCSS(String uid, String cssName){
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

    walkToWithException(context, uid)?.getCSS(cssName) {loc ->
      String locator = locatorMapping(context, loc)
      String out = extension.getCSS(locator, cssName)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  //This only works for jQuery selector
  String[] getAllTableCellText(String uid){
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    return walkToWithException(context, uid)?.getAllTableCellText(){loc, cell ->
      String locator = locatorMapping(context, loc)
      locator = locator + cell
      String out = extension.getAllText(locator)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  //This only works for jQuery selector and Standard Table
  String[] getAllTableCellTextForTbody(String uid, int index){
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    return walkToWithException(context, uid)?.getAllTableCellTextForTbody(index){loc, cell ->
      String locator = locatorMapping(context, loc)
      locator = locator + cell
      String out = extension.getAllText(locator)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  int getTableHeaderColumnNumByXPath(String uid) {
    //force to use xpath
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableHeaderColumnNumByXPath {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableFootColumnNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableFootColumnNumByXPath {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxRowNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxColumnNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxRowNumForTbodyByXPath(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxColumnNumForTbodyByXPath(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxTbodyNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxTbodyNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }
  int getTableHeaderColumnNumBySelector(String uid) {
    //force to use jQuery selector
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    def obj = walkToWithException(context, uid)

    return obj.getTableHeaderColumnNumBySelector {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableFootColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    def obj = walkToWithException(context, uid)

    return obj.getTableFootColumnNumBySelector {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxRowNumBySelector() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxColumnNumBySelector() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxRowNumForTbodyBySelector(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxColumnNumForTbodyBySelector(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxTbodyNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(true)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxTbodyNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableHeaderColumnNum(String uid) {
      if(this.exploreJQuerySelector)
        return getTableHeaderColumnNumBySelector(uid)

      return getTableHeaderColumnNumByXPath(uid)
  }

  int getTableFootColumnNum(String uid) {
    if(this.exploreJQuerySelector)
      return getTableFootColumnNumBySelector(uid)

    return getTableFootColumnNumByXPath(uid)
  }

  int getTableMaxRowNum(String uid) {
    if(this.exploreJQuerySelector)
      return getTableMaxRowNumBySelector(uid)

    return getTableMaxRowNumByXPath(uid)
  }

  int getTableMaxColumnNum(String uid) {
    if(this.exploreJQuerySelector)
       return getTableMaxColumnNumBySelector(uid)

    return getTableMaxColumnNumByXPath(uid)
  }

  int getTableMaxRowNumForTbody(String uid, int ntbody) {
    if(this.exploreJQuerySelector)
      return getTableMaxRowNumForTbodyBySelector(uid, ntbody)

    return getTableMaxRowNumForTbodyByXPath(uid, ntbody)
  }

  int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    if(this.exploreJQuerySelector)
      return getTableMaxColumnNumForTbodyBySelector(uid, ntbody)

    return getTableMaxColumnNumForTbodyByXPath(uid, ntbody)
  }

  int getTableMaxTbodyNum(String uid) {
    if(this.exploreJQuerySelector)
      return getTableMaxTbodyNumBySelector(uid)

    return getTableMaxTbodyNumByXPath(uid)
  }

   def boolean isDisabledByXPath(String uid) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      return walkToWithException(context, uid).isDisabled() {loc ->
        String locator = locatorMapping(context, loc) + "/self::node()[@disabled]"
        accessor.isElementPresent(locator)
      }
    }

    def boolean isDisabledBySelector(String uid) {
      WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)

      return walkToWithException(context, uid).isDisabled() {loc ->
        String locator = locatorMapping(context, loc)
        extension.isDisabled(locator)
      }
    }

    def boolean isDisabled(String uid){
      if(this.exploreJQuerySelector)
        return isDisabledBySelector(uid)

      return isDisabledByXPath(uid)
    }

  def getAttribute(String uid, String attribute) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
      String locator = locatorMapping(context, loc)
      if(this.exploreJQuerySelector){
        return accessor.getAttribute(locator + "@${attr}")
      }else{
        return accessor.getAttribute(locator + "/self::node()@${attr}")
      }
    }
  }

  def hasCssClass(String uid, String cssClass) {
    WorkflowContext context = WorkflowContext.getContextByStrategy(this.exploreJQuerySelector)
    String[] strings = walkToWithException(context, uid)?.hasCssClass() {loc, classAttr ->
      String locator = locatorMapping(context, loc)
      String clazz
      if(this.exploreJQuerySelector){
        clazz = accessor.getAttribute(locator + "@${classAttr}")
      }else{
        clazz = accessor.getAttribute(locator + "/self::node()@${classAttr}")
      }
      if(clazz != null && clazz.trim().length() > 0){
        return clazz.split(" ")
      }
      return null
    }
    if (strings?.length) {
      for (i in 0..strings?.length) {
        if (cssClass.equalsIgnoreCase(strings[i])) {
          return true
        }
      }
    }
    return false
  }

  //walkTo through the object tree to until the Ui Object is found by the UID
  public UiObject walkTo(WorkflowContext context, UiID uiid) {
    //if not child listed, return itself
    if (uiid.size() < 1)
      return this

    //otherwise,
    //check if the uid is the same as the uiid
    //if it is, return itself
    //otherwise, return null since widget is atomic and should not any other ui object

    String nextid = (String) uiid.pop()
    if (nextid.equalsIgnoreCase(this.uid)) {
      return this
    } else {
      println("Cannot find UI Object ${nextid} in ${this.uid}")
      return null
    }
  }
}