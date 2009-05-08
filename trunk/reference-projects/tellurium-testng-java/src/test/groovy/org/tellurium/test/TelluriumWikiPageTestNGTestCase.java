package org.tellurium.test;

import org.tellurium.test.java.TelluriumTestNGTestCase;
import org.tellurium.util.Helper;
import org.tellurium.module.TelluriumWikiPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.List;


/**
 * Tests Wiki Page on Tellurium project website
 *
 *  @author Quan Bui (Quan.Bui@gmail.com)
 *  @autoher Haroon Rasheed (haroonzone@gmail.com)
 *
 * Date: Aug 6, 2008
 *
 */
public class TelluriumWikiPageTestNGTestCase extends TelluriumTestNGTestCase{
    private static TelluriumWikiPage wikiPage;

    public static String newline = System.getProperty("line.separator");

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
        assertTrue(types[1].contains("All wiki pages"));
        assertTrue(types[2].contains("Featured pages"));
        assertTrue(types[3].contains("Current pages"));
        assertTrue(types[4].contains("Deprecated pages"));
    }

    @Test
    public void testSearchPageByText(){
        connectUrl("http://code.google.com/p/aost/w/list");
        // Set download type with other value
        wikiPage.selectSearchType(" All wiki pages");
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
        wikiPage.selectSearchType(" All wiki pages");
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

}