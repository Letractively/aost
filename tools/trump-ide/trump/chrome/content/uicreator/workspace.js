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

    this.uimBuilder = new UiModuleBuilder();

    this.uiBuilder = uiBuilder;

    this.checker = uiChecker;

    //UiAlg
    this.uiAlg = new UiAlg();

    this.innerTree = null;
}

Workspace.prototype.clear = function(){
    this.innerTree = null;
    this.uim = null;
    this.id = null;
    this.dom = null;
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