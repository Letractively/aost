//Tellurium APIs to replace selenium APIs or new APIs

function getTargetXY(element, coordString) {
   // Parse coordString
   var coords = null;
   var x;
   var y;
   if (coordString) {
      coords = coordString.split(/,/);
      x = Number(coords[0]);
      y = Number(coords[1]);
   }
   else {
      x = y = 0;
   }
   var offset = teJQuery(element).offset();
   return [offset.left + x, offset.top + y];
}

function TelluriumApi(cache){
    this.cache = cache;
    this.textWorker = new TextUiWorker();
    this.toggleWorker = new ToggleUiWorker();
    this.colorWorker = new ColorUiWorker();
}

TelluriumApi.prototype.cacheAwareLocate = function(locator){
    //This is not really elegant, but we have to share this
    //locate strategy with Selenium. Otherwise, call Tellurium
    //methods directly.
    fbLog("Trying to find element with locator ", locator);
    return selenium.browserbot.findElement(locator);
};

TelluriumApi.prototype.blur = function(locator) {
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).blur();
};

TelluriumApi.prototype.click = function(locator) {
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).click();
};

TelluriumApi.prototype.clickAt = function(locator, coordString) {
    var element = this.cacheAwareLocate(locator);
    var clientXY = getTargetXY(element, coordString);
    //TODO: how to do click at using jQuery
    teJQuery(element).click();
};

TelluriumApi.prototype.doubleClick = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).dblclick();
};

TelluriumApi.prototype.fireEvent = function(locator, event){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger(event);
};

TelluriumApi.prototype.focus = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).focus();
};

TelluriumApi.prototype.typeKey = function(locator, key){
    var element = this.cacheAwareLocate(locator);
    var $elem = teJQuery(element);
	$elem.val($elem.val()+key).trigger(getEvent("keydown", key)).trigger(getEvent("keypress", key)).trigger(getEvent("keyup", key));
};

TelluriumApi.prototype.keyDown = function(locator, key){
    fbLog("Key Down cache aware locate", locator);
    var element = this.cacheAwareLocate(locator);   
    var $elem = teJQuery(element);
    $elem.val($elem.val()).trigger(getEvent("keydown", key));
};

TelluriumApi.prototype.keyPress = function(locator, key){
    var element = this.cacheAwareLocate(locator);
    var $elem = teJQuery(element);
    $elem.val($elem.val()+key).trigger(getEvent("keypress", key));
};

TelluriumApi.prototype.keyUp = function(locator, key){
    var element = this.cacheAwareLocate(locator);
    var $elem = teJQuery(element);
    $elem.val($elem.val()).trigger(getEvent("keyup", key));
};

TelluriumApi.prototype.mouseOver = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger('mouseover');
};

TelluriumApi.prototype.mouseDown = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger('mousedown');
};

TelluriumApi.prototype.mouseDownRight = function(locator){
    var element = this.cacheAwareLocate(locator);
    //TODO: how to fire right mouse down in jQuery?
    //   teJQuery(element).trigger('mousedown');
};

TelluriumApi.prototype.mouseEnter = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger('mouseenter');
};

TelluriumApi.prototype.mouseLeave = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger('mouseleave');
};

TelluriumApi.prototype.mouseOut = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).trigger('mouseout');
};

TelluriumApi.prototype.submit = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).submit();
};

TelluriumApi.prototype.check = function(locator){
    var element = this.cacheAwareLocate(locator);
    element.checked = true;
};

TelluriumApi.prototype.uncheck = function(locator){
    var element = this.cacheAwareLocate(locator);
    element.checked = false;
};

TelluriumApi.prototype.isElementPresent = function(locator){
    var element = this.cacheAwareLocate(locator);
    
    return element != null;
};

TelluriumApi.prototype.getAttribute = function(attributeLocator){
    var cal = JSON.parse(attributeLocator.substring(7), null);
    fbLog("Parsed attribute locator", cal);
    var locator = cal.locator;
    var attributeName = null;
    //get attribute name
    var attributePos = locator.lastIndexOf("@");
    if (attributePos != -1) {
        attributeName = locator.slice(attributePos + 1);
        if (attributeName.endsWith("]")) {
            attributeName = attributeName.substr(0, attributeName.length - 1);
        }
        fbLog("attribute name", attributeName);
        //update locator
        cal.locator = locator.slice(0, attributePos);
        if (cal.locator.endsWith("[")) {
            cal.locator = cal.locator.substr(0, cal.locator.length - 1);
        }
    }

    var json = "uimcal=" + JSON.stringify(cal);
    fbLog("re-jsonified locator", json);
    var element = this.cacheAwareLocate(json);
    return teJQuery(element).attr(attributeName);
};

TelluriumApi.prototype.waitForPageToLoad = function(timeout){
    selenium.doWaitForPageToLoad(timeout);
};

TelluriumApi.prototype.type = function(locator, val){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).val(val);
};

TelluriumApi.prototype.select = function(locator, optionLocator){
    var element = this.cacheAwareLocate(locator);
    var $sel = teJQuery(element);
    //first, remove all selected element
    $sel.find("option").removeAttr("selected");
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).attr("selected","selected");
};

TelluriumApi.prototype.addSelection = function(locator, optionLocator){
    var element = this.cacheAwareLocate(locator);
    var $sel = teJQuery(element);
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).attr("selected","selected");
};

TelluriumApi.prototype.removeSelection = function(locator, optionLocator){
    var element = this.cacheAwareLocate(locator);
    var $sel = teJQuery(element);
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).removeAttr("selected");
};

TelluriumApi.prototype.removeAllSelections = function(locator){
    var element = this.cacheAwareLocate(locator);
    var $sel = teJQuery(element);
    //first, remove all selected element
    $sel.find("option").removeAttr("selected");
};

TelluriumApi.prototype.open = function(url){
    selenium.open(url);
};

TelluriumApi.prototype.getText = function(locator) {
    var element = this.cacheAwareLocate(locator);
    return teJQuery(element).text();
};

TelluriumApi.prototype.isChecked = function(locator) {
    var element = this.cacheAwareLocate(locator);
    if (element.checked == null) {
        throw new SeleniumError("Element is not a toggle-button.");
    }
    return element.checked;
};

TelluriumApi.prototype.isVisible = function(locator) {
    var element = this.cacheAwareLocate(locator);
    return teJQuery(element).is(':visible');
};

TelluriumApi.prototype.isEditable = function(locator) {
    var element = this.cacheAwareLocate(locator);
    if (element.value == undefined) {
        Assert.fail("Element " + locator + " is not an input.");
    }
    if (element.disabled) {
        return false;
    }

    var readOnlyNode = element.getAttributeNode('readonly');
    if (readOnlyNode) {
        // DGF on IE, every input element has a readOnly node, but it may be false
        if (typeof(readOnlyNode.nodeValue) == "boolean") {
            var readOnly = readOnlyNode.nodeValue;
            if (readOnly) {
                return false;
            }
        } else {
            return false;
        }
    }
    return true;
};

TelluriumApi.prototype.getXpathCount = function(xpath) {
    return selenium.getXpathCount(xpath);
};


//// NEW APIS
TelluriumApi.prototype.getAllText = function(locator) {
    var element = this.cacheAwareLocate(locator);
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
    return JSON.stringify(out);
};

TelluriumApi.prototype.getCssSelectorCount = function(locator) {
   fbLog("GetCssSelectorCount for Locator", locator);
    if(locator.startsWith("jquery=")){
        locator = locator.substring(7);
    }else if(locator.startsWith("uimcal=")){
        var cal = JSON.parse(locator.substring(7), null);
        locator = cal.locator;
    }
    fbLog("Parsed locator", locator);
    var $e = teJQuery(selenium.browserbot.findElement(locator));
    fbLog("Found elements for CSS Selector", $e.get());
    if ($e == null)
        return 0;

    return $e.length;
};

TelluriumApi.prototype.getCSS = function(locator, cssName) {
    var element = this.cacheAwareLocate(locator);
    var out = [];
    var $e = teJQuery(element);
    for(var i=0; i<$e.length; i++){
        var elem = $e.get(i);
        var val = teJQuery(elem).css(cssName);
         //need to walk up the tree if the color is transparent
        if(val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")){
            val = getColor(elem, cssName);
        }
        out.push(val);
    }

    return JSON.stringify(out);
};

TelluriumApi.prototype.isDisabled = function(locator) {
    var element = this.cacheAwareLocate(locator);
    var $e = teJQuery(element);
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);
    if ($e.length > 1)
        Assert.fail("Element for " + locator + " is not unique.");
    return $e.attr('disabled');
};

TelluriumApi.prototype.getListSize = function(locator, separators) {
    var element = this.cacheAwareLocate(locator);
    var $e = teJQuery(element);
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);

    //TODO: this may not be correct for example we have div/div/div span/span, what would $(().find("div, span") return?
//    var jq = separators.join(",")

//    var list = $e.find(separators);
    var list = $e.children(separators);

    return list.length;
};


TelluriumApi.prototype.getCacheState = function(){

    return this.cache.cacheOption;
};

TelluriumApi.prototype.enableCache = function(){
    this.cache.cacheOption = true;
};

TelluriumApi.prototype.disableCache = function(){
    this.cache.cacheOption = false;
};

TelluriumApi.prototype.cleanCache = function(){
    this.cache.cleanCache();
};

TelluriumApi.prototype.setCacheMaxSize = function(size){
    this.cache.maxCacheSize = size;
};

TelluriumApi.prototype.getCacheSize = function(){
    return this.cache.getCacheSize();
};

TelluriumApi.prototype.getCacheMaxSize = function(){
    return this.cache.maxCacheSize;
};

TelluriumApi.prototype.getCacheUsage = function(){
    return this.cache.getCacheUsage();
};

TelluriumApi.prototype.addNamespace = function(prefix, namespace){
    selenium.browserbot.addNamespace(prefix, namespace);
};

TelluriumApi.prototype.getNamespace = function(prefix){
   return selenium.browserbot._namespaceResolver(prefix);
};

TelluriumApi.prototype.useDiscardNewCachePolicy = function(){
    this.cache.useDiscardNewPolicy();
};

TelluriumApi.prototype.useDiscardOldCachePolicy = function(){
    this.cache.useDiscardOldPolicy();
};

TelluriumApi.prototype.useDiscardLeastUsedCachePolicy = function(){
    this.cache.useDiscardLeastUsedPolicy();
};

TelluriumApi.prototype.useDiscardInvalidCachePolicy = function(){
    this.cache.useDiscardInvalidPolicy();
};

TelluriumApi.prototype.getCachePolicyName = function(){
    return this.cache.getCachePolicyName();
};

TelluriumApi.prototype.getUseUiModule = function(json){
    return this.cache.useUiModule(json);
};

TelluriumApi.prototype.getValidateUiModule = function(json){
    return this.cache.validateUiModule(json);
};

TelluriumApi.prototype.isUiModuleCached = function(id){
    return this.cache.isUiModuleCached(id);
};

TelluriumApi.prototype.useClosestMatch = function(isUse){
     this.cache.useClosestMatch(isUse);
};  

TelluriumApi.prototype.useTeApi = function(isUse){
    tellurium.useTeApi(isUse);
};

TelluriumApi.prototype.toggle = function(locator){
    var element = this.cacheAwareLocate(locator);
    teJQuery(element).toggle();
};

TelluriumApi.prototype.deleteAllCookiesByJQuery = function() {
    teJQuery.cookies.del(true);
};

TelluriumApi.prototype.deletelCookieByJQuery = function(cookieName) {
    teJQuery.cookies.del(cookieName);
};

TelluriumApi.prototype.setCookieByJQuery = function(cookieName, value, options){
    teJQuery.cookies.set(cookieName, value, options);
};

TelluriumApi.prototype.getCookieByJQuery = function(cookieName){
    return teJQuery.cookies.get(cookieName);
};

TelluriumApi.prototype.updateEngineState = function(json){
    var state = JSON.parse(json, null);
    tellurium.cache.useClosestMatch(state.relax);
    tellurium.useTeApi(state.teApi);
    tellurium.cache.cacheOption = state.cache;
};

TelluriumApi.prototype.getEngineState = function(){
    var state = new EngineState();
    state.relax = tellurium.cache.uiAlg.allowRelax;
    state.teApi = tellurium.isUseTeApi;
    state.cache = tellurium.cache.cacheOption;

    return JSON.stringify(state);
};

TelluriumApi.prototype.getAllTableBodyText = function(uid) {
    var context = new WorkflowContext();
    var obj = this.cache.walkToUiObjectWithException(context, uid);
    if(obj.respondsToWithException("getAllBodyCell")){
        var out = obj.getAllBodyCell(context, this.textWorker);
        fbLog("Get All Table Body Text Result", out);

        return out;
    }

    return null;
};

/*
TelluriumApi.prototype.getAllTableBodyText = function(uid) {
    var context = new WorkflowContext();
    var obj = this.cache.walkToUiObject(context, uid);
    if(obj == null){
        fbError("Cannot find UI object " + uid, this);
        throw new SeleniumError("Cannot find UI object " + uid);
    }else{
        if(obj["getAllBodyCell"] != undefined){
            var out = obj.getAllBodyCell(context, this.textWorker);
            fbLog("Get All Table Body Text Result", out);

            return out;
        }else{
            fbError("UI Object " + uid + " does not have the method getAllBodyCell", obj);
            throw new SeleniumError("UI Object " + uid + " does not have the method getAllBodyCell");
        }
    }
};*/

TelluriumApi.prototype.showUi = function(uid, delay){
    var context = new WorkflowContext();
    var elist = this.cache.getIndexedTree(context, uid);
    this.toggleWorker.work(context, elist, delay);
//    this.colorWorker.work(context, elist, delay);

/*
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
    */
};