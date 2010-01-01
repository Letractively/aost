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

    lookChildren: function() {
        return null;
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
        fbLog("Children for Container " + this.uid + ": " , this.components.showMe());
        var valset = this.components.valSet();
        fbLog("Val set: ", valset);
        for(var i=0; i<valset.length; i++){
             var component = valset[i];
             fbLog("component: ", component);
             if(!component.lazy){
                fbLog("Add child of Container " + this.uid + " to UiAlg : ", component);
                uialg.addChildUiObject(component);
            }
        }
    },

    lookChildren: function(){
        var valset = this.components.valSet();
        fbLog("Val set: ", valset);
        var validChildren = new Array();
        for(var i=0; i<valset.length; i++){
             var component = valset[i];
             fbLog("component: ", component);
             if(!component.lazy){
                fbLog("Add cachable child of Container " + this.uid + ": ", component);
                validChildren.push(component);
            }
        }

        return validChildren;
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
        //need to push all its children into the object queue
        var valset = this.components.valSet();
        for(var i=0; i<valset.length; i++){
            if(!valset[i].lazy)
                uialg.addChildUiObject(valset[i]);
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

    //hold a hashtable of the uid to UI objects for fast access
    this.map = new Hashtable();

    //index for uid - dom reference for fast access
    this.indices = null;

    //If the UI Module is relaxed, i.e., use closest match
    this.relaxed = false;

    //the relax details including the UIDs and their corresponding html source
    this.relaxDetails = null;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;
};

UiModule.prototype.getId = function(){
    if(this.root != null)
        return this.root.uid;

    return null;
};

UiModule.prototype.parseUiModule = function(json){

    var ulst = JSON.parse(json, null);
    var klst = new Array();
    fbLog("JSON Object ulst: ", ulst);
    fbLog("ulst length: ", ulst.length);
    for(var i=0; i<ulst.length; i++){
        fbLog("i: ", i);
        fbLog("Build from JSON object: ", ulst[i].obj);
        this.map.put(ulst[i].key, this.buildFromJSON(ulst[i].obj));
        klst.push(ulst[i].key);
    }

    this.buildTree(klst);
    fbLog("Parsed Ui Module " + this.id + ": ", this);
};

UiModule.prototype.buildFromJSON = function(jobj){
    //TODO: find a more elegant way to create a Javascript function by its name
    var obj = null;
    switch(jobj.uiType){
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

    objectCopy(obj, jobj);
    fbLog("Build from JSON: ", jobj);
    fbLog("Object built: ", obj);

    return obj;
};

UiModule.prototype.buildTree = function(keys){
    for(var i=0; i<keys.length; i++){
        var uiobj = this.map.get(keys[i]);
        if(this.root == null){
            this.root = uiobj;
            this.id = uiobj.uid;
        }else{
            var uiid = new Uiid();
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

UiModule.prototype.index = function(uid){
    return this.indices.get(uid);
};

UiModule.prototype.walkTo = function(context, uiid) {
    var first = uiid.pop();
    if (first == this.root.uid) {
        return this.root.walkTo(context, uiid);
    }

    return null;
};

function RelaxDetail(){
    //which UID got relaxed, i.e., closest Match
    this.uid = null;
    //the clocator defintion for the UI object corresponding to the UID
    this.locator = null;
    //The actual html source of the closest match element
    this.html = null;
}

//a snapshot of the UI module in the DOM
function UiSnapshot(){
    this.elements = new Hashtable();
    this.color = null;
    this.relaxed = false; 
    this.relaxDetails = new Array();
    this.score = 0;
    this.nelem = 0;
};

UiSnapshot.prototype.getScaledScore = function(){
    if(this.nelem == 0)
        return 0;
    else
        return this.score/this.nelem;
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

    //array to hold all marked data("uid) so that we can remove them later
    this.uidset = new Array();
};

UiAlg.prototype.clear = function(){
    this.dom = null;
    this.currentColor = this.colors.WHITE;
    this.squeue.clear();
    this.oqueue.clear();
    this.uidset = new Array();
};

//remove all the marked data("uid")
UiAlg.prototype.unmark = function(){
    if(this.uidset != null){
        for(var i=0; i< this.uidset.length; i++){
            fbLog("Unmarking uid " + this.uidset[i].data("uid"), this.uidset[i]);
            this.uidset[i].removeData("uid");
        }
    }
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
            this.locate(uiobj, first);
        }else{
            //exit when the snapshot color is marked for the next round
            finished = true;
            this.currentColor = this.nextColor();
            if(this.squeue.size() == 0){
                fbError("Cannot find UI element " + uiobj.uid);
                throw new SeleniumError("Cannot find UI element " + uiobj.uid);
            }
        }
    }
};

UiAlg.prototype.lookAhead = function(uiobj, $found){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var gsel = new Array();
        for(var c=0; c < children.length; c++){
            gsel.push(this.buildSelector(children[c].locator));
        }
        var result = new Array();
        for(var i=0; i<$found.size(); i++){
            if(this.hasChildren($found.get(i), gsel)){
                result.push($found.get(i));
            }
        }

        return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.hasChildren = function(one, gsel){
    var result = true;
    $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        result = result && ($me.find(gsel[i]).size() > 0);
    }

    return result;
};

UiAlg.prototype.buildSelector = function(clocator){
    
    var csel = this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, clocator.position, clocator.direct, clocator.attributes);

    return csel;
};

//TODO: may need to pass in more attributes other than clocator, for instance, the separator in the List object
UiAlg.prototype.locate = function(uiobj, snapshot){
    var uid = uiobj.fullUid();
    var clocator = uiobj.locator;
    
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
//    var csel = this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, clocator.position, clocator.direct, clocator.attributes);
    var csel = this.buildSelector(clocator);
    var $found = teJQuery(pref).find(csel);
    var foundWithoutLookAhead = false;
    if($found.size() > 0){
        foundWithoutLookAhead = true;
    }
    //if multiple matches, need to narrow down by looking ahead at the UI object's children
    if($found.size() > 1){

        $found = this.lookAhead(uiobj, $found);
    }
    
    //found any nodes in the DOM by using the
    if($found.size() == 1){
        //found exactly one, happy path
        //temporially assign uid to the found element
        fbLog("Marking uid " + uid, $found.eq(0));
        $found.eq(0).data("uid", uid);
        snapshot.addUi(uid, $found.get(0));
        //store all the elements with data("uid")
        this.uidset.push($found.eq(0));
        snapshot.setColor(ncolor);
        snapshot.score += 100;
        snapshot.nelem++;
        this.squeue.push(snapshot);
    }else if($found.size() > 1){
        //multiple results, need to create more snapshots to expend the search
        for (var i = 1; i < $found.size(); i++){
            //check if the element has the "uid" in data, if not try to clone it
            if ($found.eq(i).data("uid") == undefined){
                var newsnapshot = snapshot.clone();
                newsnapshot.addUi(uid, $found.get(i));
                newsnapshot.setColor(ncolor);
                newsnapshot.score += 100;
                newsnapshot.nelem++;
                this.squeue.push(newsnapshot);
            }
        }
        //still need the push back the orignail snapshot
        if ($found.eq(0).data("uid") == undefined) {
            snapshot.addUi(uid, $found.get(0));
            snapshot.setColor(ncolor);
            snapshot.score += 100;
            snapshot.nelem++;
            this.squeue.push(snapshot);
        }
    }else{
        //if allow us to relax the clocator/attribute constraints and use the closest matching ones instead
        if(this.allowRelax){
            var result = this.relax(clocator, pref);
            var $relaxed = result.closest;
            
            if ($relaxed.size() > 1) {
                $relaxed = this.lookAheadClosestMatchChildren(uiobj, $relaxed, result);
            }

            if($relaxed.size() == 1){
                //found exactly one
                //temporially assign uid to the found element
                fbLog("Marking closest match for uid " + uid, $relaxed.get(0));
                $relaxed.eq(0).data("uid", uid);

                if (!foundWithoutLookAhead) {
                    //get the relaxed details
                    var rdz = new RelaxDetail();
                    rdz.uid = uid;
                    rdz.locator = clocator;
                    rdz.html = $relaxed.eq(0).outerHTML();
                    snapshot.relaxed = true;
                    snapshot.relaxDetails.push(rdz);
                }

                snapshot.addUi(uid, $relaxed.get(0));
                snapshot.setColor(ncolor);
                snapshot.score += result.score;
                snapshot.nelem++;
                this.squeue.push(snapshot);
            }else if($relaxed.size() > 1){
                //multiple results, need to create more snapshots to expend the search
                for (var j = 1; j < $relaxed.size(); j++) {
                    if ($relaxed.eq(i).data("uid") == undefined) {

                        var nsnapshot = snapshot.clone();

                        if (!foundWithoutLookAhead) {
                            //get the relaxed details
                            var rdi = new RelaxDetail();
                            rdi.uid = uid;
                            rdi.locator = clocator;
                            rdi.html = $relaxed.eq(i).outerHTML();
                            nsnapshot.relaxed = true;
                            nsnapshot.relaxDetails.push(rdi);
                        }

                        nsnapshot.addUi(uid, $relaxed.get(j));
                        nsnapshot.setColor(ncolor);
                        nsnapshot.score += result.score;
                        nsnapshot.nelem++;
                        this.squeue.push(nsnapshot);
                    }
                }
                //still need the push back the orignail snapshot
                if ($relaxed.eq(0).data("uid") == undefined) {
                    
                    if (!foundWithoutLookAhead) {
                        //get the relaxed details
                        var rdf = new RelaxDetail();
                        rdf.uid = uid;
                        rdf.locator = clocator;
                        rdf.html = $relaxed.eq(0).outerHTML();
                        snapshot.relaxed = true;
                        snapshot.relaxDetails.push(rdf);
                    }

                    snapshot.addUi(uid, $relaxed.get(0));
                    snapshot.setColor(ncolor);
                    snapshot.score += result.score;
                    snapshot.nelem++;
                    this.squeue.push(snapshot);
                }
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

function MatchResult(){
    this.closest = null;
    //scaled match score, 0 - 100, or 100 percentage
    this.score = 0;
};

UiAlg.prototype.relax = function(clocator, pref) {
    var attrs = new Hashtable();
    if(clocator.text != null && clocator.text.trim().length > 0){
        attrs.put("text", clocator.text);
    }
    if(clocator.position != null){
        attrs.put("position", clocator.position);
    }

    var id = null;
    if (clocator.attributes != undefined) {
        for (var key in clocator.attributes) {
            if (!this.cssbuilder.inBlackList(key)) {
                attrs.put(key, clocator.attributes[key]);
            }
        }

        id = clocator.attributes["id"];
    }
    var jqs = "";
    var tag = clocator.tag;

    if (tag == null || tag == undefined || tag.trim().length == 0) {
        //TODO: need to double check if this is correct or not in jQuery
        tag = "*";
    }

    var result = new MatchResult();
    //Use tag for the initial search
    var $closest = teJQuery(pref).find(tag);
    if (id != null && id != undefined && (!this.cssbuilder.isPartial(id))) {
        jqs = this.cssbuilder.buildId(id);
        $closest = teJQuery(pref).find(jqs);
        //Because ID is unique, if ID matches, ignore all others and assign it a big value
        if($closest.size() > 0){
            result.score = 100;
            result.closest = $closest;
        }
        fbLog("Scaled Matching Score: " + result.score, result);
           
        return result;
    } else {
        jqs = tag;
        var keys = attrs.keySet();

        //number of properties, tag must be included
        var np = 1;
        //number of matched properties
        var nm = 0;

        if (keys != null && keys.length > 0) {
            np = np + keys.length;
            for (var m = 0; m < keys.length; m++) {
                var attr = keys[m];
                var tsel = this.cssbuilder.buildSelector(attr, attrs.get(attr));
                var $mt = teJQuery(pref).find(jqs + tsel);
                if ($mt.length > 0) {
                    $closest = $mt;
                    result.closest = $closest;
                    jqs = jqs + tsel;
                    if(nm == 0){
                        nm = 2;
                    }else{
                        nm++;
                    }
                }
            }
        }else{
            if($closest.size() > 0){
                nm = 1;
                result.closest = $closest;
            }
        }

        //calculate matching score, scaled to 100 percentage
        result.score = 100*nm/np;
        fbLog("Scaled Matching Score: " + result.score, result);

        return result;
    }
};

UiAlg.prototype.hasClosestMatchChildren = function(one, clocators){
    var score = 0;
    for(var i=0; i<clocators.length; i++){
        var result = this.relax(clocators[i], one);
        if(result.closest == null || result.closest.size() == 0){
            score = 0;
            break;
        }else{
            score = score + result.score;
        }
    }
    
    return score;
};

UiAlg.prototype.lookAheadClosestMatchChildren = function(uiobj, $found, matchresult){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var clocators = new Array();
        for(var c=0; c < children.length; c++){
            clocators.push(children[c].locator);
        }
        var result = new Array();
        var max = 0;
        var closest = null;
        for(var i=0; i<$found.size(); i++){
            var score = this.hasClosestMatchChildren($found.get(i), clocators);
            if(score > 0){
                if(max < score){
                    //try to find the higest matches, for tied condition, i.e., multiple highest matches, select the first one
                    max = score;
                    closest = $found.get(i);
                }
            }
        }

        if(closest != null){
            //average the score over the element and its children
            matchresult.score = (matchresult.score + max)/(children.length + 1);
            matchresult.closest = closest;
            result.push(closest);
        }
        
        return teJQuery(result);
    }

    return $found;
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

//
//The santa algorithm, i.e., Tellurium UI module group locating algorithm
//
//The santa name comes from the fact that I designed and finalized the algorithm
//during the Christmas season in 2009, which is a gift for me from Santa Claus
//
// by Jian Fang (John.Jian.Fang@gmail.com)
//
UiAlg.prototype.santa = function(uimodule, rootdom){
    this.clear();
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid);
        throw new SeleniumError("Cannot locate UI module " +  uimodule.root.uid);
    }
    if(this.squeue.size() > 1){
       fbError("Found " + this.squeue.size() +  " matches for UI module " + uimodule.root.uid, this.squeue);
       throw new SeleniumError("Found " + this.squeue.size() +  " matches for UI module " + uimodule.root.uid);
    }
    //found only one snapshot, happy path
    var snapshot = this.squeue.pop();
    fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
    this.bindToUiModule(uimodule, snapshot);
    this.unmark();
    uimodule.matches = 1;
};

UiAlg.prototype.validate = function(uimodule, rootdom){
    this.clear();
    var relaxflag = this.allowRelax;
    this.allowRelax = true;
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid);
        uimodule.matches = 0;
    }
    if (this.squeue.size() >= 1) {
        fbLog("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
        uimodule.matches = this.squeue.size();
        //use match score to select the best match
        var snapshot = this.squeue.pop();
        var maxscore = snapshot.getScaledScore();
        while (this.squeue.length > 0) {
            var nsnapshot = this.squeue.pop();
            var nscore = nsnapshot.getScaledScore();
            if (nscore > maxscore) {
                snapshot = nsnapshot;
                maxscore = nscore;
            }
        }

        fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
        this.bindToUiModule(uimodule, snapshot);
        this.unmark();
    }
    this.allowRelax = relaxflag;
};

UiAlg.prototype.bindToUiModule = function(uimodule, snapshot){
    this.oqueue.clear();
    this.oqueue.push(uimodule.root);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        uiobj.bind(snapshot, this);
    }
    //add index to Ui Module for uid to dom reference reference for fast access
    fbLog("Adding uid to dom reference indices for UI module " + uimodule.root.uid, snapshot.elements);
    uimodule.indices = snapshot.elements;
    uimodule.relaxed = snapshot.relaxed;
    uimodule.relaxDetails = snapshot.relaxDetails;
    uimodule.score = snapshot.getScaledScore();
};
