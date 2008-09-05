var constants = {
    ELEMENT_TYPE_NODE : 1,
    INPUT_NODE : "INPUT"
}

function init(){
    var nodeState = window.opener.nodeState;

    var clickedNode = nodeState.currentNode ;
    var bundle = getBundle();

    var tag = null;
    var attributeString = null;
    var uid = null;
    var uiText = null;
    var propertyKey = null;

    //alert("bundle : " + bundle);

    var nodeType = getNodeType(clickedNode);

    //Check if its an ELEMENT TYPE NODE
    if (nodeType == constants.ELEMENT_TYPE_NODE) {
        var nodeName = getNodeName(clickedNode);

        tag = bundle.getFormattedString("TAG", [nodeName]);
        attributeString = getAttributesString(clickedNode);
        //alert("attribute : " + attributeString);

        //If its an Input node, check the type
        if(nodeName == constants.INPUT_NODE){
            uid = createInputUID(getInputNodeType(clickedNode))
            propertyKey = getInputNodeAsProperty(clickedNode);
            uiText = bundle.getFormattedString(propertyKey, ["\""+uid+"\"", attributeString]);
        }else{
            //TODO other node types
            uid = "otherNode"
            propertyKey = nodeName;
            attributeString = tag + attributeString;
            uiText = bundle.getFormattedString(propertyKey, [uid, attributeString]);
        }

        //alert("string : " + uiText);
        updateUIModelText(uiText);
    }
}

/**
 * Returns the node type
 * @param node
 */
function getNodeType(node){
    return node.nodeType;
}

/**
 * Returns the node name
 * @param node
 */
function getNodeName(node){
    return node.nodeName;
}

function getInputNodeType(node){
    var type = node.getAttribute("type");
    return (type != null) ? type : "text"
}

function createInputUID(type){
    var uid = "inputBox1";
    switch(type){
        case "button" :uid = "button1"
            break;
        case "checkbox" : //TODO
            break;
        case "file" : //TODO
            break;
        case "hidden" : //TODO
            break;
        case "image" ://TODO
            break;
        case "password" : uid = "password1"
            break;
        case "radio" : //TODO
            break;
        case "submit" : uid = "submitButton1"
            break;
        case "text" : break;
        default : break;
    }
    return uid;
}

function getInputNodeAsProperty(node){
    var inputProperty = "INPUT"
    var type = node.getAttribute("type");
    if(type != null){
        switch(type){
            case "button" ://TODO
                break;
            case "checkbox" : //TODO
                break;
            case "file" : //TODO
                break;
            case "hidden" : //TODO
                break;
            case "image" ://TODO
                break;
            case "password" : //TODO
                break;
            case "radio" : //TODO
                break;
            case "submit" : inputProperty+=".SUBMIT"
                break;
            case "text" : //TODO
                break;
            default : break;

        }
    }
    return inputProperty;
}

/**
 *  Iterates thru the attributes of a node
 *  and creates and returns a string in key1:value1, key2:value2
 * @param node
 */
function getAttributesString(node){
    var attributes = node.attributes;
    var attr="";
    for(var i=0; i < attributes.length; ++i){
        if(i != 0){
            attr+=", "
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
}

/**
 * Gets the bunde to use
 */
function getBundle(){
    return document.getElementById("strings");
}

/**
 * Updates the value of the text box
 * text is the UI Model for the node
 * @param text
 */
function updateUIModelText(text){
    var textNode = document.getElementById('uiModelText');
    textNode.value= text;
}