package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.UrlLinkModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

public class UrlLinkTestCase extends TelluriumMockJUnitTestCase {
    private static UrlLinkModule ulm;

    @BeforeClass
    public static void initUi() {
       registerHtml("UrlLink");

        ulm = new UrlLinkModule();
        ulm.defineUi();
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("UrlLink");
    }

    @Test
    public void validate(){
        ulm.validate("tblLink");
    }

    @Test
    public void testClickUrl(){
        ulm.testLink();
    }

}
