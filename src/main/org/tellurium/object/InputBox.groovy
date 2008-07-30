package org.tellurium.object

/**
 *  Input BOX
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class InputBox extends UiObject{
    public static final String TAG = "input"
    
    def type(String input, Closure c){
        c(locator)
    }

    def keyType(String input, Closure c){
        c(locator)
    }

    def typeAndReturn(String input, Closure c){

        c(locator)
    }

    def clearText(Closure c){

        c(locator)
    }
}