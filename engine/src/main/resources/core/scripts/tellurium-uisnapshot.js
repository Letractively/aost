//a snapshot of the UI module in the DOM
function UiSnapshot(){
    this.elements = new Hashtable();
    this.color = null;
    this.relaxed = false;
    this.relaxDetails = new Array();
    this.score = 0;
    this.nelem = 0;
}

UiSnapshot.prototype.getScaledScore = function(){
    if(this.nelem == 0)
        return 0;
    else
        return this.score/this.nelem;
};

UiSnapshot.prototype.addUi = function(uid, domref){
    this.elements.put(uid, domref);
};

UiSnapshot.prototype.getUi = function(uid){
    return this.elements.get(uid);
};

UiSnapshot.prototype.clone = function(){
    var snapshot = new UiSnapshot();
    snapshot.elements = this.elements.clone();

    return snapshot;
};

UiSnapshot.prototype.setColor = function(color){
    this.color = color;
};

function UiTemplateAvatar(){
    //shared template UID
    this.uid = null;

    //the actual runtime elements that mapping to the same template
//    this.avatar = new Array();
    //run time id (rid) to snapshot node map
    //Need
    //  uid <--> rid mapping
    this.avatar = new Hashtable();
}

function UiSData(uid, rid, objRef, domRef){
    this.uid = uid;
    this.rid = rid;
    this.objRef = objRef;
    this.domRef = domRef;
}

var UiSNode = Class.extend({
    init: function() {
        //UID
        this.uid = null;

        //the index of the element with the same UID
//        this.index = 0;
        //rid, runtime id
        this.rid = null;

        //point to its parent in the UI SNAPSHOT tree
        this.parent = null;

        //UI object, which is defined in the UI module, reference
        this.objRef = null;

        //DOM reference
        this.domRef = null;
    },

    getFullRid: function(){
        if(this.parent != null){
            return this.parent.getFullRid() + "." + this.rid;
        }

        return this.rid;
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Node", this);
        var id = uiid.pop();
        if (id == this.uid) {
            var result = new Array();
            result.push(this);

            return result;
        }

        fbError("Come to the Wrong SNode, expected node is " + id + ", but actual is " + this.uid, this);
        throw new SeleniumError("Come to the Wrong SNode, expected node is " + id + ", but actual is " + this.uid);
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
    },

    insert: function(context, node){
        fbError(this.uid + " is not a container type node and it cannot have children", this);
        throw new SeleniumError(this.uid + "is not a container type node and it cannot have children");
    }
});

var UiContainerSNode = UiSNode.extend({
    init: function(){
        this._super();
        //children nodes, regular UI Nodes
        this.components = new Hashtable();

        //children index, hold rid to dom reference mapping for children
//        this.childrenIndex = new Hashtable();
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Container Node", this);

        if (uiid.size() < 1) {
            var result = new Array();
            result.push(this);

            return result;
        }

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if (child != null) {
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            return child.walkTo(context, uiid);
        } else {
            fbError("Cannot find child " + cid, child);
            return null;
        }
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var child = this.components.get(keys[i]);
                child.traverse(context, visitor);
            }
        }
    },

    insert: function(context, node){
        var rid = node.rid;
        node.parent = this;
        this.components.put(rid, node);
    }
});

var UiRepeatSNode = UiSNode.extend({
    init: function(){
        this._super();

        //children nodes with key as the template UID and value as the UI template Avatar
//        this.components = new Hashtable();
        this.avatar = new Array();
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Repeat Node", this);

        if (uiid.size() < 1) {
            var result = new Array();
            result.push(this);

            return result;
        }

        var index = uiid.pop().replace(/^_/, '');
        var nindex = parseInt(index);
        var myself = this.avatar[nindex];

        if (uiid.size() < 1) {
            return [myself];
        }

        var cid = uiid.peek();
        var child = myself.components.get(cid);
        return child.walkTo(context, uiid);
/*
        var cid = uiid.pop();
        var child = this.components.get(cid);
        if (child != null) {
            var childAvatar = child.avatar;
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            var lst = new Array();
            for(var i=0; i<childAvatar.length; i++){
                var ret = childAvatar[i].walkTo(context, uiid);
                lst.push(ret);
            }
//            return child.walkTo(context, uiid);
            return ret;

        } else {
            fbError("Cannot find child " + cid, child);
            return null;
        }
         */
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var key = keys[i];
                var avatar = this.components.get(key).avatar.valSet();
                for(var j=0; j<avatar.size(); j++){
                    var child = avatar[j];
                    child.traverse(context, visitor);
                }
            }
        }
    },

    insert: function(context, node){
        var uid = node.uid;
        var rid = node.rid;
        node.parent = this;
        var avatar = this.components.get(uid);
        if(avatar == null){
            avatar = new UiTemplateAvatar();
            avatar.uid = uid;
        }
        avatar.avatar.put(rid, node);
        this.components.put(uid, avatar);
    }
});

var UiListSNode = UiSNode.extend({
    init: function(){
        this._super();

        //children nodes with key as the template UID and value as the UI template Avatar
        this.components = new Hashtable();
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree List Node", this);

        if (uiid.size() < 1) {
            var result = new Array();
            result.push(this);

            return result;
        }

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if (child != null) {
            var childAvatar = child.avatar;
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            var lst = new Array();
            for(var i=0; i<childAvatar.length; i++){
                var ret = childAvatar[i].walkTo(context, uiid);
                lst.push(ret);
            }
//            return child.walkTo(context, uiid);
            return ret;
        } else {
            fbError("Cannot find child " + cid, child);
            return null;
        }
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var key = keys[i];
                var avatar = this.components.get(key).avatar.valSet();
                for(var j=0; j<avatar.size(); j++){
                    var child = avatar[j];
                    child.traverse(context, visitor);
                }
            }
        }
    },

    insert: function(context, node){
        var uid = node.uid;
        var rid = node.rid;
        node.parent = this;
        var avatar = this.components.get(uid);
        if(avatar == null){
            avatar = new UiTemplateAvatar();
            avatar.uid = uid;
        }
        avatar.avatar.put(rid, node);
        this.components.put(uid, avatar);
    }
});

var UiTableSNode = UiSNode.extend({
    init: function(){
        this._super();

        //header nodes with key as the template UID and value as the UI template Avatar
        this.headers = new Hashtable();

        //footer node with key as the template UID and value as the UI template Avatar
        this.footers = new Hashtable();

        //body nodes with key as the template UID and value as the UI template Avatar
        this.components = new Hashtable();
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.headers != null && this.headers.length > 0){
            var i;
            var j;
            var avatar;
            var keys;
            var child;

            keys = this.headers.keySet();
            for(i=0; i<this.headers.length; i++){
                avatar = this.headers.get(keys[i]).avatar.valSet();
                for(j=0; j<avatar.length; j++){
                    child = avatar[j];
                    child.traverse(context, visitor);
                }
            }
        }

        if(this.components != null && this.components.length > 0){
            keys = this.components.keySet();
            for(i=0; i<keys.length; i++){
                avatar = this.components.get(keys[i]).avatar.valSet();
                for(j=0; j<avatar.length; j++){
                    child = avatar[j];
                    child.traverse(context, visitor);
                }
            }
        }

        if(this.footers != null && this.footers.length > 0){
            keys = this.footers.keySet();
            for(i=0; i<this.footers.length; i++){
                avatar = this.footers.get(keys[i]).avatar.valSet();
                for(j=0; j<avatar.length; j++){
                    child = avatar[j];
                    child.traverse(context, visitor);
                }
            }
        }
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Table Node", this);

        if (uiid.size() < 1) {
            var result = new Array();
            result.push(this);

            return result;
        }

        var child = uiid.peek();

        if(child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
        }else if(child.startsWith("_FOOTER")){
            return this.walkToFooter(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }

    },

    walkToHeader: function(context, uiid){
         //pop up the "header" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        child = child.replace(/^_/, '').replace(/HEADER/, '');

        var index = parseInt(trimString(child));

        //try to find its child
        var cobj = this.findHeaderUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table head ", cobj);
            var result = new Array();
            result.push(cobj);
            return result;
//            return cobj.avatar;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table head ", cobj);
            var lst = new Array();
            for(var i=0; i<cobj.avatar.length; i++){
                var ret = cobj.avatar[i].walkTo(context, uiid);
                lst.push(ret);
            }
//            return cobj.walkTo(context, uiid);
            return lst;
        }

    },

    walkToFooter: function(context, uiid) {
        //pop up the "foot" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        child = child.replace(/^_/, '').replace(/FOOTER/, '');

        var index = parseInt(trimString(child));

        //try to find its child
        var cobj = this.findFooterUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table foot ", cobj);
            var result = new Array();
            result.push(cobj);
            return result;
//            return cobj.avatar;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table foot ", cobj);
            var lst = new Array();
            for(var i=0; i<cobj.avatar.length; i++){
                var ret = cobj.avatar[i].walkTo(context, uiid);
                lst.push(ret);
            }
//            return cobj.walkTo(context, uiid);
            return lst;
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
        var parts = child.replace(/^_/, '').split("_");
        var ntbody;
        var nrow;
        var ncolumn;
        if(parts.length == 3){
            ntbody = parseInt(parts[0]);
            nrow = parseInt(parts[1]);
            ncolumn = parseInt(parts[2]);
        }else{
            ntbody = 1;
            nrow = parseInt(parts[0]);
            ncolumn = parseInt(parts[1]);
        }

        //otherwise, try to find its child
        var cobj = this.findUiObject(ntbody, nrow, ncolumn);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }
        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table body ", cobj);
            var result = new Array();
            result.push(cobj);
            return result;
//            return cobj.avatar;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table body ", cobj);
            var lst = new Array();
            for(var i=0; i<cobj.avatar.length; i++){
                var ret = cobj.avatar[i].walkTo(context, uiid);
                lst.push(ret);
            }
            return lst;
//             return cobj.walkTo(context, uiid);
        }
    },

    insertInto: function(context, hashtable, node){
        var uid = node.uid;
        var rid = node.rid;
        node.parent = this;
        var avatar = hashtable.get(uid);
        if(avatar == null){
            avatar = new UiTemplateAvatar();
            avatar.uid = uid;
        }
        avatar.avatar.put(rid, node);
        hashtable.put(uid, avatar);
    },

    insert: function(context, node){
        var rid = node.rid;

        if(rid.startsWith("_HEADER")){
            this.insertInto(context, this.headers, node);
        }else if(rid.startsWith("_FOOTER")){
            this.insertInto(context, this.footers, node);
        } else {
            this.insertInto(context, this.components, node);
        }
    }
});

function UiSTree(){
    //the root node
    this.root = null;

    //the reference point to the UI module that the UI Snapshot tree is derived
    this.uimRef = null;
}

UiSTree.prototype.walkTo = function(context, uiid){
    !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Node", this);
    if(this.root != null){
        if(uiid.size() > 0){
            var uid = uiid.pop();
            if(this.root.uid == uid){
                return this.root.walkTo(context, uiid);   
            }

        }
        
        return null;
    }


    return null;
};

UiSTree.prototype.traverse = function(context, visitor){
    if(this.root != null){
        this.root.traverse(context, visitor);
    }
};

UiSTree.prototype.insert = function(context, node){
    if(this.root == null){
        this.root = node;
    }else{
        var pid = node.objRef.parent.fullUid();
        var uiid = getUiid(pid);
//        var pnode = this.root.walkTo(context, uiid);
        var pnode = this.walkTo(context, uiid);
        //TODO: if pnode returns an array, how to handle that
        //pnode.insert(context, node);
        pnode[0].insert(context, node);
    }
};

var STreeVisitor = Class.extend({
    init: function(){

    },

    visit: function(context, snode){

    }
});

var STreeChainVisitor = Class.extend({
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

    visit: function(context, snode){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].work(context, snode);
        }
    }
});

var UiHTMLSourceVisitor = STreeVisitor.extend({
    init: function(){
        this.htmlsource = new Hashtable();
    },

    visit: function(context, snode){
        var domref = snode.domRef;
        var html = teJQuery(domref).outerHTML();
        var frid = snode.getFullRid();
        this.htmlsource.put(frid, html);
        !tellurium.logManager.isUseLog || fbLog("HTML Source for " + frid, html);
    }
});

/*
var UiDecorationVisitor = STreeVisitor.extend({
    init: function(){
        this.decorator = new UiDecorator();
    },

    visit: function(context, snode){
        var elem = snode.domRef;
        var frid = snode.getFullRid();
//        var delay = context.getContext("DELAY");

        this.decorator.cleanShowNode();
        teJQuery(elem).append("<div id='TE_ENGINE_SHOW_NODE'>" + frid + "</div>");
        this.decorator.showNode(elem);
*//*        if(delay != null)
            teJQuery(elem).find("#TE_ENGINE_SHOW_NODE").fadeIn(100).delay(500).fadeOut(100);*//*
        !tellurium.logManager.isUseLog || fbLog("Decoration for " + frid, elem);

        teJQuery(elem).find("#TE_ENGINE_SHOW_NODE").remove();
    }
});
*/

var UiCollectVisitor = STreeVisitor.extend({
    init: function(){
        this.elements = new Array();
    },

    visit: function(context, snode){
        var elem = snode.domRef;
        this.elements.push(elem);

        !tellurium.logManager.isUseLog || fbLog("Collect " + snode.getFullRid(), elem);
    }
});

var UiOutlineVisitor = STreeVisitor.extend({
    init: function(){
//        this.outLine = "2px solid #000";
        this.outLine = "3px solid #0000FF";
    },
    
    visit: function(context, snode){
        var elem = snode.domRef;     
        elem.style.outline = this.outLine;

        !tellurium.logManager.isUseLog || fbLog("Add outline for " + snode.getFullRid(), elem);
    }
});

var UiOutlineCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        elem.style.outline = "";

        !tellurium.logManager.isUseLog || fbLog("Clean outline for " + snode.getFullRid(), elem);
    } 
});

var UiSimpleTipVisitor = STreeVisitor.extend({

    visit: function(context, snode){
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        teJQuery(elem).simpletip({
            // Configuration properties
            content: frid,
            fixed: false
        });

        !tellurium.logManager.isUseLog || fbLog("Add simple tip for " + frid, elem);
    }
});

var UiSimpleTipCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        teJQuery(elem).removeData("simpletip");

        !tellurium.logManager.isUseLog || fbLog("Clean simple tip for " + frid, elem);
    }
});