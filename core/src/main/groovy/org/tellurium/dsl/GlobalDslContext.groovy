package org.tellurium.dsl

import org.tellurium.event.EventHandler
import org.tellurium.access.Accessor
import org.tellurium.extend.Extension
import org.tellurium.framework.Environment
import org.tellurium.util.Helper
import org.tellurium.bundle.BundleProcessor
import org.tellurium.dispatch.Dispatcher

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
  protected boolean exploreJQuerySelector() {
    return Environment.instance.isUseJQuerySelector();
  }

  //flag to decide whether we should cache jQuery selector
  protected boolean exploreSelectorCache() {
    return Environment.instance.isUseCache()
  }

  public void useJQuerySelector() {
    Environment.instance.useJQuerySelector();
  }

  public void disableJQuerySelector() {
    Environment.instance.disableJQuerySelector();
  }

  public void enableSelectorCache() {
    Environment.instance.useCache();
//      this.exploreSelectorCache = true
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.enableSelectorCache(context)
  }

  public boolean disableSelectorCache() {
    Environment.instance.disableCache();
//      this.exploreSelectorCache = false
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.disableSelectorCache(context)
  }

  public boolean cleanSelectorCache() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.cleanSelectorCache(context)
  }

  public boolean getSelectorCacheState() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    return extension.getCacheState(context)
  }

  public void setCacheMaxSize(int size) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.setCacheMaxSize(context, size)
  }

  public int getCacheSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    return extension.getCacheSize(context).intValue()
  }

  public int getCacheMaxSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    return extension.getCacheMaxSize(context).intValue()
  }

  public Map<String, Long> getCacheUsage() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

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
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.useDiscardNewCachePolicy(context)
  }

  public void useDiscardOldCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.useDiscardOldCachePolicy(context)
  }

  public void useDiscardLeastUsedCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.useDiscardLeastUsedCachePolicy(context)
  }

  public void useDiscardInvalidCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.useDiscardInvalidCachePolicy(context)
  }

  public String getCurrentCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    return extension.getCurrentCachePolicy(context)
  }

  public void useDefaultXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())
    accessor.useXpathLibrary(context, DEFAULT_XPATH)
  }

  public void useJavascriptXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())
    accessor.useXpathLibrary(context, JAVASCRIPT_XPATH)
  }

  public void useAjaxsltXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())
    accessor.useXpathLibrary(context, AJAXSLT_XPATH)
  }

  public void registerNamespace(String prefix, String namespace) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    extension.addNamespace(context, prefix, namespace)
  }

  public String getNamespace(String prefix) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector(), this.exploreSelectorCache())

    return extension.getNamespace(context, prefix)
  }

  def pause(int milliseconds) {
    Helper.pause(milliseconds)
  }

  public void useMacroCmd() {
    BundleProcessor processor = BundleProcessor.instance
    processor.useBundleFeature()
  }

  public void disableMacroCmd() {
    BundleProcessor processor = BundleProcessor.instance
    processor.disableBundleFeature()
  }

  public void enableTrace(){
     Environment.instance.useTrace();
  }

  public void disableTrace(){
     Environment.instance.disableTrace();
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
}