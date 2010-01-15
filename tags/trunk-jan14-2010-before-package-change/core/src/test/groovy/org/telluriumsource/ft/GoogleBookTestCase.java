package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.module.GoogleBookModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class GoogleBookTestCase extends TelluriumJUnitTestCase {
    private static GoogleBookModule gbm;
    private static MockHttpServer server;

    @BeforeClass
    public static void initUi() {
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/gbook.html", GoogleBookModule.HTML_BODY);
        server.start();

        gbm = new  GoogleBookModule();
        gbm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
        connectUrl("http://localhost:8080/gbook.html");
    }

    @Test
    public void testDump(){
        gbm.dump("GoogleBooksList");
    }
    
    @Test
    public void testDiagnose(){
        gbm.diagnose("GoogleBooksList.subcategory[1]");
        gbm.diagnose("GoogleBooksList.subcategory[1].title");
        gbm.diagnose("GoogleBooksList.subcategory[1].links[1]");
        gbm.diagnose("GoogleBooksList.subcategory[2]");
    }

    @Test
    public void testValidateUiModule(){
        gbm.validateUiModule("GoogleBooksList");
    }
   
    @AfterClass
    public static void tearDown(){
        showTrace();
        server.stop();
    }
}
