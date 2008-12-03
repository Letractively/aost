package org.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.util.Helper;
import org.tellurium.module.TelluriumIssuesPage;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Test case for Tellurium project issues page
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
public class TelluriumIssuesPageJaveTestCase extends TelluriumJavaTestCase{
    private static TelluriumIssuesPage tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssuesPage();
        tisp.defineUi();
    }

    @Test
    public void testGetIssueTypes(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        String[] ists = tisp.getIsssueTypes();
        assertNotNull(ists);
        assertTrue(ists[2].contains("Open Issues"));
        tisp.selectIssueType(ists[2]);
        tisp.searchIssue("Alter");
    }

    @Test
    public void testAdvancedSearch(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        String[] ists = tisp.getAdvancedIsssueTypes();
        assertNotNull(ists);
        assertTrue(ists[1].contains("All Issues"));
        tisp.selectIssueType(ists[1]);

        tisp.advancedSearchIssue(ists[1], "table", null, null, null, null, null, null, null);
    }

    @Test
    public void testAdvancedSearchTips(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tisp.clickMoreSearchTips();
    }

    @Test
    public void testIssueData(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        int mcolumn = tisp.getTableHeaderNum();
        assertEquals(10, mcolumn);
        List<String> list = tisp.getHeaderNames();
        assertNotNull(list);
        assertEquals(10, list.size());
        assertTrue(Helper.include(list, "Status"));
        list = tisp.getDataForColumn(7);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "John.Jian.Fang"));
    }

    @Test
    public void testClickIssueResult(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,2);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,3);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,4);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,5);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,6);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,7);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,9);
    }

    @Test
    public void testClickHeader(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickOnTableHeader(2);
        tisp.clickOnTableHeader(3);
        tisp.clickOnTableHeader(4);
        tisp.clickOnTableHeader(5);
        tisp.clickOnTableHeader(6);
        tisp.clickOnTableHeader(7);
        tisp.clickOnTableHeader(9);
    }

    @Test
    public void testIdMenu(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickOnTableHeader(2);
        tisp.mouseMoveIdMenu();
        tisp.clickIdMenuSortDown();
        tisp.clickIdMenuSortUp();
    }

    @Test
    public void testSelectColumnMenu(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.toggleIdColumn("ID");
        tisp.toggleIdColumn("Owner");
        tisp.toggleIdColumn("Closed");
    }

    @Test
    public void testSelectDataLayout(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.selectDataLayout("Grid");
        tisp.selectDataLayout("List");
    }
}