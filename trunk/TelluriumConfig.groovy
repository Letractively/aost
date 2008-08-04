/**
 * The global place to Tellurium configuration
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 * 
 */

tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"                                     
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
        runInternally = true
        //the log file for selenium server
        logFile = "selenium.log"
    }
    //the configuration for the connector that connects the selenium client to the selenium server
    connector{
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        browser = "*chrome"
    }
}