const MYCLASS = "myClass";
const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
const DEFAULT_ATTRIBUTES_XML = "<?xml version=\"1.0\"?><attributes id=\"attributes_tree_xml\" xmlns=\"\"></attributes>";

function UiModule(){
    
    this.id = null;

    //the document object this UI module lives on
    this.doc = null;

    //top level UI object
    this.root = null;

    this.valid = false;

    //hold a hash table of the uid to UI objects for fast access
    this.map = new Hashtable();

    //index for uid - dom reference for fast access
    this.indices = null;

    //If the UI Module is relaxed, i.e., use closest match
    this.relaxed = false;

    //the relax details including the UIDs and their corresponding html source
    this.relaxDetails = null;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;

    //ID Prefix tree, i.e., Trie, for the lookForId operation in group locating
    this.idTrie = new Trie();

    //Cache hit, i.e., direct get dom reference from the cache
    this.cacheHit = 0;

    //Cache miss, i.e., have to use walkTo to locate elements
    this.cacheMiss = 0;

    //the latest time stamp for the cache access
    this.timestamp = null;

    //UI module dump visitor
    this.dumpVisitor = new UiDumpVisitor();

    //Snapshot Tree, i.e., STree
    this.stree = null;
}

UiModule.prototype.buildRefUidMap = function(){
    if(this.root != null){
        var visitor = new RefUidVisitor();
        var context = new WorkflowContext();
        this.root.traverse(context, visitor);
        
        return visitor.refUidMap;
    }

    return null;
};

UiModule.prototype.postUidChange = function(){
    var visitors = new STreeChainVisitor();
    var cuidChecker = new ChildrenUidChecker();
    var indexVisitor = new UiIndexVisitor();
    var idVisitor = new UiIdTrieVisitor();
    visitors.addVisitor(cuidChecker);
    visitors.addVisitor(indexVisitor);
    visitors.addVisitor(idVisitor);
    this.visit(visitors);
    this.map = indexVisitor.indices;
    this.idTrie = idVisitor.idTrie;
};

UiModule.prototype.buildUiIndex = function(){
    if(this.root != null){
        var visitor = new UiIndexVisitor();
        var context = new WorkflowContext();
        this.root.traverse(context, visitor);
        this.map = visitor.indices();
    }
};

UiModule.prototype.dumpMe = function(){
    if(this.root != null){
        fbInfo("Dump UI Module " + this.id, this);
        var context = new WorkflowContext();
        this.root.traverse(context, this.dumpVisitor);
    }
};

UiModule.prototype.visit = function(visitor){
    if(this.root != null){
//        fbInfo("Visitor UI Module " + this.id, this);
        var context = new WorkflowContext();
        this.root.traverse(context, visitor);
    }
};

UiModule.prototype.around = function(visitor){
    if(this.root != null){
        var context = new WorkflowContext();
        this.root.around(context, visitor);
    }
};

UiModule.prototype.increaseCacheHit = function(){
    this.cacheHit++;
    this.timestamp = Number(new Date());
};

UiModule.prototype.increaseCacheMiss = function(){
    this.cacheMiss++;
    this.timestamp = Number(new Date());
};

UiModule.prototype.getCacheUsage = function(){
    var cusage = new CacheUsage();
    cusage.cacheHit = this.cacheHit;
    cusage.totalCall = this.cacheHit + this.cacheMiss;
    if(cusage.totalCall > 0){
        cusage.usage = 100*cusage.cacheHit/cusage.totalCall;
    }

    !tellurium.logManager.isUseLog || fbLog("Get Cache Usage for UI Module " + this.id, cusage);
    return cusage;
};

UiModule.prototype.getId = function(){
    if(this.root != null)
        return this.root.uid;

    return null;
};

UiModule.prototype.parseUiModule = function(ulst){

//    var ulst = JSON.parse(json, null);
//    var ulst = jsonarray;
    var klst = new Array();
    !tellurium.logManager.isUseLog || fbLog("JSON Object ulst: ", ulst);
    !tellurium.logManager.isUseLog || fbLog("ulst length: ", ulst.length);
    for(var i=0; i<ulst.length; i++){
        !tellurium.logManager.isUseLog || fbLog("i: ", i);
        !tellurium.logManager.isUseLog || fbLog("Build from JSON object: ", ulst[i].obj);
        this.map.put(ulst[i].key, this.buildFromJSON(ulst[i].obj));
        klst.push(ulst[i].key);
    }

    this.buildTree(klst);
    !tellurium.logManager.isUseLog || fbLog("Parsed Ui Module " + this.id + ": ", this);
    if(tellurium.logManager.isUseLog)
        this.dumpMe();
};

UiModule.prototype.buildFromJSON = function(jobj){
    var builder = tellurium.uiBuilderMap.get(jobj.uiType);

    var obj = null;
    if(builder  != null){
        obj = builder.build();
    }
    if(obj == null){
        obj = new UiContainer();
    }

    objectCopy(obj, jobj);
    !tellurium.logManager.isUseLog || fbLog("Build from JSON: ", jobj);
    !tellurium.logManager.isUseLog || fbLog("Object built: ", obj);

    return obj;
};

UiModule.prototype.buildTree = function(keys){
    for(var i=0; i<keys.length; i++){
        var uiobj = this.map.get(keys[i]);
        //link the uiobject back to the ui module so that it knows which UI module it lives in
        uiobj.uim = this;
        var id = uiobj.getIdAttribute();
        //build ID Prefix tree, i.e., Trie
        //TODO: may consider stricter requirement that the ID cannot be partial, i.e., cannot starts with * ^ ! $
        if(id != null && id.trim().length > 0){
            !tellurium.logManager.isUseLog || fbLog("Add object " + keys[i] + "'s id " + id + " to ID Trie. ", uiobj);
            this.idTrie.insert(keys[i], id);
        }

        if(this.root == null){
            this.root = uiobj;
            this.id = uiobj.uid;
        }else{
            var uiid = new Uiid();
            uiid.convertToUiid(keys[i]);
            this.root.goToPlace(uiid, uiobj);
        }
    }
};

UiModule.prototype.prelocate = function(){
    if(this.root != null){
        this.root.prelocate();
        this.valid = true;
    }
};

UiModule.prototype.index = function(uid){
    return this.indices.get(uid);
};

UiModule.prototype.walkTo = function(context, uiid) {
    var first = uiid.pop();
    if (first == this.root.uid) {
        context.domRef = this.root.domRef;
        return this.root.walkTo(context, uiid);
    }

    return null;
};

UiModule.prototype.addUiObject = function(uid, obj){
    var uiid = getUiid(uid);
    var succeed = true;
    if (this.root == null) {
        if (uiid.size() == 1) {
            this.root = obj;
            this.id = this.root.uid;
        } else {
            fbError("Invalid uid " + uiid, this);
            succeed = false;
        }
    }else{
        var key = uiid.pop();
        if(key == this.root.uid){
            this.root.insertChild(uiid, obj);
        }else{
            fbError("Invalid uid " + uiid, this);
            succeed = false;
        }
    }

    if(succeed){
        obj.uim = this;
        this.map.put(uid, obj);
        var id = obj.getIdAttribute();
        //build ID Prefix tree, i.e., Trie
        //TODO: may consider stricter requirement that the ID cannot be partial, i.e., cannot starts with * ^ ! $
        if (id != null && id.trim().length > 0) {
            this.idTrie.insert(uid, id);
        }
    }
};

UiModule.prototype.findInvalidAncestor = function(context, uiid){
    var obj = this.walkTo(context, uiid);
    var queue = new FiloQueue();
    queue.push(obj);
    while(obj.parent != null){
        if(!validateDomRef(obj.parent)){
            queue.push(obj.parent);
            obj = obj.parent;
        }
    }

    return queue;
};

UiModule.prototype.buildXML = function(){
    if(this.root != null){
        try{
            var xmlVisitor = new XMLSourceVisitor();
            var context = new WorkflowContext();
            this.root.around(context, xmlVisitor);
            var xml = "<?xml version=\"1.0\"?>\n<UIs id=\"customize_tree_xml\" xmlns=\"\">\n";
             xml += xmlVisitor.formatXML();
//            xml += this.formatXML(xmlArray);
            xml += "</UIs>";
            //        logger.debug("Generated XML: \n" + xml);
            return xml;
        }catch(error){
            logger.error("Build XML failed:\n" + describeErrorStack(error));
        }
    }

    return DEFAULT_XML;
};

UiModule.prototype.validate = function(alg, doc){
    if (doc)
        alg.validate(this, doc);
    else
        alg.validate(this, this.doc);

    var found = false;
    if (this.score == 100 || (!this.relaxed))
        found = true;

    var invalid = null;
    if (!found) {
        if (this.relaxDetails != null) {
            invalid = new Array();
            for (var i = 0; i < this.relaxDetails.length; i++) {
                invalid.push(this.relaxDetails[i].uid);
            }
        }
    }
    var visitor = new MarkInvalidVisitor();
    visitor.invalid = invalid;
    this.root.traverse(new WorkflowContext(), visitor);

    var response = new UiModuleLocatingResponse();
    response.id = this.id;
    response.relaxed = this.relaxed;
    response.found = found;
    response.relaxDetails = this.relaxDetails;
    response.matches = this.matches;
    response.score = this.score;

    return response;
};

function UiModuleLocatingResponse(){
    //ID for the UI module
    this.id = null;

    //Successfully found or not
    this.found = false;

    //whether this the UI module used closest Match or not
    this.relaxed = false;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;

    //details for the relax, i.e., closest match
    this.relaxDetails = null;
}

UiModuleLocatingResponse.prototype.toString = function(){
    var msg = new StringBuffer();
    msg.append("Validation result for UI Module " + this.id + "\n");
    msg.append("Found: " + this.found + "\n");
    msg.append("Relaxed: " + this.relaxed + "\n");
    msg.append("Match count: " + this.matches + "\n");
    msg.append("Match score: " + this.score + "\n");
    if(this.relaxDetails != null && this.relaxDetails.length > 0){
        msg.append("Relax details: \n");
        for(var i=0; i<this.relaxDetails.length; i++){
            msg.append("\tUID: " + this.relaxDetails[i].uid + "\n");
            if(this.relaxDetails[i].locator != null){
               msg.append("\tLocator: " + this.relaxDetails[i].locator.strLocator() + "\n");
            }else{
               msg.append("\tLocator: " + "\n");
            }
            msg.append("\tHTML: " + this.relaxDetails[i].html + "\n");
        }
    }

    return msg;
};

function RelaxDetail(){
    //which UID got relaxed, i.e., closest Match
    this.uid = null;
    //the clocator defintion for the UI object corresponding to the UID
    this.locator = null;
    //The actual html source of the closest match element
    this.html = null;
}

var AroundVisitor = Class.extend({
    init: function(){

    },

    before: function(context, node){

    },

    after: function(context, node){

    }
});

var AroundChainVisitor = Class.extend({
    init: function(){
        this.chain = new Array();
    },

    removeAll: function(){
        this.chain = new Array();
    },

    addVisitor: function(visitor){
        this.chain.push(visitor);
    },

    size: function(){
        return this.chain.length;
    },

    before: function(context, snode){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].before(context, snode);
        }
    },

    after: function(context, snode){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].after(context, snode);
        }
    }
});

var StringifyVisitor = AroundVisitor.extend({
    init: function(){
        this.out = new Array();
    },

    before: function(context, node){
        var level = node.checkLevel();
        var str = node.strUiObject(level);
        this.out.push(str);
    },

    after: function(context, node){
        if(node.hasChildren()){
            var level = node.checkLevel();
            var str = node.strUiObjectFooter(level);
            this.out.push(str);
        }
    }
});

var XMLSourceVisitor = AroundVisitor.extend({
    init: function() {
        this.xmlArray = new Array();
    },

    before: function(context, node) {

        //get the current level of the node so that we can do pretty print
        var level = node.checkLevel();
        var padding = node.paddingByLevel(level + 1);
        var descobj = node.descObject();

        var isXPathValid = node.isLocatorValid ? "" : "X";
        var valid = "valid=\"" + isXPathValid + "\"";
        var myclass = "class=\"" + MYCLASS + level + "\"";

        var myUID = "id=\"" + node.fullUid() + "\"";

        if (node.hasChildren()) {
            this.xmlArray.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + " " + valid + ">\n");

        } else {
            this.xmlArray.push(padding + "<UiObject desc=\"" + descobj + "\" " + myclass + " " + myUID + " " + valid + "/>\n");
        }
    },

    after: function(context, node) {
        if (node.hasChildren()) {
            var level = node.checkLevel();
            var padding = node.paddingByLevel(level + 1);
            this.xmlArray.push(padding + "</UiObject>\n");
        }
    },

    formatXML: function() {
        var xml = new StringBuffer();
        if (this.xmlArray != null) {
            for (var i = 0; i < this.xmlArray.length; ++i) {
                xml.append(this.xmlArray[i]);
            }
        }

        return xml.toString();
    }
});

