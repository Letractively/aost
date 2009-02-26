package org.tellurium.test.java;

import org.tellurium.bootstrap.TelluriumSupport;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.framework.TelluriumFramework;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Tellurium Test Case to support TestNG
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2008
 */
public abstract class TelluriumTestNGTestCase extends BaseTelluriumJavaTestCase {

    protected static TelluriumFramework tellurium;

    @BeforeClass
    public static void setUpForClass() {
        tellurium = TelluriumSupport.addSupport();
        tellurium.start(customConfig);
//        connector = (SeleniumConnector) tellurium.getProperty("connector");
        connector = (SeleniumConnector) tellurium.getConnector();
    }

    @AfterClass
    public static void tearDownForClass() {
        if(tellurium != null)
            tellurium.stop();
    }
}
