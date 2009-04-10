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
}
