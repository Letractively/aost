package org.telluriumsource.ut

import org.telluriumsource.server.Xvfb

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 20, 2010
 * 
 */
class Xvfb_UT extends GroovyTestCase {

  public void testRun(){
    Xvfb xvfb = new Xvfb();
    xvfb.run();
  }

}
