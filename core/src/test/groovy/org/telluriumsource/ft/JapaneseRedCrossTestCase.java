package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.JapaneseRedCrossModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (jfang@book.com)
 *
 *         Date: 6/1/11
 */
public class JapaneseRedCrossTestCase extends TelluriumJUnitTestCase {
    private static JapaneseRedCrossModule jrm;

    @BeforeClass
    public static void initUi() {
        jrm = new JapaneseRedCrossModule();
        jrm.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void connect() {
        connectUrl("http://www.jrc.or.jp/index.html");
    }

    @Test
    public void testClickLink() {
        jrm.clickLink();
    }
}
