package org.telluriumsource.ut

import org.telluriumsource.udl.ListMetaData
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.routing.ListRTree

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 23, 2010
 * 
 */
class ListRTree_UT extends GroovyTestCase {

  public void testList(){
    ListMetaData allMeta = new ListMetaData("Rest", "all");
    UiObject all = new TextBox();
    all.metaData = allMeta;
    ListMetaData anyMeta = new ListMetaData("A", "any");
    UiObject any = new TextBox();
    any.metaData = anyMeta;
    ListMetaData oddMeta = new ListMetaData("B", "odd");
    UiObject odd = new TextBox();
    odd.metaData = oddMeta;
    ListMetaData evenMeta = new ListMetaData("C", "even");
    UiObject even = new TextBox();
    even.metaData = evenMeta;
    ListMetaData firstMeta = new ListMetaData("D", "first");
    UiObject first = new TextBox();
    first.metaData = firstMeta;
    ListMetaData lastMeta = new ListMetaData("E", "last");
    UiObject last = new TextBox();
    last.metaData = lastMeta;
    ListMetaData twoMeta = new ListMetaData("F", "2");
    UiObject two = new TextBox();
    two.metaData = twoMeta;
    ListMetaData threeMeta = new ListMetaData("G", "3");
    UiObject three = new TextBox();
    three.metaData = threeMeta;
    ListMetaData fiveMeta = new ListMetaData("H", "5");
    UiObject five = new TextBox();
    five.metaData = fiveMeta;

    ListRTree tree = new ListRTree();
    tree.preBuild();
    tree.insert(all);
    tree.insert(any);
    tree.insert(odd);
    tree.insert(even);
    tree.insert(first);
    tree.insert(last);
    tree.insert(two);
    tree.insert(three);
    tree.insert(five);

    UiObject a = tree.route("A");
    assertNotNull(a);
    assertEquals("A", a.metaData.id);
    assertEquals("any", a.metaData.index.value);

    UiObject b = tree.route("last");
    assertNotNull(b);
    assertEquals("E", b.metaData.id);
    assertEquals("last", b.metaData.index.value);

    UiObject c = tree.route("3");
    assertNotNull(c);
    assertEquals("G", c.metaData.id);
    assertEquals("3", c.metaData.index.value);
  }

}
