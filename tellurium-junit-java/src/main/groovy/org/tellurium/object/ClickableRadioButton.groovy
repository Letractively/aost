package org.tellurium.object
/**
 * The Radio Button can be reactive to onClick events
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 3, 2008
 * 
 */
class ClickableRadioButton extends RadioButton{

    def click(Closure c) {

        c(locator)
    }
}