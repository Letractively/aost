package org.telluriumsource.test.java;

import org.telluriumsource.component.bundle.BundleProcessor;
import org.telluriumsource.dsl.IDslContext;
import org.telluriumsource.dsl.SeleniumWrapper;
import org.telluriumsource.dsl.TelluriumApi;
import org.telluriumsource.framework.RuntimeEnvironment;
import org.telluriumsource.framework.SessionManager;
import org.telluriumsource.framework.config.CustomConfig;
import org.telluriumsource.component.connector.SeleniumConnector;
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.TelluriumFramework;
import org.telluriumsource.entity.CachePolicy;
import org.telluriumsource.entity.EngineState;
import org.telluriumsource.ui.builder.UiObjectBuilder;
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry;
import org.telluriumsource.util.Helper;

/**
 * The base Java Testcase class for Tellurium and it will not include the before class and after class methods
 * so that the TestCase can be run as in a TestSuite
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
public abstract class BaseTelluriumJavaTestCase {
    //custom configuration
    protected static CustomConfig customConfig = null;
//    protected IResourceBundle i18nBundle;

    protected static SeleniumConnector connector;

    protected static TelluriumFramework tellurium;

    public static void openUrl(String url) {
        connector.connectSeleniumServer();
        connector.connectUrl(url);
    }

    public static IResourceBundle getI18nResourceBundle() {
        return SessionManager.getSession().getI18nBundle();
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String baseUrl, String browser, String browserOptions) {
        connector.configBrowser(serverHost, serverPort, baseUrl, browser, browserOptions);
        openUrl(url);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser, String browserOptions) {
        openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, browserOptions);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser) {
        openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, null);
    }

    public static void connectUrl(String url) {
        connector.connectUrl(url);
    }

    public static void connectSeleniumServer() {
        connector.connectSeleniumServer();
    }

    public static void disconnectSeleniumServer() {
        connector.disconnectSeleniumServer();
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation);
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost);
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost, String browserOptions) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost, browserOptions);
    }


  public static SeleniumConnector getCurrentConnector(){
     return (SeleniumConnector) SessionManager.getSession().getByClass(SeleniumConnector.class);
  }

  //register ui object builder
  //users can overload the builders or add new builders for new ui objects
  //by call this method
  public static void registerBuilder(String uiObjectName, UiObjectBuilder builder) {
    UiObjectBuilderRegistry registry = (UiObjectBuilderRegistry) SessionManager.getSession().getByClass(UiObjectBuilderRegistry.class);
    registry.registerBuilder(uiObjectName, builder);
  }

  public static void useMacroCmd(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    env.setUseBundle(isUse);
  }

  public static void setMaxMacroCmd(int max) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    env.setMaxMacroCmd(max);
  }

  public static int getMaxMacroCmd() {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    return env.getMaxMacroCmd();
  }

  public static void helpTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.helpTest();
  }

  public static void noTest(){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.helpTest();
  }

  public static void useTrace(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    env.setUseTrace(isUse);
  }

  public static void generateBugReport(boolean isUse) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    env.setUseBugReport(isUse);
  }

  public static void showTrace() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.showTrace();
  }

  public static void setEnvironment(String name, Object value) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();
    env.setEnvironmentVariable(name, value);
  }

  public static Object getEnvironment(String name) {
    RuntimeEnvironment env = SessionManager.getSession().getEnv();

    return env.getEnvironmentVariable(name);
  }

  public static void useClosestMatch(boolean isUse){
    IDslContext api = (IDslContext) SessionManager.getSession().getApi();
    if (isUse) {
      api.enableClosestMatch();
    } else {
      api.disableClosestMatch();
    }
  }

  public static void useCssSelector(boolean isUse) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    if (isUse) {
      wrapper.enableCssSelector();
    } else {
      wrapper.disableCssSelector();
    }
  }

  public static void cleanCache() {
    TelluriumApi api = SessionManager.getSession().getApi();
    api.cleanCache();
  }

  public static void setCacheMaxSize(int size) {
    TelluriumApi api = SessionManager.getSession().getApi();
    api.setCacheMaxSize(size);
  }

  public static int getCacheSize() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheSize();
  }

  public static int getCacheMaxSize() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheMaxSize();
  }

  public static String getCacheUsage() {
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getCacheUsage();
  }

  public static void useCachePolicy(CachePolicy policy) {
    TelluriumApi api = SessionManager.getSession().getApi();

    if (policy != null) {
      switch (policy) {
        case DISCARD_NEW:
          api.useDiscardNewCachePolicy();
          break;
        case DISCARD_OLD:
          api.useDiscardOldCachePolicy();
          break;
        case DISCARD_LEAST_USED:
          api.useDiscardLeastUsedCachePolicy();
          break;
        case DISCARD_INVALID:
          api.useDiscardInvalidCachePolicy();
          break;
      }
    }
  }

  public static String getCurrentCachePolicy() {
    TelluriumApi api = SessionManager.getSession().getApi();

    return api.getCurrentCachePolicy();
  }

  public static void useDefaultXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useDefaultXPathLibrary();
  }

  public static void useJavascriptXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useJavascriptXPathLibrary();
  }

  public static void useAjaxsltXPathLibrary() {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.useAjaxsltXPathLibrary();
  }

  public static void allowNativeXpath(boolean allow) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.allowNativeXpath(allow);
  }

  public static void registerNamespace(String prefix, String namespace) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.registerNamespace(prefix, namespace);
  }

  public static String getNamespace(String prefix) {
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    return wrapper.getNamespace(prefix);
  }

  public static void addScript(String scriptContent, String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.addScript(scriptContent, scriptTagId);
  }

  public static void removeScript(String scriptTagId){
    SeleniumWrapper wrapper = SessionManager.getSession().getWrapper();
    wrapper.removeScript(scriptTagId);
  }

  public static EngineState getEngineState(){
    TelluriumApi api = SessionManager.getSession().getApi();
    return api.getEngineState();
  }

  public static void pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = SessionManager.getSession().getByClass(BundleProcessor.class); 
    processor.flush();

    Helper.pause(milliseconds);
  }

  public static void useEngineLog(boolean isUse){
    TelluriumApi api = SessionManager.getSession().getApi();
    api.useEngineLog(isUse);
  }

    public static void useTelluriumEngine(boolean isUse) {
        TelluriumApi api = SessionManager.getSession().getApi();
        if (isUse)
            api.enableTelluriumEngine();
        else
            api.disableTelluriumEngine();
    }

  public static void dumpEnvironment(){
    System.out.println(SessionManager.getSession().getEnv().toString());
  }
}
