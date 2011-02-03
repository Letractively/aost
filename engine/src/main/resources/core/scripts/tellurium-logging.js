
function dumpObject(obj) {
    if (typeof(console) != "undefined") {
        var output = '';
        for (var p in obj)
            output += p + '\n';

        console.log(output);
    }
}

function strObject(obj){
    if(obj == null){
        return "null";    
    }

    if(obj == undefined){
        return "undefined";
    }
    var output = "";
    for(var p in obj){
        output += p + ": " + obj[p] + ", "
    }

    return output;
}

function fbLog(msg, obj){
    logger.debug(msg);
    if (typeof(console) != "undefined") {
        console.log(msg, obj);
    }
}

function fbInfo(msg, obj){
    logger.info(msg);
    if (typeof(console) != "undefined") {
        console.info(msg, obj);
    }
}

function fbDebug(msg, obj){
     logger.debug(msg);
    if (typeof(console) != "undefined") {
        console.debug(msg, obj);
    }
}

function fbWarn(msg, obj){
    logger.warn(msg);
    if (typeof(console) != "undefined") {
        console.warn(msg, obj);
    }
}

function fbError(msg, obj){
    logger.error(msg);
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


function LogManager(){
    this.isUseLog = false;
//    this.isUseLog = true;
    this.logLevel = "info";
}

//dump logging message to dummy device, which sallows all messages == no logging
function DummyLogger(){

}

DummyLogger.prototype.info = function(msg){

};

DummyLogger.prototype.warn = function(msg){

};

DummyLogger.prototype.error = function(msg){

};

DummyLogger.prototype.fatal = function(msg){

};

DummyLogger.prototype.debug = function(msg){

};

DummyLogger.prototype.trace = function(msg){

};


 //uncomment this and comment the next line if you want to see the logging message in window
 //but it would slow down the testing dramatically, for debugging purpose only.

/*
var jslogger = new Log4js.getLogger("TeEngine");
jslogger.setLevel(Log4js.Level.ALL);
//jslogger.addAppender(new Log4js.MozillaJSConsoleAppender());
jslogger.addAppender(new Log4js.ConsoleAppender());
*/

//var logger = new Log4js.getLogger("TeEngine");
var jslogger = new DummyLogger();
var logger = LOG;
