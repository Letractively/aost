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

    //namespace, useful for XML, XHTML, XForms
    this.namespace = null;
    
    this.locator = null;

    //event this object should be respond to
    this.events = null;

    //Tellurium Core generated locator for this UI Object
    this.generated = null;

    //dom reference
    this.domRef = null;
};

UiObject.prototype.walkToPlace = function(uiid, uiobj){

    var ouid = uiid.pop();
    objectCopy(this, uiobj);
    if(uiid.length > 0){
        alert("Wrong uiid " + ouid);
    }
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
    group: "false",
    components: {},

    walkToPlace:  function(uiid, uiobj) {

        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.length > 0) {
            var cuid = uiid.peek();
            var child = this.components[cuid];
            if (child != null) {
                child.walkToPlace(uiid, uiobj);
            } else {
                this.components.push(cuid, uiobj);
            }
        }
    }
});

/*
Container.prototype.walkToPlace = function(uiid, uiobj){

    var suid = uiid.pop();
    if(this.uid == null)
        objectCopy(this, uiobj);
    
    if(uiid.length > 0){
        var cuid = uiid.peek();
        var child = this.components[cuid];
        if(child != null){
            child.walkToPlace(uiid, uiobj);
        }else{
            this.components.push(cuid, uiobj);
        }
    }
};
*/


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
    separator: null
});

var Table  = classCreate();
objectExtends(Table.prototype, Container.prototype, {
    uiType: 'Table',
    tag: "table",
    headers: {},
    
    walkToPlace:  function(uiid, uiobj) {

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
                    child.walkToPlace(uiid, uiobj);
                } else {
                    this.headers.push(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components[cuid];
                if (child != null) {
                    child.walkToPlace(uiid, uiobj);
                } else {
                    this.components.push(cuid, uiobj);
                }
            }
        }
    }
});

var StandardTable  = classCreate();
objectExtends(StandardTable.prototype, Container.prototype, {
    uiType: 'StandardTable',
    tag: "table",
    headers: {},
    foots: {},

    walkToPlace:  function(uiid, uiobj) {

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
                    child.walkToPlace(uiid, uiobj);
                } else {
                    this.headers.push(cuid, uiobj);
                }
            }else if(cuid == "foot"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.foots[cuid];
                if (child != null) {
                    child.walkToPlace(uiid, uiobj);
                } else {
                    this.foots.push(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components[cuid];
                if (child != null) {
                    child.walkToPlace(uiid, uiobj);
                } else {
                    this.components.push(cuid, uiobj);
                }
            }
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
            this.root.walkToPlace(uiid, uiobj);
        }
    }
};


