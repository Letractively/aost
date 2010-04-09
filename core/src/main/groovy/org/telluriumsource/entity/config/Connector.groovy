package org.telluriumsource.entity.config

import org.json.simple.JSONObject

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
  public static String SERVER_HOST = "serverHost";
  String serverHost = "localhost";

  //server port number the client needs to connect
  public static String PORT = "port";
  String port = "4444";

  //base URL
  public static String BASE_URL = "baseUrl";
  String baseUrl = "http://localhost:8080";

  //Browser setting, valid options are
  //  *firefox [absolute path]
  //  *iexplore [absolute path]
  //  *chrome
  //  *iehta
  public static String BROWSER = "browser";
  String browser = "*chrome";

  //user's class to hold custom selenium methods associated with user-extensions.js
  //should in full class name, for instance, "com.mycom.CustomSelenium"
  public static String CUSTOM_CLASS = "customClass";
  String customClass = "";

  //browser options such as
  //    options = "captureNetworkTraffic=true, addCustomRequestHeader=true"
  public static String OPTIONS = "options";
  String options = "";

  def Connector() {
  }

  def Connector(Map map) {
    this.serverHost = map.get(SERVER_HOST);
    this.port = map.get(PORT);
    this.baseUrl = map.get(BASE_URL);
    this.customClass = map.get(CUSTOM_CLASS);
    this.options = map.get(OPTIONS);
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(SERVER_HOST, this.serverHost);
    obj.put(PORT, this.port);
    obj.put(CUSTOM_CLASS, this.customClass);
    obj.put(OPTIONS, this.options);

    return obj;
  }
}
