package org.telluriumsource.ft;

import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.CheckBoxExample;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

import static org.junit.Assert.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 28, 2010
 */
public class CheckBoxExampleJUnitTestCase extends TelluriumJUnitTestCase
{
    protected static CheckBoxExample cbx;

    @BeforeClass
    public static void initUi() {
        cbx = new CheckBoxExample();
        cbx.defineUi();
    }

    @Test
    public void testCheckOneBox() {
        connectSeleniumServer();
        connectUrl("http://www.asp101.com/samples/checkbox.asp");
        cbx.checkOneBox("root.input0");
        assertEquals("on", cbx.checkBoxValues("root.input0"));
    }
}
