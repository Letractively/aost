// User extensions can be added here.
//
// Keep this file to avoid  mystifying "Invalid Character" error in IE

Selenium.prototype.getAllText = function(locator) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
    return JSON.stringify(out);
};

Selenium.prototype.getCssSelectorCount = function(locator) {
    fbLog("GetCssSelectorCount for Locator", locator);
    if(locator.startsWith("jquery=")){
        locator = locator.substring(7);
    }else if(locator.startsWith("uimcal=")){
        var cal = JSON.parse(locator.substring(7), null);
         locator = cal.locator;
    }
    fbLog("Parsed locator", locator);
    var $e = teJQuery(this.browserbot.findElement(locator));
    fbLog("Found elements for CSS Selector", $e.get());
    if ($e == null)
        return 0;

    return $e.length;
};

Selenium.prototype.getCSS = function(locator, cssName) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    for(var i=0; i<$e.length; i++){
        var elem = $e.get(i);
        var val = teJQuery(elem).css(cssName);
        //['backgroundColor', 'borderBottomColor', 'borderLeftColor', 'borderRightColor', 'borderTopColor', 'color', 'outlineColor']
         //need to walk up the tree if the color is transparent
        if(val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")){
            val = getColor(elem, cssName);
        }
        out.push(val);
    }
/*    $e.each(function() {
        var val = teJQuery(this).css(cssName);
        //need to walk up the tree if the color is transparent
        if(val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")){
            val = getColor(this, cssName);
        }
        out.push(val);        
//        out.push(teJQuery(this).css(cssName));
    });*/
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

Selenium.prototype.doUseDiscardNewCachePolicy = function(){
    tellurium.cache.useDiscardNewPolicy();
};

Selenium.prototype.doUseDiscardOldCachePolicy = function(){
    tellurium.cache.useDiscardOldPolicy();
};

Selenium.prototype.doUseDiscardLeastUsedCachePolicy = function(){
    tellurium.cache.useDiscardLeastUsedPolicy();
};

Selenium.prototype.doUseDiscardInvalidCachePolicy = function(){
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


function DiagnosisRequest(){
    this.uid = null;
    this.pLocator = null;
    this.attributes = null;
    this.retMatch = true;
    this.retHtml = true;
    this.retParent = true;
    this.retClosest = true;
}

function DiagnosisResponse(){
    this.uid = null;
    this.count = 0;
    this.matches = null;
    this.parents = null;
    this.closest = null;
    this.html = null;
}

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
    fbLog("diagnosis request", request);
    
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
        if(request.pLocator == null || trimString(request.pLocator).length == 0 || request.pLocator == "jquery="){
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
        if(request.pLocator == null || trimString(request.pLocator).length == 0 || request.pLocator == "jquery="){
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
//    return tellurium.processMacroCmd();
    return tellurium.dispatchMacroCmd();
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

Selenium.prototype.doUseTeApi = function(isUse){
    tellurium.useTeApi(isUse);
};

Selenium.prototype.doUseClosestMatch = function(isUse){
     tellurium.cache.useClosestMatch(isUse);
};

Selenium.prototype.doToggle = function(locator){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.toggle();
};

Selenium.prototype.doDeleteAllCookies = function() {
    jaaulde.utils.cookies.del(true);
};

Selenium.prototype.doDeleteAllCookiesByJQuery = function() {
    teJQuery.cookies.del(true);
};

Selenium.prototype.doDeletelCookieByJQuery = function(cookieName) {
    teJQuery.cookies.del(cookieName);
};

Selenium.prototype.doSetCookieByJQuery = function(cookieName, value, options){
    teJQuery.cookies.set(cookieName, value, options);
};

Selenium.prototype.getCookieByJQuery = function(cookieName){
    return teJQuery.cookies.get(cookieName);
};

Selenium.prototype.doUseClosestMatch = function(isUse){
    tellurium.cache.useClosestMatch(isUse);
};

Selenium.prototype.doUpdateEngineState = function(json){
    var state = JSON.parse(json, null);
    tellurium.cache.useClosestMatch(state.relax);
    tellurium.useTeApi(state.teApi);
    tellurium.cache.cacheOption = state.cache;
};

Selenium.prototype.getEngineState = function(){
    var state = new EngineState();
    state.relax = tellurium.cache.uiAlg.allowRelax;
    state.teApi = tellurium.isUseTeApi;
    state.cache = tellurium.cache.cacheOption;

    return JSON.stringify(state);
};

Selenium.prototype.doShowUi = function(uid, delay){
/*    var elist = tellurium.getUiElementAndDescendant(uid);
    if(elist != null && elist.length > 0){
        var $es = teJQuery(elist);
        $es.each(function() {
            var $te = teJQuery(this);
            $te.data("te-color-bak", $te.css("color"));
        });
        $es.css("color", "read");
        $es.delay(delay);
        $es.each(function() {
            //back to the original color
            var $te = teJQuery(this);
            $te.css("color", $te.data("te-color-bak"));
            $te.removeData("te-color-bak");
        });
    }*/
    var elist = tellurium.getUiElementAndDescendant(uid);

    if(elist != null && elist.length > 0){
        var $es = teJQuery(elist);
        $es.each(function() {
            var $te = teJQuery(this);
            $te.data("te-color-bak", $te.css("background-color"));
        });
        $es.css("background-color", "red");
        fbLog("Set elements to red for " + uid, $es.get());
//        $es.delay(delay);
//        $es.slideDown().delay(delay).fadeIn();
//        $es.first().slideUp(300).delay(800).fadeIn(400);
        $es.first().slideUp(100).slideDown(100).delay(delay).fadeOut(100).fadeIn(100);
        fbLog("Delayed for " + delay, this);
        $es.each(function() {
            //back to the original color
            var $te = teJQuery(this);
            $te.css("background-color", $te.data("te-color-bak"));
            $te.removeData("te-color-bak");
        });
        fbLog("Elements' color restored to original ones for " + uid, $es.get());
    }
};
