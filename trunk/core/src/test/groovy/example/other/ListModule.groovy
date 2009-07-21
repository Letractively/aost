package example.other

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class ListModule extends DslContext {
     public void defineUi() {
       ui.Container(uid: "rotator", clocator: [tag: "div", class: "thumbnails"]) {
         List(uid: "tnails", clocator: [tag: "ul"], separator: "li") {
           UrlLink(uid: "all", clocator: [:])
         }
       }
     }  
}