
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
