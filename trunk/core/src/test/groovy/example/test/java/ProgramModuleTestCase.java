package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import example.other.ProgramModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2009
 */
public class ProgramModuleTestCase extends TelluriumJavaTestCase

    {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/program.html", ProgramModule.HTML_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ProgramModule pm = new ProgramModule();
        pm.defineUi();
        pm.useJQuerySelector();
        connectUrl("http://localhost:8080/program.html");
        pm.diagnose("Program.triggerBox.trigger");
        pm.click("Program.triggerBox.trigger");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
