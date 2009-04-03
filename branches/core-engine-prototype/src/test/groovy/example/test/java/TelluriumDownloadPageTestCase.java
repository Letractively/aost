package example.test.java;

import example.other.TelluriumDownloadPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tellurium.test.java.TelluriumJavaTestCase;

/**
 *
 * Example to show how to create Tellurium test case from Selenium recorded test case
 * 
 */
public class TelluriumDownloadPageTestCase extends TelluriumJavaTestCase {

    protected static TelluriumDownloadPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new TelluriumDownloadPage();
        ngsp.defineUi();
    }

    @Test
    public void testSearchDownload(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        ngsp.searchDownload(" All Downloads", "TrUMP");
    }
}
