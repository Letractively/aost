package test;

import module.TelluriumIssueModuleDecorator;
import module.TelluriumIssueModule;
import util.TestResult;

import java.util.List;
import java.util.ArrayList;

import org.junit.*;
import org.tellurium.test.java.TelluriumJavaTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 30, 2009
 */
public class SpeedTest extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;
    private static List<TestResult> results = new ArrayList<TestResult>();
    private static int repeatCount = 10;

    @BeforeClass
    public static void initUi() {
    }

    @Before
    public void resetTime(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModule());
        tim.resetAccumulatedTime();
    }

    public void testFlow(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        for (int i = 0; i < repeatCount; i++) {
            tim.testGetIssueTypes(1, "TrUMP");
            tim.waitPageLod();
            tim.testGetIssueTypes(2, "jQuery");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "Maven");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "core");
            tim.waitPageLod();
            tim.testGetIssueTypes(2, "dojo");
            tim.waitPageLod();
            tim.testGetIssueTypes(2, "xpath");
            tim.waitPageLod();
            tim.testGetIssueTypes(1, "widget");
            tim.waitPageLod();
            tim.testGetIssueTypes(2, "selector");
            tim.waitPageLod();
        }
    }
     public void testSelect(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        for (int i = 0; i < repeatCount; i++) {
            tim.testSelectIssueOnly(1, "TrUMP");
            tim.testSelectIssueOnly(2, "jQuery");
            tim.testSelectIssueOnly(1, "Maven");
            tim.testSelectIssueOnly(1, "core");
            tim.testSelectIssueOnly(2, "dojo");
            tim.testSelectIssueOnly(2, "xpath");
            tim.testSelectIssueOnly(1, "widget");
            tim.testSelectIssueOnly(2, "selector");
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

    @Test
    public void testFlowByDefaultXPath(){
        tim.disableJQuerySelector();
        tim.disableSelectorCache();
        tim.useDefaultXPathLibrary();
//        for(int i=0; i<repeatCount; i++)
//            testFlow();
            testSelect();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Default XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Ignore
    @Test
    public void testFlowByJavascriptXPath(){
        tim.disableJQuerySelector();
        tim.disableSelectorCache();
        tim.useJavascriptXPathLibrary();
//        for(int i=0; i<repeatCount; i++)
//            testFlow();
            testSelect();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Javascript XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJavascriptXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJQuerySelector(){
        tim.useJQuerySelector();
        tim.disableSelectorCache();
        tim.useDefaultXPathLibrary();
//        for(int i=0; i<repeatCount; i++)
//            testFlow();
            testSelect();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelector", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJQuerySelectorCacheEnabled(){
        tim.useJQuerySelector();
        tim.enableSelectorCache();
        tim.useDefaultXPathLibrary();
//        tim.useJavascriptXPathLibrary();
//        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
//            tim.cleanSelectorCache(;
//            testFlow();
            testSelect();
            tim.showCacheUsage();
 //       }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelectorCacheEnabled", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @AfterClass
    public static void outputResult(){
        System.out.println("\n\nFinal results:");
        for(TestResult result: results){
            System.out.println(result.strResult());
        }
    }
}