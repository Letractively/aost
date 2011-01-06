TE.ns("TE.main");
TE.main = {
    openWindow:function(){
        //window opens asynchronously... so you cannot call methods immediately
        TE.main.window = window.open("chrome://source/content/tellurium-ide.xul","uiEditor","chrome,centerscreen,alwaysRaised=true,resizable");
    }
};
