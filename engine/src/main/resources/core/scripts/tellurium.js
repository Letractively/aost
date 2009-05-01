//add custom jQuery Selector :te_text()
//

jQuery.extend(jQuery.expr[':'], {
    te_text: function(a, i, m) {
        return jQuery(a).text() === m[3];
    }
});

/*
jQuery.fn.extend({
  inDOM = function() { return !!$(this).parents('html').length; }
});
*/

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

/*
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

function TeInput(){
    this.metaCmd = null;
    this.selector = null;
    this.optimized = null;
    this.isAttribute = false;
    this.attribute = null;
};

//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to the the reminding selector for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //jQuery object for DOM reference
    this.reference = null;

    this.count = 0;

    this.timestamp = Number(new Date());
};

//cache eviction policies
//simply discard new selector
function DiscardNewPolicy(){
    this.name = "DiscardNewPolicy";
};

DiscardNewPolicy.prototype.applyPolicy = function (cache, key, data){

};

DiscardNewPolicy.prototype.myName = function(){
    return this.name;
};

var discardNewCachePolicy = new DiscardNewPolicy();

//remove the least used select in the cache
function DiscardLeastUsedPolicy (){
    this.name = "DiscardLeastUsedPolicy";
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

DiscardLeastUsedPolicy.prototype.myName = function(){
    return this.name;
};

var discardLeastUsedCachePolicy = new DiscardLeastUsedPolicy();

//If found invalid selector, remove it and put the new one in
//otherwise, discard the new one
function DiscardInvalidPolicy(){
    this.name = "DiscardInvalidPolicy";
};

DiscardInvalidPolicy.prototype.applyPolicy = function(cache, key, data){
    var keys = cache.keySet();
    for(var i=0; i<keys.length; i++){
        var akey = keys[i];
        var $ref = cache.get(akey).reference;
        var isVisible = false;
        try{
           isVisible = $ref.is(':visible'); 
        }catch(e){
            isVisible = false;
        }
        if(!isVisible){
            cache.remove(akey);
            cache.put(key, data);
            break;
        }
    }
};

DiscardInvalidPolicy.prototype.myName = function(){
    return this.name;
};

var discardInvalidCachePolicy = new DiscardInvalidPolicy();

function Tellurium (){

    //global flag to decide whether to cache jQuery selectors
    this.cacheSelector = false;

    //cache for jQuery selectors
    this.sCache = new HashMap();
    
    this.maxCacheSize = 50;

    this.cachePolicy = discardNewCachePolicy;

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

    return this.sCache.get(key);
};

Tellurium.prototype.addSelectorToCache = function(key, data){
    if(this.sCache.size() < this.maxCacheSize){
        this.sCache.put(key, data);
    }else{
        this.cachePolicy.applyPolicy(this.sCache, key, data);
    }
};

//update existing selector to the cache
Tellurium.prototype.updateSelectorToCache = function(key, data){
    data.count++;
    data.timestamp = Number(new Date());
    this.sCache.put(key, data);
};

Tellurium.prototype.validateCache = function($reference){
    //This may impose some problem if the DOM element becomes invisable instead of being removed
    try{
        return $reference.is(':visible');
//        return ($reference.eq(0).parent().length > 0);
//        return jQuery($reference).is(':visible');
//        return jQuery($reference).parents('html').length > 0;
//        return $reference.parents('html').length > 0;
//        return true;
    }catch(e){
        //seems for IE, it throws exception
//        jslogger.error("Validate exception " + e.message);
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

Tellurium.prototype.checkSelectorFromCache = function(key){
    var $found = null;

    var cached = this.getCachedSelector(key);
    if (cached != null) {
        $found = cached.reference;
        //validate the DOM reference
        if (!this.validateCache($found)) {
            $found = null;
            this.sCache.remove(key);
        }else{
            this.updateSelectorToCache(key, cached);
        }
    }

    return $found;
};

Tellurium.prototype.checkAncestorSelector = function(akey){
    var cached = this.getCachedSelector(akey);
    
    if (cached != null) {
        //check if the ancestor's DOM reference is still valid
        if (!this.validateCache(cached)) {
            //if not valid, try to select it using jQuery
            var $newsel = jQuery(cached.optimized);
            if ($newsel.length > 0) {
                cached.reference = $newsel;
                cached.count = 0;
                this.updateSelectorToCache(akey, cached);
            } else {
                //remove invalid selector
                this.sCache.remove(akey);
                cached = null;
            }
        }else{
            this.updateSelectorToCache(akey, cached);
        }
    }

    return cached;
};

Tellurium.prototype.findFromAncestor = function(ancestor, sel){
    var asel = ancestor.selector;
    var $found = null;

    if(sel.length > asel.length){
        var start = sel.substring(0, asel.length);
        if(start == asel){
            var leftover = trimString(sel.substring(asel.length));
            $found = ancestor.reference.find(leftover);
        }
    }

    return $found;
};

Tellurium.prototype.getDOMElement = function($found){
    if ($found.length == 1) {
        return $found[0];
    } else if ($found.length > 1) {
        return $found.get();
    } else {
        return null;
    }
};

Tellurium.prototype.getDOMAttributeNode = function($found, attr) {
    if ($found.length == 1) {
        return $found[0].getAttributeNode(attr);

    } else if ($found.length > 1) {
        return $found.get().getAttributeNode(attr);
    } else {
        return null;
    }
};

//convert jQuery result to DOM reference
Tellurium.prototype.convResult = function($result, input){
    if(input.isAttribute){
        return this.getDOMAttributeNode($result, input.attribute);
    }

    return this.getDOMElement($result);
};

Tellurium.prototype.parseLocator = function(locator){
    var input = new TeInput();
    
    var purged = locator;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        purged = locator.substring(0, inx);
        input.attribute = locator.substring(inx + 1);
        input.isAttribute = true;
    }

    var tecmd = JSON.parse(purged, null);

    input.selector = tecmd.locator;
    input.optimized = tecmd.optimized;
    var metaCmd = new MetaCmd();
    metaCmd.uid = tecmd.uid;
    metaCmd.cacheable = tecmd.cacheable;
    metaCmd.unique = tecmd.unique;
    input.metaCmd = metaCmd;

    return input;
};

Tellurium.prototype.validateResult = function($result, unique, selector){
    if(unique){
        if($result != null && $result.length > 1){
            throw new SeleniumError("Element is not unique, Found " + $result.length + " elements for " + selector);
        }
    }
};

Tellurium.prototype.locateElementByCacheAwareJQuerySelector = function(locator, inDocument, inWindow){
    var input = this.parseLocator(locator);
    var $found = null;
    
    //If do not cache selector or meta command without UID, directly find jQuery selector
    if((!this.cacheSelector) || input.metaCmd.uid == null || trimString(input.metaCmd.uid).length == 0){
        //cannot cache without uid, thus, go directly to find the element using jQuery
         $found = jQuery(inDocument).find(input.optimized);
         this.validateResult($found);
         return this.convResult($found, input);
    }else{
        var sid = new Uiid();
        sid.convertToUiid(input.metaCmd.uid);
        var key = sid.getUid();
        //if this selector is cacheable, need to check the cache first
        if(input.metaCmd.cacheable){
            $found = this.checkSelectorFromCache(key);
            
            if($found == null){
                //could not find from cache or the cached one is invalid
                while(sid.size() > 1){
                    //try to find from its ancestor
                    sid.pop();
                    var akey = sid.getUid();
                    var ancestor = this.checkAncestorSelector(akey);
                    if(ancestor != null){
                        $found = this.findFromAncestor(ancestor, input.selector);
                        break;
                    }
                }

                //if still could not find do jQuery select
                if($found == null){
                    $found = jQuery(inDocument).find(input.optimized);
                }

                //validate the result before storing it
                this.validateResult($found);

                //If find valid selector, update to cache
                if($found != null && $found.length > 0){
                    var cachedata = new CacheData();
                    cachedata.selector = input.selector;
                    cachedata.optimized = input.optimized;
                    cachedata.reference = $found;
                    this.addSelectorToCache(key, cachedata);  
                }
            }

            return this.convResult($found, input);
        } else {
            //cannot cache the selector directly, try to find the DOM elements using ancestor first
            while (sid.size() > 1) {
                //try to find from its ancestor
                sid.pop();
                var ckey = sid.getUid();
                var ancester = this.checkAncestorSelector(ckey);
                if (ancester != null) {
                    $found = this.findFromAncestor(ancester, input.selector);
                    break;
                }
            }
            
            //if still could not find do jQuery select
            if ($found == null) {
                $found = jQuery(inDocument).find(input.optimized);
            }

            //validate the result before storing it
            this.validateResult($found);

            return this.convResult($found, input);
        }
    }   
};

/*
Tellurium.prototype.locateElementByCacheAwareJQuerySelector = function(locator, inDocument, inWindow){

*//*
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
*//*

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
            var currentUid = sid.getUid();
            var cached = this.getCachedSelector(currentUid);
//            jslogger.info("Found cached selector " + currentUid);
            if(cached != null){
                $found = cached.reference;
                //validate the DOM reference
                if(!this.validateCache($found)){
                    $found = null;
//                    jslogger.warn("Cached selector " + currentUid + " is not valid, removing...");
                    this.sCache.remove(currentUid);
                }
                
            }
        }else{
            while(sid.size() > 1){
                sid.pop();
                var ancestor = sid.getUid();
                var cachedAncestor = this.getCachedSelector(ancestor);
                if(cachedAncestor != null){
                    var validAncestor = true;
                   //check if the ancestor's DOM reference is still valid
//                    jslogger.info("Found ancestor selector " + ancestor);
                    if(!this.validateCache(cachedAncestor)){
                        //if not valid, try to select it using jQuery
                        var $newancestorsel = jQuery(cachedAncestor.optimized);
                        if($newancestorsel.length > 0){
                            cachedAncestor.reference = $newancestorsel;
                            this.updateSelectorToCache(ancestor, cachedAncestor);
//                            jslogger.warn("Ancestor selector " + ancestor + " is not valid, re-select and update");
                        }else{
                            validAncestor = false;
//                            jslogger.warn("Cannot find ancestor selector " + ancestor + ", removing...");
                            //remove invalid selector
                            this.sCache.remove(ancestor);
                        }
                    }

                    if (validAncestor) {
                        //ancestor's jQuery Selector
                        var pjqs = cachedAncestor.selector;

                        if (loc.length > pjqs.length) {
                            var start = loc.substring(0, pjqs.length);
                            if (start == pjqs) {
                                //the start part of loc matches the parent's selector
                                var leftover = trimString(loc.substring(pjqs.length));
//                                $found = jQuery(cachedAncestor.reference).find(leftover);
                                $found = cachedAncestor.reference.find(leftover);
//                                jslogger.info("Use ancestor and leftover selector " + leftover + " to find element with size " + $found.length);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    //if could not find from cache partially or wholely, search the DOM
    if($found == null){
        $found = jQuery(inDocument).find(optimized);
//        jslogger.info("Not found selector " + metaCmd.uid + ", re-select and get element with size " + $found.length);
        if($found != null && $found.length > 0){
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
//        jslogger.info("Add selector " + nuid.getUid() + " to cache");
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
*/

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
};

Tellurium.prototype.useDiscardLeastUsedPolicy = function(){
    this.cachePolicy = discardLeastUsedCachePolicy;
};

Tellurium.prototype.useDiscardInvalidPolicy = function(){
    this.cachePolicy = discardInvalidCachePolicy;
};

Tellurium.prototype.getCachePolicyName = function(){
    return this.cachePolicy.name;
};