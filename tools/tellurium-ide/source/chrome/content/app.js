
function UiPage(){
    this.window = null;

    //UI Module
    this.uim = null;

    //Root DOM
    this.dom = null;

    this.commandList = null;

}

UiPage.prototype.insertFirst = function(cmd){
    if(this.commandList !== null){
        this.commandList.splice(0, 0, cmd);
    }
};

UiPage.prototype.getIndex = function(cmd){
    if(this.commandList !== null){
        for(var i=0; i<this.commandList.length; i++){
            if(this.commandList[i].seq == cmd.seq){
                return i;
            }
        }
    }

    return -1;
};

UiPage.prototype.removeCommand = function(index){
    if(this.commandList !== null){
        this.commandList.splice(index, 1);
    }
};

UiPage.prototype.insertAt = function(index, cmd){
    if(this.commandList !== null){
        this.commandList.splice(index, 0, cmd);
    }
};

UiPage.prototype.commandSize = function(){
    return this.commandList === null ? 0 : this.commandList.length;
};

const SourceLanguage = {
    JavaScript: "JavaScript",
    Groovy: "Groovy",
    Java: "Java",
    Ruby: "Ruby"
};

const VariableKeyword = {
    JavaScript: "var",
    Groovy: "def"
};

function SourceFormat(){
    this.language = SourceLanguage.JavaScript;

}

SourceFormat.prototype.getVarKeyword = function(){
    if(this.language == SourceLanguage.JavaScript){
        return VariableKeyword.JavaScript;
    }else if(this.language == SourceLanguage.Groovy){
        return VariableKeyword.Groovy;
    }else{
        throw new Error("Not implemented!");
    }
};

function App(){
    this.pages = new Array();
    this.uimMap = new Hashtable();
    this.cmdIndex = new Hashtable();
    this.uiAlg = new UiAlg();
    this.refUidMap = null;
    this.cmdMap = new Hashtable();
    this.identifier = new Identifier(0);
    this.initCmdMap();
    this.format = new SourceFormat();
}

App.prototype.initCmdMap = function(){
    this.cmdMap.put("open", "connectUrl");
    UiCommand.cmdMap.put("open", "connectUrl");
};

App.prototype.clearAll = function(){
    this.pages = new Array();
    this.uimMap = new Hashtable();
    this.cmdIndex = new Hashtable();
    this.refUidMap = null;
};

App.prototype.findRefFromUid = function(uid){
    if(this.refUidMap !== null && this.refUidMap.size() > 0){
        var keySet = this.refUidMap.keySet();
        for(var i=0; i<keySet.length; i++){
            var key = keySet[i];
            if(this.refUidMap.get(key) == uid){
                return key;
            }
        }
    }

    return null;
};

App.prototype.updateCommand = function(cmd){
    if(cmd !== null){
        var command = this.cmdIndex.get(cmd.seq);
        command.name = cmd.name;
        command.target = cmd.target;
        command.value = cmd.value;
    }
};

App.prototype.updateCommandList = function() {
    if (this.pages !== null && this.pages.length > 0) {
        for (var i = 0; i < this.pages.length; i++) {
            this.updateCommandListForPage(this.pages[i]);
        }
    }
};

App.prototype.updateCommandListForPage = function(page) {
    if (page !== null) {
        var commandList = page.commandList;
        if (commandList !== null && commandList.length > 0) {
            for (var i = 0; i < commandList.length; i++) {
                var cmd = commandList[i];
                if (this.refUidMap !== null && cmd.ref !== null) {
                    var uid = this.refUidMap.get(cmd.ref);
                    if (uid !== null && cmd.target != uid) {
                        cmd.target = uid;
                    }
                }
            }
        }
    }
};

App.prototype.insertCommand = function(prevCmd, cmd){
    if(this.pages !== null && this.pages.length > 0){
        if(prevCmd === null){
            //insert as the first command
            this.pages[0].insertFirst(cmd);
        }else{
            for(var i=0; i<this.pages.length; i++){
                var page = this.pages[i];
                var index = page.getIndex(prevCmd);
                if(index != -1){
                    if(index == page.commandSize()-1){
                        //It is the last command
                        if(i == this.pages.length-1){
                            //It is the last page
                            page.insertAt(index+1, cmd);
                        }else{
                            //put as the first element of the next page
                            this.pages[i+1].insertFirst(cmd);
                        }
                    }else{
                        page.insertAt(index+1, cmd);
                    }
                    break;
                }
            }
        }
        this.cmdIndex.put(cmd.seq, cmd);
    }
};

App.prototype.deleteCommand = function(cmd) {
    if (cmd !== null) {
        this.cmdIndex.remove(cmd.seq);
        for (var i = 0; i < this.pages.length; i++) {
            var page = this.pages[i];
            var index = page.getIndex(cmd);
            if(index != -1){
                page.removeCommand(index);
                break;
            }
        }
    }
};

App.prototype.getCommandList = function(){
    var list = new Array();
    var prevCmd = null;
    var skip = false;
    if(this.pages !== null && this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            var commandList = this.pages[i].commandList;
            if(commandList !== null && commandList.length > 0){
                for(var j=0; j<commandList.length; j++) {
                    var uiCmd = commandList[j];
                    skip = false;
                    if (prevCmd !== null && "waitForPageToLoad" == prevCmd.name && "waitForPageToLoad" == uiCmd.name) {
                        logger.warn("Duplicated waitForPageToLoad, ignore it");
                        skip = true;
                    }
                    if (!skip) {
                        this.cmdIndex.put(uiCmd.seq, uiCmd);
                        list.push(uiCmd);
                        prevCmd = uiCmd;
                    }
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
    var uim = this.uimMap.get(first);
//    this.refUidMap = uim.buildRefUidMap();

//    return this.refUidMap;
    return this.getRefUidMapForUim(uim);
};

App.prototype.getRefUidMapForUim = function(uim){
    this.refUidMap = uim.buildRefUidMap();

    return this.refUidMap;
};

App.prototype.getUids = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();
    var uim = this.uimMap.get(first);
//    uid = first;
//    uiid.convertToUiid(uid);
    if (uim !== null) {
        var obj = uim.walkTo(context, uiid);
        if (obj !== null) {
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

App.prototype.notEmpty = function(){
    return this.pages.length > 0;
};

App.prototype.getUiModule = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    return this.uimMap.get(first);
};

App.prototype.getUiModules = function(){
    return this.uimMap.valSet();
};

App.prototype.updateUiModule = function(oldId, uim){
    this.uimMap.remove(oldId);
    this.uimMap.put(uim.id, uim);
};

App.prototype.walkToUiObject = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    var first = uiid.peek();

    var uim = this.uimMap.get(first);

    if(uim !== null){
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
    //prevent duplicated uim root name
    if(uim !== null){
        var um = this.uimMap.get(uim.id);
        if(um !== null){
            //found duplicated root name
            var newName = uim.id + this.identifier.next();
            uim.id = newName;
            if(uim.root !== null){
                uim.root.uid = newName;
            }
            uim.postUidChange();
            this.getRefUidMapForUim(uim);
            this.updateCommandListForPage(page);
        }

        this.uimMap.put(uim.id, uim);
    }

    this.pages.push(page);
};

App.prototype.toSource = function(){
    var code = "";
    if(this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            if(this.pages[i].uim !== null){
                code = code + this.describeUiModule(this.pages[i].uim) + "\n";
            }
        }
        UiCommand.isUseCmdMap = false;
        for(var j=0; j<this.pages.length; j++){
            code = code + this.describeCommand(this.pages[j].commandList, null) + "\n";
        }
    }

    return code;
};

App.prototype.toGroovyDsl = function() {
    var sb = new StringBuffer();
    this.format.language = SourceLanguage.Groovy;
    UiCommand.isUseCmdMap = true;

    try {
        sb.append("/**\n *\tThis Groovy DSL script is automatically generated by Tellurium IDE 0.8.0.\n");
        sb.append(" *\n");
        sb.append(" *\tTo run the script, you need Tellurium rundsl.groovy script. \n");
        sb.append(" *\tThe detailed guide is available at:\n");
        sb.append(" *\t\thttp://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Run_DSL_Script\n");
        sb.append(" *\n");
        sb.append(" *\tOr you can use TelluriumWorks, a Tellurium DSL script Runner, to run the test.\n");
        sb.append(" *\t\thttp://code.google.com/p/aost/wiki/TelluriumWorks080RC1\n");
        sb.append(" *\n");
        sb.append(" *\tFor any problems, please report to Tellurium User Group at: \n");
        sb.append(" *\t\thttp://groups.google.com/group/tellurium-users\n");
        sb.append(" *\n");
        sb.append(" */\n\n");
        if (this.pages.length > 0) {
            for (var i = 0; i < this.pages.length; i++) {
                if (this.pages[i].uim !== null) {
                    sb.append(teJQuery.escape(this.describeUiModule(this.pages[i].uim))).append("\n");
                }
            }
            sb.append(this.describeTestSetup());
            for (var j = 0; j < this.pages.length; j++) {
                sb.append(teJQuery.escape(this.describeCommand(this.pages[j].commandList, this.cmdMap))).append("\n");
            }
        }

    } catch(error) {
        logger.debug("Error Stringify Groovy DSL: " + describeErrorStack(error));
    }

    this.format.language = SourceLanguage.JavaScript;
    UiCommand.isUseCmdMap = false;
    return sb.toString();
};

App.prototype.toUiModule = function() {
    var sb = new StringBuffer();
    this.format.language = SourceLanguage.Groovy;
    UiCommand.isUseCmdMap = true;
    try {
        sb.append("package test\n\n");
        sb.append("import org.telluriumsource.dsl.DslContext\n\n");
        sb.append("/**\n *\tThis UI module file is automatically generated by Tellurium IDE 0.8.0.\n");
        sb.append(" *\tFor any problems, please report to Tellurium User Group at: \n");
        sb.append(" *\t\thttp://groups.google.com/group/tellurium-users\n");
        sb.append(" *\n");
        sb.append(" *\tExample: Google Search Module\n");
        sb.append(" *\n");
        sb.append(" *\t\tui.Container(uid: \"Google\", clocator: [tag: \"td\"]){\n");
        sb.append(" *\t\t\tInputBox(uid: \"Input\", clocator: [title: \"Google Search\"]\n");
        sb.append(" *\t\t\tSubmitButton(uid: \"Search\", clocator: [name: \"btnG\", value: \"Google Search\"]\n");
        sb.append(" *\t\t\tSubmitButton(uid: \"ImFeelingLucky\", clocator: [value: \"I'm Feeling Lucky\"]\n");
        sb.append(" *\t\t}\n");
        sb.append(" *\n");
        sb.append(" *\t\tpublic void doGoogleSearch(String input) {\n");
        sb.append(" *\t\t\tkeyType \"Google.Input\", input\n");
        sb.append(" *\t\t\tclick \"Google.Search\"\n");
        sb.append(" *\t\t\twaitForPageToLoad 30000\n");
        sb.append(" *\t\t}\n");
        sb.append(" *\n");
        sb.append(" */\n\n");
        sb.append("class MyUiModule extends DslContext{\n\n");
        sb.append("\tpublic void defineUi() {\n");

        if (this.pages.length > 0) {
            for (var i = 0; i < this.pages.length; i++) {
                if (this.pages[i].uim !== null) {
                    sb.append(teJQuery.escape(this.describeUiModule(this.pages[i].uim))).append("\n");
                }
            }
            sb.append("\t}\n\n");
            sb.append("\t//Add your methods here\n\n");

            for (var j = 0; j < this.pages.length; j++) {
                sb.append("\tpublic void execFlow").append(j + 1).append("{\n");
                sb.append(teJQuery.escape(this.describeCommand(this.pages[j].commandList, this.cmdMap)));
                sb.append("\t}\n\n");
            }
            sb.append("}\n");
        }

    } catch(error) {
        logger.debug("Error Stringify UI module: " + describeErrorStack(error));
    }
    this.format.language = SourceLanguage.JavaScript;
    UiCommand.isUseCmdMap = false;

    return sb.toString();
};

App.prototype.toJavaCode = function(){
    var sb = new StringBuffer();

    sb.append("package test;\n\n");
    sb.append("import org.telluriumsource.test.java.TelluriumJUnitTestCase;\n");
    sb.append("import org.junit.*;\n\n");
    sb.append("/**\n *\tThis test file is automatically generated by Tellurium IDE 0.8.0.\n");
    sb.append(" *\n");
    sb.append(" */\n\n");
    sb.append("public class MyTestCase extends TelluriumJUnitTestCase {\n");
    sb.append("\tprivate static MyUiModule mum;\n\n");
    sb.append("\t@BeforeClass\n");
    sb.append("\tpublic static void initUi() {\n");
    sb.append("\t\tmum = new MyUiModule()\n");
    sb.append("\t\tnum.defineUi();\n");
    sb.append("\t\tconnectSeleniumServer();\n");
    sb.append("\t\tuseTelluriumEngine(true);\n");
    sb.append("\t}\n\n");
    sb.append("\t//Add your test cases here\n");
    sb.append("\t@Test\n");
    sb.append("\tpublic void testCase(){\n");
    if(this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            sb.append("\t\tmum.execFlow").append(i+1).append("()\n");
        }
    }
    sb.append("\t}\n");
    sb.append("}\n");

    return sb.toString();
};

App.prototype.describeTestSetup = function(){
    return "\t\tconnectSeleniumServer()\n";
};

App.prototype.describeCommand = function(commandList, mapper){
    var sb = new StringBuffer();
    if(commandList !== null && commandList.length > 0){
        var firstCmd = commandList[0];
        var start = 0;
        if(firstCmd.name == "waitForPageToLoad"){
            start++;
        }
        for(var i=start; i<commandList.length; i++){
            var cmd = commandList[i];
            var name = cmd.name;
            if(mapper !== null){
                name = this.cmdMap.get(cmd.name) || cmd.name;
            }
            sb.append("\t\t");
            sb.append(cmd.strCommand(this.format.getVarKeyword()));
            
            sb.append("\n");
        }
    }

    return sb.toString();
};

App.prototype.describeUiModule = function(uim) {
    var visitor = new StringifyVisitor();
    uim.around(visitor);
    var uiModelArray = visitor.out;
    if (uiModelArray != undefined && uiModelArray !== null) {
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
     
