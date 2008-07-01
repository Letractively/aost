package example.aost

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
       assertNotNull(table1.findObject("main.table1"))
   }

    void testTable2(){
       Table2 table2 = new Table2()
       table2.defineUi()
       assertNotNull(table2.findObject("main.table1"))
   }

   void testTable3(){
       Table3 table3 = new Table3()
       table3.defineUi()
       assertNotNull(table3.findObject("main.table1"))
   }

   void testTable4(){
       Table4 table4 = new Table4()
       table4.defineUi()
       assertNotNull(table4.findObject("main.table1"))
   }

}