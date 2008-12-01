

var trumpWindow;
//opens or update the xpatherBowser window
function openUIModelWindow(clickedNode){

    //var string = bundle.getFormattedString(nodeName, attributeString);
        //window opens asynchronously... so you cannot call methods immediately
    trumpWindow = window.open("chrome://trump/content/trumpBrowser.xul","uimodel_results","chrome,centerscreen,alwaysRaised=true,resizable");
//    trumpWindow.updateUIModelText(string);
}

