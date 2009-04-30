package test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.*;
import module.TelluriumIssueModule;
import module.TelluriumIssueModuleDecorator;
import util.TestResult;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 30, 2009
 */
public class BulkDataTest extends TelluriumJavaTestCase {
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
