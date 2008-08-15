package org.tellurium.test

import org.tellurium.dsl.WorkflowContext
import org.tellurium.object.UiObject
import org.tellurium.object.Button
import org.tellurium.object.UrlLink
import org.tellurium.test.Table1
import org.tellurium.test.Table2
import org.tellurium.test.Table4
import org.tellurium.test.Table3
import org.tellurium.object.TextBox

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

   void testTable5(){
       Table5 table5 = new Table5()
       table5.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table5.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[1]/th[1]", context.getReferenceLocator())
       context = WorkflowContext.getDefaultContext()
       obj = table5.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[3]/td[2]")
   }

   void testTable6(){
       Table6 table6 = new Table6()
       table6.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table6.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[1]/th[1]", context.getReferenceLocator())
       
       context = WorkflowContext.getDefaultContext()
       obj = table6.ui.walkTo(context, "main.table1.header[2]")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("//table/tbody/tr[1]/th[2]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table6.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[3]/td[2]")
   }

   void testTable7(){
       Table7 table7 = new Table7()
       table7.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table7.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("//table/tbody/tr[1]/th[1]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table7.ui.walkTo(context, "main.table1.header[2]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[1]/th[2]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table7.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[3]/td[2]")
   }

   void testTable8(){
       Table8 table8 = new Table8()
       table8.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table8.ui.walkTo(context, "IdMenu[1][1")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("/div[@class='popup' and @id='pop_0']/descendant-or-self::table[descendant::*[text()=\"Sort Up\"] and descendant::*[text()=\"Sort Down\"] and descendant::*[text()=\"Hide Column\"]]/tbody/tr[1]/td[1]", context.getReferenceLocator())
   }
}