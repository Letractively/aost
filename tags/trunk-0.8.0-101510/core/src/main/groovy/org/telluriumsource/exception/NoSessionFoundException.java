package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 11, 2010
 */
public class NoSessionFoundException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_SESSION_NOT_FOUND";
    
  public NoSessionFoundException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
