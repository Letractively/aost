package org.telluriumsource.example.test.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenModule

/**
 * Data Driven Module for google code hosting UI module
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
class GoogleCodeHostingModule extends TelluriumDataDrivenModule{

    public void defineModule() {
       ui.Table(uid: "labels_table", clocator: [:], group: "true"){
         TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
         Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
             UrlLink(uid: "all", locator: "/a")
           }
       }

        //define file data format
        fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
            Test(value: "getGCHStatus")
            Field(name: "label")
            Field(name: "rowNum", type: "int")
            Field(name: "columNum", type: "int")
        }

        fs.FieldSet(name: "GCHLabel", description: "Google Code Hosting input") {
            Test(value: "clickGCHLabel")
            Field(name: "row", type: "int")
            Field(name: "column", type: "int")
        }

        defineTest("getGCHStatus"){
            def expectedLabel = bind("GCHStatus.label")
            def expectedRowNum = bind("GCHStatus.rowNum")
            def expectedColumnNum = bind("GCHStatus.columNum")

            openUrl("http://code.google.com/hosting/")
            def label = getText("labels_table[1][1]")
            def rownum = getTableMaxRowNum("labels_table[2][1]")
            def columnum = getTableMaxColumnNum("labels_table[2][1]")

            cacheVariable "RowNum", rownum
            cacheVariable "ColumnNum", columnum
            
            compareResult(expectedLabel, label)
            compareResult(expectedRowNum, rownum)
            compareResult(expectedColumnNum, columnum)
            pause 1000
       }

       defineTest("clickGCHLabel"){
           openUrl("http://code.google.com/hosting/")
           
           def erownum = getTableMaxRowNum("labels_table[2][1]")
           def ecolumnum = getTableMaxColumnNum("labels_table[2][1]")
           def rownum = getCachedVariable("RowNum")
           def columnum = getCachedVariable("ColumnNum")
           compareResult(rownum, erownum)
           compareResult(columnum, ecolumnum)
           pause 1000

           def row = bind("GCHLabel.row")
           def column = bind("GCHLabel.column")

           click  "labels_table[2][1].[${row}][${column}]"

           waitForPageToLoad 30000
       }
    }
}