package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 31, 2009
 *
 */

public class GenericTableModule extends DslContext {
    public static String HTML_BODY = """
 <div id="table">
   <div>
       <div id="name">
           <div>Data</div>
           <div>
               <img/>
           </div>
       </div>
       <div id="shortname">
           <div>Bezeichnung</div>
           <div>
               <img/>
           </div>
       </div>
       <div id="type">
           <div>Typ</div>
           <div>
               <img/>
           </div>
       </div>
   </div>
   <div>
       <div>
           <div/>
       </div>
   </div>
   <div>
       <div>
           <div/>
       </div>
   </div>
   <div id="client-area">
       <div>
           <div>Bildsystem</div>
           <div>Bildsystem</div>
           <div>Bildserver</div>
       </div>
       <div>
           <div>Partner</div>
           <div>Partner</div>
           <div>Bestandssystem</div>
       </div>
       <div>
           <div>MS</div>
           <div>MS</div>
           <div>MS</div>
       </div>
   </div>
</div>
"""

    public void defineUi() {
      ui.GenericTable(uid: "table", tagOfTable: "div", tagOfHead: "div",
              tagOfHeadRow: "div", tagOfHeadColumn: "div", tagOfBody: "div",
              tagOfBodyRow: "div", tagOfBodyColum: "div", clocator: [id: "table"]){
        TextBox(uid: "header: 1", clocator: [tag: "div", text: "Data", inside: "true"])
        TextBox(uid: "header: 2", clocator: [tag: "div", text: "Bezeichnung", inside: "true"])
        TextBox(uid: "header: 3", clocator: [tag: "div", text: "Type", inside: "true"])
        TextBox(uid: "all", clocator: [tag: "div", inside: "true"])
      }
    }
}