package org.tellurium.test.java;

import org.tellurium.config.CustomConfig;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.dispatch.Dispatcher;

/**
 *  The base Java Testcase class for Tellurium and it will not include the before class and after class methods
 *  so that the TestCase can be run as in a TestSuite
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *
 */
public abstract class BaseTelluriumJavaTestCase {
    //custom configuration
    protected static CustomConfig customConfig = null;
    
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

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation);
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost);
    }
}
