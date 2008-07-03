package aost.object

import aost.dsl.WorkflowContext
import aost.dsl.UiID

abstract class UiObject {
    /*
      Let UI Object be a pure data structure and only include placeholders for
      methods wich should be responsed to.

      decoupling them and let them be handled by DSL context framework


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
           //div[contains(@id,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@id,'jupiter_widget_SearchTextbox')]/descendant::input[@type='button' and @dojoattachpoint='dap_findBtn']
        Here
           //div[contains(@id,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@id,'jupiter_widget_SearchTextbox')]

        is the reference xpath and
           input[@type='button' and @dojoattachpoint='dap_findBtn'] is the inherent xpath, i.e., it is specific for that ui object

       The same idea can be applied to a group of composited ui objects.

       The composited ui objects always come with a reference xpath and each ui object inside the composited ui object
       has the xpath in the format:
            group reference xpath + reference xpath (related the the group reference xpath) + inherent xpath
            
      */

    String id
    String namespace

	def locator
    def feature

    //reference back to its parent
    def Container parent

    def boolean isElementPresent(Closure c){
        return c(locator)
    }

    def boolean isVisible(Closure c){
        return c(locator)
    }

    def boolean waitForElementPresent(int timeout, Closure c){
        return c(locator)
    }

    def boolean waitForElementPresent(int timeout, int step, Closure c){
        return c(locator)
    }

    //walk through the object tree to until the Ui Object is found by the ID
    public Object walk(WorkflowContext context, UiID uiid){
        //if not child listed, return itself
        if(uiid.size() < 1)
            return this           

        //otherwise,
        //by default, regular ui objects cannot walk forward to the child
        // since only Container can have children, override this method
        // for container or its subclasses

        //cannot find child
        println("Cannot find UI Object ${child} in ${this.id}")

        return null
    }
}