package org.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.module.NewUiModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: jiafan1
 * Date: Feb 15, 2009
 * Time: 11:11:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewTestCase extends TelluriumJavaTestCase {
    private static NewUiModule app;

    @BeforeClass
    public static void initUi() {
        app = new NewUiModule();
        app.defineUi();
    }

    @Before
    public void setUpForTest() {
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testDownloadTypes() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        assertTrue(allTypes[2].contains("Featured Downloads"));
        assertTrue(allTypes[3].contains("Current Downloads"));
        assertTrue(allTypes[4].contains("Deprecated Downloads"));
    }

    @Test
    public void testTelluriumProjectPage() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All Downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }
}
