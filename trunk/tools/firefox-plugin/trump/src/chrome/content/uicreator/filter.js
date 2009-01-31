var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "onclick", "href", "autocomplete"]

//filter out do not wanted Node attributes, most copied from builder.js
function Filter(){
     
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
            attr+=", "
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
}

Filter.prototype.getNotBlackListedAttributes = function(attributes){
    var wantedAttributes = new HashMap();
    for(var i=0; i < attributes.length; ++i){
        if(this.isNotBlackListed(attributes[i].name)){
            wantedAttributes.set(attributes[i].name, attributes[i].value);
        }
    }
    return wantedAttributes;
}

Filter.prototype.isNotBlackListed = function(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
}
