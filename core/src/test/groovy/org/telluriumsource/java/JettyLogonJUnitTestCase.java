package org.telluriumsource.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.module.JettyLogonModule;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 23, 2009
 */
public class JettyLogonJUnitTestCase extends TelluriumJUnitTestCase {
    private static JettyLogonModule jlm;
    private static MockHttpServer server;

    @BeforeClass
    public static void initUi() {
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/logon.html", JettyLogonModule.HTML_BODY);
        server.start();

        jlm = new  JettyLogonModule();
        jlm.defineUi();    
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
        connectUrl("http://localhost:8080/logon.html");
    }

    @Test
    public void testJsonfyUiModule(){
        String json = jlm.jsonify("Form");
        System.out.println(json);
    }

    @Test
    public void testDiagnose(){
        jlm.diagnose("Form.Username.Input");
        jlm.diagnose("ProblematicForm.Username.Input");
    }

    @Test
    public void testValidateUiModule(){
        jlm.validateUiModule("Form");
        jlm.validateUiModule("ProblematicForm");
    }

    @Test
    public void testLogon() {
        jlm.logon("test", "test");
    }

    @Test
    public void testLogonWithClosestMatch() {
        useClosestMatch(true);
        jlm.plogon("test", "test");
        useClosestMatch(false);
    }

    @Test
    public void testToggle(){
        jlm.toggle("Form.Username.Input");
        pause(500);
        jlm.toggle("Form.Username.Input");
    }

    @Ignore
    @Test
    public void testCookies(){
        useTelluriumApi(false);
        jlm.setCookieByJQuery("tellurium", "cool");
        String cookie = jlm.getCookieByJQuery("tellurium");
        assertEquals("cool", cookie);
        jlm.deleteCookieByJQuery("tellurium");
        cookie = jlm.getCookieByJQuery("tellurium");
        assertNull(cookie);
        jlm.setCookieByJQuery("aost", "cool");
        jlm.setCookieByJQuery("tellurium", "great");
        jlm.deleteAllCookiesByJQuery();
        cookie = jlm.getCookieByJQuery("aost");
        assertNull(cookie);
        cookie = jlm.getCookieByJQuery("tellurium");
        assertNull(cookie);
    }
    
    @AfterClass
    public static void tearDown(){
        showTrace();
        server.stop();
    }
}
