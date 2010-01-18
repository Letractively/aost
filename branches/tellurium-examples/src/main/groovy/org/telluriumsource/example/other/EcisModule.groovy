package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 19, 2009
 *
 */

public class EcisModule extends DslContext{

  public void defineUi() {
    ui.Container(uid: "EcisPlusUiLoginModule", clocator: [tag: "div"], group: "true"){
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

    ui.Container(uid: "EcisPlusUiCAV", clocator: [tag: "div"])
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

  public String HTML_BODY = """
<div>
    <input id="loginForm:name" type="text"/>
    <input id="loginForm:password" type="password"/>
    <a id="loginForm:loginButton"/>
</div>
<form action="/nextgen/advancedSearch.seam" method="post">
    <input id="qrf:quickSearchTerm" type="text"/>
    <input id="qrf:quickSearchButton" type="submit"/>
</form>
<table>
    <tbody>
    <tr>
        <td>
            <input id="cif:emailAddressDecorator:email" type="text" name="cif:emailAddressDecorator:email"/>
        </td>
        <a id="qrForm:innie"/>
        <a id="qrForm:outie"/>
        <td>
            <a id="mailto"/>
        </td>

    </tr>
    </tbody>

</table>

<a id="mailToo"/>

<div>
    <input type="button" value="Save" name="cif:customerInfoSaveButton" id="cif:customerInfoSaveButton"
           class="btn saveButton"/>
</div>

"""
}