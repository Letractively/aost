package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.JettyLogonModule;
import org.telluriumsource.entity.EngineState;
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

        jlm = new JettyLogonModule();
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
    public void testStringifyUiModule(){
        String json = jlm.toString("Form");
        System.out.println(json);
    }

    @Test
    public void testDiagnose(){
        jlm.diagnose("Form.Username.Input");
        jlm.diagnose("ProblematicForm.Username.Input");
    }

    @Test
    public void testValidateUiModule(){
        jlm.validate("Form");
        jlm.validate("ProblematicForm");
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

    @Test
    public void testGetCSS(){
        String[] css = jlm.getCSS("Form.Username.Input", "background-color");
        assertNotNull(css);
        System.out.println("Background color for Form.Username.Input: " + css[0]);
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
        jlm.validate("Logo");
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
        jlm.validate("AbstractForm");
        jlm.diagnose("AbstractForm.Form1.Password.Password1.Label");
        jlm.alogon("test", "test");
    }

    @Ignore
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

    @Test
    public void testGetEngineState(){
        EngineState state = getEngineState();
        assertNotNull(state);
        System.out.println(state.showMe());
    }

    @Test
    public void testUseEngineLog(){
        useEngineLog(true);
        jlm.logon("tellurium", "source");
        useEngineLog(false);
        connect("JettyLogon");
        jlm.logon("tellurium", "testing");
    }

    @Test
    public void testGetHTMLSource(){
        useEngineLog(true);
        useTelluriumApi(true);
        useCache(true);
        jlm.getHTMLSource("Form");
    }

    @Test
    public void testShowUi(){
        useEngineLog(true);
        useTelluriumApi(true);
        useCache(true);
        jlm.show("Form", 5000);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
