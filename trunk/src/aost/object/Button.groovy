package aost.object

class Button extends UiObject {
    
    //TODO: need to add support for isDisabled
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