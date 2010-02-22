package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject

/**
 * Routing Node
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 22, 2010
 * 
 */
class RNode {

  def RNode() {
  }

  def RNode(key, parent, objectRef, presented) {
    this.key = key;
    this.parent = parent;
    this.objectRef = objectRef;
    this.presented = presented;
    this.children = new LinkedList<RNode>();
  }

  String key;

  RNode parent;

  LinkedList<RNode> children;

  UiObject objectRef;

  //whether this node is actually presented or not
  boolean presented;

  public void addChild(RNode child) {
    children.add(child);
  }

  public RNode findChild(String key){
    if(this.children != null && this.children.size() > 0){
      for(RNode node: children){
        if(node.key.equalsIgnoreCase(key))
          return node;
      }
    }

    return null;
  }
}
