package aost.builder

import aost.object.CheckBox
import aost.locator.BaseLocator

/**
 *    CheckBox builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class CheckBoxBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can use them if not specified
        def df = [:]
        df.put(TAG, CheckBox.TAG)
        df.put(TYPE, CheckBox.TAG)
        CheckBox checkbox = this.internBuild(new CheckBox(), map, df)

        return checkbox
    }
}