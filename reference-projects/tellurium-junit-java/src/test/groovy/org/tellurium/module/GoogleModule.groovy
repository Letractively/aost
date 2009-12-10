package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * Created by IntelliJ IDEA.
 * User: jiafan1
 * Date: Feb 14, 2009
 * Time: 7:30:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class GoogleModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "Lucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }
  }

  public void dragAndDrop() {
    dragAndDrop "Google.Lucky", "+70,-300"
    pause 500
  }

  public void dragAndDropTo() {
    dragAndDropTo "Google.Search", "Google.Lucky"
    pause 500
  }

}