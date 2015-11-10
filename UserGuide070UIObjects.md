

# Tellurium UI Objects #

## UI Module ##

The UI Module is the heart of Tellurium. The UI module is a collection of UI elements grouped together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium's project site is defined as follows:

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

Tellurium is built on the foundation of the UI module. The UI module makes it possible to build locators for UI elements at runtime. First, this makes Tellurium robust and responsive to changes from internal UI elements. Second, the UI module makes Tellurium expressive.

A UI element is referred to simply by appending the names (uids) along the path to the specific element. This also enables Tellurium's "Group Locating" feature, making composite objects reusable and addressing dynamic web pages.

This frees up the testers to write better tests rather than spending precious testing time identifying and resolving test failures due to XPath changes.

The following class diagram illustrates the relationship among UI objects.

http://tellurium-users.googlegroups.com/web/uiobjectsclassdiagram.png?gda=VowyaEsAAAA7fMi2EBxrNTLhqoq3FzPr8-lHGHYf1lh09U1h9nXl9Zkd8ga5dUQpIAfkYTP3A3dkQsC9FE1SZNdLMNocxqCPBkXa90K8pT5MNmkW1w_4BQ

## Basic UI Object ##

Tellurium provides a set of predefined UI objects to be used directly when setting up a test.

The basic UI object is an abstract class. Users cannot instantiate it directly. The basic UI Object works as the base class for all UI objects and it includes the following attributes:

|<b>ATTRIBUTE</b>|<b>DESCRIPTION</b>|
|:---------------|:-----------------|
|<b>UI Object</b>|Basic Tellurium component|
|<b>UiId</b>     |UI object's identifier|
|<b>Namespace</b>|Used for XHTML    |
|<b>Locator</b>  |UI Object Locator including Base Locator and Composite Locator|
|<b>Group</b>    |Group Locating attribute that only applies to a collection type of UI object such as Container, Table, List, Form|
|<b>Respond</b>  |JavaScript events the UI object responds to|

The value is a list and the base UI object provides the following methods:

  * boolean isElementPresent()
  * boolean isVisible()
  * boolean isDisabled()
  * waitForElementPresent(int timeout), where the time unit is ms.
  * waitForElementPresent(int timeout, int step)
  * String getText()
  * getAttribute(String attribute)

All UI Objects inherit the above attributes and methods. Do not call these methods directly but use DSL syntax instead.

For example, use:

```
click "GoogleSearchModule.Search"
```

In this way, Tellurium first maps the UIID "`GoogleSearchModule.Search`" to the actual UI Object. Then the user calls the click method on the UI Object. If that UI Object does not have the click method defined, an error displays.

The UML class diagram is shown as follows.

http://tellurium-users.googlegroups.com/web/UiObject.png?gda=0JeoTj4AAACvY3VTaWrtpkaxlyj9o09EK9OqypH-u6ovWjcCAevrnUS0NUtU8tBM-H4ipR_0oWnjsKXVs-X7bdXZc5buSfmx&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

## UI Object Default Attributes ##

Tellurium UI objects have default attributes as shown in Table 2-1:

**Table 2-1 UI Object Default Attributes**


|<b>Tellurium Object</b>|<b>Locator Default Attributes</b>|<b>Extra Attributes</b>|<b>UI Template</b>|
|:----------------------|:--------------------------------|:----------------------|:-----------------|
|Button                 |tag: "input"                     |                       |no                |
|Container              |                                 |group                  |no                |
|CheckBox               |tag: "input", type: "checkbox"   |                       |no                |
|Div                    |tag: "div"                       |                       |no                |
|Form                   |tag: "form"                      |group                  |no                |
|Image                  |tag: "img"                       |                       |no                |
|InputBox               |tag: "input"                     |                       |no                |
|RadioButton            |tag: "input", type: "radio"      |                       |no                |
|Selector               |tag: "select"                    |                       |no                |
|Span                   |tag: "span"                      |                       |no                |
|SubmitButton           |tag: "input", type: "submit"     |                       |no                |
|UrlLink                |tag: "a"                         |                       |no                |
|Repeat                 |                                 |                       |no                |
|List                   |                                 |separator              |yes               |
|Table                  |tag: "table"                     |group, header          |yes               |
|StandardTable          |tag: "table"                     |group, header, footer  |yes               |
|Frame                  |                                 |group, id, name, title |no                |
|Window                 |                                 |group, id, name, title |no                |

## UI Object Description ##

### Button ###

Button represents various Buttons on the web and its default tag is "input". The following methods can be applied to Button:

  * click()
  * doubleClick()
  * clickAt(String coordination)

**Example:**

```
Button(uid: "searchButton", clocator: [value: "Search", name: "btn"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Button.png?gda=3KovTTwAAACvY3VTaWrtpkaxlyj9o09ErAuyvr_BhY1ILt3t4UiY02BNhIfMicGpzO7OcOv9IkT9Wm-ajmzVoAFUlE7c_fAt&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Submit Button ###

SubmitButton is a special Button with its type being "submit".

**Example:**

```
SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/SubmitButton.png?gda=23ltUEIAAACvY3VTaWrtpkaxlyj9o09Eq2re9z1g46laR7HVj3aMdhQB0YElXntmg3UvY_6wrxpV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Check Box ###

The CheckBox on the web is abstracted as "CheckBox" Ui object. The default tag for CheckBox is "input" and its type is "checkbox". CheckBox comes with the following methods:

  * check()
  * boolean isChecked()
  * uncheck()
  * String getValue()

**Example:**

```
CheckBox(uid: "autoRenewal", clocator: [dojoattachpoint: 'dap_auto_renew'])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/CheckBox.png?gda=lh-8Zj4AAACvY3VTaWrtpkaxlyj9o09ENlEhIiA-hb2mdKJPpbNwXCHJ3Uy3kY3kE7J2mE4y0vHjsKXVs-X7bdXZc5buSfmx&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Div ###

Div is often used in the Dojo framework and it can represent many objects. Its tag is "div" and it has the following method:

  * click()

**Example:**

```
Div(uid: "dialog", clocator: [class: 'dojoDialog', id: 'loginDialog'])
```

The Div object has been changed to be a Container type object. For example, you can define the following UI module.

```
   ui.Div(uid: "div1", clocator: [id: "div1"]) {
      Div(uid: "div2", clocator: [id: "div2"]) {
        List(uid: "list1", clocator: [tag: "ul"], separator: "li"){
          UrlLink(uid: "{all}", clocator: [:])
        }
      } 
   }
```

### Image ###

Image is used to abstract the "img" tag and it comes with the following additional methods:

  * getImageSource()
  * getImageAlt()
  * String getImageTitle()

**Example:**

```
Image(uid: "dropDownArrow", clocator: [src: 'drop_down_arrow.gif'])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Image.png?gda=0z5QJzsAAACvY3VTaWrtpkaxlyj9o09EujBhvAMotBAe8u4e4cflj34XZ2iAB5nmNWaSiL8s4UwGRdr3QrylPkw2aRbXD_gF&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Icon ###

Icon is similar to the Image object, but user can perform actions on it. As a result, it can have the following additional methods:

  * click()
  * doubleClick()
  * clickAt(String coordination)

**Example:**

```
Icon(uid: "taskIcon", clocator:[tag: "p", dojoonclick: 'doClick', img: "Show_icon.gif"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Icon.png?gda=PJSddzoAAACvY3VTaWrtpkaxlyj9o09EZcK6fJrP-123puIckMFHhbHJFgsrcyPlBEaANubSDln97daDQaep90o7AOpSKHW0&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Radio Button ###

RadioButton is the abstract object for the Radio Button Ui. As a result, its default tag is "input" and its type is "radio". RadioButton has the following additional methods:

  * check()
  * boolean isChecked()
  * uncheck()
  * String getValue()

**Example:**

```
RadioButton(uid: "autoRenewal", clocator: [dojoattachpoint: 'dap_auto_renew'])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/RadioButton.png?gda=dZD62kEAAACvY3VTaWrtpkaxlyj9o09EA9G0sOYo7hcFDFCV1utGuehRDoWPgHq-DlEIqfKM7N5TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Text Box ###

TextBox is the abstract Ui object from which the user returns to the text. For example, it comes with the following method:

  * String waitForText(int timeout)

**Note**: TextBox can have various types of tags.

**Example:**

```
TextBox(uid: "searchLabel", clocator: [tag: "span"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/TextBox.png?gda=frdGUj0AAACvY3VTaWrtpkaxlyj9o09EZBJxiwBnFeW2bGnkYb-FBqoIxTd_Qry1lDY-jgYSWY3lNv--OykrTYJH3lVGu2Z5&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Input Box ###

InputBox is the Ui where user types in input data. As its name stands, InputBox's default tag is "input". InputBox has the following additional methods:

  * type(String input)
  * keyType(String input), used to simulate keyboard typing
  * typeAndReturn(String input)
  * clearText()
  * boolean isEditable()
  * String getValue()

**Example:**

```
InputBox(uid: "searchBox", clocator: [name: "q"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/InputBox.png?gda=mOLQqT4AAACvY3VTaWrtpkaxlyj9o09EOS25eozxTd1-2Kl5EQxVuo83nWIEvwSk2L4t2l-mabLjsKXVs-X7bdXZc5buSfmx&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### URL Link ###

UrlLink stands for the web url link, i.e., its tag is "a". UrlLink has the following additional methods:

  * String getLink()
  * click()
  * doubleClick()
  * clickAt(String coordination)

**Example:**

```
UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/UrlLink.png?gda=OmfYUj0AAACvY3VTaWrtpkaxlyj9o09EPKrVkdl5tr4h2nKWPPTg00bJ3MnJmkZ45utMyqVp1HjlNv--OykrTYJH3lVGu2Z5&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Repeat Object ###

Very often, we need to use the same UI elements for multiple times on the web page. For example, we have the following html.

```
   <div class="segment clearfix">
       <div class="option">
           <ul class="fares">
               <li>
                   <input type="radio">&nbsp;
                   <label>Economy</label>
               </li>
               <li>
                   <input type="radio">&nbsp;
                   <label>Flexible</label>
               </li>
           </ul>
           <div class="details">
               <dl>
                   <dt>Ship:</dt>
                   <dd>A</dd>
                   <dt>Departs</dt>
                   <dd>
                       <em>08:00</em>
                   </dd>
                   <dt>Arrives</dt>
                   <dd>
                       <em>11:45</em>
                   </dd>
               </dl>
           </div>
       </div>
       <div class="option">
           <ul class="fares">
               <li>
                   <input type="radio">&nbsp;
                   <label>Economy</label>
               </li>
               <li>
                   <input type="radio">&nbsp;
                   <label>Flexible</label>
               </li>
           </ul>
           <div class="details">
               <dl>
                   <dt>Ship:</dt>
                   <dd>B</dd>
                   <dt>Departs</dt>
                   <dd>
                       <em>17:30</em>
                   </dd>
                   <dt>Arrives</dt>
                   <dd>
                       <em>21:15</em>
                   </dd>
               </dl>
           </div>
       </div>
   </div>
   <div class="segment clearfix">
       <div class="option">
           <ul class="fares">
               <li>
                   <input type="radio">&nbsp;
                   <label>Economy</label>
               </li>
               <li>
                   <input type="radio">&nbsp;
                   <label>Flexible</label>
               </li>
           </ul>
           <div class="details">
               <div class="photo"><img/></div>
               <dl>
                   <dt>Ship:</dt>
                   <dd>C</dd>
                   <dt>Departs</dt>
                   <dd>
                       <em>02:00</em>
                   </dd>
                   <dt>Arrives</dt>
                   <dd>
                       <em>06:00</em>
                   </dd>
               </dl>
           </div>
       </div>
       <div class="option">
           <ul class="fares">
               <li>
                   <input type="radio">&nbsp;
                   <label>Economy</label>
               </li>
               <li>
                   <input type="radio">&nbsp;
                   <label>Flexible</label>
               </li>
           </ul>
           <div class="details">
               <dl>
                   <dt>Ship:</dt>
                   <dd>D</dd>
                   <dt>Departs</dt>
                   <dd>
                       <em>12:45</em>
                   </dd>
                   <dt>Arrives</dt>
                   <dd>
                       <em>16:30</em>
                   </dd>
               </dl>
           </div>
       </div>
   </div>
</form>
```

You can see the elements `<div class="segment clearfix">` and `<div class="option">` repeated for couple times. We can use the Repeat object for this UI module. The Repeat object inherits the Container object and You can use it just like a Container. The difference is that you should use index to refer to a Repeat object.

For the above html source, we can create the following UI module

```
    ui.Form(uid: "SailingForm", clocator: [name: "selectedSailingsForm"] ){
      Repeat(uid: "Section", clocator: [tag: "div", class: "segment clearfix"]){
        Repeat(uid: "Option", clocator: [tag: "div", class: "option", direct: "true"]){
          List(uid: "Fares", clocator: [tag: "ul", class: "fares", direct: "true"], separator: "li"){

            Container(uid: "all"){
                RadioButton(uid: "radio", clocator: [:], respond: ["click"])
                TextBox(uid: "label", clocator: [tag: "label"])
            }
          }
          Container(uid: "Details", clocator: [tag: "div", class: "details"]){
            Container(uid: "ShipInfo", clocator: [tag: "dl"]){
              TextBox(uid: "ShipLabel", clocator: [tag: "dt", position: "1"])
              TextBox(uid: "Ship", clocator: [tag: "dd", position: "1"])
              TextBox(uid: "DepartureLabel", clocator: [tag: "dt", position: "2"])
              Container(uid: "Departure", clocator: [tag: "dd", position: "2"]){
                TextBox(uid: "Time", clocator: [tag: "em"])
              }
              TextBox(uid: "ArrivalLabel", clocator: [tag: "dt", position: "3"])
              Container(uid: "Arrival", clocator: [tag: "dd", position: "3"]){
                TextBox(uid: "Time", clocator: [tag: "em"])
              }
            }
          }
        }
      }
    }
```

To test the UI module, we can create the following test case.

```
    @Test
    public void testRepeat(){
        connect("JForm");
//        useCssSelector(true);            //uncomment this line if you choose to use css selector
        int num = jlm.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = jlm.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = jlm.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }

```


### Selector ###

Selector represents the Ui with tag "select". The user can select from a set of options. Selector has methods, such as:

  * selectByLabel(String target)
  * selectByValue(String value)
  * addSelectionByLabel(String target)
  * addSelectionByValue(String value)
  * removeSelectionByLabel(String target)
  * removeSelectionByValue(String value)
  * removeAllSelections()
  * String getSelectOptions()
  * String getSelectedLabels()
  * String getSelectedLabel()
  * String getSelectedValues()
  * String getSelectedValue()
  * String getSelectedIndexes()
  * String getSelectedIndex()
  * String getSelectedIds()
  * String getSelectedId()
  * boolean isSomethingSelected()

**Example:**

```
Selector(uid: "issueType", clocator: [name: "can", id: "can"])
```

For the Selector UI object, the following DSL methods have been implemented in the Tellurium new Engine:

  * `select(String uid, String target)`: Equals to `selectByLabel`.
  * `selectByLabel(String uid, String target)`: Select based on the text attribute.
  * `selectByValue(String uid, String target)`: Select based on the value attribute.
  * `selectByIndex(String uid, int target)`: Select based on index (starting from 1).
  * `selectById(String uid, String target)`: Select based on the ID attribute.
  * `String[] getSelectOptions(String uid)`: Get all option texts.
  * `String[] getSelectValues(String uid)`: Get all option values.
  * `String[] getSelectedLabels(String uid)`: Get selected texts.
  * `String getSelectedLabel(String uid)`: Get selected text, for multiple selections, return the first one.
  * `String[] getSelectedValues(String uid)`: Get selected values.
  * `String getSelectedValue(String uid)`: Get selected value, for multiple selections, return the first one.
  * `addSelectionByLabel(String uid, String target)`: Add selection based on text.
  * `addSelectionByValue(String uid, String target)`: Add selection based on value.
  * `removeSelectionByLabel(String uid, String target)`: Remove selection based on text.
  * `removeSelectionByValue(String uid, String target)`: Remove selection based on value.
  * `removeAllSelections(String uid)`: Remove all selections.

Be aware, the above `target` variable can be a partial matching as follows.

  * `^text`: starts with text.
  * `$text`: ends with text.
  * `*text`: contains text.
  * `!text`: does not contain text.

We can use the following example to demonstrate the use of the new Selector APIs.

HTML snippet:
```
<form method="POST" action="check_phone">
    <select name="Profile/Customer/Telephone/@CountryAccessCode" style="font-size:92%">
        <option value="1" selected=selected>US</option>
        <option value="2" id="uk">UK</option>
        <option value="3">AT</option>
        <option value="4">BE</option>
        <option value="4" id="ca">CA</option>
        <option value="6">CN</option>
        <option value="7">ES</option>
        <option value="8">VG</option>
    </select>
    <input type="text" class="medium paxFerryNameInput" value="" name="Profile/Customer/Telephone/@PhoneNumber"
           maxlength="16" id="phone1" tabindex="26">
    <input name="submit" type="submit" value="Check">
</form>
```

UI Module definition:

```
   ui.Form(uid: "Form", clocator: [method: "POST", action: "check_phone"]){
      Selector(uid: "Country", clocator: [name: "\$CountryAccessCode"])
      InputBox(uid: "Number", clocator: [name: "\$PhoneNumber"])
      SubmitButton(uid: "check", clocator: [value: "Check"])
   }
```

Test Cases:

```
    @Test
    public void testSelect(){
        String[] countries = sm.getSelectOptions("Form.Country");
        for(String country: countries){
            System.out.println("Country: " + country);
        }
        String[] values = sm.getSelectValues("Form.Country");
        for(String value: values){
            System.out.println("Value: " + value);
        }
        sm.selectByLabel("Form.Country", "US");
        String selected = sm.getSelectedLabel("Form.Country");
        assertEquals("US", selected);
        sm.selectByLabel("Form.Country", "$E");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("BE", selected);
        sm.selectByLabel("Form.Country", "^E");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("ES", selected);
        sm.selectByValue("Form.Country", "8");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("VG", selected);
        sm.selectByIndex("Form.Country", 6);
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("CN", selected);
        sm.selectById("Form.Country", "uk");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("UK", selected);
    }
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Selector.png?gda=b632Hj4AAACvY3VTaWrtpkaxlyj9o09EYhkj25wnL3ZjnmNSek4H7Li8ZNp69eplCP3Btz3M8UDjsKXVs-X7bdXZc5buSfmx&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Container ###

Container is an abstract object that can hold a collection of Ui objects. As a result, the Container has a special attribute "useGroupInfo" and its default value is false. If this attribute is true, the Group Locating is enabled. But make sure all the Ui objects inside the Container are children nodes of the Container in the DOM, otherwise, you should not use the Group Locating capability.

**Example:**

```
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
   InputBox(uid: "searchbox", clocator: [title: "Google Search"])
   SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
   SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
 }
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Container.png?gda=nR7EcT8AAACvY3VTaWrtpkaxlyj9o09E_HoO-w-KJ5H5AQEAtuACpL8e0j-ZJvcoFEzwQ0yOKlOccyFKn-rNKC-d1pM_IdV0&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Form ###

Form is a type of Container with its tag being "form" and it represents web form. Like Container, it has the capability to use Group Locating and it has a special method:

  * submit()

This method is useful and can be used to submit input data if the form does not have a submit button.

**Example:**

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   TextBox(uid: "searchLabel", clocator: [tag: "span"])

   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Form.png?gda=Sy4vpzoAAACvY3VTaWrtpkaxlyj9o09EAysfc1sfERzii7dZMaerX1c5GCXVf1Htqu13xS-QMXn97daDQaep90o7AOpSKHW0&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Table ###

Table is one of the most complicated Ui Object and also the most often used one. Obviously, its tag is "table" and a table can have headers besides rows and columns.

Table is a good choice for a data grid. Tellurium can handle its header, rows, and columns automatically. Table has one important feature: a different UiID than other Ui objects.

For example, if the id of the table is "table1", then its i-th row and j-th column is referred as `"table1[i][j]"` and its m-th header is `"table1.header[m]"`.

Another distinguished feature of Table is that a user can define Ui templates for its elements.

For example, the following example defines different table headers and the template for the first column, the element on the second row and the second column, and the template for all the other elements in other rows and columns.

```
ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], 
      group: "true")
{
   //define table header
   //for the border column 
   TextBox(uid: "{header: 1}", clocator: [:])
   UrlLink(uid: "{header: 2} as Filename", clocator: [text: "*Filename"])
   UrlLink(uid: "{header: 3} as Summary", clocator: [text: "*Summary + Labels"])
   UrlLink(uid: "{header: 4} as Uploaded", clocator: [text: "*Uploaded"])
   UrlLink(uid: "{header: 5} as Size", clocator: [text: "*Size"])
   UrlLink(uid: "{header: 6} as DownloadCount", clocator: [text: "*DownloadCount"])
   UrlLink(uid: "{header: 7} as Extra", clocator: [text: "*..."])

   //define Ui object for the second row and the second column
   InputBox(uid: "{row: 2, column: 2}" clocator: [:])
   //define table elements
   //for the border column
   TextBox(uid: "{row: all, column: 1}", clocator: [:])
   //For the rest, just UrlLink
   UrlLink(uid: "{row: all, column: all}", clocator: [:])
}
```

Be aware, the templates inside the Table follow the name convention:

  * For the i-th row, j-th column, the uid should be "{row: i, column: j} as whatever\_id"
  * The wild case for row or column is `"all"`

As a result, `"row : all, column : 3"` refers to the 3rd column for all rows. Once the templates are defined for the table, Tellurium uses a special way to find a matching for a Ui element `"table[i][j]"` in the table.

For more details on how Tellurium maps the runtime uid to UI templates, please see [Tellurium UID Description Language](http://code.google.com/p/aost/wiki/TelluriumUIDDescriptionLanguage).

Generally speaking, Tellurium always searches for the special case first, then broadening the search for more general cases, until all matching cases are found. In this way, user can define very flexible templates for tables.

Table is a type of Container and thus, it can use the Group Locating feature. Table has the following special methods:

  * boolean hasHeader()
  * int getTableHeaderColumnNum()
  * int getTableMaxRowNum()
  * int getTableMaxColumnNum()

From Tellurium 0.6.0, you can also specify the tbody attribute for the Table object. This may be helpful if the user has multiple tbody elements inside a single table tab.

For example, specify `tbody` as follows:

```
Container(uid: "tables", clocator:[:]){
   Table(uid: "first", clocator: [id: "someId", tbody: [position: "1"]]){
      ......
    }

   Table(uid: "second", clocator: [id: "someId", tbody: [position: "2"]]){
      ......
    }
    ...
}
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Table.png?gda=Q7WHKjsAAACvY3VTaWrtpkaxlyj9o09EUIpv859W_EwCa1Q6m6z5KZXQRNjjvS1-2bMcnX_XyTwGRdr3QrylPkw2aRbXD_gF&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Standard Table ###

A StandardTable is a table in the following format

```

table
      thead
         tr
           th
           ...
           th
      tbody
         tr
           td
           ...
           td
         ...
       tbody (multiple tbodies)
         tr
           td
           ...
           td
         ...
      tfoot
         tr
           td
           ...
           td 
```

To overwrite the default layout, Tellurium core provides the following attributes in the StandardTable object.
  * _ht_: header tag.
  * _hrt_: header row tag, the default tag is "tr".
  * _hct_: header column tag, the default tag is "th".
  * _bt_: body tag.
  * _brt_: body row tag, the default tag is "tr".
  * _bct_: body column tag, the default tag is "td".
  * _ft_: footer tag.
  * _frt_: footer row tag, the default tag is "tr".
  * _fct_: footer column tag, the default tag is "td".

To overwrite the above tags, simply define them in the object definition. For example,

```
    ui.StandardTable(uid: "table3", clocator: [:], hct: "td"){
      TextBox(uid: "{header: all}", clocator: [:])
      TextBox(uid: "{footer: all}", clocator: [:])
    }
```

The header column tag is overwritten to be "td" instead of the default tag "th".

To be more accurate, the footer definition has been changed from "foot" to "footer".

For a StandardTable, you can specify UI templates for different tbodies. Apart from the methods in Table, it has the following additional methods:

  * int getTableFootColumnNum()
  * int getTableMaxTbodyNum()
  * int getTableMaxRowNumForTbody(int tbody\_index)
  * int getTableMaxColumnNumForTbody(int body\_index)

Example:

```
ui.StandardTable(uid: "table", clocator: [id: "std"]) {
   UrlLink(uid: "{header: 2} as Filename", clocator: [text: "*Filename"])
   UrlLink(uid: "{header: 3} as Uploaded", clocator: [text: "*Uploaded"])
   UrlLink(uid: "{header: 4} as Size", clocator: [text: "*Size"])
   TextBox(uid: "{header: all}", clocator: [:])

   Selector(uid: "{tbody: 1, row:1, column: 3} as Select", clocator: [name: "can"])
   SubmitButton(uid: "{tbody: 1, row:1, column:4} as Search", clocator: [value: "Search", name: "btn"])
   InputBox(uid: "{tbody: 1, row:2, column:3} as Words", clocator: [name: "words"])
   InputBox(uid: "{tbody: 2, row:2, column:3} as Without", clocator: [name: "without"])
   InputBox(uid: "{tbody: 2, row:*, column:1} as Labels", clocator: [name: "labels"])

   TextBox(uid: "{foot: all}", clocator: [tag: "td"])
}
```

### List ###

List is also a Container type abstract Ui object and it can be used to represent any list like Ui objects. Very much like Table, users can define Ui templates for List and following rule of "the special case first and then the general case". The index number is  used to specify an element and "all" is used to match all elements in the List. List also uses TextBox as the default Ui if no template could be found. Since List is a Container type, it can use the Group Locating feature.

List has one special attribute "separator", which is used to indicate the tag used to separate different List UI elements. If "separator" is not specified or "", all UI elements must be under the same DOM node, i.e., they are siblings.

Example:

```

List(uid: "subcategory", locator: "", separator: "p"){
    InputBox(uid: "{2} as Search", clocator: [title: "Google Search"])
    UrlLink(uid: "{all}", clocator: [:])
}
```

The List includes the following additional methods:

  * int getListSize(id): Gets the item count of a list


UML Class Diagram:

http://tellurium-users.googlegroups.com/web/List.png?gda=01ee8zoAAACvY3VTaWrtpkaxlyj9o09EHGLXJixxWuoZZiLNkS9cOj7XS0Nk1NECX1UixwubQ5H97daDQaep90o7AOpSKHW0&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Simple Menu ###

The SimpleMenu represent a menu without a header and only contains menu items. The default tag is "div" and user should specify the alias name for each menu item. For example,

```
//items is a map in the format of "alias name" : menu_item
ui.SimpleMenu(uid: "IdMenu", clocator:[class: "popup", id: "pop_0"],
    items: ["SortUp":"Sort Up", "SortDown":"Sort Down", "HideColumn":"Hide Column"])
```

The above menu specified the menu item "Sort up", "Sort Down", and "Hiden Column" with their alias names. Users should use the alias name to refer the menu item, for instance, "IdMenu.SortUp".

The SimpleMenu has the following methods:

  * click()
  * mouseOve()
  * mouseOut()

### Select Menu ###

SelectMenu is designed for the selecting column menu on the Tellurium Issues page and it is prototyped to demonstrate how to write Ui object with interaction with the DOM since the Ui elements have different patterns at runtime, hence, it is not a general purpose Ui object. SelectMenu could have a header and its menu item content could keep changing when users select different columns to display.

The SelectMenu on the Tellurium issues page is expressed as follows,

```
ui.SelectMenu(uid: "selectColumnMenu", clocator:[class: "popup",id: "pop__dot"], 
    title: "Show columns:", items: ["ID":"ID", "Type":"Type", "Status":"Status",
    "Priority":"Priority", "Milestone":"Milestone", "Owner":"Owner", 
    "Summary":"Summary", "Stars":"Stars", "Opened":"Opened", "Closed":"Closed",
    "Modified":"Modified", "EditColumn":"Edit Column Spec..." ])
```

Like SimpleMenu, SelectMenu also has the following methods:

  * click()
  * mouseOve()
  * mouseOut()

### Frame ###

Frame is a type of Container and is used to mode Frame or IFrame. It includes the
following attributes:
  * id
  * name
  * title

and the following methods

  * selectParentFrame()
  * selectTopFrame()
  * selectFrame(locator)
  * getWhetherThisFrameMatchFrameExpression(currentFrameString, target)
  * waitForFrameToLoad(frameAddress, timeout)

When you test website with IFrames, you should use multiple window mode, i.e., set the option useMultiWindows to be true in TelluriumConfig.groovy. Be aware that Selenium uses the _name_ attribute to locate a Frame.

Example:

```
ui.Frame(uid: "SubscribeFrame", name: "subscrbe"){
   Form(uid: "LoginForm", clocator: [name: "loginForm"]){
      InputBox(uid: "UserName", clocator: [id: "username", type: "text"])
      InputBox(uid: "Password", clocator: [id: "password", type: "password"])
      Button(uid: "Login", clocator: [type: "image", class: "login"])
      CheckBox(uid: "RememberMe", clocator: [id: "rememberme"])
   }
} 
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Frame.png?gda=r8U0ejsAAACvY3VTaWrtpkaxlyj9o09EjtjD0VCwlgWwf4IYqIRmHEThbDJeoOs4Ibpiwk-_c9AGRdr3QrylPkw2aRbXD_gF&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q


### Window ###

Window is a type of Container and is used to mode Popup Window. It includes the
following attributes:
  * id
  * name
  * title

and the following methods

  * openWindow(String UID, String url)
  * selectWindow(String UID)
  * closeWindow(String UID)
  * boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target)
  * waitForPopup(String UID, int timeout)

Example:

```
ui.Window(uid: "HelpWindow", name: "HelpWindow"){
...
}

openWindow helpUrl, "HelpWindow"
waitForPopUp "HelpWindow", 2000
selectWindow "HelpWindow" 
...
selectMainWindow()
```

UML Class Diagram:

http://tellurium-users.googlegroups.com/web/Window.png?gda=Gu9YEDwAAACvY3VTaWrtpkaxlyj9o09E5Vet3V7T4_qN9X8E0xCb5LaejjoTjElKJray_l5X0DD9Wm-ajmzVoAFUlE7c_fAt&gsc=9wy8aQsAAAAwbhxuc1eUADl_IisDYQ6Q

### Option ###

Option is also designed to be adaptive the dynamic web. Option is a pure abstract object and it holds multiple UIs with each representing a possible UI pattern at runtime. For example, the List/Grid selector on the issue page can described as:

```
//The selector to choose the data grid layout as List or Grid
ui.Option(uid: "layoutSelector"){
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
        TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"])
        UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
    }
    Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
        UrlLink(uid: "List", clocator: [text: "List", direct: "true"])
        TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"])
    }
}
```

Note, the option's uid must be the same as the next UI objects it represent and in this way, you do not need to include option's uid in the UiID. For example,  you can just use

```
click "layoutSelector.List"
```

instead of

```
click "layoutSelector.layoutSelector.List"
```

The option object will automatically detect which UI pattern you need to use at runtime.


### All Purpose Object ###

Tellurium 0.7.0 added an all purpose object for internal usage only. The object is defined as

```
class AllPurposeObject extends UiObject {

}
```

This object includes all methods for non-Container type objects.