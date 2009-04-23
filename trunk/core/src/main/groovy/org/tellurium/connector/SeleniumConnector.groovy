package org.tellurium.connector

import com.thoughtworks.selenium.Selenium
import org.tellurium.client.SeleniumClient
import org.tellurium.config.Configurable
import org.tellurium.connector.CustomSelenium
import com.thoughtworks.selenium.CommandProcessor

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

	protected Selenium sel

    protected CustomSelenium customSelenium

    protected CommandProcessor commandProcessor

	protected String baseURL = HTTP_BASE_URL

    protected String browser = "*chrome"

    protected seleniumServerHost = "localhost"

    protected def customClass = null
  
    public void connect(String url){
		sel.open(baseURL + url);
	}

    public void connectUrl(String url){
		sel.open(url);
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
          customSelenium.customClass = this.customClass 
          customSelenium.startSeleniumSession(seleniumServerHost, port, browser, baseURL)
          sel = customSelenium.getActiveSeleniumSession()
//        sel = new DefaultSelenium("localhost", port, "*firefox /usr/lib64/firefox-3.0.1/firefox", baseURL)
        //hardcoded firefox path if it cannot be found from system paht
        //sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-3.0/firefox", baseURL);
        //make sure firefox-bin in your environment path
//        sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-2.0.0.8/firefox-bin", baseURL);
          // No need to start the selenium session.
//        sel.start()

//        initSeleniumClient()
        SeleniumClient sc = new SeleniumClient()
        sc.client = customSelenium

        //MK: add the jquery location strategy

      //use Get to return the DOM reference.  
      //need to check if it is an attribute locator in the format of locator@attr

     sel.addLocationStrategy("jquery", '''
        return tellurium.locateElementByJQuery(locator, inDocument, inWindow);
     ''')

        sel.addLocationStrategy("jqueryall", '''
          var found = jQuery(inDocument).find(locator);
          if(found.length > 0){
              return found;
          } else {
              return null;
          }
          ''')

    }

	public void disconnectSeleniumServer() {

		if(customSelenium.getActiveSeleniumSession() != null)
			customSelenium.closeSeleniumSession();
	}

    protected void initSeleniumClient(){
        SeleniumClient sc = new SeleniumClient()
        sc.client = customSelenium;
    }

}