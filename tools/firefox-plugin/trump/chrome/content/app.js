
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
}

App.prototype.isEmpty = function(){
    return this.pages.length == 0;
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

App.prototype.getUiModule = function(id){
    return this.map.get(id);    
};

App.prototype.toSource = function(){
    var code = "";
    if(this.pages.length > 0){
        for(var i=0; i<this.pages.length; i++){
            code = code + this.describeUiModule(this.pages[i].uim) + "\n";
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
            if(cmd.ref != null && cmd.ref != undefined){
//                sb.append(" \"").append(cmd.ref).append("\"");
                sb.append(" ").append(cmd.ref);
            }
            if(cmd.value != null && cmd.value != undefined){
                if(cmd.ref != null && cmd.ref != undefined){
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
     
