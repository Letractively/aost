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
        port = "4445"                                     
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
        runInternally = true
        //the log file for selenium server
//        logFile = "selenium.log"
    }
    //the configuration for the connector that connects the selenium client to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "localhost"
        //server port number the client needs to connect
        port = "4445"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //browser = "*iehta"
        browser = "*chrome"
    }
    datadriven{
        dataprovider{
            //specify which data reader you like the data provider to use
            //the valid options include "PipeFileReader", "CVSFileReader" at this point
            reader = "PipeFileReader"
        }
    }
    test{
        result{
            //specify what result reporter used for the test result
            //valid options include "SimpleResultReporter", "XMLResultReporter", and "StreamXMLResultReporter"
            reporter = "XMLResultReporter"
            //the output of the result
            //valid options include "Console", "File" at this point
            //if the option is "File", you need to specify the file name, other wise it will use the default
            //file name "TestResults.output"
            output = "Console"
            //test result output file name
            filename = "TestResult.output"
        }
    }
    uiobject{
        builder{
            //user can specify custom UI objects here by define the builder for each UI object
            //the custom UI object builder must extend UiObjectBuilder class
            //and implement the following method:
            //
            // public build(Map map, Closure c)
            //
            //For container type UI object, the builder is a bit more complicated, please
            //take the TableBuilder or ListBuilder as an example

            //example:
//           Icon="org.tellurium.builder.IconBuilder"
            
        }
    }
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
//            included="dojo, extjs"
            included="dojo"
        }
    }
}
