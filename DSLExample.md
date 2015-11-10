## DSL Examples ##

Tellurium supports pure dsl tests. The following examples demonstrate how to write pure dsl files as selenium tests.

### Tellurium Testing DSL Script ###

We created an example dsl test file in src/example/dsl/google.dsl. In this file we define multiple UI modules. Since we have multiple UI modules, we must use the full path, i.e., the top level UI names such as "google\_start\_page", "menu", and "search" cannot be omitted.

In test script, you can use any methods defined in DslContext and DslTelluriumGroovyTestCase including assertions, any Groovy code for flow control, or Java code since Groovy is compatible with Java syntax, which is really powerful.

The google.dsl is defined as follows,

```
//define google start page
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
    InputBox(uid: "searchbox", clocator: [title: "Google Search"])
    SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
    SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}

//define Tellurium project menu
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
    UrlLink(uid: "project_home", clocator: [text: "%%Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the Tellurium project search module, which includes an input box, two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}

openUrl "http://www.google.com"
type "google_start_page.searchbox", "Tellurium Selenium"
pause 500
click "google_start_page.SubmitButton"
waitForPageToLoad 30000

openUrl "http://code.google.com/p/aost/"
click "menu.project_home"
waitForPageToLoad 30000
click "menu.downloads"
waitForPageToLoad 30000
click "menu.wiki"
waitForPageToLoad 30000
click "menu.issues"
waitForPageToLoad 30000

openUrl "http://code.google.com/p/aost/"
type "search.searchbox", "Tellurium Selenium"
click "search.search_project_button"
waitForPageToLoad 30000

type "search.searchbox", "tellurium selenium dsl groovy"
click "search.search_web_button"
waitForPageToLoad 30000
```

In your own .dsl scripts you can put assertions in.

To run the .dsl file, you can call "DslScriptExecutor dsl\_file". For example, in the project directory, you can run google.dsl as follows:

```
java -cp ./lib/junit-3.8.2.jar:./lib/selenium-java-client-driver.jar:./lib/selenium-server.jar:./lib/groovy-all-1.5.6.jar:./dist/tellurium-0.4.0.jar aost.dsl.DslScriptExecutor src/example/dsl/google.dsl 
```

Here we assume you have created the tellurium-0.4.0.jar in the "dist/" directory by running
```
ant compile-test
```

There is a shell script "rundsl.sh" in the project directory and you can simply run

```
./rundsl.sh src/example/dsl/google.dsl 
```

In windows, please run rundsl.bat instead.


### Tellurium Data Driven Testing DSL Script ###

The example described in the [Data driven testing examples](http://code.google.com/p/aost/wiki/DataDrivenTestingExample) session, i.e., Google Book List and Google Code Hosting pages, can also be written in pure DSL as follows,

```
//define UI module
ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true") {
    TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
    List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "/p") {
      UrlLink(uid: "all", locator: "/a")
    }
}

ui.Table(uid: "labels_table", clocator: [:], group: "true"){
    TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
    Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        UrlLink(uid: "all", locator: "/a")
    }
}

//define file data format
fs.FieldSet(name: "GoogleBookList", description: "Google Book List") {
    Test(value: "checkBookList")
    Field(name: "category", description: "book category")
    Field(name: "size", type: "int", description: "google book list size ")
}

fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
    Test(value: "getGCHStatus")
    Field(name: "label")
    Field(name: "rowNum", type: "int")
    Field(name: "columNum", type: "int")
}

fs.FieldSet(name: "GCHLabel", description: "Google Code Hosting input") {
    Test(value: "clickGCHLabel")
    Field(name: "row", type: "int")
    Field(name: "column", type: "int")
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

defineTest("clickGCHLabel"){
    def row = bind("GCHLabel.row")
    def column = bind("GCHLabel.column")

    openUrl("http://code.google.com/hosting/")
    click  "labels_table[2][1].[${row}][${column}]"

    waitForPageToLoad 30000
}

defineTest("checkBookList"){
    def expectedCategory = bind("GoogleBookList.category")
    def expectedSize = bind("GoogleBookList.size")
    openUrl("http://books.google.com/")
    String category = getText("GoogleBooksList.category")
    compareResult(expectedCategory, category)

    int size = getListSize("GoogleBooksList.subcategory")
    compareResult(expectedSize, size){
        assertTrue(expectedSize == size)
    }
}

//load file
loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"

//read each line and run the test script until the end of the file
stepToEnd()

/close file
closeData()

```