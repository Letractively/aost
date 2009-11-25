package org.tellurium.example.test.java;

import org.tellurium.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.tellurium.example.other.CitrixHomePageUI;
import org.tellurium.example.other.LoginModuleUI;
import org.tellurium.example.other.HomePageSectionUI;

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
        app.useJQuerySelector();
        app.enableSelectorCache();
        // Login module
        LoginUI = new LoginModuleUI();
        LoginUI.defineUi();
        LoginUI.useJQuerySelector();
        LoginUI.enableSelectorCache();
        // Home Page section UI Links
        SectionUI = new HomePageSectionUI();
        SectionUI.defineUi();
        SectionUI.useJQuerySelector();
        SectionUI.enableSelectorCache();
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
        SectionUI.clickProductsAndSolutions();
        assertEquals("Citrix Systems » The Best Application Delivery Solution", app.getTitle());
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

}

