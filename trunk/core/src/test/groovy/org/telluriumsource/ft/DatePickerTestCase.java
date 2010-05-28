package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JQueryDatePicker;
import org.telluriumsource.support.JettyFileServer;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: May 27, 2010
 */
public class DatePickerTestCase extends TelluriumJUnitTestCase{
    private static JettyFileServer server;
    
    @BeforeClass
    public static void setup(){
        server = new JettyFileServer(8088, "src/test/resources/webapp");
        server.start();
     }

    @Test
    public void testShowDatePicker(){
        connectSeleniumServer();
        connectUrl("http://localhost:8088/datepicker/datepicker.html");
        useTelluriumEngine(true);
        useEngineLog(true);
        JQueryDatePicker datePicker = new JQueryDatePicker();
        datePicker.defineUi();
        datePicker.click("DatePickerInput");
        datePicker.next();
        datePicker.selectDay(20);
        String date = datePicker.getValue("DatePickerInput");
        System.out.println("Selected Date: " + date);

        datePicker.click("DatePickerInput");
        datePicker.prev();
        datePicker.selectFriday(2);
        date = datePicker.getValue("DatePickerInput");
        System.out.println("Selected Date: " + date);
        date = datePicker.getDate();
        System.out.println("Selected Month and Year: " + date);
    }

    @AfterClass
    public static void tearDown(){
       server.shutDown();
    }
}
