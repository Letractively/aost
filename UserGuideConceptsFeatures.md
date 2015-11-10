(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



# Concepts and Features #

## UI Object ##

Tellurium provides a set of predefined UI objects, which users can use directly.

The basic UI object is an abstract class. Users cannot instantiate it directly. The basic UI Object works as the base class for all UI objects and it includes the following attributes:

  1. uid: UI object's identifier
  1. namespace: can be used for XHTML
  1. locator: the locator of the UI object, could be a base locator or a composite locator
  1. group: this attribute is used for Group Locating and it only applies to a collection type of UI object such as Container, Table, List, Form.
  1. respond: the JavaScript events the UI object can respond to. The value is a list.

All UI Objects will inherit the above attributes and methods. But be aware that you usually do not call these methods directly and you should use DSL syntax instead. For example, use:

```
click "GoogleSearchModule.Search"
```

In this way, Tellurium will first map the UIID "GoogleSearchModule.Search" to the actual UI object and then call the _click_ method on it. If that Ui object does not have the _click_ method defined, you will get an error.

## Composite Locator ##

Tellurium supports two types of locators: _base locator_ and _composite locator_. The _base locator_ is a relative XPath. The _composite locator_, denoted by "clocator", specifies a set of attributes for the UI object and the actual locator will be derived automatically by Tellurium at runtime.

The Composite Locator is defined as follows:

```
class CompositeLocator {
    String header
    String tag
    String text
    String trailer
    def position
    boolean direct
    Map<String, String> attributes = [:]
}
```

To use the composite locator, you need to use "clocator" with a map as its value. For example:

```
clocator: [key1: value1, key2: value2, ...]
```

The default attributes include "header", "tag", "text", "trailer", "position", and "direct". They are all optional. The "direct" attribute specifies whether this UI object is a direct child of its parent UI, and the default value is "false".

If you have additional attributes, you can define them in the same way as the default attributes, for example:

```
clocator: [tag: "div", value: "Tellurium home"]
```

Most Tellurium objects come with default values for certain attributes, for example, the  tag attribute. If these attributes are not specified, the default attribute values will be used. In other words, if you know the default attribute values of a Tellurium UI object, you can omit them in clocator. Take the RadioButton object as an example, its default tag is "input," and its default type is "radio." You can omit them and write the clocator as follows:

```
clocator: [:]
```

which is equivalent to

```
clocator: [tag: "input", type: "radio"]
```

## UI Module ##

The UI Module is the heart of Tellurium. The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium's project site is defined as follows:

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

Tellurium is built on the foundation of the UI module. The UI module makes it possible to build locators for UI elements at runtime.  First, this makes Tellurium robust and responsive to changes from internal UI elements.  Second, the UI module makes Tellurium expressive.  A UI element can be referred to simply by appending the names (uids) along the path to the specific element. This also enables Tellurium's "Group Locating" feature, making composite objects reusable and addressing dynamic web pages.

This frees up the testers to write better tests rather than spend precious testing time on identifying and resolving test failures due to XPath changes.

## UiID ##

In Tellurium, the UI object is referred by its UiID. For nested Ui objects, the UiID of the UI Object is a concatenated of the UI objects' uids along its path to the UI Object. For example, in the following nested UI Module,

```
ui.Container(uid: "parent_ui"){
   InputBox(uid: "inputbox1", locator: "...")
   Button(uid: "button1", locator: "...")
   Container(uid: "child_ui){
     Selector(uid: "selector1", locator: "...")
     ...
     Container(uid: "grand_child"){
       TextBox(uid: "textbox1", locator: "...")
       ...
     }
   }
}
```

the TextBox is referred as "parent\_ui.child\_ui.grand\_child.textbox1". The exceptions are tables and lists, which use `[x][y]` or `[x]` to reference its elements inside. For example, `labels_table[2][1]` and `GoogleBooksList.subcategory[2]`. Table header can be referred in the format of `issueResult.header[2]`.

More general case can be shown by the following diagram,

http://tellurium-users.googlegroups.com/web/TelluriumUID.png?gda=Ewu9OkIAAAD5mhXrH3CK0rVx4StVj0LYJWJZMftxLZyWDn45QfUHefQJMzacoSN0t8eFUXmEG4hV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=D-2lBwsAAABkwKwxr1YSWDDrmKhvxTo1

For example, the UiID of the List _E_ in the above diagram is `A.C.E` and the InputButton in the List _E_ is referred by its index _`n`_, i.e., `A.C.E[n]`.

## Group Locating ##

In the Tellurium UI module, you often see the "group" attribute. For example:

```
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
  InputBox(uid: "searchbox", clocator: [title: "Google Search"])
  SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
  SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

What does the attribute _group_ mean? The group attribute is a flag for the Group Locating Concept. Usually, the XPath generated by Selenium IDE, XPather, or other tools is a single
path to the target node such as:

```
//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]
```

No sibling node's information is used here. What is wrong with this? The XPath depends too much on information from nodes far away from the target node. In Tellurium, we try to localize the information and reduce this dependency by using sibling information or local information. For example, in the above google UI module, the group locating concept will try to find the "td" tag with its children as "InputBox", "googlesearch" button, and
"Imfeelinglucky" button. In this way, we can reduce the dependencies of the UI elements inside a UI module on external UI elements to make the UI definition more robust.

## UI Templates ##

Tellurium UI templates are used for two purposes:

  1. When there are many identical UI elements, you can use one template to represent them all
  1. When here are a variable/dynamic size of UI elements at runtime, you know the patterns, but not the size.

More specifically, Table and List are two Tellurium objects that can define UI templates. Table defines two dimensional UI templates, and List is for one dimensional. The Template has special UIDs such as "2", "all", or "row: 1, column: 2".

Let us look at use case (1), we have the following HTML source

```
      <ul class="a">
        <li>
            <A HREF="site?tcid=a"
               class="b">AA
            </A>
        </li>
        <li>
            <A HREF="site?tcid=b"
               class="b">BB
            </A>
        </li>
        <li>
            <A HREF="site?;tcid=c"
               class="b">CC
            </A>
        </li>
        <li>
            <A HREF="site?tcid=d"
               class="b">DD
            </A>
        </li>
        <li>
            <A HREF="site?tcid=e"
               class="b">EE
            </A>
        </li>
        <li>
            <A HREF="site?tcid=f"
               class="b">FF
            </A>
        </li>
    </ul>
```

You have six links there. Without templates, you have to put six UrlLink objects in the UI module. Look at how simple by using the template

```
ui.List(uid: "list", clocator: [tag: "ul", class: "a"], separator:"li")
{
    UrlLink(uid: "all", clocator: [class: "b"])
}
```


For use case (2), a common application is the data grid. Look at the "issueResult" data grid on our Tellurium Issues page:

```
ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], 
         group: "true") 
{
    TextBox(uid: "header: 1",  clocator: [:])
    UrlLink(uid: "header: 2",  clocator: [text: "%%ID"])
    UrlLink(uid: "header: 3",  clocator: [text: "%%Type"])
    UrlLink(uid: "header: 4",  clocator: [text: "%%Status"])
    UrlLink(uid: "header: 5",  clocator: [text: "%%Priority"])
    UrlLink(uid: "header: 6",  clocator: [text: "%%Milestone"])
    UrlLink(uid: "header: 7",  clocator: [text: "%%Owner"])
    UrlLink(uid: "header: 9",  clocator: [text: "%%Summary + Labels"])
    UrlLink(uid: "header: 10", clocator: [text: "%%..."])

    //define table elements
    //for the border column
    TextBox(uid: "row: *, column: 1", clocator: [:])
    //For the rest, just UrlLink
    UrlLink(uid: "all", clocator: [:])
}
```

Aren't the definitions very simple and cool?

You may wonder how to use the templates if you have multiple templates such as the "issueResult" table shown above. The rule to apply the templates is: "specific one first, general one later".

## Javascript Events ##

Most web applications include Javascript, and thus the web testing framework must be able to handle Javascript events. What we really care about is firing the appropriate events to trigger the event handlers.

Selenium has already provided methods to generate events such as:

```
fireEvent(locator, "blur")
fireEvent(locator, "focus")
mouseOut(locator)
mouseOver(locator)
```

Tellurium was born with Javascript events in mind since it was initially designed to test applications written using the DOJO JavaScript framework. For example, we have the following radio button:

```
<input type='radio' name='mas_address_key' value='5779' onClick='SetAddress_5779()'>
```

Although we can define the radio button as follows:

```
RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'])
```

The above code will not respond to the click event since the Tellurium RadioButton only supports the "check" and "uncheck" actions, which is enough for the normal case. As a result, no "click" event/action will be generated during the testing. To address this problem, Tellurium added the "respond" attribute to Tellurium UI objects. The "respond" attribute could be used to define whatever events you want the UI object to respond to. The Radio Button can be redefined as:

```
 ui.Container(uid: "form", clocator: [whatever]){
     RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'], 
               respond: ["click"])
 }
```

That is to say, you can issue the following command:

```
  click "form.billing"
```

Even if the RadioButton does not have the _click_ method defined by default, it is still able to dynamically add the _click_ method at runtime and call it.

A more general example is as follows:

```
 InputBox(uid: "searchbox", clocator: [title: "Google Search"], 
          respond: ["click", "focus", "mouseOver", "mouseOut", "blur"])
```

Except for the "click" event, all of the "focus", "mouseOver", "mouseOut", and "blur" events will be automatically fired by Tellurium during testing. Do not worry about the event order for the respond attribute, Tellurium will automatically re-order the events and then process them appropriately for you.

## Logical Container ##

The Container object in Tellurium is used to hold child objects that are in the same subtree in the DOM object. However, there are always exceptions. For example, logical Container (Or virtual Container, but I think Logical Container is better) can violate this rule.

What is a logic Container? It is a Container with empty locator, for instance,

```
Container(uid: "logical"){
   ......
}
```


But empty != nothing, there are some scenarios that the logical container can play an important role. The reason is the Container includes a uid for you to refer and it can logically group UI element together.

Look at the following example,
```

    <div class="block_content">
        <ul>
            <li>
                <h5>
                    <a href="" title="">xxx</a>
                </h5>
                <p class="product_desc">
                    <a href=".." title="More">...</a>
                </p>
                <a href="..." title=".." class="product_image">
                    <img src="..." alt="..." height="129" width="129"/>
                </a>
                <p>
                    <a class="button" href="..." title="View">View</a>
                    <a title="Add to cart">Add to cart</a>
                </p>
            </li>
            <li>
                similar UI
            </li>
            <li>
                similar UI
            </li>
        </ul>
    </div>       

```

As you see, the UI elements under the tag li are different and how to write the UI template for them? The good news is that the logical Container comes to play. For example,
the UI module could be written as

```
ui.Container(uid: "content", clocator: [tag: "div", class: "block_content"]){
     List(uid: "list", clocator: [tag: "ul"], separator:"li") {
         Container("all"){
             UrlLink(uid: "title", clocator: [title: "View"])
                      ......
                      other elements inside the li tag
         }
    }
} 
```

Another usage of the logical Container is to convert the test case recorded by Selenium IDE to Tellurium test cases. Let us take the search UI on Tellurium download page as an example.

First, I recorded the following Selenium test case using Selenium IDE,

```
import com.thoughtworks.selenium.SeleneseTestCase;

public class SeleniumTestCase extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://code.google.com/", "*chrome");
	}
	public void testNew() throws Exception {
		selenium.open("/p/aost/downloads/list");
		selenium.select("can", "label=regexp:\\sAll Downloads");
		selenium.type("q", "TrUMP");
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
	}
}
```

Don't be confused by the locator "can" and "q", they are just UI element ids and can be easily expressed in XPath. The "label=regexp:\\sAll Downloads" part just tells you that Selenium uses regular express to match the String and the "\s" stands for a space. As a result, we can write the UI module based on the above code.

```
public class TelluriumDownloadPage extends DslContext {

  public void defineUi() {
    ui.Container(uid: "downloads") {
      Selector(uid: "downloadType", locator: "//*[@id='can']")
      InputBox(uid: "input", locator: "//*[@id='q']")
      SubmitButton(uid: "search", locator: "//input[@value='Search']")
    }
  }

  public void searchDownload(String downloadType, String searchKeyWords) {
    selectByLabel "downloads.downloadType", downloadType
    keyType "downloads.input", searchKeyWords
    click "downloads.search"
    waitForPageToLoad 30000
  }
}
```

And we can create the Tellurium test case accordingly,

```
public class TelluriumDownloadPageTestCase extends TelluriumJavaTestCase {

    protected static TelluriumDownloadPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new TelluriumDownloadPage();
        ngsp.defineUi();
    }

    @Test
    public void testSearchDownload(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        ngsp.searchDownload(" All Downloads", "TrUMP");
    }
}
```


## CSS Selector ##

The motivation behind the CSS Selector is to improve the test speed in IE since IE lacks of native XPath support and the XPath is very slow. Tellurium exploits CSS selector capability to improve the test speed dramatically.

Tellurium supports both XPath and CSS selector and still uses XPath as the default locator. To use CSS selector, you need to explicitly tell Tellurium to use CSS selector.

To enable CSS selector call the following method.

  * `useCssSelector()`: Use CSS selector.

To disable CSS selector call the following method, it also switches back to the default
locator. i.e. XPath

  * `disableCssSelector()`: Switch back to XPath locator.

The above can be better illustrated the following diagram.

http://tellurium-users.googlegroups.com/web/xpathjqsel2.png?gda=ZcLbzkEAAACsdq5EJRKLPm_KNrr_VHB_i4k4E3yBw3ZwuTWAYUCsylo_LL8k1Ivp8OS586drcZpTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=4508XgsAAACdKjHXeYuQCiQefauhN3Sg

Some of the jQuery functions are provided by the custom selenium server, which is created by our Tellurium Engine project. Be aware that CSS selector only works for composite locator, i.e. clocator. You cannot have base locator, i.e., XPath, in your UI module.

How does the CSS selector work?  The basic idea is to customize Selenium Core to load the jQuery library at startup time. You can add jquery.js in to the TestRunner.html and RemoteRunner.html. Another way is dump all jquery.js into user-extensions.js. Since our Engine prototype customizes Selenium core anyway, we used the former method.

After that, we register a custom locate strategy "jquery", using the following Selenium API call:

```
addLocationStrategy ( strategyName,functionDefinition )
```

This defines a new function for Selenium to locate elements on the page. For example,
if you define the strategy "foo", and someone runs click("foo=blah"), we'll run your
function, passing you the string "blah", and click on the element that your function
returns, or throw an "Element not found" error if your function returns null. Selenium passed three arguments to the location strategy function:

  * _locator_: the string the user passed in
  * _inWindow_: the currently selected window
  * _inDocument_: the currently selected document

The function must return null if the element can't be found.

Arguments:

  * _strategyName_: the name of the strategy to define; this should use only letters [a-zA-Z] with no spaces or other punctuation.
  * _functionDefinition_: a string defining the body of a function in JavaScript. For example: return inDocument.getElementById(locator);

Accordingly, we define the custom locate strategy as follows,

```
addLocationStrategy("jquery", '''
  var found = $(inDocument).find(locator);
  if(found.length == 1 ){
    return found[0];
  }else if(found.length > 1){
    return found.get();
  }else{
    return null;
  }
''')
```

The code is pretty straightforward. When we find one element, return its DOM reference (Note: Selenium does not accept returning an array with only one element) and if we find multiple elements, we use jQuery get() method to return an array of DOM references. Otherwise, return null.

The actual code is a bit more complicated because we need to consider our custom attribute locator. We use the same format of attribute locator as the XPath one, i.e.,

```
locator@attribute
```

Apart from that, we need to create a set of custom Selenium methods and add the appropriate Selenium RC Java methods. For example, we created the following Selenium method

```
Selenium.prototype.getAllText = function(locator){
  var $e = $(this.browserbot.findElement(locator));
  var out = [];
  $e.each(function(){
     out.push($(this).text());
  });

  return JSON.stringify(out);
};
```

Then, created the corresponding Java method by extending Selenium

```
class CustomSelenium extends DefaultSelenium {

  def String getAllText(String locator){
     String[] arr = [locator];
     String st = commandProcessor.doCommand("getAllText", arr);
     return st;
  }
}
```

CSS Selector provides the following additional Selenium methods, which are utilized in DslContext to form a set of new DSL methods.

  * `String getAllText(String locator)`: Get all text from the set of elements corresponding to the CSS Selector.

  * `String getCSS(String locator, String cssName)` : Get the CSS properties for the set of elements corresponding to the CSS Selector.

  * `Number getJQuerySelectorCount(String locator)` : Get the number of elements matching the corresponding CSS Selector

CSS also supports the following attribute selectors.

  * `attribute`: have the specified attribute.

  * `attribute=value`: have the specified attribute with a certain value.

  * `attribute!=value`: either don't have the specified attribute or do have the specified attribute but not with a certain value.

  * `attribute^=value`: have the specified attribute and it starts with a certain value.

  * `attribute$=value`: have the specified attribute and it ends with a certain value.

  * `attribute*=value`: have the specified attribute and it contains a certain value.

Apart from the above, Tellurium provides a set of locator agnostic methods, i.e., the method will automatically decide to use XPath or jQuery dependent on the _exploreJQuerySelector_ flag, which can be turn on and off by the following two methods:

```
public void useCssSelector()

public void disableCssSelector()
```

In the meanwhile, Tellurium also provides the corresponding XPath specific and CSS selector specific methods for your convenience. However, we recommend you to use the locator agnostic methods until you have a good reason not to.

The new methods are listed here:

  * Get the Generated locator from the UI module

> Locator agnostic:
    * `String getLocator(String uid)`

> CSS selector specific:
    * `String getSelector(String uid)`

> XPath specific:
    * `String getXPath(String uid)`

  * Get the Number of Elements matching the Locator

> Locator agnostic:
    * `Number getLocatorCount(String locator)`

> CSS selector specific:
    * `Number getJQuerySelectorCount(String jQuerySelector)`

> XPath specific:
    * `Number getXpathCount(String xpath)`

  * Table methods

> Locator agnostic:
    * `int getTableHeaderColumnNum(String uid)`
    * `int getTableFootColumnNum(String uid)`
    * `int getTableMaxRowNum(String uid)`
    * `int getTableMaxColumnNum(String uid)`
    * `int getTableMaxRowNumForTbody(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbody(String uid, int ntbody)`
    * `int getTableMaxTbodyNum(String uid)`

> CSS selector specific:
    * `int getTableHeaderColumnNumBySelector(String uid)`
    * `int getTableFootColumnNumBySelector(String uid)`
    * `int getTableMaxRowNumBySelector(String uid)`
    * `int getTableMaxColumnNumBySelector(String uid)`
    * `int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody)`
    * `int getTableMaxTbodyNumBySelector(String uid)`

> XPath specific:
    * `int getTableHeaderColumnNumByXPath(String uid)`
    * `int getTableFootColumnNumByXPath(String uid)`
    * `int getTableMaxRowNumByXPath(String uid)`
    * `int getTableMaxColumnNumByXPath(String uid)`
    * `int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody)`
    * `int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody)`
    * `int getTableMaxTbodyNumByXPath(String uid)`

  * Check if an Element is Disabled

> Locator agnostic:
    * `boolean isDisabled(String uid)`

> CSS selector specific:
    * `boolean isDisabledBySelector(String uid)`

> XPath specific:
    * `boolean isDisabledByXPath(String uid)`

  * Get the Attribute

> Locator agnostic:
    * `def getAttribute(String uid, String attribute)`

  * Check the CSS Class

> Locator agnostic
    * `def hasCssClass(String uid, String cssClass)`

  * Get CSS Properties

> CSS selector specific:
    * `String[] getCSS(String uid, String cssName)`

  * Get All Data from a Table

> CSS selector specific:
    * `String[] getAllTableCellText(String uid)`
    * `String[] getAllTableCellTextForTbody(String uid, int index)`

  * Get List Size

> Locator agnostic:
    * `int getListSize(String uid)`

> CSS selector specific:
    * `getListSizeBySelector(String uid)`

> XPath specific:
    * `getListSizeByXPath(String uid)`

The are something you should be aware of for CSS Selector:
  * If you have a duplicate "id" attribute on the page, CSS selector always returns the first DOM reference, ignoring other DOM references with the same "id" attribute.
  * Some attribute may not working in jQuery, for example, the "action" attribute in a form. Tellurium has a black list to automatically filter out the attributes that are not honored by CSS selector.
  * Seems the "src" attribute in Image has to be full URL such as http://www.google.com. One workaround is to put '`*`' before the URL.


## jQuery Selector Cache Option ##

jQuery cache is a mechanism to further improve the speed by reusing the found DOM reference for a given jQuery selector. Our benchmark results show that the jQuery cache could improve the speed by up to 14% over the regular jQuery selector and over 27% for some extreme cases. But the improvement of jQuery cache over regular jQuery selector has upper bound, which is the portion of jQuery locating time out of the round trip time from Tellurium core to Selenium core. jQuery cache is still considered to be experimental at the current stage. Use it at your own discretion.

To make the cache option configurable in tellurium UI module, Tellurium introduces an attribute _cacheable_ to UI object, which is set to be true by default. For a Container type object, it has an extra attribute _noCacheForChildren_ to control whether to cache its children.

One example UI module is defined as follows,

```
  ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "true",
           clocator: [id: "resultstable", class: "results"], group: "true") {
 
       ......

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }
```

Where the _cacheable_ can overwrite the default value in the UI object and _noCacheForChildren_ in the above example will force Tellurium to not cache its children.

When you choose to use jQuery Cache mechanism, Tellurium core will pass the jQuery selector and a meta command to Tellurium Engine, which is embedded inside Selenium core at the current stage. The format is as follows taking the Tellurium issue searching UI as an example,

```
jquerycache={
  "locator":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], 
             input[value=Search][type=submit]) #can",
  "optimized":"#can",
  "uid":"issueSearch.issueType",
  "cacheable":true,
  "unique":true
}
```

where _locator_ is the regular jQuery selector and _optimized_ is the optimized jQuery selector by the jQuery selector optimizer in Tellurium core. The _locator_ is used for child UI element to derive a partial jQuery selector from its parent. The _optimized_ will be used for actual DOM searching.

The rest three parameters are meta commands. _uid_ is the UID for the corresponding jQuery selector and _cacheable_ tells the Engine whether to cache the selector or not. The reason is that some UI element on the web is dynamic, for instance, the UI element inside a data grid. As a result, it may not be useful to cache the jQuery selector.

The last one, _unique_, tells the Engine whether the jQuery selector expects multiple elements or not. This is very useful to handle the case that jQuery selector is expected to return one element, but actually returns multiple ones. In such a case, a Selenium Error will be thrown.

On the Engine side the cache is defined as

```
    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
    this.sCache = new Hashtable();
    
    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;
```

The cache system includes a global flag to decide whether to use the cache capability, a hash table to store the cached data, a cache size limit, and a cache eviction policy once the cache is filled up.

The cached data structure is defined as

```
//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to find the reminding selector 
    //for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //jQuery object for DOM reference
    this.reference = null;
    //number of reuse
    this.count = 0;
    //last use time
    this.timestamp = Number(new Date());
};
```

Tellurium Engine provides the following cache eviction policies:

  * _DiscardNewPolicy_: discard new jQuery selector.
  * _DiscardOldPolicy_: discard the oldest jQuery selector measured by the last update time.
  * _DiscardLeastUsedPolicy_: discard the least used jQuery selector.
  * _DiscardInvalidPolicy_: discard the invalid jQuery selector first.

It is important to know when the cached data become invalid. There are three mechanisms we can utilize here:
  1. Listen for page refresh event and invalidate the cache data accordingly
  1. Intercept Ajax response to decide when the web is update and if the cache needs to be updated.
  1. Validate the cached jQuery selector before using it.

Right now, Tellurium Engine uses the 3rd mechanism to check if the cached data is valid or not. The first two mechanisms are still under development.

Whenever Tellurium Core passes a locator to the Engine, the Engine will first look at the meta command _cacheable_. If this flag is true, it will first try to look up the DOM reference from the cache. If no cached DOM reference is available, do a fresh jQuery search and cache the DOM reference, otherwise, validate the cached DOM reference and use it directly. If the _cacheable_ flag is false, the Engine will look for the UI element's ancestor by its UID and do a jQuery search starting from its ancestor if possible.

Tellurium Core provides the following methods for jQuery Selector cache control,

  * _`public void enableSelectorCache()`_: Enable jQuery selector cache

  * _`public boolean disableSelectorCache()`_: Disable jQuery selector cache

  * _`public boolean cleanSelectorCache()`_: Cleanup the whole jQuery selector cache

  * _`public boolean getSelectorCacheState()`_: Test if the cache is enabled or not in Engine

  * _`public void setCacheMaxSize(int size)`_: Set the cache maximum size

  * _`public int getCacheSize()`_: Get the cache current size

  * _`public int getCacheMaxSize()`_: Get the cache maximum size

  * _`public Map<String, Long> getCacheUsage()`_: Get the cache usage

  * _`public void useDiscardNewCachePolicy()`_: Use DiscardNewCachePolicy

  * _`public void useDiscardOldCachePolicy()`_: Use DiscardOldCachePolicy

  * _`public void useDiscardLeastUsedCachePolicy()`_: Use DiscardLeastUsedCachePolicy

  * _`public void useDiscardInvalidCachePolicy()`_: Use DiscardInvalidCachePolicy

  * _`public String getCurrentCachePolicy()`_: Get the current cache policy

## "Include" Frequently Used Sets of elements in UI Modules ##

Sometimes, you have a frequently used set of elements, which you do not want to redefined them over and over again in your UI module. Now, you can use the "Include" syntax to re-use pre-defined UI elements,

```
Include(uid: UID, ref: REFERRED_UID)
```

You should use "ref" to reference the object you want to include, you can still specify the uid for the object (if you do not need a different UID, you do not need to specify it), if the object uid is not the same as the original one, Tellurium will clone a new object for you so that you can have multiple objects with different UIDs.

For example, you first define the following reused UI module

```
   ui.Container(uid: "SearchModule", clocator: [tag: "td"], group: "true") {
     InputBox(uid: "Input", clocator: [title: "Google Search"])
     SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search"])
     SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"])
   }
```

Then, you can include it into your new UI module as follows,

```
   ui.Container(uid: "Google", clocator: [tag: "table"]) {
     Include(ref: "SearchModule")
     Include(uid: "MySearchModule", ref: "SearchModule")
     Container(uid: "Options", clocator: [tag: "td", position: "3"], group: "true") {
       UrlLink(uid: "LanguageTools", clocator: [tag: "a", text: "Language Tools"])
       UrlLink(uid: "SearchPreferences", clocator: [tag: "a", text: "Search Preferences"])
       UrlLink(uid: "AdvancedSearch", clocator: [tag: "a", text: "Advanced Search"])
     }
   }
```

## Customize Individual Test settings Using setCustomConfig ##

TelluriumConfig.groovy provides project level test settings, you can use setCustomConfig to custmoize individual testing settings.

```
public void setCustomConfig(boolean runInternally, int port, String browser,
                            boolean useMultiWindows, String profileLocation)

public void setCustomConfig(boolean runInternally, int port, String browser,
                  boolean useMultiWindows, String profileLocation, String serverHost)
```

For example, you can specify your custom settings as follows,

```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase {
    static{
        setCustomConfig(true, 5555, "*chrome", true, null);
    }

    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
        ngsp.useJavascriptXPathLibrary();
    }

    @Test
    public void testGoogleSearch() {
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
    }

    ......

}
```

## User Custom Selenium Extensions ##

To support user custom selenium extensions, Tellurium Core added the following configurations to TelluriumConfig.groovy

```
  embeddedserver {

    ......

    //user-extension.js file, for example "target/user-extensions.js"
    userExtension = ""
  }
  connector{

    ......

    //user's class to hold custom selenium methods associated with user-extensions.js
    //should in full class name, for instance, "com.mycom.CustomSelenium"
    customClass = ""
  }
```

Where the _userExtension_ points to user's user-extensions.js file, for example, you have the following user-extensions.js

```
Selenium.prototype.doTypeRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to type
    var valueToType = text + text;

    // Replace the element text with the new text
    this.page().replaceText(element, valueToType);
};
```

The _customClass_ is user's class for custom selenium methods, which should extend Tellurium org.tellurium.connector.CustomCommand class:

```
public class MyCommand extends CustomCommand{

    public void typeRepeated(String locator, String text){
                String[] arr = [locator, text];
                commandProcessor.doCommand("typeRepeated", arr);
        }
   } 
}
```

Tellurium core will automatically load up your user-extensions.js and custom commands. The n, user can use

```
customUiCall(String uid, String method, Object[] args)
```

to call the custom methods, for instance,

```
customUiCall "Google.Input", typeRepeated, "Tellurium Groovy" 
```

The _customUiCall_ method handles all the UI to locator mapping for users. Tellurium also provides the following method for users to make direct calls to Selenium server.

```
customDirectCall(String method, Object[] args)
```

## Data Driven Testing ##

[Data Driven Testing](http://code.google.com/p/aost/w/edit/DataDrivenTesting) is a different way to write tests, i.e, separate test data from the test scripts and the test flow is not controlled by the test scripts, but by the input file instead. In the input file, users can specify which tests to run, what are input parameters, and what are expected results. Data driven testing in Tellurium can be illustrated by the following system diagram.

http://tellurium-users.googlegroups.com/web/TelluriumDataDrivenSmall.jpg?gda=WePTVk4AAAD5mhXrH3CK0rVx4StVj0LYYQ-a0sZzxEmmZWlHbP2MWzTrErMgjh_s8-a7FfMpsGsNL5bu5vgQy2xsA01CuO9M47Cl1bPl-23V2XOW7kn5sQ&gsc=ODykzgsAAACEc2FtJPGdXe_7CHb1VB6Z

The tellurium data driven test consists of three main parts, i.e., Data Provider, TelluriumDataDrivenModule, and TelluriumDataDrivenTest. The data provider is responsible for reading data from input stream and converting data to Java variables. Tellurium provides the following data provider methods:

  1. loadData file\_name, load input data from a file
  1. useData String\_name, load input data from a String in the test script
  1. bind(field\_name), bind a variable to a field in a field set
  1. closeData, close the input data stream and report the test results
  1. cacheVariable(name, variable), put variable into cache
  1. getCachedVariable(name, variable), get variable from cache

where the _`file_name`_ should include the file path, for example,

```
  loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
```

Right now, Tellurium supports pipe format and CSV formatinput file. To change the file reader for different formats, please change the following settings in the configuration file TelluriumConfig.groovy:

```
datadriven{
    dataprovider{
        //specify which data reader you like the data provider to use
        //the valid options include "PipeFileReader", "CSVFileReader" at this point
        reader = "PipeFileReader"
    }
}
```

Sometimes, you may like to specify test data in the test scripts directly, _useData_ is designed for this purpose and it loads input from a String. The String is usually defined in Groovy style using triple quota, for example:

```
  protected String data = """
    google_search | true | 865-692-6000 | tellurium
    google_search | false| 865-123-4444 | tellurium selenium test
    google_search | true | 755-452-4444 | tellurium groovy
    google_search | false| 666-784-1233 | tellurium user group
    google_search | true | 865-123-5555 | tellurium data driven
"""
    ...

  useData data
```

bind is the command to bind a variable to input Field Set field at runtime. FieldSet is the format of a line of data and it is defined in the next section. For example,

```
  def row = bind("GCHLabel.row")
```

is used to bind the row variable to the "row" field in the FieldSet "GCHLabel". Tellurium does not explicitly differentiate input parameters from the expected results in the input data. You only need to bind variables to the input data and then you can use any of them as the expected results for result comparison.

cacheVariable and getCachedVariable are used to pass intermediate variables among tests. cacheVariable is used to put variable into a cache and getCachedVariable is used to get back the variable. For example,

```
int headernum = getTableHeaderNum()
cacheVariable("headernum", headernum)

...

int headernum = getCachedVariable("headernum")
...
```

When you are done with your testing, please use "closeData" to close the input data stream. In the meantime, the result reporter will output the test results in the format you specified in the configuration file, for example, XML file as shown in the TelluriumConfig.groovy file:

```
test{
    result{
       //specify what result reporter used for the test result
       //valid options include "SimpleResultReporter", "XMLResultReporter", 
       //and "StreamXMLResultReporter"
       reporter = "XMLResultReporter"
       //the output of the result
       //valid options include "Console", "File" at this point
       //if the option is "File", you need to specify the file name, 
       //other wise it will use the default
       //file name "TestResults.output"
       output = "Console"
       //test result output file name
       filename = "TestResult.output"
    }
}
```

TelluriumDataDrivenModule is used to define modules, where users can define UI Modules, FieldSets, and tests as shown in the following sequence diagram. Users should extend this class to define their own test modules.

http://tellurium-users.googlegroups.com/web/TelluriumDDTModule.png?gda=oIfzLkgAAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPW9wwFNWzBcwQWJJR7cmP5glzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

TelluriumDataDrivenModule provides one method "defineModule" for users to implement. Since it extends the DslContext class, users can define UI modules just like in regular Tellurium UI Module. For example:

```
ui.Table(uid: "labels_table", clocator: [:], group: "true"){
   TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", 
           text: "Example project labels:"])
   Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        UrlLink(uid: "all", locator: "/a")
   }
}
```

FieldSet is used to define the format of one line of input data and FieldSet consists of fields, i.e., columns, in the input data. There is a special field "test", which users can specify what test this line of data apply to. For example,

```
fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
    Test(value: "getGCHStatus")
    Field(name: "label")
    Field(name: "rowNum", type: "int")
    Field(name: "columNum", type: "int")
}  
```

The above FieldSet defines the input data format for testing google code hosting web page. Note, the Test field must be the first column of the input data. The default name of the test field is "test" and does not need to be specified. If the value attribute of the test field is not specified, it implies this same format, i.e., FieldSet, can used for different tests.

For a regular field, it includes the following attributes:

```
class Field {
	//Field name
	private String name

        //Field type, default is String
        private String type = "String"

        //optional description of the Field
	private String description

	//If the value can be null, default is true
	private boolean nullable = true

	//optional null value if the value is null or not specified
	private String nullValue

	//If the length is not specified, it is -1
	private int length = -1

	//optional String pattern for the value
	//if specified, we must use it for String validation
	private String pattern
   } 
```

Tellurium can automatically handle Java primitive types. Another flexibility Tellurium provides is that users can define their own custom type handlers to deal with more complicated data types by using "typeHandler", for example,

```
//define custom data type and its type handler
typeHandler "phoneNumber", "org.tellurium.test.PhoneNumberTypeHandler"

//define file data format
fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
    Field(name: "regularSearch", type: "boolean", 
          description: "whether we should use regular search or use I'm feeling lucky")
    Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
    Field(name: "input", description: "input variable")
}
```

The above script defined a custom type "PhoneNumber" and the Tellurium will automatically call this type handler to convert the input data to the "PhoneNumber" Java type.

The "defineTest" method is used to define a test in the TelluriumDataDrivenModule, for example, the following script defines the "clickGCHLabel" test:

```
defineTest("clickGCHLabel"){
    def row = bind("GCHLabel.row")
    def column = bind("GCHLabel.column")
           
    openUrl("http://code.google.com/hosting/")
    click  "labels_table[2][1].[${row}][${column}]"

    waitForPageToLoad 30000
}
```

Note, the bind command is used to bind variables row, column to the fields "row" and "column" in the FieldSet "GCHLabel".

Tellurium also provide the command "compareResult" for users to compare the actual result with the expected result. For example, the following script compares the expected label, row number, and column number with the acutal ones at runtime:

```
defineTest("getGCHStatus"){
    def expectedLabel = bind("GCHStatus.label")
    def expectedRowNum = bind("GCHStatus.rowNum")
    def expectedColumnNum = bind("GCHStatus.columNum")

    openUrl("http://code.google.com/hosting/")
    def label = getText("labels_table[1][1]")
    def rownum = getTableMaxRowNum("labels_table[2][1]")
    def columnum = getTableMaxColumnNum("labels_table[2][1]")

    compareResult(expectedLabel, label)
    compareResult(expectedRowNum, rownum)
    compareResult(expectedColumnNum, columnum)
    pause 1000
}
```

Sometimes, users may need custom "compareResult" to handle more complicated situation, for example, users want to compare two lists. In such a case, users can override the default "compareResult" behaviour by specifying custom code in the closure:

```
compareResult(list1, list2){
    assertTrue(list1.size() == list2.size())
    for(int i=0; i<list1.size();i++){
        //put your custom comparison code here
    }
}
```

If users want to check a variable in the test and the "checkResult" method should be used, which comes with a closure where users can define the actually assertions inside.

```
checkResult(issueTypeLabel) {
    assertTrue(issueTypeLabel != null)
}
```

Like "compareResult", "checkResult" will capture all assertion errors. The test will resume even the assertions fail and the result will be reported in the output.

In addition to that the "logMessage" can be used for users to log any messages in the output.

```
logMessage "Found ${actual.size()} ${issueTypeLabel} for owner " + issueOwner 
```

TelluriumDataDrivenTest is the class users should extend to run the actual data driven testing and it is more like a data driven testing engine. There is only one method, "testDataDriven", which users need to implement. The sequence diagram for the testing process is shown as follows.

http://tellurium-users.googlegroups.com/web/TelluriumDDTTestSequence.png?gda=Fgy8N04AAAD5mhXrH3CK0rVx4StVj0LY6r7Fxo4RaVZ2InRIkvRUPYEAgewWsw-pfk-JI3kLTQ9omPhdHiHZ5EjvmEnpg6SE47Cl1bPl-23V2XOW7kn5sQ&gsc=x3TBYwsAAAAeXMPG6HH-B1VXA1h0gdTp

Usually, users need to do the following steps:

  1. use "includeModule" to load defined Modules
  1. use "loadData" or "useData" to load input data stream
  1. use "stepToEnd" to read the input data line by line and pick up the specified test and run it, until reaches the end of the data stream
  1. use "closeData" to close the data stream and output the test results

What the "includeModule" does is to merge in all Ui modules, FieldSets, and tests defined in that module file to the global registry. "stepToEnd" will look at each input line, first to find the test name and pass in all input parameters to it, and then run the test. The whole process can be illustrated in the following example:

```
class GoogleBookListCodeHostTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {

        includeModule  example.google.GoogleBookListModule.class
        includeModule  example.google.GoogleCodeHostingModule.class

        //load file
        loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
        
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()
    }
}

```

The input data for this example are as follows,

```
##TEST should be always be the first column

##Data for test "checkBookList"
##TEST | CATEGORY | SIZE
checkBookList|Fiction|8
checkBookList|Fiction|3

##Data for test "getGCHStatus"
##TEST | LABEL | Row Number | Column Number
getGCHStatus |Example project labels:| 3 | 6
getGCHStatus |Example project| 3 | 6

##Data for test "clickGCHLabel"
##TEST | row | column
clickGCHLabel | 1 | 1
clickGCHLabel | 2 | 2
clickGCHLabel | 3 | 3
```

Note that line starting with "##" is comment line and empty line will be ignored.

If some times, users want to control the testing execution flow by themselves, Tellurium also provides this capability even though it is not recommended. Tellurium provides two additional commands, i.e., "step" and "stepOver". "step" is used to read one line of input data and run it, and "stepOver" is used to skip one line of input data. In this meanwhile, Tellurium also allows you to specify additional test script using closure. For example,

```
step{
    //bind variables
    boolean regularSearch = bind("regularSearch")
    def phoneNumber = bind("fs4googlesearch.phoneNumber")
    String input = bind("input")

    openUrl "http://www.google.com"
    type "google_start_page.searchbox", input
    pause 500
    click "google_start_page.googlesearch"
    waitForPageToLoad 30000
}
```

But this usually implies that the input data format is unique or the test script knows about what format the current input data are using.


## The Dump Method ##

Tellurium Core added a dump method to print out the UI object's and its descendants' runtime locators that Tellurium Core generates for you.

```
public void dump(String uid)
```

where the _uid_ is the UI object id you want to dump. The cool feature of the dump() method is that it does not require you to run the actual tests, that is to say, you do not need to run selenium server and do not need to launch the web browser. Just simply define the UI module and then call the dump() method.

For example, we can define the UI module for Tellurium Issue Search module as follows,

```
public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true"){
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }
  }
}
```

Then you can use the dump method in the following way,

```
  TelluriumIssueModule tisp = new TelluriumIssueModule();
  tisp.defineUi();
  tisp.dump("issueSearch");
```

The above code will print out the runtime XPath locators

```
Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: //descendant-or-self::form[@action="list" and @method="get"]
issueSearch.issueType: //descendant-or-self::form[descendant::select[@name="can" and 
     @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and 
     descendant::input[@value="Search" and @type="submit"] and 
     @action="list" and @method="get"]/descendant-or-self::select
                         [@name="can" and @id="can"]
issueSearch.searchLabel: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::span[contains(text(),"for")]
issueSearch.searchBox: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::input[@type="text" and @name="q"]
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" 
     and @id="can"] and descendant::span[contains(text(),"for")] and 
     descendant::input[@type="text" and @name="q"] and descendant::input[
     @value="Search" and @type="submit"] and @action="list" and @method="get"]
     /descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------
```

## Selenium Grid Support ##

Selenium Grid transparently distributes your tests on multiple machines so that you can run your tests in parallel. We recently added support for Selenium Grid to the Tellurium. Now you can run the Tellurium tests against different browsers using Selenium Grid. Tellurium core is updated to support Selenium Grid sessions.

Assume we have 3 machines set up to run the Tellurium tests on Selenium Grid. You can do all these steps on your local box. To do this locally remove the machine names with localhost. Each machine in this set up has a defined role as described below.

**dev1.tellurium.com**
Tellurium test development machine.

**hub.tellurium.com**
Selenium Grid hub machine that will drive the tests.

**rc.tellurium.com**
Multiple Selenium RC server running and registered to the Selenium Grid HUB. The actual test execution will be done on this machine. You can register as many Selenium RC servers as you wish. You need to be realistic about the hardware specification though.

Download Selenium Grid from the following URL and extract the contents of the folder on each of these machines.

We use Selenium Grid 1.0.3 which is the current released version.
http://selenium-grid.seleniumhq.org/download.html

Following is the illustration of the environment.

http://tellurium-users.googlegroups.com/web/TelluriumGridSetup.png?gda=9fgieUgAAAD5mhXrH3CK0rVx4StVj0LYYQ-a0sZzxEmmZWlHbP2MW6uQS0SyLBzYwfM7_kvx7qklzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=ODykzgsAAACEc2FtJPGdXe_7CHb1VB6Z

The first step would be to launch the selenium grid hub on the hub machine. Open up a terminal on the HUB machine **hub.tellurium.com** and go to the download directory of Selenium Grid.

```
> cd /Tools/selenium-grid-1.0.3
> ant launch-hub
```

You will notice that it has launched the Selenium HUB on the machine with different browsers. To ensure that the HUB is working properly go to the following location.

http://hub.tellurium.com:4444/console

You can see a web page with 3 distinct columns as **Configured Environments, Available Remote Controls, Active Remote Controls**

You will have a list of browsers that are configured by default to run the tests while the list for Available Remote Controls and Avtive Remote Controls will be empty.

The next step would be to launch the Selenium RC servers and register them with the selenium HUB. Open up a terminal on **rc.tellurium.com** and go to the selenium grid download directory.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5555 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 \
      -Denvironment="Firefox on Windows" launch-remote-control
```
This command will start a Selenium RC server on this machine and register it with the Selenium Grid hub machine as specified by the hubURL.

To register another Selenium RC server on this machine for internet explorer do the same on a different port.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5556 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 -Denvironment="IE on Windows" launch-remote-control
```

  * _port_ will that the remote control will be listening at. Must be unique on the machine the remote control runs on.

  * _hostname_ Hostname or IP address of the machine the remote control runs on. Must be visible from the Hub machine.

  * _hub url_  Which hub the remote control should register/unregister to. as the hub is running on hostname hub.tellurium.com, this URL will be http://hub.tellurium.com:4444

Once you are successful in replicating a setup similar to the one described above, point your browser to the Hub console (http://hub.tellurium.com:4444/console). Check that all the remote controls did register properly. Available remote controls list should be updated and have these 2 selenium servers available to run the tests.

Now you have started the selenium hub and the selenium rc servers on the Grid environment, the next step would be to run the Tellurium tests against different browsers.

Go to the Tellurium test development machine which in our case is **dev1.tellurium.com**.

Open up the TelluriumConfig.groovy and change the values of the selenium server and port to make sure that tellurium requests for the new sessions from Selenium HUB and then Selenium HUB can point tellurium tests to be run on **rc.tellurium.com** based on the browser of choice.

You need to change the values for the following properties.

  * _runInternally_: ensures that you do not launch the Selenium Server on the local machine.
  * _serverHost_: the selenium grid hub machine that has the information about the available selenium rc servers.
  * _port_: port that Selenium HUB is running on, by default this port is 4444 but you can change that in the grid\_configuraton.yml file if this port is not available on your HUB machine.
  * _browser_: the browser that comes under the configured environments list on the selenium HUB machine, you can change these values to your own choice in the grid\_configuration.yml file.

```

tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. 
        //If false, you need to manually set up a selenium server
        runInternally = false
        //profile location
        profile = ""
        //user-extension.js file
        userExtension = "target/classes/extension/user-extensions.js"
    }
    //event handler
    eventhandler{
        //whether we should check if the UI element is presented
        checkElement = false
        //wether we add additional events like "mouse over"
        extraEvent = true
    }
    //data accessor
    accessor{
        //whether we should check if the UI element is presented
        checkElement = true
    }
    //the configuration for the connector that connects the selenium client 
    //to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "hub.tellurium.com"
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //  *iehta
        browser = "Firefox on Windows"
        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = "org.tellurium.test.MyCommand"
    }
```


The set up is now complete.

Now run the tests as you normally do by either the Maven command or the IDE and you will notice that the tests are running on **rc.tellurium.com** and the list for **Active Remote Controls** is also updated on the hub URL (http://hub.tellurium.com:4444/console) during the test execution.


## Mock Http Server ##

This feature only exists in Tellurium Core 0.7.0 SNAPSHOT. The MockHttpServer is an embedded http server leveraging the Java 6 http server and it is very convenient to test HTML source directly without running a web server. Tellurium defined two classes, i.e.,  MockHttpHandler and MockHttpServer.

The MockHttpHandler is a class to process the http request,

```
public class MockHttpHandler implements HttpHandler {

  private Map<String, String> contents = new HashMap<String, String>();

  private String contentType = "text/html";

  public void handle(HttpExchange exchange) {
     ......
  }
}
```

The main method in MockHttpHandler is `handle(HttpExchange exchange)`, which reads the request URI, finds the corresponding response HTML source from the hash map _contents_, and then sends it back to the http client.

By default, the response will be treated as a HTML source, you can change this by using the following setter.

```
public void setContentType(String contentType)
```

MockHttpHandler includes two methods to add URI and its HTML source to the hash map _contents_, i.e.,

```
public void registerBody(String url, String body)

public void registerHtml(String url, String html)
```

The MockHttpHandler comes with a default HTML template as follows,

```
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Mock HTTP Server</title>
    </head>
    <body>
    BODY_HTML_SOURCE   
    </body>
</html>
```

If you use `registerBody(String url, String body)`, the MockHttpHandler will use the above HTML template to wrap the HTML body. You overwrite the default HTML template by calling `registerHtml(String url, String html)` directly, which will use the whole HTML source you provide in the variable _html_.

Usually, the MockHttpHandler is encapsulated by the MockHttpServer and you don't really need to work on it directly.

The MockHttpServer includes an embedded http server, a http handler, and a http port:

```
public class MockHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private MockHttpHandler handler;

  public MockHttpServer() {
    this.handler = new MockHttpHandler();
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port) {
    this.handler = new MockHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  ......
}
```

As you can see, the MockHttpServer provides three different constructors so that you can overwrite the default values. The MockHttpServer encapsulates the MockHttpHander by providing the following methods,

```
public void setContentType(String contentType)

public void registerHtmlBody(String url, String body)

public void registerHtml(String url, String html)
```

You can stop and start the server with the following methods,

```
public void start()

public void stop()
```

We use a modified version of a HTML source provided by one Tellurium user as an example and create the UI module Groovy class as follows

```
public class ListModule extends DslContext {
  public static String LIST_BODY = """
<div class="thumbnails">
    <ul>
        <li class="thumbnail">
            <img alt="Image 1"
                 src="/images_root/image_pictures/01.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 2"
                 src="/images_root/image_pictures/02.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 3"
                 src="/images_root/image_pictures/03.jpg"/>
        </li>
        <li class="thumbnail">
        </li>
        <li class="thumbnail active">
            <img alt="Image 4"
                 src="/images_root/image_pictures/04.jpg"/>
        </li>
        <li class="thumbnail potd">
            <div class="potd-icon png-fix"/>
            <img alt="Image 5"
                 src="/images_root/image_pictures/05.jpg"/>
        </li>
    </ul>
</div>    
  """

  public void defineUi() {
    ui.Container(uid: "rotator", clocator: [tag: "div", class: "thumbnails"]) {
      List(uid: "tnails", clocator: [tag: "ul"], separator: "li") {
        UrlLink(uid: "all", clocator: [:])
      }
    }
  }
}
```

The reason we include the HTML source in a Groovy file is that the """ quote in Groovy is very easy to present complicated HTML source as a String variable. In Java, you have to concatenate each line of the HTML Source to make it a String variable.

The `defineUi()` defines the UI module for the given HTML source. The major part of the UI module is a List, which uses UI templates to represent a list of links. You can see how easy and concise to use UI templates to represent UI elements in Tellurium.

Based on the ListModule UI module, we define a Tellurium JUnit test case as follows,

```
public class ListTestCase  extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/list.html", ListModule.LIST_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ListModule lm = new ListModule();
        lm.defineUi();

        connectUrl("http://localhost:8080/list.html");

        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        assertEquals("thumbnail potd", attr);
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();    
    }
}
```

## Attribute Partial Matching ##

Tellurium adopts jQuery style partial matching, that is to say, you should use the following format for partial matching:

```
"PARTIAL_MATCHING_SIGNYour_string"
```

where the partial matching signs include:

  * _`!`_: either don't have the specified attribute or do have the specified attribute but not with a certain value.
  * _`^`_: have the specified attribute and it starts with a certain value.
  * _`$`_: have the specified attribute and it ends with a certain value.
  * _`*`_: have the specified attribute and it contains a certain value.

Be aware, due to the limitations of XPath, _`$`_ and _`*`_ are the same in XPath locator.

Examples:

```
clocator: [href:'!xevent']
clocator: [href:'^xevent']
clocator: [href:'$xevent']
clocator: [href:'*xevent']
```

## Testing Support ##

In the package org.tellurium.test, Tellurium provides three different ways to write Tellurium tests:

  * TelluriumJavaTestCase is for JUnit, and it supports the following JUnit 4 annotations:
    * @BeforeClass
    * @AfterClass
    * @Before
    * @After
    * @Test
    * @Ignore
  * TelluriumTestNGTestCase is for TestNG. Similarly, you can use the following annotations:
    * @BeforeSuite
    * @AfterSuite
    * @BeforeTest
    * @AfterTest
    * @BeforeGroups
    * @AfterGroups
    * @BeforeClass
    * @AfterClass
    * @BeforeMethod
    * @AfterMethod
    * @DataProvider
    * @Parameters
    * @Test
  * TelluriumGroovyTestCase is for Groovy test cases.
  * Data Driven Testing. Tellurium provides the class _TelluriumDataDrivenModule_ for users to define data driven testing modules and the class _TelluriumDataDrivenTest_ to drive the actual tests.

Apart from the above, Tellurium also provides users the capability to write Tellurium tests and Tellurium data driven tests in pure DSL scripts. The _DslScriptExecutor_ is used to run the .dsl files.