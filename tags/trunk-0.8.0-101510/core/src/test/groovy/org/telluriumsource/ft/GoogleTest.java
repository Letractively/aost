package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.GoogleSearchModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         <p/>
 *         Date: May 22, 2010
 */
public class GoogleTest extends TelluriumJUnitTestCase {
    private static GoogleSearchModule gsm;

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void stat8cconnectToGoogle() {
        connectUrl("http://www.google.com");
    }


    @Test
    public void testGoogleSearch() {
        gsm.doGoogleSearch("tellurium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        gsm.doImFeelingLucky("tellurium automated Testing");
    }
}
