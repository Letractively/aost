package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 19, 2009
 *
 */

public class CitrixHomePageUI extends DslContext {

  public void defineUi() {
    //               Citrix home page Top Menus
    ui.Container(uid: "CitrixHomePage", clocator: [tag: "div", id: "supnav"]) {
      UrlLink(uid: "LoginLink", clocator: [tag: "a", text: "Log in", href: "/English/mycitrix/index.asp"])
      Container(uid: "TopMenu", clocator: [tag: "div", class: "qmmc", id: "qm1"])
              {
                UrlLink(uid: "MyCitrixLink", clocator: [tag: "a", text: "My Citrix", position: "7", direct: "true", href: "/English/mycitrix/index.asp?ntref=hp_util_US"])
                UrlLink(uid: "GlobalSitesLink", clocator: [tag: "a", text: "Global Sites", position: "6", direct: "true", href: "/global"])
              }
      UrlLink(uid: "LogOutLink", clocator: [tag: "a", text: "Log Out", href: "/English/mycitrix/logout.asp"])
    }
  }

  //Add your methods here

  public void clickMyCitrix() {
    click "CitrixHomePage.TopMenu.MyCitrixLink"
    waitForPageToLoad(5000)
  }

  public void clickLogin() {
    click "CitrixHomePage.LoginLink"
    waitForPageToLoad(5000)
  }

  public void clickLogout() {
    click "CitrixHomePage.LogOutLink"
    waitForPageToLoad(5000)
  }

}
