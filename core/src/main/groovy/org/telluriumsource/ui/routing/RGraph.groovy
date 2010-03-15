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
  
  //Key to UI template mapping
  Map<String, UiObject> indices;

  //Internal ID to Template mapping
  Map<String, UiObject> templates;

  //row
  RNode r;

  //column
  RNode c;

  //tbody
  RNode t;

  public void createIndex(String key, UiObject obj){
    this.indices.put(key, obj);
  }

  protected String getInternalId(UiObject object){
    TableBodyMetaData meta = object.metaData;
    String tx = meta.tbody.getValue();
    String rx = meta.row.getValue();
    String cx = meta.column.getValue();

    return "_${tx}_${rx}_${cx}";
  }

   protected String getInternalId(String tx, String rx, String cx){
     return "_${tx}_${rx}_${cx}";
   }
  
  public void storeTemplate(UiObject object){
    String iid = this.getInternalId(object);
    this.templates.put(iid, object);
  }

  void insertTBody(UiObject object, String iid){
    TableBodyMetaData meta = object.metaData;
    String index = meta.tbody.getValue();

    if("all".equalsIgnoreCase(index)){
      this.t.objectRef = object;
      this.t.presented = true;
      this.t.templates.add(iid);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.t.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.templates.add(iid);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.t.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.templates.add(iid);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.t, object, true);
      last.templates.add(iid);
      this.t.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.t, object, true);
      any.templates.add(iid);
      this.t.addChild(any);
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.t.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      first.templates.add(iid);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.t.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        inode.templates.add(iid);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.t.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        inode.templates.add(iid);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    } 
  }

  void insertRow(UiObject object, String iid){
    TableBodyMetaData meta = object.metaData;
    String index = meta.row.getValue();
    
    if("all".equalsIgnoreCase(index)){
      this.r.objectRef = object;
      this.r.presented = true;
      this.r.templates.add(iid);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.r.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.templates.add(iid);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.r.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.templates.add(iid);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.r, object, true);
      last.templates.add(iid);
      this.r.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.r, object, true);
      any.templates.add(iid);
      this.r.addChild(any);
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.r.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      first.templates.add(iid);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.r.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        inode.templates.add(iid);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.r.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        inode.templates.add(iid);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void insertColumn(UiObject object, String iid){
    TableBodyMetaData meta = object.metaData;
    String index = meta.column.getValue();

    if("all".equalsIgnoreCase(index)){
      this.c.objectRef = object;
      this.c.presented = true;
      this.c.templates.add(iid);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.c.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.templates.add(iid);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.c.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.templates.add(iid);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = new RNode("last", this.c, object, true);
      last.templates.add(iid);
      this.c.addChild(last);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = new RNode("any", this.c, object, true);
      any.templates.add(iid);
      this.c.addChild(any);      
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.c.findChild("odd");
      RNode first = new RNode("1", oddNode, object, true);
      first.templates.add(iid);
      oddNode.addChild(first);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.c.findChild("odd");
        RNode inode = new RNode(index, oddNode, object, true);
        inode.templates.add(iid);
        oddNode.addChild(inode);
      }else{
        RNode evenNode = this.c.findChild("even");
        RNode inode = new RNode(index, evenNode, object, true);
        inode.templates.add(iid);
        evenNode.addChild(inode);
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void insert(UiObject object) {
    String iid = this.getInternalId(object);
    this.templates.put(iid, object);

    insertRow(object, iid);
    insertColumn(object, iid);
    insertTBody(object, iid);
  }
  
  void preBuild() {
    this.templates = new HashMap<String, UiObject>();
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
      list = this.generatePath(z);
      path = new Path(list);
      RNode nz = this.walkTo(this.c, z, path);

      boolean isLinked = false;
      //check for a shared template


    }

    return object;
  }

  boolean shareTemplate(RNode x, RNode y, RNode z){
    String iid = this.getInternalId(x.getKey(), y.getKey(), z.getKey());

    return x.templates.contains(iid) && y.templates.contains(iid) && z.templates.contains(iid);
  }

  String[] generatePath(String key){
    if("odd".equalsIgnoreCase(key) || "even".equalsIgnoreCase(key) || "last".equalsIgnoreCase(key) || "any".equalsIgnoreCase(key)){
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
