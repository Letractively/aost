package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TwoDimModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 19, 2010
 */
public class TwoDimJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static TwoDimModule tdm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("TwoDim");

        tdm = new TwoDimModule();
        tdm.defineUi();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("TwoDim");
    }

    @Test
    public void testValidateUiModule(){
        tdm.validate("Table");
    }

    @Test
    public void testGetHTMLSource(){
        tdm.getHTMLSource("Table");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
