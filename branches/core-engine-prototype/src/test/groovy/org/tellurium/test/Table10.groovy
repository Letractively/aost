package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * Test scenario for check duplicated table tags
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date:  Date: Feb 28, 2009
 *
 */

public class Table10 extends DslContext{
  
  public void defineUi() {
    ui.Table(uid: "Actions", clocator: [tag: "table", id: "ipclb1", class: "coolBar"], respond: ["mouseDown", "mouseOut", "mouseOver"]) {
      TextBox(uid: "header: 1", clocator: [tag: "th"], respond: ["click"])
      TextBox(uid: "header: 2", clocator: [tag: "th"], respond: ["click"])
      TextBox(uid: "header: 3", clocator: [tag: "th"], respond: ["click"])
      TextBox(uid: "header: 4", clocator: [tag: "th"], respond: ["click"])
      CheckBox(uid: "row: *, column: 1", clocator: [tag: "input", type: "checkbox", name: "EntityKey"])
      UrlLink(uid: "row: *, column: 2", clocator: [tag: "a", text: "Y100000542"])
      TextBox(uid: "all", clocator: [tag: "td", class: "abc"])
    }
  }

  public String getTableLocator(String uid) {
    return getLocator(uid)
  }

}