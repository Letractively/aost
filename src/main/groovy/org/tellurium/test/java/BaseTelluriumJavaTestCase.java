package org.tellurium.test.java;

import org.tellurium.connector.SeleniumConnector;

/**
 *  The base Java Testcase class for Tellurium and it will not include the before class and after class methods
 *  so that the TestCase can be run as in a TestSuite
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *
 */
public abstract class BaseTelluriumJavaTestCase {
    protected static SeleniumConnector connector;

    public static void openUrl(String url){
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
}
