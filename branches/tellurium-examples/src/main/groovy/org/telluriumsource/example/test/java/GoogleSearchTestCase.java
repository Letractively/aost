package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.telluriumsource.example.google.GoogleSearchModule;

/**
 * Google search module test case to demonstrate the usage of composite locator and JQuery selector
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 9, 2009
 *
 */

public class GoogleSearchTestCase extends TelluriumJavaTestCase {
    private static GoogleSearchModule gsm;
    private static String te_ns = "http://telluriumsource.org/ns";
    
    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        gsm.enableCssSelector();
        gsm.registerNamespace("te", te_ns);
    }

    @Before
    public void connectToGoogle() {

        connectUrl("http://www.google.com");
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
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("Google", alt);
    }

    @Test
    public void testIsDisabled(){
        gsm.enableCssSelector();
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        gsm.disableCssSelector();
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    @Test
    public void testUseSelectorCache(){
        gsm.enableCache();
        boolean result = gsm.getCacheState();
        assertTrue(result);

        gsm.disableCache();
        result = gsm.getCacheState();
        assertFalse(result);
    }

    @Test
    public void testTypeRepeated(){
        gsm.doTypeRepeated("tellurium jQuery");
    }

    @Test
    public void testRegisterNamespace(){
//        gsm.registerNamespace("te", te_ns);
        String ns = gsm.getNamespace("te");
        assertNotNull(ns);
        assertEquals(te_ns, ns);
        ns = gsm.getNamespace("x");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1999/xhtml", ns);
        ns = gsm.getNamespace("mathml");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1998/Math/MathML", ns);
    }

    @Test
    public void testCachePolicy(){
        gsm.enableCssSelector();
        gsm.enableCache();
        String policy = gsm.getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
        gsm.useDiscardLeastUsedCachePolicy();
        policy = gsm.getCurrentCachePolicy();
        assertEquals("DiscardLeastUsedPolicy", policy);
        gsm.useDiscardInvalidCachePolicy();
        policy = gsm.getCurrentCachePolicy();
        assertEquals("DiscardInvalidPolicy", policy);
        gsm.useDiscardNewCachePolicy();
        policy = gsm.getCurrentCachePolicy();
        assertEquals("DiscardNewPolicy", policy);
        gsm.useDiscardOldCachePolicy();
        policy = gsm.getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
    }
}
