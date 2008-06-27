package aost.object

class Icon extends UiObject {

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