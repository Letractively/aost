

# Ten Minutes To Tellurium #

## Introduction ##

Tellurium is an automated web testing framework. Although it is built on top of Selenium at the current stage, there are many conceptual differences between the two. The main features of Tellurium are list as follows:
  * Not a "record and replay" style
  * UI module-based, i.e., it focuses more on a set of UI elements
  * Enforce the decoupling between UI and testing code so that you have structured code
  * Robust to change, Tellurium achieves this by using composite locator to build locator at runtime and group locating to remove the dependency among UI elements inside the UI module and external UI elements
  * Expressive by using Groovy dynamic language feature and DSL
  * Reusable, UI modules are reusable for the same application and Tellurium widgets can be used for different applications
  * Address dynamic factors on the web. UI templates are used for data grid and the respond attribute in Tellurium UI object can address Javascript events
  * Core framework is implemented in Groovy and tests can be written in Groovy, JUnit, TestNG, or pure dsl scripts
  * Support data driven testing
  * Provide Maven archetypes

This tutorial tries to achieve the following goals,
  * Walk you through the steps for creating Tellurium test cases
  * Illustrate how to use Tellurium Firefox Plugin (TrUMP) to create your own UI modules
  * How to create your own Tellurium test cases and run the tests
  * Experience the features in Tellurium

## Create a New Tellurium Test Project using Maven ##

First, you need to have Maven 2.0.9 installed and make sure you have M2\_HOME environment set in your system.

Then, add Tellurium Maven repository into your Maven settings.xml, usually it is at Your\_HOME/.m2/. Cut and post the following profile between the 

&lt;settings&gt;

 and 

&lt;/settings&gt;

 tags in your settings.xml

```
   <profiles>
        <profile>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
                 <repository>  
                    <id>kungfuters-public-snapshots-repo</id>
                    <name>Kungfuters.org Public Snapshot Repository</name>
                    <releases>          
                        <enabled>false</enabled>
                    </releases>         
                    <snapshots>         
                        <enabled>true</enabled> 
                    </snapshots>        
                    <url>http://maven.kungfuters.org/content/repositories/snapshots</url>
                </repository>   
                <repository>    
                    <id>kungfuters-public-releases-repo</id>
                    <name>Kungfuters.org Public Releases Repository</name>
                    <releases>          
                        <enabled>true</enabled> 
                    </releases>         
                    <snapshots>         
                        <enabled>false</enabled>
                    </snapshots>        
                    <url>http://maven.kungfuters.org/content/repositories/releases</url>
                </repository>   
            </repositories>
        </profile>
 </profiles>
```

Go to your work space and run the following Maven command to create a new Tellurium test project called "demo":

```
mvn archetype:create -DgroupId=example -DartifactId=demo -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

Go the demo project directory and you will find the following files

http://tellurium-users.googlegroups.com/web/DemoProject.png?gda=qhBwG0EAAACXZPxEX7Ki-M5C2JpeBoXX9HoFXmXm3ZOiZ8BOudMmn0neq6aN9azf0H_SSjmmHAdTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5ESUAsAAAB_6XgBAY-OTO2KhvY2ZJUe

where the TelluriumConfig.groovy includes Tellurium project settings and you can customize them to you need

```
tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
        runInternally = true
        //profile location
        profile = ""
        //user-extension.js file, for example, "target/test-classes/extension/user-extensions.js"
        userExtension = ""
    }
    //event handler
    eventhandler{
        //whether we should check if the UI element is presented
        checkElement = false
        //wether we add additional events like "mouse over"
        extraEvent = true
    }
    //data accessor
    accessor{
        //whether we should check if the UI element is presented
        checkElement = true
    }
    //the configuration for the connector that connects the selenium client to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "localhost"
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //  *iehta
        browser = "*chrome"
        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = ""
    }
    datadriven{
        dataprovider{
            //specify which data reader you like the data provider to use
            //the valid options include "PipeFileReader", "CVSFileReader" at this point
            reader = "PipeFileReader"
        }
    }
    test{
        //at current stage, the result report is only for tellurium data driven testing
        //we may add the result report for regular tellurium test case
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
        exception{
            //whether Tellurium captures the screenshot when exception occurs.
            //Note that the exception is the one thrown by Selenium Server
            //we do not care the test logic errors here
            captureScreenshot = true
            //we may have a series of screenshots, specify the file name pattern here
            //Here the ? will be replaced by the timestamp and you might also want to put
            //file path in the file name pattern
            filenamePattern = "Screenshot?.png"
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
            included=""
        }
    }
}
```

The GoogleSearchModule is the UI module for Google search and it is automatically generated by Tellurium Firefox Plugin (TrUMP). The two test methods doGoogleSearch and doImFeelingLucky are added for regular Goolge search and Google search for "I'm Feeling Lucky".

```
public class GoogleSearchModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }
  }

  public void doGoogleSearch(String input) {
    keyType "Google.Input", input
    pause 500
    click "Google.Search"
    waitForPageToLoad 30000
  }

  public void doImFeelingLucky(String input) {
    type "Google.Input", input
    pause 500
    click "Google.ImFeelingLucky"
    waitForPageToLoad 30000
  }
}
```

The GoogleSearchTestCase is a Tellurium JUnit test case by extending the class TelluriumJavaTestCase.

```
public class GoogleSearchTestCase extends TelluriumJavaTestCase {
    private static GoogleSearchModule gsm;

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
    }

    @Before
    public void connectToGoogle() {
        connectUrl("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        gsm.doGoogleSearch("tellurium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        gsm.doImFeelingLucky("tellurium automated Testing");
    }
}
```

Once the demo project is created you can load it up using your favourite IDE. For example, in IntelliJ IDEA, you should do the following steps

```
New Project > Import project from external model > Maven > Project directory > Finish
```

Since it is a Maven project, the IDE will automatically try to solve the project dependency for you and download appropriate jars. Then, click on module settings to make sure the Groovy version is 1.6.0 as shown in the following picture

http://tellurium-users.googlegroups.com/web/ProjectStructureGroovy.png?gda=k5uZoEwAAACXZPxEX7Ki-M5C2JpeBoXXZm5kowxpyCZNxUPkbt5Zzzcv-xQ6fkRsOQF4PGv8Pa9yoU_yua7gn0-5looe3wHr_Vpvmo5s1aABVJRO3P3wLQ&gsc=r5ESUAsAAAB_6XgBAY-OTO2KhvY2ZJUe

After that, you are read to run the sampel test file GoogleSearchTestCase.

If you want to run the sample tests in command line, you can use the following command

```
mvn test
```

## Create Your own UI modules and Test Cases ##

Tellurium provides TrUMP for you to automatically create UI modules. TrUMP can be download from Tellurium project site

http://code.google.com/p/aost/downloads/list

Choose the Firefox 2 or Firefox 3 version depending on your Firefox version. Or you can download the Firefox 3 version directly from Firefox addons site at

https://addons.mozilla.org/en-US/firefox/addon/11035

Once you install it and restart Firefox, you are ready to record your UI modules by simply clicking on the UI element on the web and then click the "generate" button. You may like to customize your UI a bit by clicking the "Customize" button. More detailed TrUMP introductions can be found at

http://code.google.com/p/aost/wiki/TrUMP

and

http://code.google.com/p/aost/wiki/HowTrUMPWorks

In our example, we open up Tellurium download page

http://code.google.com/p/aost/downloads/list

and record the download search module as follows

http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPage.png?gda=OlvnAU0AAACXZPxEX7Ki-M5C2JpeBoXX_AfRGjKOg8bfAG7WWa1zzr3MhpfIdLRuVAwbLjuTZtSqonpmmrv0I-p7nCVZQmP65Tb_vjspK02CR95VRrtmeQ&gsc=r5ESUAsAAAB_6XgBAY-OTO2KhvY2ZJUe

After we customize the UI module, we export it as the module file NewUiModule.groovy to the demo project and add couple methods to the class.

```
class NewUiModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], group: "true") {
      Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
      InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])
    }
  }

  //Add your methods here
  public void searchDownload(String keyword) {
    keyType "TelluriumDownload.Input", keyword
    click "TelluriumDownload.Search"
    waitForPageToLoad 30000
  }

  public String[] getAllDownloadTypes() {
    return getSelectOptions("TelluriumDownload.DownloadType")
  }

  public void selectDownloadType(String type) {
    selectByLabel "TelluriumDownload.DownloadType", type
  }
}
```

Then, create a new Tellurium test case NewTestCase by extending TelluriumJavaTestCase class.

```
public class NewTestCase extends TelluriumJavaTestCase {
    private static NewUiModule app;

    @BeforeClass
    public static void initUi() {
        app = new NewUiModule();
        app.defineUi();
    }

    @Before
    public void setUpForTest() {
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testTelluriumProjectPage() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }
}
```

Compile the project and run the new test case.

## Tellurium TestNG Project ##

If you want to create a new Tellurium TestNG project, simply use a different Maven archetype as follows,

```
mvn archetype:create -DgroupId=example -DartifactId=demo -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

and the new Tellurium test case should extend TelluriumTestNGTestCase class. The rest are the same as the JUnit ones.

## Resources ##

The slides for this tutorial is available at

http://aost.googlecode.com/files/Ten.Minutes.To.Tellurium.pdf

The Screencast video is available at

http://aost.googlecode.com/files/TenMinutesToTellurium.ogg

Here is [the online version](http://www.youtube.com/watch?v=DyUPeg-Y-Yg)

The generated Demo project can be downloaded from [here](http://tellurium-users.googlegroups.com/web/demo.tar.gz?gda=KLuc0T0AAACXZPxEX7Ki-M5C2JpeBoXX9cV6LjM02yQQsSwuuEiWqhlX3Fx9UEqM2hr2qeRv5GDlNv--OykrTYJH3lVGu2Z5&gsc=r5ESUAsAAAB_6XgBAY-OTO2KhvY2ZJUe) or check it out from Subversion

```
http://aost.googlecode.com/svn/branches/ten-minutes-to-tellurium/
```
