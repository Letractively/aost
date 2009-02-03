var DISPLAY_LINE_NUMBERS_IN_DEBUG = true;



function getStackTrace() {
    try {(0)()} catch (e) {
        return e.stack.replace(/^.*?\n/,'').replace(/(?:\n@:0)?\s+$/m,'').replace(/^\(/gm,'{anon}(').split("\n");
    }
}
/**
 * get a log4js logger with line number support.
 * to show line numbers in log statements pass an object: {showLineNumber: true}
 */
var getTrumpLogger = function(conf){
    var showLineNumber = conf.showLineNumber || false;
    var l = new Log4js.getLogger("root");
    l.setLevel(Log4js.Level.ALL);
    l.addAppender(new Log4js.MozillaJSConsoleAppender());
    var that = {};
    var attachLogFunction = function(name){
        var f = function(m){
            if(showLineNumber){
                var from = getStackTrace()[1];
                l[name](from+" : "+m);
            } else {
                l[name](m);
            }
        };
        that[name] = f;
        return f;
    }
    attachLogFunction("trace");
    attachLogFunction("debug");
    attachLogFunction("info");
    attachLogFunction("warn");
    attachLogFunction("error");
    attachLogFunction("fatal");
    that.log = l.log;
    return that;
}

//this logger will log into firefox's error console
var logger = getTrumpLogger({showLineNumber: DISPLAY_LINE_NUMBERS_IN_DEBUG});