

# Introduction #

Recently, I worked on a Firefox plugin called [Tellurium IDE](http://code.google.com/p/aost/wiki/TelluriumIde080RC1), which is a derivative from our existing Firefox Plugin [Tellurium UI Module Plugin](http://code.google.com/p/aost/wiki/TrUMP). The main features of the Tellurium IDE are to record, automatically generate Tellurium DSL scripts, and play tests. I like to share my experience with you, but I will not cover the basics of Firefox plugin development such as XUL, but mainly focus on the implementation details of the new features.

# Implementation #

## Application Window ##

Tellurium IDE used jQuery a lot. But be aware that the main target of Tellurium IDE is the application window, not the Firefox plugin XUL window itself. Thus, I need to pay attention to the document variable and make sure it is the application window when I work on record and replay.

Fortunately, Mozilla provides two Apis to retrieve the application windows, i.e.,

```
getMostRecentWindow();

getEnumerator();
```

The former gets back the most recent window and the latter returns all application windows. For example, in Tellurium IDE, I defined the following methods for different purposes.

```
    getMostRecentDocument: function() {
        var win = Components.classes["@mozilla.org/appshell/window-mediator;1"]
                .getService(Components.interfaces.nsIWindowMediator)
                .getMostRecentWindow("navigator:browser");

        return win.getBrowser().contentDocument;
    },

    getAllWindows: function() {
        var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
        var e = wm.getEnumerator("navigator:browser");
        var wins = new Array();
        while (e.hasMoreElements()) {
            var window = e.getNext();
            var browsers = window.getBrowser().browsers;
            for (var i = 0; i < browsers.length; i++) {
                wins.push(browsers[i].contentWindow);
            }
        }

        return wins;
    }
```

## Event Listeners for recording ##

To record a user's actions such as type and click, I need to register event listeners and unregister them when the recording is done. In Tellurium IDE, I used [jQuery live API](http://api.jquery.com/live/) to preregister the event listeners even before the web elements exist, which is really cool and greatly simplified our design and implementation.

```
Recorder.prototype.attachActionListeners = function(window){
    logger.debug("Attaching listeners for action...");
    var self = this;
    window.addEventListener("beforeunload",
            function(event) {
                try {
                    var recordToolbarButton = document.getElementById("record-button");
                    if (recordToolbarButton.getAttribute("checked")) {
                        self.recordCommand("waitForPageToLoad", null, 30000, ValueType.NUMBER);
                        var url = event.target.URL || event.target.baseURI;
                        logger.debug("Unloading Window " + url);
                        self.generateSource();
                    }
                } catch(error) {
                    logger.error("Error processing beforeunload event:\n" + describeErrorStack(error));
                }
            },
     false);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").live("change", {recorder: this}, this.typeListener);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").live("click", {recorder: this}, this.clickListener);
    teJQuery(window.document).find("input, a, p, link, textarea, button, table, tr, td, th, div, span, label").live("mousedown", {recorder: this}, this.rememberClickedListener);
    teJQuery(window.document).find("select, option").live("focus", {recorder: this}, this.selectFocusListener);
    teJQuery(window.document).find("select, option").live("mousedown", {recorder: this}, this.selectMousedownListener);
    teJQuery(window.document).find("select, option").live("change", {recorder: this}, this.selectListener);
};

```

where _teJQuery_ is an alias for _jQuery_ to avoid name conflict with _jQuery_ in application, which could be in a different version. Also, I noticed that the "beforeunload" event is kinda of special and I have to register it in a traditional way.

You may also noticed that I passed event data such as `recorder: this`, which is a very elegant way to pass in the recorder object to the event handler. The event handler retrieves the event data and gets hold of the recorder object as follows.

```
Recorder.prototype.typeListener = function(event) {
    var recorder = event.data.recorder;
    var tagName = event.target.tagName.toLowerCase();
    var type = event.target.type;
    if (('input' == tagName && ('text' == type || 'password' == type || 'file' == type)) || 'textarea' == tagName) {
        recorder.recordCommand("type", event.target, event.target.value, ValueType.STRING);
    }
};

```

To unregister the event listeners, I used [jQuery die API](http://api.jquery.com/die).

```
Recorder.prototype.detachActionListeners = function(window){
    logger.debug("Detaching listeners for action...");

    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").die("change", this.typeListener);
    teJQuery(window.document).find("input, a, p, link, select, textarea, button, table, tr, td, th, div, span, label").die("click", this.clickListener);
    teJQuery(window.document).find("input, a, p, link, textarea, button, table, tr, td, th, div, span, label").die("mousedown", this.rememberClickedListener);
    teJQuery(window.document).find("select, option").die("focus", this.selectFocusListener);
    teJQuery(window.document).find("select, option").die("mousedown", this.selectMousedownListener);
    teJQuery(window.document).find("select, option").die("change", this.selectListener);
};
```

## Record Multiple Application Windows ##

When a user acts on the web application, the page may be refreshed or redirected. For the new pages, I need to attach event listeners to them as well so that I can record the user's actions. The key is the "DOMContentLoaded" event. I used the following script to detect and attach to the "DOMContentLoaded" event.

```
       var TelluriumIDE = {
            onLoad: function() {
                this.initialized = true;
                var appcontent = document.getElementById("appcontent");   
                if (appcontent) {
                    var self = this;
                    appcontent.addEventListener("DOMContentLoaded", function(event) {
                        self.onContentLoaded(event);
                    }, false);
                    appcontent.addEventListener("load", this.onPageLoad, true);
                }
            },

            onContentLoaded: function(event) {
                var editor = this.getEditor();
                if (editor) {
                    editor.onDOMContentLoaded(event);
                }

            getEditor: function() {
                var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
                var editorWindow = wm.getMostRecentWindow('global:tellurium-ide');

                return editorWindow.editor;
            }

        };

        window.addEventListener("load", function(e) {
            TelluriumIDE.onLoad(e);
        }, false);

```

The actual action code is in the Editor function as follows.

```
Editor.prototype.onDOMContentLoaded = function(event) {
    try {
        var recordToolbarButton = document.getElementById("record-button");
        if (recordToolbarButton.getAttribute("checked")) {
            logger.debug("Register window on DOMContentLoaded");
            this.recorder.registerForWindow(event.target.defaultView);
        } else {
            this.testRunner.updateDom();
        }
    } catch(error) {
        logger.error("Error processing onDOMContentLoaded:\n" + describeErrorStack(error));
    }
};
```

As you can see, it tries to register event listeners for the new page if it is still in the recording mode.

Another observation is that the "appcontent" can only be obtained from XUL overlay, otherwise, it is null.

## Autocomplete ##

I reused the autocomplete component from Selenium IDE. First, I defined the textbox to be of an autocomplete type.

```
            <textbox id="windowURL" flex="1" tooltiptext="&windowURLTextbox.tooltip;"
                     oninput="window.editor.setWindowURL(this.value)" 
                     type="autocomplete" autocompletesearch="selenium-ide-generic" enablehistory="true"
                     forcecomplete="true" tabscrolling="true"/>
```

Then, I get access to the autocomplete component in the Editor function.

```
Editor.GENERIC_AUTOCOMPLETE = Components.classes["@mozilla.org/autocomplete/search;1?name=selenium-ide-generic"].getService(Components.interfaces.nsISeleniumIDEGenericAutoCompleteSearch);

```

To populate the autocomplete list, I can use the following code.

```
        var uiTypes = this.builder.getAvailableUiTypes();
        Editor.GENERIC_AUTOCOMPLETE.setCandidates(XulUtils.toXPCOMString(this.getAutoCompleteSearchParam("uiType")), XulUtils.toXPCOMArray(uiTypes));

```

Similarly, I use the following code to remove the autocomplete list.

```
                Editor.GENERIC_AUTOCOMPLETE.clearCandidates(XulUtils.toXPCOMString(this.autoCompleteSearchParams["uiType"]));

```

## Broadcaster and Observers ##

XUL uses broadcaster to publish a state change event and all the observers will change its state accordingly. For example, I defined the following broadcaster to indicate if the IDE is in the recording state or not.

```
    <broadcasterset>
        <broadcaster id="isRecording" disabled="true"/>
    </broadcasterset>
```

The observers are the test play buttons such as "Step", "Run", and "Stop". These buttons  watch over the _disabled_ attribute from the broadcaster.

```
        <toolbarbutton id="step-button" label="Step" tooltiptext="&step.tooltip;"
                       oncommand="window.editor.stepButton()">
            <observes element="isRecording" attribute="disabled"/>
        </toolbarbutton>
        <toolbarbutton id="run-button" label="Run" tooltiptext="&run.tooltip;" oncommand="window.editor.runButton()" class="run">
            <observes element="isRecording" attribute="disabled"/>
        </toolbarbutton>
        <toolbarbutton id="stop-button" label="Stop" class="icon" tooltiptext="&stopButton.tooltip;"
                       oncommand="window.editor.toggleStopButton()">
            <observes element="isRecording" attribute="disabled"/>
        </toolbarbutton>
 
```

If the _disabled_ is changed to _false_ in the broadcaster, all the three buttons will become enabled. This implementation is a really elegant without a line of code.

## Scale ##

Firefox 3 changes the slider widget to a scale widget, which could be used to control a data range. Tellurium IDE used scale to control the interval between two commands when run tests, i.e., it controls the test speed.

```
        <hbox>
            <scale value="1000" min="300" max="3000" increment="100" id="testSpeedSlider"
                   showLabel="true" labelPosition="below" onchange="window.editor.updateTestSpeed(this.value)"/>
            <textbox label="&TestSpeed.label;" readonly="true" observes="testSpeedSlider" size="4" maxlength="4"/>
        </hbox>
```

The textbox observes the data change and update the value on the GUI accordingly.

## Test Script Generation ##

Test script includes two parts, i.e., UI modules and commands. A UI module is generated by an algorithm and then reference IDs in commands are replaced with UIDs accordingly. For more details, please see [Tellurium IDE design](http://code.google.com/p/aost/wiki/TelluriumIde080RC1#Design).

Be aware, I need to pass the root DOM element or application document in jQuery selector. For example,

```
    var $extras = teJQuery(root.domNode).find("input, link, form, select, button, table").filter(":visible");
```

## Test Runner ##

The test-runner.js is the test runner to play recorded Tellurium test scripts. The tests are run by acting on the application window directly instead of loading up the application in an iFrame like Selenium does. The test driving engine is powered totally by Tellurium new engine, which is based on jQuery and tellurium UI module group locating algorithm.

## Debug ##

Firefox plugin development needs a good debug tool, I would suggest [Venkman JavaScript debugger](http://www.mozilla.org/projects/venkman/). You unselect "exclude browser files" in the option. After the Firefox plugin is loaded up, you can see all the JavaScript files in Venkman. You can set breakpoints to debug your code.

# Summary #

Tellurium IDE is our first step to automatically generate Tellurium test script, which is different from Selenium test script. The former one does not use locator directly but use UI module to define the UI widget, then use Tellurium framework to locate the UI module, which makes Tellurium robust to changes and expressive. Thus, Tellurium IDE does not suffer the recorded test robust problem that other "record and replay" type of IDEs faced. Tellurium IDE is a work in progress. If you like it and want to contribute to it. Please contact me directly. Thanks in advance.

# Resources #

  * [Tellurium IDE](http://code.google.com/p/aost/wiki/TelluriumIde080RC1)
  * [Tellurium IDE on Firefox addon site](https://addons.mozilla.org/en-US/firefox/addon/217284/)
  * [Tellurium IDE 0.8.0 RC1 tutorial video](http://www.youtube.com/watch?v=yVIBY8QzWzE/)
  * [Tellurium Project Home](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium on Twitter](http://twitter.com/telluriumsource)
  * [Jian Fang on Twitter](http://twitter.com/johnjianfang)