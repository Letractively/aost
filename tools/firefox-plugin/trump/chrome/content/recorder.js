
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

    this.workspace = null;

    this.sequence = new Identifier();

    this.self = this;
//    this.observers = [];
//	this.attach();
//    this.registerUnloadListener();
}

Recorder.WINDOW_RECORDER_PROPERTY = "_Trump_IDE_Recorder";

Recorder.prototype.registerListeners = function(){
    var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
        .getService(Components.interfaces.nsIWindowMediator)
          .getMostRecentWindow("navigator:browser");

    var browser = win.getBrowser();

    if(browser && browser.contentWindow && browser.contentWindow.document){
        this.contentWindow = browser.contentWindow;
        teJQuery(this.contentWindow.document.body).bind("click", {recorder: this}, this.UiSelectListener);
        teJQuery(this.contentWindow).bind("beforeunload", {recorder: this}, this.onUnloadDocumentListener);
    }

    if(browser && browser.contentWindow && browser.contentWindow.frames){
        this.frames = browser.contentWindow.frames;
        if (this.frames && this.frames.length) {
            for (var j = 0; j < this.frames.length; j++) {
                var frame = this.frames[j] ;
                teJQuery(frame.document.body).bind("click", {recorder: this}, this.UiSelectListener);
                teJQuery(frame.document).bind("beforeunload", {recorder: this}, this.onUnloadDocumentListener);
                teJQuery(frame.document).bind("focus", {recorder: this}, this.frameFocusListener);
                teJQuery(frame.document).bind("blur", {recorder: this}, this.frameBlurListener);
            }
        }
    }
};

Recorder.prototype.unregisterListeners = function(){
    this.removeBackgroundForSelectedNodes();

    this.removeOutlineForSelectedNodes();

    if(this.contentWindow){
        teJQuery(this.contentWindow.document.body).unbind("click", this.UiSelectListener);
    }

    if (this.frames && this.frames.length) {
        for (var j = 0; j < this.frames.length; j++) {
            teJQuery(his.frames[j].document.body).unbind("click", this.UiSelectListener);
        }
    }
};

Recorder.prototype.registerListener = function(){
    this.registerClickListener();
};

Recorder.prototype.unregisterListener = function(){
    this.unregisterClickListener();
};

Recorder.prototype.updateListenerForWindow = function(url){
    this.unregisterListener();
//    this.reRegisterClickListener(url);
};

Recorder.prototype.registerClickListener = function() {
    var self = this;
    this.listener =
            function(event) {
                logger.debug('listener: event.type=' + event.type + ', target=' + event.target);
                var state = document.getElementById("record-button").getAttribute("class");
                if(state == RecordState.PAUSE)
                    event.preventDefault();
                var element = event.target;
                //check if the element is already selected
                var index = self.selectedElements.indexOf(element);
                if (index == -1) {
                    self.decorator.addBackground(element);
                    self.selectedElements.push(element);

                    var uid = "trumpSelected" + self.sequence.next();
                    var tagObject = self.builder.createTagObject(element, uid, self.frameName);
                    teJQuery(element).data("sid", uid);
                    self.tagObjectArray.push(tagObject);

                    self.treeView.setTagObjects(self.tagObjectArray);
                    self.treeView.rowInserted();
                    this.workspace.addNode(element, self.frameName, uid);
                } else {
                    //we are assuming to remove the element
                    self.decorator.removeBackground(element);
                    self.selectedElements.splice(index, 1);
                    self.tagObjectArray.splice(index, 1);
                    self.treeView.deleteRow(index);
                    teJQuery(element).removeData("sid");
                }

                var baseUrl = document.getElementById("windowURL").value;
                var actualUrl = element.ownerDocument.location.href;
                if (baseUrl.trim().length == 0 || baseUrl != actualUrl) {
                    document.getElementById("windowURL").value = actualUrl;
                }
            };


    this.frameFocusListener =
            function(event) {
                event.preventDefault();
                //            logger.debug("Inside frameFocusListener() ..");
                if (self.frameName == null) {
                    //                logger.debug("frameName : " + event.target.name);
                    self.frameName = event.target.name;
                }
            };

    this.frameBlurListener = function(event) {
        event.preventDefault();
        self.frameName = null;
    };

    this.getWindowAndRegisterClickListener();

};

Recorder.prototype.getWindowAndRegisterClickListener = function(){
    var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
        .getService(Components.interfaces.nsIWindowMediator)
          .getMostRecentWindow("navigator:browser");

    var browser = win.getBrowser();

    if(browser && browser.contentWindow && browser.contentWindow.document){
        this.contentWindow = browser.contentWindow;
        this.contentWindow.document.body.addEventListener("click", this.listener, false);
        this.contentWindow.addEventListener("beforeunload", function(event) {
            var url = event.target.URL || event.target.baseURI;
            logger.debug("Unloading Window " + url);
        }, false);
//        this.contentWindow.document.body.addEventListener("click", this.clickListener, false);
//        this.contentWindow.document.body.addEventListener("change", this.typeListener, false);
    }

    if(browser && browser.contentWindow && browser.contentWindow.frames){
        this.frames = browser.contentWindow.frames;
        if (this.frames && this.frames.length) {
            for (var j = 0; j < this.frames.length; j++) {
                var frame = this.frames[j] ;
                frame.document.body.addEventListener("click", this.listener, false);
//                frame.document.body.addEventListener("click", this.clickListener, false);
//                frame.document.body.addEventListener("change", this.typeListener, false);
                frame.addEventListener("focus", this.frameFocusListener, false);
                frame.addEventListener("blur", this.frameBlurListener, false);
            }
        }
    }

};

Recorder.prototype.unregisterClickListener = function(){
    this.removeBackgroundForSelectedNodes();

    this.removeOutlineForSelectedNodes();

    if(this.contentWindow){
        this.contentWindow.document.body.removeEventListener("click", this.listener, false);
    }

    if (this.frames && this.frames.length) {
        for (var j = 0; j < this.frames.length; j++) {
            this.frames[j].document.body.removeEventListener("click", this.listener, false);
        }
    } 
    
    this.listener = null;
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

Recorder.prototype.clearAll = function(){
    this.removeOutlineForSelectedNodes();
    this.removeBackgroundForSelectedNodes();
    
    this.selectedElements = new Array();
    this.tagObjectArray = new Array();
    this.treeView.clearAll();    
};

Recorder.prototype.RecordDomNode = function (element){
    //check if the element is already selected
    var index = this.selectedElements.indexOf(element);
    if (index == -1) {
        this.decorator.addBackground(element);
        this.selectedElements.push(element);

        var uid = "trumpSelected" + this.sequence.next();
        var tagObject = this.builder.createTagObject(element, uid, this.frameName);
        teJQuery(element).data("sid", uid);
        this.tagObjectArray.push(tagObject);

        this.treeView.setTagObjects(this.tagObjectArray);
        this.treeView.rowInserted();
        this.workspace.addNode(element, this.frameName, uid);
    } else {
        //we are assuming to remove the element
        this.decorator.removeBackground(element);
        this.selectedElements.splice(index, 1);
        this.tagObjectArray.splice(index, 1);
        this.treeView.deleteRow(index);
        teJQuery(element).removeData("sid");
    }
};

Recorder.eventHandlers = {};
