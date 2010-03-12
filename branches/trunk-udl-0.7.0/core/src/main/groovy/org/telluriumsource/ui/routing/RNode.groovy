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

  String key;

  RNode parent;

  LinkedList<RNode> children;

  UiObject objectRef;

  //whether this node is actually presented or not
  boolean presented;

  Adjacency adjacency;

  def RNode() {
  }

  def RNode(key, parent, objectRef, presented) {
    this.key = key;
    this.parent = parent;
    this.objectRef = objectRef;
    this.presented = presented;
    this.children = new LinkedList<RNode>();
  }

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

  protected boolean isInPath(String key, String[] path){
    boolean result = false;
    path?.each {String elem ->
      if(elem.equals(key)){
        result = true;
      }
    }

    return result;
  }

  public RNode walkTo(String key, Path path){
    if(path.size() > 0){
      String next = path.pop();
      if(this.children != null && this.children.size() > 0){
        for(RNode node: children){
          if(next.equalsIgnoreCase(node.key)){
            RNode result = node.walkTo(key, path);
            if(result != null)
              return result;
          }
        }
      }
    }else{
      if(this.children != null && this.children.size() > 0){
        for(RNode node: children){
          if(key.equalsIgnoreCase(node.key)){
            return node;
          }
        }
      }
    }

    if(this.presented)
      return this;

    return null;
  }
}
