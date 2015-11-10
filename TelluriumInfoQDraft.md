

# Background #

## Overview ##

Automated web testing has always been one of the hottest and most important topics in the software testing arena. With the rising popularity of Rich Internet applications (RIA) and Ajax-based web applications, the demand for automated web test tools has increased. With the advent of new web techniques such as RIA and Ajax, automated web testing tools must keep up with these changes in technology.

## Challenges ##

As the ongoing trend of new web technologies is speeding up, the upcoming automated web testing tools need to be able to address the following challenges:

  * **Robust/Responsive to Changes**: A good automated web testing tool should be able to address the changes in the web context to some degree so that users do not need to keep updating the test code frequently.
  * **Ajax for Dynamic Web Content**: Web applications have many benefits over desktop applications; For example, they have no installation and the application updates are instantaneous and easier to support. Ajax is a convenient way to update a part of the web page without refreshing the whole page. AJAX makes web applications richer and more user-friendly. The web context for an Ajax application is usually dynamic. For example, in a data grid, the data and number of rows keep changing at runtime.
  * **JavaScript Events**: JavaScript is everywhere on the web today. Many web applications are JavaScript heavy. To test JavaScript, the automated testing framework should be able to trigger JavaScript events in a very convenient way.
  * **Re-usability**: Many web applications use the same UI module for different parts of the application. The adoption of JavaScript frameworks such as Dojo and ExtJS increases the chance of using the same UI module for different web applications. A good testing framework should also be able to provide the re-usability of test modules.
  * **Expressiveness**: The testing framework should be able to provide users without much coding experience the ability to easily write test code or scripts in a familiar way, like by using a domain specific language (DSL).
  * **Easy to Maintain**: In an agile world, software development is based on iterations, and new features are added on in each sprint. The functional tests or user acceptance tests must be refactored and updated for the new features. The testing framework should provide the flexibility for users to maintain the test code very easily.

## Existing Open Source Automated Web Testing Frameworks ##

### Selenium ###

The **Selenium** web testing framework is one of the most popular open source automated web testing framework. It's a ground-breaking framework that offers many unique features and advantages such as browser based testing, Selenium Grid, and "record and replay" of user interactions with the Selenium IDE. As a pioneer framework, it inevitably has its shortcomings.

**Drawback** <br>
One major drawback is that it only focus on individual UI elements such as links and buttons. As a result, it is difficult to address dynamic web content in Selenium. "Record and replay" does make it much easier for users, especially non-developers, to create test cases. However, it has the drawback of being difficult to refactor and maintain. Other downsides include the coupling of UI elements and test code, being overly verbose, and being fragile and unresponsive to dynamic web changes.<br>
<br>
Take the following Selenium test code as an example:<br>
<br>
<pre><code>   setUp("http://www.google.com/", "*chrome");<br>
   selenium.open("/");<br>
   selenium.type("q", "Selenium test");<br>
   selenium.click("//input[@value='Google Search' and @type='button']");<br>
</code></pre>

Do you know what UI module the above code is testing? What does the locator <i>q</i> mean here? What if the XPath <code>//input[@value='Google Search' and @type='button']</code> becomes  invalid due to changes on the web?<br>
<br>
Most likely, you have to go through the test code to locate the lines you need to update. What if you have tens or hundreds of locators in your test code? Creating the test code using Selenium IDE is easy, but it is difficult to generalize and refactor. Refactoring is a much more tedious and painful procedure than regenerating new test code from scratch because of the use of hard-coded locators and the coupling of locators with test code. Maintaining the code is a hassle because the test code is not structured. Since Selenium only focuses on individual UI elements, it is difficult to adjust to changes on the web and to address a dynamic web context.<br>
<br>
<h3>Canoo</h3>

<b>Canoo</b> web test is another popular open source tool for automated testing of web applications. However, it also focuses on individual UI elements and thus it suffers shortcomings similar to those of Selenium, such as coupling the test code with the UI. Here is some sample test code:<br>
<br>
<pre><code>    webtest("check that WebTest is Google's top 'WebTest' result"){<br>
       invoke "http://www.google.com", description: "Go to Google"<br>
       verifyTitle "Google"<br>
       setInputField name: "q", value: "WebTest"<br>
       clickButton "I'm Feeling Lucky"<br>
       verifyTitle "Canoo WebTest"<br>
    }<br>
</code></pre>

<h3>Twill</h3>

<b>Twill</b> is a simple scripting language that allows users to browse the Web from a command-line interface. It uses links, forms, cookies, and most standard Web features. It also deals with individual UI elements but does not support javascript, and HTML needs to be parsed interactively. Twill only supports Python, which makes it fairly inconvenient for practical web applications.<br>
<br>
<br>
<h1>A New Approach for Web Testing</h1>

<h2>Motivations</h2>

Since the existing automated testing frameworks do not address the challenges and problems very well, there has been an real need for new automated testing framework to address them with the following motivations:<br>
<br>
<ul><li>Robust/responsive to changes; allow changes to be localized<br>
</li><li>Address dynamic web contexts such as JavaScript events and Ajax<br>
</li><li>Easy to refactor and maintain<br>
</li><li>Modular; test modules are reusable<br>
</li><li>Expressive and easy to use</li></ul>

Our proposal is the <b>Tellurium Automated Testing Framework</b>, or <i>Tellurium</i> for short.<br>
<br>
<h2>Tellurium Overview</h2>

The Tellurium Automated Testing Framework (Tellurium) is an open source automated testing framework for web applications that addresses the challenges and problems in web testing.<br>
<br>
The majority of existing web testing tools/frameworks focus on individual UI elements such as links and buttons. Tellurium takes a new approach for automated web testing using the concept of the <i>UI module</i>.  The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the Google search UI module can be expressed as follows:<br>
<br>
<pre><code>ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){<br>
   InputBox(uid: "Input", clocator: [title: "Google Search"])<br>
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])<br>
   SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])<br>
}<br>
</code></pre>

To understand the above definition, we need to introduce couple Tellurium concepts first.<br>
<br>
The first concept is Tellurium UI Object. Tellurium defines a set of Java UI objects, such as InputButton, Selector, List, Table, and Frame, which users can use directly. The basic UI Object works as the base class for all UI objects and it includes the following attributes:<br>
<br>
<ol><li>uid: UI object's identifier<br>
</li><li>namespace: used for XHTML<br>
</li><li>locator: the locator of the UI object, could be a base locator or a composite locator<br>
</li><li>respond: the JavaScript events the UI object can respond to. The value is a list.</li></ol>

All UI Objects inherit the above attributes and methods and many UI objects have their extra attributes and methods. For example, the List object has one special attribute "separator", which is used to indicate the tag used to separate different List child UI elements.<br>
<br>
Tellurium supports two types of locators: <i>base locator</i> and <i>composite locator</i>. The <i>base locator</i> is a relative XPath. The <i>composite locator</i>, denoted by "clocator", specifies a set of attributes for the UI object and the actual locator will be derived automatically by Tellurium at runtime.<br>
<br>
The Composite Locator is defined as follows:<br>
<br>
<pre><code>class CompositeLocator {<br>
    String header<br>
    String tag<br>
    String text<br>
    String trailer<br>
    def position<br>
    boolean direct<br>
    Map&lt;String, String&gt; attributes = [:]<br>
}<br>
</code></pre>

To use the composite locator, you need to use "clocator" with a map as its value. For example:<br>
<br>
<pre><code>clocator: [key1: value1, key2: value2, ...]<br>
</code></pre>

The default attributes include "header", "tag", "text", "trailer", "position", and "direct". They are all optional. The "direct" attribute specifies whether this UI object is a direct child of its parent UI, and the default value is "false".<br>
<br>
If you have additional attributes, you can define them in the same way as the default attributes, for example:<br>
<br>
<pre><code>clocator: [tag: "div", value: "Tellurium home"]<br>
</code></pre>

In the above Tellurium UI module, the "GoogleSearchModule" Container includes a "group" attribute. What does the attribute <i>group</i> mean? The group attribute is a flag for the Group Locating Concept. Usually, the XPath generated by Selenium IDE, XPather, or other tools is a single path to the target node such as:<br>
<br>
<pre><code>//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]<br>
</code></pre>

No sibling node's information is used here. What is wrong with this? The XPath depends too much on information from nodes far away from the target node. In Tellurium, we try to localize the information and reduce this dependency by using sibling information or local information. For example, in the above google UI module, the group locating concept will try to find the "td" tag with its children as "Input", "Search" button, and<br>
"ImFeelingLucky" button. In this way, we can reduce the dependencies of the UI elements inside a UI module on external UI elements to make the UI definition more robust.<br>
<br>
The Tellurium framework architecture is shown as in Figure 1.<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq'>http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&amp;gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq</a>

Figure 1. Tellurium Architecture.<br>
<br>
The DSL parser consists of the DSL Object Parser, Object Builders, and the Object Registry. The DSL object parser will parse the DSL object definition recursively and use object builders to build the objects on the fly. An object builder registry is designed to hold all predefined UI object builders in the Tellurium framework, and the DSL object parser will look at the builder registry to find the appropriate builders. Since the registry is a hash map, you can override a builder with a new one using the same UI name. Users can also add their customer builders into the builder registry.<br>
<br>
The DSL object definition usually comes first with a container type object. An object registry (a hash map) is used to store all top level UI Objects. As a result, for each DSL object definition, the top object ids must be unique in the DslContext. The object registry will be used by the framework to search objects by their ids and fetch objects for different actions.<br>
<br>
The Object Locator Mapping (OLM) is the core of the Tellurium framework and it includes UI ID mapping, XPath builder, jQuery selector builder, and Group Locating.<br>
<br>
The UI ID supports nested objects. For example, "GoogleSearchModule.Search" stands for a button "Search" inside a container called "GoogleSearchModule". The UI ID also supports one-dimensional and two-dimensional indices for table and list. For example, <code>"main.table[2][3]"</code> stands for the UI object of the 2nd row and the 3rd column of a table inside the container "main".<br>
<br>
XPath builder can build the XPath from the composite locator, i.e., a set of attributes. Starting with version 0.6.0, Tellurium supports jQuery selectors to address the problem of poor performance of XPath in Internet Explorer. jQuery selector builders are used to automatically generate jQuery selectors instead of XPath with the following advantages:<br>
<ul><li>Faster performance in IE.<br>
</li><li>Leverage the power of jQuery to retrieve bulk data from the web by testing with one method call.<br>
</li><li>New features provided by jQuery attribute selectors.</li></ul>

The Group Locating Concept (GLC) exploits the group information inside a collection of UI objects to help us find the locator of the collection of UI objects.<br>
<br>
The Eventhandler will handle all events like "click", "type", "select", and so on. The Data Accessor is used to fetch data or UI status from the DOM. The dispatcher will delegate all calls it receives from the Eventhandler and the data accessor to the connector, which connects to the Tellurium engine. The dispatcher is designed to decouple the rest of the Tellurium framework from the base test driving engine so that we can switch to a different test driving engine by simply changing the dispatcher logic.<br>
<br>
Tellurium evolved out of Selenium, but the UI testing approach is completely different. For example, Tellurium is not a "record and replay" style framework, and it enforces the separation of UI modules from test code, making refactoring easy. For example, once you defined the Google Search UI module shown above, you can write your test code as follows:<br>
<br>
<pre><code>type "GoogleSearchModule.Input", "Tellurium test"<br>
click "GoogleSearchModule.Search"<br>
</code></pre>

<h2>How Challenges and Problems are addressed in Tellurium ?</h2>

<h3>Robust/Responsive to Changes</h3>

First of all, Tellurium does not use "record and replay." Instead, it uses the Tellurium Firefox plugin Trump to generate the UI module (not test code) for you. Then you need to create your test code based on the UI module. In this way, the UI and the test code are decoupled. The structured test code in Tellurium makes it much easier to refactor and maintain the code.<br>
<br>
The composite locator uses UI element attributes to define the UI, and the actual locator (e.g. xpath or jQuery selector) will be generated at runtime. Any updates to the composite locator will lead to different runtime locators, and the changes inside the UI module are localized. The Group locating is used to remove the dependency of the UI objects from external UI elements (i.e. external UI changes will not affect the current UI module for most cases) so that your test code is robust and responsive to changes up to a certain level.<br>
<br>
<h3>Ajax for Dynamic Web Content</h3>

UI templates are a powerful feature in Tellurium used to represent many identical UI elements or a dynamic size of different UI elements at runtime, which are extremely useful in testing dynamic web contexts such as a data grid. Tellurium UI templates are used for two purposes:<br>
<br>
<ol><li>When there are many identical UI elements, you can use one template to represent them all<br>
</li><li>When here are a variable/dynamic size of UI elements at runtime, you know the patterns, but not the size.</li></ol>

More specifically, Table and List are two Tellurium objects that can define UI templates. Table defines two dimensional UI templates, and List is for one dimensional. The Template has<br>
special UIDs such as "2", "all", or "row: 1, column: 2".<br>
<br>
For use case (2), a common application is the data grid. Look at the "issueResult" data grid on our Tellurium Issues page:<br>
<br>
<pre><code>ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], <br>
         group: "true") <br>
{<br>
    TextBox(uid: "header: 1",  clocator: [:])<br>
    UrlLink(uid: "header: 2",  clocator: [text: "%%ID"])<br>
    UrlLink(uid: "header: 3",  clocator: [text: "%%Type"])<br>
    UrlLink(uid: "header: 4",  clocator: [text: "%%Status"])<br>
    UrlLink(uid: "header: 5",  clocator: [text: "%%Priority"])<br>
    UrlLink(uid: "header: 6",  clocator: [text: "%%Milestone"])<br>
    UrlLink(uid: "header: 7",  clocator: [text: "%%Owner"])<br>
    UrlLink(uid: "header: 9",  clocator: [text: "%%Summary + Labels"])<br>
    UrlLink(uid: "header: 10", clocator: [text: "%%..."])<br>
<br>
    //define table elements<br>
    //for the border column<br>
    TextBox(uid: "row: *, column: 1", clocator: [:])<br>
    //For the rest, just UrlLink<br>
    UrlLink(uid: "all", clocator: [:])<br>
}<br>
</code></pre>

Aren't the definitions very simple and cool?<br>
<br>
You may wonder how to use the templates if you have multiple templates such as the "issueResult" table shown above. The rule to apply the templates is: "specific one first, general one later".<br>
<br>
The <i>Option</i> UI object is designed to automatically address dynamic web contexts with multiple possible UI patterns. Option is a pure abstract object and it holds multiple UIs with each representing a possible UI pattern at runtime. For example, the List/Grid selector on the issue page can described as:<br>
<br>
<pre><code>//The selector to choose the data grid layout as List or Grid<br>
ui.Option(uid: "layoutSelector"){<br>
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {<br>
        TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"])<br>
        UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])<br>
    }<br>
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {<br>
        UrlLink(uid: "List", clocator: [text: "List", direct: "true"])<br>
        TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"])<br>
    }<br>
}<br>
</code></pre>

Note, the option's uid must be the same as the next UI objects it represent and in this way, you do not need to include option's uid in the UiID. For example,  you can just use<br>
<br>
<pre><code>click "layoutSelector.List"<br>
</code></pre>

instead of<br>
<br>
<pre><code>click "layoutSelector.layoutSelector.List"<br>
</code></pre>

The option object will automatically detect which UI pattern you need to use at runtime.<br>
<br>
<br>
<h3>JavaScript Events</h3>

Most web applications include Javascript, and thus the web testing framework must be able to handle Javascript events. What we really care about is firing the appropriate events to trigger the event handlers.<br>
<br>
Selenium has already provided methods to generate events such as:<br>
<br>
<pre><code>fireEvent(locator, "blur")<br>
fireEvent(locator, "focus")<br>
mouseOut(locator)<br>
mouseOver(locator)<br>
</code></pre>

Tellurium was born with Javascript events in mind since it was initially designed to test applications written using the DOJO JavaScript framework. For example, we have the following radio button:<br>
<br>
<pre><code>&lt;input type='radio' name='mas_address_key' value='5779' onClick='SetAddress_5779()'&gt;<br>
</code></pre>

Although we can define the radio button as follows:<br>
<br>
<pre><code>RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'])<br>
</code></pre>

The above code will not respond to the click event since the Tellurium RadioButton only supports the "check" and "uncheck" actions, which is enough for the normal case. As a result, no "click" event/action will be generated during the testing. To address this problem, Tellurium added the "respond" attribute to Tellurium UI objects. The "respond" attribute could be used to define whatever events you want the UI object to respond to. The Radio Button can be redefined as:<br>
<br>
<pre><code> ui.Container(uid: "form", clocator: [whatever]){<br>
     RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'], <br>
               respond: ["click"])<br>
 }<br>
</code></pre>

That is to say, you can issue the following command:<br>
<br>
<pre><code>  click "form.billing"<br>
</code></pre>

Even if the RadioButton does not have the <i>click</i> method defined by default, it is still able to dynamically add the <i>click</i> method at runtime and call it.<br>
<br>
A more general example is as follows:<br>
<br>
<pre><code> InputBox(uid: "searchbox", clocator: [title: "Google Search"], <br>
          respond: ["click", "focus", "mouseOver", "mouseOut", "blur"])<br>
</code></pre>

Except for the "click" event, all of the "focus", "mouseOver", "mouseOut", and "blur" events will be automatically fired by Tellurium during testing. Do not worry about the event order for the respond attribute, Tellurium will automatically re-order the events and then process them appropriately for you.<br>
<br>
<h3>Re-usability</h3>

Re-usability is achieved by the UI module when working within one application and by Tellurium Widgets when working across different web applications.<br>
Tellurium Widget is a good way to re-use UI components in testing. Tellurium provides you the capability to composite UI objects into a widget object and then you can use the widget directly just like using a tellurium UI object. The advantage is that you do not need to deal with the UI at the link or button level for the widget, you just work on the high level methods. Another advantage is that this widget is reusable.<br>
<br>
Usually, Java script frameworks provide a lot of widgets. Take the Dojo framework as an example. We use the widget DatePicker to prototype the tellurium widget. For widgets, it is important to include name space to avoid name collision between different widget modules. For example, what is Dojo and ExtJs both have the widget Date Picker? After add the name space, the widget is named as "DOJO_DatePicker".<br>
<br>
The DataPicker widget is defined like a regular Tellurium UI module and we defined the following widget methods:<br>
<br>
<pre><code>    public String getCurrentYear(){<br>
        return getText("DatePicker.year.currentYear")<br>
    }<br>
<br>
    public void selectPrevYear(){<br>
        click "DatePicker.year.prevYear"<br>
    }<br>
</code></pre>

The widget is treated as a tellurium UI object and the builder is the same as regular tellurium objects<br>
<br>
<pre><code>class DatePickerBuilder extends UiObjectBuilder{<br>
<br>
    public build(Map map, Closure c) {<br>
       //add default parameters so that the builder can use them if not specified<br>
        def df = [:]<br>
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)<br>
        datepicker.defineWidget()<br>
<br>
        return datepicker<br>
    }<br>
}<br>
</code></pre>

Now, we need to hook the widget into the Tellurium Core. Each widget module will be compiled as a separate jar file and it should define a bootstrap class to register all the widgets inside the module. By default, the full class name of the bootstrap class is <code>org.tellurium.widget.XXXX.Init</code>, where the class Init should implement the <code>WidgetBootstrap</code> interface to register widgets and <i>XXXX</i> stands for the widget module name. It is DOJO in our case.<br>
<br>
<pre><code>class Init implements WidgetBootstrap{<br>
    public void loadWidget(UiObjectBuilderRegistry uiObjectBuilderRegistry) {<br>
        if(uiObjectBuilderRegistry != null){<br>
           uiObjectBuilderRegistry.registerBuilder(getFullName("DatePicker"), <br>
                                        new DatePickerBuilder())      <br>
        }<br>
    }<br>
}<br>
</code></pre>

Then in the tellurium configuration file TelluriumConfig.groovy, you should include your module name there,<br>
<br>
<pre><code>    widget{<br>
        module{<br>
            //define your widget modules here, for example Dojo or ExtJs<br>
            included="dojo"<br>
        }<br>
    }<br>
</code></pre>

If you use your own package name for the bootstrap class, for example, com.mycompay.widget.Boot, then you should specify the full name there like<br>
<br>
<pre><code>    widget{<br>
        module{<br>
            //define your widget modules here, for example Dojo or ExtJs<br>
            included="com.mycompay.widget.Boot"<br>
        }<br>
    }<br>
</code></pre>

Note, you can load multiple widget modules into the Tellurium Core framework by define<br>
<br>
<pre><code>  included="dojo, com.mycompay.widget.Boot"<br>
</code></pre>

To use widget, you can treat a Widget as a regular tellurium UI object. For example,<br>
<br>
<pre><code>class DatePickerDemo extends DslContext{<br>
    <br>
    public void defineUi() {<br>
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){<br>
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])<br>
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])<br>
            Image(uid: "selectDate", clocator: [title: "select a date", <br>
                  dojoattachpoint: "containerDropdownNode", alt: "date"])<br>
            DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", <br>
                            dojoattachpoint: "subWidgetContainerNode"])<br>
        }<br>
    }<br>
}<br>
</code></pre>

Then on the module file DatePickerDemo, you can call the widget methods instead of dealing with low level links, buttons, and so on.<br>
<br>
To make the testing more expressive, Tellurium provides an onWidget method<br>
<br>
<pre><code>  onWidget(String uid, String method, Object[] args)<br>
</code></pre>

In that way, we can call the widget methods as follows:<br>
<br>
<pre><code>onWidget "dropdown.datePicker", selectPrevYear<br>
</code></pre>

<h3>Expressiveness</h3>

Domain Specific Language (DSL) indicates a programming language or specification language dedicated to a particular problem domain, a particular problem representation technique, or a particular solution technique. Here the domain refers to UI testing. DSL is specified in the class <b>DslContext</b>, including UI definitions, actions, and testing.<br>
<br>
With the Domain Specific Language (DSL) in Tellurium you can define UI modules and write test code in a very expressive way. Tellurium also provides you the flexibility to write test code in Java, Groovy, or pure DSL scripts.<br>
<br>
<h3>Easy to Maintain</h3>


<h1>Summary</h1>

Tellurium introduces a new approach to test web applications based on UI modules. The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uid) along the path to the specific element. This also enables Tellurium's <i>Group Locating</i> feature, making composite objects reusable, and addressing dynamic web pages.<br>
<br>
Tellurium does <i>Object to Locator Mapping (OLM)</i> automatically at run time so that UI objects can be defined simply by their attributes using <i>Composite Locators</i>. Tellurium uses the Group Locating Concept (GLC) to exploit information inside a collection of UI components so that locators can find their elements.  It also defines a set of DSLs for web testing.  Furthermore, Tellurium uses UI templates to define sets of dynamic UI elements at runtime.  As a result, Tellurium is robust, expressive, flexible, reusable, and easy to maintain.