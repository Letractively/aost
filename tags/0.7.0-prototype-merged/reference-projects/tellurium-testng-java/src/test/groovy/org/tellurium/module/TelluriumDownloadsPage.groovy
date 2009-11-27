package org.tellurium.module

import org.tellurium.dsl.DslContext

/**
 * Ui Modules for Tellurium Down page at
 *
 *   http://code.google.com/p/aost/downloads/list
 *
 * @author Quan Bui (Quan.Bui@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
class TelluriumDownloadsPage extends DslContext{

   public void defineUi() {

       //define UI module of a form include download type selector and download search
       ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "GET"], group: "true") {
           Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
           TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
           InputBox(uid: "searchBox", clocator: [name: "q"])
           SubmitButton(uid: "searchButton", clocator: [value: "Search"])
       }

       ui.Table(uid: "downloadResult", clocator: [id: "resultstable", class: "results"], group: "true"){
           //define table header
           //for the border column
           TextBox(uid: "header: 1", clocator: [:])
           UrlLink(uid: "header: 2", clocator: [text: "*Filename"])
           UrlLink(uid: "header: 3", clocator: [text: "*Summary + Labels"])
           UrlLink(uid: "header: 4", clocator: [text: "*Uploaded"])
           UrlLink(uid: "header: 5", clocator: [text: "*Size"])
           UrlLink(uid: "header: 6", clocator: [text: "*DownloadCount"])
           UrlLink(uid: "header: 7", clocator: [text: "*..."])

           //define table elements
           //for the border column
           TextBox(uid: "row: *, column: 1", clocator: [:])
           //the summary + labels column consists of a list of UrlLinks
           List(uid: "row:*, column: 3", clocator: [:]){
               UrlLink(uid: "all", clocator: [:])
           }
           //For the rest, just UrlLink
           UrlLink(uid: "all", clocator: [:])
       }
   }

    public String[] getAllDownloadTypes(){
        return  getSelectOptions("downloadSearch.downloadType")
    }

    public String getCurrentDownloadType(){
        return  getSelectedLabel("downloadSearch.downloadType");
    }

    public void selectDownloadType(String type){
        selectByLabel "downloadSearch.downloadType", type
    }

    public void searchDownload(String keyword){
        type "downloadSearch.searchBox", keyword
        click "downloadSearch.searchButton"
        waitForPageToLoad 30000
    }

    public int getTableHeaderNum(){
        return getTableHeaderColumnNum("downloadResult")
    }

    public List<String> getHeaderNames(){
        List<String> headernames = new ArrayList<String>()
        int mcolumn = getTableHeaderColumnNum("downloadResult")
        for(int i=1; i<=mcolumn; i++){
            headernames.add(getText("downloadResult.header[${i}]"))
        }

        return headernames
    }

    public List<String> getDownloadFileNames(){
        int mcolumn = getTableMaxRowNum("downloadResult")
        List<String> filenames = new ArrayList<String>()
        for(int i=1; i<=mcolumn; i++){
            filenames.add(getText("downloadResult[${i}][2]").trim())
        }

        return filenames
    }

    public void clickFileNameColumn(int row){
        click "downloadResult[${row}][2]"
        pause 1000
        chooseCancelOnNextConfirmation()
        pause 500
    }

    //click on the summary + labels column
    public void clickSummaryLabelsColumn(int row, int index){
        //because the table element is a list of UrlLinks, just click on the first one
        click "downloadResult[${row}][3].[${index}]"
        waitForPageToLoad 30000
    }

    public void clickUploadedColumn(int row){
        click "downloadResult[${row}][4]"
        waitForPageToLoad 30000
    }

    public void clickSizeColumn(int row){
        click "downloadResult[${row}][5]"
        waitForPageToLoad 30000
    }

    public void clickDownloadedCountColumn(int row){
        click "downloadResult[${row}][6]"
        waitForPageToLoad 30000
    }

    public void clickOnTableHeader(int column){
        click "downloadResult.header[${column}]"
        pause 500
    }
}