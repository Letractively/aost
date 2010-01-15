package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.telluriumsource.example.other.EcisModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 19, 2009
 */
public class EcisTestCase extends TelluriumJavaTestCase {
    private static MockHttpServer server;
    static EcisModule em;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8088);
        em = new EcisModule();
        em.defineUi();
        connectSeleniumServer();
        server.registerHtmlBody("/nextgen/login.seam", em.HTML_BODY);
        server.start();
    }

    @Test
    public void testGenerateHtml(){
        System.out.print(em.generateHtml());
    }
    
    @Test
    public void testGetSeparatorAttribute(){
        connectUrl("http://localhost:8088/nextgen/login.seam");
        useCache(true);
        assertTrue(em.checkExpend());
        em.validateUiModule("EcisPlusUiCAV");
        em.diagnose("EcisPlusUiCAV.Save");     
        em.save();
        assertTrue(em.checkExpend());
    }
}
