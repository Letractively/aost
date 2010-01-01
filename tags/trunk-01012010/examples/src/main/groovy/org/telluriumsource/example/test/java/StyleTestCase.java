package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.StyleModule;
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

    @Test
    public void testBackground(){
        StyleModule lm = new StyleModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/style.html");
        String[] style = lm.getCSS("c1", "background");
        assertNotNull(style);
        style = lm.getCSS("c1", "color");
        style = lm.getCSS("c1", "background-color");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }

}
