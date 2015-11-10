## Basic Examples ##

### Google Start Page ###

Tellurium is compatible with Selenium style locators if users define UI modules using Base Locators and only including one-level UiObjects which usually are inside a container. For example, the Google start page can be expressed as:

```
ui.Container(uid: "google_start_page"){
    InputBox(uid: "inputbox", locator: "//input[@title='Google Search']")
    Button(uid: "button", locator: "//input[@name='btnG' and @type='submit']")
}
```

But Tellurium provides you the advantage to refer Ui objects using their UiIDs, for example, the InputBox can be referred as "google\_start\_page.inputbox" instead of the urly XPath expression "//input[@title='Google Search']".


However, it is highly recommended to use composite locators because they are more robust and easier to write for non-XPath experts. The Google start page can be expressed using clocator as follows with searching actions included,

```
class NewGoogleStartPage extends DslContext{
    public void defineUi() {
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
            InputBox(uid: "searchbox", clocator: [title: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
        }
    }

    def doGoogleSearch(String input){
        type "searchbox", input
        pause 500
        click "googlesearch"
        waitForPageToLoad 30000
    }

    def doImFeelingLucky(String input){
        type "searchbox", input
        pause 500
        click "Imfeelinglucky"
        waitForPageToLoad 30000
    }
}
```

Then, write the JUnit test case as follows,

```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase {

    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
    }

    @Test
    public void testGoogleSearch(){
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
   }

   @Test
   public void testGoogleSearchFeelingLucky(){
        connectUrl("http://www.google.com");
        ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
   }
}

```

### Google Code Hosting Page ###

The google code hosting page includes nested tables to show labels for
projects. Tellurium supports nested IDs. For example XXX.YYY.ZZZ represents the fact that
the UI object YYY is inside XXX and it holds the ZZZ Ui object. Usually
the id of the UI object is the one you specified in the ui.Container block.
The exceptions are tables and lists, which use `[x][y] or [x]` to reference
its elements inside.

The Ui module for the label tables is defined as

```
ui.Table(uid: "labels_table", clocator: [:], group: "true"){
    //Here we defined templates for the elements of the table
    //id: "row: 1, column: 1" means the TextBox is a template for
    //row 1, column 1.
    TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
    //inner table, which is a template for (row 2, column 1) of the outer table
    Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        //"all" means this is the template for all table elements
        UrlLink(uid: "all", locator: "/a")
   }
}

```

The UI objects defined inside a table are actually templates for table elements.
Note the ids of the templates must follow the name convention:

  1. Template for (i row, j column), the id should be "row: i, column: j"
  1. The wild case (i.e., match all) for row or column is `"*"`, or you do not specify the row or column. For example, `"row : *, column: 3"` means template for (the 3rd column, all rows). "column: 3" means the same thing


  1. "all" is used for matching all rows and columns

If no template is defined for a (row, column), it is assumed to be a TextBox by default.

When you look for an element's (row i, column j) Object-type in run-time, the following
rules apply:

  1. First, the system will try to find the template defined for (row i, column j),return it if found.
  1. Otherwise, the system will try the template for (all rows, column j), return it if found.
  1. If not, the system will try the template for (row i, all columns), return it if found.
  1. If still cannot find, the system will try the template for "all", return it if found.
  1. Last, if no template could be found. Return the TextBox as the default template.

The module functions are defined as follows,

```

    public String getModuleLabel(){
        getText "labels_table[1][1]"
    }

    public int getLabelTableRowNum(){
        getTableMaxRowNum "labels_table[2][1]"
    }

    public int getLabelTableColumnNum(){
        getTableMaxColumnNum "labels_table[2][1]"
    }

    def getTableElement(int row, int column){
        getUiElement "labels_table[2][1].[${row}][${column}]"
    }

    Map getAllLabels(){
        int nrow = getTableMaxRowNum("labels_table[2][1]")
        int ncolumn = getTableMaxColumnNum("labels_table[2][1]")

        def map = [:]
        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
                String label = getText("labels_table[2][1].[${i}][${j}]")
                map.put(label, [i, j])
            }
        }

        return map
    }

    void clickOnLable(int row, int column){
        click  "labels_table[2][1].[${row}][${column}]"
        waitForPageToLoad 30000 
    }

    String getUrlLink(int row, int column){

        getLink("labels_table[2][1].[${row}][${column}]")
    }

```

The actual test case is

```

  public class GoogleCodeHostingJavaTestCase extends TelluriumJavaTestCase {
    private static NewGoogleCodeHosting ngch;


    @BeforeClass
    public static void initUi() {
        ngch = new NewGoogleCodeHosting();
        ngch.defineUi();
    }

    @AfterClass
    public static void setUpAfterClass(){

    }
    
    @Before
    public void setUpBeforeTest(){
        connectUrl("http://code.google.com/hosting/");
    }

    @After
    public void tearDownAfterTest(){

    }

    @Test
    public void testCodeLabelTable(){
        String label = ngch.getModuleLabel();
        assertEquals("Example project labels:", label);

        int nrow = ngch.getLabelTableRowNum();
        assertEquals(3, nrow);

        int ncolumn = ngch.getLabelTableColumnNum();
        assertEquals(6, ncolumn);

        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
               Object obj = ngch.getTableElement(i, j);
               assertTrue(obj instanceof UrlLink);
            }
        }
    }

    @Test
    public void testClickOnLabel(){
        Map map = ngch.getAllLabels();
        assertEquals(18, map.size());

        List index = (List) map.get("Java");
        assertNotNull(index);
        assertEquals(2, index.size());

        //find the url link
        int first = (Integer)index.get(0);
        int second = (Integer)index.get(1);
        String url = ngch.getUrlLink(first, second);
        assertEquals("search?q=label%3aJava", url);

        //click on "Java" link
        ngch.clickOnLable(first, second);

    }
```

### Google Book List Page ###

The Google Book List test case is used to demonstrate the List UI object.
The List UI object likes the Table, but it is one dimension. For example, you
need to use `list1[2]` format to represent the UI object id inside the list.
Also, the List comes with an option "separator", which is used to indicate the delimiter for different elements in the list.

The portion of book category "Fiction" is chosen as our demo, which is defined as:
```
ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true"){
   TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
   List(uid: "subcategory", clocator: [tag: "div", class:"sub_cat_section"], separator: "p"){
      UrlLink(uid: "all", clocator: [:])
   }
}

```

Similar to the Table example, the UrlLink inside the List "subcategory" defines a template
for elements in the List. ID "all" means it is for all elements. Otherwise, you should specify the index of the element that template applies for. When the system tries to find the UI object for `List[x]` in runtime, it first checks if there is a template for index `x`, if not, it then tries the "all" template. If no template found, it returns a TextBox as the default object.

The actions in the module is defined as
```
    String getCategory(){
        getText "GoogleBooksList.category"
    }

    int getListSize(){
        getListSize "GoogleBooksList.subcategory"
    }

    def getAllObjectInList(){
        int size = getListSize()
        List list = new ArrayList()
        for(int i=1; i<=size; i++){
           list.add(getUiElement("GoogleBooksList.subcategory[${i}]"))
        }

        return list
    }

    def clickList(int index){
        click "GoogleBooksList.subcategory[${index}]"
        pause 5000
    }

    String getText(int index){
        getText "GoogleBooksList.subcategory[${index}]"
    }

```

And the test case is as follows,

```
public class GoogleBooksListJavaTestCase extends TelluriumJavaTestCase {
    private static NewGoogleBooksList ngbl;

    @BeforeClass
    public static void initUi() {
        ngbl = new NewGoogleBooksList();
        ngbl.defineUi();
    }

    @Test
    public void testBookCategory(){
        connectUrl("http://books.google.com/");
        String category = ngbl.getCategory();
        assertEquals("Fiction", category);

        int size = ngbl.getListSize();

        assertEquals(8, size);           
    }
}

```