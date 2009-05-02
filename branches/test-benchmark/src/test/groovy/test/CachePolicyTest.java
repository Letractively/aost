package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import module.TelluriumIssueModuleDecorator;
import module.TelluriumIssueModule;
import util.TestResult;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 30, 2009
 */
public class CachePolicyTest extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;
    private static List<TestResult> results = new ArrayList<TestResult>();
    private static int repeatCount = 1;

    @BeforeClass
    public static void initUi() {
//        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModule());
    }

    @Before
    public void resetTime(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModule());
//        tim.resetAccumulatedTime();
        tim.useJQuerySelector();
        tim.enableSelectorCache();
        tim.useDefaultXPathLibrary();
        tim.setCacheMaxSize(5);
        tim.cleanSelectorCache();
    }

    public void testFlow() {
        for (int i = 0; i < repeatCount; i++) {
            connectUrl("http://code.google.com/p/aost/issues/list");
            tim.testGetIssueTypes(1, "TrUMP");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "jQuery");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "Maven");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "core");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "dojo");
            tim.waitPageLod();
            tim.testGetIssueTypes(2, "xpath");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearch(1, "TrUMP", "John.Jian.Fang", "John.Jian.Fang");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearch(1, "Maven", "John.Jian.Fang", "matter.senter");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearch(1, "jQuery", "John.Jian.Fang", "koryak");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearch(1, "core", "John.Jian.Fang", "koryak");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearch(1, "core", "John.Jian.Fang", "haroonzone");
            tim.waitPageLod();
            connectUrl("http://code.google.com/p/aost/issues/advsearch");
            tim.testAdvancedSearchTips();
            tim.waitPageLod();
        }
    }

    protected TestResult storeResult(String testName, long startTime, long endTime, long accumulatedTime, int repeatCount, String msg){
        TestResult result = new TestResult();
        result.setStartTime(startTime);
        result.setEndTime(endTime);
        result.setAccumulatedTime(accumulatedTime);
        result.setTestName(testName);
        result.setRepeatCount(repeatCount);
        result.setMessage(msg);
        results.add(result);

        return result;
    }

    @Ignore
    @Test
    public void testFlowByJQuerySelector(){
        tim.useJQuerySelector();
        tim.disableSelectorCache();
        tim.useDefaultXPathLibrary();
//        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "testFlowByJQuerySelector: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelector", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowWithDiscardNewPolicy(){
        tim.useDiscardNewCachePolicy();
        String policy = tim.getCurrentCachePolicy();
        assertEquals("DiscardNewPolicy", policy);
//        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
//            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
 //       }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "TestFlowWithDiscardNewPolic: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowWithDiscardNewPolicy", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowWithDiscardOldPolicy(){
        tim.useDiscardOldCachePolicy();
        String policy = tim.getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
//        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
//            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
 //       }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "TestFlowWithDiscardOldPolic: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowWithDiscardOldPolicy", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }
    
    @Test
    public void testFlowWithDiscardLeastUsedPolicy(){
        tim.useDiscardLeastUsedCachePolicy();
        String policy = tim.getCurrentCachePolicy();
        assertEquals("DiscardLeastUsedPolicy", policy);
//        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
//            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
 //       }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "TestFlowWithDiscardLeastUsedPolic: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowWithDiscardLeastUsedPolicy", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowWithDiscardInvalidPolicy(){
        tim.useDiscardInvalidCachePolicy();
        String policy = tim.getCurrentCachePolicy();
        assertEquals("DiscardInvalidPolicy", policy);
//        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being                        y
//            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
//        }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "TestFlowWithDiscardInvalidPolic: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowWithDiscardInvalidPolicy", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @AfterClass
    public static void outputResult(){
        System.out.println("\n\nFinal results:");
        for(TestResult result: results){
            System.out.println(result.strResult());
        }
    }
}
