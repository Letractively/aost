(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



# How to Obtain and Use Tellurium #

## Create a Tellurium Project ##

There are three ways: use [the reference project](http://code.google.com/p/aost/wiki/ReferenceProjectGuide) as a base, use the [Tellurium Maven archetype](http://code.google.com/p/aost/wiki/TelluriumMavenArchetypes), or manually create a Tellurium project using the [tellurium jar](http://code.google.com/p/aost/downloads/list) and a [Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile). Alternatively, you could create your own Tellurium Maven project manually using [the sample POM file](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom).

http://tellurium-users.googlegroups.com/web/HowToUseTellurium.png?gda=E2fneEcAAACXZPxEX7Ki-M5C2JpeBoXXwOvr7XA0t7SnOHKVzf4DhFd6vDxrTQI8X2xdNkWs9mIVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=YyVqmwsAAABmHtz3tZj6NRBcOVGYgXTk

The easiest way to create a Tellurium project is to use Tellurium Maven archetypes. Tellurium provides two Maven archetypes: tellurium-junit-archetype and tellurium-testng-archetype (for Tellurium JUnit test projects and Tellurium TestNG test projects respectively.) As a result, you can create a Tellurium project using one Maven command. For a Tellurium JUnit project, use:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
     -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium \
     -DarchetypeVersion=0.7.0-SNAPSHOT \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

and for a Tellurium TestNG project, use:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id \
     -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium \
     -DarchetypeVersion=0.7.0-SNAPSHOT \
     -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

For an Ant user, you should download [Tellurium core 0.6.0 jar file](http://aost.googlecode.com/files/tellurium-core-0.6.0.jar) from [Tellurium project download page](http://code.google.com/p/aost/downloads/list), [the Tellurium dependency file](http://aost.googlecode.com/files/tellurium-0.6.0-dependencies.zip), and [the Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile). Unpack the Tellurium dependency file to your project /lib directory together with the Tellurium core 0.6.0 jar file. Name the Tellurium configuration file as TelluriumConfig.groovy and put it at your project root directory.

For Ant build scripts, please see [the sample Tellurium Ant build scripts](http://code.google.com/p/aost/wiki/TelluriumSampleAntBuildScript)

## Setup Tellurium Project in IDEs ##

Tellurium Project can be run in IntelliJ, NetBeans, Eclipse, or other IDEs which have Groovy support. If you use Maven, you can simply open the POM file to let the IDE automatically build the project files for you.

IntelliJ IDEA is commercial and you can download a free trial version for 30 days from http://www.jetbrains.com/idea/download/index.html. You can find a detailed guide on [How to create your own Tellurium testing project with IntelliJ 7.0](http://code.google.com/p/aost/wiki/CustomTelluriumIntelliJProject).

For NetBeans users, you can find detailed Guides on [the NetBeans Starters' guide page](http://code.google.com/p/aost/wiki/TelluriumStarterUsingNetBeans) and [How to create your own Tellurium testing project with NetBeans 6.5](http://code.google.com/p/aost/wiki/CustomTelluriumNetBeansProject).

For Eclipse users, you need to download Eclipse Groovy Plugin from http://dist.codehaus.org/groovy/distributions/update/ to run the Tellurium project. For detailed instructions, please read [How to create your own Tellurium testing project with Eclipse](http://code.google.com/p/aost/wiki/CustomTelluriumEclipseProject).

## Create a UI Module ##

Tellurium provides Trump for you to automatically create UI modules. Trump can be downloaded from the Tellurium project site:

http://code.google.com/p/aost/downloads/list

Choose the Firefox 2 or Firefox 3 version depending on your Firefox version, or you can download the Firefox 3 version directly from the Firefox addons site at:

https://addons.mozilla.org/en-US/firefox/addon/11035

Once you install it and restart Firefox, you are ready to record your UI modules by simply clicking on the UI element on the web and then clicking the "generate" button. You may like to customize your UI a bit by clicking the "Customize" button. More detail:

In our example, we open up Tellurium download page

http://code.google.com/p/aost/downloads/list

and record the download search module as follows:

http://tellurium-users.googlegroups.com/web/TrUMPRecordDownloadPageSmall.png?gda=aeAak1IAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq73MhpfIdLRuVAwbLjuTZtQ7YQyGo5rEgT7iH53cuUInVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

After we customize the UI module, we export it as the module file NewUiModule.groovy to the demo project and add a couple of methods to the class:

```
class NewUiModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], 
           group: "true") 
{
      Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
      InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])
    }
  }

  //Add your methods here
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

Once you create the UI module, you can create a new Tellurium test case NewTestCase by extending TelluriumJavaTestCase class.

```
public class NewTestCase extends TelluriumJavaTestCase {
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