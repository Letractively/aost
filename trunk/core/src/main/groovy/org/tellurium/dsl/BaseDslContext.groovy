package org.tellurium.dsl

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiDslParser
import org.tellurium.dsl.WorkflowContext
import org.tellurium.event.EventHandler
import org.tellurium.exception.UiObjectNotFoundException
import org.tellurium.locator.LocatorProcessor
import org.tellurium.object.List
import org.tellurium.object.Table
import org.tellurium.object.UiObject
import org.tellurium.util.Helper

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 * 
 */
abstract class BaseDslContext {
    //later on, may need to refactor it to use resource file so that we can show message for different localities
    protected static final String ERROR_MESSAGE = "Cannot find UI Object";

    UiDslParser ui = new UiDslParser()

    //decoupling eventhandler, locateProcessor, and accessor from UI objects
    //and let DslContext to handle all of them directly. This will also make
    //the framework reconfiguration much easier.
    EventHandler eventHandler = new EventHandler()
    Accessor accessor = new Accessor()
    LocatorProcessor locatorProcessor = new LocatorProcessor()

    abstract protected String locatorMapping(WorkflowContext context, loc )

    //uid should use the format table2[2][3] for Table or list[2] for List
    def getUiElement(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = ui.walkTo(context, uid)

         return obj
    }

    def UiObject walkToWithException(WorkflowContext context, String uid){
        UiObject obj = ui.walkTo(context, uid)
        if(obj != null)
          return obj

         throw new UiObjectNotFoundException("${ERROR_MESSAGE} ${uid}")
    }

    def mouseOver(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()

        walkToWithException(context, uid)?.mouseOver(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.mouseOver(locator)
        }
    }

    def mouseOut(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()

        walkToWithException(context, uid)?.mouseOut(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.mouseOut(locator)
        }
    }
  
    def dragAndDrop(String uid, String movementsString){
        WorkflowContext context = WorkflowContext.getDefaultContext()

        walkToWithException(context, uid)?.dragAndDrop(movementsString){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.dragAndDrop(locator, movementsString)
        }
    }

    def dragAndDropTo(String sourceUid, String targetUid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def src = walkToWithException(context, sourceUid)

        WorkflowContext ncontext = WorkflowContext.getDefaultContext()
        def target = walkToWithException(ncontext, targetUid)

        if(src != null && target != null){
          String srcLocator = locatorMapping(context, src.locator)
          String targetLocator = locatorMapping(ncontext, target.locator)
          eventHandler.dragAndDropToObject(srcLocator, targetLocator)
        }
    }

    def click(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.click(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.click(locator, events)
        }
    }

    def doubleClick(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.doubleClick(){ loc, String[] events ->
           String locator = locatorMapping(context, loc)
            eventHandler.doubleClick(locator, events)
        }
    }

    def clickAt(String uid, String coordination){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.clickAt(coordination){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.clickAt(locator, coordination, events)
        }
    }

    def check(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.check(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.check(locator, events)
        }
    }

    def uncheck(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.uncheck(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.uncheck(locator, events)
        }
    }

    def type(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.type(input){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.type(locator, input, events)
        }
    }

    def keyType(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.keyType(input){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.keyType(locator, input, events)
        }
    }

    def typeAndReturn(String uid, String input){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.typeAndReturn(input){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.typeAndReturn(locator, input, events)
        }
    }

    def clearText(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.clearText(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.clearText(locator, events)
        }
    }

    def select(String uid, String target){
        selectByLabel(uid, target)
    }

    def selectByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectByLabel(target){ loc, optloc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.select(locator, optloc, events)
        }
    }

    def selectByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectByValue(target){ loc, optloc, String[] events ->
            String locator = locatorMapping(context, loc)
            eventHandler.select(locator, optloc, events)
        }
    }

    def addSelectionByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.addSelectionByLabel(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.addSelection(locator, optloc)
        }
    }

    def addSelectionByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.addSelectionByValue(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.addSelection(locator, optloc)
        }
    }

    def removeSelectionByLabel(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.removeSelectionByLabel(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeSelectionByValue(String uid, String target){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.removeSelectionByValue(target){ loc, optloc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeSelection(locator, optloc)
        }
    }

    def removeAllSelections(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.removeAllSelections(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.removeAllSelections(locator)
        }
    }

    String[] getSelectOptions(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = walkToWithException(context, uid)
         if(obj != null){
             return obj.getSelectOptions(){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.getSelectOptions(locator)
             }
         }

        return null
    }

    String[] getSelectedLabels(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
        walkToWithException(context, uid)?.waitForText(timeout){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.waitForText(locator, timeout)
        }
    }

    int getTableHeaderColumnNum(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = (Table)walkToWithException(context, uid)
         if(obj != null){
             return obj.getTableHeaderColumnNum{ loc ->
                String locator = locatorMapping(context, loc)
                locator
             }
         }

         println("Cannot find table ${uid}")
         return 0
    }

    int getTableMaxRowNum(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         Table obj = (Table)walkToWithException(context, uid)
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
         Table obj = (Table)walkToWithException(context, uid)
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
         List obj = (List)walkToWithException(context, uid)
         if(obj != null){
             return obj.getListSize(){ loc ->
                String locator = locatorMapping(context, loc)
                locator
             }
         }

         println("Cannot find list ${uid}")
         return 0
    }

    boolean isElementPresent(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
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
        def obj = walkToWithException(context, uid)
         if(obj != null){
             return obj.isDisabled (){loc ->
//                String locator = locatorMapping(context, loc)
//                accessor.isDisabled(locator)
                 String locator = locatorMapping(context, loc) + "/self::node()[@disabled]"
                 accessor.isElementPresent(locator)
             }
         }
    }

    def isEnabled(String uid){
        return !isDisabled(uid);
    }

    boolean waitForElementPresent(String uid, int timeout){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         def obj = walkToWithException(context, uid)
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
         def obj = walkToWithException(context, uid)
         if(obj != null){
             return obj.waitForElementPresent(timeout, step){ loc ->
                String locator = locatorMapping(context, loc)
                accessor.waitForElementPresent(locator, timeout, step)
             }
         }

         return false
   }

    boolean waitForCondition(String script, int timeoutInMilliSecond){
       accessor.waitForCondition(script, Integer.toString(timeoutInMilliSecond))
    }

    String getText(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getText(){ loc ->
            String locator = locatorMapping(context, loc)
            accessor.getText(locator)
        }
    }

    String getValue(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getValue(){ loc ->
            String locator = locatorMapping(context, loc)
            accessor.getValue(locator)
        }
    }

    def pause(int milliseconds){
        Helper.pause(milliseconds)
    }

    String getLink(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         walkToWithException(context, uid)?.getLink(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageSource(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         walkToWithException(context, uid)?.getImageSource(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageAlt(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         walkToWithException(context, uid)?.getImageAlt(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    String getImageTitle(String uid){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         walkToWithException(context, uid)?.getImageTitle(){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
        }
    }

    def getAttribute(String uid, String attribute){
         WorkflowContext context = WorkflowContext.getDefaultContext()
         walkToWithException(context, uid)?.getAttribute(attribute){ loc, attr ->
            String locator = locatorMapping(context, loc)
            accessor.getAttribute(locator + attr )
         }
    }

    def hasCssClass(String uid, String cssClass){
       WorkflowContext context = WorkflowContext.getDefaultContext()
        String[] strings = walkToWithException(context, uid)?.hasCssClass(){ loc, classAttr ->
            String locator = locatorMapping(context, loc)
            ((String)accessor.getAttribute(locator + classAttr ))?.split(" ")
         }
         if(strings?.length){
           for(i in 0..strings?.length){
            if(cssClass.equalsIgnoreCase(strings[i])){
              return true
            }
          }
        }
      return false
    }

    def submit(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.submit(){ loc ->
            String locator = locatorMapping(context, loc)
            eventHandler.submit(locator)
        }
    }

    boolean isEditable(String uid){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.isEditable(){ loc ->
            String locator = locatorMapping(context, loc)
            return accessor.isEditable(locator)
        }

        return false
    }

    void waitForPageToLoad(int timeout){
         accessor.waitForPageToLoad(Integer.toString(timeout))
    }

    Number getXpathCount(String xpath){
        return accessor.getXpathCount(xpath)
    }

    String getEval(String script){
        return accessor.getEval(script)
    }
}