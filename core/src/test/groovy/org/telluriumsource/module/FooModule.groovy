package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 17, 2011
 * 
 */
class FooModule extends DslContext {

  public void defineUi() {
    ui.UrlLink(uid: "TheLink", locator: "//a")
  }
}

