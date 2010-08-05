
function UiPage(){
    this.window = null;

    //UI Module
    this.uim = null;

    //Root DOM
    this.dom = null;

    this.commandList = null;
}

function App(){
    this.pages = new Array();
    this.map = new Hashtable();
    this.cmdIndex = new Hashtable();
    this.uiAlg = new UiAlg();
    this.refUidMap = null;
}

App.prototype.clearAll = function(){
    this.pages = new Array();
    this.map = new Hashtable();
    this.cmdIndex = new Hashtable();
    this.refUidMap = null;
};

App.prototype.updateCommand = function(cmd){
    if(cmd != null){
        var command = this.cmdIndex.get(cmd.seq);
        command.name = cmd.name;
        command.uid = cmd.uid;
        command.value = cmd.value;
    }
};

App.prototype.updateCommandList = function() {
    if (this.pages != null && this.pages.length > 0) {
        for (var i = 0; i < this.pages.length; i++) {
            var commandList = this.pages[i].commandList;
            if (commandList != null && commandList.length > 0) {
                for (var j = 0; j < commandList.length; j++) {
                    var cmd = commandList[j];
                    if (this.refUidMap != null && cmd.ref != null) {
                        var uid = this.refUidMap.get(cmd.ref);
                        if (uid != null && cmd.uid != uid) {
                            cmd.uid = uid;
                        }
                    }
                }
            }
        }
    }
};

App.prototype.getCommandList = function(){
    var list = new Array();
    if(this.pages != null && this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            var commandList = this.pages[i].commandList;
            if(commandList != null && commandList.length > 0){
                for(var j=0; j<commandList.length; j++){
                    var uiCmd = commandList[j];
                    this.cmdIndex.put(uiCmd.seq, uiCmd);
                    var cmd = new TeCommand(uiCmd.seq, uiCmd.name, uiCmd.uid, uiCmd.value, uiCmd.ref);
                    list.push(cmd);
                }
            }
        }
    }

    return list;
};

App.prototype.getRefUidMapFor = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.map.get(first);
    this.refUidMap = uim.buildRefUidMap();

    return this.refUidMap;
};

App.prototype.getUids = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.map.get(first);
//    uid = first;
//    uiid.convertToUiid(uid);
    if (uim != null) {
        var obj = uim.walkTo(context, uiid);
        if (obj != null) {
            var stree = this.uiAlg.buildSTree(uim);
            var visitor = new UiUIDVisitor();
            stree.traverse(context, visitor);

            return visitor.uids;
        } else {
            logger.error("Cannot find UI object " + uid);
            throw new TelluriumError(ErrorCodes.UI_OBJ_NOT_FOUND, "Cannot find UI object " + uid);
        }
    } else {
        logger.error("Cannot find UI Module " + uid);
        throw new TelluriumError(ErrorCodes.UI_MODULE_IS_NULL, "Cannot find UI Module " + uid);
    }
};

App.prototype.isEmpty = function(){
    return this.pages.length == 0;
};

App.prototype.getUiModule = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    return this.map.get(first);
};

App.prototype.getUiModules = function(){
    return this.map.valSet();
};

App.prototype.updateUiModule = function(oldId, uim){
    this.map.remove(oldId);
    this.map.put(uim.id, uim);
};

App.prototype.walkToUiObject = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    var uim = this.map.get(first);

    if(uim != null){
        return uim.walkTo(new WorkflowContext(), uiid);
    }
    
    return null;
};

App.prototype.savePage = function(window, uim, dom, commandList){
    var page = new UiPage();
    page.window = window;
    page.uim = uim;
    page.dom = dom;
    page.commandList = commandList;

    this.pages.push(page);
    if(uim != null)
        this.map.put(uim.id, uim);
};

App.prototype.toSource = function(){
    var code = "";
    if(this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            if(this.pages[i].uim != null){
                code = code + this.describeUiModule(this.pages[i].uim) + "\n";
            }
        }

        for(var j=0; j<this.pages.length; j++){
            code = code + this.describeCommand(this.pages[j].commandList) + "\n";
        }
    }

    return code;
};

App.prototype.describeCommand = function(commandList){
    var sb = new StringBuffer();
    if(commandList != null && commandList.length > 0){
        for(var i=0; i<commandList.length; i++){
            var cmd = commandList[i];
            sb.append("\t\t").append(cmd.name);
            if(cmd.uid != null && cmd.uid != undefined){
//                sb.append(" \"").append(cmd.ref).append("\"");
                sb.append(" ").append(cmd.uid);
            }
            if(cmd.value != null && cmd.value != undefined){
                if(cmd.uid != null && cmd.uid != undefined){
                    sb.append(",");
                }
//                sb.append(" \"").append(cmd.value).append("\"");
                sb.append(" ").append(cmd.value);
            }
            sb.append("\n");
        }
    }

    return sb.toString();
};

App.prototype.describeUiModule = function(uim) {
    var visitor = new StringifyVisitor();
    uim.around(visitor);
    var uiModelArray = visitor.out;
    if (uiModelArray != undefined && uiModelArray != null) {
        var sb = new StringBuffer();
        for (var i = 0; i < uiModelArray.length; ++i) {
            if (i == 0) {
                sb.append("\t\tui." + uiModelArray[i].replace(/^\s+/, ''));
            } else {
                sb.append("\t\t" + uiModelArray[i]);
            }
        }

        return sb.toString();
    }

    return "";
};
     
