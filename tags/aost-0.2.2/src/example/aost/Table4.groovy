package example.aost

import aost.dsl.DslContext

/**
 * Test scenario for specifying individual (row, column)
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 30, 2008
 */
class Table4 extends DslContext{
    public void defineUi() {
        ui.Container(id: "main"){
            InputBox(id: "inputbox1", locator: "//input[@title='Google Search']")
            Button(id: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(id: "table1", locator: "//table"){
               Button(id: "row: 1, column: 1", locator: "/input[@type='submit']")
               InputBox(id: "row: 1, column: 2 ", locator: "/input")
               UrlLink(id: "row: 2, column: 1", locator: "/link")
               Button(id: "row: 2, column: 2", locator: "/input")
            }
        }
    }

}