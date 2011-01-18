package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.FooModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 17, 2011
 */
public class FootTestCase extends TelluriumMockJUnitTestCase {
    private static FooModule fom;

    @BeforeClass
    public static void initUi() {
        registerHtml("Foo");
        fom = new FooModule();
    }

    @Before
    public void connectToLocal() {
        connect("Foo");
    }

    @Test
    public void execFlow1(){
      fom.click("TheLink");
      pause(10000);
    }

}
