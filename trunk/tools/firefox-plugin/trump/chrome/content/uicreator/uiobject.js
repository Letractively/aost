
function UiObject(){
    this.constants = {
        TAG : "tag",
        RESPOND : "respond",
        GROUP : "group"
    };
    
    this.uid = null;
    this.uiType = null;
    this.clocator = new Locator();
    this.respond = new Array();
    this.container = false;
    this.group = false;
    //the node associated with this UiObject
    this.node = null;

    this.classifier = new UiType();
    this.filter = new Filter();

    this.locatorStrategy = new LocatorStrategy();
}

UiObject.prototype.buildUiObject = function(node, hasChildren){
    this.node = node;

    var attributes = node.attributes;
    
    this.uid = node.id;

    var tag = attributes.get(this.constants.TAG);

    this.uiType = this.classifier.getTypeWithExtra(tag, attributes, hasChildren);

//    alert("With attributes " + attributes.size() + attributes.showMe() + " UID" + this.uid + " type:" + this.uiType);

    this.respond = this.filter.processEventAttributes(attributes);

//    alert("Build UI object UID:" + this.uid + " tag:" + tag + " respond:" + this.respond.length);

    this.container = hasChildren;
//    this.group = hasChildren;

    var whiteListAttributes = this.filter.getWhiteListedAttributes(attributes);
    
    this.clocator.buildLocator(whiteListAttributes);
    
    return this;
}

//build relative xpath from UiObject's locator
//If group is true, we need to use its children attributes
UiObject.prototype.buildXPath = function(){
    var xp;

    if(this.group){
        xp = this.locatorStrategy.groupLocate(this.node);
    }else{
        xp = this.locatorStrategy.compositeLocate(this.node);
    }

    return xp;
}

//UiObject.prototype.getGroupAttributes = function(){

//    return "";
//}

UiObject.prototype.strUiObject = function(level){
    var sb = new StringBuffer();
    for (var i = 0; i < level; i++) {
        sb.append("\t");
    }
    sb.append(this.uiType).append("(uid: \"").append(this.uid).append("\", ");
    sb.append(this.clocator.strLocator());

    if(this.respond != null && this.respond.length > 0){
        sb.append(", respond: [");
        for(var j=0; j<this.respond.length; j++){
            if(j>0){
                sb.append(", ");
            }
            sb.append("\"").append(this.respond[j]).append("\"");
        }
        sb.append("]");
    }
    if(this.group == true){
        sb.append(", group: \"true\"");
    }
    //comment this line out if you do not want xpath to display
    //        sb.append("[xpath: ").append(xpath).append("]");

    sb.append(")");

    if (this.container) {
        sb.append("{");
    }
    sb.append("\n");

    return sb.toString();
}

UiObject.prototype.strUiObjectFooter = function(level){
    var sb = new StringBuffer();
    
    if (this.container) {
        for (var l = 0; l < level; l++) {
            sb.append("\t");
        }
        sb.append("}\n");
    }

    return sb.toString();
}

UiObject.prototype.addRespond = function(event){
    this.respond.push(event);
}

UiObject.prototype.setUID = function(uid){
    this.uid = uid;
}

UiObject.prototype.setUiType = function(uitype){
    this.uiType = uitype;
}

UiObject.prototype.isContainer = function(){
    this.container = true;
}

UiObject.prototype.notContainer = function(){
    this.container = false;
}

UiObject.prototype.useGroupLocating = function(){
    this.group = true;
}

UiObject.prototype.noGroupLocating = function(){
    this.group = false;
}
