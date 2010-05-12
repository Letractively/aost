//New UI Module generation Algorithm


//DOM Node
function DNode(){
    this.id = null;
    this.parent = null;
    this.children = new Array();
    this.tagObject = null;
    //reference to the DTree
//    this.treeRef = null;
}

DNode.prototype.updateIndex = function(index){
    index.put(this.id, this);
    if(this.children != null && this.children.length > 0){
        for(var i=0; i< this.children.length; i++){
            this.children[i].updateIndex(index);
        }
    }
};

DNode.prototype.findRoot = function(){
    if(this.parent != null)
        return this.parent.findRoot();

    return this;
};

DNode.prototype.addChild = function(dNode){
    this.children.push(dNode);
};

DNode.prototype.findChild = function(uid){
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            if(this.children[i].id == uid){
                return this.children[i];
            }
        }
    }
};

DNode.prototype.hasChild = function(uid){
    if(this.children != null && this.children.length > 0){
        for(var i=0; i<this.children.length; i++){
            if(this.children[i].id == uid){
                return true;
            }
        }
    }

    return false;
};

//Dom Tree
function DTree(){
    this.root = null;
    //index from DNode id to DNode
    this.index = new Hashtable();
}

DTree.prototype.isExistParent = function(uid){
    if(this.root != null){
        var $parent = teJQuery(this.root.tagObject.node).closest(":data(uid," + uid + ")");
        return $parent.size() > 0;
    }

    return false;
};

DTree.prototype.updateIndex = function(){
    if(this.root != null){
        this.root.updateIndex(this.index);
    }
};

DTree.prototype.appendIndex = function(newNodes){
    if(newNodes != null && newNodes.length > 0){
        for(var i=0; i<newNodes.length; i++){
            this.index.put(newNodes[i].id, newNodes[i]);
        }
    }
};

DTree.prototype.find = function(uid){
    return this.index.get(uid);
};

function LevelComparator(){
    this.DELIMITER = "/";
}

//variable a, b are TagObjects
LevelComparator.prototype.compare = function(a, b){
    var la = a.xpath.split(this.DELIMITER).length;
    var lb = b.xpath.split(this.DELIMITER).length;

    return la > lb;
};


//Tellurium UI Module Generating Algorithm (TUG)
var TugAlg = Class.extend({
    init: function() {
        this.queue = new PriorityQueue();
        this.queue.comparator = new LevelComparator();

        //each candidate is a DTree
        this.candidates = new Array();
        //Generated UI Modules
        this.uiModules = new Array();
        
        this.tagObjectIndex = new Hashtable();
        this.builder = new Builder();
    },

    prepare: function(tagObjects){
        if(tagObjects != null && tagObjects.length > 0){
            for(var i=0; i<tagObjects.length; i++){
                //insert tag objects into a priority queue so that we can retrieve the max level element first
                this.queue.insert(tagObjects[i]);
                this.tagObjectIndex.put(tagObjects[i].uid, tagObjects[i]);
            }
        }
    },

    isProcessed: function(uid){
        if(this.candidates.length == 0)
            return false;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return true;
        }

        return false;
    },

    findProcessed: function(uid){
        if(this.candidates.length == 0)
            return null;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return obj;
        }

        return null;
    },

    findDTree: function(uid){
        if(this.candidates.length == 0)
            return null;

        for(var i=0; i<this.candidates.length; i++){
            var obj = this.candidates[i].find(uid);
            if(obj != null)
                return this.candidates[i];
        }

        return null;
    },

    findTagObject: function(uid){
        return this.tagObjectIndex.get(uid);
    },

    build: function() {
        while (this.queue.size() > 0) {
            var max = this.queue.extractMax();
            var newNodes = new Array();

            if (max != null) {
                if (!this.isProcessed(max.uid)) {
                    var current = new DNode();
                    current.id = max.uid;
                    current.tagObject = max;
                    newNodes.push(current);

                    var $closest = teJQuery(max.node).closest(':data(uid)');
                    var needNewDTree = true;

                    while ($closest.size() > 0) {
                        var uid = $closest.eq(0).data("uid");
                        var processed = this.findProcessed(uid);
                        if (processed == null) {
                            //Not in candidate list
                            var parent = new DNode();
                            parent.id = uid;
                            parent.children.push(current);
                            parent.tagObject = this.findTagObject(uid);
                            newNodes.push(parent);
                            $closest = teJQuery(parent.tagObject.node).closest(':data(uid)');
                            current = parent;
                        } else {
                            //already processed, attach to it
                            if (!processed.hasChild(current.id)) {
                                processed.addChild(current);
                            }
                            current = processed;
                            // do not need to traverse up the DOM further
                             needNewDTree = false;

                            var dt = this.findDTree(uid);
                            dt.appendIndex(newNodes);
                            break;
                        }
                    }

                    if(needNewDTree){
                        var dTree = new DTree();
                        dTree.root = current.findRoot();
                        dTree.appendIndex(newNodes);
                        this.candidates.push(dTree);
                    }
                }

            }
        }
    },

    merge: function(){
        var candidate = this.candidates[0];

        var $current = teJQuery(candidate.root.tagObject.node);
        var $parent = $current.parent();

        while($parent.size() > 0){
            var i;

            $parent.data("uid", "newroot");
            var found = true;

            for(i=1; i<this.candidates.length; i++){
                if(!this.candidates[i].isExistParent("newroot")){
                    found = false;
                }
            }

            if(!found){
                $current = $parent;
            }else{
                var tagObj = this.builder.createTagObject($parent.get(0), "newroot");

                var root = new DNode();
                root.parent = null;
                root.id = "newroot";
                root.tagObject = tagObj;
                for(i=0; i<this.candidates.length; i++){
                    root.addChild(this.candidates[i].root);
                }

                var dTree = new DTree();
                dTree.root = root;
                dTree.updateIndex();

                this.candidates = new Array();
                this.candidates.push(dTree);
                
                break;
            }
        }
    },

    suggestName: function(tagObject){
        
    },

    transform: function(dTree){
        
    }

});