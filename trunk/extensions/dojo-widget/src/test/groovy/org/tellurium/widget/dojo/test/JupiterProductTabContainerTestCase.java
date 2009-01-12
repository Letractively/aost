package org.tellurium.widget.dojo.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.widget.dojo.module.JupiterProductTabContainer;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 12, 2009
 */
public class JupiterProductTabContainerTestCase extends TelluriumJavaTestCase {
    private static JupiterProductTabContainer jptc;

    @BeforeClass
    public static void initUi() {
        jptc = new JupiterProductTabContainer();
        jptc.defineUi();
    }

    @Test
    public void testClickTabs(){
        connectUrl("https://qa.jupiter.jewelry.qa:8083/jupiter/");
        //many steps to the Customer Care app
        jptc.clickTab("Recent");
        jptc.clickTab("Search");
        jptc.clickTab("Turntable");
    }
}
