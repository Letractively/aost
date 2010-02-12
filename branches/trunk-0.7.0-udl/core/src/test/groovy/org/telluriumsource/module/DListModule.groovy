package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 9, 2010
 * 
 */
class DListModule extends DslContext {
  public void defineUi(){
    ui.Container(uid: "div1", clocator: [tag: "div", id: "div1"]) {
      Container(uid: "div2", clocator: [id: "div2"]) {
        List(uid: "list1", clocator: [tag: "ul"], separator: "li")
                {
                  Container(uid: "all")
                          {
                            List(uid: "list2", clocator: [tag: "ul"], separator: "li") {
                            }
                          }
                }//end list1
      }//end div2
    }//end div1
  }
}
