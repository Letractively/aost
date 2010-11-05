package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Aug 31, 2010
 * 
 */
class VireoModule extends DslContext {

  String postAction = "\"/vireo/admin/detail?update=true\""

  public void defineUi() {
    ui.Form(uid: "DegreeInformationForm", clocator: [tag: "form", method: "post", action: "${postAction}", class: "ds-interactive-divview-form", id: "aspect_vireo_admin_View_div_view-form"]) {
      List(uid: "degree", clocator: [tag: "select", id: "aspect_vireo_admin_View_field_degree"], separator: "option", respond: ["click"]) {
        Container(uid: "{all}", clocator: [:])
      }
    }
  }
}
