package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.SignUpModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 13, 2010
 */
public class SignUpTestCase extends TelluriumJUnitTestCase {
    private static SignUpModule sum;

    @BeforeClass
    public static void initUi() {
        sum = new SignUpModule();
        sum.defineUi();
        connectSeleniumServer();
//        useEngineLog(true);
//        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToWeb() {

        connectUrl("http://demo.sproutcore.com/signup/");
    }

    @Test
    public void testSignUp(){
        sum.signUp("John", "Smith", "John.Smith@gmail.com");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
