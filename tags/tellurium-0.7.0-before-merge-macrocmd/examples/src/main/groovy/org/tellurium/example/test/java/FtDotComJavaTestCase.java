package org.tellurium.example.test.java;

import org.tellurium.example.other.FtDotCom;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.tellurium.test.java.TelluriumJavaTestCase;

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