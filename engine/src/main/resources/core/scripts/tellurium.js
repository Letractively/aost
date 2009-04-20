//add custom jQuery Selector :te_text()
//

jQuery.extend(jQuery.expr[':'], {
    te_text: function(a, i, m) {
        return jQuery(a).text() === m[3];
    }
});

function Tellurium (){

    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;
    
    this.telluriumCache  = {}; //global variable


};

var tellurium = new Tellurium();

//$(window).unload( function () { Tellurium.telluriumCache  = {}; } );

Tellurium.prototype.locateElementByJQuery = function(locator, inDocument, inWindow){
    var loc = locator;
    var attr = null;
    var isattr = false;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        loc = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }
    var found = jQuery(inDocument).find(loc);
    if (found.length == 1) {
        if (isattr) {
            return found[0].getAttributeNode(attr);
        } else {
            return found[0];
        }
    } else if (found.length > 1) {
        if (isattr) {
            return found.get().getAttributeNode(attr);
        } else {
            return found.get();
        }
    } else {
        return null;
    }
};

Tellurium.prototype.setCacheState = function(flag){
    this.cacheSelector = flag;
};
