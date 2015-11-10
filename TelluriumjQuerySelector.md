

## Introduction ##

Starting with version 0.6.0, Tellurium will support a jQuery selector to address the problem of poor performance of xpath in Internet Explorer. Auto-generating jQuery instead of xpath has the following advantages:
  1. Faster performance in IE
  1. We are able to use the power of jQuery to call methods on jQuery collections to retrieve bulk data
  1. We will eventually be able to convert selenium browser-bot to use jQuery

## Performance Comparison ##

To test the performance of our jQuery Selector, we create a Benchmark project at

```
http://aost.googlecode.com/svn/branches/test-benchmark
```

We use the following three different locators in the project
  1. XPath using Ajaxslt library
  1. XPath using Javascript library
  1. jQuery Selector

The tests are created from Tellurium Issues page and include three sets
  1. A series of test flow including issue search and issue data access
  1. Similar to the first one but without group locating in UI modules
  1. Access data from the issue table (limit the size to 20 rows and 10 columns)

We run the benchmark in Firefox 3 and IE 7 ten times and calculate the average times. To reduce the effects from network latency and page loading, we do not count the time for methods such as opening page and waitForPageLoad. The test results are listed in the following table,

| **Locator** | **Browser** | **TestFlow** | **NoGroup** | **Bulk Data** |
|:------------|:------------|:-------------|:------------|:--------------|
| Ajaxslt XPath | FF 3        | 64.78        | 58.95       | 165.93        |
| Ajaxslt XPath | IE 7        | 138.23       | 133.13      | 475.82        |
| Javascript XPath | FF 3        | 62.39        | 59.82       | 164.23        |
| Javascript XPath | IE 7        | 132.89       | 131.20      | 470.57        |
| jQuery Selector | FF 3        | 61.75        | 59.60       | 0.159         |
| jQuery Selector | IE 7        | 78.60        | 72.97       | 0.239         |

where the time unit is second.

The following chart shows the results for Firefox 3,

http://tellurium-users.googlegroups.com/web/PerformanceBenchmark4FF3.png?gda=DkeGhU4AAACXZPxEX7Ki-M5C2JpeBoXXbmPP2IOUAOTRTov5lVDl_MGjXFE8SoUS0fQzaoQIemTXUvAzVT6AsIST1RAkZXW447Cl1bPl-23V2XOW7kn5sQ&gsc=fH0GGQsAAABpzsTfYnuWMiH1pJztxNDi

and chart for IE 7 is as follows,

http://tellurium-users.googlegroups.com/web/PerformanceBenchMark4IE7.png?gda=_eqOLE4AAACXZPxEX7Ki-M5C2JpeBoXXbmPP2IOUAOTRTov5lVDl_O7yb5RiFGiNxJbRnD1MA8Xw_aztFVOYTtJDmLj5K5sC47Cl1bPl-23V2XOW7kn5sQ&gsc=fH0GGQsAAABpzsTfYnuWMiH1pJztxNDi

Although, the results only show the performance for our Benchmark, we can still observe that:

  1. jQuery selector is as fast as Ajaxslt XPath and Javascript XPath in Firefox 3.
  1. jQuery selector is much faster in IE 7 than the two XPath libraries
  1. jQuery selector uses only one method call to get back data for all table cells and thus it is the fastest one for bulk data access.

## Brief Overview of how to Use the jQuery Selector ##

Tellurium automatically builds runtime xpath or jQuery selector based on a flag _exploreJQuerySelector_ in DslContext, which is false by default. To use jQuery selector, you should call

```
useJQuerySelector()
```

and use

```
disableJQuerySelector()
```

to go back to XPath as shown in the following diagram.

http://tellurium-users.googlegroups.com/web/xpathjqsel.png?gda=BNGlpUAAAACXZPxEX7Ki-M5C2JpeBoXXvv3pQbLsTR0DEnS8qeQoBNY-98qTaiNg3Pwb0MuStQFtxVPdW1gYotyj7-X7wDON&gsc=aNwLeQsAAABDFDbDDy4icI5hL1ANoQtp

Some of the jQuery functions are provided by the custom selenium server, which is created by our Tellurium Engine project. Be aware that jQuery selector only works for composite locator, i.e., _clocator_. If you have base locator, which is xpath, then the jQuery selector will not work for you.


## How Does the jQuery Selector Work ? ##

The basic idea is to customize Selenium Core to load the jQuery library at startup time. You can add jquery.js in to the TestRunner.html and RemoteRunner.html. Another way is dump all jquery.js into user-extensions.js. Since our Engine prototype customizes Selenium core anyway, we used the former method.

After that, we register a custom locate strategy "jquery", using the following Selenium API call:


```
addLocationStrategy ( strategyName,functionDefinition )

```
> This defines a new function for Selenium to locate elements on the page. For example,
if you define the strategy "foo", and someone runs click("foo=blah"), we'll run your
function, passing you the string "blah", and click on the element that your function
returns, or throw an "Element not found" error if your function returns null. Selenium passed three arguments to the location strategy function:

  * locator: the string the user passed in
  * inWindow: the currently selected window
  * inDocument: the currently selected document

The function must return null if the element can't be found.

Arguments:

  * strategyName - the name of the strategy to define; this should use only letters [a-zA-Z] with no spaces or other punctuation.
  * functionDefinition - a string defining the body of a function in JavaScript. For example: return inDocument.getElementById(locator);

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

## New Features Provided by jQuery Selector ##

The most important feature is that Tellurium automatically generates the runtime jQuery selector based on your UI module definition, more specifically, the composite locator _clocator_. Tellurium also supports group locating for jQuery Selector by using jQuery :has() pseudo class and multiple selectors. For example, the following UI module





```
ui.Container(uid: "Google", clocator: [tag: "table"], group: "true") {
   InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
   SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
   SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
}

```

could produce the jQuery selector with group locating as follows,

```
jquery=table:has(input[title=Google Search][name=q], input[type=submit][value=Google Search][name=btnG], input[type=submit][value$=m Feeling Lucky][name=btnI]) input[title=Google Search][name=q]
```

jQuery Selector provides the following additional Selenium methods, which are utilized in DslContext to form a set of new DSL methods:

  1. _String getAllText(String locator)_: Get all text from the set of elements corresponding to the jQuery Selector.
  1. _String getCSS(String locator, String cssName)_: Get the CSS properties for the set of elements corresponding to the jQuery Selector.
  1. _Number getJQuerySelectorCount(String locator)_: Get the number of elements matching the corresponding jQuery Selector
  1. _boolean isDisabled(String locator)_: Test if an element is disabled.

jQuery supports the following attribute selectors
  * _[attribute](attribute.md)_: have the specified attribute
  * _[attribute=value]_: have the specified attribute with a certain value
  * _[attribute!=value]_: either don't have the specified attribute or do have the specified attribute but not with a certain value.
  * _[attribute^=value]_: have the specified attribute and it starts with a certain value.
  * _[attribute$=value]_: have the specified attribute and it ends with a certain value.
  * **[attribute_=value]_: have the specified attribute and it contains a certain value.**

Tellurium supports all the attribute selectors, that is to say, it looks at the prefix of the value and converts them to the appropriate attribute selectors if the values include either '!', '^', '$', or '`*`'. Be aware Tellurium partial matching symbol "%%" is depreciated. Please use the new symbols.

The are something you should be aware of for jQuery Selector:
  * If you have a duplicate "id" attribute on the page, jQuery selector always returns the first DOM reference, ignoring other DOM references with the same "id" attribute.
  * Some attribute may not working in jQuery, for example, the "action" attribute in a form. Tellurium has a black list to automatically filter out the attributes that are not honored by jQuery selector.
  * Seems the "src" attribute in Image has to be full URL such as http://www.google.com. One workaround is to put '`*`' before the URL.

## New DslContext Methods ##

Tellurium provides a set of locator agnostic methods, i.e., the method will automatically decide to use XPath or jQuery dependent on the _exploreJQuerySelector_ flag, which can be turn on and off by the following two methods:

```

public void useJQuerySelector()

public void disableJQuerySelector()

```

In the meanwhile, Tellurium also provides the corresponding XPath specific and jQuery selector specific methods for your convenience. However, we recommend you to use the locator agnostic methods until you have a good reason not to.

The new methods are listed here:

#### Get the Generated locator from the UI module ####

Locator agnostic:
  * String getLocator(String uid)

JQuery selector specific:
  * String getSelector(String uid)

XPath specific:
  * String getXPath(String uid)

#### Get the Number of Elements matching the Locator ####

Locator agnostic:
  * Number getLocatorCount(String locator)

JQuery selector specific:
  * Number getJQuerySelectorCount(String jQuerySelector)

XPath specific:
  * Number getXpathCount(String xpath)

#### Table methods ####

Locator agnostic:
  * int getTableHeaderColumnNum(String uid)
  * int getTableFootColumnNum(String uid)
  * int getTableMaxRowNum(String uid)
  * int getTableMaxColumnNum(String uid)
  * int getTableMaxRowNumForTbody(String uid, int ntbody)
  * int getTableMaxColumnNumForTbody(String uid, int ntbody)
  * int getTableMaxTbodyNum(String uid)

JQuery selector specific:
  * int getTableHeaderColumnNumBySelector(String uid)
  * int getTableFootColumnNumBySelector(String uid)
  * int getTableMaxRowNumBySelector(String uid)
  * int getTableMaxColumnNumBySelector(String uid)
  * int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody)
  * int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody)
  * int getTableMaxTbodyNumBySelector(String uid)
  * 

XPath specific:
  * int getTableHeaderColumnNumByXPath(String uid)
  * int getTableFootColumnNumByXPath(String uid)
  * int getTableMaxRowNumByXPath(String uid)
  * int getTableMaxColumnNumByXPath(String uid)
  * int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody)
  * int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody)
  * int getTableMaxTbodyNumByXPath(String uid)

#### Check if an Element is Disabled ####

Locator agnostic:
  * boolean isDisabled(String uid)

JQuery selector specific:
  * boolean isDisabledBySelector(String uid)

XPath specific:
  * boolean isDisabledByXPath(String uid)

#### Get the Attribute ####

Locator agnostic:
  * def getAttribute(String uid, String attribute)

#### Check the CSS Class ####

Locator agnostic
  * def hasCssClass(String uid, String cssClass)

#### Get CSS Properties ####

JQuery selector specific:
  * String[.md](.md) getCSS(String uid, String cssName)

#### Get All Data from a Table ####

JQuery selector specific:
  * String[.md](.md) getAllTableCellText(String uid)
  * String[.md](.md) getAllTableCellTextForTbody(String uid, int index)

#### Get List Size ####

Locator agnostic:
  * int getListSize(String uid)

JQuery selector specific:
  * getListSizeBySelector(String uid)

XPath specific:
  * getListSizeByXPath(String uid)

## jQuery Selector Cache Option ##

Tellurium offers jQuery selector cache option to further improve the test speed. You can find more details in [The Cache Mechanism of jQuery Selector](http://code.google.com/p/aost/wiki/jQuerySelectorCache).


## How to Obtain the jQuery Selector Support ##

Tellurium Engine project starts to build a custom selenium-server.jar, you can download it from Tellurium project subversion trunk/core/lib or from Tellurium user group. But the best way is to use Maven.

The two Tellurium archetypes are updated to support jQuery selector. You can use them to create a Tellurium test project. For JUnit project, use the following Maven command

```
mvn archetype:create -DgroupId=Your_Group_ID -DartifactId=Your_Artifact_ID \
-DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium \
-DarchetypeVersion=1.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots 
```

Then go to the project and run the test

```
mvn test
```

For TestNG project, you should use the tellurium-testng-archetype

```
mvn archetype:create -DgroupId=Your_Group_ID -DartifactId=Your_Artifact_ID \
-DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium \
-DarchetypeVersion=1.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

If you want to manually add Maven dependencies, use [this sample POM](http://code.google.com/p/aost/wiki/TelluriumTestProjectMavenSamplePom) as a reference.


## Reference Materials ##

  * [JQuery](http://jquery.com/)
  * [JQuery User Group](http://groups.google.com/group/jquery-en)
  * [JQuery Development Group](http://groups.google.com/group/jquery-dev)