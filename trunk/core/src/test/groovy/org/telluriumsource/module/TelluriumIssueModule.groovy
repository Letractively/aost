package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 6, 2010
 * 
 */

public class TelluriumIssueModule extends DslContext {

  public void defineUi() {
//    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
    ui.Table(uid: "issueResult", clocator: [class: "results"], group: "true") {
      //define table header
      //for the border column
      TextBox(uid: "header: 1", clocator: [:])
      UrlLink(uid: "header: 2", clocator: [text: "*ID"])
      UrlLink(uid: "header: 3", clocator: [text: "*Type"])
      UrlLink(uid: "header: 4", clocator: [text: "*Status"])
      UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
      UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
      UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
      UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "header: 10", clocator: [text: "*..."])

      //define table elements
      //for the border column
      TextBox(uid: "row: *, column: 1", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "all", clocator: [:])
    }
  }

}