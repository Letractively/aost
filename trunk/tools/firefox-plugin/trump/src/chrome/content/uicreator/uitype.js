function UiType() {
    this.constants = {
        INPUT : "input",
        TYPE : "type",
        CHECKBOX : "checkbox",
        RADIO : "radio",
        SUBMIT : "submit"
    }

    this.map = new HashMap();
    this.map.set("divN", "DIV");
    this.map.set("divY", "Container");
    this.map.set("aN", "UrlLink");
    this.map.set("inputN", "InputBox");
    this.map.set("imgN", "Image");
    this.map.set("selectN", "Selector");
    this.map.set("formN", "Form");
    this.map.set("formY", "Form");
//    this.map.set("tableN", "Table");
//    this.map.set("tableY", "Table");
    //for table, use Container for the timebeing until we can do the post processing to handle UI templates
    this.map.set("tableN", "Container");
    this.map.set("tableY", "Container");}

UiType.prototype.getType = function(tag, hasChildren) {
    return this.getTypeWithExtra(tag, null, hasChildren);
}

UiType.prototype.getTypeWithExtra = function(tag, extra, hasChildren) {
    var addition = "N";
    if (hasChildren) {
        addition = "Y";
    }
    var uitype = this.map.get(tag + addition);
    //        alert("uitype : " + uitype) ;
    if (this.map.get(tag + addition) == null) {
        if (hasChildren) {
            uitype = "Container";
        }
        else {
            uitype = "TextBox";
        }
    }

    if (this.constants.INPUT == tag && extra != null) {
        //            alert("extra : " + extra);
        var type = extra.get(this.constants.TYPE);
        if (type != null) {
            if (this.constants.CHECKBOX == type) {
                uitype = "CheckBox";
            } else if (this.constants.RADIO == type) {
                uitype = "RadioButton";
            } else if (this.constants.SUBMIT == type) {
                uitype = "SubmitButton";
            }
        }
    }

    return uitype;
}