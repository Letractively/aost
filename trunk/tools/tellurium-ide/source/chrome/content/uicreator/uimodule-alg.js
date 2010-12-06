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
    HEIGHT: "UimHeight",
    ROOT: "root",
    PROCESSED: "processed"
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

TelluriumDomCache.prototype.addElementByRefId = function(refId, element){
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

        return refId;
    }else{
        logger.warn("Element already existed and its index is " + index);

        return this.reverseIndex[index];
    }
};

TelluriumDomCache.prototype.getRefId = function(element){
    var index = this.elements.indexOf(element);
    if(index != -1){
        return this.reverseIndex[index];
    }else{
        logger.warn("Element does not exist when call getRefId()");
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
        logger.warn("Element does not exist when call setData()");
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
        logger.warn("Element does not exist when call getData()");
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
        logger.warn("Element does not exist when call removeData()");
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
        logger.warn("Element does not exist when call removeAllData()");
    }
};

TelluriumDomCache.prototype.removeAllDataByRefId = function(refId) {
    this.dataIndex[refId] = null;
};

function UimAlg(tagObjectArray, root, refIdSetter, domCache){
    this.tagObjectArray = tagObjectArray;
    this.refIdSetter = refIdSetter;
    this.domCache = domCache;
    this.builder = new Builder();

    //root node
    this.root = root;

    this.max = 5;
}

UimAlg.prototype.findRoot = function(){
    //TODO: implement this!
    return null;
};

UimAlg.prototype.preBuild = function() {
    if (this.tagObjectArray != null && this.tagObjectArray.length > 0) {
        for (var i = 0; i < this.tagObjectArray.length; i++) {
            var node = this.tagObjectArray[i].node;
            var nodeObject = this.domCache.getData(node, UimConst.NODE_OBJECT);
            if (nodeObject == undefined || nodeObject == null) {
                nodeObject = new NodeObject();
                nodeObject.buildFromDomNode(node);
                nodeObject.refId = this.domCache.getRefId(node);
                //        nodeObject.newNode = true;
                nodeObject.id = this.suggestName(nodeObject.tag, nodeObject.attributes);
                this.domCache.setData(node, UimConst.NODE_OBJECT, nodeObject);
            }
        }
    }
    if(this.root != null){
        var rootNode = this.root.node;
        this.domCache.setData(rootNode, UimConst.ROOT, true);        
    }
};

UimAlg.prototype.buildTree = function() {
    if (this.tagObjectArray != null && this.tagObjectArray.length > 0) {
        for (var i = 0; i < this.tagObjectArray.length; i++) {
            var node = this.tagObjectArray[i].node;
            this.climbFrom(node);
         }
    }
};

UimAlg.prototype.climbFrom = function(node) {
    var processed = this.domCache.getData(node, UimConst.PROCESSED);
    var isRoot = this.domCache.getData(node, UimConst.ROOT);

    if((!processed) && (!isRoot)){
        var current = node;
        var parent = current.parentNode;
        var pNodeObject, cNodeObject;
        while (parent != null) {
            pNodeObject = this.domCache.getData(parent, UimConst.NODE_OBJECT);
            if (pNodeObject != null) {
                cNodeObject = this.domCache.getData(current, UimConst.NODE_OBJECT);
                if (!pNodeObject.exist(cNodeObject)) {
                    pNodeObject.addChild(cNodeObject);
                }
                cNodeObject.parent = pNodeObject;
                this.domCache.setData(current, UimConst.PROCESSED, true);
                current = parent;
                isRoot = this.domCache.getData(parent, UimConst.ROOT);
                processed = this.domCache.getData(parent, UimConst.PROCESSED);
                if (isRoot || processed) {
                    break;
                }
            } else {
                parent = parent.parentNode;
            }
        }
    }       
};

UimAlg.prototype.build = function(){
    if(this.tagObjectArray == null && this.root == null){
        return null;
    }

    if(this.tagObjectArray == null){
        this.tagObjectArray = [];
    }

    if(this.root == null){
        this.root = this.findRoot();
    }

    this.tagObjectArray.push(this.root);
    this.preBuild();
    this.buildTree();
    var tree = new Tree();
    tree.root = this.domCache.getData(this.root.node, UimConst.NODE_OBJECT);
    tree.document = this.tagObjectArray[0].node.ownerDocument;

    return tree;
};

/*
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
*/

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

