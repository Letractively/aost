
function Decorator(){
    this.bgColor = "#A9DA92";
    this.noBgColor = "";
}

Decorator.prototype.addBackground = function(node){
    node.style.backgroundColor = this.bgColor;
}

Decorator.prototype.removeBackground = function(node){
    node.style.backgroundColor = this.noBgColor;    
}

Decorator.prototype.addOutline = function(node){
    node.style.outline = "2px solid #000";
}

Decorator.prototype.removeOutline = function(node){
    node.style.outline = "";
}