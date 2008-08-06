package example.tellurium

import org.tellurium.dsl.DslContext

/**
 * Ui Modules for Tellurium Issues page at
 *
 *   http://code.google.com/p/aost/issues/list
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
class TelluriumIssuesPage extends DslContext{

   public void defineUi() {

       //define UI module of a form include issue type selector and issue search
       ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true") {
           Selector(uid: "issueType", clocator: [name: "can", id: "can"])
           TextBox(uid: "searchLabel", clocator: [tag: "span"])
           InputBox(uid: "searchBox", clocator: [name: "q"])
           SubmitButton(uid: "searchButton", clocator: [value: "Search"])
       }

       ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "post"], group: "true"){
           Table(uid: "searchTable", clocator: [class: "advquery"]){
               Selector(uid: "row:1, column: 3", clocator: [name: "can"])
               SubmitButton(uid: "row:1, column:4", clocator: [value: "Search", name: "btn"])
               InputBox(uid: "row:2, column:3", clocator:[name: "words"])
               InputBox(uid: "row:3, column:3", clocator:[name: "without"])
               InputBox(uid: "row:5, column:3", clocator:[name: "labels"])
               Table(uid: "row:6, column:1", clocator:[:]){
                   UrlLink(uid: "row:1, column:1", clocator:[text: "%%More Search Tips"])
               }
               InputBox(uid: "row:6, column:3", clocator:[name: "statuses"])
               InputBox(uid: "row:7, column:2", clocator:[name: "reporters"])
               InputBox(uid: "row:8, column:2", clocator:[name: "owners"])
               InputBox(uid: "row:9, column:2", clocator:[name: "cc"])
               InputBox(uid: "row:10, column:3", clocator:[name: "commentby"])
           }
       }
   }

    public String[] getIsssueTypes(){
        return  getSelectOptions("issueSearch.issueType")
    }

    public void selectIssueType(String type){
        selectByLabel "issueSearch.issueType", type
    }

    public void searchIssue(String issue){
        type "issueSearch.searchBox", issue
        click "issueSearch.searchButton"
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
        waitForPageToLoad 30000
    }

    public void clickMoreSearchTips(){
        click "issueAdvancedSearch.searchTable[6][1].[1][1]"
        waitForPageToLoad 30000
    }
}