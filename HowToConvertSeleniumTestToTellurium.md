## How to convert selenium test cases to Tellurium test cases ##

### Introduction ###
First, you should be aware that tellurium is UI module-oriented and it focuses on one or a group of objects, while selenium usually focuses on individual objects such as link, button, etc.

How difficult it is to convert existing Selenium test cases to tellurium or you just want to use Selenium tools such as selenium IDE to create telluirum test cases?

The answer is simple. You can keep all locators from selenium test cases but still utilize some good features from Tellurium, for example, the UID to locator mapping, clean UI module, out of box support for JUnit 4, TestNG, and Data Driven Testing.

### Create Selenium Test Case ###
We can use google start page as an example. First, we use Selenium IDE to record some tests and then export them as the following Java code

```
package com.example.tests;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class NewTest extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://www.google.com/", "*chrome");
	}
	public void testNew() throws Exception {
		selenium.open("/");
		selenium.type("//input[@name='q']", "tellurium automated test");
		selenium.click("//input[@name='btnG']");
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='q']", "tellurium automated testing");
		selenium.click("//input[@name='btnI']");
		selenium.waitForPageToLoad("30000");
	}
}
```

### Create Tellurium UI Module ###
The first thing to convert it to tellurium is to write the UI module. Since it is a search UI, you can create a module class GoogleStartPage by extending the DslContext:

```
class GoogleStartPage extends DslContext{

}
```

Then, look at the all the UI objects inside the selenium test and group them into different functional modules. Here we only have one search UI, which includes an input box and two buttons. Let us list their locators out,
```
search UI:
  input_box: "//input[@name='q']"
  google_search_button: "//input[@name='btnG']"
  imfelling_lucky_button: "//input[@name='btnI']"
```

The locators can be used directly in Tellurium and the Tellurium base locator "locator" is for this purpose.

Tellurium supports nested UI objects, but be aware that all the nested UI objects only use relative xpaths in Tellurium so that the actual xpath will be generated at run time. For Selenium, the XPaths are fixed once they are created, as a result, you have two options here

  1. Create a container with empty locator and all the objects inside it are flat. For example, we can create the UI module as follows,
```
ui.Container(uid: "GooglePage"){
   InputBox(uid: "InputBox", locator: "//input[@name='q']")
   Button(uid: "GoogleSearch", locator: "//input[@name='btnG']")
   Button(uid: "FeelingLucky", locator: "//input[@name='btnI']")
}
```
  1. Create individual objects such as
```
InputBox(uid: "InputBox", locator: "//input[@name='q']")
Button(uid: "GoogleSearch", locator: "//input[@name='btnG']")
Button(uid: "FeelingLucky", locator: "//input[@name='btnI']")
```

The first one is recommended since it clusters the objects into functional modules. The other advantage is that it reduces the namespace collisions. For example, the input box in 1) is referred by "GooglePage.InputBox" and the one in 2) will be referred by "InputBox". What happens if you have many InputBoxs?

The obvious advantage of the UI module is that you can use UID to refer objects, such as "GooglePage.InputBox", not the xpath itself. This way is much more intuitive. Compare the following two code sections. The first one is the original Selenium test code
```
selenium.type("//input[@name='q']", "tellurium automated testing");
selenium.click("//input[@name='btnG']");
selenium.waitForPageToLoad("30000");
selenium.type("//input[@name='q']", "tellurium automated testing");
selenium.click("//input[@name='btnI']");
selenium.waitForPageToLoad("30000");
```

The second one is the Tellurium style code
```
type "GooglePage.InputBox", "tellurium automated testing"
click "GooglePage.GoogleSearch"
waitForPageToLoad 30000
type "GooglePage.InputBox", "tellurium automated testing"
click "GooglePage.FeelingLucky"
waitForPageToLoad 30000
```
See the differences, right?

### Test Methods ###
After the UI module is created, you should define test method inside the module class. Here, we have two methods, one is use "GoogleSearch" button to search, the other one is to use "FeelingLucky". Hence, we have

```
   public void doGoogleSearch(String input){
       type "GooglePage.InputBox", input
       click "GooglePage.GoogleSearch"
       waitForPageToLoad 30000    
   }
  
    public void doFeelingLucky(String input){
        type "GooglePage.InputBox", input
        pause 500
        click "GooglePage.FeelingLucky"
        waitForPageToLoad 30000    
    }
```

By defining test methods in the module class, you can keep re-using them. Compare the following code
```
   selenium.type("//input[@name='q']", "tellurium automated testing");
   selenium.click("//input[@name='btnG']");
   selenium.waitForPageToLoad("30000");
   selenium.type("//input[@name='q']", "tellurium automated testing");
   selenium.click("//input[@name='btnI']");
   selenium.waitForPageToLoad("30000");
```
with the Tellurium code
```
   doGoogleSearch("tellurium automated testing")
   doFeelingLucky("tellurium automated testing")
```

### UI Module Class ###
Once the above two methods are defined, we have the UI module class as follows,

```
class GoogleStartPage extends DslContext{
   public void defineUi() {
        ui.Container(uid: "GooglePage"){
            InputBox(uid: "InputBox", locator: "//input[@name='q']")
            Button(uid: "GoogleSearch", locator: "//input[@name='btnG']")
            Button(uid: "FeelingLucky", locator: "//input[@name='btnI']")
        }
   }
 
   public void doGoogleSearch(String input){
        type "GooglePage.InputBox", input
        click "GooglePage.GoogleSearch"
        waitForPageToLoad 30000    
   }
  
   public void doFeelingLucky(String input){
        type "GooglePage.InputBox", input
        click "GooglePage.FeelingLucky"
        waitForPageToLoad 30000    
   }
}
```

### Create Tellurium Test Case ###

The next step will be to create the Tellurium test case and use the UI module defined in the GoogleStartPage class by extending TelluriumJavaTestCase if you want to use JUnit 4 or TelluriumTestNGTestCase for TestNG. The test case is very simple.
```
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase {

    protected static GoogleStartPage gsp;

    @BeforeClass
    public static void initUi() {
        gsp = new GoogleStartPage();
        gsp.defineUi();
    }

    @Test
    public void testGoogleSearch() {
        connectUrl("http://www.google.com");
        gsp.doGoogleSearch("tellurium automated testing");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        connectUrl("http://www.google.com");
        gsp.doFeelingLucky("tellurium automated testing");
    }
}
```

Then you are done.

### Summary ###

By the above steps, you can use Selenium locators directly in Tellurium but take advantage of Tellurium's UID reference, clean UI module, and JUnit/TestNG support. If you want to use more advanced Tellurium features, please check the details from Tellurium project web site

[http://code.google.com/p/aost/](http://code.google.com/p/aost/)

and post your questions to Tellurium user group

[http://groups.google.com/group/tellurium-users](http://groups.google.com/group/tellurium-users)

Thanks.