package aost.test;

import aost.connector.SeleniumConnector;
import aost.framework.AostFramework;
import aost.bootstrap.AostSupport;
import org.junit.BeforeClass;
import org.junit.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.AfterClass;

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
public abstract class AostJavaTestCase {

    protected static SeleniumConnector connector;
    protected static AostFramework aost;
    
    public static void openUrl(String url){
//        baseURL = url
        connector.connectSeleniumServer();
        connector.connectUrl(url);
    }

    public static void connectUrl(String url) {
         connector.connectUrl(url);
    }

    public static void connectSeleniumServer(){
        connector.connectSeleniumServer();
    }

    public static void disconnectSeleniumServer(){
         connector.disconnectSeleniumServer();
    }

    @BeforeClass
    public static void setUpForClass() {
        AostFramework aost = AostSupport.addSupport();
        aost.start();
        connector = (SeleniumConnector) aost.getProperty("connector");
    }

    @AfterClass
    public static void tearDownForClass() {
        if(aost != null)
            aost.stop();
    }
}
