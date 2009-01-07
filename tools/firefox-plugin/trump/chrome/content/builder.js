
var constants = {
    ELEMENT_TYPE_NODE : 1
}

var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "onclick"]

function Builder(){
    this.bundle = $("string");
}

/**
 * Gets the bunde to use
 */
Builder.getBundle = function(){
    return document.getElementById("strings");
}

Builder.getFormattedString = function(key){

}

Builder.prototype.createTagObject = function(node){
    var lowerCaseNodeName ;
    var attributes;
    var parent;
    var xpath;
    //Check if its an ELEMENT TYPE NODE
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        lowerCaseNodeName = getNodeName(node).toLowerCase();
        attributes = getNotBlackListedAttributes(node.attributes);
        parent = node.parentNode;

        //TODO xpath creation
        xpath = createXPath(node);

        return this.buildTagObject(node, lowerCaseNodeName, attributes, parent, xpath);
    }

    return null;
}

Builder.prototype.buildTagObject = function(node, tag, attributes, parent, xpath){
    var tagObject = new TagObject();
    tagObject.node = node;
    tagObject.tag = tag;
    tagObject.attributes = attributes;
    tagObject.parent = parent;
    tagObject.xpath = xpath;

    return tagObject;
}

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
            attr+=", "
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
}

function getNotBlackListedAttributes(attributes){
    var wantedAttributes = new Array();
    for(var i=0; i < attributes.length; ++i){
        if(isNotBlackListed(attributes[i].name)){
            wantedAttributes.push(attributes[i]);
        }
    }
    return wantedAttributes;
}

function isNotBlackListed(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
}

