package org.aost.object

/**
 *  Button
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Button extends UiObject {

    public static final String TAG = "input"
    
    def click(Closure c) {

        c(locator)
    }

    def doubleClick(Closure c) {

        c(locator)
    }

    def clickAt(String coordination, Closure c) {

        c(locator)
    }
}