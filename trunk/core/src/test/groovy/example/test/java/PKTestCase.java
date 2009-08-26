package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import example.other.PkModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Aug 21, 2009
 */
public class PKTestCase  extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/pk.html", PkModule.HTML_BODY);
        server.start();
    }

    @Test
    public void testInputBox(){
        PkModule pkm = new PkModule();
        pkm.defineUi();
        openUrl("http://localhost:8080/pk.html");
        pkm.diagnose("input0");
        pkm.type("input0", "test");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
