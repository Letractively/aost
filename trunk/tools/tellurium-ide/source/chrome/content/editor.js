
function Editor(window) {
//    alert("tellurium before editor initialization " + tellurium);
    this.window = window;
    var self = this;
    
    window.editor = this;
//    window.browserBot = browserBot;
    window.browserBot = tellurium.browserBot;
//    alert("window browserBot" + window.browserBot);

    this.document = document;
//    this.init();
    
    this.logView = new LogView(this);
    this.logView.setLog(logger);

//    alert("logView " + this.logView);
    
    this.buildCustomizeTree(DEFAULT_XML);

    this.currentUid = null;

    this.decorator = new Decorator();

    this.builder = new UiBuilder();

    this.refIdSetter = new RefIdSetter();

    this.workspace = new Workspace(this.builder, new UiChecker(), this.refIdSetter);

    this.recorder = null;

    this.recordingEnabled = true;

    this.registerRecorder();

    this.cmdHistory = new Array();

    this.cmdView = CommandView;
//    this.cmdTree = document.getElementById('commandHistoryTree');
    this.cmdTree = document.getElementById('recordedCommandListTree');
    this.cmdTree.view = this.cmdView;

    this.currentSelectedCommand = null;
//    this.currentCommandIndex = -1;

    //Map the command, because some command needs to convert the format for display, for example, array to String
    this.commandMap = new Hashtable();

    this.testResultObserver = new TestResultObserver(document);

    this.testRunner = new TestRunner();
    this.testRunner.addObserver(this.cmdView);
    this.testRunner.addObserver(this.testResultObserver);
    this.commandList = this.testRunner.cmdExecutor.getCommandList();
    this.workspace.cmdExecutor = this.testRunner.cmdExecutor;
    
//    this.options = new Preferences();

    //Detect the browser properties
    BrowserDetect.init();
    this.os = BrowserDetect.OS;
}

Editor.prototype.onDOMContentLoaded = function(event) {
    try {       
        var recordToolbarButton = document.getElementById("record-button");
        if (recordToolbarButton.getAttribute("checked")) {
            logger.debug("Register window on DOMContentLoaded");
            this.recorder.registerForWindow(event.target.defaultView);
        } else {
            this.testRunner.updateDom();
        }
    } catch(error) {
        logger.error("Error processing onDOMContentLoaded:\n" + describeErrorStack(error));
    }
};

Editor.prototype.setWindowURL = function(url){
//    document.getElementById("windowURL").value = url;
//    this.recorder.updateListenerForWindow(url);
};

Editor.prototype._isSameWindow = function(w1, w2) {
    if (w1 == null || w2 == null) return false;
    if (w1 == w1.parent && w2 == w2.parent) {
        // top level window
        return w1.name == w2.name;
    } else if (w1.parent == w2.parent) {
        // frame
        return w1.name == w2.name;
    } else {
        return false;
    }
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
    this.recorder.refIdSetter = this.refIdSetter;
    this.recorder.workspace = this.workspace;
    this.recorder.observers.push(this);
    this.recorder.registerListeners();
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

Editor.prototype.updateTestSpeed = function(value){
    this.testRunner.updateInterval(value);
};

Editor.prototype.toggleRecordButton = function(){
    var recordToolbarButton = document.getElementById("record-button");
        var broadcaster = document.getElementById("isRecording");

    if(!recordToolbarButton.getAttribute("checked")){
        recordToolbarButton.setAttribute("checked", "true");
        broadcaster.setAttribute("disabled", "true");
        document.getElementById("editorTabs").selectedItem = document.getElementById("recordTab");
        this.startRecord();
        this.recordingEnabled = true;
    }else{
        recordToolbarButton.removeAttribute("checked");
        broadcaster.setAttribute("disabled", "false");
        this.endRecord();
        this.recordingEnabled = false;
    }
};

Editor.prototype.startRecord = function() {
    this.recorder.registerListeners();
    this.populateWindowUrl();
    logger.info("Start recording...");
};

Editor.prototype.endRecord = function(){
    try {
        logger.info("Stop recording...");
        this.recorder.unregisterListeners();
        this.decorator.cleanShowNode();
        this.switchToCustomizeTab();
        var xml = DEFAULT_XML;

        this.buildCustomizeTree(xml);
        var uiTypes = this.builder.getAvailableUiTypes();
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("uiType")),
                XulUtils.toXPCOMArray(uiTypes));

        this.recorder.generateSource();
        this.workspace.clear();
        this.recorder.clearMost();
        
        var app = this.recorder.app;
        if (app != null) {
            browserBot.clear();
            this.cmdView.clearAll();
            this.testRunner.prepareFor(app);
            var commandList = this.testRunner.commandList;
            this.cmdView.setTestCommands(commandList);
        }
    } catch(error) {
        logger.error("Error end record processing:\n" + describeErrorStack(error));
    }
};

Editor.prototype.toggleStopButton = function() {
    try {
        var stopButton = document.getElementById("stop-button");
        var runButton = document.getElementById("run-button");
        runButton.removeAttribute("checked");
        runButton.setAttribute("class", "run");
        this.testRunner.restart();
    } catch(error) {
        logger.error("Error:\n" + describeErrorStack(error));
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

Editor.prototype.validateOneUiModule = function(uim){

    var glf = Preferences.getPref("extensions.teide.grouplocating");
    if(glf == undefined){
        glf = true;
    }

    if(glf){
        var msg;
        try{
            var result = uim.validate(this.recorder.app.uiAlg);
            msg = result.toString();
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

Editor.prototype.validateUI = function(){

    var glf = Preferences.getPref("extensions.teide.grouplocating");
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

    sourceTextNode.value = uiModel;
};

Editor.prototype.stepButton = function(){
    if(this.recorder.app != null && (!this.recorder.app.isEmpty())){
        logger.info("Stepping recorded tests...");
        this.testRunner.step();
    }else{
        logger.warn("There is no recorded test to step.");
    }
//    document.getElementById("editorTabs").selectedItem = document.getElementById("exportToWindowTab");
};

Editor.prototype.runButton = function(){
    var runButton = document.getElementById("run-button");
    var checked = runButton.getAttribute("checked");
    if(!checked){
        runButton.setAttribute("checked", "true");
        runButton.setAttribute("class", "run");
        if (this.recorder.app != null && (!this.recorder.app.isEmpty())) {
            logger.info("Running tests...");
            this.testRunner.start();
            this.testRunner.run();
        } else {
            logger.warn("There is no test to run.");
            runButton.removeAttribute("checked");
        }
    } else {
        var clazz = runButton.getAttribute("class");
        if (clazz == "run") {
            logger.info("Pause tests...");
            runButton.setAttribute("class", "pause");
            this.testRunner.pause();
        } else {
            runButton.setAttribute("class", "run");
            if (this.recorder.app != null && (!this.recorder.app.isEmpty())) {
                logger.info("Running tests...");
                this.testRunner.start();
                this.testRunner.run();
            } else {
                logger.warn("There is no test to run.");
            }
        }

    }
};

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
//    this.clearSourceTabContent();
    this.clearExportToWindowTabContent();
    this.logView.clear();
    this.clearCustomizeTabContext();
    this.decorator.cleanShowNode();
    this.clearMessageBox();
    this.workspace.clear();
    this.cleanCommandView();
//    this.command.clearCache();
    tellurium.clearCache();
    this.cleanupAutoComplete();   
    document.getElementById("windowURL").value = null;
    browserBot.clear();
};

Editor.prototype.clearCustomizeUiObject = function(){
    document.getElementById("uid").value = "";
    document.getElementById("uiType").value = "";
    document.getElementById("uid").setAttribute("disabled", "true");
    document.getElementById("uiType").setAttribute("disabled", "true");
    document.getElementById("group_Check_Box").disabled = true;
    this.buildUiAttributeTree(DEFAULT_ATTRIBUTES_XML);
};

Editor.prototype.clearCustomizeTabContext = function(){
    this.buildCustomizeTree(DEFAULT_XML);
    this.clearCustomizeUiObject();
};

Editor.prototype.switchToSourceTab = function(){
//    document.getElementById("editorTabs").selectedItem = document.getElementById("sourceTab");
    document.getElementById("editorTabs").selectedItem = document.getElementById("exportToWindowTab");
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

Editor.prototype.getCmdType = function(name){
    var cmd = this.testRunner.cmdExecutor.getCommand(name);
    return cmd.type;
};
Editor.prototype.getCommandDef = function(name){
    return this.testRunner.cmdExecutor.getCommand(name);
};

Editor.prototype.refreshCommandList = function(){
    try {
        var index = this.cmdTree.currentIndex;
        var cmd =  this.cmdView.getRecordByIndex(index);
        this.currentSelectedCommand = cmd;
        if(cmd != null){
            var cmdName = document.getElementById("updateCommandName");
            var cmdUid = document.getElementById("updateCommandUID");
            var cmdValue = document.getElementById("updateCommandValue");
            cmdName.disabled = false;
            cmdUid.disabled = false;
            cmdValue.disabled = false;
            cmdName.value = cmd.name;
            Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("updateCommandName")),
                                                          XulUtils.toXPCOMArray(this.commandList));
            var cmdDef = this.getCommandDef(cmd.name);
            this.checkVariableAssignButtonFor(cmdDef.returnType);

            var type = cmdDef.type;
            if(cmd.target != null && cmd.target != undefined){
                cmdUid.value = cmd.strTarget();
                if (type != CommandType.ASSERTION) {
                    var uids = this.recorder.app.getUids(cmd.target);
                    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("updateCommandUID")),
                            XulUtils.toXPCOMArray(uids));
                }
            }else{
                cmdUid.value = '';
                Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["updateCommandUID"]));
            }
            if(cmd.value != null && cmd.value != undefined){
                cmdValue.value = cmd.strValue();
            }else{
                cmdValue.value = "";
            }
        }

    } catch(error) {
        logger.error("Error processing selected command:\n" + describeErrorStack(error));
    }
};

Editor.prototype.selectUiCommand = function(){
    try {
        var index = this.cmdTree.currentIndex;
        var cmd =  this.cmdView.getRecordByIndex(index);
        this.currentSelectedCommand = cmd;
        if(cmd != null){
            var cmdName = document.getElementById("updateCommandName");
            var cmdTarget = document.getElementById("updateCommandUID");
            var cmdValue = document.getElementById("updateCommandValue");
            cmdName.disabled = false;
            cmdTarget.disabled = false;
            cmdValue.disabled = false;
            cmdName.value = cmd.name;
            Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("updateCommandName")),
                                                          XulUtils.toXPCOMArray(this.commandList));
            var cmdDef = this.getCommandDef(cmd.name);
            this.checkVariableAssignButtonFor(cmdDef.returnType);

            var type = cmdDef.type;   
            if(cmd.target != null && cmd.target != undefined){
                cmdTarget.value = cmd.strTarget();
                if (type != CommandType.ASSERTION) {
                    var uids = this.recorder.app.getUids(cmd.target);
                    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("updateCommandUID")),
                            XulUtils.toXPCOMArray(uids));
                    var uim = this.recorder.app.getUiModule(cmd.target);
                    var xml = uim.buildXML();
                    this.buildCustomizeTree(xml);
                } else {
                    Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["updateCommandUID"]));
                    this.buildCustomizeTree(DEFAULT_XML);
                }

            }else{
                cmdTarget.value = '';
                Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["updateCommandUID"]));
                this.buildCustomizeTree(DEFAULT_XML);
            }          
            this.clearCustomizeUiObject();
            if(cmd.value != null && cmd.value != undefined){
                cmdValue.value = cmd.strValue();  //cmd.value;
            }else{
                cmdValue.value = "";
            }
            if (cmd.returnValue != undefined) {
                var value = cmd.returnValue;
                if (typeof(value) == "object" && value["toString"] != undefined) {
                    value = value.toString();
                }
                document.getElementById("commandReturnResult").value = value;
            }else{
                document.getElementById("commandReturnResult").value = "";
            }
            if (cmd.returnVariable != undefined) {
                var variable = cmd.returnVariable;
                if (typeof(variable) == "object" && variable["toString"] != undefined) {
                    variable = variable.toString();
                }
                document.getElementById("returnValueVariable").value = variable;
            }else{
                document.getElementById("returnValueVariable").value = "";
            }
        }

    } catch(error) {
        logger.error("Error processing selected command:\n" + describeErrorStack(error));
     }
};

Editor.prototype.updateUiCommand = function(){
    if(this.currentSelectedCommand != null){
        logger.debug("Update command " + this.currentSelectedCommand.seq);
        var cmdName = document.getElementById("updateCommandName").value;
        var cmdTarget = document.getElementById("updateCommandUID").value;
        var cmdValue = document.getElementById("updateCommandValue").value;
        this.currentSelectedCommand.name = cmdName;
        var cmdDef = this.getCommandDef(cmdName);
        this.checkVariableAssignButtonFor(cmdDef.returnType);

        var type = cmdDef.type;
//        var type = this.getCmdType(cmdName);
        this.currentSelectedCommand.type = type;

        if(type == CommandType.ASSERTION){
            //value type could be overwritten
            this.currentSelectedCommand.valueType = ValueType.STRING;
            //target type could be overwritten
            this.currentSelectedCommand.targetType = TargetType.DATA;
        }else{
            this.currentSelectedCommand.targetType = TargetType.UID;
        }

        if(cmdTarget.trim().length == 0){
            this.currentSelectedCommand.target = null;
        }else{
            this.currentSelectedCommand.parseTarget(cmdTarget);
        }

        if(cmdValue.trim().length == 0){
            this.currentSelectedCommand.value = null;
        }else{
            this.currentSelectedCommand.parseValue(cmdValue);
        }

        this.recorder.app.updateCommand(this.currentSelectedCommand);

        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.recorder.app.toSource();
    }
};

Editor.prototype.removeUiCommand = function(){
	if(this.currentSelectedCommand != null){
        logger.debug("Remove command " + this.currentSelectedCommand.seq);
        this.recorder.app.deleteCommand(this.currentSelectedCommand);
        this.cmdView.deleteFromTestCommand(this.currentSelectedCommand);

        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.recorder.app.toSource();
        this.selectUiCommand();
    }
};

Editor.prototype.insertBeforeUiCommand = function(){
    try {
        var cmd = this.buildUiCommand();
        var index = this.cmdTree.currentIndex;
        var prevCmd = null;
        if(index != 0){
            prevCmd = this.cmdView.getRecordByIndex(index-1);
        }

        this.cmdView.insertCommand(index, cmd);
        this.recorder.app.insertCommand(prevCmd, cmd);
        
        //update commands in the app
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.recorder.app.toSource();
        this.selectUiCommand();
    }catch(error) {
        logger.error("Error insertBefore command:\n" + describeErrorStack(error));
    }
};

Editor.prototype.insertAfterUiCommand = function(){
    try {
        var cmd = this.buildUiCommand();
        var index = this.cmdTree.currentIndex;
        var prevCmd = null;
        if(index != 0){
            prevCmd = this.cmdView.getRecordByIndex(index);
        }

        this.cmdView.insertCommand(index+1, cmd);
        this.recorder.app.insertCommand(prevCmd, cmd);

        //update commands in the app
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.recorder.app.toSource();

        //also ensure the newly inserted row is the currentIndex
        this.cmdTree.currentIndex=index+1;

        //ensure the update propogates
        this.selectUiCommand();
    }catch(error) {
        logger.error("Error insertBefore command:\n" + describeErrorStack(error));
    }
};

Editor.prototype.buildUiCommand = function(){
    var name = document.getElementById("updateCommandName").value;
    var target = document.getElementById("updateCommandUID").value;
    var value = document.getElementById("updateCommandValue").value;

    if(target == undefined || target.trim().length == 0){
        target = null;
    }

    if(value == undefined || value.trim().length == 0){
        value = null;
    }

    var ref = null;
    var cmd = new UiCommand(name, ref, value, ValueType.STRING, target, this.workspace.sequence.next());

    var cmdDef = this.getCommandDef(name);
    cmd.type = cmdDef.type;
    cmd.returnType = cmdDef.returnType;
//    if(cmd.type == CommandType.ACTION || cmd.type == CommandType.ACCESSOR){
    if(cmd.type == CommandType.HasUid){
        if(target != null){
            this.recorder.app.getRefUidMapFor(target);
            cmd.ref = this.recorder.app.findRefFromUid(target);
            cmd.targetType = TargetType.UID;
        }else{
            logger.warn("Target is null for command " + name);
        }
    }else{
        cmd.targetType = TargetType.DATA;

        //overwrite target and value for Assertions
        cmd.parseTarget(target);
        cmd.parseValue(value);
    }

    return cmd;
};

Editor.prototype.assignCommandResultToVariable= function(){
    if(this.currentSelectedCommand != null){
        logger.debug("Assigned return value to variable for command " + this.currentSelectedCommand.seq);
        var variableName = document.getElementById("returnValueVariable").value;
        if(variableName != undefined && variableName.trim().length > 0){
            this.currentSelectedCommand.returnVariable = variableName.trim();
        }
    }
};

Editor.prototype.enableVariableAssign = function(){
    document.getElementById("assignReturnVariableButton").disabled = false;
};

Editor.prototype.disableVariableAssign = function(){
     document.getElementById("assignReturnVariableButton").disabled = true;
};

Editor.prototype.checkVariableAssignButtonFor = function(returnType){
    if(returnType == ReturnType.VOID){
        this.disableVariableAssign();
    }else{
        this.enableVariableAssign();
    }
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
    var uitypes = this.builder.getAvailableUiTypes();
    logger.debug("Get registered UI types: " + uitypes.join(", "));
    Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.editor.getAutoCompleteSearchParam("uiType")),
                                                      XulUtils.toXPCOMArray(uitypes));
};

Editor.prototype.processCustomizeEvent = function(event){
    this.currentUid = event.target.getAttribute("uid");
    var uiObject = this.recorder.app.walkToUiObject(this.currentUid);

    if(uiObject != null){
        this.fillUiObjectFields(uiObject);
        this.enableUiObjectFields();
        this.populateUiTypeAutoComplete();
    }else{
        logger.warn("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.updateUiModuleName = function(uid) {
//    var uids = this.command.getUids(uid);
    var uids = tellurium.getUids(uid);
    if (uids != null && uids.length > 0) {
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("commandUID")),
                XulUtils.toXPCOMArray(uids));

    }
};

Editor.prototype.cleanCommandView = function(){
    document.getElementById("updateCommandName").value = "";
    document.getElementById("updateCommandUID").value = "";
    document.getElementById("updateCommandValue").value = "";
    document.getElementById("commandReturnResult").value = "";
    document.getElementById("returnValueVariable").value = "";
    Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["updateCommandName"]));
    Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["updateCommandUID"]));

    this.cmdView.clearAll();
};

Editor.prototype.updateUiCommandName = function(value){

};

Editor.prototype.updateUiUID = function(value){

};

Editor.prototype.processCheckEvent = function(event){

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

    var xml = uiObject.buildAttributeXml();
    this.buildUiAttributeTree(xml);
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
    var uiObject = this.recorder.app.walkToUiObject(this.currentUid);
    if(uiObject != null){
        if(uiObject.domRef != null){
            this.decorator.showNode(uiObject.domRef);
        }else{
            logger.error("UI Object " + this.currentUid + " does not point to a Dom Node");
        }
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.updateUiObject = function(){
    var uiObject = this.recorder.app.walkToUiObject(this.currentUid);
    if(uiObject != null){
        var uim = uiObject.uim;
        var oldUimId = uim.id;
        logger.debug("Update UI object " + this.currentUid);

        var nuid = document.getElementById("uid").value;
        var isUidChanged = (uiObject.uid != nuid);
        
        //update UID
        uiObject.uid = nuid;

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
        if(isUidChanged){
            uim.postUidChange();
            if(uiObject.parent == null){
               uim.id = uiObject.uid;
               this.recorder.app.updateUiModule(oldUimId, uim);
            }
        }

        this.validateOneUiModule(uim);
        var xml = uim.buildXML();

        this.buildCustomizeTree(xml);
        var uiTypes = this.builder.getAvailableUiTypes();
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("uiType")),
                XulUtils.toXPCOMArray(uiTypes));
        if(isUidChanged){
            this.currentUid = uiObject.fullUid();
            var rum = this.recorder.app.getRefUidMapFor(this.currentUid);
            this.recorder.app.updateCommandList();
            this.cmdView.updateCommands(rum);
            this.refreshCommandList();
            this.testRunner.updateUiModule(this.currentUid);
        }

        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = this.recorder.app.toSource();

    } else {
        logger.error("Cannot find UI object " + this.currentUid);
    }
};

Editor.prototype.exportGroovyDsl = function(){
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toGroovyDsl();

            var dir = Preferences.getPref("extensions.teide.exportdirectory");
            if (dir == undefined || dir == null) {
                if (this.os == "Windows") {
                    dir = Preferences.DEFAULT_OPTIONS.defaultWinDirectory;
                } else {
                    dir = Preferences.DEFAULT_OPTIONS.defaultDirectory;
                }
            }

            FileUtils.saveAs(dir, txt);
            logger.debug("Groovy DSL script is exported to directory " + dir);
        }
    } catch(error) {
        logger.error("Error exporting Groovy DSL script:\n" + describeErrorStack(error));
    }
};

Editor.prototype.exportUiModule = function() {
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toUiModule();

            var dir = Preferences.getPref("extensions.teide.exportdirectory");
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
    } catch(error) {
        logger.error("Error exporting UI Module:\n" + describeErrorStack(error));
    }
};

Editor.prototype.exportJavaCode = function(){
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toJavaCode();

            var dir = Preferences.getPref("extensions.teide.exportdirectory");
            if (dir == undefined || dir == null) {
                if (this.os == "Windows") {
                    dir = Preferences.DEFAULT_OPTIONS.defaultWinDirectory;
                } else {
                    dir = Preferences.DEFAULT_OPTIONS.defaultDirectory;
                }
            }

            FileUtils.saveAs(dir, txt);
            logger.debug("Java test file is exported to directory " + dir);
        }
    } catch(error) {
        logger.error("Error exporting Java test file:\n" + describeErrorStack(error));
    }
};

Editor.prototype.clipboardGroovyDsl = function(){
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toGroovyDsl();

            if(txt != null && txt.length > 0){
                this.copyToClipboard(txt);
                logger.debug("Groovy DSL is copied to clipboard");
            }else{
                logger.warn("Groovy DSL is empty");
            }
        }
    } catch(error) {
        logger.error("Error copying Groovy DSL script:\n" + describeErrorStack(error));
    }
};

Editor.prototype.clipboardUiModule = function() {
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toUiModule();

             if(txt != null && txt.length > 0){
                this.copyToClipboard(txt);
                logger.debug("UI Module is copied to clipboard");
            }else{
                logger.warn("Ui Module is empty");
            }
        }
    } catch(error) {
        logger.error("Error copying UI Module:\n" + describeErrorStack(error));
    }
};

Editor.prototype.clipboardJavaCode = function(){
    try {
        if (this.recorder.app != null && this.recorder.app.notEmpty()) {
            var txt = this.recorder.app.toJavaCode();

            if(txt != null && txt.length > 0){
                this.copyToClipboard(txt);
                logger.debug("Java test file is copied to clipboard");
            }else{
                logger.warn("Java test file is empty");
            }
        }
    } catch(error) {
        logger.error("Error copying Java test file:\n" + describeErrorStack(error));
    }
};

Editor.prototype.copyToClipboard = function(copytext) {
    var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
    if (!str) return false;
    str.data = copytext;

    var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);    
    if (!trans) return false;

    trans.addDataFlavor("text/unicode");
    trans.setTransferData("text/unicode", str, copytext.length * 2);

    var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
    if (!clip) return false;

    clip.emptyClipboard(clip.kGlobalClipboard);
    clip.setData(trans, null, clip.kGlobalClipboard);
};

Editor.prototype.pasteFromClipboard = function() {
    var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
    if (!clip) return false;

    var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
     if (!trans) return false;
    trans.addDataFlavor("text/unicode");
    clip.getData(trans, clip.kGlobalClipboard);

    var str = new Object();
    var strLength = new Object();

    var pastetext = "";
    trans.getTransferData("text/unicode", str, strLength);
    if (str) str = str.value.QueryInterface(Components.interfaces.nsISupportsString);
    if (str) pastetext = str.data.substring(0, strLength.value / 2);

    return pastetext;
};

Editor.prototype.updateOptions = function(){
    window.openDialog("chrome://source/content/preferences.xul", "options", "chrome,modal,resizable", this.os);
    var jslog = Preferences.getPref("extensions.teide.jslog");
    if(jslog ==undefined){
        jslog = true;
    }
    logger.jslog = jslog;

    var elem = window.frames["logViewFrame"].document.getElementById("logging-console");
    if (elem != null) {
        var logWrap = Preferences.getPref("extensions.teide.logwrap");
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

function TestCmd(name, target, param){
    this.name = name;
    this.target = target;
    this.param = param;
    this.result = null;
}
