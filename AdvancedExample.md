## Advanced Examples ##

Tellurium includes advance examples to use Tellurium project website as a reference and implement test cases for different pages. They are non-trivial and rather complicated Tellurium tests.

### Tellurium Project Home Page ###

To demonstrate how to define multiple UI objects, the Tellurium project home page is described as:

```
//define the menu
//It is fine to use Container to abstract Table if you have special table
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
//since the actual text is  Project&nbsp;Home, we can useString partial match here. Note "%%" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "%%Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the search module, which includes an input box, two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}
```

### Tellurium Project Download Page ###

The download page can be modularized as follows,

```
//define UI module of a form include download type selector and download search
ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true") {
    Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
    TextBox(uid: "searchLabel", clocator: [tag: "span"])
    InputBox(uid: "searchBox", clocator: [name: "q"])
    SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}

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

The form includes a download type selector and a download search button. The table represents the data grid listing the download file names, summary + labels, upload date, size, and download count.

### Tellurium Project WiKi Page ###

Similarly, the wiki page is illustrated as follows,

```
//define UI module of a form include wiki page type selector and page search
ui.Form(uid: "wikiSearch", clocator: [action: "/p/aost/w/list", method: "get"], group: "true") {
    Selector(uid: "pageType", clocator: [name: "can", id: "can"])
    TextBox(uid: "searchLabel", clocator: [tag: "span"])
    InputBox(uid: "searchBox", clocator: [name: "q"])
    SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}

ui.Table(uid: "wikiResult", clocator: [id: "resultstable", class: "results"], group: "true"){
    //define table header
    //for the border column
    TextBox(uid: "header: 1", clocator: [:])
    UrlLink(uid: "header: 2", clocator: [text: "%%PageName"])
    UrlLink(uid: "header: 3", clocator: [text: "%%Summary + Labels"])
    UrlLink(uid: "header: 4", clocator: [text: "%%Changed"])
    UrlLink(uid: "header: 5", clocator: [text: "%%ChangedBy"])           
    UrlLink(uid: "header: 6", clocator: [text: "%%..."])

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

### Tellurium Project Issues Page ###

The Issues page is more complicated and it includes the following Ui modules

```
//define UI module of a form include issue type selector and issue search
ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
    Selector(uid: "issueType", clocator: [name: "can", id: "can"])
    TextBox(uid: "searchLabel", clocator: [tag: "span"])
    InputBox(uid: "searchBox", clocator: [name: "q"])
    SubmitButton(uid: "searchButton", clocator: [value: "Search"])
}

ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "post"], group: "true"){
    Table(uid: "searchTable", clocator: [class: "advquery"]){
        Selector(uid: "row:1, column: 3", clocator: [name: "can"])
        SubmitButton(uid: "row:1, column:4", clocator: [value: "Search", name: "btn"])
        InputBox(uid: "row:2, column:3", clocator:[name: "words"])
        InputBox(uid: "row:3, column:3", clocator:[name: "without"])
        InputBox(uid: "row:5, column:3", clocator:[name: "labels"])
        Table(uid: "row:6, column:1", clocator:[:]){
            UrlLink(uid: "row:1, column:1", clocator:[text: "%%More Search Tips"])
        }
        InputBox(uid: "row:6, column:3", clocator:[name: "statuses"])
        InputBox(uid: "row:7, column:2", clocator:[name: "reporters"])
        InputBox(uid: "row:8, column:2", clocator:[name: "owners"])
        InputBox(uid: "row:9, column:2", clocator:[name: "cc"])
        InputBox(uid: "row:10, column:3", clocator:[name: "commentby"])
   }
}

ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
    //define table header
    //for the border column
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

//items is a map in the format of "alias name" : menu_item
ui.SimpleMenu(uid: "IdMenu", clocator:[class: "popup", id: "pop_0"],
    items: ["SortUp":"Sort Up", "SortDown":"Sort Down", "HideColumn":"Hide Column"])

//define the dot menu where you can select different column to display
ui.SelectMenu(uid: "selectColumnMenu", clocator:[class: "popup",id: "pop__dot"], title: "Show columns:",
    items: ["ID":"ID", "Type":"Type", "Status":"Status", "Priority":"Priority", "Milestone":"Milestone", "Owner":"Owner", "Summary":"Summary", "Stars":"Stars", "Opened":"Opened", "Closed":"Closed", "Modified":"Modified", "EditColumn":"Edit Column Spec..." ])

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

Apart from the issue search form, issue advanced search, and the data grid, this page also includes a menu, and a SelectMenu, which is dynamic at runtime. That is to say, the Ui XPath is not fixed at runtime. The Ui Object SelectMenu will check the current DOM to determine what the actual Ui is. The last one is the Option object, which is an abstract object and it holds multiple UIs with each representing a possible UI pattern. The option object will automatically detect which UI pattern you need to use at runtime.

Please see TelluriumProjectPage, TelluriumDownloadsPage, TelluriumWikiPage, and TelluriumIssuesPage for Ui Modules. See TelluriumProjectPageJavaTestCase, TelluriumDownloadsPageJavaTestCase, TelluriumWikiPageJavaTestCase, and TelluriumIssuesPageJaveTestCase for detailed tests.