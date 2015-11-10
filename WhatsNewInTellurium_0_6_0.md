

# Introduction #

Tellurium 0.6.0 is a feature rich release and it includes the following modules
  * Telluirum core 0.6.0
  * Tellurium Maven JUnit Archetype 0.6.0 and Tellurium Maven TestNG Archetype 0.6.0
  * Tellurium JUnit Reference Project 0.6.0 and Tellurium TestNG Reference Project 0.6.0
  * Customized Selenium server 1.0.1-te with Tellurium Engine jQuery selector module

The following sections, cover the main features in each module.

# New Features #

## Tellurium Core 0.6.0 ##

The biggest move from Tellurium core 0.5.0 to 0.6.0 is the addition of jQuery selector support together with jQuery Selector Cache option, Selenium Grid support, New APIs for bulk data retrival and css styles, and numerous other enhancements.

### jQuery Selector ###

The motivation behind the jQuery Selector is to improve the test speed in IE since IE lacks of native XPath support and the XPath is very slow. Tellurium exploits jQuery selector capability to improve the test speed dramatically. As a result, Tellurium Core 0.6.0 supports both XPath and jQuery selector and still uses XPath as the default locator. To use jQuery selector, simply call

```
useJQuerySelector()
```

in you code. To switch back to XPath locator, you should call

```
disableJQuerySelector()
```

Be aware that jQuery selector only works for **composite locator**, i.e., _clocator_. You cannot have **base locator**, i.e., XPath, in your UI module.

jQuery Selector provides the following additional Selenium methods, which are utilized in DslContext to form a set of new DSL methods:

  1. _String getAllText(String locator)_: Get all text from the set of elements corresponding to the jQuery Selector. This is a fast bulk data retrieval API to get back all data in one method call.
  1. _String getCSS(String locator, String cssName)_: Get the CSS properties for the set of elements corresponding to the jQuery Selector.
  1. _Number getJQuerySelectorCount(String locator)_: Get the number of elements matching the corresponding jQuery Selector

jQuery supports the following attribute selectors
  * _[attribute](attribute.md)_: have the specified attribute
  * _[attribute=value]_: have the specified attribute with a certain value
  * _[attribute!=value]_: either don't have the specified attribute or do have the specified attribute but not with a certain value.
  * _[attribute^=value]_: have the specified attribute and it starts with a certain value.
  * _[attribute$=value]_: have the specified attribute and it ends with a certain value.
  * **[attribute_=value]_: have the specified attribute and it contains a certain value.**

Apart from the above, jQuery selector also provides a jQuery selector version of many existing DSL methods, for instance,

```
int getTableHeaderColumnNumBySelector(String uid)
```

But you should use locator agnostic API, such as

```
int getTableHeaderColumnNum(String uid) 
```

Under the hood, Tellurium core will call the appropriate API based on what locator you are currently using. For more details, please see [Tellurium jQuery Selector Guide](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector).

### jQuery Selector Cache Option ###

jQuery cache is a mechanism to further improve the speed by reusing the found DOM reference for a given jQuery selector. Our benchmark results show that the jQuery cache could improve the speed by up to 14% over the regular jQuery selector and over 27% for some extreme cases. But the improvement of jQuery cache over regular jQuery selector has upper bound, which is the portion of jQuery locating time out of the round trip time from Tellurium core to Selenium core. jQuery cache is still considered to be experimental at the current stage. Use it at your own discretion.

To make the cache option configurable in tellurium UI module, Tellurium introduces an attribute _cacheable_ to UI object, which is set to be true by default. For a Container type object, it has an extra attribute _noCacheForChildren_ to control whether to cache its children.

One example UI module is defined as follows,

```
  ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "true", clocator: [id: "resultstable", class: "results"], group: "true") {
 
       ......

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }
```

Where the _cacheable_ can overwrite the default value in the UI object and _noCacheForChildren_ in the above example will force Tellurium to not cache its children.

Tellurium Core provides a set of methods for jQuery Selector cache control, for example,

  * _public void enableSelectorCache()_: Enable jQuery selector cache

  * _public boolean disableSelectorCache()_: Disable jQuery selector cache

  * _public void setCacheMaxSize(int size)_: Set the cache maximum size

  * _public void useDiscardOldCachePolicy()_: Use DiscardOldCachePolicy

For a complete list of cache control APIs, please check  [The Cache Mechanism of jQuery Selector](http://code.google.com/p/aost/wiki/jQuerySelectorCache)

### Selenium Grid Support ###

Selenium Grid transparently distributes your tests on multiple machines so that you can run your tests in parallel. We recently added support for Selenium Grid to the Tellurium. Now you can run the Tellurium tests against different browsers using Selenium Grid. Tellurium core is updated to support Selenium Grid sessions. You should read the following the details guide [Using Selenium Grid with Tellurium](http://code.google.com/p/aost/wiki/SeleniumGridAndTellurium).

Make sure you do not run Selenium server internally and you should configure this in the  TelluriumConfig.groovy file as follows,

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
        //profile location
        profile = ""
        //user-extension.js file
        userExtension = ""
    }
```

### "Include" Frequently Used Sets of elements in UI Modules ###

Sometimes, you have a frequently used set of elements, which you do not want to redefined them over and over again in your UI module. Now, you can use the "Include" syntax to re-use pre-defined UI elements,

```
Include(uid: UID, ref: REFERRED_UID)
```

You should use "ref" to reference the object you want to include, you can still specify the uid for the object (if you do not need a different UID, you do not need to specify it), if the object uid is not the same as the original one, Tellurium will clone a new object for you so that you can have multiple objects with different UIDs.

For example, you first define the following reused UI module

```
   ui.Container(uid: "SearchModule", clocator: [tag: "td"], group: "true") {
     InputBox(uid: "Input", clocator: [title: "Google Search"])
     SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
     SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
   }
```

Then, you can include it into your new UI module as follows,

```
   ui.Container(uid: "Google", clocator: [tag: "table"]) {
     Include(ref: "SearchModule")
     Include(uid: "MySearchModule", ref: "SearchModule")
     Container(uid: "Options", clocator: [tag: "td", position: "3"], group: "true") {
       UrlLink(uid: "LanguageTools", clocator: [tag: "a", text: "Language Tools"])
       UrlLink(uid: "SearchPreferences", clocator: [tag: "a", text: "Search Preferences"])
       UrlLink(uid: "AdvancedSearch", clocator: [tag: "a", text: "Advanced Search"])
     }
   }
```

### Customize Individual Test settings Using setCustomConfig ###

TelluriumConfig.groovy provides project level test settings, you can use setCustomConfig to custmoize individual testing settings.

```
public void setCustomConfig(boolean runInternally, int port, String browser, boolean useMultiWindows, String profileLocation)
```

For example, you can specify your custom settings as follows,

```
static{
   setCustomConfig(true, 5555, "*chrome", true, null);
}
```

### User Custom Selenium Extensions ###

To support user custom selenium extensions, Tellurium Core added the following configurations to TelluriumConfig.groovy

```
    embeddedserver {

        ......

        //user-extension.js file, for example "target/test-classes/extension/user-extensions.js"
        userExtension = ""
    }
    connector{

        ......

        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = ""
    }

```

Where the _userExtension_ points to user's user-extensions.js file, for example, you have the following user-extensions.js

```
Selenium.prototype.doTypeRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to type
    var valueToType = text + text;

    // Replace the element text with the new text
    this.page().replaceText(element, valueToType);
};
```

The _customClass_ is user's class for custom selenium methods, which should extend Tellurium org.tellurium.connector.CustomCommand class:

```
public class MyCommand extends CustomCommand{

    public void typeRepeated(String locator, String text){
                String[] arr = [locator, text];
                commandProcessor.doCommand("typeRepeated", arr);
        }
   } 
}
```

Tellurium core will automatically load up your user-extensions.js and custom commands. The n, user can use

```
customUiCall(String uid, String method, Object[] args)
```

to call the custom methods, for instance,

```
customUiCall "Google.Input", typeRepeated, "Tellurium Groovy" 
```

The _customUiCall_ method handles all the UI to locator mapping for users. Tellurium also provides the following method for users to make direct calls to Selenium server.

```
customDirectCall(String method, Object[] args)
```

### Standard Table ###

StandardTable is a table with one thead, one tfoot, and multiple tbodies. For example, you can define a StandardTable as follows,

```
    ui.StandardTable(uid: "table1", clocator: [id: "std"]) {
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

Tellurium provides the following additional methods for StandardTable

  * int getTableFootColumnNum()
  * int getTableMaxTbodyNum()
  * int getTableMaxRowNumForTbody(int tbody\_index)
  * int getTableMaxColumnNumForTbody(int body\_index)

### The Dump Method ###

Tellurium Core added a dump method to print out the UI object's and its descendants' runtime locators that Tellurium Core generates for you.

```
public void dump(String uid)
```

where the _uid_ is the UI object id you want to dump. The cool feature of the dump() method is that it does not require you to run the actual tests, that is to say, you do not need to run selenium server and do not need to launch the web browser. Just simply define the UI module and then call the dump() method.

For example, we can define the UI module for Tellurium Issue Search module as follows,

```
public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true"){
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }
  }
}
```

Then you can use the dump method in the following way,

```
  TelluriumIssueModule tisp = new TelluriumIssueModule();
  tisp.defineUi();
  tisp.dump("issueSearch");
```

The above code will print out the runtime XPath locators

```
Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: //descendant-or-self::form[@action="list" and @method="get"]
issueSearch.issueType: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::select[@name="can" and @id="can"]
issueSearch.searchLabel: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::span[contains(text(),"for")]
issueSearch.searchBox: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@type="text" and @name="q"]
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------
```

### Other Enhancements ###

Other updates and enhancements include

  * Upgraded to Selenium 1.0.1 and Groovy 1.6
  * Support Javascript XPath library by calling useJavascriptXPathLibrary()
  * Added _registerNamespace_ and _getNamespace_ for XHTML
  * Added a simple logger to Tellurium Core
  * Removed isElementPresent before every command
  * Added profile location in Tellurium configuration file
  * Implemented a keyType method using jQuery to process special key sequences such as ".", "(", and "y", which could not be handled correctly in Selenium.
  * Many bug fixes and other small enhancements

## Tellurium Maven Archetypes ##

Tellurium includes two maven archetypes, i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively.

To create a JUnit Tellurium test project, run

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

For a TestNG Tellurium test project, use the following command instead

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

Please check Tellurium the following documents for more details about Maven archetypes.

  * [Tellurium Maven Archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes)
  * [Tellurium Maven Guide](http://code.google.com/p/aost/wiki/MavenHowTo)
  * [Ten Minutes To Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)

## Tellurium Reference Projects ##

Tellurium reference projects include two sub-projects,

  * tellurium-junit-java
  * tellurium-testng-java

We use Tellurium project web site as an example to illustrate how to write real-world Tellurium tests.

Basically, the two sub-projects are the same and the only difference is that tellurium-junit-java uses JUnit 4 and tellurium-testng-java uses TestNG.

The two reference projects are updated to use Tellurium Core 0.6.0 and please see more details on [the Reference Project Guide](http://code.google.com/p/aost/wiki/ReferenceProjectGuide).

## Customized Selenium Server ##

We customized selenium-core 1.0.1 and embedded our Engine code into it. The Engine mainly provides jQuery selector support, jQuery selector caching, and a set of new APIs.

# How to Obtain Tellurium 0.6.0 #

The Release candidate will be out soon, you can download the artifacts from Tellurium download page directly,

http://code.google.com/p/aost/downloads/list

However, we recommend you to use Tellurium Maven archetype to create your own project.

The Maven archetype project has everything you need for a new Tellurium project including a Maven POM file, a Tellurium Configuration file, and a sample test file.

Alternatively, you could also create your own project manually by using [the sample Pom file](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom) and [the sample configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile).

# Resources #

  * [Tellurium Project Web Site](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium Introduction](http://code.google.com/p/aost/wiki/Introduction)
  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide)
  * [Tellurium Tutorials](http://code.google.com/p/aost/wiki/Tutorial)
  * [Tellurium Frequently Asked Questions](http://code.google.com/p/aost/wiki/FAQ)
  * [Tellurium Maven Guide](http://code.google.com/p/aost/wiki/MavenHowTo)
  * [Tellurium jQuery Selector Guide](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector)
  * [Tellurium UI Model Firefox Plugin (TrUMP) 0.1.0](http://code.google.com/p/aost/wiki/TrUMP)
  * [Ten Minutes To Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)