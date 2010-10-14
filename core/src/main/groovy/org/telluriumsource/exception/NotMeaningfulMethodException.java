package org.telluriumsource.exception;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Sep 15, 2010
 */
public class NotMeaningfulMethodException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_NOT_MEANINGFUL_METHOD";

  public NotMeaningfulMethodException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
