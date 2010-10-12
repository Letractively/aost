package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 4, 2010
 */
public class BeanNotFoundException extends TelluriumException {

    protected static final String ERROR_CODE = "TELLURIUM_BEAN_NOT_FOUND_EXCEPTION";

    public BeanNotFoundException(String message) {
        super(message);
        this.errorCode = ERROR_CODE;
    }

    public BeanNotFoundException(String message, Exception e) {
        super(message, e);
        this.errorCode = ERROR_CODE;
    }
}
