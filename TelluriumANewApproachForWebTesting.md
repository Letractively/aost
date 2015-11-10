

# Background #

## Overview ##

Automated web testing has always been one of the hottest and most important topics in the software testing arena. With the rising popularity of Rich Internet applications (RIA) and Ajax-based web applications, the demand for automated web test tools has increased. With the advent of new web techniques such as RIA and Ajax, automated web testing tools must keep up with these changes in technology.

## Challenges ##
As the ongoing trend of new web technologies is speeding up, the upcoming automated web testing tools need to be able to address the following challenges:

  * **JavaScript Events**: JavaScript is everywhere on the web today. Many web applications are JavaScript heavy. To test JavaScript, the automated testing framework should be able to trigger JavaScript events in a very convenient way.
  * **Ajax for Dynamic Web Content**: Web applications have many benefits over desktop applications; For example, they have no installation and the application updates are instantaneous and easier to support. Ajax is a convenient way to update a part of the web page without refreshing the whole page. AJAX makes web applications richer and more user-friendly. The web context for an Ajax application is usually dynamic. For example, in a data grid, the data and number of rows keep changing at runtime.
  * **Robust/Responsive to Changes**: A good automated web testing tool should be able to address the changes in the web context to some degree so that users do not need to keep updating the test code frequently.
  * **Easy to Maintain**: In an agile world, software development is based on iterations, and new features are added on in each sprint. The functional tests or user acceptance tests must be refactored and updated for the new features. The testing framework should provide the flexibility for users to maintain the test code very easily.
  * **Re-usability**: Many web applications use the same UI module for different parts of the application. The adoption of JavaScript frameworks such as Dojo and ExtJS increases the chance of using the same UI module for different web applications. A good testing framework should also be able to provide the re-usability of test modules.
  * **Expressiveness**: The testing framework should be able to provide users without much coding experience the ability to easily write test code or scripts in a familiar way, like by using a domain specific language (DSL).

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

<b>Do you know what UI module the above code is testing?</b><br>
<b>What does the locator <i>q</i> mean here?</b><br>
<b>What if the XPath <code>//input[@value='Google Search' and @type='button']</code> becomes  invalid due to changes on the web?</b><br>

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
<br>
<h2>Why Tellurium is a New Approach for Web Testing</h2>

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

Tellurium is built on the foundation of the UI module.  The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uid) along the path to the specific element. This also enables Tellurium's <i>Group Locating</i> feature, making composite objects reusable, and addressing dynamic web pages.<br>
<br>
Tellurium is implemented in Groovy and Java.  The test cases can be written in Java, Groovy, or pure Domain Specific Language (DSL) scripts.  Tellurium evolved out of Selenium, but the UI testing approach is completely different. For example, Tellurium is not a "record and replay" style framework, and it enforces the separation of UI modules from test code, making refactoring easy. For example, once you defined the Google Search UI module shown above, you can write your test code as follows:<br>
<br>
<pre><code>type "GoogleSearchModule.Input", "Tellurium test"<br>
click "GoogleSearchModule.Search"<br>
</code></pre>

Tellurium does <i>Object to Locator Mapping (OLM)</i> automatically at run time so that UI objects can be defined simply by their attributes using <i>Composite Locators</i>. Tellurium uses the Group Locating Concept (GLC) to exploit information inside a collection of UI components so that locators can find their elements.  It also defines a set of DSLs for web testing.  Furthermore, Tellurium uses UI templates to define sets of dynamic UI elements at runtime.  As a result, Tellurium is robust, expressive, flexible, reusable, and easy to maintain.<br>
<br>
<h2>Main Features of Tellurium</h2>

The main features of Tellurium include:<br>
<br>
<ul><li>Abstract UI objects to encapsulate web UI elements<br>
</li><li>UI module for structured test code and re-usability<br>
</li><li>DSL for UI definition, actions, and testing<br>
</li><li>Composite Locator to use a set of attributes to describe a UI element<br>
</li><li>Group locating to exploit information inside a collection of UI components<br>
</li><li>Dynamically generate runtime locators to localize changes<br>
</li><li>UI templates for dynamic web content<br>
</li><li>XPath support<br>
</li><li>jQuery selector support to improve test speed in IE<br>
</li><li>Locator caching to improve speed<br>
</li><li>Javascript event support<br>
</li><li>Use Tellurium Firefox plugin, Trump, to automatically generate UI modules<br>
</li><li>Dojo and ExtJS widget extensions<br>
</li><li>Data driven test support<br>
</li><li>Selenium Grid support<br>
</li><li>JUnit and TestNG support<br>
</li><li>Ant and Maven support</li></ul>

<h2>How Challenges and Problems are addressed in Tellurium ?</h2>

First of all, Tellurium does not use "record and replay." Instead, it uses the Tellurium Firefox plugin Trump to generate the UI module (not test code) for you. Then you need to create your test code based on the UI module. In this way, the UI and the test code are decoupled. The structured test code in Tellurium makes it much easier to refactor and maintain the code.<br>
<br>
The composite locator uses UI element attributes to define the UI, and the actual locator (e.g. xpath or jQuery selector) will be generated at runtime. Any updates to the composite locator will lead to different runtime locators, and the changes inside the UI module are localized. The Group locating is used to remove the dependency of the UI objects from external UI elements (i.e. external UI changes will not affect the current UI module for most cases) so that your test code is robust and responsive to changes up to a certain level.<br>
<br>
Tellurium uses the <i>respond</i> attribute in a UI object for you to specify JavaScript events, and the rest will be handled automatically by the framework itself. UI templates are a powerful feature in Tellurium used to represent many identical UI elements or a dynamic size of different UI elements at runtime, which are extremely useful in testing dynamic web contexts such as a data grid. The <i>Option</i> UI object is designed to automatically address dynamic web contexts with multiple possible UI patterns.<br>
<br>
Re-usability is achieved by the UI module when working within one application and by Tellurium Widgets when working across different web applications. With the Domain Specific Language (DSL) in Tellurium you can define UI modules and write test code in a very expressive way. Tellurium also provides you the flexibility to write test code in Java, Groovy, or pure DSL scripts.<br>
<br>
In the subsequent sections, we will give a brief introduction of Tellurium, including its architecture and main concepts. Then we will introduce the Tellurium sub-projects and how to obtain and use Tellurium. This will be followed by a detailed tutorial on how to create Tellurium test cases with code samples. Finally, we will summarize and conclude this article with our plans for the future.<br>
<br>
<h1>Tellurium Overview</h1>

<h2>Tellurium Architecture</h2>

The Tellurium framework architecture is shown as in Figure 1.<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq'>http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&amp;gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq</a>

Figure 1. Tellurium Architecture.<br>
<br>
The DSL parser consists of the DSL Object Parser, Object Builders, and the Object Registry.<br>
<br>
Thanks to Groovy builder pattern, we can define UI objects expressively and in a nested fashion. The DSL object parser will parse the DSL object definition recursively and use object builders to build the objects on the fly. An object builder registry is designed to hold all predefined UI object builders in the Tellurium framework, and the DSL object parser will look at the builder registry to find the appropriate builders. Since the registry is a hash map, you can override a builder with a new one using the same UI name. Users can also add their customer builders into the builder registry.<br>
<br>
The DSL object definition always comes first with a container type object. An object registry (a hash map) is used to store all top level UI Objects. As a result, for each DSL object definition, the top object ids must be unique in the DslContext. The object registry will be used by the framework to search objects by their ids and fetch objects for different actions.<br>
<br>
The Object Locator Mapping (OLM) is the core of the Tellurium framework and it includes UI ID mapping, XPath builder, jQuery selector builder, and Group Locating.<br>
<br>
The UI ID supports nested objects. For example, "menu.wiki" stands for a URL Link "wiki" inside a container called "menu". The UI ID also supports one-dimensional and two-dimensional indices for table and list. For example, <code>"main.table[2][3]"</code> stands for the UI object of the 2nd row and the 3rd column of a table inside the container "main".<br>
<br>
XPath builder can build the XPath from the composite locator, i.e., a set of attributes. Starting with version 0.6.0, Tellurium supports jQuery selectors to address the problem of poor performance of XPath in Internet Explorer. jQuery selector builders are used to automatically generate jQuery selectors instead of XPath with the following advantages:<br>
<ul><li>Faster performance in IE.<br>
</li><li>Leverage the power of jQuery to retrieve bulk data from the web by testing with one method call.<br>
</li><li>New features provided by jQuery attribute selectors.</li></ul>

The Group Locating Concept (GLC) exploits the group information inside a collection of UI objects to help us find the locator of the collection of UI objects.<br>
<br>
The Eventhandler will handle all events like "click", "type", "select", and so on. The Data Accessor is used to fetch data or UI status from the DOM. The dispatcher will delegate all calls it receives from the Eventhandler and the data accessor to the connector, which connects to the Tellurium engine. The dispatcher is designed to decouple the rest of the Tellurium framework from the base test driving engine so that we can switch to a different test driving engine by simply changing the dispatcher logic.<br>
<br>
<h2>Tellurium UI Objects</h2>

Tellurium provides a set of predefined UI objects, which users can use directly.<br>
<br>
The basic UI object is an abstract class. Users cannot instantiate it directly. The basic UI Object works as the base class for all UI objects and it includes the following attributes:<br>
<br>
<ol><li>uid: UI object's identifier<br>
</li><li>namespace: for future extension<br>
</li><li>locator: the locator of the UI object, could be a base locator or a composite locator<br>
</li><li>respond: the JavaScript events the UI object can respond to. The value is a list.</li></ol>

All UI Objects will inherit the above attributes and methods. But be aware that you usually do not call these methods directly and you should use DSL syntax instead. For example, use:<br>
<br>
<pre><code>click "GoogleSearchModule.Search"<br>
</code></pre>

In this way, Tellurium will first map the UIID "GoogleSearchModule.Search" to the actual UI object and then call the <i>click</i> method on it. If that Ui object does not have the <i>click</i> method defined, you will get an error.<br>
<br>
The predefined Tellurium UI objects and some of their default attributes are listed in the following table:<br>
<br>
<table><thead><th> <b>Tellurium Object</b> </th><th> <b>Locator Default Attributes</b> </th><th> <b>Extra Attributes</b> </th><th> <b>UI Template</b> </th></thead><tbody>
<tr><td> Button                  </td><td> tag: "input"                      </td><td>                         </td><td> no                 </td></tr>
<tr><td> Container               </td><td>                                   </td><td> group                   </td><td> no                 </td></tr>
<tr><td> CheckBox                </td><td> tag: "input", type: "checkbox"    </td><td>                         </td><td> no                 </td></tr>
<tr><td> Div                     </td><td> tag: "div"                        </td><td>                         </td><td> no                 </td></tr>
<tr><td> Form                    </td><td> tag: "form"                       </td><td> group                   </td><td> no                 </td></tr>
<tr><td> Image                   </td><td> tag: "img"                        </td><td>                         </td><td> no                 </td></tr>
<tr><td> InputBox                </td><td> tag: "input"                      </td><td>                         </td><td> no                 </td></tr>
<tr><td> RadioButton             </td><td> tag: "input", type: "radio"       </td><td>                         </td><td> no                 </td></tr>
<tr><td> Selector                </td><td> tag: "select"                     </td><td>                         </td><td> no                 </td></tr>
<tr><td> Span                    </td><td> tag: "span"                       </td><td>                         </td><td> no                 </td></tr>
<tr><td> SubmitButton            </td><td> tag: "input", type: "submit"      </td><td>                         </td><td> no                 </td></tr>
<tr><td> UrlLink                 </td><td> tag: "a"                          </td><td>                         </td><td> no                 </td></tr>
<tr><td> List                    </td><td>                                   </td><td> separator               </td><td> yes                </td></tr>
<tr><td> Table                   </td><td> tag: "table"                      </td><td> group, header           </td><td> yes                </td></tr>
<tr><td> StandardTable           </td><td> tag: "table"                      </td><td> group, header, footer   </td><td> yes                </td></tr>
<tr><td> Frame                   </td><td>                                   </td><td> group, id, name, title  </td><td> no                 </td></tr>
<tr><td> Window                  </td><td>                                   </td><td> group, id, name, title  </td><td> no                 </td></tr></tbody></table>



<h2>UI Module</h2>

The UI Module is the heart of Tellurium. The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium's project site is defined as follows:<br>
<br>
<pre><code>ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {<br>
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])<br>
   InputBox(uid: "searchBox", clocator: [name: "q"])<br>
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])<br>
}<br>
</code></pre>

Tellurium is built on the foundation of the UI module. The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uids) along the path to the specific element. This also enables Tellurium's "Group Locating" feature, making composite objects reusable and addressing dynamic web pages.<br>
<br>
This frees up the testers to write better tests rather than spend precious testing time on identifying and resolving test failures due to XPath changes.<br>
<br>
<h2>Composite Locator</h2>

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

Most Tellurium objects come with default values for certain attributes, for example, the  tag attribute. If these attributes are not specified, the default attribute values will be used. In other words, if you know the default attribute values of a Tellurium UI object, you can omit them in clocator. Take the RadioButton object as an example, its default tag is "input," and its default type is "radio." You can omit them and write the clocator as follows:<br>
<br>
<pre><code>clocator: [:]<br>
</code></pre>

which is equivalent to<br>
<br>
<pre><code>clocator: [tag: "input", type: "radio"]<br>
</code></pre>

<h2>Group Locating</h2>

In the Tellurium UI module, you often see the "group" attribute. For example:<br>
<br>
<pre><code>ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){<br>
  InputBox(uid: "searchbox", clocator: [title: "Google Search"])<br>
  SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])<br>
  SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])<br>
}<br>
</code></pre>

What does the attribute <i>group</i> mean? The group attribute is a flag for the Group Locating Concept. Usually, the XPath generated by Selenium IDE, XPather, or other tools is a single<br>
path to the target node such as:<br>
<br>
<pre><code>//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]<br>
</code></pre>

No sibling node's information is used here. What is wrong with this? The XPath depends too much on information from nodes far away from the target node. In Tellurium, we try to localize the information and reduce this dependency by using sibling information or local information. For example, in the above google UI module, the group locating concept will try to find the "td" tag with its children as "InputBox", "googlesearch" button, and<br>
"Imfeelinglucky" button. In this way, we can reduce the dependencies of the UI elements inside a UI module on external UI elements to make the UI definition more robust.<br>
<br>
<h2>UI Templates</h2>

Tellurium UI templates are used for two purposes:<br>
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
<h2>Javascript Events</h2>

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
<h2>jQuery Selector</h2>

Starting with version 0.6.0, Tellurium supports a <a href='http://code.google.com/p/aost/wiki/TelluriumjQuerySelector'>jQuery selectors</a> to address the problem of poor performance of XPath in Internet Explorer. Auto-generating jQuery instead of XPath has the following advantages:<br>
<ul><li>Faster performance in IE.<br>
</li><li>We are able to use the power of jQuery to call methods on jQuery collections to retrieve bulk data.<br>
</li><li>Tellurium jQuery selector supports the jQuery attribute selectors such as <i>[attribute!=value]</i>, <i>[attribute^=value]</i>, <i>[attribute$=value]</i>, and <b>[attribute<i>=value]</i>.</li></ul></b>

To use jQuery selectors, simply call:<br>
<br>
<pre><code>useJQuerySelector()<br>
</code></pre>

in you code. To switch back to XPath locators, you should call:<br>
<br>
<pre><code>disableJQuerySelector()<br>
</code></pre>

Be aware that the UI module is agnostic to the locate strategy you choose, and thus you do not need to change anything in your test code.<br>
<br>
Our Benchmark test results show that:<br>
<br>
<ol><li>jQuery selector is as fast as Ajaxslt XPath and Javascript XPath in Firefox 3.<br>
</li><li>jQuery selector is much faster in IE 7 than the two XPath libraries<br>
</li><li>jQuery selector uses only one method call to get back data for all table cells, and thus it is the fastest one for bulk data access.</li></ol>

<a href='http://code.google.com/p/aost/wiki/jQuerySelectorCache'>jQuery cache</a> is a mechanism to further improve speed by reusing the previously found DOM reference for a given jQuery selector. Our benchmark results show that the jQuery cache could improve the speed by up to 14% over the regular jQuery selector and over 27% for some extreme cases.<br>
<br>
<h2>Domain Specific Language</h2>

Domain Specific Language (DSL) indicates a programming language or specification language dedicated to a particular problem domain, a particular problem representation technique, or a particular solution technique. Here the domain refers to UI testing. DSL is specified in the class <b>DslContext</b>, including UI definitions, actions, and testing.<br>
<br>
<h2>Testing Support</h2>

In the package org.tellurium.test, Tellurium provides three different ways to write Tellurium tests:<br>
<br>
<ul><li>TelluriumJavaTestCase is for JUnit, and it supports the following JUnit 4 annotations: @BeforeClass, @AfterClass, @Before, @After, @Test, and @Ignore<br>
</li><li>TelluriumTestNGTestCase is for TestNG. Similarly, you can use the following annotations: @BeforeSuite, @AfterSuite, @BeforeTest, @AfterTest, @BeforeGroups, @AfterGroups, @BeforeClass, @AfterClass, @BeforeMethod, @AfterMethod, @DataProvider, @Parameters, and @Test<br>
</li><li>TelluriumGroovyTestCase is for Groovy test cases.<br>
</li><li>Data Driven Testing. Tellurium provides the class <i>TelluriumDataDrivenModule</i> for users to define data driven testing modules and the class <i>TelluriumDataDrivenTest</i> to drive the actual tests.</li></ul>

Apart from the above, Tellurium also provides users the capability to write Tellurium tests and Tellurium data driven tests in pure DSL scripts. The <i>DslScriptExecutor</i> is used to run the .dsl files.<br>
<br>
<h1>Tellurium Subprojects</h1>

Tellurium began as a small core project over a year ago and quickly spawned multiple sub-projects including: <a href='http://code.google.com/p/aost/wiki/UserGuide'>Core</a>, <a href='http://code.google.com/p/aost/wiki/ReferenceProjectGuide'>Reference projects</a>, <a href='http://code.google.com/p/aost/wiki/TelluriumWidget'>Widget extensions</a>, <a href='http://code.google.com/p/aost/wiki/TrUMP'>Trump</a>, and Engine projects as shown in the following diagram:<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TelluriumSubprojects.png?gda=KOo7BEoAAAD5mhXrH3CK0rVx4StVj0LYlNqOmuvSTE_gHzistV7TYU3GrA4woUylGAUAJF3_rdDbYV_6pXuFBwitfyYTkV3q_e3Wg0GnqfdKOwDqUih1tA&gsc=JDDH5gsAAACcJEKey8RXDXypkrHyWRpX'>http://tellurium-users.googlegroups.com/web/TelluriumSubprojects.png?gda=KOo7BEoAAAD5mhXrH3CK0rVx4StVj0LYlNqOmuvSTE_gHzistV7TYU3GrA4woUylGAUAJF3_rdDbYV_6pXuFBwitfyYTkV3q_e3Wg0GnqfdKOwDqUih1tA&amp;gsc=JDDH5gsAAACcJEKey8RXDXypkrHyWRpX</a>

<ul><li>Tellurium Engine: Based on Selenium Core with UI module, jQuery selectors, command bundle, and exception hierarchy support.<br>
</li><li>Tellurium Core: UI module, APIs, DSL, Object to Runtime Locator mapping, and test support.<br>
</li><li>Tellurium Extensions: Dojo Javascript widgets and ExtJS Javascript widgets.<br>
</li><li>Tellurium UI Module Plugin (Trump): A Firefox plugin to automatically generate the UI module after users select the UI elements from the web under testing.<br>
</li><li>Tellurium Maven Archetypes: Maven archetypes to generate skeleton Tellurium JUnit and Tellurium TestNG projects using one Maven command.<br>
</li><li>Tellurium Reference Projects: Use Tellurium project site as examples to illustrate how to use different features in Tellurium and how to create Tellurium test cases.</li></ul>

Tellurium Core has been described in the above section, and here we would like to elaborate more on Tellurium Extensions, the Tellurium UI Module Plugin, and Tellurium Engine.<br>
<br>
<h2>Tellurium Extensions</h2>

<a href='http://code.google.com/p/aost/wiki/TelluriumWidget'>Tellurium Widget</a> is a good way to re-use UI components in testing. Usually, Javascript frameworks provide a lot of widgets. Take the Dojo framework as an example; you can define the DatePicker Dojo widget just like a regular Tellurium UI module. For widgets, it is important to include name space to avoid name collision between different widget modules. If we use "DOJO" as the name space, the widget could be written like "DOJO_DatePicker". We can define the following methods for the DOJO Date Picker widget:<br>
<br>
<pre><code>    public String getCurrentYear(){<br>
        return getText("DatePicker.year.currentYear")<br>
    }<br>
<br>
    public void selectPrevYear(){<br>
        click "DatePicker.year.prevYear"<br>
    }<br>
</code></pre>

To use the Date Picker widget, we can include the compiled jar file and include it like a regular tellurium UI object. For example:<br>
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

To make the framework scalable, Tellurium provides an <i>onWidget</i> method for Tellurium widgets. In this way we can call the widget methods as follows:<br>
<br>
<pre><code>onWidget "dropdown.datePicker", selectPrevYear<br>
</code></pre>

<h2>Tellurium UI Module Plugin (Trump)</h2>

The Tellurium UI Module Plugin (Trump) is used to automatically generate UI modules for users. The Trump IDE looks as follows:<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png?gda=EVgeNEMAAAD5mhXrH3CK0rVx4StVj0LYBWucT0-vkPMbIT-o-BeAq7267uE5ld_gRd1XTkHcEWoytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg'>http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png?gda=EVgeNEMAAAD5mhXrH3CK0rVx4StVj0LYBWucT0-vkPMbIT-o-BeAq7267uE5ld_gRd1XTkHcEWoytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg</a>

The workflow of Trump is shown here:<br>
<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TrUMPDiagramSmall.png?gda=9CFDmEcAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq8nAtNENvOA6NOHkUCAqlOUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD'>http://tellurium-users.googlegroups.com/web/TrUMPDiagramSmall.png?gda=9CFDmEcAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq8nAtNENvOA6NOHkUCAqlOUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&amp;gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD</a>

<ol><li>First, the user clicks on a web page, and the corresponding UI element is pushed into an array. If the user clicks the element again, the UI element is removed from the array.<br>
</li><li>When the user clicks on the "Generate" button, Trump does the following two steps. First, Trump generates an internal tree to represent the UI elements using a grouping algorithm. During the tree generating procedure, extra nodes are generated to group UI elements together based on their corresponding location on the DOM tree. The internal tree is very useful and holds all original data that can be used for customization. Once the internal tree is built, Trump starts the second step, which is to build the default UI module. For each node in the internal tree, Trump generates a UI object based on its tag and whether or not it is a parent node.<br>
</li><li>When the user clicks on the "Customize" button, Trump will pull out the original data held in the internal tree and the current attributes utilized by the UI module to create the "Customize" view. When the user clicks on an element, Trump lists all available optional attributes in the view for users to customize.<br>
</li><li>Trump will try to validate the UI module automatically whenever you generate a new UI module or update it. Trump will evaluate each UI element's XPath the same way that Tellurium generates the runtime XPath from the UI module and check if the generated runtime XPath is unique in the current web page. If it is not unique, you will see a red "X" mark, and you need to modify the element's attribute to make it disappear. If you do not see a red "X", you are finished. You can export the generated UI module to a groovy file and start to write Tellurium tests based on the generated UI module.</li></ol>

<h2>Tellurium Engine</h2>

Up to Tellurium 0.6.0, Tellurium still leveraged Selenium core as the test driving engine. Selenium has a rich set of APIs to act on individual UI elements, and Tellurium can use them directly. Tellurium embedded its own small engine in Selenium core to support the following enhancements:<br>
<br>
<ul><li>jQuery selector support by adding a new jQuery selector locate strategy so that the Selenium core can handle the jQuery selectors passed in from Tellurium core.<br>
</li><li>jQuery selector caching to increase the reuse of previously located DOM references and reduce the UI element locating time.<br>
</li><li>Bulk data retrieval. For example, the following method will get back all data in a table with just one method call, which improves the speed dramatically:</li></ul>

<pre><code>      String getAllTableCellText(String uid) <br>
</code></pre>

<ul><li>New APIs for partial matching of attributes, CSS query, and more.</li></ul>

Using Selenium core as the test driving engine is a quick solution for Tellurium, but it suffers some drawbacks such as low efficiency because Selenium core does not have built-in support for UI modules. We are working on our new engine to gradually replace Selenium core in order to achieve the following goals:<br>
<br>
<ul><li>Command bundle to reduce round-trip time between Tellurium core and the Tellurium Engine.<br>
</li><li>UI module caching at the Engine side to reuse the discovered locator in the UI module no matter if the locator is XPath or a jQuery selector.<br>
</li><li>Group locating at the Engine side to exploit more information about the UI elements in the UI module to help locate UI elements<br>
</li><li>Exception hierarchy so that the Engine returns meaningful error codes back to the Tellurium core.<br>
</li><li>Ajax response notification.</li></ul>


<h1>How to Obtain and Use Tellurium</h1>

<h2>Create a Tellurium Project</h2>

There are three ways: use <a href='http://code.google.com/p/aost/wiki/ReferenceProjectGuide'>the reference project</a> as a base, use the <a href='http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes'>Tellurium Maven archetype</a>, or manually create a Tellurium project using the <a href='http://code.google.com/p/aost/downloads/list'>tellurium jar</a> and a <a href='http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile'>Tellurium configuration file</a>. Alternatively, you could create your own Tellurium Maven project manually using <a href='http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom'>the sample POM file</a>.<br>
<br>
The easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetypes: tellurium-junit-archetype and tellurium-testng-archetype (for Tellurium JUnit test projects and Tellurium TestNG test projects respectively.) As a result, you can create a Tellurium project using one Maven command. For a Tellurium JUnit project, use:<br>
<br>
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=<a href='http://maven.kungfuters.org/content/repositories/snapshots'>http://maven.kungfuters.org/content/repositories/snapshots</a>

and for a Tellurium TestNG project, use:<br>
<br>
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=<a href='http://maven.kungfuters.org/content/repositories/snapshots'>http://maven.kungfuters.org/content/repositories/snapshots</a>

<h2>Create a UI Module</h2>

Tellurium provides Trump for you to automatically create UI modules. Trump can be downloaded from the Tellurium project site:<br>
<br>
<a href='http://code.google.com/p/aost/downloads/list'>http://code.google.com/p/aost/downloads/list</a>

Choose the Firefox 2 or Firefox 3 version depending on your Firefox version, or you can download the Firefox 3 version directly from the Firefox addons site at:<br>
<br>
<a href='https://addons.mozilla.org/en-US/firefox/addon/11035'>https://addons.mozilla.org/en-US/firefox/addon/11035</a>

Once you install it and restart Firefox, you are ready to record your UI modules by simply clicking on the UI element on the web and then clicking the "generate" button. You may like to customize your UI a bit by clicking the "Customize" button. More detail:<br>
<br>
In our example, we open up Tellurium download page<br>
<br>
<a href='http://code.google.com/p/aost/downloads/list'>http://code.google.com/p/aost/downloads/list</a>

and record the download search module as follows:<br>
<br>
<a href='http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPageSmall.png?gda=aeAak1IAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq73MhpfIdLRuVAwbLjuTZtQ7YQyGo5rEgT7iH53cuUInVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD'>http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPageSmall.png?gda=aeAak1IAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq73MhpfIdLRuVAwbLjuTZtQ7YQyGo5rEgT7iH53cuUInVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&amp;gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD</a>

After we customize the UI module, we export it as the module file NewUiModule.groovy to the demo project and add a couple of methods to the class:<br>
<br>
<pre><code>class NewUiModule extends DslContext {<br>
<br>
  public void defineUi() {<br>
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], <br>
           group: "true") <br>
{<br>
      Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])<br>
      InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])<br>
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])<br>
    }<br>
  }<br>
<br>
  //Add your methods here<br>
  public void searchDownload(String keyword) {<br>
    keyType "TelluriumDownload.Input", keyword<br>
    click "TelluriumDownload.Search"<br>
    waitForPageToLoad 30000<br>
  }<br>
<br>
  public String[] getAllDownloadTypes() {<br>
    return getSelectOptions("TelluriumDownload.DownloadType")<br>
  }<br>
<br>
  public void selectDownloadType(String type) {<br>
    selectByLabel "TelluriumDownload.DownloadType", type<br>
  }<br>
}<br>
</code></pre>

<h2>Create Tellurium Test Cases</h2>

Once you create the UI module, you can create a new Tellurium test case NewTestCase by extending TelluriumJavaTestCase class.<br>
<br>
<pre><code>public class NewTestCase extends TelluriumJavaTestCase {<br>
    private static NewUiModule app;<br>
<br>
    @BeforeClass<br>
    public static void initUi() {<br>
        app = new NewUiModule();<br>
        app.defineUi();<br>
    }<br>
<br>
    @Before<br>
    public void setUpForTest() {<br>
        connectUrl("http://code.google.com/p/aost/downloads/list");<br>
    }<br>
<br>
    @Test<br>
    public void testTelluriumProjectPage() {<br>
        String[] allTypes = app.getAllDownloadTypes();<br>
        assertNotNull(allTypes);<br>
        assertTrue(allTypes[1].contains("All Downloads"));<br>
        app.selectDownloadType(allTypes[1]);<br>
        app.searchDownload("TrUMP");<br>
    }<br>
}<br>
</code></pre>

Compile the project and run the new test case.<br>
<br>
<h1>Summary and Future Plans</h1>

Tellurium is robust, expressive, flexible, and reusable with many unique features. Tellurium introduces a new approach to test web applications based on UI modules.<br>
We have released Tellurium 0.6.0 and are working on 0.7.0.<br>
<br>
Tellurium will continue evolving, and we will focus on the following tasks:<br>
<br>
<ul><li>Develop the Engine project in 0.7.0 to provide better support for UI modules and achieve better re-usability, speed performance, and robust responsiveness to change.<br>
</li><li>Tellurium widgets will be re-architected to encapsulate dynamic factors and to make them easier to define and use.<br>
</li><li>The widget extension projects will be implemented to include many widgets from Javascript frameworks such as Dojo and ExtJS so that users can include these widgets in their UI modules simply by their names without the need to know the details of the internal UI elements.<br>
</li><li>Trump will be improved to support frames, pop-up widows, and UI templates. Another big move will be to automatically create UI modules as well as DSL test scripts for non-developers.<br>
</li><li>To better support functional tests, we will introduce a "stage and test binding" concept to make functional tests easier to write and configure.<br>
</li><li>Headless web testing using HtmlUnit could be another interesting topic.<br>
</li><li>Other initiatives include behavior driven test support, IDE plugins, XHTML support, integration with other testing framework like Fitnesse, and porting to other languages.</li></ul>

<h1>Acknowledgments</h1>

Thanks to Ramesh Ramamurthy for editing this article.<br>
<br>
<h1>Resources</h1>

<ul><li><a href='http://code.google.com/p/aost/'>Tellurium Project website</a>
</li><li><a href='http://groups.google.com/group/tellurium-users'>Tellurium User Group</a>
</li><li><a href='http://code.google.com/p/aost/wiki/UserGuide'>Tellurium User Guide</a>
</li><li><a href='http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf'>Tellurium User Guide PDF draft</a>.<br>
</li><li><a href='http://code.google.com/p/aost/wiki/TrUMP'>Tellurium UI Model Firefox Plugin (TrUMP) 0.1.0</a>
</li><li><a href='http://seleniumhq.org/'>Selenium</a>
</li><li><a href='http://webtest.canoo.com'>Canoo WebTest</a>
</li><li><a href='http://twill.idyll.org/'>Twill</a>
</li><li><a href='http://groovy.codehaus.org/'>Groovy</a>
</li><li><a href='http://jquery.com/'>JQuery</a>
</li><li><a href='http://www.junit.org/'>JUnit</a>
</li><li><a href='http://testng.org/doc/documentation-main.html'>TestNG</a>
</li><li><a href='http://htmlunit.sourceforge.net'>HtmlUnit</a>