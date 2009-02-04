var getStackTrace = function() {
    try {(0)()} catch (e) {
        return e.stack.replace(/^.*?\n/,'').replace(/(?:\n@:0)?\s+$/m,'').replace(/^\(/gm,'{anon}(').split("\n");
    }
}
var addLineNumber = function(loggingEvent){
    var stack = getStackTrace()[6];  //assuming that this is called from the appender this 6 is what we want!
    stack = stack.substring(stack.indexOf("chrome://trump/content/") + "chrome://trump/content/".length, stack.length);
    loggingEvent.message = "["+stack+"] " + loggingEvent.message;
    return loggingEvent;
}
Log4js.MozillaLineNumberJSConsoleAppender = function() {
	this.layout = new Log4js.SimpleLayout();
	try {
		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		this.jsConsole = Components.classes["@mozilla.org/consoleservice;1"].getService(Components.interfaces.nsIConsoleService);
		this.scriptError = Components.classes["@mozilla.org/scripterror;1"].createInstance(Components.interfaces.nsIScriptError);
	} catch (e) {
		log4jsLogger.error(e);
	}
};

Log4js.MozillaLineNumberJSConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {
	/**
	 * @see Log4js.Appender#doAppend
	 */
	doAppend: function(loggingEvent) {
        addLineNumber(loggingEvent);
        try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			this.scriptError.init(this.layout.format(loggingEvent), null, null, null, null, this.getFlag(loggingEvent), loggingEvent.categoryName);
			this.jsConsole.logMessage(this.scriptError);
		} catch (e) {
			log4jsLogger.error(e);
		}
	},
	toString: function() {
	    return "Log4js.MozillaLineNumberJSConsoleAppender";
	},
	getFlag: function(loggingEvent)
	{
		var retval;
		switch (loggingEvent.level) {
			case Log4js.Level.FATAL:
				retval = 2;//nsIScriptError.exceptionFlag = 2
				break;
			case Log4js.Level.ERROR:
				retval = 0;//nsIScriptError.errorFlag
				break;
			case Log4js.Level.WARN:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
			default:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
		}

		return retval;
	}
});
Log4js.TrumpLogAppender = function() {
	this.currentLine = 0;
};
Log4js.TrumpLogAppender.prototype = Log4js.extend(new Log4js.Appender(), {
	doAppend: function(loggingEvent) {
		var now = new Date();
		var lines = loggingEvent.message.split("\n");
		var headTag = document.getElementsByTagName("head")[0];

		for (var i = 1; i <= lines.length; i++) {
			var value = lines[i - 1];
			if (i == 1) {
				value = loggingEvent.level.toString() + ": " + value;
			} else {
				value = "> " + value;
			}

			var metaTag = document.createElement("div");
			metaTag.setAttribute("name", "X-log4js:" + this.currentLine);
			metaTag.setAttribute("content", value);
			headTag.appendChild(metaTag);
			this.currentLine += 1;
		}
	},
	toString: function() {
	    return "Log4js.TrumpLogAppender";
	}
});


//and here is the logger!
var logger = new Log4js.getLogger("root");
logger.setLevel(Log4js.Level.ALL);
logger.addAppender(new Log4js.MozillaLineNumberJSConsoleAppender());