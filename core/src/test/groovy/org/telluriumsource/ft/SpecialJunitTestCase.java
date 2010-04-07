package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.SpecialModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 6, 2010
 */
public class SpecialJunitTestCase extends TelluriumMockJUnitTestCase {
    private static SpecialModule sm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Special");

        sm = new SpecialModule();
        sm.defineUi();
        //comment out the following line, the test will break
//        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("Special");
    }

    @Test
    public void testValidateUiModule(){
        sm.validate("Form");
    }

    @Test
    public void testGetHTMLSource(){
        sm.getHTMLSource("Form");
    }

    @Test
    public void testCheck(){
        sm.check("US", "123456789");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
