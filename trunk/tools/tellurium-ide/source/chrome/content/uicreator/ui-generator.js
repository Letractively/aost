
var constants = {
    ELEMENT_TYPE_NODE : 1
};

const UID_PREFIX = "UI_";

var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "autocomplete", "nowrap"];

//var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text", "href", "src", "position"]
//var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text", "href", "src"];
var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text"];

var eventListAttributes = ["onclick", "ondblclick", "onkeyup", "onkeydown", "onkeypress", "onfocus", "onblur", "onmousedown", "onmouseup", "onmouseover", "onmouseout", "onchange", "onsubmit", "onselect"];

var onclickEvent = "onclick";
var typeAttribute = "type";
var inputImageType = "image";
var srcAttribute = "src";

//composite type
var firstChoiceTags = ["form", "table", "frame", "iframe"];
//list type
var secondChoiceTags = ["tr", "th", "ul", "dl", "ol"];
//possible list type
var thirdChoiceTags = ["td", "dt", "li","a", "span", "div"];
//single element type
var fourthChoiceTags = ["button", "img", "input", "select", "tt" ];

function TagObject(){
    this.name = "";
    this.parent = null;
    this.node = null;
    this.tag = null;
    this.attributes = new Hashtable();
    this.xpath = null;
    this.uid = null;
    this.frameName = null;
    this.refId = null;
}

TagObject.prototype.getAttribute = function(attr){
    if(this.attributes != null){
        return this.attributes.get(attr);
    }

    return null;
};

TagObject.prototype.toString = function(){

    return "TagObject - { node : \"" + this.node + "\" tag : \"" + this.tag + "\" attributes : \""+ this.attributes +
           "\" parent : \""+ this.parent + "\" xpath : \"" + this.xpath +"\" }";
};

function Builder(){
    this.bundle = document.getElementById("string");
}

/**
 * Gets the bunde to use
 */
Builder.getBundle = function(){
    return document.getElementById("strings");
};

Builder.getFormattedString = function(key){

};

Builder.prototype.createTagObject = function(node, refId, frameName){
    var lowerCaseNodeName ;
    var attributes;
    var parent;
    var xpath;
    var name;
    //Check if its an ELEMENT TYPE NODE
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        lowerCaseNodeName = getNodeName(node).toLowerCase();
        attributes = getNotBlackListedAttributes(node.attributes);
        attributes.set("tag", lowerCaseNodeName);
        var txt = this.getText(node);

        if(txt != null && trimString(txt).length > 0){
            attributes.set("text", txt);
        }

        parent = node.parentNode;
        name = getAttributeNameOrId(node);
        //TODO xpath creation
        xpath = createXPath(node);
        return this.buildTagObject(node, lowerCaseNodeName, name, attributes, parent, xpath, refId, frameName);
    }

    return null;
};

Builder.prototype.getText = function(node) {
    var txt = null;
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (node.childNodes[i].nodeType == Node.TEXT_NODE) {
                    txt = node.childNodes[i].nodeValue;
                    if (txt != null) {
                        //test if the regular expression includes "
                        var regexp = new RegExp(/\"/);
                        if(regexp.test(txt)){
                            //if we do have double quota " inside
                            //throw away the text attribute because if it is way too difficult to escape
                            txt = null;
                        }else{
                            txt = this.getTextReg(txt);
                        }

                    }

                    break;
                }
            }
        }
    }

    return txt;
};

Builder.prototype.getTextReg = function(txt) {
    var text = txt.replace(/^ *(.*?) *$/, "$1");
    if (text.match(/\xA0/)) { // if the text contains &nbsp;
        return "regexp:" + text.replace(/[\(\)\[\]\\\^\$\*\+\?\.\|\{\}]/g, function(str) {return '\\' + str;}).replace(/\s+/g, function(str) {
                if (str.match(/\xA0/)) {
                    if (str.length > 1) {
                        return "\\s+";
                    } else {
                        return "\\s";
                    }
                } else {
                    return str;
                }
            });
    } else {
        return text;
    }
};

Builder.prototype.buildTagObject = function(node, tag, name, attributes, parent, xpath, refId, frameName){
    var tagObject = new TagObject();
    tagObject.name = name;
    tagObject.node = node;
    tagObject.tag = tag;
    tagObject.attributes = attributes;
    tagObject.parent = parent;
    tagObject.xpath = xpath;
    tagObject.refId = refId;
    tagObject.frameName = frameName;

    return tagObject;
};

function createXPath(node){
    return generateXPath(node);
}


/**
 *  Iterates thru the attributes of a node
 *  and creates and returns a string in key1:value1, key2:value2
 * @param node
 */
function getAttributesString(node){
    var attributes = getNotBlackListedAttributes(node.attributes);
    var attr="";
    for(var i=0; i < attributes.length; ++i){
        if(i != 0){
            attr+=", ";
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
}

function getNotBlackListedAttributes(attributes){
    var wantedAttributes = new HashMap();
    for(var i=0; i < attributes.length; ++i){
        if(isNotBlackListed(attributes[i].name)){
            wantedAttributes.set(attributes[i].name, attributes[i].value);
        }
    }
    return wantedAttributes;
}

function isNotBlackListed(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
}

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
    this.refId = null;
    this.xpath = null;
    this.attributes = new HashMap();
    this.parent = null;
    this.children = new Array();
    this.ui = new UiType();

    this.header = null;
    this.tailer = null;
    this.nodexpath = null;

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
//    this.uiobject = new NodeUiObject();
    this.uiobject = null;

    this.exclude = false;

//    this.xmlutil = new XmlUtil();
}

NodeObject.prototype.buildFromDomNode = function(domNode){
    var lowerCaseNodeName ;
    var attributes;
    //Check if its an ELEMENT TYPE NODE
    if (getNodeType(domNode) == constants.ELEMENT_TYPE_NODE) {
        lowerCaseNodeName = getNodeName(domNode).toLowerCase();
        attributes = getNotBlackListedAttributes(domNode.attributes);
        attributes.set("tag", lowerCaseNodeName);
        var txt = this.getText(domNode);

        if(txt != null && trimString(txt).length > 0){
            attributes.set("text", txt);
        }

//        this.node = domNode;
        this.domNode = domNode;
        this.tag = lowerCaseNodeName;
        this.attributes = attributes;
    }
};

NodeObject.prototype.getText = function(node) {
    var txt = null;
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (node.childNodes[i].nodeType == Node.TEXT_NODE) {
                    txt = node.childNodes[i].nodeValue;
                    if (txt != null) {
                        //test if the regular expression includes "
                        var regexp = new RegExp(/\"/);
                        if(regexp.test(txt)){
                            //if we do have double quota " inside
                            //throw away the text attribute because if it is way too difficult to escape
                            txt = null;
                        }else{
                            txt = this.getTextReg(txt);
                        }

                    }

                    break;
                }
            }
        }
    }

    return txt;
};

NodeObject.prototype.getTextReg = function(txt) {
    var text = txt.replace(/^ *(.*?) *$/, "$1");
    if (text.match(/\xA0/)) { // if the text contains &nbsp;
        return "regexp:" + text.replace(/[\(\)\[\]\\\^\$\*\+\?\.\|\{\}]/g, function(str) {return '\\' + str})
                                      .replace(/\s+/g, function(str) {
                if (str.match(/\xA0/)) {
                    if (str.length > 1) {
                        return "\\s+";
                    } else {
                        return "\\s";
                    }
                } else {
                    return str;
                }
            });
    } else {
        return text;
    }
};

NodeObject.prototype.clearValidFlag = function(){
    if(this.uiobject != null)
        this.uiobject.isLocatorValid = true;

    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            this.children[i].clearValidFlag();
        }
    }
};

NodeObject.prototype.visitMe = function(visitor){
    visitor.visit(this);
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            this.children[i].visitMe(visitor);
        }
    }
};

NodeObject.prototype.visitAfter = function(visitor){
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            this.children[i].visitAfter(visitor);
        }
    }
    visitor.visit(this);
};

NodeObject.prototype.walkUp = function(){
    var rxp = this.uiobject.buildXPath();

    var xp;

    if(this.parent != null){
        xp = this.parent.walkUp() + rxp;
    }else{
        xp = rxp;
    }

    return xp;
};

NodeObject.prototype.normalizeXPath = function(xpath) {
    //check if the xpath starts with "//"
    if (xpath != null && (!this.xpathProcessor.startWith(xpath, "//"))) {
        xpath = "/" + xpath;
    }

    return xpath;
};

NodeObject.prototype.findNodeXPath = function(){
    var xp = this.walkUp();
    xp = this.normalizeXPath(xp);
//    logger.debug("The XPath for Node " + this.id + " is " + xp);

    return xp;
};

//validate itself and its descendants
NodeObject.prototype.validateXPath = function(){
    var xp = this.findNodeXPath();
    //validate the generated xpath from the DOM
    var num = this.xpathProcessor.checkXPathCount(this.domNode.ownerDocument, xp);
    if(num != 1){
        this.uiobject.isLocatorValid = false;
        logger.warn("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, failed validation");
    }else{
        this.uiobject.isLocatorValid = true;
        logger.debug("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, passed validation");
    }

    if (this.children.length > 0) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].validateXPath();
        }
    }
};

//only validate itself's xpath
NodeObject.prototype.validateNodeXPath = function(){
    var xp = this.findNodeXPath();
    //validate the generated xpath from the DOM
    var num = this.xpathProcessor.checkXPathCount(this.domNode.ownerDocument, xp);
    if(num != 1){
        this.uiobject.isLocatorValid = false;
        logger.warn("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, failed validation");
    }else{
        this.uiobject.isLocatorValid = true;
        logger.debug("The XPath for Node " + this.id + " " + xp + " returned " + num + " nodes, passed validation");
    }
};

NodeObject.prototype.getLevel = function(){
    var level = 0;
    var current = this;

    while(current.parent != null){
        level++;
        current = current.parent;
    }

    return level;
};

//canonical ID start from the root
NodeObject.prototype.canonUID = function(){
    var current = this;

    if(current.parent != null){
        return current.parent.canonUID() + "." + this.id;
    }

    return this.id;
};

NodeObject.prototype.getUid = function(){
    if(this.parent == null)
        return this.id;
    else
        return this.parent.getUid() + "." + this.id;
};

NodeObject.prototype.checkUiDirectAttribute = function(){
    if(this.children.length > 0){
        teJQuery(this.domNode).data("testuid", this.id);
        for(var i=0; i<this.children.length; i++){
            var $child = teJQuery(this.children[i].domNode);
            var puid = $child.parent().data("testuid");
            if(puid == this.id){
                if(this.children[i].uiobject == null){
                    logger.error("The UI object for " + this.children[i].id + " is null");
                }else if(this.children[i].uiobject.locator == null){
                    logger.warn("Locator is null for UI object: " + this.children[i].uiobject.uid);
                }else{
                    this.children[i].uiobject.locator.direct = true;
                }
            }
        }
        teJQuery(this.domNode).removeData("testuid");
    }
};

NodeObject.prototype.checkUiSelfAttribute = function(){
    if(this.children.length > 0){
        teJQuery(this.domNode).data("testuid", this.id);
        for(var i=0; i<this.children.length; i++){
            var $child = teJQuery(this.children[i].domNode);
            var cuid = $child.data("testuid");
            if(cuid == this.id){
                if(this.children[i].uiobject == null){
                    logger.error("The UI object for " + this.children[i].id + " is null");
                }else{
                    this.children[i].uiobject.self = true;
                }
            }
        }
        teJQuery(this.domNode).removeData("testuid");
    }
};

NodeObject.prototype.refUiObject = function(uiMap){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    var canonuid = this.canonUID();

    uiMap.put(canonuid, this.uiobject);

    if (hasChildren) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].refUiObject(uiMap);
        }
    }
};

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
};

NodeObject.prototype.buildXML = function(xml){
    var hasChildren = false;

    if (this.children.length > 0) {
        hasChildren = true;
    }

    //get the current level of the node so that we can do pretty print
    var level = this.getLevel();
    var padding = this.uiobject.paddingByLevel(level+1);
    var descobj = this.uiobject.descObject();

//    this.validateNodeXPath();
    var isXPathValid = this.uiobject.isLocatorValid ? "" : "X";
    var valid = "valid=\"" + isXPathValid + "\"";
    var myclass = "class=\"" + MYCLASS + level + "\"";

    var myUID = "id=\"" + this.canonUID() + "\"";

    if (hasChildren) {
        xml.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + " " + valid + ">\n");

        for (var i = 0; i < this.children.length; i++) {
            this.children[i].buildXML(xml);
        }
        xml.push(padding + "</UiObject>\n");
    }else{
        xml.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + " " + valid + "/>\n");
    }
};

NodeObject.prototype.buildAttributeXml = function(){
    var keySet = this.attributes.keySet();
    var locator = this.uiobject.locator;
    var xmlArray = new Array();
    var xmlBuffer = new StringBuffer();

    for(var i=0 ; i < keySet.length; i++){
        //should not change tag, thus, remove tag from the list
        var key = keySet[i];
        if(key != "tag"){
            var included = false;

            if(locator.isAttributeIncluded(key)){
                included = true;
            }

            xmlArray.push("<attribute name=\""+ key + "\""+ " value=\""+ specialCharacterProof(this.attributes.get(key)) + "\"" + " sel=\"" + included + "\"" + "/>\n");
        }
    }

    var xml = "<?xml version=\"1.0\"?>\n<attributes id=\"attributes_tree_xml\" xmlns=\"\">\n";

    if(xmlArray != null){
        for(var j=0; j<xmlArray.length; j++){
            xmlBuffer.append(xmlArray[j]);
        }
    }


    xml += xmlBuffer.toString();
    xml += "</attributes>\n";

    logger.debug("Attributes XML: \n" + xml);

    return xml;
};

NodeObject.prototype.checkNodeId = function(){
    //Children's names must be unique
    if(this.children != null && this.children.length > 1){
        var map = new HashMap();
        var count = 2;
        for(var i=0; i<this.children.length; i++){
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
        for(var c=0; c<this.children.length; c++){
            this.children[c].checkNodeId();
        }
    }
};

NodeObject.prototype.notEmpty = function(){
    return (this.children != null && this.children.length > 0);
};

NodeObject.prototype.addChild = function(child){
    this.children.push(child);
};

NodeObject.prototype.removeChild = function(uid){
    var child = this.findChild(uid);

    if (child != null) {
        var index = this.children.indexOf(child);
        this.children.splice(index, 1);
    }
};

NodeObject.prototype.findChild = function(uid){
    var current;

    for(var i=0; i<this.children.length ; i++){
        current = this.children[i];
        if(current.id == uid){
            return current;
        }
    }
    return null;
};

NodeObject.prototype.getChildrenSize = function(){
    return this.children.length;
};

//based on the xpath for the node, set the header and trailer
//i.e,
//     header + node's tag + trailer
NodeObject.prototype.setHeaderTrailerForRegularNode = function() {
    this.header = this.xpathProcessor.popXPath(this.xpath);
    this.trailer = null;
    this.nodexpath = this.xpath;

    if (this.header != null && trimString(this.header).length > 0) {
        this.attributes.set(this.constants.HEADER, this.header);
    }
    if (this.trailer != null && trimString(this.trailer).length > 0) {
//        this.attributes.set(this.constants.TRAILER, this.trailer);
    }
};

NodeObject.prototype.isNewNode = function(){
    return this.newNode;
};

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
};

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
        this.nodexpath = this.xpathProcessor.getSubXPath(this.xpath, rinx + 1);
//        this.trailer = this.xpath.substring(this.nodexpath.length+1, this.xpath.length-1);
        this.trailer = this.xpathProcessor.getLastXPath(this.xpath, rinx + 2);

        logger.debug("Select tag " + this.tag + " xpath " + this.xpath + " and its node xpath " + this.nodexpath + " header " + this.header + " trailer " + this.trailer);

        return this.domNode;
    }
};

NodeObject.prototype.populateAttributes = function(){
    this.attributes = this.filter.getNotBlackListedAttributes(this.domNode.attributes);
    this.attributes.set(this.constants.TAG, this.tag);

    if (this.header != null && trimString(this.header).length > 0) {
           this.attributes.set(this.constants.HEADER, this.header);
    }
    if (this.trailer != null && trimString(this.trailer).length > 0) {
//        this.attributes.set(this.constants.TRAILER, this.trailer);
    }
};

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
};

NodeObject.prototype.processNewNode = function(){
    //should process children first so that leaf node will be processed first
    //otherwise, we cannot walk from any child to the tag node we select
    for(var i=0; i<this.children.length ; i++){
        //walk all subtree to process each child node
        var current = this.children[i];
        current.processNewNode();
    }

/*    if(this.newNode){
        this.selectTag();
        this.id = suggestName(this);
    }else{
        this.setHeaderTrailerForRegularNode();
    }*/

//    var pos = this.checkNodePosition();
    var pos = teJQuery(this.domNode).index();
    if(pos != null){
        this.attributes.set(this.constants.POSITION, new String(pos));
    }
};

NodeObject.prototype.checkNodePosition = function(){
//    var pos = this.xpathProcessor.checkPositionForlastXPath(this.xpath);
    return  this.xpathProcessor.checkPositionForlastXPath(this.nodexpath);
};

function ElementObject(){
    this.uid = null;
    this.refId = null;
    this.xpath = null;
    this.domNode = null;
    this.frameName = null;
    this.attributes = new HashMap();
}

function TagState() {


}

TagState.prototype.includeTag = function(tagList, tag){
    if(tag != null && tagList != null){
        for(var i=0; i<tagList.length; i++){
            if(tag == tagList[i]){
                return true;
            }
        }
    }

    return false;
};

TagState.prototype.selectOneTag = function(choiceTags, tags){
    for (var i = 0; i < tags.length; i++) {
        if (this.includeTag(choiceTags, tags[i])) {

            return tags[i];
        }
    }

    return null;
};

TagState.prototype.selectTagByPriority = function(tags){
    if(tags != null){
        var tag = this.selectOneTag(firstChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(secondChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(thirdChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(fourthChoiceTags, tags);

        return tag;
    }

    return null;
};

function Uid(){
    this.constants = {
        UNDEFINED : "undefined",
        SLASH : "/"
    }

}

Uid.prototype.genUid = function(input){
    if(input == null || trimString(input).length == 0){
        return this.constants.UNDEFINED;
    }

    var parts = input.split(this.constants.SLASH);
    var sb = new StringBuffer();
    sb.append("T4");

    for(var i=0; i< parts.length ; ++i){
        var part = parts[i];
        if(trimString(part).length > 0){
            sb.append(part.substring(0,1));
        }
    }

    return sb.toString();
};

function UiType() {
    this.constants = {
        INPUT : "input",
        TYPE : "type",
        CHECKBOX : "checkbox",
        RADIO : "radio",
        SUBMIT : "submit",
        BUTTON : "button",
        IMAGE : "image",
        IFRAME: "iframe"
    };

    this.map = new HashMap();
    this.map.set("divN", "TextBox");
    this.map.set("divY", "Container");
    this.map.set("aN", "UrlLink");
    this.map.set("aY", "Container");
    this.map.set("linkN", "UrlLink");
    this.map.set("linkY", "UrlLink");
    this.map.set("labelN", "TextBox");
    this.map.set("labelY", "Container");
    this.map.set("inputN", "InputBox");
    this.map.set("textareaN", "InputBox");
    this.map.set("textareaY", "Container");
    this.map.set("imgN", "Image");
    this.map.set("selectN", "Selector");
    this.map.set("buttonN", "Button");
    this.map.set("buttonY", "Button");
    this.map.set("spanN", "TextBox");
    this.map.set("spanY", "Container");
    this.map.set("formN", "Form");
    this.map.set("formY", "Form");
//    this.map.set("tableN", "Table");
//    this.map.set("tableY", "Table");
    //for table, use Container for the timebeing until we can do the post processing to handle UI templates
    this.map.set("tableN", "Container");
    this.map.set("tableY", "Container");
    this.map.set("iframeN", "Frame");
    this.map.set("iframeY", "Frame");
}

UiType.prototype.getType = function(tag, hasChildren) {
    return this.getTypeWithExtra(tag, null, hasChildren);
};

UiType.prototype.getTypeWithExtra = function(tag, extra, hasChildren) {
    var addition = "N";
    if (hasChildren) {
        addition = "Y";
    }
    var uitype = this.map.get(tag + addition);

    if (this.map.get(tag + addition) == null) {
        if (hasChildren) {
            uitype = "Container";
        }
        else {
            uitype = "TextBox";
        }
    }

    if (this.constants.INPUT == tag && extra != null) {
        var type = extra.get(this.constants.TYPE);
        if (type != null) {
            if (this.constants.CHECKBOX == type) {
                uitype = "CheckBox";
            } else if (this.constants.RADIO == type) {
                uitype = "RadioButton";
            } else if (this.constants.SUBMIT == type) {
                uitype = "SubmitButton";
            }
        }

        var img = extra.get(this.constants.IMAGE);
        if(img != null){
            uitype = "Button";
        }
    }

    return uitype;
};

//filter out do not wanted Node attributes, most copied from Builder() function
function Filter(){
    this.SEPARATOR = ",";
    this.ATTR_SEPARATOR = ":";
    this.DOJO_ATTACH_EVENT = "dojoAttachEvent";

    this.eventmap =  new HashMap();
    this.eventmap.set("onclick", "click");
    this.eventmap.set("ondblclick", "doubleclick");
    this.eventmap.set("onkeyup", "keyUp");
    this.eventmap.set("onkeydown", "keyDown");
    this.eventmap.set("onkeypress", "keyPress");
    this.eventmap.set("onfocus", "focus");
    this.eventmap.set("onblur", "blur");
    this.eventmap.set("onmousedown", "mouseDown");
    this.eventmap.set("onmouseup", "mouseUp");
    this.eventmap.set("onmouseover", "mouseOver");
    this.eventmap.set("onmouseout", "mouseOut");

    //for dojoAttachEvent
    this.eventmap.set("onmouseenter", "mouseOver");
    this.eventmap.set("onmouseleave", "mouseOut");
}

/**
 *  Iterates thru the attributes of a node
 *  and creates and returns a string in key1:value1, key2:value2
 * @param node
 */
Filter.prototype.getAttributesString = function(node){
    var attributes = this.getNotBlackListedAttributes(node.attributes);
    var attr="";
    for(var i=0; i < attributes.length; ++i){
        if(i != 0){
            attr+=", ";
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
};

Filter.prototype.getNotBlackListedAttributes = function(attributes){
    var wantedAttributes = new HashMap();
    for(var i=0; i < attributes.length; ++i){
        if(this.isNotBlackListed(attributes[i].name)){
            if(this.DOJO_ATTACH_EVENT == attributes[i].name){
                this.processDojoAttachEventAttribute(attributes[i].value, wantedAttributes);
            }else{
                wantedAttributes.set(attributes[i].name, attributes[i].value);
            }
        }
    }

    return wantedAttributes;
};

Filter.prototype.processDojoAttachEventAttribute = function(attrValue, map){
    if(trimString(attrValue).length > 0 ){
        var splited = attrValue.split(this.SEPARATOR);
        for(var i=0; i<splited.length; i++){
            if(trimString(splited[i]).length > 0){
                var pair = trimString(splited[i]).split(this.ATTR_SEPARATOR);
                map.set(trimString(pair[0]), trimString(pair[1]));
            }
        }

    }
};

Filter.prototype.getWhiteListedAttributes = function(attributes){
    var wantedAttributes = new HashMap();
    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            if(whiteListAttributes.indexOf(keys[i]) != -1){
                wantedAttributes.set(keys[i], attributes.get(keys[i]));
//                alert("WhiteListAttribute: " + keys[i] + " " + attributes.get(keys[i]));
            }
        }
    }

    return wantedAttributes;
};

Filter.prototype.isNotBlackListed = function(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
};

Filter.prototype.isWhiteListed = function(attribute){
    return whiteListAttributes.indexOf(attribute) != -1;
};

Filter.prototype.isEventAttribute = function(attribute){
    return eventListAttributes.indexOf(attribute) != -1;
};

Filter.prototype.isEvent = function(attribute){
    var value = this.eventmap.get(attribute);
    return value != null;
};

Filter.prototype.processEventAttributes = function(attributes){
    var respond = new Array();
    var onclickIncluded = false;

    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            if(eventListAttributes.indexOf(key) != -1){
                var event = this.eventmap.get(key);
                if(event != null){
                    respond.push(event);
                }
                if(key == onclickEvent){
                    onclickIncluded = true;
                }
            }
        }
        if(!onclickIncluded){
            if(this.addRespondClick(attributes)){
                respond.push(this.eventmap.get(onclickEvent));
            }
        }
    }

    return respond;
};

Filter.prototype.addRespondClick = function(attributes){
    var imageType = false;
    var imageSrc = false;
    var keys = attributes.keySet();
    for(var i=0; i<keys.length; i++){
        if(keys[i] == typeAttribute && attributes.get(keys[i]) == inputImageType){
            imageType = true;
        }else if(keys[i] == srcAttribute){
            imageSrc = true;
        }
    }

    return imageType && imageSrc;
};

function Tree(){
    this.root = null;
    this.xpathMatcher = new XPathMatcher();
    this.uiModel = new Array();
    this.uid = new Uid();

    //An Array to hold reference to all the UI objects in the Tree
    //change it to a HashMap so that we can access it by key
    this.uiObjectMap = null;
    this.document = null;
}

Tree.prototype.printUI = function(){
    this.uiModel = new Array();
    if(this.root != null){
        this.root.printUI(this.uiModel);
        return this.uiModel;
    }
};

Tree.prototype.buildXML = function(){
    if(this.root != null){
        try{
            var xmlArray = new Array();
            this.root.buildXML(xmlArray);
            var xml = "<?xml version=\"1.0\"?>\n<UIs id=\"customize_tree_xml\" xmlns=\"\">\n";
            xml += this.formatXML(xmlArray);
            xml += "</UIs>";
            //        logger.debug("Generated XML: \n" + xml);
            return xml;
        }catch(error){
            logger.error("Build XML failed:\n" + describeErrorStack(error));
        }
    }

    return DEFAULT_XML;
};

Tree.prototype.formatXML = function(xmlArray){
    var xml = new StringBuffer();
    if(xmlArray != null){
        for(var i=0; i<xmlArray.length; ++i){
            xml.append(xmlArray[i]);
        }
    }

    return xml.toString();
};

//Do some post processing work
Tree.prototype.postProcess = function(){
    if(this.root != null){
        //get the tag and attributes for auto generated nodes
        this.root.processNewNode();

        //check duplicated node ids
        this.root.checkNodeId();
    }
};

Tree.prototype.buildIndex = function(){
    this.uiObjectMap = new HashMap();
    this.root.refUiObject(this.uiObjectMap);
};

Tree.prototype.markInvalidUiObject = function(uid){
    var obj = this.uiObjectMap.get(uid);
    if(obj != null){
        logger.debug("Marking UI object " + uid + " as invalid");
        obj.isLocatorValid = false;
    }else{
        logger.warn("Cannot find UI object " + uid + " from index");
    }
};

Tree.prototype.validateXPath = function() {
    //validate UI object's XPath
    if(this.root != null){
        this.root.validateXPath();
    }else{
        logger.warn("The root node in the Tree is null");
    }
};

Tree.prototype.clearValidFlag = function() {
    if(this.root != null){
        this.root.clearValidFlag();
    }else{
        logger.warn("The root node in the Tree is null");
    }
};

Tree.prototype.addElement = function(element){

    logger.debug("Building Inner Tree -> add Element RefId: "+element.refId + " XPATH: " + element.xpath + " DomNode: " + element.domNode.tagName);

    //case I: root is null, insert the first node
    if (this.root == null) {
        this.root = new NodeObject();
        this.root.id = element.uid;
        this.root.refId = element.refId;
        this.root.parent = null;
        this.root.domNode = element.domNode;
        this.root.xpath = element.xpath;
        this.root.attributes = element.attributes;
        this.document = element.domNode.ownerDocument;
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
                    son.refId = element.refId;
                    son.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                    son.attributes = element.attributes;
                    son.domNode = element.domNode;
                    son.parent = this.root;
 //                   alert("son : " + son);
                    this.root.addChild(son);
                }
            } else {
                //there are children
                this.walk(this.root, element.uid, element.refId, leftover, element.attributes, element.domNode);
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
                child.refId = element.refId;
                child.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                child.attributes = element.attributes;
                child.domNode = element.domNode;
                child.parent = this.root;
                this.root.addChild(child);
            }
        }
    }
};

Tree.prototype.walk = function(current, uid, refId, xpath, attributes, domnode) {

    if (current.children.length == 0) {
        //there is no children
        if (trimString(xpath).length > 0) {
            //only create the child if there are extra xpath
            var child = new NodeObject();
            child.id = uid;
            child.refId = refId;
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
            child.id = uid,
            child.refId = refId;
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
                this.walk(mx.node, uid, refId, this.xpathMatcher.remainingXPath(xpath, common), attributes, domnode);
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
                ch.refId = refId;
                ch.xpath = this.xpathMatcher.remainingXPath(xpath, common);
                ch.attributes = attributes;
                ch.domNode = domnode;
                ch.parent = extra;
                extra.addChild(ch);
            }
        }
    }
};

Tree.prototype.createUiModule = function() {
    var sb = new StringBuffer();
    sb.append("//----------------------- MyUiModule.groovy ------------------------\n\n");
    sb.append("package test\n\n");
    sb.append("import org.telluriumsource.dsl.DslContext\n\n");
    sb.append("/**\n *\tThis UI module file (MyUiModule.groovy) is automatically generated by Tellurium IDE 0.8.0.\n");
    sb.append(" *\tFor any problems, please report to Tellurium User Group at: \n");
    sb.append(" *\t\thttp://groups.google.com/group/tellurium-users\n");
    sb.append(" *\n");
    sb.append(" *\tExample: Google Search Module\n");
    sb.append(" *\n");
    sb.append(" *\t\tui.Container(uid: \"Google\", clocator: [tag: \"td\"]){\n");
    sb.append(" *\t\t\tInputBox(uid: \"Input\", clocator: [title: \"Google Search\"]\n");
    sb.append(" *\t\t\tSubmitButton(uid: \"Search\", clocator: [name: \"btnG\", value: \"Google Search\"]\n");
    sb.append(" *\t\t\tSubmitButton(uid: \"ImFeelingLucky\", clocator: [value: \"I'm Feeling Lucky\"]\n");
    sb.append(" *\t\t}\n");
    sb.append(" *\n");
    sb.append(" *\t\tpublic void doGoogleSearch(String input) {\n");
    sb.append(" *\t\t\tkeyType \"Google.Input\", input\n");
    sb.append(" *\t\t\tclick \"Google.Search\"\n");
    sb.append(" *\t\t\twaitForPageToLoad 30000\n");
    sb.append(" *\t\t}\n");
    sb.append(" *\n");
    sb.append(" */\n\n");
    sb.append("class MyUiModule extends DslContext{\n\n");
    sb.append("\tpublic void defineUi() {\n");
    var uiModelArray = this.printUI();
    if (uiModelArray != undefined && uiModelArray != null) {
        for (var i = 0; i < uiModelArray.length; ++i) {
            if (i == 0) {
                sb.append("\t\tui." + uiModelArray[i]);
            } else {
                sb.append("\t\t   " + uiModelArray[i]);
            }
        }
    }
    sb.append("\t}\n\n");
    sb.append("\t//Add your methods here\n\n");
    sb.append("}\n");

    sb.append("\n\n");
    sb.append("//----------------------- MyTestCase.java ------------------------\n\n");
    sb.append("package test;\n\n");
    sb.append("import org.telluriumsource.test.java.TelluriumJUnitTestCase;\n");
    sb.append("import org.junit.*;\n\n");
    sb.append("/**\n *\tThis test file (MyTestCase.java) is automatically generated by Tellurium IDE 0.8.0.\n");
    sb.append(" *\n");
    sb.append(" */\n\n");
    sb.append("public class MyTestCase extends TelluriumJUnitTestCase {\n");
    sb.append("\tprivate static MyUiModule mum;\n\n");
    sb.append("\t@BeforeClass\n");
    sb.append("\tpublic static void initUi() {\n");
    sb.append("\t\tmum = new MyUiModule()\n");
    sb.append("\t\tnum.defineUi();\n");
    sb.append("\t\tconnectSeleniumServer();\n");
    sb.append("\t\tuseTelluriumEngine(true);\n");
    sb.append("\t}\n\n");
    sb.append("\t//Add your test cases here\n");
    sb.append("\t@Test\n");
    sb.append("\tpublic void testCase(){\n");
    sb.append("\t\t...\n");
    sb.append("\t}\n");
    sb.append("}\n");

    return sb.toString();
};

Tree.prototype.visit = function(visitor){
    if(this.root != null){
        try {
            this.root.visitMe(visitor);
        } catch(error) {
            logger.error("Visit tree failed:\n" + describeErrorStack(error));
        }
    }
};

Tree.prototype.visitAfter = function(visitor){
    if(this.root != null){
        try {
            this.root.visitAfter(visitor);
        } catch(error) {
            logger.error("Visit tree failed:\n" + describeErrorStack(error));
        }
    }
};

Tree.prototype.buildUiObject = function(builder, checker){
    this.visit(builder);
    this.visit(checker);
};

function UiBuilder() {
    this.filter = new Filter();
    this.classifier = new UiType();
    this.uiBuilderMap = new Hashtable();
    this.uiBuilderMap.put("Button", new UiButtonBuilder());
    this.uiBuilderMap.put("CheckBox", new UiCheckBoxBuilder());
    this.uiBuilderMap.put("Div", new UiDivBuilder());
    this.uiBuilderMap.put("Icon", new UiIconBuilder());
    this.uiBuilderMap.put("Image", new UiImageBuilder());
    this.uiBuilderMap.put("InputBox", new UiInputBoxBuilder());
    this.uiBuilderMap.put("RadioButton", new UiRadioButtonBuilder());
    this.uiBuilderMap.put("Selector", new UiSelectorBuilder());
    this.uiBuilderMap.put("Span", new UiSpanBuilder());
    this.uiBuilderMap.put("SubmitButton", new UiSubmitButtonBuilder());
    this.uiBuilderMap.put("TextBox", new UiTextBoxBuilder());
    this.uiBuilderMap.put("UrlLink", new UiUrlLinkBuilder());
    this.uiBuilderMap.put("Container", new UiContainerBuilder());
    this.uiBuilderMap.put("Form", new UiFormBuilder());
    this.uiBuilderMap.put("Frame", new UiFrameBuilder());
    this.uiBuilderMap.put("List", new UiListBuilder());
    this.uiBuilderMap.put("Table", new UiTableBuilder());
    this.uiBuilderMap.put("StandardTable", new UiStandardTableBuilder());
    this.uiBuilderMap.put("Window", new UiWindowBuilder());
    this.uiBuilderMap.put("Repeat", new UiRepeatBuilder());
/*    this.uiBuilderMap.put("UiAllPurposeObject", new UiAllPurposeObjectBuilder());*/
}

UiBuilder.prototype.getAvailableUiTypes = function(){
    return this.uiBuilderMap.keySet().sort();
};

UiBuilder.prototype.visit = function(node) {
    var attributes = node.attributes;
    var tag = attributes.get(CONSTANTS.TAG);
    var uiType = this.classifier.getTypeWithExtra(tag, attributes, node.notEmpty());
    var respond = this.filter.processEventAttributes(attributes);
    if (respond != null && respond.length > 0) {
        if (uiType == "UrlLink" || uiType == "Button" || uiType == "SubmitButton") {
            removeElement(respond, "click");
        }
    }
    var whiteListAttributes = this.filter.getWhiteListedAttributes(attributes);
    var builder = this.uiBuilderMap.get(uiType);
    if (builder != null) {
        var obj = builder.buildFrom(whiteListAttributes, respond);
        obj.uid = node.id;
        obj.refId = node.refId;
        obj.node = node;
        obj.optionalAttributes = node.attributes;
        node.uiobject = obj;
    }else{
        logger.error("Cannot find a UI builder for UI type " + uiType);
    }
};

function UiChecker(){

}

UiChecker.prototype.visit = function(node){
    node.checkUiDirectAttribute();
    node.checkUiSelfAttribute();
};

function UiJSONConverter(){
    this.jsonArray = new Array();
}

UiJSONConverter.prototype.visit = function(node){
    if(node.uiobject != null){
        var jso = node.uiobject.toJSON();
        this.jsonArray.push(jso);
    }else{
        logger.error("The UI Object for node " + node.id + " is null");
    }
};

function UiRefMapper(){
    this.refUidMap = new Hashtable();
}

UiRefMapper.prototype.visit = function(node){
    if(node.refId != null){
        var uid = node.getUid();
        this.refUidMap.put(node.refId, uid);
    }
};

function RefIdSetter(){

    this.alphabet="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    this.timestamp = this.toBase62((new Date()).getTime());

    this.sequence = new Identifier(100);
}

RefIdSetter.prototype.toBase = function(number, base){
    if (typeof(number) == "number" && typeof(base) == "number" && (base > 0)) {
        if (number < base)
            return this.alphabet.charAt(number);

        var out = '';
        var value = number;
        while (value != 0) {
            var remind = value % base;
            value = (value - remind) / base;
            out = this.alphabet.charAt(remind) + out;
        }

        return out;
    }

    return number;
};

RefIdSetter.prototype.toBase36 = function(number){
    return this.toBase(number, 36);
};

RefIdSetter.prototype.toBase62 = function(number){
    return this.toBase(number, 62);
};

RefIdSetter.prototype.getTimestamp = function(){
    return "T" + this.timestamp;
};

RefIdSetter.prototype.getRefId = function(){
    return "T" + this.timestamp + "S" + this.sequence.next();
};

RefIdSetter.prototype.visit = function(node){
    if(node.newNode){
        node.refId = this.getRefId();
    }
};

function removeElement(array, elem){
    var index = array.indexOf(elem);
    if(index >= 0)
        array.splice(index, 1);
}