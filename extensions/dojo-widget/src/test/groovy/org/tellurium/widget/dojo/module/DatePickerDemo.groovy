package org.tellurium.widget.dojo.module

import org.tellurium.dsl.DslContext

/**
 * Example to demonstrate the Date Picker widget at
 *
 *   http://turtle.dojotoolkit.org/~dylan/dojo/tests/widget/demo_DatePicker.html
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePickerDemo extends DslContext{
    
    public void defineUi() {
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
            Image(uid: "selectDate", clocator: [title: "select a date", dojoattachpoint: "containerDropdownNode", alt: "date"])
            Container(uid: "datePicker", clocator: [tag: "div", dojoattachpoint: "subWidgetContainerNode"])
        }
    }

    public void clickWidget(){
        click "dropdown.selectDate"
        pause 500
    }

    public void increaseWeek(){
        onWidget "dropdown.datePicker", "increaseWeek"
        pause 500
    }
}