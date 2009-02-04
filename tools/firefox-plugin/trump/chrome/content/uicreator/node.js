
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
    var gattrs;

    if(this.uiobject.group){
        gattrs = this.uiobject.getGroupAttributes();
    }else{
        gattrs = new Array();    
    }

    var rxp = this.uiobject.buildXPath(gattrs);

    var xp;

    if(this.parent != null){
        xp = this.parent.walkUp() + rxp;
    }else{
        xp = rxp;
    }

    return xp;
}

NodeObject.prototype.getGroupAttributes = function(){
    var gattrs = new Array();
    
    if(this.children != null && this.children.length() > 0){
        for(var nd in this.children){
            gattrs.push(nd.getGroupAttributes());
        }
    }

    return gattrs;
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

    this.uiobject.buildUiObject(this.id, this.attributes, hasChildren);

    if (hasChildren) {
        for (var a = 0; a < this.children.length; ++a) {
            this.children[a].buildUiObject();
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
        for (var a = 0; a < this.children.length; ++a) {
            this.children[a].printUI(layout);
        }

        var strobjft = this.uiobject.strUiObjectFooter(level);
        layout.push(strobjft);
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
 //               alert("Found duplicated name " + cid + " change the second one to " + this.children[i].id);
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
//    alert("Try to find the DOM Node for selected tag " + tag + " XPath: " + this.xpath);
    
    if(this.children == null || this.children.length <0){
        logger.error("The Node " + this.id + " does not have any children");
//        alert("The Node " + this.id + " does not have any children");
        return null;
    }else{
        var tags = this.xpathProcessor.getTags(this.xpath + this.children[0].header);
        var rtags = this.xpathProcessor.reverseList(tags);

        //For child 0, we need to walk up extra nodes to account for its own xpath 
        var inx = this.xpathProcessor.findTagIndex(rtaglist, tag) + (rtags.length - rtaglist.length);
        
        var current = this.children[0].domNode;
        if(current == null){
//            alert("Child" + this.children[0].id + " DomNode is null");
            logger.error("Child" + this.children[0].id + " DomNode is null");
        }
        for(var i=0; i<=inx; i++){
            if(current.parentNode != null){
                current = current.parentNode;
                var lowerCaseNodeName = getNodeName(current).toLowerCase();
                if(lowerCaseNodeName != rtags[i]){
                    logger.error("Node tag " + lowerCaseNodeName + " does not match expected tag " + rtags[i]);
//                    alert("Loop " + i + ", Node tag " + lowerCaseNodeName + " does not match expected tag " + rtags[i]);
                    return null;
                }
            }else{
                logger.error("Cannot find the node for tag " + rtags[i]);
//                alert("Cannot find the node for tag " + rtags[i]);
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
        
//        alert("Find tag " + this.tag + " from a tag list" + tags.length);
        this.findSelectedNode(rtags, this.tag);
        this.populateAttributes();
    }
}

//select the approporiate tag from the object's xpath and return the relative xpath upto the selected tag
/*
NodeObject.prototype.selectTag = function(){
//    alert("Get tags for xpath " + this.xpath);

    var tags = this.xpathProcessor.getTags(this.xpath);

    if(tags != null && tags.length > 0){
        //revese the tag list so that we start to search from the last one 
        var rtags = this.xpathProcessor.reverseList(tags);

        var tag = this.tagState.selectTagByPriority(rtags);

        if(tag != null){
            var inx = this.xpathProcessor.findTagIndex(rtags, tag);
            //if we found the high priority tag, return the relative xpath upto that tag
            this.tag = tag;
            var rinx = tags.length - inx - 1;
//            alert("Find the index for tag " + tag + " as " + rinx);
            return this.xpathProcessor.getSubXPath(this.xpath, rinx);
        }
        //cannot find the tag, use the last one
        this.tag = tags[tags.length - 1];
    }
    
    //otherwise, return the whole xpath, i.e, use the last tag in the xpath tag chain
//    alert("Use the last tag");
    return this.xpath;
}

NodeObject.prototype.getNodeAttributes = function(absoluateXPath){
//    alert("Start to get attributes for xpath " + absoluateXPath);
 //   var nd = document.evaluate('/html/body/center/forma', document, null,  XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
//    var nd = document.evaluate('//table', document, null,  XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);


    //var nd = document.evaluate('//a', document, null,  XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);

/*
    var nodesSnapshot = document.evaluate(absoluateXPath, document, null,  XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
//    var nodesSnapshot = document.evaluate('/html/body/center/form', document, null,  XPathResult.ANY_TYPE, null);

    alert("Found node snapshots " + nodesSnapshot.snapshotLength);
    if (nodesSnapshot.snapshotLength > 1) {
        alert("XPath " + absoluateXPath + " returned " + nodesSnapshot.snapshotLength + " nodes instead of one");
        for (var i = 0; i < nodesSnapshot.snapshotLength; i++)
        {
            dump(nodesSnapshot.snapshotItem(i).textContent);
        }
    }
//      var nd = nodesSnapshot.snapshotItem(0);

//      var attrs = this.filter.getNotBlackListedAttributes(nd.attributes);

    var attrs = new HashMap();
    
    if(this.tag != null){
        //add TAG to the attribute set
        attrs.set(this.constants.TAG, this.tag);
    }
    
    return attrs;
}
*/

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
//        alert("Find tag " + this.tag + " relative xpath " + rxp);

    }else{
        this.setHeaderTrailerForRegularNode();
    }

}

/*
NodeObject.prototype.toString = function(child){
   alert("NodeObject : [ id " + this.id + " xpath : " + this.xpath + " parent : " + this.parent + " attributes : " +this.attributes.showMe()+ " ]");
}
*/

