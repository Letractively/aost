package example.aost

import aost.dsl.DslContext

/**
 *   A sample UI includes all default UI object types
 * 
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class SampleUI extends DslContext {
    public void defineUi() {
        ui.Container(id: "main"){
            InputBox(id: "inputbox1", locator: "//input[@title='Google Search']")
            Button(id: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Icon(id: "icon1", locator: "//image")
            Image(id: "image1", locator: "//image[@src='a.gif")
            Selector(id: "select1", locator: "//selector[1]")
            RadioButton(id: "radioButton1", locator: "//input[@type='radio")
            TextBox(id: "textbox1", locator: "//div[3]/p")
            UrlLink(id: "urllink1", locator: "//a[@href='text.htm'")
            Table(id: "table1", locator: "//table"){
               Button(id: "all", locator: "/input[@type='submit']")
            }
            List(id: "subcategory", locator: "/p", separator: "/p"){
                UrlLink(id: "all", locator: "/a")
            }
        }
    }

    public void defineCompositeUi() {
        ui.Container(id: "main"){
            InputBox(id: "inputbox1", clocator: [ tag: "input", title: "Google Search"])
            Button(id: "button1", clocator: [ tag: "input", name: "btn", type: "submit"])
            Icon(id: "icon1", clocator: [ tag: "image"])
            Image(id: "image1", clocator: [tag : "image", src: "a.gif"])
            Selector(id: "select1", clocator: [tag: "selector", position: "1"])
            RadioButton(id: "radioButton1", clocator: [tag: "input", type: "radio"])
            TextBox(id: "textbox1", clocator: [tag: "div", position: "3"])
            UrlLink(id: "urllink1", clocator: [tag: "a", href: "text.htm"])
            Table(id: "table1", clocator: [tag: "table"]){
               Button(id: "all", clocator: [tag: "input", type: "submit"])
            }
            List(id: "subcategory", clocator: [tag: "p"], separator: "/p"){
                UrlLink(id: "all", clocator: [tag: "a"])
            }
        }
    }

}