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
    public void connectToGoogle() {
        connectUrl("http://localhost:8080/logon.html");
    }

    @Ignore
    @Test
    public void testLogon() {
        jlm.logon("test", "test");
        pause(1000);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
