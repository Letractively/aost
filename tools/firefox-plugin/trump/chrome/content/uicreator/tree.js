function Tree(){
    this.root = null;
    this.xpathMatcher = new XPathMatcher();
    this.uiModel = new Array();
    this.uid = new Uid();
    
    //An Array to hold reference to all the UI objects in the Tree
    //change it to a HashMap so that we can access it by key
    this.uiObjectMap = null;
}

Tree.prototype.printUI = function(){
    this.uiModel = new Array();
    if(this.root != null){
        this.root.printUI(this.uiModel);
        return this.uiModel;
    }
};

Tree.prototype.buildXML = function(){
    if(this.root != null){
        var xmlArray = new Array();
        this.root.buildXML(xmlArray);
        var xml = "<?xml version=\"1.0\"?>\n<UIs id=\"customize_tree_xml\" xmlns=\"\">\n";
        xml += this.formatXML(xmlArray);
        xml += "</UIs>";
//        logger.debug("Generated XML: \n" + xml);
        return xml;
    }

    return DEFAULT_XML;
};

Tree.prototype.formatXML = function(xmlArray){
    var xml = new StringBuffer();
    if(xmlArray != null){
        for(var i=0; i<xmlArray.length; ++i){
            xml.append(xmlArray[i]);
        }
    }

    return xml.toString();
};

//Do some post processing work
Tree.prototype.postProcess = function(){
    if(this.root != null){
        //get the tag and attributes for auto generated nodes
        this.root.processNewNode();

        //check duplicated node ids
        this.root.checkNodeId();

        //build the UiObject
//        this.root.buildUiObject();

        //get UI Object reference
//        this.uiObjectMap = new HashMap();
//        this.root.refUiObject(this.uiObjectMap);
    }
};


//Do some post processing work
Tree.prototype.postProcessNoIndex = function(){
    if(this.root != null){
        //get the tag and attributes for auto generated nodes
        this.root.processNewNode();

        //check duplicated node ids
        this.root.checkNodeId();

        //build the UiObject
//        this.root.buildUiObject();

        //get UI Object reference
//        this.uiObjectMap = new HashMap();
//        this.root.refUiObject(this.uiObjectMap);
    }
};

Tree.prototype.buildIndex = function(){
    this.uiObjectMap = new HashMap();
    this.root.refUiObject(this.uiObjectMap);
};

Tree.prototype.markInvalidUiObject = function(uid){
    var obj = this.uiObjectMap.get(uid);
    if(obj != null){
        logger.debug("Marking UI object " + uid + " as invalid");
        obj.isLocatorValid = false;
    }else{
        logger.warn("Cannot find UI object " + uid + " from index");
    }
};

Tree.prototype.validate = function() {
    //validate UI object's XPath
    if(this.root != null){
        this.root.validateXPath();
    }else{
        logger.warn("The root node in the Tree is null");
    }
};

Tree.prototype.clearValidFlag = function() {
    if(this.root != null){
        this.root.clearValidFlag();
    }else{
        logger.warn("The root node in the Tree is null");
    }        
};

Tree.prototype.addElement = function(element){

    logger.debug("Building Inner Tree -> add Element UID: "+element.uid + " XPATH: " + element.xpath + " DomNode: " + element.domNode.tagName);

    //case I: root is null, insert the first node
    if (this.root == null) {
        this.root = new NodeObject();
        this.root.id = element.uid;
        this.root.parent = null;
        this.root.domNode = element.domNode;
        this.root.xpath = element.xpath;
        this.root.attributes = element.attributes;
    } else {
        //not the first node, need to match element's xpath with current node's relative xpath starting from the root
        //First, need to check the root and get the common xpath
        var common = this.xpathMatcher.match(this.root.xpath, element.xpath);

        var leftover = this.xpathMatcher.remainingXPath(element.xpath, common);

        if (this.root.xpath == common) {
            //the current node shares the same common xpath as the new node
            //no extra node need to be added for the current node
            //then check current node's children
            if (this.root.children.length == 0) {
                //no children, so create a new child
                if (leftover != null && leftover.length > 0) {
                    //only create the child if there are extra xpath
                    var son = new NodeObject();
                    son.id = element.uid;
                    son.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                    son.attributes = element.attributes;
                    son.domNode = element.domNode;
                    son.parent = this.root;
 //                   alert("son : " + son);
                    this.root.addChild(son);
                }
            } else {
                //there are children
                this.walk(this.root, element.uid, leftover, element.attributes, element.domNode);
            }

        } else {
            var newroot = new NodeObject();
            newroot.id = "root";
            newroot.xpath = common;
            newroot.parent = null;
            newroot.newNode = true;
            var newxpath = this.xpathMatcher.remainingXPath(this.root.xpath, common);

            if (this.root.id != null && this.root.id == "root") {
                this.root.id = this.uid.genUid(newxpath);
            }
            this.root.xpath = newxpath;
            this.root.parent = newroot;
            newroot.addChild(this.root);

            this.root = newroot;

            if (leftover != null && leftover.length > 0) {
                //only create the child if there are extra xpath
                var child = new NodeObject();
                child.id = element.uid;
                child.xpath = this.xpathMatcher.remainingXPath(element.xpath, common);
                child.attributes = element.attributes;
                child.domNode = element.domNode;
                child.parent = this.root;
                this.root.addChild(child);
            }
        }
    }
};

Tree.prototype.walk = function(current, uid, xpath, attributes, domnode) {

    if (current.children.length == 0) {
        //there is no children
        if (trimString(xpath).length > 0) {
            //only create the child if there are extra xpath
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attributes;
            child.domNode = domnode;
            child.parent = current;

            current.addChild(child);
        }
    } else {
        var cmp = new Array();
        var maxlen = 0;
        for (var l = 0; l < current.children.length; ++l) {
            var nd = current.children[l];
            var xpt = new XPath();
            xpt.xpath = this.xpathMatcher.match(nd.xpath, xpath);
            xpt.node = nd;
            if (xpt.xpath.length > maxlen) {
                maxlen = xpt.xpath.length;
            }
            cmp.push(xpt);
        }

        //need to handle the situation where there is no common xpath
        if (maxlen == 0) {

            //there is no shared common xpath, add the node directly
            var child = new NodeObject();
            child.id = uid;
            child.xpath = xpath;
            child.attributes = attributes;
            child.domNode = domnode;
            child.parent = current;
            current.addChild(child);
        } else {
            //there are shared common xpath
            var max = new Array();
            for (var m = 0; m < cmp.length; m++) {
                if (cmp[m].xpath.length == maxlen) {
                    max.push(cmp[m])
                }
            }

            var mx = max[0];

            var common = mx.xpath;

            if (mx.node.xpath == common) {

                //The xpath includes the common part, that is to say, we need to walk down to the child
                if (max.length > 1) {
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
                this.walk(mx.node, uid, this.xpathMatcher.remainingXPath(xpath, common), attributes, domnode);
            } else {
                //need to create extra node
                var extra = new NodeObject();
                extra.xpath = common;
                extra.parent = current;
                extra.id = this.uid.genUid(common);
                extra.newNode = true;
                current.addChild(extra);
                for (var k = 0; k < max.length; ++k) {
                    var xp = max[k];
                    var cn = xp.node;
                    cn.xpath = this.xpathMatcher.remainingXPath(cn.xpath, common);
                    cn.parent = extra;
                    extra.addChild(cn);
                    current.removeChild(cn.id);
                }

                var ch = new NodeObject();
                ch.id = uid;
                ch.xpath = this.xpathMatcher.remainingXPath(xpath, common);
                ch.attributes = attributes;
                ch.domNode = domnode;
                ch.parent = extra;
                extra.addChild(ch);
            }
        }
    }
};

Tree.prototype.createUiModule = function() {
    var sb = new StringBuffer();
    sb.append("package test\n\n");
    sb.append("import org.telluriumsource.dsl.DslContext\n\n");
    sb.append("/**\n *\tThis UI module file (MyUiModule.groovy) is automatically generated by TrUMP 0.8.0.\n");
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
    var uiModelArray = this.printUI();
    if (uiModelArray != undefined && uiModelArray != null) {
        for (var i = 0; i < uiModelArray.length; ++i) {
            if (i == 0) {
                sb.append("\t\tui." + uiModelArray[i]);
            } else {
                sb.append("\t\t   " + uiModelArray[i]);
            }
        }
    }
    sb.append("\t}\n\n");
    sb.append("\t//Add your methods here\n\n");
    sb.append("}\n");

    sb.append("\n\n");
    sb.append("package test;\n\n");
    sb.append("import org.telluriumsource.test.java.TelluriumJUnitTestCase;\n");
    sb.append("import org.junit.*;\n\n");
    sb.append("/**\n *\tThis test file (MyTestCase.java) is automatically generated by TrUMP 0.8.0.\n");
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
    sb.append("\t\t...\n");
    sb.append("\t}\n");
    sb.append("}\n");

    return sb.toString();
};

Tree.prototype.visit = function(visitor){
    if(this.root != null){
        this.root.visitMe(visitor);
    }
};


var TreeVisitor = Class.extend({
    init: function(){

    },

    visit: function(node){

    }
});

var TreeChainVisitor = Class.extend({
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

    visit: function(node){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].visit(node);
        }
    }
});

var UiBuilder = TreeVisitor.extend({
    init: function(){
        this.filter = new Filter();
        this.classifier = new UiType();
        this.uiBuilderMap = new Hashtable();
        this.uiBuilderMap.put("Button", new UiButtonBuilder());
        this.uiBuilderMap.put("CheckBox", new UiCheckBoxBuilder());
        this.uiBuilderMap.put("Div", new UiDivBuilder());
        this.uiBuilderMap.put("Icon", new UiIconBuilder());
        this.uiBuilderMap.put("Image", new UiImageBuilder());
        this.uiBuilderMap.put("InputBox", new UiInputBoxBuilder());
        this.uiBuilderMap.put("RadioButton", new UiRadioButtonBuilder());
        this.uiBuilderMap.put("Selector", new UiSelectorBuilder());
        this.uiBuilderMap.put("Span", new UiSpanBuilder());
        this.uiBuilderMap.put("SubmitButton", new UiSubmitButtonBuilder());
        this.uiBuilderMap.put("TextBox", new UiTextBoxBuilder());
        this.uiBuilderMap.put("UrlLink", new UiUrlLinkBuilder());
        this.uiBuilderMap.put("Container", new UiContainerBuilder());
        this.uiBuilderMap.put("Frame", new UiFrameBuilder());
        this.uiBuilderMap.put("List", new UiListBuilder());
        this.uiBuilderMap.put("Table", new UiTableBuilder());
        this.uiBuilderMap.put("StandardTable", new UiStandardTableBuilder());
        this.uiBuilderMap.put("Window", new UiWindowBuilder());
        this.uiBuilderMap.put("Repeat", new UiRepeatBuilder());
        this.uiBuilderMap.put("UiAllPurposeObject", new UiAllPurposeObjectBuilder());

    },

    visit: function(node){
        var attributes = node.attributes;
        var tag = attributes.get(CONSTANTS.TAG);
        var uiType = this.classifier.getTypeWithExtra(tag, attributes, node.notEmpty());
        var respond = this.filter.processEventAttributes(attributes);
        if (respond != null && respond.length > 0) {
            if (uiType == "UrlLink" || uiType == "Button" || uiType == "SubmitButton") {
                removeElement(this.respond, "click");
            }
        }
        var whiteListAttributes = this.filter.getWhiteListedAttributes(attributes);
        var builder = this.uiBuilderMap.get(uiType);
        if(builder != null){
            var obj = builder.buildFrom(whiteListAttributes, respond);
            obj.uid = node.id;
            node.uiobject = obj;
        }
        node.checkUiDirectAttribute();
        node.checkUiSelfAttribute();
    }
});


function removeElement(array, elem){
    var index = array.indexOf(elem);
    if(index >= 0)
        array.splice(index, 1);
}