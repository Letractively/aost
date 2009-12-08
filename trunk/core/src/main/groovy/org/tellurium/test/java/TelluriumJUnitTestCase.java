package org.tellurium.test.java;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.tellurium.bootstrap.TelluriumSupport;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.framework.TelluriumFramework;

/**
 * Java TestCase with @BeforeClass and @AfterClass defined
 *
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
public abstract class TelluriumJUnitTestCase extends BaseTelluriumJavaTestCase {

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
