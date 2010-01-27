function getUiid(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    return uiid;
}

function matchUiid(uiid1, uiid2){
    var result = new Array();
    var ar1 = uiid1.toArray();
    var ar2 = uiid2.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;
}

function matchUid(uid1, uid2){
    var uiid1 = getUiid(uid1);
    var uiid2 = getUiid(uid2);

    return matchUiid(uiid1, uiid2);
}


//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
}

Uiid.prototype.matchWith = function(uiid){
    var result = new Array();
    var ar1 = this.stack;
    var ar2 = uiid.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;        
};

Uiid.prototype.subUiid = function(index){
    var nuiid = new Uiid();
    if(index >= 0 && index < this.stack.length){
        nuiid.stack = this.stack.slice(index);
    }

    return nuiid;
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
};

Uiid.prototype.pop = function(){
    if(this.stack.length > 0){
//        return this.stack.pop();
        return this.stack.shift();
    }

    return null;
};

Uiid.prototype.reverse = function(){
    if(this.stack.length > 0){
        this.stack.reverse();
    }
};

Uiid.prototype.peek = function(){
    if(this.stack.length > 0){
//        return this.stack[this.stack.length-1];
        return this.stack[0];
    }

    return null;
};

Uiid.prototype.getUid = function(){
    return this.stack.join(".");
};

Uiid.prototype.size = function(){
    return this.stack.length;
};

Uiid.prototype.toArray = function(){
    return this.stack;    
};

Uiid.prototype.convertToUiid = function(uid){
    if(uid != null && trimString(uid).length > 0){
        var ids = uid.split(".");
        for(var i= 0; i<ids.length; i++){
            var pp = this.preprocess(ids[i]);
            if(pp.length == 1){
                this.push(pp[0]);
            }else{
                this.push(pp[1]);
                this.push(pp[0]);
            }
        }
    }

    return this;
};

Uiid.prototype.preprocess = function(uid){
    if(uid != null && trimString(uid).length > 0 && uid.indexOf("[") != -1){
        if(uid.indexOf("[") == 0){
            var single = uid.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = uid.indexOf("[");
            var first = uid.substring(0, index);
            var second = uid.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [uid];
};

function WorkflowContext(){
    this.refLocator = null;
    this.domRef = null;
    this.alg = null;
    this.skipNext = false;
}

//Base locator
function BaseLocator(){
    this.loc = null;
}

//composite locator
function CompositeLocator(){
    this.tag = null;
    this.text = null;
    this.position = null;
    this.direct = false;
    this.header = null;
    this.trailer = null;
    this.attributes = new Hashtable();
}

var UiVisitor = Class.extend({
    init: function(){

    },

    visit: function(context, uiobj){
        
    }
});

var UiDumpVisitor = UiVisitor.extend({
    visit: function(context, uiobj){
        fbLog("UI Object " + uiobj.fullUid(), uiobj);    
    }
});

//base UI object
var UiObject = Class.extend({
    init: function() {
        this.uid = null;

        //its parent UI object
        this.parent = null;

        //namespace, useful for XML, XHTML, XForms
        this.namespace = null;

        this.locator = null;

        //event this object should be respond to
        this.events = null;

        //should we do lazy locationg or not, i.e., wait to the time we actually use this UI object
        //usually this flag is set because the content is dynamic at runtime
        this.lazy = false;

        this.uiType = null;

        //Tellurium Core generated locator for this UI Object
        this.generated = null;

        //dom reference
        this.domRef = null;

        //UI Module reference, which UI module this UI object belongs to
        this.uim = null;
    },

    checkLevel: function(){
        if(this.parent != null){
            return this.parent.checkLevel() + 1;
        }

        return 1;
    },

    getIdAttribute: function(){
        //return the ID attribute
        var ida = null;
        if(this.locator != null && this.locator.attributes != undefined && this.locator.attributes != null){
//            ida = this.locator.attributes.get("id");
            ida = this.locator.attributes["id"];
            if(ida == undefined)
                ida = null;
        }

        return ida;
    },

    getChildrenIds: function(){
        return null;
    },

    goToPlace: function(uiid, uiobj) {

        if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
             fbError("Wrong uiid ",  uiid);
        }
    },

    locate: function(uialg) {
        uialg.locateInAllSnapshots(this);
        //need to push all its children into the object queue
    },

    lookChildren: function() {
        return null;
    },

    //add all children in no matter if they are cacheable or not, useful for templates
    lookChildrenNoMatterWhat: function() {
        return null;
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
    },

    snapshot: function() {
        if (this.generated)
            this.domRef = selenium.browserbot.findElement(this.generated);
    },

    prelocate: function() {
        if (this.amICacheable())
            this.snapshot();
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                //if the parent or root dom reference is null, cannot go any further
                //If its locator, i.e., itself is a logical object, do not do any locating
                if (this.locator != null && context.domRef != null) {

                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + uiid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + uiid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        return this;
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
    },

    amICacheable: function() {
        //check its parent and do not cache if its parent is not cacheable
        //If an object is cacheable, the path from the root to itself should
        //be all cacheable
        if (this.parent != null) {
            return (!this.lazy) && this.parent.amICacheable() && (!this.parent.noCacheForChildren);
        }

        return (!this.lazy);
    },

    fullUid: function() {
        if (this.parent != null) {
            return this.parent.fullUid() + "." + this.uid;
        }

        return this.uid;
    },

    respondsTo: function(methodName){
        if(this[methodName] != undefined){
            return true;
        }

        return false;
    },

    respondsToWithException: function(methodName){
        if(this[methodName] != undefined){
            return true;
        }else{
            var fid = this.fullUid();
            fbError("UI Object " + fid + " does not have the method " + methodName, this);
            throw new SeleniumError("UI Object " + fid + " does not have the method " + methodName);
        }
    }
});

var UiButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Button';
        this.tag = "input";
    }
});

var UiCheckBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'CheckBox';
        this.tag = "input";
        this.type = "checkbox";
    }
});


var UiDiv = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'div';
        this.tag = "div";
    }
});

var UiIcon = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Icon';
    }
});

var UiImage = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Image';
        this.tag = "img";
    }
});

var UiInputBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'InputBox';
        this.tag = "input";
    }
});

var UiRadioButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'RadioButton';
        this.tag = "input";
        this.type = "radio";
    }
});

var UiSelector = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Selector';
        this.tag = "select";
    }
});

var UiSpan = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Span';
        this.tag = "span";
    }
});

var UiSubmitButton = UiButton.extend({
    init: function(){
        this._super();
        this.uiType = 'SubmitButton';
        this.type = "submit";
    }
});

var UiTextBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'TextBox';
    }
});

var UiUrlLink = UiObject.extend({
    uiType: 'UrlLink',
    tag: "a"
});

var UiContainer = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Container';
        this.group = false;
        this.noCacheForChildren = false;
        this.components = new Hashtable();
    },

    getChildrenIds: function(){
        var ids = null;
        if(this.uim != null){
            var fid = this.fullUid();
            ids = this.uim.idTrie.getChildrenData(fid);
            !tellurium.logManager.isUseLog || fbLog("Get children ids from ID Trie for " + fid, ids);
        }

        return ids;
    },

    goToPlace:  function(uiid, uiobj) {
        if(uiid.size() == 1){
            uiid.pop();
//            if (this.uid == null)
            objectCopy(this, uiobj);            
        }else{
            uiid.pop();
            var cuid = uiid.peek();

            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                this.components.put(cuid, uiobj);
            }else{
                var child = this.components.get(cuid);
                child.goToPlace(uiid, uiobj);
            }
        }

/*        if (uiid.size() > 0) {
            var cuid = uiid.peek();
            var child = this.components.get(cuid);

            if (child != null) {
                child.goToPlace(uiid, uiobj);
            } else {
                uiid.pop();
                if(uiid.size() == 0){               
                    uiobj.parent = this;
                    this.components.put(cuid, uiobj);
                }else{
                    fbError("Error to goToPlace for ", uiid);
                }
             }
        }*/
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for Container " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of Container " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }
        }
    },

    lookChildren: function() {
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of Container " + this.uid + ": ", component);
                    if(component.locator != null){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }
        }

        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];
//            !tellurium.logManager.isUseLog || fbLog("component: ", component);
//            children.push(component);
            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of Container " + this.uid + ": ", component);
            if (component.locator != null) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
        }

        return children;
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
        //need to push all its children into the object queue
        var valset = this.components.valSet();
        for(var i=0; i<valset.length; i++){
            if(!valset[i].lazy)
                uialg.addChildUiObject(valset[i]);
        }
    },
    
    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var child = this.components.get(keys[i]);
                child.prelocate();
            }
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

    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() > 1) {
                        //first try lookId
                        $found = alg.lookId(this, $found);
                        !tellurium.logManager.isUseLog || fbLog("Look Id result for " + this.uid, $found.get());
                        if($found.size() > 1){
                            //Use lookAHead to eliminate multipe matches
                            $found = alg.lookAhead(this, $found);
                            !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + this.uid, $found.get());
                        }
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + this.uid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        if(uiid.size() < 1)
            return this;

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if(child != null){
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            return child.walkTo(context, uiid);
        }else{
            fbError("Cannot find child " + cid, child);
            context.domRef = null;
            return null;
        }
    }
});

var UiForm = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Form';
        this.tag = "form";
    }
});

var UiFrame = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Frame';
        this.id = null;
        this.name = null;
        this.title = null;
    }
});

//TODO: ui algorithm operations for List and Table 
var UiList = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'List';
        this.noCacheForChildren = true;
        this.separator = null;
        this.defaultUi = new UiTextBox();
    },
    
    findUiObject: function(index) {

        //first check _index format
        var key = "_" + index;
        var obj = this.components.get(key);

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL";
            obj = this.components.get(key);
        }
        !tellurium.logManager.isUseLog || fbLog("Found List UI object for index " + index, obj);

        return obj;
    },

    getListSelector: function(index) {
        if (this.separator == null || this.separator.trim().length == 0)
            return this.deriveListSelector(index);

        var t = index-1;
        return " > " + this.separator + ":eq(" + t + ")";
    },

    deriveListSelector: function(index) {
        var locs = new Hashtable();
        var last = null;
        var pl = null;
        for (var i = 1; i <= index; i++) {
            var obj = this.findUiObject(i);
//            var pl = tellurium.jqbuilder.buildCssSelector(obj.locator.tag, obj.locator.text, null, obj.locator.direct, obj.locator.attributes);
            //XXX: double check here, if the generated css selectors are the same for two different objects,
            //error may occur, but does it make sense to have to different objects with the same css selectors?
            //seems not make sense.
            pl = obj.uid;
            var occur = locs.get(pl);
            if (occur == null) {
                locs.put(pl, 1);
            } else {
                locs.put(pl, occur + 1);
            }
            if (i == index) {
//                last = pl;
                last = tellurium.jqbuilder.buildCssSelector(obj.locator.tag, obj.locator.text, null, obj.locator.direct, obj.locator.attributes);
            }
        }

//        var lastOccur = locs.get(last)-1;
        var lastOccur = locs.get(pl)-1;

/*        if(last.locator.direct){
          return " > ${lastTag}:eq(${lastOccur-1})";
        }else{
          return " ${lastTag}:eq(${lastOccur-1})";
        }
*/

        //force to be direct child (if consider List trailer) 
        return " > " + last + ":eq(" + lastOccur + ")";
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() > 1) {
                        //Use bestEffort() to eliminate multipe matches
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + this.uid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the List itself and got the context dom Referece", context.domRef);
        //if not child listed, return itself
        if (uiid.size() < 1)
            return this;

        var child = uiid.pop();

        var part = child.replace(/^_/, '');

        var nindex = parseInt(part);

        //otherwise, try to find its child
        var cobj = this.findUiObject(nindex);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var selt = this.getListSelector(nindex);

            var $fnd = teJQuery(context.domRef).find(selt);
            !tellurium.logManager.isUseLog || fbLog("Found child " + nindex + " with CSS selector '" + selt +"' for List " + this.uid, $fnd.get());
            if ($fnd.size() == 1) {
                context.domRef = $fnd.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($fnd.size() == 0)
                    fbError("Cannot find the child UI element " + nindex, this);
                if ($fnd.size() > 1) {
                    fbError("Found multiple matches for UI element " + nindex, $fnd.get());
                    context.domRef = null;
                }
            }
        }

        //If the List does not have a separator
        //tell WorkflowContext not to process the next object's locator because List has already added that
        if(this.separator == null || this.separator.trim().length == 0){
            context.skipNext = true;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return List child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to List child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    }
});

var UiTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Table';
        this.tag = "table";
        this.noCacheForChildren = true;
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
    },
    
    goToPlace:  function(uiid, uiobj) {
        if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
            uiid.pop();
            var cuid = uiid.peek();
            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                if(cuid.startsWith("_HEADER")){
                    this.headers.put(cuid, uiobj);                        
                }else{
                    this.components.put(cuid, uiobj);
                }
            }else{
                if(cuid.startsWith("_HEADER")){
                    var header = this.headers.get(cuid);
                    header.goToPlace(uiid, uiobj);
                }else{
                    var child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }
            }
        }

/*
        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.size() > 0) {
            var cuid = uiid.peek();
            var child = null;
 //           if(cuid == "header"){
            if(cuid.startsWith("_HEADER")){
//                uiid.pop();
                cuid = uiid.pop();
                child = this.headers.get(cuid);
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                     uiobj.parent = this;
                     this.headers.put(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components.get(cuid);
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.components.put(cuid, uiobj);
                }
            }
        }
*/
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for Table " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Children val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if ((!component.lazy)) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of Table " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Headers for Container " + this.uid + ": ", this.headers.showMe());
            valset = this.headers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Headers val set: ", valset);
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add header of Table " + this.uid + " to UiAlg : ", header);
                    uialg.addChildUiObject(header);
                }
            }
        }
    },

    lookChildren: function(){
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of Table " + this.uid + ": ", component);
//                    validChildren.push(component);
                    if(component.locator != null){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }

            valset = this.headers.valSet();
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of Table " + this.uid + ": ", header);
//                    validChildren.push(header);
                    if(header.locator != null){
                        validChildren.push(header);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                        var chr = header.lookChildren();
                        if(chr != null && chr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                            validChildren = validChildren.concat(chr);
                        }
                    }
                }
            }
        }
        
        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];
            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of Table " + this.uid + ": ", component);
            if (component.locator != null) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
//            !tellurium.logManager.isUseLog || fbLog("component: ", component);
//            children.push(component);
        }
        
        valset = this.headers.valSet();
        for (var j = 0; j < valset.length; j++) {
            var header = valset[j];
//            !tellurium.logManager.isUseLog || fbLog("header: ", header);
//            children.push(header);
            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of Table " + this.uid + ": ", header);
            if (header.locator != null) {
                children.push(header);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                var chr = header.lookChildrenNoMatterWhat();
                if (chr != null && chr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                    children = children.concat(chr);
                }
            }
        }

        return children;
    },

    findHeaderUiObject: function(index){
        var key = "_HEADER_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_HEADER_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    findUiObject: function(row, column){
        var key = "_" + row + "_" + column;
        var obj = this.components.get(key);

        if(obj == null){
            key = "_ALL_" + column;
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    getHeaderSelector: function(column) {
        var t = column - 1;
        return " > tbody > tr:has(th) > th:eq(" + t + ")";
    },
    
    getCellSelector: function(row, column) {
        var r = row-1;
        var c = column-1;
        return " > tbody > tr:has(td):eq(" + r + ") > td:eq(" + c + ")";
    },

    getAllBodyCell: function(context, worker){
        if (context.domRef != null) {
            var $found = teJQuery(context.domRef).find("> tbody > tr > td");

            if ($found.size() >0) {
                return worker.work(context, $found.get());
            }
        }

        return null;
    },

    walkToHeader: function(context, uiid) {
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

        if (context.domRef != null) {
            var sel = this.getHeaderSelector(index);

            var $found = teJQuery(context.domRef).find(sel);
            !tellurium.logManager.isUseLog || fbLog("Found child " + index + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0)
                    fbError("Cannot find the child UI element " + index, this);
                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + index, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if ("th" == cobj.locator.tag && cobj.locator.header == null) {
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table head ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table head ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
        var parts = child.replace(/^_/, '').split("_");

        var nrow = parseInt(parts[0]);
        var ncolumn = parseInt(parts[1]);

        //otherwise, try to find its child
        var cobj = this.findUiObject(nrow, ncolumn);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getCellSelector(nrow, ncolumn);

            var $found = teJQuery(context.domRef).find(sel);
            !tellurium.logManager.isUseLog || fbLog("Found child with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0)
                    fbError("Cannot find the child UI element ", this);
                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element ", $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if ("td" == cobj.locator.tag && cobj.locator.header == null) {
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.headers != null && this.headers.length > 0){
            var hkeys = this.headers.keySet();
            for(var i=0; i<this.headers.length; i++){
                var header = this.headers.get(hkeys[i]);
                header.traverse(context, visitor);
            }
        }

        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var j=0; j<keys.length; j++){
                var child = this.components.get(keys[j]);
                child.traverse(context, visitor);
            }
        }
    },

    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() > 1) {
                        //Use bestGuess() to eliminate multipe matches
                        //                       $found = alg.lookAhead(this, $found);
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + this.uid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the List itself and got the context dom Referece", context.domRef);

        if (uiid.size() < 1)
            return this;

        var child = uiid.peek();

 //       if (trimString(child) == "header") {
        if(child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiStandardTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'StandardTable';
        this.tag = "table";
        this.noCacheForChildren = true;
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
        this.footers = new Hashtable();
        //table header
        this.ht = "thead";
        this.hrt = "tr";
        this.hct = "th";

        //table body
        this.bt = "tbody";
        this.brt = "tr";
        this.bct = "td";

        //table footer
        this.ft = "tfoot";
        this.frt = "tr";
        this.fct = "td";
    },

    goToPlace:  function(uiid, uiobj) {
         if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
            uiid.pop();
            var cuid = uiid.peek();
            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                if(cuid.startsWith("_HEADER")){
                    this.headers.put(cuid, uiobj);
                }else if(cuid.startsWith("_FOOTER")){
                    this.footers.put(cuid, uiobj);
                }else{
                    this.components.put(cuid, uiobj);
                }
            }else{
                if(cuid.startsWith("_HEADER")){
                    var header = this.headers.get(cuid);
                    header.goToPlace(uiid, uiobj);
                }else if(cuid.startsWith("_FOOTER")){
                    var footer = this.footers.get(cuid);
                    footer.goToPlace(uiid, uiobj);
                }else{
                    var child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }
            }
        }

/*        uiid.pop();
        if (this.uid == null)
            objectCopy(this, uiobj);

        if (uiid.size() > 0) {
            var cuid = uiid.peek();
            var child = null;
            if(cuid == "header"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.headers.get(cuid);
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.headers.put(cuid, uiobj);
                }
            }else if(cuid == "footer"){
                uiid.pop();
                cuid = uiid.pop();
                child = this.footers.get(cuid);
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.footers.put(cuid, uiobj);
                }
            }else{
                cuid = uiid.pop();
                child = this.components.get(cuid);
                if (child != null) {
                    child.goToPlace(uiid, uiobj);
                } else {
                    uiobj.parent = this;
                    this.components.put(cuid, uiobj);
                }
            }
        }*/
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }

            keys = this.footers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.footers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for StandardTable " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Children val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if ((!component.lazy)) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of StandardTable " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Headers for StandardTable " + this.uid + ": ", this.headers.showMe());
            valset = this.headers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Headers val set: ", valset);
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add header of StandardTable " + this.uid + " to UiAlg : ", header);
                    uialg.addChildUiObject(header);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Footers for StandardTable " + this.uid + ": ", this.footers.showMe());
            valset = this.footers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Footers val set: ", valset);
            for (var k = 0; k < valset.length; k++) {
                var footer = valset[k];
                !tellurium.logManager.isUseLog || fbLog("footer: ", footer);
                if (!footer.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add footer of StandardTable " + this.uid + " to UiAlg : ", footer);
                    uialg.addChildUiObject(footer);
                }
            }
        }
    },

    lookChildren: function(){
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of StandardTable " + this.uid + ": ", component);
//                    validChildren.push(component);
                    if(component.locator != null){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }

            valset = this.headers.valSet();
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of StandardTable " + this.uid + ": ", header);
//                    validChildren.push(header);
                    if(header.locator != null){
                        validChildren.push(header);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                        var chr = header.lookChildren();
                        if(chr != null && chr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                            validChildren = validChildren.concat(chr);
                        }
                    }
                }
            }

            valset = this.footers.valSet();
            for (var k = 0; k < valset.length; k++) {
                var footer = valset[k];
                !tellurium.logManager.isUseLog || fbLog("footer: ", footer);
                if (!footer.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable footer of StandardTable " + this.uid + ": ", footer);
//                    validChildren.push(footer);
                    if(footer.locator != null){
                        validChildren.push(footer);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + footer.uid + " and look for this children", footer);
                        var cfr = footer.lookChildren();
                        if(cfr != null && cfr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + footer.uid + "'s children. ", cfr);
                            validChildren = validChildren.concat(cfr);
                        }
                    }
                }
            }
        }

        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];
//            !tellurium.logManager.isUseLog || fbLog("component: ", component);
//            children.push(component);
            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of StandardTable " + this.uid + ": ", component);
            if (component.locator != null) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
        }

        valset = this.headers.valSet();
        for (var j = 0; j < valset.length; j++) {
            var header = valset[j];
//            !tellurium.logManager.isUseLog || fbLog("header: ", header);
//            children.push(header);
            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of StandardTable " + this.uid + ": ", header);
            if (header.locator != null) {
                children.push(header);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                var chr = header.lookChildrenNoMatterWhat();
                if (chr != null && chr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                    children = children.concat(chr);
                }
            }
        }

        valset = this.footers.valSet();
        for (var k = 0; k < valset.length; k++) {
            var footer = valset[k];
//            !tellurium.logManager.isUseLog || fbLog("footer: ", footer);
//            children.push(footer);
            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable footer of StandardTable " + this.uid + ": ", footer);
            if (footer.locator != null) {
                children.push(footer);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + footer.uid + " and look for this children", footer);
                var cfr = footer.lookChildrenNoMatterWhat();
                if (cfr != null && cfr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + footer.uid + "'s children. ", cfr);
                    children = children.concat(cfr);
                }
            }
        }

        return children;
    },

    findHeaderUiObject: function(index){
        var key = "_HEADER_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_HEADER_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    findFooterUiObject: function(index){
        var key = "_FOOTER_" + index;
        var obj = this.footers.get(key);

        if(obj == null){
            key = "_FOOTER_ALL";
            obj = this.footers.get(key);
        }

        return obj;
    },

    findUiObject: function(tbody, row, column){
        var key = "_" + tbody + "_" + row + "_" + column;
        var obj = this.components.get(key);

        //thirdly, check _i_j_ALL format
        if (obj == null) {
            key = "_" + tbody + "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //then, check _i_ALL_K format
        if (obj == null) {
            key = "_" + tbody + "_ALL_" + column;
            obj = this.components.get(key);
        }

        //check _ALL_j_k format
        if (obj == null) {
            key = "_ALL_" + row + "_" + column;
            obj = this.components.get(key);
        }

        //check _i_ALL_ALL
        if(obj == null){
            key = "_" + tbody + "_ALL_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_j_ALL
        if(obj == null){
            key = "_ALL_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_ALL_k
        if(obj == null){
            key = "_ALL_ALL_" + column;
            obj = this.components.get(key);
        }

        //last, check ALL format
        if (obj == null) {
            key = "_ALL_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    getCellSelector: function(tbody, row, column) {
        var index = tbody -1;
        if(this.bt == this.ht){
            index++;
        }
        return " > " + this.bt + ":eq(" + index + ") > " + this.brt + ":eq(" + (row-1) + ") > " + this.bct + ":eq(" + (column-1) + ")";
    },

    getHeaderSelector: function(column) {

        return " > " + this.ht + ":first > " + this.hrt + " > " + this.hct + ":eq(" + (column-1) +")";
    },

    getFootSelector: function(column) {

        return " > " + this.ft + ":last " + this.frt + " > " + this.fct + ":eq(" + (column-1) + ")";
    },

    getAllBodyCell: function(context, worker){
        !tellurium.logManager.isUseLog || fbLog("Check context in getAllBodyCell", context);
        if (context.domRef != null) {
            var $found = teJQuery(context.domRef).find(this.bt);
            !tellurium.logManager.isUseLog || fbLog("Found table body ", $found.get());
            var bodylist = new Array();

            if ($found.size() > 0) {
                //If the header tag is the same as the body tag
                if (this.ht != this.bt) {
                    bodylist.push($found.first());
                }
                for (var i = 1; i < $found.size() - 1; i++) {
                    bodylist.push($found.eq(i));
                }
                //check the footer tag
                if (this.bt != this.ft) {
                    bodylist.push($found.last());
                }

                !tellurium.logManager.isUseLog || fbLog("Valid table body ", bodylist);
                !tellurium.logManager.isUseLog || fbLog("Type of bodylist ", typeof bodylist);
                var elements = new Array();
                for(var j=0; j<bodylist.length; j++){
                    var $el = teJQuery(bodylist[j]).find(" > " + this.brt + " > " + this.bct);
                    if($el.size() > 0){
                        elements = elements.concat($el.get());
                    }
                }
                !tellurium.logManager.isUseLog || fbLog("Found table body cells ", elements);
                if(elements != null && elements.length > 0){
                    return worker.work(context, elements);
                }
            }
        }
        
        return null;
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.headers != null && this.headers.length > 0){
            var hkeys = this.headers.keySet();
            for(var i=0; i<this.headers.length; i++){
                var header = this.headers.get(hkeys[i]);
                header.traverse(context, visitor);
            }
        }

        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var j=0; j<keys.length; j++){
                var child = this.components.get(keys[j]);
                child.traverse(context, visitor);
            }
        }

        if(this.footers != null && this.footers.length > 0){
            var fkeys = this.footers.keySet();
            for(var k=0; k<this.footers.length; k++){
                var footer = this.footers.get(fkeys[k]);
                footer.traverse(context, visitor);
            }
        }
    },

    walkToHeader: function(context, uiid) {
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

        if (context.domRef != null) {
            var sel = this.getHeaderSelector(index);

            var $found = teJQuery(context.domRef).find(sel);
            !tellurium.logManager.isUseLog || fbLog("Found child " + index + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0)
                    fbError("Cannot find the child UI element " + index, this);
                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + index, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if (this.hct ==  cobj.locator.tag && cobj.locator.header == null) {
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable head ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable head ", cobj);
            return cobj.walkTo(context, uiid);
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

        if (context.domRef != null) {
            var sel = this.getFootSelector(index);

            var $found = teJQuery(context.domRef).find(sel);
            !tellurium.logManager.isUseLog || fbLog("Found child " + index + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0)
                    fbError("Cannot find the child UI element " + index, this);
                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + index, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if (this.fct == cobj.locator.tag && cobj.locator.header == null) {
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable foot ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable foot ", cobj);
            return cobj.walkTo(context, uiid);
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

        if (context.domRef != null) {
            var sel = this.getCellSelector(ntbody, nrow, ncolumn);

            var $found = teJQuery(context.domRef).find(sel);
            !tellurium.logManager.isUseLog || fbLog("Found child with CSS selector '" + sel +"' for StandardTable " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0)
                    fbError("Cannot find the child UI element ", this);
                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element ", $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if (this.bct == cobj.locator.tag && cobj.locator.header == null) {
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef).find(sel);
                    if ($found.size() > 1) {
                        //Use bestGuess() to eliminate multipe matches
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + this.uid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the StandardTable itself and got the context dom Referece", context.domRef);

        if (uiid.size() < 1)
            return this;

        var child = uiid.peek();

//        if (trimString(child) == "header") {
        if(child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
//        } else if(trimString(child) == "footer"){
        }else if(child.startsWith("_FOOTER")){
            return this.walkToFooter(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiWindow = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Window';
        this.id = null;
        this.name = null;
        this.title = null;
    }
});


function UiButtonBuilder(){

}

UiButtonBuilder.prototype.build = function(){
   return new UiButton();
};

function UiCheckBoxBuilder(){

}

UiCheckBoxBuilder.prototype.build = function(){
   return new UiCheckBox();
};

function UiDivBuilder(){

}

UiDivBuilder.prototype.build = function(){
    return new UiDiv();
};

function UiIconBuilder(){

}

UiIconBuilder.prototype.build = function(){
    return new UiIcon();
};

function UiImageBuilder(){

}

UiImageBuilder.prototype.build = function(){
    return new UiImage();
};

function UiInputBoxBuilder(){

}

UiInputBoxBuilder.prototype.build = function(){
    return new UiInputBox();
};

function UiRadioButtonBuilder(){

}

UiRadioButtonBuilder.prototype.build = function(){
    return new UiRadioButton();
};

function UiSelectorBuilder(){

}

UiSelectorBuilder.prototype.build = function(){
    return new UiSelector();
};

function UiSpanBuilder(){

}

UiSpanBuilder.prototype.build = function(){
    return new UiSpan();
};

function UiSubmitButtonBuilder(){

}

UiSubmitButtonBuilder.prototype.build = function(){
    return new UiSubmitButton();
};

function UiTextBoxBuilder(){

}

UiTextBoxBuilder.prototype.build = function(){
     return new UiTextBox();
};

function UiUrlLinkBuilder(){

}

UiUrlLinkBuilder.prototype.build = function(){
    return new UiUrlLink();
};

function UiContainerBuilder(){

}

UiContainerBuilder.prototype.build = function(){
    return new UiContainer();
};

function UiFormBuilder(){

}

UiFormBuilder.prototype.build = function(){
    return new UiForm();
};

function UiFrameBuilder(){

}

UiFrameBuilder.prototype.build = function(){
    return new UiFrame();
};

function UiListBuilder(){

}

UiListBuilder.prototype.build = function(){
    return new UiList();
};

function UiTableBuilder(){

}

UiTableBuilder.prototype.build = function(){
    return new UiTable();
};

function UiStandardTableBuilder(){

}

UiStandardTableBuilder.prototype.build = function(){
    return new UiStandardTable();
};

function UiWindowBuilder(){

}

UiWindowBuilder.prototype.build = function(){
    return new UiWindow();
};

//base UI Worker
var UiWorker = Class.extend({
     init: function() {

     },

     work: function(context, elements){
         return elements;
     }
});

var TextUiWorker = UiWorker.extend({
    work: function(context, elements){
        var out = [];
        !tellurium.logManager.isUseLog || fbLog("Starting to collect text for elements ", elements);
        if(elements != null && elements.length > 0){
            var $e = teJQuery(elements);
            $e.each(function() {
                out.push(teJQuery(this).text());
            });
            !tellurium.logManager.isUseLog || fbLog("Collected text for element ", out);
        }

        return out;
    }
});

var ToggleUiWorker = UiWorker.extend({
    work: function(context, elements, delay){
        !tellurium.logManager.isUseLog || fbLog("Starting to toggle elements ", elements);
        if(elements != null && elements.length > 0){
            var $e = teJQuery(elements);
            $e.each(function(){
                teJQuery(this).toggle("slow").fadeIn(delay).toggle("slow");

            });
        }
        !tellurium.logManager.isUseLog || fbLog("Finish toggle elements", elements);
    }
});

var ColorUiWorker = UiWorker.extend({
    work: function(context, elements, delay) {
        if (elements != null && elements.length > 0) {
            var $e = teJQuery(elements);
            $e.each(function() {
                var $te = teJQuery(this);
                $te.data("te-color-bak", $te.css("background-color"));
            });
            $e.css("background-color", "red");
            !tellurium.logManager.isUseLog || fbLog("Set elements to red for ", elements);
            $e.slideUp(100).slideDown(100).delay(delay).fadeOut(100).fadeIn(100);
            !tellurium.logManager.isUseLog || fbLog("Delayed for " + delay, this);
            $e.each(function() {
                //back to the original color
                var $te = teJQuery(this);
                $te.css("background-color", $te.data("te-color-bak"));
                $te.removeData("te-color-bak");
            });
            !tellurium.logManager.isUseLog || fbLog("Elements' color restored to original ones for ", elements);
        }
    }
});

function UiModule(){
    //top level UI object
    this.root = null;

    this.valid = false;

    //hold a hashtable of the uid to UI objects for fast access
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

    //the latest timestamp for the cache access
    this.timestamp = null;

    //UI module dump visitor
    this.dumpVisitor = new UiDumpVisitor();
}

UiModule.prototype.dumpMe = function(){
    if(this.root != null){
        fbLog("Dump UI Module " + this.id, this);
        var context = new WorkflowContext();
        this.root.traverse(context, this.dumpVisitor);
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
//            uiid.reverse();
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
        return this.root.walkTo(context, uiid);
    }

    return null;
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

function RelaxDetail(){
    //which UID got relaxed, i.e., closest Match
    this.uid = null;
    //the clocator defintion for the UI object corresponding to the UID
    this.locator = null;
    //The actual html source of the closest match element
    this.html = null;
}

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
    this.avatar = new Array();
}

var UiSnapshotNode = Class.extend({
    init: function() {
        //UID
        this.uid = null;

        //the index of the element with the same UID
        this.index = 0;

        //point to its parent in the UI SNAPSHOT tree
        this.parent = null;

        //UI object, which is defined in the UI module, reference
        this.objRef = null;

        //DOM reference
        this.domRef = null;
    }
});

var UiContainerSnapshotNode = UiSnapshotNode.extend({
    init: function(){
        this._super();
        //children nodes, regular UI Nodes
        this.components = new Array();
    }
});

var UiListSnapshotNode = UiSnapshotNode.extend({
    init: function(){
        this._super();

        //children nodes with key as the template UID and value as the UI template Avatar
        this.components = new Hashtable();
    }
});

var UiTableSnapshotNode = UiSnapshotNode.extend({
    init: function(){
        this._super();

        //header nodes with key as the template UID and value as the UI template Avatar
        this.headers = new Hashtable();

        //footer node with key as the template UID and value as the UI template Avatar
        this.footers = new Hashtable();

        //body nodes with key as the template UID and value as the UI template Avatar
        this.components = new Hashtable();
    }
});

function UiSnapshotTree(){
    //the root node
    this.root = null;

    //the reference point to the UI module that the UI Snapshot tree is derived
    this.uimRef = null;
}


//algorithms to handle UI modules and UI Objects
function UiAlg(){
    //current root DOM element
    this.dom = null;
    
    this.colors = {
        WHITE: "white",
        GRAY: "gray",
        BLACK: "black"
    };

    this.currentColor = null;

    //whether allow to use closest matching element if no one matches
    this.allowRelax = false;

    //FIFO queue to hold UI snapshots
    this.squeue = new FifoQueue();

    //FIFO queue to hold UI objects in the UI module
    this.oqueue = new FifoQueue();

    //jQuery builder to build CSS selectors
    this.cssbuilder = new JQueryBuilder();

    //array to hold all marked data("uid) so that we can remove them later
    this.uidset = new Array();
}

UiAlg.prototype.clear = function(){
    this.dom = null;
    this.currentColor = this.colors.WHITE;
    this.squeue.clear();
    this.oqueue.clear();
    this.uidset = new Array();
};

//remove all the marked data("uid")
UiAlg.prototype.unmark = function(){
    if(this.uidset != null){
        for(var i=0; i< this.uidset.length; i++){
            !tellurium.logManager.isUseLog || fbLog("Unmarking uid " + this.uidset[i].data("uid"), this.uidset[i]);
            this.uidset[i].removeData("uid");
        }
    }
};

UiAlg.prototype.nextColor = function(){
    if(this.currentColor == null){
        return this.colors.GRAY;
    }else{
        if(this.currentColor == this.colors.GRAY){
            return this.colors.BLACK;
        }

        return this.colors.GRAY;       
    }
};

UiAlg.prototype.locateInAllSnapshots = function(uiobj){
    var finished = false;
    !tellurium.logManager.isUseLog || fbLog("Initial snapshot queue in LocateInAllSnapshots", this.squeue);
    while(!finished && this.squeue.size() > 0){
        var first = this.squeue.peek();
        //check the first element color
        if(this.currentColor == first.color){
            first = this.squeue.pop();
            if(uiobj.locator != null){
                this.locate(uiobj, first);
            }else{
                var ncolor = this.nextColor();
                first.setColor(ncolor);
                this.squeue.push(first);
            }
        }else{
            //exit when the snapshot color is marked for the next round
            finished = true;
            this.currentColor = this.nextColor();
            if(this.squeue.size() == 0){
                fbError("Cannot find UI element " + uiobj.uid, uiobj);
                throw new SeleniumError("Cannot find UI element " + uiobj.uid);
            }
        }
    }
};

UiAlg.prototype.lookId = function(uiobj, $found){
    var ids = uiobj.getChildrenIds();
    if(ids != null && ids.length > 0){
         var gsel = new Array();
         for(var c=0; c < ids.length; c++){
             gsel.push(this.buildIdSelector(ids[c]));
         }
         var result = new Array();
         for(var i=0; i<$found.size(); i++){
             if(this.hasChildren($found.get(i), gsel)){
                 result.push($found.get(i));
             }
         }

         return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.lookAhead = function(uiobj, $found){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var gsel = new Array();
        for(var c=0; c < children.length; c++){
            if(children[c].locator != null)
                gsel.push(this.buildSelector(children[c].locator));
        }
        var result = new Array();
        for(var i=0; i<$found.size(); i++){
            if(this.hasChildren($found.get(i), gsel)){
                result.push($found.get(i));
            }
        }

        return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.calcBonus = function(one, gsel){
    var bonus = 0;
    var $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        if($me.find(gsel[i]).size() > 0){
            bonus++;
        }
    }

    return bonus;
};

UiAlg.prototype.hasChildren = function(one, gsel){
    var result = true;
    var $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        result = result && ($me.find(gsel[i]).size() > 0);
    }

    return result;
};

UiAlg.prototype.buildIdSelector = function(id){
    return this.cssbuilder.buildIdSelector(id);    
};

UiAlg.prototype.buildSelector = function(clocator){
    //TODO: need to add header and trailer to the selector if necessary
    return this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, clocator.position, clocator.direct, clocator.attributes);
};

//TODO: may need to pass in more attributes other than clocator, for instance, the separator in the List object
UiAlg.prototype.locate = function(uiobj, snapshot){
    var uid = uiobj.fullUid();
    var clocator = uiobj.locator;
    
    //the next color to label the snapshot
    var ncolor = this.nextColor();
    //first find its parent uid
//    var puid = this.getParentUid(uid);
    var vp = this.getValidParentFor(uiobj);
    var puid = null;
    if(vp != null)
        puid = vp.fullUid();
    
    var pref = null;
    if(puid != null){
        pref = snapshot.getUi(puid);
    }else{
        pref = this.dom;
    }

    //build the CSS selector from the current element's composite locator
    var csel = this.buildSelector(clocator);
    var $found = teJQuery(pref).find(csel);
    var foundWithoutLookAhead = false;
    if($found.size() > 0){
        foundWithoutLookAhead = true;
    }
    //if multiple matches, need to narrow down by looking ahead at the UI object's children
    if($found.size() > 1){
        if(uiobj.noCacheForChildren){
            //if there is no cache for children for UI object uiobj
            $found = this.bestEffort(uiobj, $found);
            !tellurium.logManager.isUseLog || fbLog("UI object has no cache for children, best guess result for UI object " + uiobj.uid, $found.get());
        }else{
            //first try lookId
            $found = this.lookId(uiobj, $found);
            !tellurium.logManager.isUseLog || fbLog("Look Id result for " + uiobj.uid, $found.get());
            if($found.size() > 1){
                $found = this.lookAhead(uiobj, $found);
                !tellurium.logManager.isUseLog || fbLog("Look ahead result for " + uiobj.uid, $found.get());
            }
        }
    }

    //found any nodes in the DOM by using the
    if($found.size() == 1){
        //found exactly one, happy path
        //temporially assign uid to the found element
        !tellurium.logManager.isUseLog || fbLog("Marking uid " + uid, $found.eq(0));
        $found.eq(0).data("uid", uid);
        snapshot.addUi(uid, $found.get(0));
        //store all the elements with data("uid")
        this.uidset.push($found.eq(0));
        snapshot.setColor(ncolor);
        snapshot.score += 100;
        snapshot.nelem++;
        this.squeue.push(snapshot);
    }else if($found.size() > 1){
        //multiple results, need to create more snapshots to expend the search
        for (var i = 1; i < $found.size(); i++){
            //check if the element has the "uid" in data, if not try to clone it
            if ($found.eq(i).data("uid") == undefined){
                var newsnapshot = snapshot.clone();
                newsnapshot.addUi(uid, $found.get(i));
                newsnapshot.setColor(ncolor);
                newsnapshot.score += 100;
                newsnapshot.nelem++;
                this.squeue.push(newsnapshot);
            }
        }
        //still need the push back the orignail snapshot
        if ($found.eq(0).data("uid") == undefined) {
            snapshot.addUi(uid, $found.get(0));
            snapshot.setColor(ncolor);
            snapshot.score += 100;
            snapshot.nelem++;
            this.squeue.push(snapshot);
        }
    }else{
        !tellurium.logManager.isUseLog || fbLog("allowRelax: ", this.allowRelax);
        //if allow us to relax the clocator/attribute constraints and use the closest matching ones instead
        if(this.allowRelax){
            var result = this.relax(clocator, pref);
            var $relaxed = result.closest;
            
            if ($relaxed.size() > 1) {
                $relaxed = this.lookAheadClosestMatchChildren(uiobj, $relaxed, result);
            }

            if($relaxed.size() == 1){
                //found exactly one
                //temporially assign uid to the found element
                !tellurium.logManager.isUseLog || fbLog("Marking closest match for uid " + uid, $relaxed.get(0));
                $relaxed.eq(0).data("uid", uid);

                if (!foundWithoutLookAhead) {
                    //get the relaxed details
                    var rdz = new RelaxDetail();
                    rdz.uid = uid;
                    rdz.locator = clocator;
                    rdz.html = $relaxed.eq(0).outerHTML();
                    snapshot.relaxed = true;
                    snapshot.relaxDetails.push(rdz);
                }

                snapshot.addUi(uid, $relaxed.get(0));
                snapshot.setColor(ncolor);
                snapshot.score += result.score;
                snapshot.nelem++;
                this.squeue.push(snapshot);
            }else if($relaxed.size() > 1){
                //multiple results, need to create more snapshots to expend the search
                for (var j = 1; j < $relaxed.size(); j++) {
                    if ($relaxed.eq(i).data("uid") == undefined) {

                        var nsnapshot = snapshot.clone();

                        if (!foundWithoutLookAhead) {
                            //get the relaxed details
                            var rdi = new RelaxDetail();
                            rdi.uid = uid;
                            rdi.locator = clocator;
                            rdi.html = $relaxed.eq(i).outerHTML();
                            nsnapshot.relaxed = true;
                            nsnapshot.relaxDetails.push(rdi);
                        }

                        nsnapshot.addUi(uid, $relaxed.get(j));
                        nsnapshot.setColor(ncolor);
                        nsnapshot.score += result.score;
                        nsnapshot.nelem++;
                        this.squeue.push(nsnapshot);
                    }
                }
                //still need the push back the orignail snapshot
                if ($relaxed.eq(0).data("uid") == undefined) {
                    
                    if (!foundWithoutLookAhead) {
                        //get the relaxed details
                        var rdf = new RelaxDetail();
                        rdf.uid = uid;
                        rdf.locator = clocator;
                        rdf.html = $relaxed.eq(0).outerHTML();
                        snapshot.relaxed = true;
                        snapshot.relaxDetails.push(rdf);
                    }

                    snapshot.addUi(uid, $relaxed.get(0));
                    snapshot.setColor(ncolor);
                    snapshot.score += result.score;
                    snapshot.nelem++;
                    this.squeue.push(snapshot);
                }
            }else{
                //otherwise, throw exception
//                throw new SeleniumError("Cannot find UI element " + uid);
                //do not throw exception and do not push the snapshot back to the queue instead
            }
        }else{
            //otherwise, throw exception
//            throw new SeleniumError("Cannot find UI element " + uid);
            //do not throw exception and do not push the snapshot back to the queue instead
        }   
    }
};

function MatchResult(){
    //the closest match element
    this.closest = null;

    //scaled match score, 0 - 100, or 100 percentage
    this.score = 0;

    //bonus points for best guess when handle UI templates because each template may not be presented at runtime
//    this.bonus = 0;
}

UiAlg.prototype.relax = function(clocator, pref) {
    var attrs = new Hashtable();
    if(clocator.text != null && clocator.text.trim().length > 0){
        attrs.put("text", clocator.text);
    }
    if(clocator.position != null){
        attrs.put("position", clocator.position);
    }

    var id = null;
    if (clocator.attributes != undefined) {
        for (var key in clocator.attributes) {
            if (!this.cssbuilder.inBlackList(key)) {
                attrs.put(key, clocator.attributes[key]);
            }
        }

        id = clocator.attributes["id"];
    }
    var jqs = "";
    var tag = clocator.tag;

    if (tag == null || tag == undefined || tag.trim().length == 0) {
        //TODO: need to double check if this is correct or not in jQuery
        tag = "*";
    }

    var result = new MatchResult();
    //Use tag for the initial search
    var $closest = teJQuery(pref).find(tag);
    if (id != null && id != undefined && (!this.cssbuilder.isPartial(id))) {
        jqs = this.cssbuilder.buildId(id);
        $closest = teJQuery(pref).find(jqs);
        //Because ID is unique, if ID matches, ignore all others and assign it a big value
        if($closest.size() > 0){
            result.score = 100;
            result.closest = $closest;
        }
        !tellurium.logManager.isUseLog || fbLog("Scaled Matching Score: " + result.score, result);
           
        return result;
    } else {
        jqs = tag;
        var keys = attrs.keySet();

        //number of properties, tag must be included
        var np = 1;
        //number of matched properties
        var nm = 0;

        if (keys != null && keys.length > 0) {
            np = np + keys.length;
            for (var m = 0; m < keys.length; m++) {
                var attr = keys[m];
                var tsel = this.cssbuilder.buildSelector(attr, attrs.get(attr));
                var $mt = teJQuery(pref).find(jqs + tsel);
                if ($mt.size()> 0) {
                    $closest = $mt;
                    result.closest = $closest;
                    jqs = jqs + tsel;
                    if(nm == 0){
                        nm = 2;
                    }else{
                        nm++;
                    }
                }
            }
        }

//        else{
//         }
        if($closest.size() > 0){
             nm = 1;
             result.closest = $closest;
        }

        //calculate matching score, scaled to 100 percentage
        result.score = 100*nm/np;
        !tellurium.logManager.isUseLog || fbLog("Scaled Matching Score: " + result.score, result);

        return result;
    }
};

UiAlg.prototype.hasClosestMatchChildren = function(one, clocators){
    var score = 0;
    for(var i=0; i<clocators.length; i++){
        var result = this.relax(clocators[i], one);
        if(result.closest == null || result.closest.size() == 0){
            score = 0;
            break;
        }else{
            score = score + result.score;
        }
    }
    
    return score;
};

UiAlg.prototype.lookAheadClosestMatchChildren = function(uiobj, $found, matchresult){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var clocators = new Array();
        for(var c=0; c < children.length; c++){
            clocators.push(children[c].locator);
        }
        var result = new Array();
        var max = 0;
        var closest = null;
        for(var i=0; i<$found.size(); i++){
            var score = this.hasClosestMatchChildren($found.get(i), clocators);
            if(score > 0){
                if(max < score){
                    //try to find the higest matches, for tied condition, i.e., multiple highest matches, select the first one
                    max = score;
                    closest = $found.get(i);
                }
            }
        }

        if(closest != null){
            //average the score over the element and its children
            matchresult.score = (matchresult.score + max)/(children.length + 1);
            matchresult.closest = closest;
            result.push(closest);
        }
        
        return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.bestEffort = function(uiobj, $found){
    //Implement bestGuess() for UI templates
    var children = uiobj.lookChildrenNoMatterWhat();

    if(children != null && children.length > 0){
        var gsel = new Array();
        for(var c=0; c < children.length; c++){
            gsel.push(this.buildSelector(children[c].locator));
        }

        //calculate bonus point for each element first
        var bonusArray = new Array();
        var maxbonus = 0;
        for(var i=0; i<$found.size(); i++){
            var bonus = this.calcBonus($found.get(i), gsel);
            bonusArray.push(bonus);
            if(bonus > maxbonus){
                maxbonus = bonus;
            }
        }
        !tellurium.logManager.isUseLog || fbLog("calculated bonus points for " + uiobj.uid + "'s children", bonusArray);
        var result = new Array();

        for(var j=0; j<$found.size(); j++){
            if(bonusArray[j] == maxbonus){
                result.push($found.get(j));
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Get Best Guess result for " + uiobj.uid, result);
        return teJQuery(result);
    }
    return $found;
};

UiAlg.prototype.addChildUiObject = function(uiobj){
    this.oqueue.push(uiobj);
};

UiAlg.prototype.getParentUid = function(uid){
    var indx = uid.lastIndexOf(".");
    if(indx != -1){
        return uid.slice(0, indx);
    }

    return null;
};

UiAlg.prototype.getValidParentFor = function(uiobj){
    var validParent = uiobj.parent;

    while(validParent != null){
        if(validParent.locator == null){
             //walk up if the parent is a logical container
            validParent = validParent.parent;
        }else{
            break;
        }
    }

    return validParent;
};

//traverse the UI module to build a snapshot tree
UiAlg.prototype.buildSnapshotTree = function(uimodule){
    this.clear();
    var tree = new UiSnapshotTree();
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Traverse for Object " + uiobj.uid + ": ", uiobj);
//        uiobj.locate(this);
    }

    return tree;
};

//
//The santa algorithm, i.e., Tellurium UI module group locating algorithm
//
//The santa name comes from the fact that I designed and finalized the algorithm
//during the Christmas season in 2009, which is a gift for me from Santa Claus
//
// by Jian Fang (John.Jian.Fang@gmail.com)
//
UiAlg.prototype.santa = function(uimodule, rootdom){
    !tellurium.logManager.isUseLog || fbLog("call santa algorithm for UI Module ", uimodule);
    this.clear();
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    !tellurium.logManager.isUseLog || fbLog("UiAlg states before group locating: ", this);
    !tellurium.logManager.isUseLog || fbLog("Initial object queue ", this.oqueue);
    !tellurium.logManager.isUseLog || fbLog("Initial snapshot queue ", this.squeue);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid, uimodule);
        throw new SeleniumError("Cannot locate UI module " +  uimodule.root.uid);
    }

    //if allow closest match
    if (this.allowRelax) {
        !tellurium.logManager.isUseLog || fbLog("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
        uimodule.matches = this.squeue.size();
        //use match score to select the best match
        var snapshot = this.squeue.pop();
        var maxscore = snapshot.getScaledScore();
        while (this.squeue.length > 0) {
            var nsnapshot = this.squeue.pop();
            var nscore = nsnapshot.getScaledScore();
            if (nscore > maxscore) {
                snapshot = nsnapshot;
                maxscore = nscore;
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
        this.bindToUiModule(uimodule, snapshot);
        this.unmark();
    } else {
        //for exact match, cannot have multiple matches
        if (this.squeue.size() > 1) {
            fbError("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
            throw new SeleniumError("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid);
        }
        //found only one snapshot, happy path
        var osnapshot = this.squeue.pop();
        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", osnapshot);
        this.bindToUiModule(uimodule, osnapshot);
        this.unmark();
        uimodule.matches = 1;
    }
};

UiAlg.prototype.validate = function(uimodule, rootdom){
    this.clear();
    var relaxflag = this.allowRelax;
    this.allowRelax = true;
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid, uimodule);
        uimodule.matches = 0;
    }
    if (this.squeue.size() >= 1) {
        !tellurium.logManager.isUseLog || fbLog("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
        uimodule.matches = this.squeue.size();
        //use match score to select the best match
        var snapshot = this.squeue.pop();
        var maxscore = snapshot.getScaledScore();
        while (this.squeue.length > 0) {
            var nsnapshot = this.squeue.pop();
            var nscore = nsnapshot.getScaledScore();
            if (nscore > maxscore) {
                snapshot = nsnapshot;
                maxscore = nscore;
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
        this.bindToUiModule(uimodule, snapshot);
        this.unmark();
    }
    this.allowRelax = relaxflag;
};

UiAlg.prototype.bindToUiModule = function(uimodule, snapshot){
    this.oqueue.clear();
    this.oqueue.push(uimodule.root);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        uiobj.bind(snapshot, this);
    }
    //add index to Ui Module for uid to dom reference reference for fast access
    !tellurium.logManager.isUseLog || fbLog("Adding uid to dom reference indices for UI module " + uimodule.root.uid, snapshot.elements);
    uimodule.indices = snapshot.elements;
    uimodule.relaxed = snapshot.relaxed;
    uimodule.relaxDetails = snapshot.relaxDetails;
    uimodule.score = snapshot.getScaledScore();
};

function TrieNode() {
    //identifier
    this.key = -1;

    //hold the String value for this node
    this.id = null;

    //the data this node holds
    this.data = null;
    
    //the level of this node in the Trie tree
    this.level = 0;

    //pointer to its parent
    this.parent = null;

    //child nodes
    this.children = new Array();
}

TrieNode.prototype.addChild = function(child) {
    this.children.push(child);
};

TrieNode.prototype.removeArrayAt = function(ar, index )
{
  var part1 = ar.slice( 0, index);
  var part2 = ar.slice( index+1 );

  return( part1.concat( part2 ) );
};

TrieNode.prototype.findIt = function( key )
{
    var result = (-1);

    for( var i = 0; i < this.children.length; i++ )
    {
        if( this.children[i].key == key )
        {
            result = i;
            break;
        }
    }
    return result;
};

TrieNode.prototype.removeChild = function(child) {
    var elementIndex = this.findIt(child.key);

    if( elementIndex != -1 )
    {
        this.children = this.removeArrayAt(this.children, elementIndex);
    }
};

TrieNode.prototype.getChildrenSize = function() {
    return this.children.length;
};

TrieNode.prototype.checkLevel = function() {
    if (this.parent == null)
        this.level = 0;
    else
        this.level = this.parent.level + 1;
    if (this.children.length > 0) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].checkLevel();
        }
    }
};

TrieNode.prototype.getFullId = function() {
    if (this.parent == null) {
        return this.id.getUid();
    }

    return this.parent.getFullId() + "." + this.id.getUid();
};

TrieNode.prototype.printMe = function(isTrace) {
    var hasChildren = false;
    if (this.children.length > 0)
        hasChildren = true;
    var sb = new StringBuffer();
    for (var i = 0; i < this.level; i++) {
        sb.append("  ");
    }
    if(this.parent == null){
        sb.append("Trie").append(": ");
    }else{
        sb.append(this.id.getUid()).append("[").append(this.data).append("]");
    }
    if (hasChildren)
        sb.append("{");
    if(isTrace){
        fbLog(sb.toString(), this);
    }else{
        fbLog(sb.toString(), "");
    }
    if (hasChildren) {
        for (var n = 0; n < this.children.length; n++) {
            this.children[n].printMe(isTrace);
        }
    }
    if (hasChildren) {
        var indent = new StringBuffer();
        for (var j = 0; j < this.level; j++) {
            indent.append("  ");
        }
        indent.append("}");
        if(isTrace){
            fbLog(indent.toString(), this);
        }else{
            fbLog(indent.toString(), "");
        }
    }
};

function Trie() {

    this.root = null;

}

Trie.prototype.getKey = function(){
    return tellurium.idGen.next();
};

Trie.prototype.getChildrenData = function(id){
    var result = new Array();
    if(this.root == null || this.root.getChildrenSize() == 0)
        return result;

    var uiid = getUiid(id);

    return this.walk(this.root, uiid, result);
};

function TrieMatch(){
    this.score = 0;
    this.node = null;
}

Trie.prototype.walk = function(current, uiid, result){
    //there are children for the current node
    //check if the new String is a prefix of current node's child or a child is a prefix of the input String,
    var matches = new Array();
    var maxscore = 0;
    var i;
    for (i = 0; i < current.getChildrenSize(); i++) {
        var anode = current.children[i];
        var pm = uiid.matchWith(anode.id);
        var score = pm.length;
        if(score > 0){
            if(maxscore < score)
                maxscore = score;
            var matchresult = new TrieMatch();
            matchresult.score = score;
            matchresult.node = anode;
            matches.push(matchresult);
        }
    }

    //there may be multiple children
    var mchildren = new Array();
    for(i=0; i < matches.length; i++){
        if(matches[i].score == maxscore){
            mchildren.push(matches[i].node);
        }
    }

    //found one child
    if(mchildren.length == 1){
        //the child is the id itself
        if(uiid.size() == mchildren[0].id.size()){
            //return all the children for this node
            for(i=0; i<mchildren[0].getChildrenSize(); i++){
                result.push(mchildren[0].children[i].data);
            }            
//            return result;
        }else if(uiid.size() < mchildren[0].id.size()){
            result.push(mchildren[0].data);
//            return result;
        }else{
             //need to do further walk down
            var leftover = uiid.subUiid(mchildren[0].id.size());
            return this.walk(mchildren[0], leftover, result);
        }

    }else if(mchildren.length > 1){
        //more than one children match
        //
        //consider the scenario, the Prie is
        //         Username.Input
        //   Form
        //         Username.Label
        //
        //  and the input String is
        //
        //    Form.Username.Submit
        //
        // In this case, it cannot find any children. But if the input String is,
        //
        //    Form.Username
        //
        // it found both Form.Username.Input and Form.Username.Label
        //

        if(uiid.size() == maxscore){
            //The id is a prefix of the found children
            //That means all children are found
            for(i=0; i<mchildren.length; i++){
                result.push(mchildren[i].data);
            }
        }
        //otherwise, treat it as not found
    }

    return result;
};

Trie.prototype.insert = function(id, data) {
    var uiid = getUiid(id);
    if (this.root == null) {
        //If it is the first time to insert an word to the Tire
        this.root = new TrieNode();
        //root is an empty String, more like a logic node
        this.root.id = "";
        this.root.level = 0;
        this.root.parent = null;
        this.root.key = this.getKey();

        //add the word as the child of the root node
        var child = new TrieNode();

        child.id = uiid;
        child.data = data;
        child.parent = this.root;
        child.key = this.getKey();
        this.root.addChild(child);
    } else {
        //not the first node, need to walk all the way down to find a place to insert
        this.build(this.root, uiid, data);
    }
};

Trie.prototype.build = function(current, uiid, data) {
    //look at current node's children
    if (current.getChildrenSize() == 0) {
        //no child yet, add itself as the first child
        var child = new TrieNode();
        child.id = uiid;
        child.data = data;
        child.parent = current;
        child.key = this.getKey();
        current.addChild(child);
    } else {
        //there are children for the current node
        //check if the new String is a prefix of a set of children
        var common = new Array();
        for (var i = 0; i < current.getChildrenSize(); i++) {
            var anode = current.children[i];
//            if (anode.id.startsWith(id)) {
            if(anode.id.matchWith(uiid).length == uiid.size()){
                common.push(anode);
            }
        }
        //if the new String is indeed a prefix of a set of children
        if (common.length > 0) {
            var shared = new TrieNode();
            shared.id = uiid;
            shared.data = data;
            shared.parent = current;
            shared.key = this.getKey();
            for (var j = 0; j < common.length; j++) {
                var node = common[j];
                //assume no duplication in the tree, otherwise, need to consider the empty string case for a child
                node.id = node.id.subUiid(uiid.size());
                node.parent = shared;
                shared.addChild(node);
                current.removeChild(node);
            }
            current.addChild(shared);
        } else {
            //no common prefix available, then check if the child is a prefix of the input String
            var found = false;
            var next = null;
            for (var k = 0; k < current.getChildrenSize(); k++) {
                var pnode = current.children[k];
//                if (uiid.startsWith(pnode.id)) {
                if(uiid.matchWith(pnode.id).length == pnode.id.size()){
                    found = true;
                    next = pnode;
                    break;
                }
            }
            if (found) {
                //not a duplication, otherwise, do nothing
                if (uiid.size() != next.id.size()) {
                    var leftover = uiid.subUiid(next.id.size());
                    this.build(next, leftover, data);
                }
            } else {
                //not found, need to create a new node a the child of the current node
                var achild = new TrieNode();
                achild.parent = current;
                achild.id = uiid;
                achild.data = data;
                achild.key = this.getKey();
                current.addChild(achild);
            }
        }
    }
};

Trie.prototype.checkLevel = function() {
    if (this.root != null) {
        this.root.checkLevel();
    }
};

Trie.prototype.printMe = function() {
    if (this.root != null) {
        fbLog("---------------------------- Trie/Prefix Tree ----------------------------\n", "");
        this.root.printMe(false);
        fbLog("--------------------------------------------------------------------------\n", "");
    }
};

Trie.prototype.dumpMe = function() {
    if (this.root != null) {
        fbLog("---------------------------- Trie/Prefix Tree ----------------------------\n", this);
        this.root.printMe(true);
        fbLog("--------------------------------------------------------------------------\n", this);
    }
};