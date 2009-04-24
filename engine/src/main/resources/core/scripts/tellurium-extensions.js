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
    var $e = jQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(jQuery(this).text());
    });
    return JSON.stringify(out);
};

Selenium.prototype.getJQuerySelectorCount = function(locator) {
    var $e = jQuery(this.browserbot.findElement(locator));
    if ($e == null)
        return 0;

    return $e.length;
};

Selenium.prototype.getCSS = function(locator, cssName) {
    var out = [];
    var $e = jQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(jQuery(this).css(cssName));
    });
    return JSON.stringify(out);
};

Selenium.prototype.isDisabled = function(locator) {
    var $e = jQuery(this.browserbot.findElement(locator));
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);
    if ($e.length > 1)
        Assert.fail("Element for " + locator + " is not unique.");
    return $e.attr('disabled');
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

/*
Selenium.prototype.doTypeRepeated = function(locator, text) {
    // All locator-strategies are automatically handled by "findElement"
    var element = this.page().findElement(locator);

    // Create the text to type
    var valueToType = text + text;

    // Replace the element text with the new text
    this.page().replaceText(element, valueToType);
};
*/
