package org.telluriumsource.dsl

import org.telluriumsource.entity.CacheUsageResponse
import org.telluriumsource.entity.EngineState
import org.json.simple.JSONArray
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.UiByTagResponse

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 *
 */
public interface DslContract {

  EngineState getEngineState();

  void helpTest();

  void noTest();

  void enableClosestMatch();

  void disableClosestMatch();

  void enableCssSelector();

  void disableCssSelector();

  void cleanCache();

  void setCacheMaxSize(int size);

  int getCacheSize();

  int getCacheMaxSize();

  String getCacheUsage();

  CacheUsageResponse getCacheUsageResponse();

  void useDiscardNewCachePolicy();

  void useDiscardOldCachePolicy();

  void useDiscardLeastUsedCachePolicy();

  void useDiscardInvalidCachePolicy();

  String getCurrentCachePolicy();

  void useDefaultXPathLibrary();

  void useJavascriptXPathLibrary();

  void useAjaxsltXPathLibrary();

  void registerNamespace(String prefix, String namespace);

  String getNamespace(String prefix);

  void pause(int milliseconds);

  void enableTrace();

  void disableTrace();

  void showTrace();

  void setEnvironment(String name, Object value);

  Object getEnvironment(String name);

  void enableMacroCmd();

  void disableMacroCmd();

  void setMaxMacroCmd(int max);

  int getMaxMacroCmd();

  void allowNativeXpath(boolean allow);

  void addScript(String scriptContent, String scriptTagId);

  void removeScript(String scriptTagId);

  void enableEngineLog();

  void disableEngineLog();

  void useEngineLog(boolean isUse);

  void enableTelluriumEngine();

  void disableTelluriumEngine();

  void useTelluriumEngine(boolean isUse);

  def customUiCall(String uid, String method, Object[] args);

  def customDirectCall(String method, Object[] args);

  void triggerEventOn(String uid, String event);

  def getUiElement(String uid);

  void click(String uid);

  void doubleClick(String uid);

  void clickAt(String uid, String coordination);

  void check(String uid);

  void uncheck(String uid);

  void type(String uid, def input);

  void keyPress(String uid, String key);

  void keyDown(String uid, String key);

  void keyUp(String uid, String key);

  void focus(String uid);

  void fireEvent(String uid, String eventName);

  void keyType(String uid, def input);

  void typeAndReturn(String uid, def input);

  void altKeyUp();

  void altKeyDown();

  void ctrlKeyUp();

  void ctrlKeyDown();

  void shiftKeyUp();

  void shiftKeyDown();

  void metaKeyUp();

  void metaKeyDown();

  void clearText(String uid);

  void select(String uid, String target);

  void selectByLabel(String uid, String target);

  void selectByValue(String uid, String target);

  void selectByIndex(String uid, int target);

  void selectById(String uid, String target);

  void addSelectionByLabel(String uid, String target);

  void addSelectionByValue(String uid, String target);

  void removeSelectionByLabel(String uid, String target);

  void removeSelectionByValue(String uid, String target);

  void removeAllSelections(String uid);

  String[] getSelectOptions(String uid);

  String[] getSelectValues(String uid);

  String[] getSelectedLabels(String uid);

  String getSelectedLabel(String uid);

  String[] getSelectedValues(String uid);

  String getSelectedValue(String uid);

  String[] getSelectedIndexes(String uid);

  String getSelectedIndex(String uid);

  String[] getSelectedIds(String uid);

  String getSelectedId(String uid);

  boolean isSomethingSelected(String uid);

  String waitForText(String uid, int timeout);

  boolean isElementPresent(String uid);

  boolean isVisible(String uid);

  boolean isChecked(String uid);

  boolean isEnabled(String uid);

  boolean waitForElementPresent(String uid, int timeout);

  boolean waitForElementPresent(String uid, int timeout, int step);

  boolean waitForCondition(String script, int timeoutInMilliSecond);

  String getText(String uid);

  String getValue(String uid);

  String getLink(String uid);

  String getImageSource(String uid);

  String getImageAlt(String uid);

  String getImageTitle(String uid);

  void submit(String uid);

  boolean isEditable(String uid);

  String getEval(String script);

  void mouseOver(String uid);

  void mouseOut(String uid);

  void dragAndDrop(String uid, String movementsString);

  void dragAndDropTo(String sourceUid, String targetUid);

  void mouseDown(String uid);

  void mouseDownRight(String uid);

  void mouseDownAt(String uid, String coordinate);

  void mouseDownRightAt(String uid, String coordinate);

  void mouseUp(String uid);

  void mouseUpRight(String uid);

  void mouseUpRightAt(String uid, String coordinate);

  void mouseMove(String uid);

  void mouseMoveAt(String uid, String coordinate);

  Number getXpathCount(String xpath);

  String captureNetworkTraffic(String type);

  void addCustomRequestHeader(String key, String value);

  Number getCssSelectorCount(String cssSelector);

//  Number getLocatorCount(String locator);

  String getXPath(String uid);

  String getSelector(String uid);

//  String getLocator(String uid);

  String[] getCSS(String uid, String cssName);

  //This only works for jQuery selector
  String[] getAllTableCellText(String uid);

  //This only works for jQuery selector and Standard Table
  String[] getAllTableCellTextForTbody(String uid, int index);

  int getTableHeaderColumnNum(String uid);

  int getTableFootColumnNum(String uid);

  int getTableMaxRowNum(String uid);

  int getTableMaxColumnNum(String uid);

  int getTableMaxRowNumForTbody(String uid, int ntbody);

  int getTableMaxColumnNumForTbody(String uid, int ntbody);

  int getTableMaxTbodyNum(String uid);

  int getRepeatNum(String uid);

  int getListSize(String uid);

  boolean isDisabled(String uid);

//  def getParentAttribute(String uid, String attribute);

  def getAttribute(String uid, String attribute);

  boolean hasCssClass(String uid, String cssClass);

  //Toggle displaying each of the set of matched elements.
  //If they are shown, toggle makes them hidden.
  //If they are hidden, toggle makes them shown
  void toggle(String uid);

  //delay in milliseconds
  void show(String uid, int delay);

  void startShow(String uid);

  void endShow(String uid);

  void dump(String uid);

  String toString(String uid);

  JSONArray toJSONArray(String uid);

  void validate(String uid);

  UiModuleValidationResponse getUiModuleValidationResult(String uid);

  DiagnosisResponse getDiagnosisResult(String uid);

  DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options);

  void diagnose(String uid);

  void diagnose(String uid, DiagnosisOption options);

  String toHTML(String uid);

  String toHTML();

  java.util.List getHTMLSourceResponse(String uid);

  void getHTMLSource(String uid);

  String getHtmlSource();

  String retrieveLastRemoteControlLogs();

  void setTimeout(long timeoutInMilliseconds);

  boolean isCookiePresent(String name);

  String getCookie();

  String getCookieByName(String name);

  void createCookie(String nameValuePair, String optionsString);

  void deleteCookie(String name, String optionsString);

  void deleteAllVisibleCookies();

  void setCookie(String cookieName, String value, def options);

  void setCookie(String cookieName, String value);

  UiByTagResponse getUiByTag(String tag, Map filters);

  void removeMarkedUids(String tag);

  //reset a form
  void reset(String uid);

  void bugReport();

  def getWidget(String uid);

  def onWidget(String uid, String method, Object[] args);

  void selectFrame(String uid);

  void selectFrameByIndex(int index);

  void selectParentFrameFrom(String uid);

  void selectTopFrameFrom(String uid);

  boolean getWhetherThisFrameMatchFrameExpression(String uid, String target);

  void waitForFrameToLoad(String uid, int timeout);

  void openWindow(String uid, String url);

  void selectWindow(String uid);

  void closeWindow(String uid);

  void selectMainWindow();

  void selectParentWindow();

  void waitForPopUp(String uid, int timeout);

  boolean getWhetherThisWindowMatchWindowExpression(String uid, String target);

  void windowFocus();

  void windowMaximize();

  String getBodyText();

  boolean isTextPresent(String pattern);

  String getExpression(String expression);

  void runScript(String script);

  void captureScreenshot(String filename);

  void captureEntirePageScreenshot(String filename, String kwargs);

  String captureScreenshotToString();

  String captureEntirePageScreenshotToString(String kwargs);

  void chooseCancelOnNextConfirmation();

  void chooseOkOnNextConfirmation();

  void answerOnNextPrompt(String answer);

  void goBack();

  void refresh();

  boolean isAlertPresent();

  boolean isPromptPresent();

  boolean isConfirmationPresent();

  String getAlert();

  String getConfirmation();

  String getPrompt();

  String getLocation();

  String getTitle();

  String[] getAllButtons();

  String[] getAllLinks();

  String[] getAllFields();

  String[] getAllWindowIds();

  String[] getAllWindowNames();

  String[] getAllWindowTitles();

  void waitForPageToLoad(int timeout);

  String getXMLDocument();
}