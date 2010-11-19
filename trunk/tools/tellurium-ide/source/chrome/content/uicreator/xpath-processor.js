function XPath(){
    this.xpath = null;
    this.node = null;
}

function XPathBuilder(){

    this.constants = {
        CHILD : "child",
        DESCENDANT : "descendant",
        DESCENDANT_OR_SELF : "descendant-or-self",
        SELF : "self",
        FOLLOWING : "following",
        FOLLOWING_SIBLING : "following-sibling",
        PARENT : "parent",
        DESCENDANT_OR_SELF_PATH : "/descendant-or-self::",
        CHILD_PATH : "/child::",
        DESCENDANT_PREFIX : "descendant::",
        CHILD_PREFIX : "child::",
        MATCH_ALL : "*",
        CONTAIN_PREFIX : "%%",
        TEXT : "text",
        POSITION: "position"
    };

}

XPathBuilder.prototype.buildPosition = function(position){
    if(position < 1){
        return "";
    }

    return "position()=" + position;
};

XPathBuilder.prototype.startsWith = function(str, pattern) {
    return str.indexOf(pattern) === 0;
};

XPathBuilder.prototype.buildText = function(value){
    if(value == null || trimString(value).length <= 0){
        return "";
    }

    var trimed = trimString(value);
    //start with "%%"
    if(trimed.indexOf(this.constants.CONTAIN_PREFIX) == 0){
        var actual = value.substring(2, value.length-1);
        return "contains(text(), \"" + actual + "\")";
    }else{
        return "normalize-space(text())=normalize-space(\"" + trimed + "\")";
    }
};

XPathBuilder.prototype.buildAttribute = function(name, value){
    if(name == null || trimString(name).length <= 0)
        return "";

    if(value == null || trimString(value).length <= 0){
        return "@" + name;
    }

    if(value.indexOf(this.constants.CONTAIN_PREFIX) == 0){
        var actual = value.substring(2, value.length);
        return "contains(@" + name + ", \"" + actual + "\")";
    }else{
        return "@" + name + "=\"" + value + "\"";
    }
};

XPathBuilder.prototype.buildXPathWithPrefix = function(prefix, tag, text, position, attributes, groupattrs){
    var sb = new StringBuffer();
    sb.append(prefix);

    if(tag != null && tag.length > 0){
        sb.append(tag);
    }else{
        sb.append(this.constants.MATCH_ALL);
    }

    var list = new Array();
    if(groupattrs != null && groupattrs.length > 0){
        for(var j=0; j<groupattrs.length; j++){
            list.push(groupattrs[j]);
        }
    }

    var vText = this.buildText(text);
    if(vText.length > 0){
        list.push(vText);
    }

    var vPosition = this.buildPosition(position);
    if(vPosition.length > 0){
        list.push(vPosition);
    }

    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            var value = attributes.get(key);
            if(key != this.constants.TEXT && key != this.constants.POSITION){
                var vAttr = this.buildAttribute(key, value);
                if(vAttr.length > 0){
                    list.push(vAttr);
                }
            }
        }
    }

    if(list.length > 0){
        var attri = list.join(" and ");
        sb.append("[").append(attri).append("]")
    }

    return sb.toString();
};

XPathBuilder.prototype.buildDescendantXPath = function(tag, text, position, attributes){
    if(position != null && position >= 0)
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_PREFIX, tag, text, position, attributes, null);

    return  this.buildXPathWithPrefix(this.constants.DESCENDANT_PREFIX, tag, text, -1 , attributes, null);
};

XPathBuilder.prototype.buildChildXPath = function(tag, text, position, attributes){
    if(position != null && position >= 0)
        return this.buildXPathWithPrefix(this.constants.CHILD_PREFIX, tag, text, position, attributes, null);

    return  this.buildXPathWithPrefix(this.constants.CHILD_PREFIX, tag, text, -1 , attributes, null);
};

XPathBuilder.prototype.internBuildXPath = function(tag, text, position, direct, attributes, groupattrs){
    if(direct)
        return this.buildXPathWithPrefix(this.constants.CHILD_PATH, tag, text, position, attributes, groupattrs);
    else
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_OR_SELF_PATH, tag, text, position, attributes, groupattrs);
};

XPathBuilder.prototype.buildGroupXPath = function(tag, text, position, direct, attributes, groupattrs){
    if(position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, direct, attributes, groupattrs);

    return  this.internBuildXPath(tag, text, -1 , direct, attributes, groupattrs);
};

XPathBuilder.prototype.buildXPath = function(tag, text, position, attributes) {
    if (position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, false, attributes, null);

    return  this.internBuildXPath(tag, text, -1, false, attributes, null);
};

XPathBuilder.prototype.buildOptionalXPath = function(tag, text, position, direct, attributes) {
    if (position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, direct, attributes, null);

    return  this.internBuildXPath(tag, text, -1, direct, attributes, null);
};

XPathBuilder.prototype.buildOptionalXPathVHeader = function(tag, text, position, direct, attributes, header) {
    if (position != null && position >= 0)
        return this.internBuildXPathVHeader(tag, text, position, direct, attributes, null, header);

    return  this.internBuildXPathVHeader(tag, text, -1, direct, attributes, null, header);
};

XPathBuilder.prototype.internBuildXPathVHeader = function(tag, text, position, direct, attributes, groupattrs, header){
    var appendheader = "";

    if(header != null && trimString(header).length > 0){
        var inx = header.indexOf("/");
        //need to remove the "/"
        appendheader = header.substring(inx+1, header.length) + "/";
//        logger.debug("Appended header " + appendheader);
    }

    if(direct){
        return this.buildXPathWithPrefix(this.constants.CHILD_PATH + appendheader, tag, text, position, attributes, groupattrs);
    }else{
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_OR_SELF_PATH + appendheader, tag, text, position, attributes, groupattrs);
    }
};

XPathBuilder.prototype.buildGroupXPathVHeader = function(tag, text, position, direct, attributes, groupattrs, header){
    if(position != null && position >= 0)
        return this.internBuildXPathVHeader(tag, text, position, direct, attributes, groupattrs, header);

    return  this.internBuildXPathVHeader(tag, text, -1 , direct, attributes, groupattrs, header);
};

function XPathComparator(){

}

XPathComparator.prototype.compare = function(firstXPath, secondXPath){
    return secondXPath.xpath.length - firstXPath.xpath.length
};

function XPathDom(){

}


function XPathMatcher(){
    this.DELIMITER = "/";
}

XPathMatcher.prototype.match = function(src, dst){
    if(src == null || dst == null)
            return null;

        //try to find the shorter one from the two xpaths
        var minlen = src.split(this.DELIMITER).length;
        var longlen = dst.split(this.DELIMITER).length;

        var shorter = src;
        var longer = dst;

        if(minlen > longlen){
            minlen = longlen;
            shorter = dst;
            longer = src;
        }

        var shortsplit = shorter.split(this.DELIMITER);
        var longsplit = longer.split(this.DELIMITER);

        var match = new StringBuffer();

        for(var i=0; i<minlen; i++){
            if(shortsplit[i] == longsplit[i]){
                //match
                if(i>0){
                    match.append(this.DELIMITER);
                }
                match.append(shortsplit[i]);
            }else
                //not match
                break;
        }

        return match.toString();
};

XPathMatcher.prototype.remainingXPath = function(original, prefix){
    if(original == null || prefix == null)
            return original;
    if(original.indexOf(prefix) != -1){
        return original.substring(prefix.length);
    }
};

function XPathProcessor(){
    //delimiter for different nodes
    this.DELIMITER = "/";
    //separator for tag and attributes
    this.SEPARATOR = "[";
    //the separator for xpath prefix such as
    //  /default:html/default:body[@class='homepage']/default:div[@id='container']/default:div[@id='header']/default:ul/default:li[@id='menu_projects']/default:a
    this.PREFIX = ":";
}

XPathProcessor.prototype.splitXPath = function(xpath){
    var result = [];

    if(xpath !== null){
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
    if(str !== null){
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
    var tags = [];
    if(splited !== null && splited.length > 0){
        for(var i=0; i<splited.length; i++){
            var tag = this.getTag(splited[i]);
            if(tag !== null){
                tags.push(tag);
            }
        }
    }

    return tags;
};

XPathProcessor.prototype.getSubXPath = function(xpath, inx){
    var subxp = new StringBuffer();
    if(xpath !== null){
        var splited = this.splitXPath(xpath);
        if(inx > splited.length-1){
            //change to warning/error to log later
            logger.warn("XPath " + xpath + "Index " + inx + " out of bound");
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
    if(xpath !== null){
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
    if(xpath !== null){
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
    if(tagList !== null && tag !== null){
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
    if(lst === null || lst.length <= 1){
        return lst;
    }

    var result = [];
    for(var i=lst.length-1 ; i>=0; i--){
        result.push(lst[i]);
    }

    return result;
};


XPathProcessor.prototype.startWith = function(xpath, prefix) {
    if (xpath === null || prefix === null || xpath.length < prefix.length) {
        return false;
    }

    return xpath.substring(0, prefix.length - 1) === prefix;
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
    if(str !== null){
        //check the pattern such as "//a/td[13]/" and "table[@class='st' and position()=3]"
        var mcs = str.match(/(position\(\)=[\d]+|\[[\d]+\])/);
        if(mcs !== null){
            return new String(mcs).match(/[\d]+/);
        }
    }

    return null;
};

XPathProcessor.prototype.checkPositionForlastXPath = function(xpath){
    var parts = this.splitXPath(xpath);
    if(parts !== null || parts.length > 0){
        var last = parts[parts.length-1];
        return this.checkPosition(last);
    }

    return null;
};