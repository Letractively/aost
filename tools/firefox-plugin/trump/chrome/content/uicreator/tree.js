function Tree(){
    this.root = null;
    this.xpx = new XPathComparator();
}

Tree.prototype.printUI = function(){
    if(this.root != null){
        this.root.printUI();
    }
}

Tree.prototype.addElement = function(element){
    //case I: root is null, insert the first node
        if(this.root == null){
            this.root = new NodeObject();
            this.root.id = element.uid;
            this.root.parent = null;
            this.root.xpath = element.xpath;
            this.root.attributes = element.attributes;
        }else{
            //not the first node, need to match element's xpath with current node's relative xpath starting from the root
            //First, need to check the root and get the common xpath
            var common = XPathMatcher.match(root.xpath, element.xpath);
            var leftover = XPathMatcher.remainingXPath(element.xpath, common);

            if(root.xpath == common){
                //the current node shares the same common xpath as the new node
                //no extra node need to be added for the current node
                //then check current node's children
                if(root.children.length == 0){
                    //no children, so create a new child
                    if(leftover != null && leftover.length > 0){
                        //only create the child if there are extra xpath
                        var child = new NodeObject();
                        child.id = element.uid();
                        child.xpath = XPathMatcher.remainingXPath(element.xpath, common);
                        child.attributes = element.attributes;
                        child.parent = root;

                        root.addChild(child);
                    }
                }else{
                    //there are children
                    walk(root, element.getUid(), leftover, element.getAttributes());
                }

            }else{
                var newroot = new NodeObject();
                newroot.id = "root";
                newroot.xpath = common;
                newroot.parent = null;
                var newxpath = XPathMatcher.remainingXPath(this.root.xpath, common);
                if(this.root.id != null && this.root.id == "root"){
                    this.root.id = Uid.genUid(newxpath);
                }
                this.root.xpath = newxpath;
                this.root.parent = newroot;

                newroot.addChild(this.root);

                this.root = newroot;

                if (leftover != null && leftover.length > 0) {
                    //only create the child if there are extra xpath
                    var child = new NodeObject();
                    child.id = element.uid;
                    child.xpath = XPathMatcher.remainingXPath(element.xpath, common);
                    child.attributes = element.attributes;
                    child.parent = this.root;
                    this.root.addChild(child);
                }
            }
        }
}

Tree.prototype.walk = function(current, uid, xpath, attribute){
     if(current.children.length == 0 ){
            //there is no children
            if(xpath.trim().length > 0){
                //only create the child if there are extra xpath
                var child = new NodeObject();
                child.id = uid;
                child.xpath = xpath;
                child.attributes = attribute;
                child.parent = current;
                current.addChild(child);
            }
        }else{
            PriorityQueue<XPath> queue = new PriorityQueue<XPath>(16, xpc);

            for(var i=0; i<current.children.length; ++i){
                var node = current.children[i];
                var xp = new XPath();
                xp.xpath = XPathMatcher.match(node.xpath, xpath);
                xp.setNode(node);
                queue.add(xp);
            }

            var max = new Array();

            var maxlen = queue.peek().xpath.length;

            var length;
            for(var i=0; i< queue.size(); i++){
                var mxp = queue.remove();
                length = mxp.xpath.length;
                if(length == maxlen){
                    max.push(mxp);
                }else{
                    break;
                }
            }

            var mx = max.get(0);

            var common = mx.xpath;

            if(mx.node.xpath == common){
                //The xpath includes the common part, that is to say, we need to walk down to the child
                if(max.size() > 1){
                    //we need to merge multiple nodes into one
                    for(var i=1; i<max.size(); i++){
                        var cnode = max.get(i).node;

                        var left = XPathMatcher.remainingXPath(cnode.xpath, common);
                        if(left.length > 0){
                            //have more for the left over xpath
                            cnode.xpath =left;
                            cnode.parent = mx.node;

                            current.removeChild(cnode.id());
                        }else{
                            for(var j=0; j<cnode.children.length ; ++j){
                                var childNode = cnode.children[j];
                                mx.node.addChild(childNode);
                            }
                            current.removeChild(cnode.id);
                        }
                    }
                }
                this.walk(mx.node, uid, XPathMatcher.remainingXPath(xpath, common), attribute);
            }else{
                //need to create extra node
                var extra = new NodeObject();
                extra.xpath = common;
                extra.parent = current;
                extra.id = Uid.genUid(common);
                current.addChild(extra);
                for(var k=0; k<max.length; ++k){
                    var xp = max[k];
                    var cnode = xp.node;
                    cnode.xpath = XPathMatcher.remainingXPath(cnode.xpath, common);
                    cnode.parent = extra;
                    current.removeChild(cnode.id);
                }

                var child = new NodeObject();
                child.id = uid;
                child.xpath = XPathMatcher.remainingXPath(xpath, common);
                child.attributes = attribute;
                child.parent = extra;
                extra.addChild(child);
            }
        }
    }
