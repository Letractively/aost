package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 2, 2010
 * 
 */
class TableRowModule extends DslContext {

  public void defineUi() {
    ui.Table(uid: "data", clocator: [id: "hello"], group: "true") {

      TextBox(uid: "{row: all, column: 1}", clocator: [:])
      TextBox(uid: "{row: all, column: 2}", clocator: [:])
      TextBox(uid: "{row: all, column: 3}", clocator: [:])
      TextBox(uid: "{row: all, column: 4}", clocator: [:])
      TextBox(uid: "{row: all, column: 5}", clocator: [:])

    }

    ui.Table(uid: "data1", clocator: [id: "hello"], group: "true") {

      TextBox(uid: "{row: all, column: 1}", clocator: [tag: "td"], self: true)
      TextBox(uid: "{row: all, column: 2}", clocator: [tag: "td"], self: true)
      TextBox(uid: "{row: all, column: 3}", clocator: [tag: "td"], self: true)
      TextBox(uid: "{row: all, column: 4}", clocator: [tag: "td"], self: true)
      TextBox(uid: "{row: all, column: 5}", clocator: [tag: "td"], self: true)

    }

    ui.Table(uid: "Table", clocator: [id: "hello"]){
      TextBox(uid: "{row: all, column: all}", clocator: [tag: "td"], self: true)
    }
  }

}
