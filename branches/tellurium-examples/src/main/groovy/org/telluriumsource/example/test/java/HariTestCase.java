package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.HariModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 10, 2009
 *
 */
public class HariTestCase extends TelluriumJUnitTestCase {
    private static MockHttpServer server;
    static HariModule hm;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        hm = new HariModule();
        hm.defineUi();
        server.registerHtmlBody("/table.html", hm.generateHtml());
        server.start();
        useTrace(true);
    }

    @Test
    public void testGenerateHtml(){
        System.out.print(hm.generateHtml());
    }

    @Test
    public void testMouseDown(){
        openUrl("http://localhost:8080/table.html");
//        hm.click("dependantsTable");
        hm.check("dependantsTable[1][1]");
        showTrace();
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
