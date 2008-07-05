package aost.object

class UrlLink extends UiObject {

    String getLink(Closure c){
        c(locator, "@href")
    }

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