package org.tellurium.object

import org.tellurium.object.UiObject

/**
 *  Radio Button
 *
 *  only one option is to be selected at a time
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class RadioButton extends UiObject {

    public static final String TAG = "input"
    public static final String TYPE = "radio"

    def check(Closure c){

        c(locator, respondToEvents)
    }

    def boolean isChecked(Closure c){

        c(locator, respondToEvents)
    }

    def uncheck(Closure c){

        c(locator, respondToEvents)
    }

    String getValue(Closure c){
       return c(locator)
    }

}