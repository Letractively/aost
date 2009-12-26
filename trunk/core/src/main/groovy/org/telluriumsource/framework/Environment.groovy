package org.telluriumsource.framework
import org.telluriumsource.i18n.ResourceBundle;
import org.telluriumsource.i18n.IResourceBundle;

import org.telluriumsource.config.Configurable

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
  protected boolean exploitCssSelector = false;

  //flag to decide whether we should cache jQuery selector
  protected boolean exploitEngineCache = false;

  protected IResourceBundle resourceBundle = new ResourceBundle();

  protected boolean trace = false;

  protected boolean exploitBundle = false;

  protected int maxMacroCmd = 5;

  protected boolean captureScreenshot = false;

  protected String locale = "en_US";

  def envVariables = [:];

  protected boolean exploitTelluriumApi = false;

  public boolean isUseCssSelector(){
    return this.exploitCssSelector;
  }

  public boolean isUseCache(){
    return this.exploitEngineCache;
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

  public void useCssSelector(boolean isUse){
    this.exploitCssSelector = isUse;
  }

  public void useCache(boolean isUse){
    this.exploitEngineCache = isUse;
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

  public IResourceBundle myResourceBundle(){
    return this.resourceBundle;
  }

  public String myLocale(){
    return this.locale;
  }

  public void useLocale(String locale){
    this.locale = locale;  
  }

  public void setCustomEnvironment(String name, Object value){
    envVariables.put(name, value);
  }

  public Object getCustomEnvironment(String name){
    return envVariables.get(name);
  }
}