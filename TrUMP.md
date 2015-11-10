

# Tellurium UI Model Plugin (TrUMP) 0.1.0 #

Tellurium UI Model Plugin (TrUMP) is the Firefox plugin to automatically create UI modules for users.

## Install TrUMP IDE ##

Go to the Tellurium project download page and download the TrUMP xpi file as shown in the Figure,

http://tellurium-users.googlegroups.com/web/TrUMPDownload.png?gsc=N4jWPhYAAACC-9YBMpIljZK8WE-abHrag-kXU5InE09W2o0GCSVgCQ

Or you can download the Firefox 3 version directly from Firefox addons site at

https://addons.mozilla.org/en-US/firefox/addon/11035

Then, open the xpi file with Firefox,

http://tellurium-users.googlegroups.com/web/trump010b.png?gda=sK2vfz8AAACXZPxEX7Ki-M5C2JpeBoXXpbNhTBGwKHnla96SFPZ5YrYvoqxkO5DC4aYRH_5SRSSccyFKn-rNKC-d1pM_IdV0&gsc=z7xdXxYAAACUfwRI-f7WsvI5RgWLBanlg-kXU5InE09W2o0GCSVgCQ

You will see the following Firefox plugin installation page, click "Install Now",

http://tellurium-users.googlegroups.com/web/SoftwareInstallation.png?gsc=zCT_fBYAAACQz_B0Pa_Ea1pLiVja5Z6ag-kXU5InE09W2o0GCSVgCQ


After that, restart Firefox to make the TrUMP plugin take effect.

http://tellurium-users.googlegroups.com/web/RestartFirefox.png?gsc=N4jWPhYAAACC-9YBMpIljZK8WE-abHrag-kXU5InE09W2o0GCSVgCQ

## Setup TrUMP From Project Source ##

Sometime, you may want to work on TrUMP source. To do this, check out the firefox-plugin folder in tools/firefox-plugin.

Lets say you have checked it into C:\Project\firefox-plugin

First you’ll need to copy the file trump@aost.net inside the extensions-file folder to your firefox profile.

Profiles for firefox are located in
C:\Documents and Settings\$user\Application Data\Mozilla\Firefox\Profiles\
For Windows OS

For linux,  the firefox user profile lives in

~/.mozilla/firefox/

Lets say you want to copy it to user1 profile

Open the user1 profile. Copy the trump@aost.net file to the extensions folder

C:\Documents and Settings\$user\Application Data\Mozilla\Firefox\Profiles\user1\extensions

Edit the trump@aost.net file by opening it in a text editor. This file will contain the path to our trump plugin folder, which is C:\Project\firefox-plugin\trump\. Don’t forget the \ after the trump. Save and close the file.

We are done setting up the plugin.

After that, open the firefox with the profile “user1”. You are ready to use TrUMP IDE

## Build TrUMP From Source ##

Check out TrUMP source code from Tellurium trunk/tools/firefox-plugin.

Then run the following ant tasks,
```
ant clean
ant dist
```

The generated .xpi file is under the /dist directory

## Use TrUMP ##

In Firefox, select "Tools" > "TrUMP IDE"

http://tellurium-users.googlegroups.com/web/trump010e.png?gsc=jTelIRYAAAC0nhYDa9gni8Uq6-kI4oX7g-kXU5InE09W2o0GCSVgCQ

You will see the TrUMP IDE as follows,

![http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png](http://tellurium-users.googlegroups.com/web/TrUMPIDE0.1.0.png)

The "Record" button is on by default, you can click on "Stop" to stop recording. Now, you can start to use the TrUMP IDE to record whatever UI elements you click on the WEB. For example, you can open Tellurium Download page and click on the search elements and the three links,

![http://tellurium-users.googlegroups.com/web/TrUMPRecord.png](http://tellurium-users.googlegroups.com/web/TrUMPRecord.png)

The blue color indicates selected element, you can click on the selected element again to un-select it.

Then, you can click on the "Generate" button to create the Tellurium UI Module and you will be automatically directed to the "Source" window,

http://tellurium-users.googlegroups.com/web/TrUMPGenerate.png?gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

After that, you can click on the "Customize" button to change the UI module such as UIDs, group locating option, and attributes you selected for the UI module.

http://tellurium-users.googlegroups.com/web/TrUMPCustomize.png?gda=OfrPCkQAAACXZPxEX7Ki-M5C2JpeBoXXTJUqAjHYSxXv6NWi-vE8ybjpUZQJGBKU2NRrn2eBK8FV6u9SiETdg0Q2ffAyHU-dzc4BZkLnSFWX59nr5BxGqA&gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

You can see there is one red "X" mark, which indicates that UI element's XPath is not unique, you could select group, or add more attributes to the UI element. You will see
the new customized UI as follows,

http://tellurium-users.googlegroups.com/web/TrUMPCustomized.png?gda=eLPBKkUAAACXZPxEX7Ki-M5C2JpeBoXXTJUqAjHYSxXv6NWi-vE8yWpSkAEfxrsCm-JxAmInjVlzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A&gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

Note that the red "X" mark is removed because we turn on the group locating and the element's xpath is unique now. In the meanwhile, the UI module in the source tab will be automated updated once you click the "Save" button. The "Show" button will show the actual Web element on the web that the UI element is represented for.

http://tellurium-users.googlegroups.com/web/TrUMPCustomizedSource.png?gda=Des1l0sAAACXZPxEX7Ki-M5C2JpeBoXXTJUqAjHYSxXv6NWi-vE8yQl-ge40lZM5QmFCqlPrr94UUFmPu5RmzzCCYEsK7dk1BkXa90K8pT5MNmkW1w_4BQ&gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

At this point, you export the UI module to a groovy file. Be aware that if you see any error complaining about the directory, you should first check the "export directory" in Options > Settings and set it to "C:\" or other windows directory for Windows system before you export the file. For Linux, you may find there is no "OK" buton on the option tab, which is caused by the fact that the configure "browser.preferences.instantApply" is set to true by default. You can point your firefox to "about:config" and change the option to false. After that, you will see the "OK" button.


http://tellurium-users.googlegroups.com/web/ExportToGroovy.png?gda=XKq84kQAAACXZPxEX7Ki-M5C2JpeBoXXSb7HI-NonvaTuJe2neIoZFD6zIowMVUmhwc1DN0qlZJV6u9SiETdg0Q2ffAyHU-dzc4BZkLnSFWX59nr5BxGqA&gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

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
		   	Form(uid: "Form", clocator: [tag: "form", method: "get", action: "list"], group: "true"){
		   		Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
		   		InputBox(uid: "SearchBox", clocator: [tag: "input", type: "text", name: "q", id: "q"])
		   		SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])
		   	}
		   	Container(uid: "Title", clocator: [tag: "table", id: "mt"]){
		   		UrlLink(uid: "Issues", clocator: [tag: "a", text: "Issues"], respond: ["click"])
		   		UrlLink(uid: "Wiki", clocator: [tag: "a", text: "Wiki"], respond: ["click"])
		   		UrlLink(uid: "Downloads", clocator: [tag: "a", text: "Downloads"], respond: ["click"])
		   	}
		   }
	}

	//Add your methods here

}

```


## Create Tellurium Tests ##

We can add the following methods to the NewUiModule.groovy class

```
  public void searchDownload(String keyword) {
    keyType "Tellurium.Form.Input", keyword
    click "Tellurium.Form.Search"
    waitForPageToLoad 30000
  }

  public String[] getAllDownloadTypes() {
    return getSelectOptions("Tellurium.Form.DownloadType")
  }

  public void selectDownloadType(String type) {
    selectByLabel "Tellurium.Form.DownloadType", type
  }

  public void clickIssuesPage() {
    click "Tellurium.Title.Issues"
    waitForPageToLoad 30000
  }
```

Then you can use reference project as a template to create Tellurium Test project, or you can create your own new Tellurium Test project using Tellurium jar and configuration file TelluriumConfig.groovy.

If we choose JUnit, the test case looks like as follows,

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
    public void testDownloadTypes() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        assertTrue(allTypes[2].contains("Featured Downloads"));
        assertTrue(allTypes[3].contains("Current Downloads"));
        assertTrue(allTypes[4].contains("Deprecated Downloads"));
    }

    @Test
    public void testTelluriumProjectPage() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }    
    
    @Test
    public void testClickIssuePage(){
        app.clickIssuesPage();
    }
}

```

For TestNG, it is similar and please check the TestNG reference project for details.

## Additional Features ##

TrUMP provides the ability for users to change the TrUMP settings,

http://tellurium-users.googlegroups.com/web/TrUMPOptions.png?gda=YmFankIAAACXZPxEX7Ki-M5C2JpeBoXXTJUqAjHYSxXv6NWi-vE8yXa3N-V1JuidStSifcJ9gx1V4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=8bpDhBYAAADelCD9Y452tE9McTfxJEayg-kXU5InE09W2o0GCSVgCQ

Apart from the log window in TrUMP, logs can also be added to Javascript Error Console. To turn on the log, please first type "about:config" on Firefox address box, then select "javascript.options.showInConsole" and double click it to flip its value to be true.

![http://tellurium-users.googlegroups.com/web/AboutConfig.png](http://tellurium-users.googlegroups.com/web/AboutConfig.png)

After you click the "Generate" button, you can find the log message in Javascript console as follow (Tools > Error Console),

http://tellurium-users.googlegroups.com/web/ErrorConsole.png?gsc=ECfkzxYAAACkNoHhoJXKpjewPqNZCHesg-kXU5InE09W2o0GCSVgCQ

## How TrUMP Works ##

[How TrUMP Works](http://code.google.com/p/aost/wiki/HowTrUMPWorks)

## Known Issues ##

The known issues include
  * Cannot record popup/dialog window, which is also a problem for many other Firefox plugins, for instance, Selenium IDE
  * TrUMP does not support UI template yet. This feature will be provided in later versions because it requires an inference engine.