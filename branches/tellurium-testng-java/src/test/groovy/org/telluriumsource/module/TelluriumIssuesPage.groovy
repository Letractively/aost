package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

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
       ui.Form(uid: "issueSearch", clocator: [action: "list", method: "GET"], group: "true") {
         Selector(uid: "issueType", clocator: [name: "can", id: "can"])
         TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
         InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
         SubmitButton(uid: "searchButton", clocator: [value: "Search"])
       }

       ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "POST"], group: "true"){
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
       ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
         TextBox(uid: "header: 1", clocator: [:])
         UrlLink(uid: "header: 2", clocator: [text: "*ID"])
         UrlLink(uid: "header: 3", clocator: [text: "*Type"])
         UrlLink(uid: "header: 4", clocator: [text: "*Status"])
         UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
         UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
         UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
         UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
         UrlLink(uid: "header: 10", clocator: [text: "...", href: "#columnprefs"])

         //define table elements
         //for the border column
         TextBox(uid: "row: *, column: 1", clocator: [:])
         TextBox(uid: "row: *, column: 8", clocator: [:])
         TextBox(uid: "row: *, column: 10", clocator: [:])
         //For the rest, just UrlLink
         UrlLink(uid: "all", clocator: [:])
       }


       //items is a map in the format of "alias name" : menu_item
       ui.SimpleMenu(uid: "IdMenu", clocator:[class: "popup", id: "pop_0"],
               items: ["SortUp":"Sort Up", "SortDown":"Sort Down", "HideColumn":"Hide Column"])

       //define the dot menu where you can select different column to display
       ui.SelectMenu(uid: "selectColumnMenu", clocator:[class: "popup",id: "pop__dot"], title: "Show columns:",
               items: ["ID":"ID", "Type":"Type", "Status":"Status", "Priority":"Priority", "Milestone":"Milestone", "Owner":"Owner", "Summary":"Summary", "Stars":"Stars", "Opened":"Opened", "Closed":"Closed", "Modified":"Modified", "EditColumn":"Edit Column Spec..." ])

       //The selector to choose the data grid layout as List or Grid
       ui.Option(uid: "layoutSelector"){
           Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
               TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"])
               UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
           }
           Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
               UrlLink(uid: "List", clocator: [text: "List", direct: "true"])
               TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"])
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
        keyType "issueSearch.searchBox", issue
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
            keyType "issueAdvancedSearch.searchTable[2][3]", words
        }

        if(without != null){
            keyType "issueAdvancedSearch.searchTable[3][3]", without
        }

        if(labels != null){
            keyType "issueAdvancedSearch.searchTable[5][3]", labels
        }

        if(statuses != null){
            keyType "issueAdvancedSearch.searchTable[6][3]", statuses
        }

        if(reporters != null){
            keyType "issueAdvancedSearch.searchTable[7][2]", reporters
        }

        if(owners != null){
            keyType "issueAdvancedSearch.searchTable[8][2]", owners
        }

        if(cc != null){
           keyType "issueAdvancedSearch.searchTable[9][2]", cc
        }

        if(commentby != null){
           keyType "issueAdvancedSearch.searchTable[10][3]", commentby
        }

        click "issueAdvancedSearch.searchTable[1][4]"
        waitForPageToLoad 30000
    }

    public void clickMoreSearchTips(){
        click "issueAdvancedSearch.searchTable[6][1].[1][1]"
        waitForPageToLoad 30000
    }

    public int getTableHeaderNum(){
        return getTableHeaderColumnNum("issueResult")
    }

    public List<String> getHeaderNames(){
        List<String> headernames = new ArrayList<String>()
        int mcolumn = getTableHeaderColumnNum("issueResult")
        for(int i=1; i<=mcolumn; i++){
            headernames.add(getText("issueResult.header[${i}]"))
        }

        return headernames
    }

    public List<String> getDataForColumn(int column){
        int mcolumn = getTableMaxRowNum("issueResult")
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<=mcolumn; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
    }

    public void clickTable(int row, int column){
        click "issueResult[${row}][${column}]"
        waitForPageToLoad 30000
    }

    public void clickOnTableHeader(int column){
        click "issueResult.header[${column}]"
        pause 1000
    }

    public void mouseMoveIdMenu(){
        mouseOver "IdMenu.SortUp"
        pause 500
        mouseOut  "IdMenu.SortUp"
        pause 50

        mouseOver "IdMenu.SortDown"
        pause 500
        mouseOut  "IdMenu.SortDown"
        pause 50

        mouseOver "IdMenu.HideColumn"
        pause 500
        mouseOut  "IdMenu.HideColumn"
        pause 50

        mouseOver "IdMenu.SortDown"
        pause 500
        mouseOut  "IdMenu.SortDown"
        pause 50

        mouseOver "IdMenu.SortUp"
        pause 500
        mouseOut  "IdMenu.SortUp"
        pause 50
    }

    public void toggleIdColumn(String column){
        click "issueResult.header[10]"
        pause 1000
        click "selectColumnMenu.${column}"
        pause 1000
        click "issueResult.header[10]"
        pause 1000
        click "selectColumnMenu.${column}"
        pause 1000
    }

    public void clickIdMenuSortUp(){
        click "IdMenu.SortUp"
        waitForPageToLoad 30000
    }

    public void clickIdMenuSortDown(){
        click "IdMenu.SortDown"
        waitForPageToLoad 30000
    }

    public void clickIdMenuHideColumn(){
        click "IdMenu.HideColumn"
        waitForPageToLoad 30000
    }

    public void selectDataLayout(String layout){
        if("List".equalsIgnoreCase(layout)){
            click "layoutSelector.List"
            waitForPageToLoad 30000
        }
        if("Grid".equalsIgnoreCase(layout)){
            click "layoutSelector.Grid"
            waitForPageToLoad 30000
        }
    }

    public String[] getAllText(){
      return getAllTableCellText("issueResult")
    }

    public int getTableCellCount(){
      String sel = getSelector("issueResult")
      sel = sel + " > tbody > tr > td"

      return getCssSelectorCount(sel)
    }
}