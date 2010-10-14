package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 30, 2010
 */
public class InstanceCreationException extends TelluriumException {
  protected static final String ERROR_CODE = "TELLURIUM_INSTANCE_CREATION_EXCEPTION";

  public InstanceCreationException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }

  public InstanceCreationException(String message, Exception e) {
    super(message, e);
    this.errorCode = ERROR_CODE;
  }
}
