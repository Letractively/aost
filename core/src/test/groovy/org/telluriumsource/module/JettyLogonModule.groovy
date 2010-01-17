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
    ui.Form(uid: "Form", clocator: [tag: "form"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }

    ui.Form(uid: "ProblematicForm", clocator: [tag: "form"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])
    }

    ui.Form(uid: "AbstractForm", clocator: [tag: "form"]) {
      Container(uid: "Form1") {
        Container(uid: "Username", clocator: [tag: "tr"]) {
          TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
          InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]) {
          Container(uid: "Password1") {
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
          }
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])

      }
    }

    ui.Image(uid: "Logo", clocator: [tag: "img", src: "*.gif", alt: "Logo"])

    ui.Container(uid: "Thumbnail", clocator: [tag: "div", class: "thumbnail potd"]){
        Container(uid: "ICon", clocator: [tag: "div", class: "potd:icon png.fix"]){
            Image(uid: "Image", clocator:  [tag: "img", src: "*.jpg"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", id: "Image:name"])
        }
    }
  }

  public void logon(String username, String password){
    keyType "Form.Username.Input", username
    keyType "Form.Password.Input", password
    click "Form.Submit"
    waitForPageToLoad 30000
  }

  public void alogon(String username, String password){
    keyType "AbstractForm.Form1.Username.Input", username
    keyType "AbstractForm.Form1.Password.Password1.Input", password
    click "AbstractForm.Form1.Submit"
    waitForPageToLoad 30000
  }

  public void plogon(String username, String password){
    keyType "ProblematicForm.Username.Input", username
    keyType "ProblematicForm.Password.Input", password
    click "ProblematicForm.Submit"
    waitForPageToLoad 30000
  }

  public String getLogoAlt(){
    return getImageAlt("Logo")
  }

  public String getImageAlt(){
    return getImageAlt("Thumbnail.ICon.Image")
  }

  public String typeImageName(String name){
    keyType "Thumbnail.ICon.Input", name
    pause 500
  }
}