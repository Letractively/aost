package example.dynarch

import org.tellurium.dsl.DslContext
import org.tellurium.widget.DynArchDatePicker

/**
 * Example to test the widget DynArchDatePicker appeared on the following page
 *
 *  http://www.dynarch.com/projects/calendar/
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 * 
 */
class DynArchPage extends DslContext{

    public void defineUi() {
        ui.Container(uid: "projectlink", clocator: [tag: "div", class: "project-links"]){
            DynArchDatePicker(uid: "datepicker", clocator: [tag: "div", id: "calendar"])
        }
    }

    public void clickAbout(){
        DynArchDatePicker datePicker = getUiElement("projectlink.datepicker")
        datePicker.clickAbout()
        pause 1000
    }
}