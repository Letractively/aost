package telluriumworks

import groovy.beans.Bindable
import net.sourceforge.gvalidation.annotation.Validatable

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Aug 25, 2010
 * 
 */
@Validatable
@Bindable
class TelluriumConfig {
  String browser = "*chrome"
  String serverHost = "localhost"
  int serverPort = 4444
  String macroCmd = "5"
  String option = ""
  boolean useTrace = false
  boolean useScreenShot = false
  boolean useTelluriumEngine = false
  String locale = "en_US"

  static constraints = {
      serverHost(nullable: false, blank: false)
      serverPort(range: 0..65535)
  }

  public String toString(){
    return "[browser: ${browser}, serverHost: ${serverHost}, serverPort: ${serverPort}, macroCmd: ${macroCmd}, option: ${option}, useTrace: ${useTrace}, useScreenShot: ${useScreenShot}, useTelluriumEngine: ${useTelluriumEngine}, locale: ${locale}]"
  }

  public void updateFrom(TelluriumConfig conf){
    if(conf != null){
      if(conf.browser != null && conf.browser.trim().length() > 0){
        this.browser = conf.browser  
      }
      if(conf.serverHost != null && conf.serverHost.trim().length() > 0){
        this.serverHost = conf.serverHost
      }
      this.serverPort = conf.serverPort
      
      if(conf.macroCmd != null && conf.macroCmd.trim().length() > 0){
        this.macroCmd = conf.macroCmd
      }
      this.option = conf.option
      this.useTrace = conf.useTrace
      this.useScreenShot = conf.useScreenShot
      this.useTelluriumEngine = conf.useTelluriumEngine
      if(conf.locale != null && conf.locale.trim().length() > 0){
        this.locale = conf.locale
      }
    }
  }
}
