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

var TestResultObserver = TestObserver.extend({
    init: function(doc) {
        this.document = doc;
    },

    cmdStart: function(cmd) {
        document.getElementById("commandReturnResult").value = "";
    },

    cmdSucceed: function(cmd) {
        if(cmd.returnValue != undefined){
            var value = cmd.returnValue;
            if(typeof(value) == "object" && value["toString"] != undefined){
                value = value.toString();
            }
            document.getElementById("commandReturnResult").value = value;
        }
    },

    cmdFailed: function(cmd) {

    },

    clear: function(){
        document.getElementById("commandReturnResult").value = "";
    }
});

var TestRunner = Class.extend({
    init: function() {
        this.running = false;
        this.cmdExecutor = tellurium; 
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

    updateDom: function(){
        this.cmdExecutor.updateCurrentDom();
    },

    prepareFor: function(app) {
        this.app = app;
        this.commandList = this.app.getCommandList();
        this.uimMap = this.app.uimMap;
        this.currentIndex = -1;
        this.running = false;
    },

    getVariableValue: function(variable){
        if(this.commandList != null && this.commandList.length > 0){
            for(var i=0; i<this.commandList.length; i++){
                var cmd = this.commandList[i];
                if(cmd.returnVariable == variable){
                    return cmd.returnValue;
                }
            }
        }

        return null;
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
            logger.debug("Running test [name: " + cmd.name + ", uid: " + cmd.target + ", value: " + cmd.value + "]");
            this.observers.cmdStart(cmd);
            var tcmd = this.cmdExecutor.getCommand(cmd.name);

            var value = cmd.value;
            if(cmd.value != null && cmd.valueType == ValueType.VARIABLE){
                value = this.getVariableValue(cmd.value);
            }
            var target;
            var args = [];
            //only allow assertions to use variables
            if (tcmd.type == CommandType.ASSERTION) {
                //replace the variable with its value
                if(cmd.targetType == TargetType.VARIABLE){
                    target = this.getVariableValue(cmd.target);
                }else{
                    target = cmd.target;
                }
                args.push(target);
                args.push(value);
            }else if(tcmd.type == CommandType.NoUid){
                if(cmd.targetType != TargetType.NIL){
                    args.push(target);
                }
                if(cmd.valueType != ValueType.NIL){
                    args.push(value);
                }
            }else {
                if (cmd.target != null && cmd.targetType == TargetType.UID) {
                    this.useUiModule(cmd.target);
                }
                target = cmd.target;
                args.push(target);
                if(cmd.valueType != ValueType.NIL){
                    args.push(value);
                }
            }
//            var result = this.cmdExecutor.run(cmd.name, target, value);
            var result = this.cmdExecutor.runCmd(cmd.name, args);

            if(result != undefined){
                logger.debug("Command Result: " + result);
                cmd.returnValue = result;
            }
            this.observers.cmdSucceed(cmd);
        } catch(error) {
            this.observers.cmdFailed(cmd);
            logger.error("Failed to run command [name: " + cmd.name + ", uid: " + cmd.target + ", value: " + cmd.value + "]:\n" + describeErrorStack(error));
        }
    },

    delayedRun: function(){
        if (this.running) {
            var self = this;
            if (this.currentIndex < this.commandList.length - 1) {
                var cmd = this.commandList[this.currentIndex + 1];

                if (cmd.name == "open" || cmd.name.startsWith("waitFor")) {
                    this.currentIndex++;
                    this.runCommand(this.commandList[this.currentIndex]);
                    if (this.currentIndex < this.commandList.length - 1) {
                        setTimeout(TestJob, this.interval, self);
                    } else {
                        this.finish();
                    }

                } else {
//                    logger.debug("run one command: browserBot.newPageLoaded " + browserBot.newPageLoaded);

                    if (browserBot.newPageLoaded) {
                        this.currentIndex++;
                        this.runCommand(this.commandList[this.currentIndex]);
                        if (this.currentIndex < this.commandList.length - 1) {
                            setTimeout(TestJob, this.interval, self);
                        } else {
                            this.finish();
                        }

                    } else {
                        if (browserBot.pageLoadError == null) {
                            setTimeout(TestJob, this.interval, self);
                        } else {
                            logger.error("Stop Running tests because of page load error: " + browserBot.pageLoadError);
                        }
                    }

                }
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

    getUiModule: function(uid) {

        return this.app.getUiModule(uid);
    }
});

function TestJob(scope){
    scope.delayedRun();
}