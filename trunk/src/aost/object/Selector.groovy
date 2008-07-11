package aost.object

/**
 *  Selector
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Selector extends UiObject {
    public static final String TAG = "select"

    def selectByLabel(String target, Closure c){
        c(locator, "label=${target}")
    }

    def selectByValue(String target, Closure c){

        c(locator, "value=${target}")()
    }

    def addSelectionByLabel(String target, Closure c){
         c(locator, "label=${target}")
    }

    def addSelectionByValue(String target, Closure c){
          c(locator, "value=${target}")
    }

    def removeSelectionByLabel(String target, Closure c){
          c(locator, "label=${target}")
    }

    def removeSelectionByValue(String target, Closure c){
        c(locator, "value=${target}")
    }    

    def removeAllSelections(Closure c){
         c(locator)
    }

    String[] getSelectOptions(Closure c){
        c(locator)
    }

    String[] getSelectedLabels(Closure c){
        c(locator)
    }

    String getSelectedLabel(Closure c){
        c(locator)
    }

    String[] getSelectedValues(Closure c){
        c(locator)
    }

    String getSelectedValue(Closure c){
         c(locator)
    }

    String[] getSelectedIndexes(Closure c){
         c(locator)
    }

    String getSelectedIndex(Closure c){
         c(locator)
    }

    String[] getSelectedIds(Closure c){
         c(locator)
    }

    String getSelectedId(Closure c){
         c(locator)
    }

    boolean isSomethingSelected(Closure c){
         c(locator)
    }
}