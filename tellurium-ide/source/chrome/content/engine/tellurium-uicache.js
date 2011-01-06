function CacheUsage(){
    this.totalCall = 0;
    this.cacheHit = 0;
    //percentage of the cacheHit/totalCall
    this.usage = 0;
}

var CachePolicy = Class.extend({
    init: function(){
        this.name = "";
    },

    myName: function(){
        return this.name;
    },

    applyPolicy: function (cache, key, data){

    }
});

var DiscardNewPolicy = CachePolicy.extend({
    init: function(){
        this.name = "DiscardNewPolicy";
    }
});

var DiscardOldPolicy = CachePolicy.extend({
    init: function() {
        this.name = "DiscardOldPolicy";
    },

    applyPolicy: function (cache, key, data) {
        var keys = cache.keySet();
        var toBeRemoved = keys[0];
        var oldest = cache.get(toBeRemoved).timestamp;
        for (var i = 1; i < keys.length; i++) {
            var akey = keys[i];
            var val = cache.get(akey).timestamp;
            if (val < oldest) {
                toBeRemoved = akey;
                oldest = val;
            }
        }

        cache.remove(toBeRemoved);
        cache.put(key, data);
    }
});

var DiscardLeastUsedPolicy = CachePolicy.extend({
    init: function() {
        this.name = "DiscardLeastUsedPolicy";
    },

    applyPolicy: function(cache, key, data) {
        var keys = cache.keySet();
        var toBeRemoved = keys[0];
        var leastCount = cache.get(toBeRemoved).count;
        for (var i = 1; i < keys.length; i++) {
            var akey = keys[i];
            var val = cache.get(akey).count;
            if (val < leastCount) {
                toBeRemoved = akey;
                leastCount = val;
            }
        }

        cache.remove(toBeRemoved);
        cache.put(key, data);
    }
});

var DiscardInvalidPolicy = CachePolicy.extend({
    init: function() {
        this.name = "DiscardOldPolicy";
    },

    applyPolicy: function(cache, key, data) {
        var keys = cache.keySet();
        for (var i = 0; i < keys.length; i++) {
            var akey = keys[i];
            if (!akey.valid) {
                cache.remove(akey);
                cache.put(key, data);
                break;
            }
        }
    }
});

var discardNewCachePolicy = new DiscardNewPolicy();

var discardOldCachePolicy = new DiscardOldPolicy();

var discardLeastUsedCachePolicy = new DiscardLeastUsedPolicy();

var discardInvalidCachePolicy = new DiscardInvalidPolicy();

function CacheData(){
    this.data = null;
    this.timestamp = null;
    this.count = 0;
}

function TelluriumUiCache(){

    //cache for UI modules
    this.cache = new Hashtable();

    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;

    //Cache hit, i.e., direct get dom reference from the cache
    this.cacheHit = 0;

    //Cache miss, i.e., have to use walkTo to locate elements
    this.cacheMiss = 0;

}

TelluriumUiCache.prototype.clear = function(){
    this.cache.clear();
    this.cacheHit = 0;
    this.cacheMiss = 0;
};

TelluriumUiCache.prototype.size = function(){
    return this.cache.size();
};

TelluriumUiCache.prototype.remove = function(key){
    this.cache.remove(key);    
};

TelluriumUiCache.prototype.put = function(key, val) {
    var cached = this.cache.get(key);
    if (cached == null) {
        cached = new CacheData();
        cached.data = val;
        cached.timestamp = Number(new Date());

        if (this.cache.size() < this.maxCacheSize) {
            this.cache.put(key, cached);
        } else {
            this.cachePolicy.applyPolicy(this.cache, key, cached);
        }
    } else {
        cached.data = val;
        cached.timestamp = Number(new Date());
    }
};

TelluriumUiCache.prototype.get = function(key){
    var cached = this.cache.get(key);
    if(cached != null){
        ++this.cacheHit;
        ++cached.count;
        cached.timestamp = Number(new Date());
        return cached.data;
    }else{
        ++this.cacheMiss;

        return null;
    }
};

TelluriumUiCache.prototype.isCached = function(id){

    return this.cache.get(id) != null;
};

TelluriumUiCache.prototype.keySet = function(){
    return this.cache.keySet();
};

TelluriumUiCache.prototype.getCacheUsage = function(){
    var usage = new CacheUsage();
    usage.totalCall = this.cacheHit + this.cacheMiss;
    usage.cacheHit = this.cacheHit;
    usage.usage = (usage.totalCall == 0) ? 0 : this.cacheMiss/usage.totalCall;

    return usage;
};

TelluriumUiCache.prototype.useDiscardNewPolicy = function(){
    this.cachePolicy = discardNewCachePolicy;
};

TelluriumUiCache.prototype.useDiscardOldPolicy = function(){
    this.cachePolicy = discardOldCachePolicy;
};

TelluriumUiCache.prototype.useDiscardLeastUsedPolicy = function(){
    this.cachePolicy = discardLeastUsedCachePolicy;
};

TelluriumUiCache.prototype.useDiscardInvalidPolicy = function(){
    this.cachePolicy = discardInvalidCachePolicy;
};

TelluriumUiCache.prototype.getCachePolicyName = function(){
    return this.cachePolicy.name;
};
