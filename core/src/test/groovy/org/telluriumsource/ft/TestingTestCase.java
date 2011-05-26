package org.telluriumsource.ft;



import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.LogonModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

import static org.junit.Assert.assertEquals;

public class TestingTestCase extends TelluriumJUnitTestCase {

    private static LogonModule Logon;

    @BeforeClass
    public static void initUi() {
        Logon = new LogonModule();
        connectSeleniumServer();
    }

    @Test
    public void testEnvironment() {
        setCustomConfig(true, 5555, "*firefox", true, "");
        connectUrl("http://localhost/");
        assertEquals("output", "output");
    }
}
