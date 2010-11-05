

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