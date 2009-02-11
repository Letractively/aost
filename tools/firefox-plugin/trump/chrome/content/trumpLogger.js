function TrumpLogger() {
	var self = this;
	var levels = ["log","debug","info","warn","error"];
    this.maxEntries = 2000;
	this.entries = [];

	levels.forEach(function(level) {
					   self[level] = function(message) {
						   self.log(message, level);
                           //also log to Javascript Console, can comment it out if we do not want it
                           self.logToConsole(message, level);
					   }
				   });

	this.observers = [];

	this.exception = function(exception) {
        var msg = "Unexpected Exception: " + describe(exception, ', ');
        this.error(msg);
	}

	this.log = function(message, level) {
		var entry = {
			message: message,
			level: level,
			line: function() {
				return '[' + this.level + '] ' + message + "\n";
			}
		};
		this.entries.push(entry);
        if (this.entries.length > this.maxEntries) this.entries.shift();
		this.observers.forEach(function(o) { o.onAppendEntry(entry) });
	}

	this.clear = function() {
		this.entries.splice(0, this.entries.length);
		this.observers.forEach(function(o) { o.onClear() });
	}

    this.logToConsole = function(message, level){
        if(level == "debug"){
            logger.debug(message);
        }
        if(level == "info"){
            logger.info(message);
        }
        if(level == "warn"){
            logger.warn(message);
        }
        if(level == "error"){
            logger.error(message);
        }
    }
}

var LOG = new TrumpLogger();