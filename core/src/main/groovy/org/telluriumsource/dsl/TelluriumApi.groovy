package org.telluriumsource.dsl

import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.exception.UiObjectNotFoundException
import org.telluriumsource.ui.object.UiObject

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 10, 2010
 * 
 */
class TelluriumApi extends BaseDslContext {

  UiObject walkToWithException(WorkflowContext context, String uid) {
//      env.lastDslContext = this;
    UiObject obj = ui.walkTo(context, uid);
    if (obj != null) {
//        context.attachMetaCmd(uid, obj.amICacheable(), true);
//        context.putContext(WorkflowContext.DSLCONTEXT, this);
      env.lastUiModule = getUiModuleId(uid);

      return obj;
    }

    throw new UiObjectNotFoundException(i18nBundle.getMessage("BaseDslContext.CannotFindUIObject", uid));
  }

  public void enableClosestMatch() {
      env.setUseClosestMatch(true);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useClosestMatch(context, true);
  }

  public void disableClosestMatch() {
      env.setUseClosestMatch(false);
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.useClosestMatch(context, false)
  }

  public void enableCssSelector() {
      env.setUseCssSelector(true);
  }

  public void disableCssSelector() {
      env.setUseCssSelector(false);
  }

  public void cleanCache() {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.cleanCache(context)
  }

  public void setCacheMaxSize(int size) {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.cleanCache(context)
  }

  public int getCacheSize() {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return extension.getCacheSize(context).intValue();
  }

  public int getCacheMaxSize() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     return extension.getCacheMaxSize(context).intValue()
  }

  public String getCacheUsage() {
     CacheUsageResponse resp = this.getCacheUsageResponse();

     return resp.toString();
  }

  public CacheUsageResponse getCacheUsageResponse() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    List out = extension.getCacheUsage(context);
    CacheUsageResponse resp = new CacheUsageResponse();
    resp.parseJSON(out);

    return resp;

  }

  public void useDiscardNewCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardNewCachePolicy(context)
  }

  public void useDiscardOldCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardOldCachePolicy(context)
  }

  public void useDiscardLeastUsedCachePolicy() {
     WorkflowContext context = WorkflowContext.getDefaultContext();
     extension.useDiscardLeastUsedCachePolicy(context)
  }

  public void useDiscardInvalidCachePolicy() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    extension.useDiscardInvalidCachePolicy(context)
  }

  public String getCurrentCachePolicy() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return extension.getCachePolicyName(context)
  }

  public def customUiCall(String uid, String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    walkToWithException(context, uid);
    Object[] list = [context, uid, args].flatten();

    return extension.invokeMethod(method, list);
  }

  def customDirectCall(String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    Object[] list = [context, args].flatten()
    return extension.invokeMethod(method, list)
  }

  public void triggerEventOn(String uid, String event) {

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

  def int getTableHeaderColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableFootColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxRowNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTableMaxTbodyNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getRepeatNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getRepeatNumByCssSelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getRepeatNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getListSize(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean isDisabled(String uid) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object getParentAttribute(String uid, String attribute) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def Object getAttribute(String uid, String attribute) {
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

  def List getHTMLSourceResponse(String uid) {
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

  def void waitForPopUp(String uid, int timeout) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  def boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

}
