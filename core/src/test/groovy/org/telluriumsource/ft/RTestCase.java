package org.telluriumsource.ft;

import org.telluriumsource.module.RListModule;
import org.telluriumsource.test.java.TelluriumMockTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 20, 2010
 */
public class RTestCase extends TelluriumMockTestNGTestCase {

       private static RListModule listUi;

       @BeforeClass
       public static void initUi() {
               registerHtmlBody("RList");
               listUi = new RListModule();

               listUi.defineUi();

               connectSeleniumServer();

               useCssSelector(true);

               useTelluriumEngine(true);

               useTrace(true);
       }

       @BeforeMethod
       public void connectUrl() {
            connect("RList");
       }

       @Test
       public void testAA() {
//              useCssSelector(false);
//
//              listUi.dump("A.AA");
//
//              String html = listUi.toHTML();
//
//              System.out.println(html);

               listUi.click("A.AA");
       }

       @Test
       public void testDD() {
//              useCssSelector(false);
//
//              listUi.dump("A.DD");
//
//              String html = listUi.toHTML();
//
//              System.out.println(html);

               listUi.click("A.DD");
       }
}
