package org.tellurium.object

/**
 *  Text Box
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class TextBox extends UiObject {

    String waitForText(int timeout, Closure c){
       c(locator)
    }
}