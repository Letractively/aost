package org.tellurium.server

import org.openqa.selenium.server.RemoteControlConfiguration
import org.openqa.selenium.server.SeleniumServer;

/**
 * Programmatically run Selenium Server so that we do not have to
 * start the server in the shell.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
public class SeleniumServerDaemon {
    //determine if it runs
    private boolean listening = false;
    //default port number

    private static final int DEFAULT_PORT = 4444;

    private static final String DEFAULT_LOG_FILE = "selenium.log";

    private int port;

    private String logFile;

    private boolean useMultiWindows = false;

    private boolean slowResources = false;

    private SeleniumServer server;

    private String [] getParams(){
		String cmd = "-port " + port + " -log " + logFile;

		if(useMultiWindows){
			cmd = cmd + " -multiWindow";
		}

		return cmd.split(" ");
    }

	public SeleniumServerDaemon(int port, String logFile, boolean useMultiWindows) {
		super();
		this.port = port;
		this.logFile = logFile;
		this.useMultiWindows = useMultiWindows;
		listening = false;
		if(this.port <0 )
			port = DEFAULT_PORT;
		if(this.logFile == null)
			this.logFile = DEFAULT_LOG_FILE;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final String getLogFile() {
		return logFile;
	}

	public final void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public void run() {
		RemoteControlConfiguration config = new RemoteControlConfiguration();
		config.port = port;
		config.multiWindow = useMultiWindows;
		config.setProxyInjectionModeArg(true); //this may not be needed, or atleast needs to be configurable
//		File userExt = new File("./lib/user-extensions.js");
//		config.setUserExtensions(userExt);
		try {
            server = new SeleniumServer(config);
			server.boot()

			//String[] args = getParams();
			//SeleniumServer.main(args);
            listening = true;
		} catch (Exception e) {

			e.printStackTrace();
		}

        /*
		try {
            server = new SeleniumServer(port);
            server.multiWindow = useMultiWindows
            server.start()

            //String[] args = getParams();
			//SeleniumServer.main(args);
            listening = true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		*/
	}

    public void stop(){
       try{
            if(server != null)
                server.stop()
        } catch (Exception e) {

			e.printStackTrace();
		}
    }
}
