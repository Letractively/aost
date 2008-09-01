package example.aost

import aost.dsl.DslContext

/**
 * Test scenario for ALL MATCHING
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 30, 2008
 */
class Table1 extends DslContext {
    public void defineUi() {
        ui.Container(id: "main"){
            InputBox(id: "inputbox1", locator: "//input[@title='Google Search']")
            Button(id: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(id: "table1", locator: "//table"){
               Button(id: "all", locator: "//input[@type='submit']")
            }
        }
    }

}