package org.tellurium.test.java;

import org.tellurium.framework.TelluriumFramework;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.bootstrap.TelluriumSupport;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

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
        tellurium.start();
        connector = (SeleniumConnector) tellurium.getProperty("connector");
    }

    @AfterClass
    public static void tearDownForClass() {
        if(tellurium != null)
            tellurium.stop();
    }
}
