package org.telluriumsource.integration;

import org.telluriumsource.module.AccountModule;
import org.telluriumsource.module.LoginModule;
import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Mar 3, 2010
 */
public class FinanceManager_FuncTest extends TelluriumTestNGTestCase {
    private static LoginModule lnm;
    private static AccountModule acm;
    @BeforeClass
    public static void initUi() {
        lnm = new LoginModule();
        lnm.defineUi();
        acm = new AccountModule();
        acm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @BeforeMethod
    public void connectToFinanceManager() {

    }

    @Test
    public void testLogin(){
        connectUrl("http://localhost:8080/FinanceManager/login.jsp");
        String username = "super@admin.com";
        String password = "admin";
        lnm.login(username, password);
    }

    @Test(enabled=false)
    public void testCreateAccount(){
        connectUrl("http://localhost:8080/FinanceManager/account");
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
