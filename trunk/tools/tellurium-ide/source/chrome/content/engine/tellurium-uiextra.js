
var getEvent = function(name, key , objRef){
    var e = teJQuery.Event(name);
    e.which = key.charCodeAt(0);
    e.ctrlKey = objRef.ctrl;
    e.shiftKey = objRef.shift;
    e.altKey = objRef.alt;
    e.metaKey = objRef.metaKey;
    return e;
};
//add custom jQuery Selector :te_text()
//

teJQuery.extend(teJQuery.expr[':'], {
    te_text: function(a, i, m) {
        if(m[3] != null && m[3].startsWith("regexp:")){
            var pattern = m[3].substring(7);
            return teJQuery(a).text().match(pattern);
        }
        return teJQuery.trim(teJQuery(a).text()) === teJQuery.trim(m[3]);
    }
});

teJQuery.expr[':'].group = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = m[3].split(",");
      var result = true;

      for(var i=0; i<splitted.length; i++){
         result = result && ($this.find(splitted[i]).length > 0);
      }

      return result;
};

teJQuery.expr[':'].styles = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = new Array();

      var fs = m[3].split(/:|;/);
      for(var i=0; i<fs.length; i++){
          var trimed = teJQuery.trim(fs[i]);
          if(trimed.length > 0){
              splitted.push(trimed);
          }
      }

      var result = true;

      var l=0;
      while(l < splitted.length){
         result = result && (teJQuery.trim($this.css(splitted[l])) == splitted[l+1]);
         l=l+2;
      }

      return result;
};

/*
teJQuery.expr[':'].nextToLast = function(obj, index, m){
    var $this = teJQuery(obj);

    //      return $this.find(m[3]).last().prev();
//    return $this.find(m[3]).slice(-2, -1);
    if ($this.index() == $this.siblings().length - 1) {
        return true;
    } else {
        return false;
    }
};
*/

// this is a selector called nextToLast. its sole purpose is to return the next to last
// element of the array of elements supplied to it.
// the parameters in the function below are as follows;
// obj => the current node being checked
// ind => the index of obj in the array of objects being checked
// prop => the properties passed in with the expression
// node => the array of nodes being checked
teJQuery.expr[':'].nextToLast = function(obj, ind, prop, node){

     // if ind is 2 less than the length of the array of nodes, keep it
     return ind == node.length - 2;
};

teJQuery.expr[':'].regex = function(elem, index, match) {
    var matchParams = match[3].split(','),
        validLabels = /^(data|css):/,
        attr = {
            method: matchParams[0].match(validLabels) ?
                        matchParams[0].split(':')[0] : 'attr',
            property: matchParams.shift().replace(validLabels,'')
        },
        regexFlags = 'ig',
        regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
    return regex.test(teJQuery(elem)[attr.method](attr.property));
};

teJQuery.expr[':'].data = function(elem, index, m) {

    m[0] = m[0].replace(/:data\(|\)$/g, '');

    var regex = new RegExp('([\'"]?)((?:\\\\\\1|.)+?)\\1(,|$)', 'g'),
        key = regex.exec( m[0] )[2],
        val = regex.exec( m[0] )[2];

    return val ? teJQuery(elem).data(key) == val : !!teJQuery(elem).data(key);
};

teJQuery.fn.outerHTML = function() {
//    var $me = teJQuery("<div/>").append( teJQuery(this[0]).clone() );

//    return teJQuery("<div/>").append( teJQuery(this[0]).clone() ).html();
/*    try{
        var doc = this[0] ? this[0].ownerDocument : document;
        return teJQuery('<div/>', doc).append( this.eq(0).clone() ).html();
    }catch(error){
        fbWarn("Error getting outHTML: " + error.message, error);
        return "";
    }*/

    return "";
};

function getColor(elem, cssName){
   var color = null;

   if (elem != null) {
        var parent = elem.parentNode;

        while (parent != null) {
//            color = teJQuery(parent).css(cssName);
            color = teJQuery.curCSS(parent, cssName);
            !tellurium.logManager.isUseLog || fbLog(cssName + " is " + color + " for ", parent);
            if (color != '' && color != 'transparent' || teJQuery.nodeName(parent, "body"))
                break;
            parent = parent.parentNode;
        }
    }

   return color;
}

function Outlines(){
    this.defaultOutline = null;
    this.outlines = new Array();
}

Outlines.prototype.init = function(){
//    this.defaultOutLine = "2px solid #0000FF";
    //Green
    this.defaultOutLine = "2px solid #00FF00";
    //Red
    this.outlines.push("2px solid #FF0000");
    //Yellow
    this.outlines.push("2px solid #FFFF00");
    //Blue
    this.outlines.push("2px solid #0000FF");
    //Pink
    this.outlines.push("2px solid #FF00FF");
    //Brown
    this.outlines.push("2px solid #804000");
};

Outlines.prototype.getDefaultOutline = function(){
    return this.defaultOutLine;
};

Outlines.prototype.getOutline = function(index){
    var i = index % this.outlines.length;

    return this.outlines[i];
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

var DecorateUiWorker = UiWorker.extend({
    init: function() {
        this.decorator = new UiDecorator();
    },

    work: function(context, elements, delay) {
         if (elements != null && elements.length > 0) {
            this.decorator.cleanShowNode();
            for(var i; i<elements.length; i++){
//                teJQuery("#TE_ENGINE_SHOW_NODE").remove();
                var elem = elements[i];
                teJQuery(elem).append("<div id='TE_ENGINE_SHOW_NODE'>ShowMe</div>");
                this.decorator.showNode(elem);
                teJQuery(elem).find("#TE_ENGINE_SHOW_NODE").fadeIn(100).delay(delay).fadeOut(100);
            }
         }
    }
});

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

function UiDecorator(){
    this.bgColor = "#A9DA92";
    this.showBgColor = "#00FFFF";
//    this.showBgColor = "#FF00FF";
    this.originalBgColor = "";
    this.outLine = "2px solid #000";
    this.showLine = "3px solid #0000FF";
    this.noBgColor = "";
    this.lastShownNode = null;
    this.currentShownNode = null;
}

UiDecorator.prototype.backupBackground = function(node){
   this.originalBgColor = node.style.backgroundColor;
};

UiDecorator.prototype.restoreBackground = function(node){
   node.style.backgroundColor = this.originalBgColor;     
};

UiDecorator.prototype.addBackground = function(node){
    node.style.backgroundColor = this.bgColor;
};

UiDecorator.prototype.removeBackground = function(node){
    node.style.backgroundColor = this.noBgColor;
};

UiDecorator.prototype.addOutline = function(node){
    node.style.outline = this.outLine;
};

UiDecorator.prototype.removeOutline = function(node){
    node.style.outline = "";
};

UiDecorator.prototype.showNode = function(node){
//    this.cleanShowNode(node);

//    node.style.backgroundColor = this.showBgColor;
    this.cleanShowNode();
    node.style.outline = this.showLine;
    this.currentShownNode = node;
};

UiDecorator.prototype.cleanShowNode = function(){
    if(this.currentShownNode != null){
//        this.currentShownNode.style.backgroundColor = this.noBgColor;
        this.currentShownNode.style.outline = "";
    }
};
