package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 8, 2010
 * 
 */
class TableExampleModule extends DslContext {

  public void defineUi() {
    ui.StandardTable(uid: "GT", clocator: [id: "xyz"], ht: "tbody"){
      TextBox(uid: "{header: all}", clocator: [:])
      TextBox(uid: "{row: 1, column: 1} as A", clocator: [tag: "div", class: "abc"])
      Container(uid: "{row: 1, column: 2} as B"){
        InputBox(uid: "Input", clocator: [tag: "input", class: "123"])
        Container(uid: "Some", clocator: [tag: "div", class: "someclass"]){
          Span(uid: "Span", clocator: [tag: "span", class: "x"])
          UrlLink(uid: "Link", clocator: [:])
        }
      }
    }
  }

  public void work(String input){
    keyType "GT.B.Input", input
    click "GT.B.Some.Link"
    waitForPageToLoad 30000
  }

}
