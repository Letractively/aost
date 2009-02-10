/*
 * An UI of TrUMP IDE.
 */
function Editor(window) {
    this.window = window;
    var self = this;
    window.editor = this;
    this.document = document;

    this.logView = new LogView(this);
    this.logView.setLog(LOG);

//    LOG.info("Test");
    this.registerRecorder();
}

Editor.prototype.registerRecorder = function(){
    this.recorder = new Recorder(this.window);
    this.recorder.registerListener();
}

Editor.prototype.unload = function(){
    this.recorder.unregisterListener();
}

Editor.prototype.close = function(){
//    this.recorder.unregisterListener();
}

Editor.prototype.toggleRecordButton = function(){
    var recordToolbarButton = document.getElementById("record-button");

    if(!recordToolbarButton.getAttribute("checked")){
        recordToolbarButton.setAttribute("checked", "true");

        var stopToolbarButton = document.getElementById("stop-button");
        stopToolbarButton.removeAttribute("checked");

        this.recorder.registerListener();
    }

}

Editor.prototype.toggleStopButton = function(){
    var stopToolbarButton = document.getElementById("stop-button");

    if(!stopToolbarButton.getAttribute("checked")){
        stopToolbarButton.setAttribute("checked", "true");

        var recordToolbarButton = document.getElementById("record-button");
        recordToolbarButton.removeAttribute("checked");

        this.recorder.unregisterListener();
    }
}

Editor.prototype.generateButton = function(){
    this.switchToSourceTab();

    var sourceTextNode = document.getElementById("source");
    var tagArrays = this.recorder.tagObjectArray;

    var tagObject;
    var element;
    var tree = new Tree();
    for(var i=0; i<tagArrays.length; ++i){
        tagObject = tagArrays[i];
        element = new ElementObject();
        element.uid = tagObject.tag+i;
        element.xpath = tagObject.xpath;
        element.attributes = tagObject.attributes;
        element.domNode = tagObject.node;
        tree.addElement(element);
    }
    //do some post processing work
    tree.postProcess();
    
    var uiModelArray = tree.printUI();
    var uiModel = new StringBuffer();
    if(uiModelArray != undefined){
        for(var j=0; j<uiModelArray.length; ++j){
            uiModel.append(uiModelArray[j]);
        }
    } else {
//        logger.error("uiModelArray is not defined, cannot generate source!");
        LOG.error("uiModelArray is not defined, cannot generate source!");
    }

//    logger.debug("ui model generated:\n"+uiModel);
    LOG.debug("ui model generated:\n"+uiModel);
    sourceTextNode.value = uiModel;

//    logger.debug("start to validate UI object's xpath");
    LOG.debug("start to validate UI object's xpath");

    tree.validate();
//    logger.debug("Done validating UI object's XPath");
    LOG.debug("Done validating UI object's XPath");
}

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
    this.clearSourceTabContent();
    this.logView.clear();

}

Editor.prototype.switchToSourceTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("sourceTab");
}

Editor.prototype.clearSourceTabContent = function(){
    document.getElementById("source").value = "";    
}

Editor.prototype.selectedTreeItem = function(event){
    this.recorder.showSelectedNode();  
}

