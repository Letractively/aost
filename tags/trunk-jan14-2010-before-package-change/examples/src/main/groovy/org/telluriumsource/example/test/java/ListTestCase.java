package org.telluriumsource.example.test.java;

import org.telluriumsource.example.other.ListModule;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 20, 2009
 */
public class ListTestCase  extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/list.html", ListModule.LIST_BODY);
        server.registerHtmlBody("/test.html", ListModule.LIST_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ListModule lm = new ListModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/list.html");
        lm.disableCssSelector();
        String attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        System.out.println("XPath: Class attribute for rotator.tnails[6] " + attr);
        assertEquals("thumbnail potd", attr);
        lm.enableCssSelector();
        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        System.out.println("jQuery: Class attribute for rotator.tnails[6] " + attr);
        assertEquals("thumbnail potd", attr);
        connectUrl("http://localhost:8080/test.html");
        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        System.out.println("jQuery: Class attribute for rotator.tnails[6] " + attr);
        assertEquals("thumbnail potd", attr);
        server.setContentType("text/plain");
        connectUrl("http://localhost:8080/test.html");
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();    
    }
}
