package org.tellurium.widget.dojo.test

import org.tellurium.test.java.TelluriumJavaTestCase
import org.tellurium.widget.dojo.module.DatePickerDemo
import org.junit.BeforeClass
import org.junit.Test

/**
 * Test dojo Data picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePicker_FT extends TelluriumJavaTestCase {
    private static DatePickerDemo dpd;

    @BeforeClass
    public static void initUi() {
        dpd = new DatePickerDemo();
        dpd.defineUi();
    }

    @Test
    public void testClickDataPicker(){
        connectUrl("http://turtle.dojotoolkit.org/~dylan/dojo/tests/widget/demo_DatePicker.html");
        dpd.clickWidget();
        dpd.increaseWeek();
    }

}