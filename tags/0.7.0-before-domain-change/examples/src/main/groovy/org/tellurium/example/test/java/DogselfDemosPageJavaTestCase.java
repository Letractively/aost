package org.tellurium.example.test.java;

import org.tellurium.example.other.DogselfDemosPage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import org.tellurium.test.java.TelluriumJavaTestCase;
import static org.testng.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 14, 2009
 */
public class DogselfDemosPageJavaTestCase extends TelluriumJavaTestCase {

    private static DogselfDemosPage d;

    @BeforeClass
    public static void initUi() {
        d = new DogselfDemosPage();
    }

    @Before
    public void setUpForTest() {
        d.initUi();
        connectUrl("http://dogself.com/telluriumTest/");
    }

    @Test
    public void testHasDisabledAttrValue() {
        String attr = d.getDisabledAttributeHasAttrValue();
        assertTrue(attr != null, "the disabled attribute must exist, it is:" + attr);
    }

    @Ignore
    @Test
    public void testNoDisabledAttrValue() {
        String attr = d.getDisabledNoAttrValue();
        assertTrue(attr != null, "the disabled attribute must not exist, it is:" + attr);
    }

    @Test
    public void testIsDisabled(){
        assertTrue(d.isDisabledNoAttrValue());
        assertTrue(d.isDisabledHasAttrValue());
    }

}
