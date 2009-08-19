package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.test.mock.MockHttpServer;
import org.tellurium.dsl.DiagnosisResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import example.other.EcisModule;

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
        server = new MockHttpServer(8080);
        em = new EcisModule();
        em.defineUi();
        server.registerHtmlBody("/nextgen/login.seam", em.generateHtml());
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        connectUrl("http://localhost:8080/nextgen/login.seam");
        assertTrue(em.checkExpend());
        em.diagnose("EcisPlusUiCAV.Save");     
        em.save();
        assertTrue(em.checkExpend());
    }
}
