package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import example.other.UserModule;

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
    }

    @Test
    public void testGetSeparatorAttribute(){
        UserModule lm = new UserModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/mockFunction.do");
        lm.disableJQuerySelector();
        lm.doCreateAccount();
        connectUrl("http://localhost:8080/mockFunction.do");
        lm.useJQuerySelector();
        lm.doCreateAccount();
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
