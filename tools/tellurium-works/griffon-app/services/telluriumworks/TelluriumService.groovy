package telluriumworks

import org.telluriumsource.server.EmbeddedSeleniumServer
import org.slf4j.LoggerFactory

class TelluriumService {
    def static logger =  LoggerFactory.getLogger(Hello);

    EmbeddedSeleniumServer server = new EmbeddedSeleniumServer()
    boolean isServerRunning = false

//    TelluriumConfig telluriumConfig = new TelluriumConfig()
    DslScriptRunner runner = new DslScriptRunner()

    public void updateTelluriumConfig(TelluriumConfig config){
      this.runner.updateConfig(config)
    }

    public void runScript(String script){
      this.runner.run(script)
    }
  
    public boolean runSeleniumServer(ServerConfig config){
      server.runSeleniumServerInternally = config.local
      server.useMultiWindows = config.multipleWindow
      if(config.profile != null && config.profile.trim().length() > 0)
        server.profileLocation = config.profile
      if(config.port != null && config.port.trim().length() > 0)
        server.port = Integer.parseInt(config.port)
      if(server.runSeleniumServerInternally){
        if(!isServerRunning){
          logger.info("Running internal Selenium Server...")
          server.runSeleniumServer()
          isServerRunning = true
        }else{
          logger.warn("Selenium server is already running")
        }

      }

      return isServerRunning
    }

    public boolean stopSeleniumServer(){
      if(isServerRunning){
        logger.info("Stopping Selenium Server...")
        server.stopSeleniumServer()
        isServerRunning = false
      }else{
        logger.warn("Selenium Server is not running")
      }

      return isServerRunning
    }
}