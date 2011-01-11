TE.ns("TE.main");
TE.merge(TE.main,{
    openWindow:function(){
        //window opens asynchronously... so you cannot call methods immediately
        TE.main.window = window.open("chrome://source/content/tellurium-ide.xul","uiEditor","chrome,centerscreen,alwaysRaised=true,resizable");
    },
    onLoad:function(){
        this.initialized = true; 
        var appcontent = document.getElementById("appcontent");
        if(appcontent){
            var self = this;
            appcontent.addEventListener("DOMContentLoaded",function(event){
                self.onContentLoaded(event);
            },false);
            appcontent.addEventListener("load",this.onPageLoad,true);
        }
    },
    onContentLoaded:function(event){
        var editor = this.getEditor();
        if(editor){
           editor.onDOMContentLoaded(event);
        }
    },
    onPageLoad:function(event){
        var doc = event.originalTarget;
        if(doc.defaultView && !doc.readyState){
            doc.defaultView.setTimeout(function(){
                if(doc.wrappedJSObject){
                    doc = doc.wrappedJSObject;
                }
                doc.readyState = "complete";
            })
        }
    },
    getEditor:function(){
        var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
        var editorWindow = wm.getMostRecentWindow('global:tellurium-ide');

        return editorWindow.editor;
    }
});
