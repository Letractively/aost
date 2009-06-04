//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
};

Uiid.prototype.pop = function(){
    return this.stack.pop();
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
function UiObject(){

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
};

UiObject.prototype.goToPlace = function(uiid, uiobj){

    var ouid = uiid.pop();
    objectCopy(this, uiobj);
    if(uiid.length > 0){
        alert("Wrong uiid " + ouid);
    }
};

UiObject.prototype.snapshot = function(){
    if(this.generated)
        this.domRef = selenium.browserbot.findElement(this.generated);    
};

UiObject.prototype.prelocate = function(){
    if(this.amICacheable())
        this.snapshot();
};

UiObject.prototype.walkTo = function(context, uiid){
    return this;
};

UiObject.prototype.amICacheable = function() {
    //check its parent and do not cache if its parent is not cacheable
    //If an object is cacheable, the path from the root to itself should
    //be all cacheable
    if (parent != null)
        return this.cacheable && parent.amICacheable() && (!parent.noCacheForChildren);

    return this.cacheable;
};

var Button = classCreate();
objectExtends(Button.prototype, UiObject.prototype, {
    uiType: 'Button',
    tag: "input"
});

var CheckBox = classCreate();
objectExtends(CheckBox.prototype, UiObject.prototype, {
    uiType: 'CheckBox',
    tag: "input",
    type: "checkbox"
});

var Div = classCreate();
objectExtends(Div.prototype, UiObject.prototype, {
    uiType: 'div',
    tag: "div"
});

var Icon = classCreate();
objectExtends(Icon.prototype, UiObject.prototype, {
    uiType: 'Icon'
});

var Image = classCreate();
objectExtends(Image.prototype, UiObject.prototype, {
    uiType: 'Image',
    tag: "img"
});

var InputBox = classCreate();
objectExtends(InputBox.prototype, UiObject.prototype, {
    uiType: 'InputBox',
    tag: "input"
});

var RadioButton = classCreate();
objectExtends(RadioButton.prototype, UiObject.prototype, {
    uiType: 'RadioButton',
    tag: "input",
    type: "radio"
});

var Selector = classCreate();
objectExtends(Selector.prototype, UiObject.prototype, {
    uiType: 'Selector',
    tag: "select"
});

var Span = classCreate();
objectExtends(Span.prototype, UiObject.prototype, {
    uiType: 'Span',
    tag: "span"
});

var SubmitButton = classCreate();
objectExtends(SubmitButton.prototype, Button.prototype, {
    uiType: 'SubmitButton',
    type: "submit"
});

var TextBox = classCreate();
objectExtends(TextBox.prototype, UiObject.prototype, {
    uiType: 'TextBox'
});

var UrlLink = classCreate();
objectExtends(UrlLink.prototype, UiObject.prototype, {
    uiType: 'UrlLink',
    tag: "a"
});

var Container = classCreate();
objectExtends(Container.prototype, UiObject.prototype, {
    uiType: 'Container',
    group: false,
    noCacheForChildren: false,
    components: new Hashtable(),

    goToPlace:  function(uiid, uiobj) {

        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.length > 0) {
            var cuid = uiid.peek();
            var child = this.components[cuid];
            if (child != null) {
                child.goToPlace(uiid, uiobj);
            } else {
                uiobj.parent = this;
                this.components.put(cuid, uiobj);
            }
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

var Form = classCreate();
objectExtends(Form.prototype, Container.prototype, {
    uiType: 'Form',
    tag: "form"
});

var Frame = classCreate();
objectExtends(Frame.prototype, Container.prototype, {
    uiType: 'Frame',
    id: null,
    name: null,
    title: null
});

var List = classCreate();
objectExtends(List.prototype, Container.prototype, {
    uiType: 'List',
    separator: null,
    defaultUi: new TextBox(),
    
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

var Table  = classCreate();
objectExtends(Table.prototype, Container.prototype, {
    uiType: 'Table',
    tag: "table",
    defaultUi: new TextBox(),
    headers: new Hashtable(),
    
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

var StandardTable  = classCreate();
objectExtends(StandardTable.prototype, Container.prototype, {
    uiType: 'StandardTable',
    tag: "table",
    defaultUi: new TextBox(),
    headers: new Hashtable(),
    footers: new Hashtable(),

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

var Window  = classCreate();
objectExtends(Window.prototype, Container.prototype, {
    uiType: 'Window',
    id: null,
    name: null,
    title: null
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
            obj = new Button();
            break;
        case "CheckBox":
            obj = new CheckBox();
            break;
        case "Div":
            obj = new Div();
            break;
        case "Icon":
            obj = new Icon();
            break;
        case "Image":
            obj = new Image();
            break;
        case "InputBox":
            obj = new InputBox();
            break;
        case "RadioButton":
            obj = new RadioButton();
            break;
        case "Selector":
            obj = new Selector();
            break;
        case "Span":
            obj = new Span();
            break;
        case "SubmitButton":
            obj = new SubmitButton();
            break;
        case "TextBox":
            obj = new TextBox();
            break;
        case "UrlLink":
            obj = new UrlLink();
            break;
        case "Container":
            obj = new Container();
            break;
        case "Form":
            obj = new Form();
            break;
        case "Frame":
            obj = new Frame();
            break;
        case "List":
            obj = new List();
            break;
        case "Table":
            obj = new Table();
            break;
        case "StandardTable":
            obj = new StandardTable();
            break;
        case "Window":
            obj = new Window();
            break;
    }

    objectCopy(obj, jsobj);

    return obj;
};

UiModule.prototype.parseUiModule = function(json){
    var ulst = JSON.parse(json, null);
    var klst = new Array();
    for(var i=0; i<ulst.length; i++){
        var key = ulst[i].key;
        var obj = ulst[i].obj;
        this.map.put(key, this.buildFromJSON(obj));
        klst.push(key);
    }

    this.buildTree(klst);
};

UiModule.prototype.buildTree = function(keys){
    for(var i=0; i<keys.length; i++){
        var uiobj = this.map.get(keys[i]);
        if(this.root == null){
            this.root = uiobj;
            this.id = uiobj.uid;
        }else{
            var uiid = new Uiid();
            uiid.convertToUiid(uiobj.uid);
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


