package org.telluriumsource.ut;

import org.telluriumsource.framework.SessionManager;
import org.telluriumsource.mock.MockSessionFactory;
import org.telluriumsource.module.CaseRecordModule;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class CaseRecord_UT {
    private static CaseRecordModule crm;

    @BeforeClass
    public static void setup(){
        SessionManager.setSession(MockSessionFactory.getNewSession());

        crm = new CaseRecordModule();
        crm.defineUi();
    }

    @Test
    public void testDump(){
        crm.dump("caseRecordPopUp");
    }
}
