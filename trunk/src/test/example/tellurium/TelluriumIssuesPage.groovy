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
        ui.Form(uid: "issueSearch", clocator: [action: "list", method: "get"], group: "true"){
            Selector(uid: "issueType", clocator: [name: "can", id: "can"])
            TextBox(uid: "searchLabel", clocator: [tag: "span"])
            InputBox(uid: "searchBox", clocator: [name: "q"])
            SubmitButton(uid: "searchButton", clocator: [value: "Search"])
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
        pause 2000
    }
}