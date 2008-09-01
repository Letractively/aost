package aost.object

class Selector extends UiObject {

    def selectByLabel(String target, Closure c){
        c(locator, "label=${target}")
    }

    def selectByValue(String target, Closure c){

        c(locator, "value=${target}")()
    }

    def String[] getSelectOptions(Closure c){
        c(locator)
    }
}