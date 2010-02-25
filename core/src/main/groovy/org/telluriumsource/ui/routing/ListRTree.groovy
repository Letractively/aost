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
  String[] EMPTY_PATH = [];
  String[] ROOT_PATH = ["all"];
  String[] ODD_PATH = ["all", "odd"];
  String[] EVEN_PATH = ["all", "even"];
  
  void insert(UiObject object) {
    ListMetaData meta = object.metaData;
    createIndex(meta.id, object);
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
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.root, object, true);
      this.root.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
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
        RNode inode = new RNode(index, evenNode, object, true);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void preBuild() {
    this.indices = new HashMap<String, UiObject>();
    //TODO: how to add meta data for the default UI?
    TextBox defaultUi = new TextBox();
    RNode allNode = new RNode("all", null, defaultUi, true);
    this.root = allNode;
    RNode oddNode = new RNode('odd', allNode, defaultUi, false);
    this.root.addChild(oddNode);
    RNode evenNode = new RNode('even', allNode, defaultUi, false);
    this.root.addChild(evenNode);
  }

  UiObject route(String key) {
    UiObject object = this.indices.get(key);
    if(object == null){
      if("first".equalsIgnoreCase(key)){
        key = "1";
      }
      String[] list = this.generatePath(key);
      Path path = new Path(list);
      object = this.walkTo(key, path);
    }

    return object;
  }

  String[] generatePath(String key){
    if("odd".equalsIgnoreCase(key) || "even".equalsIgnoreCase(key) || "last".equalsIgnoreCase(key)){
      return ROOT_PATH;
    }else if(key =~ /^\d+$/){
      int inx = Integer.parseInt(key);
      if((inx % 2) == 1){
        return ODD_PATH;
      }else{
        return EVEN_PATH;
      }
    }else if("all".equalsIgnoreCase(key)){
      return EMPTY_PATH;  
    }else{
      throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", key));      
    }
  }

  UiObject walkTo(String key, Path path) {
    if(key.equalsIgnoreCase("all"))
      return this.root.objectRef;

    if(path != null && path.size() > 0){
      path.pop();
      RNode node = this.root.walkTo(key, path);
      if(node != null){
        return node.objectRef;
      }
    }

    return null;
  }
}
