//Tellurium APIs to replace selenium APIs

Tellurium.prototype.blur = function(element) {
    teJQuery(element).blur();
};

Tellurium.prototype.click = function(element) {
    teJQuery(element).click();
};

Tellurium.prototype.dblclick = function(element){
    teJQuery(element).dblclick();
};

Tellurium.prototype.fireEvent = function(element, event){
    teJQuery(element).trigger(event);
};

Tellurium.prototype.focus = function(element){
    teJQuery(element).focus();    
};

Tellurium.prototype.typeKey = function(element, key){
    var $elem = teJQuery(element);
	$elem.val($elem.val()+key).trigger(getEvent("keydown", key)).trigger(getEvent("keypress", key)).trigger(getEvent("keyup", key));
};

Tellurium.prototype.keyDown = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()+key).trigger(getEvent("keydown", key));
};

Tellurium.prototype.keyPress = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()+key).trigger(getEvent("keypress", key));
};

Tellurium.prototype.keyUp = function(element, key){
    var $elem = teJQuery(element);
    $elem.val($elem.val()+key).trigger(getEvent("keyup", key));
};

Tellurium.prototype.mouseOver = function(element){
   teJQuery(element).trigger('mouseover');
};

Tellurium.prototype.mouseDown = function(element){
   teJQuery(element).trigger('mousedown');
};

Tellurium.prototype.mouseEnter = function(element){
   teJQuery(element).trigger('mouseenter');
};

Tellurium.prototype.mouseLeave = function(element){
   teJQuery(element).trigger('mouseleave');
};

Tellurium.prototype.mouseOut = function(element){
   teJQuery(element).trigger('mouseout');
};

Tellurium.prototype.select = function(element){
    //TODO: need to add option selection piece
   teJQuery(element).select();
};

Tellurium.prototype.submit = function(element){
   teJQuery(element).submit();
};

Tellurium.prototype.check = function(element){
    element.checked = true;
};

Tellurium.prototype.uncheck = function(element){
    element.checked = false;
};

Tellurium.prototype.isElementPresent = function(element){
    if(element == null)
        return false;
    return true;
};






