package org.telluriumsource.example.test.java;

import org.telluriumsource.example.other.TelluriumDownloadPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.test.java.TelluriumJavaTestCase;

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
        ngsp.searchDownload(" All downloads", "TrUMP");
    }
}
