package example.other

import org.tellurium.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 25, 2009
 *
 */

public class GaGroupModule extends DslContext {
    public static String HTML_BODY = """
<div id="ext-gen403" class="x-combo-list-hd">Programs and Projects</div>
<div style="width: 246px;" id="ext-gen404" class="x-combo-list-inner"></div>
<div class="x-layer x-combo-list">
  <div id="ext-gen430" class="x-combo-list-hd">Programs</div>
  <div style="overflow: auto; width: 358px; height: 240px;" id="ext-gen431" class="x-combo-list-inner">
    <div class="x-combo-list-item x-combo-selected">A</div>
    <div class="x-combo-list-item">B</div>
    <div class="x-combo-list-item">C</div>
    <div class="x-combo-list-item">D</div>
    <div class="x-combo-list-item">E</div>
    <div class="x-combo-list-item">F</div>
    <div class="x-combo-list-item">G</div>
    <div class="x-combo-list-item">H</div>
    <div class="x-combo-list-item">I</div>
    <div class="x-combo-list-item">J</div>
    <div class="x-combo-list-item">K</div>
    <div class="x-combo-list-item">L</div>
  </div>
</div>
<div id="ext-gen452" class="x-combo-list-hd">NA</div>
<div style="width: 298px;" id="ext-gen453" class="x-combo-list-inner"></div>
<div id="ext-gen480" class="x-combo-list-hd">Ins</div>
<div style="width: 356px;" id="ext-gen481" class="x-combo-list-inner"></div>
<div id="ext-gen502" class="x-combo-list-hd">Alg</div>
<div style="width: 356px;" id="ext-gen503" class="x-combo-list-inner"></div>
"""

  public void defineUi() {
    ui.Container(uid: "Program", clocator: [tag: "div", id: "x-form-el-program"], group: "true") {
      InputBox(uid: "input", clocator: [tag: "input", id: "program"])
      Image(uid: "button", clocator: [tag: "img"])
    }

    ui.Container(uid: "ProgramList", clocator: [tag: "div", class: "x-layer x-combo-list"], group: "true") {
      Div(uid: "title", clocator: [tag: "div", text: "Programs", class: "x-combo-list-hd"])
      List(uid: "list", clocator: [tag: "div", class: "x-combo-list-inner"], group: "true") {
        Div(uid: "all", clocator: [:])
      }
    }
  }
}