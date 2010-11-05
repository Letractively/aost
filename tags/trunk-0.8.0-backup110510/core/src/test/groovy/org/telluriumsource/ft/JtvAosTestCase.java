package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JtvAosModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;


/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 21, 2010
 */
public class JtvAosTestCase extends TelluriumMockJUnitTestCase {
    private static JtvAosModule tam;

    @BeforeClass
    public static void initUi() {
        registerHtml("XmlDoc");
        tam = new JtvAosModule();
        tam.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void connectToAos() {
//        connectUrl("http://localhost:8080/automated-ordering-system-support/index.html");
        connect("XmlDoc");
    }


    @Test
    public void testLoginWithSelenium() {
        useTelluriumEngine(false);
        tam.login("superbob", "P@ssw0rd");
        String xml = tam.getResponse();
        System.out.println("Response XML: " + xml);
        String ticketId = tam.getServiceTicket(xml);
        System.out.println("TicketId: " + ticketId);
        tam.selectParentFrameFrom("ivrTestResults");
    }

    @Test
    public void testLoginWithTellurium() {
        useTelluriumEngine(true);
        useClosestMatch(true);
//        tam.selectFrame("ivrTestConfig");
        tam.diagnose("ivrTestConfig.Form.Submit");
        tam.validate("ivrTestConfig");
        tam.login("superbob", "P@ssw0rd");
        String xml = tam.getResponse();
        System.out.println("Response XML: " + xml);
        String ticketId = tam.getServiceTicket(xml);
        System.out.println("TicketId: " + ticketId);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}

