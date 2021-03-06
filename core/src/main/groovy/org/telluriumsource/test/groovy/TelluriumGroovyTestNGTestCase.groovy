package org.telluriumsource.test.groovy

import org.telluriumsource.framework.bootstrap.TelluriumSupport
import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.framework.TelluriumFramework
import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.telluriumsource.framework.SessionManager

/**
 * Groovy Test NG Test Case
 *
 * @author John.Jian.Fang@gmail.com
 *
 * Date: Mar 9, 2009
 *
 */

abstract public class TelluriumGroovyTestNGTestCase {
  //custom configuration
  protected CustomConfig customConfig = null;
//  protected IResourceBundle i18nBundle = this.&getI18nResourceBundle;

  protected SeleniumConnector conn;
  protected TelluriumFramework tellurium

  public SeleniumConnector getConnector() {
    return conn;
  }

  abstract public void initUi()

  public void openUrl(String url) {
    getConnector().connectSeleniumServer()
    getConnector().connectUrl(url)
  }
  public IResourceBundle getI18nBundle()
  {
    return (IResourceBundle)SessionManager.getSession().getByName("i18nBundle");
  }
  public void connectUrl(String url) {
    getConnector().connectUrl(url)
  }

  public void connectSeleniumServer() {
    getConnector().connectSeleniumServer()
  }

  public void disconnectSeleniumServer() {
    getConnector().disconnectSeleniumServer()
  }

  public void setCustomConfig(boolean runInternally, int port, String browser,
                              boolean useMultiWindows, String profileLocation) {
    customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation)
  }

  public void setCustomConfig(boolean runInternally, int port, String browser,
                              boolean useMultiWindows, String profileLocation, String serverHost) {
    customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost)
  }

  public SeleniumConnector getCurrentConnector(){
     return SessionManager.getSession().getByClass(SeleniumConnector.class); 
  }
  
  @BeforeClass
  protected void setUpForClass() {
    tellurium = TelluriumSupport.addSupport()
    tellurium.start()
    tellurium.startServer(customConfig)
    conn = getCurrentConnector();
    initUi()
  }

  @AfterClass
  protected void tearDownForClass() {
    if (tellurium != null){
      tellurium.stopServer()
      tellurium.stop()
    }
  }
}