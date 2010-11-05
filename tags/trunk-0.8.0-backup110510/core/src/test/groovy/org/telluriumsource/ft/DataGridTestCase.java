package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.DataGridModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <
 * Date: Sep 20, 2010
 */
public class DataGridTestCase extends TelluriumMockJUnitTestCase {
    private static DataGridModule dgm;

    @BeforeClass
    public static void initUi() {
        registerHtml("DataGrid");

        dgm = new DataGridModule();
        dgm.defineUi();
    }

    @Before
    public void connectToLocal() {
        connect("DataGrid");
    }

    @Test
    public void testLink1WithXPath(){
        useCssSelector(false);
        dgm.testLink1();
    }
    
    @Test
    public void testLink1WithCSS(){
        useCssSelector(true);
        dgm.testLink1();
    }

    @Test
    public void testLink1WithNewEngine(){
        useTelluriumEngine(true);
        dgm.testLink1();
        useTelluriumEngine(false);
    }

    @Test
    public void testLink2WithXPath(){
        useCssSelector(false);
        dgm.testLink2();
    }

    @Test
    public void testLink2WithCSS(){
        useCssSelector(true);
        dgm.testLink2();
    }

    @Test
    public void testLink2WithNewEngine(){
        useTelluriumEngine(true);
        dgm.testLink1();
        useTelluriumEngine(false);
    }
    
    @Test
    public void testSubmit1WithXPath(){
        useCssSelector(false);
        dgm.testSubmit1();
    }
    
    @Test
    public void testSubmit2WithXPath(){
        useCssSelector(false);
        dgm.testSubmit2();
    }

    @Test
    public void testSubmit1WithNewEngine(){
        useTelluriumEngine(true);
        dgm.testSubmit1();
        useTelluriumEngine(false);
    }

    @Test
    public void testSubmit2WithNewEngine(){
        useTelluriumEngine(true);
        dgm.testSubmit1();
        useTelluriumEngine(false); 
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
