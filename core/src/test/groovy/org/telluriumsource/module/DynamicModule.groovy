package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 8, 2010
 * 
 */
class DynamicModule extends DslContext {

  public void defineUi(){
    ui.Table(uid: "keyValue", clocator: [class: "keyValueTable"]){
      TextBox(uid: "{row: all, column: 1}, var key", clocator: [text: "key"], self: "true")
      TextBox(uid: "{row: all, column: 1}, var value", clocator: [text: "value"], self: "true")
    }

    ui.List(uid: "Views", clocator: [tag: "div", id: "view"], separator: "p"){
      UrlLink(uid: "{all}, var view_id", clocator: [id: "view_id", text: "View"])
    }
  }
}
