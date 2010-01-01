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

Selenium.prototype.getCssSelectorCount = function(locator) {
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

    return tellurium.cache.cacheOption;
};

Selenium.prototype.doEnableCache = function(){
    tellurium.cache.cacheOption = true;
};

Selenium.prototype.doDisableCache = function(){
    tellurium.cache.cacheOption = false;
};

Selenium.prototype.doCleanCache = function(){
    tellurium.cache.cleanCache();
};

Selenium.prototype.doSetCacheMaxSize = function(size){
    tellurium.cache.maxCacheSize = size;
};

Selenium.prototype.getCacheSize = function(){
    return tellurium.cache.getCacheSize();
};

Selenium.prototype.getCacheMaxSize = function(){
    return tellurium.cache.maxCacheSize;
};

Selenium.prototype.getCacheUsage = function(){
    return tellurium.cache.getCacheUsage();
};

Selenium.prototype.doAddNamespace = function(prefix, namespace){
    this.browserbot.addNamespace(prefix, namespace);
};

Selenium.prototype.getNamespace = function(prefix){
   return this.browserbot._namespaceResolver(prefix);
};

Selenium.prototype.doUseDiscardNewPolicy = function(){
    tellurium.cache.useDiscardNewPolicy();
};

Selenium.prototype.doUseDiscardOldPolicy = function(){
    tellurium.cache.useDiscardOldPolicy();
};

Selenium.prototype.doUseDiscardLeastUsedPolicy = function(){
    tellurium.cache.useDiscardLeastUsedPolicy();
};

Selenium.prototype.doUseDiscardInvalidPolicy = function(){
    tellurium.cache.useDiscardInvalidPolicy();
};

Selenium.prototype.getCachePolicyName = function(){
    return tellurium.cache.getCachePolicyName();
};

Selenium.prototype.doTypeKey = function(locator, key){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.val($elem.val()+key).trigger(getEvent("keydown", key)).trigger(getEvent("keypress", key)).trigger(getEvent("keyup", key));
};

Selenium.prototype.doTriggerEvent = function(locator, event){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.trigger(event);
};

Selenium.prototype.doDeleteAllCookies = function() {
    jaaulde.utils.cookies.del(true);
}

function DiagnosisRequest(){
    this.uid = null;
    this.pLocator = null;
    this.attributes = null;
    this.retMatch = true;
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
    this.html = null;
};

Selenium.prototype.getHtml = function(){
    var $iframe = teJQuery("#selenium_myiframe");
    var $p = null;
    if($iframe != null && $iframe.length > 0){
        //run in single Window mode
        $p = $iframe.contents().find("html:first");
    }else{
        //run in multiple Window mode
        //TODO: need to double check this
        $p = teJQuery(this.browserbot.findElement("//html"));
    }

    return $p;
};

Selenium.prototype.getDiagnosisResponse = function(locator, req){
    var dreq = JSON.parse(req, null);

    var request = new DiagnosisRequest();
    request.uid = dreq.uid;
    request.pLocator = dreq.pLocator;
    request.attributes = dreq.attributes;
    request.retMatch = dreq.retMatch;
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
        if (request.retMatch) {
            response.matches = new Array();
            $e.each(function() {
//                response.matches.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                response.matches.push(teJQuery(this).outerHTML());
            });
        }
    }

    if(request.retParent){
        response.parents = new Array();
        //if the parent is null or empty, return the whole html source
        if(request.pLocator == null || trimString(request.pLocator).length == 0){
//            response.parents.push(teJQuery('<div>').append(teJQuery("html:first").clone()).html());
             response.parents.push(teJQuery('<div>').append(this.getHtml().clone()).html());
        }else{
            var $p = null;
            try{
                $p = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
//                $p = teJQuery("html:first");
//                $p = teJQuery("#selenium_myiframe").contents().find("html:first");
                $p = this.getHtml();
            }

            $p.each(function() {
//                response.parents.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                response.parents.push(teJQuery(this).outerHTML());
            });
        }        
    }

    if(request.retClosest){
        response.closest = new Array();
        var $parents = null;
        if(request.pLocator == null || trimString(request.pLocator).length == 0){
        //    $parents = teJQuery("html:first");
//            $parents =  teJQuery("#selenium_myiframe").contents().find("html:first");
            $parents = this.getHtml();
        }else{
            try{
                $parents = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
             //   $parents = teJQuery("html:first");
             //   $parents =  teJQuery("#selenium_myiframe").contents().find("html:first");
                $parents = this.getHtml();
            }
        }

        if($parents != null && $parents.length > 0){
            if(request.attributes != null){

                var builder = new JQueryBuilder();

                var keys = new Array();
                for(var key in request.attributes){
                    if(key != "tag" && (!builder.inBlackList(key))){
                        keys.push(key);
                    }
                }
                var jqs = "";
                var id = request.attributes["id"];
                var tag = request.attributes["tag"];

                if(tag == null || tag == undefined || tag.trim().length == 0){
                    //TODO: need to double check if this is correct or not in jQuery
                    tag = "*";
                }
//                alert("tag " + tag);
//                var $closest = null;
                //Use tag for the initial search
                var $closest = $parents.find(tag);
                if(id != null && id != undefined && (!builder.isPartial(id))){
                    jqs = builder.buildId(id);
//                    alert("With ID jqs=" + jqs);
                    $closest = $parents.find(jqs);
//                    alert("Found closest " + $closest.length);
                    $closest.each(function() {
//                        response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                        response.closest.push(teJQuery(this).outerHTML());
                    });
                }else{
                    jqs = tag;
//                    alert("jqs=" + jqs);
                    for (var m = 0; m < keys.length; m++) {
                        var attr = keys[m];
                        var tsel = builder.buildSelector(attr, request.attributes[attr]);
                        var $mt = $parents.find(jqs + tsel);
//                            alert("Found for attr=" + attr + " val=" + request.attributes[attr] + " jqs=" + jqs + " tsel=" + tsel + " is " + $mt.length);
                        if ($mt.length > 0) {
                            $closest = $mt;
                            jqs = jqs + tsel;
                        }
//                            alert("jqs=" + jqs);
                    }
                    if ($closest != null && $closest.length > 0) {
                        $closest.each(function() {
//                            response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                            response.closest.push(teJQuery(this).outerHTML());
                        });
                    }
                }
            }
        }
    }

    if(request.retMatch){
//       $html = teJQuery("#selenium_myiframe").contents().find("html:first");
//       response.html = teJQuery('<div>').append($html.clone()).html();
//       response.html =  teJQuery('<div>').append(this.getHtml().clone()).html();
       response.html = this.getHtmlSource();
    }
//    return JSON.stringify(response);
    return response;
};

Selenium.prototype.getBundleResponse = function(bundle){
    tellurium.parseMacroCmd(bundle);
    return tellurium.processMacroCmd();
};

Selenium.prototype.getUseUiModule = function(json){
    return tellurium.cache.useUiModule(json);
};

Selenium.prototype.getValidateUiModule = function(json){
    return tellurium.cache.validateUiModule(json);
};

Selenium.prototype.isUiModuleCached = function(id){
    return tellurium.cache.isUiModuleCached(id);
};

Selenium.prototype.doUseTeApi = function(isUseApi){
     if ("true" == isUseApi || "TRUE" == isUseApi) {
         tellurium.isUseTeApi = true;
     }else{
         tellurium.isUseTeApi = false;
     }
};

Selenium.prototype.doUseClosestMatch = function(isUse){
     tellurium.cache.useClosestMatch(isUse);
};