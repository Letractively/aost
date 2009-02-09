function TrumpLogger() {
	var self = this;
	var levels = ["log","debug","info","warn","error"];
    this.maxEntries = 2000;
	this.entries = [];

	levels.forEach(function(level) {
					   self[level] = function(message) {
						   self.log(message, level);
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
}

var LOG = new TrumpLogger();