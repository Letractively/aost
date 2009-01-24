

function generateXPath(node){
    //log('default:'+aDefaultNS);
    return walkUp(node,0,99, null, findDefautNS(node), prepareKwds());
}

function walkUp(node, depth, maxDepth, aSentinel, aDefaultNS, kwds)
{
    //log(depth+" node:"+node.nodeName +" aSentinel:"+aSentinel.nodeName);
    var str = "";
    if(!node) return "";
    if(node==aSentinel) return ".";
    if((node.parentNode) && (depth < maxDepth)) {
        str += walkUp(node.parentNode, depth + 1, maxDepth, aSentinel, aDefaultNS, kwds);
    }
    //log(node+'  '+node.nodeName +'  type:'+node.nodeType+ ' exp:'+Node.ELEMENT_NODE);
    switch (node.nodeType) {
        case Node.ELEMENT_NODE:{
                var nname = node.localName;
                var conditions = [];
                var hasid = false;
                if (kwds['showClass'] && node.hasAttribute('class')) conditions.push("@class='"+node.getAttribute('class')+"'");
                if (kwds['showId'] && node.hasAttribute('id')) {
                    conditions.push("@id='"+node.getAttribute('id')+"'");
                    hasid = true;
                }

                //not identified by id?
                if(!hasid){
                    var index = siblingIndex(node);
                    //more than one sibling?
                    if (index) {
                        //are there also other conditions?
                        if (conditions.length>0) conditions.push('position()='+index);
                        else conditions.push(index);
                    }

                }
                if (kwds['showNS']){
                    if(node.prefix) nname=node.prefix+":"+nname;
                    else if (aDefaultNS) nname="default:"+nname;
                }
                if (kwds['toLowercase']) nname=nname.toLowerCase();
                str += "/"+nname;

                if(conditions.length>0){
                    str+="[";
                    for(var i=0;i<conditions.length; i++){
                        if (i>0) str+=' and ';
                        str+=conditions[i];
                    }
                    str+="]";
                }
                break;
            }
        case Node.DOCUMENT_NODE:{
            break;
        }
        case Node.TEXT_NODE:{
            //str='string('+str+')';
            str+='/text()';
            var index = siblingIndex(node);
            if (index) str+="["+index+"]";
            break;
        }

    }
    return str;
}

// gets index of aNode (relative to other same-tag siblings)
// first position = 1; returns null if the component is the only one
function siblingIndex(aNode){
    var siblings = aNode.parentNode.childNodes;
    var allCount = 0;
    var position;

    if (aNode.nodeType==Node.ELEMENT_NODE){
        var name = aNode.nodeName;
        for (var i=0; i<siblings.length; i++){
            var node = siblings.item(i);
            if (node.nodeType==Node.ELEMENT_NODE){
                if (node.nodeName == name) allCount++;  //nodeName includes namespace
                if (node == aNode) position = allCount;
            }
        }
    }
    else if (aNode.nodeType==Node.TEXT_NODE){
        for (var i=0; i<siblings.length; i++){
            var node = siblings.item(i);
            if (node.nodeType==Node.TEXT_NODE){
                allCount++;
                if (node == aNode) position = allCount;
            }
        }
    }
    if (allCount>1) return position;
    return null
}

function prepareKwds(){
    var kwds = new Array();
    kwds['toLowercase']= true;
    kwds['showId']=true;
    kwds['showClass']=true;
    kwds['showNS']=true;
    kwds['crossFrame']=true;
    kwds['parentView']=true;
    kwds['regexpView']=true;
//    printObject(kwds);
    return kwds;
}

function printObject(obj){
    var str = obj + "\n";
    for (a in obj) {
        str += a + ": " + obj[a] + "\n";
    }
    window.alert(str);
}

function findDefautNS(aContextNode){
    var element=aContextNode;
    while (element){
        if ((element.nodeType==Node.ELEMENT_NODE)
            &&(element.hasAttribute('xmlns'))) return element.getAttribute('xmlns');
        element=element.parentNode;
    }
    return null;
}
