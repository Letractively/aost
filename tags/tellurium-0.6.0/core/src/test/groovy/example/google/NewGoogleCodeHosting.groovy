package example.google

import org.tellurium.dsl.DslContext

/**
 *    Sample test for Google Code host page at:
 *
 *       http://code.google.com/hosting/
 *   to demostrate nested Tables and composite locator and the group information power to locate a group of ui objects
 *
 *   The portion of the HTML markup is shown in the file GoogleCodeHosting.xml
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class NewGoogleCodeHosting  extends DslContext{

    public void defineUi() {
       ui.Table(uid: "labels_table", clocator: [:], group: "true"){
         // Example project labels
         TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Explore hosted projects:"])
         Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
             UrlLink(uid: "all", locator: "/a")
           }
       }
    }

    public String getModuleLabel(){
        getText "labels_table[1][1]"
    }

    public int getLabelTableRowNum(){
        getTableMaxRowNum "labels_table[2][1]"
    }

    public int getLabelTableColumnNum(){
        getTableMaxColumnNum "labels_table[2][1]"
    }

    def getTableElement(int row, int column){
        getUiElement "labels_table[2][1].[${row}][${column}]"
    }

    Map getAllLabels(){
        int nrow = getTableMaxRowNum("labels_table[2][1]")
        int ncolumn = getTableMaxColumnNum("labels_table[2][1]")

        def map = [:]
        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
                String label = getText("labels_table[2][1].[${i}][${j}]")
                map.put(label, [i, j])
            }
        }

        return map
    }

    void clickOnLable(int row, int column){
        click  "labels_table[2][1].[${row}][${column}]"
        waitForPageToLoad 30000 
    }

    String getUrlLink(int row, int column){

        getLink("labels_table[2][1].[${row}][${column}]")
    }


}