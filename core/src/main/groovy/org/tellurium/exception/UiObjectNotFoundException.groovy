package org.tellurium.exception

import org.tellurium.exception.TelluriumException

/**
 *  Base class for Tellurium exception hierarchy
 *
 *  John.jian.fang@gmail.com
 *
 * Date: Feb 26, 2009
 *
 */

public class UiObjectNotFoundException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_UI_OBJECT_NOT_FOUND";
  //later on, may need to refactor it to use resource file so that we can show message for different localities
  protected static final String ERROR_MESSAGE = "Cannot find UI Object";
  
  protected String uid = null;

  def UiObjectNotFoundException() {
    this.errorCode = ERROR_CODE;
  }

  def UiObjectNotFoundException(String uid) {
    this.errorCode = ERROR_CODE;
    this.uid = uid;
  }

  public String getErrorMessage() {
    String id = "";
    if(this.uid != null)
      id = this.uid;

    String msg = "${ERROR_MESSAGE} ${id}"

    return msg;   
  }

}