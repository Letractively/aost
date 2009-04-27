package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import module.TelluriumIssueModuleDecorator;
import module.TelluriumIssueModule;
import module.TelluriumIssueModuleNoGroup;

import java.util.ArrayList;
import java.util.List;

import util.TestResult;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;
    private static List<TestResult> results = new ArrayList<TestResult>();
    private static int repeatCount = 10;

    @BeforeClass
    public static void initUi() {
//        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModule());
    }

    @Before
    public void resetTime(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModule());
//        tim.resetAccumulatedTime();
    }

    public void testFlow(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tim.testGetIssueTypes(1);
        tim.waitPageLod();
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tim.testAdvancedSearch(2);
        tim.waitPageLod();
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tim.testAdvancedSearchTips();
        tim.waitPageLod();
        connectUrl("http://code.google.com/p/aost/issues/list");
        tim.testIssueData(3);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tim.testClickIssueResult(3, 2);
        tim.waitPageLod();
        connectUrl("http://code.google.com/p/aost/issues/list");
        tim.testClickHeader(3);
        tim.pauseTest();
//        connectUrl("http://code.google.com/p/aost/issues/list");
//        tim.testSelectDataLayout("Grid");
//        tim.waitPageLod();
//        tim.testSelectDataLayout("List");
//        tim.waitPageLod();
    }

    public void testGetData(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tim.testGetData();
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
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Default XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJavascriptXPath(){
        tim.disableJQuerySelector();
        tim.disableSelectorCache();
        tim.useJavascriptXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
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
        for(int i=0; i<repeatCount; i++)
            testFlow();
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
        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
        }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelectorCacheEnabled", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByDefaultXPathNoGroup(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModuleNoGroup());
        tim.disableJQuerySelector();
        tim.disableSelectorCache();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow No Group: Default XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByDefaultXPathNoGroup", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJavascriptXPathNoGroup(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModuleNoGroup());
        tim.disableJQuerySelector();
        tim.disableSelectorCache();
        tim.useJavascriptXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow No Group: Javascript XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJavascriptXPathNoGroup", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJQuerySelectorNoGroup(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModuleNoGroup());
        tim.useJQuerySelector();
        tim.disableSelectorCache();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow No Group: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelectorNoGroup", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJQuerySelectorNoGroupCacheEnabled(){
        tim = new TelluriumIssueModuleDecorator(new TelluriumIssueModuleNoGroup());
        tim.useJQuerySelector();
        tim.enableSelectorCache();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++){
            //manual clean up the cache for the time being
            tim.cleanSelectorCache();
            testFlow();
            tim.showCacheUsage();
        }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow No Group: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByJQuerySelectorNoGroupCacheEnabled", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testGetDataByDefaultXPath(){
        tim.useDefaultXPathLibrary();
        tim.disableJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: Default XPath, the accumulated Time is " + accumulatedTime + "ms";
        System.out.println(msg);
        storeResult("testGetDataByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testGetDataByJavascriptXPath(){
        tim.useJavascriptXPathLibrary();
        tim.disableJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: Javascript XPath, the accumulated Time is " + accumulatedTime + "ms";
        System.out.println(msg);
        storeResult("testGetDataByJavascriptXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testGetDataByJQuerySelector(){
        tim.useDefaultXPathLibrary();
        tim.useJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: jQuery Selector, the accumulated Time is " + accumulatedTime + "ms";
        System.out.println(msg);
        storeResult("testGetDataByJQuerySelector", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testGetDataByJQuerySelectorCacheEnabled(){
        tim.useDefaultXPathLibrary();
        tim.useJQuerySelector();
        tim.enableSelectorCache();
        for(int i=0; i<repeatCount; i++){
            tim.cleanSelectorCache();
            testGetData();
            tim.showCacheUsage();
        }
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: jQuery Selector, the accumulated Time is " + accumulatedTime + "ms";
        System.out.println(msg);
        storeResult("testGetDataByJQuerySelectorCacheEnabled", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }
    
    @AfterClass
    public static void outputResult(){
        System.out.println("\n\nFinal results:");
        for(TestResult result: results){
            System.out.println(result.strResult());
        }
    }
}
