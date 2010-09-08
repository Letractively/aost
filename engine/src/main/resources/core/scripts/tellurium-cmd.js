var CmdExecutor = Class.extend({

    init: function() {
        this.ctrl = false;
        this.shift = false;
        this.alt = false;
        this.meta = false;
    }
});

var JQueryCmdExecutor = CmdExecutor.extend({

    init: function() {
        this._super();
    },

    reset: function(element){
        if (element.reset != undefined) {
            element.reset();
        }
    },

    fireEvent: function(element, event){
        teJQuery(element).trigger(event);
    },

    blur: function(element){
        teJQuery(element).blur();
    },

    focus: function(element){
        teJQuery(element).focus();
    },

    click: function(element){
        var elementWithHref = getAncestorOrSelfWithJavascriptHref(element);
        if(elementWithHref == null)
            elementWithHref = element;
        teJQuery(elementWithHref).focus();
        if (elementWithHref.href || elementWithHref.url) {
            if (teJQuery.browser.msie) {
                elementWithHref.fireEvent("onclick");
            } else {
//                var evObj = document.createEvent('HTMLEvents');
                var evObj = document.createEvent('MouseEvents');
                evObj.initEvent('click', true, true);
                elementWithHref.dispatchEvent(evObj);
            }
        } else {
            teJQuery(elementWithHref).click();
        }
    },

    clickAt: function(element, coordString){
        var clientXY = getTargetXY(element, coordString);
        //TODO: how to do click at using jQuery
        var elementWithHref = getAncestorOrSelfWithJavascriptHref(element);
        if (elementWithHref.href || elementWithHref.url) {
            if (teJQuery.browser.msie) {
                elementWithHref.fireEvent("onclick");
            } else {
                var evObj = document.createEvent('HTMLEvents');
                evObj.initEvent('click', true, true);
                elementWithHref.dispatchEvent(evObj);
            }
        } else {
            teJQuery(elementWithHref).click();
        }
    },

    doubleClick: function(element){
        teJQuery(element).dblclick();
    },

    mouseOver: function(element){
        teJQuery(element).trigger('mouseover');
    },

    mouseDown: function(element){
        teJQuery(element).trigger('mousedown');
    },

    mouseEnter: function(element){
        teJQuery(element).trigger('mouseenter');
    },

    mouseLeave: function(element){
        teJQuery(element).trigger('mouseleave');
    },

    mouseOut: function(element){
        teJQuery(element).trigger('mouseout');
    },

    toggle: function(element){
        teJQuery(element).toggle();
    },

    getValue: function(element){
        if (element.type) {
            if (element.type.toUpperCase() == 'CHECKBOX' || element.type.toUpperCase() == 'RADIO')
            {
                return (element.checked ? 'on' : 'off');
            }
        }
        if (element.value == null) {
            throw new TelluriumError(ErrorCodes.ELEMENT_HAS_NO_VALUE, "This element has no value; is it really a form field?");
        }

        return element.value;
    },

    getAttribute: function(element, attribute){
        return teJQuery(element).attr(attribute);
    },

    getText: function(element){
        return teJQuery(element).text();
    },

    isVisible: function(element){
        var isHiddenCSS = element.css("visibility") == "hidden" ? true : false;
        var isHidden = element.is(":hidden");

        if (isHidden) {
            return false;
        } else if (isHiddenCSS) {
            return false;
        } else {
            return true;
        }
    },

    isEditable: function(element){
        if (element.value == undefined) {
             Assert.fail("Element " + this.uid + " is not an input.");
         }
         if (element.disabled) {
             return false;
         }

         var readOnlyNode = element.getAttributeNode('readonly');
         if (readOnlyNode) {
             // DGF on IE, every input element has a readOnly node, but it may be false
             if (typeof(readOnlyNode.nodeValue) == "boolean") {
                 var readOnly = readOnlyNode.nodeValue;
                 if (readOnly) {
                     return false;
                 }
             } else {
                 return false;
             }
         }

         return true;
    },

    getCSS: function(element, cssName){
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
    },

    isDisabled: function(element){
        var $e = teJQuery(element);

        return $e.attr('disabled');
    },

    check: function(element){
        element.checked = true;
    },

    uncheck: function(element){
        element.checked = false;
    },

    isChecked: function(element){
        if (element.checked == null) {
            logger.warn("Element is not a toggle-button.");
            return false;
        }
        return element.checked;
    },

    type: function(element, val){
        teJQuery(element).val(val);
    },

    typeKey: function(element, key){
        var $elem = teJQuery(element);
        $elem.val($elem.val()+key).trigger(getEvent("keydown", key ,this)).trigger(getEvent("keypress", key, this)).trigger(getEvent("keyup", key, this));
    },

    keyDown: function(element, key){
        var $elem = teJQuery(element);
        $elem.val($elem.val()).trigger(getEvent("keydown", key, this));
    },

    keyPress: function(element, key){
        var $elem = teJQuery(element);
        $elem.val($elem.val() + key).trigger(getEvent("keypress", key, this));
    },

    keyUp: function(element, key){
        var $elem = teJQuery(element);
        $elem.val($elem.val()).trigger(getEvent("keyup", key , this));
    },

    select: function(element, optionSelector){
        var $sel = teJQuery(element);
        //first, remove all selected element
        $sel.find("option").removeAttr("selected");
        //construct the select option
        var opt = "option" + optionSelector;
//        alert("option to select " + opt);
        //select the appropriate option
        $sel.find(opt).attr("selected", "selected");
        if (teJQuery.browser.msie) {
            element.fireEvent("onchange");
        } else {
            var evObj = document.createEvent('HTMLEvents');
            evObj.initEvent('change', true, true);
            element.dispatchEvent(evObj);
        }
    },

    getSelectOptions: function(element) {
        var selectOptions = [];

        for (var i = 0; i < element.options.length; i++) {
            var option = element.options[i].text;
            selectOptions.push(option);
        }

        return selectOptions;
    },

    getSelectValues: function(element) {
        var selectValues = [];

        for (var i = 0; i < element.options.length; i++) {
            var option = element.options[i].value;
            selectValues.push(option);
        }

        return selectValues;
    },

    findSelectedOptionProperties: function(element, property) {
        if (!("options" in element)) {
            throw new SeleniumError("Specified element is not a Select (has no options)");
        }

        var $selected = teJQuery(element).find("option:selected");
        var selectedOptions = [];
        for(var i=0; i<$selected.size(); i++){
            selectedOptions.push($selected.get(i)[property]);
        }

        return selectedOptions;
    },

    getSelectedLabels: function(element) {
        return this.findSelectedOptionProperties(element, "text");
    },

    getSelectedLabel: function(element) {
        var options = this.findSelectedOptionProperties(element, "text");
        if (options.length > 1) {
            fbWarn("Multiple Selected options ", options);
        }

        return options[0];
    },

    getSelectedValues: function(element) {
        return this.findSelectedOptionProperties(element, "value");
    },

    getSelectedValue: function(element) {
        var options = this.findSelectedOptionProperties(element, "value");
        if (options.length > 1) {
            fbWarn("Multiple Selected options ", options);
        }

        return options[0];
    },

    getSelectedIndexes: function(element) {
        return this.findSelectedOptionProperties(element, "index");
    },

    getSelectedIndex: function(element) {
        var options = this.findSelectedOptionProperties(element, "index");
        if (options.length > 1) {
            fbWarn("Multiple Selected options ", options);
        }

        return options[0];
    },

    addSelection: function(element, option){
       teJQuery(element).find(option).attr("selected", "selected");
    },

    removeSelection: function(element, option) {
        teJQuery(element).find(option).removeAttr("selected");
    },

    removeAllSelections: function(element){
        teJQuery(element).find("option").removeAttr("selected");
    },

    submit: function(element){
        teJQuery(element).submit();
    }    
});

var SynCmdExecutor = JQueryCmdExecutor.extend({

    init: function() {
        this._super();
    },

    click: function(element){
        Syn.click(element);
    },

    doubleClick: function(element){
        Syn.click(element);
        Syn.trigger("dblclick", {}, element);
    }
});

var SeleniumCmdExecutor = SynCmdExecutor.extend({

    init: function() {
        this._super();
    },

    click: function(element) {
        tellurium.setCurrentDom(element);
        selenium.doClick("tedom=");
    },

    clickAt: function(element, coordString) {
        tellurium.setCurrentDom(element);
        selenium.doClickAt("tedom=", coordString);
    }
});


