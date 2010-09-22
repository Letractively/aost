package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext


/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 21, 2010
 * 
 */
class JtvAosModule extends DslContext{
  public void defineUi() {
    ui.Frame(uid: "ivrTestConfig", name: "ivrTestConfig", clocator: [tag: "frame"]) {
      Form(uid: "Form", clocator: [tag: "form", method: "post", action: "../ivr/InteractiveVoiceResponseAutomatedOrderingSystemService"]) {
        InputBox(uid: "VirtualIvrEmployeeUsername", clocator: [tag: "input", type: "text", name: "virtualIvrEmployeeUsername"])
        InputBox(uid: "VirtualIvrEmployeePassword", clocator: [tag: "input", type: "password", name: "virtualIvrEmployeePassword"])
        SubmitButton(uid: "Submit", clocator: [tag: "input", direct: "true", type: "submit", value: "Submit"])
        InputBox(uid: "VirtualIvrEmployeeEmpId", clocator: [tag: "input", type: "text", name: "virtualIvrEmployeeEmpId"])
        InputBox(uid: "SecurityTicket", clocator: [tag: "input", type: "text", name: "securityTicket"])
        Selector(uid: "Action", clocator: [tag: "select", name: "action"])
        InputBox(uid: "ProductNameToPurchase", clocator: [tag: "input", type: "text", name: "productNameToPurchase"])
        InputBox(uid: "ProductGroupNameSelected", clocator: [tag: "input", type: "text", name: "productGroupNameSelected"])
        InputBox(uid: "ProductQuantityToPurchase", clocator: [tag: "input", type: "text", name: "productQuantityToPurchase"])
        InputBox(uid: "DesiredSizeTouchToneDigits", clocator: [tag: "input", type: "text", name: "desiredSizeTouchToneDigits"])
        InputBox(uid: "CustomerId", clocator: [tag: "input", type: "text", name: "customerId"])
        InputBox(uid: "CreditCardNumberLastFourDigits", clocator: [tag: "input", type: "text", name: "creditCardNumberLastFourDigits"])
        InputBox(uid: "NewCreditCardExpirationDateMMYYFormat", clocator: [tag: "input", type: "text", name: "newCreditCardExpirationDateMMYYFormat"])
        InputBox(uid: "ChannelNumber", clocator: [tag: "input", type: "text", name: "channelNumber"])
        InputBox(uid: "ShowCode", clocator: [tag: "input", type: "text", name: "showCode"])
        InputBox(uid: "ItemTotalInDollars", clocator: [tag: "input", type: "text", name: "itemTotalInDollars"])
        InputBox(uid: "ShippingDeliveryTimeId", clocator: [tag: "input", type: "text", name: "shippingDeliveryTimeId"])
        InputBox(uid: "ShippingHandlingChargeInDollars", clocator: [tag: "input", type: "text", name: "shippingHandlingChargeInDollars"])
        InputBox(uid: "TaxChargeInDollars", clocator: [tag: "input", type: "text", name: "taxChargeInDollars"])
        InputBox(uid: "OrderTotalInDollars", clocator: [tag: "input", type: "text", name: "orderTotalInDollars"])
      }
    }

    ui.Frame(uid: "ivrTestResults", name: "ivrTestResults") {
//          TextBox(uid: "xml", clocator: [:])
    }


  }

  public void login(String name, String password){
		type "ivrTestConfig.Form.VirtualIvrEmployeeUsername", name
		type "ivrTestConfig.Form.VirtualIvrEmployeePassword", password
		click "ivrTestConfig.Form.Submit"
		waitForPageToLoad 3000
  }

  public String getResponse(){
    selectFrame "ivrTestResults"
    return getXMLDocument()
//    return getBodyText();
//    return getHtmlSource()
//    return getText("ivrTestResults.xml")
  }

  public String getServiceTicket(String xml){

    def root = new XmlParser().parseText(xml)
//    String ticket = root.SecurityTicket
    String ticket = root.attributes().get("SecurityTicket")

    return ticket
  }
}
