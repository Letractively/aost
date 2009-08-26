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
    <div class="x-combo-list-item x-combo-selected">National Water Quality Assessment</div>
    <div class="x-combo-list-item">Department of Defense Environmental Conservation Program</div>
    <div class="x-combo-list-item">National Stream Quality Assessment Network</div>
    <div class="x-combo-list-item">Ground-Water Resource Program</div>
    <div class="x-combo-list-item">Collection of Basic Records</div>
    <div class="x-combo-list-item">Cooperative Water Program</div>
    <div class="x-combo-list-item">Regional Aquifer System Analysis</div>
    <div class="x-combo-list-item">National Streamflow Information Program</div>
    <div class="x-combo-list-item">National Atmospheric Deposition Program</div>
    <div class="x-combo-list-item">Toxic Substances Hydrology</div>
    <div class="x-combo-list-item">Other Federal Agencies</div>
    <div class="x-combo-list-item">Federal Benchmark Sites</div>
  </div>
</div>
<div id="ext-gen452" class="x-combo-list-hd">NAWQA Study Units</div>
<div style="width: 298px;" id="ext-gen453" class="x-combo-list-inner"></div>
<div id="ext-gen480" class="x-combo-list-hd">Invertebrate Labs</div>
<div style="width: 356px;" id="ext-gen481" class="x-combo-list-inner"></div>
<div id="ext-gen502" class="x-combo-list-hd">Algae Labs</div>
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