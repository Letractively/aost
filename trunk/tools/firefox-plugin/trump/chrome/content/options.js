


function saveOptions() {

    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        TrUMPOption.directry = elem.value;
        logger.debug("TrUMP export Directory is updated to " + elem.value);
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        TrUMPOption.jslog = elem.checked;
        logger.debug("TrUMP Javascript logging option " + elem.checked);
    }

	return true;
}

function loadOptions() {
    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        elem.value = TrUMPOption.directry;
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        elem.checked = TrUMPOption.jslog;
    }
}
