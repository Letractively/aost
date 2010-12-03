package org.telluriumsource.server

import org.telluriumsource.framework.config.Configurable

import org.telluriumsource.util.Helper
import org.telluriumsource.annotation.Inject

/**
 * Embedded Selenium Server and will be running as a daemon thread
 * 
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class EmbeddedSeleniumServer implements Configurable{

    private SeleniumServerDaemon daemon;

    @Inject(name="tellurium.embeddedserver.port")
    protected int port = 4444;

    protected String logFile = "selenium.log";

    @Inject(name="tellurium.embeddedserver.useMultiWindows")
    protected boolean useMultiWindows = false;

    @Inject(name="tellurium.embeddedserver.trustAllSSLCertificates")
    protected boolean trustAllSSLCertificates = false;

    protected int DEFAULT_DELAY_IN_SECONDS = 5;

    protected int timeoutInSeconds = 30;

    protected boolean avoidProxy = false;

    protected boolean browserSessionReuse = false;

    protected boolean ensureCleanSession = false;

    protected boolean debugMode = false;

    protected boolean interactive = false;

    protected int serverDelayInSeconds = DEFAULT_DELAY_IN_SECONDS;

    @Inject(name="tellurium.embeddedserver.runInternally")
	protected boolean runSeleniumServerInternally = false;

    protected boolean isRunning = false;

    @Inject(name="tellurium.embeddedserver.profile")
    protected String profileLocation = null;

    @Inject(name="tellurium.embeddedserver.userExtension")
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