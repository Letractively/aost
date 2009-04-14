package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import module.TelluriumIssueModuleDecorator;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModuleDecorator tim;

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

    @Test
    public void testDefaultXPath(){
        tim.disableJQuerySelector();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<10; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        System.out.println("Use default XPath, the accumulated time is " + accumulatedTime + " ms");
    }

    @Test
    public void testJavascriptXPath(){
        tim.disableJQuerySelector();
        tim.useJavascriptXPathLibrary();
        for(int i=0; i<10; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        System.out.println("Use Javascript XPath, the accumulated time is " + accumulatedTime + " ms");
    }

    @Test
    public void testJQuerySelector(){
        tim.useJQuerySelector();
        tim.useDefaultXPathLibrary();
        for(int i=0; i<10; i++)
            testFlow();
        long accumulatedTime = tim.getAccumulatedTime();
        System.out.println("Use jQuery selector, the accumulated time is " + accumulatedTime + " ms");
    }

}
