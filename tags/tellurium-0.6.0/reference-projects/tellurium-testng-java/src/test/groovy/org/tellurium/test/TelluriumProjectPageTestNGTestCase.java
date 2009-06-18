package org.tellurium.test;

import org.tellurium.test.java.TelluriumTestNGTestCase;
import org.tellurium.module.TelluriumProjectPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
public class TelluriumProjectPageTestNGTestCase extends TelluriumTestNGTestCase{
    private static TelluriumProjectPage app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumProjectPage();
        app.defineUi();
    }

    @BeforeMethod
    public void setUpForTest(){
       connectUrl("http://code.google.com/p/aost/");
    }

    @Test
    public void testTelluriumProjectPage(){
        app.clickDownloads();
        app.clickIssues();
        app.clickSource();
    }

    @Test
    public void testSearchProject(){
        app.clickProjectHome();
        app.searchProject("tellurium");
    }
   
}