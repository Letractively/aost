package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.telluriumsource.module.VireoModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 31, 2010
 */
public class VireoTestCase extends TelluriumMockJUnitTestCase {
    private static VireoModule dm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Display");

        dm = new VireoModule();
        dm.defineUi();
    }

    @Before
    public void connectToLocal() {
        connect("Display");
    }

    @Ignore
    @Test
    public void testUIModuleTellurium(){
        useTelluriumEngine(true);
        dm.click("DegreeInformationForm.degree");
    }

    @Ignore
    @Test
    public void testUIModuleSelenium(){
        useTelluriumEngine(false);
        dm.click("DegreeInformationForm.degree");
    }
    
}
