package org.telluriumsource.component.connector;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 9, 2010
 * 
 */
public interface RemoteCommand {
    /** Return the URL query string which will be sent to the browser */
    String getCommandURLString();
}