package aost.dsl

import aost.dsl.UiDslParser
import aost.util.Helper
import aost.event.EventHandler
import aost.access.Accessor
import aost.locator.LocatorProcessor
import aost.object.Table

abstract class DslContext {
    UiDslParser ui = new UiDslParser()

    //decoupling eventhandler, locateProcessor, and accessor from UI objects
    //and let DslContext to handle all of them directly. This will also make
    //the framework reconfiguration much easier.
    EventHandler eventHandler = new EventHandler()
    Accessor accessor = new Accessor()
    LocatorProcessor locatorProcessor = new LocatorProcessor()

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
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.click(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.click(locator)
        }
//        findObject(id)?.click(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.click(locator)
//        }
    }

    def doubleClick(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.doubleClick(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.doubleClick(locator)
        }

//        findObject(id)?.doubleClick(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.doubleClick(locator)
//        }
    }

    def clickAt(String id, String coordination){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.clickAt(coordination){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.clickAt(locator, coordination)
        }
       
//        findObject(id)?.clickAt(coordination){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.clickAt(locator, coordination)
//        }
    }

    def check(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.check(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.check(locator)
        }

//       findObject(id)?.check(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.check(locator)
//       }
    }

    def uncheck(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.uncheck(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.uncheck(locator)
        }

//       findObject(id)?.uncheck(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.uncheck(locator)
//       }
    }

    def type(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.type(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.type(locator, input)
        }

//       findObject(id)?.type(input){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.type(locator, input)
//       }
    }

    def keyType(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.keyType(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.keyType(locator, input)
        }

//       findObject(id)?.keyType(input){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.keyType(locator, input)
//       }
    }

    def typeAndReturn(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.typeAndReturn(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.typeAndReturn(locator, input)
        }

///        findObject(id)?.typeAndReturn(input){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.typeAndReturn(locator, input)
//        }
    }

    def clearText(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.clearText(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.clearText(locator)
        }

//        findObject(id)?.clearText(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.clearText(locator)
//        }
    }

    def select(String id, String target){
//        findObject(id)?.select(target)
        selectByLabel(id, target)
    }

    def selectByLabel(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.selectByLabel(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.select(locator, optloc)
        }

//        findObject(id)?.selectByLabel(target){ loc, optloc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.select(locator, optloc)
//        }
    }

    def selectByValue(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.selectByValue(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.select(locator, optloc)
        }

//        findObject(id)?.selectByValue(target){ loc, optloc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.select(locator, optloc)
//        }
    }

    String waitForText(String id, int timeout){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.waitForText(timeout){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.waitForText(locator, timeout)
        }

//        findObject(id)?.waitForText(timeout){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            eventHandler.waitForText(locator, timeout)
//        }
    }

    int getTableMaxRowNum(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getTableMaxRowNum(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                locator
             }
         }
         
         println("Cannot find table ${id}")
         return 0
    }

    int getTableMaxColumnNum(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getTableMaxColumnNum(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                locator
             }
         }

         println("Cannot find table ${id}")
         return 0
    }

    //id should use the format table2[2][3]
    def getTableElement(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)

         return obj
    }

    boolean isElementPresent(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.isElementPresent(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.isElementPresent(locator)
             }
         }

         return false
        
//        def obj = findObject(id)
//        if(obj != null){
//            return obj.isElementPresent(){ loc ->
//                def locator = locatorProcessor.locate(loc)
//                accessor.isElementPresent(locator)
//            }
//        }
//
//        return false
    }

    boolean isVisible(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.isVisible(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.isVisible(locator)
             }
         }

         return false

//        def obj = findObject(id)
//        if(obj != null){
//            return obj.isVisible(){ loc ->
//                def locator = locatorProcessor.locate(loc)
//                accessor.isVisible(locator)
//            }
//        }
//
//        return false
    }

    boolean isChecked(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.isChecked(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.isChecked(locator)
             }
         }
        
         throw RuntimeException("Cannot find UI Object ${id})")

//        def obj = findObject(id)
//        if(obj == null)
//            throw RuntimeException("Cannot find UI Object ${id})")
//
//        return obj.isChecked(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.isChecked(locator)
//        }
    }

    boolean waitForElementPresent(String id, int timeout){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.waitForElementPresent(timeout){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.waitForElementPresent(locator, timeout)
             }
         }

         return false
        
//        def obj = findObject(id)
//       if(obj == null)
//            return false
//
//       return obj.waitForElementPresent(timeout){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.waitForElementPresent(locator, timeout)
//       }
    }

    boolean waitForElementPresent(String id, int timeout, int step){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.waitForElementPresent(timeout, step){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.waitForElementPresent(locator, timeout, step)
             }
         }

         return false

//       def obj = findObject(id)
//       if(obj == null)
//            return false
//
//       return obj.waitForElementPresent(timeout, step){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.waitForElementPresent(locator, timeout, step)
//       }
   }

    boolean waitForCondition(String script, String timeoutInMilliSecond){
       Accessor.waitForCondition(script, timeoutInMilliSecond)
    }

    String getText(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.getText(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getText(locator)
        }

//        findObject(id)?.getText(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.getText(locator)
//        }
    }

    String getValue(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.getValue(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getValue(locator)
        }

//       findObject(id)?.getValue(){ loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.getValue(locator)
//       }
    }

//     def getCellValue(String id, int row, int column){
//        findObject(id)?.getCellLocator(row,column){loc ->
//            def locator = locatorProcessor.locate(loc)
//            accessor.getText(locator)
//        }
//    }

    def pause(int milliseconds){
        Helper.pause(milliseconds)
    }
}