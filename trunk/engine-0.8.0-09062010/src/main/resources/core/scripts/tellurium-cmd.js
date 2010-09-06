
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

function TelluriumCommandExecutor(){

    this.browserBot = new TelluriumBrowserBot();

    this.dom = null;

    this.cache = new TelluriumUiCache();

    this.uiAlg = new UiAlg();
    
    this.textWorker = new TextUiWorker();

    this.cssBuilder = new JQueryBuilder();

    this.cmdMap = new Hashtable();

    this.registerCommands();
}

TelluriumCommandExecutor.prototype.registerCommand = function(name, type, returnType, handler){
    var cmd = new TelluriumCommand(name, type, returnType, handler);
    this.cmdMap.put(name, cmd);
};

TelluriumCommandExecutor.prototype.registerCommands = function(){
    this.registerCommand("useUiModuleInJSON", CommandType.ACCESSOR, ReturnType.OBJECT, this.useUiModuleInJSON)
//    this.registerCommand("useUiModule", CommandType.ACCESSOR, ReturnType.VOID, this.useUiModule)
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
    this.registerCommand("removeSelection", CommandType.ACCESSOR, ReturnType.VOID, this.removeSelection)
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
    this.registerCommand("validateUiModuleInJSON", CommandType.DIRECT, ReturnType.OBJECT, this.validateUiModuleInJSON);
    this.registerCommand("validateUiModule", CommandType.DIRECT, ReturnType.OBJECT, this.validateUiModule);
    this.registerCommand("validateUiModuleAsString", CommandType.DIRECT, ReturnType.STRING, this.validateUiModuleAsString);
    this.registerCommand("toJSON", CommandType.DIRECT, ReturnType.OBJECT, this.toJSON);
    this.registerCommand("toJSONString", CommandType.DIRECT, ReturnType.STRING, this.toJSONString);
//    this.registerCommand("waitForPageToLoad", CommandType.ACTION, ReturnType.VOID, this.waitForPageToLoad);
    this.registerCommand("getUiByTag", CommandType.DIRECT, ReturnType.OBJECT, this.getUiByTag);
    this.registerCommand("removeMarkedUids", CommandType.DIRECT, ReturnType.VOID, this.removeMarkedUids);
    this.registerCommand("getCacheState", CommandType.ACCESSOR, ReturnType.BOOLEAN, this.getCacheState);
    this.registerCommand("getCacheSize", CommandType.ACCESSOR, ReturnType.NUMBER, this.getCacheSize);
    this.registerCommand("enableCache", CommandType.ACCESSOR, ReturnType.VOID, this.enableCache);
    this.registerCommand("disableCache", CommandType.ACCESSOR, ReturnType.VOID, this.disableCache);
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

TelluriumCommandExecutor.prototype.getCommandList = function(){
    return this.cmdMap.keySet().sort();    
};

TelluriumCommandExecutor.prototype.getCommand = function(name){
    return this.cmdMap.get(name);   
};

TelluriumCommandExecutor.prototype.getCommandType = function(name){
    var cmd = this.cmdMap.get(name);
    return cmd.type;
};

TelluriumCommandExecutor.prototype.cachedUiModuleNum = function(){
    return this.cache.size();    
};

TelluriumCommandExecutor.prototype.cacheUiModule = function(uim){
    if(uim != null)
        this.cache.put(uim.id, uim);
};

TelluriumCommandExecutor.prototype.getCachedUiModuleList = function(){
    return this.cache.keySet();    
};

TelluriumCommandExecutor.prototype.getCachedUiModule = function(id){
    return this.cache.get(id);
};

TelluriumCommandExecutor.prototype.removeCachedUiModule = function(id){
    return this.cache.remove(id);
};

TelluriumCommandExecutor.prototype.clearCache = function(){
    return this.cache.clear();
};

TelluriumCommandExecutor.prototype.getCacheState = function(){

    return this.cache.cacheOption;
};

TelluriumCommandExecutor.prototype.getCacheSize = function(){

    return this.cache.size();
};

TelluriumCommandExecutor.prototype.enableCache = function(){
    this.cache.cacheOption = true;
};

TelluriumCommandExecutor.prototype.disableCache = function(){
    this.cache.cacheOption = false;
};


TelluriumCommandExecutor.prototype.useUiModuleInJSON = function(jsonarray){
    var uim = new UiModule();
    !tellurium.logManager.isUseLog || fbLog("Input JSON Array for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    var response = new UiModuleLocatingResponse();
    var result = this.uiAlg.santa(uim, null);
    if(result){
        //set the UI Module to be valid after it is located
        uim.valid = true;
        !tellurium.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
        var id = uim.getId();
        this.cacheUiModule(uim);

        response.id = id;
        response.relaxed = uim.relaxed;
        if (!response.relaxed)
            response.found = true;
        response.relaxDetails = uim.relaxDetails;
        response.matches = uim.matches;
        response.score = uim.score;
        !tellurium.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);
    }

    return response;
};

TelluriumCommandExecutor.prototype.validateUiModuleInJSON = function(jsonarray){
    var uim = new UiModule();
    !tellurium.logManager.isUseLog || fbLog("Input JSON for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    this.uiAlg.validate(uim, null);
    //set the UI Module to be valid after it is located
    uim.valid = true;
    !tellurium.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
    var id = uim.getId();

    var response = new UiModuleLocatingResponse();
    response.id = id;
    response.relaxed = uim.relaxed;
//    if(!response.relaxed)
    if(uim.score == 100)
        response.found = true;
    response.relaxDetails = uim.relaxDetails;
    response.matches = uim.matches;
    response.score = uim.score;
    !tellurium.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);

    return response;
};

TelluriumCommandExecutor.prototype.describeUiModuleList = function(){
    if(this.cache.size() > 0){
        var keySet = this.getCachedUiModuleList();
        var array = new Array();
        for(var i=0; i<keySet.length; i++){
            var uim = this.getCachedUiModule(keySet[i]);
            var visitor = new StringifyVisitor();
            uim.around(visitor);
            array.push(this.describeUiModule(visitor.out));
        }
        return array.join("\n");
    }

    return "";
};

TelluriumCommandExecutor.prototype.describeUiModule = function(uiModelArray) {
    if (uiModelArray != undefined && uiModelArray != null) {
        var sb = new StringBuffer();
        for (var i = 0; i < uiModelArray.length; ++i) {
            if (i == 0) {
                sb.append("\t\t\tui." + uiModelArray[i].replace(/^\s+/, ''));
            } else {
                sb.append("\t\t" + uiModelArray[i]);
            }
        }

        return sb.toString();
    }

    return "";
};

TelluriumCommandExecutor.prototype.useClosestMatch = function(isUse){
    !tellurium.logManager.isUseLog || fbLog("call useClosestMatch", isUse);
    if (typeof(isUse) == "boolean") {
        this.uiAlg.allowRelax = isUse;
    } else {
        this.uiAlg.allowRelax = ("true" == isUse || "TRUE" == isUse);
    }

    !tellurium.logManager.isUseLog || fbLog("Call useClosestMatch(" + isUse + ") to set allowRelax to ", this.uiAlg.allowRelax);
};

TelluriumCommandExecutor.prototype.run = function(name, uid, param){
//    var api = this[name];
    var cmd = this.cmdMap.get(name);
    if(cmd != null){
        var api = cmd.handler;
        if (typeof(api) == 'function') {
            var params = [];
            params.push(uid);
            params.push(param);
            return api.apply(this, params);
        }else{
            logger.error("Invalid Tellurium command " + name);
            throw new TelluriumError(ErrorCodes.INVALID_TELLURIUM_COMMAND, "Invalid Tellurium command " + name);
         }
    }else{
        logger.error("Cannot find Tellurium command " + name);
        throw new TelluriumError(ErrorCodes.INVALID_TELLURIUM_COMMAND, "Invalid Tellurium command " + name);
    }

};

TelluriumCommandExecutor.prototype.locateUI = function(uid){
    var uim = this.cache.get(uid);
    if(uim != null && this.dom != null){
        uim.valid = false;
        uim.doc = this.dom;
        this.uiAlg.santa(uim, this.dom);
    }else{
        if(uim == null){
            logger.error("Cannot find UI Module " + uid);
            throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
        }else{
            logger.error("DOM is not specified");
            throw new TelluriumError(ErrorCodes.DOM_NOT_SPECIFIED, "DOM is not specified");
        }
    }
};

TelluriumCommandExecutor.prototype.execCommand = function(cmd, uid, param){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if(uim != null){
        var obj = uim.walkTo(context, uiid);
        if(obj != null){
            if(obj.respondsTo(cmd)){
                var params = [context];
                if(param != null && param != undefined){
                    params.push(param);
                }
                return obj[cmd].apply(obj, params);
            }else{
                logger.error("UI Object " + uid + " of type " + obj.uiType + " and tag " + obj.tag
                        + " does not have method " + cmd);
                throw new TelluriumError(ErrorCodes.INVALID_CALL_ON_UI_OBJ, "UI Object " + uid  + " of type "
                        + obj.uiType + " and tag " + obj.tag + " does not have method " + cmd);
            }
        }else{
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }  
    }else{
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + first);
    }
};

TelluriumCommandExecutor.prototype.getUiByTag = function(tag, attributes){
//    var attrs = new Hashtable();
    var attrs = {};
    var position = null;
    var text = null;
    var i;
    if(attributes != null && attributes.length > 0){
        for(i=0; i<attributes.length; i++){
            var key = attributes[i]["key"];
            var val = attributes[i]["val"];
            fbLog("Key " + key + ", val " + val, attributes);
            if(key == "text"){
                text = val;
            }else if(key == "position"){
                position = val;
            }else{
                attrs[key] = val;
            }
        }
    }

    var sel = this.uiAlg.cssbuilder.buildCssSelector(tag, text, position, false, attrs);
//    var $found = teJQuery(selenium.browserbot.currentWindow).find(sel);
    var $found = teJQuery(selenium.browserbot.findElementOrNull("jquery=" + sel));
    var teuids = new Array();
    for(i=0; i<$found.size(); i++){
        var $e = $found.eq(i);
        !tellurium.logManager.isUseLog || fbLog("Found element for getUiByTag " + tag, $e.get());
        var teuid = "te-" + tellurium.idGen.next();
        $e.attr("teuid", teuid);
        teuids.push(teuid);
    }

    return teuids;
};

TelluriumCommandExecutor.prototype.removeMarkedUids = function(tag){
    var $found = teJQuery(selenium.browserbot.currentWindow).find(tag + "[teuid]");
    if($found.size() > 0){
        $found.removeAttr("teuid");
    }
};

TelluriumCommandExecutor.prototype.assertTrue = function(variable){
    try{
        assertTrue(variable);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertTrue failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.assertFalse = function(variable){
    try{
        assertFalse(variable);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertFalse failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.assertEquals = function(variable1, variable2){
    try{
        assertEquals(variable1, variable2);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertEquals failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.assertNotEquals = function(variable1, variable2){
    try{
        assertNotEquals(variable1, variable2);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertNotEquals failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.assertNull = function(variable){
    try{
        assertNull(variable);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertNull failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.assertNotNull = function(variable){
    try{
        assertNotNull(variable);
    }catch(error){
        if(error.isJsUnitFailure){
            var message = "assertNotNull failed: " + error.jsUnitMessage + ", " + (error.comment != null ? error.comment: "");
            throw new TelluriumError(ErrorCodes.ASSERTION_ERROR, message);
        }
        throw error;
    }
};

TelluriumCommandExecutor.prototype.blur = function(uid) {
    this.execCommand("blur", uid);
};

TelluriumCommandExecutor.prototype.click = function(uid) {
    this.execCommand("click", uid);
};

TelluriumCommandExecutor.prototype.clickAt = function(uid, coordString) {
    this.execCommand("clickAt", uid, coordString);
};

TelluriumCommandExecutor.prototype.doubleClick = function(uid){
    this.execCommand("doubleClick", uid);
};

TelluriumCommandExecutor.prototype.fireEvent = function(uid, event){
    this.execCommand("fireEvent", uid, event);
};

TelluriumCommandExecutor.prototype.focus = function(uid){
    this.execCommand("focus", uid);
};

TelluriumCommandExecutor.prototype.type = function(uid, val){
    this.execCommand("type", uid, val);
};

TelluriumCommandExecutor.prototype.typeKey = function(uid, key){
    this.execCommand("typeKey", uid, key);
};

TelluriumCommandExecutor.prototype.keyDown = function(uid, key){
    this.execCommand("keyDown", uid, key);
};

TelluriumCommandExecutor.prototype.keyPress = function(uid, key){
    this.execCommand("keyPress", uid, key);
};

TelluriumCommandExecutor.prototype.keyUp = function(uid, key){
    this.execCommand("keyUp", uid, key);
};

TelluriumCommandExecutor.prototype.mouseOver = function(uid){
    this.execCommand("mouseOver", uid);
};

TelluriumCommandExecutor.prototype.mouseDown = function(uid){
    this.execCommand("mouseDown", uid);
};

TelluriumCommandExecutor.prototype.mouseEnter = function(uid){
    this.execCommand("mouseEnter", uid);
};

TelluriumCommandExecutor.prototype.mouseLeave = function(uid){
    this.execCommand("mouseLeave", uid);
};

TelluriumCommandExecutor.prototype.mouseOut = function(uid){
    this.execCommand("mouseOut", uid);
};

TelluriumCommandExecutor.prototype.submit = function(uid){
    this.execCommand("submit", uid);
};

TelluriumCommandExecutor.prototype.check = function(uid){
    this.execCommand("check", uid);
};

TelluriumCommandExecutor.prototype.uncheck = function(uid){
    this.execCommand("uncheck", uid);
};

TelluriumCommandExecutor.prototype.getAttribute = function(uid, attribute){
    return this.execCommand("getAttribute", uid, attribute);
};

TelluriumCommandExecutor.prototype.getOptionSelector = function(optionLocator){
    var split = optionLocator.split("=");
    var sel = "";
    split[0] = split[0].trim();
    split[1] = split[1].trim();
    if(split[0] == "label" || split[0] == "text"){
        sel = this.cssBuilder.buildText(split[1]);
    }else if(split[0] == "value"){
        sel = this.cssBuilder.buildAttribute(split[0], split[1]);
    }else if(split[0] == "index"){
        var inx = parseInt(split[1]) - 1;
        sel = ":eq(" + inx + ")"
    }else if(split[0] == "id"){
        sel = this.cssBuilder.buildId(split[1]);
    }else{
        logger.error("Invalid Selector optionLocator " + optionLocator);
        return null;
    }

    return sel;
};

TelluriumCommandExecutor.prototype.select = function(uid, option){
    this.selectByLabel(uid, option);
//    var optionSelector = this.getOptionSelector(optionLocator);
//    this.execCommand("select", uid, optionSelector);
};

TelluriumCommandExecutor.prototype.selectByLabel = function(uid, option){
//    var optionSelector = this.getOptionSelector("label=" + option);
    var optionSelector = this.getOptionSelector(option);
//    alert("selectByLabel option: " + optionSelector);
    this.execCommand("select", uid, optionSelector);
};

TelluriumCommandExecutor.prototype.selectByIndex = function(uid, option){
//    var optionSelector = this.getOptionSelector("index=" + option);
    var optionSelector = this.getOptionSelector(option);
    this.execCommand("select", uid, optionSelector);
};

TelluriumCommandExecutor.prototype.selectByValue = function(uid, option){
//    var optionSelector = this.getOptionSelector("value=" + option);
    var optionSelector = this.getOptionSelector(option);
    this.execCommand("select", uid, optionSelector);
};

TelluriumCommandExecutor.prototype.getSelectOptions = function(uid) {
    return this.execCommand("getSelectOptions", uid);
};

TelluriumCommandExecutor.prototype.getSelectValues = function(uid) {
    return this.execCommand("getSelectValues", uid);
};

TelluriumCommandExecutor.prototype.getSelectedLabel = function(uid) {
    return this.execCommand("getSelectedLabel", uid);
};

TelluriumCommandExecutor.prototype.getSelectedLabels = function(uid) {
    return this.execCommand("getSelectedLabels", uid);
};

TelluriumCommandExecutor.prototype.getSelectedValue = function(uid) {
    return this.execCommand("getSelectedValue", uid);
};

TelluriumCommandExecutor.prototype.getSelectedValues = function(uid) {
    return this.execCommand("getSelectedValues", uid);
};

TelluriumCommandExecutor.prototype.getSelectedIndex = function(uid) {
    return this.execCommand("getSelectedIndex", uid);
};

TelluriumCommandExecutor.prototype.getSelectedIndexes = function(uid) {
    return this.execCommand("getSelectedIndexes", uid);
};

TelluriumCommandExecutor.prototype.addSelection = function(uid, optionLocator) {
    var opt = this.getOptionSelector(optionLocator);
    this.execCommand("getSelectedIndexes", uid, opt);
};

TelluriumCommandExecutor.prototype.removeSelection = function(uid, optionLocator) {
    var opt = this.getOptionSelector(optionLocator);
    this.execCommand("removeSelection", uid, opt);
};

TelluriumCommandExecutor.prototype.removeAllSelections = function(uid) {
    this.execCommand("removeAllSelections", uid);
};

TelluriumCommandExecutor.prototype.getAllText = function(locator){
    var element = selenium.browserbot.findElement(locator);
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).text());
    });

    return out;
};

TelluriumCommandExecutor.prototype.getListSize = function(uid) {
    return this.execCommand("getListSize", uid);
};

TelluriumCommandExecutor.prototype.getTeListSize = function(uid) {
    return this.execCommand("getListSize", uid);
};

TelluriumCommandExecutor.prototype.getAllTableBodyText = function(uid){
    return this.execCommand("getAllBodyCell", uid, this.textWorker);
};

TelluriumCommandExecutor.prototype.getTableHeaderColumnNum = function(uid) {
    return this.execCommand("getHeaderColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTeTableHeaderColumnNum = function(uid) {
    return this.execCommand("getHeaderColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTableFootColumnNum = function(uid) {
    return this.execCommand("getFooterColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTeTableFootColumnNum = function(uid) {
    return this.execCommand("getFooterColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTableRowNum = function(uid) {
    return this.execCommand("getTableRowNum", uid);
};

TelluriumCommandExecutor.prototype.getTeTableRowNum = function(uid) {
    return this.execCommand("getTableRowNum", uid);
};

TelluriumCommandExecutor.prototype.getTableColumnNum = function(uid) {
    return this.execCommand("getTableColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTeTableColumnNum = function(uid) {
    return this.execCommand("getTableColumnNum", uid);
};

TelluriumCommandExecutor.prototype.getTableRowNumForTbody = function(uid) {
    return this.execCommand("getTableRowNumForTbody", uid);
};

TelluriumCommandExecutor.prototype.getTeTableRowNumForTbody = function(uid) {
    return this.execCommand("getTableRowNumForTbody", uid);
};

TelluriumCommandExecutor.prototype.getTableColumnNumForTbody = function(uid) {
    return this.execCommand("getTableColumnNumForTbody", uid);
};

TelluriumCommandExecutor.prototype.getTeTableColumnNumForTbody = function(uid) {
    return this.execCommand("getTableColumnNumForTbody", uid);
};

TelluriumCommandExecutor.prototype.getTableTbodyNum = function(uid) {
    return this.execCommand("getTableTbodyNum", uid);
};

TelluriumCommandExecutor.prototype.getTeTableTbodyNum = function(uid) {
    return this.execCommand("getTableTbodyNum", uid);
};

TelluriumCommandExecutor.prototype.getRepeatNum = function(uid) {
    return this.execCommand("getRepeatNum", uid);
};

TelluriumCommandExecutor.prototype.getText = function(uid) {
    return this.execCommand("getText", uid);
};

TelluriumCommandExecutor.prototype.getValue = function(uid) {
    return this.execCommand("getValue", uid);
};

TelluriumCommandExecutor.prototype.isElementPresent = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if(uim != null){
        var obj = uim.walkTo(context, uiid);
        return obj != null;
    }

    return false;
};

TelluriumCommandExecutor.prototype.isChecked = function(uid) {
    return this.execCommand("isChecked", uid);
};

TelluriumCommandExecutor.prototype.isVisible = function(uid) {
    return this.execCommand("isVisible", uid);
};

TelluriumCommandExecutor.prototype.isEditable = function(uid) {
    return this.execCommand("isEditable", uid);
};

TelluriumCommandExecutor.prototype.getCSS = function(uid, cssName) {
    return this.execCommand("getCSS", uid, cssName);
};

TelluriumCommandExecutor.prototype.getCSSAsString = function(uid, cssName) {
    var out = this.getCSS(uid, cssName);

    return arrayToString(out);
};

TelluriumCommandExecutor.prototype.isDisabled = function(uid) {
    return this.execCommand("isDisabled", uid);
};

TelluriumCommandExecutor.prototype.showUi = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if(uim != null){
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            uim.stree = this.uiAlg.buildSTree(uim);
            var outlineVisitor = new UiOutlineVisitor();
            var tipVisitor = new UiSimpleTipVisitor();
            var chainVisitor = new STreeChainVisitor();
            chainVisitor.addVisitor(outlineVisitor);
            chainVisitor.addVisitor(tipVisitor);

            uiid.convertToUiid(uid);
            var uoj = uim.stree.walkTo(context, uiid);
            if(uoj != null){
                uoj.traverse(context, chainVisitor);
            }else{
                logger.error("Cannot find UI object " + uid + " in snapshot");
                throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
            }
        }else{
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    }else{
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

TelluriumCommandExecutor.prototype.cleanUi = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if(uim != null){
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            var tipCleaner = new UiSimpleTipCleaner();
            var outlineCleaner = new UiOutlineCleaner();
            var chainVisitor = new STreeChainVisitor();
            chainVisitor.addVisitor(tipCleaner);
            chainVisitor.addVisitor(outlineCleaner);

            uiid.convertToUiid(uid);
            var uoj = uim.stree.walkTo(context, uiid);
            if(uoj != null){
                uoj.traverse(context, chainVisitor);
            }else{
                logger.error("Cannot find UI object " + uid + " in snapshot");
                throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
            }
        }else{
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    }else{
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

TelluriumCommandExecutor.prototype.getHTMLSource = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if (uim != null) {
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            var stree = this.uiAlg.buildSTree(uim);
            var visitor = new UiHTMLSourceVisitor();

            uiid.convertToUiid(uid);
            var uoj = stree.walkTo(context, uiid);
            if (uoj != null) {
                uoj.traverse(context, visitor);
                return visitor.htmlsource;
            } else {
                logger.error("Cannot find UI object " + uid + " in snapshot");
                throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
            }
        } else {
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    } else {
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

TelluriumCommandExecutor.prototype.getHTMLSourceAsString = function(uid) {

    var htmls = this.getHTMLSource(uid);
    if (htmls != null && htmls.length > 0) {
        var sb = new StringBuffer();
        for (var i = 0; i <htmls.length; i++){
            sb.append(htmls[i].key).append(": ").append(htmls[i].val).append("\n");
        }
        return sb.toString();
    }

    return "";
};

TelluriumCommandExecutor.prototype.getUids = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if (uim != null) {
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            var stree = this.uiAlg.buildSTree(uim);
            var visitor = new UiUIDVisitor();

            uiid.convertToUiid(uid);
            var uoj = stree.walkTo(context, uiid);
            if (uoj != null) {
                uoj.traverse(context, visitor);
                return visitor.uids;
            } else {
                logger.error("Cannot find UI object " + uid + " in snapshot");
                throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
            }
        } else {
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    } else {
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

TelluriumCommandExecutor.prototype.getUidsAsString = function(uid){
    var uids = this.getUids(uid);
    return arrayToString(uids);
};

TelluriumCommandExecutor.prototype.getCssSelectorCount = function(uid, sel){

    return teJQuery(this.dom).find(sel).size();
};

TelluriumCommandExecutor.prototype.getCssSelectorMatch = function(uid, sel){
    var out = [];
    var $e = teJQuery(this.dom).find(sel);
    if($e.size() > 0){
        for(var i=0; i<$e.size(); i++){
            out.push($e.eq(i).outerHTML());
        }

    }

    return out;
};

TelluriumCommandExecutor.prototype.getCssSelectorMatchAsString = function(uid, sel){
    var out = this.getCssSelectorMatch(uid, sel);
    return arrayToString(out);
};

TelluriumCommandExecutor.prototype.validateUiModule = function(uid, jsonString){
    var newuim = new UiModule();
    newuim.dom = this.dom;
    var jsonArray = JSON.parse(jsonString, null);
    newuim.parseUiModule(jsonArray);
    var response = new UiModuleLocatingResponse();
    var result = this.uiAlg.santa(newuim, this.dom);
    if(result){
        //set the UI Module to be valid after it is located
        newuim.valid = true;
        response.id = newuim.getId();
        response.relaxed = newuim.relaxed;
        if (!response.relaxed)
            response.found = true;
        response.relaxDetails = newuim.relaxDetails;
        response.matches = newuim.matches;
        response.score = newuim.score;
    }

    return response;
};

TelluriumCommandExecutor.prototype.validateUiModuleAsString = function(uid,jsonString){
    var response = this.validateUiModule(uid, jsonString);
    if(response != null){
        return response.toString();
    }else{
        return "Result is emtpy";
    }
};

TelluriumCommandExecutor.prototype.useUiModule = function(uid, jsonString){
    var newuim = new UiModule();
    newuim.dom = this.dom;
    var jsonArray = JSON.parse(jsonString, null);
    newuim.parseUiModule(jsonArray);
    var result = this.uiAlg.santa(newuim, this.dom);

    if(result){
        newuim.valid = true;
        logger.debug("Validating UI module " + newuim.getId() + " successfully.");
        this.cache.put(newuim.getId(), newuim);
    }else{
        logger.error("Validating UI module " + newuim.getId() + " failed.");
    }
};

TelluriumCommandExecutor.prototype.toJSON = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.cache.get(first);
    if(uim != null){
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            var jsonConverter =  new UiJSONVisitor();
            obj.traverse(context, jsonConverter);
            return jsonConverter.jsonArray;
        }else{
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    }else{
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

TelluriumCommandExecutor.prototype.toJSONString = function(uid){
    var json = this.toJSON(uid);

    return JSON.stringify(json);
};

TelluriumCommandExecutor.prototype.decorateFunctionWithTimeout = function(f, timeout) {
    if (f == null) {
        return null;
    }

    var now = new Date().getTime();
    var timeoutTime = now + timeout;
    return function() {
        if (new Date().getTime() > timeoutTime) {
            throw new TelluriumError(ErrorCodes.TIME_OUT, "Timed out after " + timeout + "ms");
        }
        return f();
    };
};

TelluriumCommandExecutor.prototype.waitForPageToLoad = function(uid, timeout){
    var self = this;
    logger.debug("Set up page load timeout timer at " + (new Date()).getTime() + ": timeout=" + timeout);
    this.browserBot.pageTimeoutTimerId = setTimeout(function() {
        logger.debug("Page load timeout at " + (new Date()).getTime());
        self.browserBot.newPageLoaded = false;
        self.browserBot.pageLoadError = PageLoadError.TIMEOUT;
        self.browserBot.pageTimeoutTimerId = null;
        if(self.browserBot.pagePollTimerId != null){
            clearInterval(self.browserBot.pagePollTimerId);
            self.browserBot.pagePollTimerId = null;
        }
    }, timeout);

    this.browserBot.pollPageLoad();
    this.clearCache();
};

TelluriumCommandExecutor.prototype.onPageLoad = function(){
    if(!this.browserBot.newPageLoaded){
        logger.debug("Page is loaded.");
        this.dom = this.browserBot.getMostRecentDocument();
        this.browserBot.setCurrentWindowToMostRecentWindow();        
    }
};

TelluriumCommandExecutor.prototype.updateCurrentDom = function(){
    logger.debug("Update current DOM for tellurium command executor after page loaded.");
    this.dom = this.browserBot.getMostRecentDocument();
    this.browserBot.setCurrentWindowToMostRecentWindow();
    this.browserBot.newPageLoaded = true;
    this.browserBot.pageLoadError = null;
    if (this.browserBot.pageTimeoutTimerId != null) {
        clearTimeout(this.browserBot.pageTimeoutTimerId);
        this.browserBot.pageTimeoutTimerId = null;
    }
    if (this.browserBot.pagePollTimerId != null) {
        clearInterval(this.browserBot.pagePollTimerId);
        this.browserBot.pagePollTimerId = null;
    }
};

function WaitPageLoad(scope){
    scope.onPageLoad();
}

TelluriumCommandExecutor.prototype.open = function(uid, url){
    this.browserBot.showInBrowser(url);
    var self = this;
    this.browserBot.newPageLoaded = false;
    this.browserBot.pageLoadError = null;
    this.browserBot.pageTimeoutTimerId = null;
    //Strange, change the timeout value to 3000, got the error that the dom is not set.
    setTimeout(WaitPageLoad, 1000, self);
    this.clearCache();
};

TelluriumCommandExecutor.prototype.reset = function(uid){
    this.execCommand("reset", uid);
};


function arrayToString(array){
    if(array != null && array.length > 0){
        return array.join(", ");
    }

    return "";
}