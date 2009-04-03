package example.test.java;

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

