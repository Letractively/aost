package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 20, 2010
 * 
 */
class SuperModule extends DslContext {
  
  public void defineUi(){
    ui.Container(uid: "comments", clocator: [tag: "div", class: "comments"]) {
      TextBox(uid: "title", clocator: [tag: "div", class: "title comment"])
      InputBox(uid: "input_textarea", clocator: [tag: "textarea", name: "myData[comment]"], respond: ["focus"])
      TextBox(uid: "input_count", clocator: [class: "input", id: "inputCount"])
      SubmitButton(uid: "post_comment", clocator: [tag: "input", type: "submit", id: "submitComment", value: "Post comment"], respond: ["click"])

      List(uid: "comments_list", clocator: [tag: "ul"], separator: "li") {
        Container(uid: "{all}", clocator: [:]) {
          Container(uid: "member", clocator: [class: "member", tag: "div"]) {
            UrlLink(uid: "link", clocator: [tag: "a"])
            Image(uid: "image", clocator: [tag: "img"])
          }
          Container(uid: "txt", clocator: [class: "txt", tag: "div"]) {
            Container(uid: "tools", clocator: [tag: "div", class: "tools"]) {
              UrlLink(uid: "delete", clocator: [tag: "a", text: "Delete"], respond: ["click"])
            }
            Container(uid: "author", clocator: [tag: "div", class: "author"]) {
              UrlLink(uid: "link", clocator: [tag: "a"])
            }
            Container(uid: "date", clocator: [class: "date", tag: "div"])
            List(uid: "description", clocator: [tag: "div", class: "description"]) {
              UrlLink(uid: "{all}", clocator: [:])
            }
          }
        }
      }
    }
  }
  
}
