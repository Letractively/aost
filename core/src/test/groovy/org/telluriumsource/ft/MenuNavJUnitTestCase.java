package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JettyLogonModule;
import org.telluriumsource.module.MenuNavModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 5, 2010
 */
public class MenuNavJUnitTestCase  extends TelluriumMockJUnitTestCase {
    private static MenuNavModule mnm;
     private static JettyLogonModule jlm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("JettyLogon");
        registerHtmlBody("MenuNav");

        jlm = new JettyLogonModule();
        jlm.defineUi();
        mnm = new MenuNavModule();
        mnm.defineUi();
        useCssSelector(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
//        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
 //       connect("JettyLogon");
    }

    @Test
    public void testToHtml(){
        System.out.println(mnm.toHTML("mainnav"));

    }

    @Test
    public void testFlow(){
        connect("JettyLogon");
        useTelluriumApi(true);
        jlm.click("Welcome.MenuLink");
        jlm.waitForPageToLoad(10000);
        assertTrue("Did not find main menu", mnm.isElementPresent("mainnav"));
        assertFalse(mnm.isElementPresent("mainnav3.Link"));
        useTelluriumApi(false);
        mnm.click("mainnav2.suppliers");
        useTelluriumApi(true);
        mnm.waitForPageToLoad(20000);
        assertTrue(jlm.isElementPresent("welcome.MenuLink"));
    }
      
    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
