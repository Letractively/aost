package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.telluriumsource.example.other.M1Module;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 13, 2009
 */
public class MaTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/article.html", M1Module.HTML_BODY);
        server.start();
    }

    @Test
    public void testTypeArticleBodyText(){
        M1Module lm = new M1Module();
        lm.defineUi();
        openUrl("http://localhost:8080/article.html");
        lm.typeArticleBodyText("good");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
