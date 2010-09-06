var ErrorCodes = {
    UI_OBJ_NOT_FOUND: "UI object not found",
    INVALID_TELLURIUM_COMMAND : "Invalid Tellurium command",
    INVALID_SELENIUM_COMMAND: "Invalid Selenium command",
    INVALID_CALL_ON_UI_OBJ : "Invalid call on UI object",
    INVALID_NUMBER_OF_PARAMETERS: "Invalid number of parameters",
    UI_OBJ_CANNOT_BE_LOCATED: "UI object cannot be located",
    UI_MODULE_IS_NULL: "UI module is null",
    DOM_NOT_SPECIFIED: "DOM is not specified",
    ELEMENT_HAS_NO_VALUE: "No value attribute in element",
    TIME_OUT: "Command Timeouts",
    ASSERTION_ERROR: "Assertion Error",
    UNKNOWN: "Unknown"
};


function TelluriumError(type, message) {
    var error = new SeleniumError(message);
    if (typeof(arguments.caller) != 'undefined') { // IE, not ECMA
        var result = '';
        for (var a = arguments.caller; a != null; a = a.caller) {
            result += '> ' + a.callee.toString() + '\n';
            if (a.caller == a) {
                result += '*';
                break;
            }
        }
        error.stack = result;
    }

    error.isTelluriumError = true;
    error.type = type;

    return error;
}


/*teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    tellurium.initialize();
    !tellurium.logManager.isUseLog || fbLog("Tellurium initialized after document ready", tellurium);
});*/

var tellurium = null;

teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    fbLog("Start tellurium instance", tellurium);
    tellurium.initialize();
    !tellurium.logManager.isUseLog || fbLog("Tellurium initialized after document ready", tellurium);
//    document.body.appendChild(firebug);
    (function() {
        if (window.firebug != undefined && window.firebug.version) {
            firebug.init();
        } else {
            setTimeout(arguments.callee);
        }
    })();
    if(typeof (firebug) != "undefined")
        void(firebug);
});


function Identifier(sn){
    if(sn != undefined){
        this.sn = sn;
    }else{
        this.sn = 100;
    }
}

Identifier.prototype.next = function(){
    this.sn++;

    return this.sn;
};

// Command Request for Command bundle
function CmdRequest(){
    this.sequ = 0;
    this.uid = null;
    this.name = null;
    this.args = null;
}

// Command Request for Command bundle
function CmdResponse(){
    this.sequ = 0;
    this.name = null;
    this.returnType = null;
    this.returnResult = null;
}

function BundleResponse(){
    this.response = new Array();
}

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

}

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
}

function EngineState(){
    this.cache = null;
    this.teApi = null;
    this.relax = null;
}

function Tellurium(){

    this.currentWindow = null;

    this.currentDocument = null;

    //whether to use Tellurium new jQuery selector based APIs
    this.isUseTeApi = false;

    //Macro command for Tellurium
    this.macroCmd = new MacroCmd();

    this.cmdExecutor = new TelluriumCommandExecutor();

    this.cache = this.cmdExecutor.cache;

    //UI object name to Javascript object builder mapping
    this.uiBuilderMap = new Hashtable();

    //JQuery Builder
    this.jqbuilder = new JQueryBuilder();

    //identifier generator
    this.idGen = new Identifier();

    //log manager for Tellurium
    this.logManager = new LogManager();

    //outlines
    this.outlines = new Outlines();
}

Tellurium.prototype.initialize = function(){
    this.outlines.init();
    this.registerDefaultUiBuilders();
};

Tellurium.prototype.registerDefaultUiBuilders = function(){
    this.uiBuilderMap.put("Button", new UiButtonBuilder());
    this.uiBuilderMap.put("CheckBox", new UiCheckBoxBuilder());
    this.uiBuilderMap.put("Div", new UiDivBuilder());
    this.uiBuilderMap.put("Icon", new UiIconBuilder());
    this.uiBuilderMap.put("Image", new UiImageBuilder());
    this.uiBuilderMap.put("InputBox", new UiInputBoxBuilder());
    this.uiBuilderMap.put("RadioButton", new UiRadioButtonBuilder());
    this.uiBuilderMap.put("Selector", new UiSelectorBuilder());
    this.uiBuilderMap.put("Span", new UiSpanBuilder());
    this.uiBuilderMap.put("SubmitButton", new UiSubmitButtonBuilder());
    this.uiBuilderMap.put("TextBox", new UiTextBoxBuilder());
    this.uiBuilderMap.put("UrlLink", new UiUrlLinkBuilder());
    this.uiBuilderMap.put("Container", new UiContainerBuilder());
    this.uiBuilderMap.put("Frame", new UiFrameBuilder());
    this.uiBuilderMap.put("Form", new UiFormBuilder());
    this.uiBuilderMap.put("List", new UiListBuilder());
    this.uiBuilderMap.put("Table", new UiTableBuilder());
    this.uiBuilderMap.put("StandardTable", new UiStandardTableBuilder());
    this.uiBuilderMap.put("Window", new UiWindowBuilder());
    this.uiBuilderMap.put("Repeat", new UiRepeatBuilder());
    this.uiBuilderMap.put("UiAllPurposeObject", new UiAllPurposeObjectBuilder());
};

//expose this so that users can hook in their own custom UI objects or even overwrite the default UI objects
Tellurium.prototype.registerUiBuilder = function(name, builder){
    this.uiBuilderMap.put(name, builder);
};

Tellurium.prototype.getRegisteredUiTypes = function(){
    return this.uiBuilderMap.keySet();
};

Tellurium.prototype.flipLog = function(){
    this.logManager.isUseLog = !this.logManager.isUseLog;
    if(firebug != undefined)
        firebug.env.debug = this.logManager.isUseLog;
};

Tellurium.prototype.isUseLog = function(){
    return this.logManager.isUseLog;
};

Tellurium.prototype.useTeApi = function(isUse){
    if (typeof(isUse) == "boolean") {
        tellurium.isUseTeApi = isUse;
    } else {
        tellurium.isUseTeApi = ("true" == isUse || "TRUE" == isUse);
    }
};

Tellurium.prototype.isApiMissing =function(apiName){

    return this.cmdExecutor.getCommand(apiName) == null;
};

Tellurium.prototype.parseMacroCmd = function(json){
    this.macroCmd.parse(json);
};

Tellurium.prototype.camelizeApiName = function(apiName){
    return "do" + apiName.charAt(0).toUpperCase() + apiName.substring(1);
};

Tellurium.prototype.delegateToSelenium = function(response, cmd) {
    // need to use selenium api name conversion to find the api
    var apiName = cmd.name;
    var result = null;
    !tellurium.logManager.isUseLog || fbLog("Delegate Call " + cmd.name + " to Selenium", cmd);

    var returnType = null;

    //Try to get back the return type by looking at Tellurium API counterpart
//    var handler = this.apiMap.get(cmd.name);
    var command = this.cmdExecutor.getCommand(cmd.name);
    if(command != null){
        returnType = command.returnType;
    }

    if (apiName.startsWith("is")) {

        if(selenium[apiName] == undefined){
            throw new TelluriumError(ErrorCodes.INVALID_SELENIUM_COMMAND, "Invalid selenium command " + apiName);
        }

        result = selenium[apiName].apply(selenium, cmd.args);
        if(returnType == null)
            returnType = "BOOLEAN";
        response.addResponse(cmd.sequ, apiName, returnType, result);
    } else if (apiName.startsWith("get")) {

        if(selenium[apiName] == undefined){
            throw new TelluriumError(ErrorCodes.INVALID_SELENIUM_COMMAND, "Invalid selenium command " + apiName);
        }
        result = selenium[apiName].apply(selenium, cmd.args);
        if(apiName.indexOf("All") != -1){
            //api Name includes "All" should return an array
            if(returnType == null)
                returnType = "ARRAY";
            response.addResponse(cmd.sequ, apiName, returnType, result);
        }else{
            //assume the rest return "String"
            if(returnType == null)
                returnType = "STRING";
            response.addResponse(cmd.sequ, apiName, returnType, result);
        }
    } else {
        apiName = this.camelizeApiName(apiName);

        if(selenium[apiName] == undefined){
            throw new TelluriumError(ErrorCodes.INVALID_SELENIUM_COMMAND, "Invalid selenium command " + apiName);
        }
        
        !tellurium.logManager.isUseLog || fbLog("Call Selenium method " + apiName, selenium);
        selenium[apiName].apply(selenium, cmd.args);
    }
};

Tellurium.prototype.parseAttributeFromLocator = function(locator){
    var attribute = null;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
       attribute = locator.substring(inx + 1);
    }

    return attribute;
};

Tellurium.prototype.prepareArgumentList = function(args){
    if(args == null)
        return [];
    var params = [];
    if(this.isLocator(args[0])){
        for(var i=1; i< args.length; i++){
            params.push(args[i]);
        }
    }else{
        params = args;
    }

    return params;
};

Tellurium.prototype.delegateToTellurium = function(response, cmd) {

    var command = this.cmdExecutor.getCommand(cmd.name);

    if(command != null){
/*
        var param = null;
        if(cmd.args != null && cmd.args.length > 0){
            if(cmd.args.length > 1){
                //locator
                if(this.isLocator(cmd.args[0])){
                    param = cmd.args[1];
                }else if(command.type != CommandType.ASSERTION){
                    throw new TelluriumError(ErrorCodes.INVALID_NUMBER_OF_PARAMETERS, "Tellurium command " + cmd.name + " expects 1 parameter, but is " + cmd.args.length);
                }
            }else{
                param = cmd.args[0]
            }

        }
*/
        var result;
        if(command.type == CommandType.ASSERTION){
            result = this.cmdExecutor[cmd.name].apply(this.cmdExecutor, cmd.args);
        }else{
            if(cmd.name == "getAttribute"){
                var attr = this.parseAttributeFromLocator(cmd.args[0]);
//                result = this.cmdExecutor[cmd.name].apply(this.cmdExecutor, [cmd.uid, attr]);
                result = command.handler.apply(this.cmdExecutor, [cmd.uid, attr]);
            } else {
                var params = this.prepareArgumentList(cmd.args);
                params.splice(0, 0, cmd.uid);
//                result = this.cmdExecutor[cmd.name].apply(this.cmdExecutor, params);
                result = command.handler.apply(this.cmdExecutor, params);
            }
        }

        if(command.returnType == "VOID"){
            response.addResponse(cmd.sequ, cmd.name, command.returnType, result);
        }else {
//            alert("Add response " + cmd.sequ + ", " + cmd.name + ", " + command.returnType + ", " + result);
            response.addResponse(cmd.sequ, cmd.name, command.returnType, result);
        }
    } else {
        throw TelluriumError(ErrorCodes.INVALID_TELLURIUM_COMMAND, "Invalid Tellurium command " + cmd.name + " in Command Bundle.");
    }
};

Tellurium.prototype.dispatchMacroCmd = function(){
    var response = new BundleResponse();

    while (this.macroCmd.size() > 0) {
        var cmd = this.macroCmd.first();
        if(cmd.name == "getUseUiModule"){
            //do UI module locating
            var result = this.cmdExecutor.useUiModuleInJSON(cmd.args[0]);
            response.addResponse(cmd.sequ, cmd.name, ReturnType.OBJECT, result);
        }else{
            //for other commands
            !tellurium.logManager.isUseLog || fbLog("Dispatching command: ", cmd);
//            this.updateArgumentList(cmd);
//            !tellurium.logManager.isUseLog || fbLog("Command after updating argument list: ", cmd);
            if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
                !tellurium.logManager.isUseLog || fbLog("delegate command to Selenium", cmd);
                this.delegateToSelenium(response, cmd);
            }else{
                !tellurium.logManager.isUseLog || fbLog("delegate command to Tellurium", cmd);
                this.delegateToTellurium(response, cmd);
            }
        }
    }

    return response.toJSon();
};

Tellurium.prototype.isUseCache = function(){
    return this.cmdExecutor.cache.cacheOption;
};

Tellurium.prototype.useUiModule = function(jsonarray){
    return this.cmdExecutor.useUiModuleInJSON(jsonarray);
};

Tellurium.prototype.validateUiModule = function(jsonarray){
    return this.cmdExecutor.validateUiModuleInJSON(jsonarray);
};

Tellurium.prototype.isUiModuleCached = function(id){
    return this.cmdExecutor.isUiModuleCached(id);
};

Tellurium.prototype.useClosestMatch = function(isUse){
    this.cmdExecutor.useClosestMatch(isUse);
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

Tellurium.prototype.isLocator = function(locator){
    if(typeof(locator) != "string")
        return false;

    return locator.startsWith('//') || locator.startsWith('jquery=') || locator.startsWith('jquerycache=') || locator.startsWith('document.');
};
