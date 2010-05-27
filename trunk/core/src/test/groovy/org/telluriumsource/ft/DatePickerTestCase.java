package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.test.mock.JettyFileServer;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: May 27, 2010
 */
public class DatePickerTestCase {
    private static JettyFileServer server;
    
    @BeforeClass
    public static void setup(){
        server = new JettyFileServer(8088, "src/test/resources/webapp");
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShowDatePicker(){
        int delay = 5000;    
    }

    @AfterClass
    public static void tearDown(){
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
