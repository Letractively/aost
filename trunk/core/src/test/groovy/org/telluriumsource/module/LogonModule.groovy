package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 15, 2010
 * 
 */
class LogonModule extends DslContext {
  String uiModName = "createAccount";

  public void defineUi() {
    ui.Form(uid: "createAccount", clocator: [tag: "form", method: "post"]) {
      InputBox(uid: "email", clocator: [id: "email"])
      InputBox(uid: "newPassword", clocator: [id: "newPassword"])
      InputBox(uid: "newPassword2", clocator: [id: "newPassword2"])
      InputBox(uid: "firstName", clocator: [id: "firstName"])
      InputBox(uid: "lastName", clocator: [id: "lastName"])
      SubmitButton(uid: "submit", clocator: [:])
    }
  }

  public void clearForm() {
    clearText "createAccount.email"
    clearText "createAccount.newPassword"
    clearText "createAccount.newPassword2"
    clearText "createAccount.firstName"
    clearText "createAccount.lastName"
  }

}
