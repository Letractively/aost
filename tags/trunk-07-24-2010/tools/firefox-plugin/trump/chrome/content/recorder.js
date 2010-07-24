
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

    this.sequence = new Identifier();
}

Recorder.WINDOW_RECORDER_PROPERTY = "_Trump_IDE_Recorder";

Recorder.prototype.registerListener = function(){
    this.registerClickListener();
};

Recorder.prototype.unregisterListener = function(){
    this.unregisterClickListener();
};

Recorder.prototype.updateListenerForWindow = function(url){
    this.unregisterListener();
    this.reRegisterClickListener(url);
};

Recorder.prototype.record = function(command, target, value){
    var self = this;
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
        logger.debug("Recording: [command: " + command + ", target: " + uid + ", value: " + value);
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

Recorder.prototype.typeListener = function(event){
    var state = document.getElementById("record-button").getAttribute("class");
    if(state == RecordState.PAUSE)
        event.preventDefault();
    var tagName = event.target.tagName.toLowerCase();
    var type = event.target.type;
    if (('input' == tagName && ('text' == type || 'password' == type || 'file' == type)) ||
            'textarea' == tagName) {
        this.record("type", event.target, event.target.value);
    }
};

Recorder.prototype.focusListener = function(event){
    var state = document.getElementById("record-button").getAttribute("class");
    if(state == RecordState.PAUSE)
        event.preventDefault();
    var tagName = event.target.nodeName.toLowerCase();
    if('select' ==tagName){
        var options = event.target.options;
        for(var i=0; i<options.length; i++){
            if(options[i].selected){
                this.record("select", event.target, options[i]);
                break;
            }
        }
    }
};

Recorder.prototype.clickListener = function(event){
    var state = document.getElementById("record-button").getAttribute("class");
    if(state == RecordState.PAUSE)
        event.preventDefault();
/*    if (event.button == 0) {
//        var clickable = this.findClickableElement(event);
        var clickable = this.getClickable(event);
        if (clickable) {
            this.record("click", event.target, '');
        }
    }else{
        this.record("click", event.target, '');
    }*/

    this.record("click", event.target, '');
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

Recorder.prototype.reRegisterClickListener = function(url) {
    var self = this;
    this.listener =
            function(event) {
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

    this.updateWindowAndRegisterClickListener(url);

};

Recorder.prototype.updateWindowAndRegisterClickListener = function(url){
    var cWindows = getAvailableContentDocuments(Components.interfaces.nsIDocShellTreeItem.typeChrome);
//    var cWindows = getAvailableContentDocuments(Components.interfaces.nsIDocShellTreeItem.typeContent);
    var cWin = null;
    if(cWindows != null && cWindows.length > 0){
        for(var i=0; i<cWindows.length; i++){
            if(url == cWindows[i].location.href){
                cWin = cWindows[i];
                break;
            }
        }
    }

    if (cWin != null) {
        this.contentWindow = cWin;
        this.contentWindow.document.body.addEventListener("click", this.listener, false);

        if(cWin.frames){
            this.frames = cWin.frames;
            if (this.frames && this.frames.length) {
                for (var j = 0; j < this.frames.length; j++) {
                    var frame = this.frames[j];
                    frame.document.body.addEventListener("click", this.listener, false);
                    frame.addEventListener("focus", this.frameFocusListener, false);
                    frame.addEventListener("blur", this.frameBlurListener, false);
                }
            }
        }
/*        var browser = win.getBrowser();

        if (browser && browser.contentWindow && browser.contentWindow.document) {
            this.contentWindow = browser.contentWindow;
            this.contentWindow.document.body.addEventListener("click", this.listener, false);
        }

        if (browser && browser.contentWindow && browser.contentWindow.frames) {
            this.frames = browser.contentWindow.frames;
            if (this.frames && this.frames.length) {
                for (var j = 0; j < this.frames.length; j++) {
                    var frame = this.frames[j];
                    frame.document.body.addEventListener("click", this.listener, false);
                    frame.addEventListener("focus", this.frameFocusListener, false);
                    frame.addEventListener("blur", this.frameBlurListener, false);
                }
            }
        }*/
    } else {
        logger.error("Invalid Window URL: " + url);
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

Recorder.prototype.findAllWindows = function() {
	var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
	var e = wm.getEnumerator("navigator:browser");
    var out = [];
	while (e.hasMoreElements()) {
        out.push(e.getNext());
	}

    return out;
};

Recorder.prototype.findAllWindowUrls = function(){
    var contentWindows = [];
    var wins = this.findAllWindows();
    for(var i=0; i<wins.length; i++){
        var cwins = this.findAllTabs(wins[i]);
        contentWindows.concat(cwins);
    }
    var urls = [];
    for(var i=0; i<contentWindows.length; i++){
        urls.push(contentWindows[i].location.href);
    }

    return urls;
};

Recorder.prototype.findAllTabs = function(window) {
	var browsers = window.getBrowser().browsers;
    var out = [];
	for (var i = 0; i < browsers.length; i++) {
		out.push(browsers[i].contentWindow);
	}

    return out;
};