package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import example.google.GoogleSearchModule;

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

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        gsm.useJQuerySelector();
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
        gsm.useJQuerySelector();
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        gsm.disableJQuerySelector();
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    @Test
    public void testUseSelectorCache(){
        gsm.enableSelectorCache();
        boolean result = gsm.getSelectorCacheState();
        assertTrue(result);

        gsm.disableSelectorCache();
        result = gsm.getSelectorCacheState();
        assertFalse(result);
    }

    @Test
    public void testTypeRepeated(){
        gsm.doTypeRepeated("tellurium jQuery");
    }

    @Test
    public void testAddNamespace(){
        String te_ns = "http://tellurium.org/ns";
        gsm.addNamespace("te", te_ns);
        String ns = gsm.getNamespace("te");
        assertNotNull(ns);
        assertEquals(te_ns, ns);
    }
}
