package org.telluriumsource.entity.config

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Connector {
  //selenium server host
  //please change the host if you run the Selenium server remotely
  String serverHost = "localhost";

  //server port number the client needs to connect
  String port = "4444";
  //base URL
  String baseUrl = "http://localhost:8080";

  //Browser setting, valid options are
  //  *firefox [absolute path]
  //  *iexplore [absolute path]
  //  *chrome
  //  *iehta
  String browser = "*chrome";

  //user's class to hold custom selenium methods associated with user-extensions.js
  //should in full class name, for instance, "com.mycom.CustomSelenium"
  String customClass = "";

  //browser options such as
  //    options = "captureNetworkTraffic=true, addCustomRequestHeader=true"
  String options = "";

}
