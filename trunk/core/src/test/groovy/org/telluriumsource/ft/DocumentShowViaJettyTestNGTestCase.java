package org.telluriumsource.ft;

import org.telluriumsource.module.DocumentShowModule;
import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class DocumentShowViaJettyTestNGTestCase extends TelluriumTestNGTestCase {
   private static DocumentShowModule dsm;
   private static MockHttpServer server;

   @BeforeClass
   public static void initUi() {
       server = new MockHttpServer(8080);
       server.registerHtmlBody("/documentShow.html", DocumentShowModule.HTML_BODY);
       server.start();

       dsm = new  DocumentShowModule();
       dsm.defineUi();
       connectSeleniumServer();
//       useCssSelector(true);
       useTelluriumEngine(true);
       useEngineLog(true);
       useTrace(true);
   }

   @BeforeMethod
   public void connectToLocal() {
       connectUrl("http://localhost:8080/documentShow.html");
   }

   @Test
   public void testDocumentShow(){
       dsm.printDetailGroupTitles(1);
       dsm.printDetailGroupTitles(2);
   }

   @Test
   public void testGetCellTextForDetailGroup(){
       dsm.getCellTextForDetailGroup(1);  
       dsm.getCellTextForDetailGroup(2);
   }

   @AfterClass
   public static void tearDown(){
       showTrace();
       server.stop();
   }
}
