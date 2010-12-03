package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JTableModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 15, 2010
 */
public class JTableTestCase extends TelluriumMockJUnitTestCase {
    private static JTableModule jlm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("JTable");

        jlm = new JTableModule();
        jlm.defineUi();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("JTable");
    }

    @Test
    public void validate(){
        jlm.validate("AddMembersThickbox");
    }
    
    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
