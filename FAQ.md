

## FAQs for Tellurium ##

### Q1: Why do you create the Tellurium framework ###
More than one year ago, two colleagues in my company went to a conference and came back talking about the Selenium test framework, which seems very attractive at the first sight. But soon, we realized that it is very difficult to maintain and refactor, which is often the case in an agile environment. I worked on the selenium tests for a project and came out with the idea of using abstract object modules to add another tier to the selenium framework. The framework was written in Java and later on, couple colleagues from other teams tried to use my framework, but it was quite difficult to use because it lacks of expressiveness. Recently, I re-write my framework using Groovy to make it much easier to use and add a lot of functionalities which are difficult to implement without the dynamic nature of Groovy. I make it open source so that more people can use it and I can improve it with your suggestions and comments.

### Q2: Who Tellurium is developed for ###
Tellurium is developed for anyone who needs to write selenium tests, including developers, QA people, or any one who knows about XPATH or HTML markup. Tellurium includes a DSL executor. You can write your test in pure DSL, but by writing Java test cases you have the advantage to modularize you code.

### Q3: How do I use the Tellurium framework ###
The Tellurium framework is mainly written in Groovy and the code can be compiled as a jar file. You can include the Jar file in your lib directory and write your JUnit tests by extending TelluriumJavaTestCase if you want all your test code in one Java class or by extends BaseTelluriumJavaTestCase if your test codes are in multiple test files and you want to put them in a test suite.

The second way is to write Tellurium tests in groovy, users can use TelluriumGroovyTestCase for a single test and TelluriumSuiteGroovyTestCase for a test suite.

The other way is to write DSL script directly, that is to say, your tests will be pure DSL scripts. This is really good for non-developers or QA people. The DslScriptExecutor? can be used to run the .dsl files.

Tellurium comes with an embedded Selenium server and you do not need to set up an external selenium server. You can still use external selenium server by changing the settings in the configuration file, TelluriumConfig.groovy.

### Q4:  How to run the Tellurium tests ###
If your tests are written in Java, you just run them like JUnit test cases or test suites. If your test code is written in pure dsl, please use "DslScriptExecutor dsl\_file" to run it.

### Q5:  How do I get the XPath of a UI object ###
There are a lot of ways, such as Selenium IDE. For firefox, the best way is to use the XPather plugin, which supports showing xpath by clicking on WEB. You may need to refactor the xpath a bit to make it simpler and more robust. Another good plugin for Firefox is web developer, which allows you to look at the DOM at run time.

Tellurium is tested under Firefox and it should support other browsers since it is built on top of the selenium framework.

### Q6: Do I need to write a class for every page? ###
Not really, as long as the locators you specified are correct at run time, you can define UI objects from different pages in one class, which is fine for pure DSL. But for Java test cases, I would suggest you write multiple UI modules for a page as a class to make it easier to maintain and refactor.

### Q7: Does Tellurium only focus on unit testing? ###
Selenium test itself is kind of functional test or integration test. Although Tellurium uses JUnit test, it is still functional test or integration test. You can put a lot of test cases into a test suite and the test suite can be your UAT test. Furthermore, you can also create data-driven tests using TelluriumDataDrivenTest.

### Q8: Currently I am creating test cases using selenium-IDE, If I change my work to Tellurium, can I get my work simpler and much easier? ###
Sure, Tellurium has a lot of pre-defined UI Objects, which will handle all the actions and data automatically for you. For example, you can define a template for a table element, the framework will automatically help you to locate the table[i](i.md)[j](j.md) element and support the different actions for them.

### Q9: What is the object to locator mapping (OLM) framework you talked about ###
The object to locator mapping (OLM) framework is available from version 0.3.0. It includes couple parts. The first one is to map a UI id in the format of "search1.inputbox1" to the actual object you defined in your object module. The second part will be automatically mapping a certain parameters you provided for a UI to its actual locator. For example, you can say the UI object has the tag "input" and other attributes. Tellurium will try to create the XPath for you to make your life easier. The third will be to utilize the group object concept to locate the UI module in the DOM at run time.

### Q10: What IDE should I use for Tellurium ###
Since Tellurium requires Groovy support, you can use Eclipse, Netbeans, or IntelliJ with groovy plugin if you want to work on Tellurium source code. IntelliJ is recommended because its Groovy plugin is excellent. If you only need to use Tellurium as a jar file, you can use any IDE that supports JAVA.

### Q11: Why I cannot start tellurium test ###
Make sure your web browser in your environment path. If you cannot really make it work, check the settings for the embedded server section in the configuration file TelluriumConfig.groovy. Or change the file SeleniumConnector.groovy, although this is not really recommended. Change the following line:
```
sel = new DefaultSelenium("localhost", port, "*chrome", baseURL);
```

### Q12: How do I add my own UI object to Tellurium ###
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

### Q13: How to build Tellurium from source ###
Tellurium provides the ant build script. You may need to change some of the settings in the build.properties file so that it matches your environment. For example, the settings for javahome and javac.compiler. Then in the project root directory, run command:

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

### Q14: How to use composite locator ###
To use composite locator, you need to use "clocator" and its value is a map, i.e., [key1: value1, key2: value2, ...] format in Groovy. The defined keys include "header", "tag", "text", "trailer", and "position". They are all optional. If a UI object has fixed tag and it is defined in object class, you do not need to include "tag".

You may have additional attributes, define them in the same way as the predefined keys. For example `[value: "Tellurium home"]`

### Q15: How to use group locating ###
First make sure the UI object is a Container or other classe that extends Container. You can specify `group: "true"`, which will turn on group locating. That is the only thing you need to do. The group locating will be done by Tellurium automatically at run time. Right now, the UI object can only use the information provided in its child's composite locator. Other children with base locators will be ignored.

I suggest you only use the group locating option at the top level object. Once the top level object is found, it is not a big deal to find its descendants.

### Q16: Why do you use a programming language to define GUI mapping, not simply use a plain and flat properties file ###
I used that way to write selenium tests for a whole project one year ago and I know how painful that is. There are drawbacks to use properties file because you have to specifiy the runtime locators, which is painful for developers and may not be robust unless you are xpath expert. The Tellurium framework can utilize relationship between UI objects to automatically generate the runtime xpath for you. Also It exploits the Group Locating concept to co-locate a group of UI objects. In the future, more advanced features and tools will be developed so that developers do not need to manually find the locators for UI elements. They can simply map their JSP or other mark-up files to the Tellurium UI definition or use tools to automatically do that.

### Q17: Is your basic DSL really a DSL ? ###
The DSL is in the domain of "Selenium Test" and it is a DSL.

### Q18: DO I need to know Groovy before I write Tellurium test cases ###
Not really. If you write your test as pure DSL, you only need to know the DSL syntax. If you need to write your test in Java, you only need to define your UI modules in Groovy file by extending the DslContext class. Since Groovy accept all Java syntax, you can in fact write Java code in your Groovy file that defines the UI modules. For the actual TestCase or TestSuite, they are already Java code. As long as you know Java, you know how to write Tellurium tests.

### Q19: How to contribute to Tellurium ? ###

Tellurium is like a newborn and he needs your support and contribution. We welcome contributions in many different ways, for example,

  1. Try out Tellurium
  1. Use Tellurium in your project and report bugs
  1. Ask questions and answer other users' questions
  1. Promote Tellurium and post your experience on Tellurium
  1. Fix bugs for Tellurium
  1. Bring in new ideas and suggestions

For very active users in Tellurium user group, we will consider you as Tellurium contributors. We will provide you the open source license for the IntelliJ IDEA. A Tellurium contributor can be nominated by one of Tellurium team members as a new Tellurium team member. Tellurium team members will vote if we accept the contributor as a member. If most existing members agree, then the contributor will become a project member and contribute more to Tellurium.

Our project team is open for new members and is expecting for new members. We will recruit new members from Tellurium contributors. But if you think you are exceptional and want to contribute to Tellurium code immediately, please contact Jian Fang (John.Jian.Fang@gmail.com) or other existing Tellurium members.

### Q20: Why you change the project name from AOST to Tellurium ? ###
AOST is used as the project prototype name and Tellurium is the official project name. Tellurium means a lot of for us. First, it means the project is full functional and no long a prototype. Second, the project becomes a team project. Third, the project is targeting Automated testing, not just Selenium test.

### Q21: What is data driven test? ###
Data driven test means the testing flow is specified by the input data file and the test framework works as a driving engine to read the input data, do data bind, choose the test specified in the input file, run the test, and compare the expected results with the actual results. This is a total new way of writing tests.

### Q22: How Tellurium does data driven testing? ###
Tellurium provides an expressive way for you to describe the input file formats starting with "fs.FieldSet", define test using "defineTest". Bind your variables to the field you defined in a field set, i.e., the format of a line of data, using "bind", and use "compareResult" to compare the actual result with the expected one. For input file, Tellurium supports pipe format file. You can use "loadData" to read the input data from a file. You can also use "useData" to use a String defined in your test script as the input data.

### Q23: What are the usages of TelluriumDataDrivenModule and TelluriumDataDrivenTest ###
TelluriumDataDrivenModule is inherited from DslContext and you should define Ui modules, FieldSets, tests there, but not the testing flow. TelluriumDataDrivenTest is the actual testing class and it can read multiple test modules using the "includeModule" command and then use "step", "stepOver", and "stepToEnd" to control the testing flow. Please see the introduction wiki page for more details.

### Q24: How do I configure Tellurium framework? ###
Tellurium provide a configure file "TelluriumConfig.groovy" and you should put this file in the path you run the tests. In this file, you can change the settings for Tellurium, for example, what port the embedded Selenium server will use, what browser you want to run Tellurium tests, and what is the output format of your data driven tests.

### Q25: Firefox version 3.0.1 does not work properly and hang during running a testcase ###
This is a known Selenium issue. A work-around is to install the 2.0.16 firefox version or change browser type to use iehta. You can make this change in TelluriumConfig.groovy file located at the Tellirium root project folder.

### Q26: what is the problem with Selenium RC XPATH expressions and why there is need to create UI Module? ###
That is a great question, The problem is not in XPath itself, but the way you use it.

1) `"//input[@name='btnG' and @type='submit"` is a very clear XPath expression. Let us look at more general case. Usually, Selenium IDE or XPather created the XPath like

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

directly, then they do not depend on the portion `"div[2]/table[@id='something']/div[3]"`, certainly. your UI elements can address any changes in the portion of `"div[2]/table[@id='something']/div[3]"`. Note, in Tellurium, you most likely will not use the format of `"div[3]/div[1]/div[6]"`.

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

4) Selenium is a cool and the idea is brilliant. But seems to me, it is really for low level testing and it only focuses on one element at a time and does not have the whole UI module in mind. That is why we need another tier on top of it so that you can have UI module oriented testing script and not the locator oritented one. Tellurium is one of the frameworks designed for this purpose.

5) As mentioned in 4), Selenium is a quite low level thing and it is really difficult for you to handle more complicated UI components like a data grid. Tellurium can handle them pretty easily, please see our test scripts for our Tellurium project web site.

### Q27: How does Tellurium compare to RSpec style DSL tests for Selenium-RC? ###
Tellurium is still focus on UI modules at this point and all the DSLs are UI module related. Seems to me, RSpec is a bit more abstract than the current Tellurium UI module since it specifies what is the correct behavior for web UI. We do have RSpec in mind and thought of it. Hope we can add similar abstract level testing support in Tellurium later on.

### Q28: Does Tellurium support TestNG? ###
TestNG is supported in Tellurium. Please check the code on SVN trunk. The test case BaseTelluriumJavaTestCase can be extended for both JUnit 4 and TestNG. TelluriumJavaTestCase is for JUnit 4 and TelluriumTestNGTestCase is for TestNG. For TestNG, you can use the following annotations:
```
@BeforeSuite
@AfterSuite
@BeforeTest
@AfterTest
@BeforeGroups
@AfterGroups
@BeforeClass
@AfterClass
@BeforeMethod
@AfterMethod
@DataProvider
@Parameters
@Test
```
for details, please see TestNG document:

http://testng.org/doc/documentation-main.html

### Q29: How to set up IntelliJ project for Tellurium? ###
Tellurium includes IntelliJ project files in its code base. To make Tellurium work properly in IntelliJ, please first check if you have installed IntelliJ Groovy plugin, JetGroovy. To check if JetGroovy is installed, open IDE settings and click on plugins, you will see all installed plugins. If JetGroovy is not on the list, please click on the "available" tab and install it. To check your Groovy setting, please open the "project settings", under the "platform settings", you will see the "Global libraries" item, click on that, you should be able to see GROOVY. If you do not see it, something is wrong and you need to configure Groovy for IntelliJ. Once you get this right, you should be all set. IntelliJ has excellent Groovy support and you can debug groovy code just like Java code.

### Q30: How to write assertions in Tellurium DSL scripts ###

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

This brings up one interesting question "why should I put assertions inside compareResult, not anywhere in the script?"

The answer is that you can put assertions any where in the DDT script, but that will cause different behaviour if the assertion fails.

If you put assertions in compareResult and the assertion fails, the AssertionFailedError will be captured and that comparison fails, but the rest script inside a test will continue executing. But if you put assertions outside of compareResult, the AssertionFailedError will lead to the failure of the current test. The exception will be
recorded and the current test will be stopped. The next test will take over and execute.

### Q31: Tellurium supports which version of Selenium RC ###
Tellurium is built on top of Selenium and it is using Selenium RC 0.9.2, the latest stable version. Selenium RC 1.0 is still in Beta and once it becomes stable, Tellurium will start to support that version.

### Q32: How do I use Tellurium for UI that does not have a direct match for its tag in Tellurium UI objects ###
Be aware that Container, TextBox, List, and some other UI objects in Tellurium do not have default tags. The reason is that they are abstract UI objects and can represent multiple actual UIs. You can use any tag for them. Also, you can overwrite the default tag of a UI object by specify the "tag" attribute in the composite locator, but of course, such replacement should make sense. If all UI objects do not satisfy your need, you can even define your own UI object.

### Q33: How to run Selenium server remotely in Tellurium ###
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
       //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
       runInternally = false
   }
   //the configuration for the connector that connects the selenium client to the selenium server
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

After that, you can run the test just like using the embedded selenium server. But be aware that there are some performance degradation, i.e., the test is slower, with remote selenium server.

### Q34: Differences among Tellurium Table, List, and Container ###
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

### Q35: Why the "OK" button does not show on the TrUMP preference panel in Linux ###

This is for Linux, the configure "browser.preferences.instantApply" is set to true by default. You can point your firefox to "about:config" and change the option to false.
After that, you will see the "OK" button.

### Q36: How to play the Demo Videos in ogg format ###

Most media players should support ogg format, if you cannot find a media player for it, you can download the free media player VLC at

http://www.videolan.org/vlc/

### Q37: How do I use a Firefox profile in Tellurium ###

You can specify the profile in Tellurium Configuration file TelluriumConfig.groovy as follows,

```
 embeddedserver {
       //port number
       port = "4444"
       //whether to use multiple windows
       useMultiWindows = false
       //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
       runInternally = true
       //profile location
       profile = ""
   }
```

### Q38: How to overwrite Tellurium settings in my test class ###

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

### Q39: How to reuse a frequently used set of elements ###

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

### Q40: How to handle Table with multiple tbody elements ###

The StandardTable is designed for this purpose and it has the following format

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
   SubmitButton(uid: "tbody: 1, row:1, column:4", clocator: [value: "Search", name: "btn"])
   InputBox(uid: "tbody: 1, row:2, column:3", clocator: [name: "words"])
   InputBox(uid: "tbody: 2, row:2, column:3", clocator: [name: "without"])
   InputBox(uid: "tbody: 2, row:*, column:1", clocator: [name: "labels"])

   TextBox(uid: "foot: all", clocator: [tag: "td"])
}
```

### Q41: How to use the new XPath library in Selenium ###

There are three methods in DslContext for you to select different XPath Library,

```
    public void useDefaultXPathLibrary()

    public void useJavascriptXPathLibrary()

    public void useAjaxsltXPathLibrary()
```

The default one is the same as the "Ajaxslt" one. To use faster xpathlibrary, please call useJavascriptXPathLibrary().

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

### Q42: How to use jQuery Selector with weird characters in its ID ###

You should escape the "." or other jQuery reserved characters.

For example, use "dateOfBirth.\\month" for "dateOfBirth.month" as the ID.

### Q43: How to run headless tests with Xvfb ###

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