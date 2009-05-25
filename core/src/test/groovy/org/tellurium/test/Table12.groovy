package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 17, 2009
 * 
 */

public class Table12 extends DslContext{
  public void defineUI(){
    ui.Table(uid: "OrderDetail", clocator: [tag: "table", class: "table"]){
      TextBox(uid: "row: 1, column: 1", clocator: [tag: "td", text: "PO Number", class: "tablecolumnheader"])
      TextBox(uid: "row: 2, column: 1", clocator: [tag: "td", text: "Buyer", class: "tablecolumnheader"])
      TextBox(uid: "row: 3, column: 1", clocator: [tag: "td", text: "Vendor", class: "tablecolumnheader"])
      TextBox(uid: "row: 4, column: 1", clocator: [tag: "td", text: "Department", class: "tablecolumnheader"])
      TextBox(uid: "row: 5, column: 1", clocator: [tag: "td", text: "Order Date", class: "tablecolumnheader"])
      TextBox(uid: "row: 1, column: 2", clocator: [tag: "td", text: "Extended Cost", class: "tablecolumnheader"])
      TextBox(uid: "row: 2, column: 2", clocator: [tag: "td", text: "Extended Retail", class: "tablecolumnheader"])
      TextBox(uid: "row: 3, column: 3", clocator: [tag: "td", text: "Markup %", class: "tablecolumnheader"])
      TextBox(uid: "row: 4, column: 4", clocator: [tag: "td", text: "Days from ", class: "tablecolumnheader"])
      TextBox(uid: "row: 5, column: 5", clocator: [tag: "td", text: "Status", class: "tablecolumnheader"])
    }

    ui.Table(uid: "Test", clocator: [cellspacing: "0", cellpadding: "0", border: "0"]){
      UrlLink(uid: "row: 1, column: 1", clocator: [:])
      Div(uid: "row: 2, column: 1", clocator: [:])
      Selector(uid: "row: 2, column: 2", clocator: [:])
    }
  }
}