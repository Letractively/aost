(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



# Introduction #

## Motivations ##

Automated web testing has always been one of the hottest and most important topics in the software testing arena when it comes to the rising popularity of Rich Internet applications (RIA) and Ajax-based web applications. With the advent of new web techniques such as RIA and Ajax, automated web testing tools must keep up with these changes in technology and be able to address the following challenges:

  * **JavaScript Events**: JavaScript is everywhere on the web today. Many web applications are JavaScript heavy. To test JavaScript, the automated testing framework should be able to trigger JavaScript events in a very convenient way.
  * **Ajax for Dynamic Web Content**: Web applications have many benefits over desktop applications; for example they have no installation and application updates are instantaneous and easier to support. Ajax is a convenient way to update a part of the web page without refreshing the whole page. AJAX makes web applications richer and more user-friendly. The web context for an Ajax application is usually dynamic. For example, in a data grid, the data and number of rows keep changing at runtime.
  * **Robust/Responsive to Changes**: A good automated web testing tool should be able to address the changes in the web context to some degree so that users do not need to keep updating the test code.
  * **Easy to Maintain**: In an agile world, software development is based on iterations, and new features are added on in each sprint. The functional tests or user acceptance tests must be refactored and updated for the new features. The testing framework should provide the flexibility for users to maintain the test code very easily.
  * **Re-usability**: Many web applications use the same UI module for different parts of the application. The adoption of JavaScript frameworks such as Dojo and ExtJS increases the chance of using the same UI module for different web applications. A good testing framework should also be able to provide the re-usability of test modules.
  * **Expressiveness**: The testing framework should be able to provide users without much coding experience the ability to easily write test code or scripts in a familiar way, like by using a domain specific language (DSL).

The Tellurium Automated Testing Framework (Tellurium) is designed with the following motivations:

  * Robust/responsive to changes; allow changes to be localized
  * Address dynamic web contexts such as JavaScript events and Ajax
  * Easy to refactor and maintain
  * Modular; test modules are reusable
  * Expressive and easy to use

## Why Tellurium is a New Approach for Web Testing ##

The Tellurium Automated Testing Framework (Tellurium) is an open source automated testing framework for web applications that addresses the challenges and problems in web testing.

Most existing web testing tools/frameworks focus on individual UI elements such as links and buttons. Tellurium takes a new approach for automated web testing using the concept of the _UI module_.  The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the Google search UI module can be expressed as follows:

```
ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
}
```

Tellurium is built on the foundation of the UI module.  The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uid) along the path to the specific element. This also enables Tellurium's _Group Locating_ feature, making composite objects reusable, and addressing dynamic web pages.

Tellurium is implemented in Groovy and Java.  The test cases can be written in Java, Groovy, or pure Domain Specific Language (DSL) scripts.  Tellurium evolved out of Selenium, but the UI testing approach is completely different. For example, Tellurium is not a "record and replay" style framework, and it enforces the separation of UI modules from test code, making refactoring easy. For example, once you defined the Google Search UI module shown above, you can write your test code as follows:

```
type "GoogleSearchModule.Input", "Tellurium test"
click "GoogleSearchModule.Search"
```

Tellurium does Object to Locator Mapping (OLM) automatically at run time so that UI objects can be defined simply by their attributes using _Composite Locators_. Tellurium uses the Group Locating Concept (GLC) to exploit information inside a collection of UI components so that locators can find their elements.  It also defines a set of DSLs for web testing.  Furthermore, Tellurium uses UI templates to define sets of dynamic UI elements at runtime.  As a result, Tellurium is robust, expressive, flexible, reusable, and easy to maintain.

The main features of Tellurium include:

  * Abstract UI objects to encapsulate web UI elements
  * UI module for structured test code and re-usability
  * DSL for UI definition, actions, and testing
  * Composite Locator to use a set of attributes to describe a UI element
  * Group locating to exploit information inside a collection of UI components
  * Dynamically generate runtime locators to localize changes
  * UI templates for dynamic web content
  * XPath support
  * CSS selector support to improve test speed in IE
  * Locator caching to improve speed
  * Javascript event support
  * Use Tellurium Firefox plugin, Trump, to automatically generate UI modules
  * Dojo and ExtJS widget extensions
  * Data driven test support
  * Selenium Grid support
  * JUnit and TestNG support
  * Ant and Maven support

## How Challenges and Problems are addressed in Tellurium ##

First of all, Tellurium does not use "record and replay." Instead, it uses the Tellurium Firefox plugin Trump to generate the UI module (not test code) for you. Then you need to create your test code based on the UI module. In this way, the UI and the test code are decoupled. The structured test code in Tellurium makes it much easier to refactor and maintain the code.

The composite locator uses UI element attributes to define the UI, and the actual locator (e.g. xpath or CSS selector) will be generated at runtime. Any updates to the composite locator will lead to different runtime locators, and the changes inside the UI module are localized. The Group locating is used to remove the dependency of the UI objects from external UI elements (i.e. external UI changes will not affect the current UI module for most cases) so that your test code is robust and responsive to changes up to a certain level.

Tellurium uses the _respond_ attribute in a UI object for you to specify JavaScript events, and the rest will be handled automatically by the framework itself. UI templates are a powerful feature in Tellurium used to represent many identical UI elements or a dynamic size of different UI elements at runtime, which are extremely useful in testing dynamic web contexts such as a data grid. The _Option_ UI object is designed to automatically address dynamic web contexts with multiple possible UI patterns.

Re-usability is achieved by the UI module when working within one application and by Tellurium Widgets when working across different web applications. With the Domain Specific Language (DSL) in Tellurium you can define UI modules and write test code in a very expressive way. Tellurium also provides you the flexibility to write test code in Java, Groovy, or pure DSL scripts.

## Tellurium Architecture ##

The Tellurium framework architecture is shown as follows.

http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq

The DSL parser consists of the DSL Object Parser, Object Builders, and the Object Registry.

Thanks to Groovy builder pattern, we can define UI objects expressively and in a nested fashion. The DSL object parser will parse the DSL object definition recursively and use object builders to build the objects on the fly. An object builder registry is designed to hold all predefined UI object builders in the Tellurium framework, and the DSL object parser will look at the builder registry to find the appropriate builders. Since the registry is a hash map, you can override a builder with a new one using the same UI name. Users can also add their customer builders into the builder registry.

The DSL object definition always comes first with a container type object. An object registry (a hash map) is used to store all top level UI Objects. As a result, for each DSL object definition, the top object ids must be unique in the DslContext. The object registry will be used by the framework to search objects by their ids and fetch objects for different actions.

The Object Locator Mapping (OLM) is the core of the Tellurium framework and it includes UI ID mapping, XPath builder, jQuery selector builder, and Group Locating.

The UI ID supports nested objects. For example, "menu.wiki" stands for a URL Link "wiki" inside a container called "menu". The UI ID also supports one-dimensional and two-dimensional indices for table and list. For example, `"main.table[2][3]"` stands for the UI object of the 2nd row and the 3rd column of a table inside the container "main".

XPath builder can build the XPath from the composite locator, i.e., a set of attributes. Starting with version 0.6.0, Tellurium supports jQuery selectors to address the problem of poor performance of XPath in Internet Explorer. jQuery selector builders are used to automatically generate jQuery selectors instead of XPath with the following advantages:
  * Faster performance in IE.
  * Leverage the power of jQuery to retrieve bulk data from the web by testing with one method call.
  * New features provided by jQuery attribute selectors.

The Group Locating Concept (GLC) exploits the group information inside a collection of UI objects to help us find the locator of the collection of UI objects.

The Eventhandler will handle all events like "click", "type", "select", and so on. The Data Accessor is used to fetch data or UI status from the DOM. The dispatcher will delegate all calls it receives from the Eventhandler and the data accessor to the connector, which connects to the Tellurium engine. The dispatcher is designed to decouple the rest of the Tellurium framework from the base test driving engine so that we can switch to a different test driving engine by simply changing the dispatcher logic.

## HOW Tellurium Works ##

Basically, there are two parts for the Tellurium framework. The first part is to define UI objects and the second part is working on the UI objects like firing events and getting data or status from the DOM.

The `defineUI` operation can be demonstrated in the following sequence diagram:

http://tellurium-users.googlegroups.com/web/TelluriumDefineUiSequenceDiagram.png?gda=aw23nVYAAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPVa5eLn5D8aMzeq5BmQm6Iqs27ZxjBtfjzl3w_ujfDpqd64s9EZTLP9aL_4jXQez_BPhGuxsWDLdLep2NLleRSE&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

When the Test code calls `defineUI()`, the DslContext calls the Dsl Object Parser to parse the UI definition. The Parser looks at each node and call the appropriate builders to build UI objects. The top level object is stored in the UI Object registry so that we can search for the UI object by _uid_.

The processing of actions such as clicking on an UI object is illustrated in the following sequence diagram:

http://tellurium-users.googlegroups.com/web/TelluriumClickSequenceDiagram.png?gda=DDIgt1MAAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPeD1j29FUgzDhsVQNMlCcuXsGSF53sOd2QxWBQw1Q0uxMrYifh3RmGHD4v9PaZfDexVi73jmlo822J6Z5KZsXFo&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

The action processing includes following two parts.

First, the `DslContext` will create a `WorkflowContext` so that we can pass meta data such the relative locator inside it. Then, we start to look at the UI object registry by calling the `walkTo(uid)` method. Remember, the UI object registry hold all the top level UI objects. If we can find the top level UI object, we can recursively call the `walkTo(uid)` method on the next UI object until we find the UI Object matching the _uid_. During the `walkTo` method calls, we start to aggregate relative xpaths or jQuery selector into the reference locator and pass it on to the next UI object. In this way, the runtime locator is built.

If the UI Object is found, we call the action such as "click" on the UI object and the call is passed on to the EventHandler, where additional JavaScript events may be fired before and/or after the click action as shown in the above sequence diagram. The action and JavaScript events are passed all the way down from the dispatcher and connector to the Tellurium Engine, which is embedded in the Selenium server at the current stage.

# Concepts and Features #

## UI Object ##

Tellurium provides a set of predefined UI objects, which users can use directly.

The basic UI object is an abstract class. Users cannot instantiate it directly. The basic UI Object works as the base class for all UI objects and it includes the following attributes:

  1. uid: UI object's identifier
  1. namespace: can be used for XHTML
  1. locator: the locator of the UI object, could be a base locator or a composite locator
  1. group: this attribute is used for Group Locating and it only applies to a collection type of UI object such as Container, Table, List, Form.
  1. respond: the JavaScript events the UI object can respond to. The value is a list.

All UI Objects will inherit the above attributes and methods. But be aware that you usually do not call these methods directly and you should use DSL syntax instead. For example, use:

```
click "GoogleSearchModule.Search"
```

In this way, Tellurium will first map the UIID "GoogleSearchModule.Search" to the actual UI object and then call the _click_ method on it. If that Ui object does not have the _click_ method defined, you will get an error.

## Composite Locator ##

Tellurium supports two types of locators: _base locator_ and _composite locator_. The _base locator_ is a relative XPath. The _composite locator_, denoted by "clocator", specifies a set of attributes for the UI object and the actual locator will be derived automatically by Tellurium at runtime.

The Composite Locator is defined as follows:

```
class CompositeLocator {
    String header
    String tag
    String text
    String trailer
    def position
    boolean direct
    Map<String, String> attributes = [:]
}
```

To use the composite locator, you need to use "clocator" with a map as its value. For example:

```
clocator: [key1: value1, key2: value2, ...]
```

The default attributes include "header", "tag", "text", "trailer", "position", and "direct". They are all optional. The "direct" attribute specifies whether this UI object is a direct child of its parent UI, and the default value is "false".

If you have additional attributes, you can define them in the same way as the default attributes, for example:

```
clocator: [tag: "div", value: "Tellurium home"]
```

Most Tellurium objects come with default values for certain attributes, for example, the  tag attribute. If these attributes are not specified, the default attribute values will be used. In other words, if you know the default attribute values of a Tellurium UI object, you can omit them in clocator. Take the RadioButton object as an example, its default tag is "input," and its default type is "radio." You can omit them and write the clocator as follows:

```
clocator: [:]
```

which is equivalent to

```
clocator: [tag: "input", type: "radio"]
```

## UI Module ##

The UI Module is the heart of Tellurium. The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium's project site is defined as follows:

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

Tellurium is built on the foundation of the UI module. The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uids) along the path to the specific element. This also enables Tellurium's "Group Locating" feature, making composite objects reusable and addressing dynamic web pages.

This frees up the testers to write better tests rather than spend precious testing time on identifying and resolving test failures due to XPath changes.

## UiID ##

In Tellurium, the UI object is referred by its UiID. For nested Ui objects, the UiID of the UI Object is a concatenated of the UI objects' uids along its path to the UI Object. For example, in the following nested UI Module,

```
ui.Container(uid: "parent_ui"){
   InputBox(uid: "inputbox1", locator: "...")
   Button(uid: "button1", locator: "...")
   Container(uid: "child_ui){
     Selector(uid: "selector1", locator: "...")
     ...
     Container(uid: "grand_child"){
       TextBox(uid: "textbox1", locator: "...")
       ...
     }
   }
}
```

the TextBox is referred as "parent\_ui.child\_ui.grand\_child.textbox1". The exceptions are tables and lists, which use `[x][y]` or `[x]` to reference its elements inside. For example, `labels_table[2][1]` and `GoogleBooksList.subcategory[2]`. Table header can be referred in the format of `issueResult.header[2]`.

More general case can be shown by the following diagram,

http://tellurium-users.googlegroups.com/web/TelluriumUID.png?gda=Ewu9OkIAAAD5mhXrH3CK0rVx4StVj0LYJWJZMftxLZyWDn45QfUHefQJMzacoSN0t8eFUXmEG4hV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=D-2lBwsAAABkwKwxr1YSWDDrmKhvxTo1

For example, the UiID of the List _E_ in the above diagram is `A.C.E` and the InputButton in the List _E_ is referred by its index _`n`_, i.e., `A.C.E[n]`.

## Group Locating ##

In the Tellurium UI module, you often see the "group" attribute. For example:

```
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
  InputBox(uid: "searchbox", clocator: [title: "Google Search"])
  SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
  SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

What does the attribute _group_ mean? The group attribute is a flag for the Group Locating Concept. Usually, the XPath generated by Selenium IDE, XPather, or other tools is a single
path to the target node such as:

```
//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]
```

No sibling node's information is used here. What is wrong with this? The XPath depends too much on information from nodes far away from the target node. In Tellurium, we try to localize the information and reduce this dependency by using sibling information or local information. For example, in the above google UI module, the group locating concept will try to find the "td" tag with its children as "InputBox", "googlesearch" button, and
"Imfeelinglucky" button. In this way, we can reduce the dependencies of the UI elements inside a UI module on external UI elements to make the UI definition more robust.

## UI Templates ##

Tellurium UI templates are used for two purposes:

  1. When there are many identical UI elements, you can use one template to represent them all
  1. When here are a variable/dynamic size of UI elements at runtime, you know the patterns, but not the size.

More specifically, Table and List are two Tellurium objects that can define UI templates. Table defines two dimensional UI templates, and List is for one dimensional. The Template has special UIDs such as "2", "all", or "row: 1, column: 2".

Let us look at use case (1), we have the following HTML source

```
      <ul class="a">
        <li>
            <A HREF="site?tcid=a"
               class="b">AA
            </A>
        </li>
        <li>
            <A HREF="site?tcid=b"
               class="b">BB
            </A>
        </li>
        <li>
            <A HREF="site?;tcid=c"
               class="b">CC
            </A>
        </li>
        <li>
            <A HREF="site?tcid=d"
               class="b">DD
            </A>
        </li>
        <li>
            <A HREF="site?tcid=e"
               class="b">EE
            </A>
        </li>
        <li>
            <A HREF="site?tcid=f"
               class="b">FF
            </A>
        </li>
    </ul>
```

You have six links there. Without templates, you have to put six UrlLink objects in the UI module. Look at how simple by using the template

```
ui.List(uid: "list", clocator: [tag: "ul", class: "a"], separator:"li")
{
    UrlLink(uid: "all", clocator: [class: "b"])
}
```


For use case (2), a common application is the data grid. Look at the "issueResult" data grid on our Tellurium Issues page:

```
ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], 
         group: "true") 
{
    TextBox(uid: "header: 1",  clocator: [:])
    UrlLink(uid: "header: 2",  clocator: [text: "%%ID"])
    UrlLink(uid: "header: 3",  clocator: [text: "%%Type"])
    UrlLink(uid: "header: 4",  clocator: [text: "%%Status"])
    UrlLink(uid: "header: 5",  clocator: [text: "%%Priority"])
    UrlLink(uid: "header: 6",  clocator: [text: "%%Milestone"])
    UrlLink(uid: "header: 7",  clocator: [text: "%%Owner"])
    UrlLink(uid: "header: 9",  clocator: [text: "%%Summary + Labels"])
    UrlLink(uid: "header: 10", clocator: [text: "%%..."])

    //define table elements
    //for the border column
    TextBox(uid: "row: *, column: 1", clocator: [:])
    //For the rest, just UrlLink
    UrlLink(uid: "all", clocator: [:])
}
```

Aren't the definitions very simple and cool?

You may wonder how to use the templates if you have multiple templates such as the "issueResult" table shown above. The rule to apply the templates is: "specific one first, general one later".

## Javascript Events ##

Most web applications include Javascript, and thus the web testing framework must be able to handle Javascript events. What we really care about is firing the appropriate events to trigger the event handlers.

Selenium has already provided methods to generate events such as:

```
fireEvent(locator, "blur")
fireEvent(locator, "focus")
mouseOut(locator)
mouseOver(locator)
```

Tellurium was born with Javascript events in mind since it was initially designed to test applications written using the DOJO JavaScript framework. For example, we have the following radio button:

```
<input type='radio' name='mas_address_key' value='5779' onClick='SetAddress_5779()'>
```

Although we can define the radio button as follows:

```
RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'])
```

The above code will not respond to the click event since the Tellurium RadioButton only supports the "check" and "uncheck" actions, which is enough for the normal case. As a result, no "click" event/action will be generated during the testing. To address this problem, Tellurium added the "respond" attribute to Tellurium UI objects. The "respond" attribute could be used to define whatever events you want the UI object to respond to. The Radio Button can be redefined as:

```
 ui.Container(uid: "form", clocator: [whatever]){
     RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'], 
               respond: ["click"])
 }
```

That is to say, you can issue the following command:

```
  click "form.billing"
```

Even if the RadioButton does not have the _click_ method defined by default, it is still able to dynamically add the _click_ method at runtime and call it.

A more general example is as follows:

```
 InputBox(uid: "searchbox", clocator: [title: "Google Search"], 
          respond: ["click", "focus", "mouseOver", "mouseOut", "blur"])
```

Except for the "click" event, all of the "focus", "mouseOver", "mouseOut", and "blur" events will be automatically fired by Tellurium during testing. Do not worry about the event order for the respond attribute, Tellurium will automatically re-order the events and then process them appropriately for you.

## Logical Container ##

The Container object in Tellurium is used to hold child objects that are in the same subtree in the DOM object. However, there are always exceptions. For example, logical Container (Or virtual Container, but I think Logical Container is better) can violate this rule.

What is a logic Container? It is a Container with empty locator, for instance,

```
Container(uid: "logical"){
   ......
}
```


But empty != nothing, there are some scenarios that the logical container can play an important role. The reason is the Container includes a uid for you to refer and it can logically group UI element together.

Look at the following example,
```

    <div class="block_content">
        <ul>
            <li>
                <h5>
                    <a href="" title="">xxx</a>
                </h5>
                <p class="product_desc">
                    <a href=".." title="More">...</a>
                </p>
                <a href="..." title=".." class="product_image">
                    <img src="..." alt="..." height="129" width="129"/>
                </a>
                <p>
                    <a class="button" href="..." title="View">View</a>
                    <a title="Add to cart">Add to cart</a>
                </p>
            </li>
            <li>
                similar UI
            </li>
            <li>
                similar UI
            </li>
        </ul>
    </div>       

```

As you see, the UI elements under the tag li are different and how to write the UI template for them? The good news is that the logical Container comes to play. For example,
the UI module could be written as

```
ui.Container(uid: "content", clocator: [tag: "div", class: "block_content"]){
     List(uid: "list", clocator: [tag: "ul"], separator:"li") {
         Container("all"){
             UrlLink(uid: "title", clocator: [title: "View"])
                      ......
                      other elements inside the li tag
         }
    }
} 
```

Another usage of the logical Container is to convert the test case recorded by Selenium IDE to Tellurium test cases. Let us take the search UI on Tellurium download page as an example.

First, I recorded the following Selenium test case using Selenium IDE,

```
import com.thoughtworks.selenium.SeleneseTestCase;

public class SeleniumTestCase extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://code.google.com/", "*chrome");
	}
	public void testNew() throws Exception {
		selenium.open("/p/aost/downloads/list");
		selenium.select("can", "label=regexp:\\sAll Downloads");
		selenium.type("q", "TrUMP");
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
	}
}
```

Don't be confused by the locator "can" and "q", they are just UI element ids and can be easily expressed in XPath. The "label=regexp:\\sAll Downloads" part just tells you that Selenium uses regular express to match the String and the "\s" stands for a space. As a result, we can write the UI module based on the above code.

```
public class TelluriumDownloadPage extends DslContext {

  public void defineUi() {
    ui.Container(uid: "downloads") {
      Selector(uid: "downloadType", locator: "//*[@id='can']")
      InputBox(uid: "input", locator: "//*[@id='q']")
      SubmitButton(uid: "search", locator: "//input[@value='Search']")
    }
  }

  public void searchDownload(String downloadType, String searchKeyWords) {
    selectByLabel "downloads.downloadType", downloadType
    keyType "downloads.input", searchKeyWords
    click "downloads.search"
    waitForPageToLoad 30000
  }
}
```

And we can create the Tellurium test case accordingly,

```
public class TelluriumDownloadPageTestCase extends TelluriumJavaTestCase {

    protected static TelluriumDownloadPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new TelluriumDownloadPage();
        ngsp.defineUi();
    }

    @Test
    public void testSearchDownload(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        ngsp.searchDownload(" All Downloads", "TrUMP");
    }
}
```


## CSS Selector ##

The motivation behind the CSS Selector is to improve the test speed in IE since IE lacks of native XPath support and the XPath is very slow. Tellurium exploits CSS selector capability to improve the test speed dramatically.

Tellurium supports both XPath and CSS selector and still uses XPath as the default locator. To use CSS selector, you need to explicitly tell Tellurium to use CSS selector.

To enable CSS selector call the following method.

  * `useCssSelector()`: Use CSS selector.

To disable CSS selector call the following method, it also switches back to the default
locator. i.e. XPath

  * `disableCssSelector()`: Switch back to XPath locator.

The above can be better illustrated the following diagram.

http://tellurium-users.googlegroups.com/web/xpathjqsel2.png?gda=ZcLbzkEAAACsdq5EJRKLPm_KNrr_VHB_i4k4E3yBw3ZwuTWAYUCsylo_LL8k1Ivp8OS586drcZpTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=4508XgsAAACdKjHXeYuQCiQefauhN3Sg

Some of the jQuery functions are provided by the custom selenium server, which is created by our Tellurium Engine project. Be aware that CSS selector only works for composite locator, i.e. clocator. You cannot have base locator, i.e., XPath, in your UI module.

How does the CSS selector work? The basic idea is to customize Selenium Core to load the jQuery library at startup time. You can add jquery.js in to the TestRunner.html and RemoteRunner.html. Another way is dump all jquery.js into user-extensions.js. Since our Engine prototype customizes Selenium core anyway, we used the former method.

After that, we register a custom locate strategy "jquery", using the following Selenium API call:

```
addLocationStrategy ( strategyName,functionDefinition )
```

This defines a new function for Selenium to locate elements on the page. For example,
if you define the strategy "foo", and someone runs click("foo=blah"), we'll run your
function, passing you the string "blah", and click on the element that your function
returns, or throw an "Element not found" error if your function returns null. Selenium passed three arguments to the location strategy function:

  * _locator_: the string the user passed in
  * _inWindow_: the currently selected window
  * _inDocument_: the currently selected document

The function must return null if the element can't be found.

Arguments:

  * _strategyName_: the name of the strategy to define; this should use only letters [a-zA-Z] with no spaces or other punctuation.
  * _functionDefinition_: a string defining the body of a function in JavaScript. For example: return inDocument.getElementById(locator);

Accordingly, we define the custom locate strategy as follows,

```
addLocationStrategy("jquery", '''
  var found = $(inDocument).find(locator);
  if(found.length == 1 ){
    return found[0];
  }else if(found.length > 1){
    return found.get();
  }else{
    return null;
  }
''')
```

The code is pretty straightforward. When we find one element, return its DOM reference (Note: Selenium does not accept returning an array with only one element) and if we find multiple elements, we use jQuery get() method to return an array of DOM references. Otherwise, return null.

The actual code is a bit more complicated because we need to consider our custom attribute locator. We use the same format of attribute locator as the XPath one, i.e.,

```
locator@attribute
```

Apart from that, we need to create a set of custom Selenium methods and add the appropriate Selenium RC Java methods. For example, we created the following Selenium method

```
Selenium.prototype.getAllText = function(locator){
  var $e = $(this.browserbot.findElement(locator));
  var out = [];
  $e.each(function(){
     out.push($(this).text());
  });

  return JSON.stringify(out);
};
```

Then, created the corresponding Java method by extending Selenium

```
class CustomSelenium extends DefaultSelenium {

  def String getAllText(String locator){
     String[] arr = [locator];
     String st = commandProcessor.doCommand("getAllText", arr);
     return st;
  }
}
```

CSS Selector provides the following additional Selenium methods, which are utilized in DslContext to form a set of new DSL methods.

  * `String getAllText(String locator)`: Get all text from the set of elements corresponding to the CSS Selector.

  * `String getCSS(String locator, String cssName)` : Get the CSS properties for the set of elements corresponding to the CSS Selector.

  * `Number getJQuerySelectorCount(String locator)` : Get the number of elements matching the corresponding CSS Selector

jQuery also supports the following attribute selectors.

  * `attribute`: have the specified attribute.

  * `attribute=value`: have the specified attribute with a certain value.

  * `attribute!=value`: either don't have the specified attribute or do have the specified attribute but not with a certain value.

  * `attribute^=value`: have the specified attribute and it starts with a certain value.

  * `attribute$=value`: have the specified attribute and it ends with a certain value.

  * `attribute*=value`: have the specified attribute and it contains a certain value.

Apart from the above, Tellurium provides a set of locator agnostic methods, i.e., the method will automatically decide to use XPath or jQuery dependent on the _exploreCssSelector_ flag, which can be turn on and off by the following two methods:

```
public void useCssSelector()

public void disableCssSelector()
```

In the meanwhile, Tellurium also provides the corresponding XPath specific and CSS selector specific methods for your convenience. However, we recommend you to use the locator agnostic methods until you have a good reason not to.

The new methods are listed here:

  * Get the Generated locator from the UI module

> Locator agnostic:
    * `String getLocator(String uid)`

> CSS selector specific:
    * `String getSelector(String uid)`

> XPath specific:
    * `String getXPath(String uid)`

  * Get the Number of Elements matching the Locator

> Locator agnostic:
    * `Number getLocatorCount(String locator)`

> CSS selector specific:
    * `Number getJQuerySelectorCount(String jQuerySelector)`

> XPath specific:
    * `Number getXpathCount(String xpath)`

  * Table methods

> Locator agnostic:
    * `int getTableHeaderColumnNum(String uid)`
    * `int getTableFootColumnNum(String uid)`
    * `int getTableMaxRowNum(String uid)`
    * `int getTableMaxColumnNum(String uid)`
    * `int getTableMaxRowNumForTbody(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbody(String uid, int ntbody)`
    * `int getTableMaxTbodyNum(String uid)`

> CSS selector specific:
    * `int getTableHeaderColumnNumBySelector(String uid)`
    * `int getTableFootColumnNumBySelector(String uid)`
    * `int getTableMaxRowNumBySelector(String uid)`
    * `int getTableMaxColumnNumBySelector(String uid)`
    * `int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody)`
    * `int getTableMaxTbodyNumBySelector(String uid)`

> XPath specific:
    * `int getTableHeaderColumnNumByXPath(String uid)`
    * `int getTableFootColumnNumByXPath(String uid)`
    * `int getTableMaxRowNumByXPath(String uid)`
    * `int getTableMaxColumnNumByXPath(String uid)`
    * `int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody)`
    * `int getTableMaxTbodyNumByXPath(String uid)`

  * Check if an Element is Disabled

> Locator agnostic:
    * `boolean isDisabled(String uid)`

> CSS selector specific:
    * `boolean isDisabledBySelector(String uid)`

> XPath specific:
    * `boolean isDisabledByXPath(String uid)`

  * Get the Attribute

> Locator agnostic:
    * `def getAttribute(String uid, String attribute)`

  * Check the CSS Class

> Locator agnostic
    * `def hasCssClass(String uid, String cssClass)`

  * Get CSS Properties

> CSS selector specific:
    * `String[] getCSS(String uid, String cssName)`

  * Get All Data from a Table

> CSS selector specific:
    * `String[] getAllTableCellText(String uid)`
    * `String[] getAllTableCellTextForTbody(String uid, int index)`

  * Get List Size

> Locator agnostic:
    * `int getListSize(String uid)`

> CSS selector specific:
    * `getListSizeBySelector(String uid)`

> XPath specific:
    * `getListSizeByXPath(String uid)`

The are something you should be aware of for CSS Selector:
  * If you have a duplicate "id" attribute on the page, CSS selector always returns the first DOM reference, ignoring other DOM references with the same "id" attribute.
  * Some attribute may not working in jQuery, for example, the "action" attribute in a form. Tellurium has a black list to automatically filter out the attributes that are not honored by CSS selector.
  * Seems the "src" attribute in Image has to be full URL such as http://www.google.com. One workaround is to put '`*`' before the URL.


## jQuery Selector Cache Option ##

jQuery cache is a mechanism to further improve the speed by reusing the found DOM reference for a given jQuery selector. Our benchmark results show that the jQuery cache could improve the speed by up to 14% over the regular jQuery selector and over 27% for some extreme cases. But the improvement of jQuery cache over regular jQuery selector has upper bound, which is the portion of jQuery locating time out of the round trip time from Tellurium core to Selenium core. jQuery cache is still considered to be experimental at the current stage. Use it at your own discretion.

To make the cache option configurable in tellurium UI module, Tellurium introduces an attribute _cacheable_ to UI object, which is set to be true by default. For a Container type object, it has an extra attribute _noCacheForChildren_ to control whether to cache its children.

One example UI module is defined as follows,

```
  ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "true",
           clocator: [id: "resultstable", class: "results"], group: "true") {
 
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

When you choose to use jQuery Cache mechanism, Tellurium core will pass the jQuery selector and a meta command to Tellurium Engine, which is embedded inside Selenium core at the current stage. The format is as follows taking the Tellurium issue searching UI as an example,

```
jquerycache={
  "locator":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], 
             input[value=Search][type=submit]) #can",
  "optimized":"#can",
  "uid":"issueSearch.issueType",
  "cacheable":true,
  "unique":true
}
```

where _locator_ is the regular jQuery selector and _optimized_ is the optimized jQuery selector by the jQuery selector optimizer in Tellurium core. The _locator_ is used for child UI element to derive a partial jQuery selector from its parent. The _optimized_ will be used for actual DOM searching.

The rest three parameters are meta commands. _uid_ is the UID for the corresponding jQuery selector and _cacheable_ tells the Engine whether to cache the selector or not. The reason is that some UI element on the web is dynamic, for instance, the UI element inside a data grid. As a result, it may not be useful to cache the jQuery selector.

The last one, _unique_, tells the Engine whether the jQuery selector expects multiple elements or not. This is very useful to handle the case that jQuery selector is expected to return one element, but actually returns multiple ones. In such a case, a Selenium Error will be thrown.

On the Engine side the cache is defined as

```
    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
    this.sCache = new Hashtable();
    
    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;
```

The cache system includes a global flag to decide whether to use the cache capability, a hash table to store the cached data, a cache size limit, and a cache eviction policy once the cache is filled up.

The cached data structure is defined as

```
//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to find the reminding selector 
    //for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //jQuery object for DOM reference
    this.reference = null;
    //number of reuse
    this.count = 0;
    //last use time
    this.timestamp = Number(new Date());
};
```

Tellurium Engine provides the following cache eviction policies:

  * _DiscardNewPolicy_: discard new jQuery selector.
  * _DiscardOldPolicy_: discard the oldest jQuery selector measured by the last update time.
  * _DiscardLeastUsedPolicy_: discard the least used jQuery selector.
  * _DiscardInvalidPolicy_: discard the invalid jQuery selector first.

It is important to know when the cached data become invalid. There are three mechanisms we can utilize here:
  1. Listen for page refresh event and invalidate the cache data accordingly
  1. Intercept Ajax response to decide when the web is update and if the cache needs to be updated.
  1. Validate the cached jQuery selector before using it.

Right now, Tellurium Engine uses the 3rd mechanism to check if the cached data is valid or not. The first two mechanisms are still under development.

Whenever Tellurium Core passes a locator to the Engine, the Engine will first look at the meta command _cacheable_. If this flag is true, it will first try to look up the DOM reference from the cache. If no cached DOM reference is available, do a fresh jQuery search and cache the DOM reference, otherwise, validate the cached DOM reference and use it directly. If the _cacheable_ flag is false, the Engine will look for the UI element's ancestor by its UID and do a jQuery search starting from its ancestor if possible.

Tellurium Core provides the following methods for jQuery Selector cache control,

  * _`public void enableSelectorCache()`_: Enable jQuery selector cache

  * _`public boolean disableSelectorCache()`_: Disable jQuery selector cache

  * _`public boolean cleanSelectorCache()`_: Cleanup the whole jQuery selector cache

  * _`public boolean getSelectorCacheState()`_: Test if the cache is enabled or not in Engine

  * _`public void setCacheMaxSize(int size)`_: Set the cache maximum size

  * _`public int getCacheSize()`_: Get the cache current size

  * _`public int getCacheMaxSize()`_: Get the cache maximum size

  * _`public Map<String, Long> getCacheUsage()`_: Get the cache usage

  * _`public void useDiscardNewCachePolicy()`_: Use DiscardNewCachePolicy

  * _`public void useDiscardOldCachePolicy()`_: Use DiscardOldCachePolicy

  * _`public void useDiscardLeastUsedCachePolicy()`_: Use DiscardLeastUsedCachePolicy

  * _`public void useDiscardInvalidCachePolicy()`_: Use DiscardInvalidCachePolicy

  * _`public String getCurrentCachePolicy()`_: Get the current cache policy

## "Include" Frequently Used Sets of elements in UI Modules ##

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

## Customize Individual Test settings Using setCustomConfig ##

TelluriumConfig.groovy provides project level test settings, you can use setCustomConfig to custmoize individual testing settings.

```
public void setCustomConfig(boolean runInternally, int port, String browser,
                            boolean useMultiWindows, String profileLocation)

public void setCustomConfig(boolean runInternally, int port, String browser,
                  boolean useMultiWindows, String profileLocation, String serverHost)
```

For example, you can specify your custom settings as follows,

```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase {
    static{
        setCustomConfig(true, 5555, "*chrome", true, null);
    }

    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
        ngsp.useJavascriptXPathLibrary();
    }

    @Test
    public void testGoogleSearch() {
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
    }

    ......

}
```

## User Custom Selenium Extensions ##

To support user custom selenium extensions, Tellurium Core added the following configurations to TelluriumConfig.groovy

```
  embeddedserver {

    ......

    //user-extension.js file, for example "target/user-extensions.js"
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

## Data Driven Testing ##

[Data Driven Testing](http://code.google.com/p/aost/w/edit/DataDrivenTesting) is a different way to write tests, i.e, separate test data from the test scripts and the test flow is not controlled by the test scripts, but by the input file instead. In the input file, users can specify which tests to run, what are input parameters, and what are expected results. Data driven testing in Tellurium can be illustrated by the following system diagram.

http://tellurium-users.googlegroups.com/web/TelluriumDataDrivenSmall.jpg?gda=WePTVk4AAAD5mhXrH3CK0rVx4StVj0LYYQ-a0sZzxEmmZWlHbP2MWzTrErMgjh_s8-a7FfMpsGsNL5bu5vgQy2xsA01CuO9M47Cl1bPl-23V2XOW7kn5sQ&gsc=ODykzgsAAACEc2FtJPGdXe_7CHb1VB6Z

The tellurium data driven test consists of three main parts, i.e., Data Provider, TelluriumDataDrivenModule, and TelluriumDataDrivenTest. The data provider is responsible for reading data from input stream and converting data to Java variables. Tellurium provides the following data provider methods:

  1. loadData file\_name, load input data from a file
  1. useData String\_name, load input data from a String in the test script
  1. bind(field\_name), bind a variable to a field in a field set
  1. closeData, close the input data stream and report the test results
  1. cacheVariable(name, variable), put variable into cache
  1. getCachedVariable(name, variable), get variable from cache

where the _`file_name`_ should include the file path, for example,

```
  loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
```

Right now, Tellurium supports pipe format and CSV formatinput file. To change the file reader for different formats, please change the following settings in the configuration file TelluriumConfig.groovy:

```
datadriven{
    dataprovider{
        //specify which data reader you like the data provider to use
        //the valid options include "PipeFileReader", "CSVFileReader" at this point
        reader = "PipeFileReader"
    }
}
```

Sometimes, you may like to specify test data in the test scripts directly, _useData_ is designed for this purpose and it loads input from a String. The String is usually defined in Groovy style using triple quota, for example:

```
  protected String data = """
    google_search | true | 865-692-6000 | tellurium
    google_search | false| 865-123-4444 | tellurium selenium test
    google_search | true | 755-452-4444 | tellurium groovy
    google_search | false| 666-784-1233 | tellurium user group
    google_search | true | 865-123-5555 | tellurium data driven
"""
    ...

  useData data
```

bind is the command to bind a variable to input Field Set field at runtime. FieldSet is the format of a line of data and it is defined in the next section. For example,

```
  def row = bind("GCHLabel.row")
```

is used to bind the row variable to the "row" field in the FieldSet "GCHLabel". Tellurium does not explicitly differentiate input parameters from the expected results in the input data. You only need to bind variables to the input data and then you can use any of them as the expected results for result comparison.

cacheVariable and getCachedVariable are used to pass intermediate variables among tests. cacheVariable is used to put variable into a cache and getCachedVariable is used to get back the variable. For example,

```
int headernum = getTableHeaderNum()
cacheVariable("headernum", headernum)

...

int headernum = getCachedVariable("headernum")
...
```

When you are done with your testing, please use "closeData" to close the input data stream. In the meantime, the result reporter will output the test results in the format you specified in the configuration file, for example, XML file as shown in the TelluriumConfig.groovy file:

```
test{
    result{
       //specify what result reporter used for the test result
       //valid options include "SimpleResultReporter", "XMLResultReporter", 
       //and "StreamXMLResultReporter"
       reporter = "XMLResultReporter"
       //the output of the result
       //valid options include "Console", "File" at this point
       //if the option is "File", you need to specify the file name, 
       //other wise it will use the default
       //file name "TestResults.output"
       output = "Console"
       //test result output file name
       filename = "TestResult.output"
    }
}
```

TelluriumDataDrivenModule is used to define modules, where users can define UI Modules, FieldSets, and tests as shown in the following sequence diagram. Users should extend this class to define their own test modules.

http://tellurium-users.googlegroups.com/web/TelluriumDDTModule.png?gda=oIfzLkgAAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPW9wwFNWzBcwQWJJR7cmP5glzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

TelluriumDataDrivenModule provides one method "defineModule" for users to implement. Since it extends the DslContext class, users can define UI modules just like in regular Tellurium UI Module. For example:

```
ui.Table(uid: "labels_table", clocator: [:], group: "true"){
   TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", 
           text: "Example project labels:"])
   Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        UrlLink(uid: "all", locator: "/a")
   }
}
```

FieldSet is used to define the format of one line of input data and FieldSet consists of fields, i.e., columns, in the input data. There is a special field "test", which users can specify what test this line of data apply to. For example,

```
fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
    Test(value: "getGCHStatus")
    Field(name: "label")
    Field(name: "rowNum", type: "int")
    Field(name: "columNum", type: "int")
}  
```

The above FieldSet defines the input data format for testing google code hosting web page. Note, the Test field must be the first column of the input data. The default name of the test field is "test" and does not need to be specified. If the value attribute of the test field is not specified, it implies this same format, i.e., FieldSet, can used for different tests.

For a regular field, it includes the following attributes:

```
class Field {
	//Field name
	private String name

        //Field type, default is String
        private String type = "String"

        //optional description of the Field
	private String description

	//If the value can be null, default is true
	private boolean nullable = true

	//optional null value if the value is null or not specified
	private String nullValue

	//If the length is not specified, it is -1
	private int length = -1

	//optional String pattern for the value
	//if specified, we must use it for String validation
	private String pattern
   } 
```

Tellurium can automatically handle Java primitive types. Another flexibility Tellurium provides is that users can define their own custom type handlers to deal with more complicated data types by using "typeHandler", for example,

```
//define custom data type and its type handler
typeHandler "phoneNumber", "org.tellurium.test.PhoneNumberTypeHandler"

//define file data format
fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
    Field(name: "regularSearch", type: "boolean", 
          description: "whether we should use regular search or use I'm feeling lucky")
    Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
    Field(name: "input", description: "input variable")
}
```

The above script defined a custom type "PhoneNumber" and the Tellurium will automatically call this type handler to convert the input data to the "PhoneNumber" Java type.

The "defineTest" method is used to define a test in the TelluriumDataDrivenModule, for example, the following script defines the "clickGCHLabel" test:

```
defineTest("clickGCHLabel"){
    def row = bind("GCHLabel.row")
    def column = bind("GCHLabel.column")
           
    openUrl("http://code.google.com/hosting/")
    click  "labels_table[2][1].[${row}][${column}]"

    waitForPageToLoad 30000
}
```

Note, the bind command is used to bind variables row, column to the fields "row" and "column" in the FieldSet "GCHLabel".

Tellurium also provide the command "compareResult" for users to compare the actual result with the expected result. For example, the following script compares the expected label, row number, and column number with the acutal ones at runtime:

```
defineTest("getGCHStatus"){
    def expectedLabel = bind("GCHStatus.label")
    def expectedRowNum = bind("GCHStatus.rowNum")
    def expectedColumnNum = bind("GCHStatus.columNum")

    openUrl("http://code.google.com/hosting/")
    def label = getText("labels_table[1][1]")
    def rownum = getTableMaxRowNum("labels_table[2][1]")
    def columnum = getTableMaxColumnNum("labels_table[2][1]")

    compareResult(expectedLabel, label)
    compareResult(expectedRowNum, rownum)
    compareResult(expectedColumnNum, columnum)
    pause 1000
}
```

Sometimes, users may need custom "compareResult" to handle more complicated situation, for example, users want to compare two lists. In such a case, users can override the default "compareResult" behaviour by specifying custom code in the closure:

```
compareResult(list1, list2){
    assertTrue(list1.size() == list2.size())
    for(int i=0; i<list1.size();i++){
        //put your custom comparison code here
    }
}
```

If users want to check a variable in the test and the "checkResult" method should be used, which comes with a closure where users can define the actually assertions inside.

```
checkResult(issueTypeLabel) {
    assertTrue(issueTypeLabel != null)
}
```

Like "compareResult", "checkResult" will capture all assertion errors. The test will resume even the assertions fail and the result will be reported in the output.

In addition to that the "logMessage" can be used for users to log any messages in the output.

```
logMessage "Found ${actual.size()} ${issueTypeLabel} for owner " + issueOwner 
```

TelluriumDataDrivenTest is the class users should extend to run the actual data driven testing and it is more like a data driven testing engine. There is only one method, "testDataDriven", which users need to implement. The sequence diagram for the testing process is shown as follows.

http://tellurium-users.googlegroups.com/web/TelluriumDDTTestSequence.png?gda=Fgy8N04AAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPYEAgewWsw-pfk-JI3kLTQ9omPhdHiHZ5EjvmEnpg6SE47Cl1bPl-23V2XOW7kn5sQ&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

Usually, users need to do the following steps:

  1. use "includeModule" to load defined Modules
  1. use "loadData" or "useData" to load input data stream
  1. use "stepToEnd" to read the input data line by line and pick up the specified test and run it, until reaches the end of the data stream
  1. use "closeData" to close the data stream and output the test results

What the "includeModule" does is to merge in all Ui modules, FieldSets, and tests defined in that module file to the global registry. "stepToEnd" will look at each input line, first to find the test name and pass in all input parameters to it, and then run the test. The whole process can be illustrated in the following example:

```
class GoogleBookListCodeHostTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {

        includeModule  example.google.GoogleBookListModule.class
        includeModule  example.google.GoogleCodeHostingModule.class

        //load file
        loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
        
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()
    }
}

```

The input data for this example are as follows,

```
##TEST should be always be the first column

##Data for test "checkBookList"
##TEST | CATEGORY | SIZE
checkBookList|Fiction|8
checkBookList|Fiction|3

##Data for test "getGCHStatus"
##TEST | LABEL | Row Number | Column Number
getGCHStatus |Example project labels:| 3 | 6
getGCHStatus |Example project| 3 | 6

##Data for test "clickGCHLabel"
##TEST | row | column
clickGCHLabel | 1 | 1
clickGCHLabel | 2 | 2
clickGCHLabel | 3 | 3
```

Note that line starting with "##" is comment line and empty line will be ignored.

If some times, users want to control the testing execution flow by themselves, Tellurium also provides this capability even though it is not recommended. Tellurium provides two additional commands, i.e., "step" and "stepOver". "step" is used to read one line of input data and run it, and "stepOver" is used to skip one line of input data. In this meanwhile, Tellurium also allows you to specify additional test script using closure. For example,

```
step{
    //bind variables
    boolean regularSearch = bind("regularSearch")
    def phoneNumber = bind("fs4googlesearch.phoneNumber")
    String input = bind("input")

    openUrl "http://www.google.com"
    type "google_start_page.searchbox", input
    pause 500
    click "google_start_page.googlesearch"
    waitForPageToLoad 30000
}
```

But this usually implies that the input data format is unique or the test script knows about what format the current input data are using.


## The Dump Method ##

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
issueSearch.issueType: //descendant-or-self::form[descendant::select[@name="can" and 
     @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and 
     descendant::input[@value="Search" and @type="submit"] and 
     @action="list" and @method="get"]/descendant-or-self::select
                         [@name="can" and @id="can"]
issueSearch.searchLabel: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::span[contains(text(),"for")]
issueSearch.searchBox: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::input[@type="text" and @name="q"]
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------
```

## Selenium Grid Support ##

Selenium Grid transparently distributes your tests on multiple machines so that you can run your tests in parallel. We recently added support for Selenium Grid to the Tellurium. Now you can run the Tellurium tests against different browsers using Selenium Grid. Tellurium core is updated to support Selenium Grid sessions.

Assume we have 3 machines set up to run the Tellurium tests on Selenium Grid. You can do all these steps on your local box. To do this locally remove the machine names with localhost. Each machine in this set up has a defined role as described below.

**dev1.tellurium.com**
Tellurium test development machine.

**hub.tellurium.com**
Selenium Grid hub machine that will drive the tests.

**rc.tellurium.com**
Multiple Selenium RC server running and registered to the Selenium Grid HUB. The actual test execution will be done on this machine. You can register as many Selenium RC servers as you wish. You need to be realistic about the hardware specification though.

Download Selenium Grid from the following URL and extract the contents of the folder on each of these machines.

We use Selenium Grid 1.0.3 which is the current released version.
http://selenium-grid.seleniumhq.org/download.html

Following is the illustration of the environment.

http://tellurium-users.googlegroups.com/web/TelluriumGridSetup.png?gda=9fgieUgAAAD5mhXrH3CK0rVx4StVj0LYYQ-a0sZzxEmmZWlHbP2MW6uQS0SyLBzYwfM7_kvx7qklzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=ODykzgsAAACEc2FtJPGdXe_7CHb1VB6Z

The first step would be to launch the selenium grid hub on the hub machine. Open up a terminal on the HUB machine **hub.tellurium.com** and go to the download directory of Selenium Grid.

```
> cd /Tools/selenium-grid-1.0.3
> ant launch-hub
```

You will notice that it has launched the Selenium HUB on the machine with different browsers. To ensure that the HUB is working properly go to the following location.

http://hub.tellurium.com:4444/console

You can see a web page with 3 distinct columns as **Configured Environments, Available Remote Controls, Active Remote Controls**

You will have a list of browsers that are configured by default to run the tests while the list for Available Remote Controls and Avtive Remote Controls will be empty.

The next step would be to launch the Selenium RC servers and register them with the selenium HUB. Open up a terminal on **rc.tellurium.com** and go to the selenium grid download directory.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5555 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 \
      -Denvironment="Firefox on Windows" launch-remote-control
```
This command will start a Selenium RC server on this machine and register it with the Selenium Grid hub machine as specified by the hubURL.

To register another Selenium RC server on this machine for internet explorer do the same on a different port.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5556 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 -Denvironment="IE on Windows" launch-remote-control
```

  * _port_ will that the remote control will be listening at. Must be unique on the machine the remote control runs on.

  * _hostname_ Hostname or IP address of the machine the remote control runs on. Must be visible from the Hub machine.

  * _hub url_  Which hub the remote control should register/unregister to. as the hub is running on hostname hub.tellurium.com, this URL will be http://hub.tellurium.com:4444

Once you are successful in replicating a setup similar to the one described above, point your browser to the Hub console (http://hub.tellurium.com:4444/console). Check that all the remote controls did register properly. Available remote controls list should be updated and have these 2 selenium servers available to run the tests.

Now you have started the selenium hub and the selenium rc servers on the Grid environment, the next step would be to run the Tellurium tests against different browsers.

Go to the Tellurium test development machine which in our case is **dev1.tellurium.com**.

Open up the TelluriumConfig.groovy and change the values of the selenium server and port to make sure that tellurium requests for the new sessions from Selenium HUB and then Selenium HUB can point tellurium tests to be run on **rc.tellurium.com** based on the browser of choice.

You need to change the values for the following properties.

  * _runInternally_: ensures that you do not launch the Selenium Server on the local machine.
  * _serverHost_: the selenium grid hub machine that has the information about the available selenium rc servers.
  * _port_: port that Selenium HUB is running on, by default this port is 4444 but you can change that in the grid\_configuraton.yml file if this port is not available on your HUB machine.
  * _browser_: the browser that comes under the configured environments list on the selenium HUB machine, you can change these values to your own choice in the grid\_configuration.yml file.

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
        //profile location
        profile = ""
        //user-extension.js file
        userExtension = "target/classes/extension/user-extensions.js"
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
    //the configuration for the connector that connects the selenium client 
    //to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "hub.tellurium.com"
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //  *iehta
        browser = "Firefox on Windows"
        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = "org.tellurium.test.MyCommand"
    }
```


The set up is now complete.

Now run the tests as you normally do by either the Maven command or the IDE and you will notice that the tests are running on **rc.tellurium.com** and the list for **Active Remote Controls** is also updated on the hub URL (http://hub.tellurium.com:4444/console) during the test execution.


## Mock Http Server ##

This feature only exists in Tellurium Core 0.7.0 SNAPSHOT. The MockHttpServer is an embedded http server leveraging the Java 6 http server and it is very convenient to test HTML source directly without running a web server. Tellurium defined two classes, i.e.,  MockHttpHandler and MockHttpServer.

The MockHttpHandler is a class to process the http request,

```
public class MockHttpHandler implements HttpHandler {

  private Map<String, String> contents = new HashMap<String, String>();

  private String contentType = "text/html";

  public void handle(HttpExchange exchange) {
     ......
  }
}
```

The main method in MockHttpHandler is `handle(HttpExchange exchange)`, which reads the request URI, finds the corresponding response HTML source from the hash map _contents_, and then sends it back to the http client.

By default, the response will be treated as a HTML source, you can change this by using the following setter.

```
public void setContentType(String contentType)
```

MockHttpHandler includes two methods to add URI and its HTML source to the hash map _contents_, i.e.,

```
public void registerBody(String url, String body)

public void registerHtml(String url, String html)
```

The MockHttpHandler comes with a default HTML template as follows,

```
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Mock HTTP Server</title>
    </head>
    <body>
    BODY_HTML_SOURCE   
    </body>
</html>
```

If you use `registerBody(String url, String body)`, the MockHttpHandler will use the above HTML template to wrap the HTML body. You overwrite the default HTML template by calling `registerHtml(String url, String html)` directly, which will use the whole HTML source you provide in the variable _html_.

Usually, the MockHttpHandler is encapsulated by the MockHttpServer and you don't really need to work on it directly.

The MockHttpServer includes an embedded http server, a http handler, and a http port:

```
public class MockHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private MockHttpHandler handler;

  public MockHttpServer() {
    this.handler = new MockHttpHandler();
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port) {
    this.handler = new MockHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  ......
}
```

As you can see, the MockHttpServer provides three different constructors so that you can overwrite the default values. The MockHttpServer encapsulates the MockHttpHander by providing the following methods,

```
public void setContentType(String contentType)

public void registerHtmlBody(String url, String body)

public void registerHtml(String url, String html)
```

You can stop and start the server with the following methods,

```
public void start()

public void stop()
```

We use a modified version of a HTML source provided by one Tellurium user as an example and create the UI module Groovy class as follows

```
public class ListModule extends DslContext {
  public static String LIST_BODY = """
<div class="thumbnails">
    <ul>
        <li class="thumbnail">
            <img alt="Image 1"
                 src="/images_root/image_pictures/01.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 2"
                 src="/images_root/image_pictures/02.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 3"
                 src="/images_root/image_pictures/03.jpg"/>
        </li>
        <li class="thumbnail">
        </li>
        <li class="thumbnail active">
            <img alt="Image 4"
                 src="/images_root/image_pictures/04.jpg"/>
        </li>
        <li class="thumbnail potd">
            <div class="potd-icon png-fix"/>
            <img alt="Image 5"
                 src="/images_root/image_pictures/05.jpg"/>
        </li>
    </ul>
</div>    
  """

  public void defineUi() {
    ui.Container(uid: "rotator", clocator: [tag: "div", class: "thumbnails"]) {
      List(uid: "tnails", clocator: [tag: "ul"], separator: "li") {
        UrlLink(uid: "all", clocator: [:])
      }
    }
  }
}
```

The reason we include the HTML source in a Groovy file is that the """ quote in Groovy is very easy to present complicated HTML source as a String variable. In Java, you have to concatenate each line of the HTML Source to make it a String variable.

The `defineUi()` defines the UI module for the given HTML source. The major part of the UI module is a List, which uses UI templates to represent a list of links. You can see how easy and concise to use UI templates to represent UI elements in Tellurium.

Based on the ListModule UI module, we define a Tellurium JUnit test case as follows,

```
public class ListTestCase  extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/list.html", ListModule.LIST_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ListModule lm = new ListModule();
        lm.defineUi();

        connectUrl("http://localhost:8080/list.html");

        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        assertEquals("thumbnail potd", attr);
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();    
    }
}
```

## Testing Support ##

In the package org.tellurium.test, Tellurium provides three different ways to write Tellurium tests:

  * TelluriumJavaTestCase is for JUnit, and it supports the following JUnit 4 annotations:
    * @BeforeClass
    * @AfterClass
    * @Before
    * @After
    * @Test
    * @Ignore
  * TelluriumTestNGTestCase is for TestNG. Similarly, you can use the following annotations:
    * @BeforeSuite
    * @AfterSuite
    * @BeforeTest
    * @AfterTest
    * @BeforeGroups
    * @AfterGroups
    * @BeforeClass
    * @AfterClass
    * @BeforeMethod
    * @AfterMethod
    * @DataProvider
    * @Parameters
    * @Test
  * TelluriumGroovyTestCase is for Groovy test cases.
  * Data Driven Testing. Tellurium provides the class _TelluriumDataDrivenModule_ for users to define data driven testing modules and the class _TelluriumDataDrivenTest_ to drive the actual tests.

Apart from the above, Tellurium also provides users the capability to write Tellurium tests and Tellurium data driven tests in pure DSL scripts. The _DslScriptExecutor_ is used to run the .dsl files.

# Tellurium APIs #

Tellurium APIs include all methods defined in DslContext. The time here is in miliseconds unless otherwise specified.

## DSL Methods ##

Here we list all the available methods that can be used as DSLs in DslContext and their corresponding DSL syntax. Note the id here refers to the UiID in the format of "issueSearch.issueType" and the time units are all in milliseconds if not specified. Be aware, you can only apply the methods to the Ui Object if it has these methods defined.

  * `mouseOver id`: Simulates a user hovering a mouse over the specified element.

  * `mouseOut id`: Simulates a user moving the mouse pointer away from the specified element.

  * `mouseDown id`: Simulates a user pressing the mouse button (without releasing it yet) on the specified element.

  * `click id`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `doubleClick id`: Double clicks on a link, button, checkbox or radio button. If the double click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `clickAt id, coordination`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `check id`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `uncheck id`: Uncheck a toggle-button (checkbox/radio).

  * `type id, input`: Sets the value of an input field, as though you typed it in.

  * `keyType id, value`: Simulates keystroke events on the specified element, as though you typed the value key-by-key.

  * `typeAndReturn id, input`: Sets the value of an input field followed by `<Return>` key.

  * `clearText id`: Resets input field to an empty value.

  * `select id, optionLocator`: Select an option from a drop-down using an option locator.

  * `selectByLabel id, optionLocator`: Select an option from a drop-down using an option label.

  * `selectByValue id, optionLocator`: Select an option from a drop-down using an option value.

  * `addSelectionByLabel id, optionLocator`: Add a selection to the set of selected options in a multi-select element using an option label.

  * `addSelectionByValue id, optionLocator`: Add a selection to the set of selected options in a multi-select element using an option value.

  * `removeSelectionByLabel id, optionLocator`: Remove a selection from the set of selected options in a multi-select element using an option label.

  * `removeSelectionByValue id, optionLocator`: Remove a selection from the set of selected options in a multi-select element using an option value.

  * `removeAllSelections id`: Unselects all of the selected options in a multi-select element.

  * `pause time`: Suspend the current thread for a specified milliseconds.

  * `submit id, attribute`: Submit the specified form. This is particularly useful for forms without submit buttons, e.g. single-input "Search" forms.

  * `openWindow UID, url`: Opens a popup window (if a window with that ID isn't already open). After opening the window, you'll need to select it using the selectWindow command.

  * `selectWindow UID`: Selects a popup window; once a popup window has been selected, all commands go to that window. To select the main window again, use null as the target.

  * `closeWindow UID`: Close the popup window.

  * `selectMainWindow`: Select the original window, i.e., the Main window.

  * `selectFrame frameName`: Selects a frame within the current window.

  * `selectParentFrameFrom frameName`: Select the parent frame from the frame identified by the "frameName".

  * `selectTopFrameFrom`: Select the main frame from the frame identified by the "frameName".

  * `waitForPopUp UID, timeout`: Waits for a popup window to appear and load up.

  * `waitForPageToLoad timeout`: Waits for a new page to load.

  * `waitForFrameToLoad frameAdddress, timeout`: Waits for a new frame to load.

  * `runScript script`: Creates a new "script" tag in the body of the current test window, and adds the specified text into the body of the command.  Scripts run in this way can often be debugged more easily than scripts executed using Selenium's "getEval" command.  Beware that JS exceptions thrown in these script tags aren't managed by Selenium, so you should probably wrap your script in try/catch blocks if there is any chance that the script will throw an exception.

  * `captureScreenshot filename`: Captures a PNG screenshot to the specified file.

  * `chooseCancelOnNextConfirmation`: By default, Selenium's overridden window.confirm() function will return true, as if the user had manually clicked OK; after running this command, the next call to confirm() will return false, as if the user had clicked Cancel.  Selenium will then resume using the default behavior for future confirmations, automatically returning true (OK) unless/until you explicitly call this command for each confirmation.

  * `chooseOkOnNextConfirmation`: Undo the effect of calling chooseCancelOnNextConfirmation.  Note that Selenium's overridden window.confirm() function will normally automatically return true, as if the user had manually clicked OK, so you shouldn't need to use this command unless for some reason you need to change your mind prior to the next confirmation.  After any confirmation, Selenium will resume using the default behavior for future confirmations, automatically returning true (OK) unless/until you explicitly call chooseCancelOnNextConfirmation for each confirmation.

  * `answerOnNextPrompt(String answer))`: Instructs Selenium to return the specified answer string in response to the next JavaScript prompt [window.prompt()].

  * `goBack`: Simulates the user clicking the "back" button on their browser.

  * `refresh`: Simulates the user clicking the "Refresh" button on their browser.

  * `dragAndDrop(uid, movementsString)`:  Drags an element a certain distance and then drops it.

  * `dragAndDropTo(sourceUid, targetUid)`: Drags an element and drops it on another element.


## Data Access Methods ##

In addition to the above DSLs, Tellurium provides Selenium-compatible data access methods so that you can get data or Ui statuses from the web. The methods are listed here:

  * `String getConsoleInput()`: Gets input from Console.

  * ` String[] getSelectOptions(id)`: Gets all option labels in the specified select drop-down.

  * `String[] getSelectedLabels(id)`: Gets all selected labels in the specified select drop-down.

  * `String getSelectedLabel(id)`: Gets a single selected label in the specified select drop-down.

  * `String[] getSelectedValues(id)`: Gets all selected values in the specified select drop-down.

  * `String getSelectedValue(id)`: Gets a single selected value in the specified select drop-down.

  * `String[] getSelectedIndexes(id)`: Gets all selected indexes in the specified select drop-down.

  * `String getSelectedIndex(id)`: Gets a single selected index in the specified select drop-down.

  * `String[] getSelectedIds(id)`: Gets option element ID for selected option in the specified select element.

  * `String getSelectedId(id)`: Gets a single element ID for selected option in the specified select element.

  * `boolean isSomethingSelected(id)`: Determines whether some option in a drop-down menu is selected.

  * `String waitForText(id, timeout)`: Waits for a text event.

  * `int getTableHeaderColumnNum(id)`: Gets the column header count of a table

  * `int getTableMaxRowNum(id)`: Gets the maximum row count of a table

  * `int getTableMaxColumnNum(id)`: Gets the maximum column count of a table

  * `int getTableFootColumnNum(id)`: Gets the maximum foot column count of a standard table

  * `int getTableMaxTbodyNum(id)`: Gets the maximum tbody count of a standard table

  * `int getTableMaxRowNumForTbody(id, index)`: Gets the maximum row number of the index-th tbody of a standard table

  * `int getTableMaxColumnNumForTbody(id, index)`: Gets the maximum column number of the index-th tbody of a standard table

  * `int getListSize(id)`: Gets the item count of a list

  * `getUiElement(id)`: Gets the UIObject of an element.

  * `boolean isElementPresent(id)`: Verifies that the specified element is somewhere on the page.

  * `boolean isVisible(id)`: Determines if the specified element is visible. An element can be rendered invisible by setting the CSS "visibility" property to "hidden", or the "display" property to "none", either for the element itself or one if its ancestors.  This method will fail if the element is not present.

  * `boolean isChecked(id)`: Gets whether a toggle-button (checkbox/radio) is checked.  Fails if the specified element doesn't exist or isn't a toggle-button.

  * `boolean isDisabled(id)`: Determines if an element is disabled or not

  * `boolean isEnabled(id)`: Determines if an element is enabled or not

  * `boolean waitForElementPresent(id, timeout)`: Wait for the Ui object to be present

  * `boolean waitForElementPresent(id, timeout, step)`: Wait for the Ui object to be present and check the status by step.

  * `boolean waitForCondition(script, timeout)`: Runs the specified JavaScript snippet repeatedly until it evaluates to "true". The snippet may have multiple lines, but only the result of the last line will be considered.

  * `String getText(id)`: Gets the text of an element. This works for any element that contains text. This command uses either the textContent (Mozilla-like browsers) or the innerText (IE-like browsers) of the element, which is the rendered text shown to the user.

  * `String getValue(id)`: Gets the (whitespace-trimmed) value of an input field (or anything else with a value parameter). For checkbox/radio elements, the value will be "on" or "off" depending on whether the element is checked or not.

  * `String getLink(id)`: Get the href of an element.

  * `String getImageSource(id)`: Get the image source element.

  * `String getImageAlt(id)`: Get the image alternative text of an element.

  * `String getImageTitle(id)`: Get the image title of an element.

  * `getAttribute(id, attribute)`: Get an attribute of an element.

  * `getParentAttribute(id, attribute)`: Get an attribute of the parent of an element.

  * `String getBodyText()`: Gets the entire text of the page.

  * `boolean isTextPresent(pattern)`: Verifies that the specified text pattern appears somewhere on the rendered page shown to the user.

  * `boolean isEditable(id)`: Determines whether the specified input element is editable, ie hasn't been disabled. This method will fail if the specified element isn't an input element.

  * `String getHtmlSource()`: Returns the entire HTML source between the opening and closing "html" tags.

  * `String getExpression(expression)`: Returns the specified expression.

  * `getXpathCount(xpath)`: Returns the number of nodes that match the specified xpath, eg. "//table" would give the number of tables.

  * `String getCookie()`: Return all cookies of the current page under test.

  * `boolean isAlertPresent()`: Has an alert occurred?

  * `boolean isPromptPresent()`: Has a prompt occurred?

  * `boolean isConfirmationPresent()`: Has confirm() been called?

  * `String getAlert()`: Retrieves the message of a JavaScript alert generated during the previous action, or fail if there were no alerts.

  * `String getConfirmation()`: Retrieves the message of a JavaScript confirmation dialog generated during the previous action.

  * `String getPrompt()`: Retrieves the message of a JavaScript question prompt dialog generated during the previous action.

  * `String getLocation()`: Gets the absolute URL of the current page.

  * `String getTitle()`: Gets the title of the current page.

  * `String[] getAllButtons()`: Returns the IDs of all buttons on the page.

  * `String[] getAllLinks()`: Returns the IDs of all links on the page.

  * `String[] getAllFields()`: Returns the IDs of all input fields on the page.

  * `String[] getAllWindowIds()`: Returns the IDs of all windows that the browser knows about.

  * `String[] getAllWindowNames()`: Returns the names of all windows that the browser knows about.

  * `String[] getAllWindowTitles()`: Returns the titles of all windows that the browser knows about.

## Test Support DSLs ##

Tellurium defined a set of DSLs to support Tellurium tests. The most often used ones include

  * openUrl(String url): establish a new connection to Selenium server for the given url. The DSL format is:

```
    openUrl url
```

> Example:

```
    openUrl "http://code.google.com/p/aost/"
```

  * connectUrl(String url): use existing connect for the given url. The DSL format is:

```
    connectUrl url
```

> Example:

```
    connectUrl "http://code.google.com/p/aost/" 
```

# Tellurium UI Objects #

Tellurium provides a set of predefined Ui objects, which users can use directly. Here we describe them one by one in details.

## Basic UI Object ##

The basic UI object is an abstract class and users cannot instantiate it directly. The basic UI Object works as the base class for all Ui objects and it includes the following attributes:

  1. uid: UI object's identifier
  1. namespace: used for XHTML
  1. locator: the locator of the UI object, could be a base locator or a composite locator
  1. respond: the JavaScript events the UI object can respond to. The value is a list.

and the base Ui object also provides the following methods:

  * boolean isElementPresent()
  * boolean isVisible()
  * boolean isDisabled()
  * waitForElementPresent(int timeout), where the time unit is ms.
  * waitForElementPresent(int timeout, int step)
  * String getText()
  * getAttribute(String attribute)

Obviously, all UI Objects will inherit the above attributes and methods. But be aware that you usually do not call these methods directly and you should use DSL syntax instead. For example, use

```
  click "GoogleStartPage.GoogleSearch"
```

In this way, Tellurium will first map the UiID ""GoogleStartPage.GoogleSearch" to the actual UI object and then call the _click_ method on it. If that Ui object does not have the _click_ method defined, you will get an error.

## UI Object Default Attributes ##

Tellurium UI objects have some default attributes as shown in the following table:

| **Tellurium Object** | **Locator Default Attributes** | **Extra Attributes** | **UI Template** |
|:---------------------|:-------------------------------|:---------------------|:----------------|
| Button               | tag: "input"                   |                      | no              |
| Container            |                                | group                | no              |
| CheckBox             | tag: "input", type: "checkbox" |                      | no              |
| Div                  | tag: "div"                     |                      | no              |
| Form                 | tag: "form"                    | group                | no              |
| Image                | tag: "img"                     |                      | no              |
| InputBox             | tag: "input"                   |                      | no              |
| RadioButton          | tag: "input", type: "radio"    |                      | no              |
| Selector             | tag: "select"                  |                      | no              |
| Span                 | tag: "span"                    |                      | no              |
| SubmitButton         | tag: "input", type: "submit"   |                      | no              |
| UrlLink              | tag: "a"                       |                      | no              |
| List                 |                                | separator            | yes             |
| Table                | tag: "table"                   | group, header        | yes             |
| StandardTable        | tag: "table"                   | group, header, footer | yes             |
| Frame                |                                | group, id, name, title | no              |
| Window               |                                | group, id, name, title | no              |



## Tellurium UI Object List ##

### Button ###

Button represents various Buttons on the web and its default tag is "input". The following methods can be applied to Button:

  * click()
  * doubleClick()
  * clickAt(String coordination)

Example:

```
Button(uid: "searchButton", clocator: [value: "Search", name: "btn"])
```

### Submit Button ###

SubmitButton is a special Button with its type being "submit".

Example:

```
SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
```

### Check Box ###

The CheckBox on the web is abstracted as "CheckBox" Ui object. The default tag for CheckBox is "input" and its type is "checkbox". CheckBox comes with the following methods:

  * check()
  * boolean isChecked()
  * uncheck()
  * String getValue()

Example:

```
CheckBox(uid: "autoRenewal", clocator: [dojoattachpoint: 'dap_auto_renew'])
```

### Div ###

Div is often used in the Dojo framework and it can represent a lot objects. Obviously, its tag is "div" and it has the following method:

  * click()

Example:


```
Div(uid: "dialog", clocator: [class: 'dojoDialog', id: 'loginDialog'])
```

### Image ###

Image is used to abstract the "img" tag and it comes with the following additional methods:

  * getImageSource()
  * getImageAlt()
  * String getImageTitle()

Example:

```
Image(uid: "dropDownArrow", clocator: [src: 'drop_down_arrow.gif'])
```

### Icon ###

Icon is similar to the Image object, but user can perform actions on it. As a result, it can have the following additional methods:

  * click()
  * doubleClick()
  * clickAt(String coordination)

Example:

```
Icon(uid: "taskIcon", clocator:[tag: "p", dojoonclick: 'doClick', img: "Show_icon.gif"] )
```

### Radio Button ###

RadioButton is the abstract object for the Radio Button Ui. As a result, its default tag is "input" and its type is "radio". RadioButton has the following additional methods:

  * check()
  * boolean isChecked()
  * uncheck()
  * String getValue()

Example:

```
RadioButton(uid: "autoRenewal", clocator: [dojoattachpoint: 'dap_auto_renew'])
```

### Text Box ###

TextBox is the abstract Ui object from which you can get back the text, i.e., it comes with the method:

  * String waitForText(int timeout)

Note, TextBox can have various types of tags.

Example:

```
TextBox(uid: "searchLabel", clocator: [tag: "span"])
```

### Input Box ###

InputBox is the Ui where user types in input data. As its name stands, InputBox's default tag is "input". InputBox has the following additional methods:

  * type(String input)
  * keyType(String input), used to simulate keyboard typing
  * typeAndReturn(String input)
  * clearText()
  * boolean isEditable()
  * String getValue()

Example:

```
InputBox(uid: "searchBox", clocator: [name: "q"])
```

### Url Link ###

UrlLink stands for the web url link, i.e., its tag is "a". UrlLink has the following additional methods:

  * String getLink()
  * click()
  * doubleClick()
  * clickAt(String coordination)

Example:

```
UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
```

### Selector ###

Selector represents the Ui with tag "select" and user can select from a set of options. Selector has a lot of methods, such as:

  * selectByLabel(String target)
  * selectByValue(String value)
  * addSelectionByLabel(String target)
  * addSelectionByValue(String value)
  * removeSelectionByLabel(String target)
  * removeSelectionByValue(String value)
  * removeAllSelections()
  * String[.md](.md) getSelectOptions()
  * String[.md](.md) getSelectedLabels()
  * String getSelectedLabel()
  * String[.md](.md) getSelectedValues()
  * String getSelectedValue()
  * String[.md](.md) getSelectedIndexes()
  * String getSelectedIndex()
  * String[.md](.md) getSelectedIds()
  * String getSelectedId()
  * boolean isSomethingSelected()

Example:

```
Selector(uid: "issueType", clocator: [name: "can", id: "can"])
```

### Container ###

Container is an abstract object that can hold a collection of Ui objects. As a result, the  Container has a special attribute "useGroupInfo" and its default value is false. If this attribute is true, the Group Locating is enabled. But make sure all the Ui objects inside the Container are children nodes of the Container in the DOM, otherwise, you should not use the Group Locating capability.

Example:

```
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
    InputBox(uid: "searchbox", clocator: [title: "Google Search"])
    SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
    SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

### Form ###

Form is a type of Container with its tag being "form" and it represents web form. Like Container, it has the capability to use Group Locating and it has a special method:

  * submit()

This method is useful and can be used to submit input data if the form does not have a submit button.

Example,

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
    Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
    TextBox(uid: "searchLabel", clocator: [tag: "span"])

    InputBox(uid: "searchBox", clocator: [name: "q"])
    SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

### Table ###

Table is one of the most complicated Ui Object and also the most often used one. Obviously, its tag is "table" and a table could have headers besides rows and columns. Table is a good choice for data grid. Tellurium can handle its header, rows, and columns automatically for users. One important is the Table has different UiID than other Ui objects. For example, if the id of the table is "table1", then its i-th row and j-th column is referred as `"table1[i][j]"` and its m-th header is `"table1.header[m]"`.

Another distinguished feature of Table is that you can define Ui template for its elements. For example, the following example defines different table headers and the template for the first column, the element on the second row and the second column, and the template for all the other elements in other rows and columns.

```
ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], 
         group: "true")
{
    //define table header
    //for the border column
    TextBox(uid: "header: 1", clocator: [:])
    UrlLink(uid: "header: 2", clocator: [text: "%%Filename"])
    UrlLink(uid: "header: 3", clocator: [text: "%%Summary + Labels"])
    UrlLink(uid: "header: 4", clocator: [text: "%%Uploaded"])
    UrlLink(uid: "header: 5", clocator: [text: "%%Size"])
    UrlLink(uid: "header: 6", clocator: [text: "%%DownloadCount"])
    UrlLink(uid: "header: 7", clocator: [text: "%%..."])

    //define Ui object for the second row and the second column
    InputBox(uid: "row: 2, colum: 2" clocator: [:])
    //define table elements
    //for the border column
    TextBox(uid: "row: *, column: 1", clocator: [:])
    //For the rest, just UrlLink
    UrlLink(uid: "all", clocator: [:])
}
```

Be aware, the templates inside the Table follow the name convention:

  * For the i-th row, j-th column, the uid should be "row: i, column: j"
  * The wild case for row or column is `"*"`
  * "all" stands for matching all rows and columns

As a result, `"row : *, column : 3"` refers to the 3rd column for all rows. Once the templates are defined for the table, Tellurium uses a special way to find a matching for a Ui element `"table[i][j]"` in the table. i.e., the following rules apply,

  * First, Tellurium tries to find the template defined for the i-th row, j-th column.
  * If not found, Tellurium tries to search for a general template `"row: *, column: j"`, i.e., the template for column j.
  * If not found, Tellurium tries to search for another general template `"row: i, column: *"`, i.e., the template for row i.
  * If not found either, Tellurium tries to find the template matching all rows and columns.
  * If still out of luck, Tellurium will use a TextBox as the default element for this element.

Generally speaking, Tellurium always searches for the special case first, then more general case, and until the all matching case. In this way, user can define very flexible templates for tables.

Table is a type of Container and thus, it can use the Group Locating feature. Table has the following special methods:

  * boolean hasHeader()
  * int getTableHeaderColumnNum()
  * int getTableMaxRowNum()
  * int getTableMaxColumnNum()

From Tellurium 0.6.0, you can also specify the tbody attribute for the Table object and this may be helpful if you have multiple tbody elements inside a single table tab. For example, you can specify tbody as follows,

```
Container(uid: "tables", clocator:[:]){
     Table(uid: "first", clocator: [id: "someId", tbody: [position: "1"]]){
          ......
      }
     Table(uid: "second", clocator: [id: "someId", tbody: [position: "2"]]){
          ......
      }

      ...

} 

```

### Standard Table ###

A StandardTable is a table in the following format

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

For a StandardTable, you can specify UI templates for different tbodies. Apart from the methods in Table, it has the following additional methods:

  * int getTableFootColumnNum()
  * int getTableMaxTbodyNum()
  * int getTableMaxRowNumForTbody(int tbody\_index)
  * int getTableMaxColumnNumForTbody(int body\_index)

Example:

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

### List ###

List is also a Container type abstract Ui object and it can be used to represent any list like Ui objects. Very much like Table, users can define Ui templates for List and following rule of "the special case first and then the general case". The index number is  used to specify an element and "all" is used to match all elements in the List. List also uses TextBox as the default Ui if no template could be found. Since List is a Container type, it can use the Group Locating feature.

List has one special attribute "separator", which is used to indicate the tag used to separate different List UI elements. If "separator" is not specified or "", all UI elements must be under the same DOM node, i.e., they are siblings.

Example:

```

List(uid: "subcategory", locator: "", separator: "p"){
    InputBox(uid: "2", clocator: [title: "Google Search"])
    UrlLink(uid: "all", clocator: [:])
}
```

### Simple Menu ###

The SimpleMenu represent a menu without a header and only contains menu items. The default tag is "div" and user should specify the alias name for each menu item. For example,

```
//items is a map in the format of "alias name" : menu_item
ui.SimpleMenu(uid: "IdMenu", clocator:[class: "popup", id: "pop_0"],
    items: ["SortUp":"Sort Up", "SortDown":"Sort Down", "HideColumn":"Hide Column"])
```

The above menu specified the menu item "Sort up", "Sort Down", and "Hiden Column" with their alias names. Users should use the alias name to refer the menu item, for instance, "IdMenu.SortUp".

The SimpleMenu has the following methods:

  * click()
  * mouseOve()
  * mouseOut()

### Select Menu ###

SelectMenu is designed for the selecting column menu on the Tellurium Issues page and it is prototyped to demonstrate how to write Ui object with interaction with the DOM since the Ui elements have different patterns at runtime, hence, it is not a general purpose Ui object. SelectMenu could have a header and its menu item content could keep changing when users select different columns to display.

The SelectMenu on the Tellurium issues page is expressed as follows,

```
ui.SelectMenu(uid: "selectColumnMenu", clocator:[class: "popup",id: "pop__dot"], 
    title: "Show columns:", items: ["ID":"ID", "Type":"Type", "Status":"Status",
    "Priority":"Priority", "Milestone":"Milestone", "Owner":"Owner", 
    "Summary":"Summary", "Stars":"Stars", "Opened":"Opened", "Closed":"Closed",
    "Modified":"Modified", "EditColumn":"Edit Column Spec..." ])
```

Like SimpleMenu, SelectMenu also has the following methods:

  * click()
  * mouseOve()
  * mouseOut()

### Frame ###

Frame is a type of Container and is used to mode Frame or IFrame. It includes the
following attributes:
  * id
  * name
  * title

and the following methods

  * selectParentFrame()
  * selectTopFrame()
  * selectFrame(locator)
  * getWhetherThisFrameMatchFrameExpression(currentFrameString, target)
  * waitForFrameToLoad(frameAddress, timeout)

When you test website with IFrames, you should use multiple window mode, i.e., set the option useMultiWindows to be true in TelluriumConfig.groovy.

Example,

```
ui.Frame(uid: "SubscribeFrame", name: "subscrbe"){
   Form(uid: "LoginForm", clocator: [name: "loginForm"]){
      InputBox(uid: "UserName", clocator: [id: "username", type: "text"])
      InputBox(uid: "Password", clocator: [id: "password", type: "password"])
      Button(uid: "Login", clocator: [type: "image", class: "login"])
      CheckBox(uid: "RememberMe", clocator: [id: "rememberme"])
   }
} 
```

### Window ###

Window is a type of Container and is used to mode Popup Window. It includes the
following attributes:
  * id
  * name
  * title

and the following methods

  * openWindow(String UID, String url)
  * selectWindow(String UID)
  * closeWindow(String UID)
  * boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target)
  * waitForPopup(String UID, int timeout)

Example,

```
ui.Window(uid: "HelpWindow", name: "HelpWindow"){
...
}

openWindow helpUrl, "HelpWindow"
waitForPopUp "HelpWindow", 2000
selectWindow "HelpWindow" 
...
selectMainWindow()
```

### Option ###

Option is also designed to be adaptive the dynamic web. Option is a pure abstract object and it holds multiple UIs with each representing a possible UI pattern at runtime. For example, the List/Grid selector on the issue page can described as:

```
//The selector to choose the data grid layout as List or Grid
ui.Option(uid: "layoutSelector"){
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
        TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"])
        UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
    }
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
        UrlLink(uid: "List", clocator: [text: "List", direct: "true"])
        TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"])
    }
}
```

Note, the option's uid must be the same as the next UI objects it represent and in this way, you do not need to include option's uid in the UiID. For example,  you can just use

```
click "layoutSelector.List"
```

instead of

```
click "layoutSelector.layoutSelector.List"
```

The option object will automatically detect which UI pattern you need to use at runtime.


# Tellurium Subprojects #

Tellurium began as a small core project over a year ago and quickly spawned multiple sub-projects including: [Core](http://code.google.com/p/aost/wiki/UserGuide), [Reference projects](http://code.google.com/p/aost/wiki/ReferenceProjectGuide), [Widget extensions](http://code.google.com/p/aost/wiki/TelluriumWidget), [Trump](http://code.google.com/p/aost/wiki/TrUMP), and Engine projects as shown in the following diagram:

http://tellurium-users.googlegroups.com/web/TelluriumSubprojects.png?gda=KOo7BEoAAAD5mhXrH3CK0rVx4StVj0LYlNqOmuvSTE_gHzistV7TYU3GrA4woUylGAUAJF3_rdDbYV_6pXuFBwitfyYTkV3q_e3Wg0GnqfdKOwDqUih1tA&gsc=JDDH5gsAAACcJEKey8RXDXypkrHyWRpX

  * Tellurium Engine: Based on Selenium Core with UI module, jQuery selectors, command bundle, and exception hierarchy support.
  * Tellurium Core: UI module, APIs, DSL, Object to Runtime Locator mapping, and test support.
  * Tellurium Extensions: Dojo Javascript widgets and ExtJS Javascript widgets.
  * Tellurium UI Module Plugin (Trump): A Firefox plugin to automatically generate the UI module after users select the UI elements from the web under testing.
  * Tellurium Maven Archetypes: Maven archetypes to generate skeleton Tellurium JUnit and Tellurium TestNG projects using one Maven command.
  * Tellurium Reference Projects: Use Tellurium project site as examples to illustrate how to use different features in Tellurium and how to create Tellurium test cases.

## Tellurium Engine ##

Up to Tellurium 0.6.0, Tellurium still leveraged Selenium core as the test driving engine. Selenium has a rich set of APIs to act on individual UI elements, and Tellurium can use them directly. Tellurium embedded its own small engine in Selenium core to support the following enhancements:

  * jQuery selector support by adding a new jQuery selector locate strategy so that the Selenium core can handle the jQuery selectors passed in from Tellurium core.
  * jQuery selector caching to increase the reuse of previously located DOM references and reduce the UI element locating time.
  * Bulk data retrieval. For example, the following method will get back all data in a table with just one method call, which improves the speed dramatically:

```
        String getAllTableCellText(String uid) 
```

  * New APIs for partial matching of attributes, CSS query, and more.

Using Selenium core as the test driving engine is a quick solution for Tellurium, but it suffers some drawbacks such as low efficiency because Selenium core does not have built-in support for UI modules. We are working on our new engine to gradually replace Selenium core in order to achieve the following goals:

  * Command bundle to reduce round-trip time between Tellurium core and the Tellurium Engine.
  * UI module caching at the Engine side to reuse the discovered locator in the UI module no matter if the locator is XPath or a jQuery selector.
  * Group locating at the Engine side to exploit more information about the UI elements in the UI module to help locate UI elements
  * Exception hierarchy so that the Engine returns meaningful error codes back to the Tellurium core.
  * Ajax response notification.

## Tellurium Core ##

The Tellurium Core does **Object to Locator Mapping** (OLM) automatically at runtime so that you can define UI objects simply by their attributes, i.e., **Composite Locators** denoted by the "clocator". Furthermore, Tellurium Core uses the **Group Locating Concept** (GLC) to exploit information inside a collection of UI components to help finding their locators. The Tellurium Core defines a new **Domain Specific Language** (DSL) for web testing. One very powerful feature of Tellurium Core is that you can use **UI templates** to represent many identical UI elements or dynamic size of different UI elements at runtime, which are extremely useful to test dynamic web such as a data grid. One typical data grid example is as follows,

```
ui.Table(uid: "table", clocator: [:]){
   InputBox(uid: "row: 1, column: 1", clocator: [:])
   Selector(uid: "row: *, column: 2", clocator: [:])
   UrlLink(uid: "row: 3, column: *", clocator: [:])
   TextBox(uid: "all", clocator: [:])
} 
```

[Data Driven Testing](http://code.google.com/p/aost/wiki/DataDrivenTesting) is another important feature of Tellurium Core. You can define data format in an expressive way. In you data file, you can specify which test you want to run, the input parameters, and expected results. Tellurium automatically binds the input data to variables defined in your test script and run the tests you specified in the input file. The test results will be recorded by a test listener and output in different formats, for example, an XML file.

The Tellurium Core is written in Groovy and Java. The test cases can be written in Java, Groovy, or pure DSL. You do not really need to know Groovy before you use it because the UI module definition and actions on UIs are written in DSLs and the rest could be written in Java syntax.

## Tellurium Extensions ##

Tellurium Widget is a good way to re-use UI components in testing. Tellurium provides you the capability to composite UI objects into a widget object and then you can use the widget directly just like using a tellurium UI object. The advantage is that you do not need to deal with the UI at the link or button level for the widget, you just work on the high level methods. Another advantage is that this widget is reusable.

Usually, Java script frameworks provide a lot of widgets. Take the Dojo framework as an example. We use the widget DatePicker to prototype the tellurium widget. For widgets, it is important to include name space to avoid name collision between different widget modules. For example, what is Dojo and ExtJs both have the widget Date Picker? After add the name space, the widget is named as "DOJO\_DatePicker".

The DataPicker widget is defined like a regular Tellurium UI module,

```
class DatePicker extends DojoWidget{

    public void defineWidget() {
        ui.Container(uid: "DatePicker", locator: "/div[@class='datePickerContainer' 
                     and child::table[@class='calendarContainer']]"){
            Container(uid: "Title", locator: "/table[@class='calendarContainer']/thead
                     /tr/td[@class='monthWrapper']/table[@class='monthContainer']/tbody
                     /tr/td[@class='monthLabelContainer']"){
                Icon(uid: "increaseWeek", locator:
                                        "/span[@dojoattachpoint='increaseWeekNode']")
                Icon(uid: "increaseMonth", locator:
                                        "/span[@dojoattachpoint='increaseMonthNode']")
                Icon(uid: "decreaseWeek", locator:
                                        "/span[@dojoattachpoint='decreaseWeekNode']")
                Icon(uid: "decreaseMonth", locator:
                                        "/span[@dojoattachpoint='decreaseMonthNode']")
                TextBox(uid: "monthLabel", locator:
                                        "/span[@dojoattachpoint='monthLabelNode']")
            }
            StandardTable(uid: "calendar", locator: "/table[@class='calendarContainer']
                          /tbody/tr/td/table[@class='calendarBodyContainer']"){
                TextBox(uid: "header: all", locator: "")
                ClickableUi(uid: "all", locator: "")
            }
            Container(uid: "year", locator: "/table[@class='calendarContainer']/tfoot
             /tr/td/table[@class='yearContainer']/tbody/tr/td/h3[@class='yearLabel']"){
                Span(uid: "prevYear", locator: "/span[@class='previousYear' and
                     @dojoattachpoint='previousYearLabelNode']")
                TextBox(uid: "currentYear", locator: "/span[@class='selectedYear' and
                     @dojoattachpoint='currentYearLabelNode']")
                Span(uid: "nextYear", locator: "/span[@class='nextYear' and
                     @dojoattachpoint='nextYearLabelNode']")
            }
        }
    }
}
```

Here we used the XPath directly for demo purpose only. Usually, you should use the composite locator instead.

Also, we defined the following widget methods:

```
    public String getCurrentYear(){
        return getText("DatePicker.year.currentYear")
    }

    public void selectPrevYear(){
        click "DatePicker.year.prevYear"
    }
```

The widget is treated as a tellurium UI object and the builder is the same as regular tellurium objects

```
class DatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)
        datepicker.defineWidget()

        return datepicker
    }
}
```

Now, we need to hook the widget into the Tellurium Core. Each widget module will be compiled as a separate jar file and it should define a bootstrap class to register all the widgets inside the module. By default, the full class name of the bootstrap class is `org.tellurium.widget.XXXX.Init`, where the class Init should implement the `WidgetBootstrap` interface to register widgets and _XXXX_ stands for the widget module name. It is DOJO in our case.

```
class Init implements WidgetBootstrap{
    public void loadWidget(UiObjectBuilderRegistry uiObjectBuilderRegistry) {
        if(uiObjectBuilderRegistry != null){
           uiObjectBuilderRegistry.registerBuilder(getFullName("DatePicker"), 
                                        new DatePickerBuilder())      
        }
    }
}
```

Then in the tellurium configuration file TelluriumConfig.groovy, you should include your module name there,

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="dojo"
        }
    }
```

If you use your own package name for the bootstrap class, for example, com.mycompay.widget.Boot, then you should specify the full name there like

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="com.mycompay.widget.Boot"
        }
    }
```

Note, you can load multiple widget modules into the Tellurium Core framework by define

```
  included="dojo, com.mycompay.widget.Boot"
```

To use widget, you can treat a Widget as a regular tellurium UI object. For example,

```
class DatePickerDemo extends DslContext{
    
    public void defineUi() {
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
            Image(uid: "selectDate", clocator: [title: "select a date", 
                  dojoattachpoint: "containerDropdownNode", alt: "date"])
            DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", 
                            dojoattachpoint: "subWidgetContainerNode"])
        }
    }
}
```

Then on the module file DatePickerDemo, you can call the widget methods instead of dealing with low level links, buttons, and so on.

To make the testing more expressive, Tellurium provides an onWidget method

```
  onWidget(String uid, String method, Object[] args)
```

In that way, we can call the widget methods as follows:

```
onWidget "dropdown.datePicker", selectPrevYear
```

## Tellurium UI Module Plugin (Trump) ##

Go to the Tellurium project download page and download the TrUMP xpi file or you can download the Firefox 3 version directly from Firefox addons site at

https://addons.mozilla.org/en-US/firefox/addon/11035

The Tellurium UI Module Plugin (Trump) is used to automatically generate UI modules for users. The Trump IDE looks as follows:

http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png?gda=EVgeNEMAAAD5mhXrH3CK0rVx4StVj0LYBWucT0-vkPMbIT-o-BeAq7267uE5ld_gRd1XTkHcEWoytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg

The workflow of Trump is shown here:


http://tellurium-users.googlegroups.com/web/TrUMPDiagramSmall.png?gda=9CFDmEcAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq8nAtNENvOA6NOHkUCAqlOUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

  1. First, the user clicks on a web page, and the corresponding UI element is pushed into an array. If the user clicks the element again, the UI element is removed from the array.
  1. When the user clicks on the "Generate" button, Trump does the following two steps. First, Trump generates an internal tree to represent the UI elements using a grouping algorithm. During the tree generating procedure, extra nodes are generated to group UI elements together based on their corresponding location on the DOM tree. The internal tree is very useful and holds all original data that can be used for customization. Once the internal tree is built, Trump starts the second step, which is to build the default UI module. For each node in the internal tree, Trump generates a UI object based on its tag and whether or not it is a parent node.
  1. When the user clicks on the "Customize" button, Trump will pull out the original data held in the internal tree and the current attributes utilized by the UI module to create the "Customize" view. When the user clicks on an element, Trump lists all available optional attributes in the view for users to customize.
  1. Trump will try to validate the UI module automatically whenever you generate a new UI module or update it. Trump will evaluate each UI element's XPath the same way that Tellurium generates the runtime XPath from the UI module and check if the generated runtime XPath is unique in the current web page. If it is not unique, you will see a red "X" mark, and you need to modify the element's attribute to make it disappear. If you do not see a red "X", you are finished. You can export the generated UI module to a groovy file and start to write Tellurium tests based on the generated UI module.

To use Trump, select "Tools" > "TrUMP IDE" in Firefox. The "Record" button is on by default, you can click on "Stop" to stop recording. Now, you can start to use the TrUMP IDE to record whatever UI elements you click on the WEB. For example, you can open Tellurium Download page and click on the search elements and the three links,

http://tellurium-users.googlegroups.com/web/TrUMPRecordSmall.png?gda=7n7qpkYAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-zOJScwBCYlcwvHeRiAw313riz0RlMs_1ov_iNdB7P8E-Ea7GxYMt0t6nY0uV5FIQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

The blue color indicates selected element, you can click on the selected element again to un-select it. Then, you can click on the "Generate" button to create the Tellurium UI Module and you will be automatically directed to the "Source" window,

http://tellurium-users.googlegroups.com/web/TrUMPGenerateSmall.png?gda=1P97bkgAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8exKkp42KQN7AGtkLZRcnsBYlzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

After that, you can click on the "Customize" button to change the UI module such as UIDs, group locating option, and attributes you selected for the UI module.

http://tellurium-users.googlegroups.com/web/TrUMPCustomizeSmall.png?gda=W0yA4EkAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-dpsLnqVTCuG8a16FLLGJva4bZDq9fZORtBwZjSHGJkhAioEG5q2hncZWbpWmJ7IQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

You can see there is one red "X" mark, which indicates that UI element's XPath is not unique, you could select group, or add more attributes to the UI element. You will see
the new customized UI as follows,

http://tellurium-users.googlegroups.com/web/TrUMPCustomizedSmall.png?gda=AUZMQkoAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-uLN10ihJ-PJyev33cmPNkLFhim5d1LJsoAMKqscxTI_e3Wg0GnqfdKOwDqUih1tA&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

Note that the red "X" mark is removed because we turn on the group locating and the element's xpath is unique now. In the meanwhile, the UI module in the source tab will be automated updated once you click the "Save" button. The "Show" button will show the actual Web element on the web that the UI element is represented for.

http://tellurium-users.googlegroups.com/web/TrUMPCustomizedSourceSmall.png?gda=i_g_1lAAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8ewl-ge40lZM5QmFCqlPrr96JFTmvmvXK4kDlwX5DWO5WbcVT3VtYGKLco-_l-8AzjQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

At this point, you export the UI module to a groovy file. Be aware that if you see any error complaining about the directory, you should first check the "export directory" in Options > Settings and set it to "C:\" or other windows directory for Windows system before you export the file. For Linux, you may find there is no "OK" buton on the option tab, which is caused by the fact that the configure "browser.preferences.instantApply" is set to true by default. You can point your firefox to "about:config" and change the option to false. After that, you will see the "OK" button.

http://tellurium-users.googlegroups.com/web/ExportToGroovySmall.png?gda=HTfW2kkAAAD5mhXrH3CK0rVx4StVj0LYjifwtTdV1ss7lVmTlgK0W813v4RlmnSWza84shDEVE3a4bZDq9fZORtBwZjSHGJkhAioEG5q2hncZWbpWmJ7IQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

Open up the groovy file, you will see the file as follows,

```
package tellurium.ui

import org.tellurium.dsl.DslContext

/**
 *  This UI module file is automatically generated by TrUMP 0.1.0.
 * 
*/

class NewUiModule extends DslContext{

  public void defineUi() {
    ui.Container(uid: "Tellurium", clocator: [tag: "body", class: "t2"]){
      Form(uid: "Form", clocator: [tag: "form", method: "get", action: "list"], 
           group: "true")
      {
	Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
	InputBox(uid: "SearchBox", clocator: [tag: "input", type: "text", name: "q", 
                                             id: "q"])
	SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", 
                                               value: "Search"])
      }
      Container(uid: "Title", clocator: [tag: "table", id: "mt"]){
	UrlLink(uid: "Issues", clocator: [tag: "a", text: "Issues"], respond: ["click"])
	UrlLink(uid: "Wiki", clocator: [tag: "a", text: "Wiki"], respond: ["click"])
	UrlLink(uid: "Downloads", clocator: [tag: "a", text: "Downloads"], 
                                      respond: ["click"])
      }
    }
  }

	//Add your methods here

}
```

## Tellurium Maven Archetypes ##

Tellurium provides [two maven archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes), i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively.

Run the following maven command to create a new JUnit project

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
             -DarchetypeArtifactId=tellurium-junit-archetype \
             -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

Without adding the Tellurium Maven repository, you can specify it in the command line as
```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
                     -DarchetypeArtifactId=tellurium-junit-archetype \
                     -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0 \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

For TestNG project, you should use a different archetype

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
                     -DarchetypeArtifactId=tellurium-testng-archetype \
                     -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0 \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

## Tellurium Reference Projects ##

We create [reference projects](http://code.google.com/p/aost/wiki/ReferenceProjectGuide) to demonstrate how to use Tellurium for your own testing project. In the reference projects, we use Tellurium project web site as an example to illustrate how to write real-world Tellurium tests. The reference projects only use tellurium jar files and there are two sub-projects at the time of writing

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

# How to Obtain and Use Tellurium #

## Create a Tellurium Project ##

There are three ways: use [the reference project](http://code.google.com/p/aost/wiki/ReferenceProjectGuide) as a base, use the [Tellurium Maven archetype](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes), or manually create a Tellurium project using the [tellurium jar](http://code.google.com/p/aost/downloads/list) and a [Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile). Alternatively, you could create your own Tellurium Maven project manually using [the sample POM file](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom).

http://tellurium-users.googlegroups.com/web/HowToUseTellurium.png?gda=E2fneEcAAACXZPxEX7Ki-M5C2JpeBoXXwOvr7XA0t7SnOHKVzf4DhFd6vDxrTQI8X2xdNkWs9mIVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=YyVqmwsAAABmHtz3tZj6NRBcOVGYgXTk

The easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetypes: tellurium-junit-archetype and tellurium-testng-archetype (for Tellurium JUnit test projects and Tellurium TestNG test projects respectively.) As a result, you can create a Tellurium project using one Maven command. For a Tellurium JUnit project, use:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
     -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium \
     -DarchetypeVersion=0.7.0-SNAPSHOT \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

and for a Tellurium TestNG project, use:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
     -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium \
     -DarchetypeVersion=0.7.0-SNAPSHOT \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

For an Ant user, you should download [Tellurium core 0.6.0 jar file](http://aost.googlecode.com/files/tellurium-core-0.6.0.jar) from [Tellurium project download page](http://code.google.com/p/aost/downloads/list), [the Tellurium dependency file](http://aost.googlecode.com/files/tellurium-0.6.0-dependencies.zip), and [the Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile). Unpack the Tellurium dependency file to your project /lib directory together with the Tellurium core 0.6.0 jar file. Name the Tellurium configuration file as TelluriumConfig.groovy and put it at your project root directory.

For Ant build scripts, please see [the sample Tellurium Ant build scripts](http://code.google.com/p/aost/wiki/TelluriumSampleAntBuildScript)

## Setup Tellurium Project in IDEs ##

Tellurium Project can be run in IntelliJ, NetBeans, Eclipse, or other IDEs which have Groovy support. If you use Maven, you can simply open the POM file to let the IDE automatically build the project files for you.

IntelliJ IDEA is commercial and you can download a free trial version for 30 days from http://www.jetbrains.com/idea/download/index.html. You can find a detailed guide on [How to create your own Tellurium testing project with IntelliJ 7.0](http://code.google.com/p/aost/wiki/CustomTelluriumIntelliJProject).

For NetBeans users, you can find detailed Guides on [the NetBeans Starters' guide page](http://code.google.com/p/aost/wiki/TelluriumStarterUsingNetBeans) and [How to create your own Tellurium testing project with NetBeans 6.5](http://code.google.com/p/aost/wiki/CustomTelluriumNetBeansProject).

For Eclipse users, you need to download Eclipse Groovy Plugin from http://dist.codehaus.org/groovy/distributions/update/ to run the Tellurium project. For detailed instructions, please read [How to create your own Tellurium testing project with Eclipse](http://code.google.com/p/aost/wiki/CustomTelluriumEclipseProject).

## Create a UI Module ##

Tellurium provides Trump for you to automatically create UI modules. Trump can be downloaded from the Tellurium project site:

http://code.google.com/p/aost/downloads/list

Choose the Firefox 2 or Firefox 3 version depending on your Firefox version, or you can download the Firefox 3 version directly from the Firefox addons site at:

https://addons.mozilla.org/en-US/firefox/addon/11035

Once you install it and restart Firefox, you are ready to record your UI modules by simply clicking on the UI element on the web and then clicking the "generate" button. You may like to customize your UI a bit by clicking the "Customize" button. More detail:

In our example, we open up Tellurium download page

http://code.google.com/p/aost/downloads/list

and record the download search module as follows:

http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPageSmall.png?gda=aeAak1IAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq73MhpfIdLRuVAwbLjuTZtQ7YQyGo5rEgT7iH53cuUInVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

After we customize the UI module, we export it as the module file NewUiModule.groovy to the demo project and add a couple of methods to the class:

```
class NewUiModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], 
           group: "true") 
{
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

## Create Tellurium Test Cases ##

Once you create the UI module, you can create a new Tellurium test case NewTestCase by extending TelluriumJavaTestCase class.

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

# Examples #

## Custom UI Object ##

Tellurium supports custom UI objects defined by users. For most UI objects, they must extend the "UiObject" class and then define actions or methods they support. For container type UI objects, i.e., which hold other UI objects, they should extends the Container class. Please see Tellurium Table and List objects for more details.

In the tellurium-junit-java project, we defined the custom UI object "SelectMenu" as follows:

```
class SelectMenu extends UiObject{   
    public static final String TAG = "div"

    String header = null

    //map to hold the alias name for the menu item in the format of 
    //"alias name" : "menu item"
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

## UI Module ##

Take the [Tellurium issue page](http://code.google.com/p/aost/issues/list) as an example, the UI module can be defined as

```
public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }

    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], 
             group: "true") 
{
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "*ID"])
      UrlLink(uid: "header: 3", clocator: [text: "*Type"])
      UrlLink(uid: "header: 4", clocator: [text: "*Status"])
      UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "*..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }

    ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "false",
             clocator: [id: "resultstable", class: "results"], group: "true") {
      //define table header
      //for the border column
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "*ID"])
      UrlLink(uid: "header: 3", clocator: [text: "*Type"])
      UrlLink(uid: "header: 4", clocator: [text: "*Status"])
      UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "*..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }

  }

  public List<String> getDataForColumn(int column){
        int nrow = getTableMaxRowNum("issueResult")
        if(nrow > 20) nrow = 20
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
  }

  public List<String> getDataForColumnWithCache(int column){
        int nrow = getTableMaxRowNum("issueResultWithCache")
        if(nrow > 20) nrow = 20
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResultWithCache[${i}][${column}]"))
        }

        return lst
  }

  public String[] getAllText(){
    return getAllTableCellText("issueResult")
  }

  public int getTableCellCount(){
    String sel = getSelector("issueResult")
    sel = sel + " > tbody > tr > td"

    return getJQuerySelectorCount(sel)
  }

  public String[] getTableCSS(String name){
    String[] result = getCSS("issueResult.header[1]", name)

    return result
  }

  public String[] getIsssueTypes() {
    return getSelectOptions("issueSearch.issueType")
  }

  public void selectIssueType(String type) {
    selectByLabel "issueSearch.issueType", type
  }

  public void searchIssue(String issue) {
    keyType "issueSearch.searchBox", issue
    click "issueSearch.searchButton"
    waitForPageToLoad 30000
  }

  public boolean checkamICacheable(String uid){
    UiObject obj = getUiElement(uid)
    boolean cacheable = obj.amICacheable()
    return cacheable
  }
}
```

## Groovy Test Case ##

```
public class TelluriumIssueGroovyTestCase extends GroovyTestCase{

  private TelluriumIssueModule tisp;

  public void setUp() {
    tisp = new TelluriumIssueModule();
    tisp.defineUi();
  }

  public void tearDown() {
  }

  public void testDumpWithXPath() {
    tisp.disableJQuerySelector();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelector() {
    tisp.useJQuerySelector();
    tisp.exploreSelectorCache = false;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelectorCacheEnabled() {
    tisp.useJQuerySelector();
    tisp.exploreSelectorCache = true;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }
}
```

## Java Test Case ##

```
public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModule tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssueModule();
        tisp.defineUi();
        tisp.useJQuerySelector();
        tisp.enableSelectorCache();
        tisp.setCacheMaxSize(30);
    }

    @Before
    public void setUp(){
        connectUrl("http://code.google.com/p/aost/issues/list");
    }

    @Test
    public void testSearchIssueTypes(){
        String[] ists = tisp.getIsssueTypes();
        tisp.selectIssueType(ists[2]);
        tisp.searchIssue("Alter");
    }

    @Test
    public void testAllIssues(){
        String[] details = tisp.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }

    @Test
    public void testGetCellCount(){
        int count = tisp.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
        String[] details = tisp.getAllText();
        assertNotNull(details);
        assertEquals(details.length, count);
    }

    @Test
    public void testCSS(){
        tisp.disableSelectorCache();
        String[] css = tisp.getTableCSS("font-size");
        assertNotNull(css);
    }
    
    @Test
    public void checkCacheable(){
        boolean result = tisp.checkamICacheable("issueResult[1][1]");
        assertTrue(result);
        result = tisp.checkamICacheable("issueResult");
        assertTrue(result);
    }

    @Test
    public void testGetDataForColumn(){
        long beforeTime = System.currentTimeMillis();
        tisp.getDataForColumn(3);
        long endTime = System.currentTimeMillis();
        System.out.println("Time noCacheForChildren " + (endTime-beforeTime) + "ms");
        beforeTime = System.currentTimeMillis();
        tisp.getDataForColumnWithCache(3);
        endTime = System.currentTimeMillis();
        System.out.println("Time with all cache " + (endTime-beforeTime) + "ms");
    }

    @Test
    public void testCachePolicy(){
        tisp.setCacheMaxSize(2);
        tisp.useDiscardNewCachePolicy();
        tisp.searchIssue("Alter");
        tisp.getTableCSS("font-size");
        tisp.getIsssueTypes();
        tisp.searchIssue("Alter");
        tisp.setCacheMaxSize(30);
    }

    @After
    public void showCacheUsage(){
        int size = tisp.getCacheSize();
        int maxSize = tisp.getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: ");
        Map<String, Long> usages = tisp.getCacheUsage();
        Set<String> keys = usages.keySet();
        for(String key: keys){
            System.out.println("UID: " + key + ", Count: " + usages.get(key));
        }
    }
}
```

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
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and 
             use Maven to build multiple projects</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and not
             look for an IntelliJ project dependency</Message>
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
    <Message>Issue: Add Maven support for the new code base with multiple 
             sub-projects</Message>
    <Message>Issue: Cannot find symbol class Selenium</Message>
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and 
             use Maven to build multiple projects</Message>
    <Message>Issue: Need to add tar.gz file as artifact in Maven</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and 
             not look for an IntelliJ project dependency</Message>
    <Message>Issue: update versions for extensioin dojo-widget and TrUMP
             projects</Message>
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
    <Message>Issue: Create more examples to demonstrate the usage of different UI
             objects</Message>
    <Message>Issue: DslScriptEngine causes NullPointerException when attempt to 
             invoke server.runSeleniumServer() method</Message>
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
    <Message>Issue: Refactor Tellurium project web site examples using TestNG and 
             put them to the tellurium-testng-java sub-project</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project 
             for NetBeans</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project 
             for IntelliJ</Message>
    <Message>Issue: Table operations for large size one seems to be very slow</Message>
  </Test>
</TestResults>
```

## DSL Test Script ##

In Tellurium, you can create test scripts in pure DSL. Take TelluriumPage.dsl as an example,

```
//define Tellurium project menu
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"],
             group: "true")
{
    //since the actual text is  Project&nbsp;Home, we can use partial match here. 
    //Note "*" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "*Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the Tellurium project search module, which includes an input box
//and two search buttons
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

# Tellurium Support #

If you have any questions or problems with Tellurium, please join our [Tellurium user group](http://groups.google.com/group/tellurium-users) and then post them there. You will get the response very shortly.

# Resources #

## Tellurium Project ##

  * [Tellurium Project website](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium Automated Testing Framework LinkedIn Group](http://www.linkedin.com/groups?gid=1900807)
  * [Tellurium on Twitter](http://twitter.com/TelluriumTest)
  * [Tellurium on Reddit](http://www.reddit.com/r/Tellurium/)
  * [Ten Minutes to Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)
  * [Tellurium UI Model Firefox Plugin (TrUMP) 0.1.0](http://code.google.com/p/aost/wiki/TrUMP)

## Users' Experiences ##

Here is the list of experiences provided by Tellurium developers and users

  * [Fuctional Testing with Tellurium by Mikhail Koryak](http://notetodogself.blogspot.com/2009/02/functional-testing-with-tellurium.html)
  * [Tellurium and test automation process by Haroon Rasheed](http://epyramid.wordpress.com/2009/06/02/tellurium-automation-process/)
  * [Tellurium: A Better Functional Test by Mikhail Koryak](http://notetodogself.blogspot.com/2009/06/tellurium-better-functional-test.html)
  * [Experience with Web Testing Frameworks by Harihara Vinayakaram](http://startupmusings.blogspot.com/2009/06/experience-with-web-testing-frameworks.html)
  * [Why I use the Tellurium framework - an Automation QA perspective by Dominic Mooney](http://domsqablog.blogspot.com/2009/06/browser-based-testing-qa-perspective.html)

## Interviews ##

  * Interview with Tellurium creator, Dr. Jian Fang, by Kevin Zhang from [InfoQ China](http://www.infoq.com/cn/articles/tellurium-testing-framework).

## Presentations and Videos ##

  * [Tellurium at CodeStock 2009](http://wiki.codestock.org/Home/2009-slides-and-code)
  * [Tellurium video tutorial](http://aost.googlecode.com/files/tellurium_video_1.avi)
  * [How to use TrUMP to Create Tellurium Test Cases](http://code.google.com/p/aost/downloads/list) ([Flash Version](http://programmingdrunk.com/flv/))
  * [Tellurium Demo Video](http://code.google.com/p/aost/downloads/list) (Online Version [Part I](http://video.google.com/videoplay?docid=-7547683963743870970) [Part II](http://video.google.com/videoplay?docid=2145192284694104042))
  * [Online Presentation: Tellurium - A New Approach For Web Testing](http://www.slideshare.net/John.Jian.Fang/telluriumanewapproachforwebtesting)
  * [Online Presentation: 10 Minutes to Tellurium](http://www.slideshare.net/John.Jian.Fang/ten-minutes-to-tellurium)
  * [Screencast video: 10 Minutes to Tellurium](http://aost.googlecode.com/files/TenMinutesToTellurium.ogg) ([online version](http://www.youtube.com/watch?v=DyUPeg-Y-Yg))

## IDEs ##

  * [IntelliJ](http://www.jetbrains.com/idea/)
  * [NetBeans](http://www.netbeans.org/)
  * [Eclipse](http://www.eclipse.org/)

## Build ##

  * [Ant](http://ant.apache.org/)
  * [Maven](http://maven.apache.org/)

## Related ##

  * [Groovy](http://groovy.codehaus.org/)
  * [JQuery](http://jquery.com/)
  * [Dojo](http://www.dojotoolkit.org/)
  * [Ext JS](http://extjs.com/products/extjs/)
  * [Selenium](http://seleniumhq.org/)
  * [Selenium Grid](http://selenium-grid.seleniumhq.org/)
  * [Selenium Community](http://clearspace.openqa.org/community/selenium)
  * [Canoo WebTest](http://webtest.canoo.com)
  * [Twill](http://twill.idyll.org/)
  * [JUnit](http://www.junit.org/)
  * [TestNG](http://testng.org/)