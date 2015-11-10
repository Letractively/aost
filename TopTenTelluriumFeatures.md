

## UI Module ##

UI Module is the key concept of Tellurium. What is UI module? UI module is a collection of UI elements that you group them together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium project site is defined as follows,

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

Be aware that in most case the UI elements inside the composite UI object have relationship to each other. In the DOM structure, they should be on the same sub-tree. The exception is the [Logical Container](http://code.google.com/p/aost/wiki/LogicalContainer).

[Why UI module concept is so important](http://code.google.com/p/aost/wiki/Tellurium_A_New_Approach_for_Web_Test)? It is the foundation that Tellurium built on. First, UI module makes it possible to build UI elements' locator at runtime, which also makes Tellurium robust to changes from internal UI elements. Second, UI module makes Tellurium expressive. You can refer a UI element by simply appending the names along the path to the specific element. For example, you can refer the Selector element in the Tellurium download search module as "downloadSearch.downloadType". Other reasons include
  * UI module makes Group Locating possible.
  * UI module makes composite objects reusable in term of widget objects.
  * UI module requires a new Tellurium Engine to support UI Module more efficiently.

## Composite Locator ##

Tellurium supports two types of locators, i.e., _base locator_ and _composite locator_. The _base locator_ is a relative XPath. The _composite locator_, denoted by "clocator", specifies a set of attributes for the UI object and the actual locator will be derived automatically by Tellurium at runtime.

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

To use the composite locator, you need to use "clocator" with a map as its value, i.e.,

```
clocator: [key1: value1, key2: value2, ...]
```

The default attributes include "header", "tag", "text", "trailer", "position", and "direct". They are all optional. The "direct" attribute specifies whether this UI object is a direct child of its parent UI and the default value is "false".

If you have additional attributes, you can define them in the same way as the default attributes, for example:

```
clocator: [tag: "div", value: "Tellurium home"]
```

Most Tellurium objects come with default values for certain attributes, for example, the  tag attribute. If these attributes are not specified, the default attribute values will be used. In other words, if you know the default attribute values of a Tellurium UI object, you can omit them in clocator. Take the RadioButton object as an example, its default tag is "input" and its default type is "radio". You can omit them and write the clocator as follows,

```
clocator: [:]
```

which is equivalent to

```
clocator: [tag: "input", type: "radio"]
```

The default attributes for Tellurium objects are listed as follows,

| **Tellurium Object** | **Default Attributes** |
|:---------------------|:-----------------------|
| Button               | tag: "input"           |
| CheckBox             | tag: "input", type: "checkbox" |
| Div                  | tag: "div"             |
| Form                 | tag: "form"            |
| Image                | tag: "img"             |
| InputBox             | tag: "input"           |
| RadioButton          | tag: "input", type: "radio" |
| Selector             | tag: "select"          |
| Span                 | tag: "span"            |
| SubmitButton         | tag: "input", type: "submit" |
| Table                | tag: "table"           |
| UrlLink              | tag: "a"               |
| StandardTable        | tag: "table"           |



## jQuery Selector ##

Starting with version 0.6.0, Tellurium will support a [jQuery selector](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector) to address the problem of poor performance of xpath in Internet Explorer. Auto-generating jQuery instead of xpath has the following advantages:
  * Faster performance in IE.
  * We are able to use the power of jQuery to call methods on jQuery collections to retrieve bulk data.
  * Tellurium jQuery selector supports the jQuery attribute selectors such as _[attribute!=value]_, _[attribute^=value]_, _[attribute$=value]_, and **[attribute_=value]_.**

To use jQuery selector, simply call

```
useJQuerySelector()
```

in you code. To switch back to XPath locator, you should call

```
disableJQuerySelector()
```

Be aware that the UI module is agnostic to the locate strategy you choose and thus, you do not need to change anything in your test code.

Our Benchmark test results show that:

  1. jQuery selector is as fast as Ajaxslt XPath and Javascript XPath in Firefox 3.
  1. jQuery selector is much faster in IE 7 than the two XPath libraries
  1. jQuery selector uses only one method call to get back data for all table cells and thus it is the fastest one for bulk data access.

[jQuery cache](http://code.google.com/p/aost/wiki/jQuerySelectorCache) is a mechanism to further improve the speed by reusing the found DOM reference for a given jQuery selector. Our benchmark results show that the jQuery cache could improve the speed by up to 14% over the regular jQuery selector and over 27% for some extreme cases.

## Group Locating ##

In Tellurium UI module, you often see the "group" attribute, for example,

```
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
  InputBox(uid: "searchbox", clocator: [title: "Google Search"])
  SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
  SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}
```

What does the attribute "group" mean? The group attribute is a flag for the Group Locating Concept. Usually, the XPath generated by Selenium IDE, XPather, or other tools is a single
path to the target node such as

```
//div/table[@id='something']/div[2]/div[3]/div[1]/div[6]
```

No sibling node's information is used here. What is wrong with this? The xpath depends on too much information on nodes away from the target node. In Tellurium, we try to localize the information and reduce the dependency by using sibling information or local information. For example, in the above google UI module, the group locating concept will try to find the "td" tag with its children as "InputBox", "googlesearch" button, and
"Imfellingluck" button. In this way, we can reduce the dependencies of the UI elements inside a UI module on external UI elements to make the UI definition more robust.

## UI Templates ##

Tellurium UI templates are used for two purposes:

  1. There are many identical UI elements, you can use one template to represent them
  1. There are variable/dynamic size of UI elements at runtime, you know the patterns, but not the size.

More specifically, Table and List are two Tellurium objects that can define UI templates. Table defines two dimensional UI template and List is for one dimensional. Template has
special UIDs such as "2", "all", or "row: 1, column: 2".
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


For use case (2), a common application is the data grid. Look at the "issueResult" data grid at our Tellurium Issues page,

```
ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
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

You may wonder how to use the templates if you have multiple templates such the "issueResult" table as shown above. The rule to apply the templates is "specific one first, general one later". More details can be found at [The power of Tellurium UI templates](http://groups.google.com/group/tellurium-users/browse_thread/thread/8e011ff9f1f71393#).

## Javascript Events ##

Most web applications include Javascripts and thus, the web testing framework must be able to handle the Javascript events. What we really care is to fire the appropriate events to trigger the event handlers.

Selenium has already provided methods to generate events such as

```
fireEvent(locator, "blur")
fireEvent(locator, "focus")
mouseOut(locator)
mouseOver(locator)
```

Tellurium was born with Javascript events in mind since it was initially designed to test applications written using the DOJO javascript framework. For example, we have the following radio button,

```
<input type='radio' name='mas_address_key' value='5779' onClick='SetAddress_5779()'>
```

Although we can define the radio button as follows,

```
RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'])
```

but it will not be able to respond to the click event since the Tellurium RadioButton only have the "check" and "uncheck" actions, which is enough for the normal case. As a result, no "click" event/action will be generated during the testing. How to address this problem then?

Tellurium added the ["respond" attribute](http://groups.google.com/group/tellurium-users/browse_thread/thread/93fbae75a6d88624) to Tellurium UI objects and the "respond" attribute could be used to define whatever events you want the UI object to respond to. Still take the above radio button as an example, the new Radio Button can be defined as,

```
 ui.Container(uid: "form", clocator: [whatever]){
     RadioButton(uid: "billing", clocator: [name: 'mas_address_key', value: '5779'], respond: ["click"])
 }
```

That is to say, you can issue the following command:

```
  click "form.billing"
```

Even the RadioButton does not have the _click_ method defined by default, it is still able to dynamically add the _click_ method at runtime and call it.

A more general example is as follows,

```
 InputBox(uid: "searchbox", clocator: [title: "Google Search"], respond: ["click", "focus", "mouseOver", "mouseOut", "blur"])
```

Except for the "click" event, all the "focus", "mouseOver", "mouseOut", and "blur" events will be automatically fired by Tellurium during testing. Do not worry about the event order for the respond attribute, Tellurium will automatically re-order the events and then process them appropriately for you.

## Tellurium Widgets ##

[Tellurium Widget](http://code.google.com/p/aost/wiki/TelluriumWidget) is a good way to re-use the UI components in testing. Usually, Java script frameworks provide a lot of widgets. Take the Dojo framework as an example, the DatePicker Dojo widget could be described as follows,

```
class DatePicker extends DojoWidget{

    public void defineWidget() {
        ui.Container(uid: "DatePicker", locator: "/div[@class='datePickerContainer' and child::table[@class='calendarContainer']]"){
            Container(uid: "Title", locator: "/table[@class='calendarContainer']/thead/tr/td[@class='monthWrapper']/table[@class='monthContainer']/tbody/tr/td[@class='monthLabelContainer']"){
                Icon(uid: "increaseWeek", locator: "/span[@dojoattachpoint='increaseWeekNode']")
                Icon(uid: "increaseMonth", locator: "/span[@dojoattachpoint='increaseMonthNode']")
                Icon(uid: "decreaseWeek", locator: "/span[@dojoattachpoint='decreaseWeekNode']")
                Icon(uid: "decreaseMonth", locator: "/span[@dojoattachpoint='decreaseMonthNode']")
                TextBox(uid: "monthLabel", locator: "/span[@dojoattachpoint='monthLabelNode']")   
            }
            StandardTable(uid: "calendar", locator: "/table[@class='calendarContainer']/tbody/tr/td/table[@class='calendarBodyContainer']"){
                TextBox(uid: "header: all", locator: "")
                TextBox(uid: "all", locator: "", respond: ["click"])
            }
            Container(uid: "year", locator: "/table[@class='calendarContainer']/tfoot/tr/td/table[@class='yearContainer']/tbody/tr/td/h3[@class='yearLabel']"){
                Span(uid: "prevYear", locator: "/span[@class='previousYear' and @dojoattachpoint='previousYearLabelNode']")
                TextBox(uid: "currentYear", locator: "/span[@class='selectedYear' and @dojoattachpoint='currentYearLabelNode']")
                Span(uid: "nextYear", locator: "/span[@class='nextYear' and @dojoattachpoint='nextYearLabelNode']")
            }
        }
    }
}
```

You may notice that we use _base locator_, i.e., XPath directly. The reason is that we try to improve the speed for the widget. You can define the Widget using _composite locator_, i.e., attributes, as well. In Tellurium 0.7.0, the widget will be improved so that you define and use them more easily.

For widgets, it is important to include name space to avoid name collision between different widget modules. If we use "DOJO" as the name space, the widget will be like "DOJO\_DatePicker". We can define the following methods for the DOJO Date Picker widget:

```
    public String getCurrentYear(){
        return getText("DatePicker.year.currentYear")
    }

    public void selectPrevYear(){
        click "DatePicker.year.prevYear"
    }
```

To use the Date Picker widget, we can include the compiled jar file and include it like a regular tellurium UI object. For example,

```
class DatePickerDemo extends DslContext{
    
    public void defineUi() {
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
            Image(uid: "selectDate", clocator: [title: "select a date", dojoattachpoint: "containerDropdownNode", alt: "date"])
            DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", dojoattachpoint: "subWidgetContainerNode"])
        }
    }
}
```

To make the framework scalable, Tellurium provides an _onWidget_ method for Tellurium widgets. In that way, we can call the widget methods as follows:

```
onWidget "dropdown.datePicker", selectPrevYear
```

## Flexible Configuration ##

Tellurium use a configuration file TelluriumConfig.groovy to configure the framework. Do not be panic to see that it is a groovy file. Yes, it is a groovy file, but it is parsed by the framework as a text file. For you, just treat it as a text file with some rules such as using "{" and "}" for a block. Look at a snippet of configuration

tellurium{
> //embedded selenium server configuration
> embeddedserver {
> > //port number
> > port = "4444"
> > //whether to use multiple windows
> > useMultiWindows = false
> > //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
> > runInternally = true

> }
> //event handler
> eventhandler{
> > //whether we should check if the UI element is presented
> > checkElement = true
> > //wether we add additional events like "mouse over"
> > extraEvent = true

> }

> ...

}

Obviously, "//" is used for comments. You may wonder how Tellurium parses the configuration file, Tellurium actually uses the Groovy ConfigSlurper class to achieve
this. The nested settings will be turned into a Groovy ConfigObject object, which is basically a nested map, for example, the port number for the embedded server could be referred by "conf.tellurium.embeddedserver.port".

[TelluriumConfig.groovy](http://code.google.com/p/aost/wiki/TelluriumSampleConfigurationFile) acts like a global setting file for a Tellurium project. In the meanwhile, Tellurium provides the following method for you to override the settings in individual test case class,

```
void setCustomConfig(boolean runInternally, int port, String browser, boolean useMultiWindows, String profileLocation) 
```

For example, you can override the default settings in a test class as follows,

```

public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase
{
    static{
        setCustomConfig(true, 5555, "*chrome", true, null);
    }

...

} 
```

## Data Driven Testing ##

[Data Driven Testing](http://code.google.com/p/aost/w/edit/DataDrivenTesting) is a different way to write tests, i.e, separate test data from the test scripts and the test flow is not controlled by the test scripts, but by the input file instead. In the input file, users can specify which tests to run, what are input parameters, and what are expected results.

The tellurium data driven test consists of three main parts, i.e., Data Provider, TelluriumDataDrivenModule, and TelluriumDataDrivenTest. The data provider is responsible for reading data from input stream and converting data to Java variables. Tellurium provides the following data provider methods:

  1. loadData file\_name, load input data from a file
  1. useData String\_name, load input data from a String in the test script
  1. bind(field\_name), bind a variable to a field in a field set
  1. closeData, close the input data stream and report the test results
  1. cacheVariable(name, variable), put variable into cache
  1. getCachedVariable(name, variable), get variable from cache

cacheVariable and getCachedVariable are used to pass intermediate variables among tests.

TelluriumDataDrivenModule is used to define modules, where users can define Ui Modules, FieldSets, and tests as illustrated by the following example,

```
ui.Table(uid: "labels_table", clocator: [:], group: "true"){
   TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
   Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        UrlLink(uid: "all", locator: "/a")
   }
}

fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
    Test(value: "getGCHStatus")
    Field(name: "label")
    Field(name: "rowNum", type: "int")
    Field(name: "columNum", type: "int")
}  

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

TelluriumDataDrivenTest is the class users should extend to run the actual data driven testing.

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

The sample input data are as follows,

```
##TEST should be always be the first column

##Data for test "getGCHStatus"
##TEST | LABEL | Row Number | Column Number
getGCHStatus |Example project labels:| 3 | 6
getGCHStatus |Example project| 3 | 6 
```

For more detailed examples, please see [Data Driven Testing Examples](http://code.google.com/p/aost/wiki/DataDrivenTestingExample).

## The Dump Method ##

Have you ever wondered if you could know the runtime locators that Tellurium generates for your UI module before you actually run your test? This feature is now supported in Tellurium Core by [the dump() method](http://code.google.com/p/aost/w/edit/TheDumpMethod), which is very important to trace errors when you do not have access to the application under testing or run in an offline module.

The dump() method is defined in DslContext as follows,

```
public void dump(String uid)
```

where the _uid_ is the UI object id you want to dump. This method will print out the UI object's and its descendants' runtime locators that Tellurium Core generates for you.

For example, the Tellurium issue search module can be defined as follows,

```
public class TelluriumIssueModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
        Selector(uid: "issueType", clocator: [name: "can", id: "can"])
        TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
        InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
        SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }
  }
}
```

Once the UI modules are defined, you can call the dump() method in the following way,

```
public class TelluriumIssueGroovyTestCase extends GroovyTestCase{

  private TelluriumIssueModule tisp;

  public void setUp() {
    tisp = new TelluriumIssueModule();
    tisp.defineUi();
  }

  public void testDumpWithXPath() {
    tisp.disableJQuerySelector();
    tisp.dump("issueSearch");
  }

  public void testDumpWithJQuerySelector() {
    tisp.useJQuerySelector();
    tisp.exploreSelectorCache = false;
    tisp.dump("issueSearch");
  }
}
```

The XPath runtime locators are printed out as follows,

```
Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: //descendant-or-self::form[@action="list" and @method="get"]
issueSearch.issueType: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::select[@name="can" and @id="can"]
issueSearch.searchLabel: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::span[contains(text(),"for")]
issueSearch.searchBox: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@type="text" and @name="q"]
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------
```

The corresponding jQuery Selectors are shown as follows,

```
Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: jquery=form[method=get]
issueSearch.issueType: jquery=#can
issueSearch.searchLabel: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) span:contains(for)
issueSearch.searchBox: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[type=text][name=q]
issueSearch.searchButton: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]
-------------------------------------------------------
```

Be aware, the dump() method works for UI templates as well. Tellurium will go over all templates and replace the matching-all symbol in the template with an appropriate sample number so that you can see all different scenarios for your UI templates.