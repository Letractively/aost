package org.tellurium.example.other

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2009
 * 
 */

public class UserModule extends DslContext {
    public static String HTML_BODY = """
<form id="editPage" method="post">
  <input type="text" name="acc2" id="acc2"/>
  <input type="text" name="acc23" id="acc23"/>
  <input type="text" name="acc8" id="acc8"/>
  <h2>Account Edit </h2>
  <input class="btn" type="submit" title="Save" name="save"/>
</form>
    """
  public static String RESP_HEADER = """
       <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
       <html>
       <head>
               <title>Mock HTTP Server</title>
       </head>
       <body>
          <form id="editPage" method="post">
            <input type="text" name="acc2" id="acc2"/>
            <input type="text" name="acc23" id="acc23"/>
            <input type="text" name="acc8" id="acc8"/>
            <h2>Account Edit </h2>
            <input class="btn" type="submit" title="Save" name="save"/>
           </form>
       </body>
   </html>
   """

    public void defineUi() {
      ui.Form(uid: "accountEdit", clocator: [tag: "form", id: "editPage", method: "post"]) {
        InputBox(uid: "accountName", clocator: [tag: "input", type: "text", name: "acc2", id: "acc2"])
        InputBox(uid: "accountSite", clocator: [tag: "input", type: "text", name: "acc23", id: "acc23"])
        InputBox(uid: "accountRevenue", clocator: [tag: "input", type: "text", name: "acc8", id: "acc8"])
        TextBox(uid: "heading", clocator: [tag: "h2", text: "*Account Edit "])
        SubmitButton(uid: "save", clocator: [tag: "input", class: "btn", type: "submit", title: "Save", name: "save"])
      }

      ui.Container(uid: "subnav", clocator: [tag: "ul", id: "subnav"]) {
        Container(uid: "CoreLinks", clocator: [tag: "li", id: "core_links"]) {
          List(uid: "links", clocator: [tag: "ul"], separator: "li") {
            UrlLink(uid: "all", clocator: [:])
          }
        }
        UrlLink(uid: "subscribe", clocator: [tag: "li", id: "subscribe"])
      }

      ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
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
        TextBox(uid: "row: *, column: 8", clocator: [:])
        TextBox(uid: "row: *, column: 10", clocator: [:])
        //For the rest, just UrlLink
        UrlLink(uid: "all", clocator: [:])
      }

      ui.Container(uid: "ConsumersPage", clocator: [tag: "div", id: "contenu"]){
            Table(uid:"ConsumersTable", clocator: [tag: "table", class: "dataview"], group:"true"){
                Span(uid: "row: 1, column: 1", clocator: [tag: "a", text: "%%Nom"])
                Container(uid: "row: *, column: 7", clocator: [tag: "td", position:"7"]){
                    Span(uid: "Clone", clocator: [tag: "span", text: "Cloner"])
                    Span(uid: "Modify", clocator: [tag: "span", text: "Modifier"])
                    Span(uid: "DeploymentAsk", clocator: [tag: "span", text: "%%ploiement"], respond: ["click"])
                    Span(uid: "UndeploymentAsk", clocator: [tag: "span", text: "%%retrait"], respond: ["click"])

                }
            }
            UrlLink(uid: "NewConsumer", clocator: [tag: "a", text: "Nouveau consommateur"])
        }

      ui.Table(uid: "questionGroup", clocator: [class: "QuestionGroup"]) {
        Table(uid: "row: 1, column: 1", clocator: [class: "datatable2"]) {
          RadioButton(uid: "row: 1, column: 1", clocator: [name: "1"])
        }
        StandardTable(uid: "row: 2, column: 1", clocator: [class: "datatable2"]) {
          RadioButton(uid: "tbody: *, row:1, column: 1", clocator: [:])
        }
      }

      ui.Div(uid: "search", clocator: [tag: "div", text: " Search ", class: "tab-unselected-on-color tab-unselected l", id: "tabSearch"])
    }

    public void doCreateAccount() {
      type "accountEdit.accountName", "ccc"
      click "accountEdit.save"
      waitForPageToLoad 30000
    }

    public void clickSearch() {

      click "search"
    }
}