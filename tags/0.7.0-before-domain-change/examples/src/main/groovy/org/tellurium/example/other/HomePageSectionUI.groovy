package org.tellurium.example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 19, 2009
 *
 */

public class HomePageSectionUI extends DslContext {

  public void defineUi() {
    ui.Container(uid: "HomePageSection", clocator: [tag: "div", class: "qmmc", id: "qm0"]) {
      UrlLink(uid: "ProductsAndSolutionsLink", clocator: [tag: "a", text: "Products & Solutions", href: "*/English/ps2/products/product.asp"])
      UrlLink(uid: "DownloadsLink", clocator: [tag: "a", text: "Downloads", href: "*/English/ss/downloads/index.asp"])
      //   TextBox(uid: "HomePageFlash", clocator: [tag: "embed", type: "application/x-shockwave-flash", name: "flashpromo", id: "flashpromo", src: "/site/resources/dynamic/homeLeftPromos/980 x290_SimplicityIsPower2.swf "])
    }
  }

  //Add your methods here
  public void clickProductsAndSolutions() {
    click "HomePageSection.ProductsAndSolutionsLink"
    waitForPageToLoad(5000)
  }

}