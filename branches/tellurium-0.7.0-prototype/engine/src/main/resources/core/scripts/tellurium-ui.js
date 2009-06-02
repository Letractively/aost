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

    //dom reference
    this.domRef = null;
};


var Button = classCreate();
objectExtend(Button.prototype, UiObject.prototype, {
    uiType: 'Button',
    tag: "input"
});

var CheckBox = classCreate();
objectExtend(CheckBox.prototype, UiObject.prototype, {
    uiType: 'CheckBox',
    tag: "input",
    type: "checkbox"
});

var Div = classCreate();
objectExtend(Div.prototype, UiObject.prototype, {
    uiType: 'div',
    tag: "div"
});

var Icon = classCreate();
objectExtend(Icon.prototype, UiObject.prototype, {
    uiType: 'Icon'
});

var Image = classCreate();
objectExtend(Image.prototype, UiObject.prototype, {
    uiType: 'Image',
    tag: "img"
});

var InputBox = classCreate();
objectExtend(InputBox.prototype, UiObject.prototype, {
    uiType: 'InputBox',
    tag: "input"
});

var RadioButton = classCreate();
objectExtend(RadioButton.prototype, UiObject.prototype, {
    uiType: 'RadioButton',
    tag: "input",
    type: "radio"
});

var Selector = classCreate();
objectExtend(Selector.prototype, UiObject.prototype, {
    uiType: 'Selector',
    tag: "select"
});

var Span = classCreate();
objectExtend(Span.prototype, UiObject.prototype, {
    uiType: 'Span',
    tag: "span"
});

var SubmitButton = classCreate();
objectExtend(SubmitButton.prototype, Button.prototype, {
    uiType: 'SubmitButton',
    type: "submit"
});

var TextBox = classCreate();
objectExtend(TextBox.prototype, UiObject.prototype, {
    uiType: 'TextBox'
});

var UrlLink = classCreate();
objectExtend(UrlLink.prototype, UiObject.prototype, {
    uiType: 'UrlLink',
    tag: "a"
});

var Container = classCreate();
objectExtend(Container.prototype, UiObject.prototype, {
    uiType: 'Container',
    group: "false",
    components: []
});

var Form = classCreate();
objectExtend(Form.prototype, Container.prototype, {
    uiType: 'Form',
    tag: "form"
});

var Frame = classCreate();
objectExtend(Frame.prototype, Container.prototype, {
    uiType: 'Frame',
    id: null,
    name: null,
    title: null
});

var List = classCreate();
objectExtend(List.prototype, Container.prototype, {
    uiType: 'List',
    separator: null
});

var Table  = classCreate();
objectExtend(Table.prototype, Container.prototype, {
    uiType: 'Table',
    tag: "table",
    headers: []
});

var StandardTable  = classCreate();
objectExtend(StandardTable.prototype, Container.prototype, {
    uiType: 'StandardTable',
    tag: "table",
    headers: [],
    foots: []
});

var Window  = classCreate();
objectExtend(Window.prototype, Container.prototype, {
    uiType: 'Window',
    id: null,
    name: null,
    title: null
});