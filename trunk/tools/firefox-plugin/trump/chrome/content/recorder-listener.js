Recorder.prototype.typeListener = function(event) {
    var recorder = event.data.recorder;
    var tagName = event.target.tagName.toLowerCase();
    var type = event.target.type;
    if (('input' == tagName && ('text' == type || 'password' == type || 'file' == type)) ||
            'textarea' == tagName) {
        recorder.recordCommand("type", event.target, event.target.value);
    }
};

Recorder.prototype.selectFocusListener = function(event){
    var tagName = event.target.nodeName.toLowerCase();
    if ('select' == tagName && event.target.multiple) {
        logger.debug('remembering selections');
        var options = event.target.options;
        for (var i = 0; i < options.length; i++) {
            if (options[i]._wasSelected == null) {
                // is the focus was gained by mousedown event, _wasSelected would be already set
                options[i]._wasSelected = options[i].selected;
            }
        }
    }
};

Recorder.prototype.selectMousedownListener = function(event) {
    var tagName = event.target.nodeName.toLowerCase();
    if ('option' == tagName) {
        var parent = event.target.parentNode;
        if (parent.multiple) {
            logger.debug('remembering selections');
            var options = parent.options;
            for (var i = 0; i < options.length; i++) {
                options[i]._wasSelected = options[i].selected;
            }
        }
    }
};

Recorder.prototype.clickListener = function(event){
    var recorder = event.data.recorder;

    if (event.button == 0) {
        var clickable = recorder.findClickableElement(event.target);
        if (clickable) {
//            if (recorder.mouseoverLocator) {
//                recorder.recordCommand('mouseOver', this.mouseoverLocator, '');
//                delete recorder.mouseoverLocator;
//            }
            recorder.recordCommand("click", event.target, '');
        } else {
            var target = event.target;
            recorder.callIfMeaningfulEvent(function() {
                recorder.recordCommand("click", target, '');
            });
        }
    }
};

Recorder.prototype.selectListener = function(event) {
    var recorder = event.data.recorder;
    var tagName = event.target.tagName.toLowerCase();
    if ('select' == tagName) {
        if (!event.target.multiple) {
            var option = event.target.options[event.target.selectedIndex];
            logger.debug('selectedIndex=' + event.target.selectedIndex);
            recorder.recordCommand("select", event.target, option);
        } else {
            logger.debug('change selection on select-multiple');
            var options = event.target.options;
            for (var i = 0; i < options.length; i++) {
                logger.debug('option=' + i + ', ' + options[i].selected);
                if (options[i]._wasSelected == null) {
                    logger.warn('_wasSelected was not recorded');
                }
                if (options[i]._wasSelected != options[i].selected) {
                    var value = options[i];
                    if (options[i].selected) {
                        recorder.recordCommand("addSelection", event.target, value);
                    } else {
                        recorder.recordCommand("removeSelection", event.target, value);
                    }
                    options[i]._wasSelected = options[i].selected;
                }
            }
        }
    }
};

Recorder.prototype.uiSelectListener = function(event){
    var recorder = event.data.recorder;
    logger.debug('listener: event.type=' + event.type + ', target=' + event.target.tagName);
    var state = document.getElementById("record-button").getAttribute("class");
    if(state == RecordState.PAUSE)
        event.preventDefault();
    var element = event.target;
    recorder.recordDomNode(element);
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