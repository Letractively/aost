package example.other

import org.tellurium.dsl.DslContext

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
           <div>Type</div>
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
           <div>
               <a href="http://localhost">Server</a>
           </div>
           <div>
               <img src="img.png" alt="IMG"/>
           </div>
           <div>
               <span id="features" style="font-weight: bold;">Features</span>
           </div>
       </div>
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
   <div>
       <div id="foot1">
           <div>Data</div>
       </div>
       <div id="foot2">
           <div>Bezeichnung</div>
       </div>
       <div id="foot3">
           <div>Type</div>
       </div>
   </div>
</div>
"""

    public void defineUi() {
      ui.GenericTable(uid: "table", clocator: [tag: "div", id: "table"]){
        Header(uid: "header", clocator: [tag: "div"], rtag: "div", ctag: ""){
          TextBox(uid: "header: 1 as data", clocator: [tag: "div", text: "Data"])
          TextBox(uid: "bc", clocator: [tag: "div", text: "Bezeichnung"])
          TextBox(uid: "header: 3 as type, inside: true", clocator: [tag: "div", text: "Type"])
        }
        Body(uid: "tbody: 1 as client", clocator: [tag: "div", id: "client-area"], rtag: "div", ctag: "div"){
          UrlLink(uid: "(row: 1, column: data) as server", clocator: [:])
          Image(uid: "(row: 1 as first, column: 2) as image", clocator: [alt: "IMG"])
          Span(uid: "row: first, column: 3 as features", clocator: [id: "features"])
          TextBox(uid: "all")
        }
        Foot(uid: "foot", clocator: [tag: "div"], rtag: "div", ctag: ""){
          TextBox(uid: "foot: 1", clocator: [tag: "div", text: "Data"])
          TextBox(uid: "foot: 2", clocator: [tag: "div", text: "Bezeichnung"])
          TextBox(uid: "foot: 3", clocator: [tag: "div", text: "Type"])
        }
      }
    }
}