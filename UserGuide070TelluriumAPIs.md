(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))



# Tellurium APIs #

Tellurium APIs include all methods defined in DslContext. This chapter provides tables showing the various API methods and the action/result of each method when used:

  * DSL Methods
  * Data Access Methods
  * Test Support DSLs

## DSL Methods ##

This section contains a list of all available Tellurium methods that can be used as DSLs in DslContext and their corresponding DSL syntax.

**Note** the id here refers to the UiID in the format of "issueSearch.issueType" and the time units are all in milliseconds, if not specified.

Be aware, the user can only apply the methods to the Ui Object if it has these methods previously defined.

|**METHOD**|**ACTION**|
|:---------|:---------|
| `mouseOver id:` | Simulates a user hovering a mouse over the specified element.|
| `mouseOut id:`| Simulates a user moving the mouse pointer away from the specified element.|
| `mouseDown id:`| Simulates a user pressing the mouse button (without releasing it yet) on the specified element.|
| `click id:`| Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.|
| `doubleClick id:`| Double clicks on a link, button, checkbox or radio button. If the double click action causes a new page to load (like a link usually does), call waitForPageToLoad.|
| `clickAt id, coordination:`| Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.|
| `check id:`| Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.|
| `uncheck id:`| Uncheck a toggle-button (checkbox/radio).|
| `keyType id, value:`| Simulates keystroke events on the specified element, as though the user typed the value key-by-key.|
| `type id, input:`| Sets the value of an input field, as though typed it in.|
| `typeAndReturn id, input:`| Sets the value of an input field followed by `<Return>` key.|
| `clearText id:`| Resets input field to an empty value.|
| `select id, optionLocator:`| Select an option from a drop-down using an option locator.|
| `selectByLabel id, optionLocator:`| Select an option from a drop-down using an option label.|
| `selectByValue id, optionLocator:`| Select an option from a drop-down using an option value.|
| `addSelectionByLabel id, optionLocator:`| Add a selection to the set of selected options in a multi-select element using an option label.|
| `addSelectionByValue id, optionLocator:`| Add a selection to the set of selected options in a multi-select element using an option value.|
| `removeSelectionByLabel id, optionLocator:`| Remove a selection from the set of selected options in a multi-select element using an option label.|
| `removeSelectionByValue id, optionLocator:`| Remove a selection from the set of selected options in a multi-select element using an option value.|
| `removeAllSelections id:`| Unselects all of the selected options in a multi-select element.|
| `pause time:`| Suspend the current thread for a specified milliseconds.|
| `submit id, attribute:`| Submit the specified form. This is particularly useful for forms without submit buttons. For example: single-input "Search" forms.|
| `openWindow UID, url:`| Opens a popup window. (If a window with that specific ID is not already open). After opening the window, select it using the selectWindow command.|
| `selectWindow UID:`| Selects a popup window; once a popup window has been selected, all commands go to that window. To select the main window again, use null as the target.|
| `closeWindow UID:`| Close the popup window.|
| `selectMainWindows:`| Select the original window. For example: the Main window.|
| `selectFrame frameName:`| Selects a frame within the current window.|
| `selectParentFrameFrom frameName:`| Select the parent frame from the frame identified by the "frameName".|
| `selectTopFrameFrom:`| Select the main frame from the frame identified by the "frameName".|
| `waitForPopUp UID, timeout:`| Waits for a popup window to appear and load up.|
| `waitForPageToLoad timeout:`| Waits for a new page to load.|
| `waitForFrameToLoad uid, timeout:`| Waits for a new frame to load. Be aware that Selenium uses the _name_ attribute to locate a Frame.|
| `runScript script:`| Creates a new "script" tag in the body of the current test window, and adds the specified text into the body of the command. Scripts run this way can often be debugged more easily than scripts executed using Selenium's "getEval" command.|

Beware that JS exceptions thrown in these script tags are not managed by Selenium, so the user should wrap the script in try/catch blocks if there is any chance that the script will throw an exception.

| `captureScreenshot filename:`| Captures a PNG screenshot to the specified file.|
|:-----------------------------|:------------------------------------------------|
| `chooseCancelOnNextConfirmation:`| By default, Selenium's overridden window.confirm() function returns true, as if the user had manually clicked OK. |

After running this command, the next call to confirm() returns false, as if the user had clicked Cancel. Selenium then resumes using the default behavior for future confirmations, automatically returning true (OK) unless/until the user explicitly calls this command for each confirmation.

| `chooseOkOnNextConfirmation:`| Undo the effect of calling chooseCancelOnNextConfirmation. |
|:-----------------------------|:-----------------------------------------------------------|

**Note:** Selenium's overridden `window.confirm()` function normally automatically returns true, as if the user had manually clicked OK. The user does not need to use this command unless for some reason there is a need to change prior to the next confirmation.

After any confirmation, Selenium resumes using the default behavior for future confirmations, automatically returning true (OK) unless/until the user explicitly calls chooseCancelOnNextConfirmation for each confirmation.

| `answerOnNextPrompt(String answer)):`| Instructs Selenium to return the specified answer string in response to the next JavaScript prompt window.prompt()|
|:-------------------------------------|:------------------------------------------------------------------------------------------------------------------|
| `goBack:`                            | Simulates the user clicking the "back" button on their browser.                                                   |
| `refresh:`                           | Simulates the user clicking the "Refresh" button on their browser.                                                |
| `dragAndDrop(uid, movementsString):` | Drags an element a certain distance and then drops it.                                                            |
| `dragAndDropTo(sourceUid, targetUid):`| Drags an element and drops it on another element.                                                                 |

## Data Access Methods ##

In addition to the DSL methods, Tellurium provides Selenium-compatible data access methods so that a user can get data or the status of UIs from the web.

|<b>METHOD</b>|<b>ACTION/RESULT</b>|
|:------------|:-------------------|
| `String getConsoleInput():`| Gets input from Console.|
| `String[] getSelectOptions(id):`| Gets all option labels in the specified select drop-down.|
| `String[] getSelectedLabels(id):`| Gets all selected labels in the specified select drop-down.|
| `String getSelectedLabel(id):`| Gets a single selected label in the specified select drop-down.|
| `String[] getSelectedValues(id):`| Gets all selected values in the specified select drop-down.|
| `String getSelectedValue(id):`| Gets a single selected value in the specified select drop-down.|
| `String[] getSelectedIndexes(id):`| Gets all selected indexes in the specified select drop-down.|
| `String getSelectedIndex(id):`| Gets a single selected index in the specified select drop-down.|
| `String[] getSelectedIds(id):`| Gets option element ID for selected option in the specified select element.|
| `String getSelectedId(id):`| Gets a single element ID for selected option in the specified select element.|
| `boolean isSomethingSelected(id):`| Determines whether some option in a drop-down menu is selected.|
| `String waitForText(id, timeout):`| Waits for a text event.|
| `int getTableHeaderColumnNum(id):`| Gets the column header count of a table.|
| `int getTableMaxRowNum(id):`| Gets the maximum row count of a table.|
| `int getTableMaxColumnNum(id):`| Gets the maximum column count of a table|
| `int getTableFootColumnNum(id):`| Gets the maximum foot column count of a standard table|
| `int getTableMaxTbodyNum(id):`| Gets the maximum tbody count of a standard table|
| `int getTableMaxRowNumForTbody(id, index):`| Gets the maximum row number of the index-th tbody of a standard table|
| `int getTableMaxColumnNumForTbody(id, index):`| Gets the maximum column number of the index-th tbody of a standard table|
| `int getListSize(id):`| Gets the item count of a list|
| `getUiElement(id):`| Gets the UIObject of an element.|
| `boolean isElementPresent(id):`| Verifies that the specified element is somewhere on the page.|
| `boolean isVisible(id):`| Determines if the specified element is visible.|

An element can be rendered invisible by setting the CSS "visibility" property to "hidden", or the "display" property to "none", either for the element itself or one if its ancestors. This method will fail if the element is not present.

| `boolean isChecked(id):`| Gets whether a toggle-button (checkbox/radio) is checked. Fails if the specified element doesn't exist or isn't a toggle-button.|
|:------------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| `boolean isDisabled(id):`| Determines if an element is disabled or not.                                                                                    |
| `boolean isEnabled(id):`| Determines if an element is enabled or not.                                                                                     |
| `boolean waitForElementPresent(id, timeout):`| Wait for the Ui object to be present.                                                                                           |
| `boolean waitForElementPresent(id, timeout, step):`| Wait for the Ui object to be present and check the status by step.                                                              |
| `boolean waitForCondition(script, timeout):`| Runs the specified JavaScript snippet repeatedly until it evaluates to "true". The snippet may have multiple lines, but only the result of the last line will be considered.|
| `String getText(id):`   | Gets the text of an element.                                                                                                    |

This works for any element that contains text. This command uses either the textContent (Mozilla-like browsers) or the innerText (IE-like browsers) of the element, which is the rendered text shown to the user.

| `String getValue(id):`| Gets the (whitespace-trimmed) value of an input field (or anything else with a value parameter). |
|:----------------------|:-------------------------------------------------------------------------------------------------|

For checkbox/radio elements, the value will be "on" or "off" depending on whether the element is checked or not.

| `String getLink(id):`| Get the href of an element.|
|:---------------------|:---------------------------|
| `String getImageSource(id):`| Get the image source element.|
| `String getImageAlt(id):`| Get the image alternative text of an element.|
| `String getImageTitle(id):`| Get the image title of an element.|
| `getAttribute(id, attribute):`| Get an attribute of an element.|
| `getParentAttribute(id, attribute):`| Get an attribute of the parent of an element.|
| `String getBodyText():`| Gets the entire text of the page.|
| `boolean isTextPresent(pattern):`| Verifies that the specified text pattern appears somewhere on the rendered page shown to the user.|
| `boolean isEditable(id):`| Determines whether the specified input element is editable, ie hasn't been disabled. This method will fail if the specified element isn't an input element.|
| `String getHtmlSource():`| Returns the entire HTML source between the opening and closing "html" tags.|
| `String getExpression(expression):`| Returns the specified expression.|
| `getXpathCount(xpath):`| Returns the number of nodes that match the specified xpath, eg. "//table" would give the number of tables.|
| `String getCookie():`| Return all cookies of the current page under test.|
| `boolean isAlertPresent():`| Has an alert occurred?     |
| `boolean isPromptPresent():`| Has a prompt occurred?     |
| `boolean isConfirmationPresent():`| Has confirm() been called? |
| `String getAlert():` | Retrieves the message of a JavaScript alert generated during the previous action, or fail if there were no alerts.|
| `String getConfirmation():`| Retrieves the message of a JavaScript confirmation dialog generated during the previous action.|
| `String getPrompt():`| Retrieves the message of a JavaScript question prompt dialog generated during the previous action.|
| `String getLocation():`| Gets the absolute URL of the current page.|
| `String getTitle():` | Gets the title of the current page.|
| `String[] getAllButtons():`| Returns the IDs of all buttons on the page.|
| `String[] getAllLinks():`| Returns the IDs of all links on the page.|
| `String[] getAllFields():`| Returns the IDs of all input fields on the page.|
| `String[] getAllWindowIds():`| Returns the IDs of all windows that the browser knows about.|
| `String[] getAllWindowNames():`| Returns the names of all windows that the browser knows about.|
| `String[] getAllWindowTitles():`| Returns the titles of all windows that the browser knows about.|
| `allowNativeXpath(boolean allow):`| Specifies whether Selenium should use the native in-browser implementation of XPath (if any native version is available); if you pass false to this function, we will always use our pure-JavaScript xpath library.|

## UI Module APIs ##

UI Module represents a group of nested UI elements or a UI widget and it is the heart of Tellurium Automated Testing Framework (Tellurium). You may not be aware that Tellurium 0.7.0 provides a set of UI module level APIs. Here we go over them one by one.

### Example ###

First of all, we like to use Google Search module as an example so that everyone can run the test code in this article.

For Google Search Module, we define the UI module class as

```
package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

public class GoogleSearchModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }

    ui.Container(uid: "ProblematicGoogle", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "p"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btns"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnf"])
    }    
  }

  public void doProblematicGoogleSearch(String input) {
    keyType "ProblematicGoogle.Input", input
    pause 500
    click "ProblematicGoogle.Search"
    waitForPageToLoad 30000
  }
}
```

where UI Module "Google" is a correct UI Module definition for Google Search and "ProblematicGoogle" is a not-so-correct UI module to demonstrate the power of UI module partial matching in Tellurium 0.7.0.

### dump ###

The _dump_ method prints out the generated runtime locators by Tellurium core.

#### API ####

```
   void dump(String uid);
```
where _uid_ is the UI module name, i.e., the root element UID of a UI module.

#### Test case ####

```
    @Test
    public void testDump(){
        useCssSelector(false);
        gsm.dump("Google");
        useCssSelector(true);
        gsm.dump("Google");
    }
```

Note that here we ask Tellurium core to generate xpath and CSS selector locators, respectively.

#### Results ####

```
Dump locator information for Google
-------------------------------------------------------
Google: //descendant-or-self::table
Google.Input: //descendant-or-self::table/descendant-or-self::input[@title="Google Search" and @name="q"]
Google.Search: //descendant-or-self::table/descendant-or-self::input[@type="submit" and @value="Google Search" and @name="btnG"]
Google.ImFeelingLucky: //descendant-or-self::table/descendant-or-self::input[@type="submit" and @value="I'm Feeling Lucky" and @name="btnI"]
-------------------------------------------------------

Dump locator information for Google
-------------------------------------------------------
Google: jquery=table
Google.Input: jquery=table input[title=Google Search][name=q]
Google.Search: jquery=table input[type=submit][value=Google Search][name=btnG]
Google.ImFeelingLucky: jquery=table input[type=submit][value$=m Feeling Lucky][name=btnI]
-------------------------------------------------------
```

### toString ###

The _toString_ method converts a UI module to a JSON presentation. Tellurium Core actually provides two methods for your convenience.

#### API ####

```
   public String toString(String uid);
   public JSONArray toJSONArray(String uid);
```

The _toString_ method calls the _toJSONArray_ method first under the hood and then prints out the JSON array as a string.

#### Test case ####

```
    @Test
    public void testToString(){
        String json = gsm.toString("Google");
        System.out.println(json);
    }
```

#### Results ####

```
   [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}]

```

### toHTML ###

The _toHTML_ method converts the UI module to a HTML source by [reverse engineering](http://code.google.com/p/aost/wiki/GenerateHtmlFromUIModule). This API is extremely useful to work with [Tellurium mock http server](http://code.google.com/p/aost/wiki/TelluriumMockHttpServer) if we want to diagnose problems in other people's UI module definitions while we have no access to their html sources.

#### API ####

```
   public String toHTML(String uid);
   public String toHTML();
```

The first method generates the HTML source for a UI module and the second one generates the HTML source for all UI modules defined in the current UI module class.

#### Test Case ####

```
   @Test
   public void testToHTML(){
       String html = gsm.toHTML("Google");
       System.out.println(html);
   }
```

#### Result ####

```
   <table>
      <input title="Google Search" name="q"/>
      <input type="submit" value="Google Search" name="btnG"/>
      <input type="submit" value="I'm Feeling Lucky" name="btnI"/>
   </table>
```

### getHTMLSource ###

The getHTMLSource method returns the runtime HTML source for a UI module.

#### API ####

```
   java.util.List getHTMLSourceResponse(String uid);
   void getHTMLSource(String uid);
```

The first method gets back the HTML source as a list of _key_ and _val_ pair and the second one prints out them to console.

```
   class KeyValuePair {
     public static final String KEY = "key";
     String key;

     public static final String VAL = "val";
     String val;
   }
```

#### Test Case ####

```
    @Test
    public void testGetHTMLSource(){
        gsm.getHTMLSource("Google");
    }
```

#### Result ####

```
TE: Name: getHTMLSource, start: 1266260182744, duration: 67ms
TE: Found exact match for UI Module 'Google': {"id":"Google","relaxDetails":[],"matches":1,"relaxed":false,"score":100.0,"found":true}
Google: 

<table cellpadding="0" cellspacing="0"><tbody><tr valign="top"><td width="25%">&nbsp;</td><td nowrap="nowrap" align="center"><input name="hl" value="en" type="hidden"><input name="source" value="hp" type="hidden"><input autocomplete="off" onblur="google&amp;&amp;google.fade&amp;&amp;google.fade()" maxlength="2048" name="q" size="55" class="lst" title="Google Search" value=""><br><input name="btnG" value="Google Search" class="lsb" onclick="this.checked=1" type="submit"><input name="btnI" value="I'm Feeling Lucky" class="lsb" onclick="this.checked=1" type="submit"></td><td id="sbl" nowrap="nowrap" width="25%" align="left"><font size="-2">&nbsp;&nbsp;<a href="/advanced_search?hl=en">Advanced Search</a><br>&nbsp;&nbsp;<a href="/language_tools?hl=en">Language Tools</a></font></td></tr></tbody></table>

Google.Input: 

<input autocomplete="off" onblur="google&amp;&amp;google.fade&amp;&amp;google.fade()" maxlength="2048" name="q" size="55" class="lst" title="Google Search" value="">

Google.Search: 

<input name="btnG" value="Google Search" class="lsb" onclick="this.checked=1" type="submit">

Google.ImFeelingLucky: 

<input name="btnI" value="I'm Feeling Lucky" class="lsb" onclick="this.checked=1" type="submit">

```

### show ###

The _show_ method outlines the UI module on the web page under testing and shows [some visual effects](http://code.google.com/p/aost/wiki/TelluriumUiModuleVisualEffect) when a user mouses over it.

#### API ####

```
   void show(String uid, int delay);
   void startShow(String uid);
   void endShow(String uid);
```

The first method shows the UI module visual effect for some time (delay in milliseconds). The other two methods are used to manually start and end the visual effect.

#### Test Case ####

```
    @Test
    public void testShow(){
        gsm.show("Google", 10000);
//        gsm.startShow("Form");
//        gsm.endShow("Form");
    }
```

#### Result ####

The visual effect is illustrated in the following graph.

http://tellurium-users.googlegroups.com/web/GoogleSearchShowUi.png?gda=qOZnC0gAAACsdq5EJRKLPm_KNrr_VHB_3eE2qYcIelEaKO4G2gjlKg0yV8_RHJOjV4bPZr7T9lYlzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=m1-YYgsAAADW8LgqTyA9jlZ6blL9elJr

### validate ###

The _validate_ method is used to validate if the UI module is correct and returns the mismatches at runtime.

#### API ####

```
   UiModuleValidationResponse getUiModuleValidationResult(String uid);
   void validate(String uid)
```

The first method returns the validation result as a UiModuleValidationResponse object,

```
public class UiModuleValidationResponse {
  
    //ID for the UI module
    public static String ID = "id";
    private String id = null;

    //Successfully found or not
    public static String FOUND = "found";
    private boolean found = false;

    //whether this the UI module used closest Match or not
    public static String RELAXED = "relaxed";
    private boolean relaxed = false;

    //match count
    public static String MATCHCOUNT = "matches";
    private int matches = 0;

    //scaled match score (0-100)
    public static String SCORE = "score";
    private float score = 0.0;

    public static String RELAXDETAIL = "relaxDetail";
    //details for the relax, i.e., closest match
    public static String RELAXDETAILS = "relaxDetails";
    private List<RelaxDetail> relaxDetails = null;
```

and the RelaxDetail is defined as follows,

```
public class RelaxDetail {
    //which UID got relaxed, i.e., closest Match
    public static String UID = "uid";
    private String uid = null;

    //the clocator defintion for the UI object corresponding to the UID
    public static String LOCATOR = "locator";
    private CompositeLocator locator = null;

    //The actual html source of the closest match element
    public static String HTML = "html";
    private String html = null;
```

The second one simply prints out the result to console.

#### Test Case ####

```
    @Test
    public void testValidate(){
        gsm.validate("Google");
        gsm.validate("ProblematicGoogle");
    }
```

Here we validate the correct UI module "Google" and the not-so-correct UI module "ProblematicGoogle".

#### Result ####

```
TE: Name: getValidateUiModule, start: 1266260203639, duration: 68ms

UI Module Validation Result for Google

-------------------------------------------------------

	Found Exact Match: true 

	Found Closest Match: false 

	Match Count: 1 

	Match Score: 100 


-------------------------------------------------------

TE: Name: getValidateUiModule, start: 1266260203774, duration: 41ms

UI Module Validation Result for ProblematicGoogle

-------------------------------------------------------

	Found Exact Match: false 

	Found Closest Match: true 

	Match Count: 1 

	Match Score: 32.292 


	Closest Match Details: 

	--- Element ProblematicGoogle.Input -->

	 Composite Locator: <input title="Google Search" name="p"/> 

	 Closest Matched Element: <input autocomplete="off" onblur="google&amp;&amp;google.fade&amp;&amp;google.fade()" maxlength="2048" name="q" size="55" class="lst" title="Google Search" value=""> 



	--- Element ProblematicGoogle.Search -->

	 Composite Locator: <input name="btns" value="Google Search" type="submit"/> 

	 Closest Matched Element: <input name="btnG" value="Google Search" class="lsb" onclick="this.checked=1" type="submit"> 



	--- Element ProblematicGoogle.ImFeelingLucky -->

	 Composite Locator: <input name="btnf" value="I'm Feeling Lucky" type="submit"/> 

	 Closest Matched Element: <input name="btnI" value="I'm Feeling Lucky" class="lsb" onclick="this.checked=1" type="submit"> 

-------------------------------------------------------

```

As we can see, the correct UI module returns a score as 100 and the problematic UI module returns a score less than 100.

### Closest Match ###

The UI module closest match, i.e., partial match, is extremely important to keep your test code robust to changes to some degree. By partial match, Tellurium uses  [the Santa algorithm](http://code.google.com/p/aost/wiki/SantaUiModuleGroupLocatingAlgorithm) to locate the whole UI module by using only partial information inside the UI module.

#### API ####

```
   enableClosestMatch();
   disableClosestMatch();
   useClosestMatch(boolean isUse);
```

The first two methods are used in DslContext to enable and disable the closest match feature. The last one is used on Groovy or Java test case for the same purpose. Be aware that this feature only works with UI module caching, i.e., you should call either

```
   useTelluriumEngine(true);
```

or

```
   useCache(true);
```

before use this feature.

#### Test Case ####

Here we run our test code based on the problematic UI module "ProblematicGoogle".

```
    @Test
    public void testClosestMatch(){
        useClosestMatch(true);
        gsm.doProblematicGoogleSearch("Tellurium Source");
        useClosestMatch(false);
    }
```

#### Result ####

The test case passed successfully. What happens if you disable the closest match feature by calling

```
   useClosestMatch(true);
```

The above test case will break and you will get a red bar in IDE.

## Test Support DSLs ##

Tellurium defines a set of DSLs to support Tellurium tests. The most often used ones include:

connectSeleniumServer(): Connect to the selenium server.

disconnectSeleniumServer(): Disconnect the connection to the selenium server.

openUrl(String url): establish a new connection to Selenium server for the given url. The DSL format is:

```
    openUrl url
```

**Example:**

```
    openUrl "http://code.google.com/p/aost/"
```

connectUrl(String url): use existing connect for the given url. The DSL format is:

```
    connectUrl url
```

**Example:**

```
    connectUrl "http://code.google.com/p/aost/" 
```

`openUrlWithBrowserParameters()` is used to change browser settings for different test cases in the same test class. There are three methods:

```
   public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String baseUrl, String browser, String browserOptions)

   public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser, String browserOptions)

   public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser) 
```

For example,

```
public class GoogleStartPageTestNGTestCase extends TelluriumTestNGTestCase {
   protected static NewGoogleStartPage ngsp;

   @BeforeClass
   public static void initUi() {
       ngsp = new NewGoogleStartPage();
       ngsp.defineUi();
   }

   @DataProvider(name = "browser-provider")
   public Object[][] browserParameters() {
       return new Object[][]{
               new Object[] {"localhost", 4444, "*chrome"},
               new Object[] {"localhost", 4444, "*firefox"}};
   }

   @Test(dataProvider = "browser-provider")
   @Parameters({"serverHost", "serverPort", "browser"})
   public void testGoogleSearch(String serverHost, int serverPort, String browser){
       openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
       ngsp.doGoogleSearch("tellurium selenium Groovy Test");
       disconnectSeleniumServer();
  }

  @Test(dataProvider = "browser-provider")
  @Parameters({"serverHost", "serverPort", "browser"})
  public void testGoogleSearchFeelingLucky(String serverHost, int serverPort, String browser){
      openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
      ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
      disconnectSeleniumServer();
  }
}

```
