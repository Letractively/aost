package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Oct 11, 2010
 */
public class FrameworkWiringException extends TelluriumException {

    protected static final String ERROR_CODE = "TELLURIUM_FRAMEWORK_WIRING_EXCEPTION";

    public FrameworkWiringException(String message) {
        super(message);
        this.errorCode = ERROR_CODE;
    }

    public FrameworkWiringException(String message, Exception e) {
        super(message, e);
        this.errorCode = ERROR_CODE;
    }
}
