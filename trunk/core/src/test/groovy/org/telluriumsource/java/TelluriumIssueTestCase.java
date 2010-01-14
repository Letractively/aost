package org.telluriumsource.java;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.TelluriumIssueModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class TelluriumIssueTestCase extends TelluriumMockJUnitTestCase {
    private static TelluriumIssueModule tim;

    @BeforeClass
    public static void setup(){
        tim = new TelluriumIssueModule();
        tim.defineUi();
        registerHtml("TelluriumIssue");
    }

    @Before
    public void connect(){
        connect("TelluriumIssue");
    }

    @Test
    public void testDump(){
        useCssSelector(true);
        tim.dump("issueResult");
    }
}
