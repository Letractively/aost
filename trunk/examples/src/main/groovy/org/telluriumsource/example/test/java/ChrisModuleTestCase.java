package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.ChrisModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Jan 6, 2010
 */
public class ChrisModuleTestCase  extends TelluriumJUnitTestCase {
    private static MockHttpServer server;
    static ChrisModule cm;

    @BeforeClass
    public static void setUp() {
        server = new MockHttpServer(8080);
        cm = new ChrisModule();
        cm.defineUi();
        connectSeleniumServer();
        server.registerHtmlBody("/ship.html", ChrisModule.HTML_BODY);
        server.start();
    }

    @Test
    public void testClick() {
        connectUrl("http://localhost:8080/ship.html");
        cm.update();
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
}
