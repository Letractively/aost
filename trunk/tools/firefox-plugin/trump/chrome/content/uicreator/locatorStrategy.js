/**
 
    Function to build xpath using locator

 **/

function LocatorStrategy(){

    this.xpathBuilder = new XPathBuilder();
    
}

LocatorStrategy.prototype.compositeLocate = function(cnode){
    var locator = cnode.uiobject.locator;
//    alert("UI object " + cnode.uiobject.uid + "'s clocator has attribute " + locator.strLocator());
    
//    var xp = this.xpathBuilder.buildOptionalXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes);
    var xp = this.xpathBuilder.buildOptionalXPathVHeader(locator.tag, locator.text, locator.position, locator.direct, locator.attributes, locator.header);

//    if (locator.header != null && (trimString(locator.header).length > 0)) {
//        xp = locator.header + xp;
//    }

    if (locator.trailer != null && (trimString(locator.trailer).length > 0)) {
        xp = xp + locator.trailer
    }

    return xp;
};

LocatorStrategy.prototype.groupLocate = function(cnode){
    var groupAttributes = new Array();

    //first get current node's children and its child UI objects
    if(cnode.children != null && cnode.children.length > 0){

        for(var i=0; i<cnode.children.length; i++){
            var uiobj = cnode.children[i].uiobject;
            var cloc = uiobj.locator;
            
            var gattr;
            if(cloc.direct){
                gattr = this.xpathBuilder.buildChildXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes);
            }else{
                gattr = this.xpathBuilder.buildDescendantXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes);
            }
      
            groupAttributes.push(gattr);
        }
    }
    
    var cloc = cnode.uiobject.locator;
//    var xp = this.xpathBuilder.buildGroupXPath(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes, groupAttributes);
    var xp = this.xpathBuilder.buildGroupXPath(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes, groupAttributes, cloc.header);
    if(cloc.header != null && trimString(cloc.header).length > 0){
        xp = cloc.header + xp;
    }
    if(cloc.trailer != null && trimString(cloc.trailer).length > 0){
        xp = xp + cloc.trailer;
    }

    return xp;
};