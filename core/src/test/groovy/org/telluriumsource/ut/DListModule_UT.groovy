package org.telluriumsource.ut

import org.telluriumsource.module.DListModule
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 9, 2010
 * 
 */
class DListModule_UT extends GroovyTestCase {

  public void setUp(){
    SessionManager.setSession(MockSessionFactory.getNewSession());
  }

  public void testDump(){
    DListModule dlm = new DListModule();
    dlm.defineUi();
    dlm.dump("div1");
    dlm.dump("div1.div2.list1");
  }
}
