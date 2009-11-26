package org.tellurium.test.groovy

import org.tellurium.config.CustomConfig
import org.tellurium.i18n.InternationalizationManager;
import org.tellurium.connector.SeleniumConnector
import org.tellurium.framework.TelluriumFramework
import org.tellurium.framework.CachePolicy
import org.tellurium.util.Helper

abstract class BaseTelluriumGroovyTestCase extends GroovyTestCase{
    //custom configuration
	protected InternationalizationManager i18nManager = new InternationalizationManager()
    protected CustomConfig customConfig = null

    protected SeleniumConnector connector;
    protected TelluriumFramework aost

    public abstract SeleniumConnector getConnector()

	public geti18nManager()
	{
		return this.i18nManager;
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

    public void useJQuerySelector(boolean isUse){
      aost.useJQuerySelector(isUse);
    }

    public void useCache(boolean isUse){
      aost.useCache(isUse);
    }

    public void cleanCache(){
      aost.cleanCache();
    }

    public boolean isUsingCache(){
      return aost.isUsingCache();
    }

    public void setCacheMaxSize(int size){
      aost.setCacheMaxSize(size);
    }

    public int getCacheSize(){
      return aost.getCacheSize();
    }

    public int getCacheMaxSize(){
      return aost.getCacheMaxSize();
    }

    public Map<String, Long> getCacheUsage(){
      return aost.getCacheUsage();
    }

    public void useCachePolicy(CachePolicy policy){
      aost.useCachePolicy(policy);
    }

    public String getCurrentCachePolicy(){
      return aost.getCurrentCachePolicy();
    }

    public void useDefaultXPathLibrary(){
      aost.useDefaultXPathLibrary();
    }

    public void useJavascriptXPathLibrary(){
      aost.useJavascriptXPathLibrary();
    }

    public void useAjaxsltXPathLibrary() {
      aost.useAjaxsltXPathLibrary();
    }

    public void registerNamespace(String prefix, String namespace){
      aost.registerNamespace(prefix, namespace);
    }

    public String getNamespace(String prefix) {
      return aost.getNamespace(prefix);
    }

    public void pause(int milliseconds) {
      Helper.pause(milliseconds);
    }

    public void useMacroCmd(boolean isUse){
      aost.useMacroCmd(isUse);
    }

    public void setMaxMacroCmd(int max){
      aost.setMaxMacroCmd(max);
    }

    public int getMaxMacroCmd(){
      return aost.getMaxMacroCmd();
    }

    public void useTrace(boolean isUse){
      aost.useTrace(isUse);   
    }

    public void showTrace() {
      aost.showTrace();
    }

    public void setEnvironment(String name, Object value){
      aost.setEnvironment(name, value) ;
    }

    public Object getEnvironment(String name){
      return aost.getEnvironment(name);
    }
}  