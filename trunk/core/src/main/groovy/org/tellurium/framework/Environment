package org.tellurium.framework

import org.tellurium.config.Configurable

/**
 * Environment to hold runtime environment variables
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 25, 2009
 *
 */

@Singleton
public class Environment implements Configurable{

  //flag to decide whether we should use jQuery Selector
  protected boolean exploitJQuerySelector = false;

  //flag to decide whether we should cache jQuery selector
  protected boolean exploitSelectorCache = false;

  protected String locale = "en_US";

  protected boolean trace = false;

  protected boolean exploitBundle = false;

  protected int maxMacroCmd = 5;

  protected boolean captureScreenshot = false;

  def envVariables = [:];

  protected boolean exploitTelluriumApi = false;

  public boolean isUseJQuerySelector(){
    return this.exploitJQuerySelector;
  }

  public boolean isUseCache(){
    return this.exploitSelectorCache;
  }

  public boolean isUseBundle(){
    return this.exploitBundle;
  }

  public boolean isUseScreenshot(){
    return this.captureScreenshot;
  }

  public boolean isUseTrace(){
    return this.trace;
  }

  public boolean isUseTelluriumApi(){
    return this.exploitTelluriumApi;
  }

  public void useJQuerySelector(boolean isUse){
    this.exploitJQuerySelector = isUse;
  }

  public void useCache(boolean isUse){
    this.exploitSelectorCache = isUse;
  }

  public void useBundle(boolean isUse){
    this.exploitBundle = isUse;
  }

  public void useScreenshot(boolean isUse){
    this.captureScreenshot = isUse;
  }

  public void useTrace(boolean isUse){
    this.trace = isUse;
  }

  public void useTelluriumApi(boolean isUse) {
    this.exploitTelluriumApi = isUse;
  }

  public useMaxMacroCmd(int max){
    this.maxMacroCmd = max;
  }

  public int myMaxMacroCmd(){
    return this.maxMacroCmd;
  }

  public String myLocale(){
    return this.locale;
  }
  
  public void setCustomEnvironment(String name, Object value){
    envVariables.put(name, value);
  }

  public Object getCustomEnvironment(String name){
    return envVariables.get(name);
  }
}