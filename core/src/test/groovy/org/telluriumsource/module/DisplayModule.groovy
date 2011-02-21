package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 12, 2010
 * 
 */
class DisplayModule extends DslContext {

    public void defineUi() {
        ui.Container(uid: "Container1", clocator: [tag: "div", id: "Container1"])
        ui.Window(uid: "testWindow", name: "TestWindow") {

        }

        ui.Container(uid: "successMsg", clocator: [id: "successMsg"])
        ui.Container(uid: "errorMsg", clocator: [id: "errorMsg"])
        ui.Container(uid: "name", clocator: [tag: "body", id: "add_new_discussion"]) {
            Container(uid: "newArticle", clocator: [tag: "div", class: "add_new_article"]) {
                Container(uid: "head", cloator: [class: "head"]) {
                    Container(uid: "discussionEntryModuleTitle", clocator: [tag: "h3"])
                }
                Container(uid: "body", clocator: [class: "body"]) {
                    Container(uid: "discussionTitle", clocator: [tag: "li", class: "input text"]) {
                        Container(uid: "titleLabel", clocator: [tag: "label", for: "cmsgData[topic]"]);
                        InputBox(uid: "newDiscussionTitle", clocator: [name: "cmsgData[topic]"]);
                        Container(uid: "errorMessage", clocator: [class: "errorInput"])
                    }
                    Container(uid: "category", clocator: [tag: "li", class: "input select"]) {
                        Container(uid: "categoryLabel", clocator: [tag: "label", for: "cmsgData[category]"])
                        Selector(uid: "discussionCategory", clocator: [name: "cmsgData[category]"]);
                        Container(uid: "errorMessage", clocator: [class: "errorInput"])
                    }
                    Container(uid: "discussionTextArea", clocator: [tag: "li", class: "input textarea"]) {
                        Container(uid: "bodyLabel", clocator: [tag: "label", for: "cmsgData[description]"]);
                        InputBox(uid: "bodyFrame", clocator: [class: "mceContentBody ", id: "tinymce"]);
                        Container(uid: "errorMessage", clocator: [class: "errorInput"])
                        Container(uid: "charactersRemaining", clocator: [id: "inputCount"])
                    }

                }
            }
        }

    }

    public openPopupWindow() {
        click "testWindow"
        waitForPopUp("testWindow", 5000)
        selectWindow("testWindow")
    }
}
