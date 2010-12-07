function saveOptions(){
    var elem = document.getElementById("teide-options-directory");
    if(elem != null){
        Preferences.setPref("extensions.teide.exportdirectory", elem.value);
        logger.debug("Tellurium IDE export Directory is updated to " + elem.value);
    }

    elem = document.getElementById("teide-option-jslog");
    if(elem != null){
        Preferences.setPref("extensions.teide.jslog", elem.checked);
        logger.debug("Tellurium IDE Javascript logging option " + elem.checked);
    }

    elem = document.getElementById("teide-option-logwrap");
    if(elem != null){
        Preferences.setPref("extensions.teide.logwrap", elem.checked);
        logger.debug("Tellurium IDE log Wrapping option " + elem.checked);
    }

    elem = document.getElementById("teide-option-grouplocating");
    if(elem != null){
        Preferences.setPref("extensions.teide.grouplocating", elem.checked);
        logger.debug("Tellurium IDE group locating option " + elem.checked);
    }

    elem = document.getElementById("teide-option-extranodes");
    if(elem != null){
        Preferences.setPref("extensions.teide.extranodes", elem.checked);
        logger.debug("Tellurium IDE extra nodes option " + elem.checked);
    }
    
	return true;
}

function cancelOptions(){
   return true;
}

function loadOptions() {
    var os = window.arguments[0];
//    alert("OS is " + os);
    var elem = document.getElementById("teide-options-directory");
    if(elem != null){
        var exportDir = Preferences.getPref("exportdirectory");
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

    elem = document.getElementById("teide-option-jslog");
    if(elem != null){
        var jslog = Preferences.getPref("jslog");
        if(jslog == undefined){
            jslog = Preferences.DEFAULT_OPTIONS.defaultJSLog;
        }
        elem.checked = jslog
    }

    elem = document.getElementById("teide-option-logwrap");
    if(elem != null){
        var logwrap = Preferences.getPref("logwrap");
        if(logwrap == undefined){
            logwrap = Preferences.DEFAULT_OPTIONS.defaultLogWrap;
        }
        elem.checked = logwrap;
    }
    
    elem = document.getElementById("teide-option-grouplocating");
    if(elem != null){
        var glocating = Preferences.getPref("grouplocating");
        if(glocating == undefined){
            glocating = Preferences.DEFAULT_OPTIONS.defaultGroupLocating;
        }
        elem.checked = glocating;
    }

    elem = document.getElementById("teide-option-extranodes");
    if(elem != null){
        var extra = Preferences.getPref("extranodes");
        if(extra == undefined){
            extra = Preferences.DEFAULT_OPTIONS.defaultExtraNodes;
        }
        elem.checked = extra;
    }
}