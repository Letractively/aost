
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