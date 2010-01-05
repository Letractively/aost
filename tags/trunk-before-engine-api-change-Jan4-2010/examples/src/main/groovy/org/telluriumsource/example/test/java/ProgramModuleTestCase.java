package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.telluriumsource.example.other.ProgramModule;

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
        connectSeleniumServer();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ProgramModule pm = new ProgramModule();
        pm.defineUi();
        connectUrl("http://localhost:8080/program.html");
        pm.diagnose("Program.triggerBox.trigger");
        pm.click("Program.triggerBox.trigger");        
        pm.enableCssSelector();
        connectUrl("http://localhost:8080/program.html");
        pm.diagnose("Program.triggerBox.trigger");
        pm.click("Program.triggerBox.trigger");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
