package aost.object

import aost.object.UiObject
import aost.locator.BaseLocator

/**
 * User: vivmon1
 * Date: May 8, 2008
 */


class Table extends UiObject{

     def String getCellLocator(int row, int column, Closure c){
         String table_cell_template = "/tbody/tr[${row}]/td[${column}]"
         BaseLocator bl = new BaseLocator(loc : locator.loc +table_cell_template)
         c(bl)
     }
}
