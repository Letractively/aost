//add custom jQuery Selector :te_text()
//

jQuery.extend(jQuery.expr[':'], {
    te_text: function(a, i, m) {
        return jQuery(a).text() === m[3];
    }
});

//dump logging message to dummy device, which sallows all messages == no logging
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


 //uncomment this and comment the next line if you want to see the logging message in window
 //but it would slow down the testing dramatically, for debugging purpose only.
var jslogger = new Log4js.getLogger("TeEngine");
jslogger.setLevel(Log4js.Level.ALL);
//jslogger.addAppender(new Log4js.MozillaJSConsoleAppender());
jslogger.addAppender(new Log4js.ConsoleAppender());


//var jslogger = new DummyLogger();

//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
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

Uiid.prototype.convertToUiid = function(uid){
    if(uid != null && trimString(uid).length > 0){
        var ids = uid.split(".");
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

Uiid.prototype.preprocess = function(uid){
    if(uid != null && trimString(uid).length > 0 && uid.indexOf("[") != -1){
        if(uid.indexOf("[") == 0){
            var single = uid.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = uid.indexOf("[");
            var first = uid.substring(0, index);
            var second = uid.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [uid];
};


function MetaCmd(){
    this.uid = null;
    this.cacheable = true;
    this.unique = true;
};

//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to the the reminding selector for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //DOM reference
    this.reference = null;

    this.count = 0;
};

function Tellurium (){

    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
    this.sCache = new HashMap();
    
    this.maxCacheSize = 50;

    this.cachePolicy = discardNewCachePolicy;
    this.cachePolicyName = "DiscardNewPolicy";

    this.currentWindow = null;

    this.currentDocument = null;
};

var tellurium = new Tellurium();

Tellurium.prototype.cleanCache = function(){
    this.sCache = new HashMap();
};

Tellurium.prototype.getCacheSize = function(){
    return this.sCache.size();
};

Tellurium.prototype.updateUseCount = function(key, data){
    if(key != null && data != null){
        data.count++;
        this.sCache.put(key, data);
    }
};

Tellurium.prototype.getCachedSelector = function(key){
    var data = this.sCache.get(key);
    if(data != null){
        data.count++;
        this.sCache.put(key, data);
    }
    return data;
};

//cache eviction policies
//simply discard new selector
function DiscardNewPolicy(){

};

DiscardNewPolicy.prototype.applyPolicy = function (cache, key, data){

};

var discardNewCachePolicy = new DiscardNewPolicy();

//remove the least used select in the cache
function DiscardLeastUsedPolicy (){

};

DiscardLeastUsedPolicy.prototype.applyPolicy = function(cache, key, data){
    var keys = cache.keySet();
    var toBeRemoved = keys[0];
    var leastCount = cache.get(toBeRemoved).count;
    for(var i=1; i<keys.length; i++){
        var akey = keys[i];
        var val = cache.get(akey).count;
        if(val < leastCount){
            toBeRemoved = akey;
            leastCount = val;
        }
    }
    cache.remove(toBeRemoved);
    cache.put(key, data);
};

var discardLeastUsedCachePolicy = new DiscardLeastUsedPolicy();

//If found invalid selector, remove it and put the new one in
//otherwise, discard the new one
function DiscardInvalidPolicy(){
    this.validator = tellurium;
};

DiscardInvalidPolicy.prototype.applyPolicy = function(cache, key, data){
    var keys = cache.keySet();
    for(var i=0; i<keys.length; i++){
        var akey = keys[i];
        var $ref = cache.get(akey).reference;
        if(!this.validator.validateCache($ref)){
            cache.remove(akey);
            cache.put(key, data);
            break;
        }
    }
};

var discardInvalidCachePolicy = new DiscardInvalidPolicy();

Tellurium.prototype.addSelectorToCache = function(key, data){
    if(this.sCache.size() < this.maxCacheSize){
        this.sCache.put(key, data);
    }else{
        this.cachePolicy.applyPolicy(this.sCache, key, data);
    }
};

//update existing selector to the cache
Tellurium.prototype.updateSelectorToCache = function(key, data){
    this.sCache.put(key, data);
};

Tellurium.prototype.validateCache = function(reference){
    //This may impose some problem if the DOM element becomes invisable instead of being removed
    try{
        return jQuery(reference).is(':visible');
    }catch(e){
        //seems for IE, it throws exception
        return false;
    }
};

Tellurium.prototype.locateElementByJQuerySelector = function(locator, inDocument, inWindow){
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

Tellurium.prototype.locateElementByCacheAwareJQuerySelector = function(locator, inDocument, inWindow){

/*
    if(inWindow != this.currentWindow){
//        jslogger.debug("Bind cleaning cache to window unload envent " + inWindow);
        jQuery(inWindow).unload(this.cleanCache);
        this.currentWindow = inWindow;
    }

    if(this.currentDocument != null && inDocument != this.currentDocument){
        //force to clean up cache
        this.cleanCache();
        this.currentDocument = inDocument;
    }
*/

    var purged = locator;
    var attr = null;
    var isattr = false;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        purged = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }

    var tecmd = JSON.parse(purged, null);

    var loc = tecmd.locator;
    var optimized = tecmd.optimized;
    var metaCmd = new MetaCmd();
    metaCmd.uid = tecmd.uid;
    metaCmd.cacheable = tecmd.cacheable;
    metaCmd.unique = tecmd.unique;

    var $found = null;
    //If we use Cache, need to first check the cache
    var needUpdate = false;
    var noskip = true;
    //cannot cache selector without a uid
    if(metaCmd.uid == null || trimString(metaCmd.uid).length == 0)
        noskip = false;

    if(noskip && this.cacheSelector){
        var sid = new Uiid();
        sid.convertToUiid(metaCmd.uid);
        if(metaCmd.cacheable){
            //the locator could be cached
            var cached = this.getCachedSelector(sid.getUid());
            if(cached != null){
                $found = cached.reference;
                //validate the DOM reference
                if(!this.validateCache($found)){
                    $found = null;
                }
            }
        }else{
            while(sid.size() > 1){
                sid.pop();
                var ancestor = sid.getUid();
                var cachedAncestor = this.getCachedSelector(ancestor);
                if(cachedAncestor != null){
                    //check if the ancestor's DOM reference is still valid
                    jslogger.info("Found ancestor " + ancestor + " for " + metaCmd.uid);
                    if(!this.validateCache(cachedAncestor)){
                        //if not valid, try to select it using jQuery
                        cachedAncestor.reference = jQuery(cachedAncestor.optimized);
                        this.updateSelectorToCache(ancestor, cachedAncestor);
                        jslogger.info("Ancestor is invalid and update it " + ancestor);
                    }
                    //ancestor's jQuery Selector
                    var pjqs = cachedAncestor.selector;

                    if(loc.length > pjqs.length){
                        var start = loc.substring(0, pjqs.length);
                        jslogger.info("current selector: " + loc + ", ancestor selector: " + pjqs);
                        jslogger.info("Shared selector between ancestor " + ancestor + " and " + metaCmd.uid + " is " + start);
                        if(start == pjqs){
                            //the start part of loc matches the parent's selector
                            var leftover = trimString(loc.substring(pjqs.length));
                            jslogger.info("Left over selector is " + leftover);
                            $found = jQuery(cachedAncestor.reference).find(leftover);
                            jslogger.info("Found element " + $found.length + " for " + leftover);
                            break;
                        }
                    }
                }
            }
        }
    }

    //if could not find from cache partially or wholely, search the DOM
    if($found == null){
        $found = jQuery(inDocument).find(optimized);
        if($found != null){
            needUpdate = true;
        }
    }
    //Need to do validation first
    if(metaCmd.unique){
        if($found != null && $found.length > 1){
            throw new SeleniumError("Element is not unique, Found " + $found.length + " elements for " + loc);
        }
    }
    
    //cache the data if the option is on, the locator is cacheable, and we need to update the cache
    if (noskip && this.cacheSelector && metaCmd.cacheable && needUpdate) {
        var cachedata = new CacheData();
        cachedata.selector = loc;
        cachedata.optimized = optimized;
        cachedata.reference = $found;
        var nuid = new Uiid();
        nuid.convertToUiid(metaCmd.uid);
        this.addSelectorToCache(nuid.getUid(), cachedata);
    }

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

Tellurium.prototype.getCacheUsage = function(){
    var out = [];
    var keys = this.sCache.keySet();
    for(var i=0; i< keys.length; i++){
        var key = keys[i];
        var val = this.sCache.get(key);
        var usage = {};
        usage[key] = val.count;
        out.push(usage);
    }

    return JSON.stringify(out);
};

Tellurium.prototype.useDiscardNewPolicy = function(){
    this.cachePolicy = discardNewCachePolicy;
    this.cachePolicyName = "DiscardNewPolicy";
};

Tellurium.prototype.useDiscardLeastUsedPolicy = function(){
    this.cachePolicy = discardLeastUsedCachePolicy;
    this.cachePolicyName = "DiscardLeastUsedPolicy";
};

Tellurium.prototype.useDiscardInvalidPolicy = function(){
    this.cachePolicy = discardInvalidCachePolicy;
    this.cachePolicyName = "DiscardInvalidPolicy";
};

Tellurium.prototype.getCachePolicyName = function(){
    return this.cachePolicyName;
};