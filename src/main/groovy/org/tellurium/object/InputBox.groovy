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
        c(locator, respondToEvents)
    }

    def keyType(String input, Closure c){
        c(locator, respondToEvents)
    }

    def typeAndReturn(String input, Closure c){

        c(locator, respondToEvents)
    }

    def clearText(Closure c){

        c(locator, respondToEvents)
    }

    boolean isEditable(Closure c){
        return c(locator)
    }

    String getValue(Closure c){
       return c(locator)
    }

	def clickAt(String coordination, Closure c) {
        c(locator, respondToEvents)
    }
	def mouseDown(Closure c){
        c(locator, respondToEvents)
    }
	def mouseUp(Closure c){
        c(locator, respondToEvents)
    }

}