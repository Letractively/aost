package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

class DocumentShowModule extends DslContext {

   public void defineUi() {
       ui.List(uid:'DetailGroupCollection', clocator: [tag: 'div', class: 'detailGroupCollection'], separator: 'div') {
         Container(uid: "{all}"){
           TextBox(uid: 'DetailGroupTitle', clocator: [tag: 'div', class: 'detailGroupTitle'])
           Table(uid:'FieldTable', clocator: [class:'keyValueTable']){
               TextBox(uid: '{row: all, column: 1}', clocator: [tag: 'td'], self: 'true')
               TextBox(uid: '{row: all, column: 2}', clocator: [tag: 'td'], self: 'true')
           }           
         }
       }
   }

   /**
    * Should dump the table cell text for the 2nd keyValueTable
defined in HTML_BODY.
    */
   def getCellTextForFirstDetailGroup() {
       def tableCellText = getAllTableCellText("DetailGroupCollection[1].FieldTable")
       println("\ntableCellText: ${tableCellText.dump()}\n")
       tableCellText
   }

   /**
    * Should print a list size of 2 and the title 'Detail Group Title
B' for the 2nd keyValueGroup.
    */
   def printDetailGroupTitles() {
       println("\nlistSize: ${getListSize('DetailGroupCollection')}")
       println("\ntableCellTitle: ${getText('DetailGroupCollection[1].DetailGroupTitle')}\n")
   }

   public static String HTML_BODY ="""
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta content="text/html; charset=UTF-8" http-equiv="Content-
Type">
   <title>Show Document</title>
</head>
<body>
   <div class="detailGroupCollection">

       <div class="detailGroup">
           <div class="detailGroupTitle">Detail Group Title A</div>
           <table class="keyValueTable">
               <tbody>
               <tr class="odd">
                 <td id="document.key1" class="label">Key 1</td>
                 <td>1111</td>
               </tr>


               <tr class="even">
                 <td id="document.key2" class="label">Key 2</td>
                 <td>2222</td>
               </tr>


               <tr class="odd">
                 <td id="document.key3" class="label">Key 3</td>
                 <td>3333</td>
               </tr>


               <tr class="even">
                 <td id="document.key4" class="label">Key 4</td>
                 <td>4444</td>
               </tr>
               </tbody>
           </table>
       </div>

       <div class="detailGroup">
           <div class="detailGroupTitle">Detail Group Title B</div>
           <table class="keyValueTable">
               <tbody>
               <tr class="odd">
                 <td id="document.key5" class="label">Key 5</td>
                 <td>5555</td>
               </tr>


               <tr class="even">
                 <td id="document.key6" class="label">Key 6</td>
                 <td>6666</td>
               </tr>


               <tr class="odd">
                 <td id="document.key7" class="label">Key 7</td>
                 <td>7777</td>
               </tr>


               <tr class="even">
                 <td id="document.key8" class="label">Key 8</td>
                 <td>8888</td>
               </tr>
               </tbody>
           </table>
       </div>

   </div>

</body>
</html>
"""
}
