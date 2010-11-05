package org.telluriumsource.ut

import org.telluriumsource.module.LoginFormModule

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2010
 * 
 */
class LoginForm_UT extends GroovyTestCase {
  public void testUim(){
    LoginFormModule lfm = new LoginFormModule();
    lfm.defineUiModule();
    lfm.dump("DspaceLoginForm")
    lfm.disableCssSelector();
    lfm.dump("DspaceLoginForm")    
  }
}
