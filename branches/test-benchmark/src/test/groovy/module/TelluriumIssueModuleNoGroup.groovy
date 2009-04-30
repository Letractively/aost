package module

import org.tellurium.dsl.DslContext

/**
 * same as TelluriumIssue Module
 *
 * UI module without group locating
 *
 */

public class TelluriumIssueModuleNoGroup extends DslContext{

   public void defineUi() {

       //define UI module of a form include issue type selector and issue search
       ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "false") {
           Selector(uid: "issueType", clocator: [name: "can", id: "can"])
           TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
           InputBox(uid: "searchBox", clocator: [type: "text", name: "q", id: "q"])
           SubmitButton(uid: "searchButton", clocator: [value: "Search"])
       }

       ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "post"], group: "false"){
           Table(uid: "searchTable", clocator: [class: "advquery"]){
               Selector(uid: "row:1, column: 3", clocator: [name: "can"])
               SubmitButton(uid: "row:1, column:4", clocator: [value: "Search", name: "btn"])
               InputBox(uid: "row:2, column:3", clocator:[name: "words"])
               InputBox(uid: "row:3, column:3", clocator:[name: "without"])
               InputBox(uid: "row:5, column:3", clocator:[name: "labels"])
               Table(uid: "row:6, column:1", clocator:[:]){
                   UrlLink(uid: "row:1, column:1", clocator:[text: "*Search Tips"])
               }
               InputBox(uid: "row:6, column:3", clocator:[name: "statuses"])
               InputBox(uid: "row:7, column:2", clocator:[name: "reporters"])
               InputBox(uid: "row:8, column:2", clocator:[name: "owners"])
               InputBox(uid: "row:9, column:2", clocator:[name: "cc"])
               InputBox(uid: "row:10, column:3", clocator:[name: "commentby"])
           }
       }

       ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "false") {
           //define table header
           //for the border column
           TextBox(uid: "header: 1",  clocator: [:])
           UrlLink(uid: "header: 2",  clocator: [text: "*ID"])
           UrlLink(uid: "header: 3",  clocator: [text: "*Type"])
           UrlLink(uid: "header: 4",  clocator: [text: "*Status"])
           UrlLink(uid: "header: 5",  clocator: [text: "*Priority"])
           UrlLink(uid: "header: 6",  clocator: [text: "*Milestone"])
           UrlLink(uid: "header: 7",  clocator: [text: "*Owner"])
           UrlLink(uid: "header: 9",  clocator: [text: "*Summary + Labels"])
           UrlLink(uid: "header: 10", clocator: [text: "*..."])

           //define table elements
           //for the border column
           TextBox(uid: "row: *, column: 1", clocator: [:])
           TextBox(uid: "row: *, column: 8", clocator: [:])
           TextBox(uid: "row: *, column: 10", clocator: [:])
           //For the rest, just UrlLink
           UrlLink(uid: "all", clocator: [:])
       }
   }

    public String[] getIsssueTypes(){
        return  getSelectOptions("issueSearch.issueType")
    }

    public void selectIssueType(String type){
        selectByLabel "issueSearch.issueType", type
    }

    public void searchIssue(String issue){
        clearText "issueSearch.searchBox"
        keyType "issueSearch.searchBox", issue
//        type "issueSearch.searchBox", issue
        click "issueSearch.searchButton"
    }

    public void waitPageLod(){
        waitForPageToLoad 30000
    }

    public String[] getAdvancedIsssueTypes(){
        return  getSelectOptions("issueAdvancedSearch.searchTable[1][3]")
    }

    public void advancedSearchIssue(String issueType, String words, String without, String labels, String statuses,
        String reporters, String owners, String cc, String commentby){
        if(issueType != null){
            selectByLabel "issueAdvancedSearch.searchTable[1][3]", issueType
        }

        if(words != null){
            type "issueAdvancedSearch.searchTable[2][3]", words
        }

        if(without != null){
            type "issueAdvancedSearch.searchTable[3][3]", without
        }

        if(labels != null){
            type "issueAdvancedSearch.searchTable[5][3]", labels
        }

        if(statuses != null){
            type "issueAdvancedSearch.searchTable[6][3]", statuses
        }

        if(reporters != null){
            type "issueAdvancedSearch.searchTable[7][2]", reporters
        }

        if(owners != null){
            type "issueAdvancedSearch.searchTable[8][2]", owners
        }

        if(cc != null){
           type "issueAdvancedSearch.searchTable[9][2]", cc
        }

        if(commentby != null){
           type "issueAdvancedSearch.searchTable[10][3]", commentby
        }

        click "issueAdvancedSearch.searchTable[1][4]"
    }

    public void clickMoreSearchTips(){
        click "issueAdvancedSearch.searchTable[6][1].[1][1]"
    }

    public int getTableHeaderNum(){
        return getTableHeaderColumnNum("issueResult")
    }
     public List<String> getHeaderNames(){
        List<String> headernames = new ArrayList<String>()
        int mcolumn = getTableHeaderColumnNum("issueResult")
        if(mcolumn > 10)
           mcolumn = 10
        for(int i=1; i<mcolumn; i++){
            headernames.add(getText("issueResult.header[${i}]"))
        }

        return headernames
    }

    public List<String> getDataForColumn(int column){
        int nrow = getTableMaxRowNum("issueResult")
        if(nrow > 20) nrow = 20
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
    }

    public void getIssueDataByCells(){
        int mcolumn =  getTableHeaderColumnNum("issueResult");
        if(mcolumn > 10) mcolumn = 10
        int mrow = getTableMaxRowNum("issueResult")
        if(mrow > 20) mrow = 20
        for(int i=1; i<mrow; i++){
          for(int j=1; j<mcolumn; j++){
            getText("issueResult[${i}][${j}]")
          }
        }
    }

    public String[] getAllText(){
      return getAllTableCellText("issueResult")
    }

    public void getIssueData(){
      if(this.exploreJQuerySelector)
        getAllText()
      else
        getIssueDataByCells()
    }

    public void clickTable(int row, int column){
        click "issueResult[${row}][${column}]"
    }

    public void clickOnTableHeader(int column){
        click "issueResult.header[${column}]"
    }

    public void selectDataLayout(String layout){
        if("List".equalsIgnoreCase(layout)){
            click "layoutSelector.List"
        }
        if("Grid".equalsIgnoreCase(layout)){
            click "layoutSelector.Grid"
        }
    }

    public void pauseTest(int duration){
      pause(duration)
    }
}