(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))




# Examples #

## Custom UI Object ##

Tellurium supports custom UI objects defined by users. For most UI objects, they must extend the "UiObject" class and then define actions or methods they support. For container type UI objects, i.e., which hold other UI objects, they should extends the Container class. Please see Tellurium Table and List objects for more details.

In the tellurium-junit-java project, we defined the custom UI object "SelectMenu" as follows:

```
class SelectMenu extends UiObject{   
    public static final String TAG = "div"

    String header = null

    //map to hold the alias name for the menu item in the format of 
    //"alias name" : "menu item"
    Map<String, String> aliasMap

    def click(Closure c){
        c(null)
    }

    def mouseOver(Closure c){
        c(null)
    }

    def mouseOut(Closure c){
        c(null)
    }

    public void addTitle(String header){
        this.header = header
    }
    
    public void addMenuItems(Map<String, String> menuItems){
        aliasMap = menuItems
    }
   
    ......

}
```

For each UI object, you must define the builder so that Tellurium knows how to construct the UI object when it parses the UI modules. For example, we define the builder for the "SelectMenu" object as follows:

```
class SelectMenuBuilder extends UiObjectBuilder{
    static final String ITEMS = "items"
    static final String TITLE = "title"

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, SelectMenu.TAG)
        SelectMenu menu = this.internBuild(new SelectMenu(), map, df)
        Map<String, String> items = map.get(ITEMS)
        if(items != null && items.size() > 0){
           menu.addMenuItems(items) 
        }
        
        menu.addTitle(map.get(TITLE))
        
        return menu
    }
}
```

You may wonder how to hook the custom objects into Tellurium core so that it can recognize the new type. The answer is simple, you just add the UI object name and its builder class name to Tellurium configuration file TelluriumConfig.groovy. Update the following section:

```
    uiobject{
        builder{
           SelectMenu="org.tellurium.builder.SelectMenuBuilder"
        }
    }
```

## UI Module ##

Take the [Tellurium issue page](http://code.google.com/p/aost/issues/list) as an example, the UI module can be defined as

```
public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }

    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], 
             group: "true") 
{
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "*ID"])
      UrlLink(uid: "header: 3", clocator: [text: "*Type"])
      UrlLink(uid: "header: 4", clocator: [text: "*Status"])
      UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "*..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }

    ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "false",
             clocator: [id: "resultstable", class: "results"], group: "true") {
      //define table header
      //for the border column
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "*ID"])
      UrlLink(uid: "header: 3", clocator: [text: "*Type"])
      UrlLink(uid: "header: 4", clocator: [text: "*Status"])
      UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "*..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      TextBox(uid: "row: *, column: 8", clocator: [:])
      TextBox(uid: "row: *, column: 10", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }

  }

  public List<String> getDataForColumn(int column){
        int nrow = getTableMaxRowNum("issueResult")
        if(nrow > 20) nrow = 20
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
  }

  public List<String> getDataForColumnWithCache(int column){
        int nrow = getTableMaxRowNum("issueResultWithCache")
        if(nrow > 20) nrow = 20
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResultWithCache[${i}][${column}]"))
        }

        return lst
  }

  public String[] getAllText(){
    return getAllTableCellText("issueResult")
  }

  public int getTableCellCount(){
    String sel = getSelector("issueResult")
    sel = sel + " > tbody > tr > td"

    return getJQuerySelectorCount(sel)
  }

  public String[] getTableCSS(String name){
    String[] result = getCSS("issueResult.header[1]", name)

    return result
  }

  public String[] getIsssueTypes() {
    return getSelectOptions("issueSearch.issueType")
  }

  public void selectIssueType(String type) {
    selectByLabel "issueSearch.issueType", type
  }

  public void searchIssue(String issue) {
    keyType "issueSearch.searchBox", issue
    click "issueSearch.searchButton"
    waitForPageToLoad 30000
  }

  public boolean checkamICacheable(String uid){
    UiObject obj = getUiElement(uid)
    boolean cacheable = obj.amICacheable()
    return cacheable
  }
}
```

## Groovy Test Case ##

```
public class TelluriumIssueGroovyTestCase extends GroovyTestCase{

  private TelluriumIssueModule tisp;

  public void setUp() {
    tisp = new TelluriumIssueModule();
    tisp.defineUi();
  }

  public void tearDown() {
  }

  public void testDumpWithXPath() {
    tisp.disableJQuerySelector();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelector() {
    tisp.useJQuerySelector();
    tisp.exploreSelectorCache = false;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelectorCacheEnabled() {
    tisp.useJQuerySelector();
    tisp.exploreSelectorCache = true;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }
}
```

## Java Test Case ##

```
public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModule tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssueModule();
        tisp.defineUi();
        tisp.useJQuerySelector();
        tisp.enableSelectorCache();
        tisp.setCacheMaxSize(30);
    }

    @Before
    public void setUp(){
        connectUrl("http://code.google.com/p/aost/issues/list");
    }

    @Test
    public void testSearchIssueTypes(){
        String[] ists = tisp.getIsssueTypes();
        tisp.selectIssueType(ists[2]);
        tisp.searchIssue("Alter");
    }

    @Test
    public void testAllIssues(){
        String[] details = tisp.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }

    @Test
    public void testGetCellCount(){
        int count = tisp.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
        String[] details = tisp.getAllText();
        assertNotNull(details);
        assertEquals(details.length, count);
    }

    @Test
    public void testCSS(){
        tisp.disableSelectorCache();
        String[] css = tisp.getTableCSS("font-size");
        assertNotNull(css);
    }
    
    @Test
    public void checkCacheable(){
        boolean result = tisp.checkamICacheable("issueResult[1][1]");
        assertTrue(result);
        result = tisp.checkamICacheable("issueResult");
        assertTrue(result);
    }

    @Test
    public void testGetDataForColumn(){
        long beforeTime = System.currentTimeMillis();
        tisp.getDataForColumn(3);
        long endTime = System.currentTimeMillis();
        System.out.println("Time noCacheForChildren " + (endTime-beforeTime) + "ms");
        beforeTime = System.currentTimeMillis();
        tisp.getDataForColumnWithCache(3);
        endTime = System.currentTimeMillis();
        System.out.println("Time with all cache " + (endTime-beforeTime) + "ms");
    }

    @Test
    public void testCachePolicy(){
        tisp.setCacheMaxSize(2);
        tisp.useDiscardNewCachePolicy();
        tisp.searchIssue("Alter");
        tisp.getTableCSS("font-size");
        tisp.getIsssueTypes();
        tisp.searchIssue("Alter");
        tisp.setCacheMaxSize(30);
    }

    @After
    public void showCacheUsage(){
        int size = tisp.getCacheSize();
        int maxSize = tisp.getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: ");
        Map<String, Long> usages = tisp.getCacheUsage();
        Set<String> keys = usages.keySet();
        for(String key: keys){
            System.out.println("UID: " + key + ", Count: " + usages.get(key));
        }
    }
}
```

## Data Driven Testing ##

We use Tellurium Issue page as the data driven testing example, we define tests to search issues assigned to a Tellurium team member and use the input file to define which team members we want the result for.

We first define a TelluriumIssuesModule class that extends TelluriumDataDrivenModule class and includes a method "defineModule". In the "defineModule" method, we define UI modules,  input data format, and different tests. The UI modules are the same as defined before for the Tellurium issue page.

The input data format is defined as

```
fs.FieldSet(name: "OpenIssuesPage") {
   Test(value: "OpenTelluriumIssuesPage")
}

fs.FieldSet(name: "IssueForOwner", description: "Data format for test SearchIssueForOwner") {
   Test(value: "SearchIssueForOwner")
   Field(name: "issueType", description: "Issue Type")
   Field(name: "owner", description: "Owner")
}
```

Here we have two different input data formats. The "Test" field defines the test name and the "Field" field define the input data name and description. For example, the input data for the test "SearchIssueForOwner" have two input parameters "issueType" and "owner".

The tests are defined use "defineTest". One of the test "SearchIssueForOwner" is defined as follows,

```
        defineTest("SearchIssueForOwner") {
            String issueType = bind("IssueForOwner.issueType")
            String issueOwner = bind("IssueForOwner.owner")
            int headernum = getCachedVariable("headernum")
            int expectedHeaderNum = getTableHeaderNum()
            compareResult(expectedHeaderNum, headernum)
            
            List<String> headernames = getCachedVariable("headernames")
            String[] issueTypes = getCachedVariable("issuetypes")
            String issueTypeLabel = getIssueTypeLabel(issueTypes, issueType)
            checkResult(issueTypeLabel) {
                assertTrue(issueTypeLabel != null)
            }
            //select issue type
            if (issueTypeLabel != null) {
                selectIssueType(issueTypeLabel)
            }
            //search for all owners
            if ("all".equalsIgnoreCase(issueOwner.trim())) {
                searchForAllIssues()
            } else {
                searchIssue("owner:" + issueOwner)
            }
            ......
        }

    }

```

As you can see, we use "bind" to tie the variable to input data field. For example, the variable "issueType" is bound to "IssueForOwner.issueType", i.e., field "issueType" of the  input Fieldset "IssueForOwner". "getCachedVariable" is used to get variables passed from previous tests and "compareResult" is used to compare the actual result with the expected result.

The input file format looks like

```
OpenTelluriumIssuesPage
## Test Name | Issue Type | Owner
SearchIssueForOwner | Open | all
SearchIssueForOwner | All | matt.senter
SearchIssueForOwner | Open | John.Jian.Fang
SearchIssueForOwner | All |  vivekmongolu
SearchIssueForOwner | All |  haroonzone
```

The actual test class is very simple

```
class TelluriumIssuesDataDrivenTest extends TelluriumDataDrivenTest{


    public void testDataDriven() {

        includeModule org.tellurium.module.TelluriumIssuesModule.class

        //load file
        loadData "src/test/resources/org/tellurium/data/TelluriumIssuesInput.txt"

        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }
}
```

We first define which Data Driven Module we want to load and then read input data file. After that, we read the input data file line by line and execute the appropriate tests defined in the input file. Finally, close the data file.

The test result looks as follows,

```
<TestResults>
  <Total>6</Total>
  <Succeeded>6</Succeeded>
  <Failed>0</Failed>
  <Test name='OpenTelluriumIssuesPage'>
    <Step>1</Step>
    <Passed>true</Passed>
    <Input>
      <test>OpenTelluriumIssuesPage</test>
    </Input>
    <Assertion Value='10' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>2.579049</Runtime>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>2</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>Open</issueType>
      <owner>all</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' Open Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>4.118923</Runtime>
    <Message>Found 10  Open Issues for owner all</Message>
    <Message>Issue: Better way to wait or pause during testing</Message>
    <Message>Issue: Add support for JQuery selector</Message>
    <Message>Issue: Export Tellurium to Ruby</Message>
    <Message>Issue: Add check Alter function to Tellurium</Message>
    <Message>Issue: Firefox plugin to automatically generate UI module for users</Message>
    <Message>Issue: Create a prototype for container-like Dojo widgets</Message>
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and 
             use Maven to build multiple projects</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and not
             look for an IntelliJ project dependency</Message>
    <Message>Issue: Support nested properties for Tellurium</Message>
    <Message>Issue: update versions for extensioin dojo-widget and TrUMP projects</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>3</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>matt.senter</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>4.023694</Runtime>
    <Message>Found 7  All Issues for owner matt.senter</Message>
    <Message>Issue: Add Maven 2 support</Message>
    <Message>Issue: Add Maven support for the new code base with multiple 
             sub-projects</Message>
    <Message>Issue: Cannot find symbol class Selenium</Message>
    <Message>Issue: Need to create Wiki page to explain how to setup Maven and 
             use Maven to build multiple projects</Message>
    <Message>Issue: Need to add tar.gz file as artifact in Maven</Message>
    <Message>Issue: Configure IntelliJ to properly load one maven sub-project and 
             not look for an IntelliJ project dependency</Message>
    <Message>Issue: update versions for extensioin dojo-widget and TrUMP
             projects</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>4</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>Open</issueType>
      <owner>John.Jian.Fang</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' Open Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.542759</Runtime>
    <Message>Found 5  Open Issues for owner John.Jian.Fang</Message>
    <Message>Issue: Better way to wait or pause during testing</Message>
    <Message>Issue: Export Tellurium to Ruby</Message>
    <Message>Issue: Add check Alter function to Tellurium</Message>
    <Message>Issue: Create a prototype for container-like Dojo widgets</Message>
    <Message>Issue: Support nested properties for Tellurium</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>5</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>vivekmongolu</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.521415</Runtime>
    <Message>Found 5  All Issues for owner vivekmongolu</Message>
    <Message>Issue: Create more examples to demonstrate the usage of different UI
             objects</Message>
    <Message>Issue: DslScriptEngine causes NullPointerException when attempt to 
             invoke server.runSeleniumServer() method</Message>
    <Message>Issue: No such property: connector for class: DslTest</Message>
    <Message>Issue: Firefox plugin to automatically generate UI module for users</Message>
    <Message>Issue: Setup Firefox plugin sub-project</Message>
  </Test>
  <Test name='SearchIssueForOwner'>
    <Step>6</Step>
    <Passed>true</Passed>
    <Input>
      <test>SearchIssueForOwner</test>
      <issueType>All</issueType>
      <owner>haroonzone</owner>
    </Input>
    <Assertion Expected='10' Actual='10' Passed='true' />
    <Assertion Value=' All Issues' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.475594</Runtime>
    <Message>Found 5  All Issues for owner haroonzone</Message>
    <Message>Issue: Solve HTTPS certificate problem</Message>
    <Message>Issue: Refactor Tellurium project web site examples using TestNG and 
             put them to the tellurium-testng-java sub-project</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project 
             for NetBeans</Message>
    <Message>Issue: Create a new wiki page on how to create custom tellurium project 
             for IntelliJ</Message>
    <Message>Issue: Table operations for large size one seems to be very slow</Message>
  </Test>
</TestResults>
```

## DSL Test Script ##

In Tellurium, you can create test scripts in pure DSL. Take TelluriumPage.dsl as an example,

```
//define Tellurium project menu
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"],
             group: "true")
{
    //since the actual text is  Project&nbsp;Home, we can use partial match here. 
    //Note "*" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "*Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the Tellurium project search module, which includes an input box
//and two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}

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
type "search.searchbox", "Tellurium Selenium groovy"
click "search.search_project_button"
waitForPageToLoad 30000

type "search.searchbox", "tellurium selenium dsl groovy"
click "search.search_web_button"
waitForPageToLoad 30000
```

To run the DSL script, you should first compile the code with ant script. In project root, run the following ant task

```
ant compile-test
```

Then, use the rundsl.sh to run the DSL script

```
./rundsl.sh src/test/resources/org/tellurium/dsl/TelluriumPage.dsl
```

For Windows, you should use the rundsl.bat script.