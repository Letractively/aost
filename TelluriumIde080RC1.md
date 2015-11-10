

# Introduction #

Tellurium is an expressive testing framework and it uses [UI modules](http://code.google.com/p/aost/wiki/UserGuide070UIObjects#UI_Module) to define the web UIs under testing. [UIDs](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#UiID_Attribute) are used to reference UI elements instead of hardcoded locators. About two months ago, it was still a dream to automatically generate Tellurium DSL script like Selenium IDE does, but now, the dream came true.

Tellurium IDE is a Firefox plugin to record user actions and automatically generate Tellurium DSL script. Tellurium IDE is developed from the codebase of Tellurium UI Module Firefox Plugin  [Trump](http://code.google.com/p/aost/wiki/UserGuide070TelluriumSubprojects#Tellurium_UI_Module_Plugin_%28TrUMP%29), which is used to record user's selection of UIs and automatically generate UI modules. Tellurium IDE is a step further to automatically generate Tellurium commands and UI modules. The exported Groovy DSL script could be run directly with [Tellurium rundsl.groovy script](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Run_DSL_Script).

## Feature ##

Telurium IDE 0.8.0 RC1 is a feature rich release:
  * Record user actions
  * Automatically generate UI module and DSL script
  * Replay generated DSL script
  * Customize, add, and update Tellurium commands
  * assertion support
  * Export DSL Script to Groovy DSL or Java test case
  * Test runner is powered by Tellurium new engine

## Design ##

http://tellurium-developers.googlegroups.com/web/trumpworkflow.png?gda=9farzUMAAAC3f0S777RoMipgwttRc5E0rE3sb7K01jCxhcA7IRHMaYREyMfmzRjWt2IyFXBxC30ytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg

The workflow is shown as above:
  1. User acts on the web and Trump records the events
  1. The events are stored as commands with fields such as name, value, and reference id
  1. In the meanwhile, the dom node and its reference id are pushed into a list
  1. User select on the web and Trump also records the events
  1. The event does not have a command associated with it and is only pushed to the DOM list
  1. An inner tree is built based on the dom list
  1. The UI module is generated from the inner tree
  1. Additional UI elements are picked up by an algorithm
  1. At the same time, a reference id to UID map is created
  1. The UI modules are put into the top of output script file
  1. The reference ids in the command list are replaced with their UIDs
  1. The updated command list is turned into Tellurium DSL script

# Installation #

You can download Tellurium IDE 0.8.0 RC1 from [here](http://aost.googlecode.com/files/TelluriumIDE-0.8.0-RC1.xpi). Open it up in Firefox 3 and install it.

http://tellurium-users.googlegroups.com/web/TelluriumIDEInstallation.png?gda=ldGcEk4AAADzylifMuGHtadsGrIEvqZXRDnCyMFlZjoGnHO17f7J5RDLG6iaG_mHBFoyqx_XKKe8rpZNtA1kjyIDQZHzzBlI47Cl1bPl-23V2XOW7kn5sQ&gsc=rwaeIwsAAABq-cxWzGYJFOJfDU--Biod

This plugin is also available at [Firefox addon site](https://addons.mozilla.org/en-US/firefox/addon/217284/).

Tellurium IDE 0.8.0 RC1 tutorial is available online at:
<a href='http://www.youtube.com/watch?feature=player_embedded&v=yVIBY8QzWzE' target='_blank'><img src='http://img.youtube.com/vi/yVIBY8QzWzE/0.jpg' width='425' height=344 /></a>

# Usage #

## Record ##

The record button is on by default. First, record user actions and the DOM nodes. A reference ID is generated for each recorded DOM node.

http://tellurium-users.googlegroups.com/web/TelluriumIDERecordView.png?gda=9sc9jEwAAADzylifMuGHtadsGrIEvqZX6EQSG-Q9tkGBWlgjEgzAjMx10vtAIWPB37ZH86WX23MSpXuADwlmQ2U0jcQaQQWx_Vpvmo5s1aABVJRO3P3wLQ&gsc=xjJamgsAAAC59Gng8z5Qy5sBxjkZ-0PD

## Replay ##

Press the record button to stop recording. UI modules and test commands are generated automatically. Use Run or Step button to run the whole commands or a single command. The slider on the top right screen determines the delay between two commands. You can assign the return value to a variable and then use assertions to compare the result. To differentiate a variable from a String, you need to use "`var name`" syntax for a variable.

http://tellurium-users.googlegroups.com/web/TelluriumIDECommandViewAssertion.png?gda=5Degt1YAAADzylifMuGHtadsGrIEvqZXEY5GxZ10VGq9YR1SKTnX691yIRGPql2sUg_wexYEWkQhWUVHr_q0Jl4M23uJko9id64s9EZTLP9aL_4jXQez_BPhGuxsWDLdLep2NLleRSE&gsc=txxv_QsAAAA-vAePfxKkJQy-212UCZNM

## Customize ##

During the run, you can pause the test and customize the UI module. You can also update or insert a command.

http://tellurium-users.googlegroups.com/web/TelluriumIDECommandViewCustomize.png?gda=BXR8CFYAAADzylifMuGHtadsGrIEvqZX6EQSG-Q9tkGBWlgjEgzAjN1yIRGPql2sUg_wexYEWkShR1S4krmxhj0tTtlNpUN4d64s9EZTLP9aL_4jXQez_BPhGuxsWDLdLep2NLleRSE&gsc=xjJamgsAAAC59Gng8z5Qy5sBxjkZ-0PD

## Source ##

The source is shown on the source view.

http://tellurium-users.googlegroups.com/web/TelluriumIDESourceView.png?gda=A43yREwAAADzylifMuGHtadsGrIEvqZXEY5GxZ10VGq9YR1SKTnX61YcCTzBHlOy02h_YJwQ91ISpXuADwlmQ2U0jcQaQQWx_Vpvmo5s1aABVJRO3P3wLQ&gsc=txxv_QsAAAA-vAePfxKkJQy-212UCZNM

## Export ##

You can export the generated test script to either Groovy DSL script or a UI module class and a Java test case. In the meanwhile, you can paste the code to clipboard.

http://tellurium-users.googlegroups.com/web/TelluriumIDEExportMenu.png?gda=j2qv3UwAAADzylifMuGHtadsGrIEvqZXEY5GxZ10VGq9YR1SKTnX66dt_Hfg7eIfrxPn9L3dV5-047q73-aFuNjPYpy7W7T-_Vpvmo5s1aABVJRO3P3wLQ&gsc=txxv_QsAAAA-vAePfxKkJQy-212UCZNM

# Run Test #

## DSL Script ##

Tellurium 0.7.0 provides a rundsl.groovy script for users to run DSL test script. The rundsl.groovy uses [Groovy Grape](http://groovy.codehaus.org/Grape) to automatically download all dependencies and then run DSL script. Here is the detailed guide on [how to run DSL script](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Run_DSL_Script).

The generated DSL script is as follows and can be downloaded from [here](http://aost.googlecode.com/files/TelluriumIDEDemo.tar.gz).

```
/**
 *      This Groovy DSL script is automatically generated by Tellurium IDE 0.8.0.
 *
 *      To run the script, you need Tellurium rundsl.groovy script. 
 *      The detailed guide is available at:
 *              http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Run_DSL_Script
 *
 *      For any problems, please report to Tellurium User Group at: 
 *              http://groups.google.com/group/tellurium-users
 *
 */

                ui.Form(uid: "Form", clocator: [tag: "form", method: "GET", action: "list"]){
                                Selector(uid: "Can", clocator: [tag: "select", direct: "true", name: "can", id: "can"])
                                InputBox(uid: "Searchq", clocator: [tag: "input", type: "text", name: "q", id: "searchq"])
                                SubmitButton(uid: "Search", clocator: [tag: "input", direct: "true", type: "submit", value: "Search"])
                        }

                ui.Container(uid: "Mt", clocator: [tag: "table", id: "mt"]){
                                UrlLink(uid: "Wiki", clocator: [tag: "a", text: "Wiki"])
                                UrlLink(uid: "RegexpProjectsHome", clocator: [tag: "a", text: "regexp:Project\\sHome"])
                                UrlLink(uid: "Downloads", clocator: [tag: "a", text: "Downloads"])
                                UrlLink(uid: "Issues", clocator: [tag: "a", text: "Issues"])
                                UrlLink(uid: "Source", clocator: [tag: "a", text: "Source"])
                                UrlLink(uid: "Administer", clocator: [tag: "a", text: "Administer"])
                        }

                connectSeleniumServer()
                connectUrl "http://code.google.com/p/aost/downloads/list"
                selectByLabel "Form.Can", "regexp:\\sCurrent downloads"
                selectByLabel "Form.Can", "regexp:\\sFeatured downloads"
                type "Form.Searchq", "test"
                def x = getValue("Form.Searchq")
                assertEquals x, "test"
                click "Form.Search"
                waitForPageToLoad 30000
```


## Java Test Case ##

Similarly, Tellurium IDE could export the test script as a Groovy UI module file and a Java test file. For more details on how to create and run Tellurium Java test cases, please see [Tellurium test support](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Testing_Support).

## Known Issues ##

  * Tellurium IDE does not work in Mac OS X.

# What's Next #

Be aware that Tellurium IDE 0.8.0 RC1 is work in process, please report any problems back to [Tellurium user group](http://groups.google.com/group/tellurium-users).

The next step includes:
  * Let user select/unselect UI elements when customize UI
  * Improve UI Module generating algorithm
  * Generalize UIs to UI templates such as List and Table
  * Finish up new engine API

Also, we are looking for JavaScript experts to join our team to help with the Tellurium IDE and Tellurium Engine development. If you are interested, please send emails to telluriumsource at gmail dot com.

Thanks in advance.

# Resources #

  * [Tellurium IDE on Firefox addon site](https://addons.mozilla.org/en-US/firefox/addon/217284/)
  * [Tellurium IDE 0.8.0 RC1 tutorial video](http://www.youtube.com/watch?v=yVIBY8QzWzE/)
  * [Tellurium Project Home](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)
  * [Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Released)