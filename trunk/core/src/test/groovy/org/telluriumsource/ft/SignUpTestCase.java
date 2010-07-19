package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.module.NewSignUpModule;
import org.telluriumsource.module.SignUpModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 13, 2010
 */
public class SignUpTestCase extends TelluriumJUnitTestCase {

    @BeforeClass
    public static void initUi() {
        connectSeleniumServer();
        useEngineLog(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToWeb() {

        connectUrl("http://demo.sproutcore.com/signup/");
    }

    @Test
    public void testSignUp(){
        SignUpModule sum = new SignUpModule();
        sum.defineUi();
        sum.signUp("John", "Smith", "John.Smith@gmail.com");
    }

    @Ignore
    @Test
    public void testSignUpWithRespond(){
        NewSignUpModule sum = new NewSignUpModule();
        sum.defineUi();
        sum.signUp("John", "Smith", "John.Smith@gmail.com");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
