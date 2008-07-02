package aost.object

import aost.object.UiObject
import aost.locator.BaseLocator

/**
 *   Table should be very generic since each column and row could hold different
 *   UI objects. In that sense, table is also a container
 *
 *   However, all the UI objects inside the table should have related xpath. Also,
 *   the UI object id should carry informatio about table row, table column.
 *   It also has wild case to match all rows, all columns, all both.
 *
 *   That is to say, for the i-th row, j-th column, the id should use the following
 *   name conversion:
 *
 *    "row: i, column: j"
 *
 *   note that row and column starts from 1. Uppercase or lowercase would both be fine.

 *   The wild case for row or column is "*", or you do not specify the row, or column
 *   and for all, you can use "all" or "ALL".
 *
 *   As a result, the following names are valid (case insensitive):
 *
 *   "row : 1, column : 3"         //first row, 3rd column
 *   "row : 5"                    //5th row, all columns
 *   "column : 6"                 //6th column, all rows
 *
 *   "row : *, column : 3"         //3rd column, all rows
 *   "row : 5, column : *"         //5th row, all columns
 * 
 *   "all"                       //all rows, all columns
 *
 *   The internal representation is
 *
 *   "_i_j"
 *
 *   and wild case will be replace by ALL. Thus, for a given i-th row, j-th-column,
 *   the search chain would look as follows,
 *
 *   _i_j
 *
 *   _ALL_j
 *
 *   _i_ALL
 *
 *   _ALL_ALL
 *
 *   By default, the UI Object is a text box and there is no need to specify it.
 *   we can always get back the text by specifying the row number and column number.
 *
 *   If not one could be found by the above search chain, it will assume that you
 *   are using the text box.
 * 
 *   
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */


class Table extends Container{

     public static final String ID_SEPARATOR = ",";
     public static final String ID_WILD_CASE = "*";
     public static final String ID_FIELD_SEPARATOR = ":";
     public static final String ALL_MATCH = "ALL";
     public static final String ROW = "ROW";
     public static final String COLUMN = "COLUMN";

     @Override
     def add(UiObject component){
        if(validId(component.id)){
            String internId = internalId(component.id)
            components.put(internId, component)
        }else{
            System.out.println("Warning: Invalid id: ${component.id}")
        }
     }
    
/*
     @Override
     def getComponent(String id){
        components.get(id)
     }
*/

     //should validate the id before call this to convert it to internal representation
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
           if(ROW.equals(fields[0])){
               row = fields[1]
               if(ID_WILD_CASE.equals(row))
                row = "ALL"
               column = "ALL"
           }

           if(COLUMN.equals(fields[0])){
               column = fields[1]
               if(ID_WILD_CASE.equals(column))
                column = "ALL"
               row = "ALL"
           }
        }else{
           for(int i=0; i<2; i++){
             String[] fields = parts[i].trim().split(ID_FIELD_SEPARATOR)
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

     public UiObject findUiObject(int row, int column){

        //first check _i_j format
        UiObject obj = components.get("_${row}_${column}")

        //then, check _ALL_j format
        if(obj == null)
          obj = components.get("_ALL_${column}")

        //thirdly, check _i_ALL format
        if(obj == null)
          obj = components.get("_${row}_ALL")

        //last, check ALL format
        if(obj == null)
          obj = components.get("ALL")
         
        return obj
     }

     public boolean validId(String id){
        //ID cannot be empty
        if(id == null || id.trim().isEmpty())
          return false

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        //check match all case
        if(ALL_MATCH.equals(upperId))
            return true

        String[] parts = upperId.split(ID_SEPARATOR)
        //should not be more than two parts, i.e., column part and row part
        if(parts.length > 2)
            return false

        if(parts.length <= 1){
            //If only one part is specified
            return validateField(parts[0])
        }else{
            return validateField(parts[0]) && validateField(parts[1])
        }

     }

     protected boolean validateField(String field){
         if(field == null || field.trim().isEmpty())
            return false

         String[] parts = field.trim().split(ID_FIELD_SEPARATOR)
         if(parts.length != 2)
            return false

         parts[0] = parts[0].trim()
         parts[1] = parts[1].trim()
         
         //must start with "ROW" or "COLUMN"
         if(!ROW.equals(parts[0]) && !COLUMN.equals(parts[0]))
            return false

         if(ID_WILD_CASE.equals(parts[1]))
            return true
         else{
             return (parts[1] ==~ /[0-9]+/)
         }
     }

     def String getCellLocator(int row, int column, Closure c){
         String table_cell_template = "/tbody/tr[${row}]/td[${column}]"
         BaseLocator bl = new BaseLocator(loc : locator.loc +table_cell_template)
         c(bl)
     }
}
