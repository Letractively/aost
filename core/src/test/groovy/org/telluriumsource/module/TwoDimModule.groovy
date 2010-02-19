package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 19, 2010
 * 
 */
class TwoDimModule  extends DslContext {
  public void defineUi(){
    ui.StandardTable(uid: "Table", clocator: [id: "table"], ht: "div", bt: "div", frt: "div", fct: "div", brt: "div", bct: "div"){
      TextBox(uid: "footer: all", clocator: [tag: "div"])
      TextBox(uid: "row: *, column: 1", clocator: [tag: "div"])
      Image(uid: "row: *, column: 2", clocator: [:])
    }
  }
}
