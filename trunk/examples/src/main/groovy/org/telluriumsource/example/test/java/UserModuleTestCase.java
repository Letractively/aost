package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.telluriumsource.example.other.UserModule;

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 12, 2009
 *
 */
public class UserModuleTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
//        server.registerHtmlBody("/account.html", UserModule.HTML_BODY);
        server.registerHtml("/mockFunction.do", UserModule.RESP_HEADER);
        server.start();
        connectSeleniumServer();
    }

    @Test
    public void testGetSeparatorAttribute(){
        UserModule lm = new UserModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/mockFunction.do");
        lm.disableCssSelector();
        lm.doCreateAccount();
        connectUrl("http://localhost:8080/mockFunction.do");
        lm.enableCssSelector();
        lm.doCreateAccount();
//        System.out.println("Captured traffic: " + lm.captureNetworkTraffic("json"));
    }

    @Test
    public void testGenerateHTML(){
        UserModule lm = new UserModule();
        lm.defineUi();
        System.out.print(lm.generateHtml("subnav"));
        System.out.print(lm.generateHtml("issueResult"));
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
