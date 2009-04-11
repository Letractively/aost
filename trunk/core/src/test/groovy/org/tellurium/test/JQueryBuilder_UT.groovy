package org.tellurium.test

import org.tellurium.locator.JQueryBuilder

public class JQueryBuilder_UT extends GroovyTestCase {

  public void testCheckTag(){
    String tag = null
    String result = JQueryBuilder.checkTag(tag)
    assertEquals("*", result)

    tag = " "
    result = JQueryBuilder.checkTag(tag)
    assertEquals("*", result)

    tag = "div"
    result = JQueryBuilder.checkTag(tag)
    assertEquals("div", result)
  }

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

    text = "*good"
    result = JQueryBuilder.containText(text.substring(2))
    assertEquals(":contains(good)", result)

    text = "good"
    result = JQueryBuilder.attrText(text)
    assertEquals(":text(good)", result)

    text = "I'm Feeling Lucky"
    result = JQueryBuilder.attrText(text)
    assertEquals(":contains(m Feeling Lucky)", result)
  }

  public void testAttrPairs(){
    String key="method"
    String val="list"

    String result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[method=list]", result)
    assertFalse(JQueryBuilder.inBlackList(key))

    key = "action"
    assertTrue(JQueryBuilder.inBlackList(key))

    key = "mclazz"
    val = " good' sugges'tion "
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz*=sugges]", result)

    key = "mclazz"
    val = "^good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz^=good]", result)

    key = "mclazz"
    val = "\$good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz\$=good]", result)

    key = "mclazz"
    val = "*good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz*=good]", result)
  }
}