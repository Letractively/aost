package org.telluriumsource.example.test.java;

import org.telluriumsource.example.other.FtDotCom;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.telluriumsource.test.java.TelluriumJavaTestCase;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 28, 2008
 * 
 */
class FtDotComJavaTestCase extends TelluriumJavaTestCase {
   protected static FtDotCom ft;

    @BeforeClass
    public static void initUi() {
        ft = new FtDotCom();
        ft.defineUi();
        connectSeleniumServer();
    }

    //ignore the test since the web page has been changed
    @Ignore
    @Test
    public void testLogin() {
        connectUrl("http://www.ft.com/home/us");
        ft.selectSubScribeFrame();
        ft.uncheckRememberMe();
        ft.login("test", "password");
    }

}