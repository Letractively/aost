# Tellurium Automated Testing Framework #


## Looking for Contributors ##

  * We are looking for a devoted and self-motivated developer with ambition and open source project management experience to be the joint project owner (but first start as a sub-project leader)
  * We are looking for a dedicated JavaScript expert as the Engine subproject leader
  * We are looking for a dedicated JavaScript expert as the Tellurium IDE subproject leader
  * We are looking for Java, Groovy, Javascript/jQuery developers, and technical writers to join our team.

Please email telluriumsource at gmail dot com if interested.

## Latest Update ##

  * [Tellurium Engine: An Object-based Test Driving Engine](http://code.google.com/p/aost/wiki/TelluriumEngineIntroduction)
  * [Tellurium IDE 0.8.0 RC2](http://code.google.com/p/aost/wiki/TelluriumIde080RC2) is released
  * Haroon published an article on _Test Experience_ magazine: [Tellurium Automated Testing Framework](http://aost.googlecode.com/files/testingexperience12_12_10_Article.pdf)
  * [InfoQ Article: Introducing the Tellurium Automated Testing Framework](http://www.infoq.com/articles/tellurium_intro)
  * [TelluriumWorks 0.8.0 RC1: A Tellurium DSL Script Runner](http://code.google.com/p/aost/wiki/TelluriumWorks080RC1)
  * [Tellurium IDE 0.8.0 RC1 tutorial video](http://www.youtube.com/watch?v=yVIBY8QzWzE/)
  * [Tellurium IDE 0.8.0 RC1: Tellurium test script generation](http://code.google.com/p/aost/wiki/TelluriumIde080RC1)
  * [Tellurium UI Module Firefox Plugin (Trump) 0.8.0 RC1: New Face and More Power.](http://code.google.com/p/aost/wiki/TelluriumTrump080RC1)
  * [Tellurium: A Wrapper of Selenium?](http://code.google.com/p/aost/wiki/TelluriumAWrapOfSelenium)

## Introduction ##

The Tellurium Automated Testing Framework (**_Tellurium_**) is a **UI module-based** automated testing framework for web applications. The UI module is a collection of UI elements you group together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the Google search UI module can be expressed as follows,

```
ui.Container(uid: "GoogleSearchModule", clocator: [tag: "td"], group: "true"){
   InputBox(uid: "Input", clocator: [title: "Google Search"])
   SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
}
```

The UI module makes it possible to build UI elements' locators at run time. The framework does **Object to Locator Mapping** (OLM) automatically at run time so that you can define UI objects simply by their attributes, i.e., **Composite Locators** denoted by the "clocator". Furthermore, Tellurium uses the **Group Locating Concept** (GLC) to exploit information inside a collection of UI components to help finding their locators and the GLC is denoted by the "group" attribute in the above UI module.

The Tellurium framework defines a new **Domain Specific Language** (DSL) for web testing. Still take the above Google search module as an example, you can use the following DSLs
to do a Google search,

```
type "GoogleSearchModule.Input", "Tellurium test"
click "GoogleSearchModule.Search"
waitForPageToLoad 30000
```

One very powerful feature of Tellurium is that you can use **UI templates** to represent many identical UI elements or dynamic size of different UI elements at runtime, which are extremely useful to test dynamic web such as a data grid. One typical data grid example is as follows,

```
ui.Table(uid: "table", clocator: [:]){
   InputBox(uid: "{row: 1, column: 1} as Input", clocator: [:])
   Selector(uid: "{row: all, column: 2}", clocator: [:])
   UrlLink(uid: "{row: 3, column: all}", clocator: [:])
   TextBox(uid: "{row: all, column: all}", clocator: [:])
} 
```

[Data Driven Testing](http://code.google.com/p/aost/wiki/DataDrivenTesting) is another important feature of Tellurium. You can define data format in an expressive way. In you data file, you can specify which test you want to run, the input parameters, and expected results. Tellurium automatically binds the input data to variables defined in your test script and run the tests you specified in the input file. The test results will be recorded by a test listener and output in different formats, for example, an XML file.

[Tellurium UI Model Plugin](http://code.google.com/p/aost/wiki/HowTrUMPWorks) (Trump) is a Firefox plugin to automatically create UI modules for users. [Tellurium IDE](http://code.google.com/p/aost/wiki/TelluriumIde080RC1) is a Firefox plugin to record, generate, and run Tellurium DSL test scripts. [TelluriumWorks](http://code.google.com/p/aost/wiki/TelluriumWorks080RC1) is a Java Swing IDE to run Tellurium DSL test scripts.

In addition, Tellurium provides you the capability to composite UI objects into a [Tellurium widget](http://code.google.com/p/aost/wiki/TelluriumWidget) object. You can pack Tellurium widgets as a jar file and then use a Tellurium widget just like a single tellurium UI object once you include the jar file. As a result, Tellurium is **robust**, **expressive**, **flexible**, and **reusable**.


In summary, the main features of Tellurium include:

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

The Tellurium Core is written in Groovy and Java. Tellurium Engine, Tellurium IDE, and Trump are implemented using JavaScript and jQuery. The test cases can be written in Java, Groovy, or pure DSL. You do not really need to know Groovy before you use it because the UI module definition and actions on UIs are written in DSLs and the rest could be written in Java syntax. Detailed [User Guide](http://code.google.com/p/aost/wiki/UserGuide), [Frequent Asked Questions](http://code.google.com/p/aost/wiki/FAQ), and [illustrative examples](http://code.google.com/p/aost/wiki/Tutorial) are provided. We expect and welcome your [contributions](http://code.google.com/p/aost/wiki/HowToContribute).

For a quick start, please read [Ten Minutes To Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium).

## Tellurium Subprojects ##

Tellurium subprojects include [UDL](http://code.google.com/p/aost/wiki/TelluriumUIDDescriptionLanguage), [Injector](http://code.google.com/p/aost/wiki/DependencyInjectionWithGroovyASTTransformation), [Core](http://code.google.com/p/aost/wiki/UserGuide), [Reference projects](http://code.google.com/p/aost/wiki/ReferenceProjectGuide), [Widget extensions](http://code.google.com/p/aost/wiki/TelluriumWidget), [TrUMP](http://code.google.com/p/aost/wiki/TrUMP), [Tellurium IDE](http://code.google.com/p/aost/wiki/TelluriumIde080RC1), [TelluriumWorks](http://code.google.com/p/aost/wiki/TelluriumWorks080RC1), and [Engine](http://code.google.com/p/aost/wiki/TelluriumEngineIntroduction) projects as shown in the following diagram.

  * Tellurium UDL: Tellurium UID Description Language (UDL) grammars and parser.
  * Tellurium Injector: Groovy dependency injection module for Tellurium.
  * Tellurium Engine: Test driving engine with group locating, CSS selector, command bundle, and UI module caching support.
  * Tellurium Core: UI module, APIs, DSL, Object to Runtime Locator mapping, and test support.
  * Tellurium Extensions: Dojo Javascript widgets and ExtJS Javascript widgets.
  * Tellurium UI Module Plugin (Trump): A Firefox plugin to automatically generate the UI module after users select the UI elements from the web under testing.
  * Tellurium IDE: A Firefox plugin to record, generate, and run Tellurium DSL test scripts.
  * TelluriumWorks: A Java Swing IDE to run Tellurium DSL test scripts.
  * Tellurium Maven Archetypes: Maven archetypes to generate skeleton Tellurium JUnit and Tellurium TestNG projects using one Maven command.
  * Tellurium Reference Projects: Sample projects to illustrate how to use different features in Tellurium and how to create Tellurium test cases.

## How to use Tellurium ? ##

There are three ways, i.e., use [the reference project](http://code.google.com/p/aost/wiki/ReferenceProjectGuide) as a base, use [Tellurium Maven archetype](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes), or manually create Tellurium project using [tellurium jar](http://code.google.com/p/aost/downloads/list) and [Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile). Alternatively, you could create your own Tellurium Maven project manually using [the sample POM file](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom).

http://tellurium-users.googlegroups.com/web/HowToUseTellurium.png?gda=E2fneEcAAACXZPxEX7Ki-M5C2JpeBoXXwOvr7XA0t7SnOHKVzf4DhFd6vDxrTQI8X2xdNkWs9mIVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=YyVqmwsAAABmHtz3tZj6NRBcOVGYgXTk


## Tellurium Maven Repository ##

Tellurium supports Maven 2 and here is the guide on [how to use Maven for Tellurium](http://code.google.com/p/aost/wiki/MavenHowTo).

Tellurium snapshots can be found at [our Maven snapshot repository](http://maven.kungfuters.org/content/repositories/snapshots/) and the releases are at [our Maven release repository](http://maven.kungfuters.org/content/repositories/releases/). The customized Selenium server and some other dependencies are at [our Maven third party repository](http://maven.kungfuters.org/content/repositories/thirdparty).

Tellurium provides two Maven archetypes, [tellurium-junit-archetype and tellurium-testng-archetype](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes) for Tellurium JUnit and TestNG project, respectively. You can use one Maven command to create a new Tellurium test project.

Tellurium 0.8.0 core snapshots are at

http://maven.kungfuters.org/content/repositories/snapshots/org/telluriumsource/tellurium-core/0.8.0-SNAPSHOT/

Custom Selenium Server 1.0.1-te5 snapshots with Tellurium Engine are at

http://maven.kungfuters.org/content/repositories/snapshots/org/seleniumhq/selenium/server/selenium-server/1.0.1-te5-SNAPSHOT/

Tellurium IDE 0.8.0 snapshots are at

http://maven.kungfuters.org/content/repositories/snapshots/org/telluriumsource/tellurium-ide/0.8.0-SNAPSHOT/

Trump 0.8.0 snapshots are at

http://maven.kungfuters.org/content/repositories/snapshots/org/telluriumsource/trump/0.8.0-SNAPSHOT/

## Videos and Presentations ##

  * [Tellurium IDE 0.8.0 RC1 Tutorial](http://www.youtube.com/watch?v=yVIBY8QzWzE)
  * [TelluriumWorks 0.8.0 RC1 tutorial](http://www.youtube.com/watch?v=HVJgHFE2ETw)
  * [Tellurium 0.7.0 Reference Documentation](http://www.slideshare.net/John.Jian.Fang/tellurium-reference070)
  * [Tellurium UI Module Visual Demo](http://vimeo.com/9305675)
  * [Tellurium at Rich Web Experience 2009 by Jian Fang and Vivek Mongolu](http://www.slideshare.net/John.Jian.Fang/tellurium-at-rich-web-experience2009-2806967)
  * [Tellurium video tutorial by Vivek Mongolu](http://vimeo.com/8601173)
  * [Tellurium Screencast Part I: Tellurium project and Trump IDE](http://vimeo.com/8598478)
  * [Tellurium Screencast Part II: Create Tellurium Test cases](http://vimeo.com/8599445)
  * [Tellurium - A New Approach For Web Testing](http://www.slideshare.net/John.Jian.Fang/telluriumanewapproachforwebtesting)
  * [10 Minutes to Tellurium](http://vimeo.com/8600410)

## Tellurium Support ##

If you have any questions or problems with Tellurium, please join our [Tellurium user group](http://groups.google.com/group/tellurium-users) and then post them there. You will get the response very shortly.

## Acknowledgments ##

Special thanks to [JetBrains](http://www.jetbrains.com) for providing us the Open Source License for [IntelliJ IDEA](http://www.jetbrains.com/idea/download/index.html).

[![](http://www.jetbrains.com/idea/opensource/img/banners/idea88x31_blue.gif)](http://www.jetbrains.com/idea/)

Special thanks to ej-technologies for providing us the Open Source License for [JProfiler](http://www.ej-technologies.com/products/jprofiler/overview.html).

Special Thanks to [AquaFold](http://www.aquafold.com/) for providing us the Open Source Software Developer License of Aqua Data Studio.


## News ##

  * 2010-12-10, [Tellurium Engine: An Object-based Test Driving Engine](http://code.google.com/p/aost/wiki/TelluriumEngineIntroduction)
  * 2010-12-09, [Tellurium IDE 0.8.0 RC2](http://code.google.com/p/aost/wiki/TelluriumIde080RC2) is released
  * 2010-12-08, Welcome Eugene Ramirez to our team
  * 2010-12-07, Haroon published an article on _Test Experience_ magazine: [Tellurium Automated Testing Framework](http://aost.googlecode.com/files/testingexperience12_12_10_Article.pdf)
  * 2010-11-22, Welcome Nell Brisard to our team
  * 2010-10-19, [Simple Dependency Injection with Groovy AST Transformation](http://code.google.com/p/aost/wiki/DependencyInjectionWithGroovyASTTransformation)
  * 2010-10-12, Welcome Claudio Saccomandi to our team
  * 2010-09-21, [InfoQ Article: Introducing the Tellurium Automated Testing Framework](http://www.infoq.com/articles/tellurium_intro)
  * 2010-09-05, [TelluriumWorks 0.8.0 RC1: A Tellurium DSL Script Runner](http://code.google.com/p/aost/wiki/TelluriumWorks080RC1)
  * 2010-08-15, [Tellurium IDE 0.8.0 RC1 tutorial video](http://www.youtube.com/watch?v=yVIBY8QzWzE/)
  * 2010-08-15, [Tellurium IDE 0.8.0 RC1 is available](http://code.google.com/p/aost/wiki/TelluriumIde080RC1)
  * 2010-07-20, [Tellurium UI Module Firefox Plugin (Trump) 0.8.0 RC1: New Face and More Power.](http://code.google.com/p/aost/wiki/TelluriumTrump080RC1)
  * 2010-07-12, [How to write a Tellurium test using easyb.](http://code.google.com/p/aost/wiki/TelluriumEasybIntegration)
  * 2010-06-25, [Celebrate Tellurium 2nd Anniversary: Tellurium and Me](http://code.google.com/p/aost/wiki/TelluriumSecondAnniversary)
  * 2010-06-03, [An At-A-Glance View of the use of jQuery in Tellurium](http://code.google.com/p/aost/wiki/jQueryAPowerfulToolInTellurium)
  * 2010-06-01, [Tellurium as A jQuery UI Widget Testing Framework](http://code.google.com/p/aost/wiki/TelluriumjQueryUiWidgets)
  * 2010-05-24, [Interesting Algorithm Design Problems in Tellurium](http://code.google.com/p/aost/wiki/InterestingAlgorithmsInTellurium)
  * 2010-05-18, [Thinking Globally - Tellurium and Internationalization](http://code.google.com/p/aost/wiki/Telluriumi18nBlogPost)
  * 2010-05-13, [Tellurium: A Wrapper of Selenium?](http://code.google.com/p/aost/wiki/TelluriumAWrapOfSelenium)
  * 2010-05-07, [Tellurium 0.7.0 is released](http://code.google.com/p/aost/wiki/Tellurium070Released)
  * 2010-05-06, [Tellurum 0.7.0 Reference Document is available now](http://code.google.com/p/aost/wiki/tellurium070referencedoc)
  * 2010-05-04, [Build Tellurium Reference Document with Docbkx Maven Plugin](http://code.google.com/p/aost/wiki/BuildTelluriumReferenceDocumentWithDocbkxMavenPlugin)
  * 2010-04-16, Welcome Rajan to the team.
  * 2010-04-16, [Tellurium Calls for Open Source Contributors](http://code.google.com/p/aost/wiki/TelluriumCallForOpenSourceContributors)
  * 2010-04-14, [Tellurium is on GitHub now](http://github.com/telluriumsource/tellurium)
  * 2010-04-13, [Tellurium 0.7.0 RC2 is Available Now](http://code.google.com/p/aost/wiki/Tellurium070RC2Released)
  * 2010-04-05, [Tellurium UID Description Language](http://code.google.com/p/aost/wiki/TelluriumUIDDescriptionLanguage)
  * 2010-03-10, [TelluriumSource.org is up and running](http://telluriumsource.org)
  * 2010-03-04, [Run Tellurium Integration Tests with Maven Cargo Plugin](http://code.google.com/p/aost/wiki/TelluriumIntegrationTestsWithMavenCargoPlugin)
  * 2010-02-15, [Disclose UI Module APIs in Tellurium 0.7.0.](http://code.google.com/p/aost/wiki/DiscloseUIModuleOperationsInTellurium070)
  * 2010-02-11, [Tellurium 0.7.0 RC1 is Available Now](http://code.google.com/p/aost/wiki/announcements#Tellurium_0.7.0_RC1_is_Available_Now)
  * 2010-02-10, [Santa: The Tellurium UI Module Group Locating Algorithm](http://code.google.com/p/aost/wiki/SantaUiModuleGroupLocatingAlgorithm)
  * 2010-02-08, [Tellurium UI Module Visual Effect](http://code.google.com/p/aost/wiki/TelluriumUiModuleVisualEffect)
  * 2010-01-28, Tellurium is on [Vimeo](http://vimeo.com/groups/tellurium) now.
  * 2010-01-22, Added new wiki guide [How to create your own Tellurium testing project with IntelliJ 9.0 Community Edition](http://code.google.com/p/aost/wiki/CustomTelluriumIntelliJProject).
  * 2010-01-15, [What's New in Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Update) is updated to track changes in 0.7.0.
  * 2009-12-02, Tellurium was presented at [Rich Web Experience 2009](http://code.google.com/p/aost/wiki/TelluriumAtRichWebExperience2009).
  * 2009-11-20, [IRC Channel #tellurium is Available Now](http://code.google.com/p/aost/wiki/announcements#IRC_Channel_#tellurium_is_Available_Now).
  * 2009-10-10, [Tellurium Facebook Group](http://www.facebook.com/group.php?gid=298577410337&ref=mf) has been created and you are welcome to join.
  * 2009-09-20, Special thanks to Davlyn Jones, [the Tellurium user guide 0.6.0](http://code.google.com/p/aost/wiki/announcements#The_Tellurium_User_Guide_0.6.0_is_Available_Now) is officially released now.
  * 2009-09-18, Special thanks to Jared Rawlinson, Tellurium has a new project logo now.
  * 2009-09-09, Please poll [where did you find Tellurium](http://polls.linkedin.com/p/55933/svcvi).
  * 2009-08-18, Welcome Kamal Ahmed to our team.
  * 2009-08-15, Welcome Ajay Ravichandran to our team.
  * 2009-07-27, The first draft of [Tellurium User Guide](http://code.google.com/p/aost/wiki/announcements#First_Draft_of_the_Tellurium_User_Guide_0.6.0_is_Available_Now) is available now. You can download the pdf file from [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf).
  * 2009-07-15, Welcome Kevin Zhang to our team.
  * 2009-07-09, Welcome Davlyn Jones to our team.
  * 2009-06-26, Tellurium was presented at [CodeStock 2009](http://wiki.codestock.org/Home/2009-slides-and-code).
  * 2009-06-26, Tellurium is on [Twitter](http://twitter.com/TelluriumSource) and [Reddit](http://www.reddit.com/r/Tellurium/) now.
  * 2009-06-24, Tellurium celebrates its first anniversary. Please read the article, [Tellurium First Anniversary: Retrospect and Prospect](http://code.google.com/p/aost/wiki/FirstAnniversaryRetrospectAndProspect).
  * 2009-06-23, Added a video demo [Tellurium beginner tutorial](http://code.google.com/p/aost/wiki/TelluriumVideo1).
  * 2009-06-18, Tellurium 0.6.0 is released, please see [the announcement](http://code.google.com/p/aost/wiki/announcements#Tellurium_0.6.0_is_Released).
  * 2009-06-12, Tellurium creator, Dr. Jian Fang, was interviewed by [InfoQ China](http://www.infoq.com/cn/articles/tellurium-testing-framework).
  * 2009-06-10, [Tellurium Chinese Document project](http://code.google.com/p/telluriumdoc/) is created.
  * 2009-05-13, Tellurium Core and reference projects 0.6.0 RC1 are out, Please see [the announcement](http://code.google.com/p/aost/wiki/announcements#Tellurium_0.6.0_RC1_is_out).
  * 2009-05-10, Added Tellurium 0.6.0 feature introduction:  [Whats New in Tellurium 0.6.0](http://code.google.com/p/aost/wiki/WhatsNewInTellurium_0_6_0)
  * 2009-04-28, Tellurium provides [Selenium Grid support](http://code.google.com/p/aost/wiki/SeleniumGridAndTellurium)
  * 2009-04-23, [Tellurium Automated Testing Framework LinkedIn Group](http://www.linkedin.com/groups?gid=1900807) has been created and you are welcome to join.
  * 2009-04-14, Tellurium starts to support [jQuery selector](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector).
  * 2009-03-15, tutorial [10 Minutes to Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium) is created and it includes a wiki page, presentation slides, and a screencast video.
  * 2009-03-13, two [Tellurium Maven archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes) are available, i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit and TestNG project, respectively.
  * 2009-03-04, Tellurium UI Module Firefox Plugin ([TrUMP](http://code.google.com/p/aost/wiki/TrUMP)) 0.1.0 is released
  * 2009-02-18, Tellurium UI Module Firefox Plugin (TrUMP) 0.1.0 Release Candidate is available
  * 2009-02-17, Tellurium Demo Videos are uploaded, you can use [VLC media player](http://www.videolan.org/vlc/) to watch them
  * 2009-01-29, Tellurium UI Module Firefox Plugin (TrUMP) 0.1.0 preview version is available
  * 2008-12-03, Tellurium core 0.5.0 and reference projects are released
  * 2008-12-02, Welcome Mikhail Koryak to our team.
  * 2008-11-15, Tellurium 0.5.0 Release Candidate RC01 is out
  * 2008-08-22, Welcome Haroon Rasheed to our team.
  * 2008-08-13, Tellurium 0.4.0 is released and this release includes a lot of new features and enhancements such as data driven testing, framework configuration, and JUnit 4 support.
  * 2008-07-29, The AOST framework is officially renamed to the Tellurium Automated Testing framework (Tellurium).
  * 2008-07-29, Welcome Matt Senter to our team.
  * 2008-07-18, Welcome Vivek Mongolu to our team.
  * 2008-07-18, AOST user group is created. Please join and post your questions, comments, and suggestions there.
  * 2008-07-12, AOST 0.3.0 is out and it comes with significant feature enhancement including Composite Locator, Group Locating Concept, and Multiple UI Modules in one DslContext.
  * 2008-07-02, UI Object ID is refactored to support nested UI objects.
  * 2008-06-25, The AOST framework became public with version 0.2.0.
  * 2008-01-01, the AOST framework second prototype was created.
  * 2007-06-01, the AOST framework first prototype was created.

