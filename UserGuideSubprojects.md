(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



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