package org.aost.object

import org.aost.dsl.WorkflowContext
import org.aost.dsl.UiID
import org.aost.locator.LocatorProcessor
import org.aost.locator.GroupLocateStrategy

 /**
 *  container
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Container extends UiObject {

    //if it uses group informtion to infer its locator
    protected boolean useGroupInfo = false
    
    //since we useString map, the component name must be unique
    def components = [:]

    def add(UiObject component){
        components.put(component.uid, component)
    }

    def getComponent(String id){
        components.get(id)
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){

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
                if(this.useGroupInfo){
                    //need to useString group information to help us locate the container xpath
                    context.appendReferenceLocator(GroupLocateStrategy.locate(this))
                }else{
                    //do not useString the group information, process as regular
                    def lp = new LocatorProcessor()
                    context.appendReferenceLocator(lp.locate(this.locator))
                }
            }
            if(uiid.size() < 1){
                //not more child needs to be found
                return cobj
            }else{
                //recursively call walkTo until the object is found
                return cobj.walkTo(context, uiid)
            }
        }else{

            //cannot find the object
            println("Cannot find UI Object ${child} in ${this.uid}")

            return null
        }
    }
}