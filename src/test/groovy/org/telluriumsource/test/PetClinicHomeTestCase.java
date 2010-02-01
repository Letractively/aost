package org.telluriumsource.test;

import org.telluriumsource.module.PetClinicHome;
import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 1, 2010
 * 
 */
public class PetClinicHomeTestCase extends TelluriumTestNGTestCase {
    private static PetClinicHome home;

    @BeforeClass
    public static void initUi() {
        home = new PetClinicHome();
        home.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
    }

    @BeforeMethod
    public void connectToPetClinic() {

        connectUrl("http://localhost:8080/petclinic");
    }

    @Test
    public void testValidateUiModule(){
        home.validate("Home");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
