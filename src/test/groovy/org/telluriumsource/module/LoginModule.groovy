package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 4, 2010
 * 
 * 
 */
class LoginModule extends DslContext {
  
  public void defineUi() {
    ui.Container(uid: "LoginMenu", clocator: [tag: "ul"]) {
      TextBox(uid: "Security", clocator: [tag: "h2", text: "Security"])
      UrlLink(uid: "Login", clocator: [tag: "a", text: "Login", href: "/FinanceManager/login.jsp"])
      TextBox(uid: "Product", clocator: [tag: "h2", text: "Product"])
      UrlLink(uid: "List", clocator: [tag: "a", text: "List", href: "/FinanceManager/product"])
    }

    ui.Span(uid: "LoginSlide", clocator: [tag: "span", text: "Spring Security Login", class: "dijitTitlePaneTextNode"])

    ui.Form(uid: "Login", clocator: [tag: "form", method: "POST", action: "/FinanceManager/j_spring_security_check", name: "f"]) {
      TextBox(uid: "UserNameLabel", clocator: [tag: "label", text: "Email:"])
      InputBox(uid: "UserName", clocator: [tag: "input", type: "text", name: "j_username", class: "dijitReset", id: "j_username"])
      TextBox(uid: "PasswordLabel", clocator: [tag: "label", text: "Password:"])
      InputBox(uid: "Password", clocator: [tag: "input", type: "password", name: "j_password", class: "dijitReset", id: "j_password"])
      SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Submit", id: "proceed"])
      Button(uid: "Reset", clocator: [tag: "input", type: "reset", value: "Reset", id: "reset"])
    }
  }

  public void login(String username, String password){
    keyType "Login.UserName", username
    keyType "Login.Password", password
    click "Login.Submit"
    waitForPageToLoad 30000
  }

  public void selectLogin(){
    click "LoginMenu.Login"
    waitForPageToLoad 30000
  }

  public void listProduct(){
    click "LoginMenu.List"
    waitForPageToLoad 30000
  }

  public void toggle(){
    click "LoginSlide"
  }
}
