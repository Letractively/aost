package org.tellurium.builder

import org.tellurium.object.RadioButton
import org.tellurium.locator.BaseLocator

/**
 *  Radio Button Builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class RadioButtonBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, RadioButton.TAG)
        df.put(TYPE, RadioButton.TAG)
        RadioButton radiobutton = this.internBuild(new RadioButton(), map, df)

        return radiobutton
    }

}