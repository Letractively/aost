
function Recorder(window) {
    this.window = window;

    this.frames = null;
    this.frameName = null;

    this.contentWindow = null;

    this.parentWindow = this.window.opener;
    this.builder = new Builder();
    this.decorator = new Decorator();
    this.listener = null;
    this.selectedElements = new Array();
    this.tagObjectArray = new Array();

    this.treeView = TreeView;
    this.tree = document.getElementById('recordTree');
    this.tree.view=this.treeView;

    this.recordCommandList = new Array();
    this.cmdListView = CommandList;
    this.cmdList = document.getElementById("commandListTree");
    this.cmdList.view = this.cmdListView;

    this.workspace = null;

    this.refIdSetter = null;

    this.app = new App();

    this.first = true;

//    this.observers = [];
//	this.attach();
//    this.registerUnloadListener();
}

Recorder.WINDOW_RECORDER_PROPERTY = "_Trump_IDE_Recorder";

Recorder.prototype.attachActionListeners = function(window){
    logger.debug("Attaching listeners for action...");
    var self = this;
    window.addEventListener("beforeunload",
            function(event) {
                try {
                    var recordToolbarButton = document.getElementById("record-button");
                    if (recordToolbarButton.getAttribute("checked")) {
                        self.recordCommand("waitForPageToLoad", null, 30000, ValueType.NUMBER);
                        var url = event.target.URL || event.target.baseURI;
                        logger.debug("Unloading Window " + url);
                        self.generateSource();
                    }
                } catch(error) {
                    logger.error("Error processing beforeunload event:\n" + describeErrorStack(error));
                }
            },
     false);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").live("change", {recorder: this}, this.typeListener);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").live("click", {recorder: this}, this.clickListener);
    teJQuery(window.document).find("input, a, p, link, textarea, button, table, tr, td, th, div, span, label").live("mousedown", {recorder: this}, this.rememberClickedListener);
    teJQuery(window.document).find("select, option").live("focus", {recorder: this}, this.selectFocusListener);
    teJQuery(window.document).find("select, option").live("mousedown", {recorder: this}, this.selectMousedownListener);
    teJQuery(window.document).find("select, option").live("change", {recorder: this}, this.selectListener);
};

Recorder.prototype.detachActionListeners = function(window){
    logger.debug("Detaching listeners for action...");

    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").die("change", this.typeListener);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").die("click", this.clickListener);
    teJQuery(window.document).find("input, a, p, link, textarea, button, table, tr, td, th, div, span, label").die("mousedown", this.rememberClickedListener);
    teJQuery(window.document).find("select, option").die("focus", this.selectFocusListener);
    teJQuery(window.document).find("select, option").die("mousedown", this.selectMousedownListener);
    teJQuery(window.document).find("select, option").die("change", this.selectListener);
};

Recorder.prototype.attachSelectListeners = function(window){
    logger.debug("Attaching listeners for selection");
    teJQuery(window.document.body).bind("click", {recorder: this}, this.uiSelectListener);
};

Recorder.prototype.detachSelectListeners = function(window){
    logger.debug("Detaching listeners for selection");
    teJQuery(window.document.body).unbind("click", this.uiSelectListener);
};

Recorder.prototype.registerListeners = function(){
    this.registerForAllWindows();
};

Recorder.prototype.registerForWindow = function(window) {
    this.attachActionListeners(window);

    var frames = window.frames;
    for (var j = 0; j < frames.length; j++) {
        this.attachActionListeners(frames[j]);
    }
};

Recorder.prototype.unregisterForWindow = function(window){
    this.removeBackgroundForSelectedNodes();

    this.removeOutlineForSelectedNodes();

    this.deregisterForAllWindows();

    if (window) {
        this.detachActionListeners(window);
    }

    var frames =  window.frames;
    if (frames && frames.length) {
        for (var j = 0; j < frames.length; j++) {
            this.detachActionListeners(frames[j]);
        }
    }
};

Recorder.prototype.registerForAllWindows = function() {
    var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
    var e = wm.getEnumerator("navigator:browser");
    var window;
    while (e.hasMoreElements()) {
        window = e.getNext();
        logger.debug("window=" + window);
        var browsers = window.getBrowser().browsers;
        for (var i = 0; i < browsers.length; i++) {
            logger.debug("browser=" + browsers[i]);
            this.attachActionListeners(browsers[i].contentWindow);

            var frames = browsers[i].contentWindow.frames;
            for (var j = 0; j < frames.length; j++) {
                this.attachActionListeners(frames[j]);
            }
        }
    }
};

Recorder.prototype.deregisterForAllWindows = function() {
    var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
    var e = wm.getEnumerator("navigator:browser");
    var window;
    while (e.hasMoreElements()) {
        window = e.getNext();
        logger.debug("window=" + window);
        var browsers = window.getBrowser().browsers;
        for (var i = 0; i < browsers.length; i++) {
            logger.debug("browser=" + browsers[i]);
            this.detachActionListeners(browsers[i].contentWindow);

            var frames = browsers[i].contentWindow.frames;
            for (var j = 0; j < frames.length; j++) {
                this.detachActionListeners(frames[j]);
             }
        }
    }
};

Recorder.prototype.unregisterListeners = function() {
    this.removeBackgroundForSelectedNodes();

    this.removeOutlineForSelectedNodes();

    this.deregisterForAllWindows();
};

Recorder.prototype.stopRecording = function(){
    
};

Recorder.prototype.showSelectedNode = function(){

    this.removeOutlineForSelectedNodes();
    
    var node = this.selectedElements[this.tree.currentIndex];
    this.decorator.addOutline(node);

};

Recorder.prototype.removeBackgroundForSelectedNodes = function(){
    for(var i=0; i< this.selectedElements.length ; ++i){
        this.decorator.removeBackground(this.selectedElements[i]);
    }
};

Recorder.prototype.removeOutlineForSelectedNodes = function(){
    for(var i=0; i< this.selectedElements.length ; ++i){
        this.decorator.removeOutline(this.selectedElements[i]);
    }
};

Recorder.prototype.clearMost = function(){
    this.removeOutlineForSelectedNodes();
    this.removeBackgroundForSelectedNodes();

    this.selectedElements = new Array();
    this.tagObjectArray = new Array();
    this.recordCommandList = new Array();
    this.cmdListView.clearAll();
    this.treeView.clearAll();
    this.first = true;
};

Recorder.prototype.clearAll = function(){
    this.removeOutlineForSelectedNodes();
    this.removeBackgroundForSelectedNodes();
    
    this.selectedElements = new Array();
    this.tagObjectArray = new Array();
    this.recordCommandList = new Array();
    this.cmdListView.clearAll();
    this.treeView.clearAll();
    this.app.clearAll();
    this.first = true;
};

Recorder.prototype.recordDomNode = function (element){
    var refId;
    try {
        //check if the element is already selected
        var index = this.selectedElements.indexOf(element);
        if (index == -1) {
            this.decorator.addBackground(element);
            this.selectedElements.push(element);

            refId = this.refIdSetter.getRefId();
            // "T" + this.timestamp + "S" + this.sequence.next();
            var tagObject = this.builder.createTagObject(element, refId, this.frameName);
            teJQuery(element).data("sid", refId);
            teJQuery(element).data("count", "0");
            this.tagObjectArray.push(tagObject);

            this.treeView.setTagObjects(this.tagObjectArray);
            this.treeView.rowInserted();
            this.workspace.addNode(element, this.frameName, refId);
        }else {
            refId = teJQuery(element).data("sid");
            var count = teJQuery(element).data("count");
            if (count == "0") {
                //we are assuming to remove the element
                this.decorator.removeBackground(element);
                this.selectedElements.splice(index, 1);
                this.tagObjectArray.splice(index, 1);
                this.treeView.deleteRow(index);
                teJQuery(element).removeData("sid");
                teJQuery(element).removeData("count");
            }
        }
    } catch(error) {
        logger.error("Record node " + element.tagName + " failed:\n" + describeErrorStack(error));
    }

    return refId;
};

Recorder.prototype.updateWindowUrl = function(element){
    var baseUrl = document.getElementById("windowURL").value;
    var actualUrl = element.ownerDocument.location.href;
    if (baseUrl.trim().length == 0 || baseUrl != actualUrl) {
        document.getElementById("windowURL").value = actualUrl;
    }
};

Recorder.prototype.recordCommand = function(name, element, value, valueType){
    logger.debug("Recording command (name: " + name + ", element: " + element + ", value: " + value + ", type: " + valueType + ")");
    if (element != null && element != undefined) {
        if (this.first) {
            this.workspace.addCommand("open", null, element.ownerDocument.location.href, ValueType.STRING);
            var ocmd = new TestCmd("open", null, element.ownerDocument.location.href);
            this.recordCommandList.push(ocmd);
            this.first = false;
        }
        var uid = this.recordDomNode(element);
        var count = teJQuery(element).data("count");
        teJQuery(element).data("count", count + 1);
        var result = this.workspace.addCommand(name, uid, value, valueType);
        if (result) {
            var cmd = new TestCmd(name, uid, value);
            this.recordCommandList.push(cmd);
            this.cmdListView.setTestCommands(this.recordCommandList);
            this.cmdListView.rowInserted();
            this.updateWindowUrl(element);
        }
    } else {
        var result = this.workspace.addCommand(name, null, value, valueType);
        if (result) {
            var cmd = new TestCmd(name, null, value);
            this.recordCommandList.push(cmd);
            this.cmdListView.setTestCommands(this.recordCommandList);
            this.cmdListView.rowInserted();
        }
    }
};

Recorder.prototype.findClickableElement = function(e) {
	if (!e.tagName) return null;
	var tagName = e.tagName.toLowerCase();
	var type = e.type;
	if (e.hasAttribute("onclick") || e.hasAttribute("href") || tagName == "button" ||
		(tagName == "input" &&
		 (type == "submit" || type == "button" || type == "image" || type == "radio" || type == "checkbox" || type == "reset"))) {
		return e;
	} else {
		if (e.parentNode != null) {
			return this.findClickableElement(e.parentNode);
		} else {
			return null;
		}
	}
};

Recorder.prototype.domModified = function() {
    if (this.delayedRecorder) {
        this.delayedRecorder.apply(this);
        this.delayedRecorder = null;
        if (this.domModifiedTimeout) {
            clearTimeout(this.domModifiedTimeout);
        }
    }
};

Recorder.prototype.callIfMeaningfulEvent = function(handler) {
    logger.debug("callIfMeaningfulEvent");
    this.delayedRecorder = handler;
    var self = this;
    this.domModifiedTimeout = setTimeout(function() {
//        logger.debug("clear event");
        self.delayedRecorder = null;
        self.domModifiedTimeout = null;
    }, 50);
};

Recorder.prototype.getTextReg = function(option) {
    var label = option.text.replace(/^ *(.*?) *$/, "$1");
    if (label.match(/\xA0/)) { // if the text contains &nbsp;
        return "label=regexp:" + label.replace(/[\(\)\[\]\\\^\$\*\+\?\.\|\{\}]/g, function(str) {return '\\' + str})
                                      .replace(/\s+/g, function(str) {
                if (str.match(/\xA0/)) {
                    if (str.length > 1) {
                        return "\\s+";
                    } else {
                        return "\\s";
                    }
                } else {
                    return str;
                }
            });
    } else {
        return "label=" + label;
    }
};

Recorder.prototype.savePage = function(){
    if (this.workspace.uim != null || this.workspace.convertedCommandList.length > 0) {
        this.app.savePage(this.contentWindow, this.workspace.uim, this.workspace.dom, this.workspace.convertedCommandList);
    }
};

Recorder.prototype.reloadRecorder = function(){
    this.selectedElements = new Array();
    this.tagObjectArray = new Array();
    this.recordCommandList = new Array();
    this.workspace.clear();
};

Recorder.prototype.generateSource = function(){
    try {
        logger.debug("Generating UI module before page load...");
        this.workspace.generate();
        this.savePage();
        this.reloadRecorder();
        var src = this.app.toSource();
        var sourceTextNode = document.getElementById("exportSource");
        sourceTextNode.value = src;
        logger.info("UI Module and commands are generated, please see them on the UI module source view.");
    } catch(error) {
        logger.error("Error generating UI module and commands:\n" + describeErrorStack(error));
    }
};

Recorder.eventHandlers = {};
