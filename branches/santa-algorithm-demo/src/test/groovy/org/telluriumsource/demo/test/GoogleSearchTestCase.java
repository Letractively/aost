package org.telluriumsource.demo.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;


import org.telluriumsource.demo.module.GoogleSearchModule;
import org.telluriumsource.entity.CachePolicy;

import java.util.List;
import java.util.ArrayList;


/**
 * Test cases created based on the GoogleSearchModule UI module
 */
public class GoogleSearchTestCase extends TelluriumJUnitTestCase {

    private static GoogleSearchModule gsm;
    private static String te_ns = "http://telluriumsource.org/ns";

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
    }

    @Before
    public void connectToGoogle() {

        connectUrl("http://www.google.com");
    }

    @Test
    public void testJsonfyUiModule(){
        String json = gsm.jsonify("Google");
        System.out.println(json);
    }

    @Test
    public void testGoogleSearch() {
        gsm.doGoogleSearch("tellurium . ( Groovy ) Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testLogo(){
        gsm.diagnose("Logo");
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
//        assertEquals("Google", alt);
//        assertEquals("E.C. Segar's Birthday", alt);
    }

    @Test
    public void testClosestMatch(){
        useClosestMatch(true);
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        useClosestMatch(false);
    }

    @Test
    public void testIsDisabled(){
        useCssSelector(true);
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        useCssSelector(false);
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    @Test
    public void testUseSelectorCache(){
        useCache(true);
        boolean result = gsm.getCacheState();
        assertTrue(result);

        useCache(false);
        result = gsm.getCacheState();
        assertFalse(result);
    }

    @Test
    public void testRegisterNamespace(){
        registerNamespace("te", te_ns);
        String ns = getNamespace("te");
        assertNotNull(ns);
        assertEquals(te_ns, ns);
        ns = getNamespace("x");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1999/xhtml", ns);
        ns = getNamespace("mathml");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1998/Math/MathML", ns);
    }

    @Test
    public void testCachePolicy(){
        useCssSelector(true);
        useCache(true);
        String policy = getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
//        gsm.useDiscardLeastUsedCachePolicy();
        useCachePolicy(CachePolicy.DISCARD_LEAST_USED);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardLeastUsedPolicy", policy);
//        gsm.useDiscardInvalidCachePolicy();
        useCachePolicy(CachePolicy.DISCARD_INVALID);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardInvalidPolicy", policy);
//        gsm.useDiscardNewCachePolicy();
        useCachePolicy(CachePolicy.DISCARD_NEW);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardNewPolicy", policy);
//        gsm.useDiscardOldCachePolicy();
        useCachePolicy(CachePolicy.DISCARD_OLD);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
    }

    @Test
    public void testCustomDirectCall(){
        List<String> list  = new ArrayList<String>();
        list.add("//input[@title='Google Search']");
        gsm.customDirectCall("click", list.toArray());
    }

    @Test
    public void testDump(){
        gsm.dump("Google");
    }
    
    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
