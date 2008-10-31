package org.tellurium.object

import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext

/**
 *  Prototype for windows
 * 
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Window  extends Container{

    private String id;

    private String name;

    private String title;

    void openWindow(String url, Closure c){
        c(name, url)
    }

    void selectWindow(Closure c){
        c(name)
    }

    boolean getWhetherThisWindowMatchWindowExpression(String target, Closure c){
       c(name, target)
    }

    void waitForPopUp(int timeout, Closure c){
       c(name, timeout)
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
            //do not need to update the reference locator for frame/iframe since it
            //includes a total new html body
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