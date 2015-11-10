(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))



# Getting Started #

This chapter discusses the Tellurium methods for creating a Tellurium project followed by descriptions of the primary components used for testing the newly created web framework including the UI Module, UI Object and attributes, Logical Container, jQuery Selector, UI Templates, and UI Testing Support.

## Create a Tellurium Project ##

Create a Tellurium Project in one of the following methods:

  * Use a Tellurium reference project as a base
  * Use [Tellurium Maven archetypes](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes)
  * Manually create a Tellurium project using the [tellurium jars](http://code.google.com/p/aost/downloads/list) and a [Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile)
  * Alternatively, create a Tellurium Maven project manually using [the sample POM file](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom).

http://tellurium-users.googlegroups.com/web/HowToUseTellurium.png?gda=E2fneEcAAACXZPxEX7Ki-M5C2JpeBoXXwOvr7XA0t7SnOHKVzf4DhFd6vDxrTQI8X2xdNkWs9mIVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=YyVqmwsAAABmHtz3tZj6NRBcOVGYgXTk

### Tellurium Maven Archetypes ###

The easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetypes for Tellurium JUnit test projects and Tellurium TestNG test projects respectively.

  * tellurium-junit-archetype
  * tellurium-testng-archetype

As a result, a user can create a Tellurium project using one Maven command.

For a Tellurium JUnit project, use:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
   -DarchetypeArtifactId=tellurium-junit-archetype \
   -DarchetypeGroupId=org.telluriumsource \
   -DarchetypeVersion=0.7.0 \
   -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

For a Tellurium TestNG project, use:
```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
   -DarchetypeArtifactId=tellurium-testng-archetype \
   -DarchetypeGroupId=org.telluriumsource \
   -DarchetypeVersion=0.7.0 \
   -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

Note: for Maven 2.2.1, please use `mvn archetype:generate` instead.

### Tellurium Ant Projects ###

For an Ant user:

  1. Download Tellurium 0.7.0 Release package from the [Tellurium project download page](http://code.google.com/p/aost/downloads/list)
  1. Unpack the Tellurium 0.7.0 Release package and copy dependencies from the directory dependencies/lib to your project /lib directory together with the `tellurium-core` and `tellurium-udl` 0.7.0 jar files.
  1. Name the Tellurium configuration file as TelluriumConfig.groovy and place it in the project root directory.

For Ant build scripts, refer to [the sample Tellurium Ant build scripts](http://code.google.com/p/aost/wiki/TelluriumSampleAntBuildScript).

## Setup Tellurium Project in IDEs ##

A Tellurium Project can be run in IntelliJ, NetBeans, Eclipse, or other IDEs that have Groovy support.

If using Maven, open the POM file to let the IDE automatically build the project files.

### IntelliJ IDEA ###
IntelliJ IDEA Community edition is free and can be downloaded from [http://www.jetbrains.com/idea/download/](http://www.jetbrains.com/idea/download/). A detailed guide is found on
[How to create your own Tellurium testing project with IntelliJ 9.0 Community Edition](http://code.google.com/p/aost/wiki/CustomTelluriumIntelliJProject).

### NetBeans IDE ###

For NetBeans users, detailed Guides can be found on [the NetBeans Starters' guide page](http://code.google.com/p/aost/wiki/TelluriumStarterUsingNetBeans) and [How to create your own Tellurium testing project with NetBeans 6.5](http://code.google.com/p/aost/wiki/CustomTelluriumNetBeansProject).

### Eclipse ###

For Eclipse users, download the Eclipse Groovy Plugin from [http://dist.codehaus.org/groovy/distributions/update/](http://dist.codehaus.org/groovy/distributions/update/) to run the Tellurium project.

For detailed instructions, read [How to create your own Tellurium testing project with Eclipse](http://code.google.com/p/aost/wiki/CustomTelluriumEclipseProject).

## Create a UI Module ##

Tellurium provides TrUMP to automatically create UI modules. TrUMP can be downloaded from the Tellurium project site:

[http://code.google.com/p/aost/downloads/list](http://code.google.com/p/aost/downloads/list)

Choose the Firefox 2 or Firefox 3 version depending upon the user’s Firefox version, or download the Firefox 3 version directly from the Firefox addons site at:

[https://addons.mozilla.org/en-US/firefox/addon/11035](https://addons.mozilla.org/en-US/firefox/addon/11035)

Once installed, restart Firefox. Record UI modules by simply clicking UI elements on the web and then click the "Generate" button.

To customize the UI, click the "Customize" button.

In the example, open the Tellurium download page found on:

[http://code.google.com/p/aost/downloads/list](http://code.google.com/p/aost/downloads/list)

Record the download search module as follows:

http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPageSmall.png?gda=aeAak1IAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq73MhpfIdLRuVAwbLjuTZtQ7YQyGo5rEgT7iH53cuUInVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

After the UI module is customized, export it as the module file `NewUiModule.groovy` to the demo project and add a couple of methods to the class:

```
class NewUiModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", 
       action: "list"], group: "true") 
    {
      Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
      InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", 
               value: "Search"])
    }
  }

  public void searchDownload(String keyword) {
    keyType "TelluriumDownload.Input", keyword
    click "TelluriumDownload.Search"
    waitForPageToLoad 30000
  }

  public String[] getAllDownloadTypes() {
    return getSelectOptions("TelluriumDownload.DownloadType")
  }

  public void selectDownloadType(String type) {
    selectByLabel "TelluriumDownload.DownloadType", type
  }
}
```

## Create Tellurium Test Cases ##

Once the UI module is created, create a new Tellurium test case `NewTestCase` by extending `TelluriumJUnitTestCase` class.

```
public class NewTestCase extends TelluriumJUnitTestCase {
    private static NewUiModule app;

    @BeforeClass
    public static void initUi() {
        app = new NewUiModule();
        app.defineUi();
    }

    @Before
    public void setUpForTest() {
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testTelluriumProjectPage() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }
}
```

Compile the project and run the new test case.