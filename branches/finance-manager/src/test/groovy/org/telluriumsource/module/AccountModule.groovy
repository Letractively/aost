package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 3, 2010
 * 
 */
class AccountModule extends DslContext {
  public void defineUi() {
    ui.Form(uid: "Account", clocator: [tag: "form", method: "post", action: "/account", id: "account"]) {
      TextBox(uid: "Fields", clocator: [tag: "legend", text: "Account Fields"])
      TextBox(uid: "NameLabel", clocator: [tag: "label", text: "Name"])
      InputBox(uid: "Name", clocator: [tag: "input", direct: "true", type: "text", name: "name", id: "name"])
      TextBox(uid: "BalanceLabel", clocator: [tag: "label", text: "Balance"])
      InputBox(uid: "Balance", clocator: [tag: "input", type: "text", name: "balance", id: "balance"])
      TextBox(uid: "EquityLabel", clocator: [tag: "label", text: "Equity Allocation"])
      InputBox(uid: "Equity", clocator: [tag: "input", type: "text", name: "equityAllocation", id: "equityAllocation"])
      TextBox(uid: "RenewalDateLabel", clocator: [tag: "label", text: "Renewal Date"])
      InputBox(uid: "RenewalDate", clocator: [tag: "input", type: "text", name: "renewalDate", id: "renewalDate"])
      SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit"])
    }
  }

  public void createAccount(String name, String balance, String equity, String renewalDate){
    keyType "Account.Name", name
    keyType "Account.Balance", balance
    keyType "Account.Equity", equity
    keyType "Account.RenewalDate", renewalDate
    click "Account.Submit"
    waitForPageToLoad 30000
  }

  public void clear(){
    clearText "Account.Name"
    clearText "Account.Balance"
    clearText "Account.Equity"
    clearText "Account.RenewalDate"
  }
}
