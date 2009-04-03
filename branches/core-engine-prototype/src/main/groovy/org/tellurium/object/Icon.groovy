package org.tellurium.object

/**
 *  ICON
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Icon extends UiObject {

    def click(Closure c) {

        c(locator, respondToEvents)
    }

    def doubleClick(Closure c) {

        c(locator, respondToEvents)
    }

    def clickAt(String coordination, Closure c) {

        c(locator, respondToEvents)
    }
}