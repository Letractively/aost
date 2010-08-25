package telluriumworks

import groovy.beans.Bindable

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Aug 25, 2010
 * 
 */
@Bindable
class TelluriumConfig {
  String browser = "*chrome"
  String serverHost = "localhost"
  String serverPort = "4444"
  String macroCmd = "5"
  String option = ""
  boolean useTrace = false
  boolean useScreenShot = false
  String locale = "en_US"

  public String toString(){
    return "[browser: ${browser}, serverHost: ${serverHost}, serverPort: ${serverPort}, macroCmd: ${macroCmd}, option: ${option}, useTrace: ${useTrace}, useScreenShot: ${useScreenShot}, locale: ${locale}]"
  }

  public void updateFrom(TelluriumConfig conf){
    if(conf != null){
      if(conf.browser != null && conf.browser.trim().length() > 0){
        this.browser = conf.browser  
      }
      if(conf.serverHost != null && conf.serverHost.trim().length() > 0){
        this.serverHost = conf.serverHost
      }
      if(conf.serverPort != null && conf.serverPort.trim().length() > 0){
        this.serverPort = conf.serverPort
      }
      if(conf.macroCmd != null && conf.macroCmd.trim().length() > 0){
        this.macroCmd = conf.macroCmd
      }
      this.option = conf.option
      this.useTrace = conf.useTrace
      this.useScreenShot = conf.useScreenShot
      if(conf.locale != null && conf.locale.trim().length() > 0){
        this.locale = conf.locale
      }
    }
  }
}
