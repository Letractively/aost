function saveOptions(){
    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        Preferences.setPref("extensions.trump.exportdirectory", elem.value);
        logger.debug("TrUMP export Directory is updated to " + elem.value);
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        Preferences.setPref("extensions.trump.jslog", elem.checked);
        logger.debug("TrUMP Javascript logging option " + elem.checked);
    }

    elem = document.getElementById("trump-option-logwrap");
    if(elem != null){
        Preferences.setPref("extensions.trump.logwrap", elem.checked);
        logger.debug("TrUMP log Wrapping option " + elem.checked);
    }

    elem = document.getElementById("trump-option-grouplocating");
    if(elem != null){
        Preferences.setPref("extensions.trump.grouplocating", elem.checked);
        logger.debug("TrUMP group locating option " + elem.checked);
    }

	return true;
}

function cancelOptions(){
   return true;
}

function loadOptions() {
    var os = window.arguments[0];
//    alert("OS is " + os);
    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        var exportDir = Preferences.getPref("extensions.trump.exportdirectory");
        if(exportDir == undefined || exportDir == null){
            if(os == "Windows"){
                elem.value = Preferences.DEFAULT_OPTIONS.defaultWinDirectory;
            }else{
                elem.value = Preferences.DEFAULT_OPTIONS.defaultDirectory;
            }
        }else{
            elem.value = exportDir;
        }
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        var jslog = Preferences.getPref("extensions.trump.jslog");
        if(jslog == undefined){
            jslog = Preferences.DEFAULT_OPTIONS.defaultJSLog;
        }
        elem.checked = jslog
    }

    elem = document.getElementById("trump-option-logwrap");
    if(elem != null){
        var logwrap = Preferences.getPref("extensions.trump.logwrap");
        if(logwrap == undefined){
            logwrap = Preferences.DEFAULT_OPTIONS.defaultLogWrap;
        }
        elem.checked = logwrap;
    }
    
    elem = document.getElementById("trump-option-grouplocating");
    if(elem != null){
        var glocating = Preferences.getPref("extensions.trump.grouplocating");
        if(glocating == undefined){
            glocating = Preferences.DEFAULT_OPTIONS.defaultGroupLocating;
        }
        elem.checked = glocating;
    }
};