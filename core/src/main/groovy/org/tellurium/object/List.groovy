package org.tellurium.object

import org.tellurium.access.Accessor
import org.tellurium.dsl.UiID
import org.tellurium.dsl.WorkflowContext
import org.tellurium.locator.GroupLocateStrategy
import org.tellurium.locator.LocatorProcessor

/**
 * Abstracted class for a list, which holds one dimension array of Ui objects
 * similar to Table, but table is two dimensions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class List extends Container {

    public static final String ALL_MATCH = "ALL";

    protected TextBox defaultUi = new TextBox()

    @Override
    def add(UiObject component) {
        if (validId(component.uid)) {
            String internId = internalId(component.uid)
            components.put(internId, component)
        } else {
            System.out.println("Warning: Invalid id: ${component.uid}")
        }
    }

    //the separator for the list, it is empty by default
    String separator = ""

    //should validate the uid before call this to convert it to internal representation
    public static String internalId(String id) {

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        return "_${upperId}"
    }

    public UiObject findUiObject(int index) {

        //first check _index format
        String key = "_${index}"
        UiObject obj = components.get(key)

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL"
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

        //check match all case
        if (ALL_MATCH.equals(upperId))
            return true
        return (upperId ==~ /[0-9]+/)
    }

    // example:
    // //div/descendant-or-self::table[2]/descendant-or-self::table
    protected String deriveListLocator(int index) {
        Map<String, Integer> tags = new HashMap<String, Integer>()
        UiObject last = null
        for (int i = 1; i <= index; i++) {
            UiObject obj = findUiObject(i)
            String tag = obj.locator.getTag()
            Integer occur = tags.get(tag)
            if (occur == null) {
                tags.put(tag, 1)
            } else {
                tags.put(tag, occur + 1)
            }
            if (i == index) {
                last = obj
            }
        }

        String lastTag = last.locator.getTag()
        Integer lastOccur = tags.get(lastTag)

        return "/child::${lastTag}[${lastOccur}]"
    }

    String getListLocator(int index) {
        if (separator == null || separator.trim().size() == 0)
            return deriveListLocator(index)

        return separator + "[${index}]"
    }

    int getListSize(Closure c) {
        int index = 1

        String rl = c(this.locator)

        Accessor accessor = new Accessor()

        while (accessor.isElementPresent(rl + getListLocator(index))) {
            index++
        }

        index--

        return index
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        String child = uiid.pop()

        String part = child.replaceFirst('_', '')

        int nindex = Integer.parseInt(part)

        //otherwise, try to find its child
        UiObject cobj = this.findUiObject(nindex)

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