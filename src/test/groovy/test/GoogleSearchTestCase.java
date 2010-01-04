package test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.tellurium.test.java.TelluriumJavaTestCase;

import module.GoogleSearchModule;


/**
 * Test cases created based on the GoogleSearchModule UI module
 */
public class GoogleSearchTestCase extends TelluriumJavaTestCase {
    private static GoogleSearchModule gsm;

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void connectToGoogle() {
        connectUrl("http://www.google.com");
    }

    @Test
    public void testGoogleSearchWithXPath() {
        //turn off jQuery Selector
        gsm.disableJQuerySelector();
        gsm.doGoogleSearch("tellurium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLuckyWithXPath() {
        //turn off jQuery Selector
        gsm.disableJQuerySelector();
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testGoogleSearchWithSelector() {
        //turn on jQuery Selector
        gsm.useJQuerySelector();
        gsm.disableSelectorCache();
        gsm.doGoogleSearch("tellurium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLuckyWithSelector() {
        //turn on jQuery Selector
        gsm.useJQuerySelector();
        gsm.disableSelectorCache();
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testGoogleSearchWithSelectorCached() {
        //turn on jQuery Selector
        gsm.useJQuerySelector();
        gsm.enableSelectorCache();
        gsm.setCacheMaxSize(10);
        gsm.doGoogleSearch("tellurium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLuckyWithSelectorCached() {
        //turn on jQuery Selector
        gsm.useJQuerySelector();
        gsm.enableSelectorCache();
        gsm.setCacheMaxSize(10);
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testDump(){
        gsm.dump("Google");
    }
}
