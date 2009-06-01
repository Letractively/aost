package org.tellurium.object

import org.tellurium.dsl.WorkflowContext
import org.tellurium.dsl.UiID
import org.tellurium.locator.LocatorProcessor
import org.tellurium.locator.GroupLocateStrategy
import org.json.simple.JSONObject

/**
 *  container
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Container extends UiObject {

    public static final String GROUP = "group"

    //if it uses group informtion to infer its locator
    protected boolean useGroupInfo = false

    //If you have Ajax application and the container's children keep changing
    //It is wise to force the children not to use cache
    protected boolean noCacheForChildren = false

    //since we use map, the component name must be unique
    def components = [:]

    def add(UiObject component){
        components.put(component.uid, component)
    }

    def getComponent(String id){
        components.get(id)
    }

    public boolean useGroup(){
      return this.useGroupInfo
    }

    public JSONObject toJSON() {

      return buildJSON(){jso ->
        jso.put(UI_TYPE, "Container")
        if(useGroupInfo)
          jso.put(GROUP, this.useGroupInfo)
      }
    }
  
    protected void groupLocating(WorkflowContext context){
      if (this.useGroupInfo) {
        if(context.isUseJQuerySelector()){
          context.appendReferenceLocator(GroupLocateStrategy.select(this))
        }else{
          //need to use group information to help us locate the container xpath
          context.appendReferenceLocator(GroupLocateStrategy.locate(this))
        }
      } else {
        //do not use the group information, process as regular
        def lp = new LocatorProcessor()
        context.appendReferenceLocator(lp.locate(context, this.locator))
      }
    }

    public void traverse(WorkflowContext context){
      context.appendToUidList(context.getUid())
      components.each {key, component->
        context.pushUid(key)
        component.traverse(context)
      }
      context.popUid()
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
//              context.appendReferenceLocatorForUiObject(this)
                groupLocating(context)

/*                if(this.useGroupInfo){
                    //need to use group information to help us locate the container xpath
                    context.appendReferenceLocator(GroupLocateStrategy.locate(this))
                }else{
                    //do not use the group information, process as regular
                    def lp = new LocatorProcessor()
                    context.appendReferenceLocator(lp.locate(this.locator))
                }*/
              
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