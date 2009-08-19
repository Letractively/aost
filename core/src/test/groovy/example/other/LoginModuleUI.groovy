package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 19, 2009
 *
 */

public class LoginModuleUI extends DslContext {

  public void defineUi() {
    // Login Form
    ui.Form(uid: "LoginForm", clocator: [tag: "form", id: "MiniLogin", name: "LOGIN"], group: "true") {
      Container(uid: "LoginSection", clocator: [tag: "div", class: "primary"]) {
        InputBox(uid: "UserName", clocator: [tag: "input", type: "text", id: "userid", name: "userid"])
        InputBox(uid: "Password", clocator: [tag: "input", type: "password", id: "password", name: "password"])
        Button(uid: "LoginBtn", clocator: [tag: "input", type: "image", name: "loginbtn"])
      }
      UrlLink(uid: "NewUsers", clocator: [tag: "a", text: "New Users", href: "/English/myCitrix/loginNewUser.asp"])
    }
  }
  //Add your methods here
  public void doMyCitrixLogin(String UserName, String Password) {

    type "LoginForm.LoginSection.UserName", UserName
    type "LoginForm.LoginSection.Password", Password
    click "LoginForm.LoginSection.LoginBtn"
    waitForPageToLoad(5000)

  }

}