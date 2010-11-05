package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.telluriumsource.module.TableRowModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Nov 2, 2010
 */
public class TableRowTestCase  extends TelluriumMockJUnitTestCase {
    private static TableRowModule trm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("TableRow");

        trm = new TableRowModule();
        trm.defineUi();

        useCssSelector(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("TableRow");
        useTelluriumEngine(false);
    }

    @Test
    public void testWithCSS(){
        int row = trm.getTableMaxRowNum("data");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("data");
        assertEquals(5, colum);
    }

    @Test
    public void testWithEngine(){
        useTelluriumEngine(true);
        int row = trm.getTableMaxRowNum("data");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("data");
        assertEquals(5, colum);
    }

    @Test
    public void testSelfWithCSS(){
        int row = trm.getTableMaxRowNum("data1");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("data1");
        assertEquals(5, colum);
    }

    @Test
    public void testSelfWithEngine(){
        useTelluriumEngine(true);
        int row = trm.getTableMaxRowNum("data1");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("data1");
        assertEquals(5, colum);
    }

    @Test
    public void testGeneralWithCSS(){
        int row = trm.getTableMaxRowNum("Table");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("Table");
        assertEquals(5, colum);
    }

    @Test
    public void testGeneralWithEngine(){
        useTelluriumEngine(true);
        int row = trm.getTableMaxRowNum("Table");
        assertEquals(5, row);
        int colum = trm.getTableMaxColumnNum("Table");
        assertEquals(5, colum);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }

}
