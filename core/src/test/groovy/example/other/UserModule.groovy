package example.other

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
    public void defineUi() {
      ui.Form(uid: "accountEdit", clocator: [tag: "form", id: "editPage", method: "post"]) {
        InputBox(uid: "accountName", clocator: [tag: "input", type: "text", name: "acc2", id: "acc2"])
        InputBox(uid: "accountSite", clocator: [tag: "input", type: "text", name: "acc23", id: "acc23"])
        InputBox(uid: "accountRevenue", clocator: [tag: "input", type: "text", name: "acc8", id: "acc8"])
        TextBox(uid: "heading", clocator: [tag: "h2", text: "%%Account Edit "])
        SubmitButton(uid: "save", clocator: [tag: "input", class: "btn", type: "submit", title: "Save", name: "save"])
      }
    }

    public void doCreateAccount() {
      type "accountEdit.accountName", "ccc"
      click "accountEdit.save"
      waitForPageToLoad 30000
    }
}