package example.test.java;

import example.other.UserModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.tellurium.dsl.DiagnosisResponse;

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
        DiagnosisResponse resp = lm.diagnose("accountEdit.accountName");
        assertNotNull(resp);
        resp.show();

        resp = lm.diagnose("accountEdit.save");
        assertNotNull(resp);
        resp.show();
    }

    @Test
    public void testGenerateHTML(){
        UserModule lm = new UserModule();
        lm.defineUi();
        System.out.print(lm.generateHtml("subnav"));
        System.out.print(lm.generateHtml("issueResult"));
    }

    @Test
    public void testDump(){
        UserModule lm = new UserModule();
        lm.defineUi();
        lm.dump("ConsumersPage");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
