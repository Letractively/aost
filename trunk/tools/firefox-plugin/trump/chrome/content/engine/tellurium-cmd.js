function TelluriumCommand(){

//    this.uim = null;

    this.dom = null;

    this.cache = new TelluriumUiCache();

    this.uiAlg = new UiAlg(); 

    this.cssBuilder = new JQueryBuilder();
}

TelluriumCommand.prototype.cachedUiModuleNum = function(){
    return this.cache.size();    
};

TelluriumCommand.prototype.cacheUiModule = function(uim){
    if(uim != null)
        this.cache.put(uim.id, uim);
};

TelluriumCommand.prototype.getCachedUiModuleList = function(){
    return this.cache.keySet();    
};

TelluriumCommand.prototype.getCachedUiModule = function(id){
    return this.cache.get(id);
};

TelluriumCommand.prototype.clearCache = function(){
    return this.cache.clear();
};

TelluriumCommand.prototype.describeUiModuleList = function(){
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

TelluriumCommand.prototype.describeUiModule = function(uiModelArray) {
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

TelluriumCommand.prototype.generateCode = function(){
    return this.createUiModuleCode() + "\n\n" + this.createTestCaseCode() + "\n";    
};

TelluriumCommand.prototype.createUiModuleCode = function() {
    var sb = new StringBuffer();
    sb.append("//----------------------- MyUiModule.groovy ------------------------\n\n");
    sb.append("package test\n\n");
    sb.append("import org.telluriumsource.dsl.DslContext\n\n");
    sb.append("/**\n *\tThis UI module file (MyUiModule.groovy) is automatically generated by Trump 0.8.0.\n");
    sb.append(" *\tFor any problems, please report to Tellurium User Group at: \n");
    sb.append(" *\t\thttp://groups.google.com/group/tellurium-users\n");
    sb.append(" *\n");
    sb.append(" *\tExample: Google Search Module\n");
    sb.append(" *\n");
    sb.append(" *\t\tui.Container(uid: \"Google\", clocator: [tag: \"td\"]){\n");
    sb.append(" *\t\t\tInputBox(uid: \"Input\", clocator: [title: \"Google Search\"]\n");
    sb.append(" *\t\t\tSubmitButton(uid: \"Search\", clocator: [name: \"btnG\", value: \"Google Search\"]\n");
    sb.append(" *\t\t\tSubmitButton(uid: \"ImFeelingLucky\", clocator: [value: \"I'm Feeling Lucky\"]\n");
    sb.append(" *\t\t}\n");
    sb.append(" *\n");
    sb.append(" *\t\tpublic void doGoogleSearch(String input) {\n");
    sb.append(" *\t\t\tkeyType \"Google.Input\", input\n");
    sb.append(" *\t\t\tclick \"Google.Search\"\n");
    sb.append(" *\t\t\twaitForPageToLoad 30000\n");
    sb.append(" *\t\t}\n");
    sb.append(" *\n");
    sb.append(" */\n\n");
    sb.append("class MyUiModule extends DslContext{\n\n");
    sb.append("\tpublic void defineUi() {\n");
    sb.append(this.describeUiModuleList());
    sb.append("\t}\n\n");
    sb.append("\t//Add your methods here\n\n");
    sb.append("}\n");

    return sb.toString();
};

TelluriumCommand.prototype.createTestCaseCode = function() {
    var sb = new StringBuffer();
    sb.append("//----------------------- MyTestCase.java ------------------------\n\n");
    sb.append("package test;\n\n");
    sb.append("import org.telluriumsource.test.java.TelluriumJUnitTestCase;\n");
    sb.append("import org.junit.*;\n\n");
    sb.append("/**\n *\tThis test file (MyTestCase.java) is automatically generated by Trump 0.8.0.\n");
    sb.append(" *\n");
    sb.append(" */\n\n");
    sb.append("public class MyTestCase extends TelluriumJUnitTestCase {\n");
    sb.append("\tprivate static MyUiModule mum;\n\n");
    sb.append("\t@BeforeClass\n");
    sb.append("\tpublic static void initUi() {\n");
    sb.append("\t\tmum = new MyUiModule()\n");
    sb.append("\t\tnum.defineUi();\n");
    sb.append("\t\tconnectSeleniumServer();\n");
    sb.append("\t\tuseTelluriumEngine(true);\n");
    sb.append("\t}\n\n");
    sb.append("\t//Add your test cases here\n");
    sb.append("\t@Test\n");
    sb.append("\tpublic void testCase(){\n");
    sb.append("\t\t...\n");
    sb.append("\t}\n");
    sb.append("}\n");

    return sb.toString();
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

TelluriumCommand.prototype.locateUI = function(uid){
    var uim = this.cache.get(uid);
    if(uim != null && this.dom != null){
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

/*
TelluriumCommand.prototype.walkToUiObject = function(context, uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = null;

    if(uiid.size() > 0){          
        var first = uiid.peek();
        var uim = this.cache.get(first);
        if(uim != null){
            obj = uim.walkTo(context, uiid);
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
*/

TelluriumCommand.prototype.execCommand = function(cmd, uid, param){
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
                logger.error("UI Object " + uid + " does not have method " + cmd);
                throw new TelluriumError(ErrorCodes.INVALID_CALL_ON_UI_OBJ, "UI Object " + uid + " does not have method " + cmd);
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

TelluriumCommand.prototype.waitForPageToLoad = function(timeout){

};

TelluriumCommand.prototype.decorateFunctionWithTimeout = function(f, timeout) {
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

TelluriumCommand.prototype.showUI = function(uid) {
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

TelluriumCommand.prototype.cleanUI = function(uid) {
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

TelluriumCommand.prototype.getHTMLSource = function(uid) {
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

TelluriumCommand.prototype.getUids = function(uid) {
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
        return response.toString();
    }else{
        return "Result is emtpy";
    }
};

TelluriumCommand.prototype.useUiModule = function(uid, jsonString){
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

TelluriumCommand.prototype.toJSON = function(uid){
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

TelluriumCommand.prototype.toJSONString = function(uid){
    var json = this.toJSON(uid);

    return JSON.stringify(json);
};

TelluriumCommand.prototype.open = function(url){
    var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
            .getService(Components.interfaces.nsIWindowMediator)
            .getMostRecentWindow("navigator:browser");

    win.open(url);
};

function arrayToString(array){
    if(array != null && array.length > 0){
        return array.join(", ");
    }

    return "";
}