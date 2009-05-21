package org.tellurium.test

import org.tellurium.bundle.CommandBundleProcessor

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */

public class CommandBundleProcessor_UT extends GroovyTestCase{

    public void testSingleton(){
      CommandBundleProcessor p1 = CommandBundleProcessor.instance;
      CommandBundleProcessor p2 = CommandBundleProcessor.instance;
      assertTrue(p1 == p2);
    }

}