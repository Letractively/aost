package aost.dsl

import aost.dsl.UiDslParser
import aost.util.Helper
import aost.event.EventHandler
import aost.access.Accessor
import aost.locator.LocatorProcessor

abstract class DslContext {
    def UiDslParser ui = new UiDslParser()

    //decoupling eventhandler, locateProcessor, and accessor from UI objects
    //and let DslContext to handle all of them directly. This will also make
    //the framework reconfiguration much easier.
    def eventHandler = new EventHandler()
    def accessor = new Accessor()
    def locatorProcessor = new LocatorProcessor()

//    def defUi = ui.&Container

    //Must implement this method to define UI
//    remove this constraint so that DSL script does not need to define this method
//    public abstract void defineUi()

    def findObject(String id){
        def obj = ui.findUiObjectFromRegistry(id)
        if(obj == null)
            println("Cannot find UI Object ${id}")
        return obj
    }

    def click(String id){
        findObject(id)?.click(){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.click(locator)
        }
    }

    def doubleClick(String id){
        findObject(id)?.doubleClick(){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.doubleClick(locator)
        }
    }

    def clickAt(String id, String coordination){
        findObject(id)?.clickAt(coordination){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.clickAt(locator, coordination)
        }
    }

    def check(String id){
       findObject(id)?.check(){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.check(locator)
       }
    }

    def uncheck(String id){
       findObject(id)?.uncheck(){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.uncheck(locator)
       }
    }

    def type(String id, String input){
       findObject(id)?.type(input){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.type(locator, input)
       }
    }

    def keyType(String id, String input){
       findObject(id)?.keyType(input){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.keyType(locator, input)
       }
    }

    def typeAndReturn(String id, String input){
        findObject(id)?.typeAndReturn(input){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.typeAndReturn(locator, input)
        }
    }

    def clearText(String id){
        findObject(id)?.clearText(){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.clearText(locator)
        }
    }

    def select(String id, String target){
//        findObject(id)?.select(target)
      selectByLabel(id, target)
    }

    def selectByLabel(String id, String target){
        findObject(id)?.selectByLabel(target){ loc, optloc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.select(locator, optloc)
        }
    }

    def selectByValue(String id, String target){
        findObject(id)?.selectByValue(target){ loc, optloc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.select(locator, optloc)
        }
    }

    def String waitForText(String id, int timeout){
        findObject(id)?.waitForText(timeout){ loc ->
            def locator = locatorProcessor.locate(loc)
            eventHandler.waitForText(locator, timeout)
        }
    }

    def boolean isElementPresent(String id){
        def obj = findObject(id)
        if(obj != null){
            return obj.isElementPresent(){ loc ->
                def locator = locatorProcessor.locate(loc)
                accessor.isElementPresent(locator)
            }
        }
     
        return false
    }

    def boolean isVisible(String id){
        def obj = findObject(id)
        if(obj != null){
            return obj.isVisible(){ loc ->
                def locator = locatorProcessor.locate(loc)
                accessor.isVisible(locator)
            }
        }

        return false
    }

    def boolean isChecked(String id){
        def obj = findObject(id)
        if(obj == null)
            throw RuntimeException("Cannot find UI Object ${id})")

        return obj.isChecked(){ loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.isChecked(locator)
        }
    }

    def boolean waitForElementPresent(String id, int timeout){
       def obj = findObject(id)
       if(obj == null)
            return false

       return obj.waitForElementPresent(timeout){ loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.waitForElementPresent(locator, timeout)
       }
    }

    def boolean waitForElementPresent(String id, int timeout, int step){
       def obj = findObject(id)
       if(obj == null)
            return false

       return obj.waitForElementPresent(timeout, step){ loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.waitForElementPresent(locator, timeout, step)
       }
    }

    def boolean waitForCondition(String script, String timeoutInMilliSecond){
       Accessor.waitForCondition(script, timeoutInMilliSecond)
    }

    def String getText(String id){
        findObject(id)?.getText(){ loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.getText(locator)
        }
    }

    def String getValue(String id){
       findObject(id)?.getValue(){ loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.getValue(locator)
       }
    }

     def getCellValue(String id, int row, int column){
        findObject(id)?.getCellLocator(row,column){loc ->
            def locator = locatorProcessor.locate(loc)
            accessor.getText(locator)
        }
    }

    def pause(int milliseconds){
        Helper.pause(milliseconds)
    }
}