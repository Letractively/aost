package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.module.JListModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;

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
        registerHtmlBody("JForm");
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
//        connect("JList");
    }

    @Ignore
    @Test
    public void testGetListSize(){
        connect("JList");
        int size = jlm.getListSize("selectedSailings.outgoingSailings[1].fares");
        System.out.println("List Size " + size);
    }

    @Test
    public void testGetNewListSize(){
        connect("JList");
        int size = jlm.getListSize("Sailings.Fares");
        System.out.println("List Size " + size);

    }

    @Test
    public void testRepeatForXPath(){
        connect("JForm");
        useCssSelector(false);
        int num = jlm.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = jlm.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = jlm.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }

    @Test
    public void testRepeatForSelector(){
        connect("JForm");
        useCssSelector(true);
        int num = jlm.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = jlm.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = jlm.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = jlm.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }
    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
