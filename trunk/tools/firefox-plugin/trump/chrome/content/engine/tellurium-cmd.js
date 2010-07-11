function TelluriumCommand(){

    this.uim = null;

    this.uiAlg = null;

    this.dom = null;

    this.cssBuilder = new JQueryBuilder();
}

TelluriumCommand.prototype.locateUI = function(){
    if(this.uiAlg != null && this.uim != null && this.dom != null){
        this.uiAlg.santa(this.uim, this.dom);
    }else{
        logger.error("TelluriumCommand does not set up correctly!");
    }
};

TelluriumCommand.prototype.walkToUiObject = function(context, uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = null;

    if(uiid.size() > 0){
        var first = uiid.peek();
        if(this.uim != null){
            obj = this.uim.walkTo(context, uiid);
            if(obj != null)
                logger.debug("After walkTo, found object " + uid);
            else
                logger.error("Cannot find UI object " + uid)
        }
    }

    return obj;
};

TelluriumCommand.prototype.blur = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
        teJQuery(element).blur();
    }
};

TelluriumCommand.prototype.click = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        if (element.href || element.url) {
            if (teJQuery.browser.msie) {
                element.fireEvent("onclick");
            } else {
                var evObj = document.createEvent('HTMLEvents');
                evObj.initEvent('click', true, true);
                element.dispatchEvent(evObj);
            }
        } else {
            teJQuery(element).click();
        }
        teJQuery(element).blur();
    }
};

TelluriumCommand.prototype.clickAt = function(uid, coordString) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
         var clientXY = getTargetXY(element, coordString);
        //TODO: how to do click at using jQuery
        teJQuery(element).click();
    }    
};

TelluriumCommand.prototype.doubleClick = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
        teJQuery(element).dblclick();
    }
};

TelluriumCommand.prototype.fireEvent = function(uid, event){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
        teJQuery(element).trigger(event);
    }
};

TelluriumCommand.prototype.focus = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
        teJQuery(element).focus();
    }
};

TelluriumCommand.prototype.typeKey = function(uid, key){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if(obj != null){
        var element = context.domRef;
        var $elem = teJQuery(element);
        $elem.val($elem.val()+key).trigger(getEvent("keydown", key ,this)).trigger(getEvent("keypress", key, this)).trigger(getEvent("keyup", key, this));
    }
};

TelluriumCommand.prototype.keyDown = function(uid, key){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var $elem = teJQuery(element);
        $elem.val($elem.val()).trigger(getEvent("keydown", key, this));
    }
};

TelluriumCommand.prototype.keyPress = function(uid, key){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var $elem = teJQuery(element);
        $elem.val($elem.val() + key).trigger(getEvent("keypress", key, this));
    }
};

TelluriumCommand.prototype.keyUp = function(uid, key){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var $elem = teJQuery(element);
        $elem.val($elem.val()).trigger(getEvent("keyup", key , this));
    }    
};

TelluriumCommand.prototype.mouseOver = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).trigger('mouseover');
    }
};

TelluriumCommand.prototype.mouseDown = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).trigger('mousedown');
    }
};

TelluriumCommand.prototype.mouseEnter = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).trigger('mouseenter');
    }
};

TelluriumCommand.prototype.mouseLeave = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).trigger('mouseleave');
    }
};

TelluriumCommand.prototype.mouseOut = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).trigger('mouseout');
    }
};

TelluriumCommand.prototype.submit = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).submit();
    }
};

TelluriumCommand.prototype.check = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        element.checked = true;
    } 
};

TelluriumCommand.prototype.uncheck = function(uid){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        element.checked = false;
    }
};

TelluriumCommand.prototype.getAttribute = function(uid, attribute){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        return teJQuery(element).attr(attribute);
    }
};

TelluriumCommand.prototype.type = function(uid, val){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        teJQuery(element).val(val);
    }
};

TelluriumCommand.prototype.getOptionSelector = function(optionLocator){
    var split = optionLocator.split("=");
    var sel = "";
    split[0] = split[0].trim();
    split[1] = split[1].trim();
    if(split[0] == "label" || split[0] == "text"){
        sel = this.cssBuilder.buildText(split[1]);
    }else if(split[0] == "value"){
        sel = this.cssBuilder.buildAttribute(split[0], split[1]);
    }else if(split[0] == "index"){
        var inx = parseInt(split[1]) - 1;
        sel = ":eq(" + inx + ")"
    }else if(split[0] == "id"){
        sel = this.cssBuilder.buildId(split[1]);
    }else{
        logger.error("Invalid Selector optionLocator " + optionLocator);
        return null;
    }

    return sel;
};

TelluriumCommand.prototype.select = function(uid, optionLocator){
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var $sel = teJQuery(element);
        //first, remove all selected element
        $sel.find("option").removeAttr("selected");
        //construct the select option
        var opt = "option" + this.getOptionSelector(optionLocator);
        //select the appropriate option
        $sel.find(opt).attr("selected", "selected");
        if (teJQuery.browser.msie) {
            element.fireEvent("onchange");
        } else {
            var evObj = document.createEvent('HTMLEvents');
            evObj.initEvent('change', true, true);
            element.dispatchEvent(evObj);
        }
    }
};

TelluriumCommand.prototype.getText = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        return teJQuery(element).text();
    }
};

TelluriumCommand.prototype.isChecked = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        if (element.checked == null) {
            logger.warn("Element is not a toggle-button.");
            return false;
        }
        return element.checked;
    }
};

TelluriumCommand.prototype.isVisible = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var isHiddenCSS = element.css("visibility") == "hidden" ? true : false;
        var isHidden = element.is(":hidden");

        if (isHidden) {
            return false;
        } else if (isHiddenCSS) {
            return false;
        } else {
            return true;
        }
    }

    return false;
};

TelluriumCommand.prototype.getCSS = function(uid, cssName) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var out = [];
        var $e = teJQuery(element);
        for (var i = 0; i < $e.length; i++) {
            var elem = $e.get(i);
            var val = teJQuery(elem).css(cssName);
            //need to walk up the tree if the color is transparent
            if (val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")) {
                val = getColor(elem, cssName);
            }
            out.push(val);
        }

        return out;
    }
};

TelluriumCommand.prototype.isDisabled = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var element = context.domRef;
        var $e = teJQuery(element);
        return $e.attr('disabled');
    }    
};

TelluriumCommand.prototype.showUI = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        this.uim.stree = this.uiAlg.buildSTree(this.uim);
        var outlineVisitor = new UiOutlineVisitor();
        var tipVisitor = new UiSimpleTipVisitor();
        var chainVisitor = new STreeChainVisitor();
        chainVisitor.addVisitor(outlineVisitor);
        chainVisitor.addVisitor(tipVisitor);
        this.uim.stree.traverse(context, chainVisitor);
    }
};

TelluriumCommand.prototype.cleanUI = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var tipCleaner = new UiSimpleTipCleaner();
        var outlineCleaner = new UiOutlineCleaner();
        var chainVisitor = new STreeChainVisitor();
        chainVisitor.addVisitor(tipCleaner);
        chainVisitor.addVisitor(outlineCleaner);
        this.uim.stree.traverse(context, chainVisitor);
    }
};

TelluriumCommand.prototype.getHTMLSource = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.uiAlg;
    var obj = this.uim.walkTo(context, uid);
    if (obj != null) {
        var stree = this.uiAlg.buildSTree(this.uim);
        var visitor = new UiHTMLSourceVisitor();
        stree.traverse(context, visitor);

        return visitor.htmlsource;
    }
};