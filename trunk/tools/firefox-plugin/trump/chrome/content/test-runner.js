const TestState = {
    NOTSTART: "NotStart",
    RUNNING: "Running",
    SUCCEED: "Succeed",
    FAILED: "Failed"
};

var TestObserver = Class.extend({
    init: function() {

    },

    cmdStart: function(cmd) {

    },

    cmdSucceed: function(cmd) {

    },

    cmdFailed: function(cmd) {

    }
});

var TestObserverList = TestObserver.extend({
    init: function() {
        this._super();
        this.chain = new Array();
    },

    removeAll: function() {
        this.chain = new Array();
    },

    addObserver: function(observer) {
        this.chain.push(observer);
    },

    size: function() {
        return this.chain.length;
    },

    cmdStart: function(cmd) {
        for (var i = 0; i < this.chain.length; i++) {
            this.chain[i].cmdStart(cmd);
        }
    },

    cmdSucceed: function(cmd) {
        for (var i = 0; i < this.chain.length; i++) {
            this.chain[i].cmdSucceed(cmd);
        }
    },

    cmdFailed: function(cmd) {
        for (var i = 0; i < this.chain.length; i++) {
            this.chain[i].cmdFailed(cmd);
        }
    }
});

var TestRunner = Class.extend({
    init: function() {
        this.running = false;
        this.cmdExecutor = new TelluriumCommand();
        this.commandList = null;
        this.app = null;
        //current command index
        this.currentIndex = -1;
        this.interval = 300;
        this.observers = new TestObserverList();
    },

    prepareFor: function(app) {
        this.app = app;
        this.commandList = this.app.getCommandList();
        this.currentIndex = -1;
        this.running = false;
    },

    addObserver: function(observer) {
        this.observers.addObserver(observer);
    },

    start: function(){
        this.running = true;    
    },

    pause: function() {
        this.running = false;
    },

    restart: function() {
        this.running = false;
        this.currentIndex = -1;
    },

    step: function() {
        if (this.currentIndex < this.commandList.length) {
            this.currentIndex++;
            var cmd = this.commandList[this.currentIndex];
            this.runCommand(cmd);
        } else {
            logger.info("No remaining tests to run.");
        }
    },

    //run a single command
    runCommand: function(cmd) {
        try {
            logger.debug("Running test: " + cmd.seq);
            this.observers.cmdStart(cmd);
            this.cmdExecutor.run(cmd.name, cmd.uid, cmd.value);
            this.observers.cmdSucceed(cmd);
        } catch(error) {
            this.observers.cmdFailed(cmd);
            logger.error("Failed to run command " + cmd.seq + ":\n" + describeErrorStack(error));
        }
    },

    delayedRun: function(){
        if(this.running){
            if(this.currentIndex < this.commandList.length-1){
                this.currentIndex++;
                this.runCommand(this.commandList[this.currentIndex]);
            }

            if(this.currentIndex < this.commandList.length-1){
                var self = this;
                setTimeout (TestJob, this.interval, self);
            }else{
                this.finish();
            }
        }        
    },

    finish: function(){
        var runButton = document.getElementById("run-button");
        runButton.removeAttribute("checked");
        runButton.setAttribute("class", "run");
    },

    run: function() {
        this.delayedRun();
    },
        /*
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
         */
    getUiModule: function(uid) {

        return this.app.getUiModule(uid);
    },

    getMostRecentDocument: function() {
        var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
                .getService(Components.interfaces.nsIWindowMediator)
                .getMostRecentWindow("navigator:browser");

        var browser = win.getBrowser();

        return browser.contentDocument;
    }
});

function TestJob(scope){
    scope.delayedRun();
}