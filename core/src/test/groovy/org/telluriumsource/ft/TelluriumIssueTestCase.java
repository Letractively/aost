package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.entity.CachePolicy;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.TelluriumIssueModule;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class TelluriumIssueTestCase extends TelluriumMockJUnitTestCase {
    private static TelluriumIssueModule tim;
    @BeforeClass
    public static void initUi() {
        tim = new TelluriumIssueModule();
        tim.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useCache(true);
        setCacheMaxSize(30);
        useTrace(true);
        useLocatorWithCache(false);
        useClosestMatch(true);
    }

    @Before
    public void setUp(){
        connectUrl("http://code.google.com/p/aost/issues/list");
    }

    @Test
    public void testSearchIssueTypes(){
        String[] ists = tim.getIsssueTypes();
        tim.selectIssueType(ists[2]);
        tim.searchIssue("Alter");
    }

    @Test
    public void testAllIssues(){
        String[] details = tim.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }

    @Test
    public void testGetCellCount(){
        int count = tim.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
        String[] details = tim.getAllText();
        assertNotNull(details);
        assertEquals(details.length, count);
    }

    @Test
    public void testCSS(){
        tim.disableCache();
        String[] css = tim.getTableCSS("font-size");
        assertNotNull(css);
    }
    
    @Test
    public void checkCacheable(){
        boolean result = tim.checkamICacheable("issueResult[1][1]");
        assertTrue(result);
        result = tim.checkamICacheable("issueResult");
        assertTrue(result);
    }

    @Test
    public void testGetDataForColumn(){
        long beforeTime = System.currentTimeMillis();
        tim.getDataForColumn(3);
        long endTime = System.currentTimeMillis();
        System.out.println("Time noCacheForChildren " + (endTime-beforeTime) + "ms");
        beforeTime = System.currentTimeMillis();
        tim.getDataForColumnWithCache(3);
        endTime = System.currentTimeMillis();
        System.out.println("Time with all cache " + (endTime-beforeTime) + "ms");
    }

    @Test
    public void testCachePolicy(){
        setCacheMaxSize(2);
        useCachePolicy(CachePolicy.DISCARD_NEW);
        tim.searchIssue("Alter");
        tim.getTableCSS("font-size");
        tim.getIsssueTypes();
        tim.searchIssue("Alter");
        setCacheMaxSize(30);
    }

    @After
    public void showCacheUsage(){
        int size = tim.getCacheSize();
        int maxSize = tim.getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: ");
        Map<String, Long> usages = tim.getCacheUsage();
        Set<String> keys = usages.keySet();
        for(String key: keys){
            System.out.println("UID: " + key + ", Count: " + usages.get(key));
        }
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
    
/*    
    @BeforeClass
    public static void setup(){
         = new TelluriumIssueModule();
        .defineUi();
        registerHtml("TelluriumIssue");
    }

    @Before
    public void connect(){
        connect("TelluriumIssue");
    }

    @Test
    public void testDump(){
        useCssSelector(true);
        .dump("issueResult");
    }*/
}
