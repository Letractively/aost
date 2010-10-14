package org.telluriumsource.ut

import org.telluriumsource.module.GoogleSearchModule
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 31, 2009
 *
 */

public class UiModuleJSonConverter_UT extends GroovyTestCase {

  public void setUp(){
    SessionManager.setSession(MockSessionFactory.getNewSession());
  }

  public void testToJSONString(){
    GoogleSearchModule gsm = new GoogleSearchModule();
    gsm.defineUi();

//    println gsm.jsonify("Google");
    println gsm.toString("Google");
  }
}
