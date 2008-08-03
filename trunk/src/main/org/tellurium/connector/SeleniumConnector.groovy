package org.tellurium.connector

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.tellurium.client.SeleniumClient

/**
 * The connector that ties the Selenium server and Selenium Client together
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class SeleniumConnector {

 //   private boolean useDefaultParams = true;
    protected int port = 4444;

	protected final String HTTPS_BASE_URL = "https://localhost:8443";

    protected final String HTTP_BASE_URL = "http://localhost:8080";

	protected Selenium sel;

	protected String baseURL = HTTP_BASE_URL;

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
        sel = new DefaultSelenium("localhost", port, "*chrome", baseURL);
//        sel = new DefaultSelenium("localhost", port, "*firefox /usr/lib64/firefox-3.0.1/firefox", baseURL)
        //hardcoded firefox path if it cannot be found from system paht
        //sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-3.0/firefox", baseURL);
        //make sure firefox-bin in your environment path
//        sel = new DefaultSelenium("localhost", port, "*chrome /usr/lib64/firefox-2.0.0.8/firefox-bin", baseURL);
        sel.start();

//        initSeleniumClient()
        SeleniumClient sc = new SeleniumClient()
        sc.client = sel

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