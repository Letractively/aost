package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 12, 2010
 * 
 */
class DisplayModule extends DslContext {

   public void defineUi() {
     ui.Container(uid: "Container1", clocator: [tag: "div", id: "Container1"])
   }
  
}
