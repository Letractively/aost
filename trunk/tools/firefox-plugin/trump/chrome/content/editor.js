/*
 * An UI of TrUMP IDE.
 */

const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
function Editor(window) {
    this.window = window;
    var self = this;
    window.editor = this;
    this.document = document;

    this.logView = new LogView(this);
    this.logView.setLog(logger);

    this.registerRecorder();
    this.innerTree = null;

    this.buildCustomizeTree(DEFAULT_XML);
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
    this.innerTree = new Tree();

    for(var i=0; i<tagArrays.length; ++i){
        tagObject = tagArrays[i];
        element = new ElementObject();
        element.uid = tagObject.tag+i;
        element.xpath = tagObject.xpath;
        element.attributes = tagObject.attributes;
        element.domNode = tagObject.node;
        this.innerTree.addElement(element);
    }
    //do some post processing work
    this.innerTree.postProcess();
    
    var uiModelArray = this.innerTree.printUI();
    var uiModel = new StringBuffer();
    if(uiModelArray != undefined){
        for(var j=0; j<uiModelArray.length; ++j){
            uiModel.append(uiModelArray[j]);
        }
    } else {
        logger.error("uiModelArray is not defined, cannot generate source!");
    }

    logger.debug("ui model generated:\n"+uiModel);
    sourceTextNode.value = uiModel;

    logger.debug("start to validate UI object's xpath");

    this.innerTree.validate();

    logger.debug("Done validating UI object's XPath");
}

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
    this.clearSourceTabContent();
    this.logView.clear();
    this.innerTree = null;
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

Editor.prototype.customizeButton = function(){
    this.switchToCustomizeTab();
    var xml = DEFAULT_XML;
    if(this.innerTree != null){
        xml = this.innerTree.buildXML();
    }

    this.buildCustomizeTree(xml);
}

Editor.prototype.switchToCustomizeTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("customizeTab");
}

Editor.prototype.buildCustomizeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var customize_tree = document.getElementById("customize_tree");
        var pxml = parser.parseFromString(xml, "text/xml");
        customize_tree.builder.datasource = pxml;
        customize_tree.builder.rebuild();
//        this.initializeCustomizeListener();
    }
}


/*
Editor.prototype.initializeCustomizeListener = function() {
    if (this.innerTree != null) {
        var map = this.innerTree.uiObjectMap;
        if (map != null && map.size() > 0) {
            var keys = map.keySet();
            for (var i = 0; i < keys.length; i++) {
                var key = keys[i];
                var uiid = UID_PREFIX + key;
                logger.debug("Get element " + uiid);
                var buttons = document.getElementsByTagName("button");
                var sb = new StringBuffer();
                for(var j=0; j<buttons.length; j++){
                    var id = buttons[j].getAttribute("id");
                    sb.append(" id= "+id);
                }
                logger.debug("Buttons in Current document: " + sb.toString());
                try {
                    document.getElementById(uiid).addEventListener("command", this.processCustomizeEvent, true);
                }
                catch (e) {
                    logger.error("Exception: " + e);
                }
            }
        }
    }
}
*/

Editor.prototype.processCustomizeEvent = function(event){
    alert('Customize ' + event.target.getAttribute("label"));
//    alert('Customize ' + event.target.getAttribute("myclass"));
//    alert('Customize ' + event.target.getAttribute("uid"));
}
