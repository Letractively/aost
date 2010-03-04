package org.tellurium.object

import org.tellurium.dsl.WorkflowContext
import org.tellurium.dsl.UiID
import org.tellurium.locator.LocatorProcessor
import org.tellurium.access.Accessor
import java.lang.RuntimeException

/**
 * Module for a menu that can be selected. Prototype for the Tellurium Issues page dot menu
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 7, 2008
 *
 */
class SelectMenu extends UiObject{   
    public static final String TAG = "div"

    String header = null

    //map to hold the alias name for the menu item in the format of "alias name" : "menu item"
    Map<String, String> aliasMap

    def click(Closure c){
        c(null)
    }

    def mouseOver(Closure c){
        c(null)
    }

    def mouseOut(Closure c){
        c(null)
    }

    public void addTitle(String header){
        this.header = header
    }
    
    public void addMenuItems(Map<String, String> menuItems){
        aliasMap = menuItems
    }

    protected getMenuItemLocator(WorkflowContext context, String menuItem){
        String xloc = context.getReferenceLocator()
        if(header != null){
            xloc = xloc + "/table[tbody/tr[1]/th[contains(text(),\"${header}\")]]"
        }
        else{
            xloc = xloc + "/table"
        }
        int start = 1
        if(header != null)
            start = 2
        int end = start + aliasMap.size()
        Accessor accessor = new Accessor()
        for(int i=start; i<=end; i++){
            String guess = xloc + "/tbody/tr[$i]/td/descendant::text()"
            if(!guess.startsWith("//")){
                guess =  "/" + guess 
            }
            int count = accessor.getXpathCount(guess)
            if(count < 1)
                throw new RuntimeException("Cannot find XPath ${guess}")

            String menuitem
            //there is no span tag inside the td tag
            if(count == 1){
                menuitem = accessor.getText(guess)
                if(menuItem.contains(menuitem)){
                    return guess
                }
            }else{
                //there are span tag inside the td tag
                guess = guess + "[3]"
                menuitem = accessor.getText(guess)
                if(menuItem.contains(menuitem)){
                    return guess
                }
            }
        }

        throw new RuntimeException("Cannot find menu item ${menuItem}")
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        //this is the alias name
        String child = uiid.pop()
        //try to find the real menu item
        if(aliasMap == null || aliasMap.isEmpty() || aliasMap.get(child) == null){
            return new RuntimeException( "Error: cannot find menu item for ${child}")
        }

        String realItem = aliasMap.get(child)

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {

            def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(this.locator))
        }

        //append relative location to the locator
        String loc = getMenuItemLocator(context, realItem)
        context.setReferenceLocator(loc)

        //For menu, do not allow child nodes, no more walk and just return itself
        return this
    }

}