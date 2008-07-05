package aost.object

class Image extends UiObject{

    String getImageSource(Closure c){
        c(locator, "@src")
    }

    String getImageAlt(Closure c){
        c(locator, "@alt")
    }

    String getImageTitle(Closure c){
        c(locator, "@title")
    }
}