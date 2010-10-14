package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 9, 2010
 */
public class ConnectionIoException extends TelluriumException {
  protected static final String ERROR_CODE = "TELLURIUM_CONNECT_IO_EXCEPTION";

  public ConnectionIoException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }

  public ConnectionIoException(String message, Exception e) {
    super(message, e);
    this.errorCode = ERROR_CODE;
  }
}
