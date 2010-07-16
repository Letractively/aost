function TelluriumCommand(){

    this.uim = null;

    this.uiAlg = new UiAlg(); 

    this.dom = null;

    this.cssBuilder = new JQueryBuilder();
}

TelluriumCommand.prototype.locateUI = function(){
    if(this.uim != null && this.dom != null){
        this.uiAlg.santa(this.uim, this.dom);
    }else{
        if(this.uid == null){
            logger.error("UI Module is null");
            throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "UI Module is null");
        }else{
            logger.error("DOM is not specified");
            throw new TelluriumError(ErrorCodes.DOM_NOT_SPECIFIED, "DOM is not specified");
        }
    }
};

TelluriumCommand.prototype.walkToUiObject = function(context, uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = null;

    if(uiid.size() > 0){
        if(this.uim != null){
            obj = this.uim.walkTo(context, uiid);
            if(obj != null){
                logger.debug("After walkTo, found object " + uid);
            }
            else{
                logger.error("Cannot find UI object " + uid);
                throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
            }

        }
    }

    return obj;
};

TelluriumCommand.prototype.execCommand = function(cmd, uid, param){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);
    if(obj != null){
        if(obj.respondsTo(cmd)){
            var params = [context];
            if(param != null && param != undefined){
                params.push(param);
            }
            return obj[cmd].apply(obj, params);
        }else{
            logger.error("UI Object " + uid + " does not have method " + cmd);
            throw new TelluriumError(ErrorCodes.INVALID_CALL_ON_UI_OBJ, "UI Object " + uid + " does not have method " + cmd);
        }
    }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }
};

TelluriumCommand.prototype.blur = function(uid) {
    this.execCommand("blur", uid);
};

TelluriumCommand.prototype.click = function(uid) {
    this.execCommand("click", uid);
};

TelluriumCommand.prototype.clickAt = function(uid, coordString) {
    this.execCommand("clickAt", uid, coordString);
};

TelluriumCommand.prototype.doubleClick = function(uid){
    this.execCommand("doubleClick", uid);
};

TelluriumCommand.prototype.fireEvent = function(uid, event){
    this.execCommand("fireEvent", uid, event);
};

TelluriumCommand.prototype.focus = function(uid){
    this.execCommand("focus", uid);
};

TelluriumCommand.prototype.type = function(uid, val){
    this.execCommand("type", uid, val);
};

TelluriumCommand.prototype.typeKey = function(uid, key){
    this.execCommand("typeKey", uid, key);
};

TelluriumCommand.prototype.keyDown = function(uid, key){
    this.execCommand("keyDown", uid, key);
};

TelluriumCommand.prototype.keyPress = function(uid, key){
    this.execCommand("keyPress", uid, key);
};

TelluriumCommand.prototype.keyUp = function(uid, key){
    this.execCommand("keyUp", uid, key);
};

TelluriumCommand.prototype.mouseOver = function(uid){
    this.execCommand("mouseOver", uid);
};

TelluriumCommand.prototype.mouseDown = function(uid){
    this.execCommand("mouseDown", uid);
};

TelluriumCommand.prototype.mouseEnter = function(uid){
    this.execCommand("mouseEnter", uid);
};

TelluriumCommand.prototype.mouseLeave = function(uid){
    this.execCommand("mouseLeave", uid);
};

TelluriumCommand.prototype.mouseOut = function(uid){
    this.execCommand("mouseOut", uid);
};

TelluriumCommand.prototype.submit = function(uid){
    this.execCommand("submit", uid);
};

TelluriumCommand.prototype.check = function(uid){
    this.execCommand("check", uid);
};

TelluriumCommand.prototype.uncheck = function(uid){
    this.execCommand("uncheck", uid);
};

TelluriumCommand.prototype.getAttribute = function(uid, attribute){
    return this.execCommand("getAttribute", uid, attribute);
};

TelluriumCommand.prototype.getOptionSelector = function(optionLocator){
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

TelluriumCommand.prototype.select = function(uid, optionLocator){
    var optionSelector = this.getOptionSelector(optionLocator);
    this.execCommand("select", uid, optionSelector);
};

TelluriumCommand.prototype.getText = function(uid) {
    return this.execCommand("getText", uid);
};

TelluriumCommand.prototype.getValue = function(uid) {
    return this.execCommand("getValue", uid);
};

TelluriumCommand.prototype.isChecked = function(uid) {
    return this.execCommand("isChecked", uid);
};

TelluriumCommand.prototype.isVisible = function(uid) {
    return this.execCommand("isVisible", uid);
};

TelluriumCommand.prototype.getCSS = function(uid, cssName) {
    return this.execCommand("getCSS", uid, cssName);
};

TelluriumCommand.prototype.getCSSAsString = function(uid, cssName) {
    var out = this.getCSS(uid, cssName);

    return arrayToString(out);
};

TelluriumCommand.prototype.isDisabled = function(uid) {
    return this.execCommand("isDisabled", uid);
};

TelluriumCommand.prototype.showUI = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);
    if (obj != null) {
        this.uim.stree = this.uiAlg.buildSTree(this.uim);
        var outlineVisitor = new UiOutlineVisitor();
        var tipVisitor = new UiSimpleTipVisitor();
        var chainVisitor = new STreeChainVisitor();
        chainVisitor.addVisitor(outlineVisitor);
        chainVisitor.addVisitor(tipVisitor);

        uiid.convertToUiid(uid);
        var uoj = this.uim.stree.walkTo(context, uiid);
        if(uoj != null){
            uoj.traverse(context, chainVisitor);
        }else{
            logger.error("Cannot find UI object " + uid + " in snapshot");
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
        }
        
//        this.uim.stree.traverse(context, chainVisitor);
    }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }
};

TelluriumCommand.prototype.cleanUI = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);
    if (obj != null) {
        var tipCleaner = new UiSimpleTipCleaner();
        var outlineCleaner = new UiOutlineCleaner();
        var chainVisitor = new STreeChainVisitor();
        chainVisitor.addVisitor(tipCleaner);
        chainVisitor.addVisitor(outlineCleaner);
        
        uiid.convertToUiid(uid);
        var uoj = this.uim.stree.walkTo(context, uiid);
        if(uoj != null){
            uoj.traverse(context, chainVisitor);
        }else{
            logger.error("Cannot find UI object " + uid + " in snapshot");
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
        }
//        this.uim.stree.traverse(context, chainVisitor);
    }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }
};

TelluriumCommand.prototype.getHTMLSource = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);    
    if (obj != null) {
        var stree = this.uiAlg.buildSTree(this.uim);
        var visitor = new UiHTMLSourceVisitor();
        
        uiid.convertToUiid(uid);
        var uoj = stree.walkTo(context, uiid);
        if(uoj != null){
            uoj.traverse(context, visitor);
            return visitor.htmlsource;
        }else{
            logger.error("Cannot find UI object " + uid + " in snapshot");
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
        }
 //       stree.traverse(context, visitor);

     }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }
};

TelluriumCommand.prototype.getHTMLSourceAsString = function(uid) {

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

TelluriumCommand.prototype.getUids = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);
    if (obj != null) {
        var stree = this.uiAlg.buildSTree(this.uim);
        var visitor = new UiUIDVisitor();
//        stree.traverse(context, visitor);

//        return visitor.uids;
        uiid.convertToUiid(uid);
        var uoj = stree.walkTo(context, uiid);
        if(uoj != null){
            uoj.traverse(context, visitor);
            return visitor.uids;
        }else{
            logger.error("Cannot find UI object " + uid + " in snapshot");
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid + " in snapshot");
        }
    }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }    
};

TelluriumCommand.prototype.getUidsAsString = function(uid){
    var uids = this.getUids(uid);
    return arrayToString(uids);
};

TelluriumCommand.prototype.getCssSelectorCount = function(uid, sel){

    return teJQuery(this.dom).find(sel).length;
};

TelluriumCommand.prototype.getCssSelectorMatch = function(uid, sel){
    var out = [];
    var $e = teJQuery(this.dom).find(sel);
    if($e.size() > 0){
        for(var i=0; i<$e.size(); i++){
            out.push($e.eq(i).outerHTML());
        }

    }

    return out;
};

TelluriumCommand.prototype.getCssSelectorMatchAsString = function(uid, sel){
    var out = this.getCssSelectorMatch(uid, sel);
    return arrayToString(out);
};

TelluriumCommand.prototype.run = function(name, uid, param){
    var api = this[name];

    if (typeof(api) == 'function') {
        var params = [];
        params.push(uid);
        params.push(param);
        return api.apply(this, params);
    }else{
        logger.error("Invalid Tellurium command " + name);
        throw new TelluriumError(ErrorCodes.INVALID_TELLURIUM_COMMAND, "Invalid Tellurium command " + name);
     }
};

TelluriumCommand.prototype.validateUiModule = function(uid, jsonString){
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

TelluriumCommand.prototype.validateUiModuleAsString = function(uid,jsonString){
    var response = this.validateUiModule(uid, jsonString);
    if(response != null){
        return this.describeUiModuleValidationResult(response);
    }else{
        return "Result is emtpy";
    }
};

TelluriumCommand.prototype.describeUiModuleValidationResult = function(result){
    var msg = new StringBuffer();
    msg.append("Validation result for UI Module " + result.id + "\n");
    msg.append("Found: " + result.found + "\n");
    msg.append("Relaxed: " + result.relaxed + "\n");
    msg.append("Match count: " + result.matches + "\n");
    msg.append("Match score: " + result.score + "\n");
    if(result.relaxDetails != null && result.relaxDetails.length > 0){
        msg.append("Relax details: \n");
        for(var i=0; i<result.relaxDetails.length; i++){
            msg.append("\tUID: " + result.relaxDetails[i].uid + "\n");
            if(result.relaxDetails[i].locator != null){
               msg.append("\tLocator: " + result.relaxDetails[i].locator.strLocator() + "\n");
            }else{
               msg.append("\tLocator: " + "\n");
            }
            msg.append("\tHTML: " + result.relaxDetails[i].html + "\n");
        }
    }

    return msg;
};

TelluriumCommand.prototype.toJSON = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = this.uim.walkTo(context, uiid);
    if (obj != null) {
        var jsonConverter =  new UiJSONVisitor();
        obj.traverse(context, jsonConverter);
        return jsonConverter.jsonArray;
    }else{
        logger.error("Cannot find UI object " + uid);
        throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
    }
};

TelluriumCommand.prototype.toJSONString = function(uid){
    var json = this.toJSON(uid);

    return JSON.stringify(json);
};

function arrayToString(array){
    if(array != null && array.length > 0){
        return array.join(", ");
    }

    return "";
}