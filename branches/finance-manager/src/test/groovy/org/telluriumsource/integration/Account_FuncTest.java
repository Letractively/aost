package org.telluriumsource.integration;

import org.junit.*;
import org.telluriumsource.module.AccountModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Mar 3, 2010
 */
public class Account_FuncTest extends TelluriumJUnitTestCase {
    private static AccountModule acm;
    @BeforeClass
    public static void initUi() {
        acm = new AccountModule();
        acm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToFinanceManager() {
        connectUrl("http://localhost:8080/FinanceManager/account");
    }

    @Test
    public void testCreateAccount(){
        String name = getName();
        String balance = "$10,000";
        String equity = "50%";
        String renewalDate = "03/20/12";
        acm.clear();
        acm.createAccount(name, balance, equity, renewalDate);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }

    protected String getName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String date = dateFormat.format(new Date());

        return "tellurium" + date;
    }
}
