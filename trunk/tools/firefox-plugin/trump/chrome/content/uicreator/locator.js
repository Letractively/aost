function Locator(){
    this.constants = {
        TAG : "tag",
        TEXT : "text",
        HEADER : "header",
        TRAILER: "trailer",
        POSITION: "position"
    };
    this.header = null;
    this.tag = null;
    this.text = null;
    this.trailer = null;
    this.position = null;
    this.attributes = new HashMap();
    this.direct = false;
}

Locator.prototype.buildLocator = function(attributes){
    if(attributes != null && attributes.size() > 0){
        this.tag = attributes.get(this.constants.TAG);
        this.text = attributes.get(this.constants.TEXT);
        this.position = attributes.get(this.constants.POSITION);

        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            if(key != this.constants.TAG && key != this.constants.TEXT && key != this.constants.POSITION && key != this.constants.HEADER && key != this.constants.TRAILER){
                var value = attributes.get(key);
                if(value != null && trimString(value).length >0 ){
                    this.attributes.set(key, value);
                }
            }
        }

    }

    return this;
}

Locator.prototype.isAttributeIncluded = function(attribute){

    return (this.attributes.get(attribute) != null);
}

Locator.prototype.addAttribute = function(key, value) {
    this.attributes.set(key, value);
}

Locator.prototype.removeAttribute = function(key) {
    this.attributes.remove(key);
}

Locator.prototype.attributesToString = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            sb.append(", ").append(keys[i]).append(": ").append("\"").append(this.attributes.get(keys[i])).append("\"");
        }
    }

    return sb.toString();
}

Locator.prototype.descAttributes = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            sb.append(", ").append(keys[i]).append(": '").append(this.specialCharacterProof(this.attributes.get(keys[i]))).append("'");
        }
    }

    return sb.toString();
}

Locator.prototype.strLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(this.constants.TAG).append(": ").append("\"").append(this.tag).append("\"");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TEXT).append(": ").append("\"").append(this.text).append("\"");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.POSITION).append(": ").append("\"").append(this.position).append("\"");
            ++count;
        }
        if(this.header != null && this.header.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": ").append("\"").append(this.header).append("\"");
            ++count;
        }
        if(this.trailer != null && this.trailer.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": ").append("\"").append(this.header).append("\"");
            ++count;
        }
        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.attributesToString())
        }
    }
    sb.append("]")

    return sb.toString();
}

Locator.prototype.descLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(this.constants.TAG).append(": '").append(this.tag).append("'");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TEXT).append(": '").append(this.specialCharacterProof(this.text)).append("'");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.POSITION).append(": '").append(this.position).append("'");
            ++count;
        }
        if(this.header != null && this.header.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": '").append(this.header).append("'");
            ++count;
        }
        if(this.trailer != null && this.trailer.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": '").append(this.header).append("'");
            ++count;
        }
        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.descAttributes())
        }
    }
    sb.append("]")

    return sb.toString();
}

Locator.prototype.specialCharacterProof = function(str){
    if(this.containSpecialCharacters(str)){
        return this.escapeString(str);
    }

    return str;
}

Locator.prototype.escapeString = function(str){
//    return "<![CDATA[" + str + "]]>";
    var newstr = str;

    if(newstr.indexOf("&") != -1){
        newstr = newstr.replace(/\&/g, "&amp;");
//        alert("Replace & in " + str + " as " + newstr);
    }

    if(newstr.indexOf("<") != -1){
        newstr = newstr.replace(/\</g, "&lt;");
    }

    if(newstr.indexOf(">") != -1){
        newstr = newstr.replace(/\>/g, "&gt;");
    }

    if(newstr.indexOf("\"") != -1){
        newstr = newstr.replace(/\"/g, "&quot;");
    }
    
    return newstr;
}

//Check if the str contains special characters such as '&', '<',  '>', or '\"'
Locator.prototype.containSpecialCharacters = function(str){

    if(str == null || str.length == 0){
        return false;
    }

    //check if str contains special characters
    if(str.indexOf("&") == -1 && str.indexOf("<") == -1 && str.indexOf(">") == -1 && str.indexOf("\"") == -1){
        return false;
    }

    return true;
}

Locator.prototype.containEscapedCharacters = function(str){

    if(str == null || str.length == 0){
        return false;
    }

    //check if str contains special characters
    if(str.indexOf("&amp;") == -1 && str.indexOf("&lt;") == -1 && str.indexOf("&gt;") == -1 && str.indexOf("&quot;") == -1){
        return false;
    }

    return true;
}

Locator.prototype.unescapeString = function(str){
    var newstr = str;

    if(newstr.indexOf("&") != -1){
        newstr = newstr.replace(/\&amp;/g, "&");
    }

    if(newstr.indexOf("<") != -1){
        newstr.replace(/\&lt;</g, "<");
    }

    if(newstr.indexOf(">") != -1){
        newstr.replace(/\&gt/g, ">");
    }

    if(newstr.indexOf("\"") != -1){
        newstr.replace(/\&quot;"/g, "\"");
    }

    return newstr;
}

Locator.prototype.escapedCharacterProof = function(str){
    if(this.containEscapedCharacters(str)){
        return this.unescapeString(str);
    }

    return str;
}

Locator.prototype.setHeader = function(header){
    this.header = header;
}

Locator.prototype.setTag = function(tag){
    this.tag = tag;
}

Locator.prototype.setText = function(text){
    this.text = text;
}

Locator.prototype.setTrailer = function(trailer){
    this.trailer = trailer;
}

Locator.prototype.setPosition = function(pos){
    this.position = pos;
}