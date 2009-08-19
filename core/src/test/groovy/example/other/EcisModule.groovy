package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 19, 2009
 *
 */

public class EcisModule extends DslContext{

  public void defineUi() {
    ui.Container(uid: "EcisPlusUiLoginModule", clocator: [tag: "table"], group: "true"){
      InputBox(uid: "Username", clocator: [id: "loginForm:name", type: "text"])
      InputBox(uid: "Password", clocator: [id: "loginForm:password", type: "password"])
      UrlLink(uid: "Login", clocator: [id: "loginForm:loginButton"])
    }

    ui.Form(uid: "EcisPlusUiSearchModule", clocator: [action: "/nextgen/advancedSearch.seam", method: "post"], group: "true")
    {
      InputBox(uid: "Target", clocator: [id: "qrf:quickSearchTerm", type: "text"])
      SubmitButton(uid: "Search", clocator: [id: "qrf:quickSearchButton", type: "submit"])
    }

    ui.Container(uid: "EcisPlusUiEmail", clocator: [tag: "td"])
    {
      InputBox(uid: "Address", clocator: [id: "cif:emailAddressDecorator:email", type: "text", name: "cif:emailAddressDecorator:email"])
    }

    ui.UrlLink(uid: "expand", clocator: [id: "qrForm:innie"])


    ui.UrlLink(uid: "collapse", clocator: [id: "qrForm:outie"])


    ui.Container(uid: "EcisPlusUiQr", clocator: [tag: "td"])
    {
      UrlLink(uid: "CustomerEmail", clocator: [id: "mailto"])
    }

    ui.Container(uid: "MailToo")
    {
      UrlLink(uid: "MailToo", clocator: [id: "mailToo"])
    }

    ui.Container(uid: "EcisPlusUiCAV", clocator: [tag: "table"])
    {
      InputBox(uid: "Save", clocator: [tag: "input", type: "button", value: "Save", name: "cif:customerInfoSaveButton", id: "cif:customerInfoSaveButton", class: "btn saveButton"], respond: ["click"])
    }
  }

  public void save(){
    click "EcisPlusUiCAV.Save"
    println "Testing - Pausing"
    pause 10000
  }

  public boolean checkExpend(){
    boolean isItThere = isElementPresent("expand")
    println "isItThere: ${isItThere}"
    return isItThere
  }
}