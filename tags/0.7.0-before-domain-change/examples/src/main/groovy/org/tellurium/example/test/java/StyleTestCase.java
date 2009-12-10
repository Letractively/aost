package org.tellurium.example.test.java;

import org.tellurium.test.java.TelluriumJUnitTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.tellurium.example.other.StyleModule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 9, 2009
 *
 */
public class StyleTestCase  extends TelluriumJUnitTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtml("/style.html", StyleModule.HTML_BODY);
        server.start();
        connectSeleniumServer();
    }

    @Test
    public void testGetSeparatorAttribute(){
        StyleModule lm = new StyleModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/style.html");
        String[] style = lm.getCSS("errorDiv", "color");
        assertNotNull(style);
        for(String st: style){
            System.out.println("\t" + st);
        }
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }

}
