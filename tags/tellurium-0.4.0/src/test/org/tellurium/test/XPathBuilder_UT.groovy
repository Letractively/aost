package org.tellurium.test

import org.tellurium.locator.XPathBuilder
/**
 *   Test case for XPathBuilder
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class XPathBuilder_UT extends GroovyTestCase{

    void testBuildXPath(){
        String result = XPathBuilder.buildXPath(null, null, null, null)
        assertEquals("/descendant-or-self::*", result)
        result = XPathBuilder.buildXPath("input", null, null, null)
        assertEquals("/descendant-or-self::input", result)
        result = XPathBuilder.buildXPath(null,"Submit", null, null)
        assertEquals("/descendant-or-self::*[text()=\"Submit\"]", result)
        result = XPathBuilder.buildXPath(null,"%%Submit", null, null)
        assertEquals("/descendant-or-self::*[contains(text(),\"Submit\")]", result)
        result = XPathBuilder.buildXPath("input", null, "3", null)
        assertEquals("/descendant-or-self::input[position()=3]", result)
        result = XPathBuilder.buildXPath("input", "Submit", "3", null)
        assertEquals("/descendant-or-self::input[text()=\"Submit\" and position()=3]", result)
        result = XPathBuilder.buildXPath("input", "Submit", "3", [class : "button"])
        assertEquals("/descendant-or-self::input[text()=\"Submit\" and position()=3 and @class=\"button\"]", result)
        result = XPathBuilder.buildXPath("input", "Submit", "3", [class : "button", id : "%%input1"])
        assertEquals("/descendant-or-self::input[text()=\"Submit\" and position()=3 and @class=\"button\" and contains(@id,\"input1\")]", result)

        result = XPathBuilder.buildDescendantXPath("input", "Submit", "3", [class : "button"])
        assertEquals("descendant::input[text()=\"Submit\" and position()=3 and @class=\"button\"]", result)
        result = XPathBuilder.buildChildXPath("input", "Submit", "3", [class : "button"])
        assertEquals("child::input[text()=\"Submit\" and position()=3 and @class=\"button\"]", result)
   }
}