
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
    this.tree.view = this.treeView;

    this.recordCommandList = new Array();
    this.cmdListView = CommandList;
    this.cmdList = document.getElementById("commandListTree");
    this.cmdList.view = this.cmdListView;

    this.workspace = null;

    this.refIdSetter = null;

    this.app = new App();

    this.first = true;

    this.observers = [];

    this.lastWindow = null;
}

Recorder.WINDOW_RECORDER_PROPERTY = "_Tellurium_IDE_Recorder";

Recorder.get = function(window) {
	return window[Recorder.WINDOW_RECORDER_PROPERTY] || null;
};

Recorder._isSameWindow = function(w1, w2) {
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

Recorder.addEventHandler = function(handlerName, eventName, handler, options) {
	handler.handlerName = handlerName;
	if (!options) options = {};
	var key = options['capture'] ? ('C_' + eventName) : eventName;
	if (!this.eventHandlers[key]) {
		this.eventHandlers[key] = [];
	}
	if (options['alwaysRecord']) handler.alwaysRecord = true;
	this.eventHandlers[key].push(handler);
};

Recorder.removeEventHandler = function(handlerName) {
	for (var eventKey in this.eventHandlers) {
		var handlers = this.eventHandlers[eventKey];
		for (var i = 0; i < handlers.length; i++) {
			if (handlers[i].handlerName == handlerName) {
				handlers.splice(i, 1);
				break;
			}
		}
	}
};

Recorder.decorateEventHandler = function(handlerName, eventName, decorator, options) {
   var eventKey = (options && options.capture)
       ? ('C_' + eventName) : eventName;
   var handlers = this.eventHandlers[eventKey];
   for (var i = 0; i < handlers.length; ++i) {
       if (handlers[i].handlerName == handlerName) {
           handlers[i] = decorator(handlers[i]);
           handlers[i].handlerName = handlerName;
       }
   }
};

Recorder.prototype.attachActionListeners = function(window){
    logger.debug("Attaching listeners for action...");
    var self = this;

    window.addEventListener("beforeunload",
            function(event) {
                try {
                    var recordToolbarButton = document.getElementById("record-button");
                    if (recordToolbarButton.getAttribute("checked")) {
                        var url = event.target.URL || event.target.baseURI;
//                        if (self.lastWindow == url) {
                        if(self.lastWindow == null || url == null){
                            return;
                        }
                        if(self.lastWindow.indexOf(url) != -1 || url.indexOf(self.lastWindow) != -1){    
                            logger.debug("Unloading Window " + url);
                            self.recordCommand("waitForPageToLoad", null, 30000, ValueType.NUMBER);
                            self.generateSource();
                        }else{
                            logger.debug("Window " + url + " is not the current window " + url);
                        }
                    }
                } catch(error) {
                    logger.error("Error processing beforeunload event:\n" + describeErrorStack(error));
                }
            },
            false);
    this.attach(window);
};

Recorder.prototype.detachActionListeners = function(window){
    logger.debug("Detaching listeners for action...");
    this.detach(window);
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

Recorder.prototype.registerForWindow = function(contentWindow) {
 	logger.debug("Register for window: " + contentWindow);
    this.attachActionListeners(contentWindow);

    var frames = contentWindow.frames;
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

Recorder.prototype.getWrappedWindow = function() {
    if (this.window.wrappedJSObject) {
        return this.window.wrappedJSObject;
    } else {
        return this.window;
    }
};

Recorder.prototype.getWrappedWindowFor = function(window) {
    if (window.wrappedJSObject) {
        return window.wrappedJSObject;
    } else {
        return window;
    }
};

Recorder.prototype.reattachWindowMethods = function(contentWindow) {
    var window = this.getWrappedWindowFor(contentWindow);
    logger.debug("reattach");
	if (!this.windowMethods) {
		this.originalOpen = window.open;
	}
	this.windowMethods = {};
	['alert', 'confirm', 'prompt', 'open'].forEach(function(method) {
			this.windowMethods[method] = window[method];
		}, this);
	var self = this;
	window.alert = function(alert) {
		self.windowMethods['alert'].call(self.window, alert);
        self.recordCommand("assertAlert", null, alert, ValueType.STRING);
	};
	window.confirm = function(message) {
		var result = self.windowMethods['confirm'].call(self.window, message);
		if (!result) {
            self.recordCommand("chooseCancelOnNextConfirmation", null, null, null);
		}
        self.recordCommand("assertConfirmation", null, message, ValueType.STRING);
		return result;
	};
	window.prompt = function(message) {
		var result = self.windowMethods['prompt'].call(self.window, message);

        self.recordCommand("answerOnNextPrompt", null, result, ValueType.STRING);
        self.recordCommand("assertPrompt", null, message, ValueType.STRING);
		return result;
	};
	window.open = function(url, windowName, windowFeatures, replaceFlag) {
		if (self.openCalled) {
			// stop the recursion called by modifyWindowToRecordPopUpDialogs
			return self.originalOpen.call(window, url, windowName, windowFeatures, replaceFlag);
		} else {
			self.openCalled = true;
			var result = self.windowMethods['open'].call(window, url, windowName, windowFeatures, replaceFlag);
			self.openCalled = false;
            if (result.wrappedJSObject) {
                result = result.wrappedJSObject;
            }
			setTimeout(Recorder.record, 0, self, 'waitForPopUp', windowName, "30000");
            for (var i = 0; i < self.observers.length; i++) {
                if (self.observers[i].isSidebar) {
                    self.observers[i].getUserLog().warn("Actions in popup window cannot be recorded with Selenium IDE in sidebar. Please open Selenium IDE as standalone window instead to record them.");
                }
            }
			return result;
		}
	};
};

Recorder.prototype.parseEventKey = function(eventKey) {
	if (eventKey.match(/^C_/)) {
		return { eventName: eventKey.substring(2), capture: true };
	} else {
		return { eventName: eventKey, capture: false };
	}
};

Recorder.prototype.attach = function(window) {
	logger.debug("Attaching event handlers");

	this.eventListeners = {};
//	this.reattachWindowMethods(window);
	var self = this;
	for (var eventKey in Recorder.eventHandlers) {
		var eventInfo = this.parseEventKey(eventKey);
		var eventName = eventInfo.eventName;
		var capture = eventInfo.capture;
		// create new function so that the variables have new scope.
		function register() {
			var handlers = Recorder.eventHandlers[eventKey];
//            logger.debug('eventName=' + eventName + ' / handlers.length=' + handlers.length);
			var listener = function(event) {
//				logger.debug('listener: event.type=' + event.type + ', target=' + event.target);
//				logger.debug('title=' + self.window.document.title);
				var recording = false;
//                logger.debug("Observers size: " + self.observers.length);
				for (var i = 0; i < self.observers.length; i++) {
					if (self.observers[i].recordingEnabled){
                       recording = true;
                    }
//                    logger.debug("recording: " + recording);
				}
				for (var i = 0; i < handlers.length; i++) {
					if (recording || handlers[i].alwaysRecord) {
						handlers[i].call(self, event);
					}
				}
			};
			window.document.addEventListener(eventName, listener, capture);
			this.eventListeners[eventKey] = listener;
		}
		register.call(this);
	}
};

Recorder.prototype.detach = function(window) {
	logger.debug("Detaching event handlers");

	for (var eventKey in this.eventListeners) {
		var eventInfo = this.parseEventKey(eventKey);
//		logger.debug("removeEventListener: " + eventInfo.eventName + ", " + eventKey + ", " + eventInfo.capture);
		window.document.removeEventListener(eventInfo.eventName, this.eventListeners[eventKey], eventInfo.capture);
	}
	delete this.eventListeners;
/*	for (var method in this.windowMethods) {
		this.getWrappedWindowFor(window)[method] = this.windowMethods[method];
	}*/
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
        if(count == undefined){
            logger.warn("Element count is undefined for command " + name);
            teJQuery(element).data("count", "1");
        }else{
            teJQuery(element).data("count", count + 1);
        }
        var result = this.workspace.addCommand(name, uid, value, valueType);
        if (result) {
            var cmd = new TestCmd(name, uid, value);
            this.recordCommandList.push(cmd);
            this.cmdListView.setTestCommands(this.recordCommandList);
            this.cmdListView.rowInserted();
            this.updateWindowUrl(element);
            this.lastWindow = element.ownerDocument.location.href;
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
        teJQuery(document).find("#exportSource").val(src);
//        var sourceTextNode = document.getElementById("exportSource");
//        sourceTextNode.value = src;
        logger.info("UI Module and commands are generated, please see them on the UI module source view.");
    } catch(error) {
        logger.error("Error generating UI module and commands:\n" + describeErrorStack(error));
    }
};

Recorder.eventHandlers = {};

Recorder.addEventHandler('type', 'change', function(event) {
		var tagName = event.target.tagName.toLowerCase();
		var type = event.target.type;
		if (('input' == tagName && ('text' == type || 'password' == type || 'file' == type)) ||
			'textarea' == tagName) {
            this.recordCommand("type", event.target, event.target.value, ValueType.STRING);
		}
	});

/*
 * select / addSelection / removeSelection
 */
Recorder.addEventHandler('selectFocus', 'focus', function(event) {
		var tagName = event.target.nodeName.toLowerCase();
		if ('select' == tagName && event.target.multiple) {
			logger.debug('remembering selections');
			var options = event.target.options;
			for (var i = 0; i < options.length; i++) {
				if (options[i]._wasSelected == null) {
					// is the focus was gained by mousedown event, _wasSelected would be already set
					options[i]._wasSelected = options[i].selected;
				}
			}
		}
	}, { capture: true });

Recorder.addEventHandler('selectMousedown', 'mousedown', function(event) {
		var tagName = event.target.nodeName.toLowerCase();
		if ('option' == tagName) {
			var parent = event.target.parentNode;
			if (parent.multiple) {
				logger.debug('remembering selections');
				var options = parent.options;
				for (var i = 0; i < options.length; i++) {
					options[i]._wasSelected = options[i].selected;
				}
			}
		}
	}, { capture: true });

Recorder.prototype.getOptionLocator = function(option) {
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

Recorder.addEventHandler('select', 'change', function(event) {
		var tagName = event.target.tagName.toLowerCase();
		if ('select' == tagName) {
			if (!event.target.multiple) {
                var option = event.target.options[event.target.selectedIndex];
				logger.debug('selectedIndex=' + event.target.selectedIndex);
                var optionLocator = this.getTextReg(option);
                var cmdName;
                var target;
                if(optionLocator.startsWith("label=")){
                    cmdName = "selectByLabel";
                    target = optionLocator.substring(6);
                }else if(optionLocator.startsWith("index=")){
                    cmdName = "selectByIndex";
                    target = optionLocator.substring(6);
                }else if(optionLocator.startsWith("value=")){
                    cmdName = "selectByValue";
                    target = optionLocator.substring(6);
                }else{
                    cmdName = "select";
                    target = optionLocator;
                }
                this.recordCommand(cmdName, event.target, target, ValueType.STRING);

			} else {
				logger.debug('change selection on select-multiple');
				var options = event.target.options;
				for (var i = 0; i < options.length; i++) {
					logger.debug('option=' + i + ', ' + options[i].selected);
					if (options[i]._wasSelected == null) {
						logger.warn('_wasSelected was not recorded');
					}
					if (options[i]._wasSelected != options[i].selected) {
                        var value = this.getOptionLocator(options[i]);
						if (options[i].selected) {
                            this.recordCommand("addSelection", event.target, value, ValueType.STRING);
						} else {
                            this.recordCommand("removeSelection", event.target, value, ValueType.STRING);
						}
						options[i]._wasSelected = options[i].selected;
					}
				}
			}
		}
	});

Recorder.addEventHandler('clickLocator', 'click', function(event) {
		if (event.button == 0) {
			var clickable = this.findClickableElement(event.target);
			if (clickable) {
                // prepend any required mouseovers. These are defined as
                // handlers that set the "mouseoverLocator" attribute of the
                // interacted element to the locator that is to be used for the
                // mouseover command. For example:
                //
                // Recorder.addEventHandler('mouseoverLocator', 'mouseover', function(event) {
                //     var target = event.target;
                //     if (target.id == 'mmlink0') {
                //         this.mouseoverLocator = 'img' + target._itemRef;
                //     }
                //     else if (target.id.match(/^mmlink\d+$/)) {
                //         this.mouseoverLocator = 'lnk' + target._itemRef;
                //     }
                // }, { alwaysRecord: true, capture: true });
                //
/*                if (this.mouseoverLocator) {
                    this.record('mouseOver', this.mouseoverLocator, '');

                    delete this.mouseoverLocator;
                }*/

                this.recordCommand("click", event.target, null, null);
            } else {
                var target = event.target;
                this.callIfMeaningfulEvent(function() {
                        this.recordCommand("click", target, null);
                    });
            }
		}
	}, { capture: true });

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

// remember clicked element to be used in CommandBuilders
Recorder.addEventHandler('rememberClickedElement', 'mousedown', function(event) {
		this.clickedElement = event.target;
    //TODO: need to remember the dom node, not the locator!!
        this.clickedElementLocators = event.target;
	}, { alwaysRecord: true, capture: true });

Recorder.addEventHandler('attrModified', 'DOMAttrModified', function(event) {
//        logger.debug('attrModified');
        this.domModified();
    }, {capture: true});

Recorder.addEventHandler('nodeInserted', 'DOMNodeInserted', function(event) {
//        logger.debug('nodeInserted');
        this.domModified();
    }, {capture: true});

Recorder.addEventHandler('nodeRemoved', 'DOMNodeRemoved', function(event) {
//        logger.debug('nodeRemoved');
        this.domModified();
    }, {capture: true});

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
//    logger.debug("callIfMeaningfulEvent");
    this.delayedRecorder = handler;
    var self = this;
    this.domModifiedTimeout = setTimeout(function() {
//            logger.debug("clear event");
            self.delayedRecorder = null;
            self.domModifiedTimeout = null;
        }, 50);
};


/*Recorder.prototype.typeListener = function(event) {
//    var recorder = event.data.recorder;
//    var recorder = self;
    var tagName = event.target.tagName.toLowerCase();
    var type = event.target.type;
    if (('input' == tagName && ('text' == type || 'password' == type || 'file' == type)) || 'textarea' == tagName) {
        this.recordCommand("type", event.target, event.target.value, ValueType.STRING);
    }
};

Recorder.prototype.selectFocusListener = function(event){
    var tagName = event.target.nodeName.toLowerCase();
    if ('select' == tagName && event.target.multiple) {
        logger.debug('remembering selections');
        var options = event.target.options;
        for (var i = 0; i < options.length; i++) {
            if (options[i]._wasSelected == null) {
                // is the focus was gained by mousedown event, _wasSelected would be already set
                options[i]._wasSelected = options[i].selected;
            }
        }
    }
};

Recorder.prototype.selectMousedownListener = function(event) {
    var tagName = event.target.nodeName.toLowerCase();
    if ('option' == tagName) {
        var parent = event.target.parentNode;
        if (parent.multiple) {
            logger.debug('remembering selections');
            var options = parent.options;
            for (var i = 0; i < options.length; i++) {
                options[i]._wasSelected = options[i].selected;
            }
        }
    }
};

Recorder.prototype.rememberClickedListener = function(event) {
//    var recorder = event.data.recorder;
//    var recorder = self;
    this.clickedElement = event.target;
//    recorder.clickedElementLocators = this.findLocators(event.target);
};

Recorder.prototype.clickListener = function(event){
//    var recorder = event.data.recorder;
//    var recorder = self;

    if (event.button == 0) {
        var clickable = this.findClickableElement(event.target);
        if (clickable) {
//            if (recorder.mouseoverLocator) {
//                recorder.recordCommand('mouseOver', this.mouseoverLocator, '');
//                delete recorder.mouseoverLocator;
//            }
            this.recordCommand("click", event.target, null, null);
        } else {
            var target = event.target;
            var tag = target.tagName.toLowerCase();
            if(tag == "div" || tag == "span"){
               this.recordDomNode(target);
            }
            this.callIfMeaningfulEvent(function() {
                this.recordCommand("click", target, null);
            });
        }
    }
};

Recorder.prototype.selectListener = function(event) {
//    var recorder = event.data.recorder;
//    var recorder = self;

    var tagName = event.target.tagName.toLowerCase();
    if ('select' == tagName) {
        if (!event.target.multiple) {
            var option = event.target.options[event.target.selectedIndex];
            logger.debug('selected Index=' + event.target.selectedIndex);
            var optionLocator = this.getTextReg(option);
            var cmdName;
            var target;
            if(optionLocator.startsWith("label=")){
                cmdName = "selectByLabel";
                target = optionLocator.substring(6);
            }else if(optionLocator.startsWith("index=")){
                cmdName = "selectByIndex";
                target = optionLocator.substring(6);
            }else if(optionLocator.startsWith("value=")){
                cmdName = "selectByValue";
                target = optionLocator.substring(6);
            }else{
                cmdName = "select";
                target = optionLocator;
            }
            this.recordCommand(cmdName, event.target, target, ValueType.STRING);
        } else {
            logger.debug('change selection on select-multiple');
            var options = event.target.options;
            for (var i = 0; i < options.length; i++) {
                logger.debug('option=' + i + ', ' + options[i].selected);
                if (options[i]._wasSelected == null) {
                    logger.warn('_wasSelected was not recorded');
                }
                if (options[i]._wasSelected != options[i].selected) {
                    var value = this.getTextReg(options[i]);
                    if (options[i].selected) {
                        this.recordCommand("addSelection", event.target, value, ValueType.STRING);
                    } else {
                        this.recordCommand("removeSelection", event.target, value, ValueType.STRING);
                    }
                    options[i]._wasSelected = options[i].selected;
                }
            }
        }
    }
};

Recorder.prototype.attrModifiedListener = function(event){
//    var recorder = event.data.recorder;
//    var recorder = self;

    logger.debug('attrModified');
    this.domModified();
};

Recorder.prototype.nodeInsertedListener = function(event){
//    var recorder = event.data.recorder;
//    var recorder = self;

    logger.debug('nodeInserted');
    this.domModified();
};

Recorder.prototype.nodeRemovedListener = function(event) {
//    var recorder = event.data.recorder;
//    var recorder = self;

    logger.debug('nodeRemoved');
    this.domModified();
};*/

Recorder.prototype.uiSelectListener = function(event){
//    var recorder = event.data.recorder;
//    var recorder = self;

    logger.debug('listener: event.type=' + event.type + ', target=' + event.target.tagName);
//    var state = document.getElementById("record-button").getAttribute("class");
//    if(state == RecordState.PAUSE)
        event.preventDefault();
    var element = event.target;
    this.recordDomNode(element);
    this.updateWindowUrl(element);
};

Recorder.prototype.frameFocusListener = function(event) {
    event.preventDefault();
//    var recorder = event.data.recorder;
//    var recorder = self;

//  logger.debug("Inside frameFocusListener() ..");
    if (this.frameName == null) {
//      logger.debug("frameName : " + event.target.name);
        this.frameName = event.target.name;
    }
};

Recorder.prototype.frameBlurListener = function(event) {
    event.preventDefault();
//    var recorder = event.data.recorder;
//    var recorder = self;
    
    this.frameName = null;
};


