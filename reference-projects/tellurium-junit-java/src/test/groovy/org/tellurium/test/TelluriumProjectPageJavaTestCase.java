package org.telluriumsource.test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.module.TelluriumProjectPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
public class TelluriumProjectPageJavaTestCase extends TelluriumJavaTestCase{
    private static TelluriumProjectPage app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumProjectPage();
        app.defineUi();
    }

    @Before
    public void setUpForTest(){
       connectUrl("http://code.google.com/p/aost/");
    }

    @Test
    public void testTelluriumProjectPage(){
        app.clickDownloads();
        app.clickWiki();
        app.clickIssues();
        app.clickSource();
    }

    @Test
    public void testSearchProject(){
        app.clickProjectHome();
        app.searchProject("tellurium");
    }

    @Ignore
    @Test
    public void testSeachWeb(){
        app.searchWeb("tellurium selenium dsl groovy");
    }
}