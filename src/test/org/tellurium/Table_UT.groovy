package org.tellurium

import org.tellurium.dsl.WorkflowContext
import org.tellurium.object.UiObject
import org.tellurium.object.Button
import org.tellurium.object.UrlLink

/**
 * Unit tests for Table
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Table_UT extends GroovyTestCase{

   void testTable1(){
       Table1 table1 = new Table1()
       table1.defineUi()
//       assertNotNull(table1.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table1.ui.walkTo(context, "table1[1][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[1]/td[1]")
   }

    void testTable2(){
       Table2 table2 = new Table2()
       table2.defineUi()
//       assertNotNull(table2.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table2.ui.walkTo(context, "table1[1][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[1]/td[1]")
   }

   void testTable3(){
       Table3 table3 = new Table3()
       table3.defineUi()
//       assertNotNull(table3.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table3.ui.walkTo(context, "table1[3][4]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[3]/td[4]")
   }

   void testTable4(){
       Table4 table4 = new Table4()
       table4.defineUi()
 //      assertNotNull(table4.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table4.ui.walkTo(context, "main.table1[2][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[2]/td[1]")
   }

}