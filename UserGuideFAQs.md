(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



# Frequently Asked Questions #

## How Long Has Tellurium Been There ##

Tellurium is over one year old in June 2009 if we count the date from the day it became an open source project. But actually, Tellurium had been through two phases of prototyping before that. The first prototype was created in 2007 to test our company's  Dojo web applications, which was basically a Java framework based on Spring XML wiring and no UI modules, you have to use factories to create all UI objects. As a result, it was not convenient to use. The second prototype was there in early 2008 to improve the usability of the first prototype. The UI module was introduced in the second prototype. Both prototypes had been used for couple internal projects before it was re-written in Groovy and became an open source project in June 2008. You may notice that prototype framework is called AOST and it was officially renamed to the Tellurium Automated Testing framework (Tellurium) in July 2008 when it moved out of the prototyping phase and became a team project.

## What are the main differences between Tellurium and Selenium ##

Tellurium was created when I was a Selenium user and tried to address some of the shortcomings of the Selenium framework such as verbosity and fragility to changes. Selenium is a great web testing framework and up to 0.6.0, Tellurium uses Selenium core as the test driving engine. From Tellurium 0.7.0, we will gradually replace the Selenium core with our own Engine.

Although Tellurium was born from Selenium, there are some fundamental differences between Tellurium and Selenium, which mainly come from the fact that Tellurium is a UI module based testing framework, i.e., Tellurium focuses on a set of UI elements instead of individual ones. The UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium project site is defined as follows,

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

With the UI module, Tellurium automatically generates runtime locators for you and you do not need to define XPaths or other types of locators by yourself. Tellurium is robust, expressive, flexible, and reusable.

## Do I need to know Groovy before I use Tellurium ##

Tellurium Core is implemented in Groovy and Java to achieve expressiveness. But that does not mean you have to be familiar with Groovy before you start to use Tellurium. Tellurium creates DSL expressions for UI module, actions, and testing, you need to use a Groovy class to implement the UI module by extending the DslContext Groovy class, other than that, you can write the rest using Java syntax. Your test cases could be created in Java, Groovy, or Dsl scripts. However, we do encourage you to get familiar with Groovy to leverage its meta programming features.

To create a Tellurium project, you need to install a Groovy plugin for your IDE. There are Groovy plugins for commonly used IDEs such as Eclipse, Netbeans, and IntelliJ. Please check the following wiki pages on how to set up Groovy and use Tellurium in different IDEs,

http://code.google.com/p/aost/wiki/TelluriumReferenceProjectEclipseSetup

http://code.google.com/p/aost/wiki/TelluriumReferenceProjectNetBeansSetup

http://code.google.com/p/aost/wiki/TelluriumReferenceProjectIntelliJSetup

## What Unit Test and Functional Test Frameworks Does Tellurium Support ##

Tellurium supports both JUnit and TestNG frameworks. You should extend  TelluriumJavaTestCase for JUnit and TelluriumTestNGTestCase for TestNG. For more details, please check the following wiki pages,

http://code.google.com/p/aost/wiki/BasicExample

http://code.google.com/p/aost/wiki/Introduction

Apart from that, Tellurium also provides data driven testing. Data Driven Testing is a different way to write tests, i.e, test data are separated from the test scripts and the test flow is not controlled by the test scripts, but by the input file instead. In the input file, users can specify which test to run, what are input parameters, and what are expected results. More details could be found from "Tellurium Data Driven Testing" wiki page,

http://code.google.com/p/aost/wiki/DataDrivenTesting

## Does Tellurium Provide Any Tools to Automatically Generate UI Modules ##

Tellurium UI Model Plugin (TrUMP) is a Firefox Plugin for you to automatically generate UI module simply by clicking on the web page under testing. You can download it from Tellurium download page at

http://code.google.com/p/aost/downloads/list

or from Firefox Addons site at

https://addons.mozilla.org/en-US/firefox/addon/11035

The detailed user guide for TrUMP 0.1.0 is at

http://code.google.com/p/aost/wiki/TrUMP

If you want to understand more about how TrUMP works, please read "How does TrUMP work?" at

http://code.google.com/p/aost/wiki/HowTrUMPWorks

## What Build System Does Tellurium use ##

Tellurium supports both Ant and Maven build system. The ant build scripts are provided in Tellurium core and Tellurium reference projects. For Maven, please check out the
Tellurium Maven guide,

http://code.google.com/p/aost/wiki/MavenHowTo

## What is the Best Way to Create a Tellurium Project ##

Tellurium provides two reference projects for JUnit and TestNG project, respectively.
You can use one of them as a template project. Please see the reference project guide at

http://code.google.com/p/aost/wiki/ReferenceProjectGuide

But the best and easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetype, i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively. As a result, you can create a Tellurium project using one Maven command. For a Tellurium JUnit project, use

mvn archetype:create -DgroupId=your\_group\_id -DartifactId=your\_artifact\_id \
> -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium\
> -DarchetypeVersion=0.7.0-SNAPSHOT \
> -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots

and for a Tellurium TestNG project, use

mvn archetype:create -DgroupId=your\_group\_id -DartifactId=your\_artifact\_id \
> -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium \
> -DarchetypeVersion=0.7.0-SNAPSHOT \
> -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots

For more details,  please read "Tellurium Maven archetypes",

http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes


## Where can I find API documents for Tellurium ##

The user guide for Tellurium DSLs, other APIs, and default UI objects could be found at

http://code.google.com/p/aost/wiki/UserGuide

## Is There a Tellurium Tutorial Available ##

Tellurium provides very detailed tutorials including basic examples, advanced examples, data driven testing examples, and Dsl script examples. Apart from that, We also provide Tellurium Tutorial Series. Please use Tellurium tutorial wiki page as your starting point,

http://code.google.com/p/aost/wiki/Tutorial

We also provide a quick start, "Ten Minutes To Tellurium", at

http://code.google.com/p/aost/wiki/TenMinutesToTellurium

## Tellurium Dependencies ##

Tellurium is built on top of Selenium at the current stage and it uses Selenium 1.0.1 and Selenium Grid tool 1.0.2. Tellurium 0.6.0 was tested with Grooy 1.6.0 and Maven 2.0.9.

You can go to Tellurium core and run the following Maven command to check the dependencies:

```
$ mvn dependency:tree
[INFO] Scanning for projects...
[INFO] Searching repository for plugin with prefix: 'dependency'.
[INFO] ------------------------------------------------------------------------
[INFO] Building Tellurium Core
[INFO]    task-segment: [dependency:tree]
[INFO] ------------------------------------------------------------------------
[INFO] [dependency:tree]
[INFO] tellurium:tellurium-core:jar:0.6.0
[INFO] +- junit:junit:jar:4.4:compile
[INFO] +- org.testng:testng:jar:jdk15:5.8:compile
[INFO] +- caja:json_simple:jar:r1:compile
[INFO] +- org.stringtree:stringtree-json:jar:2.0.10:compile
[INFO] +- org.seleniumhq.selenium.server:selenium-server:jar:1.0.1-te:compile
[INFO] +- org.seleniumhq.selenium.client-drivers:selenium-java-client-driver:jar:1.0.1:compile
[INFO] +- org.openqa.selenium.grid:selenium-grid-tools:jar:1.0.2:compile
[INFO] +- org.codehaus.groovy:groovy-all:jar:1.6.0:compile
[INFO] |  +- org.apache.ant:ant:jar:1.7.1:compile
[INFO] |  |  \- org.apache.ant:ant-launcher:jar:1.7.1:compile
[INFO] |  \- jline:jline:jar:0.9.94:compile
[INFO] +- org.codehaus.groovy.maven.runtime:gmaven-runtime-1.6:jar:1.0-rc-5:compile
[INFO] |  +- org.slf4j:slf4j-api:jar:1.5.6:compile
[INFO] |  +- org.codehaus.groovy.maven.feature:gmaven-feature-support:jar:1.0-rc-5:compile
[INFO] |  |  \- org.codehaus.groovy.maven.feature:gmaven-feature-api:jar:1.0-rc-5:compile
[INFO] |  \- org.codehaus.groovy.maven.runtime:gmaven-runtime-support:jar:1.0-rc-5:compile
[INFO] |     +- org.codehaus.groovy.maven.runtime:gmaven-runtime-api:jar:1.0-rc-5:compile
[INFO] |     +- org.codehaus.groovy.maven:gmaven-common:jar:1.0-rc-5:compile
[INFO] |     +- org.codehaus.plexus:plexus-utils:jar:1.5.5:compile
[INFO] |     \- com.thoughtworks.qdox:qdox:jar:1.8:compile
[INFO] \- bouncycastle:bcprov-jdk15:jar:140:compile
[INFO] ------------------------------------------------------------------------
```

But be aware that some of the dependencies are required **ONLY** for Maven itself, for example, gmaven-runtime, bouncycastle, and plexus.

## What is the `ui.` in UI module ##

Very often, you will see the `ui.` symbol when you define Tellurium UI
modules, for instance, look at the following GoogleSearchModule UI module

```
ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

If you have read the Tellurium core code, you will find the following line in the `BaseDslContext` class,

```
    UiDslParser ui = new UiDslParser()
```

That is to say, the _ui_ is actually an instance of UiDslParser. On the above UI module, you actually call the method "Container" on UiDslParser with a map of attributes plus a Closure with the following nested code.

```
{
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

Look at what the UiDslParser actually does from the source code

```
class UiDslParser extends BuilderSupport{

   def registry = [:]

   def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

   protected Object createNode(Object name) {
   }

   ....
}
```

That is to say, the UiDslParser extends the Groovy `BuilderSupport` class and works as a parser for what ever you passed in starting from `Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true")` in the above example.

You may notice that the `BuilderSupport` class needs to handle couple call back methods such as,

```
protected Object createNode(Object name)

protected Object createNode(Object name, Object value)

protected Object createNode(Object name, Map map)

protected Object createNode(Object name, Map map, Object value)

protected void nodeCompleted(Object parent, Object node)

protected void setParent(Object parent, Object child)
```

If you are familiar with XML parser, you will see that this is really similar with XML PUSH style parser. You need to define call back methods and the parser will parse the message to the end automatically.

The above callback methods are doing the similar thing, i.e., to create a UI object when it sees the name like "Container", "InputBox", and "SubmitButton". The different createNode methods are used for different use cases.

Basically, what the UiDslParser does is to get the object name such as "Container" and then look at the UI builder registry to find the builder for that object, then use the builder to build that UI object. The UI builder registry is a hash map and you can find the Container builder by the object name "Container".

Also the UiDslParser will keep the parse results, i.e., UI objects in a registry so that you can refer them by UID such as "GoogleSearchModule.Search", The object hierarchy is handled by the setParent method.

## How do I add my own UI object to Tellurium ##

First, you need to create your UI object groovy class by extending class UiObject or Container if it is a container type object. Then, you need to create your UI object builder by extending class UiObjectBuilder. Finally, register your ui builder for your ui object by call the
```
public void registerBuilder(String uiObjectName, UiObjectBuilder builder)
```
method in class TelluriumFramework. You can also register your builder in class UiObjectBuilderRegistry if you work on Tellurium source code directly.

From Tellurium 0.4.0, a global configuration file TelluriumConfig.groovy is used for users to customize Tellurium. You can also define your own UI object in this file as follows,

```
    uiobject{
        builder{
           Icon="org.tellurium.builder.IconBuilder"

        }
    }
```

That is to say, you should create the UI object and its builder and then in the configuration file specify the UI object name and its builder full class name. Note, this feature is included in Tellurium 0.5.0, please check the SVN trunk for details.

## How to build Tellurium from source ##

If you want to build Tellurium from source, you can check out the trunk code using subversion command,

```
svn checkout http://aost.googlecode.com/svn/trunk/ tellurium
```

or using mvn command,

```
mvn scm:checkout -DconnectionUrl=scm:svn:http://aost.googlecode.com/svn/trunk -DcheckoutDirectory=tellurium
```

Be aware that the Maven command calls the subversion client to do the job and you must have the client installed in your system.

If you want to build the whole project, just use

```
mvn clean install
```

and Maven will compile source code and resources, compile test code and test resources, run all tests, and then install all artifacts to your local repository under `YOUR_HOME/.m2/repository`.

Sometimes, tests may break and if you still want to proceed, please use the ignore flag

```
mvn clean install -Dmaven.test.failure.ignore=true
```

If you want to build an individual project, just go to that project directory and run the same command as above.

If you like to run the tests, use command

```
mvn test
```

The sub-projects under the tools directory include Tellurium Maven archetypes and trump code, you may not really want to build them by yourself. For trump, the artifacts include a .xpi file.

The assembly project just creates a set of tar files and you may not need to build it either.

Tellurium also provides ant build scripts. You may need to change some of the settings in the build.properties file so that it matches your environment. For example, the settings for javahome and javac.compiler. Then in the project root directory, run command:

```
ant clean
```
to clean up old build and run
```
ant dist
```
to generate a new artifact, which can be found in the dist directory.

Run
```
ant compile-test
```
to compile test code.

## What is the problem with Selenium XPath expressions and why there is need to create UI Module ##

That is a great question, The problem is not in XPath itself, but the way you use it. We have the following XPath locator:

```
"//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]"
```

See any problem with that? It is not robust. Along the path `div -> table -> div -> div ->div -> div`, if anything is changed there, your XPath is no longer valid. For example, if you add additional UI elements and the new XPath was changed to

```
"//div[2]/table[@id='something']/div[3]/div[3]/div[1]/div[6]"
```

You have to keep updating the XPath. For Tellurium, it more focuses on element attributes, not the XPath and it can be adaptive to the changes to some degree.

More importantly, Tellurium uses the group locating concept to use information from a group of UI elements to locate them in the DOM. In most cases, the group of elements themself can be enough to decide their locations in the DOM, that is to say, your UI element's location does not depend on any parent or grandparent elements. For example, in the above example, if you can use the group locating concept to find locators for the following part of UI elements,

```
"div[3]/div[1]/div[6]"
```

directly, then they do not depend on the portion `"div[2]/table[@id='something']/div[3]"`, certainly. your UI elements can address any changes in the portion of `"div[2]/table[@id='something']/div[3]"`. Note, in Tellurium, you most likely will not use a locator in the format of `"div[3]/div[1]/div[6]"` directly.

2) The syntax of

```
selenium.type("//input[@title='Google Search']", input)
selenium.click("//input[@name='btnG' and @type='submit']")

...

selenium.type("//input[@title='Google Search']", input)
selenium.click("//input[@name='btnG' and @type='submit']")

...
 
selenium.type("//input[@title='Google Search']", input)
selenium.click("//input[@name='btnG' and @type='submit']")

...
```

everywhere is really ugly to users. Especially if someone needs to take over your code. In Tellurium, the UiID is used and it is very clear to users what you are acting on.

```
click "google_start_page.googlesearch"
```

3) The test script created by Selenium IDE is a mess of actions, not modularized. Other people may take quite some time to figure out what the script actually does. And it is quite difficult to refactor and reuse them. Even the UI is not changed, there are data dependence there and for most cases, you simply cannot just "record and replay" in practical tests.

In Tellurium, once you defined the UI module, for example, the Google search module, you can always reuse them and write as many test cases as possible.

4) Selenium is a cool and the idea is brilliant. But it is really for low level testing and it only focuses on one element at a time and does not have the whole UI module in mind. That is why we need another tier on top of it so that you can have UI module oriented testing script and not the locator oritented one. Tellurium is one of the frameworks designed for this purpose.

5) As mentioned in 4), Selenium is a quite low level thing and it is really difficult for you to handle more complicated UI components like a data grid. Tellurium can handle them pretty easily, please see our test scripts for our Tellurium project web site.

## How to write assertions in Tellurium DSL scripts ##

Tellurium DSL scripts are actually Groovy scripts written in DSL syntax. Thus, Tellurium DSL scripts support all assertions in JUnit 3.8, which GroovyTestCase extends.

But for Tellurium Data Driven testing scripts, it is a bit different. Usually, you should use:

```
compareResult expected, actual
```

and it in turn calls

```
assertEquals(expected, actual)
```

This is because DDT script has to be general enough for different input data. If you want to use your own assertions, Tellurium provides the capability for that. You should use a Groovy closure to replace the default asserEquals. For example, in your DDT DSL script, you can overwrite the default behaviour using

```
compareResult(expected, actual){
        assertNotNull(expected)
        assertNotNull(actual)
        assertTrue(expected.size() == actual.size())
}
```

This brings up one interesting question "why should I put assertions inside compareResult, not anywhere in the script?" The answer is that you can put assertions any where in the DDT script, but that will cause different behaviour if the assertion fails.

If you put assertions in compareResult and the assertion fails, the AssertionFailedError will be captured and that comparison fails, but the rest script inside a test will continue executing. But if you put assertions outside of compareResult, the AssertionFailedError will lead to the failure of the current test. The exception will be
recorded and the current test will be stopped. The next test will take over and execute.

## How to run Selenium server remotely in Tellurium ##

The steps to use remote selenium server in Tellurium are as follows,

First, run selenium sever on the remote machine, saying 192.168.1.106

```
java -jar selenium-server.jar -port 4444
```

for more selenium server options, please use the following commands:

```
java -jar selenium-server.jar --help
```

Then, you should modify the TelluriumConfig.groovy as follows,

```
tellurium{
   //embedded selenium server configuration
   embeddedserver {
       //port number
       port = "4444"
       //whether to use multiple windows
       useMultiWindows = false
       //whether to run the embedded selenium server. 
       //If false, you need to manually set up a selenium server
       runInternally = false
   }
   //the configuration for the connector that connects the selenium client 
   //to the selenium server
   connector{
       //selenium server host
       //please change the host if you run the Selenium server remotely
       serverHost = "192.168.1.106"

       //server port number the client needs to connect
       port = "4444"
       //base URL
       baseUrl = "http://localhost:8080"
       //Browser setting, valid options are
       //  *firefox [absolute path]
       //  *iexplore [absolute path]
       //  *chrome
       browser = "*iehta"
   }
......
}
```

That is to say, you should disable the embedded selenium server by specifying
```
runInternally = false
```
and specify the remote selenium server host as
```
serverHost = "192.168.1.106"
```

After that, you can run the test just like using the embedded selenium server. But be aware that there are some performance degradation, i.e., the test is slower with remote selenium server.

## Differences among Tellurium Table, List, and Container ##

Container is most like an abstract object and it can be of any type of UI objects that can hold other UI objects. The UI objects inside the Container are fixed once it is defined and inner objects can be referred directly by "container\_uid.object\_uid". Be aware that Tellurium Container type objects can hold any UI objects including container type objects and nested UI can be constructed in this way.

Table and List are both Container type UI objects and are designed mainly for dynamic size UI objects. For example, table can be used to mode data grid, whose size is not fixed and is dynamic at run-time. For this purpose, the UI objects inside the table can be used as templates and how they are used is totally dependent on their UIDs.

For table, the UID of its inner objects is in the following formats:

  1. `"row: i, colum: j"`, which defines the UI object for the `[i][j]` element.
  1. `"row: *, column: j"`, which defines the UI objects for all elements in column j.
  1. `"row: i, column: *"`, similarly defines the UI objects for all elements in row i.
  1. "all", which defines the UI objects for all elements.

At runtime, the following rules applies if any of them are defined:
```
1) > 2) > 3) > 4)
```
In this way, you can always define various templates for your table.

Once the templates are defined and you use `table[i][j]` to refer the inner object, Tellurium will automatically apply the above rules and find the actual UI object for you. If no templates can be found, Tellurium will use default UI object TextBox.

One such good example is the data grid of Tellurium downloads page:
```
ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], group: "true"){
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
```

List is similar to the Table, but it is one dimension. As a result, its UID uses the following formats:

  1. "i": the i-th element
  1. "all": all elements

the rule is `1) > 2)`.

## How do I use a Firefox profile in Tellurium ##

You can specify the profile in Tellurium Configuration file TelluriumConfig.groovy as follows,

```
 embeddedserver {

       ......

       //profile location, for example,
       profile = "/home/jfang/.mozilla/firefox/820j3ca9.default"
   }
```

## How to Overwrite Tellurium Settings in My Test Class ##

TelluriumConfig.groovy acts like a global setting file if you do not want to manually change it. Now, the  BaseTelluriumJavaTestCase provides two methods for you to overwrite the default settings,

```
public static void setCustomConfig(boolean runInternally, int port, String browser,
                      boolean useMultiWindows, String profileLocation)

public static void setCustomConfig(boolean runInternally, int port, String browser,
                      boolean useMultiWindows, String profileLocation, String serverHost)
```

As you result, if you want to use your custom settings for your specific test class, you can use the following way taking the Google test case as an example,

```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase
{
   static{
       setCustomConfig(true, 5555, "*chrome", true, null);
   }

...

}
```

## How to reuse a frequently used set of elements ##

The "Include" syntax in Ui module definition can be used for this purpose. You can put frequently used UI modules into a base class, for example,

```
public class BaseUiModule extends DslContext {
  public void defineBaseUi() {
    ui.Container(uid: "SearchModule", clocator: [tag: "td"], group: "true") {
      InputBox(uid: "Input", clocator: [title: "Google Search"])
      SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
    }

    ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], 
                 group: "true") 
    {
      TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
      List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"],
          separator: "p") 
      {
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
        UrlLink(uid: "SearchPreferences", clocator: [tag: "a", 
                                                       text: "Search Preferences"])
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

## How to handle Table with multiple tbody elements ##

The StandardTable is designed for tables with the following format

```

table
      thead
         tr
           td
           ...
           td
      tbody
         tr
           td
           ...
           td
         ...
       tbody (multiple tbodies)
         tr
           td
           ...
           td
         ...
      tfoot
         tr
           td
           ...
           td 
```

For a StandardTable, you can specify UI templates for different tbodies. For Example:

```
ui.StandardTable(uid: "table", clocator: [id: "std"]) {
   UrlLink(uid: "header: 2", clocator: [text: "%%Filename"])
   UrlLink(uid: "header: 3", clocator: [text: "%%Uploaded"])
   UrlLink(uid: "header: 4", clocator: [text: "%%Size"])
   TextBox(uid: "header: all", clocator: [:])

   Selector(uid: "tbody: 1, row:1, column: 3", clocator: [name: "can"])
   SubmitButton(uid: "tbody: 1, row:1, column:4", 
                                clocator: [value: "Search", name: "btn"])
   InputBox(uid: "tbody: 1, row:2, column:3", clocator: [name: "words"])
   InputBox(uid: "tbody: 2, row:2, column:3", clocator: [name: "without"])
   InputBox(uid: "tbody: 2, row:*, column:1", clocator: [name: "labels"])

   TextBox(uid: "foot: all", clocator: [tag: "td"])
}
```

## How to use the new XPath library in Selenium ##

There are three methods in DslContext for you to select different XPath Library,

```
    public void useDefaultXPathLibrary()

    public void useJavascriptXPathLibrary()

    public void useAjaxsltXPathLibrary()
```

The default one is the same as the "Ajaxslt" one. To use faster xpathlibrary, please call `useJavascriptXPathLibrary()`.

For example, in the test case file,

```
    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
        ngsp.useJavascriptXPathLibrary();
    } 
```

## How to Debug Selenium Core ##

You can use Microsoft Script Debugger to debug the Selenium Core in IE.

To debug the javascript code, follow the following step,

  1. Start custom selenium server in multiWindow mode and another useful command option is `-debug`, which will print out all trace messages

```
    java -jar selenium-server -multiWindow
```

  1. Debug the Java code in IDE and set a break point somewhere in the code
  1. Once the Java process paused, open up the Microsoft script debugger or Editor MSE7.exe.
  1. Attach you debugger to the running IE instance and you will see the JavaScript you want to debug, set a break point there.
  1. Resume you Java process and it will wait there once the breakpoint is hit in the JavaScript debugger. Then you can step into, step over, or run the JavaScript.

For Firefox, you can debug using Firebug using the same steps. But you may need to use Firefox profile to start the custom server server so that Firebug will be included in the launched instance.

## How to use jQuery Selector with weird characters in its ID ##

You should escape the "." or other jQuery reserved characters.

For example, use "dateOfBirth.\\month" for "dateOfBirth.month" as the ID.

## How to Use Tellurium for XHTML ##

For XHTML, you need to use the _namespace attribute in the UI object, for example,_

```
ui.Container(uid:"caseRecordPopUp", clocator:[id:"CaseRecordsPopUp",
              tag:"div"],namespace:"xhtml",group:"true") {
    Container(uid:"date",clocator:[id:"caseRecordPopUpDate",
              tag:"input"], namespace:"xforms")
    ......

} 
```

You can register a custom namespace as follows,

```
registerNamespace("xforms", "http://www.w3.org/2002/xforms")
```

and use the following method to get back the namespace

```
getNamespace("xforms"); 
```

## What Are the Differences Between connectUrl and openUrl ##

Starting from Tellurium 0.7.0, `connectUrl()` and `openUrl()` are used for different purposes. That is to say, `openUrl()` is used to instantiate a new browser session for each call, while `connectUrl()` reuses the same browser session. Under the hood, you have

```
openUrl() = connectSeleniumServer() + connectUrl()
```

As a result, you need to call `connectSeleniumServer()` before you call `connectUrl()` and then keep calling `connectUrl()` if you want to reuse the same browser session.

## How to do Attribute Partial Matching in Tellurium ##

Tellurium adopts jQuery style partial matching, that is to say, you should use the following format for partial matching:

```
"PARTIAL_MATCHING_SIGNYour_string"
```

where the partial matching signs include:

  * _`!`_: either don't have the specified attribute or do have the specified attribute but not with a certain value.
  * _`^`_: have the specified attribute and it starts with a certain value.
  * _`$`_: have the specified attribute and it ends with a certain value.
  * _`*`_: have the specified attribute and it contains a certain value.

Be aware, due to the limitations of XPath, _`$`_ and _`*`_ are the same in XPath locator.

Examples:

```
clocator: [href:'!xevent']
clocator: [href:'^xevent']
clocator: [href:'$xevent']
clocator: [href:'*xevent']
```

## How to Access Tellurium Maven Repo Behind a Firewall ##

You can download [NTLM Authorization Proxy Server](http://ntlmaps.sourceforge.net/) and install it as a local proxy. Then add the following proxy settings in your HOME/.m2/settings.xml.

```
  <proxies>
   <proxy>
      <active>true</active>
      <protocol>http</protocol>
      <host>localhost</host>
      <port>5865</port>
      <!--username>proxyuser</username>
      <password>somepassword</password-->
      <nonProxyHosts></nonProxyHosts>
    </proxy>
  </proxies>
```

The _nonProxyHosts_ parameter should include all the hosts you don't want them to go through the proxy server, for example, we have the following setting for that.

```
<nonProxyHosts>*.mycompany.com|*.mycompanydoman2.com</nonProxyHosts>
```

## How to Contribute to Tellurium ##

We welcome contributions in many different ways, for example,

  1. Try out Tellurium
  1. Use Tellurium in your project and report bugs
  1. Ask questions and answer other users' questions
  1. Promote Tellurium and post your experience on Tellurium
  1. Fix bugs for Tellurium
  1. Bring in new ideas and suggestions

Our project team is open for new members and is expecting new members. We need some new developers who are JavaScript and jQuery experts to join our development team. If you have free time and want to contribute to Tellurium, please contact Jian Fang or other existing Tellurium members.

We also expect to have some Evangelism Team members, who could write blogs and articles about Tellurium, or present Tellurium to people, i.e., promote Tellurium in various ways. We welcome Evangelism Team members from different parts of the world and promote Tellurium in different Languages.

## Tellurium Future Directions ##

Tellurium 0.6.0 added jQuery Selector support and a cache mechanism to increase the reuse of UI elements. From the next release, i.e., 0.7.0, we will work on our own Engine project to better support UI modules and achieve more robust performance and better speed. In the meanwhile, We will improve our Trump plugin to automatically generate DSL testing scripts for non-developers.