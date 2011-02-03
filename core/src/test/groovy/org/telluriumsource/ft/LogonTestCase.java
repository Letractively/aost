package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.module.LogonModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 *         Date: Jul 15, 2010
 */
public class LogonTestCase extends TelluriumMockJUnitTestCase {
    private static LogonModule lm;

    @BeforeClass
    public static void initUi() {
       registerHtml("Logon");

        lm = new LogonModule();
        lm.defineUi();
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("Logon");
    }

    @Test
    public void validate(){
        lm.validate("createAccount");
    }

    @Test
    public void diagnose(){
        lm.diagnose("createAccount.email");
        lm.diagnose("createAccount.newPassword");
        lm.diagnose("createAccount.newPassword2");
        lm.diagnose("createAccount.firstName");
        lm.diagnose("createAccount.lastName");
    }

    @Test
    public void testClearTextWithSelenium(){
        useTelluriumEngine(false);
        lm.clearForm();
    }

    @Test
    public void testClearTextWithTellurium(){
        useTelluriumEngine(true);
        useEngineLog(true);
        lm.clearForm();
    }

    @Ignore
    @Test
    public void testAttachFile(){
        useTelluriumEngine(false);
        lm.attachFile("selectFile", "http://www.gotdogsonline.com/chinese-foo-dog-pictures-breeders-puppies-rescue/pictures/chinese-foo-dog-0003.jpg");
    }

    @AfterClass
    public static void tearDown() {
        showTrace();
    }
}
