package aost.object

/**
 *  URL link
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class UrlLink extends UiObject {
    public static final String TAG = "a"
    
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