/**
 
    Function to build xpath using locator

 **/

function LocatorStrategy(){

    this.xpathBuilder = new XPathBuilder();
    
}

LocatorStrategy.prototype.compositeLocate = function(locator){

    var xp = this.xpathBuilder.buildXPath(locator.tag, locator.text, locator.position, locator.attributes);

    if (locator.header != null && (trimString(locator.header).length() > 0)) {
        xp = locator.header + xp;
    }

    if (locator.trailer != null && (trimString(locator.trailer).length() > 0)) {
        xp = xp + locator.trailer
    }

    return xp;
}

LocatorStrategy.prototype.groupLocate = function(cnode){
    var groupAttributes = new Array();

    //first get current node's children and its child UI objects
    if(cnode.children != null && cnode.children.length > 0){

        for(var i=0; i<cnode.children.length; i++){
            var uiobj = cnode.children[i].uiobject;
            var cloc = uiobj.clocator;
            
            var gattr;
            if(cloc.direct){
                gattr = this.xpathBuilder.buildChildXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes);
            }else{
                gattr = this.xpathBuilder.buildDescendantXPath(cloc.tag, cloc.text, cloc.position, cloc.attributes);
            }

            groupAttributes.push(gattr);
        }
    }
    
    var cloc = cnode.uiobject.clocator;
    var xp = this.xpathBuilder.buildGroupXPath(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes, groupAttributes);
    if(cloc.header != null && trimString(cloc.header).length > 0){
        xp = cloc.header + xp;
    }
    if(cloc.trailer != null && trimString(cloc.trailer).length > 0){
        xp = xp + cloc.trailer;
    }

    return xp;
}