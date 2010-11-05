package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Oct 20, 2010
 * 
 */
class RListModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "A", clocator: [tag: "ul", class: "a"]) {
      UrlLink(uid: "AA", clocator: [tag: "a", text: "AA", class: "b"])
      UrlLink(uid: "BB", clocator: [tag: "a", text: "BB", class: "b"])
      UrlLink(uid: "CC", clocator: [tag: "a", text: "CC", class: "b"])
      UrlLink(uid: "DD", clocator: [tag: "a", text: "DD", class: "b"])
      UrlLink(uid: "EE", clocator: [tag: "a", text: "EE", class: "b"])
      UrlLink(uid: "FF", clocator: [tag: "a", text: "FF", class: "b"])
    }
  }

  public void click(uid) {

    click("A." + uid)

    waitForPageToLoad(10000)
  }

}
