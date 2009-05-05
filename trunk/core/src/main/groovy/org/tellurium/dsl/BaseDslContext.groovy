package org.tellurium.dsl

import org.json.simple.JSONObject
import org.json.simple.JSONArray
import org.stringtree.json.JSONReader
import org.tellurium.access.Accessor
import org.tellurium.dsl.UiDslParser
import org.tellurium.dsl.WorkflowContext
import org.tellurium.event.EventHandler
import org.tellurium.exception.UiObjectNotFoundException
import org.tellurium.extend.Extension
import org.tellurium.locator.LocatorProcessor
import org.tellurium.object.StandardTable
import org.tellurium.object.UiObject
import org.tellurium.util.Helper
import java.util.List
import org.tellurium.locator.JQueryOptimizer
import org.tellurium.locator.MetaCmd

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 * 
 */
abstract class BaseDslContext {

  //later on, may need to refactor it to use resource file so that we can show message for different localities
  protected static final String ERROR_MESSAGE = "Cannot find UI Object"
  protected static final String JQUERY_SELECTOR = "jquery="
  protected static final String JQUERY_SELECTOR_CACHE = "jquerycache="
  protected static final String DEFAULT_XPATH = "default"
  //protected static final String JAVASCRIPT_XPATH = "javascript"
  protected static final String JAVASCRIPT_XPATH = "javascript-xpath"  
  protected static final String AJAXSLT_XPATH = "ajaxslt"
  protected static final String LOCATOR = "locator"
  protected static final String OPTIMIZED_LOCATOR = "optimized"

  //flag to decide whether we should use jQuery Selector
  protected boolean exploreJQuerySelector = false
  protected JQueryOptimizer optimizer = new JQueryOptimizer()

  //flag to decide whether we should cache jQuery selector
  protected boolean exploreSelectorCache = false

  UiDslParser ui = new UiDslParser()

  //decoupling eventhandler, locateProcessor, and accessor from UI objects
  //and let DslContext to handle all of them directly. This will also make
  //the framework reconfiguration much easier.
  EventHandler eventHandler = new EventHandler()
  Accessor accessor = new Accessor()
  LocatorProcessor locatorProcessor = new LocatorProcessor()
  Extension extension = new Extension()
  
  abstract protected String locatorMapping(WorkflowContext context, loc)
  abstract protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc)

  protected String postProcessSelector(WorkflowContext context, String jqs) {
    String locator = jqs

    String optimized = optimizer.optimize(jqs)

    if (context.isUseSelectorCache()) {
      JSONObject obj = new JSONObject()
      //meta command shoud not be null for locators
      MetaCmd metaCmd = context.extraMetaCmd()
      obj.put(LOCATOR, locator)
      obj.put(OPTIMIZED_LOCATOR, optimized)
      
      obj.put(MetaCmd.UID, metaCmd.getProperty(MetaCmd.UID))
      obj.put(MetaCmd.CACHEABLE, metaCmd.getProperty(MetaCmd.CACHEABLE))
      obj.put(MetaCmd.UNIQUE, metaCmd.getProperty(MetaCmd.UNIQUE))

      String jsonjqs = obj.toString()

      return JQUERY_SELECTOR_CACHE + jsonjqs
    } 
      return JQUERY_SELECTOR + optimized
  }

  private JSONReader reader = new JSONReader()

  public Object parseSeleniumJSONReturnValue(String out){
    if(out.startsWith("OK,")){
      out = out.substring(3);
    } else {
      return null;
    }

    return reader.read(out);
  }

  public void enableSelectorCache(){
      this.exploreSelectorCache = true
      extension.enableSelectorCache()
  }

  public boolean disableSelectorCache(){
      this.exploreSelectorCache = false
      extension.disableSelectorCache()
  }

  public boolean cleanSelectorCache(){
      extension.cleanSelectorCache()
  }

  public boolean getSelectorCacheState(){
      return extension.getCacheState()
  }

  public void setCacheMaxSize(int size){
      extension.setCacheMaxSize(size)
  }

  public int getCacheSize(){
      return extension.getCacheSize().intValue()
  }

  public int getCacheMaxSize(){
      return extension.getCacheMaxSize().intValue()
  }

  public Map<String, Long> getCacheUsage() {
    String out = extension.getCacheUsage();
    ArrayList list = (ArrayList) parseSeleniumJSONReturnValue(out)
    Map<String, Long> usages = new HashMap<String, Long>()
    list.each {Map elem ->
      elem.each {key, value ->
        usages.put(key, value)
      }
    }

    return usages
  }

  public void useDiscardNewCachePolicy(){
    extension.useDiscardNewCachePolicy()
  }

  public void useDiscardOldCachePolicy(){
    extension.useDiscardOldCachePolicy()
  }

  public void useDiscardLeastUsedCachePolicy(){
    extension.useDiscardLeastUsedCachePolicy()
  }

  public void useDiscardInvalidCachePolicy(){
    extension.useDiscardInvalidCachePolicy()
  }

  public String getCurrentCachePolicy(){
    return extension.getCurrentCachePolicy()
  }

  public void useJQuerySelector(){
    this.exploreJQuerySelector = true
  }

  public void disableJQuerySelector(){
    this.exploreJQuerySelector = false
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

  public void registerNamespace(String prefix, String namespace){
    extension.addNamespace(prefix, namespace)
  }

  public String getNamespace(String prefix){
    return extension.getNamespace(prefix)
  }

  def customUiCall(String uid, String method, Object[] args){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    return walkToWithException(context, uid).customMethod(){ loc ->
      String locator = locatorMapping(context, loc)
      Object[] list = [locator, args].flatten()    
      return extension.invokeMethod(method, list)
    }
  }

  def customDirectCall(String method, Object[] args){
    return extension.invokeMethod(method, args)
  }

  //uid should use the format table2[2][3] for Table or list[2] for List
  def getUiElement(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = ui.walkTo(context, uid)

    return obj
  }

  def UiObject walkToWithException(WorkflowContext context, String uid) {
    UiObject obj = ui.walkTo(context, uid)
    if (obj != null){
      context.attachMetaCmd(uid, obj.amICacheable(), true)
      
      return obj
    }

    throw new UiObjectNotFoundException("${ERROR_MESSAGE} ${uid}")
  }

  def click(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.click() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.click(locator, events)
    }
  }

  def doubleClick(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.doubleClick(locator, events)
    }
  }

  def clickAt(String uid, String coordination) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clickAt(locator, coordination, events)
    }
  }

  def check(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.check() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.check(locator, events)
    }
  }

  def uncheck(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.uncheck(locator, events)
    }
  }

  def type(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.type(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.type(locator, input, events)
    }
  }

  def keyType(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.keyType(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.keyType(locator, input, events)
    }
  }

  def typeAndReturn(String uid, String input) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.typeAndReturn(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.typeAndReturn(locator, input, events)
    }
  }

  def clearText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.clearText() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clearText(locator, events)
    }
  }

  def select(String uid, String target) {
    selectByLabel(uid, target)
  }

  def selectByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def selectByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def addSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def addSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def removeSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeAllSelections(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.removeAllSelections() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeAllSelections(locator)
    }
  }

  String[] getSelectOptions(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectOptions() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectOptions(locator)
    }
  }

  String[] getSelectedLabels(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabels() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabels(locator)
    }
  }

  String getSelectedLabel(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabel() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabel(locator)
    }
  }

  String[] getSelectedValues(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValues() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValues(locator)
    }
  }

  String getSelectedValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValue(locator)
    }
  }

  String[] getSelectedIndexes(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndexes() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndexes(locator)
    }
  }

  String getSelectedIndex(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndex() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndex(locator)
    }
  }

  String[] getSelectedIds(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIds() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIds(locator)
    }
  }

  String getSelectedId(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.getSelectedId() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedId(locator)
    }
  }

  boolean isSomethingSelected(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)

    return obj.isSomethingSelected() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isSomethingSelected(locator)
    }
  }

  String waitForText(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
      String locator = locatorMapping(context, loc)
      accessor.waitForText(locator, tmo)
    }
  }

  //TODO: use jQuery Selector to optimize the list operations
  int getListSize(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    org.tellurium.object.List obj = (org.tellurium.object.List) walkToWithException(context, uid)
    return obj.getListSize() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  boolean isElementPresent(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    return obj.isElementPresent() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isElementPresent(locator)
    }
  }

  boolean isVisible(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    return obj.isVisible() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isVisible(locator)
    }
  }

  boolean isChecked(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(locator, timeout)
    }
  }

  boolean waitForElementPresent(String uid, int timeout, int step) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getText() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getText(locator)
    }
  }

  String getValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getValue(locator)
    }
  }

  def pause(int milliseconds) {
    Helper.pause(milliseconds)
  }

  String getLink(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getLink() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageSource(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getImageSource() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageAlt(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageTitle(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  def submit(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    walkToWithException(context, uid)?.submit() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.submit(locator)
    }
  }

  boolean isEditable(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    return walkToWithException(context, uid)?.isEditable() {loc ->
      String locator = locatorMapping(context, loc)
      return accessor.isEditable(locator)
    }
  }

  String getEval(String script) {
    return accessor.getEval(script)
  }

  def mouseOver(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseOver() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOver(locator)
    }
  }

  def mouseOut(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseOut() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOut(locator)
    }
  }

  def dragAndDrop(String uid, String movementsString) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.dragAndDrop(locator, movementsString)
    }
  }

  def dragAndDropTo(String sourceUid, String targetUid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def src = walkToWithException(context, sourceUid)

    WorkflowContext ncontext = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def target = walkToWithException(ncontext, targetUid)

    if (src != null && target != null) {
      String srcLocator = locatorMapping(context, src.locator)
      String targetLocator = locatorMapping(ncontext, target.locator)
      eventHandler.dragAndDropToObject(srcLocator, targetLocator)
    }
  }

  def mouseDown(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseDown() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDown(locator)
    }
  }

  def mouseDownRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseDownRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRight(locator)
    }
  }

  def mouseDownAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseDownAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownAt(locator, coordinate)
    }
  }

  def mouseDownRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRightAt(locator, coordinate)
    }
  }

  def mouseUp(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseUp() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUp(locator)
    }
  }

  def mouseUpRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseUpRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRight(locator)
    }
  }

  def mouseUpRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRightAt(locator, coordinate)
    }
  }

  def mouseMove(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseMove() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMove(locator)
    }
  }

  def mouseMoveAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.mouseMoveAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMoveAt(locator, coordinate)
    }
  }

  Number getXpathCount(String xpath) {
    return accessor.getXpathCount(xpath)
  }

  Number getJQuerySelectorCount(String jQuerySelector){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, false)
    //do not cache any selectors for counting
    context.updateCacheableForMetaCmd(false);
    String jq = postProcessSelector(context, jQuerySelector.trim())
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
//    if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
    if (locator != null && (!locator.startsWith("//"))){
      locator = "/" + locator
    }

    return locator
  }

  String getSelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    walkToWithException(context, uid)?.getSelector() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
    locator = optimizer.optimize(locator.trim())
    
    return JQUERY_SELECTOR + locator
  }

  String getLocator(String uid){
    if(this.exploreJQuerySelector){
      return getSelector(uid)
    }

    return getXPath(uid)
  }

  String[] getCSS(String uid, String cssName){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

    walkToWithException(context, uid)?.getCSS(cssName) {loc ->
      String locator = locatorMapping(context, loc)
      String out = extension.getCSS(locator, cssName)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  //This only works for jQuery selector
  String[] getAllTableCellText(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    return walkToWithException(context, uid)?.getAllTableCellText(){loc, cell ->
      //for bulk data, the selector will not return a unique element
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)
      String locator = locatorMappingWithOption(context, loc, cell)
//      locator = locator + cell
      String out = extension.getAllText(locator)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  //This only works for jQuery selector and Standard Table
  String[] getAllTableCellTextForTbody(String uid, int index){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    return walkToWithException(context, uid)?.getAllTableCellTextForTbody(index){loc, cell ->
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)
      String locator = locatorMappingWithOption(context, loc, cell)
//      locator = locator + cell
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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableHeaderColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableFootColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableFootColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableMaxRowNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxRowNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableMaxColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxRowNumForTbodyBySelector(ntbody) {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxColumnNumForTbodyBySelector(ntbody) {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
    }
  }

  int getTableMaxTbodyNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreSelectorCache)
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)
    
    return obj.getTableMaxTbodyNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())
      return extension.getJQuerySelectorCount(locator)
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
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)

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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
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

  public void dump(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    if(obj != null){
      context.setNewUid(uid)
      obj.traverse(context)
      ArrayList list = context.getUidList()

      println("\nDump locator information for " + uid)
      println("-------------------------------------------------------")
      list.each {String key->
        String loc = getLocator(key)
        context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
        walkToWithException(context, key)

        if(this.exploreJQuerySelector){
          loc = this.postProcessSelector(context, loc)
        }
        println("${key}: ${loc}")          
      }
      println("-------------------------------------------------------\n")
    }
  }
}