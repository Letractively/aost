package org.telluriumsource.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.module.JettyLogonModule;
import org.junit.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 23, 2009
 */
public class JettyLogonJUnitTestCase extends TelluriumJUnitTestCase {
    private static JettyLogonModule jlm;
    @BeforeClass
    public static void initUi() {
        jlm = new  JettyLogonModule();
        jlm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
    }

    @Before
    public void connectToLocal() {
//        connectUrl("http://localhost:8080/logon.html");
    }

    @Test
    public void testJsonfyUiModule(){
        String json = jlm.jsonify("Form");
        System.out.println(json);
    }

    @Ignore
    @Test
    public void testLogon() {
        connectUrl("http://localhost:8080/logon.html");
        jlm.logon("test", "test");
//        pause(1000);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
