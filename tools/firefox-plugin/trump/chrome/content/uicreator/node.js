
function NodeObject(){
    this.tag = "tag";
    this.id = null;
    this.xpath = null;
    this.attributes = new Array();
    this.parent = null;
    this.children = new Array();

}

NodeObject.prototype.getLevel = function(){
    var level = 0;
    var current = this;
    while(current.parent != null){
        level++;
        current = current.parent;
    }
}

NodeObject.prototype.printUI = function(){

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