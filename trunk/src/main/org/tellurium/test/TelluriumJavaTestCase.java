package org.tellurium.test;

import org.tellurium.connector.SeleniumConnector;
import org.tellurium.framework.TelluriumFramework;
import org.tellurium.bootstrap.TelluriumSupport;
import org.junit.BeforeClass;
import org.junit.AfterClass;

/**
 * Java TestCase with @BeforeClass and @AfterClass defined
 *
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
public abstract class TelluriumJavaTestCase extends TelluriumJavaBaseTestCase {

    protected static TelluriumFramework tellurium;

    @BeforeClass
    public static void setUpForClass() {
        TelluriumFramework tellurium = TelluriumSupport.addSupport();
        tellurium.start();
        connector = (SeleniumConnector) tellurium.getProperty("connector");
    }

    @AfterClass
    public static void tearDownForClass() {
        if(tellurium != null)
            tellurium.stop();
    }
}
