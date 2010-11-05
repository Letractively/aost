package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         <p/>
 *         Date: Sep 9, 2010
 */
public class AssertionFailureException extends TelluriumException{

    protected static final String ERROR_CODE = "TELLURIUM_ASSERTION_FAILURE_EXCEPTION";

  public AssertionFailureException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }

  public AssertionFailureException(String message, Exception e) {
    super(message, e);
    this.errorCode = ERROR_CODE;
  }
}
