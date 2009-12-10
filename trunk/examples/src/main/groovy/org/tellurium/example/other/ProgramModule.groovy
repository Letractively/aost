package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 20, 2009
 *
 */

public class ProgramModule extends DslContext {

    public static String HTML_BODY = """
<div id="ext-gen437" class="x-form-item" tabindex="-1">
    <label class="x-form-item-label" style="width: 125px;" for="ext-comp-1043">
        <a class="help-tip-link" onclick="openTip('Program','program');return false;" title="click for more info" href="http://localhost:8080">Program</a>
    </label>

    <div id="x-form-el-ext-comp-1043" class="x-form-element" style="padding-left: 130px;">
        <div id="ext-gen438" class="x-form-field-wrap" style="width: 360px;">
            <input id="programId" type="hidden" name="programId" value=""/>
            <input id="ext-comp-1043" class="x-form-text x-form-field x-combo-noedit" type="text" autocomplete="off"
                   size="24" readonly="true" style="width: 343px;"/>
            <img id="ext-gen439" class="x-form-trigger x-form-arrow-trigger" style="overflow: auto; width: 356px; height: 100px;" src="images/s.gif"/>
        </div>
    </div>
    <div class="x-form-clear-left"/>
</div>
    """

  public void defineUi() {
    ui.Container(uid: "Program", clocator: [tag: "div"], group: "true") {
      Div(uid: "label", clocator: [tag: "a", text: "Program"])
      Container(uid: "triggerBox", clocator: [tag: "div"], group: "true") {
        InputBox(uid: "inputBox", clocator: [tag: "input", type: "text", readonly: "true", style: "width: 343px;"], respond: ["click"])
//        Image(uid: "trigger", clocator: [tag: "img", src: "*images/s.gif"], respond: ["click"])
        Image(uid: "trigger", clocator: [tag: "img",  style: "overflow: auto; width: 356px; height: 100px;"], respond: ["click"])
      }
    }
  }
}