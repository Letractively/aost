package org.telluriumsource.ui.widget.jqueryui.builder

import org.telluriumsource.ui.builder.UiObjectBuilder
import org.telluriumsource.ui.widget.jqueryui.object.DatePicker

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 28, 2010
 * 
 */
class DatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)
        datepicker.defineWidget()
        datepicker.dsl.locator = datepicker.locator

        return datepicker
    }
}
