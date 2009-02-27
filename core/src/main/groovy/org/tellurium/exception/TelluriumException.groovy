package org.tellurium.exception
/**
 * Base class for Tellurium exception hierarchy
 *
 * John.jian.fang@gmail.com
 *
 * Date: Feb 26, 2009
 * 
 */

abstract public class TelluriumException extends RuntimeException{
  protected static final String SEPARATOR = " ";

  protected String errorCode;

  abstract public String getErrorMessage();

  public String getErrorCode(){

    return this.errorCode;
  }
}