package org.telluriumsource.java;

import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.module.JettyLogonModule;
import org.testng.annotations.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 4, 2010
 */
public class JettyLogonTestNGTestCase extends TelluriumTestNGTestCase {
    
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

    @DataProvider(name = "config-provider")
    public Object[][] configParameters() {
        return new Object[][]{
                new Object[]{true, true, true},
                new Object[]{true, true, false},
                new Object[]{true, false, true},
                new Object[]{true, false, false},
                new Object[]{false, true, true},
                new Object[]{false, true, false},
                new Object[]{false, false, true},
                new Object[]{false, false, false}
        };
    }

    @BeforeMethod
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

    @Test(dataProvider = "config-provider")
    @Parameters({"useSelector", "useCache", "useTeApi"})
    public void testLogon(boolean useSelector, boolean useCache, boolean useTeApi) {
        useCssSelector(useSelector);
        useCache(useCache);
        useTelluriumApi(useTeApi);
        jlm.logon("test", "test");
    }

    @Test(dataProvider = "config-provider")
    @Parameters({"useSelector", "useCache", "useTeApi"})
    public void testLogonWithClosestMatch(boolean useSelector, boolean useCache, boolean useTeApi) {
        useClosestMatch(true);
        useCssSelector(useSelector);
        useCache(useCache);
        useTelluriumApi(useTeApi);
        jlm.plogon("test", "test");
        useClosestMatch(false);
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
        showTrace();
    }
   
}
