package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JtvAosModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;


/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 21, 2010
 */
public class JtvAosTestCase extends TelluriumJUnitTestCase {
    private static JtvAosModule tam;

    @BeforeClass
    public static void initUi() {
        tam = new JtvAosModule();
        tam.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void connectToAos() {
        connectUrl("http://localhost:8080/automated-ordering-system-support/index.html");
    }


    @Test
    public void testLogin() {
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

