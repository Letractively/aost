package org.telluriumsource.test.java;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.telluriumsource.framework.bootstrap.TelluriumSupport;
import org.telluriumsource.component.connector.SeleniumConnector;

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
        if (tellurium == null) {
            tellurium = TelluriumSupport.addSupport();
            tellurium.startServer(customConfig);
//        connector = (SeleniumConnector) tellurium.getProperty("connector");
//            tellurium.connectServer();
            connector = (SeleniumConnector) tellurium.getCurrentConnector();
        }
    }

    @AfterClass
    public static void tearDownForClass() {
        if(tellurium != null){
            tellurium.disconnectServer();
            tellurium.stopServer();
        }

    }
}
