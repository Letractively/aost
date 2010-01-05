package org.telluriumsource.connector

//import com.thoughtworks.selenium.Selenium
import org.telluriumsource.client.SeleniumClient
import org.telluriumsource.config.Configurable

import com.thoughtworks.selenium.CommandProcessor
import org.telluriumsource.bundle.BundleProcessor

/**
 * The connector that ties the Selenium server and Selenium Client together
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class SeleniumConnector implements Configurable {

    protected int port = 4444

	protected final String HTTPS_BASE_URL = "https://localhost:8443"

    protected final String HTTP_BASE_URL = "http://localhost:8080"

	protected CustomSelenium sel

    protected CustomSelenium customSelenium

    protected CommandProcessor commandProcessor

	protected String baseURL = HTTP_BASE_URL

    protected String browser = "*chrome"

    protected seleniumServerHost = "localhost"

    protected String userExtension = null
  
    protected def customClass = null

    protected String options = null
  
    public void connect(String url){
		sel.open(baseURL + url);
//        sel.cleanCache();
	}

    public void connectUrl(String url){
		sel.open(url);
//        sel.cleanCache();
	}

    public void configBrowser(String serverHost, int serverPort, String baseUrl, String browser, String browserOptions){
        this.seleniumServerHost = serverHost;
        this.port = serverPort;

        if(baseUrl != null)
          this.baseURL = baseUrl;

        this.browser = browser;

        if(browserOptions != null)
          this.options = browserOptions;
    }

    public void connectSeleniumServer() {

        //The selenium server startup logic is moved to EmbeddedSeleniumServer so that we can
        //decouple the selenium client and the selenium server.
//		if(runSeleniumServerInternally)
//			setUpSeleniumServer();
        
        //Works for https and http
//        sel = new DefaultSelenium(seleniumServerHost, port, browser, baseURL);
//        sel = new CustomSelenium(seleniumServerHost, port, browser, baseURL)
          // CustomSelenium with the new argument CommandProcess
          // This is done to make sure that implementing the Selenium Grid does
        // not break the inheritance model for CustomSelenium.
        customSelenium = new CustomSelenium (commandProcessor)
        customSelenium.setUserExt(this.userExtension)
        customSelenium.customClass = this.customClass
        if(this.options != null && this.options.trim().length() > 0){
          customSelenium.startSeleniumSession(seleniumServerHost, port, browser, baseURL, this.options)
        }else{
          customSelenium.startSeleniumSession(seleniumServerHost, port, browser, baseURL)
        }
        sel = customSelenium.getActiveSeleniumSession()

        SeleniumClient sc = new SeleniumClient()
        sc.client = customSelenium

        //MK: add the jquery location strategy

      //use Get to return the DOM reference.  
      //need to check if it is an attribute locator in the format of locator@attr

      //jQuery selector without any Cache mechanism

      sel.addLocationStrategy("jquery", '''
          return tellurium.locateElementByCSSSelector(locator, inDocument, inWindow);
      ''')


/*      //Cache Aware jQuery selector 
      sel.addLocationStrategy("jquerycache", '''
          return tellurium.locateElementByCacheAwareCSSSelector(locator, inDocument, inWindow);
      ''')*/
    }

	public void disconnectSeleniumServer() {
      if(customSelenium != null){
        CustomSelenium aseles = customSelenium.getActiveSeleniumSession();
		if(aseles != null){
            //flush out remaining commands in the command bundle before disconnection
            BundleProcessor processor = BundleProcessor.instance
            processor.flush()
            //clean up cache before close the connection
            aseles.cleanCache();
			customSelenium.closeSeleniumSession();
        }
      }
	}

    protected void initSeleniumClient(){
        SeleniumClient sc = new SeleniumClient()
        sc.client = customSelenium;
    }

}