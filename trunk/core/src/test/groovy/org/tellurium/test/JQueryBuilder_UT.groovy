package org.tellurium.test

import org.tellurium.locator.JQueryBuilder

public class JQueryBuilder_UT extends GroovyTestCase {

  public void testAttrId(){
    String id = null
    String result = JQueryBuilder.attrId(id)
    assertEquals("[id]", result)

    id = "hp_table"
    result = JQueryBuilder.attrId(id)
    assertEquals("#hp_table", result)    
  }

  public void testAttrClass(){
    String clazz = ""

    String result = JQueryBuilder.attrClass(clazz)
    assertEquals("[class]", result)

    clazz = "test"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".test", result)

    clazz = "test demo"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".test.demo", result)    
  }

  public void testAttrText(){
    String text = "%%good"
    String result = JQueryBuilder.containText(text.substring(2))
    assertEquals(":contains(good)", result)

    text = "good"
    result = JQueryBuilder.attrText(text)
    assertEquals("[text()=good]", result)

    text = "I'm Feeling Lucky"
    result = JQueryBuilder.attrText(text)
    assertEquals(":contains(m Feeling Lucky)", result)
  }
}