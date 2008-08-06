package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import example.tellurium.TelluriumIssuesPage;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
}