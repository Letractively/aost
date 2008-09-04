package org.tellurium.widget

/**
 * Widget to module the data picket from
 *
 *   http://www.dynarch.com/projects/calendar/
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 20, 2008
 *
 */
class DynArchDatePicker extends Widget {

    public void defineWidget(){
        ui.Container(uid: "datepicker", clocator: [tag: "div", class: "calendar"]){
            Container(uid: "header", clocator:[header: "/table", tag: "thead"], direct: "true"){
              Div(uid: "about", clocator:[header: "/tr/td[@class='button']", text: "%%?"], direct: "true")
              TextBox(uid: "title", clocator:[header: "/tr", tag: "td", class: "title"], direct: "true")
              Div(uid: "prevyear", clocator:[header: "/tr[@class='headrow']/td[@class='button nav']", text: "%%&laquo;"], direct:"true")
              Div(uid: "prevmonth", clocator:[header: "/tr[@class='headrow']/td[@class='button nav']", text: "%%&lsaquo;"], direct:"true")
              Div(uid: "today", clocator:[header: "/tr[@class='headrow']/td[@class='nav']", text: "Today"], direct:"true")  
              Div(uid: "nextmonth", clocator:[header: "/tr[@class='headrow']/td[@class='button nav']", text: "%%&rsaquo;"], direct:"true")
              Div(uid: "nextyear", clocator:[header: "/tr[@class='headrow']/td[@class='button nav']", text: "%%&raquo;"], direct:"true")
              UrlLink(uid: "week", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "wk"], direct:"true")
              UrlLink(uid: "Sunday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Sun"], direct:"true")
              UrlLink(uid: "Monday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Mon"], direct:"true")
              UrlLink(uid: "Tuesday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Tue"], direct:"true")
              UrlLink(uid: "Wednesday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Wed"], direct:"true")
              UrlLink(uid: "Thursday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Thu"], direct:"true")
              UrlLink(uid: "Friday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Fri"], direct:"true")
              UrlLink(uid: "Saturday", clocator:[header: "/tr[@class='daynames'", tag: "td", text: "Sat"], direct:"true")
           }
           Table(uid:"days", direct: "true"){
               TextBox(uid: "row: *, column: 1", clocator:[:])
               UrlLink(uid: "all", clocator:[:])
           }
           TextBox(uid: "foot", clocator:[header: "/table/tr[@class='footrow']", tag: "td"], direct: "true") 
        }
    }

    public void clickAbout(){
        click "datepicker.header.about"
    }

    public String getTitle(){
        return getText("datepicker.header.title")
    }
}