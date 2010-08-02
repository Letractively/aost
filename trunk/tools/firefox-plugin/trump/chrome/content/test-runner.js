
function TestRunner(){
    this.cmdExecutor = new TelluriumCommand();
    this.app = null;
}

TestRunner.prototype.run = function(app){
    this.app = app;

};

TestRunner.prototype.getUiModule = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    return this.app.getUiModule(first);
};

