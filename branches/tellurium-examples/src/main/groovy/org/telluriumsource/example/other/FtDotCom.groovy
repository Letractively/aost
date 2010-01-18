package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * test example for http://www.ft.com/home/us
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 28, 2008
 * 
 */
class FtDotCom extends DslContext{

    public void defineUi() {
        ui.Frame(uid: "SubscribeFrame", name: "subscrbe", id: "ftsubscribe"){
            Form(uid: "LoginForm", clocator: [name: "loginForm"]){
                InputBox(uid: "UserName", clocator: [id: "username", type: "text"])
                InputBox(uid: "Password", clocator: [id: "password", type: "password"])
                Button(uid: "Login", clocator: [type: "image", class: "login"])
                CheckBox(uid: "RememberMe", clocator: [id: "rememberme"])
            }
        }
    }

    void selectSubScribeFrame(){
        selectFrame "SubscribeFrame"
    }

    void selectParentFrame(){
        selectParentFrameFrom "SubscribeFrame"
    }

    void checkRememberMe(){
        check "SubscribeFrame.LoginForm.RememberMe"
    }

    void uncheckRememberMe(){
        uncheck "SubscribeFrame.LoginForm.RememberMe"
        pause 200
    }

    void login(String username, String password){
        keyType "SubscribeFrame.LoginForm.UserName", username
        keyType "SubscribeFrame.LoginForm.Password", password
        click "SubscribeFrame.LoginForm.Login"
        selectTopFrameFrom "SubscribeFrame"
        waitForPageToLoad 30000
    }
}