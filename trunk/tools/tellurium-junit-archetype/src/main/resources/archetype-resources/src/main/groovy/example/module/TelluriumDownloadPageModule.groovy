package example.module

import org.tellurium.dsl.DslContext

/**
 *  This UI module file is automatically generated by TrUMP 0.1.0.
 *
 *   Ui Modules for Tellurium Down page at
 *
 *   http://code.google.com/p/aost/downloads/list
 *
 */

class TelluriumDownloadPageModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Tellurium", clocator: [tag: "body", class: "t2"]) {
      Form(uid: "Form", clocator: [tag: "form", method: "get", action: "list"], group: "true") {
        InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "q", id: "q"])
        SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Search"])
        Selector(uid: "DownloadType", clocator: [tag: "select", name: "can", id: "can"])
      }
      Container(uid: "Title", clocator: [tag: "table", id: "mt"]) {
        UrlLink(uid: "Issues", clocator: [tag: "a", text: "Issues"], respond: ["click"])
        UrlLink(uid: "Wiki", clocator: [tag: "a", text: "Wiki"], respond: ["click"])
        UrlLink(uid: "Downloads", clocator: [tag: "a", text: "Downloads"], respond: ["click"])
        UrlLink(uid: "Source", clocator: [tag: "a", text: "Source"], respond: ["click"])
      }
    }
  }

  //Add your methods here
  public void searchDownload(String keyword) {
    keyType "Tellurium.Form.Input", keyword
    click "Tellurium.Form.Search"
    waitForPageToLoad 30000
  }

  public String[] getAllDownloadTypes() {
    return getSelectOptions("Tellurium.Form.DownloadType")
  }

  public void selectDownloadType(String type) {
    selectByLabel "Tellurium.Form.DownloadType", type
  }

  public void clickIssuesPage() {
    click "Tellurium.Title.Issues"
    waitForPageToLoad 30000
  }
}