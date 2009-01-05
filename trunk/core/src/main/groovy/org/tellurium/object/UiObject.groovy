package org.tellurium.object

import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.event.Event

/**
 *  Basic UI object
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObject {
    /*
      Let UI Object be a pure data structure and only include placeholders for
      methods wich should be responsed to.

      decoupling them and let them be handled by DSL ddc framework


      TODO: some ui object must be reactive to certain Events such as kekUP,
       We must add that feature in so that the UI object will automatically add
       the event during the simulation.

      Another good idea is to let the ui object to be specific in the last element
      in the xpath, the element before it should be just a reference xpath.
      For example, an input box should be //input[@attribute] for the last part of
      the xpath. A url link usually should be //a[@attribute]
      In that way, we can have (reference xpath + inherent xpath) to be the actual
      xpath.
      For example,
        An input box has the xpath
           //div[contains(@uid,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@uid,'jupiter_widget_SearchTextbox')]/descendant::input[@type='button' and @dojoattachpoint='dap_findBtn']
        Here
           //div[contains(@uid,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@uid,'jupiter_widget_SearchTextbox')]

        is the reference xpath and
           input[@type='button' and @dojoattachpoint='dap_findBtn'] is the inherent xpath, i.e., it is specific for that ui object

       The same idea can be applied to a group of composited ui objects.

       The composited ui objects always come with a reference xpath and each ui object inside the composited ui object
       has the xpath in the format:
            group reference xpath + reference xpath (related the the group reference xpath) + inherent xpath
            
      */

    String uid
    String namespace
    
    def locator
//    def feature

    //reference back to its parent
    def Container parent

    //respond to JavaScript events
    String[] respondToEvents

    def mouseOver(Closure c){
        c(locator)
    }

    def mouseOut(Closure c){
        c(locator)
    }

    boolean isElementPresent(Closure c){
        return c(locator)
    }

    boolean isVisible(Closure c){
        return c(locator)
    }

    boolean isDisabled(Closure c){
        return c(locator)
    }

    boolean waitForElementPresent(int timeout, Closure c){
        return c(locator)
    }

    boolean waitForElementPresent(int timeout, int step, Closure c){
        return c(locator)
    }

    String getText(Closure c){
       return c(locator)
    }

    def getAttribute(String attribute, Closure c){
        return c(locator, "@${attribute}")    
    }

    def methodMissing(String name, args) {
         //check if the click action is used and if the object can respond to the "Click" event
         //if it is, then call the "click" method, i.e., the innerClick method here
         if("click".equals(name) && isEventIncluded(Event.CLICK)){
             return this.invokeMethod("innerClick", args)
         }

        throw new MissingMethodException(name, UiObject.class, args)
    }

    boolean isEventIncluded(Event event){

        if (respondToEvents != null && event != null){
            for(String resp : respondToEvents){
                if(event.toString().equalsIgnoreCase(resp))
                    return true
            }
        }

        return false
    }

    protected innerClick(Closure c){
        c(locator)
    }

    //walkTo through the object tree to until the Ui Object is found by the UID
    public UiObject walkTo(WorkflowContext context, UiID uiid){
        //if not child listed, return itself
        if(uiid.size() < 1)
            return this           

        //otherwise,
        //by default, regular ui objects cannot walkTo forward to the child
        // since only Container can have children, override this method
        // for container or its subclasses

        //cannot find child
        String child = uiid.pop()
        println("Cannot find UI Object ${child} in ${this.uid}")

        return null
    }
}