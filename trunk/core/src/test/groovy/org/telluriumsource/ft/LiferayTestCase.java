package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.LiferayModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

public class LiferayTestCase extends TelluriumMockJUnitTestCase {
    private static LiferayModule lrm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Liferay");

        lrm = new LiferayModule();
        lrm.defineUi();
        useEngineLog(true);
        //enableLogging(LogLevels.ALL);

//        useCssSelector(true);
//        useTelluriumApi(true);
//        useCache(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("Liferay");
    }

    @Test
    public void testClick(){

        lrm.clickNode();
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}


