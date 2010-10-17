package org.telluriumsource.ut;

import org.junit.*;
import org.telluriumsource.framework.SessionManager;
import org.telluriumsource.mock.MockSessionFactory;
import org.telluriumsource.module.AAModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 17, 2010
 */
public class AATest_UT {
    private static AAModule aam;

    @BeforeClass
    public static void setup(){
        SessionManager.setSession(MockSessionFactory.getNewSession());

        aam = new AAModule();
        aam.defineUi();
    }

    @org.junit.Test
    public void testDump(){
        aam.dump("A");
    }

    @org.junit.Test
    public void testAA(){
        
    }
}
