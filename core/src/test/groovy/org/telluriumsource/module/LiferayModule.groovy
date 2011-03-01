package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

class LiferayModule extends DslContext {
    String home_node_id = "aui-3-2-0PR1-11841";

    public void defineUi() {
        ui.Container(uid: "tree_node", clocator: [id: "\"${home_node_id}\"", class: "*aui-tree-content"]) {
//        ui.Container(uid: "tree_node", clocator: [id:  "aui-3-2-0PR1-11841", class: "*aui-tree-content"]) {
            Div(uid: "hitarea", clocator: [class: "*aui-tree-hitarea"], direct: "true", respond: ["click"])
            Div(uid: "icon", clocator: [class: "*aui-tree-icon"], direct: "true", respond: ["click"])
            Container(uid: "label", clocator: [class: "*aui-tree-label"], direct: "true", respond: ["click"]) {
                UrlLink(uid: "link", clocator: [:], direct: "true", respond: ["click"])
            }
        }
    }

    public void clickNode(){
        click "tree_node.hitarea"
    }
}
