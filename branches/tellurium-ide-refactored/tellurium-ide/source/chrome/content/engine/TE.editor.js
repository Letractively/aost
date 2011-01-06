TE.ns("TE.editor");
TE.editor.Editor = function(window){
    this.window = window;
//    var self = this;
    
    window.editor = this;
//    window.browserBot = browserBot;
    window.browserBot = tellurium.browserBot;

    this.document = document;
    
    this.logView = new LogView(this);
    this.logView.setLog(logger);
    
    this.buildCustomizeTree(DEFAULT_XML);

    this.currentUid = null;

    this.decorator = new Decorator();

    this.builder = new UiBuilder();

    this.refIdSetter = new RefIdSetter();

    this.workspace = new Workspace(this.builder, new UiChecker(), this.refIdSetter);

    this.recorder = null;

    this.recordingEnabled = true;

    this.registerRecorder();

    this.cmdHistory = new Array();

    this.cmdView = CommandView;
    this.cmdTree = document.getElementById('recordedCommandListTree');
    this.cmdTree.view = this.cmdView;

    this.currentSelectedCommand = null;
//    this.currentCommandIndex = -1;

    //Map the command, because some command needs to convert the format for display, for example, array to String
    this.commandMap = new Hashtable();

    this.testResultObserver = new TestResultObserver(document);

    this.testRunner = new TestRunner();
    this.testRunner.addObserver(this.cmdView);
    this.testRunner.addObserver(this.testResultObserver);
    this.commandList = this.testRunner.cmdExecutor.getCommandList();
    this.workspace.cmdExecutor = this.testRunner.cmdExecutor;
    
//    this.options = new Preferences();

    //Detect the browser properties
    BrowserDetect.init();
    this.os = BrowserDetect.OS;

}
TE.merge(TE.editor.Editor.prototype,function(){
    onDOMContentLoaded:function(event){
        try {       
            var recordToolbarButton = document.getElementById("record-button");
            if (recordToolbarButton.getAttribute("checked")) {
                logger.debug("Register window on DOMContentLoaded");
                this.recorder.registerForWindow(event.target.defaultView);
            } else {
                this.testRunner.updateDom();
            }
        } 
        catch(error) {
            logger.error("Error processing onDOMContentLoaded:\n" + describeErrorStack(error));
        }
    }
})
