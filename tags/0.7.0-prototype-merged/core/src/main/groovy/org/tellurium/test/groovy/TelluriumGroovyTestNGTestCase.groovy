package org.tellurium.test.groovy

import org.tellurium.bootstrap.TelluriumSupport
import org.tellurium.config.CustomConfig
import org.tellurium.connector.SeleniumConnector
import org.tellurium.framework.TelluriumFramework
import org.tellurium.i18n.InternationalizationManager;
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass

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
  protected CustomConfig customConfig = null
  protected InternationalizationManager i18nManager = new InternationalizationManager()

  protected SeleniumConnector connector;
  protected TelluriumFramework aost

  public SeleniumConnector getConnector() {
    return connector;
  }

  abstract public void initUi()

  public void openUrl(String url) {
    getConnector().connectSeleniumServer()
    getConnector().connectUrl(url)
  }
  public geti18nManager()
  {
	return this.i18nManager;
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

  @BeforeClass
  protected void setUpForClass() {
    aost = TelluriumSupport.addSupport()
    aost.start(customConfig)
    connector = aost.connector
    initUi()
  }
    
  @AfterClass
  protected void tearDownForClass() {
    if (aost != null)
      aost.stop()
  }
}