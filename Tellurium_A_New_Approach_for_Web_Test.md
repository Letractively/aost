# Tellurium: A New Approach for Web Testing #

Tellurium is a five months baby now and it starts to attract more people to use it. People may have already been familiar with its features such as descriptive, expressive, easy to use, and flexible. But they may not realize that Tellurium is conceptually a new approach for web testing. You may wonder why Tellurium is a new approach, isn't is based on Selenium framework? The answer is that Tellurium is a UI module based framework. The analogy between Tellurium and Selenium is somehow like C++ and C. Although Tellurium is born from Selenium, but conceptually it is a different framework with a lot of new ideas.

What is UI module? UI module is a collection of UI elements you group them together. Usually, the UI module represents a composite UI object in the format of nested basic UI elements. For example, the download search module in Tellurium project site is defined as follows,

```
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
   Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
   TextBox(uid: "searchLabel", clocator: [tag: "span"])
   InputBox(uid: "searchBox", clocator: [name: "q"])
   SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}
```

Be aware that in most case the UI elements inside the composite UI object have relationship to each other. In the DOM structure, they should be on the same sub-tree with the top UI element as the parent. The exception is when the upper UI is only a logic UI and it does not represent any actual UI in the DOM. For example, in the following UI module, we only logically group the "inputbox1" and "button1" together.

```
ui.Container(uid: "google_search"){
   InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
   Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
}
```

But for most applications, you still want to use a physical top UI element so that you can exploit the relationship among the UI elements inside.

Why UI module concept is so important? It is the base that Tellurium built on. Let us look a bit closer at why it is important.

First, UI module makes it possible to build UI elements' xpaths at runtime. Because the nested UI elements have relationship among them in the DOM tree. Tellurium can append their relative xpaths together to form the actual runtime xpath, which also makes the clocator possible in Tellurium.

Second, UI module makes Tellurium robust. Remember, each UI element in Tellurium can be defined with clocator, i.e., a set of attributes about the UI element itself. If one UI element is changed, the rest UI elements stay the same since Tellurium will build the new xpath based on the updated attributes for that UI element.

Take the Tellurium download search module listed above as an example, if the Form is changed with attribute "method" to be "post", you only need to change that attribute and leave all the rest intact. Image the same scenario for Selenium, you have to change all the xpaths for the included UI elements: Selector, TextBox, InputBox, and SubmitButton. The reason is Selenium uses fixed locators, since the above UI elements have the same parent UI element, Form, in its locators and once the Form is changed, they are all affected and have to be updated.

Third, UI module makes Tellurium expressive. Since the UI module actually consists of a set of nested UI elements, you can specify a name for each UI element. When you try to refer to a UI element, you can simply append the names along the path to the specific element. For example, in the Tellurium download search module, you can refer the Selector element as "downloadSearch.downloadType". This name convention is expressive and you know which UI element you are referring to. In this way, you avoid writing xpaths everywhere like Selenium does.

Fourth, UI module makes Group Locating possible. The Group Locating in Tellurium is to exploit relationship among the set of nested UI elements in a composite object. For example, the Tellurium download search module sets the group attribute to be true for the Form UI element, which will turn on the Group Locating capability. That is to say, when Tellurium tries to find the Form UI element, the search becomes:

```
"Find a Form with a child element Selector, a child element TextBox, a child element InputBox, and a child element SubmitButton"
```

Once the Form locator is determined, the locators of its child elements can be defined very easily by their relative xpaths to the Form.

Fifth, UI module makes composite objects reusable. In Tellurium, you can define the composite object as a widget object, then you can keep reuse the widget and do not need to define all the inside UI elements it contains. For example, in Tellurium DOJO widget project, we defined the DatePicker widget. Then in the UI module, we can use it directly,

```
ui.Form(uid: "dropdown", clocator: [:], group: "true"){
   TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
   InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
   Image(uid: "selectDate", clocator: [title: "select a date", dojoattachpoint: "containerDropdownNode", alt: "date"])
   DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", dojoattachpoint: "subWidgetContainerNode"])
}
```

Sixth, UI module makes Tellurium be able to address dynamic web page. For example, Tellurium List and Table objects can use templates to define the UI elements. The actual UI elements could be defined at runtime. Tellurium download page includes a data grid, where the data are dynamic at runtime and which could be described as follows,

```
ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], group: "true"){
    //define table header
    //for the border column
    TextBox(uid: "header: 1", clocator: [:])
    UrlLink(uid: "header: 2", clocator: [text: "%%Filename"])
    UrlLink(uid: "header: 3", clocator: [text: "%%Summary + Labels"])
    UrlLink(uid: "header: 4", clocator: [text: "%%Uploaded"])
    UrlLink(uid: "header: 5", clocator: [text: "%%Size"])
    UrlLink(uid: "header: 6", clocator: [text: "%%DownloadCount"])
    UrlLink(uid: "header: 7", clocator: [text: "%%..."])

    //define table elements
    //for the border column
    TextBox(uid: "row: *, column: 1", clocator: [:])
    //the summary + labels column consists of a list of UrlLinks
    List(uid: "row:*, column: 3", clocator: [:]){
        UrlLink(uid: "all", clocator: [:])
    }
    //For the rest, just UrlLink
    UrlLink(uid: "all", clocator: [:])
}
```

Furthermore, Tellurium uses the Option object to provide the capability to define multiple UI modules and the actual runtime UI module will be determined by Tellurium. For example, Tellurium issue page includes the following Option objects,

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

For Selenium, it is impossible to address the above dynamic web modules.

Finally, UI module will make Tellurium move to a different future path from Selenium. Tellurium is born with the composite object in mind, but Selenium is not. At the current stage, Tellurium is built on top of Selenium. There are some drawbacks for this architecture. For example, Tellurium has to build runtime xpath first, and then drive Selenium using the xpath. This is not really efficient and things can be worse if the composite object needs to make multiple round trips to Selenium core. In the future, hopefully, we will create our own test driving engine which supports composite objects. Also, the new driving engine will address some dynamic changes in the DOM, which is very important to test Javascript applications.



