package org.aost.object

/**
 *  IMAGE
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Image extends UiObject{
    public static final String TAG = "img"

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