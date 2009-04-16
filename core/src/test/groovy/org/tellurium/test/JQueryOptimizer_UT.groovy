package org.tellurium.test

import org.tellurium.locator.JQueryOptimizer

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 15, 2009
 * 
 */

public class JQueryOptimizer_UT extends GroovyTestCase{
  protected JQueryOptimizer jqp = new JQueryOptimizer()

  public void testRemovePrefix(){
    String jqs = "jquery=table#resultstable > tbody > tr > td"
    String result = jqp.removePrefix(jqs)
    assertEquals("table#resultstable > tbody > tr > td", result)
    jqs = " table#resultstable > tbody > tr > td"
    result = jqp.removePrefix(jqs)
    assertEquals("table#resultstable > tbody > tr > td", result)
  }

  public void testContainIdSelector(){
    String jqs = "select#scan"
    boolean result = jqp.containIdSelector(jqs)
    assertTrue(result)

    jqs="select:has(div#scan)"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)

    jqs="select>div#scan"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)

    jqs="select,div#scan"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)     
  }

  public void testPickIdSelector(){
    String jqs = "form[method=get]:has(select#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) select#can"
    String result = jqp.pickIdSelector(jqs)
    assertNotNull(result)
    assertEquals("select#can", result)
  }
}