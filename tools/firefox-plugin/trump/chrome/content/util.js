
/**
 * Returns the node type
 * @param node
 */
function getNodeType(node){
//    alert(node.nodeType)
    return node.nodeType;
}

/**
 * Returns the node name
 * @param node
 */
function getNodeName(node){
    return (node &&  node.nodeName) ? node.nodeName : "";
}

/**
 * Returns the node value or innerHTML
 * @param node
 */

function getNodeValue(node){
    return node.nodeValue != null ? node.nodeValue : node.innerHTML;
}


function getAttributeNameOrId(node){
//    alert("getAttributeNameOrId()");
    var attributes = node.attributes;
    var val = "";
     for(var i=0; i < attributes.length; ++i){
//         alert(attributes[i].name);
        if(attributes[i].name == 'name' || attributes[i].name == 'id'){
            val = attributes[i].value;
//            alert(val);
            break;
        }
    }
    return val;
}

function findAttributeInList(attributes, attr){
    for(var i=0; i < attributes.length; ++i){
//         alert(attributes[i].name);
        if(attributes[i].name == attr ){
            val = attributes[i].value;
//            alert(val);
            break;
        }
    }
}

function createListCell(value){
    var cell = document.createElement("listcell");
    cell.setAttribute("label",value );

    return cell;
}

var XulUtils = {
    clearChildren: function(e) {
        var i;
        for (i = e.childNodes.length - 1; i >= 0; i--) {
            e.removeChild(e.childNodes[i]);
        }
    },

    appendMenuItem: function(e, attributes) {
        var menuitem = document.createElement("menuitem");
        for (key in attributes) {
            if (attributes[key] != null) {
                menuitem.setAttribute(key, attributes[key]);
            }
        }
        e.appendChild(menuitem);
    },

    toXPCOMArray: function(data) {
        var array = Components.classes["@mozilla.org/supports-array;1"].createInstance(Components.interfaces.nsISupportsArray);
        for (var i = 0; i < data.length; i++) {
            array.AppendElement(this.toXPCOMString(data[i]));
        }
        return array;
    },

    toXPCOMString: function(data) {
        var string = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        string.data = data;
        return string;
    },

    atomService: Components.classes["@mozilla.org/atom-service;1"].
            getService(Components.interfaces.nsIAtomService)
};

XulUtils.TreeViewHelper = classCreate();
objectExtend(XulUtils.TreeViewHelper.prototype, {
    initialize: function(tree) {
        tree.view = this;
        this.tree = tree;
    },

    scrollToRow: function(index) {
        this.treebox.ensureRowIsVisible(index);
    },

    rowUpdated: function(index) {
        this.treebox.invalidateRow(index);
    },

    //
    // nsITreeView interfaces
    //
    setTree: function(treebox) {
        this.log.debug("setTree: treebox=" + treebox);
        this.treebox = treebox;
    },
    isContainer: function(row) {
        return false;
    },
    isSeparator: function(row) {
        return false;
    },
    isSorted: function(row) {
        return false;
    },
    getLevel: function(row) {
        return 0;
    },
    getImageSrc: function(row, col) {
        return null;
    },
    getColumnProperties: function(colid, col, props) {
    },
    cycleHeader: function(colID, elt) {
    }
});

var XPCU =
{
  getService: function(aURL, aInterface)
  {
    try {
      return Components.classes[aURL].getService(Components.interfaces[aInterface]);
    } catch (ex) {
      dump("Error getting service: " + aURL + ", " + aInterface + "\n" + ex);
      return null;
    }
  },

  createInstance: function (aURL, aInterface)
  {
    try {
      return Components.classes[aURL].createInstance(Components.interfaces[aInterface]);
    } catch (ex) {
      dump("Error creating instance: " + aURL + ", " + aInterface + "\n" + ex);
      return null;
    }
  },

  QI: function(aEl, aIName)
  {
    try {
      return aEl.QueryInterface(Components.interfaces[aIName]);
    } catch (ex) {
      throw("Unable to QI " + aEl + " to " + aIName);
    }
  }

};