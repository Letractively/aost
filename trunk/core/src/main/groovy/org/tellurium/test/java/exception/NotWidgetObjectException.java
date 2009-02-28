package org.tellurium.test.java.exception;

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

/*
  public NotWidgetObjectExceptionn() {
    this.errorCode = ERROR_CODE;
  }
*/

  public NotWidgetObjectException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}

