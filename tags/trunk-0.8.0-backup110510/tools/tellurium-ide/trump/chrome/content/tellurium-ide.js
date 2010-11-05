

var telluriumIdeWindow;

//opens Trump IDE Window
function openTelluriumIDEWindow(){
    //window opens asynchronously... so you cannot call methods immediately
    telluriumIdeWindow = window.open("chrome://trump/content/tellurium-ide.xul","uiEditor","chrome,centerscreen,alwaysRaised=true,resizable");
}


