package example;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.module.NewUiModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import example.module.TelluriumDownloadPageModule;


/**
 *   JUnit Test Cases for Tellurium Down page at
 *
 *   http://code.google.com/p/aost/downloads/list
 *
 *
 */
public class TelluriumDownloadPageTestCase extends TelluriumJavaTestCase {
    private static TelluriumDownloadPageModule app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumDownloadPageModule();
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
