// User extensions can be added here.

//add custom jQuery Selector :te_text()
//

$.extend($.expr[':'],{
  te_text: function(a,i,m) {
            return $(a).text() === m[3];
        }
});

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////



Selenium.prototype.getSelectorProperties = function(jq, p){
	var p =  eval('(' + p + ')'); //eval json
	var e = this.browserbot.findElement("jqueryall="+jq);
	var out = [];
	for(var i = 0; i < e.length; i++){
		var stuff = {};
		for(var j = 0; j < p.length; j++){
			var prop = null;
			if(typeof e[i][p[j]] !== "undefined"){
				prop = e[i][p[j]];
			}
			stuff[p[j]] = prop;	//gather the requested stuff
		}
		out.push(stuff);
	}
	return JSON.stringify(out);
};

Selenium.prototype.getSelectorText = function(jq){
	var e = this.browserbot.findElement("jqueryall="+jq);
	var out = [];
	for(var i = 0; i < e.length; i++){
		out.push($(e[i]).text());
	}
	return JSON.stringify(out);
};

//generic function call. best hope the function returns json, or we are going to be sad
Selenium.prototype.getSelectorFunctionCall = function(jq, fn){
	var e = this.browserbot.findElement("jqueryall="+jq);
	fn = eval('(' + eval('(' +fn+')')[0]+')');
	return JSON.stringify(fn.apply(e));
};

Selenium.prototype.getAllText = function(locator){
	var $e = $(this.browserbot.findElement(locator)_;
	var out = [];
	$e.each(function(){
		out.push($(this).text());
	});
	return JSON.stringify(out);
};

Selenium.prototype.getJQuerySelectorCount = function(locator){
	var e = this.browserbot.findElement(locator);
    if(e == null)
        return 0;

	return e.length;
};


Selenium.prototype.getCSS = function(locator, cssName){
	var $e = $(this.browserbot.findElement(locator));
	var out = [];
	$e.each(function(){
		out.push($(this).css(cssName));
	});
	return JSON.stringify(out);
};