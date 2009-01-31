
function NodeObject(){

    this.constants = {TAG : "tag"};
    this.id = null;
    this.xpath = null;
    this.attributes = new HashMap();
    this.parent = null;
    this.children = new Array();
    this.ui = new Ui();

    //flag to indicate whether this node is a new generated during the grouping process, i.e., by the Tree algorithm
    this.newNode = false;
    //tag selection state machine
//    this.tagState = null;
    this.tagState = new TagState();
    //common methods to process xpath
//    this.xpathProcessor = null;
    this.xpathProcessor = new XPathProcessor();
    //The filter to remove unwanted attributes
//    this.filter = null;
    this.filter = new Filter();
    //used to store the element tag
    this.tag = null;
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

NodeObject.prototype.printUI = function(layout){
    var hasChildren = false;
        if(this.children.length > 0){
            hasChildren = true;
        }

        var sb = new StringBuffer();

        //get the current level of the node so that we can do pretty print
        var level = this.getLevel();

        for(var i=0; i<level; i++){
            sb.append("\t");
        }
        var type = this.ui.getTypeWithExtra(this.attributes.get(this.constants.TAG), this.attributes, hasChildren);
        sb.append(type).append("(UID: '").append(this.id).append("', clocator: [");

        if(this.attributes.size() == 0){
            sb.append(":");
        }else{
            var count = 0;
            var keySet = this.attributes.keySet();
            var valueSet = this.attributes.valSet();

            for(var s=0; s < keySet.length; ++s){
                if(++count > 1){
                     sb.append(",");
                }
                sb.append(keySet[s]).append(":").append("\"").append(valueSet[s]).append("\"");
            }
        }

        sb.append("]");

        //comment this line out if you do not want xpath to display
//        sb.append("[xpath: ").append(xpath).append("]");

        sb.append(")");

        if(hasChildren){
            sb.append("{");
        }
        sb.append("\n");

//        alert(sb.toString());
        layout.push(sb.toString());
        if(hasChildren){
            for(var a=0; a<this.children.length; ++a){
//                alert("layout before recursion: "+ layout);
                this.children[a].printUI(layout);
            }

        }
//        alert("layout after recursion : " + layout);

        var indent = new StringBuffer();
        if(hasChildren){
            for(var l=0; l<level; l++){
                indent.append("\t");
            }
            indent.append("}\n");
        }
        layout.push(indent.toString());
//        alert("layout : " + layout);
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

//select the approporiate tag from the object's xpath and return the relative xpath upto the selected tag
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
  */
    var attrs = new HashMap();
    if(this.tag != null){
        //add TAG to the attribute set
        attrs.set(this.constants.TAG, this.tag);
    }
    
    return attrs;
}

NodeObject.prototype.processNewNode = function(){
//    alert("Process new node " + this.id);
    if(this.newNode){
        var rxp = this.selectTag();
//        alert("Find tag " + this.tag + " relative xpath " + rxp);

        var axp = this.getAbsoluateXPath(rxp);
//        alert("Find absoluate xpath " + axp);
        this.attributes = this.getNodeAttributes(axp)
//        alert("Set attributes size: " + this.attributes.length);
    }

    for(var i=0; i<this.children.length ; ++i){
        //walk all subtree to process each child node
        var current = this.children[i];
        current.processNewNode();
    }
}

/*
NodeObject.prototype.toString = function(child){
   alert("NodeObject : [ id " + this.id + " xpath : " + this.xpath + " parent : " + this.parent + " attributes : " +this.attributes.showMe()+ " ]");
}
*/

