package org.aost.dsl

import org.aost.dsl.UiDslParser
import org.aost.util.Helper
import org.aost.event.EventHandler
import org.aost.access.Accessor
import org.aost.locator.LocatorProcessor
import org.aost.object.Table
import org.aost.object.List

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

    def findObject(String uid){
        def obj = ui.findUiObjectFromRegistry(uid)
        if(obj == null)
            println("Cannot find UI Object ${uid}")
        return obj
    }

    def click(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.click(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.click(locator)
        }
    }

    def doubleClick(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.doubleClick(){ loc ->
           String locator = locatorMapping(context, loc)
            eventHandler.doubleClick(locator)
        }
    }

    def clickAt(String uid, String coordination){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.clickAt(coordination){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.clickAt(locator, coordination)
        }
    }

    def check(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.check(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.check(locator)
        }
    }

    def uncheck(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.uncheck(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.uncheck(locator)
        }
    }

    def type(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.type(input){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.type(locator, input)
        }
    }

    def keyType(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.keyType(input){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.keyType(locator, input)
        }
    }

    def typeAndReturn(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.typeAndReturn(input){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.typeAndReturn(locator, input)
        }
    }

    def clearText(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.clearText(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.clearText(locator)
        }
    }

    def select(String uid, String target){
        selectByLabel(uid, target)
    }

    def selectByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.selectByLabel(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.select(locator, optloc)
        }
    }

    def selectByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.selectByValue(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.select(locator, optloc)
        }
    }

    def addSelectionByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.addSelectionByLabel(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.addSelection(locator, optloc)
        }
    }

    def addSelectionByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.addSelectionByValue(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.addSelection(locator, optloc)
        }
    }

    def removeSelectionByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.removeSelectionByLabel(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeSelectionByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.removeSelectionByValue(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeAllSelections(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.removeAllSelections(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeAllSelections(locator)
        }
    }

    String[] getSelectedLabels(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedLabels(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedLabels(locator)
             }
         }

        return null
    }

    String getSelectedLabel(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedLabel(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedLabel(locator)
             }
         }

        return null
    }

    String[] getSelectedValues(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedValues(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedValues(locator)
             }
         }

        return null
    }

    String getSelectedValue(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedValue(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedValue(locator)
             }
         }

        return null
    }

    String[] getSelectedIndexes(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedIndexes(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedIndexes(locator)
             }
         }

        return null
    }

    String getSelectedIndex(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedIndex(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedIndex(locator)
             }
         }

        return null
    }

    String[] getSelectedIds(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedIds(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedIds(locator)
             }
         }

        return null
    }

    String getSelectedId(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getSelectedId(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectedId(locator)
             }
         }

        return null
    }

    boolean isSomethingSelected(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.isSomethingSelected(){ loc ->
                String locator = locatorMapping(context, loc) 
                accessor.isSomethingSelected(locator)
             }
         }

        return false
    }

    String waitForText(String uid, int timeout){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.waitForText(timeout){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.waitForText(locator, timeout)
        }
    }

    int getTableMaxRowNum(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getTableMaxRowNum(){ loc ->
                String locator = locatorMapping(context, loc)
                locator
             }
         }
         
         println("Cannot find table ${uid}")
         return 0
    }

    int getTableMaxColumnNum(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getTableMaxColumnNum(){ loc ->
                String locator = locatorMapping(context, loc)
                locator
             }
         }

         println("Cannot find table ${uid}")
         return 0
    }

    int getListSize(String uid){
          WorkflowContext context = WorkflowContext.getDefaultContext()
         List obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.getListSize(){ loc ->
                String locator = locatorMapping(context, loc)
                locator
             }
         }

         println("Cannot find list ${uid}")
         return 0
    }

    //uid should useString the format table2[2][3] for Table or list[2] for List
    def getUiElement(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)

         return obj
    }

    boolean isElementPresent(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.isElementPresent(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.isElementPresent(locator)
             }
         }

         return false
    }

    boolean isVisible(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.isVisible(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.isVisible(locator)
             }
         }

         return false
    }

    boolean isChecked(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.isChecked(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.isChecked(locator)
             }
         }
        
         throw RuntimeException("Cannot find UI Object ${uid})")
    }

    def isDisabled(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.isDisabled (){loc ->
                 String locator = locatorMapping(context, loc)
                 accessor.isDisabled(locator);
             }
         }
    }

    def isEnabled(String uid){
        return !isDisabled(uid);
    }


    boolean waitForElementPresent(String uid, int timeout){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.waitForElementPresent(timeout){ loc ->
                String locator = locatorMapping(context, loc) 
                accessor.waitForElementPresent(locator, timeout)
             }
         }

         return false
    }

    boolean waitForElementPresent(String uid, int timeout, int step){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)
         if(obj != null){
             return obj.waitForElementPresent(timeout, step){ loc ->
                String locator = locatorMapping(context, loc) 
                accessor.waitForElementPresent(locator, timeout, step)
             }
         }

         return false
   }

    boolean waitForCondition(String script, String timeoutInMilliSecond){
       Accessor.waitForCondition(script, timeoutInMilliSecond)
    }

    String getText(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.getText(){ loc ->
            String locator = locatorMapping(context, loc)
            accessor.getText(locator)
        }
    }

    String getValue(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.getValue(){ loc ->
            String locator = locatorMapping(context, loc)
            accessor.getValue(locator)
        }
    }

    def pause(int milliseconds){
        Helper.pause(milliseconds)
    }

    String getLink(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, uid)?.getLink(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageSource(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, uid)?.getImageSource(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageAlt(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, uid)?.getImageAlt(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageTitle(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, uid)?.getImageTitle(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    def getAttribute(String uid, String attribute){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         ui.walkTo(context, uid)?.getAttribute(attribute){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
         }
    }

    def submit(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.submit(){ loc ->
            String locator = locatorMapping(context, loc)
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

    String locatorMapping(WorkflowContext context, loc ){
        //get ui object's locator
        String locator = locatorProcessor.locate(loc)

        //get the reference locator all the way to the ui object
        if(context.getReferenceLocator() != null)
            locator = context.getReferenceLocator() + locator

        //make sure the xpath starts with "//"
        if(locator != null && (!locator.startsWith("//"))){
            locator = "/" + locator
        }
        
        return locator
    }
}