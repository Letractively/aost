package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * Created from Selenium IDE recorded test case
 *
 */

public class TelluriumDownloadPage extends DslContext {

  public void defineUi() {
    ui.Container(uid: "downloads", clocator: [:]) {
      Selector(uid: "downloadType", locator: "//*[@id='can']")
      InputBox(uid: "input", locator: "//*[@id='q']")
      SubmitButton(uid: "search", locator: "//input[@value='Search']")
    }
  }

  public void searchDownload(String downloadType, String searchKeyWords) {
    selectByLabel "downloads.downloadType", downloadType
    keyType "downloads.input", searchKeyWords
    click "downloads.search"
    waitForPageToLoad 30000
  }

}
