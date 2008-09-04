package org.tellurium.widget.dojo.builder

import org.tellurium.builder.UiObjectBuilder
import org.tellurium.widget.dojo.object.DatePicker

/**
 * Builder for widget Date picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)
        datepicker.defineWidget()

        return datepicker
    }


}