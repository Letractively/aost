

Recorder.prototype.UiSelectListener = function(event){
    var recorder = event.data.recorder;
    logger.debug('listener: event.type=' + event.type + ', target=' + event.target);
    var state = document.getElementById("record-button").getAttribute("class");
    if(state == RecordState.PAUSE)
        event.preventDefault();
    var element = event.target;
    recorder.RecordDomNode(element);
    var baseUrl = document.getElementById("windowURL").value;
    var actualUrl = element.ownerDocument.location.href;
    if (baseUrl.trim().length == 0 || baseUrl != actualUrl) {
        document.getElementById("windowURL").value = actualUrl;
    }    
};

Recorder.prototype.frameFocusListener = function(event) {
    event.preventDefault();
    var recorder = event.data.recorder;
//  logger.debug("Inside frameFocusListener() ..");
    if (recorder.frameName == null) {
//      logger.debug("frameName : " + event.target.name);
        recorder.frameName = event.target.name;
    }
};

Recorder.prototype.frameBlurListener = function(event) {
    event.preventDefault();
    var recorder = event.data.recorder;
    recorder.frameName = null;
};

Recorder.prototype.onUnloadDocumentListener = function(event){
    var url = event.target.URL || event.target.baseURI;
    logger.debug("Unloading Window " + url);
};