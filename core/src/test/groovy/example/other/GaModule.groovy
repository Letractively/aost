package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 13, 2009
 *
 */

public class GaModule extends DslContext {
    public static String HTML_BODY = """
<div class="x-form-item">
    <a>Program</a>

    <div class="x-form-element">
        <input type="text" readonly="true"/>
        <img src="/ext/resources/images/default/s.gif"/>
    </div>
</div>

<div class="x-layer x-combo-list">
    <div class="x-combo-list-hd"/>
    <div class="x-combo-list-inner">
        <div/>
    </div>
</div>
"""
  public void defineUi() {
    ui.Container(uid: "Program", clocator: [tag: "div", class: "x-form-item"], group: "true") {
      Span(uid: "label", clocator: [tag: "a", text: "Program"])
      Container(uid: "triggerBox", clocator: [tag: "div", class: "x-form-element"], group: "true") {
        InputBox(uid: "inputBox", clocator: [tag: "input", type: "text", readonly: "true"], respond: ["focus", "mouseOver", "mouseOut", "blur"])
        Image(uid: "trigger", clocator: [tag: "img", src: "/ext/resources/images/default/s.gif"], respond: ["focus", "mouseOver", "mouseOut", "blur"])
      }
    }

    ui.Container(uid: "Programs", clocator: [tag: "div", class: "x-layer x-combo-list"], group: "true") {
      Div(uid: "header", clocator: [tag: "div", class: "x-combo-list-hd"], text: "Programs")
      List(uid: "list", clocator: [tag: "div", class: "x-combo-list-inner"], respond: ["click"]) {
        Div(uid: "all", clocator: [:], respond: ["click"])
      }
    }
  }

  public void selectProgram(String program) {
    click "Program.triggerBox.trigger"
    pause 500
    click "Programs.list[1]"
    pause 500
  }
}