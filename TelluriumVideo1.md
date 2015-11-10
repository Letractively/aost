

# Tellurium beginner tutorial #

## Introduction ##
First download the [screen cast video](TelluriumVideo1#Resources.md)  and follow the video with this page.

**Tellurium Automated Testing Framework (Tellurium)** is a **UI module-based** automated testing framework for web applications built on top of Selenium. Currently we are working on  our own engine. With Tellurium we are not only trying to solve some of the problems with Selenium but we've added new features which would ease the pain in writing functional tests.

You can download Selenium [here](http://seleniumhq.org/download/)

Lets begin with Selenium. **Selenium** is web application testing framework. It consists of 5 components.
  1. Selenium Core
  1. Selenium Remote Control(RC) Server
  1. Selenium Driver
  1. Selenium Grid
  1. Selenium IDE firefox plugin

http://tellurium-users.googlegroups.com/web/selenium.png?gsc=QJl9twsAAADeoAOjG7FEp37CyALn8sk1

Selenium RC server automatically launches and kills browsers, and acts as a HTTP proxy for web requests from them.

Tests are written in your favorite programming language using the corresponding client libraries. Through the test, you invoke the RC server, which then fires up browser and loads the Selenium core. Selenium core is DHTML test execution engine. First command core gets from RC is to load the web application. From then on, the commands from the test are proxied through the RC server to the core in browser. Simple commands are type into text box, clicking on the submit button..etc

With Selenium IDE its even easier to write the test. Just download and open the plugin. Plugin records all the user interaction and spits out the code.

You can download the project [here](http://aost.googlecode.com/files/Tellurium_Sample.zip). Zip file contains all the required jars, source files and the Tellurium config file.

With Selenium IDE opened, goto the download page for Tellurium project
link: http://code.google.com/p/aost/downloads/list
and search for "TrUMP" and click the Search button. Plugin outputs this code.

```
import com.thoughtworks.selenium.SeleneseTestCase;

public class SeleniumTestCase extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://code.google.com/", "*chrome");
	}
	public void testNew() throws Exception {
		selenium.open("/p/aost/downloads/list");
		selenium.type("q", "TrUMP");
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
	}
}
```

Goto the downloaded Selenium folder and start the selenium server using
```
java -jar selenium.jar
```

Run the above test and you will see the browser fires up, loads the download section, performs search for TrUMP.


Looking at the code, we can observe some problems
  1. Verbose: selenium everywhere
  1. Coupling: UI and tests are coupled
  1. What UI are we testing?
  1. Fragile: XPath to locate the element. If something along the XPath changes then Selenium cannot find the element.

Tellurium addresses all of these problems. UI model is the core of Tellurium. Its a collection of all the DOM elements. Instead of using the static xpath, we use the attributes of the DOM element and its parent to dynamically generate xpath. UI is completely separate from the test making the test and the UI refactor friendly and reusable. By looking at the UI model, you can fairly say what part of the web application you are testing. Same test in Tellurium is split into two parts UI and the actual TEST as follows.

### UI ###
```
import org.tellurium.dsl.DslContext

/**
 * @author Vivek Mongolu
 */

public class TelluriumDownloadSearchModule  extends DslContext {

  public void defineUi() {

    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], group: "true") {
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

Here we created the UI that we'll be testing using the abstract UI objects.  Tellurium comes with most commonly used DOM elements as UI objects. You can create your own UI object too. Tellurium also has helper methods to use on the respective UI objects.

Each UI object has bunch of fields, uid and clocator are the most important fields.
**uid** is the unique identifier for the DOM element. Instead of using the xpath for the DOM element, you'll use the uid. This makes the code more readable.

```
keyType "TelluriumDownload.Input", keyword
```

Here we are typing "keyword" into the Input text box which is child of Tellurium Download form.

**clocator** is composite locator which uses the attributes of the DOM element. To get the attributes for a DOM element, you can use inspect on Firebug or Dom Inspector.

For the form UI object, group locating is turned on. Group Locating in Tellurium is to exploit relationship among the set of nested UI elements in a composite object. That is to say, when Tellurium tries to find the Form UI element, the search becomes:

```
"Find a Form with a child element Selector, a child element InputBox, and a child element SubmitButton"
```


### TEST ###

```
package com.tellurium.test.ui;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Vivek Mongolu
 */
public class TelluriumDownloadSearchTestCase  extends TelluriumJavaTestCase {
    private static TelluriumDownloadSearchModule app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumDownloadSearchModule();
        app.defineUi();
        app.useJQuerySelector();
        app.enableSelectorCache();
    }

    @Before
    public void setUpForTest() {
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testDownloadSearch() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }

}
```

In the test, first create the UI module object and then call the defineUI method, which will create a tree of UI objects in memory.
In the actual test method, we are first getting all options of the Download type select element. This will first create the xpath based upon its own attributes and its parent, locate the select dom element and then get back the option labels. We're then changing the option selected to "All downloads" label and then search for "TrUMP". Thats all you need.

Since UI model is the core of Tellurium and we understand that creating is both tedious and painful, we have created firefox plugin **TrUMP - Tellurium UI Model Plugin**, which you can use to create the UI model for your test with ease. You can download the plugin from  [Resources](TelluriumVideo1#Resources.md).

Open the web app and open the **TrUMP IDE** plugin and click on the DOM elements. TrUMP IDE will record all the DOM elements selected. Click on the generate button and it will output the default UI model. Customization option is available too. Just click on the Customize and it will show you the DOM elements in hierarchy. Select one element and it will show the attributes in the right pane. Mostly I change the UID to be more meaningful. Once you are done with your changes, click the Save button. Now you can copy/paste the UI model or you can click on the File->export to export as a Groovy file.

## Resources ##

The sample project

http://aost.googlecode.com/files/Tellurium_Sample.zip

The Screencast video is available at

http://aost.googlecode.com/files/tellurium_video_1.avi

Selenium

http://seleniumhq.org/download/

TrUMP

  * Firefox 2: http://aost.googlecode.com/files/TrUMP-0.1.0.FF2.xpi
  * Firefox 3: http://aost.googlecode.com/files/TrUMP-0.1.0.FF3.xpi