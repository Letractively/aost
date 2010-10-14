package org.telluriumsource.test.groovy

import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.framework.TelluriumFramework
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.util.Helper
import org.telluriumsource.entity.EngineState
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.dsl.TelluriumApi
import org.telluriumsource.dsl.SeleniumWrapper
import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.ui.builder.UiObjectBuilder

abstract class BaseTelluriumGroovyTestCase extends GroovyTestCase{

  //custom configuration
//	protected IResourceBundle i18nBundle
    protected CustomConfig customConfig = null

    protected SeleniumConnector conn;
    protected TelluriumFramework tellurium

    public BaseTelluriumGroovyTestCase(){
	}

    public abstract SeleniumConnector getConnector()

	public IResourceBundle getI18nResourceBundle()
	{
       return SessionManager.getSession().getI18nBundle();
	}

    public void openUrl(String url){
        getConnector().connectSeleniumServer()
        getConnector().connectUrl(url)
    }

    public void connectUrl(String url) {
         getConnector().connectUrl(url)
    }

    public void connectSeleniumServer(){
        getConnector().connectSeleniumServer()
    }

    public void disconnectSeleniumServer(){
         getConnector().disconnectSeleniumServer()
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation)
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost)
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost, String browserOptions){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost, browserOptions)
    }

  public SeleniumConnector getCurrentConnector(){
     return SessionManager.getSession().getByClass(SeleniumConnector.class);
  }

  //register ui object builder
  //users can overload the builders or add new builders for new ui objects
  //by call this method
  public void registerBuilder(String uiObjectName, UiObjectBuilder builder) {
    UiObjectBuilderRegistry registry = (UiObjectBuilderRegistry) SessionManager.getSession().getByClass(UiObjectBuilderRegistry.class);
    registry.registerBuilder(uiObjectName, builder);
  }

  public void useMacroCmd(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseBundle(isUse);
  }

  public void setMaxMacroCmd(int max) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setMaxMacroCmd(max);
  }

  public int getMaxMacroCmd() {
    RuntimeEnvironment env = SessionManager.getSession().env;
    return env.getMaxMacroCmd();
  }

  public void helpTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.helpTest();
  }

  public void noTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.helpTest();
  }

  public void useTrace(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseTrace(isUse);
  }

  public void generateBugReport(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setUseBugReport(isUse);
  }

  public void showTrace() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.showTrace();
  }

  public void setEnvironment(String name, Object value) {
    RuntimeEnvironment env = SessionManager.getSession().env;
    env.setEnvironmentVariable(name, value);
  }

  public Object getEnvironment(String name) {
    RuntimeEnvironment env = SessionManager.getSession().env;

    return env.getEnvironmentVariable(name);
  }

  public void useClosestMatch(boolean isUse){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    if (isUse) {
      wrapper.enableClosestMatch();
    } else {
      wrapper.disableClosestMatch();
    }
  }

  public void useCssSelector(boolean isUse) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    if (isUse) {
      wrapper.enableCssSelector();
    } else {
      wrapper.disableCssSelector();
    }
  }

  public void cleanCache() {
    TelluriumApi api = SessionManager.getSession().getApi();
    api.cleanCache();
  }

  public void setCacheMaxSize(int size) {
    TelluriumApi api = SessionManager.getSession().getApi();
    api.setCacheMaxSize(size);
  }

  public int getCacheSize() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheSize();
  }

  public int getCacheMaxSize() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheMaxSize();
  }

  public String getCacheUsage() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheUsage();
  }

  public void useCachePolicy(CachePolicy policy) {
    TelluriumApi api = SessionManager.getSession().getApi();

    if (policy != null) {
      switch (policy) {
        case CachePolicy.DISCARD_NEW:
          api.useDiscardNewCachePolicy();
          break;
        case CachePolicy.DISCARD_OLD:
          api.useDiscardOldCachePolicy();
          break;
        case CachePolicy.DISCARD_LEAST_USED:
          api.useDiscardLeastUsedCachePolicy();
          break;
        case CachePolicy.DISCARD_INVALID:
          api.useDiscardInvalidCachePolicy();
          break;
      }
    }
  }

  public String getCurrentCachePolicy() {
    TelluriumApi api = SessionManager.getSession().getApi();

    return api.getCurrentCachePolicy();
  }

  public void useDefaultXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useDefaultXPathLibrary();
  }

  public void useJavascriptXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useJavascriptXPathLibrary();
  }

  public void useAjaxsltXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useAjaxsltXPathLibrary();
  }

  public void allowNativeXpath(boolean allow) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.allowNativeXpath(allow);
  }

  public void registerNamespace(String prefix, String namespace) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.registerNamespace(prefix, namespace);
  }

  public String getNamespace(String prefix) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    return wrapper.getNamespace(prefix);
  }

  public void addScript(String scriptContent, String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.addScript(scriptContent, scriptTagId);
  }

  public void removeScript(String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.removeScript(scriptTagId);
  }

  public EngineState getEngineState(){
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getEngineState();
  }

  public void pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = SessionManager.getSession().getByClass(BundleProcessor.class);
    processor.flush()

    Helper.pause(milliseconds);
  }

  public void useEngineLog(boolean isUse){
    TelluriumApi api = SessionManager.getSession().getApi();
    api.useEngineLog(isUse);
  }

  public void dumpEnvironment(){
    println SessionManager.getSession().env.toString();
  }

  public void useTelluriumEngine(boolean isUse) {
    TelluriumApi api = SessionManager.getSession().getApi();
    if (isUse)
      api.enableTelluriumEngine();
    else
      api.disableTelluriumEngine();
  }
}