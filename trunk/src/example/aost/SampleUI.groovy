package example.aost

import aost.dsl.DslContext

/**
 *   A sample UI includes all default UI object types
 * 
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class SampleUI extends DslContext {
    public void defineUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Icon(uid: "icon1", locator: "//image")
            Image(uid: "image1", locator: "//image[@src='a.gif")
            Selector(uid: "select1", locator: "//selector[1]")
            RadioButton(uid: "radioButton1", locator: "//input[@type='radio")
            TextBox(uid: "textbox1", locator: "//div[3]/p")
            UrlLink(uid: "urllink1", locator: "//a[@href='text.htm'")
            Table(uid: "table1", locator: "//table"){
               Button(uid: "all", locator: "/input[@type='submit']")
            }
            List(uid: "subcategory", locator: "/p", separator: "/p"){
                UrlLink(uid: "all", locator: "/a")
            }
        }
    }

    public void defineCompositeUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", clocator: [ tag: "input", title: "Google Search"])
            Button(uid: "button1", clocator: [ tag: "input", name: "btn", type: "submit"])
            Icon(uid: "icon1", clocator: [ tag: "image"])
            Image(uid: "image1", clocator: [tag : "image", src: "a.gif"])
            Selector(uid: "select1", clocator: [tag: "selector", position: "1"])
            RadioButton(uid: "radioButton1", clocator: [tag: "input", type: "radio"])
            TextBox(uid: "textbox1", clocator: [tag: "div", position: "3"])
            UrlLink(uid: "urllink1", clocator: [tag: "a", href: "text.htm"])
            Table(uid: "table1", clocator: [tag: "table"]){
               Button(uid: "all", clocator: [tag: "input", type: "submit"])
            }
            List(uid: "subcategory", clocator: [tag: "p"], separator: "/p"){
                UrlLink(uid: "all", clocator: [tag: "a"])
            }
        }
    }

}