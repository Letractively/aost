package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 10, 2010
 * 
 */
class DGridModule  extends DslContext{

    public void defineUi() {
      ui.StandardTable(uid: "LoginList", clocator: [class: "dataTable"]){
        UrlLink(uid: "{header: 1} as Username", clocator: [:])
        UrlLink(uid: "{header: 2} as Email", clocator: [:])
        UrlLink(uid: "{header: 3} as Enabled", clocator: [:])

        UrlLink(uid: "{row: all, column: 1}", clocator: [:])
        TextBox(uid: "{row: all, column: 2}", clocator: [tag: "span"])
        TextBox(uid: "{row: all, column: 3}", clocator: [tag: "span"])
      }
    }
}
