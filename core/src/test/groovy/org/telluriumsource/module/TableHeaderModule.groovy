package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

class TableHeaderModule extends DslContext
{
  public void defineUi() {
    ui.Table(uid:'sectionRaces', clocator:[class:'table']){
          TextBox(uid:'{header:any} as SubHeader', clocator:[class:'tableHeader'])
          UrlLink(uid:"{row:all, column:3}", clocator:[:])
          TextBox(uid:"{row:all, column:all}", clocator:[:])
    }
  }

}

