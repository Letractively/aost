## Logical Container, empty != nothing ##

The Container object in Tellurium is used to hold child objects that are in the same subtree in the DOM object. However, there are always exceptions. For example, logical Container (Or virtual Container, but I think Logical Container is better) can violate this rule.

What is a logic Container? It is a Container with empty locator, for instance,

```
Container(uid: "logical"){

}
```


But empty != nothing, there are some scenarios that the logical container can play an important role. The reason is the Container includes a uid for you to refer and it can logically group UI element together.

### Logically Grouping ###

We can look at two typical examples. The first example comes from Harihara and he wants to use Tellurium List to represent the UI shown as follows,

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

dominicm provided another good example, i.e., a table with multiple tbody elements as follows,

```
<table id="someId">
  <tbody id="tbody1_Id>
     <tr><td></td></tr>
     <tr><td></td></tr>
   <tbody id="tbody2_Id>
      <tr><td></td></tr>
      <tr><td></td></tr>
   <tbody id="tbody3_Id>
       <tr><td></td></tr>
       <tr><td></td></tr>

```

You could use the logical Container to describe it as

```
Container(uid: "tables"){
     Table(uid: "first", clocator: [id: "someId", tbody: [id: "tbody1_Id"]]){
          ......
      }
     Table(uid: "second", clocator: [id: "someId", tbody: [id: "tbody2_Id"]]){
          ......
      }

      ...
}

```

If the tbody IDs are assigned dynamically at runtime, you can use the position attribute instead,

```
Container(uid: "tables"){
     Table(uid: "first", clocator: [id: "someId", tbody: [position: "1"]]){
          ......
      }
     Table(uid: "second", clocator: [id: "someId", tbody: [position: "2"]]){
          ......
      }

      ...
} 

```

Then, you can use "tables.first[i](i.md)[j](j.md)" to reference the first table and "tables.second[i](i.md)[j](j.md)" for the second one and so on.

### Convert Selenium IDE test cases to Tellurium test cases ###

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

Meanwhile, you can also see the huge benefits for Tellurium, i.e., expressiveness and easy to maintain.