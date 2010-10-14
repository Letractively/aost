package org.telluriumsource.ut

import org.telluriumsource.module.LoginFormModule
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2010
 * 
 */
class LoginForm_UT extends GroovyTestCase {

  public void setUp(){
    SessionManager.setSession(MockSessionFactory.getNewSession());
  }

  public void testUim(){
    LoginFormModule lfm = new LoginFormModule();
    lfm.defineUiModule();
    lfm.dump("DspaceLoginForm")
    lfm.disableCssSelector();
    lfm.dump("DspaceLoginForm")    
  }
}
