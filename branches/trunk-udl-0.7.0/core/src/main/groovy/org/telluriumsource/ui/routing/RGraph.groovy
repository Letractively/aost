package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject

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

  }

  UiObject route(String key) {
    return null;
  }
}
