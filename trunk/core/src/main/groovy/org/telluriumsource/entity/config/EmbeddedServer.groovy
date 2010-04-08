package org.telluriumsource.entity.config

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class EmbeddedServer {
  //port number
  String port = "4444";

  //whether to use multiple windows
  boolean useMultiWindows = false;

  //whether to trust all SSL certs, i.e., option "-trustAllSSLCertificates"
  boolean trustAllSSLCertificates = true;

  //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
  boolean runInternally = false;

  //By default, Selenium proxies every browser request; set this flag to make the browser use proxy only for URLs containing '/selenium-server'
  boolean avoidProxy = false;

  //stops re-initialization and spawning of the browser between tests
  boolean browserSessionReuse = false;

  //enabling this option will cause all user cookies to be archived before launching IE, and restored after IE is closed.
  boolean ensureCleanSession = false;

  //debug mode, with more trace information and diagnostics on the console
  boolean debugMode = false;

  //interactive mode
  boolean interactive = false;

  //an integer number of seconds before we should give up
  int timeoutInSeconds = 30;

  //profile location
  String profile = "";

  //user-extension.js file
  String userExtension = "";

}
