package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.DatePickerDefaultModule;
import org.telluriumsource.support.JettyFileServer;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *        
 *         Date: May 28, 2010
 */
public class DatePickerTestCase extends TelluriumJUnitTestCase {
    private static JettyFileServer server;

    @BeforeClass
    public static void setUp(){
        server = new JettyFileServer(8088, "WebContent");
        server.start();
        connectSeleniumServer();
        useTelluriumEngine(true);
        useEngineLog(true);
   }

    @Test
    public void testDefaultDatePicker(){
        DatePickerDefaultModule datePicker = new DatePickerDefaultModule();
        datePicker.defineUi();
        connectUrl("http://localhost:8088/demos/datepicker/default.html");
        datePicker.toggleInput();
        datePicker.nextMonth();
        datePicker.selectDay(20);
        String date = datePicker.getSelectedDate();
        System.out.println("Selected Date: " + date);

        datePicker.toggleInput();
        datePicker.prevMonth();
        datePicker.selectFriday(2);
        date = datePicker.getSelectedDate();
        System.out.println("Selected Date: " + date);
        date = datePicker.getMonthYear();
        System.out.println("Selected Month and Year: " + date);
    }

    @AfterClass
    public static void tearDown(){
        if(server != null)
            server.shutDown();
    }
}
