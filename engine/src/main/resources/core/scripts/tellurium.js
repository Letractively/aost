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

/*
function TelluriumCommandHandler(api, requireElement, returnType) {
    //api method
    this.api = api;
    //whether it requires an element/elements to act on
    this.requireElement = requireElement;
    //return type
    this.returnType = returnType;
}
*/

function EngineState(){
    this.cache = null;
    this.teApi = null;
    this.relax = null;
}

const ValueType = {
    NUMBER: "number",
    STRING: 'string',
    BOOLEAN: 'boolean',
    OBJECT: 'object',
    VARIABLE: "var",
    NIL: "nil"
};

const CommandType = {
    ACTION: "action",
    ACCESSOR: "accessor",
    DIRECT: "direct",
    ASSERTION: "assertion"
};

const ReturnType = {
    VOID: "void",
    BOOLEAN: "boolean",
    STRING: "string",
    ARRAY: "Array",
    NUMBER: "number",
    OBJECT: "object"
};

function TelluriumCommand(name, type, returnType, handler){
    this.name = name;
    this.type = type;
    this.returnType = returnType;
    this.handler = handler;
}

function Tellurium(){

    this.currentWindow = null;

    this.currentDocument = null;

    //whether to use Tellurium new jQuery selector based APIs
    this.isUseTeApi = false;

    //Macro command for Tellurium
    this.macroCmd = new MacroCmd();

    this.browserBot = new TelluriumBrowserBot();

    this.dom = null;

    this.cache = new TelluriumUiCache();

    this.uiAlg = new UiAlg();

    this.textWorker = new TextUiWorker();

    this.cssBuilder = new JQueryBuilder();

    this.cmdMap = new Hashtable();
 
//    this.cmdExecutor = new TelluriumCommandExecutor();
//
//    this.cache = this.cmdExecutor.cache;

    //UI object name to Javascript object builder mapping
    this.uiBuilderMap = new Hashtable();

    //JQuery Builder
    this.jqbuilder = new JQueryBuilder();

    //identifier generator
    this.idGen = new Identifier(100);

    //log manager for Tellurium
    this.logManager = new LogManager();

    //outlines
    this.outlines = new Outlines();
}

Tellurium.prototype.initialize = function(){
    this.outlines.init();
    this.registerDefaultUiBuilders();
    this.registerCommands();
    if(typeof(this["customize"]) != "undefined"){
        this["customize"].apply(this, []);
    }
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

Tellurium.prototype.registerCommand = function(name, type, returnType, handler){
    var cmd = new TelluriumCommand(name, type, returnType, handler);
    this.cmdMap.put(name, cmd);
};

Tellurium.prototype.registerCommands = function(){
    this.registerCommand("useTeApi", CommandType.ACCESSOR, ReturnType.VOID, this.useTeApi);
    this.registerCommand("isUseLog", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isUseLog);
    this.registerCommand("open", CommandType.ACTION, ReturnType.VOID, this.open);
    this.registerCommand("blur", CommandType.ACTION, ReturnType.VOID, this.blur);
    this.registerCommand("click", CommandType.ACTION, ReturnType.VOID, this.click);
    this.registerCommand("clickAt", CommandType.ACTION, ReturnType.VOID, this.clickAt);
    this.registerCommand("doubleClick", CommandType.ACTION, ReturnType.VOID, this.doubleClick);
    this.registerCommand("fireEvent", CommandType.ACTION, ReturnType.VOID, this.fireEvent);
    this.registerCommand("focus", CommandType.ACTION, ReturnType.VOID, this.focus);
    this.registerCommand("type", CommandType.ACTION, ReturnType.VOID, this.type);
    this.registerCommand("typeKey", CommandType.ACTION, ReturnType.VOID, this.typeKey);
    this.registerCommand("keyDown", CommandType.ACTION, ReturnType.VOID, this.keyDown);
    this.registerCommand("keyPress", CommandType.ACTION, ReturnType.VOID, this.keyPress);
    this.registerCommand("keyUp", CommandType.ACTION, ReturnType.VOID, this.keyUp);
    this.registerCommand("mouseOver", CommandType.ACTION, ReturnType.VOID, this.mouseOver);
    this.registerCommand("mouseDown", CommandType.ACTION, ReturnType.VOID, this.mouseDown);
    this.registerCommand("mouseEnter", CommandType.ACTION, ReturnType.VOID, this.mouseEnter);
    this.registerCommand("mouseLeave", CommandType.ACTION, ReturnType.VOID, this.mouseLeave);
    this.registerCommand("mouseOut", CommandType.ACTION, ReturnType.VOID, this.mouseOut);
    this.registerCommand("submit", CommandType.ACTION, ReturnType.VOID, this.submit);
    this.registerCommand("check", CommandType.ACTION, ReturnType.VOID, this.check);
    this.registerCommand("uncheck", CommandType.ACTION, ReturnType.VOID, this.uncheck);
    this.registerCommand("select", CommandType.ACTION, ReturnType.VOID, this.select);
    this.registerCommand("selectByLabel", CommandType.ACTION, ReturnType.VOID, this.selectByLabel);
    this.registerCommand("selectByIndex", CommandType.ACTION, ReturnType.VOID, this.selectByIndex);
    this.registerCommand("selectByValue", CommandType.ACTION, ReturnType.VOID, this.selectByValue);

    this.registerCommand("getSelectOptions", CommandType.ACCESSOR, ReturnType.ARRAY, this.getSelectOptions);
    this.registerCommand("getSelectValues", CommandType.ACCESSOR, ReturnType.ARRAY, this.getSelectValues);
    this.registerCommand("getSelectedLabel", CommandType.ACCESSOR, ReturnType.STRING, this.getSelectedLabel);
    this.registerCommand("getSelectedLabels", CommandType.ACCESSOR, ReturnType.ARRAY, this.getSelectedLabels);
    this.registerCommand("getSelectedValue", CommandType.ACCESSOR, ReturnType.STRING, this.getSelectedValue);
    this.registerCommand("getSelectedValues", CommandType.ACCESSOR, ReturnType.ARRAY, this.getSelectedValues);
    this.registerCommand("getSelectedIndex", CommandType.ACCESSOR, ReturnType.NUMBER, this.getSelectedIndex);
    this.registerCommand("getSelectedIndexes", CommandType.ACCESSOR, ReturnType.ARRAY, this.getSelectedIndexes);
    this.registerCommand("addSelection", CommandType.ACCESSOR, ReturnType.VOID, this.addSelection);
    this.registerCommand("removeSelection", CommandType.ACCESSOR, ReturnType.VOID, this.removeSelection);
    this.registerCommand("removeAllSelections", CommandType.ACCESSOR, ReturnType.VOID, this.removeAllSelections);

    this.registerCommand("getAttribute", CommandType.ACCESSOR, ReturnType.STRING, this.getAttribute);
    this.registerCommand("getText", CommandType.ACCESSOR, ReturnType.STRING, this.getText);

    this.registerCommand("getAllText", CommandType.ACCESSOR, ReturnType.ARRAY, this.getAllText);

    this.registerCommand("getValue", CommandType.ACCESSOR, ReturnType.STRING, this.getValue);
    this.registerCommand("isElementPresent", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isElementPresent);
    this.registerCommand("isChecked", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isChecked);
    this.registerCommand("isVisible", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isVisible);
    this.registerCommand("isEditable", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isEditable);
    this.registerCommand("getCSS", CommandType.ACCESSOR, ReturnType.ARRAY, this.getCSS);
    this.registerCommand("getCSSAsString", CommandType.ACCESSOR, ReturnType.STRING, this.getCSSAsString);
    this.registerCommand("isDisable",  CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isDisabled);
    this.registerCommand("reset", CommandType.ACCESSOR, ReturnType.VOID, this.reset);
    this.registerCommand("showUi", CommandType.ACTION, ReturnType.VOID, this.showUi);
    this.registerCommand("cleanUi", CommandType.ACTION, ReturnType.VOID, this.cleanUi);
    this.registerCommand("getHTMLSource", CommandType.DIRECT, ReturnType.ARRAY, this.getHTMLSource);
    this.registerCommand("getHTMLSourceAsString", CommandType.DIRECT, ReturnType.STRING, this.getHTMLSourceAsString);
    this.registerCommand("getUids", CommandType.DIRECT, ReturnType.ARRAY, this.getUids);
    this.registerCommand("getUidsAsString", CommandType.DIRECT, ReturnType.STRING, this.getUidsAsString);
    this.registerCommand("getCssSelectorCount", CommandType.DIRECT, ReturnType.NUMBER, this.getCssSelectorCount);
    this.registerCommand("getCssSelectorMatch", CommandType.DIRECT, ReturnType.ARRAY, this.getCssSelectorMatch);
    this.registerCommand("getCssSelectorMatchAsString", CommandType.DIRECT, ReturnType.STRING, this.getCssSelectorMatchAsString);
    this.registerCommand("useUiModule", CommandType.ACCESSOR, ReturnType.OBJECT, this.useUiModule);
    this.registerCommand("useDirectUiModule", CommandType.ACCESSOR, ReturnType.VOID, this.useDirectUiModule);
    this.registerCommand("validateUiModule", CommandType.DIRECT, ReturnType.OBJECT, this.validateUiModule);
    this.registerCommand("validateDirectUiModule", CommandType.DIRECT, ReturnType.OBJECT, this.validateDirectUiModule);
    this.registerCommand("validateUiModuleAsString", CommandType.DIRECT, ReturnType.STRING, this.validateUiModuleAsString);
    this.registerCommand("toJSON", CommandType.DIRECT, ReturnType.OBJECT, this.toJSON);
    this.registerCommand("toJSONString", CommandType.DIRECT, ReturnType.STRING, this.toJSONString);
//    this.registerCommand("waitForPageToLoad", CommandType.ACTION, ReturnType.VOID, this.waitForPageToLoad);
    this.registerCommand("getUiByTag", CommandType.DIRECT, ReturnType.OBJECT, this.getUiByTag);
    this.registerCommand("removeMarkedUids", CommandType.DIRECT, ReturnType.VOID, this.removeMarkedUids);
    this.registerCommand("isUseCache", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isUseCache);

    this.registerCommand("getCacheState", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.getCacheState);
    this.registerCommand("getCacheSize", CommandType.ACCESSOR, ReturnType.NUMBER, this.getCacheSize);
    this.registerCommand("enableCache", CommandType.ACCESSOR, ReturnType.VOID, this.enableCache);
    this.registerCommand("disableCache", CommandType.ACCESSOR, ReturnType.VOID, this.disableCache);
    this.registerCommand("isUiModuleCached", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.isUiModuleCached);
    this.registerCommand("useClosestMatch", CommandType.ACCESSOR, ReturnType.VOID, this.useClosestMatch);
    
    this.registerCommand("assertTrue", CommandType.ASSERTION, ReturnType.VOID, this.assertTrue);
    this.registerCommand("assertFalse", CommandType.ASSERTION, ReturnType.VOID, this.assertFalse);
    this.registerCommand("assertEquals", CommandType.ASSERTION, ReturnType.VOID, this.assertEquals);
    this.registerCommand("assertNotEquals", CommandType.ASSERTION, ReturnType.VOID, this.assertNotEquals);
    this.registerCommand("assertNull", CommandType.ASSERTION, ReturnType.VOID, this.assertNull);
    this.registerCommand("assertNotNull", CommandType.ASSERTION, ReturnType.VOID, this.assertNotNull);

    this.registerCommand("getListSize", CommandType.ACCESSOR, ReturnType.NUMBER, this.getListSize);
    this.registerCommand("getTeListSize", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeListSize);
    this.registerCommand("getTableHeaderColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableHeaderColumnNum);
    this.registerCommand("getTeTableHeaderColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableHeaderColumnNum);
    this.registerCommand("getTableFootColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableFootColumnNum);
    this.registerCommand("getTeTableFootColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableFootColumnNum);
    this.registerCommand("getTableRowNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableRowNum);
    this.registerCommand("getTeTableRowNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableRowNum);
    this.registerCommand("getTableColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableColumnNum);
    this.registerCommand("getTeTableColumnNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableColumnNum);
    this.registerCommand("getTableRowNumForTbody", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableRowNumForTbody);
    this.registerCommand("getTeTableRowNumForTbody", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableRowNumForTbody);
    this.registerCommand("getTableColumnNumForTbody", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableColumnNumForTbody);
    this.registerCommand("getTeTableColumnNumForTbody", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableColumnNumForTbody);
    this.registerCommand("getTableTbodyNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTableTbodyNum);
    this.registerCommand("getTeTableTbodyNum", CommandType.ACCESSOR, ReturnType.NUMBER, this.getTeTableTbodyNum);
    this.registerCommand("getAllTableBodyText", CommandType.ACCESSOR, ReturnType.STRING, this.getAllTableBodyText);
    this.registerCommand("getRepeatNum", CommandType.ASSERTION, ReturnType.NUMBER, this.getRepeatNum);
};

Tellurium.prototype.getCommandList = function(){
    return this.cmdMap.keySet().sort();
};

Tellurium.prototype.getCommand = function(name){
    return this.cmdMap.get(name);
};

Tellurium.prototype.getCommandType = function(name){
    var cmd = this.cmdMap.get(name);
    return cmd.type;
};

Tellurium.prototype.cachedUiModuleNum = function(){
    return this.cache.size();
};

Tellurium.prototype.cacheUiModule = function(uim){
    if(uim != null)
        this.cache.put(uim.id, uim);
};

Tellurium.prototype.getCachedUiModuleList = function(){
    return this.cache.keySet();
};

Tellurium.prototype.getCachedUiModule = function(id){
    return this.cache.get(id);
};

Tellurium.prototype.getRegisteredUiTypes = function(){
    return this.uiBuilderMap.keySet();
};

Tellurium.prototype.flipLog = function(){
    this.logManager.isUseLog = !this.logManager.isUseLog;
    if(firebug != undefined)
        firebug.env.debug = this.logManager.isUseLog;
};

Tellurium.prototype.isApiMissing =function(apiName){

    return this.getCommand(apiName) == null;
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
    var command = this.getCommand(cmd.name);
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

    var command = this.getCommand(cmd.name);

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
            result = this[cmd.name].apply(this, cmd.args);
        }else{
            if(cmd.name == "getAttribute"){
                var attr = this.parseAttributeFromLocator(cmd.args[0]);
//                result = this[cmd.name].apply(this, [cmd.uid, attr]);
                result = command.handler.apply(this, [cmd.uid, attr]);
            } else {
                var params = this.prepareArgumentList(cmd.args);
                params.splice(0, 0, cmd.uid);
//                result = this[cmd.name].apply(this, params);
                result = command.handler.apply(this, params);
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
            var result = this.useUiModule(cmd.args[0]);
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
