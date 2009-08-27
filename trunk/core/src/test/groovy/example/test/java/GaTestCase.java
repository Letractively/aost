package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import example.other.GaModule;
import example.other.GaGroupModule;

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
        server.registerHtmlBody("/groups.html", GaGroupModule.HTML_BODY);
        server.start();
        connectSeleniumServer();
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
        openUrl("http://localhost:8080/programs.html");
        lm.selectProgram("nice");
    }

    @Test
    public void testGroupLocating(){
        GaGroupModule ggm = new GaGroupModule();
        ggm.defineUi();
        openUrl("http://localhost:8080/groups.html");
        ggm.diagnose("ProgramList.list");
        ggm.useJQuerySelector();
        ggm.diagnose("ProgramList.list");
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
