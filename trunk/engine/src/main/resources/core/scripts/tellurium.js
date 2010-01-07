var getEvent = function(name, key){
    var e = teJQuery.Event(name);
    e.which = key.charCodeAt(0);
    return e;
};

var tellurium = null;

teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    tellurium.initialize();

/*
    fbLog("Add jquery locate strategy", selenium);
    var strategyFunction = tellurium.locateElementByCSSSelector(locator, inDocument, inWindow);
    //selenium.locateByJQuery;

    var safeStrategyFunction = function() {
        try {
            return strategyFunction.apply(selenium, arguments);
        } catch (ex) {
            throw new SeleniumError("Error executing strategy function jquery" + ": " + extractExceptionMessage(ex));
        }
    }
    selenium.browserbot.locationStrategies["jquery"] = safeStrategyFunction;*/

/*  //seems not really work
    selenium.doAddLocationStrategy("jquery", "return tellurium.locateElementByCSSSelector(locator, inDocument, inWindow);");
    fbLog("Add jquery locate strategy", selenium);*/
});


/*
Selenium.prototype.locateByJQuery = function(locator, inDocument, inWindow){
    return tellurium.locateElementByCSSSelector(locator, inDocument, inWindow);
};
*/


//add custom jQuery Selector :te_text()
//

teJQuery.extend(teJQuery.expr[':'], {
    te_text: function(a, i, m) {
        return teJQuery.trim(teJQuery(a).text()) === teJQuery.trim(m[3]);
    }
});

teJQuery.expr[':'].group = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = m[3].split(",");
      var result = true;

      for(var i=0; i<splitted.length; i++){
         result = result && ($this.find(splitted[i]).length > 0);
      }

      return result;
};

teJQuery.expr[':'].styles = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = new Array();

      var fs = m[3].split(/:|;/);
      for(var i=0; i<fs.length; i++){
          var trimed = teJQuery.trim(fs[i]);
          if(trimed.length > 0){
              splitted.push(trimed);
          }
      }

      var result = true;

      var l=0;
      while(l < splitted.length){
         result = result && (teJQuery.trim($this.css(splitted[l])) == splitted[l+1]);
         l=l+2;
      }

      return result;
};

teJQuery.fn.outerHTML = function() {
    return teJQuery("<div/>").append( teJQuery(this[0]).clone() ).html();
};

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

// Command Request for Command bundle
function CmdRequest(){
    this.sequ = 0;
    this.uid = null;
    this.name = null;
    this.args = null;
};

// Command Request for Command bundle
function CmdResponse(){
    this.sequ = 0;
    this.name = null;
    this.returnType = null;
    this.returnResult = null;
};

function BundleResponse(){
    this.response = new Array();
};

BundleResponse.prototype.addVoidResponse = function(sequ, name){
    var resp = new CmdResponse();
    resp.sequ = sequ;
    resp.name = name;
    resp.returnType = "VOID";
    resp.returnResult = null;

    this.response.push(resp);
};

BundleResponse.prototype.addResponse = function(sequ, name, returnType, returnResult){
    var resp = new CmdResponse();
    resp.sequ = sequ;
    resp.name = name;
    resp.returnType = returnType;
    resp.returnResult = returnResult;

    this.response.push(resp);
};

BundleResponse.prototype.getResponse = function(){
    return this.response;
};

BundleResponse.prototype.toJSon = function(){
    var out = [];
    for(var i=0; i<this.response.length; i++){
        out.push(this.response[i]);
    }

    return JSON.stringify(out);
};

function MacroCmd(){
    this.bundle = new Array();

};

MacroCmd.prototype.size = function(){
    return this.bundle.length;
};

MacroCmd.prototype.first = function(){
    return this.bundle.shift();
};

MacroCmd.prototype.empty = function(){
    this.bundle = new Array();
};

MacroCmd.prototype.addCmd = function(sequ, uid, name, args){
    var cmd = new CmdRequest();
    cmd.sequ = sequ;
    cmd.uid = uid;
    cmd.name = name;
    cmd.args = args;
    this.bundle.push(cmd);
};

MacroCmd.prototype.parse = function(json){
    //Need to empty the bundle otherwise, old bundle commands will stay in the case of exception
    this.empty();
    var cmdbundle = JSON.parse(json, null);
    for(var i=0; i<cmdbundle.length; i++){
        this.addCmd(cmdbundle[i].sequ,  cmdbundle[i].uid, cmdbundle[i].name, cmdbundle[i].args);
    }
};

function TelluriumCommandHandler(api, requireElement, returnType) {
    //api method
    this.api = api;
    //whether it requires an element/elements to act on
    this.requireElement = requireElement;
    //return type
    this.returnType = returnType;
};

function Tellurium (){

    this.cache = new TelluriumCache();

    this.currentWindow = null;

    this.currentDocument = null;

    //Macro command for Tellurium
    this.macroCmd = new MacroCmd();

    //whether to use Tellurium new jQuery selector based APIs
    this.isUseTeApi = false;

    //cache to hold the element corresponding to a UID in command bundle
//    this.cbCache = new Hashtable();

    this.teApi = new TelluriumApi(this.cache);

    //api name to method mapping for command bundle processing
    this.apiMap = new Hashtable();

};

Tellurium.prototype.isUseCache = function(){
    return this.cache.cacheOption;    
};

//TODO: How to handle custom calls?  delegate to Selenium?
//TODO: Refactor --> use Javascript itself to do automatically discovery like selenium does instead of manually registering them
Tellurium.prototype.initialize = function(){
    this.registerApi("isElementPresent", true, "BOOLEAN");
    this.registerApi("blur", true, "VOID");
    this.registerApi("click", true, "VOID");
    this.registerApi("doubleClick", true, "VOID");
    this.registerApi("fireEvent", true, "VOID");
    this.registerApi("focus", true, "VOID");
    this.registerApi("type", true, "VOID");
    this.registerApi("typeKey", true, "VOID");
    this.registerApi("keyDown", true, "VOID");
    this.registerApi("keyPress", true, "VOID");
    this.registerApi("keyUp", true, "VOID");
    this.registerApi("mouseOver", true, "VOID");
    this.registerApi("mouseDown", true, "VOID");
    this.registerApi("mouseEnter", true, "VOID");
    this.registerApi("mouseOut", true, "VOID");
    this.registerApi("mouseLeave", true, "VOID");
    this.registerApi("submit", true, "VOID");
    this.registerApi("check", true, "VOID");
    this.registerApi("uncheck", true, "VOID");
    this.registerApi("waitForPageToLoad", false, "VOID");
    this.registerApi("getAttribute", true, "STRING");
    this.registerApi("select", true, "VOID");
    this.registerApi("addSelection", true, "VOID");
    this.registerApi("removeSelection", true, "VOID");
    this.registerApi("removeAllSelections", true, "VOID");
    this.registerApi("open", false, "VOID");
    this.registerApi("getText", true, "STRING");
    this.registerApi("isChecked", true, "BOOLEAN");
    this.registerApi("isVisible", true, "BOOLEAN");
    this.registerApi("isEditable", true, "BOOLEAN");
    this.registerApi("getXpathCount", false, "NUMBER");

    //converted from custom selenium apis, tellurium-extensions.js
    this.registerApi("getAllText", true, "STRING");
    this.registerApi("getCssSelectorCount", true, "NUMBER");
    this.registerApi("getCSS", true, "STRING");
    this.registerApi("isDisable", true, "BOOLEAN");
    this.registerApi("getListSize", true, "NUMBER");
    this.registerApi("getCacheState", false, "STRING");
    this.registerApi("enableCache", false, "VOID");
    this.registerApi("disableCache", false, "VOID");
    this.registerApi("cleanCache", false, "VOID");
    this.registerApi("setCacheMaxSize", false, "VOID");
    this.registerApi("getCacheSize", false, "NUMBER");
    this.registerApi("getCacheMaxSize", false, "NUMBER");
    this.registerApi("getCacheUsage", false, "STRING");
    this.registerApi("addNamespace", false, "VOID");
    this.registerApi("getNamespace", false, "STRING");
    this.registerApi("useDiscardNewCachePolicy", false, "VOID");
    this.registerApi("useDiscardOldCachePolicy", false, "VOID");
    this.registerApi("useDiscardLeastUsedCachePolicy", false, "VOID");
    this.registerApi("useDiscardInvalidCachePolicy", false, "VOID");
    this.registerApi("getCachePolicyName", false, "STRING");

    this.registerApi("getUseUiModule", false, "STRING");
    this.registerApi("getValidateUiModule", false, "STRING");
    this.registerApi("useClosestMatch", false, "VOID");
    this.registerApi("useTeApi", false, "VOID"); 
    this.registerApi("isUiModuleCached", false, "BOOLEAN");
    this.registerApi("toggle", true, "VOID");
};

Tellurium.prototype.useTeApi = function(isUse){
    if (typeof(isUse) == "boolean") {
        tellurium.isUseTeApi = isUse;
    } else {
        if ("true" == isUse || "TRUE" == isUse) {
            tellurium.isUseTeApi = true;
        } else {
            tellurium.isUseTeApi = false;
        }
    }
};

Tellurium.prototype.registerApi = function(apiName, requireElement, returnType){
    var api =  this.teApi[apiName];

    if (typeof(api) == 'function') {
        this.apiMap.put(apiName, new TelluriumCommandHandler(api, requireElement, returnType));
    }
};

Tellurium.prototype.isApiMissing =function(apiName){

    return this.apiMap.get(apiName) == null;
};

Tellurium.prototype.parseMacroCmd = function(json){
    this.macroCmd.parse(json);
};

Tellurium.prototype.prepareArgumentList = function(handler, args, element){
    if(args == null)
        return [];

    var params = [];

    if (handler.requireElement) {
        params.push(element);
        for (var i = 1; i < args.length; i++) {
            params.push(args[i]);
        }
    } else {
        params = args;
    }

    return params;
};

function validateDomRef(domref){
    try{
        return teJQuery(domref).is(':visible');
    }catch(e){
        fbError("Dom reference is not valid", e);
        return false;
    }
};

Tellurium.prototype.getUiElementFromCache = function(uid){

    return this.cache.getCachedUiElement(uid);
};

Tellurium.prototype.dispatchCommand = function(response, cmd, element){
    var result = null;

    var handler = this.apiMap.get(cmd.name);

    if(handler != null){
        var api = handler.api;
        //prepare the argument list
        var params = this.prepareArgumentList(handler, cmd.args, element);
        if(params != null && params.length > 0){
            if(handler.returnType == "VOID"){
                api.apply(this, params);
            }else{
                result = api.apply(this, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }else{
            if(handler.returnType == "VOID"){
                api.apply(this, params);
            }else{
                result = api.apply(this, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }

    }else{
        throw SeleniumError("Unknown command " + cmd.name + " in Command Bundle.");
    }
};

Tellurium.prototype.locate = function(locator){

    return selenium.browserbot.findElement(locator);
};

Tellurium.prototype.isLocator = function(locator){
    if(typeof(locator) != "string")
        return false;
    
    return locator.startsWith('//') || locator.startsWith('jquery=') || locator.startsWith('jquerycache=') || locator.startsWith('document.');
};

Tellurium.prototype.camelizeApiName = function(apiName){
    return "do" + apiName.charAt(0).toUpperCase() + apiName.substring(1);
};

Tellurium.prototype.processMacroCmd = function(){

    var response = new BundleResponse();

    while (this.macroCmd.size() > 0) {
        var cmd = this.macroCmd.first();
        //If don't want to use Tellurium APIs
        //or counld not find the approporiate API from Tellurium APIs, delete it to selenium directly
        //TODO: pay attention to tellurium only APIs, should not delegate to selenium if they are Tellurium only
        //should be fine if the same methods are duplicated in Selenium as well
        if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
            this.delegateToSelenium(response, cmd);
        } else {
            var element = null;
            fbLog("Process Macro Command: ", cmd);
            var locator = cmd.args[0];
            //some commands do not have any arguments, null guard args
            if (locator != null) {
                var isLoc = this.isLocator(locator);
                //if the first argument is a locator
                if (isLoc) {
                    //handle attribute locator for the getAttribute call
                    //pay attention to the xpath such as
                    // //descendant-or-self::table/descendant-or-self::input[@title="Google Search" and @name="q"]/self::node()[@disabled]
                    if (cmd.name == "getAttribute" || cmd.name == "isElementPresent") {
                        var attributePos = locator.lastIndexOf("@");
                        var attributeName = locator.slice(attributePos + 1);
                        if(attributeName.endsWith("]")){
                            attributeName = attributeName.substr(0, attributeName.length-1);
                        }
                        cmd.args.push(attributeName);
                        locator = locator.slice(0, attributePos);
                        if(locator.endsWith("[")){
                            locator = locator.substr(0, locator.length-1);
                        }
                    }

                    if (cmd.uid == null) {
                        element = this.locate(locator);
                    } else {
                        fbLog("Tellurium Cache option: ", this.isUseCache());
                        if(this.isUseCache()){
                            element = this.getUiElementFromCache(cmd.uid);
                            fbLog("Got UI element " + cmd.uid + " from Cache.", element);
                        }
                        
                        if (element == null) {
                            element = this.locate(locator);
                            if (element != null) {
                                //TODO: need to check if the element is cachable??
//                                this.cbCache.put(cmd.uid, element);
                            }else{

                                throw SeleniumError("Cannot locate element for uid " + cmd.uid + " in Command " + cmd.name + ".");
                            }
                        }

                    }
                }
            }

            this.dispatchCommand(response, cmd, element);
        }
    }

    return response.toJSon();
};

Tellurium.prototype.locateElementByCSSSelector = function(locator, inDocument, inWindow){
    var loc = locator;
    var attr = null;
    var isattr = false;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        loc = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }
    var found = teJQuery(inDocument).find(loc);
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

//TODO: depreciate this method since the UI module is built in
Tellurium.prototype.locateElementByCacheAwareCSSSelector = function(locator, inDocument, inWindow){
    var input = this.parseLocator(locator);
    var $found = null;
    
    //If do not cache selector or meta command without UID, directly find CSS selector
    if((!this.cacheOption) || input.metaCmd.uid == null || trimString(input.metaCmd.uid).length == 0){
        //cannot cache without uid, thus, go directly to find the element using jQuery
         $found = teJQuery(inDocument).find(input.optimized);
         this.validateResult($found);
         return this.convResult($found, input);
    }else{
        var sid = new Uiid();
        sid.convertToUiid(input.metaCmd.uid);
        var key = sid.getUid();
        //if this selector is cacheable, need to check the cache first
        if(input.metaCmd.cacheable){
            $found = this.cache.checkSelectorFromCache(key);
            
            if($found == null){
                //could not find from cache or the cached one is invalid
                while(sid.size() > 1){
                    //try to find from its ancestor
                    sid.pop();
                    var akey = sid.getUid();
                    var ancestor = this.cache.checkAncestorSelector(akey);
                    if(ancestor != null){
                        $found = this.cache.findFromAncestor(ancestor, input.selector);
                        break;
                    }
                }

                //if still could not find do jQuery select
                if($found == null){
                    $found = teJQuery(inDocument).find(input.optimized);
                }

                //validate the result before storing it
                this.validateResult($found);

                //If find valid selector, update to cache
                if($found != null && $found.length > 0){
                    var cachedata = new CacheData();
                    cachedata.selector = input.selector;
                    cachedata.optimized = input.optimized;
                    cachedata.reference = $found;
                    this.cache.addSelectorToCache(key, cachedata);
                }
            }

            return this.convResult($found, input);
        } else {
            //cannot cache the selector directly, try to find the DOM elements using ancestor first
            while (sid.size() > 1) {
                //try to find from its ancestor
                sid.pop();
                var ckey = sid.getUid();
                var ancester = this.cache.checkAncestorSelector(ckey);
                if (ancester != null) {
                    $found = this.cache.findFromAncestor(ancester, input.selector);
                    break;
                }
            }
            
            //if still could not find do jQuery select
            if ($found == null) {
                $found = teJQuery(inDocument).find(input.optimized);
            }

            //validate the result before storing it
            this.validateResult($found);

            return this.convResult($found, input);
        }
    }   
};

function CacheAwareLocator(){
    //runtime id
    this.rid = null;

    //whether it includes attribute
//    this.isAttribute = false;

    //original locator
    this.locator = null;
    //this.orLocator = null;

    //attribution portion
//    this.attribute = null;

    //locator portion
//    this.locator = null;
};

Tellurium.prototype.locateElementWithCacheAware = function(locator, inDocument, inWindow){
    var element = null;
    
//    var json = locator.substring(7);
    var json = locator;
    fbLog("JSON presentation of the cache aware locator: ", json);
    var cal = JSON.parse(json, null);
    fbLog("Parsed cache aware locator: ", cal);
    
    fbLog("Tellurium Cache option: ", this.isUseCache());
    if (this.isUseCache()) {
        //if Cache is used, try to get the UI element from the cache first
        element = this.getUiElementFromCache(cal.rid);
        fbLog("Got UI element " + cal.rid + " from Cache.", element);
        
        if (element != null) {
            //need to validate the result from the cache
            fbLog("Trying to validate the found UI element " + cal.rid, element);
            if (!validateDomRef(element)) {
                fbError("The UI element " + cal.rid + " from cache is not valid", element);
                this.cache.relocateUiModule(cal.rid);
                //after relocating the UI module, retry to get the UI element from the cache
                element = this.getUiElementFromCache(cal.rid);
                fbLog("After relocating UI module, found ui element" + cal.rid, element);
            }
        }else{
            //If cannot find the UI element from the cache, locate it as the last resort
            fbLog("Trying to locate the UI element " + cal.rid + " with its locator " + cal.locator + " because cannot find vaild one from cache");
            element = this.locate(cal.locator);
        }
    }else{
        fbLog("Trying to locate the UI element " + cal.rid + " with its locator " + cal.locator + " because cache option is off", cal);
        element = this.locate(cal.locator);
    } 

    if(element == null){
        fbError("Cannot locate element for uid " + cal.rid + " with locator " + cal.locator, element);
        throw SeleniumError("Cannot locate element for uid " + cal.rid + " with locator " + cal.locator);
    }

    fbLog("Returning found UI element ", element);
    return element;
};

Tellurium.prototype.dispatchMacroCmd = function(){
    var response = new BundleResponse();

    while (this.macroCmd.size() > 0) {
        var cmd = this.macroCmd.first();
        if(cmd.name == "getUseUiModule"){
            //do UI module locating
            this.delegateToTellurium(response, cmd);
        }else{
            //for other commands
            this.updateArgumentList(cmd);
            if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
                fbLog("delegate command to Selenium", cmd);
                this.delegateToSelenium(response, cmd);
            }else{
                fbLog("delegate command to Tellurium", cmd);
                this.delegateToTellurium(response, cmd);
            }
        }
    }

    return response.toJSon();
};

Tellurium.prototype.delegateToSelenium = function(response, cmd) {
    // need to use selenium api name conversion to find the api
    var apiName = cmd.name;
    var result = null;
    fbLog("Delegate Call " + cmd.name + " to Selenium", cmd);
    if (apiName.startsWith("is")) {
//        result = selenium[apiName].apply(this, cmd.args);
        result = selenium[apiName].apply(selenium, cmd.args);
        response.addResponse(cmd.sequ, apiName, "BOOLEAN", result);
    } else if (apiName.startsWith("get")) {
        result = selenium[apiName].apply(selenium, cmd.args);
        if(apiName.indexOf("All") != -1){
            //api Name includes "All" should return an array
            response.addResponse(cmd.sequ, apiName, "ARRAY", result);
        }else{
            //assume the rest return "String"
            response.addResponse(cmd.sequ, apiName, "STRING", result);
        }
    } else {
        apiName = this.camelizeApiName(apiName);
        fbLog("Call Selenium method " + apiName, selenium);
        selenium[apiName].apply(selenium, cmd.args);
    }
};

Tellurium.prototype.delegateToTellurium = function(response, cmd) {
    var result = null;

    var handler = this.apiMap.get(cmd.name);

    if (handler != null) {
        var api = handler.api;
        //the argument list
        var params = cmd.args;
        if (params != null && params.length > 0) {
            if (handler.returnType == "VOID") {
//                api.apply(this, params);
                api.apply(this.teApi, params);
            } else {
                result = api.apply(this.teApi, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        } else {
            if (handler.returnType == "VOID") {
                api.apply(this.teApi, params);
            } else {
                result = api.apply(this.teApi, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }

    } else {
        throw SeleniumError("Unknown command " + cmd.name + " in Command Bundle.");
    }
};

Tellurium.prototype.updateArgumentList = function(cmd){
    if (cmd.args != null) {

        //check the first argument to see if it is a locator or not
        var locator = cmd.args[0];

        if (this.isLocator(locator)) {
            //if it is a locator
            var cal = new CacheAwareLocator();
            cal.rid = cmd.uid;
            cal.locator = locator;
//            cal.locator = locator;

/*
            This is not really correct because XPATH could have the @ inside
            
            //check if it is an attribute locator
            var attributePos = locator.lastIndexOf("@");
            if (attributePos != -1) {
                cal.isAttribute = true;
                var attributeName = locator.slice(attributePos + 1);
                if (attributeName.endsWith("]")) {
                    attributeName = attributeName.substr(0, attributeName.length - 1);
                }
                cal.attribute = attributeName;

                cal.locator = locator.slice(0, attributePos);
                if (cal.locator.endsWith("[")) {
                    cal.locator = cal.locator.substr(0, cal.locator.length - 1);
                }
            }
*/

            //convert to locator string so that selenium could use it
            cmd.args[0] = "uimcal=" + JSON.stringify(cal);              
            fbLog("Update argument list for command " + cmd.name, cmd);
        }
        //otherwise, no modification, use the original argument list
    }
};

