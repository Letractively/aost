package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 15, 2010
 * 
 */
class JTableModule extends DslContext{
  
  public void defineUi(){
    ui.Form(uid: "AddMembersThickbox", clocator: [tag: "div"], id: "TB_window") {

      // Title
      Div(uid: "AddMembersTitleDiv", clocator: [id: "TB_title"]) {

        Div(uid: "AddMembersTitle", clocator: [id: "TB_ajaxWindowTitle"])
      }

      // Table of Members
      Table(uid: "AddMembersThickboxTable", clocator: [id: "aspect_vireo_admin_Settings_table_group-edit-search-eperson", class: "ds-table search-results"]) {
        // table headers
        //   UrlLink(uid: "{header: 1} as addButton", clocator: [text: "Add"], separator: "th")
        //   UrlLink(uid: "{header: 2} as name", clocator: [text: "Name"])
        //   UrlLink(uid: "{header: 3} as email", clocator: [text: "Email"])
        // all rows and columns
        SubmitButton(uid: "{row: all, column: 1} as addButtons", clocator: [name: "*submit_add_eperson_"])

        // The rest of the columns
        Div(uid: "{row: all, column: all}", clocator: [tag: "td", class: "ds-table-cell"])
      }

      // Add buttons to add a member
      // ids's start with: aspect_vireo_admin_Settings_field_submit_add_eperson_
      SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", name: "^submit_add_eperson_", class: "ds-button-field"])

      UrlLink(uid: "CloseThickbox", clocator: [id: "TB_closeWindowButton", text: "close"])
    }
  }

}
