

# Tutorial for Tellurium #

## How to use Tellurium ##

There are three ways to use Tellurium. The first one is write your UI modules in different Groovy files, which extends the DslContext class. Then write JUnit test cases. You can also include them in a test suite.


Starting from Tellurium 0.4.0, JUnit 4.4 is supported. Usually, users only need to extend **TelluriumJavaTestCase** to write Tellurium Tests. The TelluriumJavaTestCase has already included the setUp and tearDown methods in class level using JUnit 4 @BeforeClass and
@AfterClass annotations. If users want to include multiple test cases in one test suite, they should use **BaseTelluriumJavaTestCase** so that all the test cases can share the same Selenium connection.


The second way is to write Tellurium tests in groovy, users can use **TelluriumGroovyTestCase** for a single test and **TelluriumSuiteGroovyTestCase** for a test suite.


The other way is to write DSL script directly, that is to say, your tests will be pure DSL scripts. This is really suitable for non-developers or QA people. The **DslScriptExecutor** can be used to run the .dsl files.

## Tellurium Configuration ##

Starting from 0.4.0, Tellurium provided a global configuration file, TelluriumConfig.groovy, which sits in the project root directory and you can change all the settings in this file. If this file is missing, the framework will use the default settings specified in Groovy classes. Don't be panic by the fact that is a groovy file. It is treated by Tellurium as a text file and will be parsed by the Tellurium configuration manager. Please put this file in the directory you run the test if you want to override the default settings.

The current configuration includes settings for the embedded server, Selenium connector, and data driven testing. The file is listed as follows:

```
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
        //profile location
        profile = ""
    }
    //event handler
    eventhandler{
        //whether we should check if the UI element is presented
        checkElement = true
        //wether we add additional events like "mouse over"
        extraEvent = true
    }
    //data accessor
    accessor{
        //whether we should check if the UI element is presented
        checkElement = false
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
        //   *iehta

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
           SelectMenu="org.tellurium.builder.SelectMenuBuilder"
           ClickableRadioButton="org.tellurium.builder.ClickableRadioButtonBuilder"
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

TelluriumConfig.groovy acts like a project setting file. The BaseTelluriumJavaTestCase provides two methods for you to overwrite the default settings,

```
public static void setCustomConfig(boolean runInternally, int port, String browser,
                      boolean useMultiWindows, String profileLocation)

public static void setCustomConfig(boolean runInternally, int port, String browser,
                      boolean useMultiWindows, String profileLocation, String serverHost)
```

As you result, if you want to use your custom settings for your specific test class, you can use the following way, taking the Google test case as an example,

```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase
{
   static{
       setCustomConfig(true, 5555, "*chrome", true, null);
   }

...

}
```

## How to setup Tellurium Project in IDE ##

Tellurium Project can be run in IntelliJ, NetBeans, Eclipse, or other IDEs which have Groovy support. The Tellurium project code base has already included IntelliJ project files and NetBeans Project files.

For IntelliJ users, you simply open the project and overwrite some configurations such as Java Home, Groovy Home, and library path. Then you can compile, run, and test the code. IntelliJ is commercial, but you can download a free trial version for 30 days from http://www.jetbrains.com/idea/download/index.html.

For NetBeans users, you can find a detailed Guide on [the NetBeans Starters' guide page](http://code.google.com/p/aost/wiki/TelluriumStarterUsingNetBeans).

For Eclipse users, you need to download Eclipse Groovy Plugin from http://dist.codehaus.org/groovy/distributions/update/ to run the Tellurium project.

## Tellurium Trunk Code Structure ##

The trunk holds multiple projects now and the structure is as follows,

```
trunk/
     core                 ----- Tellurium core framework project

     extensions/          ----- Extensions to core 
               dojo-widget                   ----- Dojo widget extension project
               extjs-widget                  ----- ExtJS widget extension project (only placeholder here now)
     
     reference-projects/  ----- Projects to demonstrate the usage of tellurium
                       tellurium-junit-java  ----- Tellurium test project using JUnit 4
                       tellurium-testng-java ----- Tellurium test project using TestNG

     tools/firefox-plugin/ ----- Tellurium firefox plugin
                         trump               ---- The Tellurium UI Model Plugin project, which will automatically create tellurium UI modules for you

```


## Tellurium Project Code Structure ##

The code structure is as follows,

```

src/main              ---------------- Tellurium source code

src/test/             ---------------- Tellurium testing code

        org/tellurium/test  ----------------- Unit tests and functional tests for Tellurium framework itself

        example/
                dsl/        -----------------  DSL testing scripts
                google/     -----------------  Ui modules for Tellurium testing using Google web sites as references
                tellurium/  -----------------  Ui modules for Tellurium testing using Tellurium project web site as a reference

                test/       -----------------  Actual tests
                     ddt/   -----------------  Tellurium Data Driven tests
                     groovy/-----------------  Tellurium Groovy test cases
                     java/  -----------------  Tellurium Java test cases


lib/                 ---------------------- Lib directory including all required jars

build.properties     ---------------------- configuration file for ant build.xml
build.xml            ---------------------- ant build script
rundsl.sh            ---------------------- shell script to run DSL testing scripts in Unix or Linux sytems
rundsl.bat           ---------------------- windows script to run DSL testing scripts
LICENSE.txt          ---------------------- License file
README               ---------------------- Readme file


```

## Define multiple UI modules in a single file ##

Tellurium supports multiple UI modules in a single Ui Module file. For example, you can define multiple UI modules as follows:
```
//define the menu
//It is fine to use Container to abstract Table if you have special table
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
    //since the actual text is  Project&nbsp;Home, we can useString partial match here. Note "%%" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "%%Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the search module, which includes an input box, two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}
```

## How to Reuse a Frequently Used Set of Elements ##

The "Include" syntax in Ui module definition can be used for this purpose. You can put frequently used UI modules into a base class, for example,

```
public class BaseUiModule extends DslContext {
  public void defineBaseUi() {
    ui.Container(uid: "SearchModule", clocator: [tag: "td"], group: "true") {
      InputBox(uid: "Input", clocator: [title: "Google Search"])
      SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
    }

    ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true") {
      TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
      List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "p") {
        UrlLink(uid: "all", clocator: [:])
      }
    }
  }
}
```

Then you can extend this base Ui module as follows,

```
public class ExtendUiModule extends BaseUiModule {

  public void defineUi() {
    defineBaseUi()

    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      Include(ref: "SearchModule")
      Container(uid: "Options", clocator: [tag: "td", position: "3"], group: "true") {
        UrlLink(uid: "LanguageTools", clocator: [tag: "a", text: "Language Tools"])
        UrlLink(uid: "SearchPreferences", clocator: [tag: "a", text: "Search Preferences"])
        UrlLink(uid: "AdvancedSearch", clocator: [tag: "a", text: "Advanced Search"])
      }
    }

    ui.Container(uid: "Test", clocator: [tag: "div"]) {
      Include(uid: "newcategory", ref: "GoogleBooksList.category")
      Include(uid: "secondcategory", ref: "GoogleBooksList.category")
      Include(uid: "newsubcategory", ref: "GoogleBooksList.subcategory")
    }
  }
}
```

Note that the "Include" must have the ref attribute to refer to the element it wants to include. You can still specify the uid for the object (if you do not need a different uid, you do not need the uid), if the object uid is not equal to the original one, Tellurium will clone a new object for you so that you can have multiple objects with different uids.

## Examples ##

In this tutorial, the following examples are provided:

  1. [Basic Examples](http://code.google.com/p/aost/wiki/BasicExample), which include Tellurium tests for Google Start Page, Google Code Hosting Page, and Google Book List Page.
  1. [Advanced Examples](http://code.google.com/p/aost/wiki/AdvancedExample), which use the Tellurium project website as a reference to demonstrate how to write non-trivial and very complicated Tellurium tests in real world.
  1. [Data Driven Testing Examples](http://code.google.com/p/aost/wiki/DataDrivenTestingExample), which illustrate how to write Tellurium Data Driven tests and walk you through all the details including input, test driving engine, and output.
  1. [DSL Examples](http://code.google.com/p/aost/wiki/DSLExample). DSL is a very simple way to write Tellurium tests. The examples include regular Tellurium tests and Tellurium Data Driven tests Written in pure DSL.

## Tellurium Tutorial Series ##

The following toturial series could be found in [Tellurium User Group](http://groups.google.com/group/tellurium-users),

  * [How to convert selenium test cases to Tellurium test cases](http://code.google.com/p/aost/wiki/HowToConvertSeleniumTestToTellurium)
  * [Tellurium objects and their default attributes](http://groups.google.com/group/tellurium-users/browse_thread/thread/84dd00605209952d)
  * [Tellurium UiDslParser - What is the "ui."](http://groups.google.com/group/tellurium-users/browse_thread/thread/3533be58a7feb4d3)
  * [The UID](http://groups.google.com/group/tellurium-users/browse_thread/thread/9c41abd6470cd697)
  * [Groovy or Java](http://groups.google.com/group/tellurium-users/browse_thread/thread/fa8cd09c77d44626)
  * [Tellurium configuration](http://groups.google.com/group/tellurium-users/browse_thread/thread/51c1ca26e36e5be3)
  * [What is wrong with Selenium XPath](http://groups.google.com/group/tellurium-users/browse_thread/thread/d5da3660266b31ba)
  * [the "group" attribute](http://groups.google.com/group/tellurium-users/browse_thread/thread/28f24fe566c2b089)
  * [the secret of the Tellurium runtime XPath](http://groups.google.com/group/tellurium-users/browse_thread/thread/72a8920c692c2ec5)
  * [XPath tools](http://groups.google.com/group/tellurium-users/browse_thread/thread/96a933eb998dbff8)
  * [UI module](http://groups.google.com/group/tellurium-users/browse_thread/thread/01c8e068f30cab53)
  * [Step by Step Guide to create Tellurium UI modules](http://groups.google.com/group/tellurium-users/browse_thread/thread/db789d42c8796713)
  * [the List object](http://groups.google.com/group/tellurium-users/browse_thread/thread/5579a536818cf6be)
  * [the power of Tellurium UI templates](http://groups.google.com/group/tellurium-users/browse_thread/thread/8e011ff9f1f71393#)
  * [Javascript Events - the "respond" attribute](http://groups.google.com/group/tellurium-users/browse_thread/thread/93fbae75a6d88624)
  * [HTML Source vs. Runtime HTML Source](http://groups.google.com/group/tellurium-users/browse_thread/thread/71cb407be285c242#)
  * [Groovy Closure](http://groups.google.com/group/tellurium-users/browse_thread/thread/7ebe2b9988a3299f)
  * [What TrUMP log can tell you](http://groups.google.com/group/tellurium-users/browse_thread/thread/2c823f55abe5745f)
  * [Logical Container, empty != nothing](http://code.google.com/p/aost/wiki/LogicalContainer)
  * [How TrUMP works?](http://code.google.com/p/aost/wiki/HowTrUMPWorks)
  * [Ten Minutes to Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)
  * [The Cool Dump() Method](http://code.google.com/p/aost/wiki/TheDumpMethod)