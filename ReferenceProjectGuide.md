

# Guide for the Reference Projects #

## Introduction ##

We create reference projects to demonstrate how to use Tellurium for your own testing project. In the reference projects, we use Tellurium project web site as an example to illustrate how to write real-world Tellurium tests. The reference projects only use tellurium jar files and there are two sub-projects at the time of writing

  * tellurium-junit-java
  * tellurium-testng-java

Basically, the two sub-projects are the same and the only difference is that tellurium-junit-java uses JUnit 4 and tellurium-testng-java uses TestNG. Hence, here we only focus on the tellurium-junit-java project.

The tellurium-junit-java project illustrates the following usages of Tellurium:

  * How to create your own Tellurium testing project using tellurium jar files.
  * How to create your own UI Objects and wire them into Tellurium core
  * How to create UI module files in Groovy
  * How to create JUnit tellurium testing files in Java
  * How to create and run DSL scripts
  * How to create Tellurium Data Driven tests
  * How to configure Tellurium with the configuration file TelluriumConfig.groovy
  * Ant build script
  * Maven support
  * Support Eclipse, NetBeans, and IntelliJ IDEs

## Check out and Setup the reference project in IDEs ##

The tellurium-junit-java reference project is at SVN trunk and the URL is:

```
http://aost.googlecode.com/svn/trunk/reference-projects/tellurium-junit-java
```

We have already included all Eclipse, NetBeans, and IntelliJ project files in the project code base. What you need to do is just check out the code and twist a bit about the settings.

For detailed step-by-step directions, please see

  * [Eclipse Project Setup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectEclipseSetup)
  * [NetBeans Project Setup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectNetBeansSetup)
  * [IntelliJ Project Setup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectIntelliJSetup)

## Code Structure ##

The code structure for the tellurium-junit-java project is shown as follows

```
src/main/groovy          ---------- source code for custom UI objects
               org/tellurium/object  ------------- customer UI objects
               org/tellurium/builder ------------- builders for UI objects

src/test/groovy          ---------- test source code
               org/tellurium/module  ------------- Tellurium UI module files
               org/tellurium/test    ------------- JUnit test cases
               org/tellurium/ddt     ------------- Tellurium Data Driven test cases

src/test/resources       ---------- test resource files
               org/tellurium/data    ------------- test data files
               org/tellurium/dsl     ------------- Tellurium DSL scripts

lib/           --------------- library directory holding tellurium jars and other required jar files

build.xml      --------------- ant build script
build.properties ------------- ant build properties

pom.xml        --------------- Maven POM file

TelluriumConfig.groovy ------- Tellurium configuration file

rundsl.sh      --------------- Unix/Linux script to run Tellurium DSL scripts
rundsl.bat     --------------- Windows script to run Tellurium DSL scripts

Others are project files for Eclipse, NetBeans, and IntelliJ
```

## Create Custom UI objects ##

Tellurium supports custom UI objects defined by users. For most UI objects, they must extend the "UiObject" class and then define actions or methods they support. For container type UI objects, i.e., which hold other UI objects, they should extends the Container class. Please see Tellurium Table and List objects for more details.

In the tellurium-junit-java project, we defined the custom UI object "SelectMenu" as follows:

```
class SelectMenu extends UiObject{   
    public static final String TAG = "div"

    String header = null

    //map to hold the alias name for the menu item in the format of "alias name" : "menu item"
    Map<String, String> aliasMap

    def click(Closure c){
        c(null)
    }

    def mouseOver(Closure c){
        c(null)
    }

    def mouseOut(Closure c){
        c(null)
    }

    public void addTitle(String header){
        this.header = header
    }
    
    public void addMenuItems(Map<String, String> menuItems){
        aliasMap = menuItems
    }
   
    ......

}
```

For each UI object, you must define the builder so that Tellurium knows how to construct the UI object when it parses the UI modules. For example, we define the builder for the "SelectMenu" object as follows:

```
class SelectMenuBuilder extends UiObjectBuilder{
    static final String ITEMS = "items"
    static final String TITLE = "title"

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, SelectMenu.TAG)
        SelectMenu menu = this.internBuild(new SelectMenu(), map, df)
        Map<String, String> items = map.get(ITEMS)
        if(items != null && items.size() > 0){
           menu.addMenuItems(items) 
        }
        
        menu.addTitle(map.get(TITLE))
        
        return menu
    }
}
```

You may wonder how to hook the custom objects into Tellurium core so that it can recognize the new type. The answer is simple, you just add the UI object name and its builder class name to Tellurium configuration file TelluriumConfig.groovy. Update the following section:

```
    uiobject{
        builder{
           SelectMenu="org.tellurium.builder.SelectMenuBuilder"
        }
    }
```

## Create UI modules ##

You should create UI modules in Groovy files, which should extend the DslContext class. In the defineUi method, define your UIs and then define all methods for them. Take the Tellurium Downloads page as an example:

```
class TelluriumDownloadsPage extends DslContext{

   public void defineUi() {

       //define UI module of a form include download type selector and download search
       ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
           Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
           TextBox(uid: "searchLabel", clocator: [tag: "span"])
           InputBox(uid: "searchBox", clocator: [name: "q"])
           SubmitButton(uid: "searchButton", clocator: [value: "Search"])
       }

       ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], group: "true"){
           //define table header
           //for the border column
           TextBox(uid: "header: 1", clocator: [:])
           UrlLink(uid: "header: 2", clocator: [text: "%%Filename"])
           UrlLink(uid: "header: 3", clocator: [text: "%%Summary + Labels"])
           UrlLink(uid: "header: 4", clocator: [text: "%%Uploaded"])
           UrlLink(uid: "header: 5", clocator: [text: "%%Size"])
           UrlLink(uid: "header: 6", clocator: [text: "%%DownloadCount"])
           UrlLink(uid: "header: 7", clocator: [text: "%%..."])

           //define table elements
           //for the border column
           TextBox(uid: "row: *, column: 1", clocator: [:])
           //the summary + labels column consists of a list of UrlLinks
           List(uid: "row:*, column: 3", clocator: [:]){
               UrlLink(uid: "all", clocator: [:])
           }
           //For the rest, just UrlLink
           UrlLink(uid: "all", clocator: [:])
       }
   }

    public String[] getAllDownloadTypes(){
        return  getSelectOptions("downloadSearch.downloadType")
    }

    public String getCurrentDownloadType(){
        return  getSelectedLabel("downloadSearch.downloadType");
    }

    public void selectDownloadType(String type){
        selectByLabel "downloadSearch.downloadType", type
    }

    public void searchDownload(String keyword){
        type "downloadSearch.searchBox", keyword
        click "downloadSearch.searchButton"
        waitForPageToLoad 30000
    }

    ......

}
```

## Create JUnit Test Cases ##

You can create JUnit Test Cases by extending the TelluriumJavaTestCase class. Nothing special, just like regular JUnit test cases. For instance,

```
public class TelluriumDownloadsPageJavaTestCase extends TelluriumJavaTestCase{
   private static TelluriumDownloadsPage downloadPage;

    public static String newline = System.getProperty("line.separator");
    private static Logger logger = Logger.getLogger(TelluriumDownloadsPageJavaTestCase.class.getName());

    @BeforeClass
    public static void initUi() {
        downloadPage = new TelluriumDownloadsPage();
        downloadPage.defineUi();
    }

    @Test
    public void testDownloadTypes(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        String[] allTypes = downloadPage.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        assertTrue(allTypes[2].contains("Featured Downloads"));
        assertTrue(allTypes[3].contains("Current Downloads"));
        assertTrue(allTypes[4].contains("Deprecated Downloads"));
    }

    @Test
    public void testSearchByText(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        // Set download type with other value
        downloadPage.selectDownloadType(" All Downloads");
        downloadPage.searchDownload("tellurium-0.4.0");

        List<String> list = downloadPage.getDownloadFileNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "tellurium-0.4.0.tar.gz"));
    }

    ......

}
```

## Create and Run DSL scripts ##

In Tellurium, you can create test scripts in pure DSL. Take TelluriumPage.dsl as an example,

```
//define Tellurium project menu
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
    //since the actual text is  Project&nbsp;Home, we can use partial match here. Note "%%" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "%%Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the Tellurium project search module, which includes an input box, two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}

openUrl "http://code.google.com/p/aost/"
click "menu.project_home"
waitForPageToLoad 30000
click "menu.downloads"
waitForPageToLoad 30000
click "menu.wiki"
waitForPageToLoad 30000
click "menu.issues"
waitForPageToLoad 30000

openUrl "http://code.google.com/p/aost/"
type "search.searchbox", "Tellurium Selenium groovy"
click "search.search_project_button"
waitForPageToLoad 30000

type "search.searchbox", "tellurium selenium dsl groovy"
click "search.search_web_button"
waitForPageToLoad 30000
```

To run the DSL script, you should first compile the code with ant script. In project root, run the following ant task

```
ant compile-test
```

Then, use the rundsl.sh to run the DSL script

```
./rundsl.sh src/test/resources/org/tellurium/dsl/TelluriumPage.dsl
```

For Windows, you should use the rundsl.bat script.

## Data Driven Testing ##

We use Tellurium Issue page as the data driven testing example, we define tests to search issues assigned to a Tellurium team member and use the input file to define which team members we want the result for.

We first define a TelluriumIssuesModule class that extends TelluriumDataDrivenModule class and includes a method "defineModule". In the "defineModule" method, we define UI modules,  input data format, and different tests. The UI modules are the same as defined before for the Tellurium issue page.

The input data format is defined as

```
fs.FieldSet(name: "OpenIssuesPage") {
   Test(value: "OpenTelluriumIssuesPage")
}

fs.FieldSet(name: "IssueForOwner", description: "Data format for test SearchIssueForOwner") {
   Test(value: "SearchIssueForOwner")
   Field(name: "issueType", description: "Issue Type")
   Field(name: "owner", description: "Owner")
}
```

Here we have two different input data formats. The "Test" field defines the test name and the "Field" field define the input data name and description. For example, the input data for the test "SearchIssueForOwner" have two input parameters "issueType" and "owner".

The tests are defined use "defineTest". One of the test "SearchIssueForOwner" is defined as follows,

```
        defineTest("SearchIssueForOwner") {
            String issueType = bind("IssueForOwner.issueType")
            String issueOwner = bind("IssueForOwner.owner")
            int headernum = getCachedVariable("headernum")
            int expectedHeaderNum = getTableHeaderNum()
            compareResult(expectedHeaderNum, headernum)
            
            List<String> headernames = getCachedVariable("headernames")
            String[] issueTypes = getCachedVariable("issuetypes")
            String issueTypeLabel = getIssueTypeLabel(issueTypes, issueType)
            checkResult(issueTypeLabel) {
                assertTrue(issueTypeLabel != null)
            }
            //select issue type
            if (issueTypeLabel != null) {
                selectIssueType(issueTypeLabel)
            }
            //search for all owners
            if ("all".equalsIgnoreCase(issueOwner.trim())) {
                searchForAllIssues()
            } else {
                searchIssue("owner:" + issueOwner)
            }
            ......
        }

    }

```

As you can see, we use "bind" to tie the variable to input data field. For example, the variable "issueType" is bound to "IssueForOwner.issueType", i.e., field "issueType" of the  input Fieldset "IssueForOwner". "getCachedVariable" is used to get variables passed from previous tests and "compareResult" is used to compare the actual result with the expected result.

The input file format looks like

```
OpenTelluriumIssuesPage
## Test Name | Issue Type | Owner
SearchIssueForOwner | Open | all
SearchIssueForOwner | All | matt.senter
SearchIssueForOwner | Open | John.Jian.Fang
SearchIssueForOwner | All |  vivekmongolu
SearchIssueForOwner | All |  haroonzone
```

The actual test class is very simple

```
class TelluriumIssuesDataDrivenTest extends TelluriumDataDrivenTest{


    public void testDataDriven() {

        includeModule org.tellurium.module.TelluriumIssuesModule.class

        //load file
        loadData "src/test/resources/org/tellurium/data/TelluriumIssuesInput.txt"

        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }

}
```

We first define which Data Driven Module we want to load and then read input data file. After that, we read the input data file line by line and execute the appropriate tests defined in the input file. Finally, close the data file.

The test result looks as follows,

```
<TestResults>
  <Total>6</Total>
  <Succeeded>6</Succeeded>
  <Failed>0</Failed>
  <Test name='OpenTelluriumIssuesPage'>
    <Step>1</Step>
    <Passed>true</Passed>
    <Input>
      <test>OpenTelluriumIssuesPage</test>
    </Input>
    <Assertion Value='10' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>2.579049</Runtime>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>2</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>Open</issueType>
      <owner>all</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' Open Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>4.118923</Runtime>
    <Message>Found 10  Open Issues for owner all</Message>
    <Message>Issue: Better way to wait or pause during testing</Message>
    <Message>Issue: Add support for JQuery selector</Message>
    <Message>Issue: Export Tellurium to Ruby</Message>
    <Message>Issue: Add check Alter function to Tellurium</Message>
    <Message>Issue: Firefox plugin to automatically generate UI module for users</Message>
    <Message>Issue: Create a prototype for container-like Dojo widgets</Message>
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and use Maven to build multiple projects</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and not look for an IntelliJ project dependency</Message>
    <Message>Issue: Support nested properties for Tellurium</Message>
    <Message>Issue: update versions for extensioin dojo-widget and TrUMP projects</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>3</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>matt.senter</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>4.023694</Runtime>
    <Message>Found 7  All Issues for owner matt.senter</Message>
    <Message>Issue: Add Maven 2 support</Message>
    <Message>Issue: Add Maven support for the new code base with multiple sub-projects</Message>
    <Message>Issue: Cannot find symbol class Selenium</Message>
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and use Maven to build multiple projects</Message>
    <Message>Issue: Need to add tar.gz file as artifact in Maven</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and not look for an IntelliJ project dependency</Message>
    <Message>Issue: update versions for extensioin dojo-widget and TrUMP projects</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>4</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>Open</issueType>
      <owner>John.Jian.Fang</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' Open Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.542759</Runtime>
    <Message>Found 5  Open Issues for owner John.Jian.Fang</Message>
    <Message>Issue: Better way to wait or pause during testing</Message>
    <Message>Issue: Export Tellurium to Ruby</Message>
    <Message>Issue: Add check Alter function to Tellurium</Message>
    <Message>Issue: Create a prototype for container-like Dojo widgets</Message>
    <Message>Issue: Support nested properties for Tellurium</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>5</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>vivekmongolu</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.521415</Runtime>
    <Message>Found 5  All Issues for owner vivekmongolu</Message>
    <Message>Issue: Create more examples to demonstrate the usage of different UI objects</Message>
    <Message>Issue: DslScriptEngine causes NullPointerException when attempt to invoke server.runSeleniumServer() method</Message>
    <Message>Issue: No such property: connector for class: DslTest</Message>
    <Message>Issue: Firefox plugin to automatically generate UI module for users</Message>
    <Message>Issue: Setup Firefox plugin sub-project</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>6</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>haroonzone</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.475594</Runtime>
    <Message>Found 5  All Issues for owner haroonzone</Message>
    <Message>Issue: Solve HTTPS certificate problem</Message>
    <Message>Issue: Refactor Tellurium project web site examples using TestNG and put them to the tellurium-testng-java sub-project</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project for NetBeans</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project for IntelliJ</Message>
    <Message>Issue: Table operations for large size one seems to be very slow</Message>
  </Test>
</TestResults>
```


## Tellurium Configuration ##

Tellurium uses the configuration file TelluriumConfig.groovy to configure itself. The configuration includes different sections for embedded selenium server, selenium connection, data driven testing, custom UI objects, and widgets.

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

           SelectMenu="org.tellurium.builder.SelectMenuBuilder"

        }
    }
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included=""
        }
    }
}

```

As a result, Tellurium is very flexible for users to customize. Note, if you want to use widgets, you should include the widget jar files in your class path.

## Build Scripts ##

The tellurium-junit-java project includes ant build script and Maven POM file so that you can run ant tasks from the command line, or use Maven to build the project.

## The tellurium-testng-java Sub-Project ##

The tellurium-testng-java project is basically the same as the tellurium-junit-java project. The difference is that it uses TestNG to write test cases while tellurium-junit-java uses JUnit 4. The difference between JUnit and TestNG is out of scope. You can choose whatever you like. The subversion URL is at:

```
http://aost.googlecode.com/svn/trunk/reference-projects/tellurium-testng-java
```

For IntelliJ 7.0, TestNG support is out of box. You only need to install the JetGroovy plugin, then check out the tellurium-testng-java project and run the tests. No special work here.

For Eclipse, you need first to install the Groovy TestNG support Plugin using software update center and add the site

```
http://dist.codehaus.org/groovy/distributions/update/
```

After that, you should choose "Groovy TestNG Feature" and "Groovy Feature" as shown in the following figure:

http://tellurium-users.googlegroups.com/web/EclipseGroovyTestNG.png?gda=nbnJQEkAAABBo5YKz4My4Er929brhwkD31ryw83LiDdvubdOAW6IJ4jQR-TTDC34HLHCvRcRWDG72uWUHbQH5MG0_R87Wl3khAioEG5q2hncZWbpWmJ7IQ&gsc=2FVKqBYAAAByCct4GoRvJFz1cqlXtmOI5QzTRg0a_4LqA7LDDLzsAA

Then check out the project and you should be able to run the TestNG tests.

Seems NetBeans has very limited support for TestNG and it is really hard to find the TestNG plugin for NetBeans 6. As a result, we do not include the NetBeans project settings at this time.

## Create Your Own Project from Scratch ##

Reference projects are good examples to show you how to create your own Tellurium porjects. If you really want to create tellurium projects by your own from scratch. Please follow the wiki pages:

  * [How to create your own Tellurium testing project with Eclipse](http://code.google.com/p/aost/wiki/CustomTelluriumEclipseProject)
  * [How to create your own Tellurium testing project with NetBeans 6.5Beta](http://code.google.com/p/aost/wiki/CustomTelluriumNetBeansProject)
  * [How to create your own Tellurium testing project with IntelliJ 7.0](http://code.google.com/p/aost/wiki/CustomTelluriumIntelliJProject)

## Convert Selenium Test Cases to Tellurium Test Cases ##

If you want to convert your existing Selenium test cases to Tellurium test cases or want to use Selenium IDE to create tellurium test cases, you can find the solution here [How to convert selenium test cases to Tellurium test cases](http://code.google.com/p/aost/wiki/HowToConvertSeleniumTestToTellurium)