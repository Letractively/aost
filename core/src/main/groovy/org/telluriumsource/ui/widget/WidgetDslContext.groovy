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
import org.telluriumsource.exception.UiObjectNotFoundException

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

  UiObject walkToWithException(WorkflowContext context, String uid) {
//      env.lastDslContext = this;
    UiObject obj = ui.walkTo(context, uid);
    if (obj != null) {
      context.attachMetaCmd(uid, obj.amICacheable(), true);
//        context.putContext(WorkflowContext.DSLCONTEXT, this);
      env.lastUiModule = getUiModuleId(uid);

      return obj;
    }

    throw new UiObjectNotFoundException(i18nBundle.getMessage("BaseDslContext.CannotFindUIObject", uid));
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

    //let the missing property return the a string of the property, this is useful for the onWidget method
    //so that we can pass in widget method directly, instead of passing in the method name as a String
    def propertyMissing(String name) {
      println getDelegate().i18nBundle.getMessage("BaseDslContext.PropertyIsMissing", name)
      return name
    }

    void attachFile(String fieldLocator, String fileLocator) {
      getDelegate().attachFile(fieldLocator, fileLocator);
    }
/*
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
*//*

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
*//*

  def int getTableHeaderColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*
  def int getTeTableFootColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*//*

  def int getTableFootColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*  def int getTeTableRowNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*//*

  def int getTableMaxRowNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*
  def int getTeTableColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*//*

  def int getTableMaxColumnNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*
  def int getTeTableRowNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*//*

  def int getTableMaxRowNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*  def int getTeTableColumnNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*//*

  def int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*  def int getTeTableTbodyNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }*//*

  def int getTableMaxTbodyNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*
  def int getRepeatNumByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getRepeatNumByCssSelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTeRepeatNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*//*

  def int getRepeatNum(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

*//*
  def int getListSizeByXPath(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getListSizeBySelector(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  def int getTeListSize(String uid) {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
*//*

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

  void attachFile(String fieldLocator, String fileLocator) {

  }*/
}
