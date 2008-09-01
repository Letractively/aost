package aost.object

class CheckBox extends UiObject{
    
    def check(Closure c){

        c(locator)
    }

    def boolean isChecked(Closure c){

        c(locator)
    }

    def uncheck(Closure c){

        c(locator)
    }
}