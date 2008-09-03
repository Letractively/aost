package example.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.util.Helper;
import example.tellurium.TelluriumWikiPage;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests Wiki Page on Tellurium project website
 * 
 * @author Quan Bui (Quan.Bui@gmail.com)
 *
 * Date: Aug 6, 2008
 *
 */
public class TelluriumWikiPageJavaTestCase extends TelluriumJavaTestCase{    
    private static TelluriumWikiPage wikiPage;
    
    public static String newline = System.getProperty("line.separator");
    private static Logger logger = Logger.getLogger(TelluriumWikiPageJavaTestCase.class.getName());
    
    @BeforeClass
    public static void initUi() {                
        wikiPage = new TelluriumWikiPage();        
        wikiPage.defineUi();
    }   

    @Test    
    public void testPageTypes(){      
        connectUrl("http://code.google.com/p/aost/w/list");
        String[] types = wikiPage.getSearchOptions();
        assertNotNull(types);         
        assertTrue(types[1].contains("All Wiki Pages"));
        assertTrue(types[2].contains("Featured Pages"));        
        assertTrue(types[3].contains("Current Pages"));        
        assertTrue(types[4].contains("Deprecated Pages"));
    }  

    @Test
    public void testSearchPageByText(){  
        connectUrl("http://code.google.com/p/aost/w/list");
        // Set download type with other value
        wikiPage.selectSearchType(" All Wiki Pages");
        wikiPage.searchForKeyword("Tutorial");
        
        List<String> list = wikiPage.getPageNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "Tutorial"));
    }
    
    @Test
    public void testSearchByLabel(){  
        connectUrl("http://code.google.com/p/aost/w/list");
        // Set download type with other value
        wikiPage.selectSearchType(" All Wiki Pages");
        wikiPage.searchForKeyword("label:Featured");
        
        List<String> list = wikiPage.getPageNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "FAQ"));        
        assertTrue(Helper.include(list, "Tutorial"));
        assertTrue(Helper.include(list, "Introduction"));        
    }
      
    @Test
    public void testClickPageName(){
        connectUrl("http://code.google.com/p/aost/w/list");
        wikiPage.clickPageNameColumn(1);
    }

    @Test
    public void testClickSummaryLabels(){
        connectUrl("http://code.google.com/p/aost/w/list");
        wikiPage.clickSummaryLabelsColumn(1,1);
    }

    @Test
    public void testClickChanged(){
        connectUrl("http://code.google.com/p/aost/w/list");
        wikiPage.clickChangedColumn(1);
    }

    @Test
    public void testClickChangedBy(){
        connectUrl("http://code.google.com/p/aost/w/list");
        wikiPage.clickChangedByColumn(1);
    }

    @Test
    public void testClickHeader(){
        connectUrl("http://code.google.com/p/aost/w/list");
        wikiPage.clickOnTableHeader(2);
        wikiPage.clickOnTableHeader(3);
        wikiPage.clickOnTableHeader(4);
        wikiPage.clickOnTableHeader(5);        
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