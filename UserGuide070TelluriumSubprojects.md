(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))






# Tellurium Subprojects #

Tellurium began as a small core project and quickly generated multiple sub-projects including: JUnit and TestNG, Maven Archetypes, TrUMP, [Widget extensions](http://code.google.com/p/aost/wiki/TelluriumWidget), [Core](http://code.google.com/p/aost/wiki/UserGuide), and Engine projects as shown in the following diagram in Figure 3-1:

**Figure 3-1 Tellurium Subprojects**

http://tellurium-users.googlegroups.com/web/NewTelluriumSubProjects.png?gda=o-ZCE00AAAA7fMi2EBxrNTLhqoq3FzPr5YJ3INtuBNJW9vEluZUY6gWcRD5mEYHWd5K6yh20QaANn3YpWhqxIh1wjgDZawPl5Tb_vjspK02CR95VRrtmeQ&gsc=T5RWbwsAAAAqnE8-k4IrUFInYdtt3cMF

  * Tellurium Reference Projects: Use the Tellurium project site for examples illustrating how to use different features in Tellurium and how to create Tellurium test cases.
  * Tellurium Maven Archetypes: Maven archetypes to generate skeleton Tellurium JUnit and Tellurium TestNG projects using one Maven command.
  * Tellurium UI Module Plugin (Trump): A Firefox plugin to automatically generate the UI module after users select the UI elements from the web under testing.
  * Tellurium Extensions: Dojo Javascript widgets and ExtJS Javascript widgets.
  * Tellurium Core: UI module, APIs, DSL, Object to Runtime Locator mapping, and test support.
  * Tellurium Engine: Based on Selenium Core with UI module, jQuery selectors, command bundle, and exception hierarchy support.
  * Tellurium UDL: Tellurium UID Description Language (UDL) grammars and parser.

## Tellurium Reference Projects ##

Tellurium has created [reference projects](http://code.google.com/p/aost/wiki/TelluriumNewReferenceProjects) to demonstrate how to use Tellurium for a user’s testing project. In the reference projects, Tellurium project web site is used as an example of illustrating how to write real-world Tellurium tests. The reference projects only use tellurium jar files.

Tellurium 0.7.0 merged the original tellurium-junit-java and tellurium-testng-java two reference projects into a new reference project tellurium-website and add a new reference project ui-examples. The first one is real world tellurium tests using tellurium website as an example and the second one uses html source, mostly contributed by Tellurium users, to demonstrate the usage of different Tellurium UI objects.

### Tellurium Website Project ###

The tellurium website project illustrates the following usages of Tellurium:

  * How to create your own UI Objects and wire them into Tellurium core
  * How to create UI module files in Groovy
  * How to create JUnit or TestNG tellurium testing files in Java
  * How to create and run DSL scripts
  * How to create Tellurium Data Driven tests


### Tellurium ui-examples Project ###

Some of the html sources in the ui-examples project are contributed by tellurium users. Thanks for their contributions. ui-examples project includes the following different types of UI objects.
  * Container
  * Form
  * List
  * Table
  * Repeat

## Tellurium Maven Archetypes ##

Tellurium provides [three maven archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes). For example: tellurium-junit-archetype for Tellurium JUnit test projects, tellurium-testing-archetype for Tellurium TestNG test projects, and tellurium-widget-archetype for Tellurium Widget projects.

### Tellurium JUnit Archetype ###

Run the following Maven command to create a new JUnit test project:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
             -DarchetypeArtifactId=tellurium-junit-archetype \
             -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0
```

Without adding the Tellurium Maven repository, specify it in the command line as:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
                     -DarchetypeArtifactId=tellurium-junit-archetype \
                     -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0 \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

To create a Tellurium project based on Tellurium 0.8.0 SNAPSHOT, you should use the Maven archetype 0.7.0-SNAPSHOT. To create a JUnit project, use the following Maven command:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.8.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

### Tellurium TestNG Archetype ###

For a TestNG archetype project, use a different archetype:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
                     -DarchetypeArtifactId=tellurium-testng-archetype \
                     -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0 \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

To create a TestNG project, use the following command:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.8.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

### Tellurium Widget Archetype ###

To create a Tellurium UI widget project, we can use Tellurium Widget archetype as follows,

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-widget-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.8.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```


## Tellurium UI Module Plugin (TrUMP) ##

Go to the Tellurium project download page and download the TrUMP xpi file or download the Firefox 3 version directly from the Firefox Addons site at:

[https://addons.mozilla.org/en-US/firefox/addon/11035](https://addons.mozilla.org/en-US/firefox/addon/11035)

The Tellurium UI Module Plugin (TrUMP) automatically generates UI modules for users. An example of the TrUMP IDE is shown in Figure 3-2:

**Figure 3-2 TrUMP IDE**

http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png?gda=EVgeNEMAAAD5mhXrH3CK0rVx4StVj0LYBWucT0-vkPMbIT-o-BeAq7267uE5ld_gRd1XTkHcEWoytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg


The workflow of TrUMP is shown in Figure 3-3:

**Figure 3-3 TrUMP Workflow**

http://tellurium-users.googlegroups.com/web/TrUMPDiagramSmall.png?gda=9CFDmEcAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq8nAtNENvOA6NOHkUCAqlOUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

### TrUMP Workflow Procedure ###

  1. Click onto a web page. The corresponding UI element is pushed into an array. <br />**Note**: If the element is clicked again, the UI element is removed from the array.
  1. Click the "Generate" button. TrUMP implements the following two steps:
    * TrUMP generates an internal tree to represent the UI elements using a grouping algorithm. During the tree generating procedure, extra nodes are generated to group UI elements together based on their corresponding location on the DOM tree. The internal tree is very useful and holds all original data that can be used for customization.
    * Once the internal tree is built, TrUMP starts the second step, which is to build the default UI module. For each node in the internal tree, TrUMP generates a UI object based on its tag and whether or not it is a parent node.
  1. Click the "Customize" button. TrUMP pulls out the original data held in the internal tree and the current attributes utilized by the UI module to create the "Customize" view. When the user clicks on an element, TrUMP lists all available optional attributes in the View for users to customize.
  1. TrUMP attempts to validate the UI module automatically whenever a new UI module is generated or updated. TrUMP evaluates each UI element's XPath the same way that Tellurium generates the runtime XPath from the UI module and verifies if the generated runtime XPath is unique in the current web page.
    * If it is not unique, a red "X" mark is displayed, and the user should modify the element's attribute to make it disappear.
    * If a red "X is not displayed", the validation is completed. The user can export the generated UI module to a groovy file and start to write Tellurium tests based on the generated UI module. <br />To use TrUMP, complete the following steps:

  1. Select "Tools" > "TrUMP IDE" in Firefox.
    * The "Record" button is on by default.
    * Click on "Stop" to stop recording.
  1. Start to use the TrUMP IDE to record specific UI elements that were selected on the WEB. For example, open the Tellurium Download page and click the search elements and the three links as shown in Figure 3-4:

**Figure 3-4 Tellurium Download Page**

http://tellurium-users.googlegroups.com/web/TrUMPRecordSmall.png?gda=7n7qpkYAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-zOJScwBCYlcwvHeRiAw313riz0RlMs_1ov_iNdB7P8E-Ea7GxYMt0t6nY0uV5FIQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc


The blue color indicates selected element. Click the selected element again to de-select it. Then, click on the "Generate" button to create the Tellurium UI Module. The user is automatically directed to the "Source" window. See Figure 3-5.

**Figure 3-5 Tellurium Download Page Source Window**

http://tellurium-users.googlegroups.com/web/TrUMPGenerateSmall.png?gda=1P97bkgAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8exKkp42KQN7AGtkLZRcnsBYlzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc


Click the "Customize" button to change the UI module such as UIDs, group locating option, and attributes selected for the UI module. See Figure 3-6

**Figure 3-6 Tellurium Download Page Changed UI Module**

http://tellurium-users.googlegroups.com/web/TrUMPCustomizeSmall.png?gda=W0yA4EkAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-dpsLnqVTCuG8a16FLLGJva4bZDq9fZORtBwZjSHGJkhAioEG5q2hncZWbpWmJ7IQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc


One red "X" mark is displayed, indicating the UI element's XPath is not unique. Select group, or add more attributes to the UI element. The user sees the new customized UI as shown in Figure 3-7:

**Figure 3-7 Tellurium Download Page Customized UI**

http://tellurium-users.googlegroups.com/web/TrUMPCustomizedSmall.png?gda=AUZMQkoAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8e-uLN10ihJ-PJyev33cmPNkLFhim5d1LJsoAMKqscxTI_e3Wg0GnqfdKOwDqUih1tA&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

**Note:** The red "X" mark is removed because Tellurium turned on the group locating and the element's xpath is now unique. In the meantime, the UI module in the source tab is automated and updated once the "Save" button is clicked.

The "Show" button shows the actual Web element on the web for which the UI element is represented. See Figure 3-8.

**Figure 3-8 Tellurium Download Page Web Element**

http://tellurium-users.googlegroups.com/web/TrUMPCustomizedSourceSmall.png?gda=i_g_1lAAAAD5mhXrH3CK0rVx4StVj0LYCNu4TcAUiThf-ed6d-A8ewl-ge40lZM5QmFCqlPrr96JFTmvmvXK4kDlwX5DWO5WbcVT3VtYGKLco-_l-8AzjQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

At this point, export the UI module to a groovy file. Be aware that if any error is seen complaining about the directory, first check the "export directory" in Options > Settings and set it to "C:\" or other windows directory for the Windows system before you export the file.

For Linux, the user may find there is no "OK" button on the option tab, which is caused by the configure "browser.preferences.instantApply" is set to true by default. Point the firefox to "about:config" and change the option to false.

Once this is completed, the user sees the "OK" button. See Figure 3-9.

**Figure 3-9 Tellurium Download Page UI Module in Groovy File**

http://tellurium-users.googlegroups.com/web/ExportToGroovySmall.png?gda=HTfW2kkAAAD5mhXrH3CK0rVx4StVj0LYjifwtTdV1ss7lVmTlgK0W813v4RlmnSWza84shDEVE3a4bZDq9fZORtBwZjSHGJkhAioEG5q2hncZWbpWmJ7IQ&gsc=v-NkrgsAAACcPBjG_-VbM14GMV-6S4Xc

Open the groovy file to view the following:

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

## Tellurium Widget Extensions ##

The Tellurium Widget Extensions are a way of re-using UI components when testing. Tellurium provides the capability to composite UI objects into a widget object and then use that widget directly as if using a Tellurium UI object. The advantage is that a user does not need to deal with the UI at the link or button level for the widget, just work on the high level methods.

Another advantage is that this widget is reusable.

Usually, Java script frameworks provide a number of widgets. For example. the Dojo framework: Tellurium uses the widget DatePicker to prototype the Tellurium widget. For widgets, it is important to include name space to avoid name collision among different widget modules. For example, what is Dojo and ExtJs? Both have the widget Date Picker. After adding the name space, the widget is named as "DOJO\_DatePicker".

The DataPicker widget is defined as a normal Tellurium UI module:

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

Here the XPath is used directly for demonstration purposes only. Usually the composite locator is used instead.

Also, Tellurium defines the following widget methods:

```
    public String getCurrentYear(){
        return getText("DatePicker.year.currentYear")
    }

    public void selectPrevYear(){
        click "DatePicker.year.prevYear"
    }
```

The widget is treated as a Tellurium UI object and the builder is the same as regular Tellurium objects:

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

Now, the user hooks the widget into the Tellurium Core. Each widget module is compiled as a separate jar file and Tellurium defines a bootstrap class to register all the widgets inside the module.

By default, the full class name of the bootstrap class is `org.tellurium.widget.XXXX.Init`, where the class Init implements the `WidgetBootstrap` interface to register widgets and _XXXX_ stands for the widget module name. It is DOJO in this case.

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

Then in the tellurium configuration file `TelluriumConfig.groovy`, include the module name there:

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="dojo"
        }
    }
```

If the user’s own package name is used for the bootstrap class, for example, `com.mycompay.widget.Boot`, then specify the full name here.

```
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
            included="com.mycompay.widget.Boot"
        }
    }
```

**Note:** Load multiple widget modules into the Tellurium Core framework by definition.

```
  included="dojo, com.mycompay.widget.Boot"
```

To use the widget, treat a widget as a regular Tellurium UI object. For example:

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

Then on the module file DatePickerDemo, call the widget methods instead of dealing with low level links, buttons, etc.

To make the testing more expressive, Tellurium provides an onWidget method:

```
  onWidget(String uid, String method, Object[] args)
```

In that way, call the widget methods as follows:

```
   onWidget "dropdown.datePicker", selectPrevYear
```

## Tellurium Core ##

The Tellurium Core does **Object to Locator Mapping (OLM)** automatically at runtime so that UI objects are simply defined by their attributes. For example, **Composite Locators** denoted by the "clocator".

Furthermore, Tellurium Core uses the **Group Locating Concept (GLC)** to exploit information inside a collection of UI components to help find their locators.

The Tellurium Core defines a new **Domain Specific Language (DSL)** for web testing. One very powerful feature of Tellurium Core is that a user can use **UI templates& to represent many identical UI elements or dynamic sizes of different UI elements at runtime. This is extremely useful to test a dynamic web such as a data grid.**

One typical data grid example is as follows:

```
ui.Table(uid: "table", clocator: [:]){
   InputBox(uid: "{row: 1, column: 1}", clocator: [:])
   Selector(uid: "{row: all, column: 2}", clocator: [:])
   UrlLink(uid: "{row: 3, column: all}", clocator: [:])
   TextBox(uid: "{row: all, column: all}", clocator: [:])
} 
```

[Data Driven Testing](http://code.google.com/p/aost/wiki/DataDrivenTesting) is another important feature of Tellurium Core where a user can define data format in an expressive way. In the data file, a user specifies which test to run, the input parameters, and expected results.

Tellurium automatically binds the input data to variables defined in the test script and runs the tests specified in the input file. The test results are recorded by a test listener and the output is in different formats. For example, an XML file.

The Tellurium Core is written in Groovy and Java. The test cases can be written in Java, Groovy, or pure DSL. A user does not need to know Groovy before using it because the UI module definition and actions on UIs are written in DSLs and the rest may be written in Java syntax.

## Tellurium Engine ##

Up to Tellurium 0.6.0, Tellurium still leveraged Selenium core as the test driving engine. Selenium has a rich set of APIs to act on individual UI elements, and Tellurium can use them directly. Tellurium embedded its own small engine in the Selenium core to support the following enhancements:

  * jQuery selector support by adding a new jQuery selector locate strategy so that the Selenium core can handle the jQuery selectors passed in from Tellurium core.
  * jQuery selector caching to increase the reuse of previously located DOM references and reduce the UI element locating time.
  * Bulk data retrieval. For example, the following Tellurium method obtains all data in a table with just one method call, thus improving speed dramatically:

`String getAllTableCellText(String uid) `

  * New APIs for partial matching of attributes, CSS query, and more.

Using Selenium core as the test driving engine is a quick solution for Tellurium, but it suffers some drawbacks such as low efficiency because Selenium core does not have built-in support for UI modules.

Tellurium is developing a new engine to gradually replace the Selenium core in order to achieve the following goals:

  1. Command bundle to reduce round-trip time between Tellurium core and the Tellurium Engine
  1. UI module caching at the Engine side to reuse the discovered locator in the UI module no matter if the locator is XPath or a jQuery selector
  1. Group locating at the Engine side to exploit more information about the UI elements in the UI module to help locate UI elements
  1. Exception hierarchy so that the Engine returns meaningful error codes back to the Tellurium core
  1. Ajax response notification