package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import example.other.GaModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 13, 2009
 */
public class GaTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/programs.html", GaModule.HTML_BODY);
        server.start();
    }

    @Test
    public void generateHtml(){
        GaModule gm = new GaModule();
        gm.defineUi();
        System.out.println(gm.generateHtml("Program"));
        System.out.println(gm.generateHtml("Programs"));
    }

    @Test
    public void testSelectProgram(){
        GaModule lm = new GaModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/programs.html");
        lm.selectProgram("nice");
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
