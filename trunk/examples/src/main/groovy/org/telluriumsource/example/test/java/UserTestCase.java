package org.telluriumsource.example.test.java;

import org.telluriumsource.example.other.UserModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.dsl.DiagnosisResponse;
import org.telluriumsource.dsl.DiagnosisOption;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 24, 2009
 */
public class UserTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/account.html", UserModule.HTML_BODY);
        server.start();
        connectSeleniumServer();
    }

    @Test
    public void testGetSeparatorAttribute(){
        UserModule lm = new UserModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/account.html");
        lm.disableJQuerySelector();
        lm.doCreateAccount();
        connectUrl("http://localhost:8080/account.html");
        lm.useJQuerySelector();
        lm.doCreateAccount();
    }

    @Test
    public void testTriggerEventOn(){
        UserModule lm = new UserModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/account.html");
        lm.type("accountEdit.accountName", "ccc");
        lm.triggerEventOn("accountEdit.accountName",  "blur");
    }

    @Test
    public void testDiagnose(){
        UserModule lm = new UserModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/account.html");
        DiagnosisResponse resp = lm.getDiagnosisResult("accountEdit.accountName");
        assertNotNull(resp);
        resp.show();

        resp = lm.getDiagnosisResult("accountEdit.save", new DiagnosisOption());
        assertNotNull(resp);
        resp.show();
    }

    @Test
    public void testGenerateHTML(){
        UserModule lm = new UserModule();
        lm.defineUi();
        System.out.print(lm.generateHtml("subnav"));
        System.out.print(lm.generateHtml("issueResult"));
        System.out.print("\nGenerate html for all UI modules\n");
        System.out.print(lm.generateHtml());
    }


    @Test
    public void testDump(){
        UserModule lm = new UserModule();
        lm.defineUi();
        lm.dump("ConsumersPage");
        lm.dump("search");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
