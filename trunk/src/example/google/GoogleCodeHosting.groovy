package example.google

import aost.dsl.DslContext
import aost.object.UrlLink

/**
 *   Sample test for Google Code host page at:
 *
 *       http://code.google.com/hosting/
 *   to demostrate nested Tables
 *
 *   The portion of the HTML markup is shown in the file GoogleCodeHosting.xml
 * 
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class GoogleCodeHosting  extends DslContext{

    public void defineUi() {
       ui.Container(id: "googlecodehosting"){
           Table(id: "labels_table", locator: "//table[descendant::div[contains(text(),\"Example project labels:\")]]"){
               TextBox(id: "row: 1, column: 1", locator: "/div")
               Table(id: "row: 2, column: 1", locator: "/div[@id=\"popular\"]/table"){
                   UrlLink(id: "all", locator: "/a")
               }
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
        getTableElement "labels_table[2][1].[${row}][${column}]"
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
        pause 5000
    }
}