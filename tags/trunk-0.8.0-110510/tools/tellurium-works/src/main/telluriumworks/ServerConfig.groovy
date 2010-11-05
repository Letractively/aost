package telluriumworks

import groovy.beans.Bindable
import net.sourceforge.gvalidation.annotation.Validatable

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 24, 2010
 * 
 */
@Validatable
@Bindable
class ServerConfig {
  String serverStatus = "Not Running"
  boolean local = true
  int port = 4444
  String profile = ""
  boolean multipleWindow = false
  
  static constraints = {
      port(range: 0..65535)
  }

  public String toString(){
    return "[local: ${local}, port: ${port}, profile: ${profile}, multipleWindow: ${multipleWindow}]"
  }
}
