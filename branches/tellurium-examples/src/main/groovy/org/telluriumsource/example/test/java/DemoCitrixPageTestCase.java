package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.telluriumsource.example.other.CitrixHomePageUI;
import org.telluriumsource.example.other.LoginModuleUI;
import org.telluriumsource.example.other.HomePageSectionUI;
import org.junit.AfterClass;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Aug 19, 2009
 */
public class DemoCitrixPageTestCase extends TelluriumTestNGTestCase {

    private static CitrixHomePageUI app;
    private static LoginModuleUI LoginUI;
    private static HomePageSectionUI SectionUI;

    @BeforeClass
    public static void initUi() {
        app = new CitrixHomePageUI();
        app.defineUi();
        // Login module
        LoginUI = new LoginModuleUI();
        LoginUI.defineUi();
         // Home Page section UI Links
        SectionUI = new HomePageSectionUI();
        SectionUI.defineUi();
        connectSeleniumServer();

        useCssSelector(true);
        useCache(true);
    }

    @Test
    public void test1() {
        // go to WebSite index page
        //selenium.open("/lang/English/home.asp");
        connectUrl("http://www.citrix.com/lang/English/home.asp");
        // Verify webSite content
        app.waitForPageToLoad(30000);
        assertEquals("Citrix Systems - Virtualization, Networking and Cloud. Simplified.", app.getTitle());

        assertTrue(app.isTextPresent("Products & Solutions"));
        try{
            SectionUI.clickProductsAndSolutions();
        }catch(Exception e){
            System.out.println(e.fillInStackTrace());    
        }
//        assertEquals("Citrix Systems » The Best Application Delivery Solution", app.getTitle());
        // Login to MyCitrix
        connectUrl("http://www.citrix.com/lang/English/home.asp");
        app.clickLogin();
//        connectUrl("http://www.citrix.com/lang/English/home.asp");
        LoginUI.doMyCitrixLogin("a", "b");
        assertTrue(app.isTextPresent("Welcome to My Citrix!"));
        assertTrue(app.isTextPresent("CITRIX TEST ACCOUNT1 >> ID:3003908"));

        // Logout from Mycitrix
        app.clickLogout();
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}

