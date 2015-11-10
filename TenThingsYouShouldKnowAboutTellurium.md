

## How Long Has Tellurium Been There ##

Tellurium will be one year old in June 2009 if we count the date from the day it became an open source project. But actually, Tellurium had been through two phases of prototyping before that. The first prototype was created in 2007 to test our company's  Dojo web applications, which was basically a Java framework based on Spring XML wiring and no UI modules, you have to use factories to create all UI objects. As a result, it was not convenient to use. The second prototype was there in early 2008 to improve the usability of the first prototype. The UI module was introduced in the second prototype. Both prototypes had been used for couple internal projects before it was re-written in Groovy and became an open source project in June 2008. You may notice that prototype framework is called AOST and it was officially renamed to the Tellurium Automated Testing framework (Tellurium) in July 2008 when it moved out of the prototyping phase and became a team project.

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

With the UI module, Tellurium automatically generates runtime locators for you and you do not need to define XPaths or other types of locators by yourself. Tellurium is robust, expressive, flexible, and reusable. Want to know the reason why? Please read "Tellurium: A New Approach for Web Testing" at,

http://code.google.com/p/aost/wiki/Tellurium_A_New_Approach_for_Web_Test

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

mvn archetype:create -DgroupId=your\_group\_id -DartifactId=your\_artifact\_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots

and for a Tellurium TestNG project, use

mvn archetype:create -DgroupId=your\_group\_id -DartifactId=your\_artifact\_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots

For more details,  please read "Tellurium Maven archetypes",

http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes


## Where can I find API documents for Tellurium ##

The user guide for Tellurium DSLs, other APIs, and default UI objects could be found at

http://code.google.com/p/aost/wiki/UserGuide

## Is There a Tellurium Tutorial Available ##

Tellurium provides very detailed tutorials including basic examples, advanced examples, data driven testing examples, and Dsl script examples. Apart from that, We also provide Tellurium Tutorial Series. Please use Tellurium tutorial wiki page as your starting point,

http://code.google.com/p/aost/wiki/Tutorial

We also provide a quick start "Ten Minutes To Tellurium" at

http://code.google.com/p/aost/wiki/TenMinutesToTellurium

## Tellurium Future Directions ##

Tellurium 0.6.0 added jQuery Selector support and a cache mechanism to increase the reuse of UI elements. From the next release, i.e., 0.7.0, we will work on our own Engine project to better support UI modules and achieve more robust performance and better speed. In the meanwhile, We will improve our TrUMP plugin to automatically generate DSL testing scripts for non-developers.