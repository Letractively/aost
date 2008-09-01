package aost.object

class InputBox extends UiObject{

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