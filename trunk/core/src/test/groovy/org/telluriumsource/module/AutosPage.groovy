package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext


class AutosPage extends DslContext {

    public void defineUi() {

        ui.Div(uid: "div2", clocator: [tag: "div", id: "yui_3_1_1_8_131463586969810"]) {
        }
    }  // end defineUi


    public void returnToParentPage() {
        goBack()
    }
}
