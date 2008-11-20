package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 17, 2008
 * 
 */
class TWindow extends DslContext{

    public void defineUi() {
         ui.Window(uid: "windowName", name: "windowName"){
             InputBox(uid: "UserName", clocator: [id: "username", type: "text"])             
         }
    }

    public void waitForPopup(){
        waitForPopUp "windowName", 30000
    }

    public void selectOriginalWindow(){
        selectMainWindow()
    }

    public void selectChildWindow(String uid){
        selectWindow(uid)
    }
}