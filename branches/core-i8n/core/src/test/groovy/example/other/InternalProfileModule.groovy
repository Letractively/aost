package example.other

import org.tellurium.dsl.DslContext

/**
 * Provided by Rohan Holt to test the jQuery has() selector position issue  
 *
 *
 * Date: May 14, 2009
 * 
 */

public class InternalProfileModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Main", clocator: [tag: "div", id: "main"]) {
      Form(uid: "UserEditPrivateProfileForm", clocator: [tag: "form", name: "theForm", method: "post"], group: "true") {
        /*
        InputBox(uid: "Address1", clocator: [tag: "input", type: "text", name: "address1"])
        InputBox(uid: "Address2", clocator: [tag: "input", type: "text", name: "address2"])
        InputBox(uid: "Address3", clocator: [tag: "input", type: "text", name: "city"])
        InputBox(uid: "PostCode", clocator: [tag: "input", type: "text", name: "postcode"])
        InputBox(uid: "Telephone", clocator: [tag: "input", type: "text", name: "tel"])
        InputBox(uid: "Email", clocator: [tag: "input", type: "text", name: "email"])
        Selector(uid: "Gender", clocator: [tag: "select", name: "gender"])
        InputBox(uid: "Mobile", clocator: [tag: "input", type: "text", name: "Mobile"])
        */
        List(uid: "Fields", clocator: [tag: "div", class: "test", position: "2"], separator: "div") {
//        List(uid: "Fields", clocator: [tag: "div", class: "test"], separator: "div") {
          Container(uid: "all") {
            InputBox(uid: "item", clocator: [:])
            Selector(uid: "selector", clocator: [:])
          }
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", name: "submit"], respond: ["click", "mouseOut", "mouseOver"])
      }
    }
  }

  
}