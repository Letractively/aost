package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 20, 2010
 * 
 */
class ImageModule extends DslContext {
  public void defineUi() {
    ui.UrlLink(uid: "U3", clocator: [id: "substituterForm:enterRunA3"])
    ui.UrlLink(uid: "U2", clocator: [id: "substituterForm:enterRunA2"])
    ui.UrlLink(uid: "U1", clocator: [id: "substituterForm:enterRunA1"])
  }
}
