package org.tellurium.test

import org.tellurium.dsl.WorkflowContext
import org.tellurium.object.InputBox
import org.tellurium.object.TextBox
import org.tellurium.object.UiObject
import org.tellurium.object.UrlLink

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 2, 2008
 * 
 */
class List_UT extends GroovyTestCase{

    public void testListXPath() {
        List1 list = new List1()
        list.defineUi()
        WorkflowContext context = WorkflowContext.getDefaultContext()
        UiObject obj = list.ui.walkTo(context, "sample[1].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals(context.getReferenceLocator(), "/descendant-or-self::div/descendant::table[1]/descendant-or-self::table/tbody/tr[child::td][1]/td[1]")

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[2].text")
        assertNotNull(obj)
        assertTrue(obj instanceof TextBox)
        assertEquals(context.getReferenceLocator(), "/descendant-or-self::div/descendant::div[1]/descendant-or-self::div")

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[3].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals(context.getReferenceLocator(), "/descendant-or-self::div/descendant::table[2]/descendant-or-self::table/tbody/tr[child::td][1]/td[1]")

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[4]")
        assertNotNull(obj)
        assertTrue(obj instanceof InputBox)
        assertEquals(context.getReferenceLocator(), "/descendant-or-self::div/descendant::input[1]")

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[5].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals(context.getReferenceLocator(), "/descendant-or-self::div/descendant::table[3]/descendant-or-self::table/tbody/tr[child::td][1]/td[1]")
   }

}