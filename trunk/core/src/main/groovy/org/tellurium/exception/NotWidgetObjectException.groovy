package org.tellurium.exception

import org.tellurium.exception.TelluriumException

/**
 *  Base class for Tellurium exception hierarchy
 *
 *  John.jian.fang@gmail.com
 *
 *  Date: Feb 26, 2009
 *
 */

public class NotWidgetObjectException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_NOT_WIDGET_OBJECT";
  //later on, may need to refactor it to use resource file so that we can show message for different localities
  protected static final String ERROR_MESSAGE1 = "UI Object";
  protected static final String ERROR_MESSAGE2 = "is not a Widget";
  
  protected String uid = null;

  def NotWidgetObjectExceptionn() {
    this.errorCode = ERROR_CODE;
    super(getErrorMessage());
  }

  def NotWidgetObjectExceptionn(String uid) {
    this.errorCode = ERROR_CODE;
    this.uid = uid;
    super(getErrorMessage());
  }

  public String getErrorMessage() {
    String id = "";
    if(this.uid != null)
      id = this.uid;
    
    String msg = "${ERROR_MESSAGE1} ${id} ${ERROR_MESSAGE2}"

    return msg;
  }
}