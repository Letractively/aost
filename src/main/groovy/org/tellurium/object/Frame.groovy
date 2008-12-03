package org.tellurium.object

import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext

/**
 * Object for Frame or IFrame
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 28, 2008
 * 
 */
class Frame extends Container{

    private String id;

    private String name;

    private String title;

    void selectFrame(Closure c){
        c(name)
    }

    void selectParentFrame(Closure c){
        c("relative=parent")
    }

    void selectTopFrame(Closure c){
        c("relative=top")
    }

    boolean getWhetherThisFrameMatchFrameExpression(String target, Closure c){
       c(name, target)
    }

    void waitForFrameToLoad(int timeout, Closure c){
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