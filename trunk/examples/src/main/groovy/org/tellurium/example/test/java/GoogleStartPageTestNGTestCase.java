package org.telluriumsource.example.test.java;

import org.telluriumsource.example.google.NewGoogleStartPage;
import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

/**
 * Test NG test case
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2008
 */
public class GoogleStartPageTestNGTestCase extends TelluriumTestNGTestCase {
/*    static{
        setCustomConfig(true, 5555, "*chrome", true, null, "localhost");
    }
 */
    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
    }

/*    @BeforeMethod
    public void connectToGoogle(){
       connectUrl("http://www.google.com");
    }*/
    @DataProvider(name = "browser-provider")
    public Object[][] browserParameters() {
        return new Object[][]{new Object[] {"localhost", 4444, "*chrome"},
                new Object[] {"localhost", 4444, "*firefox"}};
    }

    @Test(dataProvider = "browser-provider")
    @Parameters({"serverHost", "serverPort", "browser"})
    public void testGoogleSearch(String serverHost, int serverPort, String browser){
//        String input = ngsp.getConsoleInput();
//        System.out.println("Get Console Input: " + input);
        openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
        disconnectSeleniumServer();
   }

   @Test(dataProvider = "browser-provider")
   @Parameters({"serverHost", "serverPort", "browser"})
   public void testGoogleSearchFeelingLucky(String serverHost, int serverPort, String browser){
       openUrlWithBrowserParameters("http://www.google.com", serverHost, serverPort, browser);
       ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
       disconnectSeleniumServer();
   }
}
