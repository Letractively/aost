
function NodeObject(){

    this.constants = {TAG : "tag"};
    this.id = null;
    this.xpath = null;
    this.attributes = new HashMap();
    this.parent = null;
    this.children = new Array();
    this.ui = new Ui();

}

NodeObject.prototype.getLevel = function(){
    var level = 0;
    var current = this;
    while(current.parent != null){
        level++;
        current = current.parent;
    }
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
            sb.append("  ");
        }
        var type = this.ui.getType(this.attributes.get(this.constants.TAG), hasChildren);
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
            for(var i=0; i<level; i++){
                indent.append("  ");
            }
            indent.append("}");
//            alert(indent.toString());
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

/*
NodeObject.prototype.toString = function(child){
   alert("NodeObject : [ id " + this.id + " xpath : " + this.xpath + " parent : " + this.parent + " attributes : " +this.attributes.showMe()+ " ]");
}
*/

