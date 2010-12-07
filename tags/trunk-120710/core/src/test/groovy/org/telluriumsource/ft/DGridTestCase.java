package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.DGridModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Nov 10, 2010
 */
public class DGridTestCase extends TelluriumMockJUnitTestCase {
    private static DGridModule dgm;

    @BeforeClass
    public static void initUi() {
        registerHtml("DGrid");

        dgm = new DGridModule();
        dgm.defineUi();
        
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("DGrid");
    }

    @Test
    public void testValidate(){
        dgm.validate("LoginList");
    }

    @Test
    public void testClickHeader(){
        dgm.click("LoginList.header[2]");
//        dgm.waitForPageToLoad(30000);
        pause(2000);
    }

    @Test
    public void testClickElement(){
        dgm.click("LoginList[1][1]");
//        dgm.waitForPageToLoad(30000);
        pause(2000);
    }

    @Test
    public void testGetText(){
        String text = dgm.getText("LoginList[3][2]");
        assertEquals("", text);
        text = dgm.getText("LoginList[3][3]");
        assertEquals("Yes", text);
    }

    @Test
    public void testTableSize(){
        int size = dgm.getTableMaxColumnNum("LoginList");
        assertEquals(3, size);
        size = dgm.getTableMaxRowNum("LoginList");
        assertEquals(3, size);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
