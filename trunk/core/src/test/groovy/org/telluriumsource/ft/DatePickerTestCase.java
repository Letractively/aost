package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JQueryDatePicker;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockFileServer;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: May 27, 2010
 */
public class DatePickerTestCase extends TelluriumJUnitTestCase{
    private static MockFileServer server;
    
    @BeforeClass
    public static void setup(){
        server = new MockFileServer(8088, "src/test/resources/webapp");
        server.start();
     }

    @Test
    public void testShowDatePicker(){
        connectSeleniumServer();
        connectUrl("http://localhost:8088/datepicker/datepicker.html");
        JQueryDatePicker datePicker = new JQueryDatePicker();
        datePicker.defineUi();
        datePicker.click("DatePickerInput");
        datePicker.next();
        datePicker.selectDay(20);
        String date = datePicker.getText("DatePickerInput");
        System.out.println("Selected Date: " + date);
    }

    @AfterClass
    public static void tearDown(){
       server.stop();
    }
}
