package org.tellurium.object
/**
 * abstract and generic UI which can be clicked on
 * can be used to present any unpredefined clickable UI objects
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class ClickableUi extends UiObject {

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