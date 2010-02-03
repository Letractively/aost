package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 1, 2010
 * 
 */
class JListModule extends DslContext {

  public void defineUi(){
    ui.Form(uid: "selectedSailings", clocator: [name: "selectedSailingsForm"]) {
      List(uid: "outgoingSailings", locator: "/div[1]") {
        Container(uid: "all", clocator: [tag: "div", 'class': "option"]) {
          List(uid: "fares", clocator: [tag: "ul"]) {
            Container(uid: "all", clocator: [tag: "li"]) {
              RadioButton(uid: "radio", clocator: [:], respond: ["click"])
              TextBox(uid: "label", clocator: [tag: "label"])
            }
          }
          TextBox(uid: "departureTime", locator: "/div/dl/dd/em[1]")
        }
      }
    }

    ui.Form(uid: "Sailings", clocator: [name: "selectedSailingsForm"]){
      List(uid: "Fares", clocator: [tag: "ul", class: "fares"], separator: "li"){
        Container(uid: "all"){
            RadioButton(uid: "radio", clocator: [:], respond: ["click"])
            TextBox(uid: "label", clocator: [tag: "label"])
        }
      }
    }

    ui.Form(uid: "SailingForm", clocator: [name: "selectedSailingsForm"] ){
      Repeat(uid: "Section", clocator: [tag: "div", class: "segment clearfix"]){
        Repeat(uid: "Option", clocator: [tag: "div", class: "option"]){
          List(uid: "Fares", clocator: [tag: "ul", class: "fares"], separator: "li"){
            Container(uid: "all"){
                RadioButton(uid: "radio", clocator: [:], respond: ["click"])
                TextBox(uid: "label", clocator: [tag: "label"])
            }
          }
          Container(uid: "Details", clocator: [tag: "div", class: "details"]){
            Container(uid: "Photo", clocator: [tag: "div", class: "photo"]){
              Image(uid: "Image", clocator: [:])
            }
            List(uid: "ShipInfo", clocator: [tag: "dl"]){
              
            }
          }
        }
      }
    }
  }
}
