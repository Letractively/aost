package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.udl.ListMetaData
import org.telluriumsource.exception.InvalidIndexException
import org.telluriumsource.framework.Environment

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 22, 2010
 * 
 */
class ListRTree extends RTree{

  void insert(UiObject object) {
    ListMetaData meta = object.metaData;
    createIndex(meta.id);
    String index = meta.getIndex().getValue();
    if("all".equalsIgnoreCase(index)){
      this.root.objectRef = object;
      this.root.presented = true;
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.root.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.root.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
    }else if("last".equalsIgnoreCase(index) || "any".equalsIgnoreCase(index)){
      //do nothing

    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.root.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.root.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.root.findChild("even");
        RNode inode = new RNode(index, oddNode, object, true);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void preBuild() {
    this.index = new HashMap<String, UiObject>();
    TextBox defaultUi = new TextBox();
    RNode allNode = new RNode("all", null, defaultUi, false);
    this.root = allNode;
    RNode oddNode = new RNode('odd', allNode, defaultUi, false);
    this.root.addChild(oddNode);
    RNode evenNode = new RNode('even', allNode, defaultUi, false);
    this.root.addChild(evenNode);
  }
}
