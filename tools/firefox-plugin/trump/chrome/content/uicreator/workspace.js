function NodeRef(dom, frameName, ref){
    this.dom = dom;
    this.frameName = frameName;
    this.ref = ref;
}

function UiCommand(name, ref, value){
    this.name = name;
    this.ref = ref;
    this.value = value;
}

function Workspace(uiBuilder, uiChecker){
    //ID of the current UI Module
    this.id = null;

    //UI Module
    this.uim = null;

    //Root DOM
    this.dom = null;

    //String presentation
    this.str = null;

    //JSON Presentation
    this.json = null;

    this.builder = new Builder();
    
    this.uimBuilder = new UiModuleBuilder();

    this.uiBuilder = uiBuilder;

    this.checker = uiChecker;

    //UiAlg
    this.uiAlg = new UiAlg();

    this.innerTree = null;

    this.nodeList = new Array();

    this.tagObjectArray = new Array();

    this.commandList = new Array();

    this.convertedCommandList = null;

    this.refUidMap = null;
}

Workspace.prototype.addNode = function(dom, frameName, ref){
    var node = new NodeRef(dom, frameName, ref);
    this.nodeList.push(node);
    var tagObject = this.builder.createTagObject(dom, ref, frameName);
    this.tagObjectArray.push(tagObject);
};

Workspace.prototype.addCommand = function(name, ref, value){
    var command = new UiCommand(name, ref, value);
    this.commandList.push(command);
};

Workspace.prototype.clear = function(){
    this.innerTree = null;
    this.uim = null;
    this.id = null;
    this.dom = null;
    this.nodeList = new Array();
    this.tagObjectArray = new Array();
    this.commandList = new Array();
    this.convertedCommandList = null;
    this.refUidMap = null;
};

Workspace.prototype.convertCommand = function(){
    this.convertedCommandList = new Array();
    if(this.commandList != null && this.commandList.length > 0){
        for(var i=0; i<this.commandList.length; i++){
            var cmd = this.commandList[i];

            var uid = null;
            if(cmd.ref != null){
                uid = this.refUidMap.get(cmd.ref);                
            }
            if(uid == undefined)
                logger.warn("Cannot find UID for reference ID " + cmd.ref + " for command " + cmd.name);
            var ccmd = new UiCommand(cmd.name, uid, cmd.value);
            this.convertedCommandList.push(ccmd);
        }
    }
};

Workspace.prototype.generate = function(){
    this.generateUiModule(this.tagObjectArray);
    this.validateUiModule();
    this.buildRefUidMap();
    this.convertCommand();
};

Workspace.prototype.describeCommand = function(){
    var sb = new StringBuffer();
    if(this.convertedCommandList != null && this.convertedCommandList.length > 0){
        for(var i=0; i<this.convertedCommandList.length; i++){
            var cmd = this.convertedCommandList[i];
            sb.append("\t\t").append(cmd.name);
            if(cmd.ref != null && cmd.ref != undefined){
                sb.append(" \"").append(cmd.ref).append("\"");
            }
            if(cmd.value != null && cmd.value != undefined){
                if(cmd.ref != null && cmd.ref != undefined){
                    sb.append(",");
                }
                sb.append(" \"").append(cmd.value).append("\"");
            }
            sb.append("\n");
        }
    }

    return sb.toString();
};

Workspace.prototype.describeUiModule = function() {
    var visitor = new StringifyVisitor();
    this.uim.around(visitor);
    var uiModelArray = visitor.out;
    if (uiModelArray != undefined && uiModelArray != null) {
        var sb = new StringBuffer();
        for (var i = 0; i < uiModelArray.length; ++i) {
            if (i == 0) {
                sb.append("\t\tui." + uiModelArray[i].replace(/^\s+/, ''));
            } else {
                sb.append("\t\t" + uiModelArray[i]);
            }
        }

        return sb.toString();
    }

    return "";
};

Workspace.prototype.convertSource = function(){
    return this.describeUiModule() + "\n" + this.describeCommand();    
};

Workspace.prototype.generateUiModule = function(tagArrays){
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
            element.uid = suggestName(tobj);
            element.refId = tobj.refId;
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
        this.innerTree.buildUiObject(this.uiBuilder, this.checker);

        this.innerTree.buildIndex();
    } else {
        for (i = 0; i < tagArrays.length; ++i) {
            var tagObject = tagArrays[i];
            element = new ElementObject();
//          element.uid = tagObject.tag+i;
            element.uid = suggestName(tagObject);
            element.refId = tagObject.refId;
            element.xpath = tagObject.xpath;
            element.attributes = tagObject.attributes;
            element.domNode = tagObject.node;
            element.frameName = tagObject.frameName;

            this.innerTree.addElement(element);
            //do some post processing work
        }
        this.innerTree.postProcess();
        this.innerTree.buildUiObject(this.uiBuilder, this.checker);
        this.innerTree.buildIndex();
    }
};

Workspace.prototype.buildRefUidMap = function(){
    var visitor = new UiRefMapper();
    this.innerTree.visit(visitor);

    this.refUidMap = visitor.refUidMap;
};

Workspace.prototype.getUiObject = function(uid){
    return this.innerTree.uiObjectMap.get(uid);    
};

Workspace.prototype.build = function(){
    var tim = null;
    if(this.innerTree != null && this.innerTree.root != null){
        tim = this.uimBuilder.build(this.innerTree);
        logger.info("Done build UI module ");
    }

    return tim;
};

Workspace.prototype.buildXML = function(){
    if(this.innerTree != null){
        return this.innerTree.buildXML();
    }
    
    return "";
};

Workspace.prototype.save = function(uiModule, dom){
    this.uim = uiModule;
    this.dom = dom;
    if(uiModule != null){
        this.id = uiModule.getId();
    }
};

Workspace.prototype.validate = function(){
    this.uiAlg.validate(this.uim, this.dom);

    var response = new UiModuleLocatingResponse();
    response.id = this.id;
    response.relaxed = this.uim.relaxed;
    if(this.uim.score == 100 || (!response.relaxed))
        response.found = true;
    response.relaxDetails = this.uim.relaxDetails;
    response.matches = this.uim.matches;
    response.score = this.uim.score;
    if(response.found){
        logger.info("Validate UI Module " + this.id + " Successfully!");
    }else{
        logger.info("Validate UI Module " + this.id + " Failed!");
    }

    return response;
};

Workspace.prototype.validateUiModule = function() {
    logger.debug("Start validating UI Module");
    //validate UI object's XPath
    if (this.innerTree.root != null) {
//        var uim = this.workspace.build();
        this.uim = this.uimBuilder.build(this.innerTree);
        this.dom = this.innerTree.document;
        this.id = this.uim.getId();
        var result = this.validate();
        if (result != null) {
            this.innerTree.clearValidFlag();
            if (!result.found) {
                if (result.relaxDetails != null) {
                    for (var j = 0; j < result.relaxDetails.length; j++) {
                        this.innerTree.markInvalidUiObject(result.relaxDetails[j].uid);
                    }
                }
            }

            return result.toString();
//            var msg = result.toString();
//            this.showMessage(msg);
        }
        logger.debug("Done validating UI Module, please see detailed result on the message window");
    } else {
        logger.warn("The root node in the Tree is null");
        return null;
    }
};

Workspace.prototype.validateXPath = function(){
    this.innerTree.validateXPath();
};

//Convert UI tree presentation to UI module
function UiModuleBuilder(){
    this.uiModule = null;
}

UiModuleBuilder.prototype.build = function(tree){
    this.uiModule = new UiModule();
    if(tree != null){
        tree.visit(this);
    }

    return this.uiModule;
};

UiModuleBuilder.prototype.visit = function(node){
    var obj = teJQuery.extend(true, {}, node.uiobject);
    obj.node = node;

    this.uiModule.addUiObject(node.getUid(), obj);
};