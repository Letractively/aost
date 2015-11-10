# Introduction #

Tellurium is an automated web testing framework. Tellurium was started in June 2007 as an internal project and became an open source project on Google Code in June 2008. The key techniques include Groovy, Groovy Grape, jQuery, Selenium, Antlr 3 parser generator, JUnit, TestNG, and Maven. Tellurium 0.7.0 is a milestone release and it is the outcome of one year's hard work. Today, we are happy to announce the release of Tellurium 0.7.0.

# Features #

This release includes many new features such as [the Santa Group Locating Algorithm](http://code.google.com/p/aost/wiki/SantaUiModuleGroupLocatingAlgorithm), [Macro Command](http://code.google.com/p/aost/wiki/Tellurium070Update#Macro_Command), [UI Module Caching](http://code.google.com/p/aost/wiki/Tellurium070Update#UI_Module_Caching), [I18n support](http://code.google.com/p/aost/wiki/InternationalizationSupportTellurium), and [the Tellurium UID Description Language](http://code.google.com/p/aost/wiki/TelluriumUIDDescriptionLanguage) (UDL), which is used to address the dynamic factors in Tellurium UI templates and increase the flexibility of Tellurium UI templates.

Over 250 issues and tasks are closed for 0.7.0 with many bug fixes and feature enhancements. For more detailed feature list, please read [What's New in Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Update).

# How to Obtain Tellurium 0.7.0 #

Tellurium 0.7.0 tar ball can be downloaded from [here](http://aost.googlecode.com/files/tellurium-0.7.0.tar.gz). You can also find Tellurium udl, Tellurium Core, and custom selenium server from the following Maven repositories, respectively.

http://maven.kungfuters.org/content/repositories/releases/org/telluriumsource/tellurium-udl/0.7.0/

http://maven.kungfuters.org/content/repositories/releases/org/telluriumsource/tellurium-core/0.7.0/

http://maven.kungfuters.org/content/repositories/thirdparty/org/seleniumhq/selenium/server/selenium-server/1.0.1-te3/

If you Tellurium Maven archetypes, you can use the following command to create a Tellurium JUnit test project.

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
-DarchetypeArtifactId=tellurium-junit-archetype \
-DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0 \
-DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

Similarly, use the following Maven command for a Tellurium TestNG project,

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
-DarchetypeArtifactId=tellurium-testng-archetype \
-DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0 \
-DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

# Release File Directory Structure #

This directory includes Tellurium 0.7.0 Release source code, dependencies, and documents.

```
|-- LICENSE.txt
|-- README
|-- RELEASENOTE
|-- dependencies
|   |-- bin
|   |   `-- rundsl.groovy
|   |-- config
|   |   `-- TelluriumConfig.groovy
|   `-- lib
|       |-- antlr-2.7.7.jar
|       |-- antlr-3.1.3.jar
|       |-- antlr-runtime-3.1.3.jar
|       |-- groovy-all-1.7.0.jar
|       |-- json_simple-r1.jar
|       |-- junit-4.7.jar
|       |-- poi-3.0.1-FINAL.jar
|       |-- reportng-0.9.8.jar
|       |-- selenium-java-client-driver-1.0.1.jar
|       |-- stringtree-json-2.0.10.jar
|       `-- testng-5.8-jdk15.jar
|-- dist
|   |-- selenium-server-1.0.1-te3.jar
|   |-- tellurium-core-0.7.0.jar
|   `-- tellurium-udl-0.7.0.jar
|-- doc
|   |-- html
|   |   |-- media
|   |   `-- tellurium-reference.html
|   `-- pdf
|       `-- tellurium-reference.pdf
`-- sources
    |-- core
    |-- engine
    |-- extensions
    |   |-- dojo-widget
    |   |-- extjs-widget
    |-- reference-projects
    |   |-- benchmarks
    |   |-- tellurium-website
    |   `-- ui-examples
    |-- tellurium-assembly
    |-- tellurium-reference
    |-- tools
    |   |-- firefox-plugin
    |   |-- tellurium-junit-archetype
    |   |-- tellurium-testng-archetype
    |   `-- tellurium-widget-archetype
    `-- udl
```


The udl is the UDL parser project. The Core is the Tellurium Core project. Engine is Tellurium Engine project. The reference projects include tellurium-website and ui-examples sub-projects, where are a good starting point for you to learn Tellurium and experience the cool features in 0.7.0. The dependencies directory includes all jars you need to run Tellurium tests and a sample Tellurium Configuration file. The doc folder holds Tellurium 0.7.0 reference documentation, which can also be download directly from [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf).

# Feedback #

Please report any problems back to Tellurium User Group at

http://groups.google.com/group/tellurium-users

and follow [Tellurium on Twitter](http://twitter.com/TelluriumSource) for any the latest update.

Tellurium Team is looking for new open source contributors to join our team and read the wiki [Tellurium Calls for Open Source Contributors](http://code.google.com/p/aost/wiki/TelluriumCallForOpenSourceContributors) for more details.

Thanks,

Tellurium Team