//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
};

Uiid.prototype.pop = function(){
    if(this.stack.length > 0){
        return this.stack.pop();
    }

    return null;
};

Uiid.prototype.reverse = function(){
    if(this.stack.length > 0){
        this.stack.reverse();
    }
};

Uiid.prototype.peek = function(){
    if(this.stack.length > 0){
        return this.stack[this.stack.length-1];
    }

    return null;
};

Uiid.prototype.getUid = function(){
    return this.stack.join(".");
};

Uiid.prototype.size = function(){
    return this.stack.length;
};

Uiid.prototype.convertToUiid = function(uid){
    if(uid != null && trimString(uid).length > 0){
        var ids = uid.split(".");
        for(var i= 0; i<ids.length; i++){
            var pp = this.preprocess(ids[i]);
            if(pp.length == 1){
                this.push(pp[0]);
            }else{
                this.push(pp[1]);
                this.push(pp[0]);
            }
        }
    }
};

Uiid.prototype.preprocess = function(uid){
    if(uid != null && trimString(uid).length > 0 && uid.indexOf("[") != -1){
        if(uid.indexOf("[") == 0){
            var single = uid.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = uid.indexOf("[");
            var first = uid.substring(0, index);
            var second = uid.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [uid];
};

function WorkflowContext(){
    this.refLocator = null;    
};

//Base locator
function BaseLocator(){
    this.loc = null;
};

//composite locator
function CompositeLocator(){
    this.tag = null;
    this.text = null;
    this.position = null;
    this.direct = false;
    this.header = null;
    this.trailer = null;
    this.attributes = new Hashtable();
};

//base UI object
var UiObject = Class.extend({
    init: function() {
        this.uid = null;

        //its parent UI object
        this.parent = null;

        //namespace, useful for XML, XHTML, XForms
        this.namespace = null;

        this.locator = null;

        //event this object should be respond to
        this.events = null;

        //should we do lazy locationg or not, i.e., wait to the time we actually use this UI object
        //usually this flag is set because the content is dynamic at runtime
        this.lazy = false;

        //Tellurium Core generated locator for this UI Object
        this.generated = null;

        //dom reference
        this.domRef = null;
    },

    goToPlace: function(uiid, uiobj) {

        var ouid = uiid.pop();
        objectCopy(this, uiobj);
        if (uiid.length > 0) {
            alert("Wrong uiid " + ouid);
        }
    },

    locate: function(uialg) {
        uialg.locateInAllSnapshots(this);
        //need to push all its children into the object queue
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
    },

    snapshot: function() {
        if (this.generated)
            this.domRef = selenium.browserbot.findElement(this.generated);
    },

    prelocate: function() {
        if (this.amICacheable())
            this.snapshot();
    },

    walkTo: function(context, uiid) {
        return this;
    },

    amICacheable: function() {
        //check its parent and do not cache if its parent is not cacheable
        //If an object is cacheable, the path from the root to itself should
        //be all cacheable
        if (this.parent != null) {
            return this.cacheable && parent.amICacheable() && (!parent.noCacheForChildren);
        }

        return this.cacheable;
    },

    fullUid: function() {
        if (this.parent != null) {
            return this.parent.fullUid() + "." + this.uid;
        }

        return this.uid;
    }
});

var UiButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Button';
        this.tag = "input";
    }
});

var UiCheckBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'CheckBox';
        this.tag = "input";
        this.type = "checkbox";
    }
});


var UiDiv = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'div';
        this.tag = "div";
    }
});

var UiIcon = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Icon';
    }
});

var UiImage = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Image';
        this.tag = "img";
    }
});

var UiInputBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'InputBox';
        this.tag = "input";
    }
});

var UiRadioButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'RadioButton';
        this.tag = "input";
        this.type = "radio";
    }
});

var UiSelector = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Selector';
        this.tag = "select";
    }
});

var UiSpan = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Span';
        this.tag = "span";
    }
});

var UiSubmitButton = UiButton.extend({
    init: function(){
        this._super();
        this.uiType = 'SubmitButton';
        this.type = "submit";
    }
});

var UiTextBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'TextBox';
    }
});

var UiUrlLink = UiObject.extend({
    uiType: 'UrlLink',
    tag: "a"
});

var UiContainer = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Container';
        this.group = false;
        this.noCacheForChildren = false;
        this.components = new Hashtable();
    },

    goToPlace:  function(uiid, uiobj) {

        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.size() > 0) {
            var cuid = uiid.peek();
            var child = this.components.get(cuid);
            if (child != null) {
                child.goToPlace(uiid, uiobj);
            } else {
                uiobj.parent = this;
                this.components.put(cuid, uiobj);
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        //need to push all its children into the object queue
        logFirebug(this.components.showMe());
        var valset = this.components.valSet();
        for(var component in valset){
            if(!component.lazy)
                uialg.addChildUiObject(component);
        }
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
        //need to push all its children into the object queue
        var valset = this.components.valSet();
        for(var component in valset){
            if(!component.lazy)
                uialg.addChildUiObject(component);
        }
    },
    
    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var child = this.components.get(keys[i]);
                child.prelocate();
            }
        }
    },
    
    walkTo: function(context, uiid){
        if(uiid.size() < 1)
            return this;

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if(child != null)
            child.walkTo(context, uiid);
    }
});

var UiForm = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Form';
        this.tag = "form";
    }
});

var UiFrame = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Frame';
        this.id = null;
        this.name = null;
        this.title = null
    }
});

//TODO: ui algorithm operations for List and Table 
var UiList = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'List';
        this.separator = null;
        this.defaultUi = new UiTextBox();
    },
    
    findUiObject: function(index) {

        //first check _index format
        var key = "_" + index;
        var obj = this.components.get(key);

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    walkTo: function(context, uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this;

        var child = uiid.pop();

        var part = child.replace(/^_/, '');

        var nindex = parseInt(part);

        //otherwise, try to find its child
        var cobj = this.findUiObject(nindex);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    }
});

var UiTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Table';
        this.tag = "table";
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
    },
    
    goToPlace:  function(uiid, uiobj) {

        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.length > 0) {
            var cuid = uiid.peek();
            var child = null;
            if(cuid == "header"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.headers[cuid];
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                     uiobj.parent = this;
                     this.headers.put(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components[cuid];
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.components.put(cuid, uiobj);
                }
            }
        }
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    findHeaderUiObject: function(index){
        var key = "_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    findUiObject: function(row, column){
        var key = "_" + row + "_" + column;
        var obj = this.components.get(key);

        if(obj == null){
            key = "_ALL_" + column;
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    walkToHeader: function(context, uiid) {
        //pop up the "header" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        child = child.replace(/^_/, '');

        var index = parseInt(trimString(child));

        //try to find its child
        var cobj = this.findHeaderUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
        var parts = child.replace(/^_/, '').split("_");

        var nrow = parseInt(parts[0]);
        var ncolumn = parseInt(parts[1]);

        //otherwise, try to find its child
        var cobj = this.findUiObject(nrow, ncolumn);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    },

    walkTo: function(context, uiid){
      if (uiid.size() < 1)
        return this;

        var child = uiid.peek();

        if (trimString(child) == "header") {
            return this.walkToHeader(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiStandardTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'StandardTable';
        this.tag = "table";
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
        this.footers = new Hashtable();
    },

    goToPlace:  function(uiid, uiobj) {

        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.length > 0) {
            var cuid = uiid.peek();
            var child = null;
            if(cuid == "header"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.headers[cuid];
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.headers.push(cuid, uiobj);
                }
            }else if(cuid == "foot"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.footers[cuid];
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.footers.push(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components[cuid];
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.components.push(cuid, uiobj);
                }
            }
        }
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }

            keys = this.footers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.footers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    findHeaderUiObject: function(index){
        var key = "_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    findFooterUiObject: function(index){
        var key = "_" + index;
        var obj = this.footers.get(key);

        if(obj == null){
            key = "_ALL";
            obj = this.footers.get(key);
        }

        return obj;
    },

    findUiObject: function(tbody, row, column){
        var key = "_" + tbody + "_" + row + "_" + column;
        var obj = this.components.get(key);

        //thirdly, check _i_j_ALL format
        if (obj == null) {
            key = "_" + tbody + "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //then, check _i_ALL_K format
        if (obj == null) {
            key = "_" + tbody + "_ALL_" + column;
            obj = this.components.get(key);
        }

        //check _ALL_j_k format
        if (obj == null) {
            key = "_ALL_" + row + "_" + column;
            obj = this.components.get(key);
        }

        //check _i_ALL_ALL
        if(obj == null){
            key = "_" + tbody + "_ALL_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_j_ALL
        if(obj == null){
            key = "_ALL_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_ALL_k
        if(obj == null){
            key = "_ALL_ALL_" + column;
            obj = this.components.get(key);
        }

        //last, check ALL format
        if (obj == null) {
            key = "_ALL_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    walkToHeader: function(context, uiid) {
        //pop up the "header" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        child = child.replace(/^_/, '');

        var index = parseInt(trimString(child));

        //try to find its child
        var cobj = this.findHeaderUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    },

    walkToFooter: function(context, uiid) {
        //pop up the "foot" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        child = child.replace(/^_/, '');

        var index = parseInt(trimString(child));

        //try to find its child
        var cobj = this.findFooterUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
        var parts = child.replace(/^_/, '').split("_");
        var ntbody;
        var nrow;
        var ncolumn;
        if(parts.length == 3){
            ntbody = parseInt(parts[0]);
            nrow = parseInt(parts[1]);
            ncolumn = parseInt(parts[2]);
        }else{
            ntbody = 1;
            nrow = parseInt(parts[0]);
            ncolumn = parseInt(parts[1]);
        }

        //otherwise, try to find its child
        var cobj = this.findUiObject(ntbody, nrow, ncolumn);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid);
        }
    },

    walkTo: function(context, uiid){
      if (uiid.size() < 1)
        return this;

        var child = uiid.peek();

        if (trimString(child) == "header") {
            return this.walkToHeader(context, uiid);
        } else if(trimString(child) == "foot"){
            return this.walkToFooter(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiWindow = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Window';
        this.id = null;
        this.name = null;
        this.title = null;
    }
});


function UiModule(){
    //top level UI object
    this.root = null;

    this.valid = false;

    //the id of the UI module should be the root uid
//    this.id = null;

    //hold a hashtable of the uids for fast access
    this.map = new Hashtable();
};

UiModule.prototype.getId = function(){
    if(this.root != null)
        return this.root.uid;

    return null;
};

UiModule.prototype.buildFromJSON = function(jsobj){
    //TODO: find a more elegant way to create a Javascript function by its name
    var obj = null;
    switch(jsobj.uiType){
        case "Button":
            obj = new UiButton();
            break;
        case "CheckBox":
            obj = new UiCheckBox();
            break;
        case "Div":
            obj = new UiDiv();
            break;
        case "Icon":
            obj = new UiIcon();
            break;
        case "Image":
            obj = new UiImage();
            break;
        case "InputBox":
            obj = new UiInputBox();
            break;
        case "RadioButton":
            obj = new UiRadioButton();
            break;
        case "Selector":
            obj = new UiSelector();
            break;
        case "Span":
            obj = new UiSpan();
            break;
        case "SubmitButton":
            obj = new UiSubmitButton();
            break;
        case "TextBox":
            obj = new UiTextBox();
            break;
        case "UrlLink":
            obj = new UiUrlLink();
            break;
        case "Container":
            obj = new UiContainer();
            break;
        case "Form":
            obj = new UiForm();
            break;
        case "Frame":
            obj = new UiFrame();
            break;
        case "List":
            obj = new UiList();
            break;
        case "Table":
            obj = new UiTable();
            break;
        case "StandardTable":
            obj = new UiStandardTable();
            break;
        case "Window":
            obj = new UiWindow();
            break;
    }

    objectCopy(obj, jsobj);
    logFirebug("Build from JSON ");
    logFirebug(jsobj);
    logFirebug(obj);
    
    return obj;
};

UiModule.prototype.parseUiModule = function(json){

//    var ulst = JSON.parse(json, null);
//XXX: Strange, JSON.parse does not work, assign directly?    
    var ulst = json;
    logFirebug(ulst);
    var klst = new Array();
    for(var i=0; i<ulst.length; i++){
        var key = ulst[i].key;
        var obj = ulst[i].obj;
        this.map.put(key, this.buildFromJSON(obj));
        klst.push(key);
    }

    this.buildTree(klst);
//    this.valid = true;
    logFirebug(this);
};

UiModule.prototype.buildTree = function(keys){
    for(var i=0; i<keys.length; i++){
        var uiobj = this.map.get(keys[i]);
        if(this.root == null){
            this.root = uiobj;
            this.id = uiobj.uid;
        }else{
            var uiid = new Uiid();
//            uiid.convertToUiid(uiobj.uid);
            uiid.convertToUiid(keys[i]);
            uiid.reverse();
            this.root.goToPlace(uiid, uiobj);
        }
    }
};

UiModule.prototype.prelocate = function(){
    if(this.root != null){
        this.root.prelocate();
        this.valid = true;
    }
};

UiModule.prototype.walkTo = function(context, uiid){
    var first = uiid.pop();
    if(first == this.root.uid){
        return this.root.walkTo(context, uiid);
    }

    return null;
};

//a snapshot of the UI module in the DOM
function UiSnapshot(){
    this.elements = new Hashtable();
    this.color = null;
};

UiSnapshot.prototype.addUi = function(uid, domref){
    this.elements.put(uid, domref);
};

UiSnapshot.prototype.getUi = function(uid){
    return this.elements.get(uid);
};

UiSnapshot.prototype.clone = function(){
    var snapshot = new UiSnapshot();
    snapshot.elements = this.elements.clone();

    return snapshot;
};

UiSnapshot.prototype.setColor = function(color){
    this.color = color;
};

//algorithms to handle UI modules and UI Objects
function UiAlg(){
    //current root DOM element
    this.dom = null;
    
    this.colors = {
        WHITE: "white",
        GRAY: "gray",
        BLACK: "black"
    };

    this.currentColor = null;

    //whether allow to use closest matching element if no one matches
    this.allowRelax = false;

    //FIFO queue to hold UI snapshots
    this.squeue = new FifoQueue();

    //FIFO queue to hold UI objects in the UI module
    this.oqueue = new FifoQueue();

    //jQuery builder to build CSS selectors
    this.cssbuilder = new JQueryBuilder();
};

UiAlg.prototype.clear = function(){
    this.dom = null;
    this.currentColor = this.colors.WHITE;
    this.squeue.clear();
    this.oqueue.clear();
};

UiAlg.prototype.nextColor = function(){
    if(this.currentColor == null){
        return this.colors.GRAY;
    }else{
        if(this.currentColor == this.colors.GRAY){
            return this.colors.BLACK;
        }

        return this.colors.GRAY;       
    }
};

UiAlg.prototype.locateInAllSnapshots = function(uiobj){
    var finished = false;
    while(!finished && this.squeue.size() > 0){
        var first = this.squeue.peek();
        //check the first element color
        if(this.currentColor == first.color){
            first = this.squeue.pop();
            this.locate(uiobj.uid, uiobj.locator, first);
        }else{
            //exit when the snapshot color is marked for the next round
            finished = true;
            this.currentColor = this.nextColor();
            if(this.squeue.size() == 0){
                throw new SeleniumError("Cannot find UI element " + uiobj.uid);
            }
        }
    }
};

//TODO: may need to pass in more attributes other than clocator, for instance, the separator in the List object
UiAlg.prototype.locate = function(uid, clocator, snapshot){
    //the next color to label the snapshot
    var ncolor = this.nextColor();
    //first find its parent uid
    var puid = this.getParentUid(uid);
    var pref = null;
    if(puid != null){
        pref = snapshot.getUi(puid);
    }else{
        pref = this.dom;
    }

    //build the CSS selector from the current element's composite locator
    var csel = this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, clocator.position, clocator.direct, clocator.attributes);
    var $found = teJQuery(pref).find(csel);
    //found any nodes in the DOM by using the
    if($found.size() == 0){
        //found exactly one, lucky path
        snapshot.addUi(uid, $found.get(0));
        snapshot.setColor(ncolor);
        this.squeue.push(snapshot);
    }else if($found.size() > 1){
        //multiple results, need to create more snapshots to expend the search
        for (var i = 1; i < $found.size(); i++){
            var newsnapshot = snapshot.clone();
            newsnapshot.addUi(uid, $found.get(i));
            newsnapshot.setColor(ncolor);
            this.squeue.push(newsnapshot);
        }
        //still need the push back the orignail snapshot
        snapshot.addUi(uid, $found.get(0));
        snapshot.setColor(ncolor);
        this.squeue.push(snapshot);
    }else{
        //if allow us to relax the clocator/attribute constraints and use the closest matching ones instead
        if(this.allowRelax){
            var $relaxed = this.relax(clocator, pref);
            if($relaxed.size() == 1){
                //found exactly one
                snapshot.addUi(uid, $relaxed.get(0));
                snapshot.setColor(ncolor);
                this.squeue.push(snapshot);
            }else if($relaxed.size() > 1){
                //multiple results, need to create more snapshots to expend the search
                for (var j = 1; j < $relaxed.size(); j++) {
                    var nsnapshot = snapshot.clone();
                    nsnapshot.addUi(uid, $relaxed.get(j));
                    nsnapshot.setColor(ncolor);
                    this.squeue.push(nsnapshot);
                }
                //still need the push back the orignail snapshot
                snapshot.addUi(uid, $relaxed.get(0));
                snapshot.setColor(ncolor);
                this.squeue.push(snapshot);
            }else{
                //otherwise, throw exception
//                throw new SeleniumError("Cannot find UI element " + uid);
                //do not throw exception and do not push the snapshot back to the queue instead
            }
        }else{
            //otherwise, throw exception
//            throw new SeleniumError("Cannot find UI element " + uid);
            //do not throw exception and do not push the snapshot back to the queue instead
        }   
    }
};

UiAlg.prototype.relax = function(clocator, pref) {
    var attrs = new Hashtable();
    if(clocator.text != null && clocator.trim().length > 0){
        attrs.put("text", clocator.text);
    }
    if(clocator.position != null){
        attrs.put("position", clocator.position);
    }

    for (var key in clocator.attributes) {
        if (!this.cssbuilder.inBlackList(key)) {
            attrs.put(key, clocator.get(key));
        }
    }
    var jqs = "";
    var id = clocator.attributes["id"];
    var tag = clocator.tag;

    if (tag == null || tag == undefined || tag.trim().length == 0) {
        //TODO: need to double check if this is correct or not in jQuery
        tag = "*";
    }

    //Use tag for the initial search
    var $closest = teJQuery(pref).find(tag);
    if (id != null && id != undefined && (!this.cssbuilder.isPartial(id))) {
        jqs = this.cssbuilder.buildId(id);
        $closest = teJQuery(pref).find(jqs);
        return $closest;
    } else {
        jqs = tag;
        var keys = attrs.keySet();
        for (var m = 0; m < keys.length; m++) {
            var attr = keys[m];
            var tsel = this.cssbuilder.buildSelector(attr, attrs[attr]);
            var $mt = teJQuery(pref).find(jqs + tsel);
            if ($mt.length > 0) {
                $closest = $mt;
                jqs = jqs + tsel;
            }
        }

        return $closest;
    }
};

UiAlg.prototype.addChildUiObject = function(uiobj){
    this.oqueue.push(uiobj);
};

UiAlg.prototype.getParentUid = function(uid){
    var indx = uid.lastIndexOf(".");
    if(indx != -1){
        return uid.slice(0, indx);
    }

    return null;
};

UiAlg.prototype.takeSnapshot = function(uimodule, rootdom){
    this.clear();
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
        this.dom = selenium.browserbot.findElement("jquery=html > body");
    }
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        logFirebug(uiobj);
        dumpObject(uiobj);
        uiobj.locate(this);
        logFirebug(this.oqueue.size());
    }
    if(this.squeue.size() == 0){
         throw new SeleniumError("Cannot locate UI module " +  uimodule.root.uid);
    }
    if(this.squeue.size() > 1){
       throw new SeleniumError("Found" + this.squeue.size() +  " matches for UI module " + uimodule.root.uid);
    }
    //found only one snapshot, happy path
    var snapshot = this.squeue.pop();
    this.bindToUiModule(uimodule, snapshot);
};

UiAlg.prototype.bindToUiModule = function(uimodule, snapshot){
    this.oqueue.clear();
    this.oqueue.push(uimodule.root);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        uiobj.bind(snapshot, this);
    }   
};
