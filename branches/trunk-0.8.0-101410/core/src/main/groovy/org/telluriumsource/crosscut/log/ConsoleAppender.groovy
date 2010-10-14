package org.telluriumsource.crosscut.log

import org.telluriumsource.annotation.Provider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 * 
 */

@Provider(name="ConsoleAppender", type=Appender.class)
public class ConsoleAppender implements Appender {

  public static final String TE = "TE"

  public void listen(String message) {
    println "${TE}: ${message}"
  }

}