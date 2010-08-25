package telluriumworks

import groovy.beans.Bindable

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 24, 2010
 * 
 */
@Bindable
class ServerConfig {
  boolean local = true
  String port = "4444"
  String profile = ""
  boolean multipleWindow = false

  public String toString(){
    return "[local: ${local}, port: ${port}, profile: ${profile}, multipleWindow: ${multipleWindow}]"
  }
}
