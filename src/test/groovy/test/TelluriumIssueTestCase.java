package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import module.TelluriumIssueModuleDecorator;

import java.util.ArrayList;
import java.util.List;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;
    private static List<String> results = new ArrayList<String>();
    private static int repeatCount = 5;

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

    @Test
    public void testFlowByDefaultXPath(){
        tim.disableJQuerySelector();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Use default XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        results.add(msg);
    }

    @Test
    public void testFlowByJavascriptXPath(){
        tim.disableJQuerySelector();
        tim.useJavascriptXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Use Javascript XPath, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        results.add(msg);
    }

    @Test
    public void testFlowByJQuerySelector(){
        tim.useJQuerySelector();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<repeatCount; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Test Flow: Use jQuery selector, the accumulated time is " + accumulatedTime + " ms";
        System.out.println(msg);
        results.add(msg);
    }

    @Test
    public void testGetDataByDefaultXPath(){
        tim.useDefaultXPathLibrary();
        tim.disableJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: Use default XPath, the accumulated Time for get Table data is " + accumulatedTime + "ms";
        System.out.println(msg);
        results.add(msg);
    }

    @Test
    public void testGetDataByJavascriptXPath(){
        tim.useJavascriptXPathLibrary();
        tim.disableJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: Use Javascript XPath, the accumulated Time for get Table data is " + accumulatedTime + "ms";
        System.out.println(msg);
        results.add(msg);
    }

    @Test
    public void testGetDataByJQuerySelector(){
        tim.useDefaultXPathLibrary();
        tim.useJQuerySelector();
        for(int i=0; i<repeatCount; i++)
            testGetData();
        long accumulatedTime = tim.getAccumulatedTime();
        String msg = "Bulk Data: Use jQuery Selector, the accumulated Time for get Table data is " + accumulatedTime + "ms";
        System.out.println(msg);
        results.add(msg);
    }

    @AfterClass
    public static void outputResult(){
        System.out.println("\n\nFinal results:");
        for(String str: results){
            System.out.println(str);
        }
    }
}
