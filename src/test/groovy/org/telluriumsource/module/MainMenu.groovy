package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 4, 2010
 * 
 * 
 */
class MainMenu extends DslContext {

  public void defineUi() {
    ui.Container(uid: "MainMenu", clocator: [tag: "ul"]) {
      TextBox(uid: "Welcome", clocator: [tag: "h2", text: "*Welcome"])
      UrlLink(uid: "Logout", clocator: [tag: "a", text: "Logout", href: "/FinanceManager/j_spring_security_logout"])
      TextBox(uid: "PersonLabel", clocator: [tag: "h2", text: "Person"])
      Container(uid: "Person", clocator: [tag: "ul"]) {
        UrlLink(uid: "List", clocator: [tag: "a", text: "List", href: "/FinanceManager/person"])
        UrlLink(uid: "Create", clocator: [tag: "a", text: "Create", href: "/FinanceManager/person/form"])
      }
      TextBox(uid: "AccountLabel", clocator: [tag: "h2", text: "Account"])
      Container(uid: "Account", clocator: [tag: "ul"]) {
        UrlLink(uid: "List", clocator: [tag: "a", text: "List", href: "/FinanceManager/account"])
        UrlLink(uid: "Create", clocator: [tag: "a", text: "Create", href: "/FinanceManager/account/form"])
      }

      TextBox(uid: "ProductLabel", clocator: [tag: "h2", text: "Product"])
      Container(uid: "Product", clocator: [tag: "ul"]) {
        UrlLink(uid: "List", clocator: [tag: "a", text: "List", href: "/FinanceManager/product"])
        UrlLink(uid: "CreateCash", clocator: [tag: "a", text: "Create Cash Product", href: "/FinanceManager/product/form/cash"])
        UrlLink(uid: "CreateFund", clocator: [tag: "a", text: "Create Managed Fund Product", href: "/FinanceManager/product/form/managedFund"])
        UrlLink(uid: "CreateLoan", clocator: [tag: "a", text: "Create Loan Product", href: "/FinanceManager/product/form/loan"])
      }
      TextBox(uid: "Wizard", clocator: [tag: "h2", text: "Wizard"])
      UrlLink(uid: "Start", clocator: [tag: "a", text: "Start", href: "/FinanceManager/wizard"])
    }
  }
}

