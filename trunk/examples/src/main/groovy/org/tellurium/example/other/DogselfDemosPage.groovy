package org.tellurium.example.other

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 14, 2009
 * 
 */
class DogselfDemosPage extends DslContext {
    
    public void initUi() {
        ui.Container(uid: "div", clocator: [id: "disabledTest"], group: "true") {
            Selector(uid: "disabledNoAttrValue", clocator: [id: "d"]) //<select disabled id="d">
            Selector(uid: "disabled", clocator: [id: "d2"]) //<select disabled="anything" id="d2">
        }
    }

    public String getDisabledNoAttrValue() {
        return getAttribute("div.disabledNoAttrValue", "disabled")
    }

    public String getDisabledAttributeHasAttrValue() {
        return getAttribute("div.disabled", "disabled")
    }

    public boolean isDisabledNoAttrValue(){
        return isDisabled("div.disabledNoAttrValue")
    }

    public boolean isDisabledHasAttrValue(){
        return isDisabled("div.disabled")
    }
}