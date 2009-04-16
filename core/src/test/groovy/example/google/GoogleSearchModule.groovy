package example.google

import org.tellurium.dsl.DslContext

/**
 * Google start page with composite locator and JQuery selector
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 9, 2009
 *
 */

public class GoogleSearchModule extends DslContext {

  public void defineUi() {

    //TODO: need to check while the following src does not work
    ui.Image(uid: "Logo", clocator: [tag: "img", src: "*/intl/en_ALL/images/logo.gif"])
//    ui.Image(uid: "Logo", clocator: [tag: "img", alt: "Google"])

    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }
  }

  public void doGoogleSearch(String input) {
    keyType "Google.Input", input
    pause 500
    click "Google.Search"
    waitForPageToLoad 30000
  }

  public void doImFeelingLucky(String input) {
    type "Google.Input", input
    pause 500
    click "Google.ImFeelingLucky"
    waitForPageToLoad 30000
  }

  //Test jQuery selector for attributes 
  public String getLogoAlt(){
    return getImageAlt("Logo")
  }

  boolean isInputDisabled() {
    return isDisabled("Google.Input")
  }
}