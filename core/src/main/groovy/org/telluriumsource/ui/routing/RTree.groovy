package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject

/**
 * Routing Tree
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 22, 2010
 * 
 */
abstract class RTree {
  //RTree holds the tree nodes for routing search
  RNode root;

  //ID to UI template mapping
  Map<String, UiObject> indices;

  //setup the routing tree structure before actually insert the UI object into the tree
  abstract public void preBuild();

  abstract public void insert(UiObject object);

  abstract public UiObject route(String key);

  abstract public UiObject walkTo(String key, Path path);

  public void createIndex(String key, UiObject){
    this.indices.put(key, UiObject);
  }
}
