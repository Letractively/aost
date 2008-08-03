package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import example.tellurium.TelluriumDownloadsPage;
import example.tellurium.TelluriumProjectPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import java.util.logging.Level;
import java.util.logging.Logger;
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