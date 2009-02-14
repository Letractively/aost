/*
 * An UI of TrUMP IDE.
 */

const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
const UIOBJECT_ATTRIBUTES_XML = "<?xml version=\"1.0\"?><attributes id=\"attributes_tree_xml\" xmlns=\"\"><attribute name=\"attr1\" value=\"val1\" /></attributes>";
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

    this.currentUid = null;
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

     this.updateSource();

    logger.debug("start to validate UI object's xpath");

    this.innerTree.validate();

    logger.debug("Done validating UI object's XPath");
}

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

    logger.debug("ui model generated:\n"+uiModel);
    sourceTextNode.value = uiModel;
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
    }
}

Editor.prototype.processCustomizeEvent = function(event){
    logger.debug('Customize ' + event.target.getAttribute("label"));
    this.currentUid = event.target.getAttribute("uid");
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
    logger.debug("uiObject : " + uiObject.descObject());
    if(uiObject != null){
        this.fillUiObjectFields(uiObject);
        this.enableUiObjectFields();
    }else{
        logger.warn("Cannot find UI object " + this.currentUid);   
    }
}

Editor.prototype.fillUiObjectFields = function(uiObject){
    document.getElementById("uid").value= uiObject.uid;
    document.getElementById("uiType").value = uiObject.uiType;
    if(uiObject.node != null){
       var xml = uiObject.node.buildAttributeXml();
        this.buildUiAttributeTree(xml);
    }else{
        logger.warn("Ui object " + uiObject.uid + " does not point to a Node in the tree")
    }
}

Editor.prototype.enableUiObjectFields = function(){
    document.getElementById("uid").removeAttribute("disabled")
    document.getElementById("uiType").removeAttribute("disabled");
}

Editor.prototype.disableUiObjectFields = function(){
    document.getElementById("uid").setAttribute("disabled", "true");
    document.getElementById("uiType").setAttribute("disabled", "true");   
}

/*
Editor.prototype.buildAttributeXml = function(node){
    var keySet = node.attributes.keySet();
    var xmlArray = new Array();
    var xmlBuffer = new StringBuffer();

    for(var i=0 ; i < keySet.length; ++i){
        xmlArray.push("<attribute name=\""+ keySet[i] + "\""+ " value=\""+node.attributes.get(keySet[i]) + "\"" + "/>\n");
    }

    var xml = "<?xml version=\"1.0\"?>\n<attributes id=\"attributes_tree_xml\" xmlns=\"\">\n";

    if(xmlArray != null){
        for(var i=0; i<xmlArray.length; ++i){
            xmlBuffer.append(xmlArray[i]);
        }
    }


    xml += xmlBuffer.toString();
    xml += "</attributes>\n";

    logger.debug("Attributes XML: \n" + xml);

    return xml;
}
*/

Editor.prototype.buildUiAttributeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var ui_attribute_tree = document.getElementById("ui_attribute_tree");
        var pxml = parser.parseFromString(xml, "text/xml");
        ui_attribute_tree.builder.datasource = pxml;
        ui_attribute_tree.builder.rebuild();
    }
}

Editor.prototype.updateUiObject = function(){
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
    uiObject.setUID(document.getElementById("uid").value);
    uiObject.setUiType(document.getElementById("uiType").value);

    this.customizeButton();
    this.updateSource();
}
