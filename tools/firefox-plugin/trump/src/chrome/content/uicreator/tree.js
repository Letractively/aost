function Tree(){
    this.root = null;
    this.xpathMatcher = new XPathMatcher();
    this.uiModel = new Array();
    this.uid = new Uid();
}

Tree.prototype.printUI = function(){
    if(this.root != null){
        this.root.printUI(this.uiModel);
        return this.uiModel;
    }
}

Tree.prototype.addElement = function(element){
    alert("add Element UID: " + element.uid + " XPATH:" + element.xpath);
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
            var common = this.xpathMatcher.match(this.root.xpath, element.xpath);
//            alert("common : " + common);
            var leftover = this.xpathMatcher.remainingXPath(element.xpath, common);
//            alert("leftover : " + leftover);

            if(this.root.xpath == common){
                //the current node shares the same common xpath as the new node
                //no extra node need to be added for the current node
                //then check current node's children
                if(this.root.children.length == 0){
                    //no children, so create a new child
                    if(leftover != null && leftover.length > 0){
                        //only create the child if there are extra xpath
                        var son = new NodeObject();
                        son.id = element.uid();
                        son.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                        son.attributes = element.attributes;
                        son.parent = this.root;
                        alert("son : " + son);
                        this.root.addChild(son);
                    }
                }else{
                    //there are children
                    this.walk(this.root, element.uid, leftover, element.attributes);
                }

            }else{
                var newroot = new NodeObject();
                newroot.id = "root";
                newroot.xpath = common;
                newroot.parent = null;
                var newxpath = this.xpathMatcher.remainingXPath(this.root.xpath, common);
//                alert("newxpath : " + newxpath);
                if(this.root.id != null && this.root.id == "root"){
                    this.root.id = this.uid.genUid(newxpath);
                    alert("this.root.id : " + this.root.id);
                }
                this.root.xpath = newxpath;
                this.root.parent = newroot;
                alert("adding child ID: " + this.root + this.root.id + " XPATH:" + this.root.xpath);
                newroot.addChild(this.root);

                this.root = newroot;

                if (leftover != null && leftover.length > 0) {
                    //only create the child if there are extra xpath
                    var child = new NodeObject();
                    child.id = element.uid;
                    child.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                    child.attributes = element.attributes;
                    child.parent = this.root;
                    alert("adding child ID:" + child.id + " XPATH:" + child.xpath);
                    this.root.addChild(child);
                }
            }
        }
}

Tree.prototype.walk = function(current, uid, xpath, attribute) {
    if (current.children.length == 0) {
        //there is no children
//        if (xpath.trim().length > 0) {
        if (trimString(xpath).length > 0) {
            //only create the child if there are extra xpath
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attribute;
            child.parent = current;
            //                alert("adding child : " + child);
            current.addChild(child);
        }
    } else {
        var cmp = new Array();
        var maxlen = 0;
        for(var l=0; l < current.children.length; ++l){
            var nd = current.children[l];
            var xpt = new XPath();
            xpt.xpath = this.xpathMatcher.match(nd.xpath, xpath);
            xpt.node = nd;
            if(xpt.xpath.length > maxlen){
                maxlen = xpt.xpath.length;
            }
            cmp.push(xpt);
        }
        alert("walk for UID:" + uid + " XPATH:" + xpath + " max common lenth: " + maxlen);

 /*
        var queue = new PriorityQueue();
                    alert("Iterating child nodes");
        for (var l = 0; l < current.children.length; ++l) {
            var nd = current.children[l];
                            alert("child node " + nd + " xpath : " + nd.xpath);

            var xpt = new XPath();
                            alert("matching xpath : " + nd.xpath +" , " + xpath);
            xpt.xpath = this.xpathMatcher.match(nd.xpath, xpath);
            xpt.node = nd;
                            alert("Inserting into the queue : node : "+ nd + " priority : " + xpt.xpath.length);
            queue.insert(xpt, xpt.xpath.length);
        }

        var max = new Array();

        var maxlen = queue.peek().priority;
 */

        //need to handle the situation where there is no common xpath
        if (maxlen == 0) {

            alert("No shared common xpath, add child UID:" + uid + " XPATH:" + xpath + " directly");
            //there is no shared common xpath, add the node directly
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attribute;
            child.parent = current;
            current.addChild(child);
        } else {
            //there are shared common xpath
            var max = new Array();
            for(var m=0; m<cmp.length; m++){
                if(cmp[m].xpath.length == maxlen){
                    max.push(cmp[m])
                }
            }


/*
            var length;
                        alert("queue size : " + queue.size());
                        alert("queue : " + queue.display());

            for (var i = 0; i < queue.size(); i++) {
                var mxp = queue.remove();
                                alert("mxp : " + mxp);
                                alert("mxp.xpath : " + mxp.xpath);
                if (mxp) {
                    length = mxp.xpath.length;
                                        alert("length : "+length);
                    if (length == maxlen) {
                        max.push(mxp);
                    }
                } else {
                    break;
                }
            }
  */
            
            var mx = max[0];

            var common = mx.xpath;

            alert("Shared common XPATH:" + common)

            if (mx.node.xpath == common) {
                //The xpath includes the common part, that is to say, we need to walk down to the child
                if (max.size() > 1) {
                    //we need to merge multiple nodes into one
                    for (var t = 1; t < max.length; t++) {
                        var cnode = max[t].node;

                        var left = this.xpathMatcher.remainingXPath(cnode.xpath, common);
                        if (left.length > 0) {
                            //have more for the left over xpath
                            cnode.xpath = left;
                            cnode.parent = mx.node;

                            current.removeChild(cnode.id());
                        } else {
                            for (var j = 0; j < cnode.children.length; ++j) {
                                var childNode = cnode.children[j];
                                mx.node.addChild(childNode);
                            }
                            current.removeChild(cnode.id);
                        }
                    }
                }
                this.walk(mx.node, uid, this.xpathMatcher.remainingXPath(xpath, common), attribute);
            } else {
                //need to create extra node
                var extra = new NodeObject();
                extra.xpath = common;
                extra.parent = current;
                alert("Add extra node: common " + common + " max size " + max.length );
                extra.id = this.uid.genUid(common);
                alert("Extra ID:" + extra.id + " XPATH:" + extra.xpath);
                current.addChild(extra);
                for (var k = 0; k < max.length; ++k) {
                    var xp = max[k];
                    var cn = xp.node;
                    cn.xpath = this.xpathMatcher.remainingXPath(cn.xpath, common);
                    cn.parent = extra;
                    extra.addChild(cn);
                    current.removeChild(cn.id);
                    alert("Node XPATH:" + cn.xpath + " ID:" + cn.id);
                }

                var ch = new NodeObject();
                ch.id = uid;
                ch.xpath = this.xpathMatcher.remainingXPath(xpath, common);
                ch.attributes = attribute;
                ch.parent = extra;
                extra.addChild(ch);
                alert("Add extra node: extra " + extra.xpath );               
            }

        }

    }
}
