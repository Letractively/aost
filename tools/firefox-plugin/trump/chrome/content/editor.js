/*
 * An UI of Trump IDE.
 */

const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
const DEFAULT_ATTRIBUTES_XML = "<?xml version=\"1.0\"?><attributes id=\"attributes_tree_xml\" xmlns=\"\"></attributes>";

const RecordState = {
    RECORD: "record",
    PAUSE: "pause",
    STOP: "stop"
};

function Editor(window) {
    this.window = window;
    var self = this;
    
    window.editor = this;
    this.document = document;
//    this.init();
    
    this.logView = new LogView(this);
    this.logView.setLog(logger);

    this.buildCustomizeTree(DEFAULT_XML);

    this.currentUid = null;

    this.decorator = new Decorator();

    this.builder = new UiBuilder();

    this.workspace = new Workspace(this.builder, new UiChecker());

    this.recorder = null;

    this.registerRecorder();

    this.cmdHistory = new Array();

    this.cmdView = CommandView;
    this.cmdTree = document.getElementById('commandHistoryTree');
    this.cmdTree.view = this.cmdView;

//    this.checker = new UiChecker();

    this.command = new TelluriumCommand();

    this.commandList = new Array();

    //Map the command, because some command needs to convert the format for display, for example, array to String
    this.commandMap = new Hashtable();

    this.registerCommands();

//    this.options = new Preferences();

    //Detect the browser properties
    BrowserDetect.init();
    this.os = BrowserDetect.OS;

    if(tellurium == null){
        tellurium = new Tellurium();
        tellurium.initialize();        
    }

}

Editor.prototype.onDOMContentLoaded = function(event){
    logger.debug("Register window on DOMContentLoaded");
    this.recorder.registerForWindow(event.target.defaultView);
};

Editor.prototype.setWindowURL = function(url){
//    document.getElementById("windowURL").value = url;
//    this.recorder.updateListenerForWindow(url);
};

Editor.prototype.registerCommands = function(){
    this.commandList.push("blur");
    this.commandList.push("click");
    this.commandList.push("clickAt");
    this.commandList.push("doubleClick");
    this.commandList.push("fireEvent");
    this.commandList.push("focus");
    this.commandList.push("type");
    this.commandList.push("typeKey");
    this.commandList.push("keyDown");
    this.commandList.push("keyPress");
    this.commandList.push("keyUp");
    this.commandList.push("mouseOver");
    this.commandList.push("mouseDown");
    this.commandList.push("mouseEnter");
    this.commandList.push("mouseLeave");
    this.commandList.push("mouseOut");
    this.commandList.push("submit");
    this.commandList.push("check");
    this.commandList.push("uncheck");
    this.commandList.push("getAttribute");
    this.commandList.push("select");
    this.commandList.push("getText");
    this.commandList.push("getValue");
    this.commandList.push("isChecked");
    this.commandList.push("isVisible");
    this.commandList.push("getCSS");
    this.commandList.push("isDisable");
    this.commandList.push("showUI");
    this.commandList.push("cleanUI");
    this.commandList.push("getHTMLSource");
    this.commandList.push("getUids");
    this.commandList.push("getCssSelectorCount");
    this.commandList.push("getCssSelectorMatch");
    this.commandList.push("validateUiModule");
    this.commandList.push("toJSON");
    this.commandList.sort();
    this.commandMap.put("getHTMLSource", "getHTMLSourceAsString");
    this.commandMap.put("getUids", "getUidsAsString");
    this.commandMap.put("getCSS", "getCSSAsString");
    this.commandMap.put("getCssSelectorMatch", "getCssSelectorMatchAsString");
    this.commandMap.put("validateUiModule", "validateUiModuleAsString");
    this.commandMap.put("toJSON", "toJSONString");
};

Editor.prototype.getAutoCompleteSearchParam = function(id) {
    var textbox = document.getElementById(id);
    if (!this.autoCompleteSearchParams)
        this.autoCompleteSearchParams = {};
    if (this.autoCompleteSearchParams[id]) {
        return this.autoCompleteSearchParams[id];
    } else {
        var param = id + "_";
        for (var i = 0; i < 10; i++) {
            param += Math.floor(Math.random()*36).toString(36);
        }
        this.autoCompleteSearchParams[id] = param;
        textbox.searchParam = param;
        return param;
    }
};

Editor.prototype.cleanupAutoComplete = function() {
    if (this.autoCompleteSearchParams) {
        for (var id in this.autoCompleteSearchParams) {
            Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams[id]));
        }
    }
};

Editor.GENERIC_AUTOCOMPLETE = Components.classes["@mozilla.org/autocomplete/search;1?name=selenium-ide-generic"].getService(Components.interfaces.nsISeleniumIDEGenericAutoCompleteSearch);

Editor.prototype.updateUiType = function(value){

};

Editor.prototype.registerRecorder = function(){
    this.recorder = new Recorder(this.window);
    this.recorder.workspace = this.workspace;
    this.recorder.registerListeners();
//    this.recorder.observers.push(this);
    this.populateWindowUrl();
};

Editor.prototype.populateWindowUrl = function(){
    var contentWindows = getAvailableContentDocumentUrls(Components.interfaces.nsIDocShellTreeItem.typeChrome);
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("windowURL")),
            XulUtils.toXPCOMArray(contentWindows));
};

Editor.prototype.unload = function(){
    this.recorder.unregisterListeners();
    this.decorator.cleanShowNode();
};

Editor.prototype.close = function(){
//    this.recorder.unregisterListener();
};

Editor.prototype.toggleRecordButton = function(){
    var recordToolbarButton = document.getElementById("record-button");

    if(!recordToolbarButton.getAttribute("checked")){
        recordToolbarButton.setAttribute("checked", "true");
        recordToolbarButton.setAttribute("class", RecordState.RECORD);

        var stopToolbarButton = document.getElementById("stop-button");
        stopToolbarButton.removeAttribute("checked");

        this.recorder.registerListeners();
        this.populateWindowUrl();
    }else{
        var state = recordToolbarButton.getAttribute("class");

        if(state == RecordState.RECORD){
            recordToolbarButton.setAttribute("class", RecordState.PAUSE);
        }else{
            recordToolbarButton.setAttribute("class", RecordState.RECORD);
        }

        this.recorder.unregisterListeners();
        this.recorder.registerListeners();
    }
};

Editor.prototype.toggleStopButton = function(){
    var stopToolbarButton = document.getElementById("stop-button");

    if(!stopToolbarButton.getAttribute("checked")){
        stopToolbarButton.setAttribute("checked", "true");
        
        var recordToolbarButton = document.getElementById("record-button");
        recordToolbarButton.removeAttribute("checked");
        recordToolbarButton.setAttribute("class", RecordState.STOP);
        this.recorder.unregisterListeners();
    }
};

function suggestName(tagObject){
    var tag = tagObject.tag;
    var attributes = tagObject.attributes;
    
    var name = attributes.get("id");
    if(name == null || name.length == 0){
        name = attributes.get("value");
    }
    if(name == null || name.length == 0){
        name = attributes.get("name");
    }
    if(name == null || name.length == 0){
        name = attributes.get("title");
    }
    if(name == null || name.length == 0){
        name = attributes.get("text");
    }
    if(name == null || name.length == 0){
        name = attributes.get("class");
    }
    if(name == null || name.length == 0){
        if(tag == "input"){
            var type = attributes.get("type");
            if(type == "text"){
                name = "Input";
            }else if(type == "submit"){
                name = "Submit";
            }else if(type == "image"){
                name = "Image";
            }else if(type == "checkbox"){
                name = "Option";
            }else if(type == "radio"){
                name = "Option";
            }else if(type == "password"){
                name = "Password";
            }else{
                name = "Button";
            }
        }else if(tag == "a" || tag == "link"){
            name = "Link";
        }else if(tag == "select"){
            name = "Select";
        }else if(tag == "tr"){
            name = "Section";
        }else if(tag == "td"){
            name = "Part";
        }else if(tag == "th"){
            name = "Header";
        }else if(tag == "tfoot"){
            name = "Footer";
        }else if(tag == "tbody"){
            name = "Group";
        }else if(tag == "form"){
            name = "Form";
        }else if(tag == "image"){
            name = "Image";
        }else if(tag == "table"){
            name = "Table";
        }
    }
    if(name != null && name.length > 0){
        var split = name.split(" ");
        if(split.length > 1){
            name = split[0].toCamel() + split[1].toCamel();
        }
    }

    if(name != null && name.length > 0){
        //remove special characters and only keep alphanumeric and "_"
        name = name.replace(/[^a-zA-Z_0-9]+/g,'');
    }

    if(name == null || name.trim().length == 0){
        name = tag;
    }
    
    return name.toCamel();
}

Editor.prototype.generateButton = function(){
   this.switchToSourceTab();
/* 
    try {
        this.workspace.generateUiModule(this.recorder.tagObjectArray);

        this.updateSource();

        this.validateUI();

    }catch(error){
        logger.error("Error generating UI Module:\n" + describeErrorStack(error));
    }*/
    try {
        this.recorder.generateSource();
    }catch(error){
        logger.error("Error generating UI Module:\n" + describeErrorStack(error));
    }
};

Editor.prototype.validateUI = function(){
//    if(tellurium == null){
//        tellurium = new Tellurium();
//        tellurium.initialize();
//    }
    var glf = Preferences.getPref("extensions.trump.grouplocating");
//    alert("extensions.trump.grouplocating: " + glf);
    if(glf == undefined){
        glf = true;
    }
    
    if(glf){
        var msg;
        try{
            msg = this.workspace.validateUiModule();
            logger.debug(msg);
            this.showMessage(msg);
        }catch(error){
            msg = "Error while validating UI Module. \n" + describeErrorStack(error) +"\n";
            logger.error(msg);
            this.showMessage(msg);
        }
    }else{
        this.validateXPath();
    }
};

Editor.prototype.validateXPath = function(){
    logger.debug("start to validate UI object's xpath");

    this.workspace.validateXPath();

    logger.debug("Done validating UI object's XPath");
};

Editor.prototype.showMessage = function(message){
    document.getElementById("exportMessage").value = message;
};

Editor.prototype.updateSource = function(){
    this.clearSourceTabContent();
    var sourceTextNode = document.getElementById("source");
    var uiModelArray = this.workspace.innerTree.printUI();
    var uiModel = new StringBuffer();
    if(uiModelArray != undefined){
        for(var j=0; j<uiModelArray.length; ++j){
            uiModel.append(uiModelArray[j]);
        }
    } else {
        logger.error("uiModelArray is not defined, cannot generate source!");
    }

//    logger.debug("ui model generated:\n"+uiModel);
    sourceTextNode.value = uiModel;
};

Editor.prototype.saveButton = function(){
    if(this.workspace.uim != null){
        this.command.cacheUiModule(this.workspace.uim);
        logger.info("Saved UI module " + this.workspace.uim.id + " to cache.");
        this.recorder.clearAll();
        this.clearCustomizeTabContext();
        this.decorator.cleanShowNode();
        this.exportToWindowInBackground();
    }else{
        logger.warn("There is no UI module in workspace to save to cache.");
    }
    document.getElementById("editorTabs").selectedItem = document.getElementById("exportToWindowTab");
};

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
    this.clearSourceTabContent();
    this.clearExportToWindowTabContent();
    this.logView.clear();
    this.clearCustomizeTabContext();
    this.decorator.cleanShowNode();
    this.clearMessageBox();
    this.workspace.clear();
    this.cleanTestView();
    this.command.clearCache();
    this.cleanupAutoComplete();   
    document.getElementById("windowURL").value = null;
};

Editor.prototype.clearCustomizeTabContext = function(){
    document.getElementById("uid").value = "";
    document.getElementById("uiType").value = "";
    document.getElementById("uid").setAttribute("disabled", "true");
    document.getElementById("uiType").setAttribute("disabled", "true");
    document.getElementById("group_Check_Box").disabled = true;
    this.buildCustomizeTree(DEFAULT_XML);
    this.buildUiAttributeTree(DEFAULT_ATTRIBUTES_XML);
};

Editor.prototype.switchToSourceTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("sourceTab");
};

Editor.prototype.clearSourceTabContent = function(){
    document.getElementById("source").value = "";
};

Editor.prototype.clearExportToWindowTabContent = function(){
    document.getElementById("exportSource").value = "";
};

Editor.prototype.clearMessageBox = function(){
    document.getElementById("exportMessage").value = "";
};

Editor.prototype.selectedTreeItem = function(event){
    this.recorder.showSelectedNode();
};

Editor.prototype.customizeButton = function(){
    this.switchToCustomizeTab();
    var xml = DEFAULT_XML;
    if(this.workspace.innerTree != null){
        xml = this.workspace.buildXML();
    }

    this.buildCustomizeTree(xml);
    var uiTypes = this.builder.getAvailableUiTypes();
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("uiType")),
                                                      XulUtils.toXPCOMArray(uiTypes));
};

Editor.prototype.switchToCustomizeTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("customizeTab");
};

Editor.prototype.buildCustomizeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var customize_tree = document.getElementById("customize_tree"); 
        customize_tree.builder.datasource = parser.parseFromString(xml, "text/xml");
        customize_tree.builder.rebuild();
    }
};

Editor.prototype.populateUiTypeAutoComplete = function(){
//    var uitypes = tellurium.getRegisteredUiTypes();
    var uitypes = this.builder.getAvailableUiTypes();
    logger.debug("Get registered UI types: " + uitypes.join(", "));
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.editor.getAutoCompleteSearchParam("uiType")),
                                                      XulUtils.toXPCOMArray(uitypes));
};

Editor.prototype.processCustomizeEvent = function(event){
//    logger.debug('Customize ' + event.target.getAttribute("label"));
    this.currentUid = event.target.getAttribute("uid");
    var uiObject = this.workspace.innerTree.uiObjectMap.get(this.currentUid);
//    logger.debug("uiObject : " + uiObject.descObject());
    if(uiObject != null){
        this.fillUiObjectFields(uiObject);
        this.enableUiObjectFields();
        this.populateUiTypeAutoComplete();
    }else{
        logger.warn("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.testButton = function(){
    this.toggleStopButton();
    document.getElementById("editorTabs").selectedItem = document.getElementById("testTab");
    try{
        var uims = this.command.getCachedUiModuleList();

        if(uims != null && uims.length > 0){
            document.getElementById("uiModuleId").disabled = false;
            document.getElementById("commandName").disabled = false;
            document.getElementById("commandUID").disabled = false;
            document.getElementById("commandParam").disabled = false;
            document.getElementById("commandResult").disabled = false;

            Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("uiModuleId")),
                                                          XulUtils.toXPCOMArray(uims));

            Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandName")),
                                                          XulUtils.toXPCOMArray(this.commandList));

            var uim = this.command.getCachedUiModule(uims[0]);
            document.getElementById("uiModuleId").value = uim.id;
            var uids = this.command.getUids(uim.id);
            if(uids != null && uids.length > 0){
                Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandUID")),
                                                          XulUtils.toXPCOMArray(uids));
            }
            this.command.dom = this.workspace.dom;
            this.command.locateUI(uim.id);
        }else{
            document.getElementById("uiModuleId").disabled = true;
            document.getElementById("commandName").disabled = true;
            document.getElementById("commandUID").disabled = true;
            document.getElementById("commandParam").disabled = true;
            document.getElementById("commandResult").disabled = true;
        }        
    }catch(error){
        logger.error("Executing command failed:\n" + describeErrorStack(error));
    }
    
};

Editor.prototype.updateUiModuleName = function(uid) {
    var uids = this.command.getUids(uid);
    if (uids != null && uids.length > 0) {
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandUID")),
                XulUtils.toXPCOMArray(uids));

    }
};

Editor.prototype.cleanTestView = function(){
    document.getElementById("uiModuleId").value = "";
    document.getElementById("commandName").value = "";
    document.getElementById("commandUID").value = "";
    document.getElementById("commandParam").value = "";
    document.getElementById("commandResult").value = "";
    document.getElementById("uiModuleId").disabled = true;
    document.getElementById("commandName").disabled = true;
    document.getElementById("commandUID").disabled = true;
    document.getElementById("commandParam").disabled = true;
    document.getElementById("commandResult").disabled = true;
    this.cmdHistory = new Array();
    this.cmdView.clearAll();
};

Editor.prototype.updateUiCommand = function(value){

};

Editor.prototype.updateUiUID = function(value){

};

Editor.prototype.runUiCommand = function(){
    var name = document.getElementById("commandName").value;
    var uid = document.getElementById("commandUID").value;
    var param = document.getElementById("commandParam").value;
    var cmd = new TestCmd(name, uid, param);

    logger.info("Run command " + name + "('" + uid + "', '" + param + "')");
    try{

        var realName = this.commandMap.get(name);
        var result;
        if(realName != null){
           result = this.command.run(realName, uid, param);
        }else{
           result = this.command.run(name, uid, param);
        }

        logger.info("Executing command " + name + " finished, result: " + result);
        if (result != null && result != undefined) {
            document.getElementById("commandResult").value = result;
            cmd.result = result;
        } else {
            cmd.result = "";
        }
        this.cmdHistory.push(cmd);
        this.cmdView.setTestCommands(this.cmdHistory);
        this.cmdView.rowInserted();
    }catch(error){
        logger.info("Executing command " + name + " failed:\n" + describeErrorStack(error));
    }
};

Editor.prototype.processCheckEvent = function(event){
//    alert("You selected " + event.target.getAttribute("cid"));
};

Editor.prototype.fillUiObjectFields = function(uiObject){
    document.getElementById("uid").value = uiObject.uid;
    document.getElementById("uiType").value = uiObject.uiType;

    if (uiObject.isContainer) {
        document.getElementById("group_Check_Box").disabled = false;
        document.getElementById("group_Check_Box").checked = uiObject.group;
    } else {
        document.getElementById("group_Check_Box").disabled = false;
        document.getElementById("group_Check_Box").checked = false;
        document.getElementById("group_Check_Box").disabled = true;
    }

    if (uiObject.node != null) {
        var xml = uiObject.node.buildAttributeXml();
        this.buildUiAttributeTree(xml);
    } else {
        logger.warn("Ui object " + uiObject.uid + " does not point to a Node in the tree")
    }
};

Editor.prototype.enableUiObjectFields = function(){
    teJQuery("#uid").removeAttr("disabled");
    teJQuery("#uiType").removeAttr("disabled");
};

Editor.prototype.disableUiObjectFields = function(){
    teJQuery("#uid").attr("disabled","disabled");
    teJQuery("#uiType").attr("disabled","disabled");
};

Editor.prototype.buildUiAttributeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var ui_attribute_tree = document.getElementById("ui_attribute_tree");
        ui_attribute_tree.builder.datasource = parser.parseFromString(xml, "text/xml");
        ui_attribute_tree.builder.rebuild();
    }
};

Editor.prototype.getElementsByTagValue = function(tag, attr, val){
    var elements = document.getElementsByTagName(tag);
    if(elements != null){
        for(var i=0; i<elements.length; i++){
            var attrval = elements[i].getAttribute(attr);
            if(attrval == val){
                return elements[i];
            }
        }
    }

    return null;
};

Editor.prototype.showNodeOnWeb = function(){
    var uiObject = this.workspace.getUiObject(this.currentUid);
    if(uiObject != null){
        if(uiObject.node != null && uiObject.node.domNode != null){
            this.decorator.showNode(uiObject.node.domNode);
        }else{
            logger.error("UI Object " + this.currentUid + " does not point to a Dom Node");
        }
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.updateUiObject = function(){
    var uiObject = this.workspace.getUiObject(this.currentUid);
    if(uiObject != null){
        logger.debug("Update UI object " + this.currentUid);

        //update UID
        uiObject.uid = document.getElementById("uid").value;

        //update UI Type
        uiObject.uiType = document.getElementById("uiType").value;

        //update Group attribute
        if(!document.getElementById("group_Check_Box").disabled){
            uiObject.group = document.getElementById("group_Check_Box").checked;
        }

        //update attributes
        var attrmap = new HashMap();
        attrmap.put("position", null);
        var keys = uiObject.node.attributes.keySet();
        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
            if (key != "tag") {
                //check each attribute, first look at the check box by the name conversion
                var cid = "CID" + key;

                var elem = this.getElementsByTagValue("checkbox", "name", cid);
                if (elem != null) {
                    //if the attribute is selected
                    if (elem.checked) {
                        //get the id of the value of the attribute
                        var vid = "VID" + key;
                        var velem = this.getElementsByTagValue("textbox", "name", vid);
                        if(velem != null){
                            var val = velem.value;
                            attrmap.set(key, val);
                        }else{
                            logger.error("Cannot find value node " + vid + " for attribute " + key);
                        }
                    }

                } else {
                    logger.warn("Cannot Find CheckBox " + cid + " for attribute " + key);
                }
            }
        }
        uiObject.updateAttributes(attrmap);
        if(uiObject.parent == null){
            this.workspace.id = uiObject.uid;
            this.workspace.innerTree.root.id = uiObject.uid;
            this.workspace.innerTree.buildIndex();
        }
        this.validateUI();
        this.customizeButton();
        this.updateSource();
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.exportUiModule = function(){
    if(this.workspace.innerTree != null){
        var txt = this.workspace.innerTree.createUiModule();
//        logger.debug("Create UI Module:\n" + txt);

        var dir = Preferences.getPref("extensions.trump.exportdirectory");
        if (dir == undefined || dir == null) {
            if (this.os == "Windows") {
                dir = Preferences.DEFAULT_OPTIONS.defaultWinDirectory;
            } else {
                dir = Preferences.DEFAULT_OPTIONS.defaultDirectory;
            }
        }

        FileUtils.saveAs(dir, txt);
        logger.debug("UI Module is exported to directory " + dir);
    }
};

Editor.prototype.exportToWindow = function(){
    if(this.workspace.innerTree != null){
        var txt = this.workspace.innerTree.createUiModule();
        //switch to the exportToWindows tab
        document.getElementById("editorTabs").selectedItem = document.getElementById("exportToWindowTab");
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value  = txt;

        logger.debug("UI Module is exported to window ");
    }
};

Editor.prototype.exportToWindowInBackground = function() {
    if (this.command.cachedUiModuleNum() > 0) {
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.command.generateCode();

        logger.debug("UI Module is exported to window ");
    }
};

Editor.prototype.updateOptions = function(){
    window.openDialog("chrome://trump/content/preferences.xul", "options", "chrome,modal,resizable", this.os);
    var jslog = Preferences.getPref("extensions.trump.jslog");
    if(jslog ==undefined){
        jslog = true;
    }
    logger.jslog = jslog;

//    var elem = document.getElementById("logging-console");
    var elem = window.frames["logViewFrame"].document.getElementById("logging-console");
    if (elem != null) {
        var logWrap = Preferences.getPref("extensions.trump.logwrap");
        if(logWrap == undefined){
            logWrap = true;
        }

        if (logWrap) {
            elem.style.whiteSpace = "normal";
        } else {
            elem.style.whiteSpac = "nowrap";
        }
    }
};

function TestCmd(name, uid, param){
    this.name = name;
    this.uid = uid;
    this.param = param;
    this.result = null;
}