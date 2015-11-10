

# Introduction #

One of the most frequently asked questions in [Tellurium user group](http://groups.google.com/group/tellurium-users) is that an element defined in a UI module cannot be located. Of course, you could use [Tellurium Firefox Plugin Trump](http://code.google.com/p/aost/wiki/TrUMP) to create a new UI module, or use DOM inspector to see what happened in the DOM. There are easier ways for this. For example, Tellurium provides a powerful utility: [diagnose](http://code.google.com/p/aost/wiki/TelluriumPowerUtilityDiagnose) for this purpose. The method signature of diagnose is straightforward.

```
public void diagnose(String uid);
```

You only need to pass in the uid of the element that you cannot locate and the method will return two possible result sets:

  * Multiple matched elements, in which case, you need to add more attributes and narrow down your locator.
  * Closest matched elements if no element could be found. Based on the closest matched elements, you can correct your UI module accordingly.

In this article, I am going to show you a practical experience that I used diagnose to fix one UI module.

# Problem #

I have a simple UI module defined for the Google search page as follows,

```
package org.tellurium.module

import org.tellurium.dsl.DslContext

public class GoogleSearchModule extends DslContext {
  public void defineUi() {

    ui.Image(uid: "Logo", clocator: [tag: "img", src: "*/intl/en_ALL/images/logo.gif"])

    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }
  }

  public void doGoogleSearch(String input) {
    keyType "Google.Input", input
    pause 500
    click "Google.Search"
    waitForPageToLoad 30000
  }

  public void doImFeelingLucky(String input) {
    type "Google.Input", input
    pause 500
    click "Google.ImFeelingLucky"
    waitForPageToLoad 30000
  }

  //Test jQuery selector for attributes
  public String getLogoAlt(){
    return getImageAlt("Logo")
  }

  boolean isInputDisabled() {
    return isDisabled("Google.Input")
  }

  public void doTypeRepeated(String input){
    customUiCall "Google.Input", typeRepeated, input
    pause 500
    click "Google.Search"
    waitForPageToLoad 30000
  }
}
```

And the test case is a simple one.

```
public class GoogleSearchJUnitTestCase extends TelluriumJUnitTestCase {
    private static GoogleSearchModule gsm;
    private static String te_ns = "http://tellurium.org/ns";

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useJQuerySelector(true);
        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
    }

    @Before
    public void connectToGoogle() {

        connectUrl("http://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        gsm.doGoogleSearch("tellurium . ( Groovy ) Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testLogo(){
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("Google", alt);
    }

    @Test
    public void testIsDisabled(){
        useJQuerySelector(true);
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        useJQuerySelector(false);
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    @Test
    public void testUseSelectorCache(){
        useCache(true);
        boolean result = gsm.getSelectorCacheState();
        assertTrue(result);

        useCache(false);
        result = gsm.getSelectorCacheState();
        assertFalse(result);
    }


    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
```

When I run the tests, all passed except the `testLogo` test case and I got the following error:

```
Screenshot for exception <<ERROR: Element //descendant-or-self::img[contains(@src,"/intl/en_ALL/images/logo.gif")] not found>> is saved to file Screenshot1260307871472.png

com.thoughtworks.selenium.SeleniumException: ERROR: Element //descendant-or-self::img[contains(@src,"/intl/en_ALL/images/logo.gif")] not found
```


# Diagnose #

What I should do then? I simply put a diagnose command to the `testLogo` test case as follows,

```
    @Test
    public void testLogo(){
        gsm.diagnose("Logo");     //diagnose the problem
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("Google", alt);
    }
```

Run the test case again and I got the following output:

```
Diagnosis Result for Logo

-------------------------------------------------------

	Matching count: 0 


	Parents: 

	--- Parent 1 ---

SKIPPED


	Closest: 


	Closest: 

	--- closest element 1 ---

<img src="/logos/ecsegar09.gif" alt="E.C. Segar's Birthday" title="E.C. Segar's Birthday" id="logo" onload="window.lol&amp;&amp;lol()" border="0" height="145" width="264">

HTML Source: 

SKIPPED

-------------------------------------------------------
```

Yes, indeed, the "Logo" element could not be found, but the diagnose method nicely returned the closest element. See, the logo has been changed. We need to update the UI module accordingly.

# Fix the Problem #

To fix the problem in the UI module, I defined the UI module in a more robust way as follows,

```
ui.Image(uid: "Logo", clocator: [tag: "img", src: "*.gif"])
```

where `*` is a [partial matching symbol](http://code.google.com/p/aost/wiki/UserGuide070AppendixB#How_to_do_Attribute_Partial_Matching_in_Tellurium).

Then, updated the test case as

```
    @Test
    public void testLogo(){
//        gsm.diagnose("Logo");
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("E.C. Segar's Birthday", alt);
    }
```

Cool, all test cases passed this time.

# Summary #

The diagnose utility is really powerful to find runtime problem of your UI module. Please update the Tellurium core artifact to 0.7.0-SNAPSHOT to use this functionality.

# Resources #

  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium Powerful Utility: Diagnose](http://code.google.com/p/aost/wiki/TelluriumPowerUtilityDiagnose)
  * [Tellurium User Guide 0.6.0](http://aost.googlecode.com/files/TelluriumUserGuide.0.6.0.pdf)
  * [Tellurium UI Model Firefox Plugin (TrUMP)](http://code.google.com/p/aost/wiki/TrUMP)