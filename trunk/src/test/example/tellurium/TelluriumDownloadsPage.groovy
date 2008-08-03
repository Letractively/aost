package example.tellurium

import org.tellurium.dsl.DslContext

/**
 * Ui Modules for Tellurium Down page at
 *
 *   http://code.google.com/p/aost/issues/list
 *
 * @author Quan Bui (Quan.Bui@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
class TelluriumDownloadsPage extends DslContext{

   public void defineUi() {

        //define UI module of a form include download type selector and download search
        ui.Form(uid: "downloadSearch", clocator: [action: "list", method: "get"], group: "true"){
            Selector(uid: "downloadType", clocator: [name: "can", id: "can"])
            TextBox(uid: "searchLabel", clocator: [tag: "span"])
            InputBox(uid: "searchBox", clocator: [name: "q"])
            SubmitButton(uid: "searchButton", clocator: [value: "Search"])
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
}