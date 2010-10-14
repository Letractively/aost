package org.telluriumsource.ft;

import org.junit.Before;
import org.telluriumsource.module.SuperModule;
import org.telluriumsource.test.java.TelluriumMockTestNGTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: May 20, 2010
 */
public class SuperTestCase  extends TelluriumMockTestNGTestCase {

    private static SuperModule sm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("Super");
        sm = new SuperModule();
        sm.defineUi();
        useTelluriumEngine(true);
        useEngineLog(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("Super");
    }

    @Test
    public void testValidate(){
        sm.validate("comments");
    }

    @Test
    public void testList(){  
        useClosestMatch(true);
        sm.isElementPresent("comments.title");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
