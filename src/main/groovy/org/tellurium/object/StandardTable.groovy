package org.tellurium.object

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.locator.GroupLocateStrategy
import org.tellurium.locator.LocatorProcessor

/**
 * Standard table is in the format of
 *
 *  table
 *     thead
 *        tr
 *          td
 *          ...
 *          td
 *     tbody
 *        tr
 *          td
 *          ...
 *          td
 *        ...
 *     tfoot
 *        tr
 *          td
 *          ...
 *          td
 *
 * The above table format is used a lot in Java Script framework such as Dojo
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class StandardTable extends Container{
     public static final String TAG = "table"

     public static final String ID_SEPARATOR = ","
     public static final String ID_WILD_CASE = "*"
     public static final String ID_FIELD_SEPARATOR = ":"
     public static final String ALL_MATCH = "ALL"
     public static final String ROW = "ROW"
     public static final String COLUMN = "COLUMN"
     public static final String HEADER = "HEADER"
     public static final String FOOT = "FOOT"
    
     protected TextBox defaultUi = new TextBox()
     //add a map to hold all the header elements
     def headers = [:]
     //add a map to hold all the tfoot elements
     def foots = [:]

     @Override
     def add(UiObject component){
        if(validId(component.uid)){
            if(component.uid.toUpperCase().trim().startsWith(HEADER)){
                //this is a header
                String internHeaderId = internalHeaderId(component.uid)
                headers.put(internHeaderId, component)
            }else if(component.uid.toUpperCase().trim().startsWith(FOOT)){
                //this is a foot
                String internFootId = internalFootId(component.uid)
                foots.put(internFootId, component)
            }else{
                //this is a regular element
                String internId = internalId(component.uid)
                components.put(internId, component)
            }
        }else{
            System.out.println("Warning: Invalid id: ${component.uid}")
        }
     }

     public boolean hasHeader(){
         return (headers.size() > 0)
     }

     public static String internalHeaderId(String id){
         String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
         parts[0] = parts[0].trim()
         parts[1] = parts[1].trim()
         if(ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return "_ALL"
         else
            return "_${parts[1].toUpperCase()}"
     }

     public static String internalFootId(String id){
         String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
         parts[0] = parts[0].trim()
         parts[1] = parts[1].trim()
         if(ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return "_ALL"
         else
            return "_${parts[1].toUpperCase()}"
     }

     //should validate the uid before call this to convert it to internal representation
     public static String internalId(String id){
        String row
        String column

         //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

         //check match all case
        if(ALL_MATCH.equals(upperId)){
            row = "ALL"
            column = "ALL"

            return "_${row}_${column}"
        }

        String[] parts = upperId.split(ID_SEPARATOR);

        if(parts.length == 1){
           String[] fields = parts[0].trim().split(ID_FIELD_SEPARATOR)
           fields[0] = fields[0].trim()
           fields[1] = fields[1].trim()
           if(ROW.equals(fields[0])){
               row = fields[1]
               if(ID_WILD_CASE.equalsIgnoreCase(row))
                row = "ALL"
               column = "ALL"
           }

           if(COLUMN.equals(fields[0])){
               column = fields[1]
               if(ID_WILD_CASE.equalsIgnoreCase(column))
                column = "ALL"
               row = "ALL"
           }
        }else{
           for(int i=0; i<2; i++){
             String[] fields = parts[i].trim().split(ID_FIELD_SEPARATOR)
             fields[0] = fields[0].trim()
             fields[1] = fields[1].trim()
             if(ROW.equals(fields[0])){
               row = fields[1]
               if(ID_WILD_CASE.equals(row))
                row = "ALL"
             }

             if(COLUMN.equals(fields[0])){
               column = fields[1]
               if(ID_WILD_CASE.equals(column))
                column = "ALL"
             }
           }
        }

        return "_${row}_${column}"
     }

    public UiObject findHeaderUiObject(int index) {
        //first check _i format
        String key = "_${index}"
        UiObject obj = headers.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
            obj = headers.get(key)
        }

        return obj
    }

    public UiObject findFootUiObject(int index) {
        //first check _i format
        String key = "_${index}"
        UiObject obj = foots.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
            obj = foots.get(key)
        }

        return obj
    }

    public UiObject findUiObject(int row, int column) {

        //first check _i_j format
        String key = "_${row}_${column}"
        UiObject obj = components.get(key)

        //then, check _ALL_j format
        if (obj == null) {
            key = "_ALL_${column}"
            obj = components.get(key)
        }

        //thirdly, check _i_ALL format
        if (obj == null) {
            key = "_${row}_ALL"
            obj = components.get(key)
        }

        //last, check ALL format
        if (obj == null) {
            key = "_ALL_ALL"
            obj = components.get(key)
        }

        return obj
    }

    public boolean validId(String id) {
        //UID cannot be empty
        if (id == null || (id.trim().length() <= 0))
            return false

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()
        //check if this object is for the header in the format of
        // "header: 2", "header: all"
        if (upperId.startsWith(HEADER)) {
            return validateHeader(id)
        }

        //check if this object is for the foot in the format of
        // "foot: 2", "foot: all"
        if (upperId.startsWith(FOOT)) {
            return validateFoot(id)
        }

        //check match all case
        if (ALL_MATCH.equals(upperId))
            return true

        String[] parts = upperId.split(ID_SEPARATOR)
        //should not be more than two parts, i.e., column part and row part
        if (parts.length > 2)
            return false

        if (parts.length <= 1) {
            //If only one part is specified
            return validateField(parts[0])
        } else {
            return validateField(parts[0]) && validateField(parts[1])
        }

    }

    protected boolean validateFoot(String id) {
        if (id == null || (id.trim().length() <= 0))
            return false

        String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        if (!HEADER.equalsIgnoreCase(parts[0]))
            return false

        //check the template, which could either be "*", "all", or numbers
        if (ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected boolean validateHeader(String id) {
        if (id == null || (id.trim().length() <= 0))
            return false

        String[] parts = id.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        if (!HEADER.equalsIgnoreCase(parts[0]))
            return false

        //check the template, which could either be "*", "all", or numbers
        if (ID_WILD_CASE.equalsIgnoreCase(parts[1]) || ALL_MATCH.equalsIgnoreCase(parts[1]))
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected boolean validateField(String field) {
        if (field == null || (field.trim().length() <= 0))
            return false

        String[] parts = field.trim().split(ID_FIELD_SEPARATOR)
        if (parts.length != 2)
            return false

        parts[0] = parts[0].trim()
        parts[1] = parts[1].trim()

        //must start with "ROW" or "COLUMN"
        if (!ROW.equals(parts[0]) && !COLUMN.equals(parts[0]))
            return false

        if (ID_WILD_CASE.equals(parts[1]) )
            return true
        else {
            return (parts[1] ==~ /[0-9]+/)
        }
    }

    protected String getCellLocator(int row, int column) {

        return "/tbody/tr[${row}]/td[${column}]"
    }

    protected String getHeaderLocator(int column) {

        return "/tbody/thead/tr/td[${column}]"
    }

    protected String getFootLocator(int column) {

        return "/tbody/tfoot/tr/td[${column}]"
    }

    int getTableHeaderColumnNum(Closure c) {
        /*
        int column = 1

        String rl = c(this.locator)
        Accessor accessor = new Accessor()

        while (accessor.isElementPresent(rl + getHeaderLocator(column))) {
            column++
        }

        column--

        return column
        */

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody/thead/tr/td"
        int columnum = accessor.getXpathCount(xpath)

        return columnum

    }

    int getTableMaxRowNum(Closure c) {
/*
        int row = 1
        int column = 1

        String rl = c(this.locator)

        Accessor accessor = new Accessor()

        while (accessor.isElementPresent(rl + getCellLocator(row, column))) {
            row++
        }

        row--

        return row
*/

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody/tr/td[1]"
        int rownum = accessor.getXpathCount(xpath)

        return rownum
    }

    int getTableMaxColumnNum(Closure c) {
/*
        int row = 1
        int column = 1
        String rl = c(this.locator)
        Accessor accessor = new Accessor()

        while (accessor.isElementPresent(rl + getCellLocator(row, column))) {
            column++
        }

        column--

        return column
*/

        String rl = c(this.locator)
        Accessor accessor = new Accessor()
        String xpath = rl + "/tbody/tr[1]/td"

        int columnum = accessor.getXpathCount(xpath)

        return columnum
    }

    //walk to a regular UI element in the table
    protected walkToElement(WorkflowContext context, UiID uiid) {
        String child = uiid.pop()
        String[] parts = child.replaceFirst('_', '').split("_")

        int nrow = Integer.parseInt(parts[0])
        int ncolumn = Integer.parseInt(parts[1])
        //otherwise, try to find its child
        UiObject cobj = this.findUiObject(nrow, ncolumn)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
            if (this.useGroupInfo) {
                //need to useString group information to help us locate the container xpath
                context.appendReferenceLocator(GroupLocateStrategy.locate(this))
            } else {
                //do not useString the group information, process as regular
                def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(this.locator))
            }
        }

        //append relative location, i.e., row, column to the locator
        String loc = getCellLocator(nrow, ncolumn)

        context.appendReferenceLocator(loc)

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walk to a header UI element in the table
    protected walkToHeader(WorkflowContext context, UiID uiid) {
        //pop up the "header" indicator
        uiid.pop()
        //reach the actual uiid for the header element
        String child = uiid.pop()

        child = child.replaceFirst('_', '')
        int index = Integer.parseInt(child.trim())

        //try to find its child
        UiObject cobj = this.findHeaderUiObject(index)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
            if (this.useGroupInfo) {
                //need to useString group information to help us locate the container xpath
                context.appendReferenceLocator(GroupLocateStrategy.locate(this))
            } else {
                //do not useString the group information, process as regular
                def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(this.locator))
            }
        }

        //append relative location, i.e., row, column to the locator
        String loc =  getHeaderLocator(index)
        context.appendReferenceLocator(loc)

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walk to a foot UI element in the table
    protected walkToFoot(WorkflowContext context, UiID uiid) {
        //pop up the "foot" indicator
        uiid.pop()
        //reach the actual uiid for the foot element
        String child = uiid.pop()

        child = child.replaceFirst('_', '')
        int index = Integer.parseInt(child.trim())

        //try to find its child
        UiObject cobj = this.findHeaderUiObject(index)

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
            if (this.useGroupInfo) {
                //need to useString group information to help us locate the container xpath
                context.appendReferenceLocator(GroupLocateStrategy.locate(this))
            } else {
                //do not useString the group information, process as regular
                def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(this.locator))
            }
        }

        //append relative location, i.e., row, column to the locator
        String loc =  getFootLocator(index)
        context.appendReferenceLocator(loc)

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }

    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        String child = uiid.peek()

        if (child.trim().equalsIgnoreCase(HEADER)) {
            return walkToHeader(context, uiid)
        }else if(child.trim().equalsIgnoreCase(FOOT)){
            return walkToFoot(context, uiid)
        }else {
            return walkToElement(context, uiid)
        }
    }


}