package org.telluriumsource.test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.util.Helper;
import org.telluriumsource.module.TelluriumDownloadsPage;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests Downloads Page on Tellurium project website
 * 
 * @author Quan Bui (Quan.Bui@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
public class TelluriumDownloadsPageJavaTestCase extends TelluriumJavaTestCase{
   private static TelluriumDownloadsPage downloadPage;

    public static String newline = System.getProperty("line.separator");
    private static Logger logger = Logger.getLogger(TelluriumDownloadsPageJavaTestCase.class.getName());

    @BeforeClass
    public static void initUi() {
        downloadPage = new TelluriumDownloadsPage();
        downloadPage.defineUi();
    }

    @Test
    public void testDownloadTypes(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        String[] allTypes = downloadPage.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All downloads"));
        assertTrue(allTypes[2].contains("Featured downloads"));
        assertTrue(allTypes[3].contains("Current downloads"));
        assertTrue(allTypes[4].contains("Deprecated downloads"));
    }

    @Test
    public void testDefaultDownloadType(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");

        // Navigate away from download page
        connectUrl("http://code.google.com/p/aost/downloads/list");
        String defaultType = downloadPage.getCurrentDownloadType();
        assertNotNull(defaultType);
        assertTrue(defaultType.contains("Current downloads"));
    }

    @Test
    public void testSearchByText(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");
        downloadPage.searchDownload("Tellurium-0.5.0");

        List<String> list = downloadPage.getDownloadFileNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "tellurium-core-0.5.0.tar.gz"));
    }

    @Test
    public void testSearchByLabel(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");
        downloadPage.searchDownload("label:Featured");

        List<String> list = downloadPage.getDownloadFileNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
//        assertTrue(Helper.include(list, "tellurium-core-0.6.0.tar.gz"));
    }

    @Test
    public void testDownloadFileNames(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        int mcolumn = downloadPage.getTableHeaderNum();
        assertEquals(7, mcolumn);
        List<String> list = downloadPage.getHeaderNames();
        assertNotNull(list);
        assertEquals(7, list.size());
        assertTrue(Helper.include(list, "Filename"));
        list = downloadPage.getDownloadFileNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "tellurium-core-0.5.0.tar.gz"));
    }

    @Test
    public void testClickDownload(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickFileNameColumn(1);
    }

    @Test
    public void testClickSummaryLabels(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickSummaryLabelsColumn(1,1);
    }

    @Test
    public void testClickUploaded(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickUploadedColumn(1);
    }

    @Test
    public void testClickSize(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickSizeColumn(1);
    }

    @Test
    public void testClickDownloadedCount(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickDownloadedCountColumn(1);
    }

    @Test
    public void testClickHeader(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
        downloadPage.clickOnTableHeader(2);
        downloadPage.clickOnTableHeader(3);
        downloadPage.clickOnTableHeader(4);
        downloadPage.clickOnTableHeader(5);
        downloadPage.clickOnTableHeader(6);
    }

    /**
     * Debug the items using Logger.log
     *
     */
    private void debug(String[] items)
    {
        if (items != null)
        {
          StringBuffer sb = new StringBuffer();
          for(String item : items) {
            sb.append(item);
            sb.append(newline);
          }
          logger.log(Level.INFO, sb.toString());
        }
    }
}