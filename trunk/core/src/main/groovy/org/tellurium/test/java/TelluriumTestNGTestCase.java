package org.tellurium.test.java;

import org.tellurium.bootstrap.TelluriumSupport;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.framework.TelluriumFramework;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * Tellurium Test Case to support TestNG
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2008
 */
public abstract class TelluriumTestNGTestCase extends BaseTelluriumJavaTestCase {

//    protected static TelluriumFramework tellurium;

//    @BeforeClass
    @BeforeTest(alwaysRun = true)
    public static void setUpForTest() {
        tellurium = TelluriumSupport.addSupport();
        tellurium.start(customConfig);
//        connector = (SeleniumConnector) tellurium.getProperty("connector");
        connector = (SeleniumConnector) tellurium.getConnector();
    }

//    @AfterClass
    @AfterTest(alwaysRun = true)
    public static void tearDownForTest() {
        if(tellurium != null)
            tellurium.stop();
    }
}
