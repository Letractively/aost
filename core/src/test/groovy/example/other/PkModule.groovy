package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2009
 *
 */

public class PkModule extends DslContext {
    public static String HTML_BODY = """
<input type="button" title="New" onclick="navigateToUrl('/setup/ui/
recordtypeselect.jsp?ent=Account&amp;retURL=
%2F001%2Fo&amp;save_new_url=%2F001%2Fe%3FretURL%3D%252F001%252Fo');"
name="new" class="btn" value=" New "/>
"""
  public void defineUi() {
    ui.InputBox(uid: "input0", clocator: [tag: "input", type: "button", title: "New", name: "new", class: "btn", value: " New "], respond: ["click"])
//     ui.InputBox(uid: "input0", clocator: [tag: "input", type: "button", title: "New", name: "new", class: "btn"], respond: ["click"])
  }
}