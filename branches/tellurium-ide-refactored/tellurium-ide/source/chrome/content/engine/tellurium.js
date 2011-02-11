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
    MAX_HEIGHT_NOT_SET: "Maximum height of the UI module is not set",
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

//var tellurium = null;

/*
teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    tellurium.initialize();
    !tellurium.logManager.isUseLog || fbLog("Tellurium initialized after document ready", tellurium);
});
*/


/*teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    fbLog("Start tellurium instance", tellurium);
    tellurium.initialize();
    !tellurium.logManager.isUseLog || fbLog("Tellurium initialized after document ready", tellurium);
    (function() {
        if (window.firebug != undefined && window.firebug.version) {
            firebug.init();
        } else {
            setTimeout(arguments.callee);
        }
    })();
    if(typeof (firebug) != "undefined")
        void(firebug);
});*/


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
    HasUid: "hasUid",
    NoUid: "noUid",
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

