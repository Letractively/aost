package example.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import example.dynarch.DynArchPage;

/**
 * Test case for DynArch Date picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *  Date: Aug 21, 2008
 */
public class DynArchPageJavaTestCase extends TelluriumJavaTestCase {
    private static DynArchPage dap;

    @BeforeClass
    public static void initUi() {
        dap = new DynArchPage();
        dap.defineUi();
    }

    @Test
    public void testClickAbout(){
        connectUrl("http://www.dynarch.com/projects/calendar/");
        dap.clickAbout();
    }
    
}
