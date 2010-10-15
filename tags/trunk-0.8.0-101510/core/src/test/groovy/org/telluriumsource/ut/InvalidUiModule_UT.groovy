package org.telluriumsource.ut

import org.telluriumsource.exception.InvalidObjectTypeException
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory

/**
 *
 * @author Jian Fang(John.Jian.Fang@gmail.com)
 *
 * Date: Jun 16, 2009
 *
 */

public class InvalidUiModule_UT extends GroovyTestCase{

  public void setUp(){
    SessionManager.setSession(MockSessionFactory.getNewSession());
  }

  public void testException(){
    InvalidUIModule ium = new InvalidUIModule();
    try{
      ium.defineUi();
      fail("Expect invalidObjectTypeException!");
    }catch(InvalidObjectTypeException e){
      assertTrue(true);
    }
  }

}