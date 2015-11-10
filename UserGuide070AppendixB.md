(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))



# Appendix B Tellurium Frequently Asked Questions (FAQs) #

## When Did Tellurium Start? ##

Tellurium was over one year old in June 2009 if we count the date from the day it became an open source project. But actually, Tellurium had been through two phases of prototyping before that. The first prototype was created in 2007 to test our company's Dojo web applications, which was basically a Java framework based on Spring XML wiring and no UI modules. You have to use factories to create all UI objects.

As a result, it was not convenient to use. The second prototype was created in early 2008 to improve the usability of the first prototype. The UI module was introduced in the second prototype. Both prototypes had been used for a few internal projects before it was re-written in Groovy and became an open source project in June 2008. Notice that prototype framework is called AOST and it was officially renamed to the Tellurium Automated Testing framework (Tellurium) in July 2008 when it moved out of the prototyping phase and became a team project.

## What Are the Main Differences Between Tellurium and Selenium? ##

Tellurium was created when I was a Selenium user and tried to address some of the shortcomings of the Selenium framework such as verbosity and fragility to changes. Selenium is a great web testing framework and up to 0.6.0, Tellurium uses Selenium core as the test driving engine. From Tellurium 0.7.0, we will gradually replace the Selenium core with our own Engine.

Although Tellurium was born from Selenium, there are some fundamental differences between Tellurium and Selenium, which mainly come from the fact that Tellurium is a UI module-based testing framework. For example, Tellurium focuses on a set of UI elements instead of individual ones. The UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium project site is defined as follows:

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

With the UI module, Tellurium automatically generates runtime locators for you and there is no need to define XPaths or other types of locators by yourself. Tellurium is robust, expressive, flexible, and reusable.

## Do I Need to Know Groovy Before I Use Tellurium? ##

Tellurium Core is implemented in Groovy and Java to achieve expressiveness. But that does not mean you have to be familiar with Groovy before you start to use Tellurium. Tellurium creates DSL expressions for UI module, actions, and testing. Use a Groovy class to implement the UI module by extending the DslContext Groovy class. Then the user can write the rest using Java syntax. The test cases can be created in Java, Groovy, or Dsl scripts. However, we do encourage geting familiar with Groovy to leverage its meta programming features.

To create a Tellurium project, install a Groovy plugin for your IDE. There are Groovy plugins for commonly used IDEs such as Eclipse, Netbeans, and IntelliJ. Refer to the following WIKI pages on how to set up Groovy and use Tellurium in different IDEs,

[http://code.google.com/p/aost/wiki/TelluriumReferenceProjectEclipseSetup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectEclipseSetup)

[http://code.google.com/p/aost/wiki/TelluriumReferenceProjectNetBeansSetup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectNetBeansSetup)

[http://code.google.com/p/aost/wiki/TelluriumReferenceProjectIntelliJSetup](http://code.google.com/p/aost/wiki/TelluriumReferenceProjectIntelliJSetup)

## What Unit Test and Functional Test Frameworks Does Tellurium Support? ##

Tellurium supports both JUnit and TestNG frameworks. Extend TelluriumJavaTestCase for JUnit and TelluriumTestNGTestCase for TestNG. For more details, please check the following WIKI pages:

[http://code.google.com/p/aost/wiki/BasicExample](http://code.google.com/p/aost/wiki/BasicExample)

[http://code.google.com/p/aost/wiki/Introduction](http://code.google.com/p/aost/wiki/Introduction)

Tellurium also provides data driven testing. Data Driven Testing is a different way to write tests. For example, test data are separated from the test scripts and the test flow is not controlled by the test scripts, but by the input file instead. In the input file, users can specify which test to run, what the input parameters are, and what the expected results are. More details can be found from "Tellurium Data Driven Testing" WIKI page,

[http://code.google.com/p/aost/wiki/DataDrivenTesting](http://code.google.com/p/aost/wiki/DataDrivenTesting)

## Does Tellurium Provide Any Tools to Automatically Generate UI Modules? ##

Tellurium UI Model Plugin (TrUMP) is a Firefox Plugin used to automatically generate UI modules simply by clicking on the web page under testing. A user can download it from the Tellurium download page at:

[http://code.google.com/p/aost/download/list](http://code.google.com/p/aost/downloads/list)

or from Firefox Addons site at:

[https://addons.mozilla.org/en-US/firefox/addon/11035](https://addons.mozilla.org/en-US/firefox/addon/11035)

The detailed user guide for TrUMP 0.1.0 is at:

[http://code.google.com/p/aost/wiki/TrUMP](http://code.google.com/p/aost/wiki/TrUMP)

To understand more about how TrUMP works, please read "How does TrUMP work?" at:

[http://code.google.com/p/aost/wiki/HowTrUMPWorks](http://code.google.com/p/aost/wiki/HowTrUMPWorks)

## What Build System Does Tellurium Use? ##

Tellurium supports both Ant and Maven build systems. The ant build scripts are provided in Tellurium core and Tellurium reference projects. For Maven, please check out the Tellurium Maven guide at:

[http://code.google.com/p/aost/wiki/MavenHowTo](http://code.google.com/p/aost/wiki/MavenHowTo)

## What is the Best Way to Create a Tellurium Project? ##

Tellurium provides two reference projects for JUnit and TestNG project, respectively. Use one of them as a template project. Please see the reference project guide at:

[http://code.google.com/p/aost/wiki/ReferenceProjectGuide](http://code.google.com/p/aost/wiki/ReferenceProjectGuide)

However, the best and easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetypes. For example, tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively.

As a result, you can create a Tellurium project using one Maven command. For a Tellurium JUnit project, use:

```
mvn archetype:create -DgroupId =your_group_id -DartifactId=your_artifact_id \
-DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium\ -DarchetypeVersion=0.7.0-SNAPSHOT \ 
-DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

and for a Tellurium TestNG project, use

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
-DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium \ -DarchetypeVersion=0.7.0-SNAPSHOT \ 
-DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots 
```
For more details, please read "Tellurium Maven archetypes",

[http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes)

## Where Can I Find API Documents for Tellurium? ##

The user guide for Tellurium DSLs, other APIs, and default UI objects could be found at:

[http://code.google.com/p/aost/wiki/UserGuide070Introduction](http://code.google.com/p/aost/wiki/UserGuide070Introduction)

## Is There a Tellurium Tutorial Available? ##

Tellurium provides very detailed tutorials including basic examples, advanced examples, data driven testing examples, and Dsl script examples. We also provide Tellurium Tutorial Series. Please use Tellurium tutorial WIKI page as your starting point,

[http://code.google.com/p/aost/wiki/Tutorial](http://code.google.com/p/aost/wiki/Tutorial)

We also provide a quick start, "Ten Minutes To Tellurium", at

[http://code.google.com/p/aost/wiki/TenMinutesToTellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)

## Where Can I Find a Sample Tellurium Configuration File? ##

Tellurium Sample Configuration File is available [here](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile)

## Tellurium Dependencies ##

Tellurium is built on top of Selenium at the current stage and it uses Selenium 1.0.1 and Selenium Grid tool 1.0.2. Tellurium 0.6.0 was tested with Grooy 1.6.0 and Maven 2.0.9.

You can go to Tellurium core and run the following Maven command to check the dependencies. For example, the dependency tree for 0.7.0 is shown as follows:

```
$ mvn dependency:tree

[INFO] [dependency:tree {execution: default-cli}]
[INFO] tellurium:tellurium-core:jar:0.7.0-SNAPSHOT
[INFO] +- junit:junit:jar:4.4:compile
[INFO] +- org.testng:testng:jar:jdk15:5.8:compile
[INFO] +- caja:json_simple:jar:r1:compile
[INFO] +- org.apache.poi:poi:jar:3.0.1-FINAL:compile
[INFO] |  \- commons-logging:commons-logging:jar:1.1:compile
[INFO] |     \- log4j:log4j:jar:1.2.13:compile
[INFO] +- org.stringtree:stringtree-json:jar:2.0.10:compile
[INFO] +- org.seleniumhq.selenium.server:selenium-server:jar:1.0.1-te2:compile
[INFO] +- org.seleniumhq.selenium.client-drivers:selenium-java-client-driver:jar:1.0.1:compile
[INFO] +- org.codehaus.groovy:groovy-all:jar:1.6.0:compile
[INFO] |  +- org.apache.ant:ant:jar:1.7.1:compile
[INFO] |  |  \- org.apache.ant:ant-launcher:jar:1.7.1:compile
[INFO] |  \- jline:jline:jar:0.9.94:compile
[INFO] +- org.codehaus.gmaven.runtime:gmaven-runtime-1.6:jar:1.1-SNAPSHOT:compile
[INFO] |  +- org.slf4j:slf4j-api:jar:1.5.8:compile
[INFO] |  +- org.codehaus.gmaven.feature:gmaven-feature-support:jar:1.1-SNAPSHOT:compile
[INFO] |  |  \- org.codehaus.gmaven.feature:gmaven-feature-api:jar:1.1-SNAPSHOT:compile
[INFO] |  \- org.codehaus.gmaven.runtime:gmaven-runtime-support:jar:1.1-SNAPSHOT:compile
[INFO] |     +- org.codehaus.gmaven.runtime:gmaven-runtime-api:jar:1.1-SNAPSHOT:compile
[INFO] |     +- org.codehaus.gmaven:gmaven-common:jar:1.1-SNAPSHOT:compile
[INFO] |     +- org.codehaus.plexus:plexus-utils:jar:1.5.5:compile
[INFO] |     \- com.thoughtworks.qdox:qdox:jar:1.8:compile
[INFO] \- bouncycastle:bcprov-jdk15:jar:140:compile
[INFO] ------------------------------------------------------------------------

```

But be aware that some of the dependencies are required ONLY for Maven itself, for example, gmaven-runtime, bouncycastle, and plexus.

If you use ant, please download [the dependent files for 0.6.0](http://aost.googlecode.com/files/tellurium-0.6.0-dependencies.zip). For 0.7.0, you need [http://maven.kungfuters.org/content/repositories/thirdparty/org/seleniumhq/selenium/server/selenium-server/1.0-te-2/ selenium-server-1.0.1-te2.jar](.md) and [the Apache POI - the Java API for Microsoft Documents](http://poi.apache.org/download.html).

## What Is the ui. in UI Module? ##

Very often, you will see the ui. symbol when you define Tellurium UI modules. For instance, look at the following GoogleSearchModule UI module:

```
ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

If you have read the Tellurium core code, you will find the following line in the BaseDslContext class,

`    UiDslParser ui = new UiDslParser()`

The ui is actually an instance of UiDslParser. On the above UI module, call the method "Container" on UiDslParser with a map of attributes plus a Closure with the following nested code.

```
{
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

Look at what the UiDslParser actually does from the source code:
```
class UiDslParser extends BuilderSupport{

   def registry = [:]

   def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

   protected Object createNode(Object name) {
   }

   ....
}
```

The UiDslParser extends the Groovy `BuilderSupport` class and works as a parser for what ever you passed in starting from `Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true")` in the above example.

You may notice that the `BuilderSupport` class needs to handle couple call back methods such as:

```
protected Object createNode(Object name)

protected Object createNode(Object name, Object value)

protected Object createNode(Object name, Map map)

protected Object createNode(Object name, Map map, Object value)

protected void nodeCompleted(Object parent, Object node)

protected void setParent(Object parent, Object child)
```

If you are familiar with XML parser, you will see that this is really similar to the XML PUSH style parser. Define call back methods and the parser will parse the message to the end automatically.

The above callback methods are doing the similar thing. For example, to create a UI object when it sees the name like "Container", "InputBox", and "SubmitButton". The different createNode methods are used for different use cases.

Basically, what the UiDslParser does is to get the object name such as "Container" and then look at the UI builder registry to find the builder for that object, then use the builder to build that UI object. The UI builder registry is a hash map and you can find the Container builder by the object name "Container".

Also the UiDslParser will keep the parse results. For example, UI objects in a registry so that you can refer to them by UID such as "GoogleSearchModule.Search", The object hierarchy is handled by the setParent method.

## How Do I Add My Own UI Object to Tellurium? ##

First, create your UI object groovy class by extending class UiObject or Container if it is a container type object. Then, create your UI object builder by extending class UiObjectBuilder. Finally, register your ui builder for your ui object by call method in class TelluriumFramework:

```
public void registerBuilder(String uiObjectName, UiObjectBuilder builder)
```

You can also register your builder in class UiObjectBuilderRegistry if you work on Tellurium source code directly.

From Tellurium 0.4.0, a global configuration file TelluriumConfig.groovy is used to customize Tellurium. You can also define your own UI object in this file as follows,

```
    uiobject{
        builder{
           Icon="org.tellurium.builder.IconBuilder"
        }
    }
```

That is to say, create the UI object and its builder and then in the configuration file specify the UI object name and its builder full class name. '''Note''': this feature is included in Tellurium 0.5.0. Please check the SVN trunk for details.

## How to Build Tellurium from Source ##

If you want to build Tellurium from source, you can check out the trunk code using the subversion command:

```
svn checkout http://aost.googlecode.com/svn/trunk/ tellurium
```

or using the Maven (`mvn`) command:

```
mvn scm:checkout -DconnectionUrl=scm:svn:http://aost.googlecode.com/svn/trunk -DcheckoutDirectory=tellurium
```

Be aware that the Maven command calls the subversion client to do the job and you must have the client installed in your system.

To build the whole project, use:

```
mvn clean install
```

and Maven compiles source code and resources, compiles test code and test resources, runs all tests, and then installs all artifacts to your local repository under YOUR\_HOME/.m2/repository.

Sometimes, tests may break and if you still want to proceed, please use the ignore flag:

```
mvn clean install -Dmaven.test.failure.ignore=true
```

To build an individual project, go to that project directory and run the same command as above.

To run the tests, use the command:

```
mvn test
```

The sub-projects under the tools directory include Tellurium Maven archetypes and TrUMP code, you may not really want to build them by yourself. For TrUMP, the artifacts include a .xpi file.

The assembly project just creates a set of tar files and you may not need to build it either.

Tellurium also provides ant build scripts. You may need to change some of the settings in the build.properties file so that it matches your environment. For example, the settings for javahome and javac.compiler. Then in the project root directory, run command:

```
ant clean
```

to clean up old build and run:

```
ant dist
```

to generate a new artifact, which can be found in the dist directory.

Run the following to compile test code:

```
ant compile-test
```

## What is the Issue with Selenium XPath Expressions and Why is There a Need to Create a UI Module? ##

The problem is not in XPath itself, but the way you use it. If the following XPath locator is:

```
"//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]"
```

then the problem is easily seen. It is not robust. Along the path

```
div -> table -> div -> div ->div -> div
```

if anything is changed there, your XPath is no longer valid. For example, if you add additional UI elements and the new XPath was changed to:

```
"//div[2]/table[@id='something']/div[3]/div[3]/div[1]/div[6]"
```

you would have to keep updating the XPath. For Tellurium, it focuses on element attributes, not the XPath, and it can be adaptive to the changes to some degree.

More importantly, Tellurium uses the group locating concept to use information from a group of UI elements to locate them in the DOM. In most cases, the group of elements are enough to decide their locations in the DOM, that is to say, your UI element's location does not depend on any parent or grandparent elements.

For instance, in the example above, if you use the group locating concept to find locators for the following part of UI elements directly:

```

"div[3]/div[1]/div[6]"

```

then they do not depend on the portion certainly.

```

"div[2]/table[@id='something']/div[3]" 

```

1) The UI elements can address any changes in the portion of :

```

"div[2]/table[@id='something']/div[3]"

```

**Note:** In Tellurium, the user will not use a locator in the format of:

`"div[3]/div[1]/div[6]" `
directly.

2) The syntax of:
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

everywhere is really ugly to users. Especially if someone needs to take over your code. In Tellurium, the UiID is used and it is very clear to users what you are acting upon.

`click "google_start_page.googlesearch"`

3) The test script created by Selenium IDE is a mess of actions, not modularized. Other people may take quite some time to figure out what the script actually does. And it is quite difficult to refactor and reuse them. Even the UI is not changed, there are data dependence there and for most cases, you simply cannot just "record and replay" in practical tests.

In Tellurium, once you defined the UI module, for example, the Google search module, you can always reuse them and write as many test cases as possible.

4) Selenium is cool and the idea is brilliant. But it is really for low level testing only, focusing on one element at a time and it does not have the whole UI module in mind. That is why another tier on top of it is needed so that you can have a UI module-oriented testing script and not the locator-oriented one. Tellurium is one of the frameworks designed for this purpose.

5) As mentioned in 4), Selenium is quite a low level process and it is really difficult to handle more complicated UI components like a data grid. Tellurium can handle them easily. Please see the test scripts for the Tellurium project web site.

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

## How to upgrade Firefox version in Selenium server ##

You can do the following steps:
  * unpack the custom selenium-server by running
```
   jar xvf selenium-server.jar
```

  * Find the versions in install.rdf and change them, for instance,

```
/customProfileDirCUSTFF/extensions/readyst...@openqa.org/install.rdf
./customProfileDirCUSTFF/extensions/{538F0036-F358-4f84-A764-89FB437166B4}/install.rdf
./customProfileDirCUSTFFCHROME/extensions/readyst...@openqa.org/install.rdf
./customProfileDirCUSTFFCHROME/extensions/{503A0CD4-EDC8-489b-853B-19E0BAA8F0A4}/install.rdf
./customProfileDirCUSTFFCHROME/extensions/{538F0036-F358-4f84-A764-89FB437166B4}/install.rdf
./customProfileDirCUSTFFCHROME/extensions/{636fd8b0-ce2b-4e00-b812-2afbe77ee899}/install.rdf

change the versions from 

        <em:targetApplication>
            <Description>
                <em:id>{ec8030f7-c20a-464f-9b0e-13a3a9e97384}</em:id>
                <em:minVersion>1.4.1</em:minVersion>
                <em:maxVersion>3.5.*</em:maxVersion>
            </Description>
        </em:targetApplication>

to

        <em:targetApplication>
            <Description>
                <em:id>{ec8030f7-c20a-464f-9b0e-13a3a9e97384}</em:id>
                <em:minVersion>1.4.1</em:minVersion>
                <em:maxVersion>3.6.*</em:maxVersion>
            </Description>
        </em:targetApplication>
```

  * repack the jar file.

```
   jar cmf META-INF/MANIFEST.MF selenium-server.jar *
```

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

Table and List are both Container type UI objects and are designed mainly for dynamic size UI objects. For example, table can be used to mode data grid, whose size is not fixed and is dynamic at run-time. For this purpose, the UI objects inside the table can be used as templates and how they are used is totally dependent on their UIDs. For more details on how the UIDs for List and Table are defined, please see [Tellurium UID Description Language](http://code.google.com/p/aost/wiki/TelluriumUIDDescriptionLanguage).

Once the templates are defined and you use `table[i][j]` to refer the inner object, Tellurium will automatically apply the above rules and find the actual UI object for you. If no templates can be found, Tellurium will use default UI object TextBox.

One such good example is the data grid of Tellurium downloads page:
```
ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], group: "true"){
    //define table elements
    //for the border column
    TextBox(uid: "{row: all, column: 1}", clocator: [:])
    //the summary + labels column consists of a list of UrlLinks
    List(uid: "{row:all, column: 3}", clocator: [:]){
        UrlLink(uid: "{all}", clocator: [:])
    }
    //For the rest, just UrlLink
    UrlLink(uid: "{row: all, column: all}", clocator: [:])
}
```

## How do I use a Firefox profile in Tellurium ##

You can specify the profile in Tellurium Configuration file TelluriumConfig.groovy as follows,

```
 embeddedserver {

       ......

       //profile location, for example,
       profile = "/home/jfang/.mozilla/firefox/820j3ca9.default"
   }
```

This is especially useful if you are behind a firewall. Please read more about
[Firefox Profiles](http://support.mozilla.com/en-US/kb/Profiles#On_Windows_2000_and_XP).

If you run the Selenium server externally, you can specify the Firefox profile using the following option:

```
  -firefoxProfileTemplate <dir>: normally, we generate a fresh empty Firefox profile
     every time we launch.  You can specify a directory to make us copy your profile directory instead.
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
        UrlLink(uid: "{all}", clocator: [:])
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
   UrlLink(uid: "{header: 2}", clocator: [text: "*Filename"])
   UrlLink(uid: "{header: 3}", clocator: [text: "*Uploaded"])
   UrlLink(uid: "{header: 4", clocator: [text: "*Size"])
   TextBox(uid: "{header: all}", clocator: [:])

   Selector(uid: "{tbody: 1, row:1, column: 3}", clocator: [name: "can"])
   SubmitButton(uid: "{tbody: 1, row:1, column:4}", 
                                clocator: [value: "Search", name: "btn"])
   InputBox(uid: "{tbody: 1, row:2, column:3}", clocator: [name: "words"])
   InputBox(uid: "{tbody: 2, row:2, column:3}", clocator: [name: "without"])
   InputBox(uid: "{tbody: 2, row:all, column:1}", clocator: [name: "labels"])

   TextBox(uid: "{foot: all}", clocator: [tag: "td"])
}
```

## How to Run Tellurium Tests in Different Browsers ##

You could use the `openUrlWithBrowserParameters()` methods to change browser settings for different test cases in the same test class,

```
 public static void openUrlWithBrowserParameters(String url, String
serverHost, int serverPort, String baseUrl, String browser, String
browserOptions)

   public static void openUrlWithBrowserParameters(String url, String
serverHost, int serverPort, String browser, String browserOptions)

   public static void openUrlWithBrowserParameters(String url, String
serverHost, int serverPort, String browser)
```

For example,

```
public class GoogleStartPageTestNGTestCase extends TelluriumTestNGTestCase {
   protected static NewGoogleStartPage ngsp;

   @BeforeClass
   public static void initUi() {
       ngsp = new NewGoogleStartPage();
       ngsp.defineUi();
   }

   @DataProvider(name = "browser-provider")
   public Object[][] browserParameters() {
       return new Object[][]{
               new Object[] {"localhost", 4444, "*chrome"},
               new Object[] {"localhost", 4444, "*firefox"}};
   }

   @Test(dataProvider = "browser-provider")
   @Parameters({"serverHost", "serverPort", "browser"})
   public void testGoogleSearch(String serverHost, int serverPort, String browser){
       openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
       ngsp.doGoogleSearch("tellurium selenium Groovy Test");
       disconnectSeleniumServer();
  }

  @Test(dataProvider = "browser-provider")
  @Parameters({"serverHost", "serverPort", "browser"})
  public void testGoogleSearchFeelingLucky(String serverHost, int serverPort, String browser){
      openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
      ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
      disconnectSeleniumServer();
  }
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

## How to Debug Tellurium in IE ##

(Contributed by Dominic Mooney).

Script to add Firebug Lite to a page

```
String script = "var firebug=document.createElement('script');" +
       "firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');" +
       "document.body.appendChild(firebug);" +
       "(function(){if(window.firebug.version){firebug.init();}else {setTimeout(arguments.callee);}})();" +
       "void(firebug);";
```

Call addScript Selenium command, which will add a script to the Selenium runner window.
```
   addScript(script, "firebug-lite");
```

After the code has run you should see a Firebug Lite window in the Selenium runner window (use milti-window mode). From there you can do the usual debug stuff, such as this, which uses jQuery to find all parents of an element.

```
teJQuery(selenium.browserbot.findElement("your-locator")).parents()
```

Call the following command to remove the script.

```
   removeScript("firebug-lite);
```

## How to use jQuery Selector with weird characters in its ID ##

You should escape the "." or other jQuery reserved characters.

For example, use "dateOfBirth\\.month" for "dateOfBirth.month" as the ID.

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
  * _`\$`_: have the specified attribute and it ends with a certain value. Be aware that `$` is reserved in Groovy for GString, please add "\" to escape it.
  * _`*`_: have the specified attribute and it contains a certain value.

Be aware, due to the limitations of XPath, _`$`_ and _`*`_ are the same in XPath locator.

Examples:

```
clocator: [href:'!xevent']
clocator: [href:'^xevent']
clocator: [href:'\$xevent']
clocator: [href:'*xevent']
```

## What are the rules to define Tellurium UIDs ##

With the addition of Tellurium UID Description Language (UDL), for most UI objects, the UIDs are IDs, but for List and Table objects, the UIDs include UI template definitions and IDs.

The rules for the ID definition are as follows:

  * Starts with a letter and followed by either letter, digits, or underscore `"_"`.
  * `row`, `column`, `header`, `tbody`, `tfooter` are reserved by UDL as tokens and you should not use them as IDs.

## How to load Tellurium configuration from a String ##

From 0.7.0, Tellurium added support to load Tellurium configuration from a JSON String. For example, you can define the Tellurium configuration JSON string as follows.

```
public class JettyLogonModule extends DslContext {
 public static String JSON_CONF = """{"tellurium":{"test":{"result":{"reporter":"XMLResultReporter","filename":"TestResult.output","output":"Console"},"exception":{"filenamePattern":"Screenshot?.png","captureScreenshot":false},"execution":{"trace":false}},"accessor":{"checkElement":false},"embeddedserver":{"port":"4444","browserSessionReuse":false,"debugMode":false,"ensureCleanSession":false,"interactive":false,"avoidProxy":false,"timeoutInSeconds":30,"runInternally":true,"trustAllSSLCertificates":true,"useMultiWindows":false,"userExtension":"","profile":""},"uiobject":{"builder":{}},"eventhandler":{"checkElement":false,"extraEvent":false},"i18n":{"locale":"en_US"},"connector":{"baseUrl":"http://localhost:8080","port":"4444","browser":"*chrome","customClass":"","serverHost":"localhost","options":""},"bundle":{"maxMacroCmd":5,"useMacroCommand":true},"datadriven":{"dataprovider":{"reader":"PipeFileReader"}},"widget":{"module":{"included":""}}}}""";

...
}
```

where the pretty look version of the JSON String is shown as follows.

```
{
    "tellurium": {
        "test": {
            "result": {
                "reporter": "XMLResultReporter",
                "filename": "TestResult.output",
                "output": "Console"
            },
            "exception": {
                "filenamePattern": "Screenshot?.png",
                "captureScreenshot": false
            },
            "execution": {
                "trace": false
            }
        },
        "accessor": {
            "checkElement": false
        },
        "embeddedserver": {
            "port": "4444",
            "browserSessionReuse": false,
            "debugMode": false,
            "ensureCleanSession": false,
            "interactive": false,
            "avoidProxy": false,
            "timeoutInSeconds": 30,
            "runInternally": true,
            "trustAllSSLCertificates": true,
            "useMultiWindows": false,
            "userExtension": "",
            "profile": ""
        },
        "uiobject": {
            "builder": { }
        },
        "eventhandler": {
            "checkElement": false,
            "extraEvent": false
        },
        "i18n": {
            "locale": "en_US"
        },
        "connector": {
            "baseUrl": "http://localhost:8080",
            "port": "4444",
            "browser": "*chrome",
            "customClass": "",
            "serverHost": "localhost",
            "options": ""
        },
        "bundle": {
            "maxMacroCmd": 5,
            "useMacroCommand": true
        },
        "datadriven": {
            "dataprovider": {
                "reader": "PipeFileReader"
            }
        },
        "widget": {
            "module": {
                "included": ""
            }
        }
    }
}
```

Then in the test case, we can load the configuration.

```
public class JettyLogonJUnitTestCase extends TelluriumMockJUnitTestCase {
   private static JettyLogonModule jlm;
   static{
       Environment env = Environment.getEnvironment();
       env.useConfigString(JettyLogonModule.JSON_CONF);
   }

   ...
```

## How to register a custom method in Tellurium API ##

For a custom method, the new Tellurium Engine provides the following method for users to register the custom method.

```
    Tellurium.prototype.registerApi = function(apiName, requireElement, returnType)
```

where _apiName_ is the method name, _requireElement_ means it requires a locator. The _returnType_ is defined by the enum in core

```
public enum ReturnType {
  VOID,
  NUMBER,
  STRING,
  BOOLEAN,
  ARRAY,
  MAP,
  OBJECT
}
```

For example, you can register a custom method as follows,

```
teJQuery(document).ready(function() {
    tellurium.registerApi("getUiByTag", false, "ARRAY");
});
```

## Why I always get List size Zero ##

If you use Selenium APIs, there are some constraints on the List object:
  1. If the separator attribute is defined, the separator tags must be the immediate children of the List object.
  1. If the separator attribute is not defined, the List elements must be the immediate children of the List object.

If you use Tellurium new Engine by calling

```
   useTelluriumEngine(true)
```

there is no such a limitation.

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

## How to Generate IDE project files ##

Tellurium provides [Maven archetypes](http://code.google.com/p/aost/wiki/UserGuide070TelluriumSubprojects#Tellurium_Maven_Archetypes) for you to generate a Tellurium Maven project. Once you create the Maven project, you can create the IDE project files automatically.

For IntelliJ IDEA, you don't need to do anything and just simply open the project by picking the pom file.

For NetBeans, you can use [the following Maven command](http://maven.apache.org/guides/mini/guide-ide-netbeans/guide-ide-netbeans.html) to create NetBeans project files.

```
mvn netbeans-freeform:generate-netbeans-project
```

For Eclipse, you can use [a similar Maven command](http://maven.apache.org/guides/mini/guide-ide-eclipse.html)

```
mvn eclipse:eclipse
```

## How to Run Tellurium Tests in Google Chrome ##

Selenium supports the following web browsers,

```
  *firefox
  *mock
  *firefoxproxy
  *pifirefox
  *chrome
  *iexploreproxy
  *iexplore
  *firefox3
  *safariproxy
  *googlechrome
  *konqueror
  *firefox2
  *safari
  *piiexplore
  *firefoxchrome
  *opera
  *iehta
  *custom
```

For Google Chrome, you should use `*googlechrome`. But there are bugs for the current Google Chrome support in Selenium:
  * There have been reports that Chrome support for selenium won't work on Windows XP 64-bit.
  * The Google Chrome launcher does not support the avoidProxy option ([SRC-524](http://jira.openqa.org/browse/SRC-524))
  * `http://localhost` doesn't work as a starting URL in Google Chrome ([SRC-529](http://jira.openqa.org/browse/SRC-529))

## How to run headless tests with Xvfb ##

First, you need to install Xvfb

In linux, do

```
[root@Mars ~]# yum search Xvfb
```

you will see which rpm is for your linux os. For example, I found

xorg-x11-server-Xvfb.x86\_64 : A X Windows System virtual framebuffer X
server.

for my box, then install it.

```
[root@Mars ~]# yum install xorg-x11-server-Xvfb.x86_64
```

If you use Maven to run test, you can use the following script

```
#!/bin/bash

nohup startx -- `which Xvfb` :20 -screen 0 1024x768x24 & sleep 7
DISPLAY=:20 firefox  &     DISPLAY=:20 mvn test
pkill Xvfb
```

For ant, you can replace "mvn test" with your ant task.

## How to Make Tellurium work with Firefox and Snow Leopard ##

Please refer to Scott's blog on [Make Tellurium / Selenium work with Firefox and Snow Leopard](http://scott.phillips.name/2010/09/make-tellurium-selenium-work-with-firefox-and-snow-leopard/)

## How to Search Tellurium Documents ##

The trick is to use google's site meta command, which will narrow the search to one domain. You can use the following key words to do Google search,

```
site:aost.googlecode.com YOUR_KEY_WORDS
```

For example,

```
site:aost.googlecode.com datepicker
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

  * Use jQuery to re-implement Selenium APIs. 0.7.0 has implemented a set of Selenium APIs, but there are still a lot of remaining work.
  * Algorithm design, for example, automatically build UI module from HTML source. Reverse engineering to build UI templates from HTML source is very challenging.
  * Tellurium Widgets, create reusable Dojo, ExtJS widgets so that other people can reuse the widgets simply by including the jar files to their projects.
  * Tellurium UID Description Language improvement.
  * Improve Trump Firefox Plugin, for example, generate test script as well as UI modules so that Tellurium is not only good for developers, but also QA people.
  * Add Behavior Driven Testing support
  * Add testing flow support. Many unit testing and functional testing frameworks do not really have the testing flow/stage concept.
  * Tellurium as a cloud testing tool.
  * Tellurium as a web security testing tool.
  * IDE and other plugins.
  * Improve code quality.