package aost.object

import aost.dsl.WorkflowContext
import aost.dsl.UiID
import aost.locator.LocatorProcessor

class Container extends UiObject {

    //since we use map, the component name must be unique
    def components = [:]

    def add(UiObject component){
        components.put(component.id, component)
    }

    def getComponent(String id){
        components.get(id)
    }

    //walk through the object tree to until the UI object is found by the ID from the stack
    @Override
    public Object walk(WorkflowContext context, UiID uiid){

        //if not child listed, return itself
        if(uiid.size() < 1)
            return this

        String child = uiid.pop()

        //otherwise, try to find its child
        UiObject cobj = components.get(child)

        //if found the object
        if(cobj != null){
            //update reference locator by append the relative locator for this container
            if(this.locator != null){
                def locatorProcessor = new LocatorProcessor()
                context.appendReferenceLocator(locatorProcessor.locate(this.locator))
            }
            if(uiid.size() < 1){
                //not more child needs to be found
                return child
            }else{
                //recursively call walk until the object is found
                return cobj.walk(context, uiid)
            }
        }else{

            //cannot find the object
            println("Cannot find UI Object ${child} in ${this.id}")

            return null
        }
    }
}