// User extensions can be added here.
//
// Keep this file to avoid  mystifying "Invalid Character" error in IE

/*
BrowserBot.prototype.registerLocateStrategy = function (strategyName, strategyFunction) {
    alert("Register " + strategyName);
    
    if (!/^[a-zA-Z]+$/.test(strategyName)) {
        throw new SeleniumError("Invalid strategy name: " + strategyName);
    }
    var safeStrategyFunction = function() {
        try {
            return strategyFunction.apply(this, arguments);
        } catch (ex) {
            throw new SeleniumError("Error executing strategy function " + strategyName + ": " + extractExceptionMessage(ex));
        }
    }
    this.locationStrategies[strategyName] = safeStrategyFunction;
};

Selenium.prototype.getSelectorProperties = function(jq, p) {
    var p = eval('(' + p + ')'); //eval json
    var e = this.browserbot.findElement("jqueryall=" + jq);
    var out = [];
    for (var i = 0; i < e.length; i++) {
        var stuff = {};
        for (var j = 0; j < p.length; j++) {
            var prop = null;
            if (typeof e[i][p[j]] !== "undefined") {
                prop = e[i][p[j]];
            }
            stuff[p[j]] = prop;	//gather the requested stuff
        }
        out.push(stuff);
    }
    return JSON.stringify(out);
};

Selenium.prototype.getSelectorText = function(jq) {
    var e = this.browserbot.findElement("jqueryall=" + jq);
    var out = [];
    for (var i = 0; i < e.length; i++) {
        out.push(jQuery(e[i]).text());
    }
    return JSON.stringify(out);
};

//generic function call. best hope the function returns json, or we are going to be sad
Selenium.prototype.getSelectorFunctionCall = function(jq, fn) {
    var e = this.browserbot.findElement("jqueryall=" + jq);
    fn = eval('(' + eval('(' + fn + ')')[0] + ')');
    return JSON.stringify(fn.apply(e));
};
*/

Selenium.prototype.getAllText = function(locator) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
    return JSON.stringify(out);
};

Selenium.prototype.getJQuerySelectorCount = function(locator) {
    var $e = teJQuery(this.browserbot.findElement(locator));
    if ($e == null)
        return 0;

    return $e.length;
};

Selenium.prototype.getCSS = function(locator, cssName) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(teJQuery(this).css(cssName));
    });
    return JSON.stringify(out);
};

Selenium.prototype.isDisabled = function(locator) {
    var $e = teJQuery(this.browserbot.findElement(locator));
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);
    if ($e.length > 1)
        Assert.fail("Element for " + locator + " is not unique.");
    return $e.attr('disabled');
};

Selenium.prototype.getListSize = function(locator, separators) {
    var $e = teJQuery(this.browserbot.findElement(locator));
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);

    //TODO: this may not be correct for example we have div/div/div span/span, what would $(().find("div, span") return? 
//    var jq = separators.join(",")

//    var list = $e.find(separators);
    var list = $e.children(separators);

    return list.length;
};


Selenium.prototype.getCacheState = function(){

    return tellurium.cacheSelector;
};

Selenium.prototype.doEnableCache = function(){
    tellurium.cacheSelector = true;
};

Selenium.prototype.doDisableCache = function(){
    tellurium.cacheSelector = false;
};

Selenium.prototype.doCleanCache = function(){
    tellurium.cleanCache();
};

Selenium.prototype.doSetCacheMaxSize = function(size){
    tellurium.maxCacheSize = size;
};

Selenium.prototype.getCacheSize = function(){
    return tellurium.getCacheSize();
};

Selenium.prototype.getCacheMaxSize = function(){
    return tellurium.maxCacheSize;
};

Selenium.prototype.getCacheUsage = function(){
    return tellurium.getCacheUsage();
};

Selenium.prototype.doAddNamespace = function(prefix, namespace){
    this.browserbot.addNamespace(prefix, namespace);
};

Selenium.prototype.getNamespace = function(prefix){
   return this.browserbot._namespaceResolver(prefix);
};

Selenium.prototype.doUseDiscardNewPolicy = function(){
    tellurium.useDiscardNewPolicy();
};

Selenium.prototype.doUseDiscardOldPolicy = function(){
    tellurium.useDiscardOldPolicy();
};

Selenium.prototype.doUseDiscardLeastUsedPolicy = function(){
    tellurium.useDiscardLeastUsedPolicy();
};

Selenium.prototype.doUseDiscardInvalidPolicy = function(){
    tellurium.useDiscardInvalidPolicy();
};

Selenium.prototype.getCachePolicyName = function(){
    return tellurium.getCachePolicyName();
};

var getEvent = function(name, key){
    var e = teJQuery.Event(name);
    e.which = key.charCodeAt(0);
    return e;
};

Selenium.prototype.doTypeKey = function(locator, key){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.val($elem.val()+key).trigger(getEvent("keydown", key)).trigger(getEvent("keypress", key)).trigger(getEvent("keyup", key));
};

Selenium.prototype.doTriggerEvent = function(locator, event){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.trigger(event);
};

function DiagnosisRequest(){
    this.uid = null;
    this.pLocator = null;
    this.attributes = null;
    this.retHtml = true;
    this.retParent = true;
    this.retClosest = true;
};

function DiagnosisResponse(){
    this.uid = null;
    this.count = 0;
    this.matches = null;
    this.parents = null;
    this.closest = null;
};

function jQueryBuilder(){
    this.ATTR_BLACK_LIST = ['action'];
    this.TEXT_PSEUDO_CLASS = ":te_text";
    this.MATCH_ALL = "*";
    this.CONTAINS_FILTER = ":contains";
    this.NOT_PREFIX = "!";
    this.START_PREFIX = "^";
    this.END_PREFIX = "$";
    this.ANY_PREFIX = "*";
    this.HAS = ":has";
    this.SELECTOR_SEPARATOR = ", ";
    this.SPACE = " ";
    this.CHILD_SEPARATOR = " > ";
    this.DESCENDANT_SEPARATOR = " ";
    this.NEXT_SEPARATOR = " + ";
    this.SIBLING_SEPARATOR = " ~ ";
    this.ID_SELECTOR_PREFIX = "#";
    this.CLASS_SELECTOR_PREFIX = ".";
    this.SINGLE_QUOTE = "'";
    this.TITLE = "title";
    this.ID = "id";
    this.NAME = "name";
    this.CLASS = "class";
    this.CONTAIN_PREFIX = "%%";
};

jQueryBuilder.prototype.inBlackList = function(attr){
    return this.ATTR_BLACK_LIST.indexOf(attr) != -1;
};

jQueryBuilder.prototype.isPartial = function(val){

    return val != null && (val.startsWith(this.START_PREFIX) || val.startsWith(this.END_PREFIX)
            || val.startsWith(this.ANY_PREFIX) || val.startsWith(this.NOT_PREFIX)
            || val.startsWith(this.CONTAIN_PREFIX));
};

jQueryBuilder.prototype.buildId = function(id){
    if(id.startsWith("^")){
        return "[id^=" + id.substring(1) + "]";
    }else if(id.startsWith("$")){
        return "[id$=" + id.substring(1) + "]";
    }else if(id.startsWith("*")){
        return "[id*=" + id.substring(1) + "]";
    }else if(id.startsWith("!")){
        return "[id!=" + id.substring(1) + "]";
    }else{
        return "#" + id;
    }
};

jQueryBuilder.prototype.buildSingleClass = function(clazz){
    if(clazz.startsWith("^")){
        return tag + "[class^=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("$")){
        return tag + "[class$=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("*")){
        return tag + "[class*=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("!")){
        return tag + "[class!=" + clazz.substring(1) + "]";
    }else{
        return "." + clazz;
    }        
};

jQueryBuilder.prototype.buildClass = function(clazz){
    if (clazz != null && trimString(clazz).length > 0) {
        var parts = clazz.split(this.SPACE);
        if (parts.length == 1) {
            //only only 1 class

            return this.buildSingleClass(parts[0]);
        } else {

            var sb = new StringBuffer();
            for (var part in parts) {
                sb.append(this.buildSingleClass(part));
            }

            return sb.toString();
        }
    }

    return "[class]";
};

jQueryBuilder.prototype.containText = function(text){

    return this.CONTAINS_FILTER + "(" + text + ")";
};

jQueryBuilder.prototype.attrText = function(text){

    //need the following custom selector ":te_text()" support
    return this.TEXT_PSEUDO_CLASS + "(" + text + ")";
};

jQueryBuilder.prototype.buildText = function(text){
    if (text != null && trimString(text).length > 0) {
        if (text.startsWith(this.CONTAIN_PREFIX)) {
            return this.containText(text.substring(2));
        } else if (text.startsWith(this.START_PREFIX) || text.startsWith(this.END_PREFIX) || text.startsWith(this.ANY_PREFIX)) {
            //TODO: need to refact this to use start, end, any partial match
            return this.containText(text.substring(1));
        } else if (text.startsWith(this.NOT_PREFIX)) {
            return ":not(" + this.containText(text.substring(1)) + ")";
        } else {
            return this.attrText(text);
        }
    }

    return "";
};

jQueryBuilder.prototype.includeSingleQuote = function(val) {

    return val != null && val.indexOf(this.SINGLE_QUOTE) > 0;
};

jQueryBuilder.prototype.buildAttribute = function(attr, val) {
    if (val == null || trimString(val).length == 0) {
        return "[" + attr + "]";
    }

    if (val.startsWith(this.START_PREFIX)) {
        return "[" + attr + "^=" + val.substring(1) + "]";
    } else if (val.startsWith(this.END_PREFIX)) {
        return "[" + attr + "$=" + val.substring(1) + "]";
    } else if (val.startsWith(this.ANY_PREFIX)) {
        return "[" + attr + "*=" + val.substring(1) + "]";
    } else if (val.startsWith(this.NOT_PREFIX)) {
        return "[" + attr + "!=" + val.substring(1) + "]";
    } else {
        return "[" + attr + "=" + val + "]";
    }
};

jQueryBuilder.prototype.buildSelector = function(attr, val){
    if(attr == "id"){
        return this.buildId(val);
    }else if(attr == "text"){
        return this.buildText(val);
    }else if(attr == "class"){
        return this.buildClass(val);
    }else{
        return this.buildAttribute(attr, val);
    }
};

Selenium.prototype.getDiagnosisResponse = function(locator, req){
    var dreq = JSON.parse(req, null);

    var request = new DiagnosisRequest();
    request.uid = dreq.uid;
    request.pLocator = dreq.pLocator;
    request.attributes = dreq.attributes;
    request.retHtml = dreq.retHtml;
    request.retParent = dreq.retParent;

    var response = new DiagnosisResponse();
    response.uid = request.uid;
    var $e = null;
    try{
      $e = teJQuery(this.browserbot.findElement(locator));
    }catch(err){

    }

    if ($e == null) {
        response.count = 0;
    } else {
        response.count = $e.length;
        if (request.retHtml) {
            response.matches = new Array();
            $e.each(function() {
                response.matches.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
            });
        }
    }

    if(request.retParent){
        response.parents = new Array();
        //if the parent is null or empty, return the whole html source
        if(request.pLocator == null || trimString(request.pLocator).length == 0){
            response.parents.push(teJQuery('<div>').append(teJQuery("html:first").clone()).html());
        }else{
            var $p = null;
            try{
                $p = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
                $p = teJQuery("html:first");
            }

            $p.each(function() {
                response.parents.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
            });
        }        
    }

    if(request.retClosest){
        response.closest = new Array();
        var $parents = null;
        if(request.pLocator == null || trimString(request.pLocator).length == 0){
            $parents = teJQuery("html:first");
        }else{
            try{
                $parents = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
                $parents = teJQuery("html:first");
            }
        }

        if($parents != null && $parents.length > 0){
            if(request.attributes != null){
                var keys = new Array();
                for(var key in request.attributes){
                    if(key != "tag"){
                        keys.push(key);
                    }
                }
                var builder = new jQueryBuilder();
                var jqs = "";
                var id = request.attributes["id"];
                var tag = request.attributes["tag"];

                if(tag == null || tag == undefined || tag.trim().length == 0){
                    //TODO: need to double check if this is correct or not in jQuery
                    tag = "*";
                }
//                alert("tag " + tag);
                var $closest = null;
                if(id != null && id != undefined && (!builder.isPartial(id))){
                    jqs = builder.buildId(id);
//                    alert("With ID jqs=" + jqs);
                    $closest = $parents.find(jqs);
//                    alert("Found closest " + $closest.length);
                    $closest.each(function() {
                        response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                    });
                }else{
                    jqs = tag;
//                    alert("jqs=" + jqs);
                    for(var m=0; m<keys.length; m++){
//                    for (var key in keys) {
                        var attr = keys[m];
                        if (!builder.inBlackList(attr)) {
                            var tsel = builder.buildSelector(attr, request.attributes[attr]);
                            var $mt = $parents.find(jqs + tsel);
//                            alert("Found for attr=" + attr + " val=" + request.attributes[attr] + " jqs=" + jqs + " tsel=" + tsel + " is " + $mt.length);
                            if ($mt.length > 0) {
                                $closest = $mt;
                                jqs = jqs + tsel;
                            }
//                            alert("jqs=" + jqs);
                        }
                    }
                    if ($closest != null && $closest.length > 0) {
                        $closest.each(function() {
                            response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                        });
                    }
                }
            }
        }
    }

    return JSON.stringify(response);
};
