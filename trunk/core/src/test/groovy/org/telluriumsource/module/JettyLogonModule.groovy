package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 23, 2009
 * 
 */

public class JettyLogonModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Form", clocator: [tag: "table"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", direct: "true", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", direct: "true", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }
  }

  public void logon(String username, String password){
    type "Form.Username.Input", username
    type "Form.Password.Input", password
    click "Form.Submit"
    waitForPageToLoad 30000
  }
}