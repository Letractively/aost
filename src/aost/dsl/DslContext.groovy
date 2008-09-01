package aost.dsl

import aost.dsl.UiDslParser
import aost.util.Helper
import aost.event.EventHandler
import aost.access.Accessor
import aost.locator.LocatorProcessor
import aost.object.Table
import aost.object.List

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
    }

    def doubleClick(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.doubleClick(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.doubleClick(locator)
        }
    }

    def clickAt(String id, String coordination){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.clickAt(coordination){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.clickAt(locator, coordination)
        }
    }

    def check(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.check(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.check(locator)
        }
    }

    def uncheck(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.uncheck(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.uncheck(locator)
        }
    }

    def type(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.type(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.type(locator, input)
        }
    }

    def keyType(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.keyType(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.keyType(locator, input)
        }
    }

    def typeAndReturn(String id, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.typeAndReturn(input){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.typeAndReturn(locator, input)
        }
    }

    def clearText(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.clearText(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.clearText(locator)
        }
    }

    def select(String id, String target){
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
    }

    def selectByValue(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.selectByValue(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.select(locator, optloc)
        }
    }

    def addSelectionByLabel(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.addSelectionByLabel(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.addSelection(locator, optloc)
        }
    }

    def addSelectionByValue(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.addSelectionByValue(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.addSelection(locator, optloc)
        }
    }

    def removeSelectionByLabel(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.removeSelectionByLabel(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeSelectionByValue(String id, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.removeSelectionByValue(target){ loc, optloc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeAllSelections(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.removeAllSelections(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.removeAllSelections(locator)
        }
    }

    String[] getSelectedLabels(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedLabels(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedLabels(locator)
             }
         }

        return null
    }

    String getSelectedLabel(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedLabel(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedLabel(locator)
             }
         }

        return null
    }

    String[] getSelectedValues(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedValues(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedValues(locator)
             }
         }

        return null
    }

    String getSelectedValue(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedValue(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedValue(locator)
             }
         }

        return null
    }

    String[] getSelectedIndexes(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedIndexes(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedIndexes(locator)
             }
         }

        return null
    }

    String getSelectedIndex(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedIndex(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedIndex(locator)
             }
         }

        return null
    }

    String[] getSelectedIds(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedIds(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedIds(locator)
             }
         }

        return null
    }

    String getSelectedId(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getSelectedId(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.getSelectedId(locator)
             }
         }

        return null
    }

    boolean isSomethingSelected(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.isSomethingSelected(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                accessor.isSomethingSelected(locator)
             }
         }

        return false
    }

    String waitForText(String id, int timeout){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.waitForText(timeout){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.waitForText(locator, timeout)
        }
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

    int getListSize(String id){
          WorkflowContext context = WorkflowContext.getDefaultContext()
         List obj = ui.walkTo(context, id)
         if(obj != null){
             return obj.getListSize(){ loc ->
                String locator = locatorProcessor.locate(loc)
                if(context.getReferenceLocator() != null)
                    locator = context.getReferenceLocator() + locator
                locator
             }
         }

         println("Cannot find list ${id}")
         return 0
    }

    //id should use the format table2[2][3] for Table or list[2] for List
    def getUiElement(String id){
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
    }

    String getValue(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.getValue(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getValue(locator)
        }
    }

    def pause(int milliseconds){
        Helper.pause(milliseconds)
    }

    String getLink(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, id)?.getLink(){ loc, attr ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageSource(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, id)?.getImageSource(){ loc, attr ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageAlt(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, id)?.getImageAlt(){ loc, attr ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageTitle(String id){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, id)?.getImageTitle(){ loc, attr ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getAttribute(locator + attr )
        }
    }

    def getAttribute(String id, String attribute){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, id)?.getAttribute(attribute){ loc, attr ->
             String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            accessor.getAttribute(locator + attr )
         }
    }

    def submit(String id){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, id)?.submit(){ loc ->
            String locator = locatorProcessor.locate(loc)
            if(context.getReferenceLocator() != null)
                locator = context.getReferenceLocator() + locator
            eventHandler.submit(locator)
        }
    }

    def openWindow(String url, String windowID){
        eventHandler.openWindow(url, windowID)
    }

    def selectWindow(String windowID){
        eventHandler.selectWindow(windowID)
    }

    def selectFrame(String locator){
        eventHandler.selectFrame(locator)
    }

    def waitForPopUp(String windowID, String timeout){
        return accessor.waitForPopUp(windowID, timeout)
    }

    String getBodyText(){
        return accessor.getBodyText()
    }

    boolean isTextPresent(String pattern){
       return accessor.isTextPresent(pattern)
    }

    boolean isEditable(String locator){
        return accessor.isEditable(locator)
    }

    String getHtmlSource(){
        return accessor.getHtmlSource()
    }

    String getExpression(String expression){
        return accessor.getExpression(expression)
    }

    Number getXpathCount(String xpath){
        return accessor.getXpathCount(xpath)
    }

    String getCookie(){
        return accessor.getCookie()
    }

    void runScript(String script){
        accessor.runScript(script)
    }

    void captureScreenshot(String filename){
        accessor.captureScreenshot(filename)
    }
}