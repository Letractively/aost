package org.telluriumsource.dsl

import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.framework.Session
import org.telluriumsource.exception.NoSessionFoundException
import org.telluriumsource.framework.RuntimeEnvironment

import org.telluriumsource.framework.ILookup

class DslContext implements IDslContext {

  protected UiDslParser ui = this.&getUiParser();

  protected UiDslParser getUiParser(){
    return SessionManager.getSession().getByClass(UiDslParser.class);
  }

  protected IDslContext getDelegate(){
    Session session = SessionManager.getSession();
    if(session == null){
      throw new NoSessionFoundException("Cannot find session");
    }

    RuntimeEnvironment env = session.getEnv();
    if(env.isUseNewEngine())
      return session.getApi();
    else
      return session.getWrapper();
  }

  public EngineState getEngineState() {

    return getDelegate().getEngineState();
  }

  public void helpTest() {
     getDelegate().helpTest();
  }

  public void noTest() {
    getDelegate().noTest();
  }

  public void enableClosestMatch() {
    getDelegate().enableClosestMatch();
  }

  public void disableClosestMatch() {
    getDelegate().disableClosestMatch();
  }

  public void enableCssSelector() {
    getDelegate().enableCssSelector();
  }

  public void disableCssSelector() {
     getDelegate().disableCssSelector();
  }

  public void cleanCache() {
    getDelegate().cleanCache();
  }

  public void setCacheMaxSize(int size) {
     getDelegate().setCacheMaxSize(size);
  }

  public int getCacheSize() {
    return getDelegate().getCacheSize();
  }

  public int getCacheMaxSize() {
    return getDelegate().getCacheMaxSize();
  }

  public String getCacheUsage() {
    return getDelegate().getCacheUsage();
  }

  public CacheUsageResponse getCacheUsageResponse() {
    return getDelegate().getCacheUsageResponse();
  }

  public void useDiscardNewCachePolicy() {
    getDelegate().useDiscardNewCachePolicy();
  }

  public void useDiscardOldCachePolicy() {
    getDelegate().useDiscardOldCachePolicy();
  }

  public void useDiscardLeastUsedCachePolicy() {
    getDelegate().useDiscardLeastUsedCachePolicy();
  }

  public void useDiscardInvalidCachePolicy() {
    getDelegate().useDiscardInvalidCachePolicy();
  }

  public String getCurrentCachePolicy() {
    return getDelegate().getCurrentCachePolicy();
  }

  public void useDefaultXPathLibrary() {
      getDelegate().useDefaultXPathLibrary();
  }

  public void useJavascriptXPathLibrary() {
      getDelegate().useJavascriptXPathLibrary();
  }

  public void useAjaxsltXPathLibrary() {
      getDelegate().useAjaxsltXPathLibrary();
  }

  public void registerNamespace(String prefix, String namespace) {
      getDelegate().registerNamespace(prefix, namespace);
  }

  public String getNamespace(String prefix) {
    return getDelegate().getNamespace(prefix);
  }

  public void pause(int milliseconds) {
      getDelegate().pause(milliseconds);
  }

  public void enableTrace() {
     getDelegate().enableTrace();
  }

  public void disableTrace() {
      getDelegate().disableTrace();
  }

  public void showTrace() {
      getDelegate().showTrace();
  }

  public void setEnvironment(String name, Object value) {
     getDelegate().setEnvironment(name, value);
  }

  public Object getEnvironment(String name) {
    return getDelegate().getEnvironment(name);
  }

  public void enableMacroCmd() {
     getDelegate().enableMacroCmd();
  }

  public void disableMacroCmd() {
    getDelegate().disableMacroCmd();
  }

  public int getMaxMacroCmd() {
    return getDelegate().getMaxMacroCmd();
  }

  public void setMaxMacroCmd(int max) {
    getDelegate().setMaxMacroCmd(max);
  }

  public void allowNativeXpath(boolean allow) {
     getDelegate().allowNativeXpath(allow);
  }

  public void addScript(String scriptContent, String scriptTagId) {
     getDelegate().addScript(scriptContent, scriptTagId);
  }

  public void removeScript(String scriptTagId) {
     getDelegate().removeScript(scriptTagId);
  }

  public void enableEngineLog() {
     getDelegate().enableEngineLog();
  }

  public void disableEngineLog() {
     getDelegate().disableEngineLog();
  }

  public void useEngineLog(boolean isUse) {
     getDelegate().useEngineLog(isUse);
  }

  public void enableTelluriumEngine() {
     getDelegate().enableTelluriumEngine();
  }

  public void disableTelluriumEngine() {
     getDelegate().disableTelluriumEngine();
  }

  public void useTelluriumEngine(boolean isUse) {
      getDelegate().useTelluriumEngine(isUse);
  }

  public def customUiCall(String uid, String method, Object[] args) {
    return getDelegate().customUiCall(uid, method, args);  
  }

  public def customDirectCall(String method, Object[] args) {
    return getDelegate().customDirectCall(method, args);
  }

  public void triggerEventOn(String uid, String event) {
     getDelegate().triggerEventOn(uid, event);
  }

  public def getUiElement(String uid) {
    return getDelegate().getUiElement(uid);  
  }

  public void click(String uid) {
     getDelegate().click(uid);
  }

  public void doubleClick(String uid) {
     getDelegate().doubleClick(uid);
  }

  public void clickAt(String uid, String coordination) {
     getDelegate().clickAt(uid, coordination);
  }

  public void check(String uid) {
    getDelegate().check(uid);
  }

  public void uncheck(String uid) {
    getDelegate().uncheck(uid);
  }

  public void type(String uid, Object input) {
    getDelegate().type(uid, input);
  }

  public void keyPress(String uid, String key) {
     getDelegate().keyPress(uid, key);
  }

  public void keyDown(String uid, String key) {
     getDelegate().keyDown(uid, key);
  }

  public void keyUp(String uid, String key) {
     getDelegate().keyUp(uid, key);
  }

  public void focus(String uid) {
     getDelegate().focus(uid);
  }

  public void fireEvent(String uid, String eventName) {
    getDelegate().fireEvent(uid, eventName);
  }

  public void keyType(String uid, Object input) {
    getDelegate().keyType(uid, input);
  }

  public void typeAndReturn(String uid, Object input) {
    getDelegate().typeAndReturn(uid, input);
  }

  public void altKeyUp() {
    getDelegate().altKeyUp();
  }

  public void altKeyDown() {
    getDelegate().altKeyDown();
  }

  public void ctrlKeyUp() {
    getDelegate().ctrlKeyUp();
  }

  public void ctrlKeyDown() {
    getDelegate().ctrlKeyDown();
  }

  public void shiftKeyUp() {
    getDelegate().shiftKeyUp();
  }

  public void shiftKeyDown() {
    getDelegate().shiftKeyDown();
  }

  public void metaKeyUp() {
    getDelegate().metaKeyUp();
  }

  public void metaKeyDown() {
    getDelegate().metaKeyDown();
  }

  public void clearText(String uid) {
    getDelegate().clearText(uid);
  }

  public void select(String uid, String target) {
    getDelegate().select(uid, target);
  }

  public void selectByLabel(String uid, String target) {
    getDelegate().selectByLabel(uid, target);
  }

  public void selectByValue(String uid, String target) {
    getDelegate().selectByValue(uid, target);
  }

  public void selectByIndex(String uid, int target) {
    getDelegate().selectByIndex(uid, target);
  }

  public void selectById(String uid, String target) {
    getDelegate().selectById(uid, target);
  }

  public void addSelectionByLabel(String uid, String target) {
    getDelegate().addSelectionByLabel(uid, target);
  }

  public void addSelectionByValue(String uid, String target) {
    getDelegate().addSelectionByValue(uid, target);
  }

  public void removeSelectionByLabel(String uid, String target) {
    getDelegate().removeSelectionByLabel(uid, target);
  }

  public void removeSelectionByValue(String uid, String target) {
    getDelegate().removeSelectionByValue(uid, target);
  }

  public void removeAllSelections(String uid) {
    getDelegate().removeAllSelections(uid);
  }

  public String[] getSelectOptions(String uid) {
    return getDelegate().getSelectOptions(uid);
  }

  public String[] getSelectValues(String uid) {
    return getDelegate().getSelectValues(uid);
  }

  public String[] getSelectedLabels(String uid) {
    return getDelegate().getSelectedLabels(uid);
  }

  public String getSelectedLabel(String uid) {
    return  getDelegate().getSelectedLabel(uid);
  }

  public String[] getSelectedValues(String uid) {
    return getDelegate().getSelectedValues(uid);
  }

  public String getSelectedValue(String uid) {
    return getDelegate().getSelectedValue(uid);
  }

  public String[] getSelectedIndexes(String uid) {
    return getDelegate().getSelectedIndexes(uid);
  }

  public String getSelectedIndex(String uid) {
    return getDelegate().getSelectedIndex(uid);
  }

  public String[] getSelectedIds(String uid) {
    return getDelegate().getSelectedIds(uid);
  }

  public String getSelectedId(String uid) {
    return getDelegate().getSelectedId(uid);
  }

  public boolean isSomethingSelected(String uid) {
    return getDelegate().isSomethingSelected(uid);
  }

  public String waitForText(String uid, int timeout) {
    return getDelegate(). waitForText(uid, timeout);
  }

  public boolean isElementPresent(String uid) {
    return getDelegate().isElementPresent(uid);
  }

  public boolean isVisible(String uid) {
    return getDelegate().isVisible(uid);
  }

  public boolean isChecked(String uid) {
    return  getDelegate().isChecked(uid);
  }

  public boolean isEnabled(String uid) {
    return getDelegate().isEnabled(uid);
  }

  public boolean waitForElementPresent(String uid, int timeout) {
    return getDelegate().waitForElementPresent(uid, timeout);
  }

  public boolean waitForElementPresent(String uid, int timeout, int step) {
    return getDelegate().waitForElementPresent(uid, timeout, step);
  }

  public boolean waitForCondition(String script, int timeoutInMilliSecond) {
    return getDelegate().waitForCondition(script, timeoutInMilliSecond);
  }

  public String getText(String uid) {
    return getDelegate().getText(uid);
  }

  public String getValue(String uid) {
    return getDelegate().getValue(uid);
  }

  public String getLink(String uid) {
    return getDelegate().getLink(uid);
  }

  public String getImageSource(String uid) {
    return getDelegate().getImageSource(uid);
  }

  public String getImageAlt(String uid) {
    return  getDelegate().getImageAlt(uid);
  }

  public String getImageTitle(String uid) {
    return getDelegate().getImageTitle(uid);
  }

  public void submit(String uid) {
    getDelegate().submit(uid);
  }

  public boolean isEditable(String uid) {
    return getDelegate().isEditable(uid);
  }

  public String getEval(String script) {
    return getDelegate().getEval(script);
  }

  public void mouseOver(String uid) {
    getDelegate().mouseOver(uid);
  }

  public void mouseOut(String uid) {
    getDelegate().mouseOut(uid)
  }

  public void dragAndDrop(String uid, String movementsString) {
    getDelegate().dragAndDrop(uid, movementsString);
  }

  public void dragAndDropTo(String sourceUid, String targetUid) {
    getDelegate().dragAndDropTo(sourceUid, targetUid);
  }

  public void mouseDown(String uid) {
    getDelegate().mouseDown(uid);
  }

  public void mouseDownRight(String uid) {
    getDelegate().mouseDownRight(uid);
  }

  public void mouseDownAt(String uid, String coordinate) {
    getDelegate().mouseDownAt(uid, coordinate);
  }

  public void mouseDownRightAt(String uid, String coordinate) {
    getDelegate().mouseDownRightAt(uid, coordinate);
  }

  public void mouseUp(String uid) {
    getDelegate().mouseUp(uid);
  }

  public void mouseUpRight(String uid) {
    getDelegate().mouseUpRight(uid);
  }

  public void mouseUpRightAt(String uid, String coordinate) {
    getDelegate().mouseUpRightAt(uid, coordinate);
  }

  public void mouseMove(String uid) {
    getDelegate().mouseMove(uid);
  }

  public void mouseMoveAt(String uid, String coordinate) {
    getDelegate().mouseMoveAt(uid, coordinate);
  }

  public Number getXpathCount(String xpath) {
    return getDelegate().getXpathCount(xpath);
  }

  public String captureNetworkTraffic(String type) {
    return getDelegate().captureNetworkTraffic(type);
  }

  public void addCustomRequestHeader(String key, String value) {
    getDelegate().addCustomRequestHeader(key, value);
  }

  public Number getCssSelectorCount(String cssSelector) {
    return getDelegate().getCssSelectorCount(cssSelector);
  }

  public Number getLocatorCount(String locator) {
    return getDelegate().getLocatorCount(locator);
  }

  public String getXPath(String uid) {
    return getDelegate().getXPath(uid);
  }

  public String getSelector(String uid) {
    return getDelegate().getSelector(uid);
  }

  public String getLocator(String uid) {
    return getDelegate().getLocator(uid);
  }

  public String[] getCSS(String uid, String cssName) {
    return getDelegate().getCSS(uid, cssName);
  }

  public String[] getAllTableCellText(String uid) {
    return getDelegate().getAllTableCellText(uid);
  }

  public String[] getAllTableCellTextForTbody(String uid, int index) {
    return getDelegate().getAllTableCellTextForTbody(uid, index);
  }

  public int getTableHeaderColumnNum(String uid) {
    return getDelegate().getTableHeaderColumnNum(uid);
  }

  public int getTableFootColumnNum(String uid) {
    return  getDelegate().getTableFootColumnNum(uid);
  }

  public int getTableMaxRowNum(String uid) {
    return getDelegate().getTableMaxRowNum(uid);  
  }

  public int getTableMaxColumnNum(String uid) {
    return getDelegate().getTableMaxColumnNum(uid);
  }

  public int getTableMaxRowNumForTbody(String uid, int ntbody) {
    return getDelegate().getTableMaxRowNumForTbody(uid, ntbody);
  }

  public int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    return getDelegate().getTableMaxColumnNumForTbody(uid, ntbody);
  }

  public int getTableMaxTbodyNum(String uid) {
    return getDelegate().getTableMaxTbodyNum(uid);
  }


  public int getRepeatNum(String uid) {
    return getDelegate().getRepeatNum(uid);
  }

  public int getListSize(String uid) {
    return getDelegate().getListSize(uid);
  }

  public boolean isDisabled(String uid) {
    return getDelegate().isDisabled(uid);
  }

//  public def getParentAttribute(String uid, String attribute) {
//    return getDelegate().getParentAttribute(uid, attribute);
//  }

  public def getAttribute(String uid, String attribute) {
    return getDelegate().getAttribute(uid, attribute);
  }

  public boolean hasCssClass(String uid, String cssClass) {
    return getDelegate().hasCssClass(uid, cssClass);
  }

  public void toggle(String uid) {
    getDelegate().toggle(uid);
  }

  public void show(String uid, int delay) {
    getDelegate().show(uid, delay);
  }

  public void startShow(String uid) {
    getDelegate().startShow(uid);
  }

  public void endShow(String uid) {
    getDelegate().endShow(uid);
  }

  public void dump(String uid) {
    getDelegate().dump(uid);
  }

  public String toString(String uid) {
    return getDelegate().toString(uid);
  }

  public JSONArray toJSONArray(String uid) {
    return getDelegate().toJSONArray(uid);
  }

  public void validate(String uid) {
    getDelegate().validate(uid);
  }

  public UiModuleValidationResponse getUiModuleValidationResult(String uid) {
    return getDelegate().getUiModuleValidationResult(uid);
  }

  public DiagnosisResponse getDiagnosisResult(String uid) {
    return getDelegate().getDiagnosisResult(uid);
  }

  public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
    return getDelegate().getDiagnosisResult(uid, options);
  }

  public void diagnose(String uid) {
    getDelegate().diagnose(uid);
  }

  public void diagnose(String uid, DiagnosisOption options) {
    getDelegate().diagnose(uid, options);
  }

  public String toHTML(String uid) {
    return getDelegate().toHTML(uid);
  }

  public String toHTML() {
    return getDelegate().toHTML();
  }

  public List getHTMLSourceResponse(String uid) {
    return getDelegate().getHTMLSourceResponse(uid);
  }

  public void getHTMLSource(String uid) {
    getDelegate().getHTMLSource(uid);
  }

  public String getHtmlSource() {
    return getDelegate().getHtmlSource();
  }

  public String retrieveLastRemoteControlLogs() {
    return getDelegate().retrieveLastRemoteControlLogs();
  }

  public void setTimeout(long timeoutInMilliseconds) {
    getDelegate().setTimeout(timeoutInMilliseconds);
  }

  public boolean isCookiePresent(String name) {
    return getDelegate().isCookiePresent(name);
  }

  public String getCookie() {
    return getDelegate().getCookie();
  }

  public String getCookieByName(String name) {
    return getDelegate().getCookieByName(name);
  }

  public void createCookie(String nameValuePair, String optionsString) {
    getDelegate().createCookie(nameValuePair, optionsString)
  }

  public void deleteCookie(String name, String optionsString) {
    getDelegate().deleteCookie(name, optionsString)
  }

  public void deleteAllVisibleCookies() {
    getDelegate().deleteAllVisibleCookies();
  }

  public void setCookie(String cookieName, String value, Object options) {
    getDelegate().setCookie(cookieName, value, options);
  }

  public void setCookie(String cookieName, String value) {
    getDelegate().setCookie(cookieName, value);
  }

  public UiByTagResponse getUiByTag(String tag, Map filters) {
    return getDelegate().getUiByTag(tag, filters);
  }

  public void removeMarkedUids(String tag) {
    getDelegate().removeMarkedUids(tag);
  }

  public void reset(String uid) {
    getDelegate().reset(uid);
  }

  public void bugReport() {
    getDelegate().bugReport();
  }

  public Object getWidget(String uid) {
    return getDelegate().getWidget(uid);
  }

  public Object onWidget(String uid, String method, Object[] args) {
    return getDelegate().onWidget(uid, method, args);
  }

  public void selectFrame(String uid) {
    getDelegate().selectFrame(uid);
  }

  public void selectFrameByIndex(int index) {
    getDelegate().selectFrameByIndex(index);
  }

  public void selectParentFrameFrom(String uid) {
    getDelegate().selectParentFrameFrom(uid);
  }

  public void selectTopFrameFrom(String uid) {
    getDelegate().selectTopFrameFrom(uid);
  }

  public boolean getWhetherThisFrameMatchFrameExpression(String uid, String target) {
    return getDelegate().getWhetherThisFrameMatchFrameExpression(uid, target);
  }

  public void waitForFrameToLoad(String uid, int timeout) {
    getDelegate().waitForFrameToLoad(uid, timeout);
  }

  public void openWindow(String uid, String url) {
    getDelegate().openWindow(uid, url);
  }

  public void selectWindow(String uid) {
    getDelegate().selectWindow(uid);
  }

  public void closeWindow(String uid) {
    getDelegate().closeWindow(uid);
  }

  public void selectMainWindow() {
    getDelegate().selectMainWindow();
  }

  public void selectParentWindow() {
    getDelegate().selectParentWindow();
  }

  public void waitForPopUp(String uid, int timeout) {
    getDelegate().waitForPopUp(uid, timeout);
  }

  public boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
    return getDelegate().getWhetherThisWindowMatchWindowExpression(uid, target);
  }

  public void windowFocus() {
    getDelegate().windowFocus();
  }

  public void windowMaximize() {
    getDelegate().windowMaximize();
  }

  public String getBodyText() {
    return getDelegate().getBodyText();
  }

  public boolean isTextPresent(String pattern) {
    return getDelegate().isTextPresent(pattern);
  }

  public String getExpression(String expression) {
    return getDelegate().getExpression(expression);
  }

  public void runScript(String script) {
    getDelegate().runScript(script);
  }

  public void captureScreenshot(String filename) {
    getDelegate().captureScreenshot(filename);
  }

  public void captureEntirePageScreenshot(String filename, String kwargs) {
    getDelegate().captureEntirePageScreenshot(filename, kwargs);
  }

  public String captureScreenshotToString() {
    return getDelegate().captureScreenshotToString();
  }

  public String captureEntirePageScreenshotToString(String kwargs) {
    return getDelegate().captureEntirePageScreenshotToString(kwargs);
  }

  public void chooseCancelOnNextConfirmation() {
    getDelegate().chooseCancelOnNextConfirmation();
  }

  public void chooseOkOnNextConfirmation() {
    getDelegate().chooseOkOnNextConfirmation();
  }

  public void answerOnNextPrompt(String answer) {
    getDelegate().answerOnNextPrompt(answer)
  }

  public void goBack() {
    getDelegate().goBack();
  }

  public void refresh() {
    getDelegate().refresh();
  }

  public boolean isAlertPresent() {
    return getDelegate().isAlertPresent();
  }

  public boolean isPromptPresent() {
    return getDelegate().isPromptPresent();
  }

  public boolean isConfirmationPresent() {
    return getDelegate().isConfirmationPresent();
  }

  public String getAlert() {
    return getDelegate().getAlert();
  }

  public String getConfirmation() {
    return getDelegate().getConfirmation();
  }

  public String getPrompt() {
    return getDelegate().getPrompt();
  }

  public String getLocation() {
    return getDelegate().getLocation();
  }

  public String getTitle() {
    return getDelegate().getTitle();
  }

  public String[] getAllButtons() {
    return getDelegate().getAllButtons();
  }

  public String[] getAllLinks() {
    return getDelegate().getAllLinks();
  }

  public String[] getAllFields() {
    return getDelegate().getAllFields();
  }

  public String[] getAllWindowIds() {
    return getDelegate().getAllWindowIds();
  }

  public String[] getAllWindowNames() {
    return getDelegate().getAllWindowNames();
  }

  public String[] getAllWindowTitles() {
    return getDelegate().getAllWindowTitles();
  }

  public void waitForPageToLoad(int timeout) {
    getDelegate().waitForPageToLoad(timeout)
  }

  public String getXMLDocument() {
    return getDelegate().getXMLDocument();
  }

}