package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 13, 2010
 * 
 */
class SignUpModule extends DslContext{

  public void defineUi() {
    ui.Container(uid: "SignUp", clocator: [tag: "div", class: "sc-view", id: "sc378"]) {
      Div(uid: "Name", clocator: [tag: "div", text: "Name:", direct: "true", class: "sc-view sc-label-view sc-regular-size", id: "sc382"])
      InputBox(uid: "First", clocator: [tag: "input", type: "text", name: "sc383"])
      InputBox(uid: "Last", clocator: [tag: "input", type: "text", name: "sc385"])
      InputBox(uid: "Email", clocator: [tag: "input", type: "text", name: "sc388"])

      Container(uid: "Sex", clocator: [tag: "div", direct: "true", class: "sc-view sc-radio-view sc-layout-vertical sc-regular-size", id: "sc393"]) {
        RadioButton(uid: "Male", clocator: [name: "sc393", value: "0"])
        RadioButton(uid: "Female", clocator: [name: "sc393", value: "1"])
      }

      UrlLink(uid: "Ok", clocator: [id: "sc380"])
//      UrlLink(uid: "Ok", clocator: [id: "sc380"], respond: ["mouseOver", "focus", "mouseDown", "mouseUp", "mouseOut"])
      UrlLink(uid: "Cancel", clocator: [id: "sc381"], respond: ["mouseOver", "focus", "mouseDown", "mouseUp", "mouseOut"])
    }
  }

  public void signUp(String first, String last, String email){
    type "SignUp.First", first
    type "SignUp.Last", last
    type "SignUp.Email", email
    mouseDown "SignUp.Ok"
    mouseUp "SignUp.Ok"
    waitForPageToLoad 30000
  }

}