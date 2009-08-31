package example.test.java;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import org.tellurium.test.mock.MockHttpServer;
import org.tellurium.test.java.TelluriumJavaTestCase;
import example.other.GenericTableModule;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Aug 31, 2009
 */
public class GenericTableTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/table.html", GenericTableModule.HTML_BODY);
        server.start();
        connectSeleniumServer();
    }

    @Test
    public void testDump(){
       GenericTableModule gtm = new GenericTableModule();
       gtm.defineUi();
       gtm.dump("table");
    }

    @Test
    public void testDiagnose(){
       GenericTableModule gtm = new GenericTableModule();
       gtm.defineUi();
       connectUrl("http://localhost:8080/table.html");
       gtm.diagnose("table.header[2]");
       gtm.diagnose("table[2][1]");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
