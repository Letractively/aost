package org.tellurium.example.google

import org.tellurium.dsl.DslContext

/**
 *  Google start page with new UI definition features such as composite locator and group information
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class NewGoogleStartPage extends DslContext{

    public void defineUi() {

        //clocator stands for composite locator, i.e., the attributes specified in its map will be
        //used to construct the xpath. Group: "true" means we will useString group information to specifiy
        //the container's locator, i.e., the whole UI module's locator. Only container and its extended
        //classes has this attribute and it is false by default.
        //Since group information will be used, the attributes in inputbox and two submit buttons will
        //be used to locate the container's locator.
        //This is based on the fact that you have more attributes to specify, you are more likely to be
        //able to find the locator. Think about the observation: how difficult is to find a inputbox on the
        //web and how difficult is to find a group of ui elements including one input box, two submit buttons.
        //of course, thisqures that the input box and two submit buttons are indeed the children of the
        //container, i.e., here they must be co-located following the "td" tag in the DOM.

      /*
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
//            InputBox(uid: "searchbox", clocator: [title: "Google Search"], respond: ["click", "doubleclick", "focus", "mouseOver", "mouseOut", "blur", "keyDown"])
            InputBox(uid: "searchbox", clocator: [name: "q"], respond: ["click", "doubleclick", "focus", "mouseOver", "mouseOut", "blur", "keyDown"])
            
//            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG"])
//            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [name: "btnl"])
        }
*/      
        ui.Container(uid: "google_start_page", clocator: [tag: "table"]) {
          InputBox(uid: "searchbox", clocator: [tag: "input", title: "Google Search", name: "q"], respond: ["click", "doubleclick", "focus", "mouseOver", "mouseOut", "blur", "keyDown"])
          SubmitButton(uid: "googlesearch", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
          SubmitButton(uid: "Imfeelinglucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
        }

    }

    def doGoogleSearch(String input){
//        type "searchbox", input
        keyType "searchbox", input
        pause 500
        click "googlesearch"
        waitForPageToLoad 30000
    }

    def doImFeelingLucky(String input){
        type "searchbox", input
        pause 500
        click "Imfeelinglucky"
        waitForPageToLoad 50000
    }

    def testClick(){
        click "searchbox"
        pause 500
    }

    def testDoubleClick(){
        doubleClick "searchbox"
        pause 500
    }
    
    String getAttribute(){
        getAttribute("google_start_page.searchbox", "title")
    }
  
    def clickWrongButton(){
      click "google_start_page.search"
    }
}