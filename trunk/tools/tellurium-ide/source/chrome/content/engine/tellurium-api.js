
Tellurium.prototype.removeCachedUiModule = function(id){
    return this.cache.remove(id);
};

Tellurium.prototype.clearCache = function(){
    return this.cache.clear();
};

Tellurium.prototype.getCacheState = function(){

    return this.cache.cacheOption;
};

Tellurium.prototype.getCacheSize = function(){

    return this.cache.size();
};

Tellurium.prototype.enableCache = function(){
    this.cache.cacheOption = true;
};

Tellurium.prototype.disableCache = function(){
    this.cache.cacheOption = false;
};


Tellurium.prototype.isUseLog = function(){
    return this.logManager.isUseLog;
};

Tellurium.prototype.useTeApi = function(isUse){
    fbLog("before call useTeApi(" + isUse + ")" + this.isUseTeApi, this);
    if (typeof(isUse) == "boolean") {
        this.isUseTeApi = isUse;
        this.cache.cacheOption = isUse;
    } else {
        this.isUseTeApi = ("true" == isUse || "TRUE" == isUse);
        this.cache.cacheOption = ("true" == isUse || "TRUE" == isUse);
    }
    fbLog("After call useTeApi(" + isUse + ")" + this.isUseTeApi, this);
};

Tellurium.prototype.isUseCache = function(){
    return this.cache.cacheOption;
};

Tellurium.prototype.isUiModuleCached = function(id){
    return this.getCachedUiModule(id) != null; 
};

/*Tellurium.prototype.useClosestMatch = function(isUse){
    this.useClosestMatch(isUse);
};*/

Tellurium.prototype.useUiModule = function(jsonarray){
    var uim = new UiModule();
    !this.logManager.isUseLog || fbLog("Input JSON Array for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    var response = new UiModuleLocatingResponse();
    var result = this.uiAlg.santa(uim, null);
    if(result){
        //set the UI Module to be valid after it is located
        uim.valid = true;
        !this.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
        var id = uim.getId();
        this.cacheUiModule(uim);

        response.id = id;
        response.relaxed = uim.relaxed;
        if (!response.relaxed){
            response.found = true;            
        }
        response.relaxDetails = uim.relaxDetails;
        response.matches = uim.matches;
        response.score = uim.score;
        !this.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);
    }

    return response;
};

Tellurium.prototype.validateUiModule = function(jsonarray){
    var uim = new UiModule();
    !this.logManager.isUseLog || fbLog("Input JSON for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    this.uiAlg.validate(uim, null);
    //set the UI Module to be valid after it is located
    uim.valid = true;
    !this.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
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
    !this.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);

    return response;
};

Tellurium.prototype.describeUiModuleList = function(){
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

Tellurium.prototype.describeUiModule = function(uiModelArray) {
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

Tellurium.prototype.useClosestMatch = function(isUse){
    !this.logManager.isUseLog || fbLog("call useClosestMatch", isUse);
    if (typeof(isUse) == "boolean") {
        this.uiAlg.allowRelax = isUse;
    } else {
        this.uiAlg.allowRelax = ("true" == isUse || "TRUE" == isUse);
    }

    !this.logManager.isUseLog || fbLog("Call useClosestMatch(" + isUse + ") to set allowRelax to ", this.uiAlg.allowRelax);
};

Tellurium.prototype.locateUI = function(uid){
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

Tellurium.prototype.execCommand = function(cmd, uid, param){
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
        var proxyObject = this.proxyObject.walkTo(context, uid);
        if(proxyObject != null){
            if(proxyObject.respondsTo(cmd)){
                var params = [context];
                if(param != null && param != undefined){
                    params.push(param);
                }

                return proxyObject[cmd].apply(proxyObject, params);
            }else{
                logger.error("Proxy UI Object " + uid + " does not have method " + cmd);
                throw new TelluriumError(ErrorCodes.INVALID_CALL_ON_UI_OBJ, "Proxy UI Object " + uid + " does not have method " + cmd);
            }    
        }else{
            logger.error("Cannot find UI Module " + uid);
            throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + first);
        }
    }
};

Tellurium.prototype.getUiByTag = function(tag, attributes){
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
        !this.logManager.isUseLog || fbLog("Found element for getUiByTag " + tag, $e.get());
        var teuid = "te-" + tellurium.idGen.next();
        $e.attr("teuid", teuid);
        this.proxyObject.addUiObject(teuid, $e.get(0));
        teuids.push(teuid);
    }

    return teuids;
};

Tellurium.prototype.removeMarkedUids = function(tag){
    var $found = teJQuery(selenium.browserbot.currentWindow).find(tag + "[teuid]");
    if($found.size() > 0){
        $found.removeAttr("teuid");
    }
};

Tellurium.prototype.assertTrue = function(variable){
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

Tellurium.prototype.assertFalse = function(variable){
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

Tellurium.prototype.assertEquals = function(variable1, variable2){
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

Tellurium.prototype.assertNotEquals = function(variable1, variable2){
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

Tellurium.prototype.assertNull = function(variable){
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

Tellurium.prototype.assertNotNull = function(variable){
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

Tellurium.prototype.toggle = function(uid) {
    this.execCommand("toggle", uid);
};

Tellurium.prototype.blur = function(uid) {
    this.execCommand("blur", uid);
};

Tellurium.prototype.click = function(uid) {
    this.execCommand("click", uid);
};

Tellurium.prototype.clickAt = function(uid, coordString) {
    this.execCommand("clickAt", uid, coordString);
};

Tellurium.prototype.doubleClick = function(uid){
    this.execCommand("doubleClick", uid);
};

Tellurium.prototype.fireEvent = function(uid, event){
    this.execCommand("fireEvent", uid, event);
};

Tellurium.prototype.focus = function(uid){
    this.execCommand("focus", uid);
};

Tellurium.prototype.type = function(uid, val){
    this.execCommand("type", uid, val);
};

Tellurium.prototype.typeKey = function(uid, key){
    this.execCommand("typeKey", uid, key);
};

Tellurium.prototype.keyDown = function(uid, key){
    this.execCommand("keyDown", uid, key);
};

Tellurium.prototype.keyPress = function(uid, key){
    this.execCommand("keyPress", uid, key);
};

Tellurium.prototype.keyUp = function(uid, key){
    this.execCommand("keyUp", uid, key);
};

Tellurium.prototype.mouseOver = function(uid){
    this.execCommand("mouseOver", uid);
};

Tellurium.prototype.mouseDown = function(uid){
    this.execCommand("mouseDown", uid);
};

Tellurium.prototype.mouseEnter = function(uid){
    this.execCommand("mouseEnter", uid);
};

Tellurium.prototype.mouseLeave = function(uid){
    this.execCommand("mouseLeave", uid);
};

Tellurium.prototype.mouseOut = function(uid){
    this.execCommand("mouseOut", uid);
};

Tellurium.prototype.submit = function(uid){
    this.execCommand("submit", uid);
};

Tellurium.prototype.check = function(uid){
    this.execCommand("check", uid);
};

Tellurium.prototype.uncheck = function(uid){
    this.execCommand("uncheck", uid);
};

Tellurium.prototype.getAttribute = function(uid, attribute){
    return this.execCommand("getAttribute", uid, attribute);
};

Tellurium.prototype.getOptionSelector = function(optionLocator){
    var split = optionLocator.split("=");
    var sel = "";
    if(split.length > 1){
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        if(split[0] == "label" || split[0] == "text"){
            sel = this.cssBuilder.buildText(split[1]);
        }else if(split[0] == "value"){
            sel = this.cssBuilder.buildAttribute(split[0], split[1]);
        }else if(split[0] == "index"){
            var inx = parseInt(split[1]) - 1;
            sel = ":eq(" + inx + ")";
        }else if(split[0] == "id"){
            sel = this.cssBuilder.buildId(split[1]);
        }else{
            logger.error("Invalid Selector optionLocator " + optionLocator);
            return null;
        }
    }else{
        //use label or text as default if no prefix
        sel = this.cssBuilder.buildText(split[0].trim());
    }

    return sel;
};

Tellurium.prototype.select = function(uid, option){
    this.selectByLabel(uid, option);
//    var optionSelector = this.getOptionSelector(optionLocator);
//    this.execCommand("select", uid, optionSelector);
};

Tellurium.prototype.selectByLabel = function(uid, option){
//    var optionSelector = this.getOptionSelector("label=" + option);
    var optionSelector = this.getOptionSelector(option);
    this.execCommand("select", uid, optionSelector);
};

Tellurium.prototype.selectByIndex = function(uid, option){
//    var optionSelector = this.getOptionSelector("index=" + option);
    var optionSelector = this.getOptionSelector(option);
    this.execCommand("select", uid, optionSelector);
};

Tellurium.prototype.selectByValue = function(uid, option){
//    var optionSelector = this.getOptionSelector("value=" + option);
    var optionSelector = this.getOptionSelector(option);
    this.execCommand("select", uid, optionSelector);
};

Tellurium.prototype.getSelectOptions = function(uid) {
    return this.execCommand("getSelectOptions", uid);
};

Tellurium.prototype.getSelectValues = function(uid) {
    return this.execCommand("getSelectValues", uid);
};

Tellurium.prototype.getSelectedLabel = function(uid) {
    return this.execCommand("getSelectedLabel", uid);
};

Tellurium.prototype.getSelectedLabels = function(uid) {
    return this.execCommand("getSelectedLabels", uid);
};

Tellurium.prototype.getSelectedValue = function(uid) {
    return this.execCommand("getSelectedValue", uid);
};

Tellurium.prototype.getSelectedValues = function(uid) {
    return this.execCommand("getSelectedValues", uid);
};

Tellurium.prototype.getSelectedIndex = function(uid) {
    return this.execCommand("getSelectedIndex", uid);
};

Tellurium.prototype.getSelectedIndexes = function(uid) {
    return this.execCommand("getSelectedIndexes", uid);
};

Tellurium.prototype.addSelection = function(uid, optionLocator) {
    var opt = this.getOptionSelector(optionLocator);
    this.execCommand("getSelectedIndexes", uid, opt);
};

Tellurium.prototype.removeSelection = function(uid, optionLocator) {
    var opt = this.getOptionSelector(optionLocator);
    this.execCommand("removeSelection", uid, opt);
};

Tellurium.prototype.removeAllSelections = function(uid) {
    this.execCommand("removeAllSelections", uid);
};

Tellurium.prototype.getAllText = function(locator){
    var element = selenium.browserbot.findElement(locator);
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).text());
    });

    return out;
};

Tellurium.prototype.getListSize = function(uid) {
    return this.execCommand("getListSize", uid);
};

Tellurium.prototype.getAllTableBodyText = function(uid){
    return this.execCommand("getAllBodyCell", uid, this.textWorker);
};

Tellurium.prototype.getTableHeaderColumnNum = function(uid) {
    return this.execCommand("getHeaderColumnNum", uid);
};

Tellurium.prototype.getTableFootColumnNum = function(uid) {
    return this.execCommand("getFooterColumnNum", uid);
};

Tellurium.prototype.getTableRowNum = function(uid) {
    return this.execCommand("getTableRowNum", uid);
};

Tellurium.prototype.getTableColumnNum = function(uid) {
    return this.execCommand("getTableColumnNum", uid);
};

Tellurium.prototype.getTableRowNumForTbody = function(uid) {
    return this.execCommand("getTableRowNumForTbody", uid);
};

Tellurium.prototype.getTableColumnNumForTbody = function(uid) {
    return this.execCommand("getTableColumnNumForTbody", uid);
};

Tellurium.prototype.getTableTbodyNum = function(uid) {
    return this.execCommand("getTableTbodyNum", uid);
};

Tellurium.prototype.getRepeatNum = function(uid) {
    return this.execCommand("getRepeatNum", uid);
};

Tellurium.prototype.getText = function(uid) {
    return this.execCommand("getText", uid);
};

Tellurium.prototype.getValue = function(uid) {
    return this.execCommand("getValue", uid);
};

Tellurium.prototype.isElementPresent = function(uid){
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

Tellurium.prototype.isChecked = function(uid) {
    return this.execCommand("isChecked", uid);
};

Tellurium.prototype.isVisible = function(uid) {
    return this.execCommand("isVisible", uid);
};

Tellurium.prototype.isEditable = function(uid) {
    return this.execCommand("isEditable", uid);
};

Tellurium.prototype.getCSS = function(uid, cssName) {
    return this.execCommand("getCSS", uid, cssName);
};

Tellurium.prototype.getCSSAsString = function(uid, cssName) {
    var out = this.getCSS(uid, cssName);

    return arrayToString(out);
};

Tellurium.prototype.isDisabled = function(uid) {
    return this.execCommand("isDisabled", uid);
};

/*
Tellurium.prototype.showUi = function(uid) {
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

Tellurium.prototype.cleanUi = function(uid) {
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
*/

Tellurium.prototype.getHTMLSource = function(uid) {
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

Tellurium.prototype.getHTMLSourceAsString = function(uid) {

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

Tellurium.prototype.getUids = function(uid) {
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

Tellurium.prototype.getUidsAsString = function(uid){
    var uids = this.getUids(uid);
    return arrayToString(uids);
};

Tellurium.prototype.getCssSelectorCount = function(sel){

    return teJQuery(this.dom).find(sel).size();
};

Tellurium.prototype.getCssSelectorMatch = function(sel){
    var out = [];
    var $e = teJQuery(this.dom).find(sel);
    if($e.size() > 0){
        for(var i=0; i<$e.size(); i++){
            out.push($e.eq(i).outerHTML());
        }

    }

    return out;
};

Tellurium.prototype.getCssSelectorMatchAsString = function(sel){
    var out = this.getCssSelectorMatch(sel);
    return arrayToString(out);
};

Tellurium.prototype.validateDirectUiModule = function(jsonString){
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

Tellurium.prototype.validateUiModuleAsString = function(jsonString){
    var response = this.validateUiModule(jsonString);
    if(response != null){
        return response.toString();
    }else{
        return "Result is emtpy";
    }
};

Tellurium.prototype.useDirectUiModule = function(jsonString){
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

Tellurium.prototype.toJSON = function(uid){
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

Tellurium.prototype.toJSONString = function(uid){
    var json = this.toJSON(uid);

    return JSON.stringify(json);
};

Tellurium.prototype.waitForPageToLoad = function(uid, timeout){
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

Tellurium.prototype.onPageLoad = function(){
    if(!this.browserBot.newPageLoaded){
        logger.debug("Page is loaded.");
        this.dom = this.browserBot.getMostRecentDocument();
        this.browserBot.setCurrentWindowToMostRecentWindow();
    }
};

Tellurium.prototype.updateCurrentDom = function(){
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

Tellurium.prototype.open = function(url) {
//    logger.debug("url " + url + ", browserBot current url " + this.browserBot.getCurrentUrl());
    if (url == this.browserBot.getCurrentUrl()) {
        this.onPageLoad();
    } else {
        this.browserBot.showInBrowser(url);
        var self = this;
        this.browserBot.newPageLoaded = false;
        this.browserBot.pageLoadError = null;
        this.browserBot.pageTimeoutTimerId = null;
        //Strange, change the timeout value to 3000, got the error that the dom is not set.
        setTimeout(WaitPageLoad, 1000, self);
    }

    this.clearCache();
};

Tellurium.prototype.reset = function(uid){
    this.execCommand("reset", uid);
};
