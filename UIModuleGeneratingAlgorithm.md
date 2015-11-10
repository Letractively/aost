

# Introduction #

The [Tellurium UI Module Plugin](http://code.google.com/p/aost/wiki/TrUMP) (Trump) automatically generates UI modules for users. The workflow of Trump is shown as follows:

http://tellurium-users.googlegroups.com/web/TrUMPDiagramSmall.png?gda=9CFDmEcAAAD5mhXrH3CK0rVx4StVj0LYmqln6HzIDYRu0sy-jUnaq8nAtNENvOA6NOHkUCAqlOUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=5FPzPwsAAAA9y3PlQReYHIKRUJU7LIYD

You may wonder in step 2, how the UI module is generated. The main idea behind this is to get the full xpath for each UI element that a user selects. Then, construct the UI module based on the xpaths.

The core algorithm is a similar to the one to build a prefix tree. The data structure of the [prefix tree, or Trie in short](http://en.wikipedia.org/wiki/Trie) can be illustrated by the following figure, which representing a dictionary with prefix chains.


http://tellurium-users.googlegroups.com/web/Trie_example.png?gda=wQPArEIAAACsdq5EJRKLPm_KNrr_VHB_1oeA7nOF2iS8uQDPb9OlTU7sRkzRAxlFeFD9Wh0qRLFV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=1t-rGQsAAAAPdfdLxsCDbw1Fr9_YJcs3

To better understand the UI module generating algorithm, we first show how to implement the trie based dictionary in Java, then explain the actual UI module generating algorithm.

# A Trie Based Dictionary #

Usually, a dictionary can be stored in the Trie data structure so that it is very easy to find the words with the same prefix and to do [long prefix match](http://en.wikipedia.org/wiki/Longest_prefix_match).

To create the Trie data structure, first, we need to define a node in the prefix tree.

```
public class Node {
    public static final int LENGTH = 64;

    //hold the String value for this node
    private String elem;

    //the level of this node in the Trie tree
    private int level;

    //pointer to its parent
    private Node parent;

    //child nodes 
    private LinkedList<Node> children = new LinkedList<Node>();
}
```

We can add couple methods for the node

```
    public void addChild(Node child){
        children.add(child);
    }

    public void removeChild(Node child){
        children.remove(child);
    }

    public int getChildrenSize(){
        return this.children.size();
    }

    public void checkLevel(){
        if(this.parent == null)
            this.level = 0;
        else
            this.level = this.parent.getLevel() + 1;
        if(this.children.size() > 0){
            for(Node node: children){
                node.checkLevel();
            }
        }
    }

    public String getFullWord(){
        if(parent == null){
            return this.elem;
        }

        return parent.getFullWord() + this.elem;
    }

    public void printMe(){
        boolean hasChildren = false;
        if(children.size() > 0)
            hasChildren = true;
        StringBuffer sb = new StringBuffer(LENGTH);
        for(int i=0; i<this.level; i++){
            sb.append("  ");
        }
        sb.append(this.elem);
        if(hasChildren)
            sb.append("{");
        System.out.println(sb.toString());
        if(hasChildren){
            for(Node node: children){
                node.printMe();
            }
        }
        if(hasChildren){
            StringBuffer indent = new StringBuffer(LENGTH);
            for(int i=0; i<level; i++){
                indent.append("  ");
            }
            indent.append("}");
            System.out.println(indent.toString());
        }
    }
```

The Trie is actually a Tree structure.

```
public class Trie {
    //the root of the Trie, or Prefix Tree
    private Node root;
}
```

To build a tree, we need the following methods,

```
    public void buildTree(String[] dictionary){
        if(dictionary != null && dictionary.length > 0){
            for(String word: dictionary){
                this.insert(word);
            }
        }
    }

    public void insert(String word){
        if(this.root == null){
            //If it is the first time to insert an word to the Tire
            this.root = new Node();
            //root is an empty String, more like a logic node
            this.root.setElem("");
            this.root.setLevel(0);
            this.root.setParent(null);

            //add the word as the child of the root node
            Node child = new Node();
            child.setElem(word);
            child.setParent(this.root);
            this.root.addChild(child);
        }else{
            //not the first node, need to walk all the way down to find a place to insert
            this.walk(this.root, word);
        }
    }

    protected void walk(Node current, String word) {
        //look at current node's children
        if(current.getChildrenSize() == 0){
            //no child yet, add itself as the first child
            Node child = new Node();
            child.setElem(word);
            child.setParent(current);
            current.addChild(child);
        }else{
            //there are children for the current node
            //check if the new String is a prefix of a set of children
            List<Node> common = new ArrayList<Node>();
            for(Node node: current.getChildren()){
                if(node.getElem().startsWith(word)){
                    common.add(node);
                }
            }
            //if the new String is indeed a prefix of a set of children
            if(common.size() > 0){
                Node shared = new Node();
                shared.setElem(word);
                shared.setParent(current);
                for(Node node: common){
                    //assume no duplication in the dictionary, otherwise, need to consider the empty string case for a child
                    node.setElem(node.getElem().substring(word.length()));
                    node.setParent(shared);
                    shared.addChild(node);
                    current.removeChild(node);
                }
                current.addChild(shared);
            }else{
                //not common prefix available, then check if the child is a prefix of the input String
                boolean found = false;
                Node next = null;
                for(Node node: current.getChildren()){
                    if(word.startsWith(node.getElem())){
                        found = true;
                        next = node;
                        break;
                    }
                }
                if(found){
                    //not a duplication, otherwise, do nothing
                    if(word.length() != next.getElem().length()){
                        String leftover = word.substring(next.getElem().length());
                        walk(next, leftover);
                    }
                }else{
                    //not found, need to create a new node a the child of the current node
                    Node child = new Node();
                    child.setParent(current);
                    child.setElem(word);
                    current.addChild(child);
                }
            }
        }
    }
```

In addition, we need couple more help methods

```
    public void checkLevel(){
        if(root != null){
            root.checkLevel();
        }
    }

    public void printMe(){
        if(this.root != null){
            System.out.println("---------------------------- Trie/Prefix Tree ----------------------------\n");
            this.root.printMe();
            System.out.println("--------------------------------------------------------------------------\n");
        }        
    }
```

That's all. We can create a unit test for the Tire data structure. The code are in [Tellurium Core](http://aost.googlecode.com/svn/trunk/core) project.

```
public class Trie_UT {
    public String[] randomize(String[] dictionary){
        if(dictionary != null && dictionary.length > 0){
            Random rand = new Random();
            List<String> list = new ArrayList<String>();
            for(String str: dictionary){
                list.add(str);
            }
            List<String> nlist = new ArrayList<String>();
            while(list.size() > 0){
                int index = rand.nextInt(list.size());
                nlist.add(list.remove(index));
            }

            return nlist.toArray(new String[0]);
        }

        return null;
    }

    @Test
    public void testInsert(){
        String[] dictionary = {"a", "an", "and", "andy", "bo", "body", "bodyguard", "some", "someday", "goodluck", "joke"};
        Trie trie = new Trie();
        String[] ndict = randomize(dictionary);
        trie.buildTree(ndict);
        trie.checkLevel();
        trie.printMe();   
    }
}
```

The test output looks like the following result,

```

---------------------------- Trie/Prefix Tree ----------------------------

{
  a{
    n{
      d{
        y
      }
    }
  }
  joke
  bo{
    dy{
      guard
    }
  }
  goodluck
  some{
    day
  }
}
--------------------------------------------------------------------------

```

# The UI Module Generating Algorithm #

The UI module generating algorithm is similar to the above, but there are some differences.
  * The root node in a UI module is usually not an empty one
  * In the algorithm, we actually compare the common xpath instead of prefix.

The algorithm is implemented in Javascript. Similarly, we need to first define the node object,

```
function NodeObject(){

    this.constants = {
        TAG : "tag",
        POSITION: "position",
        HEADER : "header",
        TRAILER: "trailer"
    };
    
    //hold the dom Node associated to the current tree node 
    this.domNode = null;
    this.id = null;
    this.xpath = null;
    this.attributes = new HashMap();
    this.parent = null;
    this.children = new Array();
    this.ui = new UiType();
    
    this.header = null;
    this.tailer = null;
    this.nodexpath = null;
    
    //flag to indicate whether this node is a new generated during the grouping process, i.e., by the algorithm
    this.newNode = false;
    //tag selection state machine
    this.tagState = new TagState();
    //common methods to process xpath
    this.xpathProcessor = new XPathProcessor();
    //The filter to remove unwanted attributes
    this.filter = new Filter();
    //used to store the element tag
    this.tag = null;

    //The UI object associated with this node
    this.uiobject = new UiObject();

    this.xmlutil = new XmlUtil();
}
```

and some methods.

```
NodeObject.prototype.walkUp = function(){
    var rxp = this.uiobject.buildXPath();

    var xp;

    if(this.parent != null){
        xp = this.parent.walkUp() + rxp;
    }else{
        xp = rxp;
    }

    return xp;
}

NodeObject.prototype.getLevel = function(){
    var level = 0;
    var current = this;
    
    while(current.parent != null){
        level++;
        current = current.parent;
    }

    return level;
}

NodeObject.prototype.buildUiObject = function(){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    this.uiobject.buildUiObject(this, hasChildren);

    if (hasChildren) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].buildUiObject();
        }
    }
    
    this.checkUiDirectAttribute();
}

NodeObject.prototype.printUI = function(layout){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    //get the current level of the node so that we can do pretty print
    var level = this.getLevel();

    var strobj = this.uiobject.strUiObject(level);
    layout.push(strobj);

    if (hasChildren) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].printUI(layout);
        }

        var strobjft = this.uiobject.strUiObjectFooter(level);
        layout.push(strobjft);
    }
}
```

The Trie Tree is defined as follows,

```
function Tree(){
    this.root = null;
    this.xpathMatcher = new XPathMatcher();
    this.uiModel = new Array();
    this.uid = new Uid();
    
    //An Array to hold reference to all the UI objects in the Tree
    //change it to a HashMap so that we can access it by key
    this.uiObjectMap = null;
};
```

To do the insertion, we need to implement the tree build process, which is a more complicated than the dictionary one because of the xpath processing.

```
Tree.prototype.addElement = function(element){

    //case I: root is null, insert the first node
    if (this.root == null) {
        this.root = new NodeObject();
        this.root.id = element.uid;
        this.root.parent = null;
        this.root.domNode = element.domNode;
        this.root.xpath = element.xpath;
        this.root.attributes = element.attributes;
    } else {
        //not the first node, need to match element's xpath with current node's relative xpath starting from the root
        //First, need to check the root and get the common xpath
        var common = this.xpathMatcher.match(this.root.xpath, element.xpath);

        var leftover = this.xpathMatcher.remainingXPath(element.xpath, common);

        if (this.root.xpath == common) {
            //the current node shares the same common xpath as the new node
            //no extra node need to be added for the current node
            //then check current node's children
            if (this.root.children.length == 0) {
                //no children, so create a new child
                if (leftover != null && leftover.length > 0) {
                    //only create the child if there are extra xpath
                    var son = new NodeObject();
                    son.id = element.uid;
                    son.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                    son.attributes = element.attributes;
                    son.domNode = element.domNode;
                    son.parent = this.root;
                    this.root.addChild(son);
                }
            } else {
                //there are children
                this.walk(this.root, element.uid, leftover, element.attributes, element.domNode);
            }

        } else {
            var newroot = new NodeObject();
            newroot.id = "root";
            newroot.xpath = common;
            newroot.parent = null;
            newroot.newNode = true;
            var newxpath = this.xpathMatcher.remainingXPath(this.root.xpath, common);

            if (this.root.id != null && this.root.id == "root") {
                this.root.id = this.uid.genUid(newxpath);
            }
            this.root.xpath = newxpath;
            this.root.parent = newroot;
            newroot.addChild(this.root);

            this.root = newroot;

            if (leftover != null && leftover.length > 0) {
                //only create the child if there are extra xpath
                var child = new NodeObject();
                child.id = element.uid;
                child.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                child.attributes = element.attributes;
                child.domNode = element.domNode;
                child.parent = this.root;
                this.root.addChild(child);
            }
        }
    }
}

Tree.prototype.walk = function(current, uid, xpath, attributes, domnode) {

    if (current.children.length == 0) {
        //there is no children
        if (trimString(xpath).length > 0) {
            //only create the child if there are extra xpath
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attributes;
            child.domNode = domnode;
            child.parent = current;

            current.addChild(child);
        }
    } else {
        var cmp = new Array();
        var maxlen = 0;
        for (var l = 0; l < current.children.length; ++l) {
            var nd = current.children[l];
            var xpt = new XPath();
            xpt.xpath = this.xpathMatcher.match(nd.xpath, xpath);
            xpt.node = nd;
            if (xpt.xpath.length > maxlen) {
                maxlen = xpt.xpath.length;
            }
            cmp.push(xpt);
        }

        //need to handle the situation where there is no common xpath
        if (maxlen == 0) {

            //there is no shared common xpath, add the node directly
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attributes;
            child.domNode = domnode;
            child.parent = current;
            current.addChild(child);
        } else {
            //there are shared common xpath
            var max = new Array();
            for (var m = 0; m < cmp.length; m++) {
                if (cmp[m].xpath.length == maxlen) {
                    max.push(cmp[m])
                }
            }

            var mx = max[0];

            var common = mx.xpath;

            if (mx.node.xpath == common) {

                //The xpath includes the common part, that is to say, we need to walk down to the child
                if (max.length > 1) {
                    //we need to merge multiple nodes into one

                    for (var t = 1; t < max.length; t++) {
                        var cnode = max[t].node;

                        var left = this.xpathMatcher.remainingXPath(cnode.xpath, common);
                        if (left.length > 0) {
                            //have more for the left over xpath
                            cnode.xpath = left;
                            cnode.parent = mx.node;

                            current.removeChild(cnode.id());
                        } else {
                            for (var j = 0; j < cnode.children.length; ++j) {
                                var childNode = cnode.children[j];
                                mx.node.addChild(childNode);
                            }
                            current.removeChild(cnode.id);
                        }
                    }
                }
                this.walk(mx.node, uid, this.xpathMatcher.remainingXPath(xpath, common), attributes, domnode);
            } else {
                //need to create extra node
                var extra = new NodeObject();
                extra.xpath = common;
                extra.parent = current;
                extra.id = this.uid.genUid(common);
                extra.newNode = true;
                current.addChild(extra);
                for (var k = 0; k < max.length; ++k) {
                    var xp = max[k];
                    var cn = xp.node;
                    cn.xpath = this.xpathMatcher.remainingXPath(cn.xpath, common);
                    cn.parent = extra;
                    extra.addChild(cn);
                    current.removeChild(cn.id);
                }

                var ch = new NodeObject();
                ch.id = uid;
                ch.xpath = this.xpathMatcher.remainingXPath(xpath, common);
                ch.attributes = attributes;
                ch.domNode = domnode;
                ch.parent = extra;
                extra.addChild(ch);
            }
        }
    }
}
```

Similarly, we defined a printUI method for the prefix tree.

```
Tree.prototype.printUI = function(){
    this.uiModel = new Array();
    if(this.root != null){
        this.root.printUI(this.uiModel);
        return this.uiModel;
    }
}
```

If you want to understand more about the algorithm, please read the Javascript code at our [Trump](http://aost.googlecode.com/svn/trunk/tools/firefox-plugin/trump) project.

To demonstrate how the algorithm works, assume we have the following UI elements and their corresponding xpaths.

```
A:  "/html/body/table[@id='mt']"
B:  "/html/body/table[@id='mt']/tbody/tr/th[3]"
C:  "/html/body/table[@id='mt']/tbody/tr/th[3]/div"
D:  "/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']"
E:  "/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']"
F:  "/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[2]/td[3]/a"
```

Run the algorithm, you will get the following UI module.

```
ui.Container(UID: 'root', clocator: [:]){
  Container(UID: 'A', clocator: [tag:'table']){
    Container(UID: 'B', clocator: [tag:'th']){
      DIV(UID: 'C', clocator: [tag:'div'])
    }
  }
  Container(UID: 'D', clocator: [tag:'div']){
    Container(UID: 'E', clocator: [id:'resultstable',tag:'table']){
      UrlLink(UID: 'F', clocator: [tag:'a'])
    }
  }
}
```

The generated Trie tree is shown as follows,

http://tellurium-users.googlegroups.com/web/uimodule_trie.png?gda=1D8QO0MAAACsdq5EJRKLPm_KNrr_VHB_XmnSH7TN7DozaffH78zyKasyKjZkDBLKqnneTcDd43IytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg&gsc=b9Q3UQsAAAAUaQdv2RDvtpfZgVA8VbAQ

# Summary #

The UI module generating algorithm in Tellurium UI Module Plugin (Trump) is based on a prefix tree and the procedure is more complicated than the one used in dictionary. In Trump 0.2.0 and later versions, the algorithm will be improved to automatically include some critical UI elements even users do not select them. Generalizing UI elements into UI templates is another big challenge.

# Resources #

  * [Tellurium UI Module Plugin](http://code.google.com/p/aost/wiki/TrUMP)
  * [Trie](http://en.wikipedia.org/wiki/Trie)