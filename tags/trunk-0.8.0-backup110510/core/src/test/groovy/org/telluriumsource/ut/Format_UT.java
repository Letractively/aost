package org.telluriumsource.ut;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *       
 *         Date: Aug 5, 2010
 */
public class Format_UT {

    @Test
    public void testUicode(){
        String test = "\u004E\u00FC\u0068\u0065\u00F0\u0061\u006E\u0020\u03AC\u03C1\u03C7";
        try {
            String c = new String(test.getBytes(),"UTF-8");
            System.out.println("Converted: " + c);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
