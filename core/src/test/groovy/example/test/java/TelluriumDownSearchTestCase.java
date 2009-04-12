package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import example.other.TelluriumDownSearchModule;

public class TelluriumDownSearchTestCase extends TelluriumJavaTestCase {
    private static TelluriumDownSearchModule app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumDownSearchModule();
        app.defineUi();
    }

    @Before
    public void setUpForTest() {
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testDownloadSearch() {
        String[] allTypes = app.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All downloads"));
        app.selectDownloadType(allTypes[1]);
        app.searchDownload("TrUMP");
    }

    @Test
    public void testHelp(){
        app.clickHelp();    
    }

    @Test
    public void testCSS(){
        String[] css = app.getTableCSS("color");
        assertNotNull(css);
    }
}
