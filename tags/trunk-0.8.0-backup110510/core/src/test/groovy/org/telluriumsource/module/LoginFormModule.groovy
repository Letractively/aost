package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2010
 * 
 */
class LoginFormModule extends DslContext{
  public void defineUiModule() {
    String VireoTestConfig = "vireo101";
    
    ui.Form(uid: "DspaceLoginForm", clocator: [tag: "form", method:"post",
            action: "${VireoTestConfig}/password-login",
            class: "ds-interactive-div primary", id: "aspect_eperson_PasswordLogin_div_login"]){
      InputBox(uid: "Input", clocator: [:])
    }
  }

}
