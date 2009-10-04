package org.tellurium.test.java;

import org.tellurium.config.CustomConfig;
import org.tellurium.connector.SeleniumConnector;
import org.tellurium.i18n.InternationalizationManager;

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
	protected InternationalizationManager i18nManager = new InternationalizationManager();

    protected static SeleniumConnector connector;

    public static void openUrl(String url){
        connector.connectSeleniumServer();
        connector.connectUrl(url);
    }
    public InternationalizationManager geti18nManager()
	{
		return this.i18nManager;
	}

/*    protected static void configBrowser(String serverHost, int serverPort, String baseUrl, String browser, String browserOptions){
        connector.setProperty("seleniumServerHost", serverHost);
        connector.setProperty("port", serverPort);
        if(baseUrl != null)
            connector.setProperty("baseURL", baseUrl);
        connector.setProperty("browser", browser);
        if(browserOptions != null)
            connector.setProperty("options", browserOptions);
    }
    */

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String baseUrl, String browser, String browserOptions){
        connector.configBrowser(serverHost, serverPort, baseUrl, browser, browserOptions);
        openUrl(url);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser, String browserOptions){
       openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, browserOptions);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser){
       openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, null);
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

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost, String browserOptions){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost, browserOptions);
    }
}
