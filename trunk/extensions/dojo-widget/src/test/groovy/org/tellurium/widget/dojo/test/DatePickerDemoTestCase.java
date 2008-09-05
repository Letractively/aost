package org.tellurium.widget.dojo.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.widget.dojo.module.DatePickerDemo;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.util.List;

/**
 * Test dojo Data picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 4, 2008
 */

public class DatePickerDemoTestCase extends TelluriumJavaTestCase {
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
        dpd.increaseMonth();
        dpd.decreaseMonth();
        dpd.decreaseWeek();
        dpd.selectPrevYear();
        dpd.selectNextYear();
        List days = (List) dpd.peekDaysForCurrentMonth();
        assertNotNull(days);
        assertFalse(days.isEmpty());
        dpd.selectDaysForCurrentMonth((Integer)days.get(0));
        String currentYear = (String) dpd.getCurrentYear();
        String currentMonth = (String) dpd.getCurrentMonth();

    }

}
