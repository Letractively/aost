package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * Test scenario for COLUMN ALL MATCHING
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 30, 2008
 */
class Table3 extends DslContext {
    public void defineUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(uid: "table1", locator: "//table"){
               Button(uid: "row: 1, column: *", locator: "/input[@type='submit']")
               InputBox(uid: "row: 2, column: * ", locator: "/input[@title='Google Search']")
               UrlLink(uid: "row: 3", locator: "/link")
            }
        }
    }

}