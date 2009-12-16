function XPathProcessor(){
    //delimiter for different nodes
    this.DELIMITER = "/";
    //separator for tag and attributes
    this.SEPARATOR = "[";
    //the separator for xpath prefix such as
    //  /default:html/default:body[@class='homepage']/default:div[@id='container']/default:div[@id='header']/default:ul/default:li[@id='menu_projects']/default:a
    this.PREFIX = ":";
};

XPathProcessor.prototype.splitXPath = function(xpath){
    var result = new Array();

    if(xpath != null){
        var splited = xpath.split(this.DELIMITER);
        //need to remove the first empty string
        for(var i=0; i<splited.length; i++){
            if(trimString(splited[i]).length > 0){
                result.push(trimString(splited[i]));
            }
        }
    }

    return result;
};

XPathProcessor.prototype.getTag = function(str){
    var tag = null;
    if(str != null){
        var lst = str.split(this.SEPARATOR);
        tag = lst[0];
        var inx = tag.indexOf(this.PREFIX);
        if(inx != -1){
            //We have name space or prefix in the tag
            tag = tag.substring(inx + 1, tag.length);
        }
    }

    return tag;
};

XPathProcessor.prototype.getTags = function(xpath){
    var splited = this.splitXPath(xpath);
    var tags = new Array();
    if(splited != null && splited.length > 0){
        for(var i=0; i<splited.length; i++){
            var tag = this.getTag(splited[i]);
            if(tag != null){
                tags.push(tag);
            }
        }
    }

    return tags;
};

XPathProcessor.prototype.getSubXPath = function(xpath, inx){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);
        if(inx > splited.length-1){
            //change to warning/error to log later
            inx = splited.length-1;
        }

        for(var i=0; i<=inx; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i]);
        }
    }

    return subxp.toString();
};

XPathProcessor.prototype.getLastXPath = function(xpath, inx){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);
        if(inx > splited.length-1){
            //change to warning/error to log later
//            logger.warn("XPath " + xpath + "Index " + inx + " out of bound");
//            inx = splited.length-1;
            return null;
        }

        for(var i=inx; i<splited.length; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i]);
        }
    }

    return subxp.toString();
};

//remove the last portion from the XPath
XPathProcessor.prototype.popXPath = function(xpath){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);

        for(var i=0; i<splited.length-1; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i]);
        }
    }

    return subxp.toString();
};

XPathProcessor.prototype.findTagIndex = function(tagList, tag){
    var inx = -1;
    if(tagList != null && tag != null){
        for(var i=0; i<tagList.length; i++){
            if(tag == tagList[i]){
                inx = i;
                break;
            }
        }
    }

    return inx;
};

XPathProcessor.prototype.reverseList = function (lst){
    if(lst == null || lst.length <= 1){
        return lst;
    }

    var result = new Array();
    for(var i=lst.length-1 ; i>=0; i--){
        result.push(lst[i]);
    }

    return result;
};

XPathProcessor.prototype.startWith = function(xpath, prefix){
    if(xpath == null || prefix == null || xpath.length < prefix.length){
        return false;
    }

    return xpath.substring(0, prefix.length - 1) == prefix;
};

XPathProcessor.prototype.checkXPathCount = function(doc, xpath) {
//    var nodesSnapshot = document.evaluate(xpath, document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
//    return nodesSnapshot.snapshotLength;

    var xpct = "count(" + xpath + ")";
    var result = doc.evaluate(xpct,  doc, null, XPathResult.NUMBER_TYPE, null);
//    logger.debug("Get XPath evalution result " + result.numberValue + " for xpath " + xpath);

    return result.numberValue;
};

XPathProcessor.prototype.checkPosition = function(str){
    if(str != null){
        //check the pattern such as "//a/td[13]/" and "table[@class='st' and position()=3]"
        var mcs = str.match(/(position\(\)=[\d]+|\[[\d]+\])/);
        if(mcs != null){
            return new String(mcs).match(/[\d]+/);
        }
    }

    return null;
};

XPathProcessor.prototype.checkPositionForlastXPath = function(xpath){
    var parts = this.splitXPath(xpath);
    if(parts != null || parts.length > 0){
        var last = parts[parts.length-1];
        return this.checkPosition(last);
    }

    return null;
};

function JQueryBuilder(){
    this.TEXT_PSEUDO_CLASS = ":te_text";
    this.ATTR_BLACK_LIST = ['action'];
    this.MATCH_ALL = "*";
    this.CONTAINS_FILTER = ":contains";
    this.NOT_PREFIX = "!";
    this.START_PREFIX = "^";
    this.END_PREFIX = "\$";
    this.ANY_PREFIX = "*";
    this.HAS = ":has";
    this.SELECTOR_SEPARATOR = ", ";
    this.SPACE = " ";
    this.CHILD_SEPARATOR = " > ";
    this.DESCENDANT_SEPARATOR = " ";
    this.NEXT_SEPARATOR = " + ";
    this.SIBLING_SEPARATOR = " ~ ";
    this.ID_SELECTOR_PREFIX = "#";
    this.CLASS_SELECTOR_PREFIX = ".";
    this.SINGLE_QUOTE = "'";
    this.TITLE = "title";
    this.ID = "id";
    this.NAME = "name";
    this.CLASS = "class";
    this.CONTAIN_PREFIX = "%%";

    this.xpathProcessor = new XPathProcessor();
};

JQueryBuilder.prototype.inBlackList = function(attr){
    return this.ATTR_BLACK_LIST.indexOf(attr) != -1;
};

JQueryBuilder.prototype.checkTag = function(tag){
    if(tag != null && trimString(tag).length > 0){
      return tag;
    }

    return this.MATCH_ALL;
};

//TODO: We ignore single quote here, need to re-visit this piece
JQueryBuilder.prototype.containText = function(text){

    return this.CONTAINS_FILTER + "(" + text + ")";
};

JQueryBuilder.prototype.attrText = function(text){

    //need the following custom selector ":te_text()" support
    return this.TEXT_PSEUDO_CLASS + "(" + text + ")";
};

  //starts from zero
JQueryBuilder.prototype.attrPosition = function(index){
    return ":eq(" + index + ")";
};

JQueryBuilder.prototype.attrId = function(id){
    if(id == null)
       return "[id]";

    if (id.startsWith(this.START_PREFIX)) {
      return "[id^=" + id.substring(1) + "]";
    } else if (id.startsWith(this.END_PREFIX)) {
      return "[id\$=" + id.substring(1) + "]";
    } else if (id.startsWith(this.ANY_PREFIX)) {
      return "[id*=" + id.substring(1) + "]";
    } else if (id.startsWith(this.NOT_PREFIX)) {
      return "[id!=" + id.substring(1) + "]";
    } else {
      //should never come here
      return "[id]";
    }
};

JQueryBuilder.prototype.attrClass = function(clazz) {
    //need to consider the multiple-class syntax, for example, $('.myclass.otherclass')
    //As a CSS selector, the multiple-class syntax of example 3 is supported by all modern
    //web browsers, but not by Internet Explorer versions 6 and below
    if (clazz != null && trimString(clazz).length > 0) {
        var parts = clazz.split(this.SPACE);
        if (parts.length == 1) {
            //only only 1 class

            return this.attrSingleClass(parts[0]);
        } else {

            var sb = new StringBuffer();
            for (var part in parts) {
                sb.append(this.attrSingleClass(part));
            }

            return sb.toString();
        }
    }

    return "[class]";
};

JQueryBuilder.prototype.attrSingleClass = function(clazz) {

    if (clazz == null || trimString(clazz).length == 0) {
        return "[class]";
    }

    if (clazz.startsWith(this.START_PREFIX)) {
        return "[class^=" + clazz.substring(1) + "]";
    } else if (clazz.startsWith(this.END_PREFIX)) {
        return "[class\$=" + clazz.substring(1) + "]";
    } else if (clazz.startsWith(this.ANY_PREFIX)) {
        return "[class*=" + clazz.substring(1) + "]";
    } else if (clazz.startsWith(this.NOT_PREFIX)) {
        return "[class!=" + clazz.substring(1) + "]";
    } else {
        return this.CLASS_SELECTOR_PREFIX + clazz;
    }
};

JQueryBuilder.prototype.attrPairs = function(attr, val) {
    if (val == null || trimString(val).length() == 0) {
        return "[" + attr + "]";
    }

    if (val.startsWith(this.START_PREFIX)) {
        return "[" + attr + "^=" + val.substring(1) + "]";
    } else if (val.startsWith(this.END_PREFIX)) {
        return "[" + attr + "\$=" + val.substring(1) + "]";
    } else if (val.startsWith(this.ANY_PREFIX)) {
        return "[" + attr + "*=" + val.substring(1) + "]";
    } else if (val.startsWith(this.NOT_PREFIX)) {
        return "[" + attr + "!=" + val.substring(1) + "]";
    } else {
        return "[" + attr + "=" + val + "]";
    }
};

//not really working if we convert String multiple times
JQueryBuilder.prototype.guardQuote = function(val) {
    if (val != null && val.indexOf(this.SINGLE_QUOTE) > 0) {
        return val.replace(/'/, "\\\'");
    }

    return val;
};

JQueryBuilder.prototype.includeSingleQuote = function(val) {

    return val != null && val.indexOf(this.SINGLE_QUOTE) > 0;
};


JQueryBuilder.prototype.buildCssSelector = function(tag, text, position, direct, attributes) {
    var sb = new StringBuffer(64);
    if (direct) {
        sb.append(this.CHILD_SEPARATOR);
    } else {
        sb.append(this.DESCENDANT_SEPARATOR);
    }

    //put the tag name first
    sb.append(this.checkTag(tag));
    if (attributes != null && attributes.size() > 0) {
        var id = attributes.get(this.ID);
        if (id != null && trimString(id).length() > 0) {
            id = trimString(id);
            if (id.startsWith(this.START_PREFIX) || id.startsWith(this.END_PREFIX) || id.startsWith(this.ANY_PREFIX) || id.startsWith(this.NOT_PREFIX)) {
                sb.append(this.attrId(id));
            } else {
                //should not add other attributes if the ID is presented since jQuery will only select the first element for
                // the ID and additional attributes will not help at all
                //also since id is unique, we do not need to include tag here
                return " #" + id;
            }
        }

        var clazz = attributes.get(this.CLASS);
        if (clazz != null) {
            clazz = trimString(clazz);
            sb.append(this.attrClass(clazz));
        }

        var keys = attributes.keySet();
        for (var key in keys) {
            var val = attributes.get(key);
            if ((key != this.ID) && (key != this.CLASS) && (!this.inBlackList(key))) {
                sb.append(this.attrPairs(key, val));
            }
        }
    }

    if (text != null && trimString(text).length > 0) {
        if (text.startsWith(this.CONTAIN_PREFIX)) {
            sb.append(this.containText(text.substring(2)));
        } else if (text.startsWith(this.START_PREFIX) || text.startsWith(this.END_PREFIX) || text.startsWith(this.ANY_PREFIX)) {
            //TODO: need to refact this to use start, end, any partial match
            sb.append(this.containText(text.substring(1)));
        } else if (text.startsWith(this.NOT_PREFIX)) {
            sb.append(":not(" + this.containText(text.substring(1)) + ")");
        } else {
            sb.append(this.attrText(text));
        }
    }

    if (position != null && trimString(position).length > 0) {
        var index = parseInt(position);
        if (index > 0) {
            sb.append(this.attrPosition(index - 1));
        }
    }

    return sb.toString();
};

JQueryBuilder.prototype.convHeader = function(header) {
    if (header != null && trimString(header).length > 0) {
        var xps = this.xpathProcessor.splitXPath(header);
        var sb = new StringBuffer(64);

        var tag = this.xpathProcessor.getTag(xps[0]);

        sb.append(this.DESCENDANT_SEPARATOR).append(tag);
        for (var i = 1; i < xps.length; i++) {
            tag = this.xpathProcessor.getTag(xps[i]);
            sb.append(this.CHILD_SEPARATOR).append(tag);
        }

        return sb.toString();
    }

    return "";
};

JQueryBuilder.prototype.convTrailer = function(trailer) {
    if (trailer != null && trimString(trailer).length > 0) {
        var xps = this.xpathProcessor.splitXPath(trailer);
        var sb = new StringBuffer(64);

        var tag = this.xpathProcessor.getTag(xps[0]);

        sb.append(this.CHILD_SEPARATOR).append(tag);
        for (var i = 1; i < xps.length; i++) {
            tag = this.xpathProcessor.getTag(xps[i]);
            sb.append(this.CHILD_SEPARATOR).append(tag);
        }

        return sb.toString();
    }

    return "";
};

JQueryBuilder.prototype.isPartial = function(val){

    return val != null && (val.startsWith(this.START_PREFIX) || val.startsWith(this.END_PREFIX)
            || val.startsWith(this.ANY_PREFIX) || val.startsWith(this.NOT_PREFIX)
            || val.startsWith(this.CONTAIN_PREFIX));
};

JQueryBuilder.prototype.buildId = function(id){
    if(id.startsWith("^")){
        return "[id^=" + id.substring(1) + "]";
    }else if(id.startsWith("$")){
        return "[id$=" + id.substring(1) + "]";
    }else if(id.startsWith("*")){
        return "[id*=" + id.substring(1) + "]";
    }else if(id.startsWith("!")){
        return "[id!=" + id.substring(1) + "]";
    }else{
        return "#" + id;
    }
};

JQueryBuilder.prototype.buildSingleClass = function(clazz){
    if(clazz.startsWith("^")){
        return "[class^=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("$")){
        return "[class$=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("*")){
        return "[class*=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("!")){
        return "[class!=" + clazz.substring(1) + "]";
    }else{
        return "." + clazz;
    }
};

JQueryBuilder.prototype.buildClass = function(clazz){
    if (clazz != null && trimString(clazz).length > 0) {
        var parts = clazz.split(this.SPACE);
        if (parts.length == 1) {
            //only only 1 class

            return this.buildSingleClass(parts[0]);
        } else {

            var sb = new StringBuffer();
            for (var part in parts) {
                sb.append(this.buildSingleClass(part));
            }

            return sb.toString();
        }
    }

    return "[class]";
};

JQueryBuilder.prototype.buildText = function(text){
    if (text != null && trimString(text).length > 0) {
        if (text.startsWith(this.CONTAIN_PREFIX)) {
            return this.containText(text.substring(2));
        } else if (text.startsWith(this.START_PREFIX) || text.startsWith(this.END_PREFIX) || text.startsWith(this.ANY_PREFIX)) {
            //TODO: need to refact this to use start, end, any partial match
            return this.containText(text.substring(1));
        } else if (text.startsWith(this.NOT_PREFIX)) {
            return ":not(" + this.containText(text.substring(1)) + ")";
        } else {
            return this.attrText(text);
        }
    }

    return "";
};

JQueryBuilder.prototype.buildAttribute = function(attr, val) {
    if (val == null || trimString(val).length == 0) {
        return "[" + attr + "]";
    }

    if (val.startsWith(this.START_PREFIX)) {
        return "[" + attr + "^=" + val.substring(1) + "]";
    } else if (val.startsWith(this.END_PREFIX)) {
        return "[" + attr + "$=" + val.substring(1) + "]";
    } else if (val.startsWith(this.ANY_PREFIX)) {
        return "[" + attr + "*=" + val.substring(1) + "]";
    } else if (val.startsWith(this.NOT_PREFIX)) {
        return "[" + attr + "!=" + val.substring(1) + "]";
    } else {
        return "[" + attr + "=" + val + "]";
    }
};

JQueryBuilder.prototype.buildStyle = function(style){
    if(style == null || trimString(style).length == 0){
      return "[style]";
    }

    return ":styles(" + style + ")";
};

JQueryBuilder.prototype.buildSelector = function(attr, val){
    if(attr == "id"){
        return this.buildId(val);
    }else if(attr == "text"){
        return this.buildText(val);
    }else if(attr == "class"){
        return this.buildClass(val);
    }else if(attr == "style"){
        return this.buildStyle(val);
    }else{
        return this.buildAttribute(attr, val);
    }
};

/*

function jQueryBuilder(){
    this.ATTR_BLACK_LIST = ['action'];
    this.TEXT_PSEUDO_CLASS = ":te_text";
    this.MATCH_ALL = "*";
    this.CONTAINS_FILTER = ":contains";
    this.NOT_PREFIX = "!";
    this.START_PREFIX = "^";
    this.END_PREFIX = "$";
    this.ANY_PREFIX = "*";
    this.HAS = ":has";
    this.SELECTOR_SEPARATOR = ", ";
    this.SPACE = " ";
    this.CHILD_SEPARATOR = " > ";
    this.DESCENDANT_SEPARATOR = " ";
    this.NEXT_SEPARATOR = " + ";
    this.SIBLING_SEPARATOR = " ~ ";
    this.ID_SELECTOR_PREFIX = "#";
    this.CLASS_SELECTOR_PREFIX = ".";
    this.SINGLE_QUOTE = "'";
    this.TITLE = "title";
    this.ID = "id";
    this.NAME = "name";
    this.CLASS = "class";
    this.CONTAIN_PREFIX = "%%";
};

jQueryBuilder.prototype.inBlackList = function(attr){
    return this.ATTR_BLACK_LIST.indexOf(attr) != -1;
};

jQueryBuilder.prototype.isPartial = function(val){

    return val != null && (val.startsWith(this.START_PREFIX) || val.startsWith(this.END_PREFIX)
            || val.startsWith(this.ANY_PREFIX) || val.startsWith(this.NOT_PREFIX)
            || val.startsWith(this.CONTAIN_PREFIX));
};

jQueryBuilder.prototype.buildId = function(id){
    if(id.startsWith("^")){
        return "[id^=" + id.substring(1) + "]";
    }else if(id.startsWith("$")){
        return "[id$=" + id.substring(1) + "]";
    }else if(id.startsWith("*")){
        return "[id*=" + id.substring(1) + "]";
    }else if(id.startsWith("!")){
        return "[id!=" + id.substring(1) + "]";
    }else{
        return "#" + id;
    }
};

jQueryBuilder.prototype.buildSingleClass = function(clazz){
    if(clazz.startsWith("^")){
        return "[class^=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("$")){
        return "[class$=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("*")){
        return "[class*=" + clazz.substring(1) + "]";
    }else if(clazz.startsWith("!")){
        return "[class!=" + clazz.substring(1) + "]";
    }else{
        return "." + clazz;
    }
};

jQueryBuilder.prototype.buildClass = function(clazz){
    if (clazz != null && trimString(clazz).length > 0) {
        var parts = clazz.split(this.SPACE);
        if (parts.length == 1) {
            //only only 1 class

            return this.buildSingleClass(parts[0]);
        } else {

            var sb = new StringBuffer();
            for (var part in parts) {
                sb.append(this.buildSingleClass(part));
            }

            return sb.toString();
        }
    }

    return "[class]";
};

jQueryBuilder.prototype.containText = function(text){

    return this.CONTAINS_FILTER + "(" + text + ")";
};

jQueryBuilder.prototype.attrText = function(text){

    //need the following custom selector ":te_text()" support
    return this.TEXT_PSEUDO_CLASS + "(" + text + ")";
};

jQueryBuilder.prototype.buildText = function(text){
    if (text != null && trimString(text).length > 0) {
        if (text.startsWith(this.CONTAIN_PREFIX)) {
            return this.containText(text.substring(2));
        } else if (text.startsWith(this.START_PREFIX) || text.startsWith(this.END_PREFIX) || text.startsWith(this.ANY_PREFIX)) {
            //TODO: need to refact this to use start, end, any partial match
            return this.containText(text.substring(1));
        } else if (text.startsWith(this.NOT_PREFIX)) {
            return ":not(" + this.containText(text.substring(1)) + ")";
        } else {
            return this.attrText(text);
        }
    }

    return "";
};

jQueryBuilder.prototype.includeSingleQuote = function(val) {

    return val != null && val.indexOf(this.SINGLE_QUOTE) > 0;
};

jQueryBuilder.prototype.buildAttribute = function(attr, val) {
    if (val == null || trimString(val).length == 0) {
        return "[" + attr + "]";
    }

    if (val.startsWith(this.START_PREFIX)) {
        return "[" + attr + "^=" + val.substring(1) + "]";
    } else if (val.startsWith(this.END_PREFIX)) {
        return "[" + attr + "$=" + val.substring(1) + "]";
    } else if (val.startsWith(this.ANY_PREFIX)) {
        return "[" + attr + "*=" + val.substring(1) + "]";
    } else if (val.startsWith(this.NOT_PREFIX)) {
        return "[" + attr + "!=" + val.substring(1) + "]";
    } else {
        return "[" + attr + "=" + val + "]";
    }
};

jQueryBuilder.prototype.buildStyle = function(style){
    if(style == null || trimString(style).length == 0){
      return "[style]";
    }

    return ":styles(" + style + ")";
};

jQueryBuilder.prototype.buildSelector = function(attr, val){
    if(attr == "id"){
        return this.buildId(val);
    }else if(attr == "text"){
        return this.buildText(val);
    }else if(attr == "class"){
        return this.buildClass(val);
    }else if(attr == "style"){
        return this.buildStyle(val);
    }else{
        return this.buildAttribute(attr, val);
    }
};
        

*/