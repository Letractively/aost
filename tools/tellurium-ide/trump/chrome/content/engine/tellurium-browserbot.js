const PageLoadError = {
    TIMEOUT: "timeout"
};

var BrowserBot = Class.extend({
    init: function() {
        this.currentWindow = null;
        this.currentWindowName = null;
        this.newPageLoaded = false;
        this.pageLoadError = null;
        this.defaultTimeout = 30000;
        this.pagePollTimeout = 500;
        this.pageTimeoutTimerId = null;
        this.pagePollTimerId = null;
    },

    getCurrentWindow: function(){
        return this.currentWindow;    
    },

    setCurrentWindow: function(window){
        this.currentWindow = window;
    },

    setCurrentWindowToMostRecentWindow: function(){
        this.currentWindow = this.getMostRecentWindow();
    },

    checkReadyState: function() {
        try{
            var doc = this.getMostRecentDocument();
            if (doc.readyState == "complete") {
                clearInterval(this.pagePollTimerId);
                this.pagePollTimerId = null;
                this.newPageLoaded = true;
                this.pageLoadError = null;
            }            
        }catch(error) {
            logger.error("Error:\n" + describeErrorStack(error));
        }
    },

    pollPageLoad: function(){
        var self = this;
        this.pagePollTimerId = setInterval(self.checkReadyState, self.pagePollTimeout);
    },

    openNewWindow:  function(uid, url) {
        var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
                .getService(Components.interfaces.nsIWindowMediator)
                .getMostRecentWindow("navigator:browser");

        win.open(url);
    },

    getMostRecentDocument: function() {
        var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
                .getService(Components.interfaces.nsIWindowMediator)
                .getMostRecentWindow("navigator:browser");

        return win.getBrowser().contentDocument;
    },

    getMostRecentWindow: function() {
        var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
                .getService(Components.interfaces.nsIWindowMediator)
                .getMostRecentWindow("navigator:browser");

        return win.getBrowser().contentWindow;
    },

    showInBrowser: function(url) {
        var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
        var window = wm.getMostRecentWindow('navigator:browser');
        var contentWindow = window.getBrowser().contentWindow;
        contentWindow.location.href = url;
    },

    getMainWindow: function() {
        var mainWindow = window.QueryInterface(Components.interfaces.nsIInterfaceRequestor)
                .getInterface(Components.interfaces.nsIWebNavigation)
                .QueryInterface(Components.interfaces.nsIDocShellTreeItem)
                .rootTreeItem
                .QueryInterface(Components.interfaces.nsIInterfaceRequestor)
                .getInterface(Components.interfaces.nsIDOMWindow);

        return mainWindow;
    },

    getAllWindows: function() {
        var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
        var e = wm.getEnumerator("navigator:browser");
        var wins = new Array();
        while (e.hasMoreElements()) {
            var window = e.getNext();
            var browsers = window.getBrowser().browsers;
            for (var i = 0; i < browsers.length; i++) {
                wins.push(browsers[i].contentWindow);
            }
        }

        return wins;
    }
});

var browserBot = new BrowserBot();