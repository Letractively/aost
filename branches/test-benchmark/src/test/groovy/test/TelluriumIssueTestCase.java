package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import module.TelluriumIssueModuleDecorator;

import java.util.ArrayList;
import java.util.List;

import util.TestResult;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;
    private static List<TestResult> results = new ArrayList<TestResult>();
    private static int repeatCount = 1;

    @BeforeClass
    public static void initUi() {
        tim = new TelluriumIssueModuleDecorator();
    }

    @Before
    public void resetTime(){
        tim.resetAccumulatedTime();
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
        tim.useJavascriptXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Javascript XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @Test
    public void testFlowByJQuerySelector(){
        tim.useJQuerySelector();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
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
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
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
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
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
        storeResult("testFlowByDefaultXPath", tim.getStartTime(), tim.getEndTime(), tim.getAccumulatedTime(), repeatCount, "");
    }

    @AfterClass
    public static void outputResult(){
        System.out.println("\n\nFinal results:");
        for(TestResult result: results){
            System.out.println(result.strResult());
        }
    }
}
