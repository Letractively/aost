package org.telluriumsource.dsl

import org.telluriumsource.event.EventHandler
import org.telluriumsource.access.Accessor
import org.telluriumsource.extend.Extension
import org.telluriumsource.framework.Environment
import org.telluriumsource.util.Helper

import org.telluriumsource.dispatch.Dispatcher

/**
 * Global methods, which should not be tired to an individual UI module
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 26, 2009
 *
 *
 */

public class GlobalDslContext {

  //decoupling eventhandler, locateProcessor, and accessor from UI objects
  //and let DslContext to handle all of them directly. This will also make
  //the framework reconfiguration much easier.
  EventHandler eventHandler = new EventHandler()
  Accessor accessor = new Accessor()
  Extension extension = new Extension()

  //flag to decide whether we should use jQuery Selector
  protected boolean exploreCssSelector() {
    return Environment.instance.isUseCssSelector();
  }

  //flag to decide whether we should cache jQuery selector
  protected boolean exploreEngineCache() {
    return Environment.instance.isUseCache()
  }

  public void useCssSelector() {
    Environment.instance.useCssSelector(true);
  }

  public void disableCssSelector() {
    Environment.instance.useCssSelector(false);
  }

  public void setUseCacheFlag(boolean isUse){
    Environment.instance.useCache(isUse);
  }

  public void enableCache() {
    Environment.instance.useCache(true);
//      this.exploreEngineCache = true
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.enableCache(context)
  }

  public boolean disableCache() {
    Environment.instance.useCache(false);
//      this.exploreEngineCache = false
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.disableCache(context)
  }

  public boolean cleanCache() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.cleanCache(context)
  }

  public boolean getCacheState() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    return extension.getCacheState(context)
  }

  public void setCacheMaxSize(int size) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.setCacheMaxSize(context, size)
  }

  public int getCacheSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    return extension.getCacheSize(context).intValue()
  }

  public int getCacheMaxSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    return extension.getCacheMaxSize(context).intValue()
  }

  public Map<String, Long> getCacheUsage() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    String out = extension.getCacheUsage(context);
    ArrayList list = (ArrayList) parseSeleniumJSONReturnValue(out)
    Map<String, Long> usages = new HashMap<String, Long>()
    list.each {Map elem ->
      elem.each {key, value ->
        usages.put(key, value)
      }
    }

    return usages
  }

  public void useDiscardNewCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.useDiscardNewCachePolicy(context)
  }

  public void useDiscardOldCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.useDiscardOldCachePolicy(context)
  }

  public void useDiscardLeastUsedCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.useDiscardLeastUsedCachePolicy(context)
  }

  public void useDiscardInvalidCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.useDiscardInvalidCachePolicy(context)
  }

  public String getCurrentCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    return extension.getCurrentCachePolicy(context)
  }

  public void useDefaultXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())
    accessor.useXpathLibrary(context, DEFAULT_XPATH)
  }

  public void useJavascriptXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())
    accessor.useXpathLibrary(context, JAVASCRIPT_XPATH)
  }

  public void useAjaxsltXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())
    accessor.useXpathLibrary(context, AJAXSLT_XPATH)
  }

  public void registerNamespace(String prefix, String namespace) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    extension.addNamespace(context, prefix, namespace)
  }

  public String getNamespace(String prefix) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreEngineCache())

    return extension.getNamespace(context, prefix)
  }

  def pause(int milliseconds) {
    Helper.pause(milliseconds)
  }

  public void useMacroCmd() {
     Environment.instance.useBundle(true);
  }

  public void disableMacroCmd() {
      Environment.instance.useBundle(false);
  }

  public void enableTrace(){
     Environment.instance.useTrace(true);
  }

  public void disableTrace(){
     Environment.instance.useTrace(false);
  }
  
  public void showTrace() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.showTrace()
  }

  public void setEnvironment(String name, Object value){
    Environment.instance.setCustomEnvironment(name, value);
  }

  public Object getEnvironment(String name){
    return Environment.instance.getCustomEnvironment(name);
  }

  public useMaxMacroCmd(int max){
    Environment.instance.useMaxMacroCmd(max);
  }

  public int getMaxMacroCmd(){
    return Environment.instance.myMaxMacroCmd();
  }

  public boolean isUseTelluriumApi(){
    return Environment.instance.isUseTelluriumApi();
  }

  public void useTelluriumApi(boolean isUse){
    Environment.instance.useTelluriumApi(isUse);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useTeApi(context, isUse);
  }
  
  void allowNativeXpath(boolean allow) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.allowNativeXpath(context, allow)
  }
}