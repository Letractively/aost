package org.telluriumsource.ft;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TableHeaderModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

public class TableHeaderTestCase extends TelluriumMockJUnitTestCase

    {
    private static TableHeaderModule dgm;

    @BeforeClass
    public static void initUi() {
        registerHtml("TableHeader");

        dgm = new TableHeaderModule();
        dgm.defineUi();

        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("TableHeader");
    }

    @Test
    public void testValidate(){
        dgm.validate("sectionRaces");
    }
}
