package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 28, 2010
 * 
 */
class DatePickerDefaultModule extends DslContext {
    public void defineUi() {
       ui.InputBox(uid: "DatePickerInput", clocator: [id: "datepicker"])
       ui.jQuery_DatePicker(uid: "Default", clocator: [tag: "body"])
    }

  public void prevMonth(){
    onWidget "Default", click, "DatePicker.Header.Prev"
  }

  public void nextMonth(){
    onWidget "Default", click, "DatePicker.Header.Next"
  }

  public void selectDay(int day){
    onWidget "Default", selectDay, day
  }

  public void selectFriday(int week){
    onWidget "Default", selectFriday, week
  }

  public String getMonthYear(){
    onWidget "Default", getMonthYear
  }
 
  public void toggleInput(){
    click "DatePickerInput"
  }

  public String getSelectedDate(){
    getValue "DatePickerInput"
  }
}
