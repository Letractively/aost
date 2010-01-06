package org.telluriumsource.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.module.TelluriumIssueModule;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class TelluriumIssueTestCase extends TelluriumJUnitTestCase {
    private static TelluriumIssueModule tim;

    @BeforeClass
    public static void setup(){
        tim = new TelluriumIssueModule();
        tim.defineUi();
    }

    @Test
    public void testDump(){
        useCssSelector(true);
        tim.dump("issueResult");
    }
}
