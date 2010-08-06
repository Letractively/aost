const TestState = {
    READY: " ",
    RUNNING: 'Running',
    SUCCEED: 'Passed',
    FAILED: 'Failed'
};

var TestObserver = Class.extend({
    init: function() {

    },

    cmdStart: function(cmd) {

    },

    cmdSucceed: function(cmd) {

    },

    cmdFailed: function(cmd) {

    },

    clear: function(){

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
    },

    clear: function(){
        for (var i = 0; i < this.chain.length; i++) {
            this.chain[i].clear();
        }
    }
});

var TestRunner = Class.extend({
    init: function() {
        this.running = false;
        this.cmdExecutor = new TelluriumCommandExecutor();
        this.commandList = null;
        this.app = null;
        this.uimMap = null;
        //current command index
        this.currentIndex = -1;
        this.interval = 1000;
        this.observers = new TestObserverList();
    },

    updateInterval: function(interval){
        if(interval != undefined && interval > 0){
            this.interval = interval;
        }
    },

    prepareFor: function(app) {
        this.app = app;
        this.commandList = this.app.getCommandList();
        this.uimMap = this.app.map;
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
        this.cmdExecutor.clearCache();
        this.observers.clear();
    },

    step: function() {
        if (this.currentIndex < this.commandList.length-1) {
            this.currentIndex++;
            var cmd = this.commandList[this.currentIndex];
            this.runCommand(cmd);
        } else {
            logger.info("No remaining tests to run.");
        }
    },

    useUiModule: function(uid){
        var uiid = new Uiid();
        uiid.convertToUiid(uid);

        var first = uiid.peek();
        var uim = this.cmdExecutor.getCachedUiModule(first);
        if(uim == null){
            uim = this.uimMap.get(first);
            this.cmdExecutor.cacheUiModule(uim);
            this.cmdExecutor.locateUI(first);
        }else{
            if(!uim.valid){
                this.cmdExecutor.locateUI(first);
            }
        }
    },

    updateUiModule: function(uid){
        var uiid = new Uiid();
        uiid.convertToUiid(uid);

        var first = uiid.peek();
        var uim = this.cmdExecutor.getCachedUiModule(first);
        if(uim != null){
            //remove it so that it will get automatically cached and located for the next command
            this.cmdExecutor.removeCachedUiModule(first);
        }
    },

    //run a single command
    runCommand: function(cmd) {
        try {
            logger.debug("Running test [name: " + cmd.name + ", uid: " + cmd.uid + ", value: " + cmd.value + "]");
            this.observers.cmdStart(cmd);
            if(cmd.uid != null){
                this.useUiModule(cmd.uid);
            }
            this.cmdExecutor.run(cmd.name, cmd.uid, cmd.value);
            this.observers.cmdSucceed(cmd);
        } catch(error) {
            this.observers.cmdFailed(cmd);
            logger.error("Failed to run command [name: " + cmd.name + ", uid: " + cmd.uid + ", value: " + cmd.value + "]:\n" + describeErrorStack(error));
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
    }
});

function TestJob(scope){
    scope.delayedRun();
}