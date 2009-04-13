package org.tellurium.connector

import com.thoughtworks.selenium.Selenium
import org.tellurium.client.SeleniumClient
import org.tellurium.config.Configurable
import org.tellurium.connector.CustomSelenium

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

	protected String baseURL = HTTP_BASE_URL

    protected String browser = "*chrome"

    protected seleniumServerHost = "localhost"

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
        sel = new CustomSelenium(seleniumServerHost, port, browser, baseURL)
//        sel = new DefaultSelenium("localhost", port, "*firefox /usr/lib64/firefox-3.0.1/firefox", baseURL)
        //hardcoded firefox path if it cannot be found from system paht
        //sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-3.0/firefox", baseURL);
        //make sure firefox-bin in your environment path
//        sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-2.0.0.8/firefox-bin", baseURL);
        sel.start()

//        initSeleniumClient()
        SeleniumClient sc = new SeleniumClient()
        sc.client = sel

        //MK: add the jquery location strategy

      /*
        sel.addLocationStrategy("jquery", '''
          var found = $(inDocument).find(locator);
          if(found.length == 1){
              //for now only support of selecting 1 element.
              return found[0];
          } else {
              return null;
          }
          ''')

        */

      //use Get to return the DOM reference.  
      //need to check if it is an attribute locator in the format of locator@attr
      sel.addLocationStrategy("jquery", '''
        var loc = locator;
        var attr = null;
        var isattr = false;         
        var inx = locator.lastIndexOf('@');
        if(inx != -1){
          loc = locator.substring(0, inx);
          attr = locator.substring(inx + 1);
          isattr = true;
        }
        var found = $(inDocument).find(loc);
        if(found.length == 1 ){
            if(isattr){
               return found[0].getAttributeNode(attr);
            }else{
              return found[0];
            }
        }else if(found.length > 1){
            if(isattr){
              return found.get().getAttributeNode(attr);
            }else{
              return found.get();
            }
        }else{
            return null;
        }''')

        sel.addLocationStrategy("jqueryall", '''
          var found = $(inDocument).find(locator);
          if(found.length > 0){
              return found;
          } else {
              return null;
          }
          ''')

    }

	public void disconnectSeleniumServer() {

		if(sel != null)
			sel.stop();
	}

    protected void initSeleniumClient(){
        SeleniumClient sc = new SeleniumClient()
        sc.client = sel
    }

}