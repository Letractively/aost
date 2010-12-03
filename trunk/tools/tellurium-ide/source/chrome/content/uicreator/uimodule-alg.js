//New UI Module generation Algorithm

var ContainerTagSet = ["input", "select", "table", "form", "ul", "ol", "dl", "li", "button", "a", "label"];
var TableTagSet = ["tr", "th", "td", "tfoot", "tbody"];

var UimConst = {
    NODE_OBJECT: "nodeObject",
    CHILDREN: "children",
    PARENT: "parent",
    SID: "sid",
    COUNT: "count",
    EXCLUDE: "exclude",
    PARENT_COUNT: "pcount",
    HEIGHT: "UimHeight"
};

function TelluriumDomCache(refIdSetter){
    this.refIdSetter = refIdSetter;
    this.elements = [];
    this.index = {};
    this.reverseIndex = {};
    this.dataIndex = {};
}

TelluriumDomCache.prototype.clear = function(){
    this.elements = [];
    this.index = {};
    this.reverseIndex = {};
    this.dataIndex = {};
};

TelluriumDomCache.prototype.addElement = function(refId, element){
    var index = this.elements.indexOf(element);
    if(index == -1){
        this.elements.push(element);
        index = this.elements.length - 1;
        this.index[refId] = element;
        this.reverseIndex[index] = refId;
    }else{
        logger.warn("Element already existed and its index is " + index);
    }
};

TelluriumDomCache.prototype.addElement = function(element){
    var index = this.elements.indexOf(element);
    if(index == -1){
        this.elements.push(element);
        index = this.elements.length - 1;
        var refId = this.refIdSetter.getRefId();
        this.index[refId] = element;
        this.reverseIndex[index] = refId;
    }else{
        logger.warn("Element already existed and its index is " + index);
    }
};

TelluriumDomCache.prototype.getRefId = function(element){
    var index = this.elements.indexOf(element);
    if(index != -1){
        return this.reverseIndex[index];
    }else{
        logger.warn("Element does not exist");
        return null;
    }
};

TelluriumDomCache.prototype.getElement = function(refId){
    var element = this.index[refId];
    if(element == undefined){
        logger.warn("Cannot find element associated with RefId " + refId);
        return null;
    }else{
        return element;
    }
};

TelluriumDomCache.prototype.setData = function(element, key, val){
    var index = this.elements.indexOf(element);
    if(index != -1){
        var refId = this.reverseIndex[index];
        var data = this.dataIndex[refId];
        if(data == undefined){
            data = {};
            data[key] = val;
            this.dataIndex[refId] = data;
        }else{
            data[key] = val;
        }
    }else{
        logger.warn("Element does not exist");
    }
};

TelluriumDomCache.prototype.setDataByRefId = function(refId, key, val) {
    var data = this.dataIndex[refId];
    if (data == undefined) {
        data = {};
        data[key] = val;
        this.dataIndex[refId] = data;
    } else {
        data[key] = val;
    }
};

TelluriumDomCache.prototype.getData = function(element, key){
    var index = this.elements.indexOf(element);
    if(index != -1){
        var refId = this.reverseIndex[index];
        var data = this.dataIndex[refId];
        if(data == undefined || data == null){
            return null;
        }else{
            return data[key];
        }
    }else{
        logger.warn("Element does not exist");
        return null;
    }
};

TelluriumDomCache.prototype.getDataByRefId = function(refId, key) {
    var data = this.dataIndex[refId];
    if (data == undefined || data == null) {
        return null;
    } else {
        return data[key];
    }
};

TelluriumDomCache.prototype.removeData = function(element, key){
    var index = this.elements.indexOf(element);
    if(index != -1){
        var refId = this.reverseIndex[index];
        var data = this.dataIndex[refId];
        if(data != undefined && data != null){
            delete data[key];
        }
    }else{
        logger.warn("Element does not exist");
    }
};

TelluriumDomCache.prototype.removeDataByRefId = function(refId, key) {
    var data = this.dataIndex[refId];
    if (data != undefined && data != null) {
        delete data[key];
    }
};

TelluriumDomCache.prototype.removeAllData = function(element){
    var index = this.elements.indexOf(element);
    if(index != -1){
        var refId = this.reverseIndex[index];
        this.dataIndex[refId] = null;
    }else{
        logger.warn("Element does not exist");
    }
};

TelluriumDomCache.prototype.removeAllDataByRefId = function(refId) {
    this.dataIndex[refId] = null;
};

function UimAlg(tagObjectArray, refIdSetter){
    this.tagObjectArray = tagObjectArray;
    this.markedNodeArray = new Array();
    this.refIdSetter = refIdSetter;
    this.builder = new Builder();
    this.max = 5;
}

UimAlg.prototype.buildWithRoot = function(root){
    if(this.tagObjectArray && this.tagObjectArray.length > 0){
        for(var i=0; i<this.tagObjectArray.length; i++){
            this.mark(this.tagObjectArray[i]);
        }

        var tree = new Tree();
        tree.root = root;
        tree.document = this.tagObjectArray[0].node.ownerDocument;
        this.addExtra(root);

        return tree;
    }

    return null;
};

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
        this.addExtra(root);

        return tree;
    }

    return null;
};

function ExtraContainerNodeVisitor(){
    this.nodes = new Array();
    this.limit = 10;
    this.contd = true;
}

ExtraContainerNodeVisitor.prototype.visit = function(node){
    var $extras = teJQuery(node.domNode).find("input, a, link, form, select, button, table").filter(":visible");
    var total = this.nodes.length + $extras.size();
    //TODO: check nodes, make sure to exclude the child elements that has been selected
};

function ExtraTextNodeVisitor(){
    this.nodes = new Array();
    this.limit = 10;
    this.contd = true;
}

ExtraTextNodeVisitor.prototype.visit = function(node){

};

UimAlg.prototype.addExtraFor = function(root, sel){
    var $ems = teJQuery(root.domNode).find(sel).filter(":visible");
    if($ems.length >0){
        var count = 0;
        for(var i=0; i<$ems.length; i++){
            var $em = $ems.eq(i);
            if($em.data(UimConst.SID) != undefined && count < this.max){
                count++;
                this.markNode($em.get(0));
            }
        }
    }

};

UimAlg.prototype.addExtra = function(root){
    this.addExtraFor(root, "input");
    this.addExtraFor(root, "form");
    this.addExtraFor(root, "select");
    this.addExtraFor(root, "button");
    this.addExtraFor(root, "table");
    this.addExtraFor(root, "tbody");
    this.addExtraFor(root, "tfoot");
    this.addExtraFor(root, "tr");
    this.addExtraFor(root, "td");
    this.addExtraFor(root, "ol");
    this.addExtraFor(root, "li");
    this.addExtraFor(root, "ul");
    this.addExtraFor(root, "dl");
    this.addExtraFor(root, "div");
    this.addExtraFor(root, "span");
    this.addExtraFor(root, "a");
    this.addExtraFor(root, "link");
};

function TestText(){
    return /[^\s]+/.test(teJQuery(this).text());
}

UimAlg.prototype.reachTop = function($node){    
    var top = $node.data(UimConst.NODE_OBJECT);
    while(top != null && top.parent != null){
        top = top.parent;
    }

    return top;
};

UimAlg.prototype.chooseRoot = function(top){
    var lowest = top;

    while(lowest.newNode && lowest.getChildrenSize() == 1){
        lowest = lowest.children[0];
    }

    if(lowest.getChildrenSize() == 0 && lowest.parent != null){
        lowest = lowest.parent;
        var tag = lowest.domNode.tagName.toLowerCase();
        if(TableTagSet.indexOf(tag) != -1 || tag == "li" || tag == "dd" || tag == "dt"){
            lowest = lowest.parent;    
        }
    }
    
    lowest.parent = null;

    return lowest;
};

UimAlg.prototype.markNode = function(node) {
    var $current = teJQuery(node);
    var nodeObject = $current.data(UimConst.NODE_OBJECT);
    if (nodeObject == undefined || nodeObject == null) {
        nodeObject = new NodeObject();
        nodeObject.buildFromDomNode(node);
        nodeObject.refId = this.refIdSetter.getRefId();
        nodeObject.newNode = true;
        nodeObject.id = this.suggestName(nodeObject.tag, nodeObject.attributes);
        $current.data(UimConst.NODE_OBJECT, nodeObject);
        this.markedNodeArray.push($current);
    }
    var $parent = $current.parent();
    if ($parent.size() > 0) {
        var tag = $parent.get(0).tagName.toLowerCase();
        while (tag != "html" && tag != "body") {
            var pNode = $parent.get(0);

            var childrenSize = $parent.children().size();
            
            if (ContainerTagSet.indexOf(tag) != -1
                    || ((tag == "div" || tag == "span") && (childrenSize > 1
                        || pNode.getAttribute("id") != null
                        || pNode.getAttribute("onclick") != null
                        || pNode.getAttribute("ondblclick") != null
                        || pNode.getAttribute("onchange") != null
                        || pNode.getAttribute("onkeydown") != null
                        || pNode.getAttribute("onkeypress") != null
                        || pNode.getAttribute("onkeyup") != null
                        || pNode.getAttribute("onmousedown") != null
                        || pNode.getAttribute("onmouseout") != null
                        || pNode.getAttribute("onmouseover") != null
                        || pNode.getAttribute("onblur") != null )
                    )) {
                var sid = $parent.data(UimConst.SID);

                var pNodeObject, cNodeObject;

                if (sid == undefined || sid == null) {
                    pNodeObject = new NodeObject();
                    pNodeObject.buildFromDomNode($parent.get(0));
                    pNodeObject.refId = this.refIdSetter.getRefId();
                    pNodeObject.newNode = true;
                    pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                    cNodeObject = $current.data(UimConst.NODE_OBJECT);
                    if(!pNodeObject.exist(cNodeObject)){
                        pNodeObject.addChild(cNodeObject);
                    }
                    $parent.data(UimConst.NODE_OBJECT, pNodeObject);
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
                    pNodeObject = $parent.data(UimConst.NODE_OBJECT);
                    if (pNodeObject == null) {
                        pNodeObject.buildFromDomNode($parent.get(0));
                        pNodeObject.refId = sid;
                        pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                        $parent.data(UimConst.NODE_OBJECT, pNodeObject);
                        this.markedNodeArray.push($parent);
                    }
                    cNodeObject = $current.data(UimConst.NODE_OBJECT);
                    if(!pNodeObject.exist(cNodeObject)){
                        pNodeObject.addChild(cNodeObject);
                    }
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

UimAlg.prototype.mark = function(tagObject) {
    var node = tagObject.node;
    var $current = teJQuery(node);
    var nodeObject = $current.data(UimConst.NODE_OBJECT);
    if (nodeObject == undefined || nodeObject == null) {
        nodeObject = new NodeObject();
        nodeObject.domNode = node;
        nodeObject.refId = tagObject.refId;
        nodeObject.attributes = tagObject.attributes;
        nodeObject.id = this.suggestName(tagObject.tag, tagObject.attributes);
        nodeObject.tag = tagObject.tag;
        $current.data(UimConst.NODE_OBJECT, nodeObject);
        this.markedNodeArray.push($current);
    }
    var $parent = $current.parent();
    if ($parent.size() > 0) {
        var tag = $parent.get(0).tagName.toLowerCase();
        while (tag != "html" && tag != "body") {
            var pNode = $parent.get(0);
            var childrenSize = $parent.children().size();

            if (ContainerTagSet.indexOf(tag) != -1
                    || ((tag == "div" || tag == "span") && (childrenSize > 1
                        || pNode.getAttribute("id") != null
                        || pNode.getAttribute("onclick") != null
                        || pNode.getAttribute("ondblclick") != null
                        || pNode.getAttribute("onchange") != null
                        || pNode.getAttribute("onkeydown") != null
                        || pNode.getAttribute("onkeypress") != null
                        || pNode.getAttribute("onkeyup") != null
                        || pNode.getAttribute("onmousedown") != null
                        || pNode.getAttribute("onmouseout") != null
                        || pNode.getAttribute("onmouseover") != null
                        || pNode.getAttribute("onblur") != null )
                    )) {
                var sid = $parent.data(UimConst.SID);

                var pNodeObject, cNodeObject;

                if (sid == undefined || sid == null) {
                    pNodeObject = new NodeObject();
                    pNodeObject.buildFromDomNode($parent.get(0));
                    pNodeObject.refId = this.refIdSetter.getRefId();
                    pNodeObject.newNode = true;
                    pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                    cNodeObject = $current.data(UimConst.NODE_OBJECT);
                    if(!pNodeObject.exist(cNodeObject)){
                        pNodeObject.addChild(cNodeObject);
                    }
                    $parent.data(UimConst.NODE_OBJECT, pNodeObject);
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
                    pNodeObject = $parent.data(UimConst.NODE_OBJECT);
                    if (pNodeObject == null) {
                        pNodeObject = new NodeObject();
                        pNodeObject.buildFromDomNode($parent.get(0));
                        pNodeObject.refId = sid;
                        pNodeObject.id = this.suggestName(pNodeObject.tag, pNodeObject.attributes);
                        $parent.data(UimConst.NODE_OBJECT, pNodeObject);
                        this.markedNodeArray.push($parent);
                    }
                    cNodeObject = $current.data(UimConst.NODE_OBJECT);
                    if(!pNodeObject.exist(cNodeObject)){
                        pNodeObject.addChild(cNodeObject);
                    }
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

        this.sequence = new Identifier(0);
//        this.sequence.sn = 0;
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