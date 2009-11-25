package org.tellurium.example.google

import org.tellurium.dsl.DslContext

class GoogleStartPage extends DslContext{

    public void defineUi() {
        ui.Container(uid: "google_start_page"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
        }
    }

    def type(String input){
        type "inputbox1", input
        pause 500
        click "button1"
        waitForPageToLoad 30000    
    }
}