var getEvent = function(name, key){
    var e = teJQuery.Event(name);
    e.which = key.charCodeAt(0);
    return e;
};

var tellurium = null;

teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    tellurium.initialize();
});

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
     /*
      var fs = m[3].split(':');

      for(var i=0; i<fs.length; i++){
        var sfs = fs[i].split(';');
        for(var j=0; j<sfs.length; j++){
          if(teJQuery.trim(sfs[j]).length >0){
            splitted.push(teJQuery.trim(sfs[j]));
          }
        }
      }*/

//      alert("splitted = " + splitted.join());

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

function CommandBundle(){
    this.bundle = new Array();

};

CommandBundle.prototype.size = function(){
    return this.bundle.length;
};

CommandBundle.prototype.first = function(){
    return this.bundle.shift();
};

CommandBundle.prototype.addCmd = function(sequ, uid, name, args){
    var cmd = new CmdRequest();
    cmd.sequ = sequ;
    cmd.uid = uid;
    cmd.name = name;
    cmd.args = args;
    this.bundle.push(cmd);
};

CommandBundle.prototype.parse = function(json){
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

    //command bundle for Tellurium
    this.commandbundle = new CommandBundle();

    //whether to use Tellurium new jQuery selector based APIs
    this.isUseTeApi = false;

    //cache to hold the element corresponding to a UID in command bundle
    this.cbCache = new Hashtable();

    this.teApi = new TelluriumApi(this.cache);

    //api name to method mapping for command bundle processing
    this.apiMap = new Hashtable();

};

//TODO: How to handle custom calls?
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
    this.registerApi("getJQuerySelectorCount", true, "NUMBER");
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
    this.registerApi("useDiscardNewPolicy", false, "VOID");
    this.registerApi("useDiscardOldPolicy", false, "VOID");
    this.registerApi("useDiscardLeastUsedPolicy", false, "VOID");
    this.registerApi("useDiscardInvalidPolicy", false, "VOID");
    this.registerApi("getCachePolicyName", false, "STRING");

    this.registerApi("useUiModule", false, "VOID");
    this.registerApi("isUiModuleCached", false, "BOOLEAN");

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

Tellurium.prototype.parseCommandBundle = function(json){
    this.commandbundle.parse(json);
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

Tellurium.prototype.getUiElementFromCache = function(uid){
    var uielem = this.cache.getCachedUiElement(uid);
    if(uielem != null){
        var elem = uielem.domRef;
        if(elem != null){
            return elem;
        }else{
            elem = this.locate(elem.generated);
            if(uielem.amICacheable()){
                uielem.domRef = elem;
            }

            return elem;
        }
    }else{
//        throw SeleniumError("Cannot find Ui Element " + uid);
        return null;
    }
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
    return locator.startsWith('//') || locator.startsWith('jquery=') || locator.startsWith('jquerycache=') || locator.startsWith('document.');
};

Tellurium.prototype.camelizeApiName = function(apiName){
    return "do" + apiName.charAt(0).toUpperCase() + apiName.substring(1);
};

Tellurium.prototype.delegateToSelenium = function(response, cmd) {
    // need to use selenium api name conversion to find the api
    var apiName = cmd.name;
    var result = null;
    if (apiName.startsWith("is")) {
        result = selenium[apiName].apply(this, cmd.args);
        response.addResponse(cmd.sequ, apiName, "BOOLEAN", result);
    } else if (apiName.startsWith("get")) {
        result = selenium[apiName].apply(this, cmd.args);
        if(apiName.indexOf("All") != -1){
            //api Name includes "All" should return an array
            response.addResponse(cmd.sequ, apiName, "ARRAY", result);
        }else{
            //assume the rest return "String"
            response.addResponse(cmd.sequ, apiName, "STRING", result);
        }
    } else {
        apiName = this.camelizeApiName(apiName);
        selenium[apiName].apply(this, cmd.args);
    }
};

Tellurium.prototype.processCommandBundle = function(){

    this.cbCache.clear();

    var response = new BundleResponse();

    while (this.commandbundle.size() > 0) {
        var cmd = this.commandbundle.first();
        //If don't want to use Tellurium APIs
        //or counld not find the approporiate API from Tellurium APIs, delete it to selenium directly
        //TODO: pay attention to tellurium only APIs, should not delegate to selenium if they are Tellurium only
        if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
            this.delegateToSelenium(response, cmd);
        } else {
            var element = null;
            var locator = cmd.args[0];
            //some commands do not have any arguments, null guard args
            if (locator != null) {
                var isLoc = this.isLocator(locator);
                //if the first argument is a locator
                if (isLoc) {
                    //handle attribute locator for the getAttribute call
                    if (cmd.name == "getAttribute") {
                        var attributePos = locator.lastIndexOf("@");
                        var attributeName = locator.slice(attributePos + 1);
                        cmd.args.push(attributeName);
                        locator = locator.slice(0, attributePos);
                    }

                    if (cmd.uid == null) {
                        element = this.locate(locator);
                    } else {
//                        element = this.cbCache.get(cmd.uid);
                        element = this.getUiElementFromCache(cmd.uid);

                        if (element == null) {
                            element = this.locate(locator);
                            if (element != null) {
                                //TODO: need to check if the element is cachable??
                                this.cbCache.put(cmd.uid, element);
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

Tellurium.prototype.locateElementByCacheAwareJQuerySelector = function(locator, inDocument, inWindow){
    var input = this.parseLocator(locator);
    var $found = null;
    
    //If do not cache selector or meta command without UID, directly find jQuery selector
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
