//add custom jQuery Selector :te_text()
//

jQuery.extend(jQuery.expr[':'], {
    te_text: function(a, i, m) {
        return jQuery(a).text() === m[3];
    }
});

function DummyLogger(){

};

DummyLogger.prototype.info = function(msg){

};

DummyLogger.prototype.warn = function(msg){

};

DummyLogger.prototype.error = function(msg){

};

DummyLogger.prototype.fatal = function(msg){

};

DummyLogger.prototype.debug = function(msg){

};

DummyLogger.prototype.trace = function(msg){

};

/*
 //and here is the logger!
var jslogger = new Log4js.getLogger("TeEngine");
jslogger.setLevel(Log4js.Level.ALL);
//jslogger.addAppender(new Log4js.MozillaJSConsoleAppender());
jslogger.addAppender(new Log4js.ConsoleAppender());
*/

var jslogger = new DummyLogger();

//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
};

Uiid.prototype.push = function(id){
    this.stack.push(id);
};

Uiid.prototype.pop = function(){
    return this.stack.pop();
};

Uiid.prototype.getUid = function(){
    return this.stack.join(".");
};

Uiid.prototype.size = function(){
    return this.stack.length;
};

Uiid.prototype.convertToUiid = function(id){
    if(id != null && trimString(id).length > 0){
        var ids = id.split(".");
        for(var i= 0; i<ids.length; i++){
            var pp = this.preprocess(ids[i]);
            if(pp.length == 1){
                this.push(pp[0]);
            }else{
                this.push(pp[1]);
                this.push(pp[0]);
            }
        }
    }
};

Uiid.prototype.preprocess = function(id){
    if(id != null && trimString(id).length > 0 && id.indexOf("[") != -1){
        if(id.indexOf("[") == 0){
            var single = id.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = id.indexOf("[");
            var first = id.substring(0, index);
            var second = id.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [id];
};


function MetaCmd(){
    this.uid = null;
    this.cacheable = true;
    this.unique = true;
};

//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference
    this.selector = null;
    //DOM reference
    this.reference = null;
};

function Tellurium (){

    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
//    this.sCache  = {}; //global variable
    this.sCache = new HashMap();

    this.currentWindow = null;

    this.currentDocument = null;
};

var tellurium = new Tellurium();

//$(window).unload( function () { Tellurium.sCache  = {}; } );

Tellurium.prototype.cleanCache = function(){
    this.sCache = new HashMap();
    jslogger.debug("Clean up selector cache");
};

Tellurium.prototype.locateElementByJQuery = function(locator, inDocument, inWindow){

    if(inWindow != this.currentWindow){
        jslogger.debug("Bind cleaning cache to window unload envent " + inWindow);
        jQuery(inWindow).unload(this.cleanCache);
        this.currentWindow = inWindow;
    }

    if(this.currentDocument != null && inDocument != this.currentDocument){
        //force to clean up cache
        this.cleanCache();
        this.currentDocument = inDocument;
    }

    var command = JSON.parse(locator, null);

    var loc = command.locator;
    var metaCmd = new MetaCmd();
    metaCmd.uid = command.uid;
    metaCmd.cacheable = command.cacheable;
    metaCmd.unique = command.unique;
    
    var attr = null;
    var isattr = false;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        loc = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }

    jslogger.debug("Tellurium received locator: " + loc);

    var $found = null;
    //If we use Cache, need to first check the cache
    var needUpdate = false;
    var noskip = true;
    if(metaCmd.uid == null || trimString(metaCmd.uid).length == 0)
        noskip = false;

    if(noskip && this.cacheSelector){
        jslogger.debug("Tellurium jQuery Selector Cache is turned on");
        var uiid = new Uiid();
        uiid.convertToUiid(metaCmd.uid);
        if(metaCmd.cacheable){
            //the locator could be cached
            var cached = this.sCache.get(uiid.getUid());
            if(cached != null){
                $found = cached.reference;
                jslogger.debug("Locator cacheable, found cached selector for " + uiid.getUid());
            }
        }else{
            while(uiid.size() > 1){
                uiid.pop();
                var parent = uiid.getUid();
                var cachedParent = this.sCache.get(parent);
                if(cachedParent != null){
                    //parent's jQuery Selector
                    var pjqs = cachedParent.selector;
                    if(loc.length > pjqs.length){
                        var start = loc.substring(0, pjqs.length);
                        if(start == pjqs){
                            //the start part of loc matches the parent's selector
                            var leftover = trimString(loc.substring(pjqs.length));
                            $found = jQuery(cachedParent.reference).find(leftover);
                            jslogger.debug("Locator not cacheable, found parent cached selector " + parent);
                            break;
                        }
                    }
                }
            }
        }
    }else{
        if(!noskip)
          jslogger.debug("Skip Tellurium jQuery Selector Cache");
        else
          jslogger.debug("Tellurium jQuery Selector Cache is turned off");
    }

    //if could not find from cache partially or wholely, search the DOM
    if($found == null){
         $found = jQuery(inDocument).find(loc);
        if($found == null){
            jslogger.debug("Search the DOM, but could not find any element");
        }else{
            needUpdate = true;
            jslogger.debug("Search the DOM and found " + $found.length + " elements");
        }
    }
    //Need to do validation first
    if(metaCmd.unique){
        if($found != null && $found.length > 1){
            jslogger.error("Element is not unique, Found " + $found.length + " elements for " + loc);
            throw new SeleniumError("Element is not unique, Found " + $found.length + " elements for " + loc);
        }
    }
    //cache the data if the option is on, the locator is cacheable, and we need to update the cache
    if (noskip && this.cacheSelector && metaCmd.cacheable && needUpdate) {
        var cachedata = new CacheData();
        cachedata.selector = loc;
        cachedata.reference = $found;
        var nuid = new Uiid();
        nuid.convertToUiid(metaCmd.uid);
        this.sCache.put(nuid.getUid(), cachedata);
        jslogger.debug("Need to cache the selector for " + nuid.getUid());
    }
//    var found = jQuery(inDocument).find(loc);

    if ($found.length == 1) {
        if (isattr) {
            return $found[0].getAttributeNode(attr);
        } else {
            return $found[0];
        }
    } else if ($found.length > 1) {
        if (isattr) {
            return $found.get().getAttributeNode(attr);
        } else {
            return $found.get();
        }
    } else {
        return null;
    }
};

Tellurium.prototype.setCacheState = function(flag){
    this.cacheSelector = flag;
};
