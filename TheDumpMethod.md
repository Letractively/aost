

# Introduction #

Have you ever wondered if you could know the runtime locators that Tellurium generates for your UI module before you actually run your test? This feature is now supported in Tellurium Core by the dump() method.

# The Cool Dump Method #

The dump() method is defined in DslContext as follows,

```
public void dump(String uid)
```

where the _uid_ is the UI object id you want to dump. This method will print out the UI object's and its descendants' runtime locators that Tellurium Core generates for you. As a result, you could inspect the generated xpath or jQuery selector right after you define your UI module and do not need to dig them out from your runtime log. Isn't this cool?

Another cool feature of the dump() method is that it does not require you to run the actual tests, that is to say, you do not need to run selenium server and do not need to launch the web browser. Just simply define the UI module and then call the dump() method.

# How does it work #

When you call the dump() method, Tellurium core will first find the object you want to dump and then traverse the internal object tree to get all locators for the object itself and its descendants.

It would become more complicated if you have UI templates defined in your UI module. Tellurium Core will try to go over all templates and replace the matching-all symbol in the template with an appropriate sample number so that you can see all different scenarios for your UI templates. With that has been said, Tellurium Table, Standard Table, and List  objects will automatically handle the UI templates for you.

# How to Use #

Take Tellurium Issue page UI modules as example,

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

    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
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

  ......

}
```

Once the UI modules are defined, you can write the test cases calling the dump() method on the above UI modules, for instance, we could define a Groovy test class as follows,

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

Note that, you should not call enableSelectorCache() or disableSelectorCache() here, because they need to talk to the Selenium server. As a result, we specify the _exploreSelectorCache_ attribute directly.

Run the tests and you would see the output as follows.

### XPath Locators ###

```
Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: //descendant-or-self::form[@action="list" and @method="get"]
issueSearch.issueType: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::select[@name="can" and @id="can"]
issueSearch.searchLabel: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::span[contains(text(),"for")]
issueSearch.searchBox: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@type="text" and @name="q"]
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------


Dump locator information for issueSearch.searchButton
-------------------------------------------------------
issueSearch.searchButton: //descendant-or-self::form[descendant::select[@name="can" and @id="can"] and descendant::span[contains(text(),"for")] and descendant::input[@type="text" and @name="q"] and descendant::input[@value="Search" and @type="submit"] and @action="list" and @method="get"]/descendant-or-self::input[@value="Search" and @type="submit"]
-------------------------------------------------------


Dump locator information for issueResult
-------------------------------------------------------
issueResult: //descendant-or-self::table[@id="resultstable" and @class="results"]
issueResult.header[1]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[1]/descendant-or-self::*
issueResult.header[2]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[2]/descendant-or-self::a[contains(text(),"ID")]
issueResult.header[3]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[3]/descendant-or-self::a[contains(text(),"Type")]
issueResult.header[4]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[4]/descendant-or-self::a[contains(text(),"Status")]
issueResult.header[5]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[5]/descendant-or-self::a[contains(text(),"Priority")]
issueResult.header[6]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[6]/descendant-or-self::a[contains(text(),"Milestone")]
issueResult.header[7]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[7]/descendant-or-self::a[contains(text(),"Owner")]
issueResult.header[9]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[9]/descendant-or-self::a[contains(text(),"Summary + Labels")]
issueResult.header[10]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::th]/th[10]/descendant-or-self::a[contains(text(),"...")]
issueResult[1][1]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::td][1]/td[1]/descendant-or-self::*
issueResult[1][8]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::td][1]/td[8]/descendant-or-self::*
issueResult[1][10]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::td][1]/td[10]/descendant-or-self::*
issueResult[1][11]: //descendant-or-self::table[descendant::a and @id="resultstable" and @class="results"]/tbody/tr[child::td][1]/td[11]/descendant-or-self::a
-------------------------------------------------------
```

### jQuery Selectors ###

```

Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: jquery=form[method=get]
issueSearch.issueType: jquery=#can
issueSearch.searchLabel: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) span:contains(for)
issueSearch.searchBox: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[type=text][name=q]
issueSearch.searchButton: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]
-------------------------------------------------------


Dump locator information for issueSearch.searchButton
-------------------------------------------------------
issueSearch.searchButton: jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]
-------------------------------------------------------


Dump locator information for issueResult
-------------------------------------------------------
issueResult: jquery=#resultstable
issueResult.header[1]: jquery=#resultstable > tbody > tr:has(th) > th:eq(0)
issueResult.header[2]: jquery=#resultstable > tbody > tr:has(th) > th:eq(1) a:contains(ID)
issueResult.header[3]: jquery=#resultstable > tbody > tr:has(th) > th:eq(2) a:contains(Type)
issueResult.header[4]: jquery=#resultstable > tbody > tr:has(th) > th:eq(3) a:contains(Status)
issueResult.header[5]: jquery=#resultstable > tbody > tr:has(th) > th:eq(4) a:contains(Priority)
issueResult.header[6]: jquery=#resultstable > tbody > tr:has(th) > th:eq(5) a:contains(Milestone)
issueResult.header[7]: jquery=#resultstable > tbody > tr:has(th) > th:eq(6) a:contains(Owner)
issueResult.header[9]: jquery=#resultstable > tbody > tr:has(th) > th:eq(8) a:contains(Summary + Labels)
issueResult.header[10]: jquery=#resultstable > tbody > tr:has(th) > th:eq(9) a:contains(...)
issueResult[1][1]: jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(0)
issueResult[1][8]: jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(7)
issueResult[1][10]: jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(9)
issueResult[1][11]: jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(10) a
-------------------------------------------------------

```

### jQuery Selector Cache ###

```

Dump locator information for issueSearch
-------------------------------------------------------
issueSearch: jquerycache={"unique":true,"locator":"jquery=form[method=get]","optimized":"form[method=get]","uid":"issueSearch","cacheable":true}
issueSearch.issueType: jquerycache={"unique":true,"locator":"jquery=#can","optimized":"#can","uid":"issueSearch.issueType","cacheable":true}
issueSearch.searchLabel: jquerycache={"unique":true,"locator":"jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) span:contains(for)","optimized":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) span:contains(for)","uid":"issueSearch.searchLabel","cacheable":true}
issueSearch.searchBox: jquerycache={"unique":true,"locator":"jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[type=text][name=q]","optimized":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[type=text][name=q]","uid":"issueSearch.searchBox","cacheable":true}
issueSearch.searchButton: jquerycache={"unique":true,"locator":"jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]","optimized":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]","uid":"issueSearch.searchButton","cacheable":true}
-------------------------------------------------------


Dump locator information for issueSearch.searchButton
-------------------------------------------------------
issueSearch.searchButton: jquerycache={"unique":true,"locator":"jquery=form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]","optimized":"form[method=get]:has(#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) input[value=Search][type=submit]","uid":"issueSearch.searchButton","cacheable":true}
-------------------------------------------------------


Dump locator information for issueResult
-------------------------------------------------------
issueResult: jquerycache={"unique":true,"locator":"jquery=#resultstable","optimized":"#resultstable","uid":"issueResult","cacheable":true}
issueResult.header[1]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(0)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(0)","uid":"issueResult.header[1]","cacheable":true}
issueResult.header[2]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(1) a:contains(ID)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(1) a:contains(ID)","uid":"issueResult.header[2]","cacheable":true}
issueResult.header[3]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(2) a:contains(Type)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(2) a:contains(Type)","uid":"issueResult.header[3]","cacheable":true}
issueResult.header[4]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(3) a:contains(Status)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(3) a:contains(Status)","uid":"issueResult.header[4]","cacheable":true}
issueResult.header[5]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(4) a:contains(Priority)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(4) a:contains(Priority)","uid":"issueResult.header[5]","cacheable":true}
issueResult.header[6]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(5) a:contains(Milestone)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(5) a:contains(Milestone)","uid":"issueResult.header[6]","cacheable":true}
issueResult.header[7]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(6) a:contains(Owner)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(6) a:contains(Owner)","uid":"issueResult.header[7]","cacheable":true}
issueResult.header[9]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(8) a:contains(Summary + Labels)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(8) a:contains(Summary + Labels)","uid":"issueResult.header[9]","cacheable":true}
issueResult.header[10]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(th) > th:eq(9) a:contains(...)","optimized":"#resultstable > tbody > tr:has(th) > th:eq(9) a:contains(...)","uid":"issueResult.header[10]","cacheable":true}
issueResult[1][1]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(0)","optimized":"#resultstable > tbody > tr:has(td):eq(0) > td:eq(0)","uid":"issueResult[1][1]","cacheable":true}
issueResult[1][8]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(7)","optimized":"#resultstable > tbody > tr:has(td):eq(0) > td:eq(7)","uid":"issueResult[1][8]","cacheable":true}
issueResult[1][10]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(9)","optimized":"#resultstable > tbody > tr:has(td):eq(0) > td:eq(9)","uid":"issueResult[1][10]","cacheable":true}
issueResult[1][11]: jquerycache={"unique":true,"locator":"jquery=#resultstable > tbody > tr:has(td):eq(0) > td:eq(10) a","optimized":"#resultstable > tbody > tr:has(td):eq(0) > td:eq(10) a","uid":"issueResult[1][11]","cacheable":true}
-------------------------------------------------------

```
