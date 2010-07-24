package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.MultipleCheckModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 23, 2010
 */
public class MultipleCheckTestCase extends TelluriumMockJUnitTestCase {
    private static MultipleCheckModule mcm;
    @BeforeClass
    public static void initUi() {
        registerHtml("MultipleCheck");

        mcm = new MultipleCheckModule();
        mcm.defineUi();
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("MultipleCheck");
    }

    @Test
    public void testDiagnose(){
        mcm.diagnose("Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList[2]");    
    }

    @Test
    public void testCheck(){
        mcm.doSetAllCheckboxes(true);
        mcm.doSetAllCheckboxes(false);
    } 


    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
