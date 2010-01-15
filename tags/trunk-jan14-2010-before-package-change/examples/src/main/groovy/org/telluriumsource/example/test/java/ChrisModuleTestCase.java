package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.ChrisModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.*;


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
        server.registerHtmlBody("/bread.html", cm.generateHtml("breadcrumb"));
        server.start();
    }

    @Test
    public void testClick() {
        connectUrl("http://localhost:8080/ship.html");
        cm.update();
    }

    @Test
    public void testIsElementPresent(){
        connectUrl("http://localhost:8080/bread.html");
        assertTrue(cm.isElementPresent("breadcrumb[1].urlLink"));
        assertEquals("Home", cm.getText("breadcrumb[1].urlLink"));
    }

    @Test
    public void testGetAttributeWithSelenium(){
        connectUrl("http://localhost:8080/bread.html");
        useCache(false);
        useTelluriumApi(false);
        assertEquals("first", cm.getAttribute("breadcrumb[1]","class"));
    }

    @Test
    public void testGetAttributeWithTellurium(){
        connectUrl("http://localhost:8080/bread.html");
        useCache(true);
        useTelluriumApi(true);
        assertEquals("first", cm.getAttribute("breadcrumb[1]","class"));
        useCache(false);
        useTelluriumApi(false);
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
}
