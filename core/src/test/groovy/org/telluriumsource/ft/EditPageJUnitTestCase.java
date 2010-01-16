package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.EditPageModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 16, 2010
 */
public class EditPageJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static EditPageModule epm;

    @BeforeClass
    public static void initUi() {
        setHtmlClassPath("org/telluriumsource/html");
        registerHtmlBody("EditPage");

        epm = new EditPageModule();
        epm.defineUi();
        useCssSelector(true);
        useTelluriumApi(false);
        useTrace(true);
        useCache(true);
        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
        connect("EditPage");
    }

    @Test
    public void testValidateUiModule(){
        epm.validateUiModule("Account");
    }

    @Test
    public void testFillForm(){
        epm.fillForm("tellurium source", "www.telluriumsource.org", 2000.45, "Test");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
