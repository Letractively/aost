function Index(){
    this.type = null;
    this.value = null;
}

function RIndex() {
  //for tbody
  this.x = null;

  //for row
  this.y = null;

  //for column
  this.z = null;
}



var MetaData = Class.extend({
   init: function() {
       this.id = null;
   }
});

var ListMetaData = MetaData.extend({
    init: function(){
        this._super();
        this.index = null;
    }
});

var TableMetaData = MetaData.extend({
    init: function(){
        this._super();
        this.tbody = null;
        this.row = null;
        this.column = null;
    }
});

function Path(){
    this.stack = new Array();
}

Path.prototype.init = function(paths) {
    if (paths != null && paths.length > 0) {
        for (var i = paths.length - 1; i >= 0; i--) {
            this.stack.push(paths[i]);
        }
    }
};

Path.prototype.size = function(){
    return this.stack.length;
};

Path.prototype.pop = function(){
    if(this.stack.length > 0){
        return this.stack.pop();
    }

    return null;
};

var RNode = Class.extend({
    init: function() {
        this.key = null;
        this.bias = 0;
        this.parent = null;
        this.children = new Array();
        this.objectRef = null;
        this.presented = false;
        this.templates = new Array();
    },

    create: function(key, parent, objectRef, presented) {
        this.key = key;
        this.parent = parent;
        this.objectRef = objectRef;
        this.presented = presented;
    },

    addTemplate: function(template){
        if(!this.contains(template))
            this.templates.push(template);
    },

    contains: function(template){
        for(var i=0; i<this.templates.length; i++){
            if(template == this.templates[i])
                return true;
        }

        return false;
    },

    addChild: function(child) {
        this.children.push(child);
    },

    findChild: function(key){
        if(this.children.length > 0){
            for(var i=0; i<this.children.length; i++){
                if(key == this.children[i].key){
                    return this.children[i];
                }
            }
        }

        return null;
    },

    getFitness: function() {
        if (this.parent == null)
            return this.bias;
        else
            return this.parent.getFitness() + 1;
    },

    isInPath: function(key, paths){
        var result = false;

        if(paths != null && paths.length > 0){
            for(var i=0; i<paths.length; i++){
                if(key == paths[i]){
                    result = true;
                    break;
                }
            }
        }

        return result;
    },

    walkTo: function(key, path){
        var i;
        if(path.size() > 0){
            var next = path.pop();
            if(this.children != null && this.children.length > 0){
                for(i=0; i<this.children.length; i++){
                    if(key == this.children[i].key){
                        var result = this.children[i].walkTo(key, path);
                        if(result != null)
                            return result;
                    }
                }
            }
        }else{
            if(this.children != null && this.children.length > 0){
                for(i=0; i<this.children.length; i++){
                    if(key == this.children[i].key){
                        return this.children[i];
                    }
                }
            }
        }

        if(this.presented)
            return this;

        return null;
    }
});

var RTree = Class.extend({
    init: function(){
        this.EMPTY_PATH = [];
        this.ROOT_PATH = ["all"];
        this.ODD_PATH = ["all", "odd"];
        this.EVEN_PATH = ["all", "even"];

        this.root = null;
        this.indices = new Hashtable();
    },
    
    insert: function(object){
        var meta = object.metaData;
//        this.createIndex(meta.id, object);
        var index = meta.index.value;
        if("all" == index){
            this.root.objectRef = object;
            this.root.presented = true;
        }else if("odd" == index){
            var oddNode = this.root.findChild("odd");
            oddNode.presented = true;
            oddNode.objectRef = object;
        }else if("even" == index){
            var evenNode = this.root.findChild("even");
            evenNode.presented = true;
            evenNode.objectRef = object;
        }else if("last" == index){
            var last = this.root.findChild("last");
            if(last == null){
                last = new RNode();
                last.create("last", this.root, object, true);
                this.root.addChild(last);
            }
        }else if("any" == index){

        }else if("first" == index){
            var oNode = this.root.findChild("odd");
            var f = oNode.findChild("1");
            if(f == null){
                f = new RNode();
                f.create("1", oNode, object, true);
                oNode.addChild(f);   
            }
        }else if(index.match(/^\d+$/)){
            var inx = parseInt(index);
            if((inx % 2) == 1 ){
                var odNode = this.root.findChild("odd");
                var inode = odNode.findChild(index);
                if(inode == null){
                    inode = new RNode();
                    inode.create(index, odNode, object, true);
                    odNode.addChild(inode);
                }
            }else{
                var eNode = this.root.findChild("even");
                var nNode = eNode.findChild(Index);
                if(nNode == null){
                    nNode = new RNode();
                    nNode.create(index, eNode, object, true);
                    eNode.addChild(nNode);
                }
            }
        }else{
            throw new SeleniumError("Invalid Index" + index);
        }
    },

    preBuild: function(){
        var defaultUi = new UiTextBox();
        var allNode = new RNode();
        allNode.create("all", null, defaultUi, true);
        this.root = allNode;
        var oddNode = new RNode();
        oddNode.create("odd", allNode, defaultUi, false);
        this.root.addChild(oddNode);
        var evenNode = new RNode();
        evenNode.create("even", allNode, defaultUi, false);
        this.root.addChild(evenNode);
    },

    route: function(key){
        var object = this.indices.get(key);
        if(object == null){
            if("first" == key){
                key = "1";
            }

            var list = this.generatePath(key);
            var path = new Path();
            path.init(list);
            object = this.walkTo(key, path);
        }

        return object;
    },

    generatePath: function(key){
        if("odd" == key || "even" == key || "last" == key){
            return this.ROOT_PATH;
        }else if(key.match(/^\d+$/)){
            var inx = parseInt(key);
            if((inx % 2) == 1){
                return this.ODD_PATH;
            }else{
                return this.EVEN_PATH;
            }
        }else if("all" == key){
            return this.EMPTY_PATH;
        }else{
            throw new SeleniumError("Invalid Index" + key);
        }
    },

    walkTo: function(key, path){
        if("all" == key){
            return this.root.objectRef;
        }

        if(path != null && path.size() > 0){
            path.pop();
            var node = this.root.walkTo(key, path);
            if(node != null){
                return node.objectRef;
            }
        }

        return null;
    },

    createIndex: function(key, uiobject){
        this.indices.put(key, uiobject);
    }
});
