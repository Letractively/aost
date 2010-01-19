package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.JettyLogonModule;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 23, 2009
 */
public class JettyLogonJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static JettyLogonModule jlm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("JettyLogon");

        jlm = new  JettyLogonModule();
        jlm.defineUi();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
        connect("JettyLogon");
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

    @Test
    public void testIsDisabled(){
        useCssSelector(true);
        boolean result = jlm.isDisabled("Form.Username.Input");
        assertFalse(result);
        useCssSelector(false);
        result = jlm.isDisabled("Form.Username.Input");
        assertFalse(result);
        useCssSelector(true);
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

    @Test
    public void testLogo(){
        jlm.validateUiModule("Logo");
        jlm.diagnose("Logo");
        String alt = jlm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("Logo", alt);
    }

    @Test
    public void testSpecialCharacter(){
        String alt = jlm.getImageAlt();
        assertNotNull(alt);
        assertEquals("Image 5", alt);
        jlm.typeImageName("Image 5");
    }

    @Test
    public void testLogicContainer(){
        jlm.validateUiModule("AbstractForm");
        jlm.diagnose("AbstractForm.Form1.Password.Password1.Label");
        jlm.alogon("test", "test");
    }

    @Test
    public void testAddRemoveScript(){
        String script = "var firebug=document.createElement('script');" +
       "firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');" +
       "document.body.appendChild(firebug);" +
       "(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();" +
       "void(firebug);";
        addScript(script, "firebug-litle");
        pause(500);
        removeScript("firebug-litle");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
