
function getUiid(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    return uiid;
}

function matchUiid(uiid1, uiid2){
    var result = new Array();
    var ar1 = uiid1.toArray();
    var ar2 = uiid2.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;
}

function matchUid(uid1, uid2){
    var uiid1 = getUiid(uid1);
    var uiid2 = getUiid(uid2);

    return matchUiid(uiid1, uiid2);
}


//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
}

Uiid.prototype.matchWith = function(uiid){
    var result = new Array();
    var ar1 = this.stack;
    var ar2 = uiid.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;
};

Uiid.prototype.subUiid = function(index){
    var nuiid = new Uiid();
    if(index >= 0 && index < this.stack.length){
        nuiid.stack = this.stack.slice(index);
    }

    return nuiid;
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
};

Uiid.prototype.pop = function(){
    if(this.stack.length > 0){
//        return this.stack.pop();
        return this.stack.shift();
    }

    return null;
};

Uiid.prototype.reverse = function(){
    if(this.stack.length > 0){
        this.stack.reverse();
    }
};

Uiid.prototype.peek = function(){
    if(this.stack.length > 0){
//        return this.stack[this.stack.length-1];
        return this.stack[0];
    }

    return null;
};

Uiid.prototype.getUid = function(){
    return this.stack.join(".");
};

Uiid.prototype.size = function(){
    return this.stack.length;
};

Uiid.prototype.toArray = function(){
    return this.stack;
};

Uiid.prototype.convertToUiid = function(uid){
    if(uid != null && trimString(uid).length > 0){
        var ids = uid.split(".");
        for(var i= 0; i<ids.length; i++){
            var pp = this.preprocess(ids[i]);
            if(pp.length == 1){
                this.push(pp[0]);
            }else{
                this.push(pp[1]);
                this.push(pp[0]);
            }
        }
    }

    return this;
};

Uiid.prototype.preprocess = function(uid){
    if(uid != null && trimString(uid).length > 0 && uid.indexOf("[") != -1){
        if(uid.indexOf("[") == 0){
            var single = uid.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = uid.indexOf("[");
            var first = uid.substring(0, index);
            var second = uid.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [uid];
};

//i.e., DSL Presentation Table[1][2], its internal RID presentation is Table._1_2
//need a converter for:
//                Table[1][2]  <---> Table._1_2

function convertRidToUid(rid){
    if(rid != null && rid.trim().length > 0){
        var ids = rid.split(".");
        var idl = new StringBuffer();
        var last = null;
        for(var i=0; i<ids.length; i++){
            var t = convertRidField(ids[i]);
            if(last != null){
                if(!t.match(/^\[/) || last.match(/\]$/)){
                    idl.append(".");
                }
            }
            idl.append(t);
            last = t;
        }

        return idl.toString();
    }else{
        return "";
    }
}

function convertRidField(field){
    if(field == null || field.trim().length == 0){
        return "";
    }

    var fields = field.split("_");
    var ar = new StringBuffer();
    for(var i=0; i<fields.length; i++){
        var f = fields[i];
        if(f.trim().length > 0){
            if(f.match(/^\d+$/)){
                ar.append("[" + f + "]");
            }else{
                ar.append(f);
            }
        }
    }

    return ar.toString();
}

function WorkflowContext(){
    this.refLocator = null;
    this.domRef = null;
    this.alg = null;
    this.skipNext = false;
    this.context = null;
}

WorkflowContext.prototype.setContext = function(key, val){
    if(this.context == null)
        this.context = new Hashtable();
    this.context.put(key, val);
};

WorkflowContext.prototype.getContext = function(key){
    if(this.context == null)
        return null;
    return this.context.get(key);
};

//Base locator
function BaseLocator(){
    this.loc = null;
}

//composite locator
function CompositeLocator(){    
    this.tag = null;
    this.text = null;
    this.position = null;
    this.direct = false;
    this.header = null;
    this.trailer = null;
    this.attributes = new Hashtable();
}

CompositeLocator.prototype.updateLocator = function(attributes){
     this.attributes = new HashMap();

     if(attributes != null && attributes.size() > 0){
        //neend to unescape attributes for attributes getting from xml
        var esAttributes = new HashMap();
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            var val = escapedCharacterProof(attributes.get(key));
            esAttributes.set(key, val);
        }

        this.text = esAttributes.get(CONSTANTS.TEXT);
        this.position = esAttributes.get(CONSTANTS.POSITION);
        this.header =  esAttributes.get(CONSTANTS.HEADER);
        this.trailer = esAttributes.get(CONSTANTS.TRAILER);

        this.buildLocatorFromAttributes(esAttributes, true);
    }

    return this;
};

CompositeLocator.prototype.isAttributeIncluded = function(attribute){

    if(attribute == CONSTANTS.TEXT && this.text != null && trimString(this.text).length > 0){
        return true;
    }
    if(attribute == CONSTANTS.TRAILER && this.trailer != null && trimString(this.trailer).length > 0){
        return true;
    }
    if(attribute == CONSTANTS.HEADER && this.header != null && trimString(this.header).length > 0){
        return true;
    }

    return (this.attributes.get(attribute) != null);
};

CompositeLocator.prototype.addAttribute = function(key, value) {
    this.attributes.set(key, value);
};

CompositeLocator.prototype.removeAttribute = function(key) {
    this.attributes.remove(key);
};

CompositeLocator.prototype.attributesToString = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            if(keys[i] != CONSTANTS.TEXT && keys[i] != CONSTANTS.POSITION && keys[i] != CONSTANTS.HEADER && keys[i] != CONSTANTS.TRAILER){
                sb.append(", ").append(keys[i]).append(": ").append("\"").append(this.attributes.get(keys[i])).append("\"");
            }
        }
    }

    return sb.toString();
};

CompositeLocator.prototype.descLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(CONSTANTS.TAG).append(": '").append(this.tag).append("'");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.TEXT).append(": '").append(specialCharacterProof(this.text)).append("'");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.POSITION).append(": '").append(this.position).append("'");
            ++count;
        }
        if(this.header != null && trimString(this.header).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.HEADER).append(": '").append(this.header).append("'");
            ++count;
        }
        if(this.trailer != null && trimString(this.trailer).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.TRAILER).append(": '").append(this.trailer).append("'");
            ++count;
        }
        if (this.direct) {
            if(count > 0){
                sb.append(", ");
            }
            sb.append("direct: 'true'");
            ++count;
        }

        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.descAttributes())
        }
    }
    sb.append("]");

    return sb.toString();
};

CompositeLocator.prototype.strLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(CONSTANTS.TAG).append(": ").append("\"").append(this.tag).append("\"");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.TEXT).append(": ").append("\"").append(this.text).append("\"");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.POSITION).append(": ").append("\"").append(this.position).append("\"");
            ++count;
        }
        if(this.header != null && trimString(this.header).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.HEADER).append(": ").append("\"").append(this.header).append("\"");
            ++count;
        }
        if(this.trailer != null && trimString(this.trailer).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(CONSTANTS.TRAILER).append(": ").append("\"").append(this.trailer).append("\"");
            ++count;
        }
        if (this.direct) {
            if(count > 0){
                sb.append(", ");
            }
            sb.append("direct: \"true\"");
            ++count;
        }
        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.attributesToString())
        }

    }
    sb.append("]");

    return sb.toString();
};

CompositeLocator.prototype.descAttributes = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            if(keys[i] != CONSTANTS.TEXT && keys[i] != CONSTANTS.POSITION && keys[i] != CONSTANTS.HEADER && keys[i] != CONSTANTS.TRAILER){
                sb.append(", ").append(keys[i]).append(": '").append(specialCharacterProof(this.attributes.get(keys[i]))).append("'");
            }
        }

    }

    return sb.toString();
};

CompositeLocator.prototype.buildLocator = function(attributes){
    if(attributes != null && attributes.size() > 0){
        this.tag = attributes.get(CONSTANTS.TAG);
        this.text = attributes.get(CONSTANTS.TEXT);
        this.position = attributes.get(CONSTANTS.POSITION);
        var header = attributes.get(CONSTANTS.HEADER);
        if (header != null){
            this.header = header;
        }
        var trailer = attributes.get(CONSTANTS.TRAILER);
        if(trailer != null){
            this.trailer = trailer;
        }

        this.buildLocatorFromAttributes(attributes);
    }

    return this;
};


CompositeLocator.prototype.buildLocatorFromAttributes = function(attributes, overrideignoredattributes) {
    if (attributes != null && attributes.size() > 0) {
        // load ignored attributes from preferences
        var ignoredAttributes = [];
        if (!overrideignoredattributes) {
            var prefIgnoredAttributes = Preferences.getPref("extensions.trump.ignoredattributes");
            if (prefIgnoredAttributes && prefIgnoredAttributes != "") {
                ignoredAttributes = ignoredAttributes.concat(prefIgnoredAttributes.split(";"));
            }
        }

        var keys = attributes.keySet();
        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
//            if(key != CONSTANTS.TAG && key != CONSTANTS.TEXT && key != CONSTANTS.POSITION && key != CONSTANTS.HEADER && key != CONSTANTS.TRAILER){
            if (key != CONSTANTS.TAG && key != CONSTANTS.HEADER && key != CONSTANTS.TRAILER && key != CONSTANTS.TEXT && ignoredAttributes.indexOf(key) == -1) {
                var value = attributes.get(key);
                if (value != null && trimString(value).length > 0) {
                    this.attributes.set(key, value);
                }
            }
        }
    }
};

CompositeLocator.prototype.toJSON = function(){
    var jso = {};
    if(this.header != null){
        jso[CONSTANTS.HEADER] = this.header;
    }
    if(this.trailer != null){
       jso[CONSTANTS.TRAILER] = this.trailer;
    }
    if(this.tag != null){
        jso[CONSTANTS.TAG] = this.tag;
    }
    if(this.text != null){
        jso[CONSTANTS.TEXT] = this.text;
    }
    if(this.position != null){
       jso[CONSTANTS.POSITION] = this.position;
    }
    if(this.direct){
        jso[CONSTANTS.DIRECT] = this.direct;
    }
    if(this.attributes != null && this.attributes.size() > 0){
        var attrs = {};
        var keys = this.attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            attrs[key] = this.attributes.get(key);
        }
        jso[CONSTANTS.ATTRIBUTES] = attrs;
    }

    return jso;
};