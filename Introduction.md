(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))



# Introduction #

## Motivation ##

Automated web testing has always been one of the hottest and most important topics in the software testing arena when it comes to the rising popularity of Rich Internet applications (RIA) and Ajax-based web applications. With the advent of new web techniques such as RIA and Ajax, automated web testing tools must keep current with changes in technology and be able to address the following challenges:

  * **JavaScript Events**: JavaScript is everywhere on the web today. Many web applications are JavaScript heavy. To test JavaScript, the automated testing framework should be able to trigger JavaScript events in a convenient way.
  * **Ajax for Dynamic Web Content**: Web applications have many benefits over desktop applications. For example, these applications have no installation and updates are instantaneous and easier to support. Ajax is a convenient way to update a part of the web page without refreshing the whole page. AJAX makes web applications richer and more user-friendly. The web context for an Ajax application is usually dynamic. For example, in a data grid, the data and number of rows keeps changing at runtime.
  * **Robust/Responsive to Changes**: A good automated web-testing tool should be able to address the changes in the web context to some degree so that users do not need to keep updating the test code.
  * **Easy to Maintain**: In an agile testing world, software development is based on iterations, and new features are added on in each sprint. The functional tests or user acceptance tests must be refactored and updated for the new features. The testing framework should provide the flexibility for users to maintain the test code easily.
  * **Re-usability**: Many web applications use the same UI module for different parts of the application. The adoption of JavaScript frameworks such as Dojo and ExtJS increases the chance of using the same UI module for different web applications. A good testing framework should also be able to provide the re-usability of test modules.
  * **Expressiveness**: The testing framework provides users without much coding experience the ability to easily write test code or scripts in a familiar way, such as using a domain specific language (DSL).

The Tellurium Automated Testing Framework (Tellurium) is designed around these considerations and has defined as its focus the following goals:

  * Robust/responsive to changes; allow changes to be localized
  * Addresses dynamic web contexts such as JavaScript events and Ajax
  * Easy to refactor and maintain
  * Modular; test modules are reusable
  * Expressive and easy to use

## Tellurium, the New Approach for Web Testing ##

The Tellurium Automated Testing Framework (Tellurium) is an open source automated testing framework for web applications that addresses the challenges and problems of today’s web testing.

Most existing web testing tools/frameworks focus on individual UI elements such as links and buttons. Tellurium takes a new approach for automated web testing by using the concept of the UI module.

The _UI module_ is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the Google search UI module can be expressed as follows:
```
ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){
  InputBox(uid: "Input", clocator: [title: "Google Search"])
  SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
  SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
}
```

Tellurium is built on the foundation of the UI module. The UI module makes it possible to build locators for UI elements at runtime. First, this makes Tellurium robust and responsive to changes from internal UI elements. Second, the UI module makes Tellurium expressive. UI elements can be referred to simply by appending the names (uid) along the path to the specific element. This also enables _Tellurium's Group Locating_ feature, making composite objects reusable, and addressing dynamic web pages.

Tellurium is implemented in Groovy and Java. The test cases can be written in Java, Groovy, or pure Domain Specific Language (DSL) scripts. Tellurium evolved out of Selenium. However, the UI testing approach is completely different. Tellurium is not a "record and replay" style framework, and it enforces the separation of UI modules from test code, making refactoring easy.

For example, once the Google Search UI module is defined as previously shown, the test code is written as follows:

```
type "GoogleSearchModule.Input", "Tellurium test"
click "GoogleSearchModule.Search"
```

Tellurium sets the Object to Locator Mapping (OLM) automatically at runtime so that UI objects can be defined simply by their attributes using Composite Locators. Tellurium uses the Group Locating Concept (GLC) to exploit information inside a collection of UI components so that locators can find their elements.

Tellurium also defines a set of DSLs for web testing. Furthermore, Tellurium uses UI templates to define sets of dynamic UI elements at runtime. As a result, Tellurium is robust, expressive, flexible, reusable, and easy to maintain.

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

## How Challenges and Problems Are Addressed in Tellurium ##

First, Tellurium does not use "record and replay". Instead, it uses the Tellurium Firefox plugin TrUMP to generate the UI module (not test code) for you. Then test code based on the UI module is created.

In this way, the UI and the test code are decoupled. The structured test code in Tellurium makes it much easier to refactor and maintain the code.

The composite locator uses UI element attributes to define the UI, and the actual locator (for example, XPath or jQuery selector), is generated at runtime. Any updates to the composite locator lead to different runtime locators, and the changes inside the UI module are localized.

The Group locating is used to remove the dependency of the UI objects from external UI elements (for example, external UI changes do not affect the current UI module for most cases), so that test code is robust and responsive to changes up to a certain level.

Tellurium uses the _respond_ attribute in a UI object to specify JavaScript events, and the rest is handled automatically by the framework itself.

UI templates are a powerful feature in Tellurium used to represent many identical UI elements or a dynamic size of different UI elements at runtime. This is extremely useful in testing dynamic web contexts such as a data grid.

The _Option_ UI object is designed to automatically address dynamic web contexts with multiple possible UI patterns.

Re-usability is achieved by the UI module when working within one application and by Tellurium Widgets when working across different web applications. With the Domain Specific Language (DSL) in Tellurium, UI modules can be defined and test code written in a very expressive way.

Tellurium also provides flexibility to write test code in Java, Groovy, or pure DSL scripts.