package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2010
 * 
 */
class MultipleCheckModule extends DslContext {
  public void defineUi() {

    ui.Form(uid: "Formprefs", clocator: [tag: "form", method: "post",
            action: "/vireo101/vireo/admin/settings?update=true&page=user", class:"ds-interactive-div form-prefs", id: "aspect_vireo_admin_Settings_div_form-prefs"], group: "true") {
      Div(uid: "ListTabOptions", clocator: [tag: "div", class: "ds-static-div settings-options", id: "aspect_vireo_admin_Settings_div_list-tab-options"]) {
        Container(uid: "FilterOptionsListFieldset", clocator: [tag: "fieldset", class: "ds-form-list thick display-filters-two pref-options asyncParent", id: "aspect_vireo_admin_Settings_list_display-filters-one"]) {
          List(uid: "FilterOptionsList", clocator: [tag: "ol"], separator: "li") {
            CheckBox(uid: "{all}", clocator: [tag: "input", type: "checkbox"], respond: ["click"])
          }
        }
        Container(uid: "ColumnsListFieldset", clocator: [tag: "fieldset", class: "ds-form-list thick display-filters-two pref-optionsasyncParent", id: "aspect_vireo_admin_Settings_list_display-filters-two"]) {
          List(uid: "ColumnsList", clocator: [tag: "ol"], separator: "li") {

            CheckBox(uid: "{all}", clocator: [tag: "input", type: "checkbox"], respond: ["click"])
          }
        }
        Container(uid: "RecordsPerPageFieldset", clocator: [tag: "fieldset", class: "ds-form-list page-size pref-options asyncParent", id: "aspect_vireo_admin_Settings_list_list-page-size"]) {
          List(uid: "RecordsPerPage", clocator: [tag: "ol"], separator: "li") {
            RadioButton(uid: "{all}", clocator: [tag: "input", type: "radio"], respond: ["click"])
          }
        }
      }
    }
  }

  public void doSetAllCheckboxes(boolean checked) {

    String filterList =
    "Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList"
    int len = getListSize(filterList)

    for (int i = 1; i <= len; ++i) {
      if (checked) {
        def value = getValue("Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList[2]")  //for debugging
        //def item = filterList[i];

// error on next line: SeleniumException: ERROR: Element is not a toggle-button
        if (!isChecked("Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList[${i}]")) {
          click "Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList[${i}]"
          pause 300   // for ajax
          String script = "isChecked(\"Formprefs.ListTabOptions.FilterOptionsListFieldset.FilterOptionsList[${i}]\");";
          waitForCondition script, 30000
        }
      }
    }
    // This times out
    //runScript
    ("teJQuery('input:checkbox[name=LIST_COLUMN_ASSIGNED_TO]').click();");
  }

}
