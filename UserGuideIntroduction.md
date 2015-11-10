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
  * jQuery selector support to improve test speed in IE
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

The composite locator uses UI element attributes to define the UI, and the actual locator (e.g. xpath or jQuery selector) will be generated at runtime. Any updates to the composite locator will lead to different runtime locators, and the changes inside the UI module are localized. The Group locating is used to remove the dependency of the UI objects from external UI elements (i.e. external UI changes will not affect the current UI module for most cases) so that your test code is robust and responsive to changes up to a certain level.

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