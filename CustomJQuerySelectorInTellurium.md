

# Introduction #

Tellurium leverages jQuery to add [jQuery selector](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector) as a new locator to improve the test speed in IE and add other new functionalities to Selenium core, for example, fetching bulk data in one method call and [the diagonse utility](http://code.google.com/p/aost/wiki/TelluriumPowerUtilityDiagnose).

With the adoption of jQuery, we also need some custom jQuery selectors and plugins to meet our needs.

To design jQuery custom selectors, we need to understand the jQuery selector syntax:

```
$.expr[':'].selector_name = function(obj, index, meta, stack){
......
}
```

where
  * _obj_: a current DOM element
  * _index_: the current loop index in stack
  * _meta_: meta data about your selector
  * _stack_: stack of all elements to loop

The above function returns true to include current element and returns false to exclude current element. A more detailed explanation could be found from [jQuery Custom Selectors with Parameters](http://jquery-howto.blogspot.com/2009/06/jquery-custom-selectors-with-parameters.html).

To avoid conflicts with user's jQuery library, we yield the "$" symbol and rename jQuery to teJQuery in Tellurium.

# Custom jQuery Selectors #

## :te\_text ##

The _:te\_text_ selector is created to select a UI element whose text attribute is a given string. The implementation is simple,

```
teJQuery.extend(teJQuery.expr[':'], {
    te_text: function(a, i, m) {
        return teJQuery.trim(teJQuery(a).text()) === teJQuery.trim(m[3]);
    }
});
```

You may wonder why we use _`m[3]`_ here, the variable _m_ includes the following parameters

  * _`m[0]`_: `te_text(argument)` full selector
  * _`m[1]`_: `te_text` selector name
  * _`m[2]`_: `''` quotes used
  * _`m[3]`_: `argument` parameters

As a result, the selector picks up the elements whose text attribute, obtained by `text()`, is equal to the passed in parameter _`m[3]`_.

## :group ##

The _:group_ selector is used to implement [the group locating](http://code.google.com/p/aost/wiki/UserGuide#Group_Locating) in Tellurium. For example, we want to select a "div" whose children include one "input", one "img", and one "span" tags. How to express this using jQuery?

One way is to use the following selector,

```
teJQuery.expr[':'].group = function(obj){
      var $this = teJQuery(obj);
      return ($this.find("input").length > 0) && ($this.find("img").length > 0) && ($this.find("span").length > 0);
};
```

That is to say, only a DOM node satisfying all the three conditions, i.e, whose children include "input", "img", and "span", is selected because the AND conditions. Remember, only the node that returns true for the above function is selected.

However, in real world, we may have many conditions and we cannot use this hard-coded style selector and we need to use the custom selector with parameters instead. Here is our implementation,

```
teJQuery.expr[':'].group = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = m[3].split(",");
      var result = true;

      for(var i=0; i<splitted.length; i++){
         result = result && ($this.find(splitted[i]).length > 0);
      }

      return result;
};
```

If we use firebug to debug the code by running the following jQuery selector

```
teJQuery("div:group(input, img, span)")
```

We can see the variable _m_ includes the following parameters

  * _`m[0]`_: `group(input, img, span)` full selector
  * _`m[1]`_: `group` selector name
  * _`m[2]`_: `''` quotes used
  * _`m[3]`_: `input, img, span` parameters


## :styles ##

One user provided us the following UI module,

```
   ui.Container(uid: "Program", clocator: [tag: "div"], group: "true") {
      Div(uid: "label", clocator: [tag: "a", text: "Program"])
      Container(uid: "triggerBox", clocator: [tag: "div"], group: "true") {
        InputBox(uid: "inputBox", clocator: [tag: "input", type: "text", readonly: "true", style: "width: 343px;"], respond: ["click"])
        Image(uid: "trigger", clocator: [tag: "img",  style: "overflow: auto; width: 356px; height: 100px;"], respond: ["click"])
      }
    }
```

Unfortunately, the following generated jQuery selector does not work.

```
 $('div:has(input[type=text][readonly=true][style="width: 343px;"], img[style="overflow: auto; width: 356px;height: 100px;"]) img[style="overflow: auto; width: 356px; height: 100px;"]')
```

We have to use a custom jQuery selector to handle the style attribute as follows,

```
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
```

The main idea is to split the content of the style attribute into multiple single-css classes, then try to match each css class one by one. This approach may not be the optimal one, but it works.

Then, the new runtime jQuery selector becomes,

```
div:group(a:te_text(Program), div) div:group(input:styles(width: 343px;)[type=text][readonly=true], img:styles(overflow: auto; width: 356px; height: 100px;)) img:styles(overflow: auto; width: 356px; height: 100px;)
```

## :nextToLast ##

One implemented suggested by Kevin is shown as follows,

```
teJQuery.expr[':'].nextToLast = function(obj, index, m){
    var $this = teJQuery(obj);

    if ($this.index() == $this.siblings().length - 1) {
        return true;
    } else {
        return false;
    }
};
```

and he also suggested [a more efficient implementation](http://www.tentonaxe.com/2010/03/custom-jquery-selectors.html).

```
// this is a selector called nextToLast. its sole purpose is to return the next to last
// element of the array of elements supplied to it.
// the parameters in the function below are as follows;
// obj => the current node being checked
// ind => the index of obj in the array of objects being checked
// prop => the properties passed in with the expression
// node => the array of nodes being checked
teJQuery.expr[':'].nextToLast = function(obj, ind, prop, node){

     // if ind is 2 less than the length of the array of nodes, keep it
     if (ind == node.length-2) {
          return true;
     } else {
          // else, remove the node
          return false;
     }
};
```

# Custom jQuery Plugins #

## outerHTML ##

When we worked on [the diagnose utility](http://code.google.com/p/aost/wiki/TelluriumPowerUtilityDiagnose), we were frustrated because we need to get the HTML source of a DOM node, but the `html()` method in jQuery only returns innerHTML. We posted a question to [jQuery group](http://groups.google.com/group/jquery-en) and got the answer,

```
$('<div>').append( $(jQuery_Selector).clone() ).html() 
```

and as suggested by another person, we went further to implement this as a simple jQuery plugin,

```
teJQuery.fn.outerHTML = function() {
    return teJQuery("<div/>").append( teJQuery(this[0]).clone() ).html();
};
```

We made two changes here.

  1. _outerHTML_ is defined as a new property of `jQuery.fn` rather than as a standalone function. This registers the function as a plug-in method.
  1. We use the keyword _this_ as a replacement for the jQuery selector. Within a plug-in method, _this_ refers to the jQuery object that is being acted upon.

# Summary #

With the evolution of Tellurium and the heavy use of jQuery in Tellurium, the list of custom jQuery selectors and plugins in Tellurium will keep growing. We will keep updating this wiki page, please do check back.

If you are a Javascript or jQuery guru and like to contribute to Tellurium, please contact us.

# Acknowledgements #

Special thanks to folks in [jQuery forum](http://forum.jquery.com/) for providing us helps on custom jQuery selectors and functions.

# Resources #

  * [jQuery](http://jquery.com/)
  * [jQuery group](http://groups.google.com/group/jquery-en)
  * [jQuery forum](http://forum.jquery.com/)
  * [jQuery Custom Selectors with Parameters](http://jquery-howto.blogspot.com/2009/06/jquery-custom-selectors-with-parameters.html)
  * [Tellurium jQuery selector](http://code.google.com/p/aost/wiki/TelluriumjQuerySelector)
  * [Tellurium jQuery Cache](http://code.google.com/p/aost/wiki/jQuerySelectorCache)
  * [Tellurium Powerful Utility: Diagnose](http://code.google.com/p/aost/wiki/TelluriumPowerUtilityDiagnose)
  * [Use Firebug and JQuery to Trace Problems in Tellurium Tests](http://code.google.com/p/aost/wiki/TelluriumJQueryFirebug)
  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide)