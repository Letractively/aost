package example.google

import aost.dsl.DslContext

class GoogleStartPage extends DslContext{

    public void defineUi() {
 //       defUi(id: "google_start_page"){
        ui.Container(id: "google_start_page"){
            InputBox(id: "inputbox1", locator: "//input[@title='Google Search']")
            Button(id: "button1", locator: "//input[@name='btnG' and @type='submit']")
        }
    }

    def type(String input){
        type "inputbox1", input
        pause 500
        click "button1"
        pause 1000        
    }
}