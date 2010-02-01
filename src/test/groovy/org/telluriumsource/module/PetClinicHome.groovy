package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 1, 2010
 * 
 */
class PetClinicHome extends DslContext {

  public void defineUi(){
    ui.Container(uid: "Home", clocator: [tag: "div", id: "main"]) {
      TextBox(uid: "Welcome", clocator: [tag: "h2", text: "welcome"])
      Container(uid: "Menu", clocator: [tag: "ul"]) {
        UrlLink(uid: "FindOwner", clocator: [tag: "a", text: "Find owner", href: "/petclinic/owner/find"])
        UrlLink(uid: "ShowVete", clocator: [tag: "a", text: "Display all veterinarians", href: "/petclinic/clinic/vets"])
        UrlLink(uid: "Tutorial", clocator: [tag: "a", text: "Tutorial", href: "/petclinic/html/petclinic.html"])
      }
      UrlLink(uid: "GoHome", clocator: [tag: "a", text: "Home", href: "/petclinic/"])
    }
  }
}
