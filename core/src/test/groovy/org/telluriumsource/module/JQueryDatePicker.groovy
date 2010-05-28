package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 28, 2010
 * 
 */
class JQueryDatePicker extends DslContext{

  public void defineUi(){
    ui.InputBox(uid: "DatePickerInput", clocator: [id: "datepicker"], respond: ["click"])
    
    ui.Container(uid: "DatePicker", clocator: [tag: "div", class: "ui-datepicker ui-widget ui-widget-content"]) {
      Container(uid: "Header", clocator: [tag: "div", class: "ui-datepicker-header"]) {
        UrlLink(uid: "Prev", clocator: [class: "ui-datepicker-prev"])
        UrlLink(uid: "Next", clocator: [class: "ui-datepicker-next"])
        Container(uid: "Title", clocator: [tag: "div", class: "ui-datepicker-title"]) {
          TextBox(uid: "Month", clocator: [tag: "span", class: "ui-datepicker-month"])
          TextBox(uid: "Year", clocator: [tag: "span", class: "ui-datepicker-year"])
        }
      }
      StandardTable(uid: "Calendar", clocator: [class: "ui-datepicker-calendar"]) {
        TextBox(uid: "{header: 1} as Sunday", clocator: [tag: "span", text: "Su"])
        TextBox(uid: "{header: 2} as Monday", clocator: [tag: "span", text: "Mo"])
        TextBox(uid: "{header: 3} as Tuesday", clocator: [tag: "span", text: "Tu"])
        TextBox(uid: "{header: 4} as Wednesday", clocator: [tag: "span", text: "We"])
        TextBox(uid: "{header: 5} as Thursday", clocator: [tag: "span", text: "Th"])
        TextBox(uid: "{header: 6} as Friday", clocator: [tag: "span", text: "Fr"])
        TextBox(uid: "{header: 7} as Sunday", clocator: [tag: "span", text: "Sa"])
        UrlLink(uid: "{row: any, column: any} as D1", clocator: [text: "1"])
        UrlLink(uid: "{row: any, column: any} as D2", clocator: [text: "2"])
        UrlLink(uid: "{row: any, column: any} as D3", clocator: [text: "3"])
        UrlLink(uid: "{row: any, column: any} as D4", clocator: [text: "4"])
        UrlLink(uid: "{row: any, column: any} as D5", clocator: [text: "5"])
        UrlLink(uid: "{row: any, column: any} as D6", clocator: [text: "6"])
        UrlLink(uid: "{row: any, column: any} as D7", clocator: [text: "7"])
        UrlLink(uid: "{row: any, column: any} as D8", clocator: [text: "8"])
        UrlLink(uid: "{row: any, column: any} as D9", clocator: [text: "9"])
        UrlLink(uid: "{row: any, column: any} as D10", clocator: [text: "10"])
        UrlLink(uid: "{row: any, column: any} as D11", clocator: [text: "11"])
        UrlLink(uid: "{row: any, column: any} as D12", clocator: [text: "12"])
        UrlLink(uid: "{row: any, column: any} as D13", clocator: [text: "13"])
        UrlLink(uid: "{row: any, column: any} as D14", clocator: [text: "14"])
        UrlLink(uid: "{row: any, column: any} as D15", clocator: [text: "15"])
        UrlLink(uid: "{row: any, column: any} as D16", clocator: [text: "16"])
        UrlLink(uid: "{row: any, column: any} as D17", clocator: [text: "17"])
        UrlLink(uid: "{row: any, column: any} as D18", clocator: [text: "18"])
        UrlLink(uid: "{row: any, column: any} as D19", clocator: [text: "19"])
        UrlLink(uid: "{row: any, column: any} as D20", clocator: [text: "20"])
        UrlLink(uid: "{row: any, column: any} as D21", clocator: [text: "21"])
        UrlLink(uid: "{row: any, column: any} as D22", clocator: [text: "22"])
        UrlLink(uid: "{row: any, column: any} as D23", clocator: [text: "23"])
        UrlLink(uid: "{row: any, column: any} as D24", clocator: [text: "24"])
        UrlLink(uid: "{row: any, column: any} as D25", clocator: [text: "25"])
        UrlLink(uid: "{row: any, column: any} as D26", clocator: [text: "26"])
        UrlLink(uid: "{row: any, column: any} as D27", clocator: [text: "27"])
        UrlLink(uid: "{row: any, column: any} as D28", clocator: [text: "28"])
        UrlLink(uid: "{row: any, column: any} as D29", clocator: [text: "29"])
        UrlLink(uid: "{row: any, column: any} as D30", clocator: [text: "30"])
        UrlLink(uid: "{row: any, column: any} as D31", clocator: [text: "31"])
        TextBox(uid: "{row: all, column: all}", clocator: [class: "ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled"], self: "true")
      }
    }
  }

  public String getYear(){
    getText("DatePicker.Header.Title.Year");
  }

  public String getMonth(){
    getText("DatePicker.Header.Title.Month");
  }

  public void prev(){
    click("DatePicker.Header.Prev")
  }

  public void next(){
    click("DatePicker.Header.Next")
  }

  public void selectDay(int day){
    String uid = "DatePicker.Calendar.D" + day;
    mouseOver(uid);
    click(uid);
    mouseOut(uid);
  }

  public void selectFriday(int week){
    String uid = "DatePicker.Calendar[" + week + "][Friday]";
    mouseOver(uid);
    click(uid);
    mouseOut(uid);
  }

  public String getDate(){
    String month = getText("DatePicker.Header.Title.Month");
    String year = getText("DatePicker.Header.Title.Year");

    return "${month} ${year}";
  }
}
