package org.tellurium.dsl

import org.json.simple.JSONArray
import org.stringtree.json.JSONReader
import org.tellurium.access.Accessor
import org.tellurium.dsl.UiDslParser
import org.tellurium.dsl.WorkflowContext
import org.tellurium.event.EventHandler
import org.tellurium.exception.UiObjectNotFoundException
import org.tellurium.extend.Extension
import org.tellurium.locator.LocatorProcessor
import org.tellurium.object.List
import org.tellurium.object.StandardTable
import org.tellurium.object.UiObject
import org.tellurium.util.Helper

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

  UiDslParser ui = new UiDslParser()

  //decoupling eventhandler, locateProcessor, and accessor from UI objects
  //and let DslContext to handle all of them directly. This will also make
  //the framework reconfiguration much easier.
  EventHandler eventHandler = new EventHandler()
  Accessor accessor = new Accessor()
  LocatorProcessor locatorProcessor = new LocatorProcessor()
  Extension extension = new Extension()
  
  abstract protected String locatorMapping(WorkflowContext context, loc)

/*
  private JSONReader reader = new JSONReader()

  private Object parseSeleniumJSONReturnValue(String out){
    if(out.startsWith("OK,")){
      out = out.substring(3);
    } else {
      return null;
    }

    return reader.read(out);
  }
*/

  /**
   * Pass in a jquery selector, and a list of DOM properties to gather from each selected element.
   * returns an arraylist of hashmaps with the requested properties as 'key->value'
   */

/*
  def ArrayList getSelectorProperties(String jqSelector, java.util.List<String> props) {
    JSONArray arr = new JSONArray();
    arr.addAll(props);
    String json = arr.toString();
    String out = extension.getSelectorProperties(jqSelector, json);
    return (ArrayList) parseSeleniumJSONReturnValue(out);
  }
*/

  /**
   * pass in a jquery selector, and get back an arraylist of inner text of all elements selected,
   * one string per element
   */

/*
  def ArrayList getSelectorText(String jqSelector) {
    String out = extension.getSelectorText(jqSelector);
    return (ArrayList) parseSeleniumJSONReturnValue(out);
  }*/

  /**
   * pass in a jquery selector, and a javascript function as a string. the function will be called within
   * the context of the wrapped set, ie, the wrapped set will be 'this' in the function.
   * the function must return JSON
   *
   * NOTE: the function CAN NOT have any comments or you will get a syntax error inside of selenium core.
   * NOTE: each line of the function must be ended with a semicolin ';'
   */

/*
  def Object getSelectorFunctionCall(String jqSelector, String fn) {
      JSONArray arr = new JSONArray();
      fn = "function(){" + fn + "}";
      arr.add(fn.replaceAll("[\n\r]", ""));
      String json = arr.toString();
      String out = extension.getSelectorFunctionCall(jqSelector, json);

      return parseSeleniumJSONReturnValue(out);
  }
*/

  //uid should use the format table2[2][3] for Table or list[2] for List
  def getUiElement(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = ui.walkTo(context, uid)

    return obj
  }

  def UiObject walkToWithException(WorkflowContext context, String uid) {
    UiObject obj = ui.walkTo(context, uid)
    if (obj != null)
      return obj

    throw new UiObjectNotFoundException("${ERROR_MESSAGE} ${uid}")
  }

  def mouseOver(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseOver() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOver(locator)
    }
  }

  def mouseOut(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseOut() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOut(locator)
    }
  }

  def dragAndDrop(String uid, String movementsString) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.dragAndDrop(locator, movementsString)
    }
  }

  def dragAndDropTo(String sourceUid, String targetUid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def src = walkToWithException(context, sourceUid)

    WorkflowContext ncontext = WorkflowContext.getDefaultContext()
    def target = walkToWithException(ncontext, targetUid)

    if (src != null && target != null) {
      String srcLocator = locatorMapping(context, src.locator)
      String targetLocator = locatorMapping(ncontext, target.locator)
      eventHandler.dragAndDropToObject(srcLocator, targetLocator)
    }
  }

  def click(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.click() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.click(locator, events)
    }
  }

  def doubleClick(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.doubleClick(locator, events)
    }
  }

  def clickAt(String uid, String coordination) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clickAt(locator, coordination, events)
    }
  }

  def check(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.check() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.check(locator, events)
    }
  }

  def uncheck(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.uncheck(locator, events)
    }
  }

  def type(String uid, String input) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.type(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.type(locator, input, events)
    }
  }

  def keyType(String uid, String input) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.keyType(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.keyType(locator, input, events)
    }
  }

  def typeAndReturn(String uid, String input) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.typeAndReturn(input) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.typeAndReturn(locator, input, events)
    }
  }

  def clearText(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.clearText() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clearText(locator, events)
    }
  }

  def select(String uid, String target) {
    selectByLabel(uid, target)
  }

  def selectByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def selectByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(locator, optloc, events)
    }
  }

  def addSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def addSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(locator, optloc)
    }
  }

  def removeSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(locator, optloc)
    }
  }

  def removeAllSelections(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.removeAllSelections() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeAllSelections(locator)
    }
  }

  String[] getSelectOptions(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectOptions() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectOptions(locator)
    }
  }

  String[] getSelectedLabels(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabels() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabels(locator)
    }
  }

  String getSelectedLabel(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabel() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabel(locator)
    }
  }

  String[] getSelectedValues(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValues() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValues(locator)
    }
  }

  String getSelectedValue(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValue(locator)
    }
  }

  String[] getSelectedIndexes(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndexes() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndexes(locator)
    }
  }

  String getSelectedIndex(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndex() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndex(locator)
    }
  }

  String[] getSelectedIds(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIds() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIds(locator)
    }
  }

  String getSelectedId(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getSelectedId() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedId(locator)
    }
  }

  boolean isSomethingSelected(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.isSomethingSelected() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isSomethingSelected(locator)
    }
  }

  String waitForText(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
      String locator = locatorMapping(context, loc)
      accessor.waitForText(locator, tmo)
    }
  }

  int getTableHeaderColumnNum(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableHeaderColumnNum {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableFootColumnNum(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableFootColumnNum {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNum(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxRowNum() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNum(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxColumnNum() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumForTbody(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxRowNumForTbody(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxColumnNumForTbody(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxTbodyNum(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxTbodyNum() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getListSize(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    List obj = (List) walkToWithException(context, uid)
    return obj.getListSize() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  boolean isElementPresent(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    return obj.isElementPresent() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isElementPresent(locator)
    }
  }

  boolean isVisible(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    return obj.isVisible() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isVisible(locator)
    }
  }

  boolean isChecked(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    return obj.isChecked() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isChecked(locator)
    }
  }

  def isDisabled(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    return walkToWithException(context, uid).isDisabled() {loc ->
//                String locator = locatorMapping(context, loc)
//                accessor.isDisabled(locator)
      String locator = locatorMapping(context, loc) + "/self::node()[@disabled]"
      accessor.isElementPresent(locator)
    }
  }

  def isEnabled(String uid) {
    return !isDisabled(uid);
  }

  boolean waitForElementPresent(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(locator, timeout)
    }
  }

  boolean waitForElementPresent(String uid, int timeout, int step) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
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
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getText() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getText(locator)
    }
  }

  String getValue(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getValue(locator)
    }
  }

  def pause(int milliseconds) {
    Helper.pause(milliseconds)
  }

  String getLink(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getLink() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageSource(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getImageSource() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageAlt(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  String getImageTitle(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  def getAttribute(String uid, String attribute) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(locator + attr)
    }
  }

  def hasCssClass(String uid, String cssClass) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    String[] strings = walkToWithException(context, uid)?.hasCssClass() {loc, classAttr ->
      String locator = locatorMapping(context, loc)
      ((String) accessor.getAttribute(locator + classAttr))?.split(" ")
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

  def submit(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.submit() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.submit(locator)
    }
  }

  boolean isEditable(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    return walkToWithException(context, uid)?.isEditable() {loc ->
      String locator = locatorMapping(context, loc)
      return accessor.isEditable(locator)
    }
  }

  void waitForPageToLoad(int timeout) {
    accessor.waitForPageToLoad(Integer.toString(timeout))
  }

  Number getXpathCount(String xpath) {
    return accessor.getXpathCount(xpath)
  }

  String getEval(String script) {
    return accessor.getEval(script)
  }

  public void useDefaultXPathLibrary() {
    accessor.useXpathLibrary("default")
  }

  public void useJavascriptXPathLibrary() {
    accessor.useXpathLibrary("javascript")
  }

  public void useAjaxsltXPathLibrary() {
    accessor.useXpathLibrary("ajaxslt")
  }

  def mouseDown(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseDown() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDown(locator)
    }
  }

  def mouseDownRight(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseDownRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRight(locator)
    }
  }

  def mouseDownAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseDownAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownAt(locator, coordinate)
    }
  }

  def mouseDownRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRightAt(locator, coordinate)
    }
  }

  def mouseUp(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseUp() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUp(locator)
    }
  }

  def mouseUpRight(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseUpRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRight(locator)
    }
  }

  def mouseUpRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRightAt(locator, coordinate)
    }
  }

  def mouseMove(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseMove() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMove(locator)
    }
  }

  def mouseMoveAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    walkToWithException(context, uid)?.mouseMoveAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMoveAt(locator, coordinate)
    }
  }
}