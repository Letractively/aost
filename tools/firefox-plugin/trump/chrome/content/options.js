function Options(){
    this.preferences = null;
}

var OPTIONS = new Options();

Options.prototype.saveOptions = function() {

    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        this.preferences.directry = elem.value;
        logger.debug("TrUMP export Directory is updated to " + elem.value);
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        this.preferences.jslog = elem.checked;
        logger.debug("TrUMP Javascript logging option " + elem.checked);
    }

    elem = document.getElementById("trump-option-logwrap");
    if(elem != null){
        this.preferences.logWrap = elem.checked;
        logger.debug("TrUMP log Wrapping option " + this.preferences.logWrap);
    }

	return true;
}

Options.prototype.loadOptions = function() {
    this.preferences = window.arguments[0];

    var elem = document.getElementById("trump-options-directory");
    if(elem != null){
        elem.value = this.preferences.directry;
    }

    elem = document.getElementById("trump-option-jslog");
    if(elem != null){
        elem.checked = this.preferences.jslog;
    }

    elem = document.getElementById("trump-option-logwrap");
    if(elem != null){
//        alert("logwrap option " + this.preferences.logWrap);
        elem.checked = this.preferences.logWrap;
    }
}
