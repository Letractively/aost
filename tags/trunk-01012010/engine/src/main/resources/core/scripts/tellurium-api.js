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
};

function TelluriumApi(cache){
    this.cache = cache;
};

TelluriumApi.prototype.blur = function(element) {
    teJQuery(element).blur();
};

TelluriumApi.prototype.click = function(element) {
    teJQuery(element).click();
};

TelluriumApi.prototype.clickAt = function(element, coordString) {
    var clientXY = getTargetXY(element, coordString);
    //TODO: how to do click at using jQuery
    teJQuery(element).click();
};

TelluriumApi.prototype.doubleClick = function(element){
    teJQuery(element).dblclick();
};

TelluriumApi.prototype.fireEvent = function(element, event){
    teJQuery(element).trigger(event);
};

TelluriumApi.prototype.focus = function(element){
    teJQuery(element).focus();
};

TelluriumApi.prototype.typeKey = function(element, key){
    var $elem = teJQuery(element);
	$elem.val($elem.val()+key).trigger(getEvent("keydown", key)).trigger(getEvent("keypress", key)).trigger(getEvent("keyup", key));
};

TelluriumApi.prototype.keyDown = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()).trigger(getEvent("keydown", key));
};

TelluriumApi.prototype.keyPress = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()+key).trigger(getEvent("keypress", key));
};

TelluriumApi.prototype.keyUp = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()).trigger(getEvent("keyup", key));
};

TelluriumApi.prototype.mouseOver = function(element){
   teJQuery(element).trigger('mouseover');
};

TelluriumApi.prototype.mouseDown = function(element){
   teJQuery(element).trigger('mousedown');
};

TelluriumApi.prototype.mouseDownRight = function(element){
    //TODO: how to fire right mouse down in jQuery?
    //   teJQuery(element).trigger('mousedown');
};

TelluriumApi.prototype.mouseEnter = function(element){
   teJQuery(element).trigger('mouseenter');
};

TelluriumApi.prototype.mouseLeave = function(element){
   teJQuery(element).trigger('mouseleave');
};

TelluriumApi.prototype.mouseOut = function(element){
   teJQuery(element).trigger('mouseout');
};

TelluriumApi.prototype.submit = function(element){
   teJQuery(element).submit();
};

TelluriumApi.prototype.check = function(element){
    element.checked = true;
};

TelluriumApi.prototype.uncheck = function(element){
    element.checked = false;
};

TelluriumApi.prototype.isElementPresent = function(element){
    if(element == null)
        return false;
    return true;
};

TelluriumApi.prototype.getAttribute = function(element, attributeName){
    return teJQuery(element).attr(attributeName);
};

TelluriumApi.prototype.waitForPageToLoad = function(timeout){
    selenium.doWaitForPageToLoad(timeout);
};

TelluriumApi.prototype.type = function(element, val){
    teJQuery(element).val(val);
};

TelluriumApi.prototype.select = function(element, optionLocator){
    var $sel = teJQuery(element);
    //first, remove all selected element
    $sel.find("option").removeAttr("selected");
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).attr("selected","selected");
};

TelluriumApi.prototype.addSelection = function(element, optionLocator){
    var $sel = teJQuery(element);
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).attr("selected","selected");
};

TelluriumApi.prototype.removeSelection = function(element, optionLocator){
    var $sel = teJQuery(element);
    //construct the select option
    var opt = "option[" + optionLocator + "]";
    //select the approporiate option
    $sel.find(opt).removeAttr("selected");
};

TelluriumApi.prototype.removeAllSelections = function(element){
    var $sel = teJQuery(element);
    //first, remove all selected element
    $sel.find("option").removeAttr("selected");
};

TelluriumApi.prototype.open = function(url){
    selenium.open(url);
};

TelluriumApi.prototype.getText = function(element) {
    return teJQuery(element).text();
};

TelluriumApi.prototype.isChecked = function(element) {
    if (element.checked == null) {
        throw new SeleniumError("Element is not a toggle-button.");
    }
    return element.checked;
};

TelluriumApi.prototype.isVisible = function(element) {
    return teJQuery(element).is(':visible');
};

TelluriumApi.prototype.isEditable = function(element) {
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
TelluriumApi.prototype.getAllText = function(element) {
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
    return JSON.stringify(out);
};

TelluriumApi.prototype.getCssSelectorCount = function(element) {
    var $e = teJQuery(element);
    if ($e == null)
        return 0;

    return $e.length;
};

TelluriumApi.prototype.getCSS = function(element, cssName) {
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).css(cssName));
    });
    return JSON.stringify(out);
};

TelluriumApi.prototype.isDisabled = function(element) {
    var $e = teJQuery(element);
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);
    if ($e.length > 1)
        Assert.fail("Element for " + locator + " is not unique.");
    return $e.attr('disabled');
};

TelluriumApi.prototype.getListSize = function(element, separators) {
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

TelluriumApi.prototype.useDiscardNewPolicy = function(){
    this.cache.useDiscardNewPolicy();
};

TelluriumApi.prototype.useDiscardOldPolicy = function(){
    this.cache.useDiscardOldPolicy();
};

TelluriumApi.prototype.useDiscardLeastUsedPolicy = function(){
    this.cache.useDiscardLeastUsedPolicy();
};

TelluriumApi.prototype.useDiscardInvalidPolicy = function(){
    this.cache.useDiscardInvalidPolicy();
};

TelluriumApi.prototype.getCachePolicyName = function(){
    return this.cache.getCachePolicyName();
};

TelluriumApi.prototype.getUseUiModule = function(json){
    return this.cache.useUiModule(json);
};

TelluriumApi.prototype.getValidateUiModule = function(json){
    return tellurium.cache.validateUiModule(json);
};

TelluriumApi.prototype.isUiModuleCached = function(id){
    return this.cache.isUiModuleCached(id);
};