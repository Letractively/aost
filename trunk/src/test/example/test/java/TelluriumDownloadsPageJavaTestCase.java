package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import example.tellurium.TelluriumDownloadsPage;
import example.tellurium.TelluriumProjectPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
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
    private static TelluriumProjectPage  projectPage;
    private static TelluriumDownloadsPage downloadPage;
    public static String newline = System.getProperty("line.separator");
    private static Logger logger = Logger.getLogger(TelluriumDownloadsPageJavaTestCase.class.getName());
    
    @BeforeClass
    public static void initUi() {
        projectPage = new TelluriumProjectPage();        
        downloadPage = new TelluriumDownloadsPage();
        projectPage.defineUi();
        downloadPage.defineUi();
    }

    @Before
    public void setUpForTest(){
       connectUrl("http://code.google.com/p/aost/");
       
       // Always make sure we are at the Download page before continue our test       
       projectPage.clickDownloads();
    }

    @Test    
    public void testGetAllDownloadTypes(){       
        String[] downloadTypes = downloadPage.getAllDownloadTypes();
        assertNotNull("Failed to retrieve download options.", downloadTypes);         
    }
    
    @Test
    public void testDefaultOptionForDownloadType(){  
        // Set download type with other value
        projectPage.clickDownloads();
        downloadPage.selectDownloadType(" All Downloads");
        
        // Navigate away from download page       
        projectPage.clickProjectHome();        
        projectPage.clickDownloads();
        String current = downloadPage.getCurrentDownloadType();
        assertNotNull("Failed to retrieve current selected download option.", current);         
        assertTrue("Failed to test default value for download types", current.indexOf("Current Downloads") > 0);         
    }

    @Test
    public void testSearchDownload(){
        downloadPage.selectDownloadType(" All Downloads");
        downloadPage.searchDownload("aost");
        downloadPage.pause(3000);
    }

    @Test
    public void testDownloadFileNames(){
        int mcolumn = downloadPage.getTableHeaderNum();
        assertEquals(7, mcolumn);
        List<String> list = downloadPage.getHeaderNames();
        assertNotNull(list);
        assertEquals(7, list.size());
        assertTrue(include(list, "Filename"));
        list = downloadPage.getDownloadFileNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(include(list, "aost-0.3.0.tar.gz"));
    }

    @Test
    public void testClickDownload(){
        downloadPage.clickFileNameColumn(1);
    }

    @Test
    public void testClickSummaryLabels(){
        downloadPage.clickSummaryLabelsColumn(1,1);
    }

    @Test
    public void testClickUploaded(){
        downloadPage.clickUploadedColumn(1);
    }

    @Test
    public void testClickSize(){
        downloadPage.clickSizeColumn(1);
    }

    @Test
    public void testClickDownloadedCount(){
        downloadPage.clickDownloadedCountColumn(1);
    }

    @Test
    public void testClickHeader(){
        downloadPage.clickOnTableHeader(2);
        downloadPage.clickOnTableHeader(3);
        downloadPage.clickOnTableHeader(4);
        downloadPage.clickOnTableHeader(5);
        downloadPage.clickOnTableHeader(6);
    }

    protected boolean include(List<String> list, String name){
        if(list == null || list.isEmpty() || name == null)
            return false;

        for(String elem: list){
            if(name.contains(elem)){
                return true;
            }
        }

        return false;
    }

    /**
     * Debug the items using Logger.log
     * @param items
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