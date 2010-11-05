function TelluriumApi(){
    this.cmdExecutor = new TelluriumCommandExecutor();
}


TelluriumApi.prototype.assertTrue = function(variable){
    this.cmdExecutor.run("assertTrue", null, variable);
};

TelluriumApi.prototype.assertFalse = function(variable){
    this.cmdExecutor.run("assertFalse", null, variable);
};

TelluriumApi.prototype.assertEquals = function(variable1, variable2){
    this.cmdExecutor.run("assertEquals", variable1, variable2);
};

TelluriumApi.prototype.assertNotEquals = function(variable1, variable2){
    this.cmdExecutor.run("assertNotEquals", variable1, variable2);
};

TelluriumApi.prototype.assertNull = function(variable){
    this.cmdExecutor.run("assertNull", null, variable);
};

TelluriumApi.prototype.assertNotNull = function(variable){
    this.cmdExecutor.run("assertNotNull", null, variable);
};

TelluriumApi.prototype.blur = function(uid) {
    this.cmdExecutor.run("blur", uid, null);
};

TelluriumApi.prototype.click = function(uid) {
    this.cmdExecutor.run("click", uid, null);
};

TelluriumApi.prototype.clickAt = function(uid, coordString) {
    this.cmdExecutor.run("clickAt", uid, coordString);
};

TelluriumApi.prototype.doubleClick = function(uid){
    this.cmdExecutor.run("doubleClick", uid, null);
};

TelluriumApi.prototype.fireEvent = function(uid, event){
    this.cmdExecutor.run("fireEvent", uid, event);
};

TelluriumApi.prototype.focus = function(uid){
    this.cmdExecutor.run("focus", uid, null);
};

TelluriumApi.prototype.type = function(uid, val){
    this.cmdExecutor.run("type", uid, val);
};

TelluriumApi.prototype.typeKey = function(uid, key){
    this.cmdExecutor.run("typeKey", uid, key);
};

TelluriumApi.prototype.keyDown = function(uid, key){
    this.cmdExecutor.run("keyDown", uid, key);
};

TelluriumApi.prototype.keyPress = function(uid, key){
    this.cmdExecutor.run("keyPress", uid, key);
};

TelluriumApi.prototype.keyUp = function(uid, key){
    this.cmdExecutor.run("keyUp", uid, key);
};

TelluriumApi.prototype.mouseOver = function(uid){
    this.cmdExecutor.run("mouseOver", uid, null);
};

TelluriumApi.prototype.mouseDown = function(uid){
    this.cmdExecutor.run("mouseDown", uid, null);
};

TelluriumApi.prototype.mouseEnter = function(uid){
    this.cmdExecutor.run("mouseEnter", uid, null);
};

TelluriumApi.prototype.mouseLeave = function(uid){
    this.cmdExecutor.run("mouseLeave", uid, null);
};

TelluriumApi.prototype.mouseOut = function(uid){
    this.cmdExecutor.run("mouseOut", uid, null);
};

TelluriumApi.prototype.submit = function(uid){
    this.cmdExecutor.run("submit", uid, null);
};

TelluriumApi.prototype.check = function(uid){
    this.cmdExecutor.run("check", uid, null);
};

TelluriumApi.prototype.uncheck = function(uid){
    this.cmdExecutor.run("uncheck", uid, null);
};

TelluriumApi.prototype.getAttribute = function(uid, attribute){
    return this.cmdExecutor.run("getAttribute", uid, attribute);
};

TelluriumApi.prototype.select = function(uid, option){
    this.cmdExecutor.run("select", uid, option);
};

TelluriumApi.prototype.selectByLabel = function(uid, option){
    this.cmdExecutor.run("selectByLabel", uid, option);
};

TelluriumApi.prototype.selectByIndex = function(uid, option){
    this.cmdExecutor.run("selectByIndex", uid, option);
};

TelluriumApi.prototype.selectByValue = function(uid, option){
    this.cmdExecutor.run("selectByValue", uid, option);
};

TelluriumApi.prototype.getText = function(uid) {
    return this.cmdExecutor.run("getText", uid, null);
};

TelluriumApi.prototype.getValue = function(uid) {
    return this.cmdExecutor.run("getValue", uid, null);
};

TelluriumApi.prototype.isChecked = function(uid) {
    return this.cmdExecutor.run("isChecked", uid, null);
};

TelluriumApi.prototype.isVisible = function(uid) {
    return this.cmdExecutor.run("isVisible", uid, null);
};

TelluriumApi.prototype.getCSS = function(uid, cssName) {
    return this.cmdExecutor.run("getCSS", uid, cssName);
};

TelluriumApi.prototype.getCSSAsString = function(uid, cssName) {

    return this.cmdExecutor.run("getCSSAsString", uid, cssName);
};

TelluriumApi.prototype.isDisabled = function(uid) {
    return this.cmdExecutor.run("isDisabled", uid, null);
};

TelluriumApi.prototype.showUI = function(uid) {
    this.cmdExecutor.run("showUI", uid, null);
};

TelluriumApi.prototype.cleanUI = function(uid) {
    this.cmdExecutor.run("cleanUI", uid, null);
};

TelluriumApi.prototype.getHTMLSource = function(uid) {
    return this.cmdExecutor.run("getHTMLSource", uid, null);
};

TelluriumApi.prototype.getHTMLSourceAsString = function(uid) {
    return this.cmdExecutor.run("getHTMLSourceAsString", uid, null);
};

TelluriumApi.prototype.getUids = function(uid) {
    return this.cmdExecutor.run("getUids", uid, null);
};

TelluriumApi.prototype.getUidsAsString = function(uid){
    return this.cmdExecutor.run("getUidsAsString", uid, null);
};

TelluriumApi.prototype.getCssSelectorCount = function(uid, sel){
    return this.cmdExecutor.run("getCssSelectorCount", uid, sel);
};

TelluriumApi.prototype.getCssSelectorMatch = function(uid, sel){
    return this.cmdExecutor.run("getCssSelectorMatch", uid, sel);
};

TelluriumApi.prototype.getCssSelectorMatchAsString = function(uid, sel){
    return this.cmdExecutor.run("getCssSelectorMatchAsString", uid, sel); 
};

TelluriumApi.prototype.validateUiModule = function(uid, jsonString){
    return this.cmdExecutor.run("validateUiModule", uid, jsonString);
};

TelluriumApi.prototype.validateUiModuleAsString = function(uid,jsonString){
    return this.cmdExecutor.run("validateUiModuleAsString", uid, jsonString);
};

TelluriumApi.prototype.useUiModule = function(uid, jsonString){
    this.cmdExecutor.run("useUiModule", uid, jsonString)
};

TelluriumApi.prototype.toJSON = function(uid){
    return this.cmdExecutor.run("toJSON", uid, null);
};

TelluriumApi.prototype.toJSONString = function(uid){
    return this.cmdExecutor.run("toJSONString", uid, null);
};

TelluriumApi.prototype.waitForPageToLoad = function(timeout){
    this.cmdExecutor.run("waitForPageToLoad", null, timeout);
};

TelluriumApi.prototype.open = function(url){
    this.cmdExecutor.run("open", null, url)
};