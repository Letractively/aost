package org.telluriumsource.ui.widget

import org.telluriumsource.dsl.BaseDslContext
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.ui.object.UiObject

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 31, 2010
 * 
 */
class WidgetDslContext extends BaseDslContext {
  def locator;

  def WidgetDslContext() {
  }

  def WidgetDslContext(locator) {
    this.locator = locator;
  }

//the reference xpath for widget's parent
  private String pRef;

  public void updateParentRef(String ref) {
     this.pRef = ref

  }

  protected String locatorMapping(WorkflowContext context, loc) {
    return locatorMappingWithOption(context, loc, null)
  }

  protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc) {
    //get ui object's locator
    String lcr = locatorProcessor.locate(context, loc)

    //widget's locator
    String wlc = locatorProcessor.locate(context, this.locator)

    //get the reference locator all the way to the ui object
    if (context.getReferenceLocator() != null) {
      context.appendReferenceLocator(lcr)
      lcr = context.getReferenceLocator()
    }

    if(optLoc != null)
        lcr = lcr + optLoc

    //append the object's locator to widget's locator
    lcr = wlc + lcr

    //add parent reference xpath
    if (pRef != null)
      lcr = pRef + lcr
    if(context.isUseCssSelector()){
//      lcr = optimizer.optimize(JQUERY_SELECTOR + lcr.trim())
      lcr = postProcessSelector(context, lcr.trim())
    } else {
      //make sure the xpath starts with "//"
      if (lcr != null && (!lcr.startsWith("//"))) {
        lcr = "/" + lcr
      }
    }

    return lcr
  }

  def EngineState getEngineState() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void helpTest() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void noTest() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableClosestMatch() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableClosestMatch() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableCssSelector() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableCssSelector() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void cleanCache() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean getCacheState() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setCacheMaxSize(int size) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getCacheSize() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getCacheMaxSize() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getCacheUsage() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def CacheUsageResponse getCacheUsageResponse() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useDiscardNewCachePolicy() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useDiscardOldCachePolicy() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useDiscardLeastUsedCachePolicy() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useDiscardInvalidCachePolicy() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getCurrentCachePolicy() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useDefaultXPathLibrary() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useJavascriptXPathLibrary() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useAjaxsltXPathLibrary() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void registerNamespace(String prefix, String namespace) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getNamespace(String prefix) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void pause(int milliseconds) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableTrace() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableTrace() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void showTrace() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setEnvironment(String name, Object value) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object getEnvironment(String name) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableMacroCmd() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableMacroCmd() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getMaxMacroCmd() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void allowNativeXpath(boolean allow) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void addScript(String scriptContent, String scriptTagId) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void removeScript(String scriptTagId) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableEngineLog() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableEngineLog() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void useEngineLog(boolean isUse) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void enableTelluriumEngine() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void disableTelluriumEngine() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object customUiCall(String uid, String method, Object[] args) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object customDirectCall(String method, Object[] args) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void triggerEventOn(String uid, String event) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object getUiElement(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void click(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void doubleClick(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void clickAt(String uid, String coordination) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void check(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void uncheck(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void type(String uid, Object input) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void keyPress(String uid, String key) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void keyDown(String uid, String key) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void keyUp(String uid, String key) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void focus(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void fireEvent(String uid, String eventName) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void keyType(String uid, Object input) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void typeAndReturn(String uid, Object input) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void altKeyUp() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void altKeyDown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void ctrlKeyUp() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void ctrlKeyDown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void shiftKeyUp() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void shiftKeyDown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void metaKeyUp() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void metaKeyDown() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void clearText(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void select(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectByLabel(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectByValue(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectByIndex(String uid, int target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectById(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void addSelectionByLabel(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void addSelectionByValue(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void removeSelectionByLabel(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void removeSelectionByValue(String uid, String target) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void removeAllSelections(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectOptions(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectValues(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectedLabels(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getSelectedLabel(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectedValues(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getSelectedValue(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectedIndexes(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getSelectedIndex(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getSelectedIds(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getSelectedId(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isSomethingSelected(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String waitForText(String uid, int timeout) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isElementPresent(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isVisible(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isChecked(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isEnabled(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean waitForElementPresent(String uid, int timeout) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean waitForElementPresent(String uid, int timeout, int step) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean waitForCondition(String script, int timeoutInMilliSecond) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getText(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getValue(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getLink(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getImageSource(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getImageAlt(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getImageTitle(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void submit(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isEditable(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getEval(String script) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseOver(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseOut(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void dragAndDrop(String uid, String movementsString) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void dragAndDropTo(String sourceUid, String targetUid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseDown(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseDownRight(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseDownAt(String uid, String coordinate) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseDownRightAt(String uid, String coordinate) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseUp(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseUpRight(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseUpRightAt(String uid, String coordinate) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseMove(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void mouseMoveAt(String uid, String coordinate) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Number getXpathCount(String xpath) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String captureNetworkTraffic(String type) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void addCustomRequestHeader(String key, String value) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Number getCssSelectorCount(String cssSelector) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def Number getLocatorCount(String locator) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getXPath(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getSelector(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getLocator(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getCSS(String uid, String cssName) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllTableCellText(String uid) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllTableCellTextForTbody(String uid, int index) {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }
/*

  def int getTableHeaderColumnNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableFootColumnNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxTbodyNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableHeaderColumnNumBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableFootColumnNumBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNumBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNumBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxTbodyNumBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTeTableHeaderColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getTableHeaderColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*
  def int getTeTableFootColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getTableFootColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*  def int getTeTableRowNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*/

  def int getTableMaxRowNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*
  def int getTeTableColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getTableMaxColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*
  def int getTeTableRowNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getTableMaxRowNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*  def int getTeTableColumnNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*/

  def int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*  def int getTeTableTbodyNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*/

  def int getTableMaxTbodyNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*
  def int getRepeatNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getRepeatNumByCssSelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTeRepeatNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getRepeatNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

/*
  def int getListSizeByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getListSizeBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTeListSize(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*/

  def int getListSize(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isDisabled(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def getParentAttribute(String uid, String attribute) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def getAttribute(String uid, String attribute) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean hasCssClass(String uid, String cssClass) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void toggle(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void show(String uid, int delay) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void startShow(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void endShow(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void dump(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String toString(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def JSONArray toJSONArray(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void validate(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def UiModuleValidationResponse getUiModuleValidationResult(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def DiagnosisResponse getDiagnosisResult(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void diagnose(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void diagnose(String uid, DiagnosisOption options) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String toHTML(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String toHTML() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def java.util.List getHTMLSourceResponse(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void getHTMLSource(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getHtmlSource() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String retrieveLastRemoteControlLogs() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setTimeout(long timeoutInMilliseconds) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isCookiePresent(String name) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getCookie() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getCookieByName(String name) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void createCookie(String nameValuePair, String optionsString) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void deleteCookie(String name, String optionsString) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void deleteAllVisibleCookies() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setCookie(String cookieName, String value, Object options) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setCookie(String cookieName, String value) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def UiByTagResponse getUiByTag(String tag, Map filters) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void removeMarkedUids(String tag) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void reset(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void bugReport() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object getWidget(String uid) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object onWidget(String uid, String method, Object[] args) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectFrame(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectFrameByIndex(int index) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectParentFrameFrom(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectTopFrameFrom(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean getWhetherThisFrameMatchFrameExpression(String uid, String target) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void waitForFrameToLoad(String uid, int timeout) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void openWindow(String uid, String url) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectWindow(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void closeWindow(String uid) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectMainWindow() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void selectParentWindow() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void waitForPopUp(String uid, int timeout) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void windowFocus() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void windowMaximize() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getBodyText() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isTextPresent(String pattern) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getExpression(String expression) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void runScript(String script) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void captureScreenshot(String filename) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void captureEntirePageScreenshot(String filename, String kwargs) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String captureScreenshotToString() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String captureEntirePageScreenshotToString(String kwargs) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void chooseCancelOnNextConfirmation() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void chooseOkOnNextConfirmation() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void answerOnNextPrompt(String answer) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void goBack() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def void refresh() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isAlertPresent() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isPromptPresent() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isConfirmationPresent() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getAlert() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getConfirmation() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getPrompt() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getLocation() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getTitle() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllButtons() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllLinks() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllFields() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllWindowIds() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllWindowNames() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def String[] getAllWindowTitles() {
    return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void waitForPageToLoad(int timeout) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def String getXMLDocument() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def void setMaxMacroCmd(int max) {
    
  }

  UiObject walkToWithException(WorkflowContext context, String uid) {
    
  }
}
