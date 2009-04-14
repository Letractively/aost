package example.other

import org.tellurium.dsl.DslContext

public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }

    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
      //define table header
      //for the border column
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "%%ID"])
      UrlLink(uid: "header: 3", clocator: [text: "%%Type"])
      UrlLink(uid: "header: 4", clocator: [text: "%%Status"])
      UrlLink(uid: "header: 5", clocator: [text: "%%Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "%%Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "%%Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "%%Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "%%..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }

  }

  public List<String> getDataForColumn(int column) {
    int mcolumn = getTableMaxRowNum("issueResult")
    List<String> lst = new ArrayList<String>()
    for (int i = 1; i <= mcolumn; i++) {
      lst.add(getText("issueResult[${i}][${column}]"))
    }

    return lst
  }

  public String[] getAllText(){
    return getAllTableCellText("issueResult")
  }

  public int getTableCellCount(){
    String sel = getSelector("issueResult")
    sel = sel + " > tbody > tr > td"

    return getJQuerySelectorCount(sel)
  }

  public String[] getTableCSS(String name){
    String[] result = getCSS("issueResult.header[1]", name)

    return result
  }

  public String[] getIsssueTypes() {
    return getSelectOptions("issueSearch.issueType")
  }

  public void selectIssueType(String type) {
    selectByLabel "issueSearch.issueType", type
  }

  public void searchIssue(String issue) {
    keyType "issueSearch.searchBox", issue
//        type "issueSearch.searchBox", issue
    click "issueSearch.searchButton"
    waitForPageToLoad 30000
  }
}