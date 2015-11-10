

Tellurium 0.7.0 include a new Engine embedded in Selenium Core. The main functionalities of the Tellurium Engine include
  * CSS Selector support based on jQuery
  * UI module group locating
  * UI module Caching
  * New APIs based on jQuery

# Code Structure #

The following are Javascript files in the Engine project:

```
[jfang@Mars engine]$ tree src/main/resources/core/scripts/
src/main/resources/core/scripts/
|-- firebuglite
|   |-- errorIcon.png
|   |-- firebug-lite.css
|   |-- firebug-lite.js
|   |-- firebug.gif
|   |-- firebug_logo.png
|   |-- infoIcon.png
|   |-- progress.gif
|   |-- spacer.gif
|   |-- tree_close.gif
|   |-- tree_open.gif
|   `-- warningIcon.png
|-- htmlutils.js
|-- jquery-1.4.2.js
|-- jquery-cookies-2.1.0.js
|-- jquery-simpletip-1.3.1.js
|-- json2.js
|-- log4js.js
|-- selenium-api.js
|-- selenium-browserbot.js
|-- selenium-browserdetect.js
|-- selenium-commandhandlers.js
|-- selenium-executionloop.js
|-- selenium-logging.js
|-- selenium-remoterunner.js
|-- selenium-testrunner.js
|-- selenium-version.js
|-- tellurium-api.js
|-- tellurium-cache.js
|-- tellurium-extensions.js
|-- tellurium-logging.js
|-- tellurium-selector.js
|-- tellurium-udl.js
|-- tellurium-uialg.js
|-- tellurium-uibasic.js
|-- tellurium-uiextra.js
|-- tellurium-uimodule.js
|-- tellurium-uiobj.js
|-- tellurium-uisnapshot.js
|-- tellurium.js
|-- tooltip
|   `-- simpletip.css
|-- user-extensions.js
|-- utils.js
`-- xmlextras.js
```

where
  * jquery-1.4.2.js: jQuery is updated to the latest version 1.4.2.
  * jquery-cookies-2.1.0.js: jQuery Cookies Plugin to support more cookie related operation
  * tellurium.js: Entry point for Tellurium Engine code and it defined the `Tellurium` function.
  * tellurium-selector.js: CSS selector builder
  * tellurium-udl.js: Tellurium UDL processing
  * tellurium-uialg.js: Tellurium UI algorithm
  * tellurium-uibasic.js Tellurium UI basic
  * tellurium-uiextra.js Tellurium extra UI functionalities
  * tellurium-uimodule.js Tellurium UI module definition on Engine side
  * tellurium-uiobj.js Tellurium UI object
  * tellurium-uisnapshot Tellurium UI snapshot
  * tellurium-cache.js: Tellurium Engine caching for UI modules and locators
  * telurium-extension.js: Extra Tellurium APIs for Selenium
  * tellurium-api.js: New Tellurium APIs based on jQuery
  * utils.js: Utility functions


# CSS Selector Support #

Started from version 0.6.0, Tellurium supports a CSS selector to address the problem of poor performance of xpath in Internet Explorer. Auto-generating jQuery instead of xpath has the following advantages:
  * Faster performance in IE
  * The power of CSS selector to call methods on jQuery collections to retrieve bulk data
  * New CSS selector based Engine to replace Selenium Core

Tellurium Core automatically builds runtime xpath or CSS selector based on a flag in DslContext. Tellurium Core uses CSS selector as the default locator. To switch back to XPath from CSS selector, you should call

```
disableCssSelector()
```

and use
```
enableCssSelector()
```

to go back to CSS selector as shown in the following diagram.

http://tellurium-users.googlegroups.com/web/xpathjqsel2.png?gda=ZcLbzkEAAACsdq5EJRKLPm_KNrr_VHB_i4k4E3yBw3ZwuTWAYUCsylo_LL8k1Ivp8OS586drcZpTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=4508XgsAAACdKjHXeYuQCiQefauhN3Sg

Be aware that CSS selector only works for composite locator, i.e., _clocator_. If you have base locator, which is pre-generated locator, then the CSS selector will not work for you.

How does the CSS Selector Work? The basic idea is to customize Selenium Core to load the jQuery library at startup time. In other words, we add jquery.js in to the TestRunner.html and RemoteRunner.html.

After that, we register a custom locate strategy "jquery" in Selenium Core. This is done by adding the following lines to the method `BrowserBot.prototype._registerAllLocatorFunctions` in the selenium-browserbot.js file. Note that the locate strategy "uimcal" is used by Tellurium Engine internally.

```
    this.locationStrategies['jquery'] = function(locator, inDocument, inWindow) {
        return tellurium.locateElementByCSSSelector(locator, inDocument, inWindow);
    };

    //used internally by Tellurium Engine
    this.locationStrategies['uimcal'] = function(locator, inDocument, inWindow) {
        return tellurium.locateElementWithCacheAware(locator, inDocument, inWindow);
    };
```

This defines new functions for Selenium to locate elements on the page. For example,
for the strategy "jquery", if someone runs click("jquery=div#myid"), Selenium Core will find the element by CSS selector `div#myid`. Selenium passed three arguments to the location strategy function:

  * locator: the string the user passed in
  * inWindow: the currently selected window
  * inDocument: the currently selected document

The function must return null if the element can't be found.

The actual implementation can be illustrated by the `locateElementByCSSSelector` method.

```
Tellurium.prototype.locateElementByCSSSelector = function(locator, inDocument, inWindow){
    var loc = locator;
    var attr = null;
    var isattr = false;
    //check attribute locator
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        loc = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }
    //find element by jQuery CSS selector
    var found = teJQuery(inDocument).find(loc);
    if (found.length == 1) {
        if (isattr) {
            return found[0].getAttributeNode(attr);
        } else {
            return found[0];
        }
    } else if (found.length > 1) {
        if (isattr) {
            return found.get().getAttributeNode(attr);
        } else {
            return found.get();
        }
    } else {
        return null;
    }
};
```

The code is pretty straightforward. When we find one element, return its DOM reference (Note: Selenium does not accept returning an array with only one element) and if we find multiple elements, we use jQuery get() method to return an array of DOM references. Otherwise, return null.

As shown in the code, we use the same format of attribute locator as the XPath one, i.e.,

```
locator@attribute
```

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

We defined the following Custom jQuery Selectors.

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

We also have the following custom jQuery plugin.

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

# UI Module Group Locating #

UI Module is the heart of Tellurium Automated Testing Framework. Even UI Module was introduced at the prototype phase, but there was really no algorithm to locate the UI module as a whole. Up to Tellurium 0.6.0, we still need Tellurium core to generate runtime locators based on the UI module definition and then pass Selenium commands to the Selenium core to locate each individual UI element.

The Santa algorithm is the missing half of the Tellurium UI module concept. The algorithm can locate the whole UI module at the runtime DOM. After that, you can just pass in UI element's UID to find it in the cached UI module on Tellurium Engine. That is to say, you don't need Tellurium Core to generate the runtime locators any more. For compatibility reason, Tellurium Core still generates runtime locators, but they are not really needed if you turn on UI module group locating and caching by calling

```
   useTelluriumEngine(true);
```

Why is the algorithm named **Santa**. This is because I have completed most of the design and coding work during the Christmas season in 2009. It is like a gift for me from Santa Claus.

## Basic Flow ##

Ui Module Group Locating is to locate all elements defined in a UI module by exploiting the relationship among themselves. The problem is to locate the UI module as a whole, not an individual UI element.

The UI module group locating basic flow is illustrated in the following diagram.

http://tellurium-users.googlegroups.com/web/EngineGroupLocatingFlow.png?gda=CL1f3U0AAACsdq5EJRKLPm_KNrr_VHB_RXAKJQtsnhpNGAFrVZVazbOp4BK91V4s-7udPy6DfdsU9ZdtcfNy08LkG6vh24c05Tb_vjspK02CR95VRrtmeQ&gsc=Yrh2TwsAAAAVdtSV2dFRYROnJX5TWJ5C

First, the Tellurium Engine API accepts a JSON presentation of the UI module. For example,

```
  var json = [{"obj":{"uid":"Form","locator":{"tag":"form"},"uiType":"Form"},"key":"Form"},
   {"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},
   {"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},
   {"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},
   {"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},
   {"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},
   {"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},
   {"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];

```

The UI tree, i.e., UTree, builder in Tellurium Engine builds a UTree based on the JSON input. Then Tellurium Engine calls the Santa algorithm to locate all UI elements in the UI module except the elements that are defined as not **cacheable** by two UI object attributes, i.e., _lazy"_ and _noCacheForChildren_. Dynamic elements can be located by searching from its parent and use a subset of the Santa algorithm, which will not be covered here.

Once an element in a UI module is located, its DOM reference is stored into the UTree and an index is also created for fast access. After the Santa algorithm is finished, the UI module is stored into a cache.

## Data Structures ##

The UI object definition in Tellurium Engine is very much similar to the one defined in Tellurium Core. The basic UI object is defined as,

```
//base UI object
var UiObject = Class.extend({
    init: function() {
        //UI object identification
        this.uid = null;

        //meta data
        this.metaData = null;

        //its parent UI object
        this.parent = null;

        //namespace, useful for XML, XHTML, XForms
        this.namespace = null;

        this.locator = null;

        //event this object should be respond to
        this.events = null;

        //should we do lazy locating or not, i.e., wait to the time we actually use this UI object
        //usually this flag is set because the content is dynamic at runtime
        this.lazy = false;

        //If it is contained in its parent or not
        this.self = false;

        this.uiType = null;

        //Tellurium Core generated locator for this UI Object
        this.generated = null;

        //dom reference
        this.domRef = null;

        //UI Module reference, which UI module this UI object belongs to
        this.uim = null;
    },
```

All UI objects extend this basic UI object. For example, the Container object is defined as follows.

```
var UiContainer = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Container';
        this.group = false;
        this.noCacheForChildren = false;
        this.components = new Hashtable();
    },
    ...
}
```

The UI module at Tellurium Engine is defined as follows.

```
function UiModule(){
    //top level UI object
    this.root = null;

    this.valid = false;

    //hold a hash table of the uid to UI objects for fast access
    this.map = new Hashtable();

    //index for uid - dom reference for fast access
    this.indices = null;

    //If the UI Module is relaxed, i.e., use closest match
    this.relaxed = false;

    //the relax details including the UIDs and their corresponding html source
    this.relaxDetails = null;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;

    //ID Prefix tree, i.e., Trie, for the lookForId operation in group locating
    this.idTrie = new Trie();

    //Cache hit, i.e., direct get dom reference from the cache
    this.cacheHit = 0;

    //Cache miss, i.e., have to use walkTo to locate elements
    this.cacheMiss = 0;

    //the latest time stamp for the cache access
    this.timestamp = null;

    //UI module dump visitor
    this.dumpVisitor = new UiDumpVisitor();

    //Snapshot Tree, i.e., STree
    this.stree = null;
}
```

From above, you can see the UI module has two indices for fast access. One is UID to UI object mapping and the other one is the UID to DOM reference mapping.

An ID prefix tree, i.e., Trie, is built from UI module JSON presentation if the UI module includes elements with an ID attribute. The Trie is used by the Santa _lookID_ operation. A more detailed Trie build process can be found on the wiki [The UI Module Generating Algorithm in Trump](http://code.google.com/p/aost/wiki/UIModuleGeneratingAlgorithm#A_Trie_Based_Dictionary)

The scaled score is used by the _relax_ operation for partial matching, i.e., closest match, and the score stands for how close the UI module matches the runtime DOM. 100 is a perfect match and zero is no found. This is very powerful to create robust Tellurium test code. That is to say, the Santa algorithm is adapt to changes on the web page under testing to some degree.

## Locate ##

Assume we have UI module as shown in the following graph.

http://tellurium-users.googlegroups.com/web/SantaTeUiModule.png?gda=efAxLkUAAACsdq5EJRKLPm_KNrr_VHB_bIUfD7-F6G2KdUD5D4MxCHMSn2U_NL2M92cFu9ULQMZzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A&gsc=-csxfQsAAABJvX1AkrZakeAe14rL-ExC

The group locating procedure is basically a breadth first search algorithm. That is to say, it starts from the root node of the UTree and then its children, its grandchildren, ..., until all node in the UTree has been searched. Santa marks color for already searched node in the UTree and you can see the color changes during the search procedure.

### Algorithm ###

The main flow of group locating can be self-explained by the following greatly simplified code snippet.

```
UiAlg.prototype.santa = function(uimodule, rootdom){
    //start from the root element in the UI module
    if(!uimodule.root.lazy){
        //object Queue
        this.oqueue.push(uimodule.root);

        var ust = new UiSnapshot();
        //Snapshot Queue
        this.squeue.push(ust);
    }
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        uiobj.locate(this);
   }

   //bind snapshot to the UI Module
   this.bindToUiModule(uimodule, snapshot);

   //unmark marked UID during the locating procedure
   this.unmark();     
```

Where the locate procedure is defined as follows.

```
UiAlg.prototype.locate = function(uiobj, snapshot){
    //get full UID
    var uid = uiobj.fullUid();
    var clocator = uiobj.locator;

    //get parent's DOM reference
    var pref = snapshot.getUi(puid);
    
    //Build CSS selector from UI object's attributes
    var csel = this.buildSelector(clocator);
    //Starting from its parent, search for the UI element
    var $found = teJQuery(pref).find(csel);

    //if multiple matches, need to narrow down
    if($found.size() > 1){
        if(uiobj.noCacheForChildren){
            //dynamic elements, use bestEffort operation
            $found = this.bestEffort(uiobj, $found);
 
        }else{
            //first try lookId operation
            $found = this.lookId(uiobj, $found);
            if($found.size() > 1){
                //then try lookAhead operation
                $found = this.lookAhead(uiobj, $found);
            }
        }
    }

   ...
   if($found.size() == 0){
        if(this.allowRelax){
            //use the relax operation
            var result = this.relax(clocator, pref);
        }  
   }
};

```

### Branch and Trim ###

Santa is basically a branch and trim search procedure on the runtime DOM. Assume at some point, the Santa algorithm has located UI elements A, B, and C. A snapshot has been generated as shown in the following diagram.

http://tellurium-users.googlegroups.com/web/SantaLocate1.png?gda=YaAO4EIAAACsdq5EJRKLPm_KNrr_VHB_bIUfD7-F6G2KdUD5D4MxCDC-P9WsSQjHe215MPg7qVZV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=-csxfQsAAABJvX1AkrZakeAe14rL-ExC

When Santa locates UI element D, it finds two matches. Santa branches the snapshot tree and create two separate ones with each hold a different D node.

http://tellurium-users.googlegroups.com/web/SantaLocate2.png?gda=mouCvUIAAACsdq5EJRKLPm_KNrr_VHB_bIUfD7-F6G2KdUD5D4MxCBn8D5nQVokhIuqb621TkD1V4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=-csxfQsAAABJvX1AkrZakeAe14rL-ExC

After couple steps, Santa locates the UI element G, it removes one of the snapshot trees because it cannot find G from the removed snapshot. Hence, only one snapshot tree is left.

http://tellurium-users.googlegroups.com/web/SantaLocatingTrim.png?gda=47DSH0cAAACsdq5EJRKLPm_KNrr_VHB_Vlnhy29Pl04MMzeNE9O6BpUJa53osfU1upinOmkFwyIVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=Y395mgsAAABmTw12zWjEGVzs38bDXQRc

Of course, the actual locating procedure is much more complicated than what described here. But this should be able to give you some idea on how the branch and trim procedure works.

### Multiple-Match Reduction Mechanisms ###

As you can see from the above procedure, it would be time-consuming if Santa branches too frequently and creates too many snapshot trees because Santa needs to exploit every possible snapshot. As a result, Santa introduced the following multiple-match reduction mechanisms to reduce the number of snapshot trees it needs to search on.

#### Mark ####

When Santa locates a node at the DOM, it marks it with its UID.

```
        $found.eq(0).data("uid", uid);
```

In this way, Santa will skip this DOM node when it tries to locate other UI elements in the UI module.

When Santa finishes the group locating procedure, it unmarks all the uids from the DOM nodes.

#### Look Ahead ####

Look Ahead means to look at not only the current UI element but also its children when Santa locates it. For example, when Santa locates the node D, it also looks at its children G and H. This could decrease snapshot trees at the early search stage and thus reduce the UI module locating time.

http://tellurium-users.googlegroups.com/web/SantaLocateLookChildren.png?gda=giCJ100AAACsdq5EJRKLPm_KNrr_VHB_DQbB4dkECwACM3mZO-ksNZbYqNF9gYK0tqjCPQpPzwk78QNvYVH-EzLyPyoQaO3f5Tb_vjspK02CR95VRrtmeQ&gsc=L_MyaAsAAAAFA-mmrSq3wKWi9zbFEGr6

#### Look ID ####

The ID attribute uniquely defines a UI element on a web page and locating a DOM element by its ID is very fast, thus, Tellurium Engine builds an ID prefix tree, i.e., Trie, when it parses the JSON presentation of the UI module. For example, assume the UI module has four elements, A, D, F, and G, with an ID attribute. The Trie looks as follows.

http://tellurium-users.googlegroups.com/web/SantaLookIDTrie.png?gda=H9_7kUUAAACsdq5EJRKLPm_KNrr_VHB_Nen3NTP9dP3oTwzmB8VfKSSWhnpA9T1yOhjOCab04vJzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A&gsc=_MkLKwsAAACPGU9cKreSbkxa0SOycyXv

When Santa locates the UI element A, it can use the IDs for element A and D to reduce multiple matches. If Santa locates element D, only the ID of element G is helpful.

#### Best Effort ####

Best effort is similar to the Look Ahead mechanism, but it is for dynamic UI elements defined Tellurium templates. For dynamic elements, Tellurium defines the following two attributes to determine whether it and its children are cacheable.

```
var UiObject = Class.extend({
    init: function(){
        ...
        //should we do lazy locating or not, i.e., wait to the time we actually use this UI object
        //usually this flag is set because the content is dynamic at runtime
        //This flag is correspond to the cacheable attribute in a Tellurium Core UI object
        this.lazy = false;
    }
});

var UiContainer = UiObject.extend({
    init: function(){
        ...
        this.noCacheForChildren = false;
    },

```

For a dynamic UI element defined by a UI template, it may have zero, one, or multiple matches at runtime. Santa defines a **Bonus Point** for dynamic UI elements. The bonus calculation is straightforward as shown in the following _calcBonus_ method, where variable _one_ is the parent DOM reference and _gsel_ is a set of CSS selectors of current node's children defined by [Tellurium UI templates](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#UI_Templates).

```
UiAlg.prototype.calcBonus = function(one, gsel){
    var bonus = 0;
    var $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        if($me.find(gsel[i]).size() > 0){
            bonus++;
        }
    }

    return bonus;
};
```

If the DOM matches more attributes defined by a UI template, the candidate DOM reference usually gets a higher bonus point. Santa chooses the candidate with the highest bonus point into the snapshot tree.

## Relax ##

The relax procedure, i.e., closest match, is to match the UI attribute with the DOM node as closely as possible. A **Match Score** is defined to measure how many attributes match the one on the DOM node. The total score is scaled to 0-100 at the end. The snapshot with the highest match score is selected.

The following simplified code snippet should give you some idea of how the relax procedure works.

```
        //the tag must be matched 
        var jqs = tag;
        //attrs is the attributes defined by a UI template
        var keys = attrs.keySet();

        //number of properties, tag must be included
        var np = 1;
        //number of matched properties
        var nm = 0;

        if (keys != null && keys.length > 0) {
            np = np + keys.length;
            for (var m = 0; m < keys.length; m++) {
                var attr = keys[m];
                //build css selector
                var tsel = this.cssbuilder.buildSelector(attr, attrs.get(attr));
                var $mt = teJQuery(pref).find(jqs + tsel);
                if ($mt.size()> 0) {
                    jqs = jqs + tsel;
                    if(nm == 0){
                        nm = 2;
                    }else{
                        nm++;
                    }
                }
            }
        }

        //calculate match score, scaled to 100 percentage
        var score = 100*nm/np;
```

As shown in the above code, the relax must satisfy one requirement, i.e., the tag name must match the one on the DOM node. Otherwise, the relax result returns as "not found".

## Usage ##

For instance, we have the following html snippet to test.

```
<H1>FORM Authentication demo</H1>

<div class="box-inner">
    <a href="js/tellurium-test.js">Tellurium Test Cases</a>
    <input name="submit" type="submit" value="Test">
</div>

<form method="POST" action="j_security_check">
    <table border="0" cellspacing="2" cellpadding="1">
        <tr>
            <td>Username:</td>
            <td><input size="12" value="" name="j_username" maxlength="25" type="text"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input size="12" value="" name="j_password" maxlength="25" type="password"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input name="submit" type="submit" value="Login">
            </td>
        </tr>
    </table>
</form>
```

The correct UI module is shown as follows,

```
    ui.Container(uid: "Form", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }
```

Assume the html was changed recently and you still use the following UI module defined some time ago.

```
    ui.Container(uid: "ProblematicForm", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])
    }
```

Here are the differences:

```
   InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
   InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
   
   InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
   InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])

   SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
   SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])     
```

What will happen without using the Santa algorithm? You tests will be broken because the generated locators will not be correct any more. But if you use the latest Tellurium 0.7.0 snapshot, you will notice that the tests still work if you allow Tellurium to do closest match by calling

```
    useClosestMatch(true);
```

The magic is that the new Tellurium Engine will locate the UI module as a whole. It may have some trouble to find some individual UI elements such as "ProblematicForm.Username.Input", but it has no problem to locate the whole UI module structure in the DOM.

Apart from that, Tellurium 0.7.0 also provides a handy method for you to validate your UI module. For example, if you call

```
    validateUiModule("ProblematicForm");
```

You will get the detailed validation results including the closest matches.

```
UI Module Validation Result for ProblematicForm

-------------------------------------------------------

	Found Exact Match: false 

	Found Closest Match: true 

	Match Count: 1 

	Match Score: 85.764 


	Closest Match Details: 

	--- Element ProblematicForm.Submit -->

	 Composite Locator: <input name="submit" value="logon" type="submit"/> 

	 Closest Matched Element: <input name="submit" value="Login" type="submit"> 



	--- Element ProblematicForm.Username.Input -->

	 Composite Locator: <input name="j" type="text"/> 

	 Closest Matched Element: <input size="12" value="" name="j_username" maxlength="25" type="text"> 



	--- Element ProblematicForm.Password.Input -->

	 Composite Locator: <input name="j" type="password"/> 

	 Closest Matched Element: <input size="12" value="" name="j_password" maxlength="25" type="password"> 


-------------------------------------------------------

```

# UI Module Caching #

From Tellurium 0.6.0, we provides the cache capability for CSS selectors so that we can reuse them without doing re-locating. In 0.7.0, we move a step further to cache the whole UI module on the Engine side. Each UI module cache holds a snapshot of the DOM references for most of the UI elements in the UI module. The exceptions are dynamic web elements defined by Tellurium UI templates. For these dynamic web elements, the Engine will try to get the DOM reference of its parent and then do locating inside this subtree with its parent node as the root, which will improve the locating speed a lot.

On the Engine side the cache is defined as

```
function TelluriumCache(){

    //global flag to decide whether to cache jQuery selectors
    this.cacheOption = false;

    //cache for UI modules
    this.sCache = new Hashtable();

    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;

    //Algorithm handler for UI
    this.uiAlg = new UiAlg();
}

```

Tellurium Engine provides the following cache eviction policies:

  * DiscardNewPolicy: discard new jQuery selector.
  * DiscardOldPolicy: discard the oldest jQuery selector measured by the last update time.
  * DiscardLeastUsedPolicy: discard the least used jQuery selector.
  * DiscardInvalidPolicy: discard the invalid jQuery selector first.

To turn on and off the caching capability, you just simply call the following method in your code.

```
    void useCache(boolean isUse);
```

Or use the following methods to do fine control of the cache.

```
    public void enableCache(); 
    public boolean disableCache();
    public boolean cleanCache();
    public boolean getCacheState();
    public void setCacheMaxSize(int size);
    public int getCacheSize();
    public void useDiscardNewCachePolicy();
    public void useDiscardOldCachePolicy();
    public void useDiscardLeastUsedCachePolicy();
    public void useDiscardInvalidCachePolicy();
    public String getCurrentCachePolicy();
    public Map<String, Long> getCacheUsage();
```

How the cached UI modules are used? Tellurium Core checks if a UI module has been located or not when it calls a method on the UI module. If not, it calls the `useUiModule(json)` API to Engine and the Engine calls the following method to do group locating using the Santa algorithm and then push the UI module into the cache.

```
TelluriumCache.prototype.useUiModule = function(jsonarray){
    var uim = new UiModule();
    uim.parseUiModule(jsonarray);
    var response = new UiModuleLocatingResponse();
    var result = this.uiAlg.santa(uim, null);
    if(result){
        //set the UI Module to be valid after it is located
        uim.valid = true;
        var id = uim.getId();
        var cached = this.getCachedData(id);
        if (cached == null) {
             this.addToCache(id, uim);
        } else {
            this.updateToCache(id, uim);
        }

        response.id = id;
        response.relaxed = uim.relaxed;
        if (!response.relaxed)
            response.found = true;
        response.relaxDetails = uim.relaxDetails;
        response.matches = uim.matches;
        response.score = uim.score;
    }

    return response;
};
```

For subsequent calls, the Engine will check the UI object UID to see if it can find the UI module from the cache. If the UI module has been cached, a UI object traverse is called to get back the runtime dom reference for the corresponding UI object.

```
TelluriumCache.prototype.getCachedUiElement = function(uid){

    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        if(uim != null){
            var domref = uim.index(uid);
            if(domref == null){
                uim.increaseCacheMiss();
                //if could not find directly from the UI module indices, 
                //then try to use walkTo to find the element first
                //and then its domRef
                var context = new WorkflowContext();
                context.alg = this.uiAlg;
                var obj = uim.walkTo(context, uiid);
                if(obj != null){
                    domref = context.domRef;
                }
            }else{
                uim.increaseCacheHit();
            }
            
            return domref;
        }
    }

    return null;
};
```

To measure the cache usage, Tellurium Engine defined the following class.

```
function CacheUsage(){
    this.totalCall = 0;
    this.cacheHit = 0;
    //percentage of the cacheHit/totalCall
    this.usage = 0;
}
```

The following method can be called from Tellurium Core to get back the actual cache usage.

```
public Map<String, Long> getCacheUsage();
```

# New APIs #

With the addition of jQuery in the Engine, it is pretty easy to create a set of custom Selenium methods. For example, we can create a `getAllText()` method to get back all texts.

```
TelluriumApi.prototype.getAllText = function(locator) {
    var element = this.cacheAwareLocate(locator);
    var out = [];
    var $e = teJQuery(element);
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
    return out;
};
```


Tellurium Engine provides another running mode, i.e., the Tellurium core does not need to generate the runtime locator anymore and it can simply pass the object UID to the Engine. For example, Tellurium Engine only needs UID in the following method.

```
TelluriumApi.prototype.getAllTableBodyText = function(uid) {
    var context = new WorkflowContext();
    context.alg = this.cache.uiAlg;
    
    var obj = this.cache.walkToUiObjectWithException(context, uid);
    if(obj.respondsToWithException("getAllBodyCell")){
        var out = obj.getAllBodyCell(context, this.textWorker);
        !tellurium.logManager.isUseLog || fbLog("Get All Table Body Text Result", out);

        return out;
    }

    return null;
};
```

Right now, new Tellurium jQuery-based APIs include

```
TelluriumApi.prototype.blur = function(locator);

TelluriumApi.prototype.click = function(locator);

TelluriumApi.prototype.clickAt = function(locator, coordString);

TelluriumApi.prototype.doubleClick = function(locator);

TelluriumApi.prototype.fireEvent = function(locator, event);

TelluriumApi.prototype.focus = function(locator);

TelluriumApi.prototype.typeKey = function(locator, key);

TelluriumApi.prototype.keyDown = function(locator, key);

TelluriumApi.prototype.keyPress = function(locator, key);

TelluriumApi.prototype.keyUp = function(locator, key);

TelluriumApi.prototype.mouseOver = function(locator);

TelluriumApi.prototype.mouseDown = function(locator);

TelluriumApi.prototype.mouseDownRight = function(locator);

TelluriumApi.prototype.mouseEnter = function(locator);

TelluriumApi.prototype.mouseLeave = function(locator);

TelluriumApi.prototype.mouseOut = function(locator);

TelluriumApi.prototype.submit = function(locator);

TelluriumApi.prototype.check = function(locator);

TelluriumApi.prototype.uncheck = function(locator);

TelluriumApi.prototype.isElementPresent = function(locator);

TelluriumApi.prototype.getAttribute = function(locator, attributeName);

TelluriumApi.prototype.waitForPageToLoad = function(timeout);

TelluriumApi.prototype.type = function(locator, val);

TelluriumApi.prototype.select = function(locator, optionLocator);

TelluriumApi.prototype.addSelection = function(locator, optionLocator);

TelluriumApi.prototype.removeSelection = function(locator, optionLocator);

TelluriumApi.prototype.removeAllSelections = function(locator);

TelluriumApi.prototype.open = function(url);

TelluriumApi.prototype.getText = function(locator);

TelluriumApi.prototype.isChecked = function(locator);

TelluriumApi.prototype.isVisible = function(locator);

TelluriumApi.prototype.isEditable = function(locator) ;

TelluriumApi.prototype.getXpathCount = function(xpath);

TelluriumApi.prototype.getAllText = function(locator);

TelluriumApi.prototype.getCssSelectorCount = function(locator);

TelluriumApi.prototype.getCSS = function(locator, cssName);

TelluriumApi.prototype.isDisabled = function(locator);

TelluriumApi.prototype.getListSize = function(locator, separators);


TelluriumApi.prototype.getCacheState = function();

TelluriumApi.prototype.enableCache = function();

TelluriumApi.prototype.disableCache = function();

TelluriumApi.prototype.cleanCache = function();

TelluriumApi.prototype.setCacheMaxSize = function(size);

TelluriumApi.prototype.getCacheSize = function();

TelluriumApi.prototype.getCacheMaxSize = function();

TelluriumApi.prototype.getCacheUsage = function();

TelluriumApi.prototype.addNamespace = function(prefix, namespace);

TelluriumApi.prototype.getNamespace = function(prefix);

TelluriumApi.prototype.useDiscardNewPolicy = function();

TelluriumApi.prototype.useDiscardOldPolicy = function();

TelluriumApi.prototype.useDiscardLeastUsedPolicy = function();

TelluriumApi.prototype.useDiscardInvalidPolicy = function();

TelluriumApi.prototype.getCachePolicyName = function();

TelluriumApi.prototype.useUiModule = function(json);

TelluriumApi.prototype.isUiModuleCached = function(id);

TelluriumApi.prototype.getEngineState = function();

TelluriumApi.prototype.useEngineLog = function(isUse);
```

As you can see, most of the new APIs have the same signature as the Selenium ones so that your test code is agnostic to which test driving engine that you use. You can always switch between the Tellurium Engine and Selenium Engine by the following API at Tellurium core.

```
public void useTelluriumApi(boolean isUse);
```


# Debug Tellurium Engine #

Tellurium Engine is similar to Selenium core to drive browser events to simulate users who physically work on the web application. The basic idea is to write a Javascript test to call Tellurium Engine and embed the test code in the header of the html page that we  want to test. Then we can set a breakpoint in the JavaScript code using Firebug. After refresh the web page, we can run the test code, which will stop at the breakpoint. One obstacle is that some parts of Tellurium Engine need to call Selenium core, we work around this problem by mocking Selenium Core.

First, we installed a Jetty server and use the Jetty server to load up the web page that we want to run tellurium Engine tests on.

Take the following html source as an example,

```
H1>FORM Authentication demo</H1>

<div class="box-inner">
    <a href="js/tellurium-test.js">Tellurium Test Cases</a>
    <input name="submit" type="submit" value="Test" onclick="teTestCase.testSuite();">
</div>

<form method="POST" action="j_security_check">
    <table border="0" cellspacing="2" cellpadding="1">
        <tr>
            <td>Username:</td>
            <td><input size="12" value="" name="j_username" maxlength="25" type="text"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input size="12" value="" name="j_password" maxlength="25" type="password"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input name="submit" type="submit" value="Login">
            </td>
        </tr>
    </table>
</form>

```

First, we added the following headers to the above html source.

```
<head>
  <title>Tellurium Test Page</title>
          <script src="js/selenium-mock.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/jquery-1.4.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/json2.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/utils.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-logging.js"></script> 
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-api.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-cache.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-extensions.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-selector.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uibasic.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uiobj.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uimodule.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uisnapshot.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uialg.js"> </script>
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium-uiextra.js"> </script>    
          <script src="http://localhost:4444/selenium-server/core/scripts/tellurium.js"> </script>
          <script src="js/tellurium-test.js"> </script>
          <script type="text/javascript">
            teJQuery(document).ready(function(){
                   var testcase = new TelluriumTestCase();
                   testcase.testLogonUiModule();      
            });
          </script>
</head>
```

Where the tellurium-test.js is the Engine test script. The above header loads up the Tellurium Engine code when the web page is served by Jetty.

Then, we defined a test class as follows,

```
function TelluriumTestCase(){

};

TelluriumTestCase.prototype.testLogonUiModule = function(){
  var json = [{"obj":{"uid":"Form","locator":{"tag":"form"},"uiType":"Form"},"key":"Form"},
   {"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},
   {"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},
   {"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},
   {"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},
   {"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},
   {"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},
   {"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = true;
    tellurium.cache.addToCache("Form", uim);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("Form.Username.Input"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("Form.Password.Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("Form.Submit"));
    tellurium.teApi.getHTMLSource("Form");
    var attrs = [{"val":"text","key":"type"}];
    var teuids = tellurium.teApi.getUiByTag("input", attrs);
    fbLog("result ", teuids);
};

```

The variable json is the JSON presentation of the UI module. You can obtain the JSON string of a UI module by calling the following method in DslContext.

```
    public String toString(String uid); 
```

The following test case first runs the Santa algorithm to do group locating and then call _walkTo_ to find the DOM reference for the UI element. After that, we test the two Tellurium APIs, _getHTMLSource_ and _getUiByTag_.

As we said, Tellurium Engine needs to call Selenium Core code somewhere. we need to mock up Selenium core as follows.

```
function Selenium(){
    this.browserbot = new BrowserBot();
};

function BrowserBot(){

};

BrowserBot.prototype.findElement = function(locator){
    if(locator.startsWith("jquery=")){
        return teJQuery(locator.substring(7));
    }

    return null;
};

function SeleniumError(message) {
    var error = new Error(message);
    if (typeof(arguments.caller) != 'undefined') { // IE, not ECMA
        var result = '';
        for (var a = arguments.caller; a != null; a = a.caller) {
            result += '> ' + a.callee.toString() + '\n';
            if (a.caller == a) {
                result += '*';
                break;
            }
        }
        error.stack = result;
    }
    error.isSeleniumError = true;
    return error;
}

var selenium = new Selenium();
```

Firebug provides very powerful console logging capability, where you can inspect the JavaScript object. Tellurium Engine provides the following wrapper for Firebug console logging.

```
function fbLog(msg, obj){
    if (typeof(console) != "undefined") {
        console.log(msg, obj);
    }
}

function fbInfo(msg, obj){
    if (typeof(console) != "undefined") {
        console.info(msg, obj);
    }
}

function fbDebug(msg, obj){
    if (typeof(console) != "undefined") {
        console.debug(msg, obj);
    }
}

function fbWarn(msg, obj){
    if (typeof(console) != "undefined") {
        console.warn(msg, obj);
    }
}

function fbError(msg, obj){
    if (typeof(console) != "undefined") {
        console.trace();
        console.error(msg, obj);
    }
}

function fbTrace(){
    if (typeof(console) != "undefined") {
        console.trace();
    }
}

function fbAssert(expr, obj){
    if (typeof(console) != "undefined") {
        console.assert(expr, obj);
    }
}

function fbDir(obj){
    if (typeof(console) != "undefined") {
        console.dir(obj);
    }
}
```

For browsers other than Firefox, Tellurium provides [the Firebug Lite](http://code.google.com/p/aost/wiki/Tellurium070Update#Engine_Logging) in the custom selenium server so that you can still use the above logging wrapper.

To run the above test page, first, we copy the above page to `JETTY_HOME/webapps/test/` directory. Copy the test script and Selenium mock script to `JETTY_HOME/webapps/test/js/` directory. Then start the Jetty server and open the Firefox browser to point to `http://localhost:8080/testpagename.html`. we can see all JavaScript code including Tellurium Engine code and the test script. Set a breakpoint somewhere and refresh the page, Firebug will stop at breakpoint and then we can start to debug the Engine.


# Tellurium UI Module Visual Effect #

Have you ever thought of seeing the actual UI on the web page under testing? Tellurium 0.7.0 adds a cool feature to show this visual effect.

Tellurium provides a _show_ command to display the UI module that you defined on the actual web page.

```
    public show(String uid, int delay);
```

where _uid_ is the UI module name and _delay_ is in milliseconds. In the meanwhile, Tellurium Core exposes the following two methods for users to start showing UI and clean up UI manually.

```
   public void startShow(String uid);

   public void endShow(String uid);
```

How it Works ? Under the hood, Tellurium Engine does the following things.

## Build a Snapshot Tree ##

The Snapshot Tree, or STree in short, is different from the UI module. The UI module defines how the UI looks like. Even [the UI module group locating algorithm - Santa](http://code.google.com/p/aost/wiki/SantaUiModuleGroupLocatingAlgorithm) only locates cachable UI elements and leaves out the dynamic elements. The snapshot tree, however, needs to include every UI elements inside the UI module. For example, the following Google Book List UI module is very simple.

```
    ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"]) {
      List(uid: "subcategory", clocator: [tag: "td", class: "sidebar"], separator: "div") {
        Container(uid: "all") {
          TextBox(uid: "title", clocator: [tag: "div", class: "sub_cat_title"])
          List(uid: "links", separator: "p") {
            UrlLink(uid: "all", clocator: [:])
          }
        }
      }
    }
```

But its STree may include many book categories and books.

A snapshot tree includes the following different types of nodes.

### SNode ###

SNode can present a non-container type UI object, i.e., UI element without any child.

```
var UiSNode = Class.extend({
    init: function() {

        //parent's rid
        this.pid = null;

        //rid, runtime id
        this.rid = null;

        //point to its parent in the UI SNAPSHOT tree
        this.parent = null;

        //UI object, which is defined in the UI module, reference
        this.objRef = null;

        //DOM reference
        this.domRef = null;
    },
...
}
```

### CNode ###

CNode is a container type node and it has children.

```

var UiCNode = UiSNode.extend({
    init: function(){
        this._super();
        //children nodes, regular UI Nodes
        this.components = new Hashtable();
    },

...
}
```

### TNode ###

The TNode stands for a table with headers, footers, and one or multiple bodies.
```
var UiTNode = UiSNode.extend({
    init: function(){
        this._super();

        //header nodes
        this.headers = new Hashtable();

        //footer nodes
        this.footers = new Hashtable();

        //body nodes
        this.components = new Hashtable();
    },
...
```

Finally, the Snapshot tree is defined as

```
function UiSTree(){
    //the root node
    this.root = null;

    //the reference point to the UI module that the UI Snapshot tree is derived
    this.uimRef = null;
}
```

The STree build process is quite complicated. The basic idea is to use the cached DOM references in a cached UI module and use a subset of the Santa algorithm to locate dynamic UI elements.

## STree Visitors ##

The STree defines a traverse method, so that we can pass in different visitors to work on each individual node in the tree for different purpose.

```
UiSTree.prototype.traverse = function(context, visitor){
    if(this.root != null){
        this.root.traverse(context, visitor);
    }
};

var UiSNode = Class.extend({
...
    traverse: function(context, visitor){
        visitor.visit(context, this);
    },
...
}

```

The STree Visitor class is defined as

```
var STreeVisitor = Class.extend({
    init: function(){

    },

    visit: function(context, snode){

    }
});
```

Tellurium Engine also defines a Visitor Chain to pass in multiple visitors.

```
var STreeChainVisitor = Class.extend({
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

    visit: function(context, snode){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].visit(context, snode);
        }
    }
});
```

For the show UI method, the following two Visitors are implemented.

### Outline Visitor ###

The outline visitor is used to mark the UI elements inside a UI module to differentiate the UI elements in the UI module from other UI elements.

The outline visitor include a worker to outline an element.

```

var UiOutlineVisitor = STreeVisitor.extend({
    
    visit: function(context, snode){
        var elem = snode.domRef;
        teJQuery(elem).data("originalOutline", elem.style.outline);
        elem.style.outline = tellurium.outlines.getDefaultOutline();
    }
});
```

and a cleaner to restore the UI to the original one.

```
var UiOutlineCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        var $elem = teJQuery(elem);
        var outline = $elem.data("originalOutline");
        elem.style.outline = outline;
        $elem.removeData("originalOutline");
    } 
});

```

### Tooltip Visitor ###

The Tooltip visitor is used to show the full UID of an element in a tooltip fasion. Tellurium exploited [jQuery Simpletip plugin](http://craigsworks.com/projects/simpletip/) to achieve this visual effect. In additional to that, the tooltip visitor also changes the outlines of the selected UI elements.

We need to change Simpletip plugin code a bit. By default Simpletip create a div element and insert this element inside a UI element that you want to show tooltip. But this would not work if the tag of the UI element is "input", thus, we changed this code to use insertAfter as follows.

```
      var tooltip = teJQuery(document.createElement('div'))
                     .addClass(conf.baseClass)
                     .addClass("teengine")
                     .addClass( (conf.fixed) ? conf.fixedClass : '' )
                     .addClass( (conf.persistent) ? conf.persistentClass : '' )
                     .css({'z-index': '2', 'position': 'right', 'padding': '8', 'color': '#303030',
                            'background-color': '#f5f5b5', 'border': '1', 'solid': '#DECA7E',
                            'font-family': 'sans-serif', 'font-size': '8', 'line-height': '12px', 'text-align': 'center'
                          })
                     .html(conf.content)
                     .insertAfter(elem);
```

In additional to that, we added a class "teengine" for the div tag so that it will conflict with users' web content.

Like the outline visitor, the tooltip visitor includes a worker to set up the visual effects,

```
var UiSimpleTipVisitor = STreeVisitor.extend({

    visit: function(context, snode) {
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        var $elem = teJQuery(elem);
        $elem.data("level", snode.getLevel());
        $elem.simpletip({
            // Configuration properties
            onShow: function() {
                var $parent = this.getParent();
                var parent = $parent.get(0);
                var level = $parent.data("level");

                var outline = $parent.data("outline");
                if(outline == undefined || outline == null){
                    $parent.data("outline", parent.style.outline);
                }

                parent.style.outline = tellurium.outlines.getOutline(level);
            },
            onHide: function() {
                var $parent = this.getParent();
                var parent = $parent.get(0);

                parent.style.outline = $parent.data("outline");
            },

            content: convertRidToUid(frid),
            fixed: false
        });
    }
});
```

and a cleaner to remove the visual effects.

```
var UiSimpleTipCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        var $elem = teJQuery(elem);
        $elem.removeData("outline");
        $elem.removeData("level");
        $elem.find("~ div.teengine.tooltip").remove();
    }
});
```

## Demo ##

To show the UI module visual effect, we consider a Login UI module as follows.

```
public class FormExampleModule extends DslContext {

  public void defineUi() {
    ui.Form(uid: "Form", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }
  }
```

Before the `startShow("Form")` command, the UI looks as follows,

http://tellurium-users.googlegroups.com/web/teshowuinoeffect.png?gda=K7wvOUYAAACsdq5EJRKLPm_KNrr_VHB_D4HLj40ELs-zhpN3Bl4yREf8cXQvcxDOBRa54Ef7YmV3riz0RlMs_1ov_iNdB7P8E-Ea7GxYMt0t6nY0uV5FIQ&gsc=oGileAsAAACCUi6IlstSnnhb_xNbAycZ

and after the `startShow` command, the UI module is outlined by the outline visitor.

http://tellurium-users.googlegroups.com/web/teshowuidefault.png?gda=O6D890UAAACsdq5EJRKLPm_KNrr_VHB_D4HLj40ELs-zhpN3Bl4yRN5qC_dOTsTgqGqvK1ZkNDxzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A&gsc=oGileAsAAACCUi6IlstSnnhb_xNbAycZ

If you mouse over the UI module, you will see the visual effects as follows.

http://tellurium-users.googlegroups.com/web/teshowuimouseover.png?gda=hwCMV0cAAACsdq5EJRKLPm_KNrr_VHB_D4HLj40ELs-zhpN3Bl4yRLXL3uGDhcmmKEh-Qhrel1EVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=oGileAsAAACCUi6IlstSnnhb_xNbAycZ

You may be a bit confused by the multiple UID labels. For example, When user hives over the Username Input box, its parent "Username" and its grandparent "Form" are shown as well. We added color coding to the outlines. Different levels in the hierarchy are presented by different colors. How the layout maps to each individual UI element in the STree can be illustrated more clearly by the following figure.

http://tellurium-users.googlegroups.com/web/telluriumshowuisnapshot2.png?gda=jciJlU4AAACsdq5EJRKLPm_KNrr_VHB__uAWGGGjpMtQflLHIKHYYIBs4RzukN2p4-NZziI_5n9VyjA3JensFE0ElYzuCQ7x47Cl1bPl-23V2XOW7kn5sQ&gsc=oz20hwsAAAD_weMBWNZir9C-tYu9DiER

Once you call the following command

```
   endShow("Form");
```

The visual effects are removed and the UI is restored to the original one.

# Engine Logging #

Tellurium Engine uses [Firebug Lite](http://getfirebug.com/lite.html) to add debug information to the console. By default the Firebug Lite is off and you will only see a Firebug icon on the bottom right as shown in the following figure.

http://tellurium-users.googlegroups.com/web/telluriumEngineFirebugLiteOff.png?gda=g8Q-1lMAAACsdq5EJRKLPm_KNrr_VHB_DVXQX3coVrzCE0wbgZNX0sYl7qkjkLygZeJEraCq1Nizt3T_4awU0H2oK-9nYgtIMrYifh3RmGHD4v9PaZfDexVi73jmlo822J6Z5KZsXFo&gsc=jh3WkgsAAABHX8Z8u5jajkir99LhxBO3

If you click on the icon and the Firebug Lite console will appear and the log information will be shown on the console as follows.

http://tellurium-users.googlegroups.com/web/telluriumEngineFirebugLiteOn.png?gda=jO0GwlIAAACsdq5EJRKLPm_KNrr_VHB_DVXQX3coVrzCE0wbgZNX0sYl7qkjkLygZeJEraCq1Njg8bv9nMK6yOBSE4EEIlmEVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE&gsc=jh3WkgsAAABHX8Z8u5jajkir99LhxBO3

To turn on the debug, you should either click on the "Tellurium Log" button or call the following method from your test case.

```
    void useEngineLog(boolean isUse);
```

# Add JavaScript Error Stack to Selenium Errors #

We utilized [the JavaScript Stack Trace project](http://github.com/emwendelin/javascript-stacktrace) to refactor Selenium Errors in Selenium Core so that the JavaScript Error Stack will be passed back Tellurium Core.

The implementation is in htmlutils.js in Selenium Core as follows.

```
function SeleniumError(message) {
    if(tellurium.logManager.isUseLog){
        var jstack = printStackTrace();
        if(jstack != null && typeof(jstack) != 'undefined'){
            message = message + "\nJavaScript Error Stack: \n" + jstack.join('\n\n');
        }
    }
    var error = new Error(message);
    if (typeof(arguments.caller) != 'undefined') { // IE, not ECMA
        var result = '';
        for (var a = arguments.caller; a != null; a = a.caller) {
            result += '> ' + a.callee.toString() + '\n';
            if (a.caller == a) {
                result += '*';
                break;
            }
        }
        error.stack = result;
    }
    error.isSeleniumError = true;
    fbError("Selenium Error: "+ message, error);
    return error;
}
```

where `printStackTrace()` is the stacktrace project API. Be aware that the
JavaScript error stack will only be passed back to Tellurium Core if the Engine
log is enabled by calling.

```
   useEngineLog(true);
```

Example output:

```
com.thoughtworks.selenium.SeleniumException: ERROR: Element uimcal={"rid":"search.search_project_button","locator":"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]"} not found
JavaScript Error Stack: 
{anonymous}(null)@http://localhost:4444/selenium-server/core/scripts/utils.js:589

printStackTrace()@http://localhost:4444/selenium-server/core/scripts/utils.js:574

SeleniumError("Element uimcal={\"rid\":\"search.search_project_button\",\"locator\":\"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]\"} not found")@http://localhost:4444/selenium-server/core/scripts/htmlutils.js:806

{anonymous}("uimcal={\"rid\":\"search.search_project_button\",\"locator\":\"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]\"}")@http://localhost:4444/selenium-server/core/scripts/selenium-browserbot.js:1341

{anonymous}("uimcal={\"rid\":\"search.search_project_button\",\"locator\":\"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]\"}")@http://localhost:4444/selenium-server/core/scripts/selenium-api.js:227

{anonymous}([object Object],[object Object])@http://localhost:4444/selenium-server/core/scripts/tellurium.js:922

{anonymous}()@http://localhost:4444/selenium-server/core/scripts/tellurium.js:876

{anonymous}("[{\"uid\":\"search.search_project_button\",\"args\":[\"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]\"],\"name\":\"click\",\"sequ\":7}]","")@http://localhost:4444/selenium-server/core/scripts/tellurium-extensions.js:338

{anonymous}("[{\"uid\":\"search.search_project_button\",\"args\":[\"jquery=form:group(input[name=q], input[value=Search projects][type=submit], input[value=Search the Web][type=submit]) input[value=Search projects][type=submit]\"],\"name\":\"click\",\"sequ\":7}]","")@http://localhost:4444/selenium-server/core/scripts/htmlutils.js:60

{anonymous}([object Object],[object Object])@http://localhost:4444/selenium-server/core/scripts/selenium-commandhandlers.js:330

{anonymous}()@http://localhost:4444/selenium-server/core/scripts/selenium-executionloop.js:112

{anonymous}(-3)@http://localhost:4444/selenium-server/core/scripts/selenium-executionloop.js:78

{anonymous}(-3)@http://localhost:4444/selenium-server/core/scripts/htmlutils.js:60
	at com.thoughtworks.selenium.HttpCommandProcessor.throwAssertionFailureExceptionOrError(HttpCommandProcessor.java:97)
	at com.thoughtworks.selenium.HttpCommandProcessor.doCommand(HttpCommandProcessor.java:91)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)

```