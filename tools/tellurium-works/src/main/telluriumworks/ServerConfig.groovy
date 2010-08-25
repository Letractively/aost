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
  @Bindable boolean local
  @Bindable String port
  @Bindable String profile
  @Bindable boolean multipleWindow

  public String toString(){
    return "[local: ${local}, port: ${port}, profile: ${profile}, multipleWindow: ${multipleWindow}]"
  }
}
