

# Prerequisites #

  * [[Tellurium Core 0.7.0-SNAPSHOT](http://maven.kungfuters.org/content/repositories/snapshots/tellurium/tellurium-core/0.7.0-SNAPSHOT/)

# Introduction #

[Tellurium Core](http://code.google.com/p/aost/wiki/UserGuide?tm=6#Tellurium_Core) is a UI module to runtime locator mapping framework. You can define UI module as a nested UI object, the Tellurium core generates the runtime locator for you.

http://tellurium-users.googlegroups.com/web/TelluriumUMLSystemDiagram.png?gda=0H5Erk8AAAD5mhXrH3CK0rVx4StVj0LYqZdbCnRI6ajcTiPCMsvamYLLytm0aso8Q_xG6LhygcwA_EMlVsbw_MCr_P40NSWJnHMhSp_qzSgvndaTPyHVdA&gsc=cq8RLwsAAADwIKTw30t0VbWQq6vz0Jcq

Usually, the main problem that users have is that their UI module is not defined correctly. As a result, the generated runtime locator is either not unique or cannot be found. Very often, users ask our developers to trace or debug their test code. However, it is a difficult task for our Tellurium developers, too because usually the web application and their full test code are not available to us. It would be more important to provide users some utilities for them to trace/debug their code by themselves instead of relying on our Tellurium developers.

The utility method _diagnose_ is designed for this purpose, which is available in the DslContext class and the method signature is as follows,

```
public void diagnose(String uid)
```

What it actually does is to dump the following information to console,

  1. The number of the matching UI element for the runtime locator corresponding to the _uid_.
  1. The html source for the parent UI object of the UI object _uid_.
  1. The closest matching UI elements in the DOM for the generated locator.
  1. The html source for the entire page.

Most of the above are optional, and thus, Tellurium provides you three more methods for your convenience.

```
public DiagnosisResponse getDiagnosisResult(String uid)
public void diagnose(String uid, DiagnosisOption options)
public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options)
```

where DiagnosisResponse is defined as

```
public class DiagnosisResponse {
  private String uid;

  private int count;

  private ArrayList<String> matches;

  private ArrayList<String> parents;

  private ArrayList<String> closest;

  private String html;
}
```

so that you can process the result programmatically.

DiagnosisOption is used to configure the return result,

```
public class DiagnosisOption {

  boolean retMatch = true;

  boolean retHtml = true;

  boolean retParent = true;

  boolean retClosest = true;
}
```


# Implementation #

Under the hood, Tellurium core first creates a request for the diagnose call,

```
public class DiagnosisRequest {
  //uid for the UI object
  private String uid;

  //parent UI object's locator
  private String pLocator;

  //UI objects attributes obtaining from the composite locator
  private Map<String, String> attributes;

  //options for the return results
  private boolean retMatch;
  
  private boolean retHtml;

  private boolean retParent;

  private boolean retClosest;
```

The request is then converted into a JSON string so that we can pass the request to Selenium as a custom method,

```
class CustomSelenium extends DefaultSelenium {
    ......

    public String diagnose(String locator, String request){
		String[] arr = [locator, request];
		String st = commandProcessor.doCommand("getDiagnosisResponse", arr);
		return st;
    }
}
```

The custom Selenium server includes [our jQuery selector](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector) support. We add the following new Selenium method,

```
Selenium.prototype.getDiagnosisResponse = function(locator, req){
......
}
```

I wouldn't go over the implementation details for this method and you can read the source code on Tellurium Engine project if you are really interested.

# Usage #

Assume we have the following Tellurium UI module defined

```
public class ProgramModule extends DslContext {

    public static String HTML_BODY = """
<div id="ext-gen437" class="x-form-item" tabindex="-1">
    <label class="x-form-item-label" style="width: 125px;" for="ext-comp-1043">
        <a class="help-tip-link" onclick="openTip('Program','program');return false;" title="click for more info" href="http://localhost:8080">Program</a>
    </label>

    <div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" type="hidden" name="programId" value=""/>
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" type="text" autocomplete="off"
                   size="24" readonly="true" style="width: 343px;"/>
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif"/>
        </div>
    </div>
    <div class="x-form-clear-left"/>
</div>
    """

  public void defineUi() {
    ui.Container(uid: "Program", clocator: [tag: "div"], group: "true") {
      Div(uid: "label", clocator: [tag: "a", text: "Program"])
      Container(uid: "triggerBox", clocator: [tag: "div"], group: "true") {
        InputBox(uid: "inputBox", clocator: [tag: "input", type: "text", readonly: "true"], respond: ["click"])
        Image(uid: "trigger", clocator: [tag: "img", src: "*images/s.gif"], respond: ["click"])
      }
    }
  }
}
```

We create a Tellurium test case using the [MockHttpServer](http://code.google.com/p/aost/wiki/TelluriumMockHttpServer) without running an actual web application.

```
public class ProgramModuleTestCase extends TelluriumJavaTestCase{
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/program.html", ProgramModule.HTML_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ProgramModule pm = new ProgramModule();
        pm.defineUi();
        pm.useJQuerySelector();
        connectUrl("http://localhost:8080/program.html");
        pm.diagnose("Program.triggerBox.trigger");
        pm.click("Program.triggerBox.trigger");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
```

Note that we want to diagnose the Image UI object "Program.triggerBox.trigger",

```
pm.diagnose("Program.triggerBox.trigger");
```

Run the test and you will see the return result as follows,

```
Diagnosis Result for Program.triggerBox.trigger

-------------------------------------------------------

	Matching count: 1

	Match elements: 

	--- Element 1 ---

<img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">


	Parents: 

	--- Parent 1---

<div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>
    </div>

	--- Parent 2---

<div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>


	Closest: 

	--- closest element 1---

<img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
HTML Source: 

<head>
    <title>Mock HTTP Server</title>
</head>
<body>
  <div id="ext-gen437" class="x-form-item" tabindex="-1">
    <label class="x-form-item-label" style="width: 125px;" for="ext-comp-1043">
        <a class="help-tip-link" onclick="openTip('Program','program');return false;" title="click for more info" href="http://localhost:8080">Program</a>
    </label>

    <div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>
    </div>
    <div class="x-form-clear-left">
    </div>
  </div>
</body>

-------------------------------------------------------
```

This is really the happy path and runtime locator is found and is unique. What if the UI module definition is a bit wrong about the Image object?

```
   Image(uid: "trigger", clocator: [tag: "img", src: "*image/s.gif"], respond:["click"])
```

That is to say, the _src_ attribute is not correct.

Run the same test code and the result is as follows,

```
Diagnosis Result for Program.triggerBox.trigger

-------------------------------------------------------

	Matching count: 0


	Parents: 

	--- Parent 1---

<div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>
    </div>

	--- Parent 2---

<div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>


	Closest: 

	--- closest element 1---

<img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">

HTML Source: 

<head>
    <title>Mock HTTP Server</title>
</head>
<body>
  <div id="ext-gen437" class="x-form-item" tabindex="-1">
    <label class="x-form-item-label" style="width: 125px;" for="ext-comp-1043">
        <a class="help-tip-link" onclick="openTip('Program','program');return false;" title="click for more info" href="http://localhost:8080">Program</a>
    </label>

    <div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" name="programId" value="" type="hidden">
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" autocomplete="off" size="24" readonly="true" style="width: 343px;" type="text">
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
        </div>
    </div>
    <div class="x-form-clear-left">
    </div>
  </div>
</body>

-------------------------------------------------------
```

You can see that there is no matching elements for the runtime locator. But the good thing is that the diagnose method provides you the closest UI elements it can find from the DOM,

```
	--- closest element 1---

<img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" src="images/s.gif">
```

By looking at this above lines, we could realize that the _src_ attribute is wrong in our UI module.

Some careful readers may want to ask "why you add a partial matching symbol `*` to the src attribute in the UI module. The reason is that in jQuery, seems the _src_ attribute in an Image has to be a full URL such as http://code.google.com/p/aost/. One workaround is to put the partial matching symbol `*` before the URL.

In some case, the return matching count is larger than 1 and you can figure out how to update your UI module definition by looking at all the return elements and their parents.

# How to Obtain #

To use the diagnose method, you need the 0.7.0-SNAPSHOT Tellurium Core jar and custom Selenium server 1.0.1-te2. You need to use the following Maven dependencies,

```
        <dependency>
            <groupId>tellurium</groupId>
            <artifactId>tellurium-core</artifactId>
            <version>0.7.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium.server</groupId>
            <artifactId>selenium-server</artifactId>
            <version>1.0.1-te2</version>
        </dependency>
```

Of course, you need to set up Tellurium Maven repository in your settings.xml or your project POM.

```
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
        <repository>
            <id>kungfuters-thirdparty-releases-repo</id>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/thirdparty</url>
        </repository>
```

Please check [Tellurium Maven guide](http://code.google.com/p/aost/wiki/MavenHowTo) for more details.

# How to Contribute #

If you are a Javascript or jQuery guru and like to contribute to Tellurium, please contact us. We are looking for new members to join our team to work on the Trump and the Engine sub-projects.

# Resources #

  * [Tellurium Project website](http://code.google.com/p/aost/)
  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide)
  * [Tellurium User Guide PDF Version](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf).
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)