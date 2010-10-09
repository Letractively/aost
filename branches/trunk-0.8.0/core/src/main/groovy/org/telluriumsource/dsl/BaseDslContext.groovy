package org.telluriumsource.dsl


import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.telluriumsource.entity.UiModuleValidationResponse
import org.stringtree.json.JSONReader
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.component.custom.Extension
import org.telluriumsource.entity.EngineState
import org.telluriumsource.component.data.Accessor
import org.telluriumsource.component.event.EventHandler
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.util.Helper
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.ui.locator.JQueryOptimizer
import org.telluriumsource.ui.locator.LocatorProcessor
import org.telluriumsource.ui.object.UiObject
import org.json.simple.JSONArray
import org.telluriumsource.annotation.Inject

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
abstract class BaseDslContext implements IDslContext {

  protected static final String JQUERY_SELECTOR = "jquery=";
  protected static final String DEFAULT_XPATH = "default";
  protected static final String JAVASCRIPT_XPATH = "javascript-xpath";
  protected static final String AJAXSLT_XPATH = "ajaxslt";
  protected static final String LOCATOR = "locator";
  protected static final String OPTIMIZED_LOCATOR = "optimized";

  //later on, may need to refactor it to use resource file so that we can show message for different localities
  protected static final String XML_DOCUMENT_SCRIPT = """var doc = window.document;
      var xml = null;
      if(doc instanceof XMLDocument){
         xml = new XMLSerializer().serializeToString(doc);
      }else{
         xml = getText(doc.body);
      }
      xml; """

  @Inject(name="i18nBundle", lazy=true)
  protected IResourceBundle i18nBundle;

  @Inject
  protected UiDslParser ui;

  @Inject
  protected RuntimeEnvironment env;

  @Inject
  protected EventHandler eventHandler;

  @Inject
  protected Accessor accessor;

  @Inject
  protected Extension extension;

  @Inject
  protected JQueryOptimizer optimizer;

  @Inject
  protected LocatorProcessor locatorProcessor;

  abstract UiObject walkToWithException(WorkflowContext context, String uid);

  protected geti18nBundle() {
    return this.i18nBundle;
  }

  protected JSONReader reader = new JSONReader()

  protected Object parseSeleniumJSONReturnValue(String out) {
    if (out.startsWith("OK,")) {
      out = out.substring(3);
    }
    if (out.length() > 0) {
      return reader.read(out);
    }

    return null;
  }

  protected Object parseSeleniumJSONReturnValue(Map out) {
    return out;
  }

  protected Object parseSeleniumJSONReturnValue(java.util.List out) {
    return out;
  }

  protected String locatorMapping(WorkflowContext context, loc) {
    return locatorMappingWithOption(context, loc, null)
  }

  protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc) {
    String locator;
    if (context.noMoreProcess) {
      locator = ""
    } else {
      locator = locatorProcessor.locate(context, loc)
    }

    //get the reference locator all the way to the ui object
    if (context.getReferenceLocator() != null) {
//            locator = context.getReferenceLocator() + locator
      context.appendReferenceLocator(locator)
      locator = context.getReferenceLocator()
    }

    if (optLoc != null)
      locator = locator + optLoc

    if (context.isUseCssSelector()) {
//          locator = optimizer.optimize(JQUERY_SELECTOR + locator.trim())
      locator = postProcessSelector(context, locator.trim())
    } else {
      //make sure the xpath starts with "//"
//          if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
      if (locator != null && (!locator.startsWith("//"))) {
        locator = "/" + locator
      }
    }

    return locator
  }

  protected String postProcessSelector(WorkflowContext context, String jqs) {
//    String locator = jqs

    String optimized = optimizer.optimize(jqs)

/*    if (context.isUseUiModuleCache()) {
      JSONObject obj = new JSONObject()
      //meta command shoud not be null for locators
      MetaCmd metaCmd = context.extraMetaCmd()
      obj.put(LOCATOR, locator)
      obj.put(OPTIMIZED_LOCATOR, optimized)

      obj.put(MetaCmd.UID, metaCmd.getProperty(MetaCmd.UID))
      obj.put(MetaCmd.CACHEABLE, metaCmd.getProperty(MetaCmd.CACHEABLE))
      obj.put(MetaCmd.UNIQUE, metaCmd.getProperty(MetaCmd.UNIQUE))

      String jsonjqs = obj.toString()

      return UIModule_CACHE + jsonjqs
    }
    */

    return JQUERY_SELECTOR + optimized
  }

  protected UiModuleValidationResponse parseUseUiModuleResponse(String result) {
    String out = result;
    if (result.startsWith("OK,")) {
      out = result.substring(3);
    }

    if (out.length() > 0) {
      Map map = reader.read(out);
      UiModuleValidationResponse response = new UiModuleValidationResponse(map);

      return response;
    }

    return null;
  }

//  protected UiModuleValidationResponse parseUseUiModuleResponse(Map result) {

  protected Map parseUseUiModuleResponse(Map result) {
    return result;
  }

  protected String getUiModuleId(String uid) {
    UiID uiid = UiID.convertToUiID(uid);
    return uiid.pop();
  }

  public EngineState getEngineState() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    Map map = extension.getEngineState(context);

    EngineState state = new EngineState();
    state.parseJson(map);

    return state;
  }

  public void helpTest() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.helpTest(context);
  }

  public void noTest() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.noTest(context);
  }


  public void useDefaultXPathLibrary() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    accessor.useXpathLibrary(context, DEFAULT_XPATH)
  }

  public void useJavascriptXPathLibrary() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    accessor.useXpathLibrary(context, JAVASCRIPT_XPATH)
  }

  public void useAjaxsltXPathLibrary() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    accessor.useXpathLibrary(context, AJAXSLT_XPATH)
  }

  public void registerNamespace(String prefix, String namespace) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    extension.addNamespace(context, prefix, namespace)
  }

  public String getNamespace(String prefix) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return extension.getNamespace(context, prefix)
  }

  public void pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = SessionManager.getSession().getByClass(BundleProcessor.class);
    processor.flush();

    Helper.pause(milliseconds);
  }

  public void setEnvironment(String name, Object value) {
    env.setCustomEnvironment(name, value);
  }

  public Object getEnvironment(String name) {
    return env.getCustomEnvironment(name);
  }

  public void enableMacroCmd() {
    env.setUseBundle(true);
  }

  public void disableMacroCmd() {
    env.setUseBundle(false);
  }

  public void setMaxMacroCmd(int max) {
    env.setMaxMacroCmd(max);
  }

  public int getMaxMacroCmd() {
    return env.getMaxMacroCmd();
  }

  public void enableTrace() {
    env.setUseTrace(true);
  }

  public void disableTrace() {
    env.setUseTrace(false);
  }

  public void showTrace() {
    Dispatcher dispatcher = SessionManager.getSession().getByClass(Dispatcher.class);
    dispatcher.showTrace();
  }

  public void flush() {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = SessionManager.getSession().getByClass(BundleProcessor.class);
    processor.flush();
  }

  void allowNativeXpath(boolean allow) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    accessor.allowNativeXpath(context, allow)
  }

  void addScript(String scriptContent, String scriptTagId) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    accessor.addScript(context, scriptContent, scriptTagId);
  }

  void removeScript(String scriptTagId) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    accessor.removeScript(context, scriptTagId);
  }

  void enableEngineLog() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    env.setUseEngineLog(true);
    extension.useEngineLog(context, true);
  }

  void disableEngineLog() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    env.setUseEngineLog(false);
    extension.useEngineLog(context, false);
  }

  void useEngineLog(boolean isUse) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    env.setUseEngineLog(isUse);
    extension.useEngineLog(context, isUse);
  }

  void enableTelluriumEngine() {
    env.setUseNewEngine(true);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useTeApi(context, true);
//    this.enableMacroCmd();
  }

  void disableTelluriumEngine() {
//      this.disableCache();
    env.setUseNewEngine(false);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useTeApi(context, false);

//    this.disableMacroCmd();
  }
  
  void useTelluriumEngine(boolean isUse){
    if(isUse){
      enableTelluriumEngine();
    }else{
      disableTelluriumEngine();
    }
  }

  //uid should use the format table2[2][3] for Table or list[2] for List

  def getUiElement(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    def obj = ui.walkTo(context, uid)

    return obj
  }

  String getConsoleInput() {
    return (String) System.in.withReader {
      it.readLine();
    }
  }


  void windowFocus() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.windowFocus(context)
  }

  void windowMaximize() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.windowMaximize(context)
  }

  String getBodyText() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getBodyText(context)
  }

  boolean isTextPresent(String pattern) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.isTextPresent(context, pattern)
  }

  String getExpression(String expression) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getExpression(context, expression)
  }

  String getCookie() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getCookie(context)
  }

  void runScript(String script) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    accessor.runScript(context, script)
  }

  String getEval(String script) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getEval(context, script)
  }

  void captureScreenshot(String filename) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    accessor.captureScreenshot(context, filename)
  }

  void captureEntirePageScreenshot(String filename, String kwargs) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    accessor.captureEntirePageScreenshot(context, filename, kwargs)
  }

  String captureScreenshotToString() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.captureScreenshotToString(context)
  }

  String captureEntirePageScreenshotToString(String kwargs) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.captureEntirePageScreenshotToString(context, kwargs)
  }

  void chooseCancelOnNextConfirmation() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.chooseCancelOnNextConfirmation(context)
  }

  void chooseOkOnNextConfirmation() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.chooseOkOnNextConfirmation(context)
  }

  void answerOnNextPrompt(String answer) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.answerOnNextPrompt(context, answer)
  }

  void goBack() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.goBack(context)
  }

  void refresh() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.refresh(context)
  }

  boolean isAlertPresent() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.isAlertPresent(context)
  }

  boolean isPromptPresent() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.isPromptPresent(context)
  }

  boolean isConfirmationPresent() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.isConfirmationPresent(context)
  }

  String getAlert() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAlert(context)
  }

  String getConfirmation() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getConfirmation(context)
  }

  String getPrompt() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getPrompt(context)
  }

  String getLocation() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getLocation(context)
  }

  String getTitle() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getTitle(context)
  }

  String[] getAllButtons() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllButtons(context)
  }

  String[] getAllLinks() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllLinks(context)
  }

  String[] getAllFields() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllFields(context)
  }

  String[] getAllWindowIds() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllWindowIds(context)
  }

  String[] getAllWindowNames() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllWindowNames(context)
  }

  String[] getAllWindowTitles() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    return accessor.getAllWindowTitles(context)
  }

  void waitForPageToLoad(int timeout) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    accessor.waitForPageToLoad(context, Integer.toString(timeout))
  }

  public String getXMLDocument() {

    return getEval(XML_DOCUMENT_SCRIPT)
  }

  void selectMainWindow() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.selectWindow(context, null)
  }

  void selectParentWindow() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.selectWindow(context, ".")
  }

  public void bugReport() {
    println "\nPlease cut and paste the following bug report to Tellurium user group (http://groups.google.com/group/tellurium-users)\n"
    println "---------------------------- Bug Report --------------------------------"

//      Environment env = env;
    if (env.lastUiModule != null) {
      println "\n-----------------------------------"
      println "UI Module " + env.lastUiModule + ": \n";

      println toString(env.lastUiModule);
      println "-----------------------------------\n"
    }

    println "\n-----------------------------------"
    println "HTML Source: \n";
    println getHtmlSource();
    println "-----------------------------------\n"

    if (env.lastUiModule != null) {
      println "\n-----------------------------------"
      println "Validate UI Module" + env.lastUiModule + ": \n";
      try {
//        getHtmlSource(env.lastUiModule);
        validate(env.lastUiModule);
      } catch (Exception e) {

      }
      println "-----------------------------------\n"
    }

    println "\n-----------------------------------"
    println "Environment: \n";
    //dump Environment variables
    println env.toString();
    println "-----------------------------------\n"

/*        println "\n-----------------------------------"
        println "Last Error: \n";
        println env.lastErrorDescription;
        println "-----------------------------------\n"*/

    println "\n-----------------------------------"
    println "System log: \n";
    println retrieveLastRemoteControlLogs();
    println "-----------------------------------\n"

    println "----------------------------    End     --------------------------------\n"
  }

  void altKeyUp() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.altKeyUp(context)
  }

  void altKeyDown() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.altKeyDown(context)
  }

  void ctrlKeyUp() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.ctrlKeyUp(context)
  }

  void ctrlKeyDown() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.ctrlKeyDown(context)
  }

  void shiftKeyUp() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.shiftKeyUp(context)
  }

  void shiftKeyDown() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.shiftKeyDown(context)
  }

  void metaKeyUp() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.metaKeyUp(context)
  }

  void metaKeyDown() {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    eventHandler.metaKeyDown(context)
  }

  boolean waitForCondition(String script, int timeoutInMilliSecond) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    accessor.waitForCondition(context, script, Integer.toString(timeoutInMilliSecond))
  }


  Number getXpathCount(String xpath) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return accessor.getXpathCount(context, xpath);
  }

  String captureNetworkTraffic(String type) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    return accessor.captureNetworkTraffic(context, type);
  }

  void addCustomRequestHeader(String key, String value) {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    accessor.addCustomRequestHeader(context, key, value);
  }

  Number getCssSelectorCount(String cssSelector) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    String jq = postProcessSelector(context, cssSelector.trim())

    return extension.getCssSelectorCount(context, jq)
  }

  String getXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getXPath() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
//    if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
    if (locator != null && (!locator.startsWith("//"))) {
      locator = "/" + locator
    }

    return locator
  }

  String getSelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreNewEngine())
    walkToWithException(context, uid)?.getSelector() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
    locator = optimizer.optimize(locator.trim())

    return JQUERY_SELECTOR + locator
  }       //flag to decide whether we should use jQuery Selector

  protected boolean exploreCssSelector() {
    return env.isUseCssSelector();
  }

  //flag to decide whether we should cache jQuery selector
  protected boolean exploreNewEngine() {
    return env.isUseNewEngine();
  }

  String getLocator(String uid) {
    if (this.exploreCssSelector()) {
      return getSelector(uid)
    }

    return getXPath(uid)
  }

  Number getLocatorCount(String locator) {
    if (this.exploreCssSelector())
      return getCssSelectorCount(locator)

    return getXpathCount(locator)
  }

  public void dump(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
    def obj = walkToWithException(context, uid)
    if (obj != null) {
      context.setNewUid(uid)
      obj.traverse(context)
      ArrayList list = context.getUidList()

      println(i18nBundle.getMessage("BaseDslContext.DumpLocatorInformation", uid))
      println("-------------------------------------------------------")
      list.each {String key ->
        String loc = getLocator(key)
        context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
        walkToWithException(context, key)

        if (this.exploreCssSelector()) {
          loc = this.postProcessSelector(context, loc)
        }
        println("${key}: ${loc}")
      }
      println("-------------------------------------------------------\n")
    }
  }
    public String toString(String uid) {
/*    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
    def obj = walkToWithException(context, uid);
    JSONArray arr = new JSONArray();
    context.setJSONArray(arr);
    obj.treeWalk(context);
    JSONArray jsa = context.getJSONArray();

    return jsa.toString();*/
      JSONArray ar = toJSONArray(uid);
      if(ar != null)
        return ar.toString();

      return null;
    }

    public JSONArray toJSONArray(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine())
      def obj = walkToWithException(context, uid);
      JSONArray arr = new JSONArray();
      context.setJSONArray(arr);
      obj.treeWalk(context);

      return context.getJSONArray();
    }

    public String toHTML(String uid) {
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreNewEngine());
      def obj = walkToWithException(context, uid);
      return obj.toHTML();
    }

    public String toHTML() {
      StringBuffer sb = new StringBuffer(128);
      ui.registry.each {String key, UiObject val ->
        sb.append(val.toHTML());
      }

      return sb.toString();
    }

    String getHtmlSource() {
      WorkflowContext context = WorkflowContext.getDefaultContext();
      return accessor.getHtmlSource(context);
    }

    String retrieveLastRemoteControlLogs(){
       WorkflowContext context = WorkflowContext.getDefaultContext();
       return accessor.retrieveLastRemoteControlLogs(context);
    }

    void setTimeout(long timeoutInMilliseconds) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      accessor.setTimeout(context, (new Long(timeoutInMilliseconds)).toString());
    }

    boolean isCookiePresent(String name) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return accessor.isCookiePresent(context, name);
    }

    String getCookieByName(String name) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return accessor.getCookieByName(context, name);
    }

    void createCookie(String nameValuePair, String optionsString) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      accessor.createCookie(context, nameValuePair, optionsString);
    }

    void deleteCookie(String name, String optionsString) {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      accessor.deleteCookie(context, name, optionsString);
    }

    void deleteAllVisibleCookies() {
      WorkflowContext context = WorkflowContext.getDefaultContext();

      accessor.deleteAllVisibleCookies(context);
    }

  public void setCookie(String cookieName, String value, Object options) {
     setCookieByJQuery(cookieName, value, options);
  }

  public void setCookie(String cookieName, String value) {
      setCookieByJQuery(cookieName, value);
  }

     //NEW Cookie APIs based on jQuery Cookie plugin
    void deleteAllCookiesByJQuery() {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      extension.deleteAllCookiesByJQuery(context)
    }

    void deleteCookieByJQuery(String cookieName) {
      WorkflowContext context = WorkflowContext.getDefaultContext()

      extension.deleteCookieByJQuery(context, cookieName);
    }

    void setCookieByJQuery(String cookieName, String value, def options){
      WorkflowContext context = WorkflowContext.getDefaultContext();

      extension.setCookieByJQuery(context, cookieName, value, options);
    }

    void setCookieByJQuery(String cookieName, String value){
      setCookieByJQuery(cookieName, value, null);
    }

    String getCookieByJQuery(String cookieName){
      WorkflowContext context = WorkflowContext.getDefaultContext();

      return extension.getCookieByJQuery(context, cookieName);
    }

}