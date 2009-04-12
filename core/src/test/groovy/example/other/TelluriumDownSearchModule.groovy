package example.other

import org.tellurium.dsl.DslContext


public class TelluriumDownSearchModule extends DslContext {

  public void defineUi() {
    
    useJQuerySelector()
    
    ui.Form(uid: "TelluriumDownload", clocator: [tag: "form", method: "get", action: "list"], group: "true") {
      Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
      InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])
    }

    ui.UrlLink(uid: "Help", clocator: [text: "Help"])

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

  //Add your methods here
  public void searchDownload(String keyword) {
    keyType "TelluriumDownload.Input", keyword
    click "TelluriumDownload.Search"
    waitForPageToLoad 30000
  }

  public String[] getAllDownloadTypes() {
    return getSelectOptions("TelluriumDownload.DownloadType")
  }

  public void selectDownloadType(String type) {
    selectByLabel "TelluriumDownload.DownloadType", type
  }

  public void clickHelp(){
    click "Help"
    waitForPageToLoad 30000
  }

  public String[] getTableCSS(String name){
    String[] result = getCSS("downloadResult", name)

    return result
  }
}
