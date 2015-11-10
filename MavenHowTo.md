

# Introduction #

Tellurium has converted its project structure to use Maven. This document will explain the steps necessary to retrieve the Maven artifacts.

# Prerequisites #

You will need to be running Maven version 2.0.9, downloadable from http://maven.apache.org/download.html.

If you never installed Maven before, please follow [the official Maven Installation Guide](http://maven.apache.org/download.html#Installation).

# settings.xml #

Here is a sample settings.xml that will allow you to automatically include Tellurium artifacts in your Maven project. This should go in your ~/.m2/settings.xml file:

```
<settings>
    <profiles>
        <profile>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
                 <repository>  
                    <id>kungfuters-public-snapshots-repo</id>
                    <name>Kungfuters.org Public Snapshot Repository</name>
                    <releases>          
                        <enabled>false</enabled>
                    </releases>         
                    <snapshots>         
                        <enabled>true</enabled> 
                    </snapshots>        
                    <url>http://maven.kungfuters.org/content/repositories/snapshots</url>
                </repository>   
                <repository>    
                    <id>kungfuters-public-releases-repo</id>
                    <name>Kungfuters.org Public Releases Repository</name>
                    <releases>          
                        <enabled>true</enabled> 
                    </releases>         
                    <snapshots>         
                        <enabled>false</enabled>
                    </snapshots>        
                    <url>http://maven.kungfuters.org/content/repositories/releases</url>
                </repository>   
            </repositories>
        </profile>
    </profiles>
</settings>
```

# Dependency #

To include Tellurium as a dependency in your project, add the following to your pom.xml:

```
<dependencies>
    ...
    <dependency>
      <groupId>tellurium</groupId>
      <artifactId>tellurium-core</artifactId>
      <version>0.6.0</version>
      <scope>compile</scope>
    </dependency>
    ...
</dependencies>
```

# Build Tellurium from Source #

If you want to build Tellurium from source, you can check out the trunk code using subversion command,

```
svn checkout http://aost.googlecode.com/svn/trunk/ tellurium
```

or using mvn command,

```
mvn scm:checkout -DconnectionUrl=scm:svn:http://aost.googlecode.com/svn/trunk -DcheckoutDirectory=tellurium
```

Be aware that the Maven command calls the subversion client to do the job and you must have the client installed in your system.

If you want to build the whole project, just use

```
mvn clean install
```

and Maven will compile source code and resources, compile test code and test resources, run all tests, and then install all artifacts to your local repository under YOUR\_HOME/.m2/repository.

Sometimes, tests may break and if you still want to proceed, please use the ignore flag

```
mvn clean install -Dmaven.test.failure.ignore=true
```

If you want to build an individual project, just go to that project directory and run the same command as above.

If you like to run the tests, use command

```
mvn test
```

The sub-projects under the tools directory include Tellurium Maven archetypes and trump code, you may not really want to build them by yourself. For trump, the artifacts include a .xpi file.

The assembly project just creates a set of tar files and you may not need to build it either.

# Create a New Tellurium Test Project using Tellurium Maven Archetype #

Tellurium provides [two maven archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes), i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively.

Run the following maven command to create a new JUnit project

```
mvn archetype:generate -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

Without adding the Tellurium Maven repository, you can specify it in the command line as
```
mvn archetype:generate -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0 \
-DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

For TestNG project, you should use a different archetype

```
mvn archetype:generate -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

If you want to see a detailed example on how to create a new Tellurium test project, you can read [Ten Minutes to Tellurium](http://code.google.com/p/aost/wiki/TenMinutesToTellurium)

# How to Create a Maven Archetype from an Existing Project #

You can create a Maven archetype from scratch, for example, [Tellurium Maven Archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes) are created in this way. But in some cases, your project may be pretty complicated and you want to create the Maven archetype from a template project. The guide on [How to Create a Maven Archetype From an Existing Project](http://code.google.com/p/jianwikis/wiki/HowToCreateMavenArchetypeFromProject) walks you through all the steps to create a Maven Archetype from an existing project.

# Build information #

To find regular build information updates, see http://kungfuters.org/tellurium. There you will find information about the project as well as unit test and coverage reports.

# Useful Maven Plugins and Commands #

## Maven Help Plugin ##

  * _help:active-profiles_ lists the profiles which are currently active for the build.
  * _help:all-profiles_ lists the available profiles under the current project.
  * _help:describe_ describes the attributes of a Plugin and/or a Mojo (Maven plain Old Java Object).
  * _help:effective-pom_ displays the effective POM as an XML for the current build, with the active profiles factored in.
  * _help:effective-settings_ displays the calculated settings as an XML for the project, given any profile enhancement and the inheritance of the global settings into the user-level settings.
  * _help:system displays_ a list of the platform details like system properties and environment variables.

## Maven Dependency Plugin ##

  * _dependency:resolve_ tells Maven to resolve all dependencies and displays the version.
  * _dependency:sources_ tells Maven to resolve all dependencies and their source attachments, and displays the version.
  * _dependency:resolve-plugins_ Tells Maven to resolve plugins and their dependencies.
  * _dependency:purge-local-repository_ tells Maven to clear all dependency-artifact files out of the local repository, and optionally re-resolve them.
  * _dependency:build-classpath_ tells Maven to output the path of the dependencies from the local repository in a classpath format to be used in java -cp. The classpath file may also be attached and installed/deployed along with the main artifact.
  * _dependency:analyze_ analyzes the dependencies of this project and determines which are: used and declared; used and undeclared; unused and declared.
  * _dependency:tree_ displays the dependency tree for this project.
  * _dependency:purge-local-repository -Dexclude=ARTIFACT_ delete all of the dependencies for the current project and re-resolved afterwards. You can disable this by specifying -DreResolve=false.
  * _dependency:copy-dependencies_ takes the list of project direct dependencies and optionally transitive dependencies and copies them to a specified location
  * _dependency:unpack-dependencies_ like copy-dependencies but unpacks.

## Maven Archetype Plugin ##

  * _archetype:create_  creates a Maven 2 project from an archetype.
  * _archetype:generate_ ask the user to choose an archetype from the archetype catalog, and retrieves it from the remote repository. Once retrieved, it is processed to create a working Maven project.
  * _archetype:create-from-project_ creates an archetype from an existing project.

# Maven IDE Configuration #

## Eclipse ##

### Install Maven Plug-in ###

  1. Click the Add Site button.
  1. Enter the following URL: http://m2eclipse.sonatype.org/update/
  1. Click OK.
  1. Expand the Maven Integration for Eclipse Update Site node.
  1. Select the Maven Integration check box.
  1. Click the Install button and follow the wizard.
  1. Restart Eclipse.

### Configure Maven Plug-in ###

  1. Click Window | Preferences.
  1. Expand the Maven node.
  1. Select the Installations node.
  1. Click the Add button.
  1. Navigate the the Maven 2.0.9 installation folder.
  1. Click OK.
  1. Click the check box next to the new Maven installation.

### Import Projects ###

  1. Click File | Import.
  1. Type Maven in the filter text field.
  1. Under the General node, select the Maven Projects node.
  1. Click Next.
  1. Click the Browse button and navigate to the ide-settings folder.
  1. Click OK.
  1. Click Finish.

## Netbeans ##

### Install Maven Plugin ###

Netbeans does not come with automatic support for Maven, you will need to install the plugin.

  1. From the menu select Tools/Plugins
  1. In the plugins screen, click on the Installed tab and Click on the Reload Catalog tab to get the list of available plugins.
  1. Select the Available Plugins tab and scroll down until you see the Maven plugin in.
  1. Click on the check box next to this plug in and then select Install.
  1. Follow the instructions to install the plug in.

### Configure Maven Plugin ###

  1. Bring up the preferences dialog.
  1. Select Miscellaneous and then click on the Maven tab.
  1. Tools=>Options for Windows users.
  1. Click on the Browse button next to the entry for External Maven Home. Navigate to the directory where you installed Maven and click Open.
  1. Update the frequency with which your local repository is indexed

### Import Projects ###

  1. Click File | Open Project
  1. Navigate to the ide-settings folder.
  1. Choose the pom.xml file and click open.

## IntelliJ ##

IntelliJ IDEA has Maven Plugin installed by default.

### Configure Maven Plug-in ###

  1. Click File | Settings to bring up the IDE settings window.
  1. Click the Maven node.
  1. Click the Override check box next to the Maven Home Directory text field.
  1. Click the ... button.
  1. Navigate to the Maven 2.0.9 installation directory.
  1. Click OK.

### Import Projects ###

  1. Click File | Open Project
  1. Choose the pom.xml file and click OK.

# Subversion Clients #

To check out code from google code SVN, you should use SVN client plugins in IDEs or you can use a standalone SVN client tools such as
  * [SmartSVN](http://smartsvn.com): a good cross-platform client that supports Subversion  up to 1.5
  * [TortoiseSVN](http://tortoisesvn.tigris.org): a good Windows client that supports Subversion up to 1.5

# Reference Material #

  * [Maven: The Definitive Guide](http://www.sonatype.com/books/maven-book/reference/public-book.html)
  * [Better Builds with maven](http://repo.exist.com/dist/maestro/1.7.0/BetterBuildsWithMaven.pdf)
  * [Maven Best Practices for NetBeans](http://wiki.netbeans.org/MavenBestPractices#section-MavenBestPractices-BestPracticesForApacheMavenInNetBeans6.x)