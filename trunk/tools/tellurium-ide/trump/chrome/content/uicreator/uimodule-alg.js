//New UI Module generation Algorithm
var OptionalTagSet = ["input", "select", "table", "form", "ul", "ol", "button", "a"];
const UimConst = {
    NODEOBJECT: "nodeObject",
    CHILDREN: "children",
    PARENT: "parent",
    SID: "sid",
    EXCLUDE: "exclude"
};

function UimAlg(tagObjectArray, refIdSetter){
    this.tagObjectArray = tagObjectArray;
    this.markedNodeArray = new Array();
    this.refIdSetter = refIdSetter;
    this.builder = new Builder();
}

/*
UimAlg.prototype.build = function(){
    if(this.tagObjectArray && this.tagObjectArray.length > 0){
        for(var i=0; i<this.tagObjectArray.length; i++){
            this.mark(this.tagObjectArray[i]);
        }

        var leave = new Array();
        for(var i=0; i<this.markedNodeArray.length; i++){
            var $node = this.markedNodeArray[i];
            var children = $node.data("children");
            if(children == null || children.length == 0){
                leave.push($node);
            }
        }

        var $leaf = leave[0];

        var $top = this.reachTop($leaf);
        this.chooseRoot($top);

        for(var i=0; i<this.markedNodeArray.length; i++){
            var $node = this.markedNodeArray[i];
            var sid = $node.data("sid");
            var tagObject = this.builder.createTagObject($node.get(0), sid, null);
            var nodeObject = new NodeObject();
            nodeObject.id = suggestName(tagObject);
            nodeObject.refId = $node.data("sid");
            nodeObject.attributes = tagObject.attributes;
            nodeObject.domNode = $node.get(0);
            $node.data("nodeObject", nodeObject);
        }

        for(var i=0; i<this.markedNodeArray.length; i++){
            var $node = this.markedNodeArray[i];
            var nodeObject = $node.data("nodeObject");
            var $parent = $node.data("parent");
            if($parent == undefined || $parent.size() == 0){
                nodeObject.parent = null;
            }else{
                var parentNodeObject = $parent.data("nodeObject");
                nodeObject.parent = parentNodeObject;
            }
            var children = $node.data("children");
            if(children != null && children.length > 0){
                for(var j=0; j<children.length; j++){
                    var $child = children[j];
                    nodeObject.addChild($child.data("nodeObject"));
                }
            }
        }

        var leaf = teJQuery(this.tagObjectArray[0].node).data("nodeObject");
        var tree = new Tree();
        tree.root = leaf;
        var root = leaf.parent;
        while(root != null){
            tree.root = root;
            root = root.parent;
        }
//        tree.root = root;

        return tree;
    }

    return null;
};

UimAlg.prototype.reachTop = function($node){
    var $parent = $node.data("parent");
    var $current = $node;
    while($parent != undefined && $parent != null){
        $current = $parent;
        $parent = $parent.data("parent");
    }

    return $current;
};

UimAlg.prototype.chooseRoot = function($top){
    var children = $top.data("children");
    var optional = $top.data("optional");
    var $parent = $top;
    if(optional && children != null && children.length == 1){
        var $child = children[0];
        children = $child.data("children");
        while(children.length == 1 && $child.data("optional")){
            $parent.data("exclude", true);
            $child.data("parent", null);
            $parent = $child;
            $child = children[0];
        }
    }
};

UimAlg.prototype.mark = function(tagObject){
    var node = tagObject.node;
    var $current = teJQuery(node);
    var children = $current.data("children");
    if(children == null)
        $current.data("children", new Array());
    this.markedNodeArray.push($current);
    var $parent = $current.parent();
    if($parent.size() > 0){
        var tag = $parent.get(0).tagName.toLowerCase();
        while (tag != "html" && tag != "body") {
//            if (OptionalTagSet.indexOf(tag) != -1 || $parent.attr("onchange")
//                    || $parent.attr("onclick") || $parent.attr("ondblclick") ) {
            if (OptionalTagSet.indexOf(tag) != -1){
                var sid = $parent.data("sid");
                if (sid == null) {
                    $parent.data("sid", this.refIdSetter.getRefId());
                    $parent.data("optional", true);
                    var pChildren = new Array();
                    pChildren.push($current);
                    $parent.data("children", pChildren);
                    $current.data("parent", $parent);
                    $current = $parent;
                    this.markedNodeArray.push($parent);
                    $parent = $parent.parent();
                    tag = $parent.get(0).tagName.toLowerCase();
                } else {
                    var pChildren = $parent.data("children");
                    if (pChildren == null) {
                        pChildren = new Array();
                    }
                    pChildren.push($current);
                    $parent.data("children", pChildren);
                    $current.data("parent", $parent);
                    break;
                }

            }else{
                $parent = $parent.parent();
                tag = $parent.get(0).tagName.toLowerCase();
            }
        }
    }
};
*/

UimAlg.prototype.build = function(){
    if(this.tagObjectArray && this.tagObjectArray.length > 0){
        for(var i=0; i<this.tagObjectArray.length; i++){
            this.mark(this.tagObjectArray[i]);
        }

        var $leaf = teJQuery(this.tagObjectArray[0].node);

        var top = this.reachTop($leaf);
        var root = this.chooseRoot(top);

        var tree = new Tree();
        tree.root = root;
        tree.document = this.tagObjectArray[0].node.ownerDocument;

        return tree;
    }

    return null;
};

UimAlg.prototype.reachTop = function($node){
    var nodeObject = $node.data(UimConst.NODEOBJECT);
    var top = nodeObject;
    while(top != null && top.parent != null){
        top = top.parent;
    }

    return top;
};

UimAlg.prototype.chooseRoot = function(top){
    var parent = top;

    if(parent.newNode && parent.getChildrenSize() == 1){
        var child = parent.children[0];
        while(child.newNode && child.getChildrenSize() == 1){
            parent = child;
            child = parent.children[0];
            child.parent = null;
        }
    }

    return child;
};

UimAlg.prototype.mark = function(tagObject) {
    var node = tagObject.node;
    var $current = teJQuery(node);
    var nodeObject = $current.data(UimConst.NODEOBJECT);
    if (nodeObject == null) {
        nodeObject = new NodeObject();
        nodeObject.domNode = node;
        nodeObject.refId = tagObject.refId;
        nodeObject.attributes = tagObject.attributes;
        nodeObject.id = this.suggestName(tagObject.tag, tagObject.attributes);
        nodeObject.tag = tagObject.tag;
        $current.data(UimConst.NODEOBJECT, nodeObject);
        this.markedNodeArray.push($current);
    }
    var $parent = $current.parent();
    if ($parent.size() > 0) {
        var tag = $parent.get(0).tagName.toLowerCase();
        while (tag != "html" && tag != "body") {
            if (OptionalTagSet.indexOf(tag) != -1) {
                var sid = $parent.data(UimConst.SID);
                if (sid == null) {
                    var pNodeObject = new NodeObject();
                    pNodeObject.buildFromDomNode($parent.get(0));
                    pNodeObject.refId = this.refIdSetter.getRefId();
                    pNodeObject.newNode = true;
                    pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                    var cNodeObject = $current.data(UimConst.NODEOBJECT);
                    pNodeObject.addChild(cNodeObject);
                    $parent.data(UimConst.NODEOBJECT, pNodeObject);
                    $parent.data(UimConst.SID, pNodeObject.refId);
                    cNodeObject.parent = pNodeObject;
                    this.markedNodeArray.push($parent);
                    $current = $parent;
                    $parent = $parent.parent();
                    if ($parent == undefined || $parent == null || $parent.size() == 0) {
                        break;
                    } else {
                        tag = $parent.get(0).tagName.toLowerCase();
                    }
                } else {
                    var pNodeObject = $parent.data(UimConst.NODEOBJECT);
                    if (pNodeObject == null) {
                        pNodeObject.buildFromDomNode($parent.get(0));
                        pNodeObject.refId = sid;
                        pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                        $parent.data(UimConst.NODEOBJECT, pNodeObject);
                        this.markedNodeArray.push($parent);
                    }
                    var cNodeObject = $current.data(UimConst.NODEOBJECT);
                    pNodeObject.addChild(cNodeObject);
                    cNodeObject.parent = pNodeObject;

                    break;
                }
            } else {
                $parent = $parent.parent();
                if($parent == undefined || $parent == null || $parent.size() == 0){
                    break;
                }else{
                    tag = $parent.get(0).tagName.toLowerCase();
                }
            }
        }
    }
};

UimAlg.prototype.suggestName = function(tag, attributes){

    var name = attributes.get("id");
    if(name == null || name.length == 0){
        name = attributes.get("value");
    }
    if(name == null || name.length == 0){
        name = attributes.get("name");
    }
    if(name == null || name.length == 0){
        name = attributes.get("title");
    }
    if(name == null || name.length == 0){
        name = attributes.get("text");
    }
    if(name == null || name.length == 0){
        name = attributes.get("class");
    }
    if(name == null || name.length == 0){
        if(tag == "input"){
            var type = attributes.get("type");
            if(type == "text"){
                name = "Input";
            }else if(type == "submit"){
                name = "Submit";
            }else if(type == "image"){
                name = "Image";
            }else if(type == "checkbox"){
                name = "Option";
            }else if(type == "radio"){
                name = "Option";
            }else if(type == "password"){
                name = "Password";
            }else{
                name = "Button";
            }
        }else if(tag == "a" || tag == "link"){
            name = "Link";
        }else if(tag == "select"){
            name = "Select";
        }else if(tag == "tr"){
            name = "Section";
        }else if(tag == "td"){
            name = "Part";
        }else if(tag == "th"){
            name = "Header";
        }else if(tag == "tfoot"){
            name = "Footer";
        }else if(tag == "tbody"){
            name = "Group";
        }else if(tag == "form"){
            name = "Form";
        }else if(tag == "image"){
            name = "Image";
        }else if(tag == "table"){
            name = "Table";
        }
    }
    if(name != null && name.length > 0){
        var split = name.split(" ");
        if(split.length > 1){
            name = split[0].toCamel() + split[1].toCamel();
        }
    }

    if(name != null && name.length > 0){
        //remove special characters and only keep alphanumeric and "_"
        name = name.replace(/[^a-zA-Z_0-9]+/g,'');
    }

    if(name == null || name.trim().length == 0){
        name = tag;
    }

    return name.toCamel();
};


//DOM Node
function DNode(){
    this.id = null;
    this.parent = null;
    this.children = new Array();
    this.tagObject = null;
    //reference to the DTree
//    this.treeRef = null;
}

DNode.prototype.updateIndex = function(index){
    index.put(this.id, this);
    if(this.children != null && this.children.length > 0){
        for(var i=0; i< this.children.length; i++){
            this.children[i].updateIndex(index);
        }
    }
};

DNode.prototype.findRoot = function(){
    if(this.parent != null)
        return this.parent.findRoot();

    return this;
};

DNode.prototype.addChild = function(dNode){
    this.children.push(dNode);
};

DNode.prototype.findChild = function(uid){
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            if(this.children[i].id == uid){
                return this.children[i];
            }
        }
    }
};

DNode.prototype.hasChild = function(uid){
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            if(this.children[i].id == uid){
                return true;
            }
        }
    }

    return false;
};

//Dom Tree
function DTree(){
    this.root = null;
    //index from DNode id to DNode
    this.index = new Hashtable();
}

DTree.prototype.isExistParent = function(uid){
    if(this.root != null){
        var $parent = teJQuery(this.root.tagObject.node).closest(":data(uid," + uid + ")");
        return $parent.size() > 0;
    }

    return false;
};

DTree.prototype.updateIndex = function(){
    if(this.root != null){
        this.root.updateIndex(this.index);
    }
};

DTree.prototype.appendIndex = function(newNodes){
    if(newNodes != null && newNodes.length > 0){
        for(var i=0; i<newNodes.length; i++){
            this.index.put(newNodes[i].id, newNodes[i]);
        }
    }
};

DTree.prototype.find = function(uid){
    return this.index.get(uid);
};

function LevelComparator(){
    this.DELIMITER = "/";
}

//variable a, b are TagObjects
LevelComparator.prototype.compare = function(a, b){
    var la = a.xpath.split(this.DELIMITER).length;
    var lb = b.xpath.split(this.DELIMITER).length;

    return la > lb;
};


//Tellurium UI Module Generating Algorithm (TUG)
var TugAlg = Class.extend({
    init: function() {
        this.queue = new PriorityQueue();
        this.queue.comparator = new LevelComparator();

        //each candidate is a DTree
        this.candidates = new Array();
        //Generated UI Modules
        this.uiModules = new Array();
        
        this.tagObjectIndex = new Hashtable();

        this.builder = new Builder();

        this.sequence = new Identifier();
        this.sequence.sn = 0;
    },

    prepare: function(tagObjects){
        if(tagObjects != null && tagObjects.length > 0){
            for(var i=0; i<tagObjects.length; i++){
                //insert tag objects into a priority queue so that we can retrieve the max level element first
                this.queue.insert(tagObjects[i]);
                this.tagObjectIndex.put(tagObjects[i].uid, tagObjects[i]);
            }
        }
    },

    isProcessed: function(uid){
        if(this.candidates.length == 0)
            return false;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return true;
        }

        return false;
    },

    findProcessed: function(uid){
        if(this.candidates.length == 0)
            return null;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return obj;
        }

        return null;
    },

    findDTree: function(uid){
        if(this.candidates.length == 0)
            return null;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return this.candidates[i];
        }

        return null;
    },

    findTagObject: function(uid){
        return this.tagObjectIndex.get(uid);
    },

    build: function() {
        while (this.queue.size() > 0) {
            var max = this.queue.extractMax();
            var newNodes = new Array();

            if (max != null) {
                if (!this.isProcessed(max.uid)) {
                    var current = new DNode();
                    current.id = max.uid;
                    current.tagObject = max;
                    newNodes.push(current);

                    var $closest = teJQuery(max.node).closest(':data(uid)');
                    var needNewDTree = true;

                    while ($closest.size() > 0) {
                        var uid = $closest.eq(0).data("uid");
                        var processed = this.findProcessed(uid);
                        if (processed == null) {
                            //Not in candidate list
                            var parent = new DNode();
                            parent.id = uid;
                            parent.children.push(current);
                            parent.tagObject = this.findTagObject(uid);
                            newNodes.push(parent);
                            $closest = teJQuery(parent.tagObject.node).closest(':data(uid)');
                            current = parent;
                        } else {
                            //already processed, attach to it
                            if (!processed.hasChild(current.id)) {
                                processed.addChild(current);
                            }
                            current = processed;
                            // do not need to traverse up the DOM further
                             needNewDTree = false;

                            var dt = this.findDTree(uid);
                            dt.appendIndex(newNodes);
                            break;
                        }
                    }

                    if(needNewDTree){
                        var dTree = new DTree();
                        dTree.root = current.findRoot();
                        dTree.appendIndex(newNodes);
                        this.candidates.push(dTree);
                    }
                }

            }
        }
    },

    merge: function(){
        var candidate = this.candidates[0];

        var $current = teJQuery(candidate.root.tagObject.node);
        var $parent = $current.parent();

        while($parent.size() > 0){
            var i;

            $parent.data("uid", "newroot");
            var found = true;

            for(i=1; i<this.candidates.length; i++){
                if(!this.candidates[i].isExistParent("newroot")){
                    found = false;
                }
            }

            if(!found){
                $current = $parent;
            }else{
                var tagObj = this.builder.createTagObject($parent.get(0), "newroot");

                var root = new DNode();
                root.parent = null;
                root.id = "newroot";
                root.tagObject = tagObj;
                for(i=0; i<this.candidates.length; i++){
                    root.addChild(this.candidates[i].root);
                }

                var dTree = new DTree();
                dTree.root = root;
                dTree.updateIndex();

                this.candidates = new Array();
                this.candidates.push(dTree);
                
                break;
            }
        }
    },

    suggestName: function(tagObject){
        //Id is unique, consider it first
        var id = tagObject.getAttribute("id");
        if(id != null)
            return id;
        var name = tagObject.getAttribute("name");
        if(name != null)
            return name;

        return tagObject.tag + this.sequence.next();   
    },

    transform: function(dTree){
        
    }

});