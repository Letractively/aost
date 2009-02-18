function CustomizeNode(){
    this.uid = "";
    this.padding = "";
    this.level = 0;
    this.objectDesc = "";
    this.cssClass = "";
    this.valid = null;

}

CustomizeNode.prototype.toString = function(){
    return "uid : " + this.uid + " desc : " + this.objectDesc+ "cssClass : "+ this.cssClass+ " valid : "+ this.valid;
}