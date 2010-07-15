/*
 * An UI of TrUMP IDE.
 */

const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
const DEFAULT_ATTRIBUTES_XML = "<?xml version=\"1.0\"?><attributes id=\"attributes_tree_xml\" xmlns=\"\"></attributes>";

function Editor(window) {
    this.window = window;
    var self = this;
    window.editor = this;
    this.document = document;
    
    this.logView = new LogView(this);
    this.logView.setLog(logger);

    this.recorder = null;

    this.registerRecorder();
    this.innerTree = null;

    this.buildCustomizeTree(DEFAULT_XML);

    this.currentUid = null;

    this.decorator = new Decorator();

    this.uistore = new UiModuleStore();

    this.cmdHistory = new Array();

    this.cmdView = CommandView;
    this.cmdTree = document.getElementById('commandHistoryTree');
    this.cmdTree.view = this.cmdView;
    this.builder = new UiBuilder();
    this.checker = new UiChecker();

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
    this.commandList.sort();
    this.commandMap.put("getHTMLSource", "getHTMLSourceAsString");
    this.commandMap.put("getUids", "getUidsAsString");
    this.commandMap.put("getCSS", "getCSSAsString");        
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
    this.recorder.registerListener();
    this.populateWindowUrl();
};

Editor.prototype.populateWindowUrl = function(){
    var contentWindows = getAvailableContentDocumentUrls(Components.interfaces.nsIDocShellTreeItem.typeChrome);
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("windowURL")),
            XulUtils.toXPCOMArray(contentWindows));
};

Editor.prototype.unload = function(){
    this.recorder.unregisterListener();
    this.decorator.cleanShowNode();
};

Editor.prototype.close = function(){
//    this.recorder.unregisterListener();
};

Editor.prototype.toggleRecordButton = function(){
    var recordToolbarButton = document.getElementById("record-button");

    if(!recordToolbarButton.getAttribute("checked")){
        recordToolbarButton.setAttribute("checked", "true");

        var stopToolbarButton = document.getElementById("stop-button");
        stopToolbarButton.removeAttribute("checked");

        this.recorder.registerListener();
        this.populateWindowUrl();
    }
};

Editor.prototype.toggleStopButton = function(){
    var stopToolbarButton = document.getElementById("stop-button");

    if(!stopToolbarButton.getAttribute("checked")){
        stopToolbarButton.setAttribute("checked", "true");

        var recordToolbarButton = document.getElementById("record-button");
        recordToolbarButton.removeAttribute("checked");

        this.recorder.unregisterListener();
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

Editor.prototype.generateUiModule = function(){
    var tagArrays = this.recorder.tagObjectArray;

//    var tagObject;
    var element;
    this.innerTree = new Tree();

    var frameName = null;

    var i;
    for(i=0; i<tagArrays.length; ++i){
        var obj = tagArrays[i];
        if(obj.frameName != null){
            if(frameName == null){
                frameName = obj.frameName;
            }
        }
    }

    if(frameName != null){
        var objs = new Array();
        for (i = 0; i < tagArrays.length; ++i) {
            if(tagArrays[i].frameName == frameName){
                objs.push(tagArrays[i]);
            }
        }
        for (i = 0; i < objs.length; ++i) {
            var tobj = objs[i];
            element = new ElementObject();
//          element.uid = tobj.tag+i;
            element.uid = suggestName(tobj);
            element.xpath = tobj.xpath;
            element.attributes = tobj.attributes;
            element.domNode = tobj.node;
            element.frameName = tobj.frameName;

            this.innerTree.addElement(element);

        }
        var root = this.innerTree.root;
        var frame = new NodeObject();
        frame.id = frameName;
        frame.parent = null;
//        frame.domNode = root.domNode.ownerDocument;
        frame.domNode = this.innerTree.document;
        frame.xpath = "";
        frame.attributes = new Hashtable();
        frame.attributes.put("tag", "iframe");
        frame.attributes.put("name", frameName);
        frame.tag = "iframe";
        frame.children.push(root);
        root.parent = frame;
        this.innerTree.root = frame;

        //do some post processing work
        this.innerTree.postProcess();
        this.innerTree.buildUiObject(this.builder, this.checker);

        this.innerTree.buildIndex();
    } else {
        for (i = 0; i < tagArrays.length; ++i) {
            var tagObject = tagArrays[i];
            element = new ElementObject();
//          element.uid = tagObject.tag+i;
            element.uid = suggestName(tagObject);
            element.xpath = tagObject.xpath;
            element.attributes = tagObject.attributes;
            element.domNode = tagObject.node;
            element.frameName = tagObject.frameName;

            this.innerTree.addElement(element);
            //do some post processing work
        }
        this.innerTree.postProcess();
        this.innerTree.buildUiObject(this.builder, this.checker);
        this.innerTree.buildIndex();
    }
};

Editor.prototype.generateButton = function(){
    this.switchToSourceTab();

    try {
        this.generateUiModule();

        this.updateSource();

        this.validateUI();

    }catch(error){
        logger.error("Error generating UI Module:\n" + describeErrorStack(error));
    }
};

Editor.prototype.validateUI = function(){
    if(tellurium == null){
        tellurium = new Tellurium();
        tellurium.initialize();
    }      
    var glf = Preferences.getPref("extensions.trump.grouplocating");
//    alert("extensions.trump.grouplocating: " + glf);
    if(glf == undefined){
        glf = true;
    }
    
    if(glf){
        try{
           this.validateUiModule();
        }catch(error){
            var msg;
            if(this.innerTree.root != null){
                msg = "Error while validating UI Module " + this.innerTree.root.id + ": " + error.name + " -> " + extractErrorMessage(error) +"\n";
                msg = msg + "\n" + describeErrorStack(error) + "\n";
            }else{
                msg = "Cannot validate empty UI module !\n";
            }
            logger.error(msg);
            this.showMessage(msg);
        }
    }else{
        this.validateXPath();
    }
};

Editor.prototype.validateXPath = function(){
    logger.debug("start to validate UI object's xpath");

    this.innerTree.validate();

    logger.debug("Done validating UI object's XPath");
};

Editor.prototype.validateUiModule = function() {
    logger.debug("Start validating UI Module");
    //validate UI object's XPath
    if (this.innerTree.root != null) {
        var uim = this.uistore.build(this.innerTree);
        this.uistore.save(uim, this.innerTree.document);
        var result = this.uistore.validate();
        if (result != null) {
            this.innerTree.clearValidFlag();
            if (!result.found) {
                if (result.relaxDetails != null) {
                    for (var j = 0; j < result.relaxDetails.length; j++) {
                        this.innerTree.markInvalidUiObject(result.relaxDetails[j].uid);
                    }
                }
            }
            var msg = this.describeUiModuleValidationResult(result);
            this.showMessage(msg);
        }
        logger.debug("Done validating UI Module, please see detailed result on the message window");
    } else {
        logger.warn("The root node in the Tree is null");
        return null;
    }
};

Editor.prototype.showMessage = function(message){
    document.getElementById("exportMessage").value = message;
};

Editor.prototype.describeUiModuleValidationResult = function(result){
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

Editor.prototype.updateSource = function(){
    this.clearSourceTabContent();
    var sourceTextNode = document.getElementById("source");

    var uiModelArray = this.innerTree.printUI();
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
    this.exportToWindowInBackground();
};

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
    this.clearSourceTabContent();
    this.clearExportToWindowTabContent();
    this.logView.clear();
    this.clearCustomizeTabContext();
    this.decorator.cleanShowNode();
    this.clearMessageBox();
    this.innerTree = null;
    this.cleanTestView();
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
    if(this.innerTree != null){
        xml = this.innerTree.buildXML();
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
        var pxml = parser.parseFromString(xml, "text/xml");
        customize_tree.builder.datasource = pxml;
        customize_tree.builder.rebuild();
    }
};

Editor.prototype.populateUiTypeAutoComplete = function(){
    var uitypes = tellurium.getRegisteredUiTypes();
    logger.debug("Get registered UI types: " + uitypes.join(", "));
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.editor.getAutoCompleteSearchParam("uiType")),
                                                      XulUtils.toXPCOMArray(uitypes));
};

Editor.prototype.processCustomizeEvent = function(event){
//    logger.debug('Customize ' + event.target.getAttribute("label"));
    this.currentUid = event.target.getAttribute("uid");
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
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
    if(this.uistore.id != null && this.uistore.uim != null){
        document.getElementById("commandName").disabled = false;
        document.getElementById("commandUID").disabled = false;
        document.getElementById("commandParam").disabled = false;
        document.getElementById("commandResult").disabled = false;
    }else{
        document.getElementById("commandName").disabled = true;
        document.getElementById("commandUID").disabled = true;
        document.getElementById("commandParam").disabled = true;
        document.getElementById("commandResult").disabled = true;
    }
    try{
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandName")),
                                                      XulUtils.toXPCOMArray(this.commandList));

        this.command.uim = this.uistore.uim;
        this.command.dom = this.uistore.dom;
        this.command.locateUI();
        var uids = this.command.getUids(this.command.uim.id);
        if(uids != null && uids.length > 0){
            Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandUID")),
                                                      XulUtils.toXPCOMArray(uids));
            var example = document.getElementById("exampleText");
            example.readonly = false;
            var sb = new StringBuffer();
            sb.append("Example: Command: 'mouseOver', UID: '").append(uids[0]).append("'.\n");

            example.value = sb.toString();
            example.readonly = true;
        }
    }catch(error){
        logger.error("Executing command failed:\n" + describeErrorStack(error));
    }
    
};

Editor.prototype.cleanTestView = function(){
    document.getElementById("commandName").value = "";
    document.getElementById("commandUID").value = "";
    document.getElementById("commandParam").value = "";
    document.getElementById("commandResult").value = "";
    document.getElementById("commandName").disabled = true;
    document.getElementById("commandUID").disabled = true;
    document.getElementById("commandParam").disabled = true;
    document.getElementById("commandResult").disabled = true;
    this.cmdHistory = new Array();
    this.cmdView.clearAll();
    var example = document.getElementById("exampleText");
    example.readonly = false;
    example.value = "";
    example.readonly = true;
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
        var pxml = parser.parseFromString(xml, "text/xml");
        ui_attribute_tree.builder.datasource = pxml;
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
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
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
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
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

        this.validateUI();
        this.customizeButton();
        this.updateSource();
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.exportUiModule = function(){
    if(this.innerTree != null){
        var txt = this.innerTree.createUiModule();
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
    if(this.innerTree != null){
        var txt = this.innerTree.createUiModule();
        //switch to the exportToWindows tab
        document.getElementById("editorTabs").selectedItem = document.getElementById("exportToWindowTab");
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value  = txt;

        logger.debug("UI Module is exported to window ");
    }
};

Editor.prototype.exportToWindowInBackground = function(){
    if(this.innerTree != null){
        var txt = this.innerTree.createUiModule();
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value  = txt;

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