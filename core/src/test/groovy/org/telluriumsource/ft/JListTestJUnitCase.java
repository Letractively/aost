package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.module.JListModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 1, 2010
 */
public class JListTestJUnitCase extends TelluriumMockJUnitTestCase {
   private static JListModule jlm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("JList");

        jlm = new JListModule();
        jlm.defineUi();
//        useCssSelector(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
//        useMacroCmd(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("JList");
    }

    @Ignore
    @Test
    public void testGetListSize(){
        int size = jlm.getListSize("selectedSailings.outgoingSailings[1].fares");
        System.out.println("List Size " + size);
    }

    @Test
    public void testGetNewListSize(){
        int size = jlm.getListSize("Sailings.Fares");
        System.out.println("List Size " + size);

    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
