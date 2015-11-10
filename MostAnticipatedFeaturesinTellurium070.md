

# Introduction #

Tellurium 0.7.0 will be a milestone release with a lot of new features. Here we like to list some of the most anticipated ones.

# Most Anticipated Features #

## Group Locating ##

Up to Tellurium 0.6.0, Tellurium still generates runtime locators such as XPath and CSS selector on the Tellurium Core side, then pass them to Selenium Core, which is basically still to locate one element in the UI module at a time. With the new Engine in Tellurium 0.7.0, the UI module will be located as a whole first, the subsequent calls will reuse the already located UI element in the DOM.

Group Locating has some fundamental impacts on Tellurium and this can be explained by an example.

For instance, you have the following html on the page that you want to test.

```
<H1>FORM Authentication demo</H1>

<div class="box-inner">
    <a href="js/tellurium-test.js">Tellurium Test Cases</a>
    <input name="submit" type="submit" value="Test">
</div>

<form method="POST" action="j_security_check">
    <table border="0" cellspacing="2" cellpadding="1">
        <tr>
            <td>Username:</td>
            <td><input size="12" value="" name="j_username" maxlength="25" type="text"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input size="12" value="" name="j_password" maxlength="25" type="password"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input name="submit" type="submit" value="Login">
            </td>
        </tr>
    </table>
</form>
```

The correct UI module is shown as follows,

```
    ui.Container(uid: "Form", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }
```

Assume the html was changed recently and you still use the UI module defined some time ago.

```
    ui.Container(uid: "ProblematicForm", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])
    }
```

Here are the differences:

```
   InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
   InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
   
   InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
   InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])

   SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
   SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])     
```

What will happen without the new group locating algorithm? You tests will be broken because the generated locators will not be correct any more. But if you use the latest Tellurium 0.7.0 snapshot, you will notice that the tests still work if you allow Tellurium to do closest match by calling

```
    useClosestMatch(true);
```

The magic is that the new Tellurium Engine will locate the UI module as a whole. It may have trouble to find the individual UI element such as "ProblematicForm.Username.Input", but it has no trouble to find the whole UI module structure in the DOM.

Apart from that, Tellurium 0.7.0 also provides a handy method for you to validate your UI module. For example, if you call

```
    validateUiModule("ProblematicForm");
```

You will get the detailed validation results including the closest matches.

```
UI Module Validation Result for ProblematicForm

-------------------------------------------------------

	Found Exact Match: false 

	Found Closest Match: true 

	Match Count: 1 

	Match Score: 85.764 


	Closest Match Details: 

	--- Element ProblematicForm.Submit -->

	 Composite Locator: <input name="submit" value="logon" type="submit"/> 

	 Closest Matched Element: <input name="submit" value="Login" type="submit"> 



	--- Element ProblematicForm.Username.Input -->

	 Composite Locator: <input name="j" type="text"/> 

	 Closest Matched Element: <input size="12" value="" name="j_username" maxlength="25" type="text"> 



	--- Element ProblematicForm.Password.Input -->

	 Composite Locator: <input name="j" type="password"/> 

	 Closest Matched Element: <input size="12" value="" name="j_password" maxlength="25" type="password"> 


-------------------------------------------------------

```

## Bundle ##

Tellurium 0.7.0 added a new bundle tier to automatically pack multiple Selenium API or Tellurium API commands into one call to reduce the round trip network latency.

This feature can be easily turned on or off in [the Tellurium configuration file](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile).

```
    //the bundling tier
    bundle{
        maxMacroCmd = 5
        useMacroCommand = false
    }
```

Or use the following commands in your code.

```
    void useMacroCmd(boolean isUse);
    void setMaxMacroCmd(int max);
```

If you look at the server log and you will see what happened under the hood as follows.

```
14:57:49.584 INFO - Command request: getBundleResponse[[{"uid":"ProblematicForm.Username.Input","args":["jquery=table tr input[type=text][name=j]","t"],"name":"keyDown","sequ":44},{"uid":"ProblematicForm.Username.Input","args":["jquery=table tr input[type=text][name=j]","t"],"name":"keyPress","sequ":45},{"uid":"ProblematicForm.Username.Input","args":["jquery=table tr input[type=text][name=j]","t"],"name":"keyUp","sequ":46},{"uid":"ProblematicForm.Password.Input","args":["jquery=table tr input[type=password][name=j]","t"],"name":"keyDown","sequ":47},{"uid":"ProblematicForm.Password.Input","args":["jquery=table tr input[type=password][name=j]","t"],"name":"keyPress","sequ":48}], ] on session 9165cd68806a42fdbdef9f87e804a251
14:57:49.617 INFO - Got result: OK,[] on session 9165cd68806a42fdbdef9f87e804a251

```

In the above example, the command bundle includes the following commands:

```
    keyDown "ProblematicForm.Username.Input", "t"
    keyPress "ProblematicForm.Username.Input", "t"
    keyUp "ProblematicForm.Username.Input", "t"
    keyDown "ProblematicForm.Username.Input", "t"
    keyPress "ProblematicForm.Username.Input", "t"
```

and they are combined as a single API call to the Tellurium Engine.

## New Tellurium APIs ##

Tellurium Engine in 0.7.0 re-implemented a set of Selenium APIs by exploiting jQuery, plus many more new APIs. For example,

```
TelluriumApi.prototype.getCSS = function(locator, cssName) {
    var element = this.cacheAwareLocate(locator);
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).css(cssName));
    });
    return JSON.stringify(out);
};
```

To switch between Tellurium new API and Selenium Core API, you can call the following method.

```
    public void useTelluriumApi(boolean isUse);
```

Or use the following DSL methods from DslContext.

```
   public void enableTelluriumApi();
   public void disableTelluriumApi()
```

## UI Module Caching ##

From Tellurium 0.6.0, we provides the cache capability for CSS selectors so that we can reuse them without doing re-locating. In 0.7.0, we move a step further to cache the whole UI module on the Engine side. Each UI module cache holds a snapshot of the DOM references for most of the UI elements in the UI module. The exceptions are dynamic web elements defined by Tellurium UI templates. For these dynamic web elements, the Engine will try to get the DOM reference of its parent and then do locating inside this subtree with its parent node as the root, which will improve the locating speed a lot.

To turn on and off the caching capability, you just simply call the following method in your code.

```
    void useCache(boolean isUse);
```

Or use the following methods to do fine control of the cache.

```
    public void enableCache(); 
    public boolean disableCache();
    public boolean cleanCache();
    public boolean getCacheState();
    public void setCacheMaxSize(int size);
    public int getCacheSize();
    public void useDiscardNewCachePolicy();
    public void useDiscardOldCachePolicy();
    public void useDiscardLeastUsedCachePolicy();
    public void useDiscardInvalidCachePolicy();
    public String getCurrentCachePolicy();
    public Map<String, Long> getCacheUsage();
```

# Other Features #

An incomplete list of other features include:

  * [I18N support](http://code.google.com/p/aost/wiki/InternationalizationSupportTellurium)
  * More flexible UI templates
  * Built-in trace on execution time
  * Environment
  * [TestNG ReportNG support](http://code.google.com/p/aost/wiki/BetterReportingWithReportNG)

Please check [What's new in Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Update) for the latest updates.

# Code Samples #

What to exploit the new features in Tellurium 0.7.0 right away? Please check out the Maven project from http://aost.googlecode.com/svn/branches/santa-algorithm-demo. Or download the whole project with all dependencies (under the lib directory) from [here](http://aost.googlecode.com/files/santa-algorithm-demo.tar.gz).

# Resources #

  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide070Introduction)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [What's new in Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Update)
  * [Tellurium at Rich Web Experience 2009](http://www.slideshare.net/John.Jian.Fang/tellurium-at-rich-web-experience2009-2806967)