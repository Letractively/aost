package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.GeneralTableModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;

/**
 * Test StandardTable with more general layout
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 22, 2010
 */
public class GeneralTableJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static GeneralTableModule gtm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("GeneralTable");

        gtm = new GeneralTableModule();
        gtm.defineUi();
        useCssSelector(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(false);
    }

    @Before
    public void connectToLocal() {
        connect("GeneralTable");
    }

    @Test
    public void testToJSON(){

        System.out.println(gtm.toJSON("GT"));    
    }

    @Test
    public void testValidateUiModule(){
        gtm.validate("GT");
    }

    @Test
    public void testWork(){
        useCache(false);
        System.out.println(gtm.getText("GT.header[2]"));
        System.out.println(gtm.getText("GT[1][1]"));
        gtm.work("Tellurium jQuery");
    }

    @Test
    public void testWorkWithCache(){
        useCache(true);
        System.out.println(gtm.getText("GT.header[2]"));
        System.out.println(gtm.getText("GT[1][1]"));
        gtm.work("Tellurium jQuery");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
