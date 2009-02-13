const MYCLASS = "myClass";

function NodeObject(){

    this.constants = {TAG : "tag"};
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

    //flag to indicate whether this node is a new generated during the grouping process, i.e., by the Tree algorithm
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
}

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

NodeObject.prototype.normalizeXPath = function(xpath) {
    //check if the xpath starts with "//"
    if (xpath != null && (!this.xpathProcessor.startWith(xpath, "//"))) {
        xpath = "/" + xpath;
    }

    return xpath;
}

NodeObject.prototype.findNodeXPath = function(){
    var xp = this.walkUp();
    xp = this.normalizeXPath(xp);
//    logger.debug("The XPath for Node " + this.id + " is " + xp);

    return xp;
}

NodeObject.prototype.validateXPath = function(){
    var xp = this.findNodeXPath();
    //validate the generated xpath from the DOM
    var num = this.xpathProcessor.checkXPathCount(this.domNode.ownerDocument, xp);
    if(num != 1){
        logger.warn("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, failed validation");
    }else{
        logger.debug("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, passed validation");
    }
    
    if (this.children.length > 0) {
        for (var i = 0; i < this.children.length; ++i) {
            this.children[i].validateXPath();
        }
    }
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

//canonical ID start from the root
NodeObject.prototype.canonUID = function(){
    var current = this;

    if(current.parent != null){
        return current.parent.canonUID() + "." + this.id;
    }

    return this.id;
}

NodeObject.prototype.buildUiObject = function(){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

//    this.uiobject.buildUiObject(this.id, this.attributes, hasChildren);
    this.uiobject.buildUiObject(this, hasChildren);

    if (hasChildren) {
        for (var i = 0; i < this.children.length; ++i) {
            this.children[i].buildUiObject();
        }
    }
}

NodeObject.prototype.refUiObject = function(uiMap){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    var canonuid = this.canonUID();

    uiMap.set(canonuid, this.uiobject);

    if (hasChildren) {
        for (var i = 0; i < this.children.length; ++i) {
            this.children[i].refUiObject(uiMap);
        }
    }
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
        for (var i = 0; i < this.children.length; ++i) {
            this.children[i].printUI(layout);
        }

        var strobjft = this.uiobject.strUiObjectFooter(level);
        layout.push(strobjft);
    }
}

NodeObject.prototype.buildXML = function(xml){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    //get the current level of the node so that we can do pretty print
    var level = this.getLevel();
    var padding = this.uiobject.paddingByLevel(level+1);
    var descobj = this.uiobject.descObject();

    var myclass = "class=\"" + MYCLASS + level + "\"";
    var myUID = "id=\"" + this.canonUID() + "\"";

    if (hasChildren) {
        xml.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + ">\n");

        for (var i = 0; i < this.children.length; ++i) {
            this.children[i].buildXML(xml);
        }
        xml.push(padding + "</UiObject>\n");
    }else{
        xml.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + "/>\n");
    }
}

NodeObject.prototype.checkNodeId = function(){
    //Children's names must be unique
    if(this.children != null && this.children.length > 1){
        var map = new HashMap();
        var count = 2;
        for(var i=0; i<this.children.length; ++i){
            var cid = this.children[i].id;

            if(map.get(cid) != null){
                //found duplicated ids
                this.children[i].id = this.children[i].id + new String(count);
                count++;
                logger.warn("Found duplicated name " + cid + " change the second one to " + this.children[i].id);
            }
            map.set(this.children[i].id,  "1");
        }
    }

    if(this.children != null && this.children.length > 0){
        for(var c=0; c<this.children.length; ++c){
            this.children[c].checkNodeId();
        }
    }
}

NodeObject.prototype.isEmpty = function(){
    return (this.children != null && this.children.length > 0);
}

NodeObject.prototype.addChild = function(child){
    this.children.push(child);
}

NodeObject.prototype.removeChild = function(uid){
    var child = this.findChild(uid);

    if (child != null) {
        var index = this.children.indexOf(child);
        this.children.splice(index, 1);
    }
}

NodeObject.prototype.findChild = function(uid){
    var current;

    for(var i=0; i<this.children.length ; ++i){
        current = this.children[i];
        if(current.id == uid){
            return current;
        }
    }
    return null;
}

//based on the xpath for the node, set the header and trailer
//i.e,
//     header + node's tag + trailer
NodeObject.prototype.setHeaderTrailerForRegularNode = function(){
    this.header = this.xpathProcessor.popXPath(this.xpath);
    this.trailer = null;
}

NodeObject.prototype.isNewNode = function(){
    return this.newNode;
}

NodeObject.prototype.getAbsoluateXPath = function(lastXPath){
    var axp = lastXPath;

    if(axp == null)
        axp = "";
    var current = this;
    while(current.parent != null){
        current = current.parent;
        axp = current.xpath + axp;
    }

    return axp;
}

//reverse tag list, the tag of the node you want to find
NodeObject.prototype.findSelectedNode = function(rtaglist, tag){
    
    if(this.children == null || this.children.length <0){
        logger.error("The Node " + this.id + " does not have any children");
        return null;
    }else{
        var tags = this.xpathProcessor.getTags(this.xpath + this.children[0].header);
        var rtags = this.xpathProcessor.reverseList(tags);

        //For child 0, we need to walk up extra nodes to account for its own xpath 
        var inx = this.xpathProcessor.findTagIndex(rtaglist, tag) + (rtags.length - rtaglist.length);
        
        var current = this.children[0].domNode;
        if(current == null){
            logger.error("Child" + this.children[0].id + " DomNode is null");
        }
        for(var i=0; i<=inx; i++){
            if(current.parentNode != null){
                current = current.parentNode;
                var lowerCaseNodeName = getNodeName(current).toLowerCase();
                if(lowerCaseNodeName != rtags[i]){
                    logger.error("Node tag " + lowerCaseNodeName + " does not match expected tag " + rtags[i]);
                    return null;
                }
            }else{
                logger.error("Cannot find the node for tag " + rtags[i]);
                return null;
            }
        }
        this.domNode = current;
        //set the header and trailer
        var rinx = rtaglist.length - this.xpathProcessor.findTagIndex(rtaglist, tag) - 2;
        this.header = this.xpathProcessor.getSubXPath(this.xpath, rinx);
        
        return this.domNode;
    }
}

NodeObject.prototype.populateAttributes = function(){
    this.attributes = this.filter.getNotBlackListedAttributes(this.domNode.attributes);
    this.attributes.set(this.constants.TAG, this.tag);
}

NodeObject.prototype.selectTag = function(){

    var tags = this.xpathProcessor.getTags(this.xpath);

    if(tags != null && tags.length > 0){
        //revese the tag list so that we start to search from the last one
        var rtags = this.xpathProcessor.reverseList(tags);

        var tag = this.tagState.selectTagByPriority(rtags);

        if(tag != null){
            //if we found the high priority tag, return the relative xpath upto that tag
            this.tag = tag;

        }else{
            //cannot find the tag, use the last one
            this.tag = tags[tags.length - 1];
        }
        
        this.findSelectedNode(rtags, this.tag);
        this.populateAttributes();
    }
}

NodeObject.prototype.processNewNode = function(){
    //should process children first so that leaf node will be processed first
    //otherwise, we cannot walk from any child to the tag node we select
    for(var i=0; i<this.children.length ; ++i){
        //walk all subtree to process each child node
        var current = this.children[i];
        current.processNewNode();
    }

    if(this.newNode){
        this.selectTag();

    }else{
        this.setHeaderTrailerForRegularNode();
    }

}

/*
NodeObject.prototype.toString = function(child){
   alert("NodeObject : [ id " + this.id + " xpath : " + this.xpath + " parent : " + this.parent + " attributes : " +this.attributes.showMe()+ " ]");
}
*/

