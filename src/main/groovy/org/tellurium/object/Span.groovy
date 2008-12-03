package org.tellurium.object

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class Span extends UiObject{
    public static final String TAG = "span"

    def click(Closure c){
        c(locator, respondToEvents)
    }

}