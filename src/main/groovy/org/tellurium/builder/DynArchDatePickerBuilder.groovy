package org.tellurium.builder

import org.tellurium.widget.DynArchDatePicker

/**
 * Builder for DynArch's Date Picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 * 
 */
class DynArchDatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can useString them if not specified
        def df = [:]
        DynArchDatePicker datepicker = this.internBuild(new DynArchDatePicker(), map, df)
        datepicker.defineDatePicker()
        
        return datepicker
    }

}