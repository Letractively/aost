package org.telluriumsource.server

import org.telluriumsource.framework.config.Configurable

import org.telluriumsource.util.Helper

/**
 * Embedded Selenium Server and will be running as a daemon thread
 * 
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class EmbeddedSeleniumServer implements Configurable{

    private SeleniumServerDaemon daemon;

    protected int port = 4444;

    protected String logFile = "selenium.log";

    protected boolean useMultiWindows = false;

    protected boolean trustAllSSLCertificates = false;

    protected int DEFAULT_DELAY_IN_SECONDS = 5;

    protected int timeoutInSeconds = 30;

    protected boolean avoidProxy = false;

    protected boolean browserSessionReuse = false;

    protected boolean ensureCleanSession = false;

    protected boolean debugMode = false;

    protected boolean interactive = false;

    protected int serverDelayInSeconds = DEFAULT_DELAY_IN_SECONDS;

	protected boolean runSeleniumServerInternally = true;

    protected boolean isRunning = false;

    protected String profileLocation = null;

    protected String userExtension = null;

    protected boolean useXvfb = false;

    protected Xvfb xvfb = new Xvfb();

    public final boolean isUseMultiWindows() {
		return useMultiWindows;
	}

    public void setUpSeleniumServer(){
        if(useXvfb){
          xvfb.run();
        }
        
		try {
			daemon = new SeleniumServerDaemon (port, logFile, useMultiWindows, this.trustAllSSLCertificates,
                    this.avoidProxy, this.browserSessionReuse, this.ensureCleanSession, this.debugMode, this.interactive,
                    this.timeoutInSeconds, this.profileLocation, this.userExtension);
			daemon.run();
            isRunning = true;
            Helper.pause(serverDelayInSeconds*1000)
		} catch (Exception e) {
            isRunning = false;
			e.printStackTrace();
		}
    }

    public void runSeleniumServer() {

		if(runSeleniumServerInternally && (!isRunning))
			setUpSeleniumServer();
    }

    public final boolean isRunSeleniumServerInternally() {
		return runSeleniumServerInternally;
	}

    public void stopSeleniumServer(){
        if(runSeleniumServerInternally && daemon != null && isRunning){
              daemon.stop()
              isRunning = false;
        }
    }
}