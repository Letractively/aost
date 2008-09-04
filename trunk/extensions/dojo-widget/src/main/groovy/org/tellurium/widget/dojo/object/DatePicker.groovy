package org.tellurium.widget.dojo.object

import org.tellurium.widget.dojo.DojoWidget

/**
 * Tellurium Widget object for the Dojo Date Picker widget
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePicker extends DojoWidget{

    public void defineWidget() {
        ui.Container(uid: "DatePicker", locator: "/div[@class='datePickerContainer' and child::table[@class='calendarContainer']"){
            Container(uid: "Title", locator: "/table[@class='calendarContainer'/thead/tr/td[@class='monthWrapper']/table[@class='monthContainer']/tbody/tr/td[@class='monthLabelContainer']"){
                Icon(uid: "increaseWeek", locator: "/span[@dojoattachpoint='increaseWeekNode']")
                Icon(uid: "increaseMonth", locator: "/span[@dojoattachpoint='increaseMonthNode']")
                Icon(uid: "decreaseWeek", locator: "/span[@dojoattachpoint='decreaseWeekNode']")
                Icon(uid: "decreaseMonth", locator: "/span[@dojoattachpoint='decreaseMonthNode']")
                TextBox(uid: "monthLabel", locator: "/span[@dojoattachpoint='monthLabelNode']")   
            }
            
        }
    }

    public void increaseWeek(){
        click "DatePicker.Title.increaseWeek"
    }

    public void increaseMonth(){
        click "DatePicker.Title.increaseMonth"
    }

    public void decreaseWeek(){
        click "DatePicker.Title.decreaseWeek"
    }

    public void decreaseMonth(){
        click "DatePicker.Title.decreaseMonth"
    }

    public String getMonthLabel(){
        return getText("DatePicker.Title.monthLabel")
    }
}