package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.TextBox

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 12, 2010
 * 
 */
class RGraph {

  String[] EMPTY_PATH = [];
  String[] ROOT_PATH = ["all"];
  String[] ODD_PATH = ["all", "odd"];
  String[] EVEN_PATH = ["all", "even"];
  //ID to UI template mapping
  Map<String, UiObject> indices;

  //row
  RNode r;

  //column
  RNode c;

  //tbody
  RNode t;

  public void createIndex(String key, UiObject obj){
    this.indices.put(key, obj);
  }

  void insert(UiObject object) {

  }
  
  void preBuild() {
    TextBox defaultUi = new TextBox();
    RNode raNode = new RNode("all", null, defaultUi, true);
    this.r = raNode;
    RNode roNode = new RNode('odd', raNode, defaultUi, false);
    this.r.addChild(roNode);
    RNode reNode = new RNode('even', raNode, defaultUi, false);
    this.r.addChild(reNode);
    RNode caNode = new RNode("all", null, defaultUi, true);
    this.c = caNode;
    RNode coNode = new RNode('odd', caNode, defaultUi, false);
    this.c.addChild(coNode);
    RNode ceNode = new RNode('even', caNode, defaultUi, false);
    this.c.addChild(ceNode);
    RNode taNode = new RNode("all", null, defaultUi, true);
    this.t = taNode;
    RNode toNode = new RNode('odd', taNode, defaultUi, false);
    this.t.addChild(toNode);
    RNode teNode = new RNode('even', taNode, defaultUi, false);
    this.t.addChild(teNode);
  }

  UiObject route(String key) {
    return null;
  }
}
