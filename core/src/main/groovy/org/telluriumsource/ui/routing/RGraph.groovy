package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.udl.TableBodyMetaData
import org.telluriumsource.exception.InvalidIndexException
import org.telluriumsource.framework.Environment

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

  void insertTBody(UiObject object){
    TableBodyMetaData meta = object.metaData;
    String index = meta.tbody.getValue();
    String next = meta.row.getValue();

    if("all".equalsIgnoreCase(index)){
      this.t.objectRef = object;
      this.t.presented = true;
      this.t.linkTo.add(next);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.t.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.linkTo.add(next);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.t.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.linkTo.add(next);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.t, object, true);
      last.linkTo.add(next);
      this.t.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.t, object, true);
      any.linkTo.add(next);
      this.t.addChild(any);
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.t.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      first.linkTo.add(next);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.t.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        inode.linkTo.add(next);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.t.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        inode.linkTo.add(next);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    } 
  }

  void insertRow(UiObject object){
    TableBodyMetaData meta = object.metaData;
    String index = meta.row.getValue();
    String next = meta.column.getValue();
    
    if("all".equalsIgnoreCase(index)){
      this.r.objectRef = object;
      this.r.presented = true;
      this.r.linkTo.add(next);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.r.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.linkTo.add(next);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.r.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.linkTo.add(next);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.r, object, true);
      last.linkTo.add(next);
      this.r.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.r, object, true);
      any.linkTo.add(next);
      this.r.addChild(any);
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.r.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      first.linkTo.add(next);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.r.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        inode.linkTo.add(next);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.r.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        inode.linkTo.add(next);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void insertColumn(UiObject object){
    TableBodyMetaData meta = object.metaData;
    String index = meta.column.getValue();

    if("all".equalsIgnoreCase(index)){
      this.c.objectRef = object;
      this.c.presented = true;
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.c.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.c.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.c, object, true);
      this.c.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.c, object, true);
      this.c.addChild(any);      
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.c.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.c.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.c.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void insert(UiObject object) {
    insertRow(object);
    insertColumn(object);
    insertTBody(object);
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
    UiObject object = this.indices.get(key);
    if(object == null){
      String[] ids= key.replaceFirst('_', '').split('_');
      String x = ids[0];
      if("first".equalsIgnoreCase(x)){
        x = "1";
      }
      String y = ids[1];
      if("first".equalsIgnoreCase(y)){
        y = "1";
      }
      String z = ids[2];
      if("first".equalsIgnoreCase(z)){
        z = "1";
      }
      
      String[] list = this.generatePath(x);
      Path path = new Path(list);
      RNode nx = this.walkTo(this.t, x, path);
      list = this.generatePath(y);
      path = new Path(list);
      RNode ny = this.walkTo(this.r, y, path);
      list = this.generatePath(y);
      path = new Path(list);
      RNode nz = this.walkTo(this.c, z, path);

      boolean isLinked = false;
      //check for a direct link
      nx.linkTo.contains(ny.getKey() && ny.linkTo.contains(nz.getKey())) {
        isLinked = true;
      }



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

  RNode walkTo(RNode root, String key, Path path) {
    if(key.equalsIgnoreCase("all"))
      return root;

    if(path != null && path.size() > 0){
      path.pop();
      RNode node = root.walkTo(key, path);
      if(node != null){
        return node;
      }
    }

    return null;
  }
}
