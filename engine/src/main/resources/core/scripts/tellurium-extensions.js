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
};

function DiagnosisResponse(){
    this.uid = null;
    this.count = 0;
    this.matches = null;
    this.parents = null;
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
    var $e = teJQuery(this.browserbot.findElement(locator));
    response.count = $e.length;
    if(request.retHtml){
        response.matches = new Array();
        $e.each(function() {
            response.matches.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
        });
    }

    if(request.retParent){
        response.parents = new Array();
        //if the parent is null or empty, return the whole html source
        if(request.pLocator == null || trimString(request.pLocator).length == 0){
            response.parents.push(teJQuery("html")[0].innerHTML);
        }else{
            var $p = teJQuery(this.browserbot.findElement(request.pLocator));

            $p.each(function() {
                response.parents.push(teJQuery(this).html());
            });
        }        
    }

    return JSON.stringify(response);
};