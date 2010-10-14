package org.telluriumsource.component.connector;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         <p/>
 *         Date: Sep 9, 2010
 */
public class SeleniumException extends RuntimeException {
    public SeleniumException(String message) {
        super(message);
    }

    public SeleniumException(Exception e) {
        super(e);
    }

    public SeleniumException(String message, Exception e) {
        super(message, e);
    }
}
