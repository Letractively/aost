package aost.object

import aost.dsl.WorkflowContext
import aost.dsl.UiID
import aost.locator.LocatorProcessor
import aost.access.Accessor
import aost.locator.GroupLocateStrategy

/**
 * Abstracted class for a list, which holds one dimension array of Ui objects
 * similar to Table, but table is two dimensions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class List  extends Container{

     public static final String ALL_MATCH = "ALL";

     protected TextBox defaultUi = new TextBox()

     @Override
     def add(UiObject component){
        if(validId(component.uid)){
            String internId = internalId(component.uid)
            components.put(internId, component)
        }else{
            System.out.println("Warning: Invalid id: ${component.uid}")
        }
     }

    //the separator for the list, it is empty by default
    String separator = ""

     //should validate the uid before call this to convert it to internal representation
     public static String internalId(String id){

         //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        return "_${upperId}"
     }

     public UiObject findUiObject(int index){

        //first check _index format
        String key = "_${index}"
        UiObject obj = components.get(key)

        //then, check _ALL format
        if(obj == null){
          key = "_ALL"
          obj = components.get(key)
        }

        return obj
     }

     public boolean validId(String id){
        //UID cannot be empty
        if(id == null || id.trim().isEmpty())
          return false

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        //check match all case
        if(ALL_MATCH.equals(upperId))
            return true
        return (upperId ==~ /[0-9]+/)
     }

     String getListLocator(int index){
         if(separator == null)
            return ""

         return  separator + "[${index}]"
     }

    int getListSize(Closure c) {
        int index = 1

        String rl = c(this.locator)

        Accessor accessor = new Accessor()

        while(accessor.isElementPresent(rl + getListLocator(index))){
            index++
        }

        index--

        return index
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){

        //if not child listed, return itself
        if(uiid.size() < 1)
            return this

        String child = uiid.pop()

        String part = child.replaceFirst('_', '')

        int nindex = Integer.parseInt(part)

        //otherwise, try to find its child
        UiObject cobj = this.findUiObject(nindex)

        //If cannot find the object as the object template, return the TextBox as the default object
        if(cobj == null){
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
            if (this.useGroupInfo) {
                //need to use group information to help us locate the container xpath
                context.appendReferenceLocator(GroupLocateStrategy.locate(this))
            } else {
                //do not use the group information, process as regular
                def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(this.locator))
            }
        }

        //append relative location, i.e., index to the locator
        String loc = getListLocator(nindex)
        context.appendReferenceLocator(loc)

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }
    }

}