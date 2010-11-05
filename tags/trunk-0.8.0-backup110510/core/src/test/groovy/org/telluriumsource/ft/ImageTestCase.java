package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.ImageModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: May 20, 2010
 */
public class ImageTestCase extends TelluriumMockJUnitTestCase {
    private static ImageModule im;

    @BeforeClass
    public static void setUp(){
        registerHtmlBody("Image");
        im = new ImageModule();
        im.defineUi();
        useTelluriumEngine(true);
        useEngineLog(true);
        useTrace(true);
    }

    @Before
    public void connect(){
        connect("Image");
    }

    @Test
    public void testId(){
        im.isElementPresent("U3");
        im.isElementPresent("U2");
        im.isElementPresent("U1");
    }

    @AfterClass
    public static void tearDown(){
       showTrace();
    }
}
