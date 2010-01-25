package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.GeneralTableModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import org.telluriumsource.util.LogLevels;

import static org.junit.Assert.assertNotNull;

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
        //enableLogging(LogLevels.ALL);

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
    public void testToJSONString(){

        System.out.println(gtm.toString("GT"));
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

    @Test
    public void testGetTableText(){
        useCache(true);
        useTelluriumApi(true);
        String[] texts = gtm.getAllTableCellText("GT");
        assertNotNull(texts);
        System.out.println("Get Table body text for GT");
        for(String text: texts){
            System.out.println(text);
        }
    }

    @Test
    public void testShowUiModule(){
        useCache(true);
        useTelluriumApi(true);
        gtm.show("GT", 2000);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
