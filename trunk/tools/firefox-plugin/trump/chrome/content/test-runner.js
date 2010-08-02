
function TestRunner(){
    this.cmdExecutor = new TelluriumCommand();
    this.app = null;
}

TestRunner.prototype.run = function(app) {
    this.app = app;
    var pages = app.pages;
    if (pages != null && pages.length > 0) {
        var cmd = pages[0].commandList[0];
        if (cmd.name == "open") {
            this.open(cmd.value);
            var uim = pages[0].uim;
            uim.doc = this.getMostRecentDocument();
            this.cmdExecutor.dom = uim.doc;
            this.cmdExecutor.cacheUiModule(uim);
            this.cmdExecutor.locateUI(uim.id);
            for(var i=1; i<pages[0].commandList.length; i++){
                var command = pages[0].commandList[i];
                this.cmdExecutor.run(command.name, command.ref, command.value);
            }
        }
    }
};

TestRunner.prototype.getUiModule = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    return this.app.getUiModule(first);
};

TestRunner.prototype.getMostRecentDocument = function(){
    var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
            .getService(Components.interfaces.nsIWindowMediator)
            .getMostRecentWindow("navigator:browser");

    var browser = win.getBrowser();

    return browser.contentDocument;
};

TestRunner.prototype.open = function(url){
    var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
            .getService(Components.interfaces.nsIWindowMediator)
            .getMostRecentWindow("navigator:browser");

    win.open(url);
    
/*    try {
        var gBrowser = win.getBrowser();
        //        var gBrowser = window.opener.getBrowser();
        gBrowser.selectedTab = gBrowser.addTab(url);
    }
    catch (e) {
        //        window.open(url);
        win.open(url);
    }*/
};

